package com.flash.toolbar.omp.packagesub.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.TcFlowSub;

/**
 * @author zhulin
 *
 */
public class TcFlowSubModel extends BaseModel<TcFlowSub>{


	private static final long serialVersionUID = 1L;

	/**
	 * 手机号码
	 * */
	private String mobileNo;
	
	/**
	 * 套餐名称
	 * */
	private String fGName;
	
	/**
	 * 订单状态
	 * */
	private String status;
	
	/**
	 * 运营商订单编号
	 * */
	private String operatorOrderNo;
	
	/**
	 * 开始时间
	 * */
	private Date startDate;
	
	/**
	 * 结束时间
	 * */
	private Date endDate;
	
	/**
	 * 运营商ID
	 * */
	private String tOperatorId;
	
	/**
	 * 国家编码
	 * */
	private String countryNo;
	

	public TcFlowSubModel() {
		super();
	}

	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getfGName() {
		return fGName;
	}

	public void setfGName(String fGName) {
		this.fGName = fGName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String gettOperatorId() {
		return tOperatorId;
	}

	public void settOperatorId(String tOperatorId) {
		this.tOperatorId = tOperatorId;
	}

	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getOperatorOrderNo() {
		return operatorOrderNo;
	}

	public void setOperatorOrderNo(String operatorOrderNo) {
		this.operatorOrderNo = operatorOrderNo;
	}
	
}
