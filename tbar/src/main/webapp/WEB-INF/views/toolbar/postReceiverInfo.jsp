<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview"  >
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <link rel="stylesheet" href="<%=path %>/jQuery-Validation-Engine/css/validationEngine.jquery.css">
	<script src="<%=path %>/jQuery-Validation-Engine/js/languages/jquery.validationEngine-en.js"></script>
	<script src="<%=path %>/jQuery-Validation-Engine/js/jquery.validationEngine.min.js"></script>    
	<script src="<%=basePath%>js/pageEvent.js"></script>    
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class=" bgc_p2 after_payfor"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="goBackNotice" onclick="goBackNotice();"></i><h2>Reward Information</h2><i class="close_i"> </i></header>

<!-- <div class="page_container reward_infos"> -->
<div class="page_container">
    <div class="account_profile">
        <img src="<%=basePath%>static/images/icon/defaultPfile.png" alt="">
        <p class="phonenum">${phoneNum }</p>
    </div>

	<form action="" id="rewardinfo">
    <div class="tabinp_container">
        <ul>
            <li>
                <label class="">Name <span class="red_point">*</span></label>
                <input class="reinpt" name="name" value="${name }" type="text" data-validation-engine=="validate[required,maxSize[20]]">
            </li>
            <li>
                <label class="">Gender <span class="red_point">*</span></label>
                <select class="reinpt" name="gender"  ata-validation-engine=="validate[required]">
                    <option value="0" <c:if test="${gender=='0' }">selected=selected</c:if>>Male</option>
                    <option value="1" <c:if test="${gender=='1' }">selected=selected</c:if>>Female</option>
                </select>                
            </li>
            <li>
                <label class="">NRIC or Passport Number <span class="red_point">*</span></label>
                <input class="reinpt" name="id" value="${id }" type="text" data-validation-engine=="validate[required,custom[onlyLetterNumber]]">
            </li>
            <li>
                <label class="">Email <span class="red_point">*</span></label>
                <input class="reinpt" name="email" value="${email }" type="text" data-validation-engine=="validate[required,custom[email]]">
            </li>
            <li>
                <label class="">Address <span class="red_point">*</span></label>
                <input class="reinpt" name="address" value="${address }" type="text" data-validation-engine=="validate[required,maxSize[450]]">
            </li>
            <input type="hidden" name="suid" value="${suid }" />
            <input type="hidden" name="receiverid" value="${receiverid }" />
            <li>
                <button type="button" class="tab_subbtn fulls" id="rewardSub">Submit</button>
            </li>
        </ul>
    </div>
    </form>
</div>
<div class="mask_layout"  id="aqmask" style="display: none;">
        <div class="tips_box" data-aqbox="p4" id="tips_boxp" style="display: none;height: 4rem;" >
            <div class="title">
                 <i class="iclose" id="iclose"></i>
            </div>
            <div class="cont" style="height: 1rem;">
                <p class="p1" id="p1"></p>
<!--                 <p class="p2"> you did not win this round,</p> -->
<!--                 <p class="p2"> please try again.</p> -->
            </div>
            <div class="bottom">
                <button class="btn_ov btn_full" id="oopsConfirm">OK</button>
            </div>
            <div class="error_tips"></div>
        </div> 
</div>
<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/postReceiverInfo.jsp";
	
	recordVist('${suid}');
	
	    $(function(){
	    
	    $(".iclose").on("click",function () {
	        $("#aqmask").hide();
	        $(this).parents(".tips_box").hide();
	    });	 	    
	    
	    $("#oopsConfirm").on("click",function () {
	        $("#aqmask").hide();
	        $(this).parents(".tips_box").hide();
	        window.location.href="<%= basePath%>page/turnNotice?suid=${suid}";     
	    });	    	    
	    
		$('#rewardinfo').validationEngine('attach',{
			promptPosition : 'topLeft',
			showOneMessage : true
		});	    
    
        $("#rewardSub").on("click",function () {
			var flag = $('#rewardinfo').validationEngine('validate');
			if(!flag){
				return;
			}
				$.ajax({
					type: "post",
					async: false,
					url: '<%=basePath%>portal/saveReceiverInfo',
					data: $("#rewardinfo").serialize(),
					dataType: "jsonp",
					jsonp: "jsonpCallback",
					success : function(data) {
						var result = eval( "(" + data.result + ")" );
						if('0'==result.resultCode){
// 							alert('sucess');
							$("#p1").empty().text("Thank you for your submission");
							$("#aqmask").show();
							$("#tips_boxp").show();
						}else{
							$("#p1").empty().text(result.resultMsg);
							$("#aqmask").show();
							$("#tips_boxp").show();
						}
					},
					failure :function(){
							$("#p1").empty().text("etwork anomaly");
							$("#aqmask").show();
							$("#tips_boxp").show();	
					}
				});
				recordClickEvent('${suid}','toolbar/postReceiverInfo.jsp/rewardSub');			               
        });
    });
    
    function goBackNotice(){
    	recordClickEvent('${suid}','toolbar/postReceiverInfo.jsp/goBackNotice');		
    	history.back();
    }
</script>
</body>

</html>
