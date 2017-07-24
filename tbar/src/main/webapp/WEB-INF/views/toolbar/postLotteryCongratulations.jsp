<%@ include file="../../../toolbar/header.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<div class="page_container">
    <div class="page_screen banner">
        <img src="<%=basePath%>static/images/banners/blue_topconban.jpg" alt="">
    </div>
<form action="" id="rewardinfo">
<input type="hidden" name="receiverid" value="${receiverid}"/>
<input type="hidden" name="suid" value="${suid}"/>
<input type="hidden" name="name" value="${name}"/>
<input type="hidden" name="gender" value="${gender}"/>
<input type="hidden" name="id" value="${id}"/>
<input type="hidden" name="email" value="${email}"/>
<input type="hidden" name="address" value="${address}"/>
    <div class="tabinp_container">
        <div class="wrap100">
            <textarea class="reinpt" name="remark" data-inp="texta" type="text" placeholder="Traffic will be within 7 working days sent to your mobile phone. After we send the prize we will also send the Stand inside letter , please check."></textarea>
        </div>
        <div class="consubbtn_wrap">
            <button type="button" class="tab_subbtn fulls" id="rewardSub">Submit</button>
        </div>

    </div>
    </form>
</div>

<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/postLotteryCongratulations.jsp";
	
	recordVist('${suid}');
	
	    $(function(){
	    console.log(window);
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
// 							alert('sucess');console.log(window);
							console.log("<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847");
							window.location.href="<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847";     
						}else{
// 							alert(result.resultMsg);
                            $("#p1").empty().text(result.resultMsg);
							$("#aqmask").show();
							$("#tips_boxp").show();
						}
					},
					failure :function(){
// 						alert("etwork anomaly");
                            $("#p1").empty().text("etwork anomaly");
							$("#aqmask").show();
							$("#tips_boxp").show();	
					}
				});
				recordClickEvent('${suid}','toolbar/postLotteryCongratulations.jsp/rewardSub');			               
        });
    });
    
    function goBackNotice(){
    	recordClickEvent('${suid}','toolbar/postLotteryCongratulations.jsp/goBackLucky');
    	window.location.href="<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847&serviceType=${param.serviceType}";  		
    	//history.back();
    }
</script>
</body>

</html>
