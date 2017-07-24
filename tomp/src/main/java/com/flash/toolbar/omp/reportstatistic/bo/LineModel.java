package com.flash.toolbar.omp.reportstatistic.bo;

import java.util.List;

public class LineModel {

	private String name;
	
	private String type;
	
	private List data;
	
	private NormalModel itemStyle;
	
	private MarkPointModel markPoint;

	public MarkPointModel getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(MarkPointModel markPoint) {
		this.markPoint = markPoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public NormalModel getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(NormalModel itemStyle) {
		this.itemStyle = itemStyle;
	}

}
