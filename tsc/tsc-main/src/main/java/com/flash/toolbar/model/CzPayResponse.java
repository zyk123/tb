package com.flash.toolbar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CzPayResponse implements Serializable {
    private String id;

    private String storeid;

    private String orderid;

    private String transdate;

    private BigDecimal totalamount;

    private String msisdn;

    private BigDecimal balance;

    private String suspenddate;

    private String returncode;

    private BigDecimal reasoncode;

    private String reasondesc;

    private String referid;

    private String signature;

    private BigDecimal paymentmethod;

    private String cardpaddednum;

    private String authcode;

    private String token;

    private String payer;

    private String transactionid;

    private String accountnum;

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

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSuspenddate() {
        return suspenddate;
    }

    public void setSuspenddate(String suspenddate) {
        this.suspenddate = suspenddate == null ? null : suspenddate.trim();
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode == null ? null : returncode.trim();
    }

    public BigDecimal getReasoncode() {
        return reasoncode;
    }

    public void setReasoncode(BigDecimal reasoncode) {
        this.reasoncode = reasoncode;
    }

    public String getReasondesc() {
        return reasondesc;
    }

    public void setReasondesc(String reasondesc) {
        this.reasondesc = reasondesc == null ? null : reasondesc.trim();
    }

    public String getReferid() {
        return referid;
    }

    public void setReferid(String referid) {
        this.referid = referid == null ? null : referid.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public BigDecimal getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(BigDecimal paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getCardpaddednum() {
        return cardpaddednum;
    }

    public void setCardpaddednum(String cardpaddednum) {
        this.cardpaddednum = cardpaddednum == null ? null : cardpaddednum.trim();
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode == null ? null : authcode.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum == null ? null : accountnum.trim();
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