
var  allR1 = false ,allR2=false;
var toolbar={
    openToolbar:false,
    mdistance:0,
    timer1:null,
    speed2:5000,
    leftx:$(".toolbar_menu").offset().left,
    widthall:$(window).width(),
    heighty:$(window).height(),
    mbw:0,
    currentNum:0,
    beginNum:0,
    calculateWidth:function(){
            mbh=$("#toolbar_menu").width();
            bgh= mbh*1.35;
            bgmt=-(bgh-mbh)/2;
            lbmt =bgh*0.1;
            $(".bgdiv").css({"height":bgh,top:bgmt});
            $(".menulist").css({top:bgmt+lbmt});
    },
    menuTouchMove:function(e) {//拖拽
        var event = e || window.event,
            $tb =toolbar,
            mbw =$("#toolbar_menu").width();
        if(event.originalEvent){
            event = event.originalEvent;
        }
        event.preventDefault();
        var leftx = event.changedTouches[0].clientX - mbw/2;
        var topy = event.changedTouches[0].clientY - mbw/2;
        if(topy<=0){
            this.topy=0;
        }else if(topy>=$tb.heighty-mbw){
            this.topy=$tb.heighty-mbw;
        }else{
            this.topy=topy;
        }
        $(".menudiv").removeClass("active");
        if(leftx<=0){//最左边吸附
            this.leftx =0;
        } else if(leftx>=toolbar.widthall-mbw){//最右边吸附
            this.leftx =toolbar.widthall-mbw;
        } else if (leftx>0&&leftx<toolbar.widthall){
            this.leftx =  leftx;
        }
       // $(".showS").html(this.topy);
        $(this).css({"left":this.leftx + "px", "top":this.topy + "px","bottom":"auto"});
    },
    menuTouchEnd:function(e) {
        var $tb=toolbar;
        var $tbm = $(".toolbar_menu");
        var $tbmp =$(".waveball_wrap");
        var event = e || window.event,
            mbw = $tbm.width(),
            ballSgap =$tb.widthall*0.037;
        if(event.originalEvent){
            event = event.originalEvent;
        }
        var leftx = event.changedTouches[0].clientX - mbw/2;
        clearTimeout(toolbar.timer1);
        //手放开的时候靠左或者靠右 保证不在最中间停留
        //2017/3/6 注释logo位置；
       /* if (leftx <= $tb.widthall / 2 - mbw/2){
            leftx = ballSgap*2;
            $tbm.removeAttr("style");
            $tbm.addClass("shrinkL");
            $tbm.css({"left" : leftx-ballSgap + "px","top":this.topy,"bottom":"auto"});
            $tbmp.css({"opacity":0.9});

        }else{
            $tbm.removeClass("shrinkL");
            $tbm.removeAttr("style");
            $(this).css({"right":ballSgap+"px","top":this.topy,"bottom":"auto"});
            $tbmp.css({"opacity":0.9});
        }
        if(this.topy==""||this.topy==null||this.topy==undefined){
            $tbm.css({"bottom":"70px"});
            if(this.topy==0){
                $tbm.css({"bottom":"auto"});
            }
        }*/
        $tbm.css({"bottom":0,"opacity":1});
        console.log("end top"+this.topy);
        toolbar.timer1 = setTimeout($tb.ballHide, $tb.speed2);
    },
    showTrafficTips:function(){ //弹出流量 提示框
        $(".menu_item.bg1").addClass("active");
        $(".menu_item.bg2").show().addClass("active");
        if(serviceType=='Prepaid' && isAlertOpen == "true"){
            var $t =$("#left_refer");
            $t.fadeIn(2000).addClass("active");
            $t.animate({"width":"500%"},500,function () {
                $(".menu_item.bg1").hide();
                setTimeout(toolbar.hideTraffictips,10000);
            })
        }
    },
    showActivity:function(){
        $(".menu_item.bg1").addClass("active"); 
        $(".menu_item.bg3").show().addClass("active");
        $.ajax({
            url:basePath + 'portal/isActivityPopup',
            type:'get',
            data:{
                suid:suid
            },
            dataType:'jsonp',
            jsonp:'jsonpCallback',
            success:function(ret){
                if(ret == "SUCCESS"){
                    $('#activeAd').show();
                }
            },
            error:function(){
                alert('server occur error!');
            },
            complete : function(){
                if($("#activity_ball")){
                    clearTimeout(this.timer1);
                    toolbar.timer1 = setTimeout(toolbar.ballHide, toolbar.speed2);
                }else{
                    console.warn("actoolbar error"+$("#activity_ball"));
                }
            }
        });
    },
    hideTraffictips:function () { //隐藏流量 提示框
        clearTimeout(this.timer1);
        var $t =$("#left_refer");
        $t.animate({"width":"100%"},500,function () {
            $t.hide();
            toolbar.timer1 = setTimeout(toolbar.ballHide, toolbar.speed2);
        })
    },
    resetPostionBall:function(){ //重置位置
        toolbar.calculateWidth();
        toolbar.calcWindowSize();
        $("#menudiv").removeClass("active");
        clearTimeout(this.timer1);
        toolbar.timer1 = setTimeout(toolbar.ballHide, toolbar.speed2);
    },
    calcWindowSize:function(){ //计算宽度；
        var $tb =toolbar;
        var $tbmp =$(".waveball_wrap");
        $tb.widthall=$(window).width();
        $tb.heighty=$(window).height();
        var ballSgap =$tb.widthall*0.037;
        var mbh=$tb.widthall*0.135;
        if(allR1&&allR2){
            $("#toolbar_menu").css({"top":$tb.heighty-mbh-10,"left":$tb.widthall-mbh-ballSgap});
            $tbmp.css({"opacity":0.9});

            allR1 =false;
            allR2 =false;
        }
    },
    ballHide:function() { //主球吸边；
        var $tbm =$("#toolbar_menu");
        var $tbmp =$(".waveball_wrap");
        var leftx= 0;

        //右吸边 注释；
       /* if($tbm.hasClass("shrinkL")){
            leftx= $tbm.width()*0.3*-1;
        }else{
            leftx= $(window).width()-$tbm.width()*0.7;
        }
        $tbm.css({"left":leftx});*/
        // console.log($tbm.height()*.8);
        // console.log("高速"+$tbm.width());
//        ,"bottom":-$tbm.width()/7.13*.8+"px"
        /*$tbm.css({"opacity":0.3,"bottom":-$tbm.width()/7.13*.26+"px"});*/
       /*$tbm.css({"opacity":0.5});*/
    },
    next:function(){ //下一张
      var  $tb =toolbar;
      var  count =$('#toolbar_menu .menu_item').length;
        $tb.beginNum==count-1?$tb.beginNum=0:$tb.beginNum++;
        $tb.showControl($tb.beginNum);
        return this;
    },
    showControl:function(target){ //图片切换效果
      var  $tmi =$("#toolbar_menu .menu_item");
        $tmi.css("display","none");
        $('#toolbar_menu .menu_item:eq('+target+')').css("display","block");
    },
    closetbad:function(thi) {
        $.ajax({
            url:basePath + 'portal/activityHasShown',
            type:'get',
            data:{
                suid:suid
            },
            dataType:'jsonp',
            jsonp:'jsonpCallback',
            success:function(ret){
                if(ret == 'SUCCESS'){
                    thi.parentNode.style.display="none";
                }else{
                    alert('server occur error');
                }
            },
            error:function(){
                alert('server occur error!');
            }
        });
    },
    showtbad:function () {
        $(".toolbar_active_ad").show();
    }
}
window.onorientationchange=function(){
    toolbar.resetPostionBall();
    console.log(toolbar.widthall);
    allR1 =true;
};
window.onresize=function(){
    toolbar.resetPostionBall();
    allR2 =true;
};

$(document).ready(function() {
	var lenOriginal = $("#cnToolbarWrap .original .floor-container ul li").size();
	$("#cnToolbarWrap .original .floor-container ul li").css({"width":(100/lenOriginal)+"%"});
	
    $("#left_refer").on("touchend touchmove",function (e) {e.stopPropagation();});
    toolbar.calcWindowSize();
    toolbar.calculateWidth();
    $tbm=$(".toolbar_menu");
    /*$tbm.on('touchmove',toolbar.menuTouchMove);*/
    $tbm.on('touchend',toolbar.menuTouchEnd);


    //获取图片
      var temp = 100;
      /*      
  	$.ajax({
  		url: basePath + 'portal/queryUserFlow?suid=' + suid,
  		type: "get",
  		async:false,
  		dataType: "jsonp",
  		jsonp: "jsonpCallback",
  		success : function(data) {
  			var result = eval( "(" + data.result + ")" );
  			if(result.resultCode == "0" && result.body.retCode == "0"){
  				temp = Number(result.body.remainedData)/Number(result.body.totalData)*10;
  				if(!$.isNumeric(temp)){
  					temp = 0;
  				}
  				temp = Math.round(temp)*10;
				$('#toolbar_per').text(temp+'%');
				// $('#toolbar_left').text("Left "+Number(result.body.remainedData).toFixed(2) + "GB");
  			}			
  		}
  	});	
 */
  	$.ajax({
  		url:basePath + 'portal/getAllIcon',
  		type:'post',
  		data:{
  			suid:suid
  		},
  		dataType: "jsonp",
  		jsonp: "jsonpCallback",
  		success:function(data){
  			var type = serviceType=='Prepaid'?1:2;
  			var array = [];
  			$.each(data,function(i,obj){
  				if(obj.sign==1){
                    $('#main_ball').css( 'opacity', 1);
					$('#main_ball').attr({
						'data-cnToolbarIf':basePath + obj.linkurl + "?suid=" + suid,
						src:"data:image/png;base64," + obj.icon
					});
  				}else if(obj.sign==2 && temp <= flowThreshold && temp == obj.percent){
					$('#flow_ball').attr('src',"data:image/png;base64," + obj.icon);
					$('.tradet').attr('data-cnToolbarIf',basePath + obj.linkurl + "?suid=" + suid);
  				}else if(obj.sign==3){
  					array.push(obj);
  				}
  			});
  			if (array)
            {
                var o = array[Math.round(Math.random()*(array.length-1))];
                $('#activity_ball').attr({
                    'data-cnToolbarIf':basePath + o.linkurl + "?suid=" + suid,
                    src:"data:image/png;base64," + o.icon
                });
                $('#activity_pop').attr({
                    'data-cnToolbarIf':basePath + o.linkurl + "?suid=" + suid,
                    src:"data:image/png;base64," + o.popup
                });
            }
  		}
  	});


   $("#mainBall img").on("load",function () {
	   if(temp <= flowThreshold){
//		   if( isAlertOpen == "true"){
		   setTimeout(toolbar.showTrafficTips,2000);
//		   }
	   }else{
		   setTimeout(toolbar.showActivity,2000);
	   }       
   })
   
   $(".floor_nav li").on("click",function(){
	   $(".floor_nav li").removeClass("active");
	   $(this).addClass("active");
	/*   if($("#homelc").hasClass("active")){
		   $(".foller_nav.original").addClass("homeac");
	   }else{
		   $(".foller_nav.original").removeClass("homeac");
	   }*/
   });
   
    $(".ball_a_pic").on("click",function(e){
    	$('body').css({
    		"height":"0",
    		"overflow":"hidden",
    		"visibility":"hidden"
    	});
    	$('#toolbar_menu').hide();
        $("#cnToolbarIf").remove();
    	var cnToolbarIfScr=$(this).attr("data-cnToolbarIf");
    	$("#cnToolbarIfWrap").append('<iframe id="cnToolbarIf" src="" style="width:100%; height:100%; z-index:2100000000;border:none;padding:0;margin:0; background:#fff;display:block;"></iframe>');
    	if(cnToolbarIfScr.indexOf("turnUsage")!=-1){
    		$('body').css({
        		"height":"100%",
        		"overflow":"initial",
        		"visibility":"initial"
        	});
    		/*$(".foller_nav.original").addClass("homeac");*/
    		$("#cnToolbarIf").css({"background": "rgba(0, 0, 0, 0.38)"});
    		$("#cnToolbarWrap").css({"background-color": "transparent"});
//    		$("#cnToolbarIfWrap").css({"height":"100%"});
//    		$(".foller_nav.original").hide();
//    		$(".foller_nav.home").show().addClass("active");
    	/*	$(".floatover").show();*/
//    		$("#cnToolbarIfWrapClose").hide();
    	}else{
    		$("#cnToolbarIfWrap").css({"height":"86%"});
    		if(serviceType && serviceType=="Prepaid"){
    			$(".foller_nav.original .floor").css({"background-color":"#431e5e"});
    		}else{
    			$(".foller_nav.original .floor").css({"background-color":"#00aeef"});
    		}
    		$(".foller_nav").show();
    		$("#cnToolbarIfWrapClose").show();  
//    		$(".foller_nav.original").show();
//    		$(".foller_nav.home").hide().removeClass("active");
    			
    	}    	
    	$('#cnToolbarWrap').show();
    	$("#cnToolbarIf").attr("src",cnToolbarIfScr);
    	var e = e||window.event;
    	var target = e.currentTarget;
    	recordClickEvent(suid,'index.jsp/'+target.id);
    });

    $("#cnToolbarIfWrapClose").on("click",function(e){
    	$('body').css({
    		"height":"100%",
    		"overflow":"initial",
    		"visibility":"initial"
    	});
    	$('#toolbar_menu').show();
        $("#cnToolbarIf").remove();
    	$('#cnToolbarWrap').hide();
        recordClickEvent(suid,'index.jsp/cnToolbarIfWrapClose');
    });

});


