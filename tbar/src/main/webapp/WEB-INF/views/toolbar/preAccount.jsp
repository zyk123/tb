<%@ include file="../../../toolbar/header.jsp" %>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview"  >
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>js/pageEvent.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    function goHome(){
    window.location.href="<%=basePath%>page/turnUsage?suid=${param.suid}";
    }
    </script>
</head>
<body class=""  data-ctrl-name="pageview" >
<header class="usage fixed-top tb_header"><i class="icon_back"  style="visibility: hidden;"></i><h2>Settings</h2><i class="close_i"> </i></header>

<div class="page_container">
<!--     <div class="account_profile"> -->
<!--         <img src="<%=basePath%>static/images/icon/defaultPfile.png" alt=""> <P >${phoneNum }</P> -->
<!--     </div> -->
    <div>
        <ul>
            <li>
                <i class="bg_icon"></i>
            </li>
        </ul>
    </div>
    <div class="sets temp1">
        <ul class="sets_ul">
<!--             <li class="set_item" id="Notice" onclick="jumpNotice();"> -->
<!--                 <i class="bg_icon bgi1"></i><a href="feedback.html">feedback</a> -->
<!--                 <i class="bg_icon bgi0"></i><span >Notice</span> -->
<!--             </li> -->
<!--             <li class="set_item" id="feedback"> -->
<!--                 <i class="bg_icon bgi1"></i><a href="feedback.html">feedback</a> -->
<!--                 <i class="bg_icon bgi1"></i><span >Feedback</span> -->
<!--             </li> -->
            <li class="set_item" id="closeService">
                <!-- <i class="bg_icon bgi2"></i> --> <span>Switch off</span> <i>></i>
            </li>
            <li class="set_item" id="jumpHelp" onclick="jumpHelp()">
               <!--  <i class="bg_icon bgi3"></i>  --><span>Help</span><i>></i>
            </li>
        </ul>
    </div>
</div>


<!-- <footer class="floor_nav floor"> -->
<!--     <div class="floor-container"> -->
<!--         <ul class="nav-ul"> -->
<!--             <li> -->
<!--                 <span class="bar-img"> -->
<!--                    <a href="javascript:void(0);"></a> <img src="<%=basePath%>static/images/icon/nav/ballpic2.png" alt=""> -->
<!--                 </span> -->
<!--             </li> -->
<!--             <li> -->
<!--                 <span class="bar-img"> -->
<!--                    <a href="javascript:void(0);"></a> <img src="<%=basePath%>static/images/icon/nav/s.png" alt=""> -->
<!--                 </span> -->
<!--             </li> -->
<!--             <li> -->
<!--                 <span class="bar-img"> -->
<!--                    <a href="javascript:void(0);"></a> <img src="<%=basePath%>static/images/icon/nav/r.png" alt=""> -->
<!--                 </span> -->
<!--             </li> -->
<!--         </ul> -->
<!--     </div> -->
<!-- </footer> -->


<div class="mask_layout" style="display: none;">
    <div class="setting_close">
        <div class="close_top">Switch Off</div>
        <ul class="setting_ul">
            <li class="setting_item active" data-value="1">One day <i class="i_bor"><i class="i_con"></i></i></li>
            <li class="setting_item" data-value="2">One week <i class="i_bor"><i class="i_con"></i></i></li>
            <li class="setting_item" data-value="3" style="border:none;">One month <i class="i_bor"><i class="i_con"></i></i></li>
        </ul>
        <div class="btn_inline_block">
            <button class="btn_ov btn_mid ok" style="font-size: .34rem;" id="cancel">Cancel</button>  <button class="btn_ov btn_mid cancel" id="apply" style="font-size: .34rem;">Apply</button>
        </div>

    </div>
</div>
</body>
<script type="text/javascript">

    var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/preAccount.jsp";
	
	recordVist('${suid}');
    var defaultType = 1;
    window.onload=function () {
        $(window).on("orientationchange",function (e) {

            $(".setting_close").addClass("landscape");
            if ( window.orientation == 180 || window.orientation==0 ) {
                $(".setting_close").removeClass("landscape");
            }
            if( window.orientation == 90 || window.orientation == -90 ) {
                $(".setting_close").addClass("landscape");
            }

        });
        
        var activeFlag="1";
           $.ajax({
				type: "post",
				async: false,
				url: "<%=basePath%>portal/getDefaultSetting?suid=${suid}",
				dataType : "jsonp",
				jsonp : "jsonpCallback",
				success : function(data) {
				    var result = eval( "(" + data.result + ")" );
				    if('0'==result.body.tmp.retCode){
				    var closetype = result.body.tmp.result.closetype;
				    if('10'==closetype){
				       activeFlag = "1";
				    }else if('11'==closetype){
				       activeFlag = "2";
				    }else if('12'==closetype){
				       activeFlag = "3";
				    }else{
				       activeFlag = "1";
				    }
				    }
				},
				error : function() {
			    }
			});
             
             $(".setting_ul .setting_item").each(function(){
                if(activeFlag==$(this).attr("data-value")){
                    $(this).addClass("active");
                }else{
                    $(this).removeClass("active");
                }
             });

        $(".setting_ul .setting_item").on("click",function(){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/setting_item');
            defaultType = $(this).attr("data-value");
            $(".setting_ul .setting_item").not(this).removeClass("active");
            //$(this).hasClass("active")?$(this).removeClass("active"):$(this).addClass("active");
            if(!$(this).hasClass("active")){
            	$(this).addClass("active");
            }
        });
        
        $("#apply").on("click",function(){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/apply');
        	$.ajax({
				type: "post",
				async: false,
				url: "<%=basePath%>portal/setToolbarAvaliable",
				data: {
					'type' : defaultType,
					'suid' : '${suid}'
					 },
				dataType : "jsonp",
				jsonp : "jsonpCallback",
				success : function(data) {
								console.log(data.result);
				},
				error : function() {
												//alert('fail');
						}
			});
			$(this).parents(".mask_layout").slideUp();
		});

        $("#closeService").on("click",function () {
            recordClickEvent('${suid}','toolbar/preAccount.jsp/closeService');
            $(".mask_layout").slideDown();
        });

        $(".mask_layout").on("click",function(){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/mask_layout');
            $(this).slideUp();
        });
        $("#cancel").on("click",function(){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/cancel');
            $(this).parents(".mask_layout").slideUp();
        });

        $(".setting_close").on("click",function(e){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/setting_close');
            e.stopPropagation();
        });
        $("#feedback").on("click",function(){
            recordClickEvent('${suid}','toolbar/preAccount.jsp/feedback');
			window.location.href="<%= basePath%>page/turnFeedback?suid=${suid}";        
        });
    };

	function jumpNotice(){
	    recordClickEvent('${suid}','toolbar/preAccount.jsp/jumpNotice');
		window.location.href="<%= basePath%>page/turnNotice?suid=${suid}";     
	}
	
	function jumpHelp(){
	    recordClickEvent('${suid}','toolbar/preAccount.jsp/jumpHelp');
		window.location.href="<%=basePath%>page/turnHelp?suid=${suid}";  		
	}

</script>
</html>