package com.flash.toolbar.omp.promotionment.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HdPrizeShip;

/**
 * 包含实体bean和业务需要字段等
 * @author ocean
 *
 */
public class PrizeDeliveryModel extends BaseModel<HdPrizeShip>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 扩展开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 扩展批次编号
	 */
	private String activityName;
	
	private String prizeName;
	
	private String prizeLevel;
	
	private String receiverName;
	
	private String receiverAddress;
	
	private String prizeDesc;
	
	private String iconUrl;
	
	private String prizeType;
	public PrizeDeliveryModel(){
		bean =  new HdPrizeShip();
	}
	

	public Date getStartTime() {
		return startTime;
	}



	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public Date getEndTime() {
		return endTime;
	}



	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getActivityName() {
		return activityName;
	}


	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public String getPrizeName() {
		return prizeName;
	}


	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}


	public String getPrizeLevel() {
		return prizeLevel;
	}


	public void setPrizeLevel(String prizeLevel) {
		this.prizeLevel = prizeLevel;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getReceiverAddress() {
		return receiverAddress;
	}


	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}


	public String getPrizeDesc() {
		return prizeDesc;
	}


	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}


	public String getIconUrl() {
		return iconUrl;
	}


	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}


	public String getPrizeType() {
		return prizeType;
	}


	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	
}
