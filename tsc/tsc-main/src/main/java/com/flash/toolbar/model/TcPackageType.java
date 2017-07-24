package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class TcPackageType implements Serializable {
    private String packagetypeid;

    private String ptname;

    private Integer orderno;

    private String countryno;

    private String toperatorid;

    private Date adddate;
    
    private String brand;

    private static final long serialVersionUID = 1L;

    public String getPackagetypeid() {
        return packagetypeid;
    }

    public void setPackagetypeid(String packagetypeid) {
        this.packagetypeid = packagetypeid == null ? null : packagetypeid.trim();
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname == null ? null : ptname.trim();
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
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

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
    
    
}