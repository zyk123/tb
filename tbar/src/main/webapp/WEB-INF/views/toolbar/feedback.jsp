<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 


<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview"  >
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <link rel="stylesheet" href="<%=path %>/jQuery-Validation-Engine/css/validationEngine.jquery.css">
	<script src="<%=path %>/jQuery-Validation-Engine/js/languages/jquery.validationEngine-en.js"></script>
	<script src="<%=path %>/jQuery-Validation-Engine/js/jquery.validationEngine.min.js"></script> 
	<script src="<%=basePath%>js/pageEvent.js"></script>      
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class=" bgc_p1"  data-ctrl-name="pageview" >

<header class="usage fixed-top tb_header"><i class="icon_back" id="goBack" onclick="goBack()"></i><h2>Feedback</h2><i class="close_i"> </i></header>

<div class="fullscreen_container">
	<div class="feedback_cont">
	    <form action="" id="feedForm">
	        <ul>
	            <li class="inp_name">
	                <label for="username">Name</label>
	            </li>
	            <li>
	                <input type="text" name="username">
	            </li>
<!-- 	            <li class="inp_name"> -->
<!-- 	                <label for="state">Title</label> -->
<!-- 	            </li> -->
<!-- 	            <li> -->
<!-- 	                <input type="text" name="state"> -->
<!-- 	            </li> -->
	            <li class="inp_name">
	                <label for="email">Email</label>
	            </li>
	            <li>
	                <input type="text" name="email" data-validation-engine=="validate[custom[email]]">
	            </li>
	            <li class="inp_name">
	                <label for="comments">Comments (required)</label><i class="point">*</i>
	            </li>
	            <li>
	                <textarea name="comments" id="comments"  data-validation-engine=="validate[required,maxSize[500]]"></textarea>
	            </li>
	        </ul>
	    </form>
	
	    <buttton type="button" class="btn_ov btn_full ok2" id="feedback" >Submit</buttton>
	</div>
</div>

<div class="mask_layout"  id="aqmask" style="display: none;">
        <div class="tips_box" data-aqbox="p4" id="tips_boxp" style="display: none;height: 4rem;" >
            <div class="title">
                 <i class="iclose" id="iclose"></i>
            </div>
            <div class="cont" style="height: 1rem;">
                <p class="p1" id="p1"></p>
<!--                 <p class="p2"> you did not win this round,</p> -->
<!--                 <p class="p2"> please try again.</p> -->
            </div>
            <div class="bottom">
                <button class="btn_ov btn_full" id="oopsConfirm">OK</button>
            </div>
            <div class="error_tips"></div>
        </div> 
</div>

<script type="text/javascript">
    var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/preFeedBack.jsp";
	
	recordVist('${suid}');	

    $(function(){
	    $(".iclose").on("click",function () {
	        $("#aqmask").hide();
	        $(this).parents(".tips_box").hide();
	    });	   	    
	    
	    $("#oopsConfirm").on("click",function () {
	        $("#aqmask").hide();
	        $(this).parents(".tips_box").hide();
			history.back(-1);    	        
	    });	    
    
    
		$('#feedForm').validationEngine('attach',{
			promptPosition : 'topLeft',
			showOneMessage : true
		});	    
    
        $("#feedback").on("click",function () {
			var flag = $('#feedForm').validationEngine('validate');
			if(!flag){
				return;
			}
				$.ajax({
					type: "post",
					async: false,
					url: '<%=basePath%>portal/sendFeedBack?suid=${suid}',
					data: $("#feedForm").serialize(),
					dataType: "jsonp",
					jsonp: "jsonpCallback",
					success : function(data) {
						var result = eval( "(" + data.result + ")" );
						if('0'==result.resultCode){
// 							alert('sucess');
							$("#p1").empty().text("Thank you for your submission");
							$("#aqmask").show();
							$("#tips_boxp").show();
						}else{
							$("#p1").empty().text(result.resultMsg);
							$("#aqmask").show();
							$("#tips_boxp").show();						
						}
					},
					failure :function(){
							$("#p1").empty().text("etwork anomaly");
							$("#aqmask").show();
							$("#tips_boxp").show();	
					}
				});	
				
				recordClickEvent('${suid}','toolbar/preFeedBack.jsp/feedback');			               
        });
    });
    
    
    function goBack(){
    	recordClickEvent('${suid}','toolbar/preFeedBack.jsp/goBack');	
    	javascript :history.back(-1);
    }    
</script>
</body>

</html>