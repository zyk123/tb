package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class SysTelecomOperatorAndHyMember implements Serializable {
	
	private String memberid;
	
    private String toperatorid;

    private String toperatorno;

    private String toperatorname;

    private String countryno;

    private String countryname;

    private String langno;

    private String langname;

    private String currency;

    private String timezone;

    private Date adddate;

    private String delflag;

    private String modifyman;

    private Date modifydate;
    
    private String mobileno;
    
    private String preposindicator;
    
    private String brand;
    
    private String firstshow;

    private static final long serialVersionUID = 1L;
    
    public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}


    public String getToperatorid() {
        return toperatorid;
    }

    public void setToperatorid(String toperatorid) {
        this.toperatorid = toperatorid == null ? null : toperatorid.trim();
    }

    public String getToperatorno() {
        return toperatorno;
    }

    public void setToperatorno(String toperatorno) {
        this.toperatorno = toperatorno == null ? null : toperatorno.trim();
    }

    public String getToperatorname() {
        return toperatorname;
    }

    public void setToperatorname(String toperatorname) {
        this.toperatorname = toperatorname == null ? null : toperatorname.trim();
    }

    public String getCountryno() {
        return countryno;
    }

    public void setCountryno(String countryno) {
        this.countryno = countryno == null ? null : countryno.trim();
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname == null ? null : countryname.trim();
    }

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public String getLangname() {
        return langname;
    }

    public void setLangname(String langname) {
        this.langname = langname == null ? null : langname.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone == null ? null : timezone.trim();
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

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
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