package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyBlackImportBatch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 导入批次主键ID
	 */
	private String batchId;
	/**
	 * 批次编号（自增）
	 */
	private String batchNo;	
	/**
	 * 导入日期
	 */
	private Date importDate;	
	/**
	 * 导入数量
	 */
	private Integer importNum;	
	/**
	 * 国家编码
	 */
	private String countryNo;	
	/**
	 * 运营商id
	 */
	private String tOperatorId;	
	/**
	 * 运营商编号
	 */
	private String tOperatorNo;	
	/**
	 * 运营商名称
	 */
	private String tOperatorName;	
	/**
	 * 修改人
	 */
	private String modifyMan;	
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	/**
	 * 扩展seqnum
	 * @return
	 */
	private String seqnum;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public String getCountryNo() {
		return countryNo;
	}
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	public String gettOperatorId() {
		return tOperatorId;
	}
	public void settOperatorId(String tOperatorId) {
		this.tOperatorId = tOperatorId;
	}
	public String gettOperatorNo() {
		return tOperatorNo;
	}
	public void settOperatorNo(String tOperatorNo) {
		this.tOperatorNo = tOperatorNo;
	}
	public String gettOperatorName() {
		return tOperatorName;
	}
	public void settOperatorName(String tOperatorName) {
		this.tOperatorName = tOperatorName;
	}
	public String getModifyMan() {
		return modifyMan;
	}
	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
	
}
