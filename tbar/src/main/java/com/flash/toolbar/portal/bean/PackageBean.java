package com.flash.toolbar.portal.bean;

/**
 * 套餐信息
 * @author zhulin
 *
 */
public class PackageBean {
	//套餐id
	private String packageid;
	
	//套餐编号
	private String fgno;

	//套餐名称
	private String fgname;
	
	//套餐tips
	private String fgtips;
	
	//套餐类型名称
	private String ptname;
	
	//套餐描述
	private String fgdesc;
	
	//金额
	private String amount;
	  
	//货币
	private String currency;
	
	//资费
	private String expenses;
	
	

	
	public String getPackageid() {
		return packageid;
	}

	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}

	public String getFgno() {
		return fgno;
	}

	public void setFgno(String fgno) {
		this.fgno = fgno;
	}

	public String getFgname() {
		return fgname;
	}

	public void setFgname(String fgname) {
		this.fgname = fgname;
	}

	public String getPtname() {
		return ptname;
	}

	public void setPtname(String ptname) {
		this.ptname = ptname;
	}


	public String getFgdesc() {
		return fgdesc;
	}

	public void setFgdesc(String fgdesc) {
		this.fgdesc = fgdesc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExpenses() {
		return expenses;
	}

	public void setExpenses(String expenses) {
		this.expenses = expenses;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFgtips() {
		return fgtips;
	}

	public void setFgtips(String fgtips) {
		this.fgtips = fgtips;
	}  
	
	 
}
