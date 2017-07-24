package com.flash.toolbar.omp.promotionment.bo;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HdPromotionPrize;

/**
 * 包含实体bean和业务需要字段等
 * @author ocean
 *
 */
public class PromotionPrizeModel extends BaseModel<HdPromotionPrize>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prizename;
	
	private String ptotalnum;
	
	private String prestnum;
	
	private String promotionid;

	public String getPromotionid() {
		return promotionid;
	}

	public void setPromotionid(String promotionid) {
		this.promotionid = promotionid;
	}

	public String getPrizename() {
		return prizename;
	}

	public void setPrizename(String prizename) {
		this.prizename = prizename;
	}

	public String getPtotalnum() {
		return ptotalnum;
	}

	public void setPtotalnum(String ptotalnum) {
		this.ptotalnum = ptotalnum;
	}

	public String getPrestnum() {
		return prestnum;
	}

	public void setPrestnum(String prestnum) {
		this.prestnum = prestnum;
	}

	public PromotionPrizeModel(){
		bean =  new HdPromotionPrize();
	}
}
