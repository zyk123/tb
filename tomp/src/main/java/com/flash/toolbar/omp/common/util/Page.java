package com.flash.toolbar.omp.common.util;

import java.io.Serializable;


public class Page implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//每页显示的记录数，默认显示10条
	private int[] countPerPage = new int[]{10,20,50};

	//总记录数
	private int totalCount;
	
	//每页显示记录数
	private int showCount = countPerPage[0];
	
	//总页数
	private int totalPage;
	
	//当前页
	private int currentPage = 1;
	
	//起始记录数
	private int startCount;
	
	//截止记录数
	private int endCount;
	
	//显示几个页面
	private int showPage = 5;

	public int getTotalCount() {
		return totalCount;
	}	

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getTotalPage() {
		int mod = totalCount%showCount;
		if(0 == mod){
			totalPage = totalCount/showCount;
		}else {
			totalPage = totalCount/showCount + 1;
		}
		return totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartCount() {
		if(1 >= currentPage){
			startCount = 1;
		}else{
			startCount = (currentPage - 1) * showCount + 1;
		}
		return startCount;
	}

	public int getEndCount() {
		if(1 >= currentPage){
			endCount = showCount;
		}else{
			endCount = currentPage * showCount;
		}
		return endCount;
	}

	public int[] getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int[] countPerPage) {
		this.countPerPage = countPerPage;
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}
}
