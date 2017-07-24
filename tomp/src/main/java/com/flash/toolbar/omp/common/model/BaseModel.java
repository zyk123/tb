package com.flash.toolbar.omp.common.model;

import java.io.Serializable;

import com.flash.toolbar.omp.common.util.Pagination;

public class BaseModel<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected T bean;
	
	private int pageIndex = 1;
	
	private int pageRowNum = 10;
	
	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

    /**
     * 分页导航
     */
    private Pagination pager = new Pagination();

    public Pagination getPager() {
    	pager.setCurrentPageNum(getPageIndex());
    	pager.setRowsPeerPage(getPageRowNum());
        return pager;
    }
    

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}


	public int getPageRowNum() {
		return pageRowNum;
	}

	public void setPageRowNum(int pageRowNum) {
		this.pageRowNum = pageRowNum;
	}

	public void setPager(Pagination pager) {
		this.pager = pager;
	}

}
