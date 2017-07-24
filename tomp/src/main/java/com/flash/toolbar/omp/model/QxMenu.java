package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QxMenu implements Serializable {
    private String menuid;

    private String menuno;

    private String menuname;

    private String menuindex;

    private String fid;

    private String menulevel;

    private Integer orderno;

    private Date createdate;

    private String countryno;

    private String toperatorid;

    private String delflag;

    private String modifyman;

    private Date modifydate;
    
    private List<QxPrivilege> privileges;

    private static final long serialVersionUID = 1L;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getMenuno() {
        return menuno;
    }

    public void setMenuno(String menuno) {
        this.menuno = menuno == null ? null : menuno.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getMenuindex() {
        return menuindex;
    }

    public void setMenuindex(String menuindex) {
        this.menuindex = menuindex == null ? null : menuindex.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }    

    public String getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}

	public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
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

	public List<QxPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<QxPrivilege> privileges) {
		this.privileges = privileges;
	}   	
}