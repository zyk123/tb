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
import com.flash.toolbar.omp.model.RpPackageSubReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpPackageSubReportModel;
import com.flash.toolbar.omp.reportstatistic.service.PackagePurchaseService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/packagePurchase")
public class PackagePurchaseController extends BaseAction{
	private static final Logger log = Logger.getLogger(PackagePurchaseController.class);
	
	public static final int DEFAULT_MAX_ROWS = 1024;
	
	private static final String REQ_ATTR_PROGRESS_CUR = "entity.progress.cur";
	private static final String REQ_ATTR_PROGRESS_COUNT = "entity.progress.count";
	private static final String REQ_ATTR_PROGRESS_TIME_DIFF = "entity.progress.time.diff";
	private static final String REQ_ATTR_PROGRESS_LAST_TIME = "entity.progress.time.last";		
	
	@Autowired
	private PackagePurchaseService packagePurchaseService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "reportStatistic/packagePurchase";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,RpPackageSubReportModel rpPackageSubReportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				rpPackageSubReportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				rpPackageSubReportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<RpPackageSubReportModel> list = packagePurchaseService.getPackagePurchaseListInfo(rpPackageSubReportModel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", rpPackageSubReportModel.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the packagePurchaseList :"+e);
		}
	}
	
	
	@RequestMapping(value="/export")
	public void export(HttpServletRequest request, HttpServletResponse response,RpPackageSubReportModel rpPackageSubReportModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				rpPackageSubReportModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				rpPackageSubReportModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<RpPackageSubReportModel> list = packagePurchaseService.getPackagePurchaseListAll(rpPackageSubReportModel);
			
			File xlsFile = exportPackagePurchase(request, response, list);
			
			download(xlsFile, response);
		} catch (Exception e) {
			log.error("The exception of exporting the packagePurchaseList :"+e);
		}
	}
	
	private File exportPackagePurchase(HttpServletRequest request, HttpServletResponse response,List<RpPackageSubReportModel> list) throws Exception{
		File xlsFile = TempFileProvider.createTempFile("packagePurchase", ".xls");
		
		WritableWorkbook workbook = createWorkbook(xlsFile);
		WritableSheet sheet = createWritableSheet(workbook, "packagePurchase", null);

		CellFormats formats = createCellFormats();
		
		List<Column> listCol = new ArrayList<Column>();
		if(list!=null && list.size()>0){
			Column fC = new Column();
			fC.setTitle("Time");
			fC.setWidth(getColumnWidth("Time"));
			listCol.add(fC);			
			Column column = new Column();
			column.setTitle("Total");
			column.setWidth(getColumnWidth("TotalConsnum  "+"  TotalTotnum"  +"  TotalPaynum"));
			listCol.add(column);
			String fgName = list.get(0).getBean().getFgname();
			String[] fgNames = StringUtil.getStr(fgName).split(",");
			if(fgNames!=null && fgNames.length>0){
				for (String str : fgNames) {
					Column c = new Column();
					c.setTitle(str);
					c.setWidth(getColumnWidth("Consnum  "+"  Totnum"  +"  Paynum"));
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
        response.addHeader("Content-Disposition", "attachment;filename=packagePurchase.xls");  
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
	
	
	
	private void writeDataColumns(List<RpPackageSubReportModel>  objectList, WritableSheet sheet, HttpServletRequest request,
			HttpServletResponse response, CellFormats formats) throws Exception {
			int rows = sheet.getRows();
			

//			int c = 0;
			for (RpPackageSubReportModel rpPackageSubReportModel : objectList) {
				increaseProgressCurrent(request);
				
				sheet.addCell(new Label(0, rows, DateUtil.getFormatDate(rpPackageSubReportModel.getBean().getDatadate(),"yyyy-MM-dd"), formats.getDataFormat()));				
				
				String alltotnum = rpPackageSubReportModel.getAlltotnum();
				String allconsnum = rpPackageSubReportModel.getAllconsnum();
				String allpaynum = rpPackageSubReportModel.getAllpaynum();
				sheet.addCell(new Label(1,rows, allconsnum+"                  "+alltotnum+"                  "+allpaynum, formats.getDataFormat()));
				String totnum = rpPackageSubReportModel.getBean().getTotnum();
				String consnum = rpPackageSubReportModel.getBean().getConsnum();
				String paynum = rpPackageSubReportModel.getBean().getPaynum();
				String[] totnums = StringUtil.getStr(totnum).split(",");
				String[] consnums = StringUtil.getStr(consnum).split(",");
				String[] paynums = StringUtil.getStr(paynum).split(",");
				if(totnums!=null && consnums!=null && paynums!=null && totnums.length==consnums.length && consnums.length==paynums.length){
					for (int i = 0; i < totnums.length; i++) {
						String value1 = "empty".equals(consnums[i])?"":consnums[i];
						String value2 = "empty".equals(totnums[i])?"":totnums[i];
						String value3 = "empty".equals(paynums[i])?"":paynums[i];
						sheet.addCell(new Label(i+2, rows, value1+"            "+value2+"            "+value3, formats.getDataFormat()));
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
			if(c==1){
				Label label1 = new Label(c, 1, "TotalConsnum  "+"  TotalTotnum"  +"  TotalPaynum", formats.getHeaderFormat());
				sheet.addCell(label1);					
			}
			if(c>1){
				Label label1 = new Label(c, 1, "Consnum  "+"  Totnum"  +"  Paynum", formats.getHeaderFormat());
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
