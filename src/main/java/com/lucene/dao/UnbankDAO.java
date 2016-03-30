package com.lucene.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface UnbankDAO {

	/**
	 * 根据SQL查询
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> findBySql(String sql);

	/**
	 * 根据SQL查询
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	List<Map<String, Object>> findBySql(String sql, Object... args);

	/**
	 * 根据SQL查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	<T> List<T> findBySql(String sql, RowMapper<T> rowMapper);

	/**
	 * 根据SQL查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 */
	<T> List<T> findBySql(String sql, RowMapper<T> rowMapper, Object... args);

	/**
	 * 根据SQL查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param rse
	 * @return
	 */
	<T> T findBySql(String sql, ResultSetExtractor<T> rse);

	/**
	 * 根据SQL查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param rse
	 * @param args
	 * @return
	 */
	<T> T findBySql(String sql, ResultSetExtractor<T> rse, Object... args);

	/**
	 * 批量更新
	 * 
	 * @param sql
	 * @param bpss
	 * @return
	 */
	int[] batchUpdate(String sql, BatchPreparedStatementSetter bpss);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	int update(String sql, Object... args);

}
