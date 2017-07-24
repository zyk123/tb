package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class Browsermobileosreport implements Serializable {
    private String bmoid;

    private String datatype;

    private Date datadate;

    private String nametype;

    private String usename;

    private Long usenum;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getBmoid() {
        return bmoid;
    }

    public void setBmoid(String bmoid) {
        this.bmoid = bmoid == null ? null : bmoid.trim();
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

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype == null ? null : nametype.trim();
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename == null ? null : usename.trim();
    }

    public Long getUsenum() {
        return usenum;
    }

    public void setUsenum(Long usenum) {
        this.usenum = usenum;
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