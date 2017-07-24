package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class QxRoleMenuPrivilege implements Serializable {
    private String rmpid;

    private String roleid;

    private String privilegeid;

    private String menuid;

    private Date createdate;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getRmpid() {
        return rmpid;
    }

    public void setRmpid(String rmpid) {
        this.rmpid = rmpid == null ? null : rmpid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid == null ? null : privilegeid.trim();
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
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