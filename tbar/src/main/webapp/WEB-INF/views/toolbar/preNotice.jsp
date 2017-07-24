<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <script src="<%=basePath%>js/pageEvent.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class=" bgc_p1"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="jumpAccount" onclick="jumpAccount();"></i><h2>Notice</h2><i class="close_i"> </i></header>

<div class="page_container">
    <div class="notice_container">
        <div class="reinfos_box">
            <div class="title">Prize Delivery Information</div>
            <div class="cont address">
                <p class="p1">${receiverAddress }</p>
                <div class="btn_wrap">
                    <button class="btn_ov modify" id="modifyOrAdd" onclick="jumpRewardInfo()"><c:choose> <c:when test="${empty receiverid}">Add</c:when> <c:otherwise>Modify</c:otherwise> </c:choose></button>
                </div>
                <p class="tips">More information as the mailing address</p>
            </div>
        </div>
		<c:if test="${prizeListInfo!=null && fn:length(prizeListInfo) > 0 }">
		<c:forEach var="item" items="${prizeListInfo}" varStatus="status">
        <div class="reinfos_box">
            <div class="title">Campaign Name: ${item.promotionname }</div>
            <div class="cont award clearfixa">
                <div class="fl">
                    <div class="award_wrap">
                        <img src="<%=basePath%>${item.iconurl }" alt="">
                    </div>
                    <div class="award_post">
                        <label class="text title">${ item.shipstatus}</label>
                        <date  class="text date"> ${ item.shipdatestr}</date>
                    </div>

                </div>
                <div class="fr">
                    <div class="award_name">
                        <label class="name">${item.prizename }</label>
                    </div>
                    <p class="desc">${item.prizedesc } </p>
                </div>
            </div>
        </div>
        </c:forEach>
        </c:if>
    </div>
</div>

<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var isJumpCompetition = "${isJumpCompetition}";
	var browseUrl = "toolbar/preNotice.jsp";
	
	recordVist('${suid}');
	
	function jumpAccount(){
		recordClickEvent('${suid}','toolbar/preNotice.jsp/jumpAccount');
		if(isJumpCompetition=='1'){
			window.location.href='<%= basePath%>page/turnCompetition?suid=${suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848';
		}else if(isJumpCompetition=='2'){
			window.location.href='<%= basePath%>page/turnPreLucky?suid=${suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847';
		}else{
			window.location.href='<%= basePath%>page/turnPreAccount?suid=${suid}';
		}		
		
	}

	function jumpRewardInfo(){
		recordClickEvent('${suid}','toolbar/preNotice.jsp/modifyOrAdd');		
		window.location.href = "<%= basePath%>page/turnRewardInfo?suid=${suid}&receiverid=${receiverid}";
	}
</script>
</body>

</html>