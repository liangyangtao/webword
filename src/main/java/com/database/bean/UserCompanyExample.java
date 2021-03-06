package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCompanyExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected Integer limitStart;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected Integer limitEnd;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public UserCompanyExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public Integer getLimitStart() {
		return limitStart;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
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

		public Criteria andUseridIsNull() {
			addCriterion("userid is null");
			return (Criteria) this;
		}

		public Criteria andUseridIsNotNull() {
			addCriterion("userid is not null");
			return (Criteria) this;
		}

		public Criteria andUseridEqualTo(Integer value) {
			addCriterion("userid =", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotEqualTo(Integer value) {
			addCriterion("userid <>", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThan(Integer value) {
			addCriterion("userid >", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
			addCriterion("userid >=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThan(Integer value) {
			addCriterion("userid <", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThanOrEqualTo(Integer value) {
			addCriterion("userid <=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridIn(List<Integer> values) {
			addCriterion("userid in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotIn(List<Integer> values) {
			addCriterion("userid not in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridBetween(Integer value1, Integer value2) {
			addCriterion("userid between", value1, value2, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotBetween(Integer value1, Integer value2) {
			addCriterion("userid not between", value1, value2, "userid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidIsNull() {
			addCriterion("companygroupid is null");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidIsNotNull() {
			addCriterion("companygroupid is not null");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidEqualTo(Integer value) {
			addCriterion("companygroupid =", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidNotEqualTo(Integer value) {
			addCriterion("companygroupid <>", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidGreaterThan(Integer value) {
			addCriterion("companygroupid >", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidGreaterThanOrEqualTo(Integer value) {
			addCriterion("companygroupid >=", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidLessThan(Integer value) {
			addCriterion("companygroupid <", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidLessThanOrEqualTo(Integer value) {
			addCriterion("companygroupid <=", value, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidIn(List<Integer> values) {
			addCriterion("companygroupid in", values, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidNotIn(List<Integer> values) {
			addCriterion("companygroupid not in", values, "companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidBetween(Integer value1, Integer value2) {
			addCriterion("companygroupid between", value1, value2,
					"companygroupid");
			return (Criteria) this;
		}

		public Criteria andCompanygroupidNotBetween(Integer value1,
				Integer value2) {
			addCriterion("companygroupid not between", value1, value2,
					"companygroupid");
			return (Criteria) this;
		}

		public Criteria andAdminidIsNull() {
			addCriterion("adminid is null");
			return (Criteria) this;
		}

		public Criteria andAdminidIsNotNull() {
			addCriterion("adminid is not null");
			return (Criteria) this;
		}

		public Criteria andAdminidEqualTo(Integer value) {
			addCriterion("adminid =", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidNotEqualTo(Integer value) {
			addCriterion("adminid <>", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidGreaterThan(Integer value) {
			addCriterion("adminid >", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidGreaterThanOrEqualTo(Integer value) {
			addCriterion("adminid >=", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidLessThan(Integer value) {
			addCriterion("adminid <", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidLessThanOrEqualTo(Integer value) {
			addCriterion("adminid <=", value, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidIn(List<Integer> values) {
			addCriterion("adminid in", values, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidNotIn(List<Integer> values) {
			addCriterion("adminid not in", values, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidBetween(Integer value1, Integer value2) {
			addCriterion("adminid between", value1, value2, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdminidNotBetween(Integer value1, Integer value2) {
			addCriterion("adminid not between", value1, value2, "adminid");
			return (Criteria) this;
		}

		public Criteria andAdmindateIsNull() {
			addCriterion("admindate is null");
			return (Criteria) this;
		}

		public Criteria andAdmindateIsNotNull() {
			addCriterion("admindate is not null");
			return (Criteria) this;
		}

		public Criteria andAdmindateEqualTo(Date value) {
			addCriterion("admindate =", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateNotEqualTo(Date value) {
			addCriterion("admindate <>", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateGreaterThan(Date value) {
			addCriterion("admindate >", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateGreaterThanOrEqualTo(Date value) {
			addCriterion("admindate >=", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateLessThan(Date value) {
			addCriterion("admindate <", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateLessThanOrEqualTo(Date value) {
			addCriterion("admindate <=", value, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateIn(List<Date> values) {
			addCriterion("admindate in", values, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateNotIn(List<Date> values) {
			addCriterion("admindate not in", values, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateBetween(Date value1, Date value2) {
			addCriterion("admindate between", value1, value2, "admindate");
			return (Criteria) this;
		}

		public Criteria andAdmindateNotBetween(Date value1, Date value2) {
			addCriterion("admindate not between", value1, value2, "admindate");
			return (Criteria) this;
		}

		public Criteria andStarttimeIsNull() {
			addCriterion("starttime is null");
			return (Criteria) this;
		}

		public Criteria andStarttimeIsNotNull() {
			addCriterion("starttime is not null");
			return (Criteria) this;
		}

		public Criteria andStarttimeEqualTo(Date value) {
			addCriterion("starttime =", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotEqualTo(Date value) {
			addCriterion("starttime <>", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeGreaterThan(Date value) {
			addCriterion("starttime >", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeGreaterThanOrEqualTo(Date value) {
			addCriterion("starttime >=", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeLessThan(Date value) {
			addCriterion("starttime <", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeLessThanOrEqualTo(Date value) {
			addCriterion("starttime <=", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeIn(List<Date> values) {
			addCriterion("starttime in", values, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotIn(List<Date> values) {
			addCriterion("starttime not in", values, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeBetween(Date value1, Date value2) {
			addCriterion("starttime between", value1, value2, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotBetween(Date value1, Date value2) {
			addCriterion("starttime not between", value1, value2, "starttime");
			return (Criteria) this;
		}

		public Criteria andEndtimeIsNull() {
			addCriterion("endtime is null");
			return (Criteria) this;
		}

		public Criteria andEndtimeIsNotNull() {
			addCriterion("endtime is not null");
			return (Criteria) this;
		}

		public Criteria andEndtimeEqualTo(Date value) {
			addCriterion("endtime =", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotEqualTo(Date value) {
			addCriterion("endtime <>", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeGreaterThan(Date value) {
			addCriterion("endtime >", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
			addCriterion("endtime >=", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeLessThan(Date value) {
			addCriterion("endtime <", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeLessThanOrEqualTo(Date value) {
			addCriterion("endtime <=", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeIn(List<Date> values) {
			addCriterion("endtime in", values, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotIn(List<Date> values) {
			addCriterion("endtime not in", values, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeBetween(Date value1, Date value2) {
			addCriterion("endtime between", value1, value2, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotBetween(Date value1, Date value2) {
			addCriterion("endtime not between", value1, value2, "endtime");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table user_company
	 * @mbggenerated  Mon Dec 14 09:45:31 CST 2015
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
     * This class corresponds to the database table user_company
     *
     * @mbggenerated do_not_delete_during_merge Tue Oct 27 16:13:09 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}