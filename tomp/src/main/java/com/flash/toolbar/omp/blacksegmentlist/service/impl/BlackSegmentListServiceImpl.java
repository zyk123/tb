package com.flash.toolbar.omp.blacksegmentlist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.blacksegmentlist.bo.HyBlackSectionModel;
import com.flash.toolbar.omp.blacksegmentlist.service.BlackSegmentListService;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HyBlackImportBatchMapper;
import com.flash.toolbar.omp.mapper.HyBlackSectionMapper;
import com.flash.toolbar.omp.model.HyBlackImportBatch;
import com.flash.toolbar.omp.model.HyBlackSection;
import com.flash.toolbar.omp.user.bo.QxUserModel;
@Transactional
@Service
public class BlackSegmentListServiceImpl implements BlackSegmentListService{
	@Resource
	private HyBlackSectionMapper hyBlackSectionMapper;
	
	@Resource
	private HyBlackImportBatchMapper hyBlackImportBatchMapper;
	
	@Override
	public List<HyBlackSectionModel> getBlackSectionListInfo (
			HyBlackSectionModel blackSectionModel) throws Exception{
		int totalRows = countByCondition(blackSectionModel);
		blackSectionModel.getPager().setTotalRowsCount(totalRows);
		blackSectionModel.getPager().doPage();
		int totalPageNum = blackSectionModel.getPager().getTotalPageNum();
		int pageIndex = blackSectionModel.getPageIndex();
		if(pageIndex>totalPageNum){
			blackSectionModel.setPageIndex(1);
			blackSectionModel.getPager().doPage();
		}
//		List<HyBlackSectionModel> listModel = new ArrayList<HyBlackSectionModel>();		
//		//将批次编号转换为批次Id查询
//		if(blackSectionModel!=null &&  !StringUtil.isNull(blackSectionModel.getBatchNo())){
//			HyBlackImportBatch hyBlackImportBatch = hyBlackImportBatchMapper.selectByBatchNo(blackSectionModel.getBatchNo());
//			if(hyBlackImportBatch!=null && !StringUtil.isNull(hyBlackImportBatch.getBatchId())){
//				blackSectionModel.getBean().setBatchId(hyBlackImportBatch.getBatchId());
//			}else{
//				return listModel;
//			}
//		}
		List<HyBlackSectionModel> list = hyBlackSectionMapper.selectByPageSelective(blackSectionModel);
//		if(list!=null && list.size()>0){
//			List<String> listStr = new ArrayList<String>(list.size());
//			for (HyBlackSection hyBlackSection : list) {
//				listStr.add(hyBlackSection.getBatchId());
//			}
//			List<String> listBatchNo = hyBlackImportBatchMapper.selectByIdBatch(listStr);
//			if(listBatchNo!=null && listBatchNo.size()>0){
//				for (int i = 0; i < list.size(); i++) {
//					HyBlackSectionModel  hyBlackSectionListModel = new HyBlackSectionModel();
//					hyBlackSectionListModel.setBean(list.get(i));
//					hyBlackSectionListModel.setBatchNo(listBatchNo.get(i));
//					listModel.add(hyBlackSectionListModel);
//				}
//			}
//		}
		return list;
	}	
	
	@Override
	public int countByCondition(HyBlackSectionModel blackSectionModel) throws Exception{
		return hyBlackSectionMapper.countByCondition(blackSectionModel);
	}

	@Override
	public void deleteBlackSectionList(String[] bSectionListIds,
			QxUserModel qxUserModel) throws Exception{
		hyBlackSectionMapper.deleteBlackSectionListBatch(bSectionListIds, qxUserModel);
		
	}





	@Override
	public void importBlackSectionList(List<Map<String, String>> list,
			QxUserModel qxUserModel) throws Exception {
		final String uuid = RuleUtil.generateUUID();
		if(list==null || list.size()==0 || qxUserModel==null){
			return;
		}
		deleteExistSection(list,qxUserModel);
		addBlackSectionListImp(uuid,qxUserModel,list);
		addBlackSectionListBatch(list,uuid,qxUserModel);
		
	}
	
	private void addBlackSectionListBatch(List<Map<String, String>> list,String uuid,QxUserModel qxUserModel) throws Exception{
		List<HyBlackSection> newList = new ArrayList<HyBlackSection>(list.size());
		for (int i = 0; i < list.size(); i++) {
			HyBlackSection bean = new HyBlackSection();
			String mobileStartNo = list.get(i).get("startNo");
			String mobileEndNo = list.get(i).get("endNo");
			bean.setAddDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
			bean.setBatchId(uuid);
			bean.setbSectionId(RuleUtil.generateUUID());
			bean.setMobileNoStart(mobileStartNo);
			bean.setMobileNoEnd(mobileEndNo);
			bean.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
			bean.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
			newList.add(bean);
		}
		hyBlackSectionMapper.insertBlackSectionListBatch(newList);
	}
	private void addBlackSectionListImp(String uuid,QxUserModel qxUserModel,List<Map<String, String>> list) throws Exception{
		HyBlackImportBatch hyBlackImportBatch = new HyBlackImportBatch();
		hyBlackImportBatch.setBatchId(uuid);
		hyBlackImportBatch.setBatchNo(DateUtil.getTimeZoneStr(new Date(),qxUserModel.getTimeZone(),"yyyyMMdd")+"1");
		hyBlackImportBatch.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
		hyBlackImportBatch.setImportDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
		hyBlackImportBatch.setImportNum(list.size());
		hyBlackImportBatch.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
		hyBlackImportBatch.settOperatorName(StringUtil.getStr(qxUserModel.gettOperatorName()));
		hyBlackImportBatch.settOperatorNo(StringUtil.getStr(qxUserModel.gettOperatorNo()));
		hyBlackImportBatchMapper.insert(hyBlackImportBatch);
	}
	
	private void deleteExistSection(List<Map<String, String>> list,QxUserModel qxUserModel) throws Exception{
		List<HyBlackSection> modelList = new ArrayList<HyBlackSection>(list.size());
		for (int i = 0; i < list.size(); i++) {
			HyBlackSection bean = new HyBlackSection();
			String mobileStartNo = list.get(i).get("startNo");
			String mobileEndNo = list.get(i).get("endNo");
			bean.setMobileNoStart(mobileStartNo);
			bean.setMobileNoEnd(mobileEndNo);
			bean.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
			bean.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
			modelList.add(bean);
		}
		hyBlackSectionMapper.deleteExistSection(modelList);
	}

//	public static void main(String[] args) {
//	    String dateStr = "2013-1-31 22:17:14";  
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-6"));  
//	    Date dateTmp = null;
//	    try {  
//	    	dateTmp= dateFormat.parse(dateStr);  
//	        System.out.println(dateTmp);  
//	    } catch (ParseException e) {  
//	        e.printStackTrace();  
//	    }  
//	    String dateStrTmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTmp);  
//	    System.out.println(dateStrTmp);
//	}
}
