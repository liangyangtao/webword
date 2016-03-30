package com.unbank.test.compant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unbank.test.dao.TestDao;

@Component
public class TestCompant {

	@Autowired
	private TestDao testDao;
	
	public String test() {
		return testDao.test();
	}
}
