package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HdPromotionInfo implements Serializable {
    private String promotionid;

    private String promotionname;

    private String promotiondesc;

    private String prizedesc;

    private BigDecimal status;

    private Date startdate;

    private Date enddate;

    private BigDecimal onedaytimes;

    private BigDecimal countdown;

    private String bannerurl;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid == null ? null : promotionid.trim();
    }

    public String getPromotionname() {
        return promotionname;
    }

    public void setPromotionname(String promotionname) {
        this.promotionname = promotionname == null ? null : promotionname.trim();
    }

    public String getPromotiondesc() {
        return promotiondesc;
    }

    public void setPromotiondesc(String promotiondesc) {
        this.promotiondesc = promotiondesc == null ? null : promotiondesc.trim();
    }

    public String getPrizedesc() {
        return prizedesc;
    }

    public void setPrizedesc(String prizedesc) {
        this.prizedesc = prizedesc == null ? null : prizedesc.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public BigDecimal getOnedaytimes() {
        return onedaytimes;
    }

    public void setOnedaytimes(BigDecimal onedaytimes) {
        this.onedaytimes = onedaytimes;
    }

    public BigDecimal getCountdown() {
        return countdown;
    }

    public void setCountdown(BigDecimal countdown) {
        this.countdown = countdown;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl == null ? null : bannerurl.trim();
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