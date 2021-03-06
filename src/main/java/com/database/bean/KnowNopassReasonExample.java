package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KnowNopassReasonExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected Integer limitEnd;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public KnowNopassReasonExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNopassReasonIdIsNull() {
            addCriterion("nopass_reason_id is null");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdIsNotNull() {
            addCriterion("nopass_reason_id is not null");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdEqualTo(Integer value) {
            addCriterion("nopass_reason_id =", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdNotEqualTo(Integer value) {
            addCriterion("nopass_reason_id <>", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdGreaterThan(Integer value) {
            addCriterion("nopass_reason_id >", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("nopass_reason_id >=", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdLessThan(Integer value) {
            addCriterion("nopass_reason_id <", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdLessThanOrEqualTo(Integer value) {
            addCriterion("nopass_reason_id <=", value, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdIn(List<Integer> values) {
            addCriterion("nopass_reason_id in", values, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdNotIn(List<Integer> values) {
            addCriterion("nopass_reason_id not in", values, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdBetween(Integer value1, Integer value2) {
            addCriterion("nopass_reason_id between", value1, value2, "nopassReasonId");
            return (Criteria) this;
        }

        public Criteria andNopassReasonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("nopass_reason_id not between", value1, value2, "nopassReasonId");
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

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andPassTimeIsNull() {
            addCriterion("pass_time is null");
            return (Criteria) this;
        }

        public Criteria andPassTimeIsNotNull() {
            addCriterion("pass_time is not null");
            return (Criteria) this;
        }

        public Criteria andPassTimeEqualTo(Date value) {
            addCriterion("pass_time =", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotEqualTo(Date value) {
            addCriterion("pass_time <>", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeGreaterThan(Date value) {
            addCriterion("pass_time >", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pass_time >=", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeLessThan(Date value) {
            addCriterion("pass_time <", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeLessThanOrEqualTo(Date value) {
            addCriterion("pass_time <=", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeIn(List<Date> values) {
            addCriterion("pass_time in", values, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotIn(List<Date> values) {
            addCriterion("pass_time not in", values, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeBetween(Date value1, Date value2) {
            addCriterion("pass_time between", value1, value2, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotBetween(Date value1, Date value2) {
            addCriterion("pass_time not between", value1, value2, "passTime");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNull() {
            addCriterion("article_type is null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNotNull() {
            addCriterion("article_type is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeEqualTo(String value) {
            addCriterion("article_type =", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotEqualTo(String value) {
            addCriterion("article_type <>", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThan(String value) {
            addCriterion("article_type >", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("article_type >=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThan(String value) {
            addCriterion("article_type <", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThanOrEqualTo(String value) {
            addCriterion("article_type <=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLike(String value) {
            addCriterion("article_type like", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotLike(String value) {
            addCriterion("article_type not like", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIn(List<String> values) {
            addCriterion("article_type in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotIn(List<String> values) {
            addCriterion("article_type not in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeBetween(String value1, String value2) {
            addCriterion("article_type between", value1, value2, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotBetween(String value1, String value2) {
            addCriterion("article_type not between", value1, value2, "articleType");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table know_nopass_reason
     *
     * @mbggenerated do_not_delete_during_merge Fri Jul 17 11:26:55 GMT+08:00 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table know_nopass_reason
     *
     * @mbggenerated Fri Jul 17 11:26:55 GMT+08:00 2015
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

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
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
}