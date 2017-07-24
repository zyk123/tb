package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QxMenuPrivilege implements Serializable {
    private String menuprivilegeid;

    private String menuid;

    private String privilegeid;

    private Date createdate;

    private String countryno;

    private String toperatorid;
    
    private String roleid;

    private static final long serialVersionUID = 1L;

    public String getMenuprivilegeid() {
        return menuprivilegeid;
    }

    public void setMenuprivilegeid(String menuprivilegeid) {
        this.menuprivilegeid = menuprivilegeid == null ? null : menuprivilegeid.trim();
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid == null ? null : privilegeid.trim();
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
    
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
}