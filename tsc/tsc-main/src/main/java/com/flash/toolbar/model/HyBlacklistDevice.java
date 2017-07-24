package com.flash.toolbar.model;

import java.io.Serializable;

public class HyBlacklistDevice implements Serializable {
    private String id;

    private String name;
    
    private String countryno;
    
    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getCountryno() {
		return countryno;
	}

	public void setCountryno(String countryno) {
		this.countryno = countryno;
	}

	public String getToperatorid() {
		return toperatorid;
	}

	public void setToperatorid(String toperatorid) {
		this.toperatorid = toperatorid;
	}
    
    
}