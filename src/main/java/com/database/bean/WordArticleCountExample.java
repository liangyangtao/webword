package com.database.bean;

import java.util.ArrayList;
import java.util.List;

public class WordArticleCountExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public WordArticleCountExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_article_count
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_article_count
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

		public Criteria andArticleIdIsNull() {
			addCriterion("article_id is null");
			return (Criteria) this;
		}

		public Criteria andArticleIdIsNotNull() {
			addCriterion("article_id is not null");
			return (Criteria) this;
		}

		public Criteria andArticleIdEqualTo(Integer value) {
			addCriterion("article_id =", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdNotEqualTo(Integer value) {
			addCriterion("article_id <>", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdGreaterThan(Integer value) {
			addCriterion("article_id >", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("article_id >=", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdLessThan(Integer value) {
			addCriterion("article_id <", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdLessThanOrEqualTo(Integer value) {
			addCriterion("article_id <=", value, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdIn(List<Integer> values) {
			addCriterion("article_id in", values, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdNotIn(List<Integer> values) {
			addCriterion("article_id not in", values, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdBetween(Integer value1, Integer value2) {
			addCriterion("article_id between", value1, value2, "articleId");
			return (Criteria) this;
		}

		public Criteria andArticleIdNotBetween(Integer value1, Integer value2) {
			addCriterion("article_id not between", value1, value2, "articleId");
			return (Criteria) this;
		}

		public Criteria andDownCountIsNull() {
			addCriterion("down_count is null");
			return (Criteria) this;
		}

		public Criteria andDownCountIsNotNull() {
			addCriterion("down_count is not null");
			return (Criteria) this;
		}

		public Criteria andDownCountEqualTo(Integer value) {
			addCriterion("down_count =", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountNotEqualTo(Integer value) {
			addCriterion("down_count <>", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountGreaterThan(Integer value) {
			addCriterion("down_count >", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("down_count >=", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountLessThan(Integer value) {
			addCriterion("down_count <", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountLessThanOrEqualTo(Integer value) {
			addCriterion("down_count <=", value, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountIn(List<Integer> values) {
			addCriterion("down_count in", values, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountNotIn(List<Integer> values) {
			addCriterion("down_count not in", values, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountBetween(Integer value1, Integer value2) {
			addCriterion("down_count between", value1, value2, "downCount");
			return (Criteria) this;
		}

		public Criteria andDownCountNotBetween(Integer value1, Integer value2) {
			addCriterion("down_count not between", value1, value2, "downCount");
			return (Criteria) this;
		}

		public Criteria andViewCountIsNull() {
			addCriterion("view_count is null");
			return (Criteria) this;
		}

		public Criteria andViewCountIsNotNull() {
			addCriterion("view_count is not null");
			return (Criteria) this;
		}

		public Criteria andViewCountEqualTo(Integer value) {
			addCriterion("view_count =", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountNotEqualTo(Integer value) {
			addCriterion("view_count <>", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountGreaterThan(Integer value) {
			addCriterion("view_count >", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("view_count >=", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountLessThan(Integer value) {
			addCriterion("view_count <", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountLessThanOrEqualTo(Integer value) {
			addCriterion("view_count <=", value, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountIn(List<Integer> values) {
			addCriterion("view_count in", values, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountNotIn(List<Integer> values) {
			addCriterion("view_count not in", values, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountBetween(Integer value1, Integer value2) {
			addCriterion("view_count between", value1, value2, "viewCount");
			return (Criteria) this;
		}

		public Criteria andViewCountNotBetween(Integer value1, Integer value2) {
			addCriterion("view_count not between", value1, value2, "viewCount");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_article_count
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
     * This class corresponds to the database table word_article_count
     *
     * @mbggenerated do_not_delete_during_merge Mon Oct 26 15:26:42 GMT+08:00 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}