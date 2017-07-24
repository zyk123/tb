package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyFlowAlert implements Serializable {
    private String flowalertid;

    private String memberid;

    private String mobileno;

    private Date alertdate;

    private String countryno;

    private String toperatorid;

    private String delflag;

    private String modifyman;

    private Date modifydate;
    
    private String currentvalue;
    
    private String alertvalue;

    private static final long serialVersionUID = 1L;


	public String getCurrentvalue() {
		return currentvalue;
	}
	
	public void setCurrentvalue(String currentvalue) {
		this.currentvalue = currentvalue;
	}
	
	public String getAlertvalue() {
		return alertvalue;
	}
	
	public void setAlertvalue(String alertvalue) {
		this.alertvalue = alertvalue;
	}
	
    public String getFlowalertid() {
        return flowalertid;
    }

    public void setFlowalertid(String flowalertid) {
        this.flowalertid = flowalertid == null ? null : flowalertid.trim();
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public Date getAlertdate() {
        return alertdate;
    }

    public void setAlertdate(Date alertdate) {
        this.alertdate = alertdate;
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

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
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
}