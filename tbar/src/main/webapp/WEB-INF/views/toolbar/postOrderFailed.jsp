<%@ include file="../../../toolbar/header.jsp" %>
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
<body class="bgc_w after_payfor"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="goBackSubscribe" onclick="goBackSubscribe();"></i><h2>Purchase Unsuccessful</h2><i class="close_i"> </i></header>
<div class="page_container">
<div class="inner" style="width:9rem;margin:0 auto;">
<div class="bg_wrap bgc_w1">
<div class="order_cont mb-1">
<!--     <div class="status_icon"> -->
<!--         <img src="../static/images/icon/FailPic.png" alt=""> -->
<!--     </div> -->
    <div class="order_text">
        <p class="status_text">OOPS!</p>
        <p class="order_p">${retMsg }</p>
    </div>
</div>
</div>
<div class="bg_wrap2 p-02 mt-02">
 <a class="btn_ov btn_full c_ora mt-08r" onclick="backToSubscribe()" id="backToSubscribe">BACK TO SUBSCRIBE</a>
    <a class="btn_ov btn_full c_ora mt-06r" href="javascript:void(0);" id="buyAgain">TRY AGAIN </a>
</div>
<div class="mask_layout" style="display: none;">
<!--     <div class="setting_close confrim"> -->
<!--         <div class="close_top">Confirm</div> -->
<!--         <ul class="setting_ul"> -->
<!--             <li class="setting_item" style="border:none;">Are you sure ?</li> -->
<!--         </ul> -->
<!--         <div class="btn_inline_block"> -->
<!--             <button class="btn_ov btn_mid cancel" id="cancel">Cancel</button>  <button class="btn_ov btn_mid ok2" id="sure">Yes</button> -->
<!--         </div> -->
<!--     </div> -->
	<img src="<%=basePath %>static/images/icon/load.gif" style=" position:absolute; top:50%;left:50%; margin-top:-22px;margin-left:-22px;" id="waitDiv"/>    
</div>
</div>
</div>

<script type="text/javascript">
		var baseUrl = "<%=basePath%>";
		var browseUrl = "toolbar/preOrderFailed.jsp";
		recordVist('${suid}');
		
		function goBackSubscribe(){
			recordClickEvent('${suid}','toolbar/preOrderFailed.jsp/goBackSubscribe');
			javascript :history.back();
		}
		
		function backToSubscribe(){
			recordClickEvent('${suid}','toolbar/preOrderFailed.jsp/backToSubscribe');
			javascript:jumpStore();
		}		
		
		function jumpStore(){
			history.go(-2);
		}
		
		function buyAgain(){
// 				$(".confrim").hide();
// 				$("#waitDiv").show();		
// 				$(".mask_layout #sure").off("click");
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
							window.location.replace("<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg=''");
						}
// 							var result = $.parseJSON(data.result);
						var result = data.result;
						if(result=='' || result==null){
							window.location.replace("<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg=''");
						}
							var resultCode = result.resultCode;
							var resultMsg= result.resultMsg;
							var retCode = result.body.retCode;
							var orderStatus = result.body.orderStatus;
							var retMsg = result.body.retMsg;
								if(retMsg == ""){
									retMsg = "The network is currently busy, Please try again later.";
								}							
							var expireDate = result.body.expireDate;
							if(resultCode=="0" && orderStatus=="1" ){
								window.location.replace("<%=basePath%>page/turnOrderSuccess?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&expireDate="+expireDate);
							}else{
								window.location.replace("<%=basePath%>page/turnOrderFail?suid=${suid}&fgno=${packageBean.fgno}&fgname=${packageBean.fgname}&expenses=${packageBean.expenses}&fgtips=${packageBean.fgtips}&retMsg="+retMsg);
							}
// 							$(".mask_layout").hide();
					},
					failure : function(){
						alert("etwork anomaly");
					},
					complete : function(){
// 						$("#waitDiv").hide();
// 						$(".confrim").show();					
						$(".mask_layout").slideUp();
// 				        $("#sure").on("click",function(){
// 				             buyAgain();
// 				        });						
					}
				});				
		}
		
		
	    $(function () {
	        $("#buyAgain").on("click",function () {
	            $(".mask_layout").slideDown();
	            buyAgain();
	            recordClickEvent('${suid}','toolbar/preOrderFailed.jsp/buyAgain');
	        });
	
	        $("#cancel").on("click",function(){
	            $(".mask_layout").slideUp();
	        });
	        
	        $("#sure").on("click",function(){
	             buyAgain();
	        });        
	        $(".setting_close").on("click",function(e){
	            e.stopPropagation();
	        });
	    });					
</script>
</body>

</html>
