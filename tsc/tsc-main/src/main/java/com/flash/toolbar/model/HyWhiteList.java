package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HyWhiteList implements Serializable {
    private String wlistid;

    private String batchid;

    private String mobileno;

    private String countryno;

    private String toperatorid;

    private Date adddate;

    private static final long serialVersionUID = 1L;

    public String getWlistid() {
        return wlistid;
    }

    public void setWlistid(String wlistid) {
        this.wlistid = wlistid == null ? null : wlistid.trim();
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid == null ? null : batchid.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
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
}