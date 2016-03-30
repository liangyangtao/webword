package com.web.global.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.Area;
import com.database.bean.AreaExample;
import com.database.bean.Industry;
import com.database.bean.IndustryExample;
import com.database.bean.IndustryExample.Criteria;
import com.database.bean.Tags;
import com.database.bean.TagsExample;
import com.database.bean.WordColumn;
import com.database.bean.WordJournal;
import com.database.bean.WordJournalExample;
import com.database.bean.WordLabel;
import com.database.bean.WordLabelExample;
import com.database.bean.WordPlate;
import com.database.bean.WordPlateExample;
import com.database.dao.AreaMapper;
import com.database.dao.IndustryMapper;
import com.database.dao.TagsMapper;
import com.database.dao.WordColumnMapper;
import com.database.dao.WordJournalMapper;
import com.database.dao.WordLabelMapper;
import com.database.dao.WordPlateMapper;
import com.util.DateUtil;

@Service
public class GlobalService {
	@Autowired
	private IndustryMapper industryMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Autowired
	private WordPlateMapper wordPlateMapper;
	
	@Autowired
	WordJournalMapper  wordJournalMapper;
	
	@Autowired
	private WordColumnMapper wordColumnMapper;
	
	@Autowired
	private WordLabelMapper wordLabelMapper;
	
	@Autowired
	private TagsMapper tagsMapper;
	
	/*
	 * 获取所有区域
	 */
	public List<Area> getAllArea(Integer mid){
		if(mid==null){
			AreaExample example = new AreaExample();
			example.or().andMidEqualTo(0);
			List<Area> list = areaMapper.selectByExample(example);
			getSubArea(list);
			return list;
		}else{
			AreaExample example = new AreaExample();
			example.or().andMidEqualTo(mid);
			return areaMapper.selectByExample(example);
		}
	}
	
	public void getSubArea(List<Area> list){
		if(list!=null && !list.isEmpty()){
			for(Area area:list){
				AreaExample example = new AreaExample();
				example.or().andMidEqualTo(area.getId());
				List<Area> subs = areaMapper.selectByExample(example);
				getSubArea(subs);
				area.setSubs(subs);
			}
		}
	}
	/*
	 * 获取所有的行业
	 */
	public List<Industry> getAllIndustry(){
		IndustryExample  example= new IndustryExample();
		IndustryExample.Criteria cr = example.createCriteria();
		//cr.andShowIndexEqualTo(0);
		return industryMapper.selectByExample(example);
	}
	/*
	 * 获取行业通过pid
	 * @param pid 
	 */
	public List<Industry> getIndustryByPid( int pid){
		IndustryExample example = new IndustryExample();
		Criteria cr = example.createCriteria();
		cr.andPidEqualTo(pid);
		cr.andShowIndexEqualTo(0);
		return industryMapper.selectByExample(example);
	}
	/**
	 * 添加栏目
	 * @param plate
	 * @return
	 */
	public  WordPlate plateGet(int plateId){
		return wordPlateMapper.selectByPrimaryKey(plateId);
	}
	/**
	 * 添加栏目
	 * @param plate
	 * @return
	 */
	public WordPlate plateAdd(WordPlate plate){
		int maxOrderId =wordPlateMapper.getWordPlateMaxOrder(plate.getUserId(),plate.getPid());
		plate.setOrderId(maxOrderId+1);
		plate.setInserTime(DateUtil.currentDateTime());
		int plateId =wordPlateMapper.insertSelective(plate);
		return plate;
	}
	
	/**
	 * 添加推荐的栏目
	 * @param plate
	 * @return
	 */
	public WordPlate plateAddHot(WordPlate plate){

		int maxOrderId = wordPlateMapper.getWordPlateMaxOrder(plate.getUserId(),plate.getPid());
		plate.setOrderId(maxOrderId+1);
		plate.setInserTime(DateUtil.currentDateTime());
		WordColumn column = wordColumnMapper.selectByPrimaryKey(plate.getColumnId());
		if (column != null){
			plate.setConditions(column.getConditions());
			plate.setPlateName(column.getColumnName());
		}
		int plateId = wordPlateMapper.insertSelective(plate);
		return plate;
	}
	
	/**
	 * 修改栏目
	 * @param plate
	 * @return
	 */
	public boolean plateEdit(WordPlate plate){
		//WordPlate newPlate = wordPlateMapper.selectByPrimaryKey(plate.getPlateId());
		wordPlateMapper.updateByPrimaryKeySelective(plate);
		return true;
	} 
	/**
	 * 删除栏目(包含子栏目)
	 * @param plateid
	 * @return
	 */
	public boolean plateDel(int plateId){
		WordPlateExample example = new WordPlateExample();
		example.or().andPlateIdEqualTo(plateId);
		example.or().andPidEqualTo(plateId);
		wordPlateMapper.deleteByExample(example);
		return true;
	}
	/**
	 * 生成目录的树形结构
	 * @param userId
	 * @return
	 */
	public List<WordPlate> getUserWordPlate(int userId){
		//获取用户的id
		List<WordPlate> oldPlateList =wordPlateMapper.getWordPlate(userId);
		///System.out.println(oldPlateList.size());
		List<WordPlate> targetList = new ArrayList<WordPlate>();
		treeList(targetList,oldPlateList,0);
		//生成目录树的结构
		return targetList;
	}
	/**
	 * 递归，生成Ext tree需要树形结构目录
	 * 
	 * @param targetList
	 * @param originalList
	 * @param pid
	 */
	public void treeList(List<WordPlate> targetList, List<WordPlate> oldPlateList, Integer pid) {
		for(WordPlate plate : oldPlateList) {
			if(plate.getPid().equals(pid)) {
				targetList.add(plate);
				treeList(plate.getSubs(), oldPlateList, plate.getPlateId());
			}
		}
	}
	/**
	 * 
	 * @param firstId 拖动元素的id修改顺序
	 * @param loaction 拖动的方向before after
	 * @param loactionId
	 * @return
	 */
	public boolean updateOrder(int firstId,String loaction,int loactionId){
		WordPlate firstWordPlate = wordPlateMapper.selectByPrimaryKey(firstId);
		WordPlate locationWordPlate = wordPlateMapper.selectByPrimaryKey(loactionId);
		if(firstWordPlate==null||locationWordPlate==null){
			return false;
		}
		if("before".equals(loaction)){//插入到之前
			/*
			 * 1.firstId栏目的orderid 等于 loactionId栏目的orderId
			 * 2.id不等于firstId,并orderId大于等于 firstId的所有栏目,orderId+1
			 */
			firstWordPlate.setOrderId(locationWordPlate.getOrderId());
			wordPlateMapper.updateOrder(firstWordPlate);
			//wordPlateMapper.updateOrderAdd(firstWordPlate.getUserId(), firstWordPlate.getPid(),firstWordPlate.getOrderId(), firstWordPlate.getPlateId());
			wordPlateMapper.updateOrderAdd(firstWordPlate);
		}else {//插入到之后
			/**
			 * 1.firstId栏目的orderid 等于 loactionId元素的orderId+1
			 * id不等于firstId,并orderId大于等于 firstId的所有栏目,orderId+1
			 */
			firstWordPlate.setOrderId(locationWordPlate.getOrderId()+1);
			wordPlateMapper.updateOrder(firstWordPlate);
			wordPlateMapper.updateOrderAdd(firstWordPlate);
		}
		return true;
	}
	/**
	 * 获取栏目
	 * @param userId
	 * @return
	 */
	public List<WordPlate> getWordPlate(int userId){
		//WordPlateExample example = new WordPlateExample();
		//return wordPlateMapper.selectByExample(example);
		return wordPlateMapper.getWordPlate(userId);
		
	}
	/**
	 * 通过pid获取栏目
	 * @param userId
	 * @param pid
	 * @return
	 */
	public List<WordPlate> getWordPlareByPid(int userId,int pid){
		return wordPlateMapper.getWordPlateByPid(userId, pid);
	}
	/**
	 * 通过pid获取栏目
	 * @param userId
	 * @param pid
	 * @return
	 */
	public int getCountByPid(int userId,int pid){
		return wordPlateMapper.getCountByPid(userId, pid);
	}
	/**
	 * 搜索期刊名字
	 * @param name
	 * @return
	 */
	public List<WordJournal> searchJournal(String name,int pageId,int pageSize){
		int start = (pageId-1)*pageSize;
		WordJournalExample example = new WordJournalExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		if("".equals(name)){//空的显示全部
			example.or().andPassTypeEqualTo("PASSED");
		}else{
			example.or().andPassTypeEqualTo("PASSED").andNameLike("%"+name+"%");
		}
		//测试数据
		//example.or().andNameLike("%"+name+"%");
		return wordJournalMapper.selectByExample(example);
	}

	/**
	 * 搜索总数
	 * @param name
	 * @return
	 */
	public int  countJournal(String name) {
		// TODO Auto-generated method stub
		WordJournalExample example = new WordJournalExample();
		if("".equals(name)){//空的显示全部
			example.or().andPassTypeEqualTo("PASSED");
		}else{
			example.or().andPassTypeEqualTo("PASSED").andNameLike("%"+name+"%");
		}
		//测试数据
		//example.or().andNameLike("%"+name+"%");
		return wordJournalMapper.countByExample(example);
	}
	
	/**
	 * 获取推荐的栏目
	 * @param id
	 * @return
	 */
	public WordColumn getWordColumn(Integer id){
		
		WordColumn column = wordColumnMapper.selectByPrimaryKey(id);
		return column;
	}

	/**
	 * 根据columnID查询栏目
	 * @param userId
	 * @param columnID
	 * @return
	 */
	public List<WordPlate> getPlatList(int userId, int columnId){
		WordPlateExample example = new WordPlateExample();
		example.or().andColumnIdEqualTo(columnId).andUserIdEqualTo(userId);
		List<WordPlate> list = wordPlateMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 根据名称搜索关键词
	 * @param name
	 * @return
	 */
	public List<WordLabel> searchLabel(String name,int pageId,int pageSize){
		int start = (pageId-1)*pageSize;
		WordLabelExample example = new WordLabelExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		example.or().andNameLike("%"+name+"%");
		example.setOrderByClause("count desc");
		List<WordLabel> list = wordLabelMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 根据名称搜索关键词总数
	 * @param name
	 * @return
	 */
	public int countLabel(String name) {
		
		WordLabelExample example = new WordLabelExample();
		example.or().andNameLike("%"+name+"%");
		int count = wordLabelMapper.countByExample(example);
		return count;
	}

	/**
	 * 根据名称搜索标签
	 * @param name
	 * @return
	 */
	public List<Tags> searchTags(String name,int pageId,int pageSize){
		int start = (pageId-1)*pageSize;
		TagsExample example = new TagsExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		example.or().andNameLike("%"+name+"%");
		example.setOrderByClause("count desc");
		List<Tags> list = tagsMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 根据名称搜索标签总数
	 * @param name
	 * @return
	 */
	public int countTags(String name) {
		
		TagsExample example = new TagsExample();
		example.or().andNameLike("%"+name+"%");
		int count = tagsMapper.countByExample(example);
		return count;
	}
}
