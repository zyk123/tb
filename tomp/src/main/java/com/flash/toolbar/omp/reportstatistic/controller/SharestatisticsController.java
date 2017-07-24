package com.flash.toolbar.omp.reportstatistic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.reportstatistic.bo.BrowsermobileosreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.ColorModel;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValue;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValueStr;
import com.flash.toolbar.omp.reportstatistic.bo.LineModel;
import com.flash.toolbar.omp.reportstatistic.bo.LinePoint;
import com.flash.toolbar.omp.reportstatistic.bo.MarkPointModel;
import com.flash.toolbar.omp.reportstatistic.bo.NormalModel;
import com.flash.toolbar.omp.reportstatistic.bo.PieModel;
import com.flash.toolbar.omp.reportstatistic.service.SharestatisticsService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value = "/sharestatisticsController")
public class SharestatisticsController extends BaseAction {

	@Autowired
	private SharestatisticsService sharestatisticsService;

	@RequestMapping(value = "/show")
	public String show() {
		return "reportStatistic/sharestatistics";
	}

	private String requireColor(){
		String color = null;
		String []colors ={"#A4C639","#c0c1c5","#007cc2","#85bfce"};
		Random random=new Random();
		int result=random.nextInt(4);
		color = colors[result];
		return color;
	}
	
	@RequestMapping(value = "/loadData1")
	public void loadData1(HttpServletRequest request,
			HttpServletResponse response,
			BrowsermobileosreportModel browsermobileosreportModel) {
		QxUserModel qxUserModel = getSessionModel();
		if (qxUserModel != null) {
			browsermobileosreportModel.getBean().setCountryno(
					qxUserModel.getBean().getCountryno());
			browsermobileosreportModel.getBean().setToperatorid(
					qxUserModel.getBean().getToperatorid());
		}
		browsermobileosreportModel.setFlag("1");
		Map<String, Object> map = new HashMap<String, Object>();
		List<KeyAndValue> listData = sharestatisticsService
				.loadData1(browsermobileosreportModel);
		List<PieModel> list = new ArrayList<PieModel>();
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < listData.size(); i++) {

			KeyAndValue keyAndValue = listData.get(i);

			categories.add(keyAndValue.getName());

			PieModel pieModel = new PieModel();
			NormalModel normalModel = new NormalModel();
			ColorModel colorModel = new ColorModel();
			colorModel.setColor(requireColor());
			normalModel.setNormal(colorModel);
			pieModel.setItemStyle(normalModel);
			pieModel.setName(keyAndValue.getName());
			pieModel.setValue(keyAndValue.getValue());
			list.add(pieModel);
		}
		map.put("categories", categories);
		map.put("values", list);
		HtmlUtil.writerJson(response, map);
	}

	@RequestMapping(value = "/loadData2")
	public void loadData2(HttpServletRequest request,
			HttpServletResponse response,
			BrowsermobileosreportModel browsermobileosreportModel) {
		QxUserModel qxUserModel = getSessionModel();
		if (qxUserModel != null) {
			browsermobileosreportModel.getBean().setCountryno(
					qxUserModel.getBean().getCountryno());
			browsermobileosreportModel.getBean().setToperatorid(
					qxUserModel.getBean().getToperatorid());
		}
		browsermobileosreportModel.setFlag("2");
		Map<String, Object> map = new HashMap<String, Object>();
		List<KeyAndValue> listData = sharestatisticsService
				.loadData2(browsermobileosreportModel);
		List<PieModel> list = new ArrayList<PieModel>();
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < listData.size(); i++) {

			KeyAndValue keyAndValue = listData.get(i);

			categories.add(keyAndValue.getName());

			PieModel pieModel = new PieModel();
			NormalModel normalModel = new NormalModel();
			ColorModel colorModel = new ColorModel();
			colorModel.setColor(requireColor());
			normalModel.setNormal(colorModel);
			pieModel.setItemStyle(normalModel);
			pieModel.setName(keyAndValue.getName());
			pieModel.setValue(keyAndValue.getValue());
			list.add(pieModel);
		}
		map.put("categories", categories);
		map.put("values", list);
		HtmlUtil.writerJson(response, map);
	}

	@RequestMapping(value = "/loadData3")
	public void loadData3(HttpServletRequest request,
			HttpServletResponse response,
			BrowsermobileosreportModel browsermobileosreportModel) {
		QxUserModel qxUserModel = getSessionModel();
		if (qxUserModel != null) {
			browsermobileosreportModel.getBean().setCountryno(
					qxUserModel.getBean().getCountryno());
			browsermobileosreportModel.getBean().setToperatorid(
					qxUserModel.getBean().getToperatorid());
		}
		browsermobileosreportModel.setFlag("0");
		Map<String, Object> map = new HashMap<String, Object>();
		List<KeyAndValue> listData = sharestatisticsService
				.loadData3(browsermobileosreportModel);
		List<PieModel> list = new ArrayList<PieModel>();
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < listData.size(); i++) {

			KeyAndValue keyAndValue = listData.get(i);

			categories.add(keyAndValue.getName());

			PieModel pieModel = new PieModel();
			NormalModel normalModel = new NormalModel();
			ColorModel colorModel = new ColorModel();
			colorModel.setColor(requireColor());
			normalModel.setNormal(colorModel);
			pieModel.setItemStyle(normalModel);
			pieModel.setName(keyAndValue.getName());
			pieModel.setValue(keyAndValue.getValue());
			list.add(pieModel);
		}
		map.put("categories", categories);
		map.put("values", list);
		HtmlUtil.writerJson(response, map);
	}

	@RequestMapping(value = "/loadData4")
	public void loadData4(HttpServletRequest request,
			HttpServletResponse response,
			BrowsermobileosreportModel browsermobileosreportModel) {
		QxUserModel qxUserModel = getSessionModel();
		if (qxUserModel != null) {
			browsermobileosreportModel.getBean().setCountryno(
					qxUserModel.getBean().getCountryno());
			browsermobileosreportModel.getBean().setToperatorid(
					qxUserModel.getBean().getToperatorid());
		}
		String flag = browsermobileosreportModel.getFlag();
		String flagT = "1";
		if("pie".equals(flag)){
			flagT = "1";
		}else if("pie2".equals(flag)){
			flagT = "2";
		}else if("pie3".equals(flag)){
			flagT = "0";
		}else{
			flagT = "1";
		}
		browsermobileosreportModel.setFlag(flagT);
		String start = browsermobileosreportModel.getBeginDate();
		String end = browsermobileosreportModel.getEndDate();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dBegin = null;
        Date dEnd = null;
		try {
			dBegin = sdf.parse(start);
			dEnd = sdf.parse(end);  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        List<Date> categoriesDate = getDatesBetweenTwoDate(dBegin, dEnd);
        List<String> categories =new ArrayList<String>();
        for(int i=0;i<categoriesDate.size();i++){
        	categories.add(sdf.format(categoriesDate.get(i)));
        }
		List<KeyAndValueStr> listData = sharestatisticsService
				.loadData4(browsermobileosreportModel);
		List<LineModel> list = new ArrayList<LineModel>();

		for (int i = 0; i < listData.size(); i++) {
			KeyAndValueStr keyAndValue = listData.get(i);
			String value = keyAndValue.getValue();
			String []s = value.split(",");
			List data = Arrays.asList(s);
			LineModel lineModel = new LineModel();
			NormalModel normalModel = new NormalModel();
			ColorModel colorModel = new ColorModel();
			MarkPointModel markPointModel = new MarkPointModel();
			LinePoint LinePointMax = new LinePoint();
			LinePoint LinePointMin = new LinePoint();
			List<LinePoint> linePointData = new ArrayList<LinePoint>();
			colorModel.setColor(requireColor());
			normalModel.setNormal(colorModel);
			LinePointMax.setName("最大值");
			LinePointMax.setType("max");
			LinePointMin.setName("最小值");
			LinePointMin.setType("min");
			linePointData.add(LinePointMax);
			linePointData.add(LinePointMin);
			markPointModel.setData(linePointData);
			markPointModel.setItemStyle(normalModel);
			lineModel.setName(keyAndValue.getName());
			lineModel.setType("line");
			lineModel.setData(data);
			lineModel.setItemStyle(normalModel);
			lineModel.setMarkPoint(markPointModel);
			list.add(lineModel);
		}
		map.put("categories", categories);
		map.put("values", list);
		HtmlUtil.writerJson(response, map);
	}
	
    /** 
     * 根据开始时间和结束时间返回时间段内的时间集合 
     *  
     * @param beginDate 
     * @param endDate 
     * @return List 
     */  
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(beginDate);// 把开始时间加入集合  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        lDate.add(endDate);// 把结束时间加入集合  
        return lDate;  
    }

	// public static void main(String[] args) {
	// PieModel pieModel1 = new PieModel();
	// PieModel pieModel2 = new PieModel();
	// PieModel pieModel3 = new PieModel();
	// NormalModel normalModel = new NormalModel();
	// ColorModel colorModel = new ColorModel();
	// colorModel.setColor("#A4C639");
	// normalModel.setNormal(colorModel);
	//
	// pieModel1.setItemStyle(normalModel);
	// pieModel1.setName("Android");
	// pieModel1.setValue(335);
	//
	// pieModel2.setItemStyle(normalModel);
	// pieModel2.setName("Iphone");
	// pieModel2.setValue(336);
	//
	// pieModel3.setItemStyle(normalModel);
	// pieModel3.setName("Other");
	// pieModel3.setValue(337);
	//
	// List<PieModel> list = new ArrayList<PieModel>();
	//
	// list.add(pieModel1);
	// list.add(pieModel2);
	// list.add(pieModel3);
	// System.out.println(JSON.toJSON(list));
	// }
	public static void main(String[] args) {
		List<LineModel> list = new ArrayList<LineModel>();
		LineModel lineModel1 = new LineModel();
		NormalModel normalModel1 = new NormalModel();
		ColorModel colorModel1 = new ColorModel();
		MarkPointModel markPointModel1 = new MarkPointModel();
		LinePoint LinePointMax1 = new LinePoint();
		LinePoint LinePointMin1 = new LinePoint();
		List data1 = new ArrayList();
		data1.add(50);
		data1.add(60);
		data1.add(55);
		data1.add(51);
		data1.add(49);
		data1.add(60);
		data1.add(62);
		List<LinePoint> linePointData1 = new ArrayList<LinePoint>();
		colorModel1.setColor("#A4C639");
		normalModel1.setNormal(colorModel1);
		LinePointMax1.setName("最大值");
		LinePointMax1.setType("max");
		LinePointMin1.setName("最小值");
		LinePointMin1.setType("min");
		linePointData1.add(LinePointMax1);
		linePointData1.add(LinePointMin1);
		markPointModel1.setData(linePointData1);
		markPointModel1.setItemStyle(normalModel1);
		lineModel1.setName("Android");
		lineModel1.setType("line");
		lineModel1.setData(data1);
		lineModel1.setItemStyle(normalModel1);
		lineModel1.setMarkPoint(markPointModel1);

		LineModel lineModel = new LineModel();
		NormalModel normalModel = new NormalModel();
		ColorModel colorModel = new ColorModel();
		MarkPointModel markPointModel = new MarkPointModel();
		LinePoint LinePointMax = new LinePoint();
		LinePoint LinePointMin = new LinePoint();
		List data = new ArrayList();
		data.add(50);
		data.add(60);
		data.add(55);
		data.add(51);
		data.add(49);
		data.add(60);
		data.add(62);
		List<LinePoint> linePointData = new ArrayList<LinePoint>();
		colorModel.setColor("#c0c1c5");
		normalModel.setNormal(colorModel);
		LinePointMax.setName("最大值");
		LinePointMax.setType("max");
		LinePointMin.setName("最小值");
		LinePointMin.setType("min");
		linePointData.add(LinePointMax);
		linePointData.add(LinePointMin);
		markPointModel.setData(linePointData);
		markPointModel.setItemStyle(normalModel);
		lineModel.setName("IOS");
		lineModel.setType("line");
		lineModel.setData(data);
		lineModel.setItemStyle(normalModel);
		lineModel.setMarkPoint(markPointModel);
		list.add(lineModel);
		list.add(lineModel1);
		System.out.println(JSON.toJSON(list));
	}
}
