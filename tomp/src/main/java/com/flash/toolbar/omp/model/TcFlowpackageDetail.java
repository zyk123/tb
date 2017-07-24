package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TcFlowpackageDetail implements Serializable {
    private String packagedetailid;

    private String packageid;

    private String detailname;

    private String itemname;

    private String itemvalue;

    private BigDecimal orderno;

    private String parentid;

    private String countryno;

    private String toperatorid;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getPackagedetailid() {
        return packagedetailid;
    }

    public void setPackagedetailid(String packagedetailid) {
        this.packagedetailid = packagedetailid == null ? null : packagedetailid.trim();
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid == null ? null : packageid.trim();
    }

    public String getDetailname() {
        return detailname;
    }

    public void setDetailname(String detailname) {
        this.detailname = detailname == null ? null : detailname.trim();
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    public String getItemvalue() {
        return itemvalue;
    }

    public void setItemvalue(String itemvalue) {
        this.itemvalue = itemvalue == null ? null : itemvalue.trim();
    }

    public BigDecimal getOrderno() {
        return orderno;
    }

    public void setOrderno(BigDecimal orderno) {
        this.orderno = orderno;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
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