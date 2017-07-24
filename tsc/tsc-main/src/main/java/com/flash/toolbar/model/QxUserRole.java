package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class QxUserRole implements Serializable {
    private String userroleid;

    private String userid;

    private String roleid;

    private Date createdate;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getUserroleid() {
        return userroleid;
    }

    public void setUserroleid(String userroleid) {
        this.userroleid = userroleid == null ? null : userroleid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCountryno() {
        return countryno;
    }

    public void setCountryno(String countryno) {
        this.countryno = countryno == null ? null : countryno.trim();
    }

    public String getToperatorid() {
        return toperatorid;
    }

    public void setToperatorid(String toperatorid) {
        this.toperatorid = toperatorid == null ? null : toperatorid.trim();
    }
}