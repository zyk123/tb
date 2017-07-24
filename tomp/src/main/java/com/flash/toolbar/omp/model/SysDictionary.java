package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class SysDictionary implements Serializable {
    private String dictionaryid;

    private String dicttype;

    private String dictname;

    private String dictkey;

    private String dictvalue;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getDictionaryid() {
        return dictionaryid;
    }

    public void setDictionaryid(String dictionaryid) {
        this.dictionaryid = dictionaryid == null ? null : dictionaryid.trim();
    }

    public String getDicttype() {
        return dicttype;
    }

    public void setDicttype(String dicttype) {
        this.dicttype = dicttype == null ? null : dicttype.trim();
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname == null ? null : dictname.trim();
    }

    public String getDictkey() {
        return dictkey;
    }

    public void setDictkey(String dictkey) {
        this.dictkey = dictkey == null ? null : dictkey.trim();
    }

    public String getDictvalue() {
        return dictvalue;
    }

    public void setDictvalue(String dictvalue) {
        this.dictvalue = dictvalue == null ? null : dictvalue.trim();
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