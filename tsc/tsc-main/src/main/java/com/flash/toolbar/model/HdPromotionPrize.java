package com.flash.toolbar.model;

import java.io.Serializable;
import java.util.Date;

public class HdPromotionPrize implements Serializable {
    private String promotionprizeid;

    private String promotionid;

    private String prizeid;

    private String prizelevel;
    
    private Integer scoreStart;
    
    private Integer scoreEnd;

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

    private static final long serialVersionUID = 1L;

    public String getPromotionprizeid() {
        return promotionprizeid;
    }

    public void setPromotionprizeid(String promotionprizeid) {
        this.promotionprizeid = promotionprizeid == null ? null : promotionprizeid.trim();
    }

    public String getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid == null ? null : promotionid.trim();
    }

    public String getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(String prizeid) {
        this.prizeid = prizeid == null ? null : prizeid.trim();
    }

    public String getPrizelevel() {
        return prizelevel;
    }

    public void setPrizelevel(String prizelevel) {
        this.prizelevel = prizelevel == null ? null : prizelevel.trim();
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
        this.iconurl = iconurl == null ? null : iconurl.trim();
    }

    public String getBackgroundurl() {
        return backgroundurl;
    }

    public void setBackgroundurl(String backgroundurl) {
        this.backgroundurl = backgroundurl == null ? null : backgroundurl.trim();
    }

    public String getCountryno() {
        return countryno;
    }

    public void setCountryno(String countryno) {
        this.countryno = countryno == null ? null : countryno.trim();
    }

    public String getToperatorid() {
        return toperatorid;
    }

    public void setToperatorid(String toperatorid) {
        this.toperatorid = toperatorid == null ? null : toperatorid.trim();
    }

    public String getModifyman() {
        return modifyman;
    }

    public void setModifyman(String modifyman) {
        this.modifyman = modifyman == null ? null : modifyman.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

	public Integer getScoreStart() {
		return scoreStart;
	}

	public void setScoreStart(Integer scoreStart) {
		this.scoreStart = scoreStart;
	}

	public Integer getScoreEnd() {
		return scoreEnd;
	}

	public void setScoreEnd(Integer scoreEnd) {
		this.scoreEnd = scoreEnd;
	}
    
    
}