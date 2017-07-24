package com.flash.toolbar.omp.common.export;


import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

public class CellFormats {
	private WritableCellFormat defaultFormat;
	private WritableCellFormat headerFormat;
	private WritableCellFormat dataFormat;
	private WritableCellFormat dataLockedFormat;
	private WritableCellFormat optionalFormat;
	private WritableCellFormat requiredFormat;

	private WritableCellFormat statusHeaderFormat;
	private WritableCellFormat statusOKFormat;
	private WritableCellFormat statusErrorFormat;
	private WritableCellFormat statusUnknownFormat;
	
	private WritableCellFormat customizedFormat;

	public static WritableCellFormat createHeaderCellFormat() throws Exception {
		WritableCellFormat format = createDefaultCellFormat();
		format.setLocked(true);
		return format;
	}

	public static WritableCellFormat createDataCellFormat() throws Exception {
		return createDefaultCellFormat();
	}

	public static WritableCellFormat createDefaultCellFormat() throws Exception {
		WritableCellFormat cellFormat = new WritableCellFormat(NumberFormats.TEXT);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setWrap(true);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		// 默认不锁定
		cellFormat.setLocked(false);
		return cellFormat;
	}

	/**
	 * 金色表示必须提供的列, 对应optional=false属性
	 * 
	 * 浅黄色表示必填,对应required=true属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public static CellFormats createDefaultCellFormats() throws Exception {
		CellFormats formats = new CellFormats(createDefaultCellFormat());

		formats.setDataFormat(createDataCellFormat());
		formats.setHeaderFormat(createHeaderCellFormat());

		WritableCellFormat dataLockedFormat = createDataCellFormat();
		dataLockedFormat.setLocked(true);
		formats.setDataLockedFormat(dataLockedFormat);// 锁定格式

		WritableCellFormat optinalColumnFormater = createHeaderCellFormat();
		optinalColumnFormater.setBackground(Colour.GOLD);// 金色表示必须提供的列
		formats.setOptionalFormat(optinalColumnFormater);

		WritableCellFormat requiredColumnFormater = createHeaderCellFormat();
		requiredColumnFormater.setBackground(Colour.VERY_LIGHT_YELLOW);// 浅黄色表示必填
		formats.setRequiredFormat(requiredColumnFormater);

		WritableCellFormat headerStatusFormat = createHeaderCellFormat();
		headerStatusFormat.setBackground(Colour.LIGHT_GREEN);
		WritableFont font = new WritableFont(WritableFont.ARIAL);
		font.setBoldStyle(WritableFont.BOLD);
		headerStatusFormat.setFont(font);
		formats.setStatusHeaderFormat(headerStatusFormat);

		WritableCellFormat dataStatusFormat = createDataCellFormat();
		dataStatusFormat.setAlignment(Alignment.CENTRE);
		font = new WritableFont(WritableFont.ARIAL);
		font.setBoldStyle(WritableFont.BOLD);
		font.setColour(Colour.GREEN);
		dataStatusFormat.setFont(font);
		formats.setStatusOKFormat(dataStatusFormat);

		dataStatusFormat = createDataCellFormat();
		dataStatusFormat.setAlignment(Alignment.CENTRE);
		font = new WritableFont(WritableFont.ARIAL);
		font.setBoldStyle(WritableFont.BOLD);
		font.setColour(Colour.RED);
		dataStatusFormat.setFont(font);
		formats.setStatusErrorFormat(dataStatusFormat);

		dataStatusFormat = createDataCellFormat();
		dataStatusFormat.setAlignment(Alignment.CENTRE);
		font = new WritableFont(WritableFont.ARIAL);
		font.setBoldStyle(WritableFont.BOLD);
		font.setColour(Colour.YELLOW);
		dataStatusFormat.setFont(font);
		formats.setStatusUnknownFormat(dataStatusFormat);

		return formats;
	}

	public CellFormats(WritableCellFormat defaultFormat) {
		if (defaultFormat == null)
			throw new IllegalArgumentException("defaultFormat is null");

		this.defaultFormat = defaultFormat;
	}

	public WritableCellFormat getDefaultFormat() {
		return new WritableCellFormat(defaultFormat);
	}

	public void setDefaultFormat(WritableCellFormat defaultFormat) {
		this.defaultFormat = defaultFormat;
	}

	public WritableCellFormat getHeaderFormat() {
		return headerFormat == null ? getDefaultFormat() : new WritableCellFormat(headerFormat);
	}

	public void setHeaderFormat(WritableCellFormat headerFormat) {
		this.headerFormat = headerFormat;
	}

	public WritableCellFormat getDataFormat() {
		return dataFormat == null ? getDefaultFormat() : new WritableCellFormat(dataFormat);
	}

	public void setDataFormat(WritableCellFormat dataFormat) {
		this.dataFormat = dataFormat;
	}

	public WritableCellFormat getDataLockedFormat() {
		return dataLockedFormat == null ? getDefaultFormat() : new WritableCellFormat(dataLockedFormat);
	}

	public void setDataLockedFormat(WritableCellFormat dataLockedFormat) {
		this.dataLockedFormat = dataLockedFormat;
	}

	public WritableCellFormat getOptionalFormat() {
		return optionalFormat == null ? getDefaultFormat() : new WritableCellFormat(optionalFormat);
	}

	public void setOptionalFormat(WritableCellFormat optionalFormat) {
		this.optionalFormat = optionalFormat;
	}

	public WritableCellFormat getRequiredFormat() {
		return requiredFormat == null ? getDefaultFormat() : new WritableCellFormat(requiredFormat);
	}

	public void setRequiredFormat(WritableCellFormat requiredFormat) {
		this.requiredFormat = requiredFormat;
	}

	public WritableCellFormat getStatusHeaderFormat() {
		return statusHeaderFormat == null ? getDefaultFormat() : new WritableCellFormat(statusHeaderFormat);
	}

	public void setStatusHeaderFormat(WritableCellFormat statusHeaderFormat) {
		this.statusHeaderFormat = statusHeaderFormat;
	}

	public WritableCellFormat getStatusOKFormat() {
		return statusOKFormat == null ? getDefaultFormat() : new WritableCellFormat(statusOKFormat);
	}

	public void setStatusOKFormat(WritableCellFormat statusOKFormat) {
		this.statusOKFormat = statusOKFormat;
	}

	public WritableCellFormat getStatusErrorFormat() {
		return statusErrorFormat == null ? getDefaultFormat() : new WritableCellFormat(statusErrorFormat);
	}

	public void setStatusErrorFormat(WritableCellFormat statusErrorFormat) {
		this.statusErrorFormat = statusErrorFormat;
	}

	public WritableCellFormat getStatusUnknownFormat() {
		return statusUnknownFormat == null ? getDefaultFormat() : new WritableCellFormat(statusUnknownFormat);
	}

	public void setStatusUnknownFormat(WritableCellFormat statusUnknownFormat) {
		this.statusUnknownFormat = statusUnknownFormat;
	}

	public WritableCellFormat getCustomizedFormat() {
		return customizedFormat == null ? getDefaultFormat() : new WritableCellFormat(customizedFormat);
	}

	public void setCustomizedFormat(WritableCellFormat customizedFormat) {
		this.customizedFormat = customizedFormat;
	}
	
	
	
}
