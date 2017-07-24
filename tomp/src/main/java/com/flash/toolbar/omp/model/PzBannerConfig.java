package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PzBannerConfig implements Serializable {
    private String id;

    private BigDecimal orderno;

    private String type;

    private BigDecimal pagetype;

    private String turnurl;

    private String countryno;

    private String toperatorid;

    private byte[] icon;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getOrderno() {
        return orderno;
    }

    public void setOrderno(BigDecimal orderno) {
        this.orderno = orderno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getPagetype() {
        return pagetype;
    }

    public void setPagetype(BigDecimal pagetype) {
        this.pagetype = pagetype;
    }

    public String getTurnurl() {
        return turnurl;
    }

    public void setTurnurl(String turnurl) {
        this.turnurl = turnurl == null ? null : turnurl.trim();
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

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}