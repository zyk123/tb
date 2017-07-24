package com.flash.toolbar.omp.reportstatistic.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.export.CellFormats;
import com.flash.toolbar.omp.common.export.Column;
import com.flash.toolbar.omp.common.export.RemoteWorkbook;
import com.flash.toolbar.omp.common.io.TempFileProvider;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.Reloadreport;
import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.ReloadreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;
import com.flash.toolbar.omp.reportstatistic.service.ReloadService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/reload")
public class ReloadController extends BaseAction{
	private static final Logger log = Logger.getLogger(ReloadController.class);
	
	public static final int DEFAULT_MAX_ROWS = 1024;
	
	private static final String REQ_ATTR_PROGRESS_CUR = "entity.progress.cur";
	private static final String REQ_ATTR_PROGRESS_COUNT = "entity.progress.count";
	private static final String REQ_ATTR_PROGRESS_TIME_DIFF = "entity.progress.time.diff";
	private static final String REQ_ATTR_PROGRESS_LAST_TIME = "entity.progress.time.last";	
	
	@Autowired
	private ReloadService reloadService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "reportStatistic/reload";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,ReloadreportModel reloadreportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				reloadreportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				reloadreportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<Reloadreport> list = reloadService.getClickEventInfo(reloadreportModel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", reloadreportModel.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the clickEvent :"+e);
		}
	}
	
	
	@RequestMapping(value="/export")
	public void export(HttpServletRequest request, HttpServletResponse response,RpClickEventReportModel rpClickEventReportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				rpClickEventReportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				rpClickEventReportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<RpClickEventReport> list = reloadService.getClickEventInfoAll(rpClickEventReportModel);
			
			File xlsFile = exportClickEvent(request, response, list);
			
			download(xlsFile, response);
		} catch (Exception e) {
			log.error("The exception of exporting the pageAccess :"+e);
		}
	}	
	
	
	
	
	private File exportClickEvent(HttpServletRequest request, HttpServletResponse response,List<RpClickEventReport> list) throws Exception{
		File xlsFile = TempFileProvider.createTempFile("clickEvent", ".xls");
		
		WritableWorkbook workbook = createWorkbook(xlsFile);
		WritableSheet sheet = createWritableSheet(workbook, "clickEvent", null);

		CellFormats formats = createCellFormats();
		
		List<Column> listCol = new ArrayList<Column>();
		Column c1 = new Column("DATADATE");
		c1.setWidth(getColumnWidth("DATADATE"));
		listCol.add(c1);
		Column c2 = new Column("CLICKURL");
		c2.setWidth(getColumnWidth("CLICKURL"));
		listCol.add(c2);
		Column c3 = new Column("TOTNUM");
		c3.setWidth(getColumnWidth("TOTNUM"));
		listCol.add(c3);
		Column c4 = new Column("CONSNUM");
		c4.setWidth(getColumnWidth("CONSNUM"));		
		listCol.add(c4);
		
		writeHeaderColumns(request, listCol, sheet, formats);
		
		setProgressCount(request, list.size());
		
		writeDataColumns(list, sheet, request, response, formats);
		
		workbook.write();
		workbook.close();
		return xlsFile;
	}
	
    private void download(File file, HttpServletResponse response) throws Exception{  
        InputStream fis = new BufferedInputStream(new FileInputStream(file));  
        byte[] buffer = new byte[fis.available()];  
        fis.read(buffer);  
        fis.close();  
        
        response.reset();  
        response.addHeader("Content-Disposition", "attachment;filename=clickEvent.xls");  
        response.addHeader("Content-Length", "" + file.length());  
        OutputStream os = new BufferedOutputStream(  
                response.getOutputStream());  
        response.setContentType("application/vnd.ms-excel;charset=utf-8");  
        os.write(buffer);  
        os.flush();  
        os.close();  
} 	


private WorkbookSettings createWorkbookSettings() {
	return RemoteWorkbook.createWorkbookSettings();
}


private WritableWorkbook createWorkbook(File xlsFile) throws Exception {
	// return Workbook.createWorkbook(xlsFile,
	// this.createWorkbookSettings());
	return RemoteWorkbook.createWorkbook(xlsFile, createWorkbookSettings(), getXlsSplitRows());
}

private int getXlsSplitRows() {
	return RemoteWorkbook.DEFAULT_MAX_ROWS;
}

private WritableSheet createWritableSheet(WritableWorkbook workbook, String sheetName, String headerStr)
		throws Exception {
	return RemoteWorkbook.createWritableSheet(workbook, sheetName, headerStr);
}

private CellFormats createCellFormats() throws Exception {
	return CellFormats.createDefaultCellFormats();
}



private void increaseProgressCurrent(HttpServletRequest request) throws Exception {
	long lastTime = getProgressLastTime(request);
	long curTime = System.currentTimeMillis();
	long diff = curTime - lastTime;
	setProgressLastTime(request, curTime);
	setProgressTimeDiff(request, diff);
	setProgressCurrent(request, getProgressCurrent(request) + 1);
	if (Thread.currentThread().isInterrupted())
		throw new InterruptedException();
}

private long getProgressLastTime(HttpServletRequest request) {
	Long t = (Long) request.getAttribute(REQ_ATTR_PROGRESS_LAST_TIME);
	return t == null ? 0L : t.longValue();
}	


private void setProgressCount(HttpServletRequest request, long count) {
	request.setAttribute(REQ_ATTR_PROGRESS_COUNT, count);
}

private void setProgressLastTime(HttpServletRequest request, long time) {
	request.setAttribute(REQ_ATTR_PROGRESS_LAST_TIME, time);
}

private void setProgressTimeDiff(HttpServletRequest request, long diff) {
	request.setAttribute(REQ_ATTR_PROGRESS_TIME_DIFF, diff);
}

private void setProgressCurrent(HttpServletRequest request, long cur) {
	request.setAttribute(REQ_ATTR_PROGRESS_CUR, cur);
}

public  long getProgressCurrent(HttpServletRequest request) {
	Long cur = (Long) request.getAttribute(REQ_ATTR_PROGRESS_CUR);
	return cur == null ? 0L : cur.longValue();
}



private void writeDataColumns(List<RpClickEventReport>  objectList, WritableSheet sheet, HttpServletRequest request,
		HttpServletResponse response, CellFormats formats) throws Exception {
	
		int rows = sheet.getRows();
	
//		int c = 0;
		for (RpClickEventReport rpClickEventReport : objectList) {
			increaseProgressCurrent(request);
			
			String dataDate = DateUtil.getFormatDate(rpClickEventReport.getDatadate(),"yyyy-MM-dd");
			String clickUrl = rpClickEventReport.getClickurl();
			String totnum = StringUtil.getStr(rpClickEventReport.getTotnum());
			String consnum = StringUtil.getStr(rpClickEventReport.getConsnum());
			if(!StringUtil.isNull(totnum) && !StringUtil.isNull(consnum)){
					totnum = "empty".equals(totnum)?"":totnum;
					consnum = "empty".equals(consnum)?"":consnum;
				}
			sheet.addCell(new Label(0, rows, dataDate, formats.getDataFormat()));
			sheet.addCell(new Label(1, rows, clickUrl, formats.getDataFormat()));
			sheet.addCell(new Label(2, rows, totnum, formats.getDataFormat()));
			sheet.addCell(new Label(3, rows, consnum, formats.getDataFormat()));
//			c++;
			rows++;
		}
		
	}


private void writeHeaderColumns(HttpServletRequest request, List<Column> columns, WritableSheet sheet, CellFormats formats)
		throws Exception {
	int c = 0;
	for (Column column : columns) {
		Label label = new Label(c, 0, column.getTitle(), formats.getRequiredFormat());
		sheet.addCell(label);
		sheet.setColumnView(c, column.getWidth());
		c++;
	}
}

private int  getColumnWidth(String str){
	if(StringUtil.isNull(str)){
		return 13;
	}
	return (str.length()+10)<13?13:(str.length()+10);
}	
}
