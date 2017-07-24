package com.flash.toolbar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TcFlowPackage implements Serializable {
    private String packageid;

    private String fgno;

    private String fgname;

    private String fgdesc;

    private String countryno;

    private String toperatorid;

    private String langno;

    private String expenses;

    private BigDecimal amount;

    private String currency;

    private String packagetypeid;

    private Integer orderno;

    private String status;

    private String remark;

    private Date adddate;

    private String delflag;

    private String modifyman;

    private Date modifydate;
    
    private String fgtips;

    private static final long serialVersionUID = 1L;

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid == null ? null : packageid.trim();
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

    public String getFgdesc() {
        return fgdesc;
    }

    public void setFgdesc(String fgdesc) {
        this.fgdesc = fgdesc == null ? null : fgdesc.trim();
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

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses == null ? null : expenses.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getPackagetypeid() {
        return packagetypeid;
    }

    public void setPackagetypeid(String packagetypeid) {
        this.packagetypeid = packagetypeid == null ? null : packagetypeid.trim();
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
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

	public String getFgtips() {
		return fgtips;
	}

	public void setFgtips(String fgtips) {
		this.fgtips = fgtips;
	}
    
    
}