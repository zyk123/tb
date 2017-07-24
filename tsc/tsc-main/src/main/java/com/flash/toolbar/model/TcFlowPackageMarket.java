package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class TcFlowPackageMarket implements Serializable {
    private String packagemarketid;

    private String fgno;

    private String fgname;

    private String fgremark;

    private String fgurl;

    private String countryno;

    private String toperatorid;

    private String langno;

    private Date begindate;

    private Date enddate;

    private String status;

    private String remark;

    private Date adddate;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getPackagemarketid() {
        return packagemarketid;
    }

    public void setPackagemarketid(String packagemarketid) {
        this.packagemarketid = packagemarketid == null ? null : packagemarketid.trim();
    }

    public String getFgno() {
        return fgno;
    }

    public void setFgno(String fgno) {
        this.fgno = fgno == null ? null : fgno.trim();
    }

    public String getFgname() {
        return fgname;
    }

    public void setFgname(String fgname) {
        this.fgname = fgname == null ? null : fgname.trim();
    }

    public String getFgremark() {
        return fgremark;
    }

    public void setFgremark(String fgremark) {
        this.fgremark = fgremark == null ? null : fgremark.trim();
    }

    public String getFgurl() {
        return fgurl;
    }

    public void setFgurl(String fgurl) {
        this.fgurl = fgurl == null ? null : fgurl.trim();
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

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}