package com.flash.toolbar.omp.common.util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Edward
 * Date: 13-6-6
 * Time: 上午9:40
 * Description:
 */
@SuppressWarnings("serial")
public class Pagination implements Serializable {

    /**
     * 页面几个
     */
    int displayIndexCount = 5;
    /**
     * 总页数
     */
    int totalPageNum = 0;
    /**
     * 默认显示第一页
     */
    int currentPageNum = 1;
    /**
     * 默认显示20条记录
     */
    int rowsPeerPage = 10;
    /**
     * 总行数
     */
    int totalRowsCount;
    /**
     * 当前页起始记录
     */
    int pageOffset = 0;
    /**
     * 当前页终止记录
     */
    int pageTail = 0;
    
    int begin = 0;
    
    int end = 0;
    
    List<Integer> pageRowsList = new ArrayList<Integer>();

    public Pagination() {
        //  默认初始化的
        pageRowsList.add(10);
        pageRowsList.add(20);
        pageRowsList.add(50);
        //pageRowsList.add(100);
    }
    
    public void doPage() {
        this.totalPageNum = (this.rowsPeerPage - 1) / this.rowsPeerPage + 1;
        this.pageOffset = (this.currentPageNum - 1) * this.rowsPeerPage + 1;
        this.pageTail = this.pageOffset + this.rowsPeerPage - 1;
        if ((this.pageOffset + this.rowsPeerPage) > this.totalRowsCount) {
            this.pageTail = this.totalRowsCount;
        }
        
        if (totalRowsCount != 0 || rowsPeerPage != 0) {
        	totalPageNum =  (totalRowsCount + rowsPeerPage - 1) / rowsPeerPage;
        } 
        
        int range = (currentPageNum - 1) / 5;
        begin = range * displayIndexCount + 1;
        end = range * displayIndexCount + displayIndexCount;
        
    }    

    public int getDisplayIndexCount() {
        return displayIndexCount;
    }

    public void setDisplayIndexCount(int displayIndexCount) {
        this.displayIndexCount = displayIndexCount;
    }

    public List<Integer> getPageRowsList() {
        return pageRowsList;
    }

    public void setPageRowsList(List<Integer> pageRowsList) {
        this.pageRowsList = pageRowsList;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getRowsPeerPage() {
        return rowsPeerPage;
    }

    public void setRowsPeerPage(int rowsPeerPage) {
        this.rowsPeerPage = rowsPeerPage;
    }

    public int getTotalRowsCount() {
        return totalRowsCount;
    }

    public void setTotalRowsCount(int totalRowsCount) {
        this.totalRowsCount = totalRowsCount;
    }
    
    public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getPageTail() {
		return pageTail;
	}

	public void setPageTail(int pageTail) {
		this.pageTail = pageTail;
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public static void main(String[] args) {
		Pagination p = new Pagination();
		p.setCurrentPageNum(2);
		p.setRowsPeerPage(10);
		p.doPage();
	}
}