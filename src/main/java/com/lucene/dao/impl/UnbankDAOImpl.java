package com.lucene.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.lucene.dao.UnbankDAO;


public class UnbankDAOImpl extends JdbcDaoSupport implements UnbankDAO {

	@Override
	public List<Map<String, Object>> findBySql(String sql) {
		try {
			List<Map<String, Object>> list = getJdbcTemplate()
					.queryForList(sql);
			logger.info(sql);
			return list;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public List<Map<String, Object>> findBySql(String sql, Object... args) {
		try {
			List<Map<String, Object>> list = getJdbcTemplate().queryForList(
					sql, args);
			logger.info(sql);
			return list;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public <T> List<T> findBySql(String sql, RowMapper<T> rowMapper) {
		try {
			List<T> list = getJdbcTemplate().query(sql, rowMapper);
			logger.info(sql);
			return list;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public <T> List<T> findBySql(String sql, RowMapper<T> rowMapper,
			Object... args) {
		try {
			List<T> list = getJdbcTemplate().query(sql, rowMapper, args);
			logger.info(sql);
			return list;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public <T> T findBySql(String sql, ResultSetExtractor<T> rse) {
		try {
			T t = getJdbcTemplate().query(sql, rse);
			logger.info(sql);
			return t;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public <T> T findBySql(String sql, ResultSetExtractor<T> rse,
			Object... args) {
		try {
			T t = getJdbcTemplate().query(sql, rse, args);
			logger.info(sql);
			return t;
		} catch (Exception e) {
			logger.error("findBySql error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
		try {
			int[] n = getJdbcTemplate().batchUpdate(sql, bpss);
			logger.info(sql);
			return n;
		} catch (Exception e) {
			logger.error("batchUpdate error!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public int update(String sql, Object... args) {
		try {
			int n = getJdbcTemplate().update(sql, args);
			logger.info(sql);
			return n;
		} catch (Exception e) {
			logger.error("update error!", e);
			throw new RuntimeException();
		}
	}

}
