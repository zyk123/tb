package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class RpClickEventReport implements Serializable {
    private String wsrid;

    private String datatype;

    private Date datadate;

    private String clickurl;

    private Long totnum;

    private Long consnum;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getWsrid() {
        return wsrid;
    }

    public void setWsrid(String wsrid) {
        this.wsrid = wsrid == null ? null : wsrid.trim();
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

    public String getClickurl() {
        return clickurl;
    }

    public void setClickurl(String clickurl) {
        this.clickurl = clickurl == null ? null : clickurl.trim();
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