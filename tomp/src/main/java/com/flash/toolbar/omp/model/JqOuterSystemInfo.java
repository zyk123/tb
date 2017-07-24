package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class JqOuterSystemInfo implements Serializable {
    private String outerid;

    private String outername;

    private String remark;

    private String modifyman;

    private Date modifydate;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

    public String getOuterid() {
        return outerid;
    }

    public void setOuterid(String outerid) {
        this.outerid = outerid == null ? null : outerid.trim();
    }

    public String getOutername() {
        return outername;
    }

    public void setOutername(String outername) {
        this.outername = outername == null ? null : outername.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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