package com.flash.toolbar.omp.reportstatistic.bo;

public class PieModel {

	private int value;
	
	private String name;
	
	private NormalModel itemStyle;

	public NormalModel getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(NormalModel itemStyle) {
		this.itemStyle = itemStyle;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
