<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview"  >
<head>
    <meta charset="UTF-8">
   	<link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/mobileSlide.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>static/js/jquery.touchSlider.js"></script>
    <script src="<%=basePath%>js/pageEvent.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class=" bgc_p1 after_payfor"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_ac" style="visibility: hidden;"></i><h2>Subscribe</h2><i class="close_i"> </i>
</header>

<div class="page_container">
<!--     <div class="wrap100 bgcw"> -->
<!--         <div class="left_cont"> -->
<!--             <div class="top_infos"> -->
<!--                 <P class="balance_num"> -->
<!--                     <span class="title">Internet Balance</span> -->
<!--                     <c:choose> <c:when test="${empty remainedData || empty totalData}"><strong>Query failed...</strong></c:when> <c:otherwise><strong>${remainedData }</strong>/<span>${totalData }GB</span></c:otherwise> </c:choose></P> -->
<!--                 <div class="left_progress_wrap"> -->
<!--                     <P class="left_progress"> -->
<!--                         <i class="left_current" style="width:<c:choose> <c:when test="${empty remainedData || empty totalData}">0%</c:when> <c:otherwise><fmt:formatNumber value="${(remainedData/totalData)*100 }" type="number" pattern="#"/>%</c:otherwise> </c:choose>"></i> -->
<!--                     </P> -->
<!--                 </div> -->
<!--                 <p class="tcenter_tips"><c:if test="${!empty validity}">Expiry date:${validity }</c:if></p> -->
<!--                 <p class="tcenter_tips"><c:choose> <c:when test="${empty lastUpdate}">Querying...</c:when> <c:otherwise>Last updated at ${lastUpdate }</c:otherwise> </c:choose></p> -->
<!--             </div> -->
<!--         </div> -->
<!--         <div class="refer_package full"> -->
<!--             <section class="main_image"> -->
<!--                 <ul class="" style="width: 1678px; overflow: visible; "> -->
<!--                     <li style="display: block;"><img id="t_iph" onclick="invoke('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li> -->
<!--                     <li style=" display: block;"><img id="t_chris" onclick="invoke('<%=basePath%>page/turnPostLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li> -->
<!--                     <li style=" display: block;"><img id="t_mobile" onclick="invoke('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li> -->
<!--                 </ul> -->
<!--                 <a href="javascript:;" id="btn_prev" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--                 <a href="javascript:;" id="btn_next" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--             </section> -->
<!--             <div class="flicking_con" style="margin-left: -40px;"> -->
<!--                 <a href="#" class="">1</a> -->
<!--                 <a href="#" class="">2</a> -->
<!--                 <a href="#" class="">3</a> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->


    <div class="wrap100 bgcw">
        <div class="left_cont">
            <div class="top_infos">
                <p class="title">Internet Balance(GB)</p>
                <P class="balance_num"><c:choose> <c:when test="${empty remainedData || empty totalData}"><strong>Querying...</strong></c:when> <c:otherwise><strong>${remainedData }</strong>/<span>${totalData }GB</span></c:otherwise> </c:choose></P>
                <div class="left_progress_wrap">
                    <P class="left_progress">
                        <i class="left_current" style="width: <c:choose> <c:when test="${empty remainedData || empty totalData}">0%</c:when> <c:otherwise><fmt:formatNumber value="${(remainedData/totalData)*100 }" type="number" pattern="#"/>%</c:otherwise> </c:choose>"></i>
                    </P>
                    <span class="left_tips_percent"><c:choose> <c:when test="${empty remainedData || empty totalData}"></c:when> <c:otherwise><fmt:formatNumber value="${(remainedData/totalData)*100 }" type="number" pattern="#"/>%</c:otherwise> </c:choose></span>
<!--                     <img class="left_warning" style="" src="<%=basePath%>static/images/icon/runningOutBtn.png" alt=""> -->
                </div>
                <p class="tips">Expiry date:<c:choose> <c:when test="${empty validity}">Querying...</c:when> <c:otherwise>${validity }</c:otherwise> </c:choose></p>
                <p class="tips tcenter_tips"><c:choose> <c:when test="${empty lastUpdate}">Querying...</c:when> <c:otherwise>Last updated at ${lastUpdate }</c:otherwise> </c:choose></p>
            </div>
            <div class="gap_block" style="width: 100%; height: 0.5rem;"></div>
        </div>
<!--         <div class="refer_package full"> -->
<!--             <section class="main_image"> -->
<!--                 <ul class="" style="width: 1678px; overflow: visible; "> -->
<!--                     <li style="display: block;"><img id="t_iph" onclick="invoke('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li> -->
<!--                     <li style=" display: block;"><img id="t_chris" onclick="invoke('<%=basePath%>page/turnPostLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li> -->
<!--                     <li style=" display: block;"><img id="t_mobile" onclick="invoke('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li> -->
<!--                 </ul> -->
<!--                 <a href="javascript:;" id="btn_prev" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--                 <a href="javascript:;" id="btn_next" style="width: 1678px; overflow: visible; display: inline;"></a> -->
<!--             </section> -->
<!--             <div class="flicking_con" style="margin-left: -40px;"> -->
<!--                 <a href="#" class="">1</a> -->
<!--                 <a href="#" class="">2</a> -->
<!--                 <a href="#" class="">3</a> -->
<!--             </div> -->
<!--         </div>         -->
<!--         <p class="tcenter_tips mb-02r"><c:choose> <c:when test="${empty lastUpdate}">Querying...</c:when> <c:otherwise>Last updated at ${lastUpdate }</c:otherwise> </c:choose></p> -->
    </div>

	<form id="store_form" action="<%= basePath%>page/turnPackageDetailOrder" method="post">
		<input type="hidden" id="packageid" name="packageid">
		<input type="hidden" id="fgno" name="fgno">
		<input type="hidden" id="fgname" name="fgname">
		<input type="hidden" id="ptname" name="ptname">
		<input type="hidden" id="fgdesc" name="fgdesc">
		<input type="hidden" id="amount" name="amount">
		<input type="hidden" id="currency" name="currency">
		<input type="hidden" id="expenses" name="expenses">
		<input type="hidden" id="suid" name="suid">
		<input type="hidden" id="fgtips" name="fgtips">
		<input type="hidden" id="isShowbuy" name="isShowbuy">
	</form>
	<div id="hack" style="height: 5000px;">
    <div class="package_detail" id="showPackages">
<!--         <div class="refer_package"> -->
<!--             <img src="<%=basePath%>static/images/imgs/refer.jpg" alt="" onclick="AddOnSub()"> -->
<!--         </div> -->


    </div>
    </div>
</div>

<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/preStore.jsp";
    function invoke(url,id){
        recordClickEvent('${param.suid}','toolbar/postStore.jsp/'+id);
        window.location.href=url;
    }
	$(document).ready(function(){		
       	$.ajax({
			type: "post",
			async: false,
			url: "<%=basePath%>store/getPackageListManager?suid=${suid}&serviceType=Prepaid",
			dataType: "jsonp",
			jsonp: "jsonpCallback",
			success : function(data) {
				var suid = '${suid}';
				var packageType = eval( "(" + data.result + ")" );
				
				var html = '';
				
				$.each(packageType,function(i,pt){
					var packageList = pt.packageList;
					
					html += '<div class="">';
					if(packageList.size()!=0){
						html += 	'<p class="title">'+ pt.ptname +'</p>';
					}
					html += 	'<ul class="package_ul">';
					
					$.each(packageList,function(j,pl){
						var t_packageid = (typeof(pl.packageid) == "undefined"?'':pl.packageid);
						var t_fgno = (typeof(pl.fgno) == "undefined"?'':pl.fgno);
						var t_fgname = (typeof(pl.fgname) == "undefined"?'':pl.fgname);
						var t_fgtips = (typeof(pl.fgtips) == "undefined"?'':pl.fgtips);
						var t_ptname =(typeof(pt.ptname) == "undefined"?'':pt.ptname);
						var t_fgdesc = (typeof(pl.fgdesc) == "undefined"?'':pl.fgdesc);
						var t_amount = (typeof(pl.amount) == "undefined"?'':pl.amount);
						var t_currency = (typeof(pl.currency) == "undefined"?'':pl.currency);
						var t_expenses = (typeof(pl.expenses) == "undefined"?'':pl.expenses);
					
						html += 	'<li class="details clearfixa">';
						html +=       	'<div class="content">'; 	
						html += 			'<a href="javascript:void(0);" id="package_"+t_packageid onclick="showPackage(\''+t_packageid+'\',\''+t_fgtips+'\',\''+t_fgno+'\',\''+t_fgname+'\',\''+t_ptname+'\',\''+t_fgdesc+'\',\''+t_amount+'\',\''+t_currency+'\',\''+t_expenses+'\',\''+suid+'\'); " >';				
						html += 				'<p class="package_name">'+ t_fgname +'</p>';	
						html += 				'<p class="content1">'+ t_fgdesc +'</p>';
						html += 		'<i class="i_icon"><img src="<%=basePath%>static/images/icon/arrowr.png" /></i>';	
						html += 			'</a>';
						html += 		'</div>';
						html += 	'</li>';	
					
					});
					
					html += 	'</ul>';
					html += '</div>';
					
				});
					
				$("#showPackages").append(html);
                $('#hack').css('height',$("#showPackages").height());
			},
			error:function(){
			}
		});
        
		
		//记录页面访问事件
		recordVist('${suid}');
	});
	
	function showPackage(packageid,fgtips,fgno,fgname,ptname,fgdesc,amount,currency,expenses,suid){
		recordClickEvent('${suid}','toolbar/preStore.jsp/package_'+packageid);
		$("#packageid").val(packageid);
		$("#fgtips").val(fgtips);
		$("#fgno").val(fgno);
		$("#fgname").val(fgname);
		$("#ptname").val(ptname);
		$("#fgdesc").val(fgdesc);
		$("#amount").val(amount);
		$("#currency").val(currency);
		$("#expenses").val(expenses);
		$("#suid").val(suid);
		$("#isShowbuy").val("N");
		
		$("#store_form")[0].submit();
		
// 		window.location.href="<%= basePath%>page/turnPackageDetailOrder?" + $('#store_form').serialize();
		
	}
	
	function AddOnSub(){
		showPackage('12','Add-On Internet 500MB','45562','Add-On Internet 500MB','Add-OnInternet','Follow Internet plan validity period','10.0000','RM','','${suid}');
	}
</script>
<script src="<%=basePath%>js/pageEvent.js"></script>
</body>

</html>