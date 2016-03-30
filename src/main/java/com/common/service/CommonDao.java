package com.common.service;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDao extends SqlSessionDaoSupport {

	public void insert(String sqlMapName, Object obj) {
		getSqlSession().insert(sqlMapName, obj);
	}
	
	public List<?> selectList(String sqlMapName, Object obj) {
		return getSqlSession().selectList(sqlMapName, obj);
	}
}
