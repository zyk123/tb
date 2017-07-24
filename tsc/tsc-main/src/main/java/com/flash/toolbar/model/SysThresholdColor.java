package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class SysThresholdColor implements Serializable {
    private String thresholdcolorid;

    private Integer beginflowvalue;

    private Integer endflowvalue;

    private String colorvalue;

    private String countryno;

    private String toperatorid;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getThresholdcolorid() {
        return thresholdcolorid;
    }

    public void setThresholdcolorid(String thresholdcolorid) {
        this.thresholdcolorid = thresholdcolorid == null ? null : thresholdcolorid.trim();
    }

    public Integer getBeginflowvalue() {
        return beginflowvalue;
    }

    public void setBeginflowvalue(Integer beginflowvalue) {
        this.beginflowvalue = beginflowvalue;
    }

    public Integer getEndflowvalue() {
        return endflowvalue;
    }

    public void setEndflowvalue(Integer endflowvalue) {
        this.endflowvalue = endflowvalue;
    }

    public String getColorvalue() {
        return colorvalue;
    }

    public void setColorvalue(String colorvalue) {
        this.colorvalue = colorvalue == null ? null : colorvalue.trim();
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
}