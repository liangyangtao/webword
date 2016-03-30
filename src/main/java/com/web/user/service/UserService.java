package com.web.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.User;
import com.database.bean.UserExample;
import com.database.bean.UserExample.Criteria;
import com.database.dao.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private  UserMapper userMapper  ;

	/*
	 * 获取文章列表
	 */
	public List<User> login(String userName,String passWord){
		List<User> listUser = new ArrayList<User>();
		UserExample example = new UserExample();
		example.setLimitStart(0);
		example.setLimitEnd(10);
		Criteria cr =example.createCriteria();
		cr.andUserAccountEqualTo(userName);
		cr.andUserPasswordEqualTo(passWord);
		
		listUser = userMapper.selectByExample(example);
		System.out.println("listArticle.length="+listUser.size());
		return listUser;
	}
}
