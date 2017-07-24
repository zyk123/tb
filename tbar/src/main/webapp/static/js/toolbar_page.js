var openToolbar = false;
var mdistance =0,timer1 = null,speed2 = 5000;
var leftx = $(".toolbar_menu").offset().left;
var widthall = $(window).width();
var ballSgap =widthall*0.037;
var width = widthall - 56;
var heighty = $(window).height();

var mbh=widthall*0.135,
    bgh=mbh*1.35,
    bgmt=-(bgh-mbh)/2,
    lbmt =bgh*0.12,
    mbw =0;
var  allR1 = false ,allR2=false;


window.onorientationchange=function(){
    resetPostionBall();
    allR1 =true;
};
window.onresize=function(){
    resetPostionBall();
    allR2 =true;
};

function resetPostionBall() {
    calculateWidth();
    calcWindowSize();
    $("#menudiv").removeClass("active");
    clearTimeout(timer1);
    timer1 = setTimeout(ballHide, speed2);
}

function calcWindowSize() {
    leftx = $(".toolbar_menu").offset().left;
    widthall = $(window).width();
    ballSgap =widthall*0.037;
    width = widthall - 56;
    heighty = $(window).height();
    if(allR1&&allR2){
        $("#toolbar_menu").css({"top":heighty-mbh-10,"left":widthall-mbh-ballSgap,"opacity":1});
        allR1 =false;
        allR2 =false;
    }
}

$(document).ready(function() {
    calcWindowSize();

    //关闭按钮
    $("#close_wave").click(function(){
        $("#menudiv").hide();
    });  
    calculateWidth();
    
	$("#wave_wrap").on("click",function () {
		$("#balance_tip_wrap").toggleClass("active");
    });
	$(".toolbar_menu").on('touchmove', function(e) {//拖拽
    	var event = e || window.event;
        mbh = $("#mainBall").height();
           mbw =$("#mainBall").width();
       if(event.originalEvent){
    	   event = event.originalEvent;
       }
       var leftx = event.changedTouches[0].clientX - mbw/2;
       var topy = event.changedTouches[0].clientY - mbw/2;
       topy = topy < 0 ? 0 : topy > heighty-mbh ? heighty - mbh: topy;
        $(".menudiv").removeClass("active");
        e.preventDefault();
        if(leftx <= 0){//最左边吸附
            leftx = -0;
        } else if(leftx >= width){//最右边吸附
            leftx = width + 0;
        } else if (leftx > 0 && leftx < width){
            leftx = leftx;
        }
        $(this).css({"left":leftx + "px", "top":topy + "px","bottom":"auto"});
        $(".advmenu").css({"left":leftx+"px", "top":topy + "px","bottom":"auto"});
    });
    $(".toolbar_menu").on('touchend', function(e) {
    	var event = e || window.event;
        mbh = $("#mainBall").height();
           mbw =$("#mainBall").width();
       if(event.originalEvent){
    	   event = event.originalEvent;
       }
       var leftx = event.changedTouches[0].clientX - mbw/2;
       var topy = event.changedTouches[0].clientY - mbw/2;
       topy = topy < 0 ? 0 : topy > heighty-mbh ? heighty - mbh: topy;
        clearTimeout(timer1);
        //手放开的时候靠左或者靠右 保证不在最中间停留
        if (leftx <= widthall / 2 - mbw/2){
            leftx = ballSgap*2;
            $(".menudiv").removeClass("right").addClass("left");
        }else{
            leftx = widthall-mbw;
            $(".menudiv").removeClass("left").addClass("right");
        }
        $(".toolbar_menu").css({"left" : leftx-ballSgap + "px","opacity":1});
        $(".advmenu").css({"left" : leftx-ballSgap+ "px"});
        timer1 = setTimeout(ballHide, speed2);
    });
    
    $(".ball_a_pic").on("click",function(){
    	$('body').css({
    		"height":"0",
    		"overflow":"hidden",
    		"visibility":"hidden"
    	});
    	$('#toolbar_menu,#menudiv').hide();
    	var cnToolbarIfScr=$(this).attr("data-cnToolbarIf");
    	$("#cnToolbarIfWrap").append('<iframe id="cnToolbarIf" src="" style="width:100%; height:100%; z-index:2100000000;border:none;padding:0;margin:0; background:#fff;display:block;"></iframe>').show();
    	$("#cnToolbarIf").attr("src",cnToolbarIfScr);    	
    });
    $("#cnToolbarIfWrapClose").on("click",function(e){
    	$('body').css({
    		"height":"100%",
    		"overflow":"initial",
    		"visibility":"initial"
    	});
    	if($('#wave_wrap').is(':hidden')){
    		$('#toolbar_menu,#menudiv').show();
    	}    	
    	$('#cnToolbarIfWrap').hide();    
    	$("#cnToolbarIf").remove();
    });
});

function showToolbar() {
  var leftx=  $(".toolbar_menu").offset().left;
  var	topy = $(".toolbar_menu").offset().top-document.body.scrollTop;
     mbw =$("#mainBall").width();
	openToolbar = !openToolbar;
    clearTimeout(timer1);
    var $menudiv =$(".menudiv");
    if($menudiv.hasClass("active")){
        $menudiv.removeClass("active");
    }else{
        $menudiv.addClass("active").css({"height":"10px"});
    }
    calculateWidth();
    if($(".menudiv").hasClass("active")){//如果在展开状态
        $(".menudiv").css("top", topy + "px");
        if (leftx <= widthall / 2 - 28){//靠左 左侧菜单显示
            $(".menudiv").addClass("waveleft").show();
            $(".toolbar_menu").css({"left":ballSgap, "top":topy + "px","bottom":"auto"}).show();
            $(".advmenu").css({"left":0, "top":topy + "px","bottom":"auto"});
            $(".advmenudiv").addClass("waveleft");
        }
        else if(leftx >=  widthall / 2 - 28){//靠右 右侧菜单显示
            $(".menudiv").removeClass("waveleft").show();
            $(".toolbar_menu").css({"left":widthall -mbw-ballSgap+ "px", "top":topy + "px","bottom":"auto"}).show();
            $(".advmenu").css({"left":width -mbw+ "px", "top":topy + "px","bottom":"auto"});
            $(".advmenudiv").removeClass("waveleft");
        }
    } else {//关闭状态执行timer
        timer1 = setTimeout(ballHide, speed2);
    } 
}

function closeBalanceBall() {
    $(".ball_wave").hide();
    $('#toolbar_menu').show();
    timer1 = setTimeout(ballHide, speed2);
}
function showBalanceBall() {
    $('#toolbar_menu').hide();
    $("#wave_wrap").show();
    setTimeout(closeBalanceBall,10000);
}

//流量提醒
function showBalance(userid, toid, cno,isFirstUseToolbar,path){
	$.ajax({
		type: "post",
		async: false,
		url: baseUrl + "portal/queryFlowPortal",
		data: {
			'memberId': userid,
			'tOperatorId': toid,
			'countryNo': cno
		},
		dataType: "jsonp",
		jsonp: "jsonpCallback",
		success : function(data) {
			var result = eval( "(" + data.result + ")" );
			if(result.resultCode == "0" && result.body.status == "1"){
				$("#remainedData").text(Number(result.body.remainedData).toFixed(2));//剩余流量
				var temp = Number(result.body.remainedData)/Number(result.body.totalData)*10;
				if(!$.isNumeric(temp)){
					temp = 0;
				}
				if(temp>=1){
					var per = (temp>10?10:Math.round(temp))*10;
					$("#remainedPer").text(per+"%");
					$('#imgBalance').attr('src',path + 'static/images/tbBall/tbwave/' + per + '.png');
					showBalanceBall();
				}else{
					var per = temp>=0.95?9:Math.round(temp*10);
					$("#remainedPer").text(per+"%");
					showBalanceBall();
				}	
			}
			else{
			    timer1 = setTimeout(ballHide, 10000);
			}
		},
		error:function(){
			console.log("failed");
		}
	});
}

function isShowRemind(isFirstUseToolbar){
	if(isFirstUseToolbar == 'true'){
//		$("#toolbar_menu").hide();
		$("#popRemind").show();
	}
}

function ballHide() {
	$("#menudiv").removeClass("active");
    var leftx= $(window).width()-$("#toolbar_menu").width()*0.7;
    $("#toolbar_menu").css({"left":leftx,"opacity":0.5});
}

function calculateWidth(){
	   mbh=$("#toolbar_menu").width();
      bgh=mbh*1.35;
      bgmt=-(bgh-mbh)/2;
      lbmt =bgh*0.1;
	  $(".bgdiv").css({"height":bgh,top:bgmt});
	  $(".menulist").css({top:bgmt+lbmt});
}