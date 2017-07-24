package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class SysRemindConfig implements Serializable {
    private String id;

    private String remindval;

    private String type;

    private String countryno;

    private String toperatorid;
    
    private String preposindicator;
    
    private String isopen;
    
    private String modifyman;
    
    private Date modifydate;
    
    private String brand;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRemindval() {
        return remindval;
    }

    public void setRemindval(String remindval) {
        this.remindval = remindval == null ? null : remindval.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

	public String getPreposindicator() {
		return preposindicator;
	}

	public void setPreposindicator(String preposindicator) {
		this.preposindicator = preposindicator;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getModifyman() {
		return modifyman;
	}

	public void setModifyman(String modifyman) {
		this.modifyman = modifyman;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
    
    
}