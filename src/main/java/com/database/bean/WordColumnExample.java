package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WordColumnExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public WordColumnExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andPidIsNull() {
			addCriterion("pid is null");
			return (Criteria) this;
		}

		public Criteria andPidIsNotNull() {
			addCriterion("pid is not null");
			return (Criteria) this;
		}

		public Criteria andPidEqualTo(Integer value) {
			addCriterion("pid =", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotEqualTo(Integer value) {
			addCriterion("pid <>", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidGreaterThan(Integer value) {
			addCriterion("pid >", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidGreaterThanOrEqualTo(Integer value) {
			addCriterion("pid >=", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidLessThan(Integer value) {
			addCriterion("pid <", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidLessThanOrEqualTo(Integer value) {
			addCriterion("pid <=", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidIn(List<Integer> values) {
			addCriterion("pid in", values, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotIn(List<Integer> values) {
			addCriterion("pid not in", values, "pid");
			return (Criteria) this;
		}

		public Criteria andPidBetween(Integer value1, Integer value2) {
			addCriterion("pid between", value1, value2, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotBetween(Integer value1, Integer value2) {
			addCriterion("pid not between", value1, value2, "pid");
			return (Criteria) this;
		}

		public Criteria andColumnNameIsNull() {
			addCriterion("column_name is null");
			return (Criteria) this;
		}

		public Criteria andColumnNameIsNotNull() {
			addCriterion("column_name is not null");
			return (Criteria) this;
		}

		public Criteria andColumnNameEqualTo(String value) {
			addCriterion("column_name =", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameNotEqualTo(String value) {
			addCriterion("column_name <>", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameGreaterThan(String value) {
			addCriterion("column_name >", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameGreaterThanOrEqualTo(String value) {
			addCriterion("column_name >=", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameLessThan(String value) {
			addCriterion("column_name <", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameLessThanOrEqualTo(String value) {
			addCriterion("column_name <=", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameLike(String value) {
			addCriterion("column_name like", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameNotLike(String value) {
			addCriterion("column_name not like", value, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameIn(List<String> values) {
			addCriterion("column_name in", values, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameNotIn(List<String> values) {
			addCriterion("column_name not in", values, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameBetween(String value1, String value2) {
			addCriterion("column_name between", value1, value2, "columnName");
			return (Criteria) this;
		}

		public Criteria andColumnNameNotBetween(String value1, String value2) {
			addCriterion("column_name not between", value1, value2,
					"columnName");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNull() {
			addCriterion("user_id is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("user_id is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(Integer value) {
			addCriterion("user_id =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Integer value) {
			addCriterion("user_id <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Integer value) {
			addCriterion("user_id >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("user_id >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Integer value) {
			addCriterion("user_id <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("user_id <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<Integer> values) {
			addCriterion("user_id in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<Integer> values) {
			addCriterion("user_id not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(Integer value1, Integer value2) {
			addCriterion("user_id between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("user_id not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andConditionsIsNull() {
			addCriterion("conditions is null");
			return (Criteria) this;
		}

		public Criteria andConditionsIsNotNull() {
			addCriterion("conditions is not null");
			return (Criteria) this;
		}

		public Criteria andConditionsEqualTo(String value) {
			addCriterion("conditions =", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsNotEqualTo(String value) {
			addCriterion("conditions <>", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsGreaterThan(String value) {
			addCriterion("conditions >", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsGreaterThanOrEqualTo(String value) {
			addCriterion("conditions >=", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsLessThan(String value) {
			addCriterion("conditions <", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsLessThanOrEqualTo(String value) {
			addCriterion("conditions <=", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsLike(String value) {
			addCriterion("conditions like", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsNotLike(String value) {
			addCriterion("conditions not like", value, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsIn(List<String> values) {
			addCriterion("conditions in", values, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsNotIn(List<String> values) {
			addCriterion("conditions not in", values, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsBetween(String value1, String value2) {
			addCriterion("conditions between", value1, value2, "conditions");
			return (Criteria) this;
		}

		public Criteria andConditionsNotBetween(String value1, String value2) {
			addCriterion("conditions not between", value1, value2, "conditions");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIsNull() {
			addCriterion("insert_time is null");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIsNotNull() {
			addCriterion("insert_time is not null");
			return (Criteria) this;
		}

		public Criteria andInsertTimeEqualTo(Date value) {
			addCriterion("insert_time =", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotEqualTo(Date value) {
			addCriterion("insert_time <>", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeGreaterThan(Date value) {
			addCriterion("insert_time >", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("insert_time >=", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeLessThan(Date value) {
			addCriterion("insert_time <", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeLessThanOrEqualTo(Date value) {
			addCriterion("insert_time <=", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIn(List<Date> values) {
			addCriterion("insert_time in", values, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotIn(List<Date> values) {
			addCriterion("insert_time not in", values, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeBetween(Date value1, Date value2) {
			addCriterion("insert_time between", value1, value2, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotBetween(Date value1, Date value2) {
			addCriterion("insert_time not between", value1, value2,
					"insertTime");
			return (Criteria) this;
		}

		public Criteria andTypeIdIsNull() {
			addCriterion("type_id is null");
			return (Criteria) this;
		}

		public Criteria andTypeIdIsNotNull() {
			addCriterion("type_id is not null");
			return (Criteria) this;
		}

		public Criteria andTypeIdEqualTo(Integer value) {
			addCriterion("type_id =", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotEqualTo(Integer value) {
			addCriterion("type_id <>", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdGreaterThan(Integer value) {
			addCriterion("type_id >", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("type_id >=", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdLessThan(Integer value) {
			addCriterion("type_id <", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
			addCriterion("type_id <=", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdIn(List<Integer> values) {
			addCriterion("type_id in", values, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotIn(List<Integer> values) {
			addCriterion("type_id not in", values, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdBetween(Integer value1, Integer value2) {
			addCriterion("type_id between", value1, value2, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
			addCriterion("type_id not between", value1, value2, "typeId");
			return (Criteria) this;
		}

		public Criteria andDelListIsNull() {
			addCriterion("del_list is null");
			return (Criteria) this;
		}

		public Criteria andDelListIsNotNull() {
			addCriterion("del_list is not null");
			return (Criteria) this;
		}

		public Criteria andDelListEqualTo(String value) {
			addCriterion("del_list =", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListNotEqualTo(String value) {
			addCriterion("del_list <>", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListGreaterThan(String value) {
			addCriterion("del_list >", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListGreaterThanOrEqualTo(String value) {
			addCriterion("del_list >=", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListLessThan(String value) {
			addCriterion("del_list <", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListLessThanOrEqualTo(String value) {
			addCriterion("del_list <=", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListLike(String value) {
			addCriterion("del_list like", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListNotLike(String value) {
			addCriterion("del_list not like", value, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListIn(List<String> values) {
			addCriterion("del_list in", values, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListNotIn(List<String> values) {
			addCriterion("del_list not in", values, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListBetween(String value1, String value2) {
			addCriterion("del_list between", value1, value2, "delList");
			return (Criteria) this;
		}

		public Criteria andDelListNotBetween(String value1, String value2) {
			addCriterion("del_list not between", value1, value2, "delList");
			return (Criteria) this;
		}

		public Criteria andTopListIsNull() {
			addCriterion("top_list is null");
			return (Criteria) this;
		}

		public Criteria andTopListIsNotNull() {
			addCriterion("top_list is not null");
			return (Criteria) this;
		}

		public Criteria andTopListEqualTo(String value) {
			addCriterion("top_list =", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListNotEqualTo(String value) {
			addCriterion("top_list <>", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListGreaterThan(String value) {
			addCriterion("top_list >", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListGreaterThanOrEqualTo(String value) {
			addCriterion("top_list >=", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListLessThan(String value) {
			addCriterion("top_list <", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListLessThanOrEqualTo(String value) {
			addCriterion("top_list <=", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListLike(String value) {
			addCriterion("top_list like", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListNotLike(String value) {
			addCriterion("top_list not like", value, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListIn(List<String> values) {
			addCriterion("top_list in", values, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListNotIn(List<String> values) {
			addCriterion("top_list not in", values, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListBetween(String value1, String value2) {
			addCriterion("top_list between", value1, value2, "topList");
			return (Criteria) this;
		}

		public Criteria andTopListNotBetween(String value1, String value2) {
			addCriterion("top_list not between", value1, value2, "topList");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_column
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table word_column
     *
     * @mbggenerated do_not_delete_during_merge Mon Oct 26 15:26:42 GMT+08:00 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}