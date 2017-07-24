package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HyWhiteSection implements Serializable {
    private String wsectionid;

    private String batchid;

    private String mobilenostart;

    private String mobilenoend;

    private String countryno;

    private String toperatorid;

    private Date adddate;

    private static final long serialVersionUID = 1L;

    public String getWsectionid() {
        return wsectionid;
    }

    public void setWsectionid(String wsectionid) {
        this.wsectionid = wsectionid == null ? null : wsectionid.trim();
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid == null ? null : batchid.trim();
    }

    public String getMobilenostart() {
        return mobilenostart;
    }

    public void setMobilenostart(String mobilenostart) {
        this.mobilenostart = mobilenostart == null ? null : mobilenostart.trim();
    }

    public String getMobilenoend() {
        return mobilenoend;
    }

    public void setMobilenoend(String mobilenoend) {
        this.mobilenoend = mobilenoend == null ? null : mobilenoend.trim();
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