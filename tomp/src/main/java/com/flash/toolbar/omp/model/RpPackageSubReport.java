package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class RpPackageSubReport implements Serializable {
    private String psid;

    private String datatype;

    private Date datadate;

    private String fgno;

    private String fgname;

    private String totnum;

    private String consnum;

    private String paynum;

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

    public String getTotnum() {
		return totnum;
	}

	public void setTotnum(String totnum) {
		this.totnum = totnum;
	}

	public String getConsnum() {
		return consnum;
	}

	public void setConsnum(String consnum) {
		this.consnum = consnum;
	}

	public String getPaynum() {
		return paynum;
	}

	public void setPaynum(String paynum) {
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