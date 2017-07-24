package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class AuthorizedMenuPrivilege implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	private String menuid;
	
	/**
	 * 菜单名称
	 */
	private String menuname;
	
	/**
	 * 菜单路径
	 */
	private String menuindex;
	
	/**
	 * 菜单层级
	 */
	private String menulevel;
	
	/**
	 * 当前菜单权限编码
	 */
	private Set<String> privilegecodes;
	
	/**
	 * 当前菜单夏季菜单
	 */
	private List<AuthorizedMenuPrivilege> childmenuprivilege;

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuindex() {
		return menuindex;
	}

	public void setMenuindex(String menuindex) {
		this.menuindex = menuindex;
	}

	public String getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}
	public Set<String> getPrivilegecodes() {
		return privilegecodes;
	}

	public void setPrivilegecodes(Set<String> privilegecodes) {
		this.privilegecodes = privilegecodes;
	}

	public List<AuthorizedMenuPrivilege> getChildmenuprivilege() {
		return childmenuprivilege;
	}

	public void setChildmenuprivilege(
			List<AuthorizedMenuPrivilege> childmenuprivilege) {
		this.childmenuprivilege = childmenuprivilege;
	}

	
	
	
}
