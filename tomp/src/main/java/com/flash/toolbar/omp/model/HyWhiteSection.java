package com.flash.toolbar.omp.model;

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
    
    /**
     * 扩展字段
     */
    private String mobileNo;
    
    private String batchNo;
    
    private Date startTime;
    
    private Date endTime;

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}