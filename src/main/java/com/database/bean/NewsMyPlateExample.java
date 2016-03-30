package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsMyPlateExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public NewsMyPlateExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
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

		public Criteria andPlateIdIsNull() {
			addCriterion("plate_id is null");
			return (Criteria) this;
		}

		public Criteria andPlateIdIsNotNull() {
			addCriterion("plate_id is not null");
			return (Criteria) this;
		}

		public Criteria andPlateIdEqualTo(Integer value) {
			addCriterion("plate_id =", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdNotEqualTo(Integer value) {
			addCriterion("plate_id <>", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdGreaterThan(Integer value) {
			addCriterion("plate_id >", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("plate_id >=", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdLessThan(Integer value) {
			addCriterion("plate_id <", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdLessThanOrEqualTo(Integer value) {
			addCriterion("plate_id <=", value, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdIn(List<Integer> values) {
			addCriterion("plate_id in", values, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdNotIn(List<Integer> values) {
			addCriterion("plate_id not in", values, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdBetween(Integer value1, Integer value2) {
			addCriterion("plate_id between", value1, value2, "plateId");
			return (Criteria) this;
		}

		public Criteria andPlateIdNotBetween(Integer value1, Integer value2) {
			addCriterion("plate_id not between", value1, value2, "plateId");
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

		public Criteria andColumnIdIsNull() {
			addCriterion("column_id is null");
			return (Criteria) this;
		}

		public Criteria andColumnIdIsNotNull() {
			addCriterion("column_id is not null");
			return (Criteria) this;
		}

		public Criteria andColumnIdEqualTo(Integer value) {
			addCriterion("column_id =", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdNotEqualTo(Integer value) {
			addCriterion("column_id <>", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdGreaterThan(Integer value) {
			addCriterion("column_id >", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("column_id >=", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdLessThan(Integer value) {
			addCriterion("column_id <", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdLessThanOrEqualTo(Integer value) {
			addCriterion("column_id <=", value, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdIn(List<Integer> values) {
			addCriterion("column_id in", values, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdNotIn(List<Integer> values) {
			addCriterion("column_id not in", values, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdBetween(Integer value1, Integer value2) {
			addCriterion("column_id between", value1, value2, "columnId");
			return (Criteria) this;
		}

		public Criteria andColumnIdNotBetween(Integer value1, Integer value2) {
			addCriterion("column_id not between", value1, value2, "columnId");
			return (Criteria) this;
		}

		public Criteria andPlateNameIsNull() {
			addCriterion("plate_name is null");
			return (Criteria) this;
		}

		public Criteria andPlateNameIsNotNull() {
			addCriterion("plate_name is not null");
			return (Criteria) this;
		}

		public Criteria andPlateNameEqualTo(String value) {
			addCriterion("plate_name =", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameNotEqualTo(String value) {
			addCriterion("plate_name <>", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameGreaterThan(String value) {
			addCriterion("plate_name >", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameGreaterThanOrEqualTo(String value) {
			addCriterion("plate_name >=", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameLessThan(String value) {
			addCriterion("plate_name <", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameLessThanOrEqualTo(String value) {
			addCriterion("plate_name <=", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameLike(String value) {
			addCriterion("plate_name like", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameNotLike(String value) {
			addCriterion("plate_name not like", value, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameIn(List<String> values) {
			addCriterion("plate_name in", values, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameNotIn(List<String> values) {
			addCriterion("plate_name not in", values, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameBetween(String value1, String value2) {
			addCriterion("plate_name between", value1, value2, "plateName");
			return (Criteria) this;
		}

		public Criteria andPlateNameNotBetween(String value1, String value2) {
			addCriterion("plate_name not between", value1, value2, "plateName");
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

		public Criteria andInserTimeIsNull() {
			addCriterion("inser_time is null");
			return (Criteria) this;
		}

		public Criteria andInserTimeIsNotNull() {
			addCriterion("inser_time is not null");
			return (Criteria) this;
		}

		public Criteria andInserTimeEqualTo(Date value) {
			addCriterion("inser_time =", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeNotEqualTo(Date value) {
			addCriterion("inser_time <>", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeGreaterThan(Date value) {
			addCriterion("inser_time >", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("inser_time >=", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeLessThan(Date value) {
			addCriterion("inser_time <", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeLessThanOrEqualTo(Date value) {
			addCriterion("inser_time <=", value, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeIn(List<Date> values) {
			addCriterion("inser_time in", values, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeNotIn(List<Date> values) {
			addCriterion("inser_time not in", values, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeBetween(Date value1, Date value2) {
			addCriterion("inser_time between", value1, value2, "inserTime");
			return (Criteria) this;
		}

		public Criteria andInserTimeNotBetween(Date value1, Date value2) {
			addCriterion("inser_time not between", value1, value2, "inserTime");
			return (Criteria) this;
		}

		public Criteria andOrderIdIsNull() {
			addCriterion("order_id is null");
			return (Criteria) this;
		}

		public Criteria andOrderIdIsNotNull() {
			addCriterion("order_id is not null");
			return (Criteria) this;
		}

		public Criteria andOrderIdEqualTo(Integer value) {
			addCriterion("order_id =", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdNotEqualTo(Integer value) {
			addCriterion("order_id <>", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdGreaterThan(Integer value) {
			addCriterion("order_id >", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("order_id >=", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdLessThan(Integer value) {
			addCriterion("order_id <", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
			addCriterion("order_id <=", value, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdIn(List<Integer> values) {
			addCriterion("order_id in", values, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdNotIn(List<Integer> values) {
			addCriterion("order_id not in", values, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdBetween(Integer value1, Integer value2) {
			addCriterion("order_id between", value1, value2, "orderId");
			return (Criteria) this;
		}

		public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
			addCriterion("order_id not between", value1, value2, "orderId");
			return (Criteria) this;
		}

		public Criteria andPlateLevelIsNull() {
			addCriterion("plate_level is null");
			return (Criteria) this;
		}

		public Criteria andPlateLevelIsNotNull() {
			addCriterion("plate_level is not null");
			return (Criteria) this;
		}

		public Criteria andPlateLevelEqualTo(Integer value) {
			addCriterion("plate_level =", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelNotEqualTo(Integer value) {
			addCriterion("plate_level <>", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelGreaterThan(Integer value) {
			addCriterion("plate_level >", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelGreaterThanOrEqualTo(Integer value) {
			addCriterion("plate_level >=", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelLessThan(Integer value) {
			addCriterion("plate_level <", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelLessThanOrEqualTo(Integer value) {
			addCriterion("plate_level <=", value, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelIn(List<Integer> values) {
			addCriterion("plate_level in", values, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelNotIn(List<Integer> values) {
			addCriterion("plate_level not in", values, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelBetween(Integer value1, Integer value2) {
			addCriterion("plate_level between", value1, value2, "plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateLevelNotBetween(Integer value1, Integer value2) {
			addCriterion("plate_level not between", value1, value2,
					"plateLevel");
			return (Criteria) this;
		}

		public Criteria andPlateOtherIsNull() {
			addCriterion("plate_other is null");
			return (Criteria) this;
		}

		public Criteria andPlateOtherIsNotNull() {
			addCriterion("plate_other is not null");
			return (Criteria) this;
		}

		public Criteria andPlateOtherEqualTo(String value) {
			addCriterion("plate_other =", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherNotEqualTo(String value) {
			addCriterion("plate_other <>", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherGreaterThan(String value) {
			addCriterion("plate_other >", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherGreaterThanOrEqualTo(String value) {
			addCriterion("plate_other >=", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherLessThan(String value) {
			addCriterion("plate_other <", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherLessThanOrEqualTo(String value) {
			addCriterion("plate_other <=", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherLike(String value) {
			addCriterion("plate_other like", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherNotLike(String value) {
			addCriterion("plate_other not like", value, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherIn(List<String> values) {
			addCriterion("plate_other in", values, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherNotIn(List<String> values) {
			addCriterion("plate_other not in", values, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherBetween(String value1, String value2) {
			addCriterion("plate_other between", value1, value2, "plateOther");
			return (Criteria) this;
		}

		public Criteria andPlateOtherNotBetween(String value1, String value2) {
			addCriterion("plate_other not between", value1, value2,
					"plateOther");
			return (Criteria) this;
		}

		public Criteria andPicPathIsNull() {
			addCriterion("pic_path is null");
			return (Criteria) this;
		}

		public Criteria andPicPathIsNotNull() {
			addCriterion("pic_path is not null");
			return (Criteria) this;
		}

		public Criteria andPicPathEqualTo(String value) {
			addCriterion("pic_path =", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathNotEqualTo(String value) {
			addCriterion("pic_path <>", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathGreaterThan(String value) {
			addCriterion("pic_path >", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathGreaterThanOrEqualTo(String value) {
			addCriterion("pic_path >=", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathLessThan(String value) {
			addCriterion("pic_path <", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathLessThanOrEqualTo(String value) {
			addCriterion("pic_path <=", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathLike(String value) {
			addCriterion("pic_path like", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathNotLike(String value) {
			addCriterion("pic_path not like", value, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathIn(List<String> values) {
			addCriterion("pic_path in", values, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathNotIn(List<String> values) {
			addCriterion("pic_path not in", values, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathBetween(String value1, String value2) {
			addCriterion("pic_path between", value1, value2, "picPath");
			return (Criteria) this;
		}

		public Criteria andPicPathNotBetween(String value1, String value2) {
			addCriterion("pic_path not between", value1, value2, "picPath");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
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
     * This class corresponds to the database table news_my_plate
     *
     * @mbggenerated do_not_delete_during_merge Wed Feb 24 10:44:44 GMT+08:00 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}