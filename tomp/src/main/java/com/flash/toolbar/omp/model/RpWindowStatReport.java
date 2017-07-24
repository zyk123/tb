package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class RpWindowStatReport implements Serializable {
    private String wsrid;

    private String datatype;

    private Date datadate;

    private String accessurl;

    private String totnum;

    private String  consnum;

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


    public String getAccessurl() {
		return accessurl;
	}

	public void setAccessurl(String accessurl) {
		this.accessurl = accessurl == null ? null : accessurl.trim();;
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