package com.unbank.test.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.unbank.test.Test;

@Repository
public class TestDao extends SqlSessionDaoSupport {

	public String test() {
		Test t = new Test();
		t.setId(11);
		List<Test>  tests = getSqlSession().selectList("com.unbank.mybatis.Test.selectTest", t);
		System.out.println(tests.size());
		return "hello";
	}
}
