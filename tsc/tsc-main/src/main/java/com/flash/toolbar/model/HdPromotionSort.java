package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HdPromotionSort implements Serializable {
    private String sortid;

    private String memberid;

    private String mobileno;

    private String promotionid;

    private Integer totalnum;

    private Integer topscore;

    private Date topscoredate;

    private Integer lastscore;

    private Date lastscoredate;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid == null ? null : sortid.trim();
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

    public String getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid == null ? null : promotionid.trim();
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getTopscore() {
        return topscore;
    }

    public void setTopscore(Integer topscore) {
        this.topscore = topscore;
    }

    public Date getTopscoredate() {
        return topscoredate;
    }

    public void setTopscoredate(Date topscoredate) {
        this.topscoredate = topscoredate;
    }

    public Integer getLastscore() {
        return lastscore;
    }

    public void setLastscore(Integer lastscore) {
        this.lastscore = lastscore;
    }

    public Date getLastscoredate() {
        return lastscoredate;
    }

    public void setLastscoredate(Date lastscoredate) {
        this.lastscoredate = lastscoredate;
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