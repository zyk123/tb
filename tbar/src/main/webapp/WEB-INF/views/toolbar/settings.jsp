<%@ include file="../../../toolbar/header.jsp" %>

<!DOCTYPE html>
<html lang="en"  style="height:100%;width:100%">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>Settings</title>
    <link rel="stylesheet" href="<%=basePath %>static/css/base.css">
    <script src="<%=basePath %>static/js/jquery.1.8.3.min.js"></script>
    <style type="text/css">
		html ,body{height:100%; position: relative; font-family:"Exo 2",sans-serif,monospace;}

        body {
            background: #eee;
            font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
            font-size: 14px;
            color:#000;
            margin: 0;
            padding: 0;
        }
        .swiper-container {
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.8);
        }
        .swiper-slide {
            text-align: center;
            font-size: 18px;
            background: #fff;
            /* Center slide text vertically */
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }
        .swiper-pagination-bullet {
            width: 20px;
            height: 20px;
            text-align: center;
            line-height: 20px;
            font-size: 12px;
            color:#000;
            opacity: 1;
            background: rgba(0,0,0,0.2);
        }
        .swiper-pagination-bullet-active {
            color:#fff;
            background: #007aff;
        }
        .mask2{
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.8);
            position: absolute;
            left: 0;
            top: 0;
        }
        .guide>img{
            position: absolute;
            top:0;
            left: 0;
            width: 100%;
        }
        .guides img{
            width: 100%;
            position: absolute;
            top: 0;
            left: 0;
        }
        .swiper-slide{
            background:inherit;
        }
        .swiper-pagination-bullet{
            width: 10px;
            height:10px;
        }

        .swiper-pagination-bullet{
            background-color: #5c398d;
        }
        .swiper-pagination-bullet-active{
            background-color: #fc7f01;
        }
        .swiper-pagination {
            position: absolute;
            text-align: center;
            -webkit-transition: 300ms;
            -moz-transition: 300ms;
            -o-transition: 300ms;
            transition: 300ms;
            -webkit-transform: translate3d(0, 0, 0);
            -ms-transform: translate3d(0, 0, 0);
            -o-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
            z-index: 10;
        }

        .swiper-pagination-fraction, .swiper-pagination-custom, .swiper-container-horizontal > .swiper-pagination-bullets {
            bottom: 10px;
            left: 0;
            width: 100%;
        }
        .close{
            width: 12%;
            position: absolute;
            right: 0;
            top: 0;
            z-index: 100;
        }
    </style>
      <link rel="stylesheet" href="<%=basePath%>static/css/swiper.css">
    <script src="<%=basePath%>static/js/swiper.min.js"></script>
</head>
<body class="bgc_p1 pt-08r" style="height:100%;width:100% ;overflow:hidden; position:fixed; top:0;">

<div class="myBody" style="position:absolute;display:block; z-index:2410000000; width:100%;height:100%;top:0;background-color:#fff;margin-top:0.8rem;">
<header class="store tb_header fixed-top">
    <span class="s_gap"></span>
    <h2>Settings</h2>
    <i class="close_i"> </i>
</header>
<div class="sets">
    <ul class="sets_ul">
<!--        <li class="set_item" >
            Language   <span style="">English</span> <i> ></i>
        </li>
        <li class="set_item">
            Notifications  <i> ></i>
        </li>-->
        <li class="set_item" id="closeService">Switch Off <i>></i>
        </li>
        <li class="set_item" id="feedback">
            Send feedback  <i> ></i>
        </li>
<!--         <li class="set_item"> -->
<!--             <a href="feedback.html">Send feedback<i>></i></a> -->
<!--         </li> -->
<!--         <li class="set_item" id="feedback"> -->
<!--             Send feedback  <i> ></i> -->
<!--         </li> -->
      <li class="set_item">
          <a href="javascript:$('#frame').css('z-index','2410000000')">Help<i>></i></a>
        </li>
<!--       <li class="set_item"> -->
<!--           <a href="<%=basePath%>about.jsp?suid=${suid}">About<i>></i></a> -->
<!--       </li> -->
<!--       <li class="set_item"> -->
<!--           <a href="#">Download Xpax App Now!<i>></i></a> -->
<!--       </li> -->
    </ul>
</div>

<div class="mask_layout" style="display: none;">
    <div class="setting_close">
        <div class="close_top">Switch Off</div>
        <ul class="setting_ul">
            <li class="setting_item active" data-value="1">One day <i class="i_bor"><i class="i_con"></i></i></li>
            <li class="setting_item" data-value="2">One week <i class="i_bor"><i class="i_con"></i></i></li>
            <li class="setting_item" data-value="3" style="border:none;">One month <i class="i_bor"><i class="i_con"></i></i></li>
        </ul>
        <div class="btn_inline_block">
            <button class="btn_ov btn_mid cancel" id="cancel">Cancel</button>  <button class="btn_ov btn_mid ok" id="apply">Apply</button>
        </div>

    </div>
</div>

</div>


<!-- Swiper -->
<div id="frame" style="display:block;position: absolute;top: 0;z-index: 240000000;width:100%; height:100%; border:none;padding:0;margin:0; background:#fff;" >

<div class="swiper-container"  >
    <a href=""></a><img class="close" onclick="jump()" src="<%=basePath%>static/images/icon/xguide.png" alt="">
    <div class="swiper-wrapper ">
        <div class="swiper-slide guide ">
            <img class="" src="<%=basePath%>static/images/imgs/guide/guide1.jpg" alt="">
        </div>
        <div class="swiper-slide guide ">
            <img class="" src="<%=basePath%>static/images/imgs/guide/guide2.jpg" alt="">

        </div>
        <div class="swiper-slide guide ">
            <img class="" src="<%=basePath%>static/images/imgs/guide/guide3.jpg" alt="">
        </div>
        <div class="swiper-slide guides">
            <img src="<%=basePath%>static/images/imgs/guide/guide4.jpg" alt="">
        </div>
        <div class="swiper-slide guides">
            <img src="<%=basePath%>static/images/imgs/guide/guide5.jpg" alt="">
        </div>
    </div>
    <!-- Add Pagination -->
    <div class="swiper-pagination" style="position:fixed;"></div>
</div>

</div>
<script src="<%=basePath%>js/pageEvent.js"></script>
<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/settings.jsp";
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
        
        
        <!-- Swiper JS -->
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
                if(location.href.indexOf("turnSetting") == -1){
					parent.location.href=document.referrer;
				  }else{			
					e.preventDefault();
				  }
            }

        });
    }

	function jump(){
		if(document.referrer.indexOf("turnSetting") == -1){
			parent.location.href=document.referrer;
		}
	}

<!-- Initialize Swiper -->
        
        $(function(){
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
        });

        $(".setting_ul .setting_item").on("click",function(){
        	defaultType = $(this).attr("data-value");
            $(".setting_ul .setting_item").not(this).removeClass("active");
            //$(this).hasClass("active")?$(this).removeClass("active"):$(this).addClass("active");
            if(!$(this).hasClass("active")){
            	$(this).addClass("active");
            }
        });

        $("#closeService").on("click",function () {
            $(".mask_layout").slideDown();
        });
        
        $("#feedback").on("click",function(){
			window.location.href="<%= basePath%>page/turnFeedback?suid=${suid}";        
        });
		$("#about").on("click",function(){
			window.location.href="<%= basePath%>page/turnAbout?suid=${suid}";    
		})
        $(".mask_layout").on("click",function(){
            $(this).slideUp();
        });
        $("#cancel").on("click",function(){
            $(this).parents(".mask_layout").slideUp();
        });

        $(".setting_close").on("click",function(e){
            e.stopPropagation();
        });
        
        $("#apply").on("click",function(){
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
		
		//记录页面访问事件
		recordVist('${suid}');
	  };
</script>



</body>
</html>