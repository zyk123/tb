package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class RzClickEventLog implements Serializable {
    private String accesslogid;

    private String mobileno;

    private Date accesstime;

    private String referurl;

    private String clickurl;

    private String useragent;

    private String sip;

    private String susercookieid;

    private String sdeviceno;

    private String sdevicetype;

    private String scity;

    private Integer staytime;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getAccesslogid() {
        return accesslogid;
    }

    public void setAccesslogid(String accesslogid) {
        this.accesslogid = accesslogid == null ? null : accesslogid.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public Date getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(Date accesstime) {
        this.accesstime = accesstime;
    }

    public String getReferurl() {
        return referurl;
    }

    public void setReferurl(String referurl) {
        this.referurl = referurl == null ? null : referurl.trim();
    }

    public String getClickurl() {
        return clickurl;
    }

    public void setClickurl(String clickurl) {
        this.clickurl = clickurl == null ? null : clickurl.trim();
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent == null ? null : useragent.trim();
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip == null ? null : sip.trim();
    }

    public String getSusercookieid() {
        return susercookieid;
    }

    public void setSusercookieid(String susercookieid) {
        this.susercookieid = susercookieid == null ? null : susercookieid.trim();
    }

    public String getSdeviceno() {
        return sdeviceno;
    }

    public void setSdeviceno(String sdeviceno) {
        this.sdeviceno = sdeviceno == null ? null : sdeviceno.trim();
    }

    public String getSdevicetype() {
        return sdevicetype;
    }

    public void setSdevicetype(String sdevicetype) {
        this.sdevicetype = sdevicetype == null ? null : sdevicetype.trim();
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity == null ? null : scity.trim();
    }

    public Integer getStaytime() {
        return staytime;
    }

    public void setStaytime(Integer staytime) {
        this.staytime = staytime;
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
}