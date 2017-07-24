package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class SysOperatorLang implements Serializable {
    private String operatorlangid;

    private String toperatorno;

    private String langno;

    private String langname;

    private Date adddate;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getOperatorlangid() {
        return operatorlangid;
    }

    public void setOperatorlangid(String operatorlangid) {
        this.operatorlangid = operatorlangid == null ? null : operatorlangid.trim();
    }

    public String getToperatorno() {
        return toperatorno;
    }

    public void setToperatorno(String toperatorno) {
        this.toperatorno = toperatorno == null ? null : toperatorno.trim();
    }

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public String getLangname() {
        return langname;
    }

    public void setLangname(String langname) {
        this.langname = langname == null ? null : langname.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
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