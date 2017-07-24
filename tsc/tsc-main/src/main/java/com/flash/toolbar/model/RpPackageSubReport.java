package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class RpPackageSubReport implements Serializable {
    private String psid;

    private String datatype;

    private Date datadate;

    private String fgno;

    private String fgname;

    private Long totnum;

    private Long consnum;

    private Long paynum;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid == null ? null : psid.trim();
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype == null ? null : datatype.trim();
    }

    public Date getDatadate() {
        return datadate;
    }

    public void setDatadate(Date datadate) {
        this.datadate = datadate;
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

    public Long getTotnum() {
        return totnum;
    }

    public void setTotnum(Long totnum) {
        this.totnum = totnum;
    }

    public Long getConsnum() {
        return consnum;
    }

    public void setConsnum(Long consnum) {
        this.consnum = consnum;
    }

    public Long getPaynum() {
        return paynum;
    }

    public void setPaynum(Long paynum) {
        this.paynum = paynum;
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