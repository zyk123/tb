<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <style>
        html, body {
            position: relative;
            height: 100%;
        }
        body {
            background: #eee;
            font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
            font-size: 14px;
            color:#000;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body class=" bgc_w after_payfor"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" style="visibility: hidden" id="goBackAccount" onclick="goBackAccount();"></i><h2>Help</h2><i class="close_i" onclick="parent.location.href=document.referrer"> </i></header>

<div class="fullscreen_container">
    <div class="page_about">
        <div class="package_detail">
            <p class="desc_text">The widget provides convenience for you to manage your prepaid account while browsing. You can perform simple account management features such as usage & internet quota query, credit & internet reload, internet plans subcription as well as add-ons purchase.</p>
            <p class="title">Usage Inquiry</p>
            <p class="desc_text">You can view your prepaid account information such as credit balance, internet quota & validity period on the <b>"Home"</b> page.</p>
        </div>
        <div class="package_detail">
            <p class="title">Subscribe</p>
            <p class="desc_text">You can subscribe to any internet plans or purchase any add-ons by clicking on the <b>"Subscribe"</b> icon on the navigation menu. Choose from any of the available options to proceed.</p>
        </div>
        <div class="package_detail">
            <p class="title">Reload</p>
            <p class="desc_text">You can perform credit or internet reload by clicking on the <b>"Reload"</b> icon on the navigation menu. Choose the amount you would like to reload from the available options.</p>
        </div>
        <div class="package_detail">
            <p class="title">Switch off</p>
            <p class="desc_text">You can manage the widget by clicking on the <b>"Setting"</b> icon on the navigation menu. Click on the <b>"Switch Off"</b> option. You may choose to deactivate the widget by choosing from the available switch off periods.</p>
        </div>
      <%--  <div class="package_detail">
            <p class="title">- Mobile phone recharge</p>
            <p class="desc_text">Click Reload in the Online Store page, you can recharge your own or other people's Celcom in advance</p>
        </div>--%>

    </div>
</div>

<!-- Swiper JS -->

<!-- Initialize Swiper -->
<script>
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/preHelp.jsp";
	
	recordVist('${suid}');

    var swiper = new Swiper('.swiper-container', {
        loop:false,
        grabCursor: true,
        pagination: '.swiper-pagination',
        paginationClickable: false,
        paginationBulletRender: function (index, className) {

            return '<span class="' + className + '"></span>';


        },
        onSlideChangeStart: function(){
            var activeIndex = swiper.activeIndex;

        },
        onTouchEnd:function(e){   /*手指滑动时，判断手指滑动的方向*/
            var pageCount = swiper.slides.length;
            gonext();
        }
    });

    function gonext(){
        var startX, startY, moveEndX, moveEndY, X, Y;
        $(".swiper-wrapper .swiper-slide:last").on("touchstart", function(e) {
            // e.preventDefault();
            startX = e.originalEvent.changedTouches[0].pageX,
                    startY = e.originalEvent.changedTouches[0].pageY;
        });
        $(".swiper-wrapper .swiper-slide:last").on("touchmove", function(e) {
            moveEndX = e.originalEvent.changedTouches[0].pageX,
                    moveEndY = e.originalEvent.changedTouches[0].pageY,
                    X = moveEndX - startX,
                    Y = moveEndY - startY;

            if ( Math.abs(X) > Math.abs(Y) && X < 0 ) {  //从右侧向左滑动
                if(document.referrer.indexOf("turnSetting") == -1){
					parent.location.href=document.referrer;
				  }else{			
					e.preventDefault();
				  }
            }

        });
    }
    
    function goBackAccount(){
    	recordClickEvent('${suid}','toolbar/preHelp.jsp/goBackAccount');	
    	javascript :history.back(-1);
    }

	function jump(){
		if(document.referrer.indexOf("turnSetting") == -1){
			parent.location.href=document.referrer;
		}else{			
			history.go(-1);
		}
	}

</script>
</body>
</html>