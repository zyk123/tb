package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class QxPrivilege implements Serializable {
    private String privilegeid;

    private String privilegeno;
    
    private String privilegecode;

    private String privilegename;

    private Date createdate;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;
    
    private String menuid;

    private static final long serialVersionUID = 1L;

    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid == null ? null : privilegeid.trim();
    }

    public String getPrivilegeno() {
        return privilegeno;
    }

    public void setPrivilegeno(String privilegeno) {
        this.privilegeno = privilegeno == null ? null : privilegeno.trim();
    }
    
    public String getPrivilegecode() {
		return privilegecode;
	}

	public void setPrivilegecode(String privilegecode) {
		this.privilegecode = privilegecode;
	}

	public String getPrivilegename() {
        return privilegename;
    }

    public void setPrivilegename(String privilegename) {
        this.privilegename = privilegename == null ? null : privilegename.trim();
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

    public String getModifyman() {
        return modifyman;
    }

    public void setModifyman(String modifyman) {
        this.modifyman = modifyman == null ? null : modifyman.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
}