<%@ include file="../../../toolbar/header.jsp" %>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview" class="fullscreen" >
<head>
    <meta charset="UTF-8">

    <title>usage</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/mobileSlide.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>static/js/jquery.touchSlider.js"></script>
    <style type="text/css">
    		.full_top_r .bottom>p{
    			margin:.2rem 0;
    		}
    </style>
<!--     a/F>540&&(a=540*F); -->
    <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>
</head>
<body class=" pt-08r fullscreen  after_payfor"  data-ctrl-name="pageview" >

<a href=""></a><img class="close" onclick="jump()" style=" position: absolute;right: 0; width: 20%;" src="<%=basePath%>/static/images/icon/xguide.png" alt="">

<div class="half_screen" style="height: 5.3rem; overflow: hidden;">
<!--     <div class="adbox_top"> -->
<!--         <img src="<%=basePath%>static/images/imgs/advertising_xpax.png" alt=""> -->
<!--     </div> -->

    <div class="sobody">
 <div class="brand_wrap">
            <img class="brand_pic" src="<%=basePath%>static/images/imgs/Group-4_01.png" alt="">
        </div>    
        <div class="full_top_infos empty  clearfixa">
            <div class="full_top_l">
                <div class="left_ball">
                    <img class="flow_ball" src="<%=basePath%>static/images/tbBall/ballOutput/10.png" alt="">
                    <div class="balance_tips">
                        <p class="text t1">Internet Available(GB)</p>
                        <p style="display:block;color:#fb2e5e;" class="query_msg">Querying...</p>
                        <p id="remainedData" class="text t2"></p>
                        <a id="add_on" href="javascript:invoke('<%=basePath%>page/turnStore?suid=<%=request.getParameter("suid")%>&serviceType=${param.serviceType}','add_on')"></a>
                        <!--<img src="../../static/images/icon/button/ButtonAdd-onNow.png" alt="">-->
                    </div>
                  <%--   <img class="ball_shadows" src="<%=basePath%>static/images/tbBall/ballOutput/ballshadows.png" alt=""> --%>
                </div>
            </div>

            <div class="full_top_r">
<!--                 <div class=" top"> -->
<!--                 	<p class="text t2" style="margin-top: .4rem;">Credit Balance</p> -->
<!--                     <p class="text t1" ><span class="query_msg2" style="display:block;color:#fb2e5e;font-size: 20px;">Querying...</span><span id="balance"></span></p> -->
<!--                     <p id="expiry" class="text t3"></p> -->
<!--                 </div> -->
                <div class="bottom single">
                	<p class="query_msg" style="color:#fb2e5e;font-size: 20px;">Querying...</p>
                	<p class="text t2">Internet Balance</p>
                    <p class="text t1"><span id="usedData"></span>/<span id="totalData" class="totalData"></span></p>
                    <p id="expiry2" class="text t3"></p>
                     <p id="lastOn" class="full_top_tips" style="color:#a588bf;"></p>
                </div>
            </div>
<!--             <div class="customer_nav"> -->
<!--                 <div class="nav-container "> -->
<!--                     <ul class="nav-ul"> -->
<!--                         <li class="brand"> -->
<!--                         <a id="t_game" style="display:inline-block;" href="javascript:goto('<%=basePath%>page/turnGameList?prefix=pre&suid=${param.suid}&serviceType=${param.serviceType}','t_game')"> -->
<!-- 		                    <span class="bar-img"> -->
<!-- 		                       <img src="<%=basePath%>static/images/icon/brand2/logo1.png" alt=""> -->
<!-- 		                    </span> -->
<!--                             <p class="text t1">Game</p> -->
<!--                         </a> -->
<!--                         </li> -->
<!--                         <li class="brand"> -->
<!--                         <a href="javascript:void(0);"> -->
<!-- 		                    <span class="bar-img"> -->
<!-- 		                       <img src="<%=basePath%>static/images/icon/brand2/logo3.png" alt=""> -->
<!-- 		                    </span> -->
<!--                             <p class="text t1">Celcom</p> -->
<!--                         </a> -->
<!--                         </li> -->
<!--                         <li class="brand"> -->
<!--                         <a id="t_website" style="display:inline-block;" href="javascript:invoke('<%=basePath%>page/turnNavigation?prefix=pre&suid=${param.suid}&serviceType=${param.serviceType}','t_website')"> -->
<!-- 		                    <span class="bar-img"> -->
<!-- 		                       <img src="<%=basePath%>static/images/icon/brand2/logo2.png" alt=""> -->
<!-- 		                    </span> -->
<!--                             <p class="text t1">Website</p> -->
<!--                         </a> -->
<!--                         </li> -->
<!--                         <li class="brand"> -->
<!--                         <a href="javascript:void(0);"> -->
<!-- 		                    <span class="bar-img"> -->
<!-- 		                       <img src="<%=basePath%>static/images/icon/brand2/logo4.png" alt=""> -->
<!-- 		                    </span> -->
<!--                             <p class="text t1">Celcom</p> -->
<!--                         </a> -->
<!--                         </li> -->
<!--                     </ul> -->
<!--                 </div> -->
<!--             </div> -->
           
        </div>
    </div>


<!--     <div class="full_screen banner "> -->
<!--             <div class="main_visual"> -->
<!--                 <section class="main_image"> -->
<!--                     <ul class="" style="width: 1678px; overflow: visible; "> -->
<!--                         <li style="display: block; top: 0px;"><img id="t_iph" onclick="invoke('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li> -->
<!--                         <li style=" display: block;top: 0px;"><img id="t_chris" onclick="invoke('<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li> -->
<!--                         <li style=" display: block;top: 0px;"><img id="t_mobile" onclick="invoke('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li> -->
<!--                     </ul> -->
<!--                     <a href="javascript:;" id="btn_prev" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--                     <a href="javascript:;" id="btn_next" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--                 </section> -->
<!--                 <div class="flicking_con" style="margin-left: -40px;"> -->
<!--                     <a href="#" class="">1</a> -->
<!--                     <a href="#" class="">2</a> -->
<!--                     <a href="#" class="">3</a> -->
<!--                 </div> -->
<!--         </div> -->
<!--     </div> -->

<!--     <footer class="floor_nav floor" id="floor_nav" style="display: block;" > -->
        <!--<i class="bg_line"></i>-->
<!--         <div class="floor-container"> -->
<!--             <ul class="nav-ul"> -->
<!--                 <li class="brand"> -->
<!--                     <span class="bar-img left"> -->
<!--                        <a href="javascript:void(0);"><img src="<%=basePath%>static/images/icon/nav/x04.png" alt=""></a> -->
<!--                     </span> -->
<!--                     <span class="right"> -->
<!--                         <p class="text t1">Subscribe</p> -->
<!--                     </span> -->
<!--                 </li> -->
<!--                 <li class="brand"> -->
<!--                     <span class="bar-img left"> -->
<!--                        <a href="javascript:void(0);"><img src="<%=basePath%>static/images/icon/nav/x03.png" alt=""></a> -->
<!--                     </span> -->
<!--                     <span class="right"> -->
<!--                         <p class="text t1">Reload</p> -->
<!--                     </span> -->
<!--                 </li> -->
<!--                 <li class="brand"> -->
<!--                     <span class="bar-img left"> -->
<!--                        <a href="javascript:void(0);"><img src="<%=basePath%>static/images/icon/nav/x02.png" alt=""></a> -->
<!--                     </span> -->
<!--                     <span class="right"> -->
<!--                         <p class="text t1">Promotion</p> -->
<!--                     </span> -->
<!--                 </li> -->
<!--                 <li class="brand"> -->
<!--                     <span class="bar-img left"> -->
<!--                       <a href="javascript:void(0);"><img src="<%=basePath%>static/images/icon/nav/x01.png" alt=""></a> -->
<!--                     </span> -->
<!--                     <span class="right"> -->
<!--                         <p class="text t1">Account</p> -->
<!--                     </span> -->
<!--                 </li> -->
<!--             </ul> -->
<!--         </div> -->
<!--     </footer> -->
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/pageEvent.js"></script>
<script type="text/javascript">

    var baseUrl = "<%=basePath%>";
    var browseUrl = "toolbar/postHome.jsp";
    recordVist('${suid}');


    var len = $("#floor_nav .floor-container ul li").size();
	$("#floor_nav .floor-container ul li").css({"width":(100/len)+"%"});

	var baseUrl = '<%=basePath%>';
	function invoke(url,id){
		recordClickEvent('${param.suid}','toolbar/postHome.jsp/'+id);
		window.location.href=url;
	}
	function goto(url,id){
		recordClickEvent('${param.suid}','toolbar/postHome.jsp/'+id);
   		window.location.href=url + "&agent=" + navigator.userAgent;
   	}
	function ball_wave(per) {
        $(".flow_ball").attr("src",'<%=basePath%>static/images/tbBall/ballOutput/apf/' + per + '.png');
    }
    
    
	var changeTwoDecimal_f= function (floatvar){ 
	    var f_x = parseFloat(floatvar); 
	    if (isNaN(f_x)){ 
	        return '0.00'; 
	    } 
	    var f_x = Math.round(f_x*100)/100; 
	    var s_x = f_x.toString(); 
	    var pos_decimal = s_x.indexOf('.'); 
	    if (pos_decimal < 0){ 
	        pos_decimal = s_x.length; 
	        s_x += '.'; 
	    } 
	    while (s_x.length <= pos_decimal + 2){ 
	        s_x += '0'; 
	    } 
	    return s_x; 
	}    
	$(function() {
		//查询剩余流量
		$.ajax({
			type: "get",
			async: false,
			url: "<%=basePath%>portal/queryUserFlow?suid=${suid}",
			dataType: "jsonp",
			jsonp: "jsonpCallback",
			success : function(data) {
				var result = eval( "(" + data.result + ")" );
				var temp
				if(result.resultCode == "0" && result.body.retCode == "0"){
					$(".query_msg").hide();
					$("#usedData").text(changeTwoDecimal_f(result.body.remainedData));
					$("#totalData").text(changeTwoDecimal_f(result.body.totalData)+"GB");//总流量
					$("#remainedData").text( changeTwoDecimal_f(result.body.remainedData));//剩余流量
					temp = result.body.remainedData/result.body.totalData*10;
					if(!$.isNumeric(temp)){
						temp = 0;
					}
					ball_wave((temp>10?10:Math.round(temp))*10);
					//到期时间
					if(result.body.validity2 != null && result.body.validity2 != undefined && result.body.validity2 != ""){
						$("#expiry2").text("Expiry date: " + result.body.validity2);
					}
				}
				else{
					$(".query_msg").text("Query failed...");
					$(".ball_wave").hide();
				}
				//预付费余额
				if(result.body.balance != null && result.body.balance != undefined){
					$(".query_msg2").hide();
					$("#balance").text(result.body.unit + changeTwoDecimal_f(result.body.balance));
					if(result.body.validity != null && result.body.validity != undefined && result.body.validity != ""){
						$("#expiry").text("Expiry date: " + result.body.validity);
					}
					//上一次获取时间
					if(result.body.lastOn != null && result.body.lastOn != undefined && result.body.lastOn != ""){
						$("#lastOn").text("Last updated at " + result.body.lastOn);
					}
					else{
						if(result.body.lastOn2 != null && result.body.lastOn2 != undefined && result.body.lastOn2 != ""){
							$("#lastOn").text("Last updated at " + result.body.lastOn2);
						}
					}
				}
				else{
					$(".query_msg2").text("Query failed...");
				}
				if(temp<4){
					$('.full_top_infos').addClass('empty');
				}
			},
			error:function(){
			}
		})
	});
</script>
</html>