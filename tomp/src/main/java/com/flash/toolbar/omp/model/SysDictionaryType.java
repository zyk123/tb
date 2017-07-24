package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class SysDictionaryType implements Serializable {
    private String dictionarytypeid;

    private String dicttype;

    private String dicttypename;

    private String dicttypedesc;

    private String delflag;

    private String modifyman;

    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public String getDictionarytypeid() {
        return dictionarytypeid;
    }

    public void setDictionarytypeid(String dictionarytypeid) {
        this.dictionarytypeid = dictionarytypeid == null ? null : dictionarytypeid.trim();
    }

    public String getDicttype() {
        return dicttype;
    }

    public void setDicttype(String dicttype) {
        this.dicttype = dicttype == null ? null : dicttype.trim();
    }

    public String getDicttypename() {
        return dicttypename;
    }

    public void setDicttypename(String dicttypename) {
        this.dicttypename = dicttypename == null ? null : dicttypename.trim();
    }

    public String getDicttypedesc() {
        return dicttypedesc;
    }

    public void setDicttypedesc(String dicttypedesc) {
        this.dicttypedesc = dicttypedesc == null ? null : dicttypedesc.trim();
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