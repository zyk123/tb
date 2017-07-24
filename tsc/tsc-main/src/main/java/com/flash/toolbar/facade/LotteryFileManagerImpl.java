package com.flash.toolbar.facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.common.bean.PageLoadBean;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Configuration;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.FileHelper;
import com.flash.toolbar.service.LotteryFileService;
import com.flash.toolbar.service.ScheduleService;

@Service("lotteryFileManager")
public class LotteryFileManagerImpl implements LotteryFileManager{
	@Resource
	private ScheduleService scheduleService;
	@Resource
	private LotteryFileService lotteryFileService;
	
	/**
	 * 保存页面话单文件
	 */
	@Override
	public String savePageLoad(PageLoadBean bean,String requestSerial) {
		//tsc记录用户访问页面事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "savePageLoad", true, null, String.valueOf(new Date().getTime()));
		String result = Constant.FAILURE;
		Configuration cfg = new Configuration(null, "environment.properties");
		String fileURL = cfg.getValue("lottery.pageload.path");
		String splitSize = cfg.getValue("lottery.split.size");//文件分割大小配置
		BufferedWriter writer = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
//			fileURL = fileURL + time.substring(0, 8);
			String fileURLy = fileURL + time.substring(0, 6);
			File diry = new File(fileURLy);
			if (!diry.exists()) {
				diry.mkdirs();
			}
			String fileURLt = fileURLy + time.substring(0, 8);
			File dirt = new File(fileURLt);
			if (!dirt.exists()) {
				dirt.mkdirs();
			}
			int fileIndex = FileHelper.getSplitFileIndex(fileURLt);
			String fileName = fileURLt + "/pageload_" +fileIndex+ ".csv";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			//文件大于设定值则创建一个新文件
			long lSize = Long.parseLong(splitSize);
			if(file.length() > lSize){
				fileName = fileURLt + "/pageload_" + (fileIndex + 1) + ".csv";
				file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append(bean.getPhoneNo()).append(",");
			sb.append(bean.getImei()).append(",");
			sb.append(bean.getMsip()).append(",");
			sb.append(bean.getMemberId()).append(",");
			sb.append(bean.gettOperatorId()).append(",");
			sb.append(bean.getCountryNo()).append(",");
			sb.append(bean.getPageName().replaceAll(",", "#")).append(",");
			sb.append(time).append(",");
			sb.append(bean.getTimeZone()).append(",");
			sb.append(bean.getIp().replaceAll(",", "#")).append(",");
			sb.append(bean.getHref().replaceAll(",", "#")).append(",");
			sb.append(bean.getReferer().replaceAll(",", "#")).append(",");
			sb.append(bean.getUserAgent().replaceAll(",", "#"));
			
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(sb.toString());
			writer.newLine();
			writer.flush();
			result = Constant.SUCCEED;
		} catch (Exception e) {
			//e.printStackTrace();
			ExceptionLogger.LoggerInfo(requestSerial, "", "LotteryFileManagerImpl savePageLoad", e);
		}finally{
			if(null != writer){
				try {
					writer.close();
					writer = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//tsc记录用户访问页面事件业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "savePageLoad", false,result, String.valueOf(new Date().getTime()));
		return result;
	}

	@Override
	public String manualDoStaticCache() {
		scheduleService.doStaticCache();
		return null;
	}

	public String manualReadPageCsv() {
		lotteryFileService.readPageLoadCsv();
		return null;
	}
	
	public String manualReadClickEventCsv(){
		lotteryFileService.readClickEventCsv();
		return null;
	}
	
	/**
	 * 保存页面话单文件
	 */
	@Override
	public String saveClickEventCsv(PageLoadBean bean,String requestSerial) {
		//tsc记录用户访问页面事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveClickEventCsv", true, null, String.valueOf(new Date().getTime()));
		String result = Constant.FAILURE;
		Configuration cfg = new Configuration(null, "environment.properties");
		String fileURL = cfg.getValue("lottery.clickevent.path");
		String splitSize = cfg.getValue("lottery.split.size");//文件分割大小配置
		BufferedWriter writer = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
//			fileURL = fileURL + time.substring(0, 8);
			String fileURLy = fileURL + time.substring(0, 6);
			File diry = new File(fileURLy);
			if (!diry.exists()) {
				diry.mkdirs();
			}
			String fileURLt = fileURLy + time.substring(0, 8);
			File dirt = new File(fileURLt);
			if (!dirt.exists()) {
				dirt.mkdirs();
			}
			int fileIndex = FileHelper.getSplitFileIndex(fileURLt);
			String fileName = fileURLt + "/pageload_" +fileIndex+ ".csv";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			//文件大于设定值则创建一个新文件
			long lSize = Long.parseLong(splitSize);
			if(file.length() > lSize){
				fileName = fileURLt + "/pageload_" + (fileIndex + 1) + ".csv";
				file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append(bean.getPhoneNo()).append(",");
			sb.append(bean.getImei()).append(",");
			sb.append(bean.getMsip()).append(",");
			sb.append(bean.getMemberId()).append(",");
			sb.append(bean.gettOperatorId()).append(",");
			sb.append(bean.getCountryNo()).append(",");
			sb.append(bean.getClickEventName().replaceAll(",", "#")).append(",");
			sb.append(time).append(",");
			sb.append(bean.getTimeZone()).append(",");
			sb.append(bean.getIp().replaceAll(",", "#")).append(",");
			sb.append(bean.getHref().replaceAll(",", "#")).append(",");
			sb.append(bean.getReferer().replaceAll(",", "#")).append(",");
			sb.append(bean.getUserAgent().replaceAll(",", "#"));
			
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(sb.toString());
			writer.newLine();
			writer.flush();
			result = Constant.SUCCEED;
		} catch (Exception e) {
			//e.printStackTrace();
			ExceptionLogger.LoggerInfo(requestSerial, "", "LotteryFileManagerImpl saveClickEventCsv", e);
		}finally{
			if(null != writer){
				try {
					writer.close();
					writer = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//tsc记录用户访问页面事件业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveClickEventCsv", false,result, String.valueOf(new Date().getTime()));
		return result;
	}
}
