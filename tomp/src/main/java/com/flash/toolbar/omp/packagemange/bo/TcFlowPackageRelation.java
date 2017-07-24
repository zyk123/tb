package com.flash.toolbar.omp.packagemange.bo;

import java.io.Serializable;

public class TcFlowPackageRelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String packageid;
	
	private String fgname;
	
	private String fgno;
	
	private String packagetypeid;

	public String getPackageid() {
		return packageid;
	}

	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}

	public String getFgname() {
		return fgname;
	}

	public void setFgname(String fgname) {
		this.fgname = fgname;
	}

	public String getFgno() {
		return fgno;
	}

	public void setFgno(String fgno) {
		this.fgno = fgno;
	}

	public String getPackagetypeid() {
		return packagetypeid;
	}

	public void setPackagetypeid(String packagetypeid) {
		this.packagetypeid = packagetypeid!=null?packagetypeid:"";
	}
	
	
}
