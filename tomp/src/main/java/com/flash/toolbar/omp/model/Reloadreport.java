package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Reloadreport implements Serializable {
    private String rrid;

    private String datatype;

    private Date datadate;

    private Integer totnum;

    private Long consnum;

    private BigDecimal paynum;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid == null ? null : rrid.trim();
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

    public Integer getTotnum() {
        return totnum;
    }

    public void setTotnum(Integer totnum) {
        this.totnum = totnum;
    }

    public Long getConsnum() {
        return consnum;
    }

    public void setConsnum(Long consnum) {
        this.consnum = consnum;
    }

    public BigDecimal getPaynum() {
        return paynum;
    }

    public void setPaynum(BigDecimal paynum) {
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