package com.flash.toolbar.omp.promotionment.bo;

import java.util.Date;

import com.flash.toolbar.omp.model.HdPrizeInfo;

public class PromotionRelation {
	    private String promotionprizeid;

	    private String promotionid;

	    private String prizeid;

	    private String prizelevel;

	    private Integer prizetotalnum;

	    private Integer prizerestnum;

	    private Integer probability;

	    private Integer orderno;

	    private String iconurl;

	    private String backgroundurl;

	    private String countryno;

	    private String toperatorid;

	    private String modifyman;

	    private Date modifydate;
	    
	    private HdPrizeInfo hdPrizeInfo;
	    
	    private Integer scorestart;
	    
	    private Integer scoreend;
	    

		public Integer getScorestart() {
			return scorestart;
		}

		public void setScorestart(Integer scorestart) {
			this.scorestart = scorestart;
		}

		public Integer getScoreend() {
			return scoreend;
		}

		public void setScoreend(Integer scoreend) {
			this.scoreend = scoreend;
		}

		public String getPromotionprizeid() {
			return promotionprizeid;
		}

		public void setPromotionprizeid(String promotionprizeid) {
			this.promotionprizeid = promotionprizeid;
		}

		public String getPromotionid() {
			return promotionid;
		}

		public void setPromotionid(String promotionid) {
			this.promotionid = promotionid;
		}

		public String getPrizeid() {
			return prizeid;
		}

		public void setPrizeid(String prizeid) {
			this.prizeid = prizeid;
		}

		public String getPrizelevel() {
			return prizelevel;
		}

		public void setPrizelevel(String prizelevel) {
			this.prizelevel = prizelevel;
		}

		public Integer getPrizetotalnum() {
			return prizetotalnum;
		}

		public void setPrizetotalnum(Integer prizetotalnum) {
			this.prizetotalnum = prizetotalnum;
		}

		public Integer getPrizerestnum() {
			return prizerestnum;
		}

		public void setPrizerestnum(Integer prizerestnum) {
			this.prizerestnum = prizerestnum;
		}

		public Integer getProbability() {
			return probability;
		}

		public void setProbability(Integer probability) {
			this.probability = probability;
		}

		public Integer getOrderno() {
			return orderno;
		}

		public void setOrderno(Integer orderno) {
			this.orderno = orderno;
		}

		public String getIconurl() {
			return iconurl;
		}

		public void setIconurl(String iconurl) {
			this.iconurl = iconurl;
		}

		public String getBackgroundurl() {
			return backgroundurl;
		}

		public void setBackgroundurl(String backgroundurl) {
			this.backgroundurl = backgroundurl;
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

		public HdPrizeInfo getHdPrizeInfo() {
			return hdPrizeInfo;
		}

		public void setHdPrizeInfo(HdPrizeInfo hdPrizeInfo) {
			this.hdPrizeInfo = hdPrizeInfo;
		}

}
