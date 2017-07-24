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
<body class=" bgc_p1"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_ac" style="visibility: hidden;"></i><h2>Reload</h2><i class="close_i"> </i></header>

<div class="page_container reload3">
<!--     <div class="page_screen banner " style="height:3.5rem;overflow:hidden;"> -->
<!--         <img src="<%=basePath%>static/images/imgs/tbstorebg_xxx.jpg" alt=""> -->
<!--     </div> -->
<!--       <div class="refer_package full"> -->
<!--             <section class="main_image"> -->
<!--                 <ul class="" style="width: 1678px; overflow: visible; "> -->
<!--                     <li style="display: block;"><img id="t_iph" onclick="invoke('<%= basePath%>page/turnPackageDetailOrder?packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB&ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=&suid=${param.suid}','t_iph')" class="slideimg" src="<%=basePath%>static/images/banners/147/iph.jpg"  alt=""></li> -->
<!--                     <li style=" display: block;"><img id="t_chris" onclick="invoke('<%=basePath%>page/turnPreLucky?suid=${param.suid}&promotionId=6c6c3b249e3d4577bd27940e5e3cb847','t_chris')" src="<%=basePath%>static/images/banners/147/ch.png"  alt=""></li> -->
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

    <div class="package_detail">
        <div class="package_ul">
            <div class="details ">
                <div class="content">
                        <p>Mobile Number</p>
                        <input type="text" class="phone_num" id="phone_num" value="${msisdn}" maxlength="12"/>
                </div>
            </div>
        </div>
    </div>

    <!--<div class="package_detail reload">
        <p class="title">Reload Amount</p>
        <div class="package_ul">
            <div class="details">
                <div class="content">
                    <ul class="charges_ul clearfixa">
                        <li class="charges_item"> 10RM</li>
                        <li class="charges_item"> 30RM</li>
                        <li class="charges_item"> 50RM</li>
                        <li class="charges_item"> 100RM</li>
                        <li class="charges_item"> 300RM</li>
                        <li class="charges_item"> 500RM</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>-->
    <div class="package_detail">
        <p class="title active"><strong>-</strong> Credit Reload</p>
        <ul class="package_ul credit_reload">
            <li class="details clearfixa" amount="10.00" style="border-top-left-radius:5px;border-top-right-radius:5px;">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount('10.00');"></i>
                        <p class="package_name">RM10</p>
                </div>
            </li>
            <li class="details clearfixa" amount="30.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount('30.00');"></i>
                        <p class="package_name">RM30</p>
                </div>
            </li>
            <li class="details clearfixa" amount="50.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount('50.00');"></i>
                        <p class="package_name">RM50</p>
                </div>
            </li>
            <li class="details clearfixa" amount="100.00" style="border-bottom-left-radius:5px;border-bottom-right-radius:5px;">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount('100.00');"></i>
                        <p class="package_name">RM100</p>
                </div>
            </li>
        </ul>
    </div>

    <div class="package_detail">
        <p class="title"><strong>+</strong>  Internet Plans</p>
        <ul class="package_ul plans">
            <li class="details clearfixa" amount="50.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount1('50.00');"></i>
                    <a href="javascript:void(0);">
                        <p class="package_name">RM50 for 10GB Monthly</p>
                    </a>
                </div>
                <i class="i_icon" onclick="abcde('111','40565','RM50 for 10GB Monthly','Internet Plan','','50','RM','','Internet Plan RM50 for 10GB Monthly','50.00');"><img src="<%=basePath%>static/images/icon/arrowr.png" alt=""></i>
            </li>
            <li class="details clearfixa" amount="30.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount1('30.00');"></i>
                    <a href="javascript:void(0);">
                        <p class="package_name">RM30 for 5GB Monthly</p>
                    </a>
                </div>
                <i class="i_icon" onclick="abcde('112','40564','RM30 for 5GB Monthly','Internet Plan','','30','RM','','Internet Plan RM30 for 5GB Monthly','30.00');"><img src="<%=basePath%>static/images/icon/arrowr.png" alt=""></i>
            </li>
            <li class="details clearfixa" amount="10.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount1('10.00');"></i>
                    <a href="javascript:void(0);">
                        <p class="package_name">RM10 for 2GB Weekly</p>
                    </a>
                </div>
                <i class="i_icon" onclick="abcde('113','40562','RM10 for 2GB Weekly','Internet Plan','','10','RM','','Internet Plan RM10 for 2GB Weekly','10.00');"><img src="<%=basePath%>static/images/icon/arrowr.png" alt=""></i>
            </li>
           <%-- <li class="details clearfixa" amount="3.00">
                <div class="content">
                    <i class="icon interPlans" onclick="changeAmount1('3.00');"></i>
                    <a href="javascript:void(0);">
                        <p class="package_name">RM3 for 1GB Daily</p>
                    </a>
                </div>
                <i class="i_icon" onclick="abcde('114','40561','RM3 for 1GB Daily','Internet Plan','','3','RM','','Internet Plan RM3 for 1GB Daily','3.00');"><img src="<%=basePath%>static/images/icon/arrowr.png" alt=""></i>
            </li> --%>
        </ul>
    </div>
    <div class="package_btn_wrap">
    <form id="merchant" name="merchant" method="post"
				action="https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment">
				<div style="height:0px; overflow:hidden;display:none;">
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
				</div>
				
        <buttton type="button" class="btn_ov btn_full ok2" id="buyNow">Proceed</buttton>
        </form>
    </div>
</div>

   <div class="mask_layout bg_coloured" id="aqmask" style="display: none;">
		<div class="tips_box" data-aqbox="p1" style="display: block;height: 4rem;">
			<div class="title">
				Tips <i class="iclose"></i>
			</div>
			<div class="cont">
				<p class="p1">
					<span class="message_id" style="position: absolute;top: 1.5rem;left: 0.2rem;"></span>
				</p>
			</div>
		</div>
	</div>

<form id="store_form" action="<%= basePath%>page/turnReloadPackageDetailOrder" method="post">
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
		<input type="hidden" id="flagamount" name="flagamount">
		<input type="hidden" id="isShowbuy" name="isShowbuy" value="Y">
	</form>

<script type="text/javascript">

      function abcde(packageid,fgno,fgname,ptname,fgdesc,amount,currency,expenses,fgtips,flagamount){
         $("#packageid").val(packageid);
		 $("#fgtips").val(fgtips);
		 $("#fgno").val(fgno);
		 $("#fgname").val(fgname);
		 $("#ptname").val(ptname);
		 $("#fgdesc").val(fgdesc);
		 $("#amount").val(amount);
		 $("#currency").val(currency);
		 $("#expenses").val(expenses);
		 $("#suid").val('${suid}');
		 $("#flagamount").val(flagamount);
         $("#store_form").submit();
        };
    $(function () {



        if(''!='${flag}'&&'Y'=='${flag}'){
            $(".package_detail .package_ul.plans").show();
            $(".package_detail .package_ul.credit_reload").hide();
            $(".package_ul.plans li").each(function(){
            if('${flagamount}'==$(this).attr("amount")){
                 $(this).parent().parent().siblings().find("strong").html(" + ");
                 $(this).parent().parent().siblings().find(".title").removeClass("active");
                 $(this).parent().parent().find("strong").html(" - ");
                 $(this).parent().parent().find(".title").toggleClass("active");
                 $(this).toggleClass("active");
                 changeAmount1('${flagamount}');
            }
            });
        }else{
            $(".package_detail .package_ul.plans").hide();
        }
        $(".charges_ul>li").on("click",function () {
            $(this).toggleClass("active");
            $(".charges_ul>li").not(this).removeClass("active");
        });
        $(".package_ul.plans li").on("click",function () {
            $(".package_ul.plans li").removeClass("active");
            $(".package_ul.credit_reload li").removeClass("active");
            $(this).toggleClass("active");
        });
        $(".package_ul.credit_reload li").on("click",function () {
            
            $(".package_ul.plans li").removeClass("active");
            $(".package_ul.credit_reload li").removeClass("active");
            $(this).toggleClass("active");
        });
        $(".package_detail .title").on("click",function () {
             $(this).parent().siblings().find(".title").next().slideUp();
              $(this).parent().siblings().find(".title").removeClass("active");
             $(this).parent().siblings().find(".title").find("strong").html("+")
            if($(this).hasClass("active")){
                $(this).next().slideUp();
                $(this).find("strong").html("+");
            }else{
                $(this).next().slideDown();
                $(this).find("strong").html(" - ");
            }
            $(this).toggleClass("active");
        });
    });

      var baseUrl = "<%=basePath%>";
      var browseUrl = "toolbar/preReload.jsp";
      recordVist('${suid}');
	var storeFlag;
	
	$(".iclose").on("click",function(){
	    $("#aqmask").hide();
	});
	function showMessage(msg){
         $("#aqmask").show();
         $("[data-aqbox='p1'] .message_id").text(msg);
    }
	function invoke(url,id){
        recordClickEvent('${suid}','toolbar/preReload.jsp/'+id);
        window.location.href=url;
    }
	function changeAmount(amount){
	    storeFlag='t';// t = PrepaidPayment
	    $("#storeId").val('toolbar');//TODO  wangxiaoran
	    $("#totalAmount").val(amount);
	    $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment/PrepaidPayment");
//        $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment");

    }
	function changeAmount1(amount){
	    storeFlag='x';// x = MIPayment
	   $("#storeId").val('xpax');//TODO  wangxiaoran
	    $("#totalAmount").val(amount);
	    $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment/MIPayment");
//        $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment-Testing/MIPayment");

    }
	
    $("#buyNow").on("click",function () {
        recordClickEvent('${suid}',browseUrl+'/buyNow');
     $(".package_ul.credit_reload li").each(function(){
         if($(this).hasClass("active")){
           $("#totalAmount").val($(this).attr("amount"));
           $("#storeId").val("toolbar");//TODO  wangxiaoran
           $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment/PrepaidPayment");
//             $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment");

             storeFlag='t';//TODO t = PrepaidPayment
//            $("#responseUrl").val("http://58.27.120.219:8080/tbar/reload/turnReloadResult");
         }
     });
     $(".package_ul.plans li").each(function(){
         if($(this).hasClass("active")){
           $("#totalAmount").val($(this).attr("amount"));
           $("#storeId").val("toolbar");//TODO  wangxiaoran phase2 上线 暂时改为toolbar 改动之前为xpax
           $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment/MIPayment");
//             $("#merchant").attr("action","https://onlinepayment.celcom.com.my/Payment-Testing/MIPayment");

             storeFlag='x';//TODO x = MIPayment
//            $("#responseUrl").val("http://58.27.120.219:8080/tbar/reload/turnReloadResult");
         }
     });
     
     if(''== $("#totalAmount").val().trim()){
//         alert("please select amount");
           showMessage('please select amount');
        return;
     }
    
    var phone_num = $("#phone_num").val();
    
    if(''==phone_num){
      showMessage("please input Mobile number");
      return;
    }
    
    if(!(/^\d{10,12}$/.test(phone_num))){
      showMessage("Mobile number is not correct!");
      return;
    }
    
    $("#msisdn").val(phone_num);
    
    var flag = false;
    $.ajax({
		type: "post",
		async: false,
		url: '<%=basePath%>reload/prepaidPayment?suid=${suid}&storeFlag='+storeFlag,
		data: $("#merchant").serialize(),
// 		dataType: "jsonp",
// 		jsonp: "jsonpCallback",
		success : function(data) {
// 			var result = eval( "(" + data.result + ")" );
            data = eval( "(" + data + ")" );
			var result = data.result;
			result = eval( "(" + result + ")" );
			if('0'==result.resultCode){
			var orderId = result.body.orderId;
			var reconFilename = result.body.reconFilename;
			var transDate = result.body.transDate;
			var signature = result.body.signature;
			var msisdn = result.body.msisdn;
			var requestUrl = result.body.requestUrl;
			var responseUrl = result.body.responseUrl;
			var totalAmount = result.body.totalAmount;
			var storeId = result.body.storeId;
			
			$("#storeId").val(storeId);
			$("#orderId").val(orderId);
// 			$("#totalAmount").val(totalAmount);
			$("#reconFilename").val(reconFilename);
			$("#transDate").val(transDate);
			$("#signature").val(signature);
			$("#msisdn").val(msisdn);
			$("#responseUrl").val(responseUrl);
			
			flag = true;
			}else{
			alert('failture1');
			}
		},
		failure :function(){
		alert('failture2');
		}
	});
	
	if(flag){
//       $('#merchant').attr({
//         action:"https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment",
//         method:"get",
//         enctype:"multipart/form-data"
//       }).submit();
      window.open($("#merchant").attr("action")+"?"+$("#merchant").serialize(),"_blank");
	}
    });
</script>
</body>

</html>