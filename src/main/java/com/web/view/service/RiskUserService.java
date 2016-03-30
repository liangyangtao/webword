package com.web.view.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.Companyandbank;
import com.database.bean.CompanyandbankExample;
import com.database.bean.Module;
import com.database.bean.ModuleExample;
import com.database.bean.MyModule;
import com.database.bean.MyModuleExample;
import com.database.bean.UserOtherInfo;
import com.database.bean.UserOtherInfoExample;
import com.database.bean.WordUsers;
import com.database.bean.WordUsersExample;
import com.database.bean.WordUsersRoles;
import com.database.dao.CompanyandbankMapper;
import com.database.dao.MyModuleMapper;
import com.database.dao.UserOtherInfoMapper;
import com.database.dao.WordUsersMapper;
import com.database.dao.WordUsersRolesMapper;
import com.web.bean.ParamsBean;

@Service
public class RiskUserService {
	
	@Autowired
	WordUsersMapper wordUsersMapper;
	@Autowired
	WordUsersRolesMapper wordUsersRolesMapper;
	@Autowired
	MyModuleMapper myModuleMapper;
	@Autowired
	UserOtherInfoMapper userOtherInfoMapper;
	@Autowired
	CompanyandbankMapper companyandbankMapper;
	
	private static Logger logger = Logger.getLogger(HomeService.class);

	/*
	 * 根据账号或手机号查询用户
	 */
	public WordUsers login(String account) {
		WordUsersExample example = new WordUsersExample();
		example.or().andUserAccountEqualTo(account);
		example.or().andUserPhoneEqualTo(account);
		List<WordUsers> list = wordUsersMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/*
	 * 登陆
	 */
	public List login(String account, String md5Str) {
		WordUsersExample example = new WordUsersExample();
		example.or().andUserAccountEqualTo(account).andUserPasswordEqualTo(md5Str);
		example.or().andUserPhoneEqualTo(account).andUserPasswordEqualTo(md5Str);
		List<WordUsers> list = wordUsersMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	/*
	 * 注册
	 */
	public WordUsers regist(WordUsers user) {
		wordUsersMapper.insert(user);
		WordUsersRoles wur = new WordUsersRoles();
		wur.setRoleId(3);
		wur.setUserId(user.getUserId());
		wordUsersRolesMapper.insert(wur);
		return user;
	}
	public void regist(MyModule myModule) {
		myModuleMapper.insertSelective(myModule);
	}
	
	
	/*
	 * 完善资料
	 */
	public void perfectdata(WordUsers user) {
		wordUsersMapper.updateByPrimaryKey(user);
	}
	public void perfectdata(MyModule myModule) {
		myModuleMapper.updateByPrimaryKeySelective(myModule);
	}
	public List<MyModule> getPersonData(WordUsers user) {
		MyModuleExample example = new MyModuleExample();
		example.or().andUseridEqualTo(user.getUserId());
		List<MyModule> list = myModuleMapper.selectByExample(example);
		return list;
	}
	
	/*
	 * 修改密码
	 */
	public void editpassword(WordUsers user) {
		wordUsersMapper.updateByPrimaryKeySelective(user);
	}

	public void insetCompanyInfo(UserOtherInfo userOtherInfo) {
		userOtherInfoMapper.insert(userOtherInfo);
	}

	public List<Companyandbank> searchCompanyAndBankList(ParamsBean pb, String name,
			int pageId, int pageSize) {
		int start = (pageId-1)*pageSize;
		CompanyandbankExample example = new CompanyandbankExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		
		example.or().andBcnameLike("%"+name+"%");
		List<Companyandbank> list2 = companyandbankMapper.selectByExample(example);
		return list2;
	}

	public int searchCompanyAndBankListCount(ParamsBean pb, String name) {
		CompanyandbankExample example = new CompanyandbankExample();
		example.or().andBcnameLike("%"+name+"%");
		List<Companyandbank> list2 = companyandbankMapper.selectByExample(example);
		return list2.size();
	}

	public UserOtherInfo getUserOtherInfo(Integer userId) {
		UserOtherInfoExample example = new UserOtherInfoExample();
		example.or().andUserIdEqualTo(userId);
		List<UserOtherInfo> userOtherInfos = userOtherInfoMapper.selectByExample(example);
		if(userOtherInfos==null||userOtherInfos.size()<1){
			return null;
		}
		return userOtherInfos.get(0);
	}

	public void perfectUserOtherInfo(UserOtherInfo uoi) {
		UserOtherInfo userOtherInfo=getUserOtherInfo(uoi.getUserId());
//		UserOtherInfoExample ex= new UserOtherInfoExample();
//		ex.or().andUserIdEqualTo(uoi.getUserId());
//		userOtherInfoMapper.deleteByExample(ex);
//		userOtherInfoMapper.insertSelective(uoi);
		
		if(userOtherInfo==null){
			userOtherInfoMapper.insertSelective(uoi);
		}else{
			UserOtherInfoExample example = new UserOtherInfoExample();
			uoi.setId(userOtherInfo.getId());
			example.or().andUserIdEqualTo(uoi.getUserId());
			userOtherInfoMapper.updateByExample(uoi, example);
			//userOtherInfoMapper.updateByExample(uoi, example);
		}
	}

	public Companyandbank getCompanyAndBank(Integer companyId) {
		return companyandbankMapper.selectByPrimaryKey(companyId);
	}
	
}
