package com.flash.toolbar.portal.bean;

import java.io.Serializable;
import java.util.Date;

public class HdPrizeShipUnion implements Serializable{
	private String shipid;

    private String memberid;

    private String mobileno;

    private String promotionid;

    private String prizeid;

    private String shipstatus;

    private Date winningdate;

    private Date shipdate;
    
    private String shipdatestr;

    private String shipnumber;

    private String receiverid;

    private String countryno;

    private String toperatorid;

    private String modifyman;

    private Date modifydate;
    
    private String prizename;

    private String prizedesc;
    
    private String prizetype;
    
    private String prizelevel;
    
    private String iconurl;
    
    private String promotionname;

    private String promotiondesc;

    private int rn;

    private static final long serialVersionUID = 1L;
    
    

    public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getPromotionname() {
		return promotionname;
	}

	public void setPromotionname(String promotionname) {
		this.promotionname = promotionname;
	}

	public String getPromotiondesc() {
		return promotiondesc;
	}

	public void setPromotiondesc(String promotiondesc) {
		this.promotiondesc = promotiondesc;
	}

	public String getPrizelevel() {
		return prizelevel;
	}

	public void setPrizelevel(String prizelevel) {
		this.prizelevel = prizelevel;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	public String getShipid() {
        return shipid;
    }

    public void setShipid(String shipid) {
        this.shipid = shipid == null ? null : shipid.trim();
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
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

    public String getShipstatus() {
        return shipstatus;
    }

    public void setShipstatus(String shipstatus) {
        this.shipstatus = shipstatus == null ? null : shipstatus.trim();
    }

    public Date getWinningdate() {
        return winningdate;
    }

    public void setWinningdate(Date winningdate) {
        this.winningdate = winningdate;
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public String getShipnumber() {
        return shipnumber;
    }

    public void setShipnumber(String shipnumber) {
        this.shipnumber = shipnumber == null ? null : shipnumber.trim();
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid == null ? null : receiverid.trim();
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
    

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename == null ? null : prizename.trim();
    }

    public String getPrizedesc() {
        return prizedesc;
    }

    public void setPrizedesc(String prizedesc) {
        this.prizedesc = prizedesc == null ? null : prizedesc.trim();
    }
    
    public String getPrizetype() {
        return prizetype;
    }

    public void setPrizetype(String prizetype) {
        this.prizetype = prizetype == null ? null : prizetype.trim();
    }

	public String getShipdatestr() {
		return shipdatestr;
	}

	public void setShipdatestr(String shipdatestr) {
		this.shipdatestr = shipdatestr;
	}
    
    
}
