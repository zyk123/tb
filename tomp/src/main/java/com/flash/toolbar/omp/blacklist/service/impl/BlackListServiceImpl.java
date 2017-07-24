package com.flash.toolbar.omp.blacklist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.blacklist.service.BlackListService;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HyBlackImportBatchMapper;
import com.flash.toolbar.omp.mapper.HyBlackListMapper;
import com.flash.toolbar.omp.model.HyBlackImportBatch;
import com.flash.toolbar.omp.model.HyBlackList;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class BlackListServiceImpl implements BlackListService{
	@Resource
	private HyBlackListMapper hyBlackListMapper;
	
	@Resource
	private HyBlackImportBatchMapper hyBlackImportBatchMapper;
	
	@Resource
	private DefaultSqlSessionFactory sqlSessionFactory;
	
	@Override
	public List<HyBlackListModel> getBlackListInfo(HyBlackListModel blackListModel) throws Exception{
		int totalRows = countByCondition(blackListModel);
		blackListModel.getPager().setTotalRowsCount(totalRows);
		blackListModel.getPager().doPage();
		int totalPageNum = blackListModel.getPager().getTotalPageNum();
		int pageIndex = blackListModel.getPageIndex();
		if(pageIndex>totalPageNum){
			blackListModel.setPageIndex(1);
			blackListModel.getPager().doPage();
		}
//		List<HyBlackListModel> listModel = new ArrayList<HyBlackListModel>();		
//		//将批次编号转换为批次Id查询
//		if(blackListModel!=null &&  !StringUtil.isNull(blackListModel.getBatchNo())){
//			HyBlackImportBatch hyBlackImportBatch = hyBlackImportBatchMapper.selectByBatchNo(blackListModel.getBatchNo());
//			if(hyBlackImportBatch!=null && !StringUtil.isNull(hyBlackImportBatch.getBatchId())){
//				blackListModel.getBean().setBatchId(hyBlackImportBatch.getBatchId());
//			}else{
//				return listModel;
//			}
//		}
		List<HyBlackListModel> list = hyBlackListMapper.selectByPageSelective(blackListModel);
//		if(list!=null && list.size()>0){
//			List<String> listStr = new ArrayList<String>(list.size());
//			for (HyBlackList hyBlackList : list) {
//				listStr.add(hyBlackList.getBatchId());
//			}
//			List<String> listBatchNo = hyBlackImportBatchMapper.selectByIdBatch(listStr);
//			if(listBatchNo!=null && listBatchNo.size()>0){
//				for (int i = 0; i < list.size(); i++) {
//					HyBlackListModel  hyBlackListModel = new HyBlackListModel();
//					hyBlackListModel.setBean(list.get(i));
//					hyBlackListModel.setBatchNo(listBatchNo.get(i));
//					listModel.add(hyBlackListModel);
//				}
//			}
//		}
		return list;
	}
	
	@Override
	public int countByCondition(HyBlackListModel blackListModel) throws Exception{
		return hyBlackListMapper.countByCondition(blackListModel);
	}
	
	@Override
	public void deleteBlackList(String[] bListIds,QxUserModel qxUserModel) throws Exception{
		hyBlackListMapper.deleteBlackListBatch(bListIds,qxUserModel);
	}
	
	@Override
	public void importBlackList(List<String> list,QxUserModel qxUserModel) throws Exception{
		final String uuid = RuleUtil.generateUUID();
		if(list==null || list.size()==0 || qxUserModel==null){
			return;
		}
		hyBlackListMapper.deleteExistMobileNo(list.toArray(new String[0]), qxUserModel);
		addBlackListImp(uuid,qxUserModel,list.size());
		addBlackListBatch(list,uuid,qxUserModel);
	}
	
	@Override
	public void importBlackList2(String uuid, List<String> list,QxUserModel qxUserModel) throws Exception{
		if(list==null || list.size()==0 || qxUserModel==null){
			return;
		}
//		hyBlackListMapper.deleteExistMobileNo(list.toArray(new String[0]), qxUserModel);
		addBlackListBatch2(list,uuid,qxUserModel);
	}
	
	private void addBlackListBatch(List<String> list,String uuid,QxUserModel qxUserModel) throws Exception{
		List<HyBlackList> newList = new ArrayList<HyBlackList>(list.size());
		for (int i = 0; i < list.size(); i++) {
			HyBlackList bean = new HyBlackList();
			String mobileNo = list.get(i);
			bean.setAddDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
			bean.setBatchId(uuid);
			bean.setbListId(RuleUtil.generateUUID());
			bean.setMobileNo(mobileNo);
			bean.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
			bean.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
			newList.add(bean);
		}
		hyBlackListMapper.insertBlackListBatch(newList);
	}
	
	private void addBlackListImp(String uuid,QxUserModel qxUserModel, int count) throws Exception{
		HyBlackImportBatch hyBlackImportBatch = new HyBlackImportBatch();
		hyBlackImportBatch.setBatchId(uuid);
		hyBlackImportBatch.setBatchNo(DateUtil.getTimeZoneStr(new Date(),qxUserModel.getTimeZone(),"yyyyMMdd")+"0");
		hyBlackImportBatch.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
		hyBlackImportBatch.setImportDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
		hyBlackImportBatch.setImportNum(count);
		hyBlackImportBatch.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
		hyBlackImportBatch.settOperatorName(StringUtil.getStr(qxUserModel.gettOperatorName()));
		hyBlackImportBatch.settOperatorNo(StringUtil.getStr(qxUserModel.gettOperatorNo()));
		hyBlackImportBatchMapper.insert(hyBlackImportBatch);
	}
	
	public void addBlackListImp2(String uuid,QxUserModel qxUserModel, int count) throws Exception{
		HyBlackImportBatch hyBlackImportBatch = new HyBlackImportBatch();
		hyBlackImportBatch.setBatchId(uuid);
		hyBlackImportBatch.setBatchNo(DateUtil.getTimeZoneStr(new Date(),qxUserModel.getTimeZone(),"yyyyMMdd")+"0");
		hyBlackImportBatch.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
		hyBlackImportBatch.setImportDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
		hyBlackImportBatch.setImportNum(count);
		hyBlackImportBatch.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
		hyBlackImportBatch.settOperatorName(StringUtil.getStr(qxUserModel.gettOperatorName()));
		hyBlackImportBatch.settOperatorNo(StringUtil.getStr(qxUserModel.gettOperatorNo()));
		hyBlackImportBatchMapper.insert(hyBlackImportBatch);
	}
	
	private void addBlackListBatch2(List<String> list,String uuid,QxUserModel qxUserModel) throws Exception{
		List<HyBlackList> newList = new ArrayList<HyBlackList>(list.size());
		for (int i = 0; i < list.size(); i++) {
			HyBlackList bean = new HyBlackList();
			String mobileNo = list.get(i);
			bean.setAddDate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
			bean.setBatchId(uuid);
			bean.setbListId(RuleUtil.generateUUID());
			bean.setMobileNo(mobileNo);
			bean.setCountryNo(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
			bean.settOperatorId(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
			newList.add(bean);
		}
		saveBatch(newList);
	}
	
    private void saveBatch(List<HyBlackList> data) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
//		DefaultSqlSessionFactory sqlSessionFactory=(DefaultSqlSessionFactory) ctx.getBean("sqlSessionFactory");  
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 100000;//每批commit的个数
            for(int index = 0; index < data.size();index++){
            	HyBlackList t = data.get(index);
            	if(batchSqlSession.getMapper(HyBlackListMapper.class).selectCountByMobileno(t.getMobileNo()) == 0){
            		batchSqlSession.getMapper(HyBlackListMapper.class).insert(t);
            	}
                if(index !=0 && index%batchCount == 0){
                    batchSqlSession.commit();
                }
            }
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
