<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en" class="h_full">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>js/pageEvent.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class=" bgc_p3"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="goBackStore" onclick="goBackStore();"></i><h2>Purchase Plans</h2><i class="close_i"> </i></header>


<div class="page_container bgc_p1">
<div class="bg_wrap">
<c:choose> 

  <c:when test="${fn:indexOf(packageBean.ptname, 'Add-On')!='-1'}">   

		<div class="package_cont mtb-06rem"> 
			<c:if test="${ packageBean.fgname!=''}">
		    <div class="package_title"><p>${fn:toUpperCase(packageBean.fgname) }</p></div>
		    </c:if>
			<div class="order_cont mb-03">
			<!--     <div class="status_icon"> -->
			<!--         <img src="../static/images/icon/SuccessPic.png" alt=""> -->
			<!--     </div> -->
				<c:forEach items="${details}" var="item">
					<c:if test="${item.parentId != '-1'}">
					    <div class="order_text">
					        <p class="order_p">You are purchasing:</p>
					        <p class="order_p2">Internet Add-On ${item.itemValue } for ${item.itemName } </p><br/>
					        <p class="order_p2">${item.itemValue } will be deducted from your</p>
					        <p class="order_p2">number below:</p>
					        <p class="order_p"> ${phoneNum }</p>
					        <!-- <p class="order_p3">New Internet balance: <strong>${packageBean.expenses }</strong></p> -->
					    </div>										
					</c:if>
				</c:forEach>
			</div>
		</div>

  </c:when> 



  <c:otherwise>   

	<div class="package_cont mtb-06rem">
			<c:if test="${ packageBean.fgname!=''}">
		    <div class="package_title"><p>${fn:toUpperCase(packageBean.fgname) }</p></div>
		    </c:if>	    
	    
        <c:forEach items="${details}" var="item" varStatus="status">
        	<c:if test="${item.parentId == '-1'}">
	        <ul class="packages_ul">
	        </c:if>
        	<c:if test="${item.parentId == '-1' && status.index!='0'}">
	        <ul class="packages_ul bord-t5">
	        </c:if>	        
        		<c:if test="${item.parentId == '-1'}">
        			<li class="package_name">${item.detailName }</li>
        		</c:if>
        		<c:forEach items="${details}" var="item1">
        		
        			<c:if test="${(item1.parentId != '-1') && (item1.parentId==item.packageDetailId)}">
			        <li class="package_text">
			            <div class="fl"><p>${item1.itemName}</p></div>
			            <div class="fr"><p>${item1.itemValue}</p></div>
			        </li>  
			        </c:if>        			
        		</c:forEach>
        	<c:if test="${item.parentId == '-1'}">	
	        </ul>        	
	        </c:if>
        </c:forEach>
	    
	
	</div>

  </c:otherwise> 

</c:choose> 

</div>

<c:if test="${isShowbuy != 'Y'}">
<div class="mask_layout" <c:if test='${!flag}'>style="display: none;"</c:if>>
<!--     <div class="setting_close confrim"> -->
<!--         <div class="close_top">Confirm</div> -->
<!--         <ul class="setting_ul"> -->
<!--             <li class="setting_item" style="border:none;">Are you sure ?</li> -->
<!--         </ul> -->
<!--         <div class="btn_inline_block"> -->
<!--             <button class="btn_ov btn_mid cancel" id="cancel">Cancel</button>  <button class="btn_ov btn_mid ok2" id="sure1">Yes</button> -->
<!--         </div> -->

<!--     </div> -->
	<c:if test="${flag}">
	    <div class="setting_close confrim">
	        <div class="close_top">Purchase Unsuccessful</div>
			<div class="order_cont mb-03">
			<!--     <div class="status_icon"> -->
			<!--         <img src="../static/images/icon/SuccessPic.png" alt=""> -->
			<!--     </div> -->
			    <div class="order_text">
			    	<p class="status_text">OOPS!</p>
			        <p class="order_p">You do not have enough credit to purchase</p>
			        <p class="order_p2">${packageBean.fgtips } For</p>
			        <p class="order_p"> ${phoneNum }</p>
			        <!-- <p class="order_p3">New Internet balance: <strong>${packageBean.expenses }</strong></p> -->
			    </div>
			</div>
	        <div class="btn_inline_block" style="overflow:hidden;">
	            <button class="btn_ov btn_mid ok2" id="sureKnow" style="margin:0 auto; float:none;">Close</button>
	        </div>
	
	    </div>
    </c:if>

    <img src="<%=basePath %>static/images/icon/load.gif" style="<c:if test='${flag}'>display: none;</c:if> position:absolute; top:50%;left:50%; margin-top:-22px;margin-left:-22px;" id="waitDiv"/>
</div>
</c:if>
</div>


	<c:if test="${isShowbuy != 'Y'}">
<div class="package_btn_wrap" style="margin-bottom:2rem;">
    <buttton class="btn_ov btn_full ok2 <c:if test='${flag }'>disa</c:if>" <c:if test='${!flag }'>id="buyNow"</c:if> >Buy Now</buttton>
</div>
</c:if>
</div>



<script type="text/javascript">
		var baseUrl = "<%=basePath%>";
		var browseUrl = "toolbar/prePackageDetailOrder.jsp";
		recordVist('${suid}');
		
		function goBackStore(){
			recordClickEvent('${suid}','toolbar/prePackageDetailOrder.jsp/goBackStore');		
			javascript :history.back();
		}
		function buy(){
// 					$(".confrim").hide();
// 					$("#waitDiv").show();
// 					$(".mask_layout #sure1").off("click");
				$.ajax({
					type: "post",
					async: true,
					url:  '<%=basePath%>package/subScribePackage',
					data: {
						'fGNo':'${packageBean.fgno}',
						'suid':'${suid}'
					},
					dataType: "json",
					timeout : 15000,
// 					jsonp: "jsonpCallback",
					success : function(data) {
							if(data=='' || data==null){
								window.location.href = "<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg=''";
							}
	// 						var result = $.parseJSON(data.result);
							var result = data.result;
							if(result=='' || result==null){
								window.location.href = "<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg=''";
							}							
								var resultCode = result.resultCode;
								var resultMsg= result.resultMsg;
								var retCode = result.body.retCode;
								var retMsg = result.body.retMsg;
								if(retMsg == ""){
									retMsg = "The network is currently busy, Please try again later.";
								}
								var expireDate = result.body.expireDate;
								var orderStatus = result.body.orderStatus;
								if(resultCode=="0" && orderStatus=="1"){
									window.location.href = "<%=basePath%>page/turnOrderSuccess?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&expireDate="+expireDate;	
								}else{
									window.location.href = "<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg="+retMsg;
								}
								
						},
						failure : function(){
							alert("network anomaly");
						},
						complete : function(){
// 							$("#waitDiv").hide();
// 							$(".confrim").show();
							$(".mask_layout").slideUp();
// 					        $("#sure1").on("click",function(){
// 					             buy();
// 					        }); 				
						}
					});				
		}
		
    $(function () {
    
    	
        $("#buyNow").on("click",function () {
            $(".mask_layout").slideDown();
            buy();
            recordClickEvent('${suid}','toolbar/prePackageDetailOrder.jsp/buyNow');
        });

        $("#cancel").on("click",function(){
            $(".mask_layout").slideUp();
        });
        
        $("#sureKnow").on("click",function(){
            $(".mask_layout").slideUp();
        });
        
        $("#sure1").on("click",function(){
             buy();
        });        
        $(".setting_close").on("click",function(e){
            e.stopPropagation();
        });
    });		
</script>
</body>

</html>