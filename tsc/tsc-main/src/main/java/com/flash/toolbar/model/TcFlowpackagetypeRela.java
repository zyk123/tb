package com.flash.toolbar.model;

import java.io.Serializable;

public class TcFlowpackagetypeRela implements Serializable {
    private String relaid;

    private String packagetypeid;

    private String packageid;

    private static final long serialVersionUID = 1L;

    public String getRelaid() {
        return relaid;
    }

    public void setRelaid(String relaid) {
        this.relaid = relaid == null ? null : relaid.trim();
    }

    public String getPackagetypeid() {
        return packagetypeid;
    }

    public void setPackagetypeid(String packagetypeid) {
        this.packagetypeid = packagetypeid == null ? null : packagetypeid.trim();
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid == null ? null : packageid.trim();
    }
}