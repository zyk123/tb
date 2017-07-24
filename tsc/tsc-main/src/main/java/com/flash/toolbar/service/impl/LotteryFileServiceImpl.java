package com.flash.toolbar.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Configuration;
import com.flash.toolbar.common.util.DateUtil;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.mapper.RzAccessLogMapper;
import com.flash.toolbar.mapper.RzClickEventLogMapper;
import com.flash.toolbar.model.RzAccessLog;
import com.flash.toolbar.model.RzClickEventLog;
import com.flash.toolbar.service.LotteryFileService;

@Transactional
@Service("lotteryFileService")
public class LotteryFileServiceImpl implements LotteryFileService{
	
	@Autowired
	private RzAccessLogMapper rzAccessLogMapper;
	
	@Resource
	private DefaultSqlSessionFactory sqlSessionFactory;

	/**
	 * 读取页面话单文件
	 */
	@Override
	public void readPageLoadCsv() {
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "readPageLoadCsv", true, null, String.valueOf(new Date().getTime()));
		BufferedReader reader = null;
		Configuration cfg = new Configuration(null, "environment.properties");
		String fileURL = cfg.getValue("lottery.pageload.path");
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
			String time = format.format(calendar.getTime());
//			fileURL = fileURL + time.substring(0, 8);
//			File dir = new File(fileURL);
//			if (!dir.exists()) {
//				TraceLogger.error(fileURL + " does not exist");
//				return;
//			}
			
			String fileURLy = fileURL + time.substring(0, 6);
			File diry = new File(fileURLy);
			if (!diry.exists()) {
				TraceLogger.error(fileURLy + " does not exist");
				return;
			}
			String fileURLt = fileURLy + File.separator + time.substring(0, 8);
			File dirt = new File(fileURLt);
			if (!dirt.exists()) {
				TraceLogger.error(fileURLt + " does not exist");
				return;
			}
			
			File[] files = dirt.listFiles();
			int totalCount = 0;
			for(File file : files){
				String fileName = file.getName();
				if (!file.exists()) {
					TraceLogger.error(file.getName() + " does not exist");
					continue;
				}
				String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				if(!ext.equalsIgnoreCase("csv")){
					continue;
				}
				
				StringBuffer sbCount = new StringBuffer();//已经读取的行数
				StringBuffer endLine = new StringBuffer();//结束标记
				int eachCount = 1000;//每次读取的行数
				int curCount = 0;
				
				while(!endLine.toString().equals("-1")){
					List<RzAccessLog> list = parseCsvForAccessLog(file, sbCount, endLine, curCount, eachCount);
					if(list != null && list.size() > 0){
						totalCount += list.size();
						saveBatch(list);
					}
					curCount = Integer.parseInt(sbCount.toString());
					list.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != reader){
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "readPageLoadCsv", false, null, String.valueOf(new Date().getTime()));
	}
	
	/**
	 * 分页读取csv
	 * @param file
	 * @param sbCount 返回当前行
	 * @param endLine 返回结束标记 -1标识文件读取结束
	 * @param curCount 当前行
	 * @param eachCount 每次读取的行
	 * @return
	 */
	private List<RzAccessLog> parseCsvForAccessLog(File file, StringBuffer sbCount, StringBuffer endLine, int curCount, int eachCount){
		endLine.delete(0, endLine.length());
		sbCount.delete(0, sbCount.length());
		List<RzAccessLog> list = new ArrayList<RzAccessLog>();
		BufferedReader reader = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = new String("");
			int count = 0;//记录当前读取的行数
			while ((line = reader.readLine()) != null) {
				count += 1;
				//当前行数小于已经读取的行数则跳过
				if(count < curCount){
					continue;
				}
				//当前行数减已经读取的行数如果大于每次需要读取的行数则跳出
				if((count - curCount) >= eachCount){
					break;
				}
				try{
					RzAccessLog log = new RzAccessLog();
					String[] strArr = line.split(",");
					if(strArr.length > 12){
						log.setAccesslogid(StringUtil.formatUUID(false));
						log.setMobileno(strArr[0]);
						
						String accurl = strArr[6];
						if(accurl.length()>800) {
							accurl = accurl.substring(0,399);
						}
						log.setAccessurl(accurl);
						log.setAccesstime(format.parse(strArr[7]));
						
						String cururl = strArr[10];
						if(cururl.length()>800) {
							cururl = cururl.substring(0, 399);
						}
						log.setCururl(cururl);
						
						String refer = strArr[11];
						if(refer.length()>800) {
							refer = refer.substring(0, 399);
						}
						log.setReferurl(refer);
						
						String userAgent = strArr[12];
						if(userAgent.length()>800) {
							userAgent = userAgent.substring(0,399);
						}
						log.setUseragent(userAgent);
						
						log.setSip(strArr[9]);
						log.setSusercookieid("");
						log.setSdeviceno("");
						log.setSdevicetype("");
						log.setScity("");
						log.setStaytime(0);
						log.setCountryno(strArr[5]);
						log.setToperatorid(strArr[4]);
						
						list.add(log);
					}
					else{
						TraceLogger.info(file.getName() + "-" + count + " :" +line);
					}
				}
				catch(Exception e){
					TraceLogger.error(file.getName() + " - read csv error at line:" + count);
					e.printStackTrace();
				}
			}			
			if(line == null){//读取结束
				endLine.append("-1");
			}
			else{
				endLine.append("0");
			}
			sbCount.append(count);
			
		} catch (Exception e) {
			endLine.delete(0, endLine.length());
			endLine.append("-1");
			e.printStackTrace();
		} finally{
			if(null != reader){
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					endLine.delete(0, endLine.length());
					endLine.append("-1");
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 批量插入
	 * @param data
	 */
	private void saveBatch(List<RzAccessLog> data) {
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            batchSqlSession.getMapper(RzAccessLogMapper.class).insertBatch(data);
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(batchSqlSession != null){
                batchSqlSession.close();
                data.clear();
            }
        }
    }

	/**
	 * 保存用户抽奖话单
	 * 手机号 是否中奖 活动名称 奖品名称
	 */
	@Override
	public void savePromtionCsv(String requestSerial, String mobileNo, Boolean lucky, String promotionName, String prizeName) {
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "savePromtionCsv", true, null, String.valueOf(new Date().getTime()));
		Configuration cfg = new Configuration(null, "environment.properties");
		String fileURL = cfg.getValue("lottery.promotion.path");
		BufferedWriter writer = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
			fileURL = fileURL + time.substring(0, 8);
			File dir = new File(fileURL);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(fileURL + "/promotion.csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			StringBuilder sb = new StringBuilder();
			sb.append(DateUtil.getFormatDate(new Date())).append(",");
			sb.append(mobileNo).append(",");
			sb.append(lucky==true?1:0).append(",");
			sb.append(promotionName).append(",");
			sb.append(prizeName);
			
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(sb.toString());
			writer.newLine();
			writer.flush();
		} catch (Exception e) {
			ExceptionLogger.LoggerInfo(requestSerial, mobileNo, "LotteryFileServiceImpl savePromtionCsv", e);
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
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "savePromtionCsv", false, null, String.valueOf(new Date().getTime()));
	}

	/**
	 * 读取页面话单文件
	 */
	@Override
	public void readClickEventCsv() {
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "readClickEventCsv", true, null, String.valueOf(new Date().getTime()));
		BufferedReader reader = null;
		Configuration cfg = new Configuration(null, "environment.properties");
		String fileURL = cfg.getValue("lottery.clickevent.path");
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
			String time = format.format(calendar.getTime());
//			fileURL = fileURL + time.substring(0, 8);
//			File dir = new File(fileURL);
//			if (!dir.exists()) {
//				TraceLogger.error(fileURL + " does not exist");
//				return;
//			}
			
			String fileURLy = fileURL + time.substring(0, 6);
			File diry = new File(fileURLy);
			if (!diry.exists()) {
				TraceLogger.error(fileURLy + " does not exist");
				return;
			}
			String fileURLt = fileURLy + File.separator + time.substring(0, 8);
			File dirt = new File(fileURLt);
			if (!dirt.exists()) {
				TraceLogger.error(fileURLt + " does not exist");
				return;
			}
			File[] files = dirt.listFiles();
			for(File file : files){
				String fileName = file.getName();
				if (!file.exists()) {
					TraceLogger.error(file.getName() + " does not exist");
					continue;
				}
				String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				if(!ext.equalsIgnoreCase("csv")){
					continue;
				}
				
				StringBuffer sbCount = new StringBuffer();//已经读取的行数
				StringBuffer endLine = new StringBuffer();//结束标记
				int eachCount = 1000;//每次读取的行数
				int totalCount = 0;
				int curCount = 0;
				
				while(!endLine.toString().equals("-1")){
					List<RzClickEventLog> list = parseCsvForClickEvent(file, sbCount, endLine, curCount, eachCount);
					if(list != null && list.size() > 0){
						totalCount += list.size();
						saveBatchClickEvent(list);
					}
					curCount = Integer.parseInt(sbCount.toString());
					list.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != reader){
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "readClickEventCsv", false, null, String.valueOf(new Date().getTime()));
	}
	
	/**
	 * 分页读取csv
	 * @param file
	 * @param sbCount 返回当前行
	 * @param endLine 返回结束标记 -1标识文件读取结束
	 * @param curCount 当前行
	 * @param eachCount 每次读取的行
	 * @return
	 */
	private List<RzClickEventLog> parseCsvForClickEvent(File file, StringBuffer sbCount, StringBuffer endLine, int curCount, int eachCount){
		endLine.delete(0, endLine.length());
		sbCount.delete(0, sbCount.length());
		List<RzClickEventLog> list = new ArrayList<RzClickEventLog>();
		BufferedReader reader = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = new String("");
			int count = 0;//记录当前读取的行数
			while ((line = reader.readLine()) != null) {
				count += 1;
				//当前行数小于已经读取的行数则跳过
				if(count < curCount){
					continue;
				}
				//当前行数减已经读取的行数如果大于每次需要读取的行数则跳出
				if((count - curCount) >= eachCount){
					break;
				}
				try{
					RzClickEventLog log = new RzClickEventLog();
					String[] strArr = line.split(",");
					if(strArr.length > 12){
						log.setAccesslogid(StringUtil.formatUUID(false));
						log.setMobileno(strArr[0]);
						
						String accurl = strArr[6];
						if(accurl.length()>800) {
							accurl = accurl.substring(0,399);
						}
						log.setClickurl(accurl);
						log.setAccesstime(format.parse(strArr[7]));
						
						String refer = strArr[11];
						if(refer.length()>800) {
							refer = refer.substring(0, 399);
						}
						log.setReferurl(refer);
						
						String userAgent = strArr[12];
						if(userAgent.length()>800) {
							userAgent = userAgent.substring(0,399);
						}
						log.setUseragent(userAgent);
						
						log.setSip(strArr[9]);
						log.setSusercookieid("");
						log.setSdeviceno("");
						log.setSdevicetype("");
						log.setScity("");
						log.setStaytime(0);
						log.setCountryno(strArr[5]);
						log.setToperatorid(strArr[4]);
						
						list.add(log);
					}
					else{
						TraceLogger.info(file.getName() + "-" + count + " :" +line);
					}
				}
				catch(Exception e){
					TraceLogger.error(file.getName() + " - read csv error at line:" + count);
					e.printStackTrace();
				}
			}			
			if(line == null){//读取结束
				endLine.append("-1");
			}
			else{
				endLine.append("0");
			}
			sbCount.append(count);
			
		} catch (Exception e) {
			endLine.delete(0, endLine.length());
			endLine.append("-1");
			e.printStackTrace();
		} finally{
			if(null != reader){
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					endLine.delete(0, endLine.length());
					endLine.append("-1");
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 批量插入
	 * @param data
	 */
	private void saveBatchClickEvent(List<RzClickEventLog> data) {
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            batchSqlSession.getMapper(RzClickEventLogMapper.class).insertBatch(data);
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(batchSqlSession != null){
                batchSqlSession.close();
                data.clear();
            }
        }
    }
}
