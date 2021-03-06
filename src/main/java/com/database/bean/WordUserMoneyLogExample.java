package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WordUserMoneyLogExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public WordUserMoneyLogExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
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

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(String value) {
			addCriterion("type =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(String value) {
			addCriterion("type <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(String value) {
			addCriterion("type >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(String value) {
			addCriterion("type >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(String value) {
			addCriterion("type <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(String value) {
			addCriterion("type <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLike(String value) {
			addCriterion("type like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotLike(String value) {
			addCriterion("type not like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List<String> values) {
			addCriterion("type in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List<String> values) {
			addCriterion("type not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(String value1, String value2) {
			addCriterion("type between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(String value1, String value2) {
			addCriterion("type not between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andBuyTypeIsNull() {
			addCriterion("buy_type is null");
			return (Criteria) this;
		}

		public Criteria andBuyTypeIsNotNull() {
			addCriterion("buy_type is not null");
			return (Criteria) this;
		}

		public Criteria andBuyTypeEqualTo(String value) {
			addCriterion("buy_type =", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeNotEqualTo(String value) {
			addCriterion("buy_type <>", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeGreaterThan(String value) {
			addCriterion("buy_type >", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeGreaterThanOrEqualTo(String value) {
			addCriterion("buy_type >=", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeLessThan(String value) {
			addCriterion("buy_type <", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeLessThanOrEqualTo(String value) {
			addCriterion("buy_type <=", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeLike(String value) {
			addCriterion("buy_type like", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeNotLike(String value) {
			addCriterion("buy_type not like", value, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeIn(List<String> values) {
			addCriterion("buy_type in", values, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeNotIn(List<String> values) {
			addCriterion("buy_type not in", values, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeBetween(String value1, String value2) {
			addCriterion("buy_type between", value1, value2, "buyType");
			return (Criteria) this;
		}

		public Criteria andBuyTypeNotBetween(String value1, String value2) {
			addCriterion("buy_type not between", value1, value2, "buyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIsNull() {
			addCriterion("money_type is null");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIsNotNull() {
			addCriterion("money_type is not null");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeEqualTo(String value) {
			addCriterion("money_type =", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotEqualTo(String value) {
			addCriterion("money_type <>", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeGreaterThan(String value) {
			addCriterion("money_type >", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeGreaterThanOrEqualTo(String value) {
			addCriterion("money_type >=", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeLessThan(String value) {
			addCriterion("money_type <", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeLessThanOrEqualTo(String value) {
			addCriterion("money_type <=", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeLike(String value) {
			addCriterion("money_type like", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotLike(String value) {
			addCriterion("money_type not like", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIn(List<String> values) {
			addCriterion("money_type in", values, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotIn(List<String> values) {
			addCriterion("money_type not in", values, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeBetween(String value1, String value2) {
			addCriterion("money_type between", value1, value2, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotBetween(String value1, String value2) {
			addCriterion("money_type not between", value1, value2, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyIsNull() {
			addCriterion("money is null");
			return (Criteria) this;
		}

		public Criteria andMoneyIsNotNull() {
			addCriterion("money is not null");
			return (Criteria) this;
		}

		public Criteria andMoneyEqualTo(Double value) {
			addCriterion("money =", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyNotEqualTo(Double value) {
			addCriterion("money <>", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyGreaterThan(Double value) {
			addCriterion("money >", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyGreaterThanOrEqualTo(Double value) {
			addCriterion("money >=", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyLessThan(Double value) {
			addCriterion("money <", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyLessThanOrEqualTo(Double value) {
			addCriterion("money <=", value, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyIn(List<Double> values) {
			addCriterion("money in", values, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyNotIn(List<Double> values) {
			addCriterion("money not in", values, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyBetween(Double value1, Double value2) {
			addCriterion("money between", value1, value2, "money");
			return (Criteria) this;
		}

		public Criteria andMoneyNotBetween(Double value1, Double value2) {
			addCriterion("money not between", value1, value2, "money");
			return (Criteria) this;
		}

		public Criteria andAddMoneyIsNull() {
			addCriterion("add_money is null");
			return (Criteria) this;
		}

		public Criteria andAddMoneyIsNotNull() {
			addCriterion("add_money is not null");
			return (Criteria) this;
		}

		public Criteria andAddMoneyEqualTo(Double value) {
			addCriterion("add_money =", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyNotEqualTo(Double value) {
			addCriterion("add_money <>", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyGreaterThan(Double value) {
			addCriterion("add_money >", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyGreaterThanOrEqualTo(Double value) {
			addCriterion("add_money >=", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyLessThan(Double value) {
			addCriterion("add_money <", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyLessThanOrEqualTo(Double value) {
			addCriterion("add_money <=", value, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyIn(List<Double> values) {
			addCriterion("add_money in", values, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyNotIn(List<Double> values) {
			addCriterion("add_money not in", values, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyBetween(Double value1, Double value2) {
			addCriterion("add_money between", value1, value2, "addMoney");
			return (Criteria) this;
		}

		public Criteria andAddMoneyNotBetween(Double value1, Double value2) {
			addCriterion("add_money not between", value1, value2, "addMoney");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoIsNull() {
			addCriterion("out_trade_no is null");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoIsNotNull() {
			addCriterion("out_trade_no is not null");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoEqualTo(String value) {
			addCriterion("out_trade_no =", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoNotEqualTo(String value) {
			addCriterion("out_trade_no <>", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoGreaterThan(String value) {
			addCriterion("out_trade_no >", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoGreaterThanOrEqualTo(String value) {
			addCriterion("out_trade_no >=", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoLessThan(String value) {
			addCriterion("out_trade_no <", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoLessThanOrEqualTo(String value) {
			addCriterion("out_trade_no <=", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoLike(String value) {
			addCriterion("out_trade_no like", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoNotLike(String value) {
			addCriterion("out_trade_no not like", value, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoIn(List<String> values) {
			addCriterion("out_trade_no in", values, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoNotIn(List<String> values) {
			addCriterion("out_trade_no not in", values, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoBetween(String value1, String value2) {
			addCriterion("out_trade_no between", value1, value2, "outTradeNo");
			return (Criteria) this;
		}

		public Criteria andOutTradeNoNotBetween(String value1, String value2) {
			addCriterion("out_trade_no not between", value1, value2,
					"outTradeNo");
			return (Criteria) this;
		}

		public Criteria andResourceTypeIsNull() {
			addCriterion("resource_type is null");
			return (Criteria) this;
		}

		public Criteria andResourceTypeIsNotNull() {
			addCriterion("resource_type is not null");
			return (Criteria) this;
		}

		public Criteria andResourceTypeEqualTo(String value) {
			addCriterion("resource_type =", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeNotEqualTo(String value) {
			addCriterion("resource_type <>", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeGreaterThan(String value) {
			addCriterion("resource_type >", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeGreaterThanOrEqualTo(String value) {
			addCriterion("resource_type >=", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeLessThan(String value) {
			addCriterion("resource_type <", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeLessThanOrEqualTo(String value) {
			addCriterion("resource_type <=", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeLike(String value) {
			addCriterion("resource_type like", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeNotLike(String value) {
			addCriterion("resource_type not like", value, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeIn(List<String> values) {
			addCriterion("resource_type in", values, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeNotIn(List<String> values) {
			addCriterion("resource_type not in", values, "resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeBetween(String value1, String value2) {
			addCriterion("resource_type between", value1, value2,
					"resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceTypeNotBetween(String value1, String value2) {
			addCriterion("resource_type not between", value1, value2,
					"resourceType");
			return (Criteria) this;
		}

		public Criteria andResourceIdIsNull() {
			addCriterion("resource_id is null");
			return (Criteria) this;
		}

		public Criteria andResourceIdIsNotNull() {
			addCriterion("resource_id is not null");
			return (Criteria) this;
		}

		public Criteria andResourceIdEqualTo(Integer value) {
			addCriterion("resource_id =", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdNotEqualTo(Integer value) {
			addCriterion("resource_id <>", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdGreaterThan(Integer value) {
			addCriterion("resource_id >", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("resource_id >=", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdLessThan(Integer value) {
			addCriterion("resource_id <", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdLessThanOrEqualTo(Integer value) {
			addCriterion("resource_id <=", value, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdIn(List<Integer> values) {
			addCriterion("resource_id in", values, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdNotIn(List<Integer> values) {
			addCriterion("resource_id not in", values, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdBetween(Integer value1, Integer value2) {
			addCriterion("resource_id between", value1, value2, "resourceId");
			return (Criteria) this;
		}

		public Criteria andResourceIdNotBetween(Integer value1, Integer value2) {
			addCriterion("resource_id not between", value1, value2,
					"resourceId");
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

		public Criteria andCreateTimeIsNull() {
			addCriterion("create_time is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("create_time is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterion("create_time =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterion("create_time <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterion("create_time >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("create_time >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterion("create_time <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterion("create_time <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterion("create_time in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterion("create_time not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterion("create_time between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterion("create_time not between", value1, value2,
					"createTime");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table word_user_money_log
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
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
     * This class corresponds to the database table word_user_money_log
     *
     * @mbggenerated do_not_delete_during_merge Fri Dec 18 14:58:28 GMT+08:00 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}