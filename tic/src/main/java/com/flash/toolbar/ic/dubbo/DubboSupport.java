package com.flash.toolbar.ic.dubbo;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.flash.toolbar.facade.LotteryFileManager;
import com.flash.toolbar.facade.MemberManager;
import com.flash.toolbar.facade.PackageListManager;
import com.flash.toolbar.facade.PromotionManager;
import com.flash.toolbar.facade.ReloadManager;
import com.flash.toolbar.facade.ToolbarPortalManager;

@Component
public class DubboSupport {
	//dubbo注解
	
	@Reference
	protected LotteryFileManager lotteryFileManager;
	
	@Reference
	protected MemberManager memberManager;
	
	@Reference
	protected ToolbarPortalManager toolbarPortalManager;
	
	@Reference
	protected PackageListManager packageListManager;
	
	@Reference
	protected PromotionManager promotionManager;
	
	@Reference
	protected ReloadManager reloadManager;
	
	public ReloadManager getReloadManager() {
		return reloadManager;
	}

	public PackageListManager getPackageListManager() {
		return packageListManager;
	}

	public LotteryFileManager getLotteryFileManager(){
		return this.lotteryFileManager;
	}
	
	public MemberManager getMemberManager(){
		return this.memberManager;
	}
	
	public ToolbarPortalManager getToolbarPortalManager() {
		return toolbarPortalManager;
	}
	
	public PromotionManager getPromotionManager(){
		return promotionManager;
	}
}
