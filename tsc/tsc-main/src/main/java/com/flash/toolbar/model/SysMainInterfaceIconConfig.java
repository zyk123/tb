package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class SysMainInterfaceIconConfig implements Serializable {
    private String id;

    private byte[] icon;
    
    private String linkurl;
    
    private String sign;
    
    private String type;
    
    private String percent;
    
    private String mark;

    private byte[] popup;

    private Date createdate;

    private String countryno;

    private String toperatorid;

    private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public byte[] getPopup() {
		return popup;
	}

	public void setPopup(byte[] popup) {
		this.popup = popup;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCountryno() {
		return countryno;
	}

	public void setCountryno(String countryno) {
		this.countryno = countryno;
	}

	public String getToperatorid() {
		return toperatorid;
	}

	public void setToperatorid(String toperatorid) {
		this.toperatorid = toperatorid;
	}    
}