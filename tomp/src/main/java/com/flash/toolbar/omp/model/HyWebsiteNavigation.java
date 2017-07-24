package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyWebsiteNavigation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String  name;
	
	private byte[] icon;
	
	private String url;
	
	private String type;
	
	private int orderno;
	
	private String countryno;
	
	private String toperatorid;
	
	private String modifyman;
	
	private Date modifydate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOrderno() {
		return orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
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

	public String getModifyman() {
		return modifyman;
	}

	public void setModifyman(String modifyman) {
		this.modifyman = modifyman;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	
	
}
