<%@ include file="../../../toolbar/header.jsp"%>
<!DOCTYPE html>
<html lang="en" data-ctrl-name="pageview">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
<script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
<script src="<%=basePath%>js/pageEvent.js"></script>
<style type="text/css">
.mask_layout>.tips_box>.title1 {
    background-color: #00aeef;
    width: 100%;
    height: 1.2rem;
    text-align: center;
    font-size: 0.5rem;
    color: #fff;
    line-height: 1.2rem;
}

.mask_layout>.tips_box>.title>.iclose1 {
	display: block;
	width: 0.4rem;
	height: 0.4rem;
	background-image: url(<%=basePath%>static/images/icon/xx.png);
	/* background-position: center center; */
	background-repeat: no-repeat;
	-webkit-background-size: 0.4rem;
	background-size: 0.4rem;
	position: absolute;
	right: 0.4rem;
	top: 0.4rem;
}

.lottery_wrap {
	width: 7.6rem;
	height: 7.6rem;
	margin: 0.4rem auto;
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
	overflow: hidden;
	background-image:
		url("<%=basePath%>static/images/imgs/lottery/fodder/pointborder.png");
	-webkit-background-size: 7.6rem;
	background-size: 7.6rem;
	background-repeat: no-repeat;
}

#lottery {
	width: 6.8rem;
	height: 6.8rem;
	background-color: #61320A;
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
	overflow: hidden;
	margin-top: 0.4rem;
	margin-left: 0.4rem;
}

#lottery table {
	border-spacing: 0;
}

#lottery table td {
	width: 2.26rem;
	height: 2.26rem;
	color: #333;
	background-position: center center;
	background-repeat: no-repeat;
	-webkit-background-size: 2rem;
	background-size: 1.9rem;
	padding: 0;
	margin: 0;
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
}

#lottery table td a {
	width: 100%;
	height: 100%;
	display: block;
	text-decoration: none;
}

#lottery table td.active {
	background-color: #FF7906;
}

/*CSS代码片段*/

/* 定义一个走马灯动画 */

/*CSS代码片段*/
.marquee {
	width: 100%;
	height: 1rem;
	line-height: 1rem;
	font-size: 0.4rem;
	color: #00aeef;
	overflow: hidden;
	position: absolute;
	white-space: nowrap;
	background-color: rgba(225, 225, 225, 0.5);
}
</style>

<script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
<script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
</head>
<body class="bgc_p2 after_payfor" data-ctrl-name="pageview">

	<header class="usage fixed-top tb_header">
		<!-- 		<i class="icon_ac" style="visibility: hidden;"></i> -->
		<i class="icon_back" id="jumpPromotion" onclick="jumpPromotion();"></i>
		<h2>Lucky Draw</h2>
		<i class="close_i"> </i>
	</header>

	<div class="fullscreen_container">
		<div class="page_screen banner ">
			<div class="marquee" id="marquee">
				<c:if test="${!empty record }">
					<c:forEach var="item" items="${record}" varStatus="status">
						<span>**${fn:substring(item.mobileno,2,5)}**win a prize:
							${item.prizename }</span>
					</c:forEach>
				</c:if>
				<span>**27689**win a prize:HuaweiP9</span> <span>**NIKI**win
					a prize:Mobile Coupons</span> <span>**David**win a prize:HuaweiP9</span> <span
					style="display:inline-block;width:100%;"></span>
			</div>
			<img src="<%=basePath%>static/images/banners/luckbanner2.jpg" alt="">
		</div>
		<div class="win_tips_wrap">
			<p>
				You have <span style="color:#fc7f01;" id="onedaytime">${restOnedaytimes}
				</span> lucky draw today
			</p>
		</div>
		<div class="lottery_wrap">
			<div id="lottery">
				<table>
					<tr>
						<td class="lottery-unit lottery-unit-0"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp0.png');"></td>
						<td class="lottery-unit lottery-unit-1"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp1.png');"></td>
						<td class="lottery-unit lottery-unit-2"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp2.png');"></td>
					</tr>
					<tr>
						<td class="lottery-unit lottery-unit-7"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp7.png');"></td>
						<td
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/start.png');"
							id="startLottery"><a href="#"></a>
						</td>
						<td class="lottery-unit lottery-unit-3"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp3.png');"></td>
					</tr>
					<tr>
						<td class="lottery-unit lottery-unit-6"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp6.png');"></td>
						<td class="lottery-unit lottery-unit-5"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp5.png');"></td>
						<td class="lottery-unit lottery-unit-4"
							style="background-image: url('<%=basePath%>static/images/imgs/lottery/gp4.png');"></td>
					</tr>
				</table>
			</div>
		</div>

		<div class="record_wrap"></div>
		<div class="activity_rule">
			<div class="title">
				<i class="y_line"></i> <span>Game Rules</span>
			</div>
			<div class="cont">
<!-- 				<p> -->
<!-- 					1.User click -->
<!-- 					<spring:message code="preluckydraw.quotes" /> -->
<!-- 					LUCKY DRAW -->
<!-- 					<spring:message code="preluckydraw.quotes" /> -->
<!-- 					button at the page, entered the draw page. -->
<!-- 				</p> -->
				<p>1. Click <spring:message code="preluckydraw.quotes" />Start<spring:message code="preluckydraw.quotes" />, then turntable will spin and stop automatically after a few seconds.</p>
				<p>2. You can play 3 times a day.</p>
				<p>3. Celcom reserves all rights on the final decision.</p>
			</div>
		</div>

	</div>

	<div class="mask_layout bg_coloured" id="aqmask" style="display: none;">
		<div class="tips_box" data-aqbox="p1" style="display: none;">
			<div class="title1">
				Congratulations <i class="iclose"></i>
			</div>
			<div class="cont">
				<p class="p1">
					<span class="p_inner">Congratulations to you<br>have a chance to win</span>
<!-- 					<spring:message code="preluckydraw.exclamation.mark" /> -->
					<br><span class="message_id"></span>
				</p>
				<p class="p2">Answer the following question correctly, you will
					be able to get the prize!</p>
				<p class="p3">
					Question
					<spring:message code="preluckydraw.colon" />
					Are FIRST and XPAX both sub-brands of Celcom?
				</p>
			</div>
			<div class="bottom">
				<button class="btn_ov btn_half btnyes1">YES</button>
				<button class="btn_ov btn_half btnno">NO</button>
			</div>
			<div class="error_tips"></div>
			<div class="oppserror" style="display: none;">
				<p>sorry, wrong answer question, please to draw again and answer
					the question.</p>
			</div>
		</div>
		<div class="tips_box" data-aqbox="p2" style="display: none;">
			<div class="title1">
				Congratulations <i class="iclose"></i>
			</div>
			<div class="cont">
				<p class="p1">
					<span class="p_inner">Congratulations to you<br>have a chance to win</span>
<!-- 					<spring:message code="preluckydraw.exclamation.mark" /> -->
					<br><span class="message_id"></span>
				</p>
				<p class="p2">Answer the following question correctly, you will
					be able to get the prize!</p>
				<p class="p3">
					Question
					<spring:message code="preluckydraw.colon" />
					Are FIRST and XPAX both sub-brands of Celcom?
				</p>
			</div>
			<div class="bottom">
				<button class="btn_ov btn_half btnyes2">YES</button>
				<button class="btn_ov btn_half btnno">NO</button>
			</div>
			<div class="error_tips"></div>
			<div class="oppserror" style="display: none;">
				<p>sorry, wrong answer question, please to draw again and answer
					the question.</p>
			</div>
		</div>
		<div class="tips_box" data-aqbox="p3" style="display: none;">
			<div class="title1">
				Congratulations <i class="iclose"></i>
			</div>
			<div class="cont">
				<p class="p1">
					<span class="p_inner">Congratulations to you<br>have a chance to win</span>
<!-- 					<spring:message code="preluckydraw.exclamation.mark" /> -->
					<br><span class="message_id"></span>
				</p>
				<p class="p2">Answer the following question correctly, you will
					be able to get the prize!</p>
				<p class="p3">
					Question
					<spring:message code="preluckydraw.colon" />
					Are FIRST and XPAX both sub-brands of Celcom?
				</p>
			</div>
			<div class="bottom">
				<button class="btn_ov btn_half btnyes3">YES</button>
				<button class="btn_ov btn_half btnno">NO</button>
			</div>
			<div class="error_tips"></div>
			<div class="oppserror" style="display: none;">
				<p>sorry, wrong answer question, please to draw again and answer
					the question.</p>
			</div>
		</div>
		<div class="tips_boxp4 tips_box" data-aqbox="p4" id="tips_boxp4"
			style="display: none;">
			<div class="title">
				Opps! <i class="iclose" id="iclose"></i>
			</div>
			<div class="cont">
				<p class="p1">Sorry to inform</p>
				<p class="p2">You did not win this round,</p>
				<p class="p2">please try again.</p>
			</div>
			<div class="bottom">
				<button class="btn_ov btn_full confirm_my">Confirm</button>
			</div>
			<div class="error_tips"></div>
		</div>
	</div>

	<div class="mask_layout bg_coloured" id="aqmask1"
		style="display: none;">
		<div class="tips_box" data-aqbox="" id="tips_boxp1"
			style="display: none;height: 5rem;margin-top: -2.5rem;">
			<div class="title">
				Opps! <i class="iclose1" id="iclose1"></i>
			</div>
			<div class="cont" style="height: 1.8rem;">
				<p class="p1" id="p11" style="line-height: 0.8rem;"></p>
				<!--                 <p class="p2"> you did not win this round,</p> -->
				<!--                 <p class="p2"> please try again.</p> -->
			</div>
			<div class="bottom">
				<button class="btn_ov btn_full" id="oopsConfirm">Confirm</button>
			</div>
			<div class="error_tips"></div>
		</div>
	</div>

	<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/postLuckydraw.jsp";
	recordVist('${suid}');
	
	var shipId = '1';
	var promotionprizeid = '';
	var worngAnsCount = 0;
    var lottery={
        index:0,	//当前转动到哪个位置
        count:0,	//总共有多少个位置
        timer:0,	//setTimeout的ID，用clearTimeout清除
        speed:200,	//初始转动速度
        times:0,	//转动次数
        cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
        prize:-1,	//中奖位置
        init:function(id){
            if ($("#"+id).find(".lottery-unit").length>0) {
                $lottery = $("#"+id);
                $units = $lottery.find(".lottery-unit");
                this.obj = $lottery;
                this.count = $units.length;
                $lottery.find(".lottery-unit-"+this.index).addClass("active");
                console.log(this.count+" : "+this.count);
            };
        },
        roll:function(){
            var index = this.index;
            var count = this.count;
            var lottery = this.obj;
            $(lottery).find(".lottery-unit-"+index).removeClass("active");
            index += 1;
            if (index>count-1) {
                index = 0;
            };
            $(lottery).find(".lottery-unit-"+index).addClass("active");
            this.index=index;
            return false;
        },
        stop:function(index){
            this.prize=index;
            return false;
        }
    };

    function roll(){
        lottery.times += 1;
        lottery.roll();
        if (lottery.times > lottery.cycle+10 && lottery.prize==lottery.index) {
            clearTimeout(lottery.timer);
            lottery.prize=-1;
            lottery.times=0;
//             click=false;
//             $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start.png)");
        }else{
            if (lottery.times<lottery.cycle) {
                lottery.speed -= 10;
            }else if(lottery.times==lottery.cycle) {

                /*var index = Math.random()*(lottery.count)|0;*/
                //var index = Math.random()*(lottery.count)|0; //中奖位置
// 				var index = 4;//Math.random()*(lottery.count)|0; //中奖位置
//                 if(index>1&&index<6){
//                     console.log("超时未中奖"+index);
//                     lottery.prize = 0||4;

//                     setTimeout(function(){
//                         $("#aqmask").show();
//                         $("[data-aqbox='p2']").show();
//                     },5000);
//                 }else if(index>6&&index<8){
//                     lottery.prize = index;
//                     console.log("抽完奖了,中奖的事"+index);
//                 }else{
// 				}
        var index=4;
		$.ajax({
				type: "post",
				async: false,
				url: "<%=basePath%>portal/requirePrize?suid=${suid}&promotionId=${promotionId}",
				dataType : "jsonp",
				jsonp : "jsonpCallback",
				success : function(data) {
				    //var result = eval( "(" + data + ")" );
				    console.log(data.body);
				    console.log(data.body.prizeId);
				    console.log(data.body.orderno);
				    if('0'==data.body.retCode){
				        var orderno = data.body.orderno;
				        var prizetype = data.body.prizetype;
				        var prizeName = data.body.prizeName;
				        shipId = data.body.shipId;
				        promotionprizeid = data.body.promotionprizeid;
// 				        if('133ebde449544468919b263e900045ce'==prizeId){
// 			                lottery.prize='0';
// 				        }
// 				        else if('0bfe5d58baf94ea084c47fac04ba82fc'==prizeId){
// 			                lottery.prize='1';
// 				        }
// 				        else if('4376d4a3bbc84d0688a5e8de5cb5acbf'==prizeId){
// 			                lottery.prize='2';
// 				        }
// 				        else if('b7b1fb98a88e4046ab8bb70838a0cc53'==prizeId){
// 			                lottery.prize='3';
// 				        }
// 				        else if('02ff190e5e8a4be399c428b0805b4bc0'==prizeId){
// 			                lottery.prize='4';
// 				        }
// 				        else if('08d40e37adcc49dbbf43d9c4826b9ae7'==prizeId){
// 			                lottery.prize='5';
// 				        }
// 				        else if('5d106f1d1f6a4c518d5314789c6a3be4'==prizeId){
// 			                lottery.prize='6';
// 				        }else if('1c34bbaac3d84f8ab2205d2eb4d85a71'==prizeId){
// 			                lottery.prize='7';
// 				        }
                        lottery.prize = orderno;
			            index = lottery.prize;
			        setTimeout(function(){
                        $("#aqmask").show();
                        if('1'==prizetype){
                        $("[data-aqbox='p1'] .message_id").text(prizeName);
                        $("[data-aqbox='p1']").show();
                         $("body").css("overflow","hidden");
                        }else if('2'==prizetype){
                        $("[data-aqbox='p2'] .message_id").text(prizeName);
                        $("[data-aqbox='p2']").show();
                        $("body").css("overflow","hidden");
                        }else{
                        $("[data-aqbox='p4']").show();
                        $("body").css("overflow","hidden");
                        }
                         click=false;
            $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start.png)"); 
                    },5000);
			        }else if('-1'==data.body.retCode){
			            lottery.prize=0;
			            index=lottery.prize;
			            $("[data-aqbox='p4']").show();
			             click=false;
            $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start.png)"); 
			        }else{
			            lottery.prize=0;
			            index=lottery.prize;
			            $("[data-aqbox='p4']").show();
			             click=false;
            $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start.png)"); 
			        }
				},
				error : function() {
				//console.log('aaa');
				        lottery.prize=0;
			            index=lottery.prize;
			            $("[data-aqbox='p4']").show();
			             click=false;
            $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start.png)"); 
			    }
			});

            }else{
                if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
                    lottery.speed += 110;
                }else{
                    lottery.speed += 20;
                }
            }
            if (lottery.speed<40) {
                lottery.speed=40;
            };
            //console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
            lottery.timer = setTimeout(roll,lottery.speed);
        }
        return false;
    }

    var click=false;

    window.onload=function(){
        $.ajax({
            async:false,
            url:"<%=basePath%>portal/luckyRestOnedaytimes",
            type:"post",
            data:{
                suid:'${suid}',
                promotionId:'${promotionId}'
            },
            dataType:"jsonp",
            jsonp:"jsonpCallback",
            success:function(ret){
               $("#onedaytime").text(ret);
            },
            error:function(){
            }
        });
        lottery.init('lottery');
        $("#lottery a").click(function(){
        var restTimes;
        $.ajax({
            async:false,
            url:"<%=basePath%>portal/luckyRestOnedaytimes",
            type:"post",
            data:{
                suid:'${suid}',
                promotionId:'${promotionId}'
            },
            dataType:"jsonp",
            jsonp:"jsonpCallback",
            success:function(ret){
               restTimes = ret;
            },
            error:function(){
            }
        });
        
            if (click) {
                return false;
            }else{
            if($("#onedaytime").text()<=0||undefined==restTimes||''==restTimes||restTimes<='0'){
                   $("#p11").empty().text("Out of luck.  Please try again tomorrow");
			       $("#aqmask1").show();
			       $("#tips_boxp1").show();
                   return false;
            }
            $("#onedaytime").text(($("#onedaytime").text()>0 ? $("#onedaytime").text()-1:0)+" ");
                lottery.speed=100;
                roll();
                click=true;
                $("#startLottery").css("backgroundImage","url(<%=basePath%>static/images/imgs/lottery/start1.png)"); 
                return false;
            }
            recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/clickLottery');
        });

        $(".btnyes1").on("click",function (e) {
        $.ajax({
				type: "post",
				async: false,
				url: '<%=basePath%>portal/updateManOrRobotInfo',
				data: {
					suid:'${suid}',
                    shipId:shipId,
                    status:'0',
                    promotionprizeid:promotionprizeid
				},
				dataType: "jsonp",
				jsonp: "jsonpCallback",
				success : function(data) {
				},
				failure :function(){
				}
		});
        window.location.href = "<%=basePath%>page/turnLotteryRewardInfo?suid=${suid}&receiverid=${receiverid}";
        recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/btnyes1');
        })
        $(".btnyes2").on("click",function (e) {
        $.ajax({
				type: "post",
				async: false,
				url: '<%=basePath%>portal/updateManOrRobotInfo',
				data: {
					suid:'${suid}',
                    shipId:shipId,
                    status:'0',
                    promotionprizeid:promotionprizeid
				},
				dataType: "jsonp",
				jsonp: "jsonpCallback",
				success : function(data) {
				},
				failure :function(){
				}
		});
        window.location.href = "<%=basePath%>page/turnLotteryCongratulations?suid=${suid}&receiverid=${receiverid}";
        recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/btnyes2');	
        })
        
        $(".btnno").on("click",function (e) {
            if(worngAnsCount<3){
            var event = e||window.event;
            event.preventDefault();
            var ttarget = $(this).parents(".tips_box").find(".oppserror");
                ttarget.fadeIn();
            setTimeout(function (e) {
                ttarget.fadeOut();
            },3000);
            worngAnsCount++; 
            }else{
             $.ajax({
				type: "post",
				async: false,
				url: '<%=basePath%>portal/updateManOrRobotInfo',
				data: {
					suid:'${suid}',
                    shipId:shipId,
                    status:'2',
                    promotionprizeid:promotionprizeid
				},
				dataType: "jsonp",
				jsonp: "jsonpCallback",
				success : function(data) {
				},
				failure :function(){
				}
		});
            	$("#aqmask").show();
    	        $(this).parents(".tips_box").hide();
    	        $("#tips_boxp4").show();
    	        worngAnsCount = 0;
            }
            recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/btnno');	
        })

        $(".iclose").on("click",function(){
            $("#aqmask").hide();
            $(this).parents(".tips_box").hide();
            $("body").css("overflow","auto");
            recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/iclose');	
        });
        $(".iclose1").on("click",function(){
            $("#aqmask1").hide();
            $(this).parents(".tips_box").hide();
            $("body").css("overflow","auto");
        });
        
        $("#oopsConfirm").on("click",function(){
            $("#aqmask1").hide();
           $("#tips_boxp1").show();
        });
        
        $(".confirm_my").on("click",function(){
            $("#aqmask").hide();
            $(this).parents(".tips_box").hide();
            $("body").css("overflow","auto");
            recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/confirm_my');	
        });
        
        $(".record_wrap").on("click",function(){
            window.location.href="<%=basePath%>page/turnNotice?suid=${suid}&isJumpCompetition=2";
            recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/record_wrap');
        });
    };
    
     function scroll(obj) {
        /*往左*/
        var tmp = (obj.scrollLeft)++;
//当滚动条到达右边顶端时
        if (obj.scrollLeft == tmp) {
            obj.scrollLeft = 0;
        }
    }
    var _timer = setInterval("scroll(document.getElementById('marquee'))", 20);
    
    function  jumpPromotion(){
		recordClickEvent('${suid}','toolbar/postLuckydraw.jsp/jumpPromotion');
		window.location.href = "<%=basePath%>page/turnPromotion?suid=${param.suid}";
	}
</script>

</body>

</html>