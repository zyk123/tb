package com.flash.toolbar.omp.whiteList.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HyBlackListMapper;
import com.flash.toolbar.omp.mapper.HyWhiteImportBatchMapper;
import com.flash.toolbar.omp.mapper.HyWhiteListMapper;
import com.flash.toolbar.omp.model.HyBlackImportBatch;
import com.flash.toolbar.omp.model.HyBlackList;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteList;
import com.flash.toolbar.omp.user.bo.QxUserModel;
import com.flash.toolbar.omp.whiteList.service.HyWhiteListService;


@Service(value="hyWhiteListService")
public class HyWhiteListServiceImpl implements HyWhiteListService{
	
	@Autowired
	private HyWhiteListMapper mapper;
	
	@Autowired
	private HyWhiteImportBatchMapper batchMapper;
	
	@Resource
	private DefaultSqlSessionFactory sqlSessionFactory;
	
	/**
	 * 获取白名单信息列表
	 */
	public Map<String, Object> selectWhiteList(HyWhiteList hwl,Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hwl", hwl);
		//查询总记录数
		int count = mapper.selectCount(map);		
		page.setTotalCount(count);
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		map.put("page", page);
		if(0 < count){
			map.put("list", mapper.selectHyWhiteList(map));
		}
		return map;
	}
	/**
	 * 保存白名单信息
	 */
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean saveWhiteList(List<HyWhiteList> hwList,HyWhiteImportBatch hwib){
		mapper.delDuplicateHyWhiteList(hwList,hwib);
		int i = mapper.insertHyWhiteList(hwList); 
		int j = batchMapper.insertHyWhiteImportBatch(hwib);
		if(i>0&&j>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除指定白名单信息
	 */
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean delWhiteList(String[] ids,String countryNo,String tOperatorId){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("array", ids);
		map.put("countryno", countryNo);
		map.put("toperatorid", tOperatorId);
		int i = mapper.delHyWhiteList(map);
		if(i>0){
			return true;
		}
		return false;
	}
	
	public void importWhiteList2(String uuid, List<String> list,QxUserModel qxUserModel) throws Exception{
		if(list==null || list.size()==0 || qxUserModel==null){
			return;
		}
		addWhiteListBatch2(list,uuid,qxUserModel);
	}
	
	private void addWhiteListBatch2(List<String> list,String uuid,QxUserModel qxUserModel) throws Exception{
		List<HyWhiteList> newList = new ArrayList<HyWhiteList>(list.size());
		for (int i = 0; i < list.size(); i++) {
			HyWhiteList bean = new HyWhiteList();
			String mobileNo = list.get(i);
			bean.setAdddate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
			bean.setBatchid(uuid);
			bean.setWlistid(RuleUtil.generateUUID());
			bean.setMobileno(mobileNo);
			bean.setCountryno(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
			bean.setToperatorid(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
			newList.add(bean);
		}
		saveBatch(newList);
	}
	
	private void saveBatch(List<HyWhiteList> data) {
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//            int batchCount = 100000;//每批commit的个数
//            for(int index = 0; index < data.size();index++){
//            	HyWhiteList t = data.get(index);
//            	if(batchSqlSession.getMapper(HyWhiteListMapper.class).selectCountByMobileno(t.getMobileno()) == 0){
//            		batchSqlSession.getMapper(HyWhiteListMapper.class).insert(t);
//            	}
//                if(index !=0 && index%batchCount == 0){
//                    batchSqlSession.commit();
//                }
//            }
            batchSqlSession.getMapper(HyWhiteListMapper.class).insertBatch(data);
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
	
	public void addWhiteListImp2(String uuid,QxUserModel qxUserModel, int count) throws Exception{
		HyWhiteImportBatch hyWhiteImportBatch = new HyWhiteImportBatch();
		hyWhiteImportBatch.setBatchid(uuid);
		hyWhiteImportBatch.setBatchno(DateUtil.getTimeZoneStr(new Date(),qxUserModel.getTimeZone(),"yyyyMMdd")+"0");
		hyWhiteImportBatch.setCountryno(StringUtil.getStr(qxUserModel.getBean().getCountryno()));
		hyWhiteImportBatch.setImportdate(DateUtil.getTimeZoneDate(new Date(),qxUserModel.getTimeZone(),"yyyy-MM-dd"));
		hyWhiteImportBatch.setImportnum(count);
		hyWhiteImportBatch.setToperatorid(StringUtil.getStr(qxUserModel.getBean().getToperatorid()));
		hyWhiteImportBatch.setToperatorname(StringUtil.getStr(qxUserModel.gettOperatorName()));
		hyWhiteImportBatch.setToperatorno(StringUtil.getStr(qxUserModel.gettOperatorNo()));
		batchMapper.insertHyWhiteImportBatch(hyWhiteImportBatch);
	}
}
