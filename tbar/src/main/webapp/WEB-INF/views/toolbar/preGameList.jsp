<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<body class="bgc_p2 pt-08r pb-40p bgc_p1"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"> 
	<i id="g_close" onclick="invokeSelf('<%=basePath%>page/turnUsage?suid=${param.suid}','g_close')" class="icon_back"></i>
    <h2>Game</h2>
    <i class="close_i"> </i>
</header>

<div class="page_container">
    <div class="full_screen banner ">
        <div class="main_visual">
			<section class="main_image game">
				<ul class="" style="width: 1678px; overflow: visible; ">
					<li style="display: block;"><img id="t_iph" onclick="invokeSelf('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li>
					<li style=" display: block;"><img id="t_chris" onclick="invokeSelf('<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li>
					<li style=" display: block;"><img id="t_mobile" onclick="invokeSelf('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li>
				</ul>
				<a href="javascript:;" id="btn_prev" style="width: 1678px; overflow: visible; display: inline;"></a>
				<a href="javascript:;" id="btn_next" style="width: 1678px; overflow: visible; display: inline;"></a>
			</section>
			<div class="flicking_con" style="margin-left: -40px;">
				<a href="#" class="">1</a>
				<a href="#" class="">2</a>
				<a href="#" class="">3</a>
			</div>
		</div>
	</div>
    <div class="gamelist_cont">
        <ul class="game_items">
<!--             <li class="game_item" id="Hortensia" onclick="invoke('https://hortensia.onelink.me/3773887399?pid=huawei&af_web_dp=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.gv.hortensia','Hortensia')"> -->
<!-- 					    <span class="img_wrap icon"> -->
<!-- 					        <img src="<%=basePath%>static/images/common/HS.png" alt=""> -->
<!-- 					    </span> -->
<!--                 <div class="game_infos"> -->
<!--                     <div class="game_name"> -->
<!--                         <p>Hortensia Saga</p> -->
<!--                     </div> -->
<!--                     <div class="grades"> -->
<!--                         <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt=""> -->
<!--                         <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt=""> -->
<!--                         <span class="downcount">200,00</span> -->
<!--                     </div> -->
<!--                 </div> -->
<!--                 <span class="img_wrap down"> -->
<!-- 					<span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span> -->
<!-- 				</span> -->
<!--             </li> -->
            <li class="game_item" id="Tanks" onclick="invoke('https://lot.onelink.me/3039068955?pid=huawei&af_web_dp=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.gv.lot','Tanks')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/common/LOT.png" alt="">
					    </span>
                <div class="game_infos">
                    <div class="game_name">
                        <p>League of Tanks: 5v5 Competition</p>
                    </div>
                    <div class="grades">
                        <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                        <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                        <span class="downcount">200,00</span>
                    </div>
                </div>
                <span class="img_wrap down">
					<span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
				</span>
            </li>
            <li class="game_item" id="Heroes" onclick="invoke('https://roh.onelink.me/3801665260?pid=huawei&af_web_dp=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.gv.dfws','Heroes')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/common/ROH.png" alt="">
					    </span>
                <div class="game_infos">
                    <div class="game_name">
                        <p>Romance of Heroes:Realtime 3v3</p>
                    </div>
                    <div class="grades">
                        <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                        <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                        <span class="downcount">200,00</span>
                    </div>
                </div>
                <span class="img_wrap down">
					<span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
				</span>
            </li>
            <li class="game_item" id="War" onclick="invoke('https://3k.onelink.me/363535011?pid=huawei&af_web_dp=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.gv.sbdsg','War')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/common/3K.png" alt="">
					    </span>
                <div class="game_infos">
                    <div class="game_name">
                        <p>3K:Art of War</p>
                    </div>
                    <div class="grades">
                        <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                        <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                        <span class="downcount">200,00</span>
                    </div>
                </div>
                <span class="img_wrap down">
					<span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
				</span>
            </li>
        <c:choose>
        	<c:when test="${fn:contains(param.agent,'iPhone')}">
	        		<li class="game_item" id="Mobile" onclick="invoke('https://itunes.apple.com/app/id1160056295','Mobile')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/1.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Mobile Legends: Bang Bang</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Social" onclick="invoke('https://itunes.apple.com/app/id1138663064','Social')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/2.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Dream Life 3D Social Simulation Game</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Championship" onclick="invoke('https:itunes.apple.com/app/id1054859250','Championship')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/3.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Moto GP Racing Championship Quest</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Defense" onclick="invoke('https://itunes.apple.com/app/id1078573759','Defense')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/4.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Dream Defense</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Trap" onclick="invoke('https://itunes.apple.com/app/id1159611436','Trap')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/5.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Just Trap</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Traffic" onclick="invoke('https://itunes.apple.com/app/id1143534271','Traffic')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/6.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Traffic Tour</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Monster" onclick="invoke('https://itunes.apple.com/app/id1157869791','Monster')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/7.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Monster Park Let's Go</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Dominocity1" onclick="invoke('https://itunes.apple.com/app/id1135982876','Dominocity1')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/8.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Dominocity</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Slendrina" onclick="invoke('https://itunes.apple.com/app/id965312947','Slendrina')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/9.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Slendrina The Cellar</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Battle" onclick="invoke('https://itunes.apple.com/app/id1120455929','Battle')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/10.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Blaze of Battle</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Pokeland" onclick="invoke('https://itunes.apple.com/app/id1138244012','Pokeland')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/11.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Pokeland Legends</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Ski" onclick="invoke('https://itunes.apple.com/app/id1093819671','Ski')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/12.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Ski Three</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Colorful" onclick="invoke('https://itunes.apple.com/app/id543054341','Colorful')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/13.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Word Search Colorful</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Storm" onclick="invoke('https://itunes.apple.com/app/id1114450799','Storm')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/14.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Iron Storm</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Defender" onclick="invoke('https://itunes.apple.com/app/id1119510335','Defender')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/15.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>League of Defender</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Minecraft" onclick="invoke('https://itunes.apple.com/app/id1001286466','Minecraft')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/16.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Minecraft Story Mode</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Metromillenium" onclick="invoke('https://itunes.apple.com/app/id1079334134','Metromillenium')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/17.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Upin Ipin Demi Metromillenium</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Special" onclick="invoke('https://itunes.apple.com/app/id1158672336','Special')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/18.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Special Forces Group 2</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="War" onclick="invoke('https://itunes.apple.com/app/id981800298','War')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/19.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Ark of War</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
					<li class="game_item" id="Sushi" onclick="invoke('https://itunes.apple.com/app/id1125944043','Sushi')">
					    <span class="img_wrap icon">
					        <img src="<%=basePath%>static/images/ios/20.png" alt="">
					    </span>
					    <div class="game_infos">
					        <div class="game_name">
					            <p>Sushi Caf Story 2</p>
					        </div>
					        <div class="grades">
					            <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
					            <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
					            <span class="downcount">200,00</span>
					        </div>
					    </div>
					    <span class="img_wrap down">
					        <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
					    </span>
					</li>
        	</c:when>
        	<c:when test="${fn:contains(param.agent,'Android')}">
                    <li class="game_item" id="Simulation" onclick="invoke('https://play.google.com/store/apps/details?id=com.mxxcen.fun.sea','Simulation')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/1.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Dream Life 3D Social Simulation Game</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Search" onclick="invoke('https://play.google.com/store/apps/details?id=com.rjs.wordsearchgame','Search')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/2.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Word Search</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Racing" onclick="invoke('https://play.google.com/store/apps/details?id=com.magnat.extreme.trafficcar','Racing')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/3.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Real Drift Racing Road Racer</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Sweet" onclick="invoke('https://play.google.com/store/apps/details?id=com.fruitcandy.blast','Sweet')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/4.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Sweet Candy Story</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Tersembunyi" onclick="invoke('https://play.google.com/store/apps/details?id=com.siltree.mencari.benda.tersembunyi','Tersembunyi')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/5.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Cari Benda Tersembunyi</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Cat" onclick="invoke('https://play.google.com/store/apps/details?id=com.sharpshark.catvsdog2','Cat')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/6.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Cat vs Dog War</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="ShuHui" onclick="invoke('https://play.google.com/store/apps/details?id=jpark.AOS5','ShuHui')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/7.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Shu Hui Li</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Teka" onclick="invoke('https://play.google.com/store/apps/details?id=com.gagakhitam.janganlupalirik','Teka')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/8.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Teka Lirik Lagu Ini</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Moto" onclick="invoke('https://play.google.com/store/apps/details?id=com.weplay.motogp','Moto')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/9.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Moto GP Racing Championship Quest</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Multicraft" onclick="invoke('https://play.google.com/store/apps/details?id=mobi.MultiCraft','Multicraft')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/10.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Multicraft Free Miner</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Stonehenge" onclick="invoke('https://play.google.com/store/apps/details?id=com.dwsn.huadong.MY','Stonehenge')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/11.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Brick Secret of Stonehenge</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="World" onclick="invoke('https://play.google.com/store/apps/details?id=com.mrbean.around','World')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/12.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Mr Bean Around the World</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Dominocity" onclick="invoke('https://play.google.com/store/apps/details?id=com.nostopsign.dominocity','Dominocity')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/13.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Dominocity</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="BoBoiBoy" onclick="invoke('https://play.google.com/store/apps/details?id=com.alkemis.boboiboy','BoBoiBoy')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/14.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>BoBoiBoy Power Spheres</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Potion" onclick="invoke('https://play.google.com/store/apps/details?id=com.monstronauts.potionpunch','Potion')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/15.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Potion Punch</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Bubble" onclick="invoke('https://play.google.com/store/apps/details?id=bubble.shoot.bubbles.game.saga.world','Bubble')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/16.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Bubble Shooter</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Candy" onclick="invoke('https://play.google.com/store/apps/details?id=com.mobileguru.candyfever.free','Candy')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/17.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Candy Fever</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Cafeland" onclick="invoke('https://play.google.com/store/apps/details?id=com.gamegos.mobile.cafeland','Cafeland')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/18.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Cafeland</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Taxi" onclick="invoke('https://play.google.com/store/apps/details?id=com.baklabs.taxi.simulator','Taxi')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/19.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Taxi Game</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
                    <li class="game_item" id="Jewel" onclick="invoke('https://play.google.com/store/apps/details?id=com.differencetenderwhite.skirt','Jewel')">
                        <span class="img_wrap icon">
                            <img src="<%=basePath%>static/images/android/20.png" alt="">
                        </span>
                        <div class="game_infos">
                            <div class="game_name">
                                <p>Block Puzzle Jewel</p>
                            </div>
                            <div class="grades">
                                <img class="gradespic" src="<%=basePath%>static/images/icon/starGrade/grade4.png" alt="">
                                <img class="downicon" src="<%=basePath%>static/images/icon/downicon.png" alt="">
                                <span class="downcount">200,00</span>
                            </div>
                        </div>
                        <span class="img_wrap down">
                            <span><img src="<%=basePath%>static/images/icon/button/btnDownload.png" alt=""></span>
                        </span>
                    </li>
        	</c:when>
        	<c:otherwise>No game can be adapted</c:otherwise>
        </c:choose>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/pageEvent.js"></script>
<script>
	var baseUrl = '<%=basePath%>';
	function invoke(url,id){
		recordClickEvent('${param.suid}','toolbar/preGameList.jsp/'+id);
		parent.location.href=url;
	}
    function invokeSelf(url,id){
        recordClickEvent('${param.suid}','toolbar/preGameList.jsp/'+id);
        window.location.href=url;
    }
</script>
</html>