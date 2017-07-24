package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HyMember implements Serializable {
    private String memberid;

    private String memberno;

    private String mobileno;

    private String toperatorid;

    private String countryno;

    private String langno;

    private Date adddate;

    private String delflag;

    private String modifyman;

    private Date modifydate;
    
    private String preposindicator;
    
    private String brand;
    
    private String firstshow;

    private static final long serialVersionUID = 1L;

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getMemberno() {
        return memberno;
    }

    public void setMemberno(String memberno) {
        this.memberno = memberno == null ? null : memberno.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getToperatorid() {
        return toperatorid;
    }

    public void setToperatorid(String toperatorid) {
        this.toperatorid = toperatorid == null ? null : toperatorid.trim();
    }

    public String getCountryno() {
        return countryno;
    }

    public void setCountryno(String countryno) {
        this.countryno = countryno == null ? null : countryno.trim();
    }

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
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

	public String getPreposindicator() {
		return preposindicator;
	}

	public void setPreposindicator(String preposindicator) {
		this.preposindicator = preposindicator;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFirstshow() {
		return firstshow;
	}

	public void setFirstshow(String firstshow) {
		this.firstshow = firstshow;
	}
    
    
}