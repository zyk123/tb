package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class RzOperatorlog implements Serializable {
    private String operatorlogid;

    private String userno;

    private String operatormenu;

    private String operatortype;

    private String operatorrestul;

    private String ip;

    private String remark;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getOperatorlogid() {
        return operatorlogid;
    }

    public void setOperatorlogid(String operatorlogid) {
        this.operatorlogid = operatorlogid == null ? null : operatorlogid.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getOperatormenu() {
        return operatormenu;
    }

    public void setOperatormenu(String operatormenu) {
        this.operatormenu = operatormenu == null ? null : operatormenu.trim();
    }

    public String getOperatortype() {
        return operatortype;
    }

    public void setOperatortype(String operatortype) {
        this.operatortype = operatortype == null ? null : operatortype.trim();
    }

    public String getOperatorrestul() {
        return operatorrestul;
    }

    public void setOperatorrestul(String operatorrestul) {
        this.operatorrestul = operatorrestul == null ? null : operatorrestul.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}