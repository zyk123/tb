package com.flash.toolbar.omp.packagesub.service;

import java.util.List;

import com.flash.toolbar.omp.model.TcFlowSub;
import com.flash.toolbar.omp.packagesub.bo.TcFlowSubModel;

/**
 * 套餐订单管理业务接口
 * @author zhulin
 */
public interface PackageSubService {

	List<TcFlowSub> getPackageSubInfo(TcFlowSubModel tcFlowSubModel);

}
