package com.flash.toolbar.model;

import java.io.Serializable;

/**
 * 生成自动增长序列
 * @author ocean
 *
 */
@SuppressWarnings("serial")
public class IdGenerator implements Serializable {
    /**
     * 表名
     */
    public final static String TABLE_NAME = "idsequence";

    private String id;

    private String fieldname;

    private String maxid;

    private String remark;

    private String pattern;

    private String idtype;

    /**
     * get sequenceid
     * 
     * @return Returns the sequenceid.<br>
     */
    public String getId() {
        return id;
    }

    /**
     * set sequenceid
     * 
     * @param sequenceid The sequenceid to set. <br>
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get fieldname
     * 
     * @return Returns the fieldname.<br>
     */
    public String getFieldname() {
        return fieldname;
    }

    /**
     * set fieldname
     * 
     * @param fieldname The fieldname to set. <br>
     */
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    /**
     * get maxid
     * 
     * @return Returns the maxid.<br>
     */
    public String getMaxid() {
        return maxid;
    }

    /**
     * set maxid
     * 
     * @param maxid The maxid to set. <br>
     */
    public void setMaxid(String maxid) {
        this.maxid = maxid;
    }

    /**
     * get remark
     * 
     * @return Returns the remark.<br>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * set remark
     * 
     * @param remark The remark to set. <br>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * get pattern
     * 
     * @return Returns the pattern.<br>
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * set pattern
     * 
     * @param pattern The pattern to set. <br>
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * get idtype
     * 
     * @return Returns the idtype.<br>
     */
    public String getIdtype() {
        return idtype;
    }

    /**
     * set idtype
     * 
     * @param idtype The idtype to set. <br>
     */
    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

}

