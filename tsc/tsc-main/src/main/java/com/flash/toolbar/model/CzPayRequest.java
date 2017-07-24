package com.flash.toolbar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CzPayRequest implements Serializable {
    private String id;

    private String storeid;

    private String orderid;

    private String transdate;

    private String signature;

    private BigDecimal totalamount;

    private BigDecimal reloadamount;

    private BigDecimal taxamount;

    private String responseurl;

    private String reconfilename;

    private String msisdn;

    private BigDecimal notoken;

    private String tokenn;

    private String isnotification;

    private BigDecimal notifymethod;

    private String notifyrecipient;

    private String prepayment;

    private BigDecimal paymentmethod;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid == null ? null : storeid.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate == null ? null : transdate.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getReloadamount() {
        return reloadamount;
    }

    public void setReloadamount(BigDecimal reloadamount) {
        this.reloadamount = reloadamount;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public String getResponseurl() {
        return responseurl;
    }

    public void setResponseurl(String responseurl) {
        this.responseurl = responseurl == null ? null : responseurl.trim();
    }

    public String getReconfilename() {
        return reconfilename;
    }

    public void setReconfilename(String reconfilename) {
        this.reconfilename = reconfilename == null ? null : reconfilename.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public BigDecimal getNotoken() {
        return notoken;
    }

    public void setNotoken(BigDecimal notoken) {
        this.notoken = notoken;
    }

    public String getTokenn() {
        return tokenn;
    }

    public void setTokenn(String tokenn) {
        this.tokenn = tokenn == null ? null : tokenn.trim();
    }

    public String getIsnotification() {
        return isnotification;
    }

    public void setIsnotification(String isnotification) {
        this.isnotification = isnotification == null ? null : isnotification.trim();
    }

    public BigDecimal getNotifymethod() {
        return notifymethod;
    }

    public void setNotifymethod(BigDecimal notifymethod) {
        this.notifymethod = notifymethod;
    }

    public String getNotifyrecipient() {
        return notifyrecipient;
    }

    public void setNotifyrecipient(String notifyrecipient) {
        this.notifyrecipient = notifyrecipient == null ? null : notifyrecipient.trim();
    }

    public String getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment == null ? null : prepayment.trim();
    }

    public BigDecimal getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(BigDecimal paymentmethod) {
        this.paymentmethod = paymentmethod;
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