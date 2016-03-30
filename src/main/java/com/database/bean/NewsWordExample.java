package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsWordExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public NewsWordExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
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

		public Criteria andSwordIsNull() {
			addCriterion("sword is null");
			return (Criteria) this;
		}

		public Criteria andSwordIsNotNull() {
			addCriterion("sword is not null");
			return (Criteria) this;
		}

		public Criteria andSwordEqualTo(String value) {
			addCriterion("sword =", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordNotEqualTo(String value) {
			addCriterion("sword <>", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordGreaterThan(String value) {
			addCriterion("sword >", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordGreaterThanOrEqualTo(String value) {
			addCriterion("sword >=", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordLessThan(String value) {
			addCriterion("sword <", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordLessThanOrEqualTo(String value) {
			addCriterion("sword <=", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordLike(String value) {
			addCriterion("sword like", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordNotLike(String value) {
			addCriterion("sword not like", value, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordIn(List<String> values) {
			addCriterion("sword in", values, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordNotIn(List<String> values) {
			addCriterion("sword not in", values, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordBetween(String value1, String value2) {
			addCriterion("sword between", value1, value2, "sword");
			return (Criteria) this;
		}

		public Criteria andSwordNotBetween(String value1, String value2) {
			addCriterion("sword not between", value1, value2, "sword");
			return (Criteria) this;
		}

		public Criteria andDoIdIsNull() {
			addCriterion("do_id is null");
			return (Criteria) this;
		}

		public Criteria andDoIdIsNotNull() {
			addCriterion("do_id is not null");
			return (Criteria) this;
		}

		public Criteria andDoIdEqualTo(Integer value) {
			addCriterion("do_id =", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdNotEqualTo(Integer value) {
			addCriterion("do_id <>", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdGreaterThan(Integer value) {
			addCriterion("do_id >", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("do_id >=", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdLessThan(Integer value) {
			addCriterion("do_id <", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdLessThanOrEqualTo(Integer value) {
			addCriterion("do_id <=", value, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdIn(List<Integer> values) {
			addCriterion("do_id in", values, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdNotIn(List<Integer> values) {
			addCriterion("do_id not in", values, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdBetween(Integer value1, Integer value2) {
			addCriterion("do_id between", value1, value2, "doId");
			return (Criteria) this;
		}

		public Criteria andDoIdNotBetween(Integer value1, Integer value2) {
			addCriterion("do_id not between", value1, value2, "doId");
			return (Criteria) this;
		}

		public Criteria andWordTypeIsNull() {
			addCriterion("word_type is null");
			return (Criteria) this;
		}

		public Criteria andWordTypeIsNotNull() {
			addCriterion("word_type is not null");
			return (Criteria) this;
		}

		public Criteria andWordTypeEqualTo(Integer value) {
			addCriterion("word_type =", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeNotEqualTo(Integer value) {
			addCriterion("word_type <>", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeGreaterThan(Integer value) {
			addCriterion("word_type >", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("word_type >=", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeLessThan(Integer value) {
			addCriterion("word_type <", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeLessThanOrEqualTo(Integer value) {
			addCriterion("word_type <=", value, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeIn(List<Integer> values) {
			addCriterion("word_type in", values, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeNotIn(List<Integer> values) {
			addCriterion("word_type not in", values, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeBetween(Integer value1, Integer value2) {
			addCriterion("word_type between", value1, value2, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("word_type not between", value1, value2, "wordType");
			return (Criteria) this;
		}

		public Criteria andWordSourceIsNull() {
			addCriterion("word_source is null");
			return (Criteria) this;
		}

		public Criteria andWordSourceIsNotNull() {
			addCriterion("word_source is not null");
			return (Criteria) this;
		}

		public Criteria andWordSourceEqualTo(String value) {
			addCriterion("word_source =", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceNotEqualTo(String value) {
			addCriterion("word_source <>", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceGreaterThan(String value) {
			addCriterion("word_source >", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceGreaterThanOrEqualTo(String value) {
			addCriterion("word_source >=", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceLessThan(String value) {
			addCriterion("word_source <", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceLessThanOrEqualTo(String value) {
			addCriterion("word_source <=", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceLike(String value) {
			addCriterion("word_source like", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceNotLike(String value) {
			addCriterion("word_source not like", value, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceIn(List<String> values) {
			addCriterion("word_source in", values, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceNotIn(List<String> values) {
			addCriterion("word_source not in", values, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceBetween(String value1, String value2) {
			addCriterion("word_source between", value1, value2, "wordSource");
			return (Criteria) this;
		}

		public Criteria andWordSourceNotBetween(String value1, String value2) {
			addCriterion("word_source not between", value1, value2,
					"wordSource");
			return (Criteria) this;
		}

		public Criteria andSearchCountIsNull() {
			addCriterion("search_count is null");
			return (Criteria) this;
		}

		public Criteria andSearchCountIsNotNull() {
			addCriterion("search_count is not null");
			return (Criteria) this;
		}

		public Criteria andSearchCountEqualTo(Integer value) {
			addCriterion("search_count =", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountNotEqualTo(Integer value) {
			addCriterion("search_count <>", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountGreaterThan(Integer value) {
			addCriterion("search_count >", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("search_count >=", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountLessThan(Integer value) {
			addCriterion("search_count <", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountLessThanOrEqualTo(Integer value) {
			addCriterion("search_count <=", value, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountIn(List<Integer> values) {
			addCriterion("search_count in", values, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountNotIn(List<Integer> values) {
			addCriterion("search_count not in", values, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountBetween(Integer value1, Integer value2) {
			addCriterion("search_count between", value1, value2, "searchCount");
			return (Criteria) this;
		}

		public Criteria andSearchCountNotBetween(Integer value1, Integer value2) {
			addCriterion("search_count not between", value1, value2,
					"searchCount");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
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
     * This class corresponds to the database table news_word
     *
     * @mbggenerated do_not_delete_during_merge Thu Feb 18 09:03:42 GMT+08:00 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}