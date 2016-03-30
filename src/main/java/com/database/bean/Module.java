package com.database.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Module {
	
	
	private List<Module> child = new ArrayList<Module>();
	//权限
	private int right=0;
	//用户是否选择标志
	private int  has = 0;
	
	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public List<Module> getChild() {
		return child;
	}

	public void setChild(List<Module> child) {
		this.child = child;
	}
    
    public int getHas() {
		return has;
	}

	public void setHas(int has) {
		this.has = has;
	}






	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.mid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private Integer mid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.parentid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private Integer parentid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.mname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private String mname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.adminid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private Integer adminid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.createdate
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.sortnumber
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private Integer sortnumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column module.shortname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    private String shortname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.mid
     *
     * @return the value of module.mid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.mid
     *
     * @param mid the value for module.mid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.parentid
     *
     * @return the value of module.parentid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.parentid
     *
     * @param parentid the value for module.parentid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.mname
     *
     * @return the value of module.mname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public String getMname() {
        return mname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.mname
     *
     * @param mname the value for module.mname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setMname(String mname) {
        this.mname = mname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.adminid
     *
     * @return the value of module.adminid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public Integer getAdminid() {
        return adminid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.adminid
     *
     * @param adminid the value for module.adminid
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.createdate
     *
     * @return the value of module.createdate
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.createdate
     *
     * @param createdate the value for module.createdate
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.sortnumber
     *
     * @return the value of module.sortnumber
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public Integer getSortnumber() {
        return sortnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.sortnumber
     *
     * @param sortnumber the value for module.sortnumber
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setSortnumber(Integer sortnumber) {
        this.sortnumber = sortnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column module.shortname
     *
     * @return the value of module.shortname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column module.shortname
     *
     * @param shortname the value for module.shortname
     *
     * @mbggenerated Mon Aug 03 09:35:13 CST 2015
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}