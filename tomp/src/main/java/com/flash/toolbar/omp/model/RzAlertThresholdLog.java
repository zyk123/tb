package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class RzAlertThresholdLog implements Serializable {
    private String alertthresholdlogid;

    private String memberid;

    private String mobileno;

    private Integer firstflowvalue;

    private Integer secondflowvalue;

    private String countryno;

    private String toperatorid;

    private Date setdate;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getAlertthresholdlogid() {
        return alertthresholdlogid;
    }

    public void setAlertthresholdlogid(String alertthresholdlogid) {
        this.alertthresholdlogid = alertthresholdlogid == null ? null : alertthresholdlogid.trim();
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

    public Integer getFirstflowvalue() {
        return firstflowvalue;
    }

    public void setFirstflowvalue(Integer firstflowvalue) {
        this.firstflowvalue = firstflowvalue;
    }

    public Integer getSecondflowvalue() {
        return secondflowvalue;
    }

    public void setSecondflowvalue(Integer secondflowvalue) {
        this.secondflowvalue = secondflowvalue;
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

    public Date getSetdate() {
        return setdate;
    }

    public void setSetdate(Date setdate) {
        this.setdate = setdate;
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