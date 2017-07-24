package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class RpWindowStatReport implements Serializable {
    private String wsrid;

    private String datatype;

    private Date datadate;

    private String pageno;

    private String pagename;

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

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno == null ? null : pageno.trim();
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename == null ? null : pagename.trim();
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