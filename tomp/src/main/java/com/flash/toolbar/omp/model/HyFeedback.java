package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyFeedback implements Serializable {
    private String feedbackid;

    private String memberid;

    private String mobileno;

    private String feedback;

    private Date feeddate;

    private String countryno;

    private String toperatorid;
    
    private String name;
    
    private String state;
    
    private String email;

    private static final long serialVersionUID = 1L;

    public String getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid == null ? null : feedbackid.trim();
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public Date getFeeddate() {
        return feeddate;
    }

    public void setFeeddate(Date feeddate) {
        this.feeddate = feeddate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}