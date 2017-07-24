<%@ include file="../../../toolbar/header.jsp" %>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview"  >
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/mobileSlide.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>static/js/jquery.touchSlider.js"></script>
    <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>
</head>
<body class=" bgc_p1 after_payfor"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="n_close" onclick="invoke_p('<%=basePath%>page/turnUsage?suid=${param.suid}','n_close')"></i><h2>Website</h2><i class="close_i"> </i></header>
<div class="page_container">
    <div class="page_screen banner ">
        <div class="main_visual">
            <section class="main_image">
                <ul class="" style="width: 1678px; overflow: visible; ">
<!--                     <li style="display: block;"><img id="t_iph" onclick="invoke_p('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li> -->
                    <li style=" display: block;"><img id="t_chris" onclick="invoke_p('<%=basePath%>page/turnPostLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li>
                    <li style=" display: block;"><img id="t_mobile" onclick="invoke_p('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li>
                </ul>
                <a href="javascript:;" id="btn_prev" style="width: 1678px; overflow: visible; display: inline;"></a>
                <a href="javascript:;" id="btn_next" style="width: 1678px; overflow: visible; display: inline;"></a>
            </section>
            <div class="flicking_con" style="margin-left: -40px;">
                <a href="#" class="">1</a>
                <a href="#" class="">2</a>
<!--                 <a href="#" class="">3</a> -->
            </div>
        </div>
    </div>
    <div class="brand_nav">
        <div class="brand_nav">
        <div class="navcontainer">
            <ul class="clearfixa">
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Lazada.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Lazada.png');"></i>
                        Lazada
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Maybank2u.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Maybank2u.png');"></i>
                        Maybank2u
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Mudah.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Mudah.png');"></i>
                        Mudah
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Cimbclicks.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/cimbclicks.png');"></i>
                        Cimbclicks
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Moretify.com')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Moretify.png');"></i>
                        Moretify
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Lowyat.net')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Lowyat.png');"></i>
                        Lowyat
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://11street.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/11street_97.png');"></i>
                        11street
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Paultan.org')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Paultan.png');"></i>
                        Paultan
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Lelong.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/lelong.png');"></i>
                        Lelong
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Airasia.com')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Airasia_47.png');"></i>
                        Airasia
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Thestar.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Thestar.png');"></i>
                        Thestar
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Malaysiakini.com')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Malaysiakini.png');"></i>
                        Malaysiakini
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Cari.com.my')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Cari_57.png');"></i>
                        Cari
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Tantannews.com')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/Tantannews.png');"></i>
                        Tantannews
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('https://google.com/')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/gougo.png');"></i>
                        Google
                    </a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('https://facebook.com/')" target="_blank">
                        <i class="i_icon" style="background-image: url('<%=basePath%>static/images/icon/navigation/facebook.png');"></i>
                        Facebook
                    </a>
                </li>
            </ul>
        </div>
        <div class="navcontainer2">
            <ul class="clearfixa">
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Pbebank.com')" target="_blank">Pbebank</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Jobstreet.com.my')" target="_blank">Jobstreet</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Mynewshub.cc')" target="_blank">Mynewshub</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Gjoyz.com')" target="_blank">Gjoyz</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Hermo.my')" target="_blank">Hermo</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Kshowonline.com')" target="_blank">Kshowonline</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Hmetro.com.my')" target="_blank">Hmetro</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Sinchew.com.my')" target="_blank">Sinchew</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://I3investor.com')" target="_blank">I3investor</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Subscene.com')" target="_blank">Subscene</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Sinarharian.com.my')" target="_blank">Sinarharian</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Utusan.com.my')" target="_blank">Utusan</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Malaysiaairlines.com')" target="_blank">Malaysiaairlines</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Freemalaysiatoday.com')" target="_blank">Freemalaysiatoday</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Bharian.com.my')" target="_blank">Bharian</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Shopee.com.my')" target="_blank">Shopee</a>
                </li>
                <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Airasiago.com.my')" target="_blank">Airasiago</a>
                </li>
                 <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Zalora.com.my')" target="_blank">Zalora</a>
                </li>
                 <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Mobile88.com')" target="_blank">Mobile88</a>
                </li>
                 <li class="brand_box">
                    <a class="website" href="javascript:invoke('http://Iflix.com')" target="_blank">Iflix</a>
                </li>
            </ul>
        </div>
    </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/pageEvent.js"></script>
<script>
	var baseUrl = '<%=basePath%>';
	function invoke(url){
		var id = url.substring(url.indexOf("//")+2,url.indexOf("."));
		recordClickEvent('${param.suid}','toolbar/postNavigation.jsp/'+id);
		parent.location.href=url;
	}
	function invoke_p(url,id){
		recordClickEvent('${param.suid}','toolbar/postNavigation.jsp/'+id);
		window.location.href=url;
	}
</script>
</html>