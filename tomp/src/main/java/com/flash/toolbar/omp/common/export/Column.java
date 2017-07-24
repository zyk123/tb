package com.flash.toolbar.omp.common.export;

public class Column {
	private String title;

	private int width = 5;
	
	public Column(){
		super();
	}
	
	public Column(String title){
		 this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
