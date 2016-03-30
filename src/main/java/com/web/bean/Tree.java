package com.web.bean;


import java.util.List;

public class Tree {

    private String parentid;

    private String mid;

    private String mname;

    private List<Tree> child;

    /**
     * 是否拥有这个节点
     */
    private boolean has = false;

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public List<Tree> getChild() {
        return child;
    }

    public void setChild(List<Tree> child) {
        this.child = child;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

}
