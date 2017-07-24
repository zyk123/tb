<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<header class="usage fixed-top tb_header"><i class="icon_ac" style="visibility: hidden;"></i><h2>Promotion</h2><i class="close_i"> </i></header>

<div class="page_container">
    <div class="promotion_container ">
        <ul>
        <c:if test="${list!=null && fn:length(list)>0 }">
        <c:forEach var="item" items="${list}" varStatus="status">
            <li>
                <div class="page_screen banner ">
                   	<img src="<%=basePath%>${item.bannerUrl}" alt="" id="turnPreLucky" onclick="goPreLucky('${item.promotionId}')">
<!--                     <div class="promotion_infos"> -->
<!--                         <div class="fl"> -->
<!--                             <i class="icon clock_i" style=""></i><span class="promo_text promo_date">${item.dateDesc }</span> -->
<!--                         </div> -->
<!--                         <div class="fr"> -->
<!--                             <i class="icon person_i"></i><span class="promo_text promo_counts">${item.activeNum }</span> -->
<!--                         </div> -->

<!--                     </div> -->
                </div>
            </li>
        </c:forEach>
		</c:if>
        </ul>
    </div>
</div>

<script type="text/javascript">
		var baseUrl = "<%=basePath%>";
		var browseUrl = "toolbar/prePromotion.jsp";
		recordVist('${suid}');
		
		function goPreLucky(promotionId){
			recordClickEvent('${suid}','toolbar/prePromotion.jsp/turnPreLucky');	
			if(promotionId=='6c6c3b249e3d4577bd27940e5e3cb847'){
				window.location.href = "<%=basePath%>page/turnPreLucky?suid=${suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847";
			}else{
				window.location.href = "<%=basePath%>page/turnCompetition?suid=${suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848";
			}
		}
</script>
</body>

</html>
