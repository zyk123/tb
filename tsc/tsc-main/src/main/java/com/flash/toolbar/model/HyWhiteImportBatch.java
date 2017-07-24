package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HyWhiteImportBatch implements Serializable {
    private String batchid;

    private String batchno;

    private Date importdate;

    private Integer importnum;

    private String countryno;

    private String toperatorid;

    private String toperatorno;

    private String toperatorname;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid == null ? null : batchid.trim();
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public Date getImportdate() {
        return importdate;
    }

    public void setImportdate(Date importdate) {
        this.importdate = importdate;
    }

    public Integer getImportnum() {
        return importnum;
    }

    public void setImportnum(Integer importnum) {
        this.importnum = importnum;
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

    public String getToperatorno() {
        return toperatorno;
    }

    public void setToperatorno(String toperatorno) {
        this.toperatorno = toperatorno == null ? null : toperatorno.trim();
    }

    public String getToperatorname() {
        return toperatorname;
    }

    public void setToperatorname(String toperatorname) {
        this.toperatorname = toperatorname == null ? null : toperatorname.trim();
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