<%@ include file="../../../toolbar/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview">
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
<body class=" bgc_w" data-ctrl-name="pageview">

<!-- 	<header class="usage fixed-top tb_header"> -->
<!-- 		<i class="icon_ac" style="visibility: hidden;"></i> -->
<!-- 		<h2>Reload</h2> -->
<!-- 		<i class="close_i"> </i> -->
<!-- 	</header> -->
	
	<header class="usage fixed-top tb_header"><i class="icon_ac" style="visibility: hidden;"></i><h2>Promotion</h2><i class="close_i"> </i></header>

	<div class="page_container reload">
<!-- 		<div class="page_screen banner "> -->
<!-- 			<img src="<%=basePath%>static/images/imgs/tbstorebg_xxx.jpg" alt=""> -->
<!-- 		</div> -->
       <div class="refer_package full">
            <section class="main_image">
                <ul class="" style="width: 1678px; overflow: visible; ">
                    <li style="display: block;"><img id="t_iph" onclick="invoke('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li>
                    <li style=" display: block;"><img id="t_chris" onclick="invoke('<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li>
                    <li style=" display: block;"><img id="t_mobile" onclick="invoke('<%=basePath%>page/turnCompetition?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb848','t_mobile')" src="<%=basePath%>static/images/banners/147/mobile.jpg"  alt=""></li>
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

		<div class="package_detail">
			<p class="title">Mobile number</p>
			<div class="package_ul">
				<div class="details">
					<div class="content">
						<a href="javascript:void(0);">
							<p class="phone_num">${msisdn}</p> </a>
					</div>
				</div>

			</div>
		</div>

		<div class="package_detail reload">
			<p class="title">Reload amount</p>
			<div class="package_ul">
				<div class="details">
					<div class="content">
						<ul class="charges_ul clearfixa">
							<li class="charges_item" onclick="changeAmount('10');">10RM</li>
							<li class="charges_item" onclick="changeAmount('30');">30RM</li>
							<li class="charges_item" onclick="changeAmount('50');">50RM</li>
							<li class="charges_item" onclick="changeAmount('100');">100RM</li>
							<li class="charges_item" onclick="changeAmount('300');">300RM</li>
							<li class="charges_item" onclick="changeAmount('500');">500RM</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="package_btn_wrap">
			<form id="merchant" name="merchant" method="post"
				action="https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment">
				<input type="hidden" name="orderId" id="orderId" value="" /><br>
				<input type="hidden" name="totalAmount" id="totalAmount" value="" /><br> <input
					type="hidden" name="storeId" id="storeId" value="toolbar" /><br> <input
					type="hidden" name="responseUrl" id="responseUrl"
					value=""><br>
				<input type="hidden" name="reconFilename" id="reconFilename" value="">
				<input type="hidden" name="iframes" id="iframes" value="Y">
				<input type="hidden" name="transDate" id="transDate" value="">
				<input type="hidden" name="signature" id="signature"
					value="" /><br> <input
					type="hidden" name="msisdn" id="msisdn" value=""> <br>
				<buttton type="button" class="btn_ov btn_full ok2" id="buyNow">Buy
				Now</buttton>
			</form>
		</div>
	</div>



	<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/reload.jsp";
	function invoke(url,id){
        recordClickEvent('${suid}','toolbar/reload.jsp/'+id);
        window.location.href=url;
    }
	function changeAmount(amount){
	    $("#totalAmount").val(amount);
	}
	
    $("#buyNow").on("click",function () {
    var flag = false;
    $.ajax({
		type: "post",
		async: false,
		url: '<%=basePath%>reload/prepaidPayment?suid=${suid}',
		data: $("#merchant").serialize(),
		dataType: "jsonp",
		jsonp: "jsonpCallback",
		success : function(data) {
			var result = eval( "(" + data.result + ")" );
			if('0'==result.resultCode){
			var orderId = result.body.orderId;
			var reconFilename = result.body.reconFilename;
			var transDate = result.body.transDate;
			var signature = result.body.signature;
			var msisdn = result.body.msisdn;
			var requestUrl = result.body.requestUrl;
			var responseUrl = result.body.responseUrl;
			var totalAmount = result.body.totalAmount;
			
			$("#orderId").val(orderId);
			$("#totalAmount").val(totalAmount);
			$("#reconFilename").val(reconFilename);
			$("#transDate").val(transDate);
			$("#signature").val(signature);
			$("#msisdn").val(msisdn);
			$("#responseUrl").val(responseUrl);
			
			console.log($("#orderId").val());
			console.log($("#totalAmount").val());
			console.log($("#storeId").val());
			console.log($("#responseUrl").val());
			console.log($("#reconFilename").val());
			console.log($("#transDate").val());
			console.log($("#signature").val());
			console.log($("#msisdn").val());
			flag = true;
			}else{
			alert('failture');
			}
		},
		failure :function(){
		
		}
	});
	
	if(flag){
      $('#merchant').attr({
        action:"https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment",
        method:"get",
        enctype:"multipart/form-data"
      }).submit();
	}
    });
    
    $(function () {
        $(".charges_ul>li").on("click",function () {
            $(this).toggleClass("active");
            $(".charges_ul>li").not(this).removeClass("active");
        });
    });
</script>
</body>

</html>