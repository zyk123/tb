package com.flash.toolbar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HdPrizeInfo implements Serializable {
    private String prizeid;

    private String prizename;

    private String prizedesc;

    private BigDecimal prizeprice;

    private Integer prizetotalnum;

    private Integer prizerestnum;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;

    private String prizeunit;

    private String prizetype;

    private static final long serialVersionUID = 1L;

    public String getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(String prizeid) {
        this.prizeid = prizeid == null ? null : prizeid.trim();
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename == null ? null : prizename.trim();
    }

    public String getPrizedesc() {
        return prizedesc;
    }

    public void setPrizedesc(String prizedesc) {
        this.prizedesc = prizedesc == null ? null : prizedesc.trim();
    }

    public BigDecimal getPrizeprice() {
        return prizeprice;
    }

    public void setPrizeprice(BigDecimal prizeprice) {
        this.prizeprice = prizeprice;
    }

    public Integer getPrizetotalnum() {
        return prizetotalnum;
    }

    public void setPrizetotalnum(Integer prizetotalnum) {
        this.prizetotalnum = prizetotalnum;
    }

    public Integer getPrizerestnum() {
        return prizerestnum;
    }

    public void setPrizerestnum(Integer prizerestnum) {
        this.prizerestnum = prizerestnum;
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

    public String getPrizeunit() {
        return prizeunit;
    }

    public void setPrizeunit(String prizeunit) {
        this.prizeunit = prizeunit == null ? null : prizeunit.trim();
    }

    public String getPrizetype() {
        return prizetype;
    }

    public void setPrizetype(String prizetype) {
        this.prizetype = prizetype == null ? null : prizetype.trim();
    }
}