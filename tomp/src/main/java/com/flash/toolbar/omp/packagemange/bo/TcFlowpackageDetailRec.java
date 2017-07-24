package com.flash.toolbar.omp.packagemange.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TcFlowpackageDetailRec implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String packagedetailid;

    private String packageid;
    
    private String packagename;

    private String detailname;

    private String itemname;

    private String itemvalue;

    private BigDecimal orderno;

    private String parentid;
    
    private List<TcFlowpackageDetailRec> childDetail;

	public String getPackagedetailid() {
		return packagedetailid;
	}

	public void setPackagedetailid(String packagedetailid) {
		this.packagedetailid = packagedetailid;
	}

	public String getPackageid() {
		return packageid;
	}

	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}
	
	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getDetailname() {
		return detailname;
	}

	public void setDetailname(String detailname) {
		this.detailname = detailname;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemvalue() {
		return itemvalue;
	}

	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}

	public BigDecimal getOrderno() {
		return orderno;
	}

	public void setOrderno(BigDecimal orderno) {
		this.orderno = orderno;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public List<TcFlowpackageDetailRec> getChildDetail() {
		return childDetail;
	}

	public void setChildDetail(List<TcFlowpackageDetailRec> childDetail) {
		this.childDetail = childDetail;
	}


}
