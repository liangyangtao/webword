package com.lucene.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.lucene.UnbankList;
import com.lucene.UnbankWriter;
import com.lucene.bankcount.BankDataBean;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Articlel;
import com.lucene.entity.doc.Crawl;
import com.lucene.util.PublicTools;

public class BankJob extends QuartzJobBean implements StatefulJob {
	private ComboPooledDataSource dataSource;
	private UnbankWriter<Crawl> crawlWriter;
	private static String initbankdataflag = ResourceBundle.getBundle("temp").getString("initbankdata");
	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setCrawlWriter(UnbankWriter<Crawl> crawlWriter) {
		this.crawlWriter = crawlWriter;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//初始化30天数据
		if(initbankdataflag.equals("true")){
			initdata();
			initbankdataflag="false";
		}
		
		//将前一天数据持久化
		Connection conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			
			String sql = "select * from bankdictionary";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				Searchparam s=new Searchparam();
				s.setRecord(name);
				s.setPageSize(1000000);
				s.setCurrentPage(1);
				s.setArticletype("initYesterday");
				
				UnbankList<Crawl> u=crawlWriter.searchbankdata(s);
				List<Crawl> list=u.getList();
//				for(int i=0;i<list.size();i++){
//					Crawl crawl=list.get(i);
//					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//					String date=df.format(crawl.getNewsTime());
//					if(!date.equals("2014-10-19"))
//						System.out.println(date);
//				}
				
				String curryDate=PublicTools.GetToday();
				String insertDate=PublicTools.getOneDay(curryDate, -1);
				String queryDate=PublicTools.getOneDay(curryDate, -2);
				
				
				PreparedStatement ps1 = conn.prepareStatement("select sum from bankdata where bankid="+id+" and datatime='"+queryDate+"'");
				ResultSet rs1 = ps1.executeQuery();
				int yesterdaycount=0;
				if(rs1.next())yesterdaycount=rs1.getInt(1);
				ps1.close();
				
				double onechange=0;
				
				if(list.size()==0)onechange=-1;
				else if(yesterdaycount==0) onechange=1;
				else onechange=(double)(list.size()-yesterdaycount)/ yesterdaycount;
				
				String endDate=PublicTools.getOneDay(curryDate, -30);
				//从数据库查询相关银行的数据
	    		sql = "select * from bankdata where bankid ="+id+" and datatime>='"+endDate+"' and datatime<'"+curryDate+"'";
	    		PreparedStatement ps3 = conn.prepareStatement(sql);
	    		ResultSet rs3 = ps3.executeQuery();
	    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    		
	    		Map<String,Integer> onedaycountmap=new HashMap<String,Integer>();
	    		while (rs3.next()) {
	    			int bankid=rs3.getInt("bankid");
	    			String datatime=df.format(df.parse(rs3.getString("datatime")));
	    			int sum=rs3.getInt("sum");
	    			onedaycountmap.put(bankid+"+"+datatime, sum);
	    		}
	    		ps3.close();
	    		//将昨天的数据添加进去
	    		onedaycountmap.put(id+"+"+insertDate, list.size());
	    			
	    		List<String> jihedate=PublicTools.twodatebetweendesc(PublicTools.getOneDay(curryDate, -1), PublicTools.getOneDay(curryDate, -30));
	    		
	    		double sevenchange=0;
	    		double fifteenchange=0;
	    		int countbeforeseven=0;
	    		int countlatereseven=0;
	    		int countbeforefifteen=0;
	    		int countlaterefifteen=0;
	    		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
	    		for(int j=0;j<jihedate.size();j++){
	    			String date=jihedate.get(j);
	    			int sum=onedaycountmap.get(id+"+"+date)==null?0:onedaycountmap.get(id+"+"+date);
	    			Map<String,Object> datamap=new HashMap<String,Object>();
	    			datamap.put(date, sum);
	    			dataList.add(datamap);
	    			if(j<7)countbeforeseven+=sum;
	    			if(j>=7&&j<14)countlatereseven+=sum;
	    			if(j<15)countbeforefifteen+=sum;
	    			if(j>=15&&j<30)countlaterefifteen+=sum;
	        	}
	    		
	    		if(countbeforeseven==0)sevenchange=-1;
				else if(countlatereseven==0) sevenchange=1;
				else sevenchange= ((double)(countbeforeseven-countlatereseven)/countlatereseven);  
					
	    		if(countbeforefifteen==0)fifteenchange=-1;
				else if(countlaterefifteen==0) fifteenchange=1;
				else fifteenchange= ((double)(countbeforefifteen-countlaterefifteen)/countlaterefifteen);    
				
				sql = "INSERT INTO bankdata (bankid, datatime,sum,onechange,sevenchange,fifteenchange,sevensum,fifteensum) VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE sum=?,onechange=?,sevenchange=?,fifteenchange=?,sevensum=?,fifteensum=? ";
				PreparedStatement ps2=conn.prepareStatement(sql);
				ps2.setInt(1, id);
				ps2.setString(2, insertDate);
				ps2.setInt(3, list.size());
				ps2.setDouble(4, onechange);
				ps2.setDouble(5, sevenchange);
				ps2.setDouble(6, fifteenchange);
				ps2.setInt(7, countbeforeseven);
				ps2.setInt(8, countbeforefifteen);
				ps2.setInt(9, list.size());
				ps2.setDouble(10, onechange);
				ps2.setDouble(11, sevenchange);
				ps2.setDouble(12, fifteenchange);
				ps2.setInt(13, countbeforeseven);
				ps2.setInt(14, countbeforefifteen);
				
				ps2.executeUpdate();
				
				ps2.close();
			}
			ps.close();
			
		} catch (Exception e) {
			
		} finally {
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception e){}
			
		}
	}
    public void initdata(){

		String sql = "select * from bankdictionary";
		
		Connection conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				Searchparam s=new Searchparam();
				s.setRecord(name);
				s.setPageSize(10000000);
				s.setCurrentPage(1);
				s.setArticletype("init");
				
				Map<String,Integer> countMap=new HashMap<String,Integer>();
				UnbankList<Crawl> u=crawlWriter.searchbankdata(s);
				List<Crawl> list=u.getList();
				for(int i=0;i<list.size();i++){
					Crawl crawl=list.get(i);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date=df.format(crawl.getNewsTime());
					if(countMap.get(date)==null){
						countMap.put(date, 1);
					}else{
						countMap.put(date,countMap.get(date)+1);
					}
//					System.out.println(crawl.getCrawlTitle()+"==="+df.format(crawl.getNewsTime()));
				}
				batchaddbankdata(id, countMap ) ;
			}
			ps.close();
			
			
		} catch (Exception e) {
			
		} finally {
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception e){}
			
		}
		
	
    }
	//批量插入
	public void batchaddbankdata(int bankid,Map<String,Integer> countMap ) {
		Connection conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps=null ;
			String sql = "INSERT INTO bankdata (bankid, datatime,sum,onechange,sevenchange,fifteenchange,sevensum,fifteensum) VALUES (?,?,?,?,?,?,?,?) ";
			ps=conn.prepareStatement(sql);
			
			String curryDate=PublicTools.GetToday();
			String startDate=PublicTools.getOneDay(curryDate, -31);
			List<String> jihedate=PublicTools.twodatebetween(startDate, PublicTools.getOneDay(curryDate, -1));
			int yesterdaycount=0;
			for(int i=0;i<jihedate.size();i++){
				String date=jihedate.get(i);
				ps.setInt(1, bankid);
				ps.setString(2, date);
				if(countMap.get(date)==null){
					ps.setInt(3, 0);
					ps.setDouble(4, -1);
					yesterdaycount=0;
				}else{
					int count=countMap.get(date);
					
					ps.setInt(3, count);
					if(yesterdaycount==0){
						ps.setDouble(4, 1);
					}else{
						double change=(double)(count-yesterdaycount)/ yesterdaycount;
						ps.setDouble(4, change);
					}
					yesterdaycount=count;
				}
				ps.setDouble(5, 0);
				ps.setDouble(6, 0);
				ps.setInt(7, 0);
				ps.setInt(8, 0);
				ps.addBatch();
			}
			
			ps.executeBatch();
			ps.close();
				
			conn.commit();
		}catch(Exception e){
			
			try {conn.rollback();} catch (Exception e1) {}
		}finally{
			try {if(conn!=null)conn.close();} catch (Exception e) {}
		}
	}
}
