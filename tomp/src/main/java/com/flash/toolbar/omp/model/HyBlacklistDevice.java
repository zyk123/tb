package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyBlacklistDevice implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String name;
    
    private String type;
    
    private Date createdate;
    
    private String countryno;
    
    private String toperatorid;    

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		this.countryno = countryno;
	}

	public String getToperatorid() {
		return toperatorid;
	}

	public void setToperatorid(String toperatorid) {
		this.toperatorid = toperatorid;
	}	
    
}