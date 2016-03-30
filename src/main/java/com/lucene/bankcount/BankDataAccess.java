package com.lucene.bankcount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.lucene.UnbankList;
import com.lucene.UnbankWriter;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Crawl;
import com.lucene.util.PublicTools;

public class BankDataAccess implements IBankDataAccess{
	@Autowired
	private ComboPooledDataSource dataSource;
	@Autowired
	private UnbankWriter<Crawl> crawlWriter;
	private static long selecttime=0;
	private static Map<Integer,StringBuffer> recordkeywordmap=new HashMap<Integer,StringBuffer>();
//	private static long loadtime=0;
//	private static List<Crawl> hots=null;
	private static long bankinfortime=0;
	private static Map<Integer,Map<String,Object>> bankinformap=new HashMap<Integer,Map<String,Object>>();
	public synchronized String getfenleirecord(int type){
		if((System.currentTimeMillis()-selecttime)>3600000){
			Connection conn=null;
	    	try {
	    		conn=dataSource.getConnection();
	    		conn.setAutoCommit(true); 
	    		PreparedStatement ps1 = conn.prepareStatement("select word,classid from classdictionary");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next()){
					String word=rs1.getString("word");
					int classid=rs1.getInt("classid");
					
					StringBuffer sb;
					if(recordkeywordmap.get(classid)!=null){
						sb=recordkeywordmap.get(classid);
						
					}else{
						sb=new StringBuffer();
						recordkeywordmap.put(classid, sb);
					}
					sb.append(word+" ");
				}
				ps1.close();
				
				selecttime=System.currentTimeMillis();
	    	} catch (Exception e) {
				
	    	} finally {
	    		try{if(conn!=null)conn.close();}catch(Exception e){}
	    	}
		}
		return recordkeywordmap.get(type).toString();
	}
	public synchronized Map<String,Object> getbankinfor(int bankid){
		if((System.currentTimeMillis()-bankinfortime)>3600000){
			Connection conn=null;
	    	try {
	    		conn=dataSource.getConnection();
	    		conn.setAutoCommit(true); 
	    		PreparedStatement ps1 = conn.prepareStatement("select id,name,logoimgname from bankdictionary where state=1");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next()){
					String logoimgname=rs1.getString("logoimgname");
					String name=rs1.getString("name");
					int id=rs1.getInt("id");
					
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("id", id);
					map.put("name", name);
					map.put("logoimgname", logoimgname);
					
					bankinformap.put(id, map);
				}
				ps1.close();
				
				bankinfortime=System.currentTimeMillis();
	    	} catch (Exception e) {
				
	    	} finally {
	    		try{if(conn!=null)conn.close();}catch(Exception e){}
	    	}
		}
		return bankinformap.get(bankid);
	}
//	public synchronized List<Crawl> getnews(Map<Integer,String> filter){
//		
//		if((System.currentTimeMillis()-loadtime)>600000){
//	    	try {
//	    		String record=getfenleirecord();
//	    		Searchparam s=new Searchparam();
//				s.setRecord(record);
//				s.setPageSize(36);
//				s.setCurrentPage(1);
//				s.setArticletype("");
//				UnbankList<Crawl> youbu=crawlWriter.searchbankdata(s);
//				
//				hots=youbu.getList();
//				
//				loadtime=System.currentTimeMillis();
//	    	} catch (Exception e) {
//				
//	    	} finally {
//	    	}
//		}
//		List<Crawl> crawllist=new ArrayList<Crawl>();
//		int count=0;
//		for(int i=0;i<hots.size();i++){
//			Crawl c=hots.get(i);
//			if(filter.get(c.getCrawlId())==null){
//				count++;
//				crawllist.add(c);
//			}
//			if(count==12)break;
//		}
//		return crawllist;
//	}
    public Map<String,Object> getbankcountdata(int[] bankids){
    	Connection conn=null;
    	Map<String,Object> returnmap=new HashMap<String,Object>();
    	try {
    		Map<Integer,BankDataBean> bankDataMap=new HashMap<Integer,BankDataBean>();
    		conn=dataSource.getConnection();
    		conn.setAutoCommit(true);
    		
    		String curryDate=PublicTools.GetToday();
    		String endDate=PublicTools.getOneDay(curryDate, -30);
    		String startDate=PublicTools.getOneDay(curryDate, -1);
    		
    		List<String> jihedate=PublicTools.twodatebetween(endDate, startDate);
    		
			if(bankids==null||bankids.length==0) return null;
			
			StringBuffer idinconditions=new StringBuffer();
			
			for(int i=0;i<bankids.length;i++){
				idinconditions.append(bankids[i]+",");
			}
			idinconditions.delete(idinconditions.length()-1, idinconditions.length());
			
			//从数据库查询相关银行的数据
    		String sql = "select * from bankdata where bankid in ("+idinconditions.toString()+") and datatime>='"+endDate+"' and datatime<'"+curryDate+"'";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		
    		Map<String,Map<String,Object>> onedaycountmap=new HashMap<String,Map<String,Object>>();
    		while (rs.next()) {
    			Map<String,Object> map =new HashMap<String,Object>();
    			int bankid=rs.getInt("bankid");
    			String datatime=df.format(df.parse(rs.getString("datatime")));
    			int sum=rs.getInt("sum");
    			int sevensum=rs.getInt("sevensum");
    			int fifteensum=rs.getInt("fifteensum");
    			double onechange=rs.getDouble("onechange");
    			double sevenchange=rs.getDouble("sevenchange");
    			double fifteenchange=rs.getDouble("fifteenchange");

    			map.put("sum", sum);
    			map.put("sevensum", sevensum);
    			map.put("fifteensum", fifteensum);
    			map.put("onechange", onechange);
    			map.put("sevenchange", sevenchange);
    			map.put("fifteenchange", fifteenchange);
    			onedaycountmap.put(bankid+"+"+datatime, map);
    		}
    		ps.close();	
    		
    		//格式数据
    		for(int i=0;i<bankids.length;i++){
    			BankDataBean bdb=new BankDataBean();
    			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
    			for(int j=0;j<jihedate.size();j++){
    				String date=jihedate.get(j);
    				Map<String,Object> map =onedaycountmap.get(bankids[i]+"+"+date);
    				Map<String,Object> datamap=new HashMap<String,Object>();
    				int sum=0;
    				int sevensum=0;
    				int fifteensum=0;
        			double onechange=0;
        			double sevenchange=0;
        			double fifteenchange=0;
        			if(map!=null){
        				sum=(Integer)map.get("sum");
        				sevensum=(Integer)map.get("sevensum");
        				fifteensum=(Integer)map.get("fifteensum");
        				onechange=(Double)map.get("onechange");
            			sevenchange=(Double)map.get("sevenchange");
            			fifteenchange=(Double)map.get("fifteenchange");
        			}
    				datamap.put("sum", sum);
    				datamap.put("date", date);
    				dataList.add(datamap);
    				if(j==jihedate.size()-1){
    					bdb.setOnedaycount(sum);
    					bdb.setOnedaychange(onechange);
    					bdb.setSevendaycount(sevensum);
    					bdb.setSevendaychange(sevenchange);
    					bdb.setFifteendaycount(fifteensum);
    					bdb.setFifteendaychange(fifteenchange);
    				}
        		}
    			bdb.setDataList(dataList);
    			bankDataMap.put(bankids[i], bdb);
    		}
    		//-------------------------------屏蔽掉
    		PreparedStatement ps1 = conn.prepareStatement("select id,name,logoimgname from bankdictionary where id in ("+idinconditions.toString()+")");
			ResultSet rs1 = ps1.executeQuery();
			StringBuffer sb=new StringBuffer();
			while(rs1.next()){
				String name=rs1.getString("name");
				int id=rs1.getInt("id");
				sb.append(name+" ");
    			String logoimgname=rs1.getString("logoimgname");
    			
    			bankDataMap.get(id).setLogoimgname(logoimgname);
    			bankDataMap.get(id).setBankname(name);
//				record=record.replaceAll(name, " ");
			}
			ps1.close();
			
			Searchparam s=new Searchparam();
			s.setRecord(sb.toString());
			s.setPageSize(24);
			s.setCurrentPage(1);
			s.setArticletype("onebank");
			UnbankList<Crawl> dibu=crawlWriter.searchbankdata(s);
			Map<Integer,String> filter=new HashMap<Integer,String>();
			for(int i=0;i<dibu.getList().size();i++){
				Crawl c=dibu.getList().get(i);
				filter.put(c.getCrawlId(), "");
			}
			//-------------------------------
			
			//-------------------------------放开
//    		Map<Integer,String> filter=new HashMap<Integer,String>();
//    		
//    		Map<Integer,UnbankList<Crawl>> bottomnews=new HashMap<Integer,UnbankList<Crawl>>();
//    		for(int i=0;i<bankids.length;i++){
//    			Map<String,Object> m=getbankinfor(bankids[i]);
//    			if(m!=null){
//    				Searchparam s=new Searchparam();
//        			s.setRecord((String)m.get("name"));
//        			s.setPageSize(20);
//        			s.setCurrentPage(1);
//        			s.setArticletype("onebank");
//        			UnbankList<Crawl> dibu=crawlWriter.searchbankdata(s);
//        			
//        			for(int j=0;j<dibu.getList().size();j++){
//        				Crawl c=dibu.getList().get(j);
//        				filter.put(c.getCrawlId(), "");
//        			}
//        			
//        			bottomnews.put(bankids[i], dibu);
//    			}
//    			
//    		}
    		//-------------------------------
			
			String record=getfenleirecord(1);
			Searchparam s1=new Searchparam();
			s1.setRecord(record);
			s1.setPageSize(100);
			s1.setCurrentPage(1);
			s1.setArticletype("onebank");
			UnbankList<Crawl> youbu=crawlWriter.searchbankdata(s1);
			
			List<Crawl> crawllist=new ArrayList<Crawl>();
			int count=0;
			for(int i=0;i<youbu.getList().size();i++){
				Crawl c=youbu.getList().get(i);
				if(filter.get(c.getCrawlId())==null){
					count++;
					crawllist.add(c);
				}
				if(count==12)break;
			}
			
//			returnmap.put("bottomnews", bottomnews);
			returnmap.put("bottomnews", dibu.getList());
			returnmap.put("rightnews", crawllist);
			returnmap.put("bankData", bankDataMap);
    		
    	} catch (Exception e) {
    			
    	} finally {
    		try{if(conn!=null)conn.close();}catch(Exception e){}
    	}
    	return returnmap;
    }
    /*
     * type 2=宏观发展 3=监管 4国有银行5股份制银行
     * record 搜索关键词
     * */
    public UnbankList<Crawl> gethongguan(int type,String record,int pagesize,int currypage){
//    	Connection conn=null;
    	UnbankList<Crawl> dibu=null;
    	try {
//    		conn=dataSource.getConnection();
//    		conn.setAutoCommit(true); 
//    		PreparedStatement ps1 = conn.prepareStatement("select word from classdictionary where classid="+type);
//			ResultSet rs1 = ps1.executeQuery();
//			StringBuffer sb=new StringBuffer();
//			while(rs1.next()){
//				sb.append(rs1.getString(1)+" ");
//			}
//			ps1.close();

			Searchparam s=new Searchparam();
			s.setRecord(getfenleirecord(type));
			s.setPageSize(pagesize);
			s.setCurrentPage(currypage);
			s.setArticletype("fenlei");
			s.setBankdatarecord(record);
			dibu=crawlWriter.searchbankdata(s);
			
    	} catch (Exception e) {
			
    	} finally {
//    		try{if(conn!=null)conn.close();}catch(Exception e){}
    	}
    	return dibu;
    }
    public static void main(String[] args){
    		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"activemq-context.xml","spring-context.xml"});
    		
    		context.start();
    		IBankDataAccess fqaService = (IBankDataAccess) context.getBean("bankDataAccess");
    		System.out.println(fqaService.getbankcountdata(new int[]{1,2}));
    }
}
