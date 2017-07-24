package com.flash.toolbar.omp.reportstatistic.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.flash.toolbar.omp.model.RpWindowStatReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpWindowStatReportModel;
import com.flash.toolbar.omp.reportstatistic.service.PageAccessService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/pageAccess")
public class PageAccessController extends BaseAction{
	private static final Logger log = Logger.getLogger(PageAccessController.class);
	
	public static final int DEFAULT_MAX_ROWS = 1024;
	
	private static final String REQ_ATTR_PROGRESS_CUR = "entity.progress.cur";
	private static final String REQ_ATTR_PROGRESS_COUNT = "entity.progress.count";
	private static final String REQ_ATTR_PROGRESS_TIME_DIFF = "entity.progress.time.diff";
	private static final String REQ_ATTR_PROGRESS_LAST_TIME = "entity.progress.time.last";	
	
	@Autowired
	private PageAccessService pageAccessService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "reportStatistic/pageAccess";
	}
	
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,RpWindowStatReportModel rpWindowStatReportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				rpWindowStatReportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				rpWindowStatReportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<RpWindowStatReportModel> list = pageAccessService.getPageAccessListInfo(rpWindowStatReportModel);
//			List<Map<String, Object>> listNew = new ArrayList<Map<String,Object>>();
//			if(typeList!=null && typeList.size()>0){
//				for (RpWindowStatReport rpWindowStatReport : typeList) {
//					String pageNo = rpWindowStatReport.getPageno();
//					Map<String, Object> map = new HashMap<String, Object>();
//					if(list!=null && list.size()>0){
//						for (RpWindowStatReportModel rpWindowStatReportModelTemp : list) {
//							String pageNoTemp = rpWindowStatReportModelTemp.getBean().getPageno();
//							Date dateTemp = rpWindowStatReportModelTemp.getBean().getDatadate();
//							if(!StringUtil.isNull(pageNo) && !StringUtil.isNull(pageNoTemp) && pageNo.equals(pageNoTemp)){ 
//								if(map.containsKey(dateTemp)){
//								}else{
//									map.put("datadate", dateTemp);
//								}
//							}
//						}
//					}
//				}
//			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", rpWindowStatReportModel.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the pageAccess :"+e);
		}
	}
	
	@RequestMapping(value="/export")
	public void export(HttpServletRequest request, HttpServletResponse response,RpWindowStatReportModel rpWindowStatReportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				rpWindowStatReportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				rpWindowStatReportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<RpWindowStatReport> list = pageAccessService.getPageAccessListAll(rpWindowStatReportModel);
			
			File xlsFile = exportPageAccess(request, response, list);
			
			download(xlsFile, response);
		} catch (Exception e) {
			log.error("The exception of exporting the pageAccess :"+e);
		}
	}
	
	private File exportPageAccess(HttpServletRequest request, HttpServletResponse response,List<RpWindowStatReport> list) throws Exception{
		File xlsFile = TempFileProvider.createTempFile("pageAccess", ".xls");
		
		WritableWorkbook workbook = createWorkbook(xlsFile);
		WritableSheet sheet = createWritableSheet(workbook, "pageAccess", null);

		CellFormats formats = createCellFormats();
		
		List<Column> listCol = new ArrayList<Column>();
		if(list!=null && list.size()>0){
			Column fC = new Column();
			fC.setTitle("Time");
			fC.setWidth(getColumnWidth("Time"));
			listCol.add(fC);
			String pageName = list.get(0).getAccessurl();
			String[] pageNames = StringUtil.getStr(pageName).split(",");
			if(pageNames!=null && pageNames.length>0){
				for (String str : pageNames) {
					Column c = new Column();
					c.setTitle(str);
					c.setWidth(getColumnWidth(str));
					listCol.add(c);
				}
			}
		}
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
            response.addHeader("Content-Disposition", "attachment;filename=pageAccess.xls");  
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
	
	
	
	private void writeDataColumns(List<RpWindowStatReport>  objectList, WritableSheet sheet, HttpServletRequest request,
			HttpServletResponse response, CellFormats formats) throws Exception {
		
			int rows = sheet.getRows();
		
//			int c = 0;
			for (RpWindowStatReport rpWindowStatReport : objectList) {
				increaseProgressCurrent(request);
				
				sheet.addCell(new Label(0, rows, DateUtil.getFormatDate(rpWindowStatReport.getDatadate(),"yyyy-MM-dd"), formats.getDataFormat()));
				
				String value = "";
				String totnum = rpWindowStatReport.getTotnum();
				String consnum = rpWindowStatReport.getConsnum();
				String[] totnums = StringUtil.getStr(totnum).split(",");
				String[] consnums = StringUtil.getStr(consnum).split(",");
				if(totnums!=null && consnums!=null && totnums.length>0 && consnums.length>0 && consnums.length==totnums.length){
					for (int i = 0; i < totnums.length; i++) {
						String val1 = "empty".equals(totnums[i])?"":totnums[i];
						String val2 = "empty".equals(totnums[i])?"":consnums[i];
						value = "  "+val1+"        "+val2;
						sheet.addCell(new Label(i+1, rows, value, formats.getDataFormat()));
					}
				}
				
//				c++;
				rows++;
			}
			
		}
	
	
	private void writeHeaderColumns(HttpServletRequest request, List<Column> columns, WritableSheet sheet, CellFormats formats)
			throws Exception {
		int c = 0;
		for (Column column : columns) {
			Label label = new Label(c, 0, column.getTitle(), formats.getRequiredFormat());
			sheet.addCell(label);			
			if(c!=0){
				Label label1 = new Label(c, 1, "Times  "+"  Users", formats.getHeaderFormat());
				sheet.addCell(label1);				
			}
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
