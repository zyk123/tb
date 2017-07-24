package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class SysLanguage implements Serializable {
    private String languageid;

    private String langname;

    private String langno;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getLanguageid() {
        return languageid;
    }

    public void setLanguageid(String languageid) {
        this.languageid = languageid == null ? null : languageid.trim();
    }

    public String getLangname() {
        return langname;
    }

    public void setLangname(String langname) {
        this.langname = langname == null ? null : langname.trim();
    }

    public String getLangno() {
        return langno;
    }

    public void setLangno(String langno) {
        this.langno = langno == null ? null : langno.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}