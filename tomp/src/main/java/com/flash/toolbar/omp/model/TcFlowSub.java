package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TcFlowSub implements Serializable {
    private String flowsubid;

    private String ordernumber;

    private String memberid;

    private String mobileno;

    private String fgid;

    private String fgname;

    private BigDecimal amount;

    private String currency;

    private String countryno;

    private String toperatorid;

    private String operatororderno;

    private String status;

    private Date subdate;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getFlowsubid() {
        return flowsubid;
    }

    public void setFlowsubid(String flowsubid) {
        this.flowsubid = flowsubid == null ? null : flowsubid.trim();
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber == null ? null : ordernumber.trim();
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

    public String getFgid() {
        return fgid;
    }

    public void setFgid(String fgid) {
        this.fgid = fgid == null ? null : fgid.trim();
    }

    public String getFgname() {
        return fgname;
    }

    public void setFgname(String fgname) {
        this.fgname = fgname == null ? null : fgname.trim();
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

    public String getOperatororderno() {
        return operatororderno;
    }

    public void setOperatororderno(String operatororderno) {
        this.operatororderno = operatororderno == null ? null : operatororderno.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getSubdate() {
        return subdate;
    }

    public void setSubdate(Date subdate) {
        this.subdate = subdate;
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