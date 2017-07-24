package com.flash.toolbar.portal.bean;

import java.io.Serializable;
import java.util.Date;

public class SysOperatorSeg implements Serializable {
    private String operatorsegid;

    private String startsegno;

    private String endsegno;

    private String countryno;

    private String toperatorid;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getOperatorsegid() {
        return operatorsegid;
    }

    public void setOperatorsegid(String operatorsegid) {
        this.operatorsegid = operatorsegid == null ? null : operatorsegid.trim();
    }

    public String getStartsegno() {
        return startsegno;
    }

    public void setStartsegno(String startsegno) {
        this.startsegno = startsegno == null ? null : startsegno.trim();
    }

    public String getEndsegno() {
        return endsegno;
    }

    public void setEndsegno(String endsegno) {
        this.endsegno = endsegno == null ? null : endsegno.trim();
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