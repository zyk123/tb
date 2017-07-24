<%@ include file="../../../toolbar/header.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
    <script src="<%=basePath%>js/pageEvent.js"></script>
</head>
<body class=" bgc_p1"  data-ctrl-name="pageview" style="background-color: #f5da88;" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="jumpCompetition" onclick="jumpCompetition();"></i><h2>Game rules</h2><i class="close_i"> </i></header>

<div class="page_container">
    <div class="game_rules" style="font-size: 0.4rem;width: 8rem;margin-left: 1rem;">
        <h3 style="text-align: center;">Game Rules</h3>
<!--         <p>1、 User click “Catch The Phone” button at the page, entered the game page.</p> -->
        <p>1、 Click "Start", phone will come out of the hole and hide back in.  Catch the phone as much as you can within 30 seconds. </p>
        <p>2、 You can play the game as many times as you like.</p>
        <p>3、 Higher score, higher chance to win a prize.</p>
        <p>4、 At the end of the game, the score will be displayed.</p>
        <p>5、 Click on "My Winning Records" to check the prize you won.</p>
        <p>6、Celcom reserves the right on the final decision. </p>
    </div>
</div>



<script type="text/javascript">
		var baseUrl = "<%=basePath%>";
		var browseUrl = "toolbar/preGameRule.jsp";
		recordVist('${suid}');
	
	function jumpCompetition(){
		recordClickEvent('${suid}','toolbar/preGameRule.jsp/jumpCompetition');
		window.location.href = "<%=basePath%>page/turnCompetition?suid=${suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848";
	}
</script>
</body>

</html>
