<%@ include file="../../../toolbar/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYsharePE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>catch the phone</title>
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" href="<%=basePath%>static/css/baseS2.css">
    <style>html, body {
        margin: 0;
        height: 100%;
        overflow: hidden;
        user-select: none;
        -webkit-user-select: none
    }

    canvas {
        position: absolute;
        top: 0;
        left: 0;
        display: block;
        height: 100%;
        margin: auto;

    }

	.board {
	    position: fixed;
	    top: 1.2rem;
	    left: 0;
	    width: 100%;
	    height: 100%;
	    display: none;
	    text-align: center;
	    z-index: 100;
	    background-position: center center;
	    background-size: 10rem;
	    background-repeat: no-repeat;
	    background-image: url(<%=basePath%>static/images/game/gamectp/board2.png);
	}

    .board-img {
        height: 100%
    }

    .board-text {
        position: absolute;
        width: 79%;
        max-height: 30%;
        overflow: hidden;
        top: 22%;
        left: 10%;
        font-size: 34px;
        color: #2860ad
    }

/*    .share-tip {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: url(gamectp/share_tip.png?1408476074) 50% 0 rgba(0, 0, 0, .6) no-repeat;
        background-size: 80% auto
    }*/

    .loading {
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0, 0, 0, .6);
        width: 100%;
        height: 100%
    }

    .loading-text {
        color: #fff;
        position: absolute;
        top: 40%;
        width: 100%;
        text-align: center
    }

    .start {
        position: fixed;
        top: 1.2rem;
        left: 0;
        width: 100%;
        height: 100%;
        background: url(<%=basePath%>static/images/game/gamectp/start_post.png) 50% 50% no-repeat;
        background-size: auto 100%;
        z-index:98;
    }

    .start-btn {
        position: absolute;
        width: 100%;
        left: 0;
        top: 6%;
        height: 50%;
        cursor: pointer;
        -webkit-tap-highlight-color: transparent;
        tap-highlight-color: transparent;
    }
    .gamerule-btn {
        position: absolute;
        width: 100%;
        left: 0;
        top: 74%;
        height: 7%;
        cursor: pointer;
        -webkit-tap-highlight-color: transparent;
        tap-highlight-color: transparent;
    }
    
    .waradrecord-btn {
        position: absolute;
        width: 100%;
        left: 0;
        top: 61%;
        height: 8%;
        cursor: pointer;
        -webkit-tap-highlight-color: transparent;
        tap-highlight-color: transparent;
    }
    
    .tryag_btn{
        width:60%;
        height:10%;
        background-image: url("<%=basePath%>static/images/game/gamectp/trya_btn.png");
        background-repeat:no-repeat;
        -webkit-background-size:100%;
        background-size:100%;
        position: absolute;
        bottom:40%;
        left: 20%;
    }
    .nowins_tips{
        width: 60%;
        position: absolute;
        bottom: 50%;
        left: 20%;
    }
    .trywins_pic{
        width:55%;
    }    
    
    /*CSS代码片段*/

      /* 定义一个走马灯动画 */
    .marquee {
        width: 100%;
        height: 1rem;
        line-height: 1rem;
        font-size: 0.4rem;
        color:#00aeef;
        overflow: hidden;
        position: absolute;
        white-space: nowrap;
        background-color: rgba(225,225,225,0.5);
    }
    
    .marquee span{
    	margin-left:10px;
    }
    </style>
    <script src="<%=basePath%>static/js/jquery.1.8.3.min.js"></script>
    <script>
        !function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
    <script src="<%=basePath%>js/pageEvent.js"></script> 
</head>
<body class="after_payfor" style="background-color: #f5dc88;">
<canvas id="canvas" width="640" height="960">alternate content</canvas>
<div id="start" class="start">
    <header class="usage fixed-top tb_header"><i class="icon_back" id="jumpPromotion" onclick="jumpPromotion();"></i><h2>Competition</h2><i class="close_i"> </i></header>
    <div class="marquee" id="marquee">
    <span style="display:inline-block;width:100%;"></span>
        	<c:if test="${!empty record }">
	        	<c:forEach var="item" items="${record}" varStatus="status">
	            	<span>**${fn:substring(item.mobileno,2,5)}**win a prize: ${item.prizename }</span>
	            </c:forEach>
            </c:if>
            <span style="display:inline-block;width:100%;"></span>
    </div>
    <div id="start-btn" class="start-btn"></div>
    <div id="waradrecord-btn" class="waradrecord-btn" id="wardRecord" onclick="wardRecord()"></div>
    <div id="gamerule-btn" class="gamerule-btn" id="gameRule" onclick="gameRule()"></div>
</div>
<div id="loading" class="loading">
    <div class="loading-text">Loading... [<span id="loading-progress">0</span>%]</div>
</div>
<div id="board" class="board">
    <header class="usage fixed-top tb_header"><i class="icon_ac" style="visibility: hidden;"></i><h2>Competition</h2><i class="close_i"> </i></header>
    <div id="board-text" class="board-text" ></div>
    <img class="nowins_tips" style="display:none;" src="<%=basePath%>static/images/game/gamectp/nowinnins.png" alt="">
    <div class="tryag_btn board-btn" data-action="retry"></div>
<!--    <div class="board-btn" style="top:65%" data-action="share"></div>-->
    <div class="mask_layout bg_coloured"  id="aqmask" style="display: none;">
        <div class="tips_box" data-aqbox="p0" id="tips_boxp0" style="display: none;">
            <div class="title">
                Congratulations <i class="iclose"></i>
            </div>
            <div class="cont">
                <img class="trywins_pic"  src="<%=basePath%>static/images/game/gamectp/bluetrywins.png" alt="">
                <p style="font-size:0.4rem; color:#00aeef;text-align: center;line-height: 0.8rem;">Click to draw and stand a chance to win prize.</p>
            </div>        
            <button class="btn_ov btn_full normal" style="margin-top:0.2rem;" id="trywins_pic">OK</button>    
        </div>    	
        <div class="tips_box" data-aqbox="p1" id="tips_boxp1" style="display: none;">
            <div class="title">
                Congratulations <i class="iclose"></i>
            </div>
            <div class="cont">
                <p class="p1"><span class="p_inner">Congratulations to you<br>have a chance to win</span><br><span id="prize"></span></p>
                <p class="p2">Answer the following question correctly, you will be able to get the prize!</p>
                <p class="p3">Question: Are FIRST and XPAX both sub-brands of Celcom ?</p>
            </div>
            <div class="bottom">
            	<input type="hidden" id="prizeType" value="" />
            	<input type="hidden" id="prizeName" value="" />
            	<input type="hidden" id="receiverId" value="" />
                <button class="btn_ov btn_half" id="yes" onclick="rightAnswer()">Yes</button>
                <button class="btn_ov btn_half btnno" id="no" >No</button>
            </div>
            <div class="error_tips"></div>
            <div class="oppserror" id="oppserror" style="display: none;"><p>Sorry, wrong answer. Please draw again</p>
            </div>
        </div>
        <div class="tips_box" data-aqbox="p4" id="tips_boxp4" style="display: none;">
            <div class="title">
                Opps! <i class="iclose" id="iclose"></i>
            </div>
            <div class="cont">
                <p class="p1">Sorry to inform  </p>
                <p class="p2"> You did not win this round,</p>
                <p class="p2"> Please try again.</p>
            </div>
            <div class="bottom">
                <button class="btn_ov btn_full" id="oopsConfirm">OK</button>
            </div>
            <div class="error_tips"></div>
        </div>       
    </div>
</div>
<div id="share-tip" class="share-tip"></div>
<script>
    var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/preCompetition.jsp";
	
	recordVist('${suid}');


	var userLimits = '${userLimits}';
	
	var worngAnsCount = 0;
	
	function wardRecord(){
		 recordClickEvent('${suid}','toolbar/preCompetition.jsp/wardRecord');
		 window.location.href="<%= basePath%>page/turnNotice?suid=${suid}&isJumpCompetition=1";
	}
	
	function gameRule(){
		recordClickEvent('${suid}','toolbar/preCompetition.jsp/gameRule');
		window.location.href="<%= basePath%>page/turnGameRule?suid=${suid}";
	}
	
	function judgeLimits(){
		var scoreNum = $("#board-text span").text();
// 		if(userLimits > 0){
					$.ajax({
						type: "post",
						async: false,
						url: '<%=basePath%>portal/judgeLimits',
						data: {"scoreNum":scoreNum,"suid":'${suid}',"promotionId":'${promotionId}'},
						dataType: "jsonp",
						jsonp: "jsonpCallback",
						success : function(data) {
							var result = eval( "(" + data.result + ")" );
							if('0'==result.resultCode && result.body.retCode=="0"){
								if(result.body.inStall){
					                $(".nowins_tips").hide();
					               	$("#aqmask").show();
					                $("#tips_boxp0").show();								
								}else{
									$(".nowins_tips").show();
									$("#aqmask").hide();
								}
							}
						},
						failure :function(){
							alert("etwork anomaly");
						}
					});	
					
// 		}	
	}
	
	
	function luckDraw(){
		var scoreNum = $("#board-text span").text();
						$.ajax({
						type: "post",
						async: false,
						url: '<%=basePath%>portal/phoneGameLuckDraw',
						data: {"suid":'${suid}',"promotionId":'${promotionId}',"scoreNum":scoreNum},
						dataType: "jsonp",
						jsonp: "jsonpCallback",
						success : function(data) {
							var result = eval( "(" + data.result + ")" );
							if('0'==result.resultCode && '0'==result.body.retCode){
								var prizeType = result.body.prizeType;
								$("#prizeType").val(prizeType);
								$("#receiverId").val(result.body.receiverId);
								$("#prizeName").val(result.body.prizeName);
								$("#prize").text(result.body.prizeName);
								if(prizeType!=undefined && prizeType!=null){
									$("#aqmask").show();
									if(prizeType!="0"){
										$("#tips_boxp1").show();									
									}else{
										$(".nowins_tips").show();
										$("#tips_boxp4").show();
									}
								}
							}else{
								alert(result.resultMsg);
							}
						},
						failure :function(){
							alert("etwork anomaly");
						}
					});	
	}
	
	function rightAnswer(){
		recordClickEvent('${suid}','toolbar/preCompetition.jsp/yes');	
		var prizeType = $('#prizeType').val();
		window.location.href = "<%= basePath%>page/turnPhoneGameCongratulations?suid=${suid}&promotionId=${promotionId}&receiverid="+$('#receiverId').val()+"&prizeType="+prizeType+"&prizeName="+$('#prizeName').val();
		
	}
	
	
	function  jumpPromotion(){
		recordClickEvent('${suid}','toolbar/preCompetition.jsp/jumpPromotion');
		window.location.href = "<%=basePath%>page/turnPromotion?suid=${param.suid}";
	}
	
</script>
<script>this.createjs = this.createjs || {};
(function () {
    var c = function (a, k, d) {
        this.initialize(a, k, d)
    }, b = c.prototype;
    b.type = null;
    b.target = null;
    b.currentTarget = null;
    b.eventPhase = 0;
    b.bubbles = !1;
    b.cancelable = !1;
    b.timeStamp = 0;
    b.defaultPrevented = !1;
    b.propagationStopped = !1;
    b.immediatePropagationStopped = !1;
    b.removed = !1;
    b.initialize = function (a, k, d) {
        this.type = a;
        this.bubbles = k;
        this.cancelable = d;
        this.timeStamp = (new Date).getTime()
    };
    b.preventDefault = function () {
        this.defaultPrevented = !0
    };
    b.stopPropagation = function () {
        this.propagationStopped = !0
    };
    b.stopImmediatePropagation =
        function () {
            this.immediatePropagationStopped = this.propagationStopped = !0
        };
    b.remove = function () {
        this.removed = !0
    };
    b.clone = function () {
        return new c(this.type, this.bubbles, this.cancelable)
    };
    b.toString = function () {
        return "[Event (type=" + this.type + ")]"
    };
    createjs.Event = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
    }, b = c.prototype;
    c.initialize = function (a) {
        a.addEventListener = b.addEventListener;
        a.on = b.on;
        a.removeEventListener = a.off = b.removeEventListener;
        a.removeAllEventListeners = b.removeAllEventListeners;
        a.hasEventListener = b.hasEventListener;
        a.dispatchEvent = b.dispatchEvent;
        a._dispatchEvent = b._dispatchEvent;
        a.willTrigger = b.willTrigger
    };
    b._listeners = null;
    b._captureListeners = null;
    b.initialize = function () {
    };
    b.addEventListener = function (a, k, d) {
        var b;
        b = d ? this._captureListeners = this._captureListeners ||
            {} : this._listeners = this._listeners || {};
        var c = b[a];
        return c && this.removeEventListener(a, k, d), c = b[a], c ? c.push(k) : b[a] = [k], k
    };
    b.on = function (a, k, d, b, c, f) {
        return k.handleEvent && (d = d || k, k = k.handleEvent), d = d || this, this.addEventListener(a, function (a) {
            k.call(d, a, c);
            b && a.remove()
        }, f)
    };
    b.removeEventListener = function (a, k, d) {
        if (d = d ? this._captureListeners : this._listeners) {
            var b = d[a];
            if (b)
                for (var c = 0, f = b.length; f > c; c++)
                    if (b[c] == k) {
                        1 == f ? delete d[a] : b.splice(c, 1);
                        break
                    }
        }
    };
    b.off = b.removeEventListener;
    b.removeAllEventListeners =
        function (a) {
            a ? (this._listeners && delete this._listeners[a], this._captureListeners && delete this._captureListeners[a]) : this._listeners = this._captureListeners = null
        };
    b.dispatchEvent = function (a, k) {
        if ("string" == typeof a) {
            var d = this._listeners;
            if (!d || !d[a])
                return !1;
            a = new createjs.Event(a)
        }
        if (a.target = k || this, a.bubbles && this.parent) {
            for (var b = this, d = [b]; b.parent;)
                d.push(b = b.parent);
            for (var c = d.length, b = c - 1; 0 <= b && !a.propagationStopped; b--)
                d[b]._dispatchEvent(a, 1 + (0 == b));
            for (b = 1; c > b && !a.propagationStopped; b++)
                d[b]._dispatchEvent(a,
                    3)
        } else
            this._dispatchEvent(a, 2);
        return a.defaultPrevented
    };
    b.hasEventListener = function (a) {
        var k = this._listeners, d = this._captureListeners;
        return !!(k && k[a] || d && d[a])
    };
    b.willTrigger = function (a) {
        for (var k = this; k;) {
            if (k.hasEventListener(a))
                return !0;
            k = k.parent
        }
        return !1
    };
    b.toString = function () {
        return "[EventDispatcher]"
    };
    b._dispatchEvent = function (a, k) {
        var d, b = 1 == k ? this._captureListeners : this._listeners;
        if (a && b && (b = b[a.type]) && (d = b.length)) {
            a.currentTarget = this;
            a.eventPhase = k;
            a.removed = !1;
            for (var b = b.slice(),
                     c = 0; d > c && !a.immediatePropagationStopped; c++) {
                var f = b[c];
                f.handleEvent ? f.handleEvent(a) : f(a);
                a.removed && (this.off(a.type, f, 1 == k), a.removed = !1)
            }
        }
    };
    createjs.EventDispatcher = c
})();
this.createjs = this.createjs || {};
(function () {
    createjs.indexOf = function (c, b) {
        for (var a = 0, k = c.length; k > a; a++)
            if (b === c[a])
                return a;
        return -1
    }
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"UID cannot be instantiated";
    };
    c._nextID = 0;
    c.get = function () {
        return c._nextID++
    };
    createjs.UID = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"Ticker cannot be instantiated.";
    };
    c.RAF_SYNCHED = "synched";
    c.RAF = "raf";
    c.TIMEOUT = "timeout";
    c.useRAF = !1;
    c.timingMode = null;
    c.maxDelta = 0;
    c.removeEventListener = null;
    c.removeAllEventListeners = null;
    c.dispatchEvent = null;
    c.hasEventListener = null;
    c._listeners = null;
    createjs.EventDispatcher.initialize(c);
    c._addEventListener = c.addEventListener;
    c.addEventListener = function () {
        return !c._inited && c.init(), c._addEventListener.apply(c, arguments)
    };
    c._paused = !1;
    c._inited = !1;
    c._startTime =
        0;
    c._pausedTime = 0;
    c._ticks = 0;
    c._pausedTicks = 0;
    c._interval = 50;
    c._lastTime = 0;
    c._times = null;
    c._tickTimes = null;
    c._timerId = null;
    c._raf = !0;
    c.init = function () {
        c._inited || (c._inited = !0, c._times = [], c._tickTimes = [], c._startTime = c._getTime(), c._times.push(c._lastTime = 0), c.setInterval(c._interval))
    };
    c.reset = function () {
        if (c._raf) {
            var a = window.cancelAnimationFrame || window.webkitCancelAnimationFrame || window.mozCancelAnimationFrame || window.oCancelAnimationFrame || window.msCancelAnimationFrame;
            a && a(c._timerId)
        } else
            clearTimeout(c._timerId);
        c.removeAllEventListeners("tick")
    };
    c.setInterval = function (a) {
        c._interval = a;
        c._inited && c._setupTick()
    };
    c.getInterval = function () {
        return c._interval
    };
    c.setFPS = function (a) {
        c.setInterval(1E3 / a)
    };
    c.getFPS = function () {
        return 1E3 / c._interval
    };
    c.getMeasuredTickTime = function (a) {
        var k = 0, d = c._tickTimes;
        if (1 > d.length)
            return -1;
        a = Math.min(d.length, a || 0 | c.getFPS());
        for (var b = 0; a > b; b++)
            k += d[b];
        return k / a
    };
    c.getMeasuredFPS = function (a) {
        var k = c._times;
        return 2 > k.length ? -1 : (a = Math.min(k.length - 1, a || 0 | c.getFPS()), 1E3 / ((k[0] -
        k[a]) / a))
    };
    c.setPaused = function (a) {
        c._paused = a
    };
    c.getPaused = function () {
        return c._paused
    };
    c.getTime = function (a) {
        return c._getTime() - c._startTime - (a ? c._pausedTime : 0)
    };
    c.getEventTime = function (a) {
        return (c._lastTime || c._startTime) - (a ? c._pausedTime : 0)
    };
    c.getTicks = function (a) {
        return c._ticks - (a ? c._pausedTicks : 0)
    };
    c._handleSynch = function () {
        var a = c._getTime() - c._startTime;
        c._timerId = null;
        c._setupTick();
        a - c._lastTime >= 0.97 * (c._interval - 1) && c._tick()
    };
    c._handleRAF = function () {
        c._timerId = null;
        c._setupTick();
        c._tick()
    };
    c._handleTimeout = function () {
        c._timerId = null;
        c._setupTick();
        c._tick()
    };
    c._setupTick = function () {
        if (null == c._timerId) {
            var a = c.timingMode || c.useRAF && c.RAF_SYNCHED;
            if (a == c.RAF_SYNCHED || a == c.RAF) {
                var k = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame;
                if (k)
                    return c._timerId = k(a == c.RAF ? c._handleRAF : c._handleSynch), c._raf = !0, void 0
            }
            c._raf = !1;
            c._timerId = setTimeout(c._handleTimeout, c._interval)
        }
    };
    c._tick = function () {
        var a = c._getTime() - c._startTime, k = a - c._lastTime, d = c._paused;
        if (c._ticks++, d && (c._pausedTicks++, c._pausedTime += k), c._lastTime = a, c.hasEventListener("tick")) {
            var b = new createjs.Event("tick"), e = c.maxDelta;
            b.delta = e && k > e ? e : k;
            b.paused = d;
            b.time = a;
            b.runTime = a - c._pausedTime;
            c.dispatchEvent(b)
        }
        for (c._tickTimes.unshift(c._getTime() - a); 100 < c._tickTimes.length;)
            c._tickTimes.pop();
        for (c._times.unshift(a); 100 < c._times.length;)
            c._times.pop()
    };
    var b = window.performance && (performance.now || performance.mozNow ||
        performance.msNow || performance.oNow || performance.webkitNow);
    c._getTime = function () {
        return b && b.call(performance) || (new Date).getTime()
    };
    createjs.Ticker = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, d, b, c, f, h, l, m, n, p) {
        this.initialize(a, d, b, c, f, h, l, m, n, p)
    }, b = c.prototype = new createjs.Event;
    b.stageX = 0;
    b.stageY = 0;
    b.rawX = 0;
    b.rawY = 0;
    b.nativeEvent = null;
    b.pointerID = 0;
    b.primary = !1;
    b.addEventListener = null;
    b.removeEventListener = null;
    b.removeAllEventListeners = null;
    b.dispatchEvent = null;
    b.hasEventListener = null;
    b._listeners = null;
    createjs.EventDispatcher.initialize(b);
    b._get_localX = function () {
        return this.currentTarget.globalToLocal(this.rawX, this.rawY).x
    };
    b._get_localY = function () {
        return this.currentTarget.globalToLocal(this.rawX,
            this.rawY).y
    };
    try {
        Object.defineProperties(b, {localX: {get: b._get_localX}, localY: {get: b._get_localY}})
    } catch (a) {
    }
    b.Event_initialize = b.initialize;
    b.initialize = function (a, d, b, c, f, h, l, m, n, p) {
        this.Event_initialize(a, d, b);
        this.stageX = c;
        this.stageY = f;
        this.nativeEvent = h;
        this.pointerID = l;
        this.primary = m;
        this.rawX = null == n ? c : n;
        this.rawY = null == p ? f : p
    };
    b.clone = function () {
        return new c(this.type, this.bubbles, this.cancelable, this.stageX, this.stageY, this.target, this.nativeEvent, this.pointerID, this.primary, this.rawX,
            this.rawY)
    };
    b.toString = function () {
        return "[MouseEvent (type=" + this.type + " stageX=" + this.stageX + " stageY=" + this.stageY + ")]"
    };
    createjs.MouseEvent = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, g, c, f) {
        this.initialize(a, b, d, g, c, f)
    }, b = c.prototype;
    c.identity = null;
    c.DEG_TO_RAD = Math.PI / 180;
    b.a = 1;
    b.b = 0;
    b.c = 0;
    b.d = 1;
    b.tx = 0;
    b.ty = 0;
    b.alpha = 1;
    b.shadow = null;
    b.compositeOperation = null;
    b.initialize = function (a, b, d, g, c, f) {
        return this.a = null == a ? 1 : a, this.b = b || 0, this.c = d || 0, this.d = null == g ? 1 : g, this.tx = c || 0, this.ty = f || 0, this
    };
    b.prepend = function (a, b, d, g, c, f) {
        var h = this.tx;
        if (1 != a || 0 != b || 0 != d || 1 != g) {
            var l = this.a, m = this.c;
            this.a = l * a + this.b * d;
            this.b = l * b + this.b * g;
            this.c = m * a + this.d *
                d;
            this.d = m * b + this.d * g
        }
        return this.tx = h * a + this.ty * d + c, this.ty = h * b + this.ty * g + f, this
    };
    b.append = function (a, b, d, g, c, f) {
        var h = this.a, l = this.b, m = this.c, n = this.d;
        return this.a = a * h + b * m, this.b = a * l + b * n, this.c = d * h + g * m, this.d = d * l + g * n, this.tx = c * h + f * m + this.tx, this.ty = c * l + f * n + this.ty, this
    };
    b.prependMatrix = function (a) {
        return this.prepend(a.a, a.b, a.c, a.d, a.tx, a.ty), this.prependProperties(a.alpha, a.shadow, a.compositeOperation), this
    };
    b.appendMatrix = function (a) {
        return this.append(a.a, a.b, a.c, a.d, a.tx, a.ty), this.appendProperties(a.alpha,
            a.shadow, a.compositeOperation), this
    };
    b.prependTransform = function (a, b, d, g, e, f, h, l, m) {
        if (e % 360) {
            var n = e * c.DEG_TO_RAD;
            e = Math.cos(n);
            n = Math.sin(n)
        } else
            e = 1, n = 0;
        return (l || m) && (this.tx -= l, this.ty -= m), f || h ? (f *= c.DEG_TO_RAD, h *= c.DEG_TO_RAD, this.prepend(e * d, n * d, -n * g, e * g, 0, 0), this.prepend(Math.cos(h), Math.sin(h), -Math.sin(f), Math.cos(f), a, b)) : this.prepend(e * d, n * d, -n * g, e * g, a, b), this
    };
    b.appendTransform = function (a, b, d, g, e, f, h, l, m) {
        if (e % 360) {
            var n = e * c.DEG_TO_RAD;
            e = Math.cos(n);
            n = Math.sin(n)
        } else
            e = 1, n = 0;
        return f ||
        h ? (f *= c.DEG_TO_RAD, h *= c.DEG_TO_RAD, this.append(Math.cos(h), Math.sin(h), -Math.sin(f), Math.cos(f), a, b), this.append(e * d, n * d, -n * g, e * g, 0, 0)) : this.append(e * d, n * d, -n * g, e * g, a, b), (l || m) && (this.tx -= l * this.a + m * this.c, this.ty -= l * this.b + m * this.d), this
    };
    b.rotate = function (a) {
        var b = Math.cos(a);
        a = Math.sin(a);
        var d = this.a, g = this.c, c = this.tx;
        return this.a = d * b - this.b * a, this.b = d * a + this.b * b, this.c = g * b - this.d * a, this.d = g * a + this.d * b, this.tx = c * b - this.ty * a, this.ty = c * a + this.ty * b, this
    };
    b.skew = function (a, b) {
        return a *= c.DEG_TO_RAD,
            b *= c.DEG_TO_RAD, this.append(Math.cos(b), Math.sin(b), -Math.sin(a), Math.cos(a), 0, 0), this
    };
    b.scale = function (a, b) {
        return this.a *= a, this.d *= b, this.c *= a, this.b *= b, this.tx *= a, this.ty *= b, this
    };
    b.translate = function (a, b) {
        return this.tx += a, this.ty += b, this
    };
    b.identity = function () {
        return this.alpha = this.a = this.d = 1, this.b = this.c = this.tx = this.ty = 0, this.shadow = this.compositeOperation = null, this
    };
    b.invert = function () {
        var a = this.a, b = this.b, d = this.c, g = this.d, c = this.tx, f = a * g - b * d;
        return this.a = g / f, this.b = -b / f, this.c = -d /
            f, this.d = a / f, this.tx = (d * this.ty - g * c) / f, this.ty = -(a * this.ty - b * c) / f, this
    };
    b.isIdentity = function () {
        return 0 == this.tx && 0 == this.ty && 1 == this.a && 0 == this.b && 0 == this.c && 1 == this.d
    };
    b.transformPoint = function (a, b, d) {
        return d = d || {}, d.x = a * this.a + b * this.c + this.tx, d.y = a * this.b + b * this.d + this.ty, d
    };
    b.decompose = function (a) {
        null == a && (a = {});
        a.x = this.tx;
        a.y = this.ty;
        a.scaleX = Math.sqrt(this.a * this.a + this.b * this.b);
        a.scaleY = Math.sqrt(this.c * this.c + this.d * this.d);
        var b = Math.atan2(-this.c, this.d), d = Math.atan2(this.b, this.a);
        return b == d ? (a.rotation = d / c.DEG_TO_RAD, 0 > this.a && 0 <= this.d && (a.rotation += 0 >= a.rotation ? 180 : -180), a.skewX = a.skewY = 0) : (a.skewX = b / c.DEG_TO_RAD, a.skewY = d / c.DEG_TO_RAD), a
    };
    b.reinitialize = function (a, b, d, g, c, f, h, l, m) {
        return this.initialize(a, b, d, g, c, f), this.alpha = null == h ? 1 : h, this.shadow = l, this.compositeOperation = m, this
    };
    b.copy = function (a) {
        return this.reinitialize(a.a, a.b, a.c, a.d, a.tx, a.ty, a.alpha, a.shadow, a.compositeOperation)
    };
    b.appendProperties = function (a, b, d) {
        return this.alpha *= a, this.shadow = b || this.shadow,
            this.compositeOperation = d || this.compositeOperation, this
    };
    b.prependProperties = function (a, b, d) {
        return this.alpha *= a, this.shadow = this.shadow || b, this.compositeOperation = this.compositeOperation || d, this
    };
    b.clone = function () {
        return (new c).copy(this)
    };
    b.toString = function () {
        return "[Matrix2D (a=" + this.a + " b=" + this.b + " c=" + this.c + " d=" + this.d + " tx=" + this.tx + " ty=" + this.ty + ")]"
    };
    c.identity = new c;
    createjs.Matrix2D = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b) {
        this.initialize(a, b)
    }, b = c.prototype;
    b.x = 0;
    b.y = 0;
    b.initialize = function (a, b) {
        return this.x = null == a ? 0 : a, this.y = null == b ? 0 : b, this
    };
    b.copy = function (a) {
        return this.initialize(a.x, a.y)
    };
    b.clone = function () {
        return new c(this.x, this.y)
    };
    b.toString = function () {
        return "[Point (x=" + this.x + " y=" + this.y + ")]"
    };
    createjs.Point = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, g) {
        this.initialize(a, b, d, g)
    }, b = c.prototype;
    b.x = 0;
    b.y = 0;
    b.width = 0;
    b.height = 0;
    b.initialize = function (a, b, d, g) {
        return this.x = a || 0, this.y = b || 0, this.width = d || 0, this.height = g || 0, this
    };
    b.copy = function (a) {
        return this.initialize(a.x, a.y, a.width, a.height)
    };
    b.clone = function () {
        return new c(this.x, this.y, this.width, this.height)
    };
    b.toString = function () {
        return "[Rectangle (x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + ")]"
    };
    createjs.Rectangle = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, g, c, f, h) {
        this.initialize(a, b, d, g, c, f, h)
    }, b = c.prototype;
    b.target = null;
    b.overLabel = null;
    b.outLabel = null;
    b.downLabel = null;
    b.play = !1;
    b._isPressed = !1;
    b._isOver = !1;
    b.initialize = function (a, b, d, g, c, f, h) {
        a.addEventListener && (this.target = a, a.cursor = "pointer", this.overLabel = null == d ? "over" : d, this.outLabel = null == b ? "out" : b, this.downLabel = null == g ? "down" : g, this.play = c, this.setEnabled(!0), this.handleEvent({}), f && (h && (f.actionsEnabled = !1, f.gotoAndStop && f.gotoAndStop(h)), a.hitArea = f))
    };
    b.setEnabled = function (a) {
        var b = this.target;
        a ? (b.addEventListener("rollover", this), b.addEventListener("rollout", this), b.addEventListener("mousedown", this), b.addEventListener("pressup", this)) : (b.removeEventListener("rollover", this), b.removeEventListener("rollout", this), b.removeEventListener("mousedown", this), b.removeEventListener("pressup", this))
    };
    b.toString = function () {
        return "[ButtonHelper]"
    };
    b.handleEvent = function (a) {
        var b, d = this.target;
        a = a.type;
        "mousedown" == a ? (this._isPressed = !0, b = this.downLabel) :
            "pressup" == a ? (this._isPressed = !1, b = this._isOver ? this.overLabel : this.outLabel) : "rollover" == a ? (this._isOver = !0, b = this._isPressed ? this.downLabel : this.overLabel) : (this._isOver = !1, b = this._isPressed ? this.overLabel : this.outLabel);
        this.play ? d.gotoAndPlay && d.gotoAndPlay(b) : d.gotoAndStop && d.gotoAndStop(b)
    };
    createjs.ButtonHelper = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, g) {
        this.initialize(a, b, d, g)
    }, b = c.prototype;
    c.identity = null;
    b.color = null;
    b.offsetX = 0;
    b.offsetY = 0;
    b.blur = 0;
    b.initialize = function (a, b, d, g) {
        this.color = a;
        this.offsetX = b;
        this.offsetY = d;
        this.blur = g
    };
    b.toString = function () {
        return "[Shadow]"
    };
    b.clone = function () {
        return new c(this.color, this.offsetX, this.offsetY, this.blur)
    };
    c.identity = new c("transparent", 0, 0, 0);
    createjs.Shadow = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.EventDispatcher;
    b.complete = !0;
    b.framerate = 0;
    b._animations = null;
    b._frames = null;
    b._images = null;
    b._data = null;
    b._loadCount = 0;
    b._frameHeight = 0;
    b._frameWidth = 0;
    b._numFrames = 0;
    b._regX = 0;
    b._regY = 0;
    b.initialize = function (a) {
        var b, d, g;
        if (null != a) {
            if (this.framerate = a.framerate || 0, a.images && 0 < (d = a.images.length))
                for (g = this._images = [], b = 0; d > b; b++) {
                    var c = a.images[b];
                    if ("string" == typeof c) {
                        var f = c, c = document.createElement("img");
                        c.src = f
                    }
                    g.push(c);
                    c.getContext || c.complete || (this._loadCount++, this.complete = !1, function (a) {
                        c.onload = function () {
                            a._handleImageLoad()
                        }
                    }(this))
                }
            if (null != a.frames)
                if (a.frames instanceof Array)
                    for (this._frames = [], g = a.frames, b = 0, d = g.length; d > b; b++)
                        f = g[b], this._frames.push({
                            image: this._images[f[4] ? f[4] : 0],
                            rect: new createjs.Rectangle(f[0], f[1], f[2], f[3]),
                            regX: f[5] || 0,
                            regY: f[6] || 0
                        });
                else
                    d = a.frames, this._frameWidth = d.width, this._frameHeight = d.height, this._regX = d.regX || 0, this._regY = d.regY || 0, this._numFrames = d.count, 0 == this._loadCount &&
                    this._calculateFrames();
            if (this._animations = [], null != (d = a.animations)) {
                this._data = {};
                for (var h in d) {
                    a = {name: h};
                    f = d[h];
                    if ("number" == typeof f)
                        g = a.frames = [f];
                    else if (f instanceof Array)
                        if (1 == f.length)
                            a.frames = [f[0]];
                        else
                            for (a.speed = f[3], a.next = f[2], g = a.frames = [], b = f[0]; b <= f[1]; b++)
                                g.push(b);
                    else
                        a.speed = f.speed, a.next = f.next, b = f.frames, g = a.frames = "number" == typeof b ? [b] : b.slice(0);
                    !0 !== a.next && void 0 !== a.next || (a.next = h);
                    (!1 === a.next || 2 > g.length && a.next == h) && (a.next = null);
                    a.speed || (a.speed = 1);
                    this._animations.push(h);
                    this._data[h] = a
                }
            }
        }
    };
    b.getNumFrames = function (a) {
        if (null == a)
            return this._frames ? this._frames.length : this._numFrames;
        a = this._data[a];
        return null == a ? 0 : a.frames.length
    };
    b.getAnimations = function () {
        return this._animations.slice(0)
    };
    b.getAnimation = function (a) {
        return this._data[a]
    };
    b.getFrame = function (a) {
        var b;
        return this._frames && (b = this._frames[a]) ? b : null
    };
    b.getFrameBounds = function (a, b) {
        var d = this.getFrame(a);
        return d ? (b || new createjs.Rectangle).initialize(-d.regX, -d.regY, d.rect.width, d.rect.height) : null
    };
    b.toString = function () {
        return "[SpriteSheet]"
    };
    b.clone = function () {
        var a = new c;
        return a.complete = this.complete, a._animations = this._animations, a._frames = this._frames, a._images = this._images, a._data = this._data, a._frameHeight = this._frameHeight, a._frameWidth = this._frameWidth, a._numFrames = this._numFrames, a._loadCount = this._loadCount, a
    };
    b._handleImageLoad = function () {
        0 == --this._loadCount && (this._calculateFrames(), this.complete = !0, this.dispatchEvent("complete"))
    };
    b._calculateFrames = function () {
        if (!this._frames &&
            0 != this._frameWidth) {
            this._frames = [];
            for (var a = 0, b = this._frameWidth, d = this._frameHeight, c = 0, e = this._images; c < e.length; c++) {
                for (var f = e[c], h = 0 | f.width / b, l = 0 | f.height / d, l = 0 < this._numFrames ? Math.min(this._numFrames - a, h * l) : h * l, m = 0; l > m; m++)
                    this._frames.push({
                        image: f,
                        rect: new createjs.Rectangle(m % h * b, (0 | m / h) * d, b, d),
                        regX: this._regX,
                        regY: this._regY
                    });
                a += l
            }
            this._numFrames = a
        }
    };
    createjs.SpriteSheet = c
})();
this.createjs = this.createjs || {};
(function () {
    function c(a, b, d) {
        this.f = a;
        this.params = b;
        this.path = null == d ? !0 : d
    }

    c.prototype.exec = function (a) {
        this.f.apply(a, this.params)
    };
    var b = function () {
        this.initialize()
    }, a = b.prototype;
    b.getRGB = function (a, b, d, k) {
        return null != a && null == d && (k = b, d = 255 & a, b = 255 & a >> 8, a = 255 & a >> 16), null == k ? "rgb(" + a + "," + b + "," + d + ")" : "rgba(" + a + "," + b + "," + d + "," + k + ")"
    };
    b.getHSL = function (a, b, d, k) {
        return null == k ? "hsl(" + a % 360 + "," + b + "%," + d + "%)" : "hsla(" + a % 360 + "," + b + "%," + d + "%," + k + ")"
    };
    b.Command = c;
    b.BASE_64 = {
        A: 0,
        B: 1,
        C: 2,
        D: 3,
        E: 4,
        F: 5,
        G: 6,
        H: 7,
        I: 8,
        J: 9,
        K: 10,
        L: 11,
        M: 12,
        N: 13,
        O: 14,
        P: 15,
        Q: 16,
        R: 17,
        S: 18,
        T: 19,
        U: 20,
        V: 21,
        W: 22,
        X: 23,
        Y: 24,
        Z: 25,
        a: 26,
        b: 27,
        c: 28,
        d: 29,
        e: 30,
        f: 31,
        g: 32,
        h: 33,
        i: 34,
        j: 35,
        k: 36,
        l: 37,
        m: 38,
        n: 39,
        o: 40,
        p: 41,
        q: 42,
        r: 43,
        s: 44,
        t: 45,
        u: 46,
        v: 47,
        w: 48,
        x: 49,
        y: 50,
        z: 51,
        0: 52,
        1: 53,
        2: 54,
        3: 55,
        4: 56,
        5: 57,
        6: 58,
        7: 59,
        8: 60,
        9: 61,
        "+": 62,
        "/": 63
    };
    b.STROKE_CAPS_MAP = ["butt", "round", "square"];
    b.STROKE_JOINTS_MAP = ["miter", "round", "bevel"];
    var k = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas");
    if (k.getContext) {
        var d = b._ctx = k.getContext("2d");
        b.beginCmd = new c(d.beginPath, [], !1);
        b.fillCmd = new c(d.fill, [], !1);
        b.strokeCmd = new c(d.stroke, [], !1);
        k.width = k.height = 1
    }
    a._strokeInstructions = null;
    a._strokeStyleInstructions = null;
    a._strokeIgnoreScale = !1;
    a._fillInstructions = null;
    a._fillMatrix = null;
    a._instructions = null;
    a._oldInstructions = null;
    a._activeInstructions = null;
    a._active = !1;
    a._dirty = !1;
    a.initialize = function () {
        this.clear();
        this._ctx = b._ctx
    };
    a.isEmpty = function () {
        return !(this._instructions.length || this._oldInstructions.length || this._activeInstructions.length)
    };
    a.draw = function (a) {
        this._dirty && this._updateInstructions();
        for (var b = this._instructions, d = 0, k = b.length; k > d; d++)
            b[d].exec(a)
    };
    a.drawAsPath = function (a) {
        this._dirty && this._updateInstructions();
        for (var b, d = this._instructions, k = 0, c = d.length; c > k; k++)
            ((b = d[k]).path || 0 == k) && b.exec(a)
    };
    a.moveTo = function (a, b) {
        return this._activeInstructions.push(new c(this._ctx.moveTo, [a, b])), this
    };
    a.lineTo = function (a, b) {
        return this._dirty = this._active = !0, this._activeInstructions.push(new c(this._ctx.lineTo, [a, b])), this
    };
    a.arcTo =
        function (a, b, d, k, l) {
            return this._dirty = this._active = !0, this._activeInstructions.push(new c(this._ctx.arcTo, [a, b, d, k, l])), this
        };
    a.arc = function (a, b, d, k, l, m) {
        return this._dirty = this._active = !0, null == m && (m = !1), this._activeInstructions.push(new c(this._ctx.arc, [a, b, d, k, l, m])), this
    };
    a.quadraticCurveTo = function (a, b, d, k) {
        return this._dirty = this._active = !0, this._activeInstructions.push(new c(this._ctx.quadraticCurveTo, [a, b, d, k])), this
    };
    a.bezierCurveTo = function (a, b, d, k, l, m) {
        return this._dirty = this._active = !0, this._activeInstructions.push(new c(this._ctx.bezierCurveTo, [a, b, d, k, l, m])), this
    };
    a.rect = function (a, b, d, k) {
        return this._dirty = this._active = !0, this._activeInstructions.push(new c(this._ctx.rect, [a, b, d, k])), this
    };
    a.closePath = function () {
        return this._active && (this._dirty = !0, this._activeInstructions.push(new c(this._ctx.closePath, []))), this
    };
    a.clear = function () {
        return this._instructions = [], this._oldInstructions = [], this._activeInstructions = [], this._strokeStyleInstructions = this._strokeInstructions = this._fillInstructions =
            this._fillMatrix = null, this._active = this._dirty = this._strokeIgnoreScale = !1, this
    };
    a.beginFill = function (a) {
        return this._active && this._newPath(), this._fillInstructions = a ? [new c(this._setProp, ["fillStyle", a], !1)] : null, this._fillMatrix = null, this
    };
    a.beginLinearGradientFill = function (a, b, d, k, l, m) {
        this._active && this._newPath();
        d = this._ctx.createLinearGradient(d, k, l, m);
        k = 0;
        for (l = a.length; l > k; k++)
            d.addColorStop(b[k], a[k]);
        return this._fillInstructions = [new c(this._setProp, ["fillStyle", d], !1)], this._fillMatrix =
            null, this
    };
    a.beginRadialGradientFill = function (a, b, d, k, l, m, n, p) {
        this._active && this._newPath();
        d = this._ctx.createRadialGradient(d, k, l, m, n, p);
        k = 0;
        for (l = a.length; l > k; k++)
            d.addColorStop(b[k], a[k]);
        return this._fillInstructions = [new c(this._setProp, ["fillStyle", d], !1)], this._fillMatrix = null, this
    };
    a.beginBitmapFill = function (a, b, d) {
        this._active && this._newPath();
        a = this._ctx.createPattern(a, b || "");
        return this._fillInstructions = [new c(this._setProp, ["fillStyle", a], !1)], this._fillMatrix = d ? [d.a, d.b, d.c, d.d, d.tx,
            d.ty] : null, this
    };
    a.endFill = function () {
        return this.beginFill()
    };
    a.setStrokeStyle = function (a, d, k, h, l) {
        return this._active && this._newPath(), this._strokeStyleInstructions = [new c(this._setProp, ["lineWidth", null == a ? "1" : a], !1), new c(this._setProp, ["lineCap", null == d ? "butt" : isNaN(d) ? d : b.STROKE_CAPS_MAP[d]], !1), new c(this._setProp, ["lineJoin", null == k ? "miter" : isNaN(k) ? k : b.STROKE_JOINTS_MAP[k]], !1), new c(this._setProp, ["miterLimit", null == h ? "10" : h], !1)], this._strokeIgnoreScale = l, this
    };
    a.beginStroke = function (a) {
        return this._active &&
        this._newPath(), this._strokeInstructions = a ? [new c(this._setProp, ["strokeStyle", a], !1)] : null, this
    };
    a.beginLinearGradientStroke = function (a, b, d, k, l, m) {
        this._active && this._newPath();
        d = this._ctx.createLinearGradient(d, k, l, m);
        k = 0;
        for (l = a.length; l > k; k++)
            d.addColorStop(b[k], a[k]);
        return this._strokeInstructions = [new c(this._setProp, ["strokeStyle", d], !1)], this
    };
    a.beginRadialGradientStroke = function (a, b, d, k, l, m, n, p) {
        this._active && this._newPath();
        d = this._ctx.createRadialGradient(d, k, l, m, n, p);
        k = 0;
        for (l = a.length; l >
        k; k++)
            d.addColorStop(b[k], a[k]);
        return this._strokeInstructions = [new c(this._setProp, ["strokeStyle", d], !1)], this
    };
    a.beginBitmapStroke = function (a, b) {
        this._active && this._newPath();
        var d = this._ctx.createPattern(a, b || "");
        return this._strokeInstructions = [new c(this._setProp, ["strokeStyle", d], !1)], this
    };
    a.endStroke = function () {
        return this.beginStroke(), this
    };
    a.curveTo = a.quadraticCurveTo;
    a.drawRect = a.rect;
    a.drawRoundRect = function (a, b, d, k, c) {
        return this.drawRoundRectComplex(a, b, d, k, c, c, c, c), this
    };
    a.drawRoundRectComplex =
        function (a, b, d, k, l, m, n, p) {
            var r = (k > d ? d : k) / 2, q = 0, s = 0, u = 0, t = 0;
            0 > l && (l *= q = -1);
            l > r && (l = r);
            0 > m && (m *= s = -1);
            m > r && (m = r);
            0 > n && (n *= u = -1);
            n > r && (n = r);
            0 > p && (p *= t = -1);
            p > r && (p = r);
            this._dirty = this._active = !0;
            var r = this._ctx.arcTo, w = this._ctx.lineTo;
            return this._activeInstructions.push(new c(this._ctx.moveTo, [a + d - m, b]), new c(r, [a + d + m * s, b - m * s, a + d, b + m, m]), new c(w, [a + d, b + k - n]), new c(r, [a + d + n * u, b + k + n * u, a + d - n, b + k, n]), new c(w, [a + p, b + k]), new c(r, [a - p * t, b + k + p * t, a, b + k - p, p]), new c(w, [a, b + l]), new c(r, [a - l * q, b - l * q, a + l, b, l]),
                new c(this._ctx.closePath)), this
        };
    a.drawCircle = function (a, b, d) {
        return this.arc(a, b, d, 0, 2 * Math.PI), this
    };
    a.drawEllipse = function (a, b, d, k) {
        this._dirty = this._active = !0;
        var l = 0.5522848 * (d / 2), m = 0.5522848 * (k / 2), n = a + d, p = b + k;
        d = a + d / 2;
        k = b + k / 2;
        return this._activeInstructions.push(new c(this._ctx.moveTo, [a, k]), new c(this._ctx.bezierCurveTo, [a, k - m, d - l, b, d, b]), new c(this._ctx.bezierCurveTo, [d + l, b, n, k - m, n, k]), new c(this._ctx.bezierCurveTo, [n, k + m, d + l, p, d, p]), new c(this._ctx.bezierCurveTo, [d - l, p, a, k + m, a, k])), this
    };
    a.inject = function (a, b) {
        return this._dirty = this._active = !0, this._activeInstructions.push(new c(a, [b])), this
    };
    a.drawPolyStar = function (a, b, d, k, l, m) {
        this._dirty = this._active = !0;
        null == l && (l = 0);
        l = 1 - l;
        null == m ? m = 0 : m /= 180 / Math.PI;
        var n = Math.PI / k;
        this._activeInstructions.push(new c(this._ctx.moveTo, [a + Math.cos(m) * d, b + Math.sin(m) * d]));
        for (var p = 0; k > p; p++)
            m += n, 1 != l && this._activeInstructions.push(new c(this._ctx.lineTo, [a + Math.cos(m) * d * l, b + Math.sin(m) * d * l])), m += n, this._activeInstructions.push(new c(this._ctx.lineTo,
                [a + Math.cos(m) * d, b + Math.sin(m) * d]));
        return this
    };
    a.decodePath = function (a) {
        for (var d = [this.moveTo, this.lineTo, this.quadraticCurveTo, this.bezierCurveTo, this.closePath], k = [2, 2, 4, 6, 0], c = 0, l = a.length, m = [], n = 0, p = 0, r = b.BASE_64; l > c;) {
            var q = a.charAt(c), s = r[q], u = s >> 3, t = d[u];
            if (!t || 3 & s)
                throw"bad path data (@" + c + "): " + q;
            q = k[u];
            u || (n = p = 0);
            m.length = 0;
            c++;
            s = (1 & s >> 2) + 2;
            for (u = 0; q > u; u++) {
                var w = r[a.charAt(c)], z = w >> 5 ? -1 : 1, w = (31 & w) << 6 | r[a.charAt(c + 1)];
                3 == s && (w = w << 6 | r[a.charAt(c + 2)]);
                w = z * w / 10;
                u % 2 ? n = w += n : p = w += p;
                m[u] = w;
                c += s
            }
            t.apply(this, m)
        }
        return this
    };
    a.clone = function () {
        var a = new b;
        return a._instructions = this._instructions.slice(), a._activeInstructions = this._activeInstructions.slice(), a._oldInstructions = this._oldInstructions.slice(), this._fillInstructions && (a._fillInstructions = this._fillInstructions.slice()), this._strokeInstructions && (a._strokeInstructions = this._strokeInstructions.slice()), this._strokeStyleInstructions && (a._strokeStyleInstructions = this._strokeStyleInstructions.slice()), a._active = this._active, a._dirty =
            this._dirty, a._fillMatrix = this._fillMatrix, a._strokeIgnoreScale = this._strokeIgnoreScale, a
    };
    a.toString = function () {
        return "[Graphics]"
    };
    a.mt = a.moveTo;
    a.lt = a.lineTo;
    a.at = a.arcTo;
    a.bt = a.bezierCurveTo;
    a.qt = a.quadraticCurveTo;
    a.a = a.arc;
    a.r = a.rect;
    a.cp = a.closePath;
    a.c = a.clear;
    a.f = a.beginFill;
    a.lf = a.beginLinearGradientFill;
    a.rf = a.beginRadialGradientFill;
    a.bf = a.beginBitmapFill;
    a.ef = a.endFill;
    a.ss = a.setStrokeStyle;
    a.s = a.beginStroke;
    a.ls = a.beginLinearGradientStroke;
    a.rs = a.beginRadialGradientStroke;
    a.bs = a.beginBitmapStroke;
    a.es = a.endStroke;
    a.dr = a.drawRect;
    a.rr = a.drawRoundRect;
    a.rc = a.drawRoundRectComplex;
    a.dc = a.drawCircle;
    a.de = a.drawEllipse;
    a.dp = a.drawPolyStar;
    a.p = a.decodePath;
    a._updateInstructions = function () {
        this._instructions = this._oldInstructions.slice();
        this._instructions.push(b.beginCmd);
        this._appendInstructions(this._fillInstructions);
        this._appendInstructions(this._strokeInstructions);
        this._appendInstructions(this._strokeInstructions && this._strokeStyleInstructions);
        this._appendInstructions(this._activeInstructions);
        this._fillInstructions && this._appendDraw(b.fillCmd, this._fillMatrix);
        this._strokeInstructions && this._appendDraw(b.strokeCmd, this._strokeIgnoreScale && [1, 0, 0, 1, 0, 0])
    };
    a._appendInstructions = function (a) {
        a && this._instructions.push.apply(this._instructions, a)
    };
    a._appendDraw = function (a, b) {
        b ? this._instructions.push(new c(this._ctx.save, [], !1), new c(this._ctx.transform, b, !1), a, new c(this._ctx.restore, [], !1)) : this._instructions.push(a)
    };
    a._newPath = function () {
        this._dirty && this._updateInstructions();
        this._oldInstructions =
            this._instructions;
        this._activeInstructions = [];
        this._active = this._dirty = !1
    };
    a._setProp = function (a, b) {
        this[a] = b
    };
    createjs.Graphics = b
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        this.initialize()
    }, b = c.prototype = new createjs.EventDispatcher;
    c._MOUSE_EVENTS = "click dblclick mousedown mouseout mouseover pressmove pressup rollout rollover".split(" ");
    c.suppressCrossDomainErrors = !1;
    var a = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas");
    a.getContext && (c._hitTestCanvas = a, c._hitTestContext = a.getContext("2d"), a.width = a.height = 1);
    c._nextCacheID = 1;
    b.alpha = 1;
    b.cacheCanvas = null;
    b.id = -1;
    b.mouseEnabled = !0;
    b.tickEnabled = !0;
    b.name =
        null;
    b.parent = null;
    b.regX = 0;
    b.regY = 0;
    b.rotation = 0;
    b.scaleX = 1;
    b.scaleY = 1;
    b.skewX = 0;
    b.skewY = 0;
    b.shadow = null;
    b.visible = !0;
    b.x = 0;
    b.y = 0;
    b.compositeOperation = null;
    b.snapToPixel = !1;
    b.filters = null;
    b.cacheID = 0;
    b.mask = null;
    b.hitArea = null;
    b.cursor = null;
    b._cacheOffsetX = 0;
    b._cacheOffsetY = 0;
    b._cacheScale = 1;
    b._cacheDataURLID = 0;
    b._cacheDataURL = null;
    b._matrix = null;
    b._rectangle = null;
    b._bounds = null;
    b.initialize = function () {
        this.id = createjs.UID.get();
        this._matrix = new createjs.Matrix2D;
        this._rectangle = new createjs.Rectangle
    };
    b.isVisible = function () {
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY)
    };
    b.draw = function (a, b) {
        var c = this.cacheCanvas;
        if (b || !c)
            return !1;
        var e, f = this._cacheScale, h = this._cacheOffsetX, l = this._cacheOffsetY;
        return (e = this._applyFilterBounds(h, l, 0, 0)) && (h = e.x, l = e.y), a.drawImage(c, h, l, c.width / f, c.height / f), !0
    };
    b.updateContext = function (a) {
        var b, c = this.mask;
        c && c.graphics && !c.graphics.isEmpty() && (b = c.getMatrix(c._matrix), a.transform(b.a, b.b, b.c, b.d, b.tx, b.ty), c.graphics.drawAsPath(a), a.clip(),
            b.invert(), a.transform(b.a, b.b, b.c, b.d, b.tx, b.ty));
        b = this._matrix.identity().appendTransform(this.x, this.y, this.scaleX, this.scaleY, this.rotation, this.skewX, this.skewY, this.regX, this.regY);
        createjs.Stage._snapToPixelEnabled && this.snapToPixel ? a.transform(b.a, b.b, b.c, b.d, 0 | b.tx + 0.5, 0 | b.ty + 0.5) : a.transform(b.a, b.b, b.c, b.d, b.tx, b.ty);
        a.globalAlpha *= this.alpha;
        this.compositeOperation && (a.globalCompositeOperation = this.compositeOperation);
        this.shadow && this._applyShadow(a, this.shadow)
    };
    b.cache = function (a,
                        b, c, e, f) {
        f = f || 1;
        this.cacheCanvas || (this.cacheCanvas = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas"));
        this._cacheWidth = c;
        this._cacheHeight = e;
        this._cacheOffsetX = a;
        this._cacheOffsetY = b;
        this._cacheScale = f;
        this.updateCache()
    };
    b.updateCache = function (a) {
        var b, g = this.cacheCanvas, e = this._cacheScale, f = this._cacheOffsetX * e, h = this._cacheOffsetY * e, l = this._cacheWidth, m = this._cacheHeight;
        if (!g)
            throw"cache() must be called before updateCache()";
        var n = g.getContext("2d");
        (b = this._applyFilterBounds(f,
            h, l, m)) && (f = b.x, h = b.y, l = b.width, m = b.height);
        l = Math.ceil(l * e);
        m = Math.ceil(m * e);
        l != g.width || m != g.height ? (g.width = l, g.height = m) : a || n.clearRect(0, 0, l + 1, m + 1);
        n.save();
        n.globalCompositeOperation = a;
        n.setTransform(e, 0, 0, e, -f, -h);
        this.draw(n, !0);
        this._applyFilters();
        n.restore();
        this.cacheID = c._nextCacheID++
    };
    b.uncache = function () {
        this._cacheDataURL = this.cacheCanvas = null;
        this.cacheID = this._cacheOffsetX = this._cacheOffsetY = 0;
        this._cacheScale = 1
    };
    b.getCacheDataURL = function () {
        return this.cacheCanvas ? (this.cacheID !=
        this._cacheDataURLID && (this._cacheDataURL = this.cacheCanvas.toDataURL()), this._cacheDataURL) : null
    };
    b.getStage = function () {
        for (var a = this; a.parent;)
            a = a.parent;
        return a instanceof createjs.Stage ? a : null
    };
    b.localToGlobal = function (a, b) {
        var c = this.getConcatenatedMatrix(this._matrix);
        return null == c ? null : (c.append(1, 0, 0, 1, a, b), new createjs.Point(c.tx, c.ty))
    };
    b.globalToLocal = function (a, b) {
        var c = this.getConcatenatedMatrix(this._matrix);
        return null == c ? null : (c.invert(), c.append(1, 0, 0, 1, a, b), new createjs.Point(c.tx,
            c.ty))
    };
    b.localToLocal = function (a, b, c) {
        a = this.localToGlobal(a, b);
        return c.globalToLocal(a.x, a.y)
    };
    b.setTransform = function (a, b, c, e, f, h, l, m, n) {
        return this.x = a || 0, this.y = b || 0, this.scaleX = null == c ? 1 : c, this.scaleY = null == e ? 1 : e, this.rotation = f || 0, this.skewX = h || 0, this.skewY = l || 0, this.regX = m || 0, this.regY = n || 0, this
    };
    b.getMatrix = function (a) {
        return (a ? a.identity() : new createjs.Matrix2D).appendTransform(this.x, this.y, this.scaleX, this.scaleY, this.rotation, this.skewX, this.skewY, this.regX, this.regY).appendProperties(this.alpha,
            this.shadow, this.compositeOperation)
    };
    b.getConcatenatedMatrix = function (a) {
        a ? a.identity() : a = new createjs.Matrix2D;
        for (var b = this; null != b;)
            a.prependTransform(b.x, b.y, b.scaleX, b.scaleY, b.rotation, b.skewX, b.skewY, b.regX, b.regY).prependProperties(b.alpha, b.shadow, b.compositeOperation), b = b.parent;
        return a
    };
    b.hitTest = function (a, b) {
        var g = c._hitTestContext;
        g.setTransform(1, 0, 0, 1, -a, -b);
        this.draw(g);
        var e = this._testHit(g);
        return g.setTransform(1, 0, 0, 1, 0, 0), g.clearRect(0, 0, 2, 2), e
    };
    b.set = function (a) {
        for (var b in a)
            this[b] =
                a[b];
        return this
    };
    b.getBounds = function () {
        if (this._bounds)
            return this._rectangle.copy(this._bounds);
        var a = this.cacheCanvas;
        if (a) {
            var b = this._cacheScale;
            return this._rectangle.initialize(this._cacheOffsetX, this._cacheOffsetY, a.width / b, a.height / b)
        }
        return null
    };
    b.getTransformedBounds = function () {
        return this._getBounds()
    };
    b.setBounds = function (a, b, c, e) {
        null == a && (this._bounds = a);
        this._bounds = (this._bounds || new createjs.Rectangle).initialize(a, b, c, e)
    };
    b.clone = function () {
        var a = new c;
        return this.cloneProps(a),
            a
    };
    b.toString = function () {
        return "[DisplayObject (name=" + this.name + ")]"
    };
    b.cloneProps = function (a) {
        a.alpha = this.alpha;
        a.name = this.name;
        a.regX = this.regX;
        a.regY = this.regY;
        a.rotation = this.rotation;
        a.scaleX = this.scaleX;
        a.scaleY = this.scaleY;
        a.shadow = this.shadow;
        a.skewX = this.skewX;
        a.skewY = this.skewY;
        a.visible = this.visible;
        a.x = this.x;
        a.y = this.y;
        a._bounds = this._bounds;
        a.mouseEnabled = this.mouseEnabled;
        a.compositeOperation = this.compositeOperation
    };
    b._applyShadow = function (a, b) {
        b = b || Shadow.identity;
        a.shadowColor =
            b.color;
        a.shadowOffsetX = b.offsetX;
        a.shadowOffsetY = b.offsetY;
        a.shadowBlur = b.blur
    };
    b._tick = function (a) {
        var b = this._listeners;
        b && b.tick && (b = new createjs.Event("tick"), b.params = a, this._dispatchEvent(b, this, 2))
    };
    b._testHit = function (a) {
        try {
            var b = 1 < a.getImageData(0, 0, 1, 1).data[3]
        } catch (g) {
            if (!c.suppressCrossDomainErrors)
                throw"An error has occurred. This is most likely due to security restrictions on reading canvas pixel data with local or cross-domain images.";
        }
        return b
    };
    b._applyFilters = function () {
        if (this.filters &&
            0 != this.filters.length && this.cacheCanvas)
            for (var a = this.filters.length, b = this.cacheCanvas.getContext("2d"), c = this.cacheCanvas.width, e = this.cacheCanvas.height, f = 0; a > f; f++)
                this.filters[f].applyFilter(b, 0, 0, c, e)
    };
    b._applyFilterBounds = function (a, b, c, e) {
        var f, h, l = this.filters;
        if (!l || !(h = l.length))
            return null;
        for (l = 0; h > l; l++) {
            var m = this.filters[l];
            (m = m.getBounds && m.getBounds()) && (f || (f = this._rectangle.initialize(a, b, c, e)), f.x += m.x, f.y += m.y, f.width += m.width, f.height += m.height)
        }
        return f
    };
    b._getBounds = function (a,
                             b) {
        return this._transformBounds(this.getBounds(), a, b)
    };
    b._transformBounds = function (a, b, c) {
        if (!a)
            return a;
        var e = a.x, f = a.y, h = a.width, l = a.height, m = c ? this._matrix.identity() : this.getMatrix(this._matrix);
        (e || f) && m.appendTransform(0, 0, 1, 1, 0, 0, 0, -e, -f);
        b && m.prependMatrix(b);
        b = h * m.a;
        h *= m.b;
        c = l * m.c;
        var l = l * m.d, n = m.tx, m = m.ty, p = n, r = n, q = m, s = m;
        return (e = b + n) < p ? p = e : e > r && (r = e), (e = b + c + n) < p ? p = e : e > r && (r = e), (e = c + n) < p ? p = e : e > r && (r = e), (f = h + m) < q ? q = f : f > s && (s = f), (f = h + l + m) < q ? q = f : f > s && (s = f), (f = l + m) < q ? q = f : f > s && (s = f), a.initialize(p,
            q, r - p, s - q)
    };
    b._hasMouseEventListener = function () {
        for (var a = c._MOUSE_EVENTS, b = 0, g = a.length; g > b; b++)
            if (this.hasEventListener(a[b]))
                return !0;
        return !!this.cursor
    };
    createjs.DisplayObject = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        this.initialize()
    }, b = c.prototype = new createjs.DisplayObject;
    b.children = null;
    b.mouseChildren = !0;
    b.tickChildren = !0;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function () {
        this.DisplayObject_initialize();
        this.children = []
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || this.children.length;
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY && a)
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        if (this.DisplayObject_draw(a, b))
            return !0;
        for (var d = this.children.slice(0),
                 c = 0, e = d.length; e > c; c++) {
            var f = d[c];
            f.isVisible() && (a.save(), f.updateContext(a), f.draw(a), a.restore())
        }
        return !0
    };
    b.addChild = function (a) {
        if (null == a)
            return a;
        var b = arguments.length;
        if (1 < b) {
            for (var d = 0; b > d; d++)
                this.addChild(arguments[d]);
            return arguments[b - 1]
        }
        return a.parent && a.parent.removeChild(a), a.parent = this, this.children.push(a), a
    };
    b.addChildAt = function (a, b) {
        var d = arguments.length, c = arguments[d - 1];
        if (0 > c || c > this.children.length)
            return arguments[d - 2];
        if (2 < d) {
            for (var e = 0; d - 1 > e; e++)
                this.addChildAt(arguments[e],
                    c + e);
            return arguments[d - 2]
        }
        return a.parent && a.parent.removeChild(a), a.parent = this, this.children.splice(b, 0, a), a
    };
    b.removeChild = function (a) {
        var b = arguments.length;
        if (1 < b) {
            for (var d = !0, c = 0; b > c; c++)
                d = d && this.removeChild(arguments[c]);
            return d
        }
        return this.removeChildAt(createjs.indexOf(this.children, a))
    };
    b.removeChildAt = function (a) {
        var b = arguments.length;
        if (1 < b) {
            for (var d = [], c = 0; b > c; c++)
                d[c] = arguments[c];
            d.sort(function (a, b) {
                return b - a
            });
            for (var e = !0, c = 0; b > c; c++)
                e = e && this.removeChildAt(d[c]);
            return e
        }
        if (0 >
            a || a > this.children.length - 1)
            return !1;
        b = this.children[a];
        return b && (b.parent = null), this.children.splice(a, 1), !0
    };
    b.removeAllChildren = function () {
        for (var a = this.children; a.length;)
            a.pop().parent = null
    };
    b.getChildAt = function (a) {
        return this.children[a]
    };
    b.getChildByName = function (a) {
        for (var b = this.children, d = 0, c = b.length; c > d; d++)
            if (b[d].name == a)
                return b[d];
        return null
    };
    b.sortChildren = function (a) {
        this.children.sort(a)
    };
    b.getChildIndex = function (a) {
        return createjs.indexOf(this.children, a)
    };
    b.getNumChildren =
        function () {
            return this.children.length
        };
    b.swapChildrenAt = function (a, b) {
        var d = this.children, c = d[a], e = d[b];
        c && e && (d[a] = e, d[b] = c)
    };
    b.swapChildren = function (a, b) {
        for (var d, c, e = this.children, f = 0, h = e.length; h > f && (e[f] == a && (d = f), e[f] == b && (c = f), null == d || null == c); f++)
            ;
        f != h && (e[d] = b, e[c] = a)
    };
    b.setChildIndex = function (a, b) {
        var d = this.children, c = d.length;
        if (!(a.parent != this || 0 > b || b >= c)) {
            for (var e = 0; c > e && d[e] != a; e++)
                ;
            e != c && e != b && (d.splice(e, 1), d.splice(b, 0, a))
        }
    };
    b.contains = function (a) {
        for (; a;) {
            if (a == this)
                return !0;
            a = a.parent
        }
        return !1
    };
    b.hitTest = function (a, b) {
        return null != this.getObjectUnderPoint(a, b)
    };
    b.getObjectsUnderPoint = function (a, b) {
        var d = [], c = this.localToGlobal(a, b);
        return this._getObjectsUnderPoint(c.x, c.y, d), d
    };
    b.getObjectUnderPoint = function (a, b) {
        var d = this.localToGlobal(a, b);
        return this._getObjectsUnderPoint(d.x, d.y)
    };
    b.DisplayObject_getBounds = b.getBounds;
    b.getBounds = function () {
        return this._getBounds(null, !0)
    };
    b.getTransformedBounds = function () {
        return this._getBounds()
    };
    b.clone = function (a) {
        var b = new c;
        if (this.cloneProps(b), a)
            for (var d = b.children = [], g = 0, e = this.children.length; e > g; g++) {
                var f = this.children[g].clone(a);
                f.parent = b;
                d.push(f)
            }
        return b
    };
    b.toString = function () {
        return "[Container (name=" + this.name + ")]"
    };
    b.DisplayObject__tick = b._tick;
    b._tick = function (a) {
        if (this.tickChildren)
            for (var b = this.children.length - 1; 0 <= b; b--) {
                var d = this.children[b];
                d.tickEnabled && d._tick && d._tick(a)
            }
        this.DisplayObject__tick(a)
    };
    b._getObjectsUnderPoint = function (a, b, d, g, e) {
        var f = createjs.DisplayObject._hitTestContext,
            h = this._matrix;
        e = e || g && this._hasMouseEventListener();
        for (var l = this.children, m = l.length - 1; 0 <= m; m--) {
            var n = l[m], p = n.hitArea;
            if (n.visible && (p || n.isVisible()) && (!g || n.mouseEnabled))
                if (!p && n instanceof c) {
                    if (n = n._getObjectsUnderPoint(a, b, d, g, e), !d && n)
                        return g && !this.mouseChildren ? this : n
                } else if (e || n._hasMouseEventListener())
                    if (n.getConcatenatedMatrix(h), p && (h.appendTransform(p.x, p.y, p.scaleX, p.scaleY, p.rotation, p.skewX, p.skewY, p.regX, p.regY), h.alpha = p.alpha), f.globalAlpha = h.alpha, f.setTransform(h.a, h.b,
                            h.c, h.d, h.tx - a, h.ty - b), (p || n).draw(f), this._testHit(f)) {
                        if (f.setTransform(1, 0, 0, 1, 0, 0), f.clearRect(0, 0, 2, 2), !d)
                            return g && !this.mouseChildren ? this : n;
                        d.push(n)
                    }
        }
        return null
    };
    b._getBounds = function (a, b) {
        var d = this.DisplayObject_getBounds();
        if (d)
            return this._transformBounds(d, a, b);
        var c, e, f, h, l = b ? this._matrix.identity() : this.getMatrix(this._matrix);
        a && l.prependMatrix(a);
        for (var m = this.children.length, n = 0; m > n; n++) {
            var p = this.children[n];
            if (p.visible && (d = p._getBounds(l))) {
                var p = d.x, r = d.y, q = p + d.width, s = r +
                    d.height;
                (c > p || null == c) && (c = p);
                (q > e || null == e) && (e = q);
                (f > r || null == f) && (f = r);
                (s > h || null == h) && (h = s)
            }
        }
        return null == e ? null : this._rectangle.initialize(c, f, e - c, h - f)
    };
    createjs.Container = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.Container;
    c._snapToPixelEnabled = !1;
    b.autoClear = !0;
    b.canvas = null;
    b.mouseX = 0;
    b.mouseY = 0;
    b.snapToPixelEnabled = !1;
    b.mouseInBounds = !1;
    b.tickOnUpdate = !0;
    b.mouseMoveOutside = !1;
    b.nextStage = null;
    b._pointerData = null;
    b._pointerCount = 0;
    b._primaryPointerID = null;
    b._mouseOverIntervalID = null;
    b.Container_initialize = b.initialize;
    b.initialize = function (a) {
        this.Container_initialize();
        this.canvas = "string" == typeof a ? document.getElementById(a) :
            a;
        this._pointerData = {};
        this.enableDOMEvents(!0)
    };
    b.update = function () {
        if (this.canvas) {
            this.tickOnUpdate && (this.dispatchEvent("tickstart"), this.tickEnabled && this._tick(arguments.length ? arguments : null), this.dispatchEvent("tickend"));
            this.dispatchEvent("drawstart");
            c._snapToPixelEnabled = this.snapToPixelEnabled;
            this.autoClear && this.clear();
            var a = this.canvas.getContext("2d");
            a.save();
            this.updateContext(a);
            this.draw(a, !1);
            a.restore();
            this.dispatchEvent("drawend")
        }
    };
    b.handleEvent = function (a) {
        "tick" == a.type &&
        this.update(a)
    };
    b.clear = function () {
        if (this.canvas) {
            var a = this.canvas.getContext("2d");
            a.setTransform(1, 0, 0, 1, 0, 0);
            a.clearRect(0, 0, this.canvas.width + 1, this.canvas.height + 1)
        }
    };
    b.toDataURL = function (a, b) {
        b || (b = "image/png");
        var d, c = this.canvas.getContext("2d"), e = this.canvas.width, f = this.canvas.height;
        if (a) {
            d = c.getImageData(0, 0, e, f);
            var h = c.globalCompositeOperation;
            c.globalCompositeOperation = "destination-over";
            c.fillStyle = a;
            c.fillRect(0, 0, e, f)
        }
        var l = this.canvas.toDataURL(b);
        return a && (c.clearRect(0, 0, e +
            1, f + 1), c.putImageData(d, 0, 0), c.globalCompositeOperation = h), l
    };
    b.enableMouseOver = function (a) {
        if (this._mouseOverIntervalID && (clearInterval(this._mouseOverIntervalID), this._mouseOverIntervalID = null, 0 == a && this._testMouseOver(!0)), null == a)
            a = 20;
        else if (0 >= a)
            return;
        var b = this;
        this._mouseOverIntervalID = setInterval(function () {
            b._testMouseOver()
        }, 1E3 / Math.min(50, a))
    };
    b.enableDOMEvents = function (a) {
        null == a && (a = !0);
        var b, d = this._eventListeners;
        if (!a && d) {
            for (b in d)
                a = d[b], a.t.removeEventListener(b, a.f, !1);
            this._eventListeners =
                null
        } else if (a && !d && this.canvas) {
            a = window.addEventListener ? window : document;
            var c = this, d = this._eventListeners = {};
            d.mouseup = {
                t: a, f: function (a) {
                    c._handleMouseUp(a)
                }
            };
            d.mousemove = {
                t: a, f: function (a) {
                    c._handleMouseMove(a)
                }
            };
            d.dblclick = {
                t: this.canvas, f: function (a) {
                    c._handleDoubleClick(a)
                }
            };
            d.mousedown = {
                t: this.canvas, f: function (a) {

                    c._handleMouseDown(a);
                }
            };
            for (b in d)
                a = d[b], a.t.addEventListener(b, a.f, !1)
        }
    };
    b.clone = function () {
        var a = new c(null);
        return this.cloneProps(a), a
    };
    b.toString = function () {
        return "[Stage (name=" +
            this.name + ")]"
    };
    b._getElementRect = function (a) {
        var b;
        try {
            b = a.getBoundingClientRect()
        } catch (d) {
            b = {top: a.offsetTop, left: a.offsetLeft, width: a.offsetWidth, height: a.offsetHeight}
        }
        var c = (window.pageXOffset || document.scrollLeft || 0) - (document.clientLeft || document.body.clientLeft || 0), e = (window.pageYOffset || document.scrollTop || 0) - (document.clientTop || document.body.clientTop || 0), f = window.getComputedStyle ? getComputedStyle(a) : a.currentStyle;
        a = parseInt(f.paddingLeft) + parseInt(f.borderLeftWidth);
        var h = parseInt(f.paddingTop) +
            parseInt(f.borderTopWidth), l = parseInt(f.paddingRight) + parseInt(f.borderRightWidth), f = parseInt(f.paddingBottom) + parseInt(f.borderBottomWidth);
        return {left: b.left + c + a, right: b.right + c - l, top: b.top + e + h, bottom: b.bottom + e - f}
    };
    b._getPointerData = function (a) {
        var b = this._pointerData[a];
        return b || (b = this._pointerData[a] = {
            x: 0,
            y: 0
        }, null == this._primaryPointerID && (this._primaryPointerID = a), (null == this._primaryPointerID || -1 == this._primaryPointerID) && (this._primaryPointerID = a)), b
    };
    b._handleMouseMove = function (a) {
        a ||
        (a = window.event);
        this._handlePointerMove(-1, a, a.pageX, a.pageY)
    };
    b._handlePointerMove = function (a, b, d, c) {
        if (this.canvas) {
            var e = this._getPointerData(a), f = e.inBounds;
            if (this._updatePointerPosition(a, b, d, c), f || e.inBounds || this.mouseMoveOutside)
                -1 == a && e.inBounds == !f && this._dispatchMouseEvent(this, f ? "mouseleave" : "mouseenter", !1, a, e, b), this._dispatchMouseEvent(this, "stagemousemove", !1, a, e, b), this._dispatchMouseEvent(e.target, "pressmove", !0, a, e, b), (f = e.event) && f.hasEventListener("mousemove") && f.dispatchEvent(new createjs.MouseEvent("mousemove",
                    !1, !1, e.x, e.y, b, a, a == this._primaryPointerID, e.rawX, e.rawY), e.target), this.nextStage && this.nextStage._handlePointerMove(a, b, d, c)
        }
    };
    b._updatePointerPosition = function (a, b, d, c) {
        var e = this._getElementRect(this.canvas);
        d -= e.left;
        c -= e.top;
        var f = this.canvas.width, h = this.canvas.height;
        d /= (e.right - e.left) / f;
        c /= (e.bottom - e.top) / h;
        e = this._getPointerData(a);
        (e.inBounds = 0 <= d && 0 <= c && f - 1 >= d && h - 1 >= c) ? (e.x = d, e.y = c) : this.mouseMoveOutside && (e.x = 0 > d ? 0 : d > f - 1 ? f - 1 : d, e.y = 0 > c ? 0 : c > h - 1 ? h - 1 : c);
        e.posEvtObj = b;
        e.rawX = d;
        e.rawY = c;
        a == this._primaryPointerID && (this.mouseX = e.x, this.mouseY = e.y, this.mouseInBounds = e.inBounds)
    };
    b._handleMouseUp = function (a) {
        this._handlePointerUp(-1, a, !1)
    };
    b._handlePointerUp = function (a, b, d) {
        var c = this._getPointerData(a);
        this._dispatchMouseEvent(this, "stagemouseup", !1, a, c, b);
        var e = c.target;
        e && (this._getObjectsUnderPoint(c.x, c.y, null, !0) == e && this._dispatchMouseEvent(e, "click", !0, a, c, b), this._dispatchMouseEvent(e, "pressup", !0, a, c, b));
        var f = c.event;
        f && f.hasEventListener("mouseup") && f.dispatchEvent(new createjs.MouseEvent("mouseup",
            !1, !1, c.x, c.y, b, a, a == this._primaryPointerID, c.rawX, c.rawY), e);
        d ? (a == this._primaryPointerID && (this._primaryPointerID = null), delete this._pointerData[a]) : c.event = c.target = null;
        this.nextStage && this.nextStage._handlePointerUp(a, b, d)
    };
    b._handleMouseDown = function (a) {
        this._handlePointerDown(-1, a, a.pageX, a.pageY)
    };
    b._handlePointerDown = function (a, b, d, c) {
        null != c && this._updatePointerPosition(a, b, d, c);
        var e = this._getPointerData(a);
        this._dispatchMouseEvent(this, "stagemousedown", !1, a, e, b);
        e.target = this._getObjectsUnderPoint(e.x,
            e.y, null, !0);
        e.event = this._dispatchMouseEvent(e.target, "mousedown", !0, a, e, b);
        this.nextStage && this.nextStage._handlePointerDown(a, b, d, c)
    };
    b._testMouseOver = function (a) {
        if (-1 == this._primaryPointerID && (a || this.mouseX != this._mouseOverX || this.mouseY != this._mouseOverY || !this.mouseInBounds)) {
            var b, d, c, e = this._getPointerData(-1), f = e.posEvtObj, h = -1;
            c = "";
            (a || this.mouseInBounds && f && f.target == this.canvas) && (b = this._getObjectsUnderPoint(this.mouseX, this.mouseY, null, !0), this._mouseOverX = this.mouseX, this._mouseOverY =
                this.mouseY);
            a = this._mouseOverTarget || [];
            var l = a[a.length - 1], m = this._mouseOverTarget = [];
            for (d = b; d;)
                m.unshift(d), null != d.cursor && (c = d.cursor), d = d.parent;
            this.canvas.style.cursor = c;
            c = 0;
            for (d = m.length; d > c && m[c] == a[c]; c++)
                h = c;
            l != b && this._dispatchMouseEvent(l, "mouseout", !0, -1, e, f);
            for (c = a.length - 1; c > h; c--)
                this._dispatchMouseEvent(a[c], "rollout", !1, -1, e, f);
            for (c = m.length - 1; c > h; c--)
                this._dispatchMouseEvent(m[c], "rollover", !1, -1, e, f);
            l != b && this._dispatchMouseEvent(b, "mouseover", !0, -1, e, f)
        }
    };
    b._handleDoubleClick =
        function (a) {
            var b = this._getPointerData(-1), d = this._getObjectsUnderPoint(b.x, b.y, null, !0);
            this._dispatchMouseEvent(d, "dblclick", !0, -1, b, a);
            this.nextStage && this.nextStage._handleDoubleClick(a)
        };
    b._dispatchMouseEvent = function (a, b, d, c, e, f) {
        if (a && (d || a.hasEventListener(b)))
            return b = new createjs.MouseEvent(b, d, !1, e.x, e.y, f, c, c == this._primaryPointerID, e.rawX, e.rawY), a.dispatchEvent(b), b
    };
    createjs.Stage = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.DisplayObject;
    b.image = null;
    b.snapToPixel = !0;
    b.sourceRect = null;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a) {
        this.DisplayObject_initialize();
        "string" == typeof a ? (this.image = document.createElement("img"), this.image.src = a) : this.image = a
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || this.image && (this.image.complete || this.image.getContext || 2 <= this.image.readyState);
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX &&
        0 != this.scaleY && a)
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        if (this.DisplayObject_draw(a, b))
            return !0;
        var d = this.sourceRect;
        return d ? a.drawImage(this.image, d.x, d.y, d.width, d.height, 0, 0, d.width, d.height) : a.drawImage(this.image, 0, 0), !0
    };
    b.DisplayObject_getBounds = b.getBounds;
    b.getBounds = function () {
        var a = this.DisplayObject_getBounds();
        if (a)
            return a;
        a = this.sourceRect || this.image;
        return this.image && (this.image.complete || this.image.getContext || 2 <= this.image.readyState) ? this._rectangle.initialize(0,
            0, a.width, a.height) : null
    };
    b.clone = function () {
        var a = new c(this.image);
        return this.sourceRect && (a.sourceRect = this.sourceRect.clone()), this.cloneProps(a), a
    };
    b.toString = function () {
        return "[Bitmap (name=" + this.name + ")]"
    };
    createjs.Bitmap = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b) {
        this.initialize(a, b)
    }, b = c.prototype = new createjs.DisplayObject;
    b.currentFrame = 0;
    b.currentAnimation = null;
    b.paused = !0;
    b.spriteSheet = null;
    b.snapToPixel = !0;
    b.offset = 0;
    b.currentAnimationFrame = 0;
    b.framerate = 0;
    b._advanceCount = 0;
    b._animation = null;
    b._currentFrame = null;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a, b) {
        this.DisplayObject_initialize();
        this.spriteSheet = a;
        b && this.gotoAndPlay(b)
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || this.spriteSheet.complete;
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY && a)
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        if (this.DisplayObject_draw(a, b))
            return !0;
        this._normalizeFrame();
        var d = this.spriteSheet.getFrame(0 | this._currentFrame);
        if (!d)
            return !1;
        var c = d.rect;
        return a.drawImage(d.image, c.x, c.y, c.width, c.height, -d.regX, -d.regY, c.width, c.height), !0
    };
    b.play = function () {
        this.paused = !1
    };
    b.stop = function () {
        this.paused = !0
    };
    b.gotoAndPlay = function (a) {
        this.paused = !1;
        this._goto(a)
    };
    b.gotoAndStop = function (a) {
        this.paused = !0;
        this._goto(a)
    };
    b.advance = function (a) {
        var b = this._animation && this._animation.speed || 1, d = this.framerate || this.spriteSheet.framerate;
        a = d && null != a ? a / (1E3 / d) : 1;
        this._animation ? this.currentAnimationFrame += a * b : this._currentFrame += a * b;
        this._normalizeFrame()
    };
    b.DisplayObject_getBounds = b.getBounds;
    b.getBounds = function () {
        return this.DisplayObject_getBounds() || this.spriteSheet.getFrameBounds(this.currentFrame, this._rectangle)
    };
    b.clone = function () {
        var a = new c(this.spriteSheet);
        return this.cloneProps(a), a
    };
    b.toString =
        function () {
            return "[Sprite (name=" + this.name + ")]"
        };
    b.DisplayObject__tick = b._tick;
    b._tick = function (a) {
        this.paused || this.advance(a && a[0] && a[0].delta);
        this.DisplayObject__tick(a)
    };
    b._normalizeFrame = function () {
        var a, b = this._animation, d = this.paused, c = this._currentFrame, e = this.currentAnimationFrame;
        if (b)
            if (a = b.frames.length, (0 | e) >= a) {
                var f = b.next;
                if (!this._dispatchAnimationEnd(b, c, d, f, a - 1)) {
                    if (f)
                        return this._goto(f, e - a);
                    this.paused = !0;
                    e = this.currentAnimationFrame = b.frames.length - 1;
                    this._currentFrame = b.frames[e]
                }
            } else
                this._currentFrame =
                    b.frames[0 | e];
        else if (a = this.spriteSheet.getNumFrames(), c >= a && !this._dispatchAnimationEnd(b, c, d, a - 1) && (this._currentFrame -= a) >= a)
            return this._normalizeFrame();
        this.currentFrame = 0 | this._currentFrame
    };
    b._dispatchAnimationEnd = function (a, b, d, c, e) {
        var f = a ? a.name : null;
        if (this.hasEventListener("animationend")) {
            var h = new createjs.Event("animationend");
            h.name = f;
            h.next = c;
            this.dispatchEvent(h)
        }
        a = this._animation != a || this._currentFrame != b;
        return a || d || !this.paused || (this.currentAnimationFrame = e, a = !0), a
    };
    b.DisplayObject_cloneProps =
        b.cloneProps;
    b.cloneProps = function (a) {
        this.DisplayObject_cloneProps(a);
        a.currentFrame = this.currentFrame;
        a._currentFrame = this._currentFrame;
        a.currentAnimation = this.currentAnimation;
        a.paused = this.paused;
        a._animation = this._animation;
        a.currentAnimationFrame = this.currentAnimationFrame;
        a.framerate = this.framerate
    };
    b._goto = function (a, b) {
        if (isNaN(a)) {
            var d = this.spriteSheet.getAnimation(a);
            d && (this.currentAnimationFrame = b || 0, this._animation = d, this.currentAnimation = a, this._normalizeFrame())
        } else
            this.currentAnimationFrame =
                0, this.currentAnimation = this._animation = null, this._currentFrame = a, this._normalizeFrame()
    };
    createjs.Sprite = c
})();
this.createjs = this.createjs || {};
(function () {
    if (!createjs.Sprite)
        throw"BitmapAnimation is deprecated in favour of Sprite. See VERSIONS file for info on changes.";
    (createjs.BitmapAnimation = function (c) {
        console.log("BitmapAnimation is deprecated in favour of Sprite. See VERSIONS file for info on changes.");
        this.initialize(c)
    }).prototype = new createjs.Sprite
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.DisplayObject;
    b.graphics = null;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a) {
        this.DisplayObject_initialize();
        this.graphics = a ? a : new createjs.Graphics
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || this.graphics && !this.graphics.isEmpty();
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY && a)
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        return this.DisplayObject_draw(a, b) ? !0 : (this.graphics.draw(a),
            !0)
    };
    b.clone = function (a) {
        a = new c(a && this.graphics ? this.graphics.clone() : this.graphics);
        return this.cloneProps(a), a
    };
    b.toString = function () {
        return "[Shape (name=" + this.name + ")]"
    };
    createjs.Shape = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, c) {
        this.initialize(a, b, c)
    }, b = c.prototype = new createjs.DisplayObject, a = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas");
    a.getContext && (c._workingContext = a.getContext("2d"), a.width = a.height = 1);
    c.H_OFFSETS = {start: 0, left: 0, center: -0.5, end: -1, right: -1};
    c.V_OFFSETS = {top: 0, hanging: -0.01, middle: -0.4, alphabetic: -0.8, ideographic: -0.85, bottom: -1};
    b.text = "";
    b.font = null;
    b.color = null;
    b.textAlign = "left";
    b.textBaseline = "top";
    b.maxWidth = null;
    b.outline = 0;
    b.lineHeight = 0;
    b.lineWidth = null;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a, b, c) {
        this.DisplayObject_initialize();
        this.text = a;
        this.font = b;
        this.color = c
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || null != this.text && "" !== this.text;
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY && a)
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        if (this.DisplayObject_draw(a, b))
            return !0;
        var c = this.color || "#000";
        return this.outline ? (a.strokeStyle = c, a.lineWidth = 1 * this.outline) :
            a.fillStyle = c, this._drawText(this._prepContext(a)), !0
    };
    b.getMeasuredWidth = function () {
        return this._prepContext(c._workingContext).measureText(this.text).width
    };
    b.getMeasuredLineHeight = function () {
        return 1.2 * this._prepContext(c._workingContext).measureText("M").width
    };
    b.getMeasuredHeight = function () {
        return this._drawText(null, {}).height
    };
    b.DisplayObject_getBounds = b.getBounds;
    b.getBounds = function () {
        var a = this.DisplayObject_getBounds();
        if (a)
            return a;
        if (null == this.text || "" == this.text)
            return null;
        var a = this._drawText(null,
            {}), b = this.maxWidth && this.maxWidth < a.width ? this.maxWidth : a.width, g = b * c.H_OFFSETS[this.textAlign || "left"], e = (this.lineHeight || this.getMeasuredLineHeight()) * c.V_OFFSETS[this.textBaseline || "top"];
        return this._rectangle.initialize(g, e, b, a.height)
    };
    b.clone = function () {
        var a = new c(this.text, this.font, this.color);
        return this.cloneProps(a), a
    };
    b.toString = function () {
        return "[Text (text=" + (20 < this.text.length ? this.text.substr(0, 17) + "..." : this.text) + ")]"
    };
    b.DisplayObject_cloneProps = b.cloneProps;
    b.cloneProps = function (a) {
        this.DisplayObject_cloneProps(a);
        a.textAlign = this.textAlign;
        a.textBaseline = this.textBaseline;
        a.maxWidth = this.maxWidth;
        a.outline = this.outline;
        a.lineHeight = this.lineHeight;
        a.lineWidth = this.lineWidth
    };
    b._prepContext = function (a) {
        return a.font = this.font, a.textAlign = this.textAlign || "left", a.textBaseline = this.textBaseline || "top", a
    };
    b._drawText = function (a, b) {
        var g = !!a;
        g || (a = this._prepContext(c._workingContext));
        for (var e = this.lineHeight || this.getMeasuredLineHeight(), f = 0, h = 0, l = String(this.text).split(/(?:\r\n|\r|\n)/), m = 0, n = l.length; n > m; m++) {
            var p =
                l[m], r = null;
            if (null != this.lineWidth && (r = a.measureText(p).width) > this.lineWidth)
                for (var q = p.split(/(\s)/), p = q[0], r = a.measureText(p).width, s = 1, u = q.length; u > s; s += 2) {
                    var t = a.measureText(q[s] + q[s + 1]).width;
                    r + t > this.lineWidth ? (g && this._drawTextLine(a, p, h * e), r > f && (f = r), p = q[s + 1], r = a.measureText(p).width, h++) : (p += q[s] + q[s + 1], r += t)
                }
            g && this._drawTextLine(a, p, h * e);
            b && null == r && (r = a.measureText(p).width);
            r > f && (f = r);
            h++
        }
        return b && (b.count = h, b.width = f, b.height = h * e), b
    };
    b._drawTextLine = function (a, b, c) {
        this.outline ?
            a.strokeText(b, 0, c, this.maxWidth || 65535) : a.fillText(b, 0, c, this.maxWidth || 65535)
    };
    createjs.Text = c
})();
this.createjs = this.createjs || {};
(function () {
    function c(a, b) {
        this.initialize(a, b)
    }

    var b = c.prototype = new createjs.DisplayObject;
    b.text = "";
    b.spriteSheet = null;
    b.lineHeight = 0;
    b.letterSpacing = 0;
    b.spaceWidth = 0;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a, b) {
        this.DisplayObject_initialize();
        this.text = a;
        this.spriteSheet = b
    };
    b.DisplayObject_draw = b.draw;
    b.draw = function (a, b) {
        return this.DisplayObject_draw(a, b) ? !0 : (this._drawText(a), void 0)
    };
    b.isVisible = function () {
        var a = this.cacheCanvas || this.spriteSheet && this.spriteSheet.complete &&
            this.text;
        return !!(this.visible && 0 < this.alpha && 0 != this.scaleX && 0 != this.scaleY && a)
    };
    b.getBounds = function () {
        var a = this._rectangle;
        return this._drawText(null, a), a.width ? a : null
    };
    b._getFrame = function (a, b) {
        var d, c = b.getAnimation(a);
        return c || (a != (d = a.toUpperCase()) || a != (d = a.toLowerCase()) || (d = null), d && (c = b.getAnimation(d))), c && b.getFrame(c.frames[0])
    };
    b._getLineHeight = function (a) {
        return (a = this._getFrame("1", a) || this._getFrame("T", a) || this._getFrame("L", a) || a.getFrame(0)) ? a.rect.height : 1
    };
    b._getSpaceWidth =
        function (a) {
            return (a = this._getFrame("1", a) || this._getFrame("l", a) || this._getFrame("e", a) || this._getFrame("a", a) || a.getFrame(0)) ? a.rect.width : 1
        };
    b._drawText = function (a, b) {
        var d, c, e, f = 0, h = 0, l = this.spaceWidth, m = this.lineHeight, n = this.spriteSheet, p = !!this._getFrame(" ", n);
        p || 0 != l || (l = this._getSpaceWidth(n));
        0 == m && (m = this._getLineHeight(n));
        for (var r = 0, q = 0, s = this.text.length; s > q; q++)
            if (d = this.text.charAt(q), p || " " != d)
                if ("\n" != d && "\r" != d) {
                    var u = this._getFrame(d, n);
                    if (u) {
                        var t = u.rect;
                        e = u.regX;
                        d = t.width;
                        a && a.drawImage(u.image, t.x, t.y, d, c = t.height, f - e, h - u.regY, d, c);
                        f += d + this.letterSpacing
                    }
                } else
                    "\r" == d && "\n" == this.text.charAt(q + 1) && q++, f - e > r && (r = f - e), f = 0, h += m;
            else
                f += l;
        f - e > r && (r = f - e);
        b && (b.width = r - this.letterSpacing, b.height = h + m)
    };
    createjs.BitmapText = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"SpriteSheetUtils cannot be instantiated";
    }, b = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas");
    b.getContext && (c._workingCanvas = b, c._workingContext = b.getContext("2d"), b.width = b.height = 1);
    c.addFlippedFrames = function (a, b, d, g) {
        if (b || d || g) {
            var e = 0;
            b && c._flip(a, ++e, !0, !1);
            d && c._flip(a, ++e, !1, !0);
            g && c._flip(a, ++e, !0, !0)
        }
    };
    c.extractFrame = function (a, b) {
        isNaN(b) && (b = a.getAnimation(b).frames[0]);
        var d = a.getFrame(b);
        if (!d)
            return null;
        var g = d.rect,
            e = c._workingCanvas;
        e.width = g.width;
        e.height = g.height;
        c._workingContext.drawImage(d.image, g.x, g.y, g.width, g.height, 0, 0, g.width, g.height);
        d = document.createElement("img");
        return d.src = e.toDataURL("image/png"), d
    };
    c.mergeAlpha = function (a, b, d) {
        d || (d = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas"));
        d.width = Math.max(b.width, a.width);
        d.height = Math.max(b.height, a.height);
        var c = d.getContext("2d");
        return c.save(), c.drawImage(a, 0, 0), c.globalCompositeOperation = "destination-in", c.drawImage(b,
            0, 0), c.restore(), d
    };
    c._flip = function (a, b, d, g) {
        for (var e = a._images, f = c._workingCanvas, h = c._workingContext, l = e.length / b, m = 0; l > m; m++) {
            var n = e[m];
            n.__tmp = m;
            h.setTransform(1, 0, 0, 1, 0, 0);
            h.clearRect(0, 0, f.width + 1, f.height + 1);
            f.width = n.width;
            f.height = n.height;
            h.setTransform(d ? -1 : 1, 0, 0, g ? -1 : 1, d ? n.width : 0, g ? n.height : 0);
            h.drawImage(n, 0, 0);
            var p = document.createElement("img");
            p.src = f.toDataURL("image/png");
            p.width = n.width;
            p.height = n.height;
            e.push(p)
        }
        h = a._frames;
        f = h.length / b;
        for (m = 0; f > m; m++) {
            var n = h[m], r = n.rect.clone(),
                p = e[n.image.__tmp + l * b], q = {image: p, rect: r, regX: n.regX, regY: n.regY};
            d && (r.x = p.width - r.x - r.width, q.regX = r.width - n.regX);
            g && (r.y = p.height - r.y - r.height, q.regY = r.height - n.regY);
            h.push(q)
        }
        d = "_" + (d ? "h" : "") + (g ? "v" : "");
        g = a._animations;
        a = a._data;
        e = g.length / b;
        for (m = 0; e > m; m++) {
            h = g[m];
            n = a[h];
            l = {name: h + d, speed: n.speed, next: n.next, frames: []};
            n.next && (l.next += d);
            h = n.frames;
            n = 0;
            for (p = h.length; p > n; n++)
                l.frames.push(h[n] + f * b);
            a[l.name] = l;
            g.push(l.name)
        }
    };
    createjs.SpriteSheetUtils = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        this.initialize()
    }, b = c.prototype = new createjs.EventDispatcher;
    c.ERR_DIMENSIONS = "frame dimensions exceed max spritesheet dimensions";
    c.ERR_RUNNING = "a build is already running";
    b.maxWidth = 2048;
    b.maxHeight = 2048;
    b.spriteSheet = null;
    b.scale = 1;
    b.padding = 1;
    b.timeSlice = 0.3;
    b.progress = -1;
    b._frames = null;
    b._animations = null;
    b._data = null;
    b._nextFrameIndex = 0;
    b._index = 0;
    b._timerID = null;
    b._scale = 1;
    b.initialize = function () {
        this._frames = [];
        this._animations = {}
    };
    b.addFrame = function (a, b, d, g,
                           e, f) {
        if (this._data)
            throw c.ERR_RUNNING;
        b = b || a.bounds || a.nominalBounds;
        return !b && a.getBounds && (b = a.getBounds()), b ? (d = d || 1, this._frames.push({
            source: a,
            sourceRect: b,
            scale: d,
            funct: g,
            params: e,
            scope: f,
            index: this._frames.length,
            height: b.height * d
        }) - 1) : null
    };
    b.addAnimation = function (a, b, d, g) {
        if (this._data)
            throw c.ERR_RUNNING;
        this._animations[a] = {frames: b, next: d, frequency: g}
    };
    b.addMovieClip = function (a, b, d) {
        if (this._data)
            throw c.ERR_RUNNING;
        var g = a.frameBounds, e = b || a.bounds || a.nominalBounds;
        if (!e && a.getBounds &&
            (e = a.getBounds()), !e && !g)
            return null;
        b = this._frames.length;
        for (var f = a.timeline.duration, h = 0; f > h; h++)
            this.addFrame(a, g && g[h] ? g[h] : e, d, function (a) {
                var b = this.actionsEnabled;
                this.actionsEnabled = !1;
                this.gotoAndStop(a);
                this.actionsEnabled = b
            }, [h], a);
        h = a.timeline._labels;
        a = [];
        for (var l in h)
            a.push({index: h[l], label: l});
        if (a.length)
            for (a.sort(function (a, b) {
                return a.index - b.index
            }), h = 0, l = a.length; l > h; h++) {
                d = a[h].label;
                for (var g = b + (h == l - 1 ? f : a[h + 1].index), e = [], m = b + a[h].index; g > m; m++)
                    e.push(m);
                this.addAnimation(d,
                    e, !0)
            }
    };
    b.build = function () {
        if (this._data)
            throw c.ERR_RUNNING;
        for (this._startBuild(); this._drawNext();)
            ;
        return this._endBuild(), this.spriteSheet
    };
    b.buildAsync = function (a) {
        if (this._data)
            throw c.ERR_RUNNING;
        this.timeSlice = a;
        this._startBuild();
        var b = this;
        this._timerID = setTimeout(function () {
            b._run()
        }, 50 - 50 * Math.max(0.01, Math.min(0.99, this.timeSlice || 0.3)))
    };
    b.stopAsync = function () {
        clearTimeout(this._timerID);
        this._data = null
    };
    b.clone = function () {
        throw"SpriteSheetBuilder cannot be cloned.";
    };
    b.toString = function () {
        return "[SpriteSheetBuilder]"
    };
    b._startBuild = function () {
        var a = this.padding || 0;
        this.progress = 0;
        this.spriteSheet = null;
        this._index = 0;
        this._scale = this.scale;
        var b = [];
        this._data = {images: [], frames: b, animations: this._animations};
        var d = this._frames.slice();
        if (d.sort(function (a, b) {
                return a.height <= b.height ? -1 : 1
            }), d[d.length - 1].height + 2 * a > this.maxHeight)
            throw c.ERR_DIMENSIONS;
        for (var g = 0, e = 0, f = 0; d.length;) {
            var h = this._fillRow(d, g, f, b, a);
            if (h.w > e && (e = h.w), g += h.h, !h.h || !d.length) {
                var l = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas");
                l.width = this._getSize(e, this.maxWidth);
                l.height = this._getSize(g, this.maxHeight);
                this._data.images[f] = l;
                h.h || (e = g = 0, f++)
            }
        }
    };
    b._getSize = function (a, b) {
        for (var d = 4; Math.pow(2, ++d) < a;)
            ;
        return Math.min(b, Math.pow(2, d))
    };
    b._fillRow = function (a, b, d, g, e) {
        var f = this.maxWidth, h = this.maxHeight;
        b += e;
        for (var h = h - b, l = e, m = 0, n = a.length - 1; 0 <= n; n--) {
            var p = a[n], r = this._scale * p.scale, q = p.sourceRect, s = p.source, u = Math.floor(r * q.x - e), t = Math.floor(r * q.y - e), w = Math.ceil(r * q.height + 2 * e), q = Math.ceil(r * q.width + 2 * e);
            if (q > f)
                throw c.ERR_DIMENSIONS;
            w > h || l + q > f || (p.img = d, p.rect = new createjs.Rectangle(l, b, q, w), m = m || w, a.splice(n, 1), g[p.index] = [l, b, q, w, d, Math.round(-u + r * s.regX - e), Math.round(-t + r * s.regY - e)], l += q)
        }
        return {w: l, h: m}
    };
    b._endBuild = function () {
        this.spriteSheet = new createjs.SpriteSheet(this._data);
        this._data = null;
        this.progress = 1;
        this.dispatchEvent("complete")
    };
    b._run = function () {
        for (var a = 50 * Math.max(0.01, Math.min(0.99, this.timeSlice || 0.3)), b = (new Date).getTime() + a, d = !1; b > (new Date).getTime();)
            if (!this._drawNext()) {
                d = !0;
                break
            }
        if (d)
            this._endBuild();
        else {
            var c = this;
            this._timerID = setTimeout(function () {
                c._run()
            }, 50 - a)
        }
        a = this.progress = this._index / this._frames.length;
        this.hasEventListener("progress") && (b = new createjs.Event("progress"), b.progress = a, this.dispatchEvent(b))
    };
    b._drawNext = function () {
        var a = this._frames[this._index], b = a.scale * this._scale, d = a.rect, c = a.sourceRect, e = this._data.images[a.img].getContext("2d");
        return a.funct && a.funct.apply(a.scope, a.params), e.save(), e.beginPath(), e.rect(d.x, d.y, d.width, d.height), e.clip(), e.translate(Math.ceil(d.x -
            c.x * b), Math.ceil(d.y - c.y * b)), e.scale(b, b), a.source.draw(e), e.restore(), ++this._index < this._frames.length
    };
    createjs.SpriteSheetBuilder = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.DisplayObject;
    b.htmlElement = null;
    b._oldMtx = null;
    b._visible = !1;
    b.DisplayObject_initialize = b.initialize;
    b.initialize = function (a) {
        "string" == typeof a && (a = document.getElementById(a));
        this.DisplayObject_initialize();
        this.mouseEnabled = !1;
        this.htmlElement = a;
        a = a.style;
        a.position = "absolute";
        a.transformOrigin = a.WebkitTransformOrigin = a.msTransformOrigin = a.MozTransformOrigin = a.OTransformOrigin = "0% 0%"
    };
    b.isVisible = function () {
        return null !=
            this.htmlElement
    };
    b.draw = function () {
        return this.visible && (this._visible = !0), !0
    };
    b.cache = function () {
    };
    b.uncache = function () {
    };
    b.updateCache = function () {
    };
    b.hitTest = function () {
    };
    b.localToGlobal = function () {
    };
    b.globalToLocal = function () {
    };
    b.localToLocal = function () {
    };
    b.clone = function () {
        throw"DOMElement cannot be cloned.";
    };
    b.toString = function () {
        return "[DOMElement (name=" + this.name + ")]"
    };
    b.DisplayObject__tick = b._tick;
    b._tick = function (a) {
        var b = this.getStage();
        this._visible = !1;
        b && b.on("drawend", this._handleDrawEnd,
            this, !0);
        this.DisplayObject__tick(a)
    };
    b._handleDrawEnd = function () {
        var a = this.htmlElement;
        if (a) {
            var a = a.style, b = this._visible ? "visible" : "hidden";
            if (b != a.visibility && (a.visibility = b), this._visible) {
                var b = this.getConcatenatedMatrix(this._matrix), d = this._oldMtx;
                if (d && d.alpha == b.alpha || (a.opacity = "" + (0 | 1E4 * b.alpha) / 1E4, d && (d.alpha = b.alpha)), !d || d.tx != b.tx || d.ty != b.ty || d.a != b.a || d.b != b.b || d.c != b.c || d.d != b.d) {
                    var c = "matrix(" + (0 | 1E4 * b.a) / 1E4 + "," + (0 | 1E4 * b.b) / 1E4 + "," + (0 | 1E4 * b.c) / 1E4 + "," + (0 | 1E4 * b.d) / 1E4 + "," +
                        (0 | b.tx + 0.5);
                    a.transform = a.WebkitTransform = a.OTransform = a.msTransform = c + "," + (0 | b.ty + 0.5) + ")";
                    a.MozTransform = c + "px," + (0 | b.ty + 0.5) + "px)";
                    this._oldMtx = d ? d.copy(b) : b.clone()
                }
            }
        }
    };
    createjs.DOMElement = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        this.initialize()
    }, b = c.prototype;
    b.initialize = function () {
    };
    b.getBounds = function () {
        return null
    };
    b.applyFilter = function () {
    };
    b.toString = function () {
        return "[Filter]"
    };
    b.clone = function () {
        return new c
    };
    createjs.Filter = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d) {
        this.initialize(a, b, d)
    }, b = c.prototype = new createjs.Filter;
    b.initialize = function (a, b, d) {
        (isNaN(a) || 0 > a) && (a = 0);
        this.blurX = 0 | a;
        (isNaN(b) || 0 > b) && (b = 0);
        this.blurY = 0 | b;
        (isNaN(d) || 1 > d) && (d = 1);
        this.quality = 0 | d
    };
    b.blurX = 0;
    b.blurY = 0;
    b.quality = 1;
    b.mul_table = [1, 171, 205, 293, 57, 373, 79, 137, 241, 27, 391, 357, 41, 19, 283, 265, 497, 469, 443, 421, 25, 191, 365, 349, 335, 161, 155, 149, 9, 278, 269, 261, 505, 245, 475, 231, 449, 437, 213, 415, 405, 395, 193, 377, 369, 361, 353, 345, 169, 331, 325, 319, 313, 307, 301, 37, 145, 285,
        281, 69, 271, 267, 263, 259, 509, 501, 493, 243, 479, 118, 465, 459, 113, 446, 55, 435, 429, 423, 209, 413, 51, 403, 199, 393, 97, 3, 379, 375, 371, 367, 363, 359, 355, 351, 347, 43, 85, 337, 333, 165, 327, 323, 5, 317, 157, 311, 77, 305, 303, 75, 297, 294, 73, 289, 287, 71, 141, 279, 277, 275, 68, 135, 67, 133, 33, 262, 260, 129, 511, 507, 503, 499, 495, 491, 61, 121, 481, 477, 237, 235, 467, 232, 115, 457, 227, 451, 7, 445, 221, 439, 218, 433, 215, 427, 425, 211, 419, 417, 207, 411, 409, 203, 202, 401, 399, 396, 197, 49, 389, 387, 385, 383, 95, 189, 47, 187, 93, 185, 23, 183, 91, 181, 45, 179, 89, 177, 11, 175, 87, 173, 345,
        343, 341, 339, 337, 21, 167, 83, 331, 329, 327, 163, 81, 323, 321, 319, 159, 79, 315, 313, 39, 155, 309, 307, 153, 305, 303, 151, 75, 299, 149, 37, 295, 147, 73, 291, 145, 289, 287, 143, 285, 71, 141, 281, 35, 279, 139, 69, 275, 137, 273, 17, 271, 135, 269, 267, 133, 265, 33, 263, 131, 261, 130, 259, 129, 257, 1];
    b.shg_table = [0, 9, 10, 11, 9, 12, 10, 11, 12, 9, 13, 13, 10, 9, 13, 13, 14, 14, 14, 14, 10, 13, 14, 14, 14, 13, 13, 13, 9, 14, 14, 14, 15, 14, 15, 14, 15, 15, 14, 15, 15, 15, 14, 15, 15, 15, 15, 15, 14, 15, 15, 15, 15, 15, 15, 12, 14, 15, 15, 13, 15, 15, 15, 15, 16, 16, 16, 15, 16, 14, 16, 16, 14, 16, 13, 16, 16, 16, 15, 16, 13, 16,
        15, 16, 14, 9, 16, 16, 16, 16, 16, 16, 16, 16, 16, 13, 14, 16, 16, 15, 16, 16, 10, 16, 15, 16, 14, 16, 16, 14, 16, 16, 14, 16, 16, 14, 15, 16, 16, 16, 14, 15, 14, 15, 13, 16, 16, 15, 17, 17, 17, 17, 17, 17, 14, 15, 17, 17, 16, 16, 17, 16, 15, 17, 16, 17, 11, 17, 16, 17, 16, 17, 16, 17, 17, 16, 17, 17, 16, 17, 17, 16, 16, 17, 17, 17, 16, 14, 17, 17, 17, 17, 15, 16, 14, 16, 15, 16, 13, 16, 15, 16, 14, 16, 15, 16, 12, 16, 15, 16, 17, 17, 17, 17, 17, 13, 16, 15, 17, 17, 17, 16, 15, 17, 17, 17, 16, 15, 17, 17, 14, 16, 17, 17, 16, 17, 17, 16, 15, 17, 16, 14, 17, 16, 15, 17, 16, 17, 17, 16, 17, 15, 16, 17, 14, 17, 16, 15, 17, 16, 17, 13, 17, 16, 17, 17, 16, 17, 14, 17,
        16, 17, 16, 17, 16, 17, 9];
    b.getBounds = function () {
        var a = 0.5 * Math.pow(this.quality, 0.6);
        return new createjs.Rectangle(-this.blurX * a, -this.blurY * a, 2 * this.blurX * a, 2 * this.blurY * a)
    };
    b.applyFilter = function (a, b, d, c, e, f, h, l) {
        f = f || a;
        null == h && (h = b);
        null == l && (l = d);
        try {
            var m = a.getImageData(b, d, c, e)
        } catch (n) {
            return !1
        }
        a = this.blurX / 2;
        if (isNaN(a) || 0 > a)
            return !1;
        a |= 0;
        var p = this.blurY / 2;
        if (isNaN(p) || 0 > p || (p |= 0, 0 == a && 0 == p))
            return !1;
        var r = this.quality;
        (isNaN(r) || 1 > r) && (r = 1);
        r |= 0;
        3 < r && (r = 3);
        1 > r && (r = 1);
        var q, s, u, t, w, z, A, y, B,
            H, I, J, v = m.data, F = a + a + 1;
        t = p + p + 1;
        var K = c - 1, G = e - 1, E = a + 1, D = p + 1, L = {r: 0, b: 0, g: 0, a: 0, next: null};
        b = L;
        for (q = 1; F > q; q++)
            b = b.next = {r: 0, b: 0, g: 0, a: 0, next: null};
        b.next = L;
        d = F = {r: 0, b: 0, g: 0, a: 0, next: null};
        for (q = 1; t > q; q++)
            d = d.next = {r: 0, b: 0, g: 0, a: 0, next: null};
        d.next = F;
        for (q = null; 0 < r--;) {
            w = t = 0;
            var x = this.mul_table[a], C = this.shg_table[a];
            for (d = e; -1 < --d;) {
                z = E * (H = v[t]);
                A = E * (I = v[t + 1]);
                y = E * (J = v[t + 2]);
                B = E * (u = v[t + 3]);
                b = L;
                for (q = E; -1 < --q;)
                    b.r = H, b.g = I, b.b = J, b.a = u, b = b.next;
                for (q = 1; E > q; q++)
                    s = t + ((q > K ? K : q) << 2), z += b.r = v[s], A += b.g = v[s +
                    1], y += b.b = v[s + 2], B += b.a = v[s + 3], b = b.next;
                q = L;
                for (b = 0; c > b; b++)
                    v[t++] = z * x >>> C, v[t++] = A * x >>> C, v[t++] = y * x >>> C, v[t++] = B * x >>> C, s = w + ((s = b + a + 1) < K ? s : K) << 2, z -= q.r - (q.r = v[s]), A -= q.g - (q.g = v[s + 1]), y -= q.b - (q.b = v[s + 2]), B -= q.a - (q.a = v[s + 3]), q = q.next;
                w += c
            }
            x = this.mul_table[p];
            C = this.shg_table[p];
            for (b = 0; c > b; b++) {
                t = b << 2;
                z = D * (H = v[t]);
                A = D * (I = v[t + 1]);
                y = D * (J = v[t + 2]);
                B = D * (u = v[t + 3]);
                d = F;
                for (q = 0; D > q; q++)
                    d.r = H, d.g = I, d.b = J, d.a = u, d = d.next;
                u = c;
                for (q = 1; p >= q; q++)
                    t = u + b << 2, z += d.r = v[t], A += d.g = v[t + 1], y += d.b = v[t + 2], B += d.a = v[t + 3], d = d.next,
                    G > q && (u += c);
                if (t = b, q = F, 0 < r)
                    for (d = 0; e > d; d++)
                        s = t << 2, v[s + 3] = u = B * x >>> C, 0 < u ? (v[s] = z * x >>> C, v[s + 1] = A * x >>> C, v[s + 2] = y * x >>> C) : v[s] = v[s + 1] = v[s + 2] = 0, s = b + ((s = d + D) < G ? s : G) * c << 2, z -= q.r - (q.r = v[s]), A -= q.g - (q.g = v[s + 1]), y -= q.b - (q.b = v[s + 2]), B -= q.a - (q.a = v[s + 3]), q = q.next, t += c;
                else
                    for (d = 0; e > d; d++)
                        s = t << 2, v[s + 3] = u = B * x >>> C, 0 < u ? (u = 255 / u, v[s] = (z * x >>> C) * u, v[s + 1] = (A * x >>> C) * u, v[s + 2] = (y * x >>> C) * u) : v[s] = v[s + 1] = v[s + 2] = 0, s = b + ((s = d + D) < G ? s : G) * c << 2, z -= q.r - (q.r = v[s]), A -= q.g - (q.g = v[s + 1]), y -= q.b - (q.b = v[s + 2]), B -= q.a - (q.a = v[s + 3]), q = q.next,
                            t += c
            }
        }
        return f.putImageData(m, h, l), !0
    };
    b.clone = function () {
        return new c(this.blurX, this.blurY, this.quality)
    };
    b.toString = function () {
        return "[BlurFilter]"
    };
    createjs.BlurFilter = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.Filter;
    b.initialize = function (a) {
        this.alphaMap = a
    };
    b.alphaMap = null;
    b._alphaMap = null;
    b._mapData = null;
    b.applyFilter = function (a, b, d, c, e, f, h, l) {
        if (!this.alphaMap)
            return !0;
        if (!this._prepAlphaMap())
            return !1;
        f = f || a;
        null == h && (h = b);
        null == l && (l = d);
        try {
            var m = a.getImageData(b, d, c, e)
        } catch (n) {
            return !1
        }
        a = m.data;
        b = this._mapData;
        d = a.length;
        for (c = 0; d > c; c += 4)
            a[c + 3] = b[c] || 0;
        return f.putImageData(m, h, l), !0
    };
    b.clone = function () {
        return new c(this.alphaMap)
    };
    b.toString = function () {
        return "[AlphaMapFilter]"
    };
    b._prepAlphaMap = function () {
        if (!this.alphaMap)
            return !1;
        if (this.alphaMap == this._alphaMap && this._mapData)
            return !0;
        this._mapData = null;
        var a, b = this._alphaMap = this.alphaMap, d = b;
        b instanceof HTMLCanvasElement ? a = d.getContext("2d") : (d = createjs.createCanvas ? createjs.createCanvas() : document.createElement("canvas"), d.width = b.width, d.height = b.height, a = d.getContext("2d"), a.drawImage(b, 0, 0));
        try {
            var c = a.getImageData(0, 0, b.width, b.height)
        } catch (e) {
            return !1
        }
        return this._mapData =
            c.data, !0
    };
    createjs.AlphaMapFilter = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.Filter;
    b.initialize = function (a) {
        this.mask = a
    };
    b.mask = null;
    b.applyFilter = function (a, b, d, c, e, f, h, l) {
        return this.mask ? (f = f || a, null == h && (h = b), null == l && (l = d), f.save(), f.globalCompositeOperation = "destination-in", f.drawImage(this.mask, h, l), f.restore(), !0) : !0
    };
    b.clone = function () {
        return new c(this.mask)
    };
    b.toString = function () {
        return "[AlphaMaskFilter]"
    };
    createjs.AlphaMaskFilter = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, c, e, f, h, l) {
        this.initialize(a, b, d, c, e, f, h, l)
    }, b = c.prototype = new createjs.Filter;
    b.redMultiplier = 1;
    b.greenMultiplier = 1;
    b.blueMultiplier = 1;
    b.alphaMultiplier = 1;
    b.redOffset = 0;
    b.greenOffset = 0;
    b.blueOffset = 0;
    b.alphaOffset = 0;
    b.initialize = function (a, b, d, c, e, f, h, l) {
        this.redMultiplier = null != a ? a : 1;
        this.greenMultiplier = null != b ? b : 1;
        this.blueMultiplier = null != d ? d : 1;
        this.alphaMultiplier = null != c ? c : 1;
        this.redOffset = e || 0;
        this.greenOffset = f || 0;
        this.blueOffset = h || 0;
        this.alphaOffset = l ||
            0
    };
    b.applyFilter = function (a, b, d, c, e, f, h, l) {
        f = f || a;
        null == h && (h = b);
        null == l && (l = d);
        try {
            var m = a.getImageData(b, d, c, e)
        } catch (n) {
            return !1
        }
        a = m.data;
        b = a.length;
        for (d = 0; b > d; d += 4)
            a[d] = a[d] * this.redMultiplier + this.redOffset, a[d + 1] = a[d + 1] * this.greenMultiplier + this.greenOffset, a[d + 2] = a[d + 2] * this.blueMultiplier + this.blueOffset, a[d + 3] = a[d + 3] * this.alphaMultiplier + this.alphaOffset;
        return f.putImageData(m, h, l), !0
    };
    b.toString = function () {
        return "[ColorFilter]"
    };
    b.clone = function () {
        return new c(this.redMultiplier, this.greenMultiplier,
            this.blueMultiplier, this.alphaMultiplier, this.redOffset, this.greenOffset, this.blueOffset, this.alphaOffset)
    };
    createjs.ColorFilter = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, d, c) {
        this.initialize(a, b, d, c)
    }, b = c.prototype;
    c.DELTA_INDEX = [0, 0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1, 0.11, 0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.2, 0.21, 0.22, 0.24, 0.25, 0.27, 0.28, 0.3, 0.32, 0.34, 0.36, 0.38, 0.4, 0.42, 0.44, 0.46, 0.48, 0.5, 0.53, 0.56, 0.59, 0.62, 0.65, 0.68, 0.71, 0.74, 0.77, 0.8, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98, 1, 1.06, 1.12, 1.18, 1.24, 1.3, 1.36, 1.42, 1.48, 1.54, 1.6, 1.66, 1.72, 1.78, 1.84, 1.9, 1.96, 2, 2.12, 2.25, 2.37, 2.5, 2.62, 2.75, 2.87, 3, 3.2, 3.4, 3.6, 3.8, 4, 4.3, 4.7, 4.9, 5, 5.5, 6, 6.5, 6.8, 7, 7.3,
        7.5, 7.8, 8, 8.4, 8.7, 9, 9.4, 9.6, 9.8, 10];
    c.IDENTITY_MATRIX = [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1];
    c.LENGTH = c.IDENTITY_MATRIX.length;
    b.initialize = function (a, b, d, c) {
        return this.reset(), this.adjustColor(a, b, d, c), this
    };
    b.reset = function () {
        return this.copyMatrix(c.IDENTITY_MATRIX)
    };
    b.adjustColor = function (a, b, d, c) {
        return this.adjustHue(c), this.adjustContrast(b), this.adjustBrightness(a), this.adjustSaturation(d)
    };
    b.adjustBrightness = function (a) {
        return 0 == a || isNaN(a) ? this : (a = this._cleanValue(a, 255),
            this._multiplyMatrix([1, 0, 0, 0, a, 0, 1, 0, 0, a, 0, 0, 1, 0, a, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]), this)
    };
    b.adjustContrast = function (a) {
        if (0 == a || isNaN(a))
            return this;
        a = this._cleanValue(a, 100);
        var b;
        return 0 > a ? b = 127 + 127 * (a / 100) : (b = a % 1, b = 0 == b ? c.DELTA_INDEX[a] : c.DELTA_INDEX[a << 0] * (1 - b) + c.DELTA_INDEX[(a << 0) + 1] * b, b = 127 * b + 127), this._multiplyMatrix([b / 127, 0, 0, 0, 0.5 * (127 - b), 0, b / 127, 0, 0, 0.5 * (127 - b), 0, 0, b / 127, 0, 0.5 * (127 - b), 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]), this
    };
    b.adjustSaturation = function (a) {
        if (0 == a || isNaN(a))
            return this;
        a = this._cleanValue(a,
            100);
        a = 1 + (0 < a ? 3 * a / 100 : a / 100);
        return this._multiplyMatrix([0.3086 * (1 - a) + a, 0.6094 * (1 - a), 0.082 * (1 - a), 0, 0, 0.3086 * (1 - a), 0.6094 * (1 - a) + a, 0.082 * (1 - a), 0, 0, 0.3086 * (1 - a), 0.6094 * (1 - a), 0.082 * (1 - a) + a, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]), this
    };
    b.adjustHue = function (a) {
        if (0 == a || isNaN(a))
            return this;
        a = this._cleanValue(a, 180) / 180 * Math.PI;
        var b = Math.cos(a);
        a = Math.sin(a);
        return this._multiplyMatrix([0.213 + 0.787 * b + -0.213 * a, 0.715 + -0.715 * b + -0.715 * a, 0.072 + -0.072 * b + 0.928 * a, 0, 0, 0.213 + -0.213 * b + 0.143 * a, 0.715 + b * (1 - 0.715) + 0.14 * a, 0.072 + -0.072 *
        b + -0.283 * a, 0, 0, 0.213 + -0.213 * b + -0.787 * a, 0.715 + -0.715 * b + 0.715 * a, 0.072 + 0.928 * b + 0.072 * a, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1]), this
    };
    b.concat = function (a) {
        return a = this._fixMatrix(a), a.length != c.LENGTH ? this : (this._multiplyMatrix(a), this)
    };
    b.clone = function () {
        return (new c).copyMatrix(this)
    };
    b.toArray = function () {
        for (var a = [], b = 0, d = c.LENGTH; d > b; b++)
            a[b] = this[b];
        return a
    };
    b.copyMatrix = function (a) {
        for (var b = c.LENGTH, d = 0; b > d; d++)
            this[d] = a[d];
        return this
    };
    b.toString = function () {
        return "[ColorMatrix]"
    };
    b._multiplyMatrix = function (a) {
        for (var b =
            [], d = 0; 5 > d; d++) {
            for (var c = 0; 5 > c; c++)
                b[c] = this[c + 5 * d];
            for (c = 0; 5 > c; c++) {
                for (var e = 0, f = 0; 5 > f; f++)
                    e += a[c + 5 * f] * b[f];
                this[c + 5 * d] = e
            }
        }
    };
    b._cleanValue = function (a, b) {
        return Math.min(b, Math.max(-b, a))
    };
    b._fixMatrix = function (a) {
        return a instanceof c && (a = a.toArray()), a.length < c.LENGTH ? a = a.slice(0, a.length).concat(c.IDENTITY_MATRIX.slice(a.length, c.LENGTH)) : a.length > c.LENGTH && (a = a.slice(0, c.LENGTH)), a
    };
    createjs.ColorMatrix = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.initialize(a)
    }, b = c.prototype = new createjs.Filter;
    b.matrix = null;
    b.initialize = function (a) {
        this.matrix = a
    };
    b.applyFilter = function (a, b, d, c, e, f, h, l) {
        f = f || a;
        null == h && (h = b);
        null == l && (l = d);
        try {
            var m = a.getImageData(b, d, c, e)
        } catch (n) {
            return !1
        }
        var p, r, q, s;
        a = m.data;
        b = a.length;
        p = this.matrix;
        d = p[0];
        c = p[1];
        e = p[2];
        for (var u = p[3], t = p[4], w = p[5], z = p[6], A = p[7], y = p[8], B = p[9], H = p[10], I = p[11], J = p[12], v = p[13], F = p[14], K = p[15], G = p[16], E = p[17], D = p[18], L = p[19], x = 0; b > x; x += 4)
            p = a[x], r = a[x + 1], q =
                a[x + 2], s = a[x + 3], a[x] = p * d + r * c + q * e + s * u + t, a[x + 1] = p * w + r * z + q * A + s * y + B, a[x + 2] = p * H + r * I + q * J + s * v + F, a[x + 3] = p * K + r * G + q * E + s * D + L;
        return f.putImageData(m, h, l), !0
    };
    b.toString = function () {
        return "[ColorMatrixFilter]"
    };
    b.clone = function () {
        return new c(this.matrix)
    };
    createjs.ColorMatrixFilter = c
})();
//判断设备
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"Touch cannot be instantiated";
    };
    c.isSupported = function () {
        return "ontouchstart" in window || window.navigator.msPointerEnabled && 0 < window.navigator.msMaxTouchPoints || window.navigator.pointerEnabled && 0 < window.navigator.maxTouchPoints
    };
    c.enable = function (b, a, k) {
        return b && b.canvas && c.isSupported() ? (b.__touch = {
            pointers: {},
            multitouch: !a,
            preventDefault: !k,
            count: 0
        }, "ontouchstart" in window ? c._IOS_enable(b) : (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) && c._IE_enable(b),
            !0) : !1
    };
    c.disable = function (b) {
        b && ("ontouchstart" in window ? c._IOS_disable(b) : (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) && c._IE_disable(b))
    };
    c._IOS_enable = function (b) {
        var a = b.canvas, k = b.__touch.f = function (a) {
            c._IOS_handleEvent(b, a)
        };
        a.addEventListener("touchstart", k, !1);
        a.addEventListener("touchmove", k, !1);
        a.addEventListener("touchend", k, !1);
        a.addEventListener("touchcancel", k, !1)
    };
    c._IOS_disable = function (b) {
        var a = b.canvas;
        a && (b = b.__touch.f, a.removeEventListener("touchstart",
            b, !1), a.removeEventListener("touchmove", b, !1), a.removeEventListener("touchend", b, !1), a.removeEventListener("touchcancel", b, !1))
    };
    c._IOS_handleEvent = function (b, a) {
        if (b) {
            b.__touch.preventDefault && a.preventDefault && a.preventDefault();

            for (var c = a.changedTouches, d = a.type, g = 0, e = c.length; e > g; g++) {
                var f = c[g], h = f.identifier;
                f.target == b.canvas && ("touchstart" == d ? this._handleStart(b, h, a, f.pageX, f.pageY) : "touchmove" == d ? this._handleMove(b, h, a, f.pageX, f.pageY) : ("touchend" == d || "touchcancel" == d) && this._handleEnd(b,
                    h, a))
            }
        }
    };
    c._IE_enable = function (b) {
        var a = b.canvas, k = b.__touch.f = function (a) {
            c._IE_handleEvent(b, a)
        };
        void 0 === window.navigator.pointerEnabled ? (a.addEventListener("MSPointerDown", k, !1), window.addEventListener("MSPointerMove", k, !1), window.addEventListener("MSPointerUp", k, !1), window.addEventListener("MSPointerCancel", k, !1), b.__touch.preventDefault && (a.style.msTouchAction = "none")) : (a.addEventListener("pointerdown", k, !1), window.addEventListener("pointermove", k, !1), window.addEventListener("pointerup", k, !1),
            window.addEventListener("pointercancel", k, !1), b.__touch.preventDefault && (a.style.touchAction = "none"));
        b.__touch.activeIDs = {}
    };
    c._IE_disable = function (b) {
        var a = b.__touch.f;
        void 0 === window.navigator.pointerEnabled ? (window.removeEventListener("MSPointerMove", a, !1), window.removeEventListener("MSPointerUp", a, !1), window.removeEventListener("MSPointerCancel", a, !1), b.canvas && b.canvas.removeEventListener("MSPointerDown", a, !1)) : (window.removeEventListener("pointermove", a, !1), window.removeEventListener("pointerup",
            a, !1), window.removeEventListener("pointercancel", a, !1), b.canvas && b.canvas.removeEventListener("pointerdown", a, !1))
    };
    c._IE_handleEvent = function (b, a) {
        if (b) {
            b.__touch.preventDefault && a.preventDefault && a.preventDefault();
            var c = a.type, d = a.pointerId, g = b.__touch.activeIDs;
            "MSPointerDown" == c || "pointerdown" == c ? a.srcElement == b.canvas && (g[d] = !0, this._handleStart(b, d, a, a.pageX, a.pageY)) : g[d] && ("MSPointerMove" == c || "pointermove" == c ? this._handleMove(b, d, a, a.pageX, a.pageY) : ("MSPointerUp" == c || "MSPointerCancel" == c ||
            "pointerup" == c || "pointercancel" == c) && (delete g[d], this._handleEnd(b, d, a)))
        }
    };
    c._handleStart = function (b, a, c, d, g) {

        var e = b.__touch;
        if (e.multitouch || !e.count) {
            var f = e.pointers;
            f[a] || (f[a] = !0, e.count++, b._handlePointerDown(a, c, d, g))
        }
    };
    c._handleMove = function (b, a, c, d, g) {
        b.__touch.pointers[a] && b._handlePointerMove(a, c, d, g)
    };
    c._handleEnd = function (b, a, c) {
        var d = b.__touch, g = d.pointers;
        g[a] && (d.count--, b._handlePointerUp(a, c, !0), delete g[a])
    };
    createjs.Touch = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = createjs.EaselJS = createjs.EaselJS || {};
    c.version = "NEXT";
    c.buildDate = "Thu, 12 Dec 2013 23:37:07 GMT"
})();
this.createjs = this.createjs || {};
(function () {
    var c = createjs.PreloadJS = createjs.PreloadJS || {};
    c.version = "NEXT";
    c.buildDate = "Thu, 12 Dec 2013 23:37:07 GMT"
})();
this.createjs = this.createjs || {};
(function () {
    createjs.proxy = function (c, b) {
        var a = Array.prototype.slice.call(arguments, 2);
        return function () {
            return c.apply(b, Array.prototype.slice.call(arguments, 0).concat(a))
        }
    }
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        this.init()
    };
    c.prototype = new createjs.EventDispatcher;
    var b = c.prototype;
    c.FILE_PATTERN = /^(?:(\w+:)\/{2}(\w+(?:\.\w+)*\/?)|(.{0,2}\/{1}))?([/.]*?(?:[^?]+)?\/)?((?:[^/?]+)\.(\w+))(?:\?(\S+)?)?$/;
    c.PATH_PATTERN = /^(?:(\w+:)\/{2})|(.{0,2}\/{1})?([/.]*?(?:[^?]+)?\/?)?$/;
    b.loaded = !1;
    b.canceled = !1;
    b.progress = 0;
    b._item = null;
    b.getItem = function () {
        return this._item
    };
    b.init = function () {
    };
    b.load = function () {
    };
    b.close = function () {
    };
    b._sendLoadStart = function () {
        this._isCanceled() || this.dispatchEvent("loadstart")
    };
    b._sendProgress = function (a) {
        if (!this._isCanceled()) {
            var b = null;
            "number" == typeof a ? (this.progress = a, b = new createjs.Event("progress"), b.loaded = this.progress, b.total = 1) : (b = a, this.progress = a.loaded / a.total, (isNaN(this.progress) || 1 / 0 == this.progress) && (this.progress = 0));
            b.progress = this.progress;
            this.hasEventListener("progress") && this.dispatchEvent(b)
        }
    };
    b._sendComplete = function () {
        this._isCanceled() || this.dispatchEvent("complete")
    };
    b._sendError = function (a) {
        !this._isCanceled() && this.hasEventListener("error") &&
        (null == a && (a = new createjs.Event("error")), this.dispatchEvent(a))
    };
    b._isCanceled = function () {
        return null == window.createjs || this.canceled ? !0 : !1
    };
    b._parseURI = function (a) {
        return a ? a.match(c.FILE_PATTERN) : null
    };
    b._parsePath = function (a) {
        return a ? a.match(c.PATH_PATTERN) : null
    };
    b._formatQueryString = function (a, b) {
        if (null == a)
            throw Error("You must specify data.");
        var d = [], c;
        for (c in a)
            d.push(c + "=" + escape(a[c]));
        return b && (d = d.concat(b)), d.join("&")
    };
    b.buildPath = function (a, b) {
        if (null == b)
            return a;
        var d = [], c = a.indexOf("?");
        if (-1 != c)
            var e = a.slice(c + 1), d = d.concat(e.split("&"));
        return -1 != c ? a.slice(0, c) + "?" + this._formatQueryString(b, d) : a + "?" + this._formatQueryString(b, d)
    };
    b._isCrossDomain = function (a) {
        var b = document.createElement("a");
        b.href = a.src;
        a = document.createElement("a");
        a.href = location.href;
        return "" != b.hostname && (b.port != a.port || b.protocol != a.protocol || b.hostname != a.hostname)
    };
    b._isLocal = function (a) {
        var b = document.createElement("a");
        return b.href = a.src, "" == b.hostname && "file:" == b.protocol
    };
    b.toString = function () {
        return "[PreloadJS AbstractLoader]"
    };
    createjs.AbstractLoader = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, c) {
        this.init(a, b, c)
    }, b = c.prototype = new createjs.AbstractLoader;
    c.loadTimeout = 8E3;
    c.LOAD_TIMEOUT = 0;
    c.BINARY = "binary";
    c.CSS = "css";
    c.IMAGE = "image";
    c.JAVASCRIPT = "javascript";
    c.JSON = "json";
    c.JSONP = "jsonp";
    c.MANIFEST = "manifest";
    c.SOUND = "sound";
    c.SVG = "svg";
    c.TEXT = "text";
    c.XML = "xml";
    c.POST = "POST";
    c.GET = "GET";
    b._basePath = null;
    b._crossOrigin = "";
    b.useXHR = !0;
    b.stopOnError = !1;
    b.maintainScriptOrder = !0;
    b.next = null;
    b._typeCallbacks = null;
    b._extensionCallbacks = null;
    b._loadStartWasDispatched = !1;
    b._maxConnections = 1;
    b._currentlyLoadingScript = null;
    b._currentLoads = null;
    b._loadQueue = null;
    b._loadQueueBackup = null;
    b._loadItemsById = null;
    b._loadItemsBySrc = null;
    b._loadedResults = null;
    b._loadedRawResults = null;
    b._numItems = 0;
    b._numItemsLoaded = 0;
    b._scriptOrder = null;
    b._loadedScripts = null;
    b.init = function (a, b, c) {
        this._numItems = this._numItemsLoaded = 0;
        this._loadStartWasDispatched = this._paused = !1;
        this._currentLoads = [];
        this._loadQueue = [];
        this._loadQueueBackup = [];
        this._scriptOrder = [];
        this._loadedScripts = [];
        this._loadItemsById = {};
        this._loadItemsBySrc = {};
        this._loadedResults = {};
        this._loadedRawResults = {};
        this._typeCallbacks = {};
        this._extensionCallbacks = {};
        this._basePath = b;
        this.setUseXHR(a);
        this._crossOrigin = !0 === c ? "Anonymous" : !1 === c || null == c ? "" : c
    };
    b.setUseXHR = function (a) {
        return this.useXHR = 0 != a && null != window.XMLHttpRequest, this.useXHR
    };
    b.removeAll = function () {
        this.remove()
    };
    b.remove = function (a) {
        var b = null;
        if (!a || a instanceof Array)
            if (a)
                b = a;
            else {
                if (0 < arguments.length)
                    return
            }
        else
            b = [a];
        var c = !1;
        if (b) {
            for (; b.length;) {
                for (var e =
                    b.pop(), f = this.getResult(e), h = this._loadQueue.length - 1; 0 <= h; h--)
                    if (l = this._loadQueue[h].getItem(), l.id == e || l.src == e) {
                        this._loadQueue.splice(h, 1)[0].cancel();
                        break
                    }
                for (h = this._loadQueueBackup.length - 1; 0 <= h; h--)
                    if (l = this._loadQueueBackup[h].getItem(), l.id == e || l.src == e) {
                        this._loadQueueBackup.splice(h, 1)[0].cancel();
                        break
                    }
                if (f)
                    delete this._loadItemsById[f.id], delete this._loadItemsBySrc[f.src], this._disposeItem(f);
                else
                    for (var h = this._currentLoads.length - 1; 0 <= h; h--) {
                        var l = this._currentLoads[h].getItem();
                        if (l.id == e || l.src == e) {
                            this._currentLoads.splice(h, 1)[0].cancel();
                            c = !0;
                            break
                        }
                    }
            }
            c && this._loadNext()
        } else {
            this.close();
            for (e in this._loadItemsById)
                this._disposeItem(this._loadItemsById[e]);
            this.init(this.useXHR)
        }
    };
    b.reset = function () {
        this.close();
        for (var a in this._loadItemsById)
            this._disposeItem(this._loadItemsById[a]);
        a = [];
        for (var b = 0, c = this._loadQueueBackup.length; c > b; b++)
            a.push(this._loadQueueBackup[b].getItem());
        this.loadManifest(a, !1)
    };
    c.isBinary = function (a) {
        switch (a) {
            case createjs.LoadQueue.IMAGE:
            case createjs.LoadQueue.BINARY:
                return !0;
            default:
                return !1
        }
    };
    c.isText = function (a) {
        switch (a) {
            case createjs.LoadQueue.TEXT:
            case createjs.LoadQueue.JSON:
            case createjs.LoadQueue.MANIFEST:
            case createjs.LoadQueue.XML:
            case createjs.LoadQueue.HTML:
            case createjs.LoadQueue.CSS:
            case createjs.LoadQueue.SVG:
            case createjs.LoadQueue.JAVASCRIPT:
                return !0;
            default:
                return !1
        }
    };
    b.installPlugin = function (a) {
        if (null != a && null != a.getPreloadHandlers) {
            var b = a.getPreloadHandlers();
            if (b.scope = a, null != b.types) {
                a = 0;
                for (var c = b.types.length; c > a; a++)
                    this._typeCallbacks[b.types[a]] =
                        b
            }
            if (null != b.extensions)
                for (a = 0, c = b.extensions.length; c > a; a++)
                    this._extensionCallbacks[b.extensions[a]] = b
        }
    };
    b.setMaxConnections = function (a) {
        this._maxConnections = a;
        !this._paused && 0 < this._loadQueue.length && this._loadNext()
    };
    b.loadFile = function (a, b, c) {
        if (null == a)
            return a = new createjs.Event("error"), a.text = "PRELOAD_NO_FILE", this._sendError(a), void 0;
        this._addItem(a, null, c);
        !1 !== b ? this.setPaused(!1) : this.setPaused(!0)
    };
    b.loadManifest = function (a, b, g) {
        var e = null, f = null;
        if (a instanceof Array) {
            if (0 == a.length)
                return e =
                    new createjs.Event("error"), e.text = "PRELOAD_MANIFEST_EMPTY", this._sendError(e), void 0;
            e = a
        } else if ("string" == typeof a)
            e = [{src: a, type: c.MANIFEST}];
        else {
            if ("object" != typeof a)
                return e = new createjs.Event("error"), e.text = "PRELOAD_MANIFEST_NULL", this._sendError(e), void 0;
            void 0 !== a.src ? (null == a.type ? a.type = c.MANIFEST : a.type != c.MANIFEST && (e = new createjs.Event("error"), e.text = "PRELOAD_MANIFEST_ERROR", this._sendError(e)), e = [a]) : void 0 !== a.manifest && (e = a.manifest, f = a.path)
        }
        a = 0;
        for (var h = e.length; h > a; a++)
            this._addItem(e[a],
                f, g);
        !1 !== b ? this.setPaused(!1) : this.setPaused(!0)
    };
    b.load = function () {
        this.setPaused(!1)
    };
    b.getItem = function (a) {
        return this._loadItemsById[a] || this._loadItemsBySrc[a]
    };
    b.getResult = function (a, b) {
        var c = this._loadItemsById[a] || this._loadItemsBySrc[a];
        if (null == c)
            return null;
        c = c.id;
        return b && this._loadedRawResults[c] ? this._loadedRawResults[c] : this._loadedResults[c]
    };
    b.setPaused = function (a) {
        (this._paused = a) || this._loadNext()
    };
    b.close = function () {
        for (; this._currentLoads.length;)
            this._currentLoads.pop().cancel();
        this._scriptOrder.length = 0;
        this._loadedScripts.length = 0;
        this.loadStartWasDispatched = !1
    };
    b._addItem = function (a, b, c) {
        a = this._createLoadItem(a, b, c);
        null != a && (b = this._createLoader(a), null != b && (this._loadQueue.push(b), this._loadQueueBackup.push(b), this._numItems++, this._updateProgress(), this.maintainScriptOrder && a.type == createjs.LoadQueue.JAVASCRIPT && b instanceof createjs.XHRLoader && (this._scriptOrder.push(a), this._loadedScripts.push(null))))
    };
    b._createLoadItem = function (a, b, c) {
        var e = null;
        switch (typeof a) {
            case "string":
                e =
                    {src: a};
                break;
            case "object":
                e = window.HTMLAudioElement && a instanceof window.HTMLAudioElement ? {
                    tag: a,
                    src: e.tag.src,
                    type: createjs.LoadQueue.SOUND
                } : a;
                break;
            default:
                return null
        }
        a = this._parseURI(e.src);
        null != a && (e.ext = a[6]);
        null == e.type && (e.type = this._getTypeByExtension(e.ext));
        var f = "";
        c = c || this._basePath;
        var h = e.src;
        if (a && null == a[1] && null == a[3])
            if (b) {
                var f = b, l = this._parsePath(b), h = b + h;
                null != c && l && null == l[1] && null == l[2] && (f = c + f)
            } else
                null != c && (f = c);
        if (e.src = f + e.src, e.path = f, (e.type == createjs.LoadQueue.JSON ||
            e.type == createjs.LoadQueue.MANIFEST) && (e._loadAsJSONP = null != e.callback), e.type == createjs.LoadQueue.JSONP && null == e.callback)
            throw Error("callback is required for loading JSONP requests.");
        void 0 !== e.tag && null !== e.tag || (e.tag = this._createTag(e));
        void 0 !== e.id && null !== e.id && "" !== e.id || (e.id = h);
        if (b = this._typeCallbacks[e.type] || this._extensionCallbacks[e.ext]) {
            b = b.callback.call(b.scope, e.src, e.type, e.id, e.data, f, this);
            if (!1 === b)
                return null;
            !0 === b || (null != b.src && (e.src = b.src), null != b.id && (e.id = b.id), null !=
            b.tag && (e.tag = b.tag), null != b.completeHandler && (e.completeHandler = b.completeHandler), b.type && (e.type = b.type), a = this._parseURI(e.src), null != a && null != a[6] && (e.ext = a[6].toLowerCase()))
        }
        return this._loadItemsById[e.id] = e, this._loadItemsBySrc[e.src] = e, e
    };
    b._createLoader = function (a) {
        var b = this.useXHR;
        switch (a.type) {
            case createjs.LoadQueue.JSON:
            case createjs.LoadQueue.MANIFEST:
                b = !a._loadAsJSONP;
                break;
            case createjs.LoadQueue.XML:
            case createjs.LoadQueue.TEXT:
                b = !0;
                break;
            case createjs.LoadQueue.SOUND:
            case createjs.LoadQueue.JSONP:
                b = !1;
                break;
            case null:
                return null
        }
        return b ? new createjs.XHRLoader(a, this._crossOrigin) : new createjs.TagLoader(a)
    };
    b._loadNext = function () {
        if (!this._paused) {
            this._loadStartWasDispatched || (this._sendLoadStart(), this._loadStartWasDispatched = !0);
            this._numItems == this._numItemsLoaded ? (this.loaded = !0, this._sendComplete(), this.next && this.next.load && this.next.load()) : this.loaded = !1;
            for (var a = 0; a < this._loadQueue.length && !(this._currentLoads.length >= this._maxConnections); a++) {
                var b = this._loadQueue[a];
                if (this.maintainScriptOrder &&
                    b instanceof createjs.TagLoader && b.getItem().type == createjs.LoadQueue.JAVASCRIPT) {
                    if (this._currentlyLoadingScript)
                        continue;
                    this._currentlyLoadingScript = !0
                }
                this._loadQueue.splice(a, 1);
                a--;
                this._loadItem(b)
            }
        }
    };
    b._loadItem = function (a) {
        a.on("progress", this._handleProgress, this);
        a.on("complete", this._handleFileComplete, this);
        a.on("error", this._handleFileError, this);
        this._currentLoads.push(a);
        this._sendFileStart(a.getItem());
        a.load()
    };
    b._handleFileError = function (a) {
        a = a.target;
        this._numItemsLoaded++;
        this._updateProgress();
        var b = new createjs.Event("error");
        b.text = "FILE_LOAD_ERROR";
        b.item = a.getItem();
        this._sendError(b);
        this.stopOnError || (this._removeLoadItem(a), this._loadNext())
    };
    b._handleFileComplete = function (a) {
        a = a.target;
        var b = a.getItem();
        if (this._loadedResults[b.id] = a.getResult(), a instanceof createjs.XHRLoader && (this._loadedRawResults[b.id] = a.getResult(!0)), this._removeLoadItem(a), this.maintainScriptOrder && b.type == createjs.LoadQueue.JAVASCRIPT) {
            if (!(a instanceof createjs.TagLoader))
                return this._loadedScripts[createjs.indexOf(this._scriptOrder,
                    b)] = b, this._checkScriptLoadOrder(a), void 0;
            this._currentlyLoadingScript = !1
        }
        if (delete b._loadAsJSONP, b.type == createjs.LoadQueue.MANIFEST) {
            var c = a.getResult();
            null != c && void 0 !== c.manifest && this.loadManifest(c, !0)
        }
        this._processFinishedLoad(b, a)
    };
    b._processFinishedLoad = function (a, b) {
        this._numItemsLoaded++;
        this._updateProgress();
        this._sendFileComplete(a, b);
        this._loadNext()
    };
    b._checkScriptLoadOrder = function () {
        for (var a = this._loadedScripts.length, b = 0; a > b; b++) {
            var c = this._loadedScripts[b];
            if (null === c)
                break;
            if (!0 !== c) {
                var e = this._loadedResults[c.id];
                (document.body || document.getElementsByTagName("body")[0]).appendChild(e);
                this._processFinishedLoad(c);
                this._loadedScripts[b] = !0
            }
        }
    };
    b._removeLoadItem = function (a) {
        for (var b = this._currentLoads.length, c = 0; b > c; c++)
            if (this._currentLoads[c] == a) {
                this._currentLoads.splice(c, 1);
                break
            }
    };
    b._handleProgress = function (a) {
        a = a.target;
        this._sendFileProgress(a.getItem(), a.progress);
        this._updateProgress()
    };
    b._updateProgress = function () {
        var a = this._numItemsLoaded / this._numItems,
            b = this._numItems - this._numItemsLoaded;
        if (0 < b) {
            for (var c = 0, e = 0, f = this._currentLoads.length; f > e; e++)
                c += this._currentLoads[e].progress;
            a += c / b * (b / this._numItems)
        }
        this._sendProgress(a)
    };
    b._disposeItem = function (a) {
        delete this._loadedResults[a.id];
        delete this._loadedRawResults[a.id];
        delete this._loadItemsById[a.id];
        delete this._loadItemsBySrc[a.src]
    };
    b._createTag = function (a) {
        var b = null;
        switch (a.type) {
            case createjs.LoadQueue.IMAGE:
                return b = document.createElement("img"), "" == this._crossOrigin || this._isLocal(a) ||
                (b.crossOrigin = this._crossOrigin), b;
            case createjs.LoadQueue.SOUND:
                return b = document.createElement("audio"), b.autoplay = !1, b;
            case createjs.LoadQueue.JSON:
            case createjs.LoadQueue.JSONP:
            case createjs.LoadQueue.JAVASCRIPT:
            case createjs.LoadQueue.MANIFEST:
                return b = document.createElement("script"), b.type = "text/javascript", b;
            case createjs.LoadQueue.CSS:
                return b = this.useXHR ? document.createElement("style") : document.createElement("link"), b.rel = "stylesheet", b.type = "text/css", b;
            case createjs.LoadQueue.SVG:
                return this.useXHR ?
                    b = document.createElement("svg") : (b = document.createElement("object"), b.type = "image/svg+xml"), b
        }
        return null
    };
    b._getTypeByExtension = function (a) {
        if (null == a)
            return createjs.LoadQueue.TEXT;
        switch (a.toLowerCase()) {
            case "jpeg":
            case "jpg":
            case "gif":
            case "png":
            case "webp":
            case "bmp":
                return createjs.LoadQueue.IMAGE;
            case "ogg":
            case "mp3":
            case "wav":
                return createjs.LoadQueue.SOUND;
            case "json":
                return createjs.LoadQueue.JSON;
            case "xml":
                return createjs.LoadQueue.XML;
            case "css":
                return createjs.LoadQueue.CSS;
            case "js":
                return createjs.LoadQueue.JAVASCRIPT;
            case "svg":
                return createjs.LoadQueue.SVG;
            default:
                return createjs.LoadQueue.TEXT
        }
    };
    b._sendFileProgress = function (a, b) {
        if (this._isCanceled())
            return this._cleanUp(), void 0;
        if (this.hasEventListener("fileprogress")) {
            var c = new createjs.Event("fileprogress");
            c.progress = b;
            c.loaded = b;
            c.total = 1;
            c.item = a;
            this.dispatchEvent(c)
        }
    };
    b._sendFileComplete = function (a, b) {
        if (!this._isCanceled()) {
            var c = new createjs.Event("fileload");
            c.loader = b;
            c.item = a;
            c.result = this._loadedResults[a.id];
            c.rawResult = this._loadedRawResults[a.id];
            a.completeHandler && a.completeHandler(c);
            this.hasEventListener("fileload") && this.dispatchEvent(c)
        }
    };
    b._sendFileStart = function (a) {
        var b = new createjs.Event("filestart");
        b.item = a;
        this.hasEventListener("filestart") && this.dispatchEvent(b)
    };
    b.toString = function () {
        return "[PreloadJS LoadQueue]"
    };
    createjs.LoadQueue = c;
    var a = function () {
    };
    a.init = function () {
        var b = navigator.userAgent;
        a.isFirefox = -1 < b.indexOf("Firefox");
        a.isOpera = null != window.opera;
        a.isChrome = -1 < b.indexOf("Chrome");
        a.isIOS = -1 < b.indexOf("iPod") || -1 <
            b.indexOf("iPhone") || -1 < b.indexOf("iPad")
    };
    a.init();
    createjs.LoadQueue.BrowserDetect = a
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a) {
        this.init(a)
    }, b = c.prototype = new createjs.AbstractLoader;
    b._loadTimeout = null;
    b._tagCompleteProxy = null;
    b._isAudio = !1;
    b._tag = null;
    b._jsonResult = null;
    b.init = function (a) {
        this._item = a;
        this._tag = a.tag;
        this._isAudio = window.HTMLAudioElement && a.tag instanceof window.HTMLAudioElement;
        this._tagCompleteProxy = createjs.proxy(this._handleLoad, this)
    };
    b.getResult = function () {
        return this._item.type == createjs.LoadQueue.JSONP || this._item.type == createjs.LoadQueue.MANIFEST ? this._jsonResult :
            this._tag
    };
    b.cancel = function () {
        this.canceled = !0;
        this._clean()
    };
    b.load = function () {
        var a = this._item, b = this._tag;
        clearTimeout(this._loadTimeout);
        var c = createjs.LoadQueue.LOAD_TIMEOUT;
        0 == c && (c = createjs.LoadQueue.loadTimeout);
        this._loadTimeout = setTimeout(createjs.proxy(this._handleTimeout, this), c);
        this._isAudio && (b.src = null, b.preload = "auto");
        b.onerror = createjs.proxy(this._handleError, this);
        this._isAudio ? (b.onstalled = createjs.proxy(this._handleStalled, this), b.addEventListener("canplaythrough", this._tagCompleteProxy,
            !1)) : (b.onload = createjs.proxy(this._handleLoad, this), b.onreadystatechange = createjs.proxy(this._handleReadyStateChange, this));
        c = this.buildPath(a.src, a.values);
        switch (a.type) {
            case createjs.LoadQueue.CSS:
                b.href = c;
                break;
            case createjs.LoadQueue.SVG:
                b.data = c;
                break;
            default:
                b.src = c
        }
        if (a.type == createjs.LoadQueue.JSONP || a.type == createjs.LoadQueue.JSON || a.type == createjs.LoadQueue.MANIFEST) {
            if (null == a.callback)
                throw Error("callback is required for loading JSONP requests.");
            if (null != window[a.callback])
                throw Error('JSONP callback "' +
                    a.callback + '" already exists on window. You need to specify a different callback. Or re-name the current one.');
            window[a.callback] = createjs.proxy(this._handleJSONPLoad, this)
        }
        a.type != createjs.LoadQueue.SVG && a.type != createjs.LoadQueue.JSONP && a.type != createjs.LoadQueue.JSON && a.type != createjs.LoadQueue.MANIFEST && a.type != createjs.LoadQueue.JAVASCRIPT && a.type != createjs.LoadQueue.CSS || (this._startTagVisibility = b.style.visibility, b.style.visibility = "hidden", (document.body || document.getElementsByTagName("body")[0]).appendChild(b));
        null != b.load && b.load()
    };
    b._handleJSONPLoad = function (a) {
        this._jsonResult = a
    };
    b._handleTimeout = function () {
        this._clean();
        var a = new createjs.Event("error");
        a.text = "PRELOAD_TIMEOUT";
        this._sendError(a)
    };
    b._handleStalled = function () {
    };
    b._handleError = function () {
        this._clean();
        var a = new createjs.Event("error");
        this._sendError(a)
    };
    b._handleReadyStateChange = function () {
        clearTimeout(this._loadTimeout);
        var a = this.getItem().tag;
        "loaded" != a.readyState && "complete" != a.readyState || this._handleLoad()
    };
    b._handleLoad = function () {
        if (!this._isCanceled()) {
            var a =
                this.getItem(), b = a.tag;
            if (!(this.loaded || this._isAudio && 4 !== b.readyState)) {
                switch (this.loaded = !0, a.type) {
                    case createjs.LoadQueue.SVG:
                    case createjs.LoadQueue.JSON:
                    case createjs.LoadQueue.JSONP:
                    case createjs.LoadQueue.MANIFEST:
                    case createjs.LoadQueue.CSS:
                        b.style.visibility = this._startTagVisibility, (document.body || document.getElementsByTagName("body")[0]).removeChild(b)
                }
                this._clean();
                this._sendComplete()
            }
        }
    };
    b._clean = function () {
        clearTimeout(this._loadTimeout);
        var a = this.getItem(), b = a.tag;
        null != b && (b.onload =
            null, b.removeEventListener && b.removeEventListener("canplaythrough", this._tagCompleteProxy, !1), b.onstalled = null, b.onprogress = null, b.onerror = null, null != b.parentNode && a.type == createjs.LoadQueue.SVG && a.type == createjs.LoadQueue.JSON && a.type == createjs.LoadQueue.MANIFEST && a.type == createjs.LoadQueue.CSS && a.type == createjs.LoadQueue.JSONP && b.parentNode.removeChild(b));
        a = this.getItem();
        a.type != createjs.LoadQueue.JSONP && a.type != createjs.LoadQueue.MANIFEST || (window[a.callback] = null)
    };
    b.toString = function () {
        return "[PreloadJS TagLoader]"
    };
    createjs.TagLoader = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b) {
        this.init(a, b)
    }, b = c.prototype = new createjs.AbstractLoader;
    b._request = null;
    b._loadTimeout = null;
    b._xhrLevel = 1;
    b._response = null;
    b._rawResponse = null;
    b._crossOrigin = "";
    b.init = function (a, b) {
        this._item = a;
        this._crossOrigin = b;
        !this._createXHR(a)
    };
    b.getResult = function (a) {
        return a && this._rawResponse ? this._rawResponse : this._response
    };
    b.cancel = function () {
        this.canceled = !0;
        this._clean();
        this._request.abort()
    };
    b.load = function () {
        if (null == this._request)
            return this._handleError(), void 0;
        if (this._request.onloadstart = createjs.proxy(this._handleLoadStart, this), this._request.onprogress = createjs.proxy(this._handleProgress, this), this._request.onabort = createjs.proxy(this._handleAbort, this), this._request.onerror = createjs.proxy(this._handleError, this), this._request.ontimeout = createjs.proxy(this._handleTimeout, this), 1 == this._xhrLevel) {
            var a = createjs.LoadQueue.LOAD_TIMEOUT;
            if (0 == a)
                a = createjs.LoadQueue.loadTimeout;
            else
                try {
                    console.warn("LoadQueue.LOAD_TIMEOUT has been deprecated in favor of LoadQueue.loadTimeout")
                } catch (b) {
                }
            this._loadTimeout =
                setTimeout(createjs.proxy(this._handleTimeout, this), a)
        }
        this._request.onload = createjs.proxy(this._handleLoad, this);
        this._request.onreadystatechange = createjs.proxy(this._handleReadyStateChange, this);
        try {
            this._item.values && this._item.method != createjs.LoadQueue.GET ? this._item.method == createjs.LoadQueue.POST && this._request.send(this._formatQueryString(this._item.values)) : this._request.send()
        } catch (c) {
            a = new createjs.Event("error"), a.error = c, this._sendError(a)
        }
    };
    b.getAllResponseHeaders = function () {
        return this._request.getAllResponseHeaders instanceof Function ? this._request.getAllResponseHeaders() : null
    };
    b.getResponseHeader = function (a) {
        return this._request.getResponseHeader instanceof Function ? this._request.getResponseHeader(a) : null
    };
    b._handleProgress = function (a) {
        if (a && !(0 < a.loaded && 0 == a.total)) {
            var b = new createjs.Event("progress");
            b.loaded = a.loaded;
            b.total = a.total;
            this._sendProgress(b)
        }
    };
    b._handleLoadStart = function () {
        clearTimeout(this._loadTimeout);
        this._sendLoadStart()
    };
    b._handleAbort = function () {
        this._clean();
        var a = new createjs.Event("error");
        a.text = "XHR_ABORTED";
        this._sendError(a)
    };
    b._handleError = function () {
        this._clean();
        var a = new createjs.Event("error");
        this._sendError(a)
    };
    b._handleReadyStateChange = function () {
        4 == this._request.readyState && this._handleLoad()
    };
    b._handleLoad = function () {
        if (!this.loaded) {
            if (this.loaded = !0, !this._checkError())
                return this._handleError(), void 0;
            this._response = this._getResponse();
            this._clean();
            this._generateTag() && this._sendComplete()
        }
    };
    b._handleTimeout = function (a) {
        this._clean();
        (new createjs.Event("error")).text =
            "PRELOAD_TIMEOUT";
        this._sendError(a)
    };
    b._checkError = function () {
        switch (parseInt(this._request.status)) {
            case 404:
            case 0:
                return !1
        }
        return !0
    };
    b._getResponse = function () {
        if (null != this._response)
            return this._response;
        if (null != this._request.response)
            return this._request.response;
        try {
            if (null != this._request.responseText)
                return this._request.responseText
        } catch (a) {
        }
        try {
            if (null != this._request.responseXML)
                return this._request.responseXML
        } catch (b) {
        }
        return null
    };
    b._createXHR = function (a) {
        var b = this._isCrossDomain(a),
            c = null;
        if (b && window.XDomainRequest)
            c = new XDomainRequest;
        else if (window.XMLHttpRequest)
            c = new XMLHttpRequest;
        else
            try {
                c = new ActiveXObject("Msxml2.XMLHTTP.6.0")
            } catch (g) {
                try {
                    c = new ActiveXObject("Msxml2.XMLHTTP.3.0")
                } catch (e) {
                    try {
                        c = new ActiveXObject("Msxml2.XMLHTTP")
                    } catch (f) {
                        return !1
                    }
                }
            }
        createjs.LoadQueue.isText(a.type) && c.overrideMimeType && c.overrideMimeType("text/plain; charset=utf-8");
        this._xhrLevel = "string" == typeof c.responseType ? 2 : 1;
        var h = null;
        return h = a.method == createjs.LoadQueue.GET ? this.buildPath(a.src,
            a.values) : a.src, c.open(a.method || createjs.LoadQueue.GET, h, !0), b && c instanceof XMLHttpRequest && 1 == this._xhrLevel && c.setRequestHeader("Origin", location.origin), a.values && a.method == createjs.LoadQueue.POST && c.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"), createjs.LoadQueue.isBinary(a.type) && (c.responseType = "arraybuffer"), this._request = c, !0
    };
    b._clean = function () {
        clearTimeout(this._loadTimeout);
        var a = this._request;
        a.onloadstart = null;
        a.onprogress = null;
        a.onabort = null;
        a.onerror = null;
        a.onload = null;
        a.ontimeout = null;
        a.onloadend = null;
        a.onreadystatechange = null
    };
    b._generateTag = function () {
        var a = this._item.tag;
        switch (this._item.type) {
            case createjs.LoadQueue.IMAGE:
                return a.onload = createjs.proxy(this._handleTagReady, this), "" != this._crossOrigin && (a.crossOrigin = "Anonymous"), a.src = this.buildPath(this._item.src, this._item.values), this._rawResponse = this._response, this._response = a, !1;
            case createjs.LoadQueue.JAVASCRIPT:
                return a = document.createElement("script"), a.text = this._response, this._rawResponse =
                    this._response, this._response = a, !0;
            case createjs.LoadQueue.CSS:
                if (document.getElementsByTagName("head")[0].appendChild(a), a.styleSheet)
                    a.styleSheet.cssText = this._response;
                else {
                    var b = document.createTextNode(this._response);
                    a.appendChild(b)
                }
                return this._rawResponse = this._response, this._response = a, !0;
            case createjs.LoadQueue.XML:
                return b = this._parseXML(this._response, "text/xml"), this._rawResponse = this._response, this._response = b, !0;
            case createjs.LoadQueue.SVG:
                return b = this._parseXML(this._response, "image/svg+xml"),
                    this._rawResponse = this._response, null != b.documentElement ? (a.appendChild(b.documentElement), this._response = a) : this._response = b, !0;
            case createjs.LoadQueue.JSON:
            case createjs.LoadQueue.MANIFEST:
                a = {};
                try {
                    a = JSON.parse(this._response)
                } catch (c) {
                    a = c
                }
                return this._rawResponse = this._response, this._response = a, !0
        }
        return !0
    };
    b._parseXML = function (a, b) {
        var c = null;
        try {
            window.DOMParser ? c = (new DOMParser).parseFromString(a, b) : (c = new ActiveXObject("Microsoft.XMLDOM"), c.async = !1, c.loadXML(a))
        } catch (g) {
        }
        return c
    };
    b._handleTagReady =
        function () {
            this._sendComplete()
        };
    b.toString = function () {
        return "[PreloadJS XHRLoader]"
    };
    createjs.XHRLoader = c
})();
"object" != typeof JSON && (JSON = {});
(function () {
    function c(a) {
        return 10 > a ? "0" + a : a
    }

    function b(a) {
        return d.lastIndex = 0, d.test(a) ? '"' + a.replace(d, function (a) {
            var b = f[a];
            return "string" == typeof b ? b : "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
        }) + '"' : '"' + a + '"'
    }

    function a(c, d) {
        var f, k, r, q, s, u = g, t = d[c];
        switch (t && "object" == typeof t && "function" == typeof t.toJSON && (t = t.toJSON(c)), "function" == typeof h && (t = h.call(d, c, t)), typeof t) {
            case "string":
                return b(t);
            case "number":
                return isFinite(t) ? String(t) : "null";
            case "boolean":
            case "null":
                return String(t);
            case "object":
                if (!t)
                    return "null";
                if (g += e, s = [], "[object Array]" === Object.prototype.toString.apply(t)) {
                    q = t.length;
                    for (f = 0; q > f; f += 1)
                        s[f] = a(f, t) || "null";
                    return r = 0 === s.length ? "[]" : g ? "[\n" + g + s.join(",\n" + g) + "\n" + u + "]" : "[" + s.join(",") + "]", g = u, r
                }
                if (h && "object" == typeof h)
                    for (q = h.length, f = 0; q > f; f += 1)
                        "string" == typeof h[f] && (k = h[f], r = a(k, t), r && s.push(b(k) + (g ? ": " : ":") + r));
                else
                    for (k in t)
                        Object.prototype.hasOwnProperty.call(t, k) && (r = a(k, t), r && s.push(b(k) + (g ? ": " : ":") + r));
                return r = 0 === s.length ? "{}" : g ? "{\n" + g +
                s.join(",\n" + g) + "\n" + u + "}" : "{" + s.join(",") + "}", g = u, r
        }
    }

    "function" != typeof Date.prototype.toJSON && (Date.prototype.toJSON = function () {
        return isFinite(this.valueOf()) ? this.getUTCFullYear() + "-" + c(this.getUTCMonth() + 1) + "-" + c(this.getUTCDate()) + "T" + c(this.getUTCHours()) + ":" + c(this.getUTCMinutes()) + ":" + c(this.getUTCSeconds()) + "Z" : null
    }, String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function () {
        return this.valueOf()
    });
    var k = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        d = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, g, e, f = {
            "\b": "\\b",
            "\t": "\\t",
            "\n": "\\n",
            "\f": "\\f",
            "\r": "\\r",
            '"': '\\"',
            "\\": "\\\\"
        }, h;
    "function" != typeof JSON.stringify && (JSON.stringify = function (b, c, d) {
        var f;
        if (g = "", e = "", "number" == typeof d)
            for (f = 0; d > f; f += 1)
                e += " ";
        else
            "string" == typeof d && (e = d);
        if (h = c, c && "function" != typeof c && ("object" != typeof c || "number" != typeof c.length))
            throw Error("JSON.stringify");
        return a("", {"": b})
    });
    "function" != typeof JSON.parse && (JSON.parse = function (a, b) {
        function c(a, d) {
            var e, f, k = a[d];
            if (k && "object" == typeof k)
                for (e in k)
                    Object.prototype.hasOwnProperty.call(k, e) && (f = c(k, e), void 0 !== f ? k[e] = f : delete k[e]);
            return b.call(a, d, k)
        }

        var d;
        if (a = String(a), k.lastIndex = 0, k.test(a) && (a = a.replace(k, function (a) {
                return "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
            })), /^[\],:{}\s]*$/.test(a.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
                "]").replace(/(?:^|:|,)(?:\s*\[)+/g, "")))
            return d = eval("(" + a + ")"), "function" == typeof b ? c({"": d}, "") : d;
        throw new SyntaxError("JSON.parse");
    })
})();
this.createjs = this.createjs || {};
(function () {
    var c = createjs.SoundJS = createjs.SoundJS || {};
    c.version = "NEXT";
    c.buildDate = "Thu, 12 Dec 2013 23:37:06 GMT"
})();
this.createjs = this.createjs || {};
(function () {
    function c() {
        throw"Sound cannot be instantiated";
    }

    function b(a, b) {
        this.init(a, b)
    }

    function a() {
    }

    c.DELIMITER = "|";
    c.INTERRUPT_ANY = "any";
    c.INTERRUPT_EARLY = "early";
    c.INTERRUPT_LATE = "late";
    c.INTERRUPT_NONE = "none";
    c.PLAY_INITED = "playInited";
    c.PLAY_SUCCEEDED = "playSucceeded";
    c.PLAY_INTERRUPTED = "playInterrupted";
    c.PLAY_FINISHED = "playFinished";
    c.PLAY_FAILED = "playFailed";
    c.SUPPORTED_EXTENSIONS = "mp3 ogg mpeg wav m4a mp4 aiff wma mid".split(" ");
    c.EXTENSION_MAP = {m4a: "mp4"};
    c.FILE_PATTERN = /^(?:(\w+:)\/{2}(\w+(?:\.\w+)*\/?))?([/.]*?(?:[^?]+)?\/)?((?:[^/?]+)\.(\w+))(?:\?(\S+)?)?$/;
    c.defaultInterruptBehavior = c.INTERRUPT_NONE;
    c.alternateExtensions = [];
    c._lastID = 0;
    c.activePlugin = null;
    c._pluginsRegistered = !1;
    c._masterVolume = 1;
    c._masterMute = !1;
    c._instances = [];
    c._idHash = {};
    c._preloadHash = {};
    c._defaultSoundInstance = null;
    c.addEventListener = null;
    c.removeEventListener = null;
    c.removeAllEventListeners = null;
    c.dispatchEvent = null;
    c.hasEventListener = null;
    c._listeners = null;
    createjs.EventDispatcher.initialize(c);
    c._sendFileLoadEvent = function (a) {
        if (c._preloadHash[a])
            for (var b = 0, e = c._preloadHash[a].length; e >
            b; b++) {
                var f = c._preloadHash[a][b];
                if (c._preloadHash[a][b] = !0, c.hasEventListener("fileload")) {
                    var k = new createjs.Event("fileload");
                    k.src = f.src;
                    k.id = f.id;
                    k.data = f.data;
                    c.dispatchEvent(k)
                }
            }
    };
    c.getPreloadHandlers = function () {
        return {callback: createjs.proxy(c.initLoad, c), types: ["sound"], extensions: c.SUPPORTED_EXTENSIONS}
    };
    c.registerPlugin = function (a) {
        try {
            console.log("createjs.Sound.registerPlugin has been deprecated. Please use registerPlugins.")
        } catch (b) {
        }
        return c._registerPlugin(a)
    };
    c._registerPlugin =
        function (a) {
            return c._pluginsRegistered = !0, null == a ? !1 : a.isSupported() ? (c.activePlugin = new a, !0) : !1
        };
    c.registerPlugins = function (a) {
        for (var b = 0, e = a.length; e > b; b++)
            if (c._registerPlugin(a[b]))
                return !0;
        return !1
    };
    c.initializeDefaultPlugins = function () {
        return null != c.activePlugin ? !0 : c._pluginsRegistered ? !1 : c.registerPlugins([createjs.WebAudioPlugin, createjs.HTMLAudioPlugin]) ? !0 : !1
    };
    c.isReady = function () {
        return null != c.activePlugin
    };
    c.getCapabilities = function () {
        return null == c.activePlugin ? null : c.activePlugin._capabilities
    };
    c.getCapability = function (a) {
        return null == c.activePlugin ? null : c.activePlugin._capabilities[a]
    };
    c.initLoad = function (a, b, e, f, k) {
        a = a.replace(k, "");
        a = c.registerSound(a, e, f, !1, k);
        return null == a ? !1 : a
    };
    c.registerSound = function (a, k, e, f, h) {
        if (!c.initializeDefaultPlugins())
            return !1;
        var l = (a instanceof Object && (h = k, k = a.id, e = a.data, a = a.src), c.alternateExtensions.length) ? c._parsePath2(a, "sound", k, e) : c._parsePath(a, "sound", k, e);
        if (null == l)
            return !1;
        null != h && (a = h + a, l.src = h + l.src);
        null != k && (c._idHash[k] = l.src);
        h = null;
        null != e && (isNaN(e.channels) ? isNaN(e) || (h = parseInt(e)) : h = parseInt(e.channels));
        var m = c.activePlugin.register(l.src, h);
        if (null != m && (null != m.numChannels && (h = m.numChannels), b.create(l.src, h), null != e && isNaN(e) ? e.channels = l.data.channels = h || b.maxPerChannel() : e = l.data = h || b.maxPerChannel(), null != m.tag ? l.tag = m.tag : m.src && (l.src = m.src), null != m.completeHandler && (l.completeHandler = m.completeHandler), m.type && (l.type = m.type)), 0 != f)
            if (c._preloadHash[l.src] || (c._preloadHash[l.src] = []), c._preloadHash[l.src].push({
                    src: a,
                    id: k, data: e
                }), 1 == c._preloadHash[l.src].length)
                c.activePlugin.preload(l.src, m);
            else if (1 == c._preloadHash[l.src][0])
                return !0;
        return l
    };
    c.registerManifest = function (a, b) {
        for (var c = [], f = 0, k = a.length; k > f; f++)
            c[f] = createjs.Sound.registerSound(a[f].src, a[f].id, a[f].data, a[f].preload, b);
        return c
    };
    c.removeSound = function (a, k) {
        if (null == c.activePlugin)
            return !1;
        var e = (a instanceof Object && (a = a.src), a = c._getSrcById(a), c.alternateExtensions.length) ? c._parsePath2(a) : c._parsePath(a);
        if (null == e)
            return !1;
        null != k && (e.src =
            k + e.src);
        a = e.src;
        for (var f in c._idHash)
            c._idHash[f] == a && delete c._idHash[f];
        return b.removeSrc(a), delete c._preloadHash[a], c.activePlugin.removeSound(a), !0
    };
    c.removeManifest = function (a, b) {
        for (var c = [], f = 0, k = a.length; k > f; f++)
            c[f] = createjs.Sound.removeSound(a[f].src, b);
        return c
    };
    c.removeAllSounds = function () {
        c._idHash = {};
        c._preloadHash = {};
        b.removeAll();
        c.activePlugin.removeAllSounds()
    };
    c.loadComplete = function (a) {
        var b = c.alternateExtensions.length ? c._parsePath2(a, "sound") : c._parsePath(a, "sound");
        return a =
            b ? c._getSrcById(b.src) : c._getSrcById(a), 1 == c._preloadHash[a][0]
    };
    c._parsePath = function (a, b, e, f) {
        "string" != typeof a && (a = a.toString());
        a = a.split(c.DELIMITER);
        if (1 < a.length)
            try {
                console.log('createjs.Sound.DELIMITER "|" loading approach has been deprecated. Please use the new alternateExtensions property.')
            } catch (k) {
            }
        b = {type: b || "sound", id: e, data: f};
        e = c.getCapabilities();
        f = 0;
        for (var l = a.length; l > f; f++) {
            var m = a[f], n = m.match(c.FILE_PATTERN);
            if (null == n)
                return !1;
            var p = n[4], n = n[5];
            if (e[n] && -1 < createjs.indexOf(c.SUPPORTED_EXTENSIONS,
                    n))
                return b.name = p, b.src = m, b.extension = n, b
        }
        return null
    };
    c._parsePath2 = function (a, b, e, f) {
        "string" != typeof a && (a = a.toString());
        var k = a.match(c.FILE_PATTERN);
        if (null == k)
            return !1;
        for (var l = k[4], m = k[5], n = c.getCapabilities(), p = 0; !n[m];)
            if (m = c.alternateExtensions[p++], p > c.alternateExtensions.length)
                return null;
        a = a.replace("." + k[5], "." + m);
        b = {type: b || "sound", id: e, data: f};
        return b.name = l, b.src = a, b.extension = m, b
    };
    c.play = function (a, b, e, f, k, l, m) {
        a = c.createInstance(a);
        return c._playInstance(a, b, e, f, k, l, m) || a.playFailed(),
            a
    };
    c.createInstance = function (a) {
        if (!c.initializeDefaultPlugins())
            return c._defaultSoundInstance;
        var k = (a = c._getSrcById(a), c.alternateExtensions.length) ? c._parsePath2(a, "sound") : c._parsePath(a, "sound");
        a = null;
        return null != k && null != k.src ? (b.create(k.src), a = c.activePlugin.create(k.src)) : a = c._defaultSoundInstance, a.uniqueId = c._lastID++, a
    };
    c.setVolume = function (a) {
        if (null == Number(a))
            return !1;
        if (a = Math.max(0, Math.min(1, a)), c._masterVolume = a, !this.activePlugin || !this.activePlugin.setVolume || !this.activePlugin.setVolume(a))
            for (var b =
                this._instances, e = 0, k = b.length; k > e; e++)
                b[e].setMasterVolume(a)
    };
    c.getVolume = function () {
        return c._masterVolume
    };
    c.setMute = function (a) {
        if (null == a || void 0 == a)
            return !1;
        if (this._masterMute = a, !this.activePlugin || !this.activePlugin.setMute || !this.activePlugin.setMute(a))
            for (var b = this._instances, c = 0, k = b.length; k > c; c++)
                b[c].setMasterMute(a);
        return !0
    };
    c.getMute = function () {
        return this._masterMute
    };
    c.stop = function () {
        for (var a = this._instances, b = a.length; b--;)
            a[b].stop()
    };
    c._playInstance = function (a, b, e, k, h, l,
                                m) {
        if (b instanceof Object && (e = b.delay, k = b.offset, h = b.loop, l = b.volume, m = b.pan, b = b.interrupt), b = b || c.defaultInterruptBehavior, null == e && (e = 0), null == k && (k = a.getPosition()), null == h && (h = 0), null == l && (l = a.volume), null == m && (m = a.pan), 0 == e) {
            if (!c._beginPlaying(a, b, k, h, l, m))
                return !1
        } else
            e = setTimeout(function () {
                c._beginPlaying(a, b, k, h, l, m)
            }, e), a._delayTimeoutId = e;
        return this._instances.push(a), !0
    };
    c._beginPlaying = function (a, c, e, k, h, l) {
        return b.add(a, c) ? a._beginPlaying(e, k, h, l) ? !0 : (a = createjs.indexOf(this._instances,
            a), -1 < a && this._instances.splice(a, 1), !1) : !1
    };
    c._getSrcById = function (a) {
        return null == c._idHash || null == c._idHash[a] ? a : c._idHash[a]
    };
    c._playFinished = function (a) {
        b.remove(a);
        a = createjs.indexOf(this._instances, a);
        -1 < a && this._instances.splice(a, 1)
    };
    createjs.Sound = c;
    b.channels = {};
    b.create = function (a, c) {
        return null == b.get(a) ? (b.channels[a] = new b(a, c), !0) : !1
    };
    b.removeSrc = function (a) {
        var c = b.get(a);
        return null == c ? !1 : (c.removeAll(), delete b.channels[a], !0)
    };
    b.removeAll = function () {
        for (var a in b.channels)
            b.channels[a].removeAll();
        b.channels = {}
    };
    b.add = function (a, c) {
        var e = b.get(a.src);
        return null == e ? !1 : e.add(a, c)
    };
    b.remove = function (a) {
        var c = b.get(a.src);
        return null == c ? !1 : (c.remove(a), !0)
    };
    b.maxPerChannel = function () {
        return k.maxDefault
    };
    b.get = function (a) {
        return b.channels[a]
    };
    var k = b.prototype;
    k.src = null;
    k.max = null;
    k.maxDefault = 100;
    k.length = 0;
    k.init = function (a, b) {
        this.src = a;
        this.max = b || this.maxDefault;
        -1 == this.max && (this.max = this.maxDefault);
        this._instances = []
    };
    k.get = function (a) {
        return this._instances[a]
    };
    k.add = function (a, b) {
        return this.getSlot(b,
            a) ? (this._instances.push(a), this.length++, !0) : !1
    };
    k.remove = function (a) {
        a = createjs.indexOf(this._instances, a);
        return -1 == a ? !1 : (this._instances.splice(a, 1), this.length--, !0)
    };
    k.removeAll = function () {
        for (var a = this.length - 1; 0 <= a; a--)
            this._instances[a].stop()
    };
    k.getSlot = function (a) {
        for (var b, e, k = 0, h = this.max; h > k; k++) {
            if (b = this.get(k), null == b)
                return !0;
            (a != c.INTERRUPT_NONE || b.playState == c.PLAY_FINISHED) && (0 != k ? b.playState == c.PLAY_FINISHED || b.playState == c.PLAY_INTERRUPTED || b.playState == c.PLAY_FAILED ? e = b : (a ==
            c.INTERRUPT_EARLY && b.getPosition() < e.getPosition() || a == c.INTERRUPT_LATE && b.getPosition() > e.getPosition()) && (e = b) : e = b)
        }
        return null != e ? (e._interrupt(), this.remove(e), !0) : !1
    };
    k.toString = function () {
        return "[Sound SoundChannel]"
    };
    c._defaultSoundInstance = new function () {
        this.isDefault = !0;
        this.addEventListener = this.removeEventListener = this.removeAllEventListeners = this.dispatchEvent = this.hasEventListener = this._listeners = this._interrupt = this._playFailed = this.pause = this.resume = this.play = this._beginPlaying = this._cleanUp =
            this.stop = this.setMasterVolume = this.setVolume = this.mute = this.setMute = this.getMute = this.setPan = this.getPosition = this.setPosition = this.playFailed = function () {
                return !1
            };
        this.getVolume = this.getPan = this.getDuration = function () {
            return 0
        };
        this.playState = c.PLAY_FAILED;
        this.toString = function () {
            return "[Sound Default Sound Instance]"
        }
    };
    a.init = function () {
        var b = window.navigator.userAgent;
        a.isFirefox = -1 < b.indexOf("Firefox");
        a.isOpera = null != window.opera;
        a.isChrome = -1 < b.indexOf("Chrome");
        a.isIOS = -1 < b.indexOf("iPod") ||
            -1 < b.indexOf("iPhone") || -1 < b.indexOf("iPad");
        a.isAndroid = -1 < b.indexOf("Android");
        a.isBlackberry = -1 < b.indexOf("Blackberry")
    };
    a.init();
    createjs.Sound.BrowserDetect = a
})();
this.createjs = this.createjs || {};
(function () {
    function c() {
        this._init()
    }

    c._capabilities = null;
    c.isSupported = function () {
        var a = createjs.Sound.BrowserDetect.isIOS || createjs.Sound.BrowserDetect.isAndroid || createjs.Sound.BrowserDetect.isBlackberry;
        return "file:" != location.protocol || a || this._isFileXHRSupported() ? (c._generateCapabilities(), null == c.context ? !1 : !0) : !1
    };
    c._isFileXHRSupported = function () {
        var a = !0, b = new XMLHttpRequest;
        try {
            b.open("GET", "fail.fail", !1)
        } catch (c) {
            return a = !1
        }
        b.onerror = function () {
            a = !1
        };
        b.onload = function () {
            a = 404 == this.status ||
                200 == this.status || 0 == this.status && "" != this.response
        };
        try {
            b.send()
        } catch (g) {
            a = !1
        }
        return a
    };
    c._generateCapabilities = function () {
        if (null == c._capabilities) {
            var a = document.createElement("audio");
            if (null == a.canPlayType)
                return null;
            if (window.webkitAudioContext)
                c.context = new webkitAudioContext;
            else {
                if (!window.AudioContext)
                    return null;
                c.context = new AudioContext
            }
            c._compatibilitySetUp();
            c.playEmptySound();
            c._capabilities = {panning: !0, volume: !0, tracks: -1};
            for (var b = createjs.Sound.SUPPORTED_EXTENSIONS, d = createjs.Sound.EXTENSION_MAP,
                     g = 0, e = b.length; e > g; g++) {
                var f = b[g], h = d[f] || f;
                c._capabilities[f] = "no" != a.canPlayType("audio/" + f) && "" != a.canPlayType("audio/" + f) || "no" != a.canPlayType("audio/" + h) && "" != a.canPlayType("audio/" + h)
            }
            2 > c.context.destination.numberOfChannels && (c._capabilities.panning = !1);
            c.dynamicsCompressorNode = c.context.createDynamicsCompressor();
            c.dynamicsCompressorNode.connect(c.context.destination);
            c.gainNode = c.context.createGain();
            c.gainNode.connect(c.dynamicsCompressorNode)
        }
    };
    c._compatibilitySetUp = function () {
        if (!c.context.createGain) {
            c.context.createGain =
                c.context.createGainNode;
            var a = c.context.createBufferSource();
            a.__proto__.start = a.__proto__.noteGrainOn;
            a.__proto__.stop = a.__proto__.noteOff;
            this._panningModel = 0
        }
    };
    c.playEmptySound = function () {
        var a = this.context.createBuffer(1, 1, 22050), b = this.context.createBufferSource();
        b.buffer = a;
        b.connect(this.context.destination);
        b.start(0, 0, 0)
    };
    var b = c.prototype;
    b._capabilities = null;
    b._volume = 1;
    b.context = null;
    b._panningModel = "equalpower";
    b.dynamicsCompressorNode = null;
    b.gainNode = null;
    b._arrayBuffers = null;
    b._init =
        function () {
            this._capabilities = c._capabilities;
            this._arrayBuffers = {};
            this.context = c.context;
            this.gainNode = c.gainNode;
            this.dynamicsCompressorNode = c.dynamicsCompressorNode
        };
    b.register = function (a) {
        this._arrayBuffers[a] = !0;
        return {tag: new createjs.WebAudioPlugin.Loader(a, this)}
    };
    b.isPreloadStarted = function (a) {
        return null != this._arrayBuffers[a]
    };
    b.isPreloadComplete = function (a) {
        return !(null == this._arrayBuffers[a] || 1 == this._arrayBuffers[a])
    };
    b.removeSound = function (a) {
        delete this._arrayBuffers[a]
    };
    b.removeAllSounds =
        function () {
            this._arrayBuffers = {}
        };
    b.addPreloadResults = function (a, b) {
        this._arrayBuffers[a] = b
    };
    b._handlePreloadComplete = function () {
        createjs.Sound._sendFileLoadEvent(this.src)
    };
    b.preload = function (a) {
        this._arrayBuffers[a] = !0;
        a = new createjs.WebAudioPlugin.Loader(a, this);
        a.onload = this._handlePreloadComplete;
        a.load()
    };
    b.create = function (a) {
        return this.isPreloadStarted(a) || this.preload(a), new createjs.WebAudioPlugin.SoundInstance(a, this)
    };
    b.setVolume = function (a) {
        return this._volume = a, this._updateVolume(),
            !0
    };
    b._updateVolume = function () {
        var a = createjs.Sound._masterMute ? 0 : this._volume;
        a != this.gainNode.gain.value && (this.gainNode.gain.value = a)
    };
    b.getVolume = function () {
        return this._volume
    };
    b.setMute = function () {
        return this._updateVolume(), !0
    };
    b.toString = function () {
        return "[WebAudioPlugin]"
    };
    createjs.WebAudioPlugin = c
})();
(function () {
    function c(a, b) {
        this._init(a, b)
    }

    var b = c.prototype = new createjs.EventDispatcher;
    b.src = null;
    b.uniqueId = -1;
    b.playState = null;
    b._owner = null;
    b._offset = 0;
    b._delay = 0;
    b._volume = 1;
    try {
        Object.defineProperty(b, "volume", {
            get: function () {
                return this._volume
            }, set: function (a) {
                return null == Number(a) ? !1 : (a = Math.max(0, Math.min(1, a)), this._volume = a, this._updateVolume(), void 0)
            }
        })
    } catch (a) {
    }
    b._pan = 0;
    try {
        Object.defineProperty(b, "pan", {
            get: function () {
                return this._pan
            }, set: function (a) {
                return this._owner._capabilities.panning &&
                null != Number(a) ? (a = Math.max(-1, Math.min(1, a)), this._pan = a, this.panNode.setPosition(a, 0, -0.5), void 0) : !1
            }
        })
    } catch (k) {
    }
    b._duration = 0;
    b._remainingLoops = 0;
    b._delayTimeoutId = null;
    b._soundCompleteTimeout = null;
    b.gainNode = null;
    b.panNode = null;
    b.sourceNode = null;
    b._sourceNodeNext = null;
    b._muted = !1;
    b._paused = !1;
    b._startTime = 0;
    b._endedHandler = null;
    b._sendEvent = function (a) {
        a = new createjs.Event(a);
        this.dispatchEvent(a)
    };
    b._init = function (a, b) {
        this._owner = b;
        this.src = a;
        this.gainNode = this._owner.context.createGain();
        this.panNode = this._owner.context.createPanner();
        this.panNode.panningModel = this._owner._panningModel;
        this.panNode.connect(this.gainNode);
        this._owner.isPreloadComplete(this.src) && (this._duration = 1E3 * this._owner._arrayBuffers[this.src].duration);
        this._endedHandler = createjs.proxy(this._handleSoundComplete, this)
    };
    b._cleanUp = function () {
        this.sourceNode && this.playState == createjs.Sound.PLAY_SUCCEEDED && (this.sourceNode = this._cleanUpAudioNode(this.sourceNode), this._sourceNodeNext = this._cleanUpAudioNode(this._sourceNodeNext));
        0 != this.gainNode.numberOfOutputs && this.gainNode.disconnect(0);
        clearTimeout(this._delayTimeoutId);
        clearTimeout(this._soundCompleteTimeout);
        this._startTime = 0;
        null != window.createjs && createjs.Sound._playFinished(this)
    };
    b._cleanUpAudioNode = function (a) {
        return a && (a.stop(0), a.disconnect(this.panNode), a = null), a
    };
    b._interrupt = function () {
        this._cleanUp();
        this.playState = createjs.Sound.PLAY_INTERRUPTED;
        this._paused = !1;
        this._sendEvent("interrupted")
    };
    b._handleSoundReady = function () {
        if (null != window.createjs) {
            if (1E3 *
                this._offset > this.getDuration())
                return this.playFailed(), void 0;
            0 > this._offset && (this._offset = 0);
            this.playState = createjs.Sound.PLAY_SUCCEEDED;
            this._paused = !1;
            this.gainNode.connect(this._owner.gainNode);
            var a = this._owner._arrayBuffers[this.src].duration;
            this.sourceNode = this._createAndPlayAudioNode(this._owner.context.currentTime - a, this._offset);
            this._duration = 1E3 * a;
            this._startTime = this.sourceNode.startTime - this._offset;
            this._soundCompleteTimeout = setTimeout(this._endedHandler, 1E3 * (a - this._offset));
            0 != this._remainingLoops && (this._sourceNodeNext = this._createAndPlayAudioNode(this._startTime, 0))
        }
    };
    b._createAndPlayAudioNode = function (a, b) {
        var c = this._owner.context.createBufferSource();
        return c.buffer = this._owner._arrayBuffers[this.src], c.connect(this.panNode), this._owner.context.currentTime, c.startTime = a + c.buffer.duration, c.start(c.startTime, b, c.buffer.duration - b), c
    };
    b.play = function (a, b, c, k, h, l) {
        this._cleanUp();
        createjs.Sound._playInstance(this, a, b, c, k, h, l)
    };
    b._beginPlaying = function (a, b, c, k) {
        return null !=
        window.createjs && this.src ? (this._offset = a / 1E3, this._remainingLoops = b, this.volume = c, this.pan = k, this._owner.isPreloadComplete(this.src) ? (this._handleSoundReady(null), this._sendEvent("succeeded"), 1) : (this.playFailed(), void 0)) : void 0
    };
    b.pause = function () {
        return this._paused || this.playState != createjs.Sound.PLAY_SUCCEEDED ? !1 : (this._paused = !0, this._offset = this._owner.context.currentTime - this._startTime, this._cleanUpAudioNode(this.sourceNode), this._cleanUpAudioNode(this._sourceNodeNext), 0 != this.gainNode.numberOfOutputs &&
        this.gainNode.disconnect(), clearTimeout(this._delayTimeoutId), clearTimeout(this._soundCompleteTimeout), !0)
    };
    b.resume = function () {
        return this._paused ? (this._handleSoundReady(null), !0) : !1
    };
    b.stop = function () {
        return this._cleanUp(), this.playState = createjs.Sound.PLAY_FINISHED, this._offset = 0, !0
    };
    b.setVolume = function (a) {
        return this.volume = a, !0
    };
    b._updateVolume = function () {
        var a = this._muted ? 0 : this._volume;
        return a != this.gainNode.gain.value ? (this.gainNode.gain.value = a, !0) : !1
    };
    b.getVolume = function () {
        return this.volume
    };
    b.setMute = function (a) {
        return null == a || void 0 == a ? !1 : (this._muted = a, this._updateVolume(), !0)
    };
    b.getMute = function () {
        return this._muted
    };
    b.setPan = function (a) {
        return this.pan = a, this.pan != a ? !1 : void 0
    };
    b.getPan = function () {
        return this.pan
    };
    b.getPosition = function () {
        return 1E3 * (this._paused || null == this.sourceNode ? this._offset : this._owner.context.currentTime - this._startTime)
    };
    b.setPosition = function (a) {
        return this._offset = a / 1E3, this.sourceNode && this.playState == createjs.Sound.PLAY_SUCCEEDED && (this._cleanUpAudioNode(this.sourceNode),
            this._cleanUpAudioNode(this._sourceNodeNext), clearTimeout(this._soundCompleteTimeout)), this._paused || this.playState != createjs.Sound.PLAY_SUCCEEDED || this._handleSoundReady(null), !0
    };
    b.getDuration = function () {
        return this._duration
    };
    b._handleSoundComplete = function () {
        return this._offset = 0, 0 != this._remainingLoops ? (this._remainingLoops--, this._sourceNodeNext ? (this._cleanUpAudioNode(this.sourceNode), this.sourceNode = this._sourceNodeNext, this._startTime = this.sourceNode.startTime, this._sourceNodeNext = this._createAndPlayAudioNode(this._startTime,
            0), this._soundCompleteTimeout = setTimeout(this._endedHandler, this._duration)) : this._handleSoundReady(null), this._sendEvent("loop"), void 0) : (null != window.createjs && (this._cleanUp(), this.playState = createjs.Sound.PLAY_FINISHED, this._sendEvent("complete")), void 0)
    };
    b.playFailed = function () {
        null != window.createjs && (this._cleanUp(), this.playState = createjs.Sound.PLAY_FAILED, this._sendEvent("failed"))
    };
    b.toString = function () {
        return "[WebAudioPlugin SoundInstance]"
    };
    createjs.WebAudioPlugin.SoundInstance = c
})();
(function () {
    function c(a, b) {
        this._init(a, b)
    }

    var b = c.prototype;
    b.request = null;
    b.owner = null;
    b.progress = -1;
    b.src = null;
    b.originalSrc = null;
    b.result = null;
    b.onload = null;
    b.onprogress = null;
    b.onError = null;
    b._init = function (a, b) {
        this.originalSrc = this.src = a;
        this.owner = b
    };
    b.load = function (a) {
        null != a && (this.src = a);
        this.request = new XMLHttpRequest;
        this.request.open("GET", this.src, !0);
        this.request.responseType = "arraybuffer";
        this.request.onload = createjs.proxy(this.handleLoad, this);
        this.request.onError = createjs.proxy(this.handleError,
            this);
        this.request.onprogress = createjs.proxy(this.handleProgress, this);
        this.request.send()
    };
    b.handleProgress = function (a, b) {
        this.progress = a / b;
        null != this.onprogress && this.onprogress({loaded: a, total: b, progress: this.progress})
    };
    b.handleLoad = function () {
        this.owner.context.decodeAudioData(this.request.response, createjs.proxy(this.handleAudioDecoded, this), createjs.proxy(this.handleError, this))
    };
    b.handleAudioDecoded = function (a) {
        this.progress = 1;
        this.result = a;
        this.src = this.originalSrc;
        this.owner.addPreloadResults(this.src,
            this.result);
        this.onload && this.onload()
    };
    b.handleError = function (a) {
        this.owner.removeSound(this.src);
        this.onerror && this.onerror(a)
    };
    b.toString = function () {
        return "[WebAudioPlugin Loader]"
    };
    createjs.WebAudioPlugin.Loader = c
})();
this.createjs = this.createjs || {};
(function () {
    function c() {
        this._init()
    }

    c.MAX_INSTANCES = 30;
    c._AUDIO_READY = "canplaythrough";
    c._AUDIO_ENDED = "ended";
    c._AUDIO_SEEKED = "seeked";
    c._AUDIO_STALLED = "stalled";
    c._capabilities = null;
    c.enableIOS = !1;
    c.isSupported = function () {
        if (createjs.Sound.BrowserDetect.isIOS && !c.enableIOS)
            return !1;
        c._generateCapabilities();
        return null == c.tag || null == c._capabilities ? !1 : !0
    };
    c._generateCapabilities = function () {
        if (null == c._capabilities) {
            var a = c.tag = document.createElement("audio");
            if (null == a.canPlayType)
                return null;
            c._capabilities = {panning: !0, volume: !0, tracks: -1};
            for (var b = createjs.Sound.SUPPORTED_EXTENSIONS, d = createjs.Sound.EXTENSION_MAP, g = 0, e = b.length; e > g; g++) {
                var f = b[g], h = d[f] || f;
                c._capabilities[f] = "no" != a.canPlayType("audio/" + f) && "" != a.canPlayType("audio/" + f) || "no" != a.canPlayType("audio/" + h) && "" != a.canPlayType("audio/" + h)
            }
        }
    };
    var b = c.prototype;
    b._capabilities = null;
    b._audioSources = null;
    b.defaultNumChannels = 2;
    b.loadedHandler = null;
    b._init = function () {
        this._capabilities = c._capabilities;
        this._audioSources = {}
    };
    b.register = function (a, b) {
        this._audioSources[a] = !0;
        for (var c = createjs.HTMLAudioPlugin.TagPool.get(a), g = null, e = b || this.defaultNumChannels, f = 0; e > f; f++)
            g = this._createTag(a), c.add(g);
        if (g.id = a, this.loadedHandler = createjs.proxy(this._handleTagLoad, this), g.addEventListener && g.addEventListener("canplaythrough", this.loadedHandler), null == g.onreadystatechange)
            g.onreadystatechange = this.loadedHandler;
        else {
            var h = g.onreadystatechange;
            g.onreadystatechange = function () {
                h();
                this.loadedHandler()
            }
        }
        return {tag: g, numChannels: e}
    };
    b._handleTagLoad = function (a) {
        a.target.removeEventListener && a.target.removeEventListener("canplaythrough", this.loadedHandler);
        a.target.onreadystatechange = null;
        a.target.src != a.target.id && createjs.HTMLAudioPlugin.TagPool.checkSrc(a.target.id)
    };
    b._createTag = function (a) {
        var b = document.createElement("audio");
        return b.autoplay = !1, b.preload = "none", b.src = a, b
    };
    b.removeSound = function (a) {
        delete this._audioSources[a];
        createjs.HTMLAudioPlugin.TagPool.remove(a)
    };
    b.removeAllSounds = function () {
        this._audioSources = {};
        createjs.HTMLAudioPlugin.TagPool.removeAll()
    };
    b.create = function (a) {
        if (!this.isPreloadStarted(a)) {
            var b = createjs.HTMLAudioPlugin.TagPool.get(a), c = this._createTag(a);
            c.id = a;
            b.add(c);
            this.preload(a, {tag: c})
        }
        return new createjs.HTMLAudioPlugin.SoundInstance(a, this)
    };
    b.isPreloadStarted = function (a) {
        return null != this._audioSources[a]
    };
    b.preload = function (a, b) {
        this._audioSources[a] = !0;
        new createjs.HTMLAudioPlugin.Loader(a, b.tag)
    };
    b.toString = function () {
        return "[HTMLAudioPlugin]"
    };
    createjs.HTMLAudioPlugin = c
})();
(function () {
    function c(a, b) {
        this._init(a, b)
    }

    var b = c.prototype = new createjs.EventDispatcher;
    b.src = null;
    b.uniqueId = -1;
    b.playState = null;
    b._owner = null;
    b.loaded = !1;
    b._offset = 0;
    b._delay = 0;
    b._volume = 1;
    try {
        Object.defineProperty(b, "volume", {
            get: function () {
                return this._volume
            }, set: function (a) {
                null != Number(a) && (a = Math.max(0, Math.min(1, a)), this._volume = a, this._updateVolume())
            }
        })
    } catch (a) {
    }
    b.pan = 0;
    b._duration = 0;
    b._remainingLoops = 0;
    b._delayTimeoutId = null;
    b.tag = null;
    b._muted = !1;
    b._paused = !1;
    b._endedHandler = null;
    b._readyHandler = null;
    b._stalledHandler = null;
    b.loopHandler = null;
    b._init = function (a, b) {
        this.src = a;
        this._owner = b;
        this._endedHandler = createjs.proxy(this._handleSoundComplete, this);
        this._readyHandler = createjs.proxy(this._handleSoundReady, this);
        this._stalledHandler = createjs.proxy(this._handleSoundStalled, this);
        this.loopHandler = createjs.proxy(this.handleSoundLoop, this)
    };
    b._sendEvent = function (a) {
        a = new createjs.Event(a);
        this.dispatchEvent(a)
    };
    b._cleanUp = function () {
        var a = this.tag;
        if (null != a) {
            a.pause();
            a.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_ENDED,
                this._endedHandler, !1);
            a.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_READY, this._readyHandler, !1);
            a.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_SEEKED, this.loopHandler, !1);
            try {
                a.currentTime = 0
            } catch (b) {
            }
            createjs.HTMLAudioPlugin.TagPool.setInstance(this.src, a);
            this.tag = null
        }
        clearTimeout(this._delayTimeoutId);
        null != window.createjs && createjs.Sound._playFinished(this)
    };
    b._interrupt = function () {
        null != this.tag && (this.playState = createjs.Sound.PLAY_INTERRUPTED, this._cleanUp(), this._paused = !1,
            this._sendEvent("interrupted"))
    };
    b.play = function (a, b, c, e, f, h) {
        this._cleanUp();
        createjs.Sound._playInstance(this, a, b, c, e, f, h)
    };
    b._beginPlaying = function (a, b, c, e) {
        if (null == window.createjs)
            return -1;
        var f = this.tag = createjs.HTMLAudioPlugin.TagPool.getInstance(this.src);
        return null == f ? (this.playFailed(), -1) : (f.addEventListener(createjs.HTMLAudioPlugin._AUDIO_ENDED, this._endedHandler, !1), this._offset = a, this.volume = c, this.pan = e, this._updateVolume(), this._remainingLoops = b, 4 !== f.readyState ? (f.addEventListener(createjs.HTMLAudioPlugin._AUDIO_READY,
            this._readyHandler, !1), f.addEventListener(createjs.HTMLAudioPlugin._AUDIO_STALLED, this._stalledHandler, !1), f.preload = "auto", f.load()) : this._handleSoundReady(null), this._sendEvent("succeeded"), 1)
    };
    b._handleSoundStalled = function () {
        this._cleanUp();
        this._sendEvent("failed")
    };
    b._handleSoundReady = function () {
        if (null != window.createjs) {
            if (this._duration = 1E3 * this.tag.duration, this.playState = createjs.Sound.PLAY_SUCCEEDED, this._paused = !1, this.tag.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_READY, this._readyHandler,
                    !1), this._offset >= this.getDuration())
                return this.playFailed(), void 0;
            0 < this._offset && (this.tag.currentTime = 0.001 * this._offset);
            -1 == this._remainingLoops && (this.tag.loop = !0);
            0 != this._remainingLoops && (this.tag.addEventListener(createjs.HTMLAudioPlugin._AUDIO_SEEKED, this.loopHandler, !1), this.tag.loop = !0);
            this.tag.play()
        }
    };
    b.pause = function () {
        return this._paused || this.playState != createjs.Sound.PLAY_SUCCEEDED || null == this.tag ? !1 : (this._paused = !0, this.tag.pause(), clearTimeout(this._delayTimeoutId), !0)
    };
    b.resume =
        function () {
            return this._paused && null != this.tag ? (this._paused = !1, this.tag.play(), !0) : !1
        };
    b.stop = function () {
        return this._offset = 0, this.pause(), this.playState = createjs.Sound.PLAY_FINISHED, this._cleanUp(), !0
    };
    b.setMasterVolume = function () {
        return this._updateVolume(), !0
    };
    b.setVolume = function (a) {
        return this.volume = a, !0
    };
    b._updateVolume = function () {
        if (null != this.tag) {
            var a = this._muted || createjs.Sound._masterMute ? 0 : this._volume * createjs.Sound._masterVolume;
            return a != this.tag.volume && (this.tag.volume = a), !0
        }
        return !1
    };
    b.getVolume = function () {
        return this.volume
    };
    b.setMasterMute = function () {
        return this._updateVolume(), !0
    };
    b.setMute = function (a) {
        return null == a || void 0 == a ? !1 : (this._muted = a, this._updateVolume(), !0)
    };
    b.getMute = function () {
        return this._muted
    };
    b.setPan = function () {
        return !1
    };
    b.getPan = function () {
        return 0
    };
    b.getPosition = function () {
        return null == this.tag ? this._offset : 1E3 * this.tag.currentTime
    };
    b.setPosition = function (a) {
        if (null == this.tag)
            this._offset = a;
        else {
            this.tag.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_SEEKED,
                this.loopHandler, !1);
            try {
                this.tag.currentTime = 0.001 * a
            } catch (b) {
                return !1
            }
            this.tag.addEventListener(createjs.HTMLAudioPlugin._AUDIO_SEEKED, this.loopHandler, !1)
        }
        return !0
    };
    b.getDuration = function () {
        return this._duration
    };
    b._handleSoundComplete = function () {
        this._offset = 0;
        null != window.createjs && (this.playState = createjs.Sound.PLAY_FINISHED, this._cleanUp(), this._sendEvent("complete"))
    };
    b.handleSoundLoop = function () {
        this._offset = 0;
        this._remainingLoops--;
        0 == this._remainingLoops && (this.tag.loop = !1, this.tag.removeEventListener(createjs.HTMLAudioPlugin._AUDIO_SEEKED,
            this.loopHandler, !1));
        this._sendEvent("loop")
    };
    b.playFailed = function () {
        null != window.createjs && (this.playState = createjs.Sound.PLAY_FAILED, this._cleanUp(), this._sendEvent("failed"))
    };
    b.toString = function () {
        return "[HTMLAudioPlugin SoundInstance]"
    };
    createjs.HTMLAudioPlugin.SoundInstance = c
})();
(function () {
    function c(a, b) {
        this._init(a, b)
    }

    var b = c.prototype;
    b.src = null;
    b.tag = null;
    b.preloadTimer = null;
    b.loadedHandler = null;
    b._init = function (a, b) {
        if (this.src = a, this.tag = b, this.preloadTimer = setInterval(createjs.proxy(this.preloadTick, this), 200), this.loadedHandler = createjs.proxy(this.sendLoadedEvent, this), this.tag.addEventListener && this.tag.addEventListener("canplaythrough", this.loadedHandler), null == this.tag.onreadystatechange)
            this.tag.onreadystatechange = createjs.proxy(this.sendLoadedEvent, this);
        else {
            var c =
                this.tag.onreadystatechange;
            this.tag.onreadystatechange = function () {
                c();
                this.tag.onreadystatechange = createjs.proxy(this.sendLoadedEvent, this)
            }
        }
        this.tag.preload = "auto";
        this.tag.load()
    };
    b.preloadTick = function () {
        var a = this.tag.buffered, b = this.tag.duration;
        0 < a.length && a.end(0) >= b - 1 && this.handleTagLoaded()
    };
    b.handleTagLoaded = function () {
        clearInterval(this.preloadTimer)
    };
    b.sendLoadedEvent = function () {
        this.tag.removeEventListener && this.tag.removeEventListener("canplaythrough", this.loadedHandler);
        this.tag.onreadystatechange =
            null;
        createjs.Sound._sendFileLoadEvent(this.src)
    };
    b.toString = function () {
        return "[HTMLAudioPlugin Loader]"
    };
    createjs.HTMLAudioPlugin.Loader = c
})();
(function () {
    function c(a) {
        this._init(a)
    }

    c.tags = {};
    c.get = function (a) {
        var b = c.tags[a];
        return null == b && (b = c.tags[a] = new c(a)), b
    };
    c.remove = function (a) {
        var b = c.tags[a];
        return null == b ? !1 : (b.removeAll(), delete c.tags[a], !0)
    };
    c.removeAll = function () {
        for (var a in c.tags)
            c.tags[a].removeAll();
        c.tags = {}
    };
    c.getInstance = function (a) {
        a = c.tags[a];
        return null == a ? null : a.get()
    };
    c.setInstance = function (a, b) {
        var d = c.tags[a];
        return null == d ? null : d.set(b)
    };
    c.checkSrc = function (a) {
        a = c.tags[a];
        return null == a ? null : (a.checkSrcChange(),
            void 0)
    };
    var b = c.prototype;
    b.src = null;
    b.length = 0;
    b.available = 0;
    b.tags = null;
    b._init = function (a) {
        this.src = a;
        this.tags = []
    };
    b.add = function (a) {
        this.tags.push(a);
        this.length++;
        this.available++
    };
    b.removeAll = function () {
        for (; this.length--;)
            delete this.tags[this.length];
        this.src = null;
        this.tags.length = 0
    };
    b.get = function () {
        if (0 == this.tags.length)
            return null;
        this.available = this.tags.length;
        var a = this.tags.pop();
        return null == a.parentNode && document.body.appendChild(a), a
    };
    b.set = function (a) {
        -1 == createjs.indexOf(this.tags,
            a) && this.tags.push(a);
        this.available = this.tags.length
    };
    b.checkSrcChange = function () {
        for (var a = this.tags.length - 1, b = this.tags[a].src; a--;)
            this.tags[a].src = b
    };
    b.toString = function () {
        return "[HTMLAudioPlugin TagPool]"
    };
    createjs.HTMLAudioPlugin.TagPool = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, c) {
        this.initialize(a, b, c)
    }, b = c.prototype = new createjs.EventDispatcher;
    c.NONE = 0;
    c.LOOP = 1;
    c.REVERSE = 2;
    c.IGNORE = {};
    c._tweens = [];
    c._plugins = {};
    c.get = function (a, b, d, g) {
        return g && c.removeTweens(a), new c(a, b, d)
    };
    c.tick = function (a, b) {
        for (var d = c._tweens.slice(), g = d.length - 1; 0 <= g; g--) {
            var e = d[g];
            b && !e.ignoreGlobalPause || e._paused || e.tick(e._useTicks ? 1 : a)
        }
    };
    c.handleEvent = function (a) {
        "tick" == a.type && this.tick(a.delta, a.paused)
    };
    c.removeTweens = function (a) {
        if (a.tweenjs_count) {
            for (var b =
                c._tweens, d = b.length - 1; 0 <= d; d--)
                b[d]._target == a && (b[d]._paused = !0, b.splice(d, 1));
            a.tweenjs_count = 0
        }
    };
    c.removeAllTweens = function () {
        for (var a = c._tweens, b = 0, d = a.length; d > b; b++) {
            var g = a[b];
            g.paused = !0;
            g.target.tweenjs_count = 0
        }
        a.length = 0
    };
    c.hasActiveTweens = function (a) {
        return a ? a.tweenjs_count : c._tweens && !!c._tweens.length
    };
    c.installPlugin = function (a, b) {
        var d = a.priority;
        null == d && (a.priority = d = 0);
        for (var g = 0, e = b.length, f = c._plugins; e > g; g++) {
            var h = b[g];
            if (f[h]) {
                for (var l = f[h], m = 0, n = l.length; n > m && !(d < l[m].priority); m++)
                    ;
                f[h].splice(m, 0, a)
            } else
                f[h] = [a]
        }
    };
    c._register = function (a, b) {
        var d = a._target, g = c._tweens;
        if (b)
            d && (d.tweenjs_count = d.tweenjs_count ? d.tweenjs_count + 1 : 1), g.push(a), !c._inited && createjs.Ticker && (createjs.Ticker.addEventListener("tick", c), c._inited = !0);
        else
            for (d && d.tweenjs_count--, d = g.length; d--;)
                if (g[d] == a)
                    return g.splice(d, 1), void 0
    };
    b.ignoreGlobalPause = !1;
    b.loop = !1;
    b.duration = 0;
    b.pluginData = null;
    b.target = null;
    b.position = null;
    b.passive = !1;
    b._paused = !1;
    b._curQueueProps = null;
    b._initQueueProps = null;
    b._steps =
        null;
    b._actions = null;
    b._prevPosition = 0;
    b._stepPosition = 0;
    b._prevPos = -1;
    b._target = null;
    b._useTicks = !1;
    b._inited = !1;
    b.initialize = function (a, b, d) {
        this.target = this._target = a;
        b && (this._useTicks = b.useTicks, this.ignoreGlobalPause = b.ignoreGlobalPause, this.loop = b.loop, b.onChange && this.addEventListener("change", b.onChange), b.override && c.removeTweens(a));
        this.pluginData = d || {};
        this._curQueueProps = {};
        this._initQueueProps = {};
        this._steps = [];
        this._actions = [];
        b && b.paused ? this._paused = !0 : c._register(this, !0);
        b && null !=
        b.position && this.setPosition(b.position, c.NONE)
    };
    b.wait = function (a, b) {
        if (null == a || 0 >= a)
            return this;
        var c = this._cloneProps(this._curQueueProps);
        return this._addStep({d: a, p0: c, e: this._linearEase, p1: c, v: b})
    };
    b.to = function (a, b, c) {
        return (isNaN(b) || 0 > b) && (b = 0), this._addStep({
            d: b || 0,
            p0: this._cloneProps(this._curQueueProps),
            e: c,
            p1: this._cloneProps(this._appendQueueProps(a))
        })
    };
    b.call = function (a, b, c) {
        return this._addAction({f: a, p: b ? b : [this], o: c ? c : this._target})
    };
    b.set = function (a, b) {
        return this._addAction({
            f: this._set,
            o: this, p: [a, b ? b : this._target]
        })
    };
    b.play = function (a) {
        return a || (a = this), this.call(a.setPaused, [!1], a)
    };
    b.pause = function (a) {
        return a || (a = this), this.call(a.setPaused, [!0], a)
    };
    b.setPosition = function (a, b) {
        0 > a && (a = 0);
        null == b && (b = 1);
        var c = a, g = !1;
        if (c >= this.duration && (this.loop ? c %= this.duration : (c = this.duration, g = !0)), c == this._prevPos)
            return g;
        var e = this._prevPos;
        if (this.position = this._prevPos = c, this._prevPosition = a, this._target)
            if (g)
                this._updateTargetProps(null, 1);
            else if (0 < this._steps.length) {
                for (var f =
                    0, h = this._steps.length; h > f && !(this._steps[f].t > c); f++)
                    ;
                f = this._steps[f - 1];
                this._updateTargetProps(f, (this._stepPosition = c - f.t) / f.d)
            }
        return 0 != b && 0 < this._actions.length && (this._useTicks ? this._runActions(c, c) : 1 == b && e > c ? (e != this.duration && this._runActions(e, this.duration), this._runActions(0, c, !0)) : this._runActions(e, c)), g && this.setPaused(!0), this.dispatchEvent("change"), g
    };
    b.tick = function (a) {
        this._paused || this.setPosition(this._prevPosition + a)
    };
    b.setPaused = function (a) {
        return this._paused = !!a, c._register(this,
            !a), this
    };
    b.w = b.wait;
    b.t = b.to;
    b.c = b.call;
    b.s = b.set;
    b.toString = function () {
        return "[Tween]"
    };
    b.clone = function () {
        throw"Tween can not be cloned.";
    };
    b._updateTargetProps = function (a, b) {
        var d, g, e, f;
        if (a || 1 != b) {
            if (this.passive = !!a.v, this.passive)
                return;
            a.e && (b = a.e(b, 0, 1, 1));
            d = a.p0;
            g = a.p1
        } else
            this.passive = !1, d = g = this._curQueueProps;
        for (var h in this._initQueueProps) {
            null == (e = d[h]) && (d[h] = e = this._initQueueProps[h]);
            null == (f = g[h]) && (g[h] = f = e);
            e = e == f || 0 == b || 1 == b || "number" != typeof e ? 1 == b ? f : e : e + (f - e) * b;
            var l = !1;
            if (f = c._plugins[h])
                for (var m = 0, n = f.length; n > m; m++) {
                    var p = f[m].tween(this, h, e, d, g, b, !!a && d == g, !a);
                    p == c.IGNORE ? l = !0 : e = p
                }
            l || (this._target[h] = e)
        }
    };
    b._runActions = function (a, b, c) {
        var g = a, e = b, f = -1, h = this._actions.length, l = 1;
        for (a > b && (g = b, e = a, f = h, h = l = -1); (f += l) != h;) {
            b = this._actions[f];
            var m = b.t;
            (m == e || m > g && e > m || c && m == a) && b.f.apply(b.o, b.p)
        }
    };
    b._appendQueueProps = function (a) {
        var b, d, g, e, f, h;
        for (h in a)
            if (void 0 === this._initQueueProps[h]) {
                if (d = this._target[h], b = c._plugins[h])
                    for (g = 0, e = b.length; e > g; g++)
                        d = b[g].init(this,
                            h, d);
                this._initQueueProps[h] = this._curQueueProps[h] = void 0 === d ? null : d
            }
        for (h in a) {
            if (d = this._curQueueProps[h], b = c._plugins[h])
                for (f = f || {}, g = 0, e = b.length; e > g; g++)
                    b[g].step && b[g].step(this, h, d, a[h], f);
            this._curQueueProps[h] = a[h]
        }
        return f && this._appendQueueProps(f), this._curQueueProps
    };
    b._cloneProps = function (a) {
        var b = {}, c;
        for (c in a)
            b[c] = a[c];
        return b
    };
    b._addStep = function (a) {
        return 0 < a.d && (this._steps.push(a), a.t = this.duration, this.duration += a.d), this
    };
    b._addAction = function (a) {
        return a.t = this.duration,
            this._actions.push(a), this
    };
    b._set = function (a, b) {
        for (var c in a)
            b[c] = a[c]
    };
    createjs.Tween = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function (a, b, c) {
        this.initialize(a, b, c)
    }, b = c.prototype = new createjs.EventDispatcher;
    b.ignoreGlobalPause = !1;
    b.duration = 0;
    b.loop = !1;
    b.position = null;
    b._paused = !1;
    b._tweens = null;
    b._labels = null;
    b._labelList = null;
    b._prevPosition = 0;
    b._prevPos = -1;
    b._useTicks = !1;
    b.initialize = function (a, b, c) {
        this._tweens = [];
        c && (this._useTicks = c.useTicks, this.loop = c.loop, this.ignoreGlobalPause = c.ignoreGlobalPause, c.onChange && this.addEventListener("change", c.onChange));
        a && this.addTween.apply(this, a);
        this.setLabels(b);
        c && c.paused ? this._paused = !0 : createjs.Tween._register(this, !0);
        c && null != c.position && this.setPosition(c.position, createjs.Tween.NONE)
    };
    b.addTween = function (a) {
        var b = arguments.length;
        if (1 < b) {
            for (var c = 0; b > c; c++)
                this.addTween(arguments[c]);
            return arguments[0]
        }
        return 0 == b ? null : (this.removeTween(a), this._tweens.push(a), a.setPaused(!0), a._paused = !1, a._useTicks = this._useTicks, a.duration > this.duration && (this.duration = a.duration), 0 <= this._prevPos && a.setPosition(this._prevPos, createjs.Tween.NONE), a)
    };
    b.removeTween =
        function (a) {
            var b = arguments.length;
            if (1 < b) {
                for (var c = !0, g = 0; b > g; g++)
                    c = c && this.removeTween(arguments[g]);
                return c
            }
            if (0 == b)
                return !1;
            b = this._tweens;
            for (g = b.length; g--;)
                if (b[g] == a)
                    return b.splice(g, 1), a.duration >= this.duration && this.updateDuration(), !0;
            return !1
        };
    b.addLabel = function (a, b) {
        this._labels[a] = b;
        var c = this._labelList;
        if (c) {
            for (var g = 0, e = c.length; e > g && !(b < c[g].position); g++)
                ;
            c.splice(g, 0, {label: a, position: b})
        }
    };
    b.setLabels = function (a) {
        this._labels = a ? a : {}
    };
    b.getLabels = function () {
        var a = this._labelList;
        if (!a) {
            var a = this._labelList = [], b = this._labels, c;
            for (c in b)
                a.push({label: c, position: b[c]});
            a.sort(function (a, b) {
                return a.position - b.position
            })
        }
        return a
    };
    b.getCurrentLabel = function () {
        var a = this.getLabels(), b = this.position, c = a.length;
        if (c) {
            for (var g = 0; c > g && !(b < a[g].position); g++)
                ;
            return 0 == g ? null : a[g - 1].label
        }
        return null
    };
    b.gotoAndPlay = function (a) {
        this.setPaused(!1);
        this._goto(a)
    };
    b.gotoAndStop = function (a) {
        this.setPaused(!0);
        this._goto(a)
    };
    b.setPosition = function (a, b) {
        0 > a && (a = 0);
        var c = this.loop ? a % this.duration :
            a, g = !this.loop && a >= this.duration;
        if (c == this._prevPos)
            return g;
        this._prevPosition = a;
        this.position = this._prevPos = c;
        for (var e = 0, f = this._tweens.length; f > e; e++)
            if (this._tweens[e].setPosition(c, b), c != this._prevPos)
                return !1;
        return g && this.setPaused(!0), this.dispatchEvent("change"), g
    };
    b.setPaused = function (a) {
        this._paused = !!a;
        createjs.Tween._register(this, !a)
    };
    b.updateDuration = function () {
        for (var a = this.duration = 0, b = this._tweens.length; b > a; a++) {
            var c = this._tweens[a];
            c.duration > this.duration && (this.duration =
                c.duration)
        }
    };
    b.tick = function (a) {
        this.setPosition(this._prevPosition + a)
    };
    b.resolve = function (a) {
        var b = parseFloat(a);
        return isNaN(b) && (b = this._labels[a]), b
    };
    b.toString = function () {
        return "[Timeline]"
    };
    b.clone = function () {
        throw"Timeline can not be cloned.";
    };
    b._goto = function (a) {
        a = this.resolve(a);
        null != a && this.setPosition(a)
    };
    createjs.Timeline = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"Ease cannot be instantiated.";
    };
    c.linear = function (b) {
        return b
    };
    c.none = c.linear;
    c.get = function (b) {
        return -1 > b && (b = -1), 1 < b && (b = 1), function (a) {
            return 0 == b ? a : 0 > b ? a * (a * -b + 1 + b) : a * ((2 - a) * b + (1 - b))
        }
    };
    c.getPowIn = function (b) {
        return function (a) {
            return Math.pow(a, b)
        }
    };
    c.getPowOut = function (b) {
        return function (a) {
            return 1 - Math.pow(1 - a, b)
        }
    };
    c.getPowInOut = function (b) {
        return function (a) {
            return 1 > (a *= 2) ? 0.5 * Math.pow(a, b) : 1 - 0.5 * Math.abs(Math.pow(2 - a, b))
        }
    };
    c.quadIn = c.getPowIn(2);
    c.quadOut =
        c.getPowOut(2);
    c.quadInOut = c.getPowInOut(2);
    c.cubicIn = c.getPowIn(3);
    c.cubicOut = c.getPowOut(3);
    c.cubicInOut = c.getPowInOut(3);
    c.quartIn = c.getPowIn(4);
    c.quartOut = c.getPowOut(4);
    c.quartInOut = c.getPowInOut(4);
    c.quintIn = c.getPowIn(5);
    c.quintOut = c.getPowOut(5);
    c.quintInOut = c.getPowInOut(5);
    c.sineIn = function (b) {
        return 1 - Math.cos(b * Math.PI / 2)
    };
    c.sineOut = function (b) {
        return Math.sin(b * Math.PI / 2)
    };
    c.sineInOut = function (b) {
        return -0.5 * (Math.cos(Math.PI * b) - 1)
    };
    c.getBackIn = function (b) {
        return function (a) {
            return a *
                a * ((b + 1) * a - b)
        }
    };
    c.backIn = c.getBackIn(1.7);
    c.getBackOut = function (b) {
        return function (a) {
            return --a * a * ((b + 1) * a + b) + 1
        }
    };
    c.backOut = c.getBackOut(1.7);
    c.getBackInOut = function (b) {
        return b *= 1.525, function (a) {
            return 1 > (a *= 2) ? 0.5 * a * a * ((b + 1) * a - b) : 0.5 * ((a -= 2) * a * ((b + 1) * a + b) + 2)
        }
    };
    c.backInOut = c.getBackInOut(1.7);
    c.circIn = function (b) {
        return -(Math.sqrt(1 - b * b) - 1)
    };
    c.circOut = function (b) {
        return Math.sqrt(1 - --b * b)
    };
    c.circInOut = function (b) {
        return 1 > (b *= 2) ? -0.5 * (Math.sqrt(1 - b * b) - 1) : 0.5 * (Math.sqrt(1 - (b -= 2) * b) + 1)
    };
    c.bounceIn =
        function (b) {
            return 1 - c.bounceOut(1 - b)
        };
    c.bounceOut = function (b) {
        return 1 / 2.75 > b ? 7.5625 * b * b : 2 / 2.75 > b ? 7.5625 * (b -= 1.5 / 2.75) * b + 0.75 : 2.5 / 2.75 > b ? 7.5625 * (b -= 2.25 / 2.75) * b + 0.9375 : 7.5625 * (b -= 2.625 / 2.75) * b + 0.984375
    };
    c.bounceInOut = function (b) {
        return 0.5 > b ? 0.5 * c.bounceIn(2 * b) : 0.5 * c.bounceOut(2 * b - 1) + 0.5
    };
    c.getElasticIn = function (b, a) {
        var c = 2 * Math.PI;
        return function (d) {
            if (0 == d || 1 == d)
                return d;
            var g = a / c * Math.asin(1 / b);
            return -(b * Math.pow(2, 10 * (d -= 1)) * Math.sin((d - g) * c / a))
        }
    };
    c.elasticIn = c.getElasticIn(1, 0.3);
    c.getElasticOut =
        function (b, a) {
            var c = 2 * Math.PI;
            return function (d) {
                if (0 == d || 1 == d)
                    return d;
                var g = a / c * Math.asin(1 / b);
                return b * Math.pow(2, -10 * d) * Math.sin((d - g) * c / a) + 1
            }
        };
    c.elasticOut = c.getElasticOut(1, 0.3);
    c.getElasticInOut = function (b, a) {
        var c = 2 * Math.PI;
        return function (d) {
            var g = a / c * Math.asin(1 / b);
            return 1 > (d *= 2) ? -0.5 * b * Math.pow(2, 10 * (d -= 1)) * Math.sin((d - g) * c / a) : 0.5 * b * Math.pow(2, -10 * (d -= 1)) * Math.sin((d - g) * c / a) + 1
        }
    };
    c.elasticInOut = c.getElasticInOut(1, 0.3 * 1.5);
    createjs.Ease = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = function () {
        throw"MotionGuidePlugin cannot be instantiated.";
    };
    c.priority = 0;
    c._rotOffS;
    c._rotOffE;
    c._rotNormS;
    c._rotNormE;
    c.install = function () {
        return createjs.Tween.installPlugin(c, ["guide", "x", "y", "rotation"]), createjs.Tween.IGNORE
    };
    c.init = function (b, a, c) {
        var d = b.target;
        return d.hasOwnProperty("x") || (d.x = 0), d.hasOwnProperty("y") || (d.y = 0), d.hasOwnProperty("rotation") || (d.rotation = 0), "rotation" == a && (b.__needsRot = !0), "guide" == a ? null : c
    };
    c.step = function (b, a, k, d, g) {
        if ("rotation" == a && (b.__rotGlobalS =
                k, b.__rotGlobalE = d, c.testRotData(b, g)), "guide" != a)
            return d;
        var e;
        d.hasOwnProperty("path") || (d.path = []);
        a = d.path;
        if (d.hasOwnProperty("end") || (d.end = 1), d.hasOwnProperty("start") || (d.start = k && k.hasOwnProperty("end") && k.path === a ? k.end : 0), d.hasOwnProperty("_segments") && d._length)
            return d;
        k = a.length;
        if (!(6 <= k && 0 == (k - 2) % 4))
            throw"invalid 'path' data, please see documentation for valid paths";
        d._segments = [];
        d._length = 0;
        for (var f = 2; k > f; f += 4) {
            for (var h, l, m = a[f - 2], n = a[f - 1], p = a[f + 0], r = a[f + 1], q = a[f + 2], s = a[f + 3],
                     u = m, t = n, w = 0, z = [], A = 1; 10 >= A; A++) {
                l = A / 10;
                var y = 1 - l;
                h = y * y * m + 2 * y * l * p + l * l * q;
                l = y * y * n + 2 * y * l * r + l * l * s;
                w += z[z.push(Math.sqrt((e = h - u) * e + (e = l - t) * e)) - 1];
                u = h;
                t = l
            }
            d._segments.push(w);
            d._segments.push(z);
            d._length += w
        }
        e = d.orient;
        d.orient = !0;
        a = {};
        return c.calc(d, d.start, a), b.__rotPathS = Number(a.rotation.toFixed(5)), c.calc(d, d.end, a), b.__rotPathE = Number(a.rotation.toFixed(5)), d.orient = !1, c.calc(d, d.end, g), d.orient = e, d.orient ? (b.__guideData = d, c.testRotData(b, g), d) : d
    };
    c.testRotData = function (b, a) {
        if (void 0 === b.__rotGlobalS ||
            void 0 === b.__rotGlobalE) {
            if (b.__needsRot)
                return;
            b.__rotGlobalS = b.__rotGlobalE = void 0 !== b._curQueueProps.rotation ? b._curQueueProps.rotation : a.rotation = b.target.rotation || 0
        }
        if (void 0 !== b.__guideData) {
            var c = b.__guideData, d = b.__rotGlobalE - b.__rotGlobalS, g = b.__rotPathE - b.__rotPathS, e = d - g;
            if ("auto" == c.orient)
                180 < e ? e -= 360 : -180 > e && (e += 360);
            else if ("cw" == c.orient) {
                for (; 0 > e;)
                    e += 360;
                0 == e && 0 < d && 180 != d && (e += 360)
            } else if ("ccw" == c.orient) {
                for (e = d - (180 < g ? 360 - g : g); 0 < e;)
                    e -= 360;
                0 == e && 0 > d && -180 != d && (e -= 360)
            }
            c.rotDelta =
                e;
            c.rotOffS = b.__rotGlobalS - b.__rotPathS;
            b.__rotGlobalS = b.__rotGlobalE = b.__guideData = b.__needsRot = void 0
        }
    };
    c.tween = function (b, a, k, d, g, e, f) {
        g = g.guide;
        if (void 0 == g || g === d.guide)
            return k;
        if (g.lastRatio != e) {
            switch (c.calc(g, (g.end - g.start) * (f ? g.end : e) + g.start, b.target), g.orient) {
                case "cw":
                case "ccw":
                case "auto":
                    b.target.rotation += g.rotOffS + g.rotDelta * e;
                    break;
                default:
                    b.target.rotation += g.rotOffS
            }
            g.lastRatio = e
        }
        return "rotation" != a || g.orient && "false" != g.orient ? b.target[a] : k
    };
    c.calc = function (b, a, k) {
        void 0 ==
        b._segments && c.validate(b);
        void 0 == k && (k = {x: 0, y: 0, rotation: 0});
        var d = b._segments, g = b.path, e = b._length * a, f = d.length - 2;
        for (a = 0; e > d[a] && f > a;)
            e -= d[a], a += 2;
        for (var d = d[a + 1], h = 0, f = d.length - 1; e > d[h] && f > h;)
            e -= d[h], h++;
        e = h / ++f + e / (f * d[h]);
        a = 2 * a + 2;
        f = 1 - e;
        return k.x = f * f * g[a - 2] + 2 * f * e * g[a + 0] + e * e * g[a + 2], k.y = f * f * g[a - 1] + 2 * f * e * g[a + 1] + e * e * g[a + 3], b.orient && (k.rotation = 57.2957795 * Math.atan2((g[a + 1] - g[a - 1]) * f + (g[a + 3] - g[a + 1]) * e, (g[a + 0] - g[a - 2]) * f + (g[a + 2] - g[a + 0]) * e)), k
    };
    createjs.MotionGuidePlugin = c
})();
this.createjs = this.createjs || {};
(function () {
    var c = createjs.TweenJS = createjs.TweenJS || {};
    c.version = "NEXT";
    c.buildDate = "Thu, 12 Dec 2013 23:37:07 GMT"
})();
</script>
<script>(function () {
    function m(e, b, f, g) {
        b.split(" ").forEach(function (b) {
            e.addEventListener(b, f, g || !1)
        });
    }

    function C(e, b, f, g) {
        b.split(" ").forEach(function (b) {
            e.removeEventListener(b, f, g || !1)
        })
    }

    function M() {
        function e() {
            n = 0;
            Z = K;
            D = N;
            x = M;
            O = Y;
            q.text = p.text = r = 0;
            q.x = p.x = 400;
            i$ = Date.now() / 1E3 | 0;
            window.removeEventListener("resize", f, !1);
            s.style.display = "none";
            B()
        }

        function b() {
            y = Math.min(innerWidth / 640, innerHeight / 960);
            P = 640 * y | 0;
            Q = 960 * y | 0;
            R = (innerHeight - Q) / 2 | 0;
            S = (innerWidth - P) / 2 | 0;
            var d = E.style, c = {
                width: P + "px", height: Q +
                "px", left: S + "px", top: R + "px"
            }, a;
            for (a in c)
                c.hasOwnProperty(a) && (d[a] = c[a])
        }

        function f() {
            var d = 640 / 960 * innerHeight | 0;
            s.style.width = d + "px";
            s.style.left = ((innerWidth - d) / 2 | 0) + "px";
            aa.style.fontSize = (34 / 960 * innerHeight | 0) + "px"
        }

        function g() {
            function d(a) {
                a.stopPropagation();
                switch (a.target.getAttribute("data-action")) {
                    case "retry":
                        C(s, "click", d);
                        e();
                       /* _hmt.push(["_trackEvent", "button", "gameReStart"]);*/
                        break;
                    case "share":
                        F.style.display = "block", m(F, "click", function fa() {
                            C(F, "click", fa);
                            F.style.display = "none"
                        })/*, _hmt.push(["_trackEvent", "button", "share"])*/
                }
            }

            f();
            //  成绩百分比 计算
            window.addEventListener("resize", f, !1);
            k = 100 * (r / 80);
            k = isNaN(k) ? 0.1 : Math.max(0.1, Math.min(99.9, k));
            k = k.toFixed(1);
            for (var c = n, a = T.length - 1; -1 < a; --a) {
                var ba = T[a], c = c - ba[0] * n;
                if (r >= c) {
                    ca = ba[1];
                    break
                }
            }
            //游戏结束统计
/*            aa.innerHTML = 'Congratulate you catch the phone<span style="color:#f00">' + r + "</span>times,超过了" + k + "%的小渣渣" + ca + '';*/
            aa.innerHTML = 'You catch the phone <span style="color:#f00">' + r + "</span> times";
            da(r);
            s.style.display = "block";
            m(s, "click", d)

            /*document.getElementById("board-btn").style.display="none";*/
        //////////////////
        judgeLimits();

//             if(r>0){
//                 $(".nowins_tips").hide();
//                 setTimeout(function(){
//                     mask.style.display="block";
//                     tbox0.show();
//                 },2000);
//             }else{
//                 $(".nowins_tips").show();
//             }

        }

        function L(d) {
            var c = new createjs.Bitmap(ga);
            c.x = d.x - ha / 2 - 13;
            c.y = d.y - ea - 14;
            l.addChild(c);
            var a = new createjs.Bitmap(ia);
            return {
                full: function () {
                    var c = a.getBounds();
                    a.x = d.x - c.width / 2 - 10;
                    a.y = d.y - ea + 34;
                    l.addChild(a)
                }, clean: function () {
                    l.removeChild(c);
                    l.removeChild(a)
                }
            }
        }

        function z(d) {
            function c(a, d, e, t, b) {
                0 >= e ? b() : (--e, G.get(a).to({x: d}, t).to({x: d}, t).call(function () {
                    c(a, d, e, t, b)
                }))
            }

            var a = d.person;
            if (!a || a.y > u - 10)
                return !1;
            d.hit = !0;
            ++r;
            q.text = p.text = r;
            q.x = p.x =
                480 - p.getBounds().width;
            var e = d.y + a.y+150; // 手指捕捉 图片位置;
            G.removeTweens(a);
            var t = L({x: d._x, y: e});
            c(a, 5, 1, x / 8, function () {
                a.visible = !1;
                var e = h.getResult(a.wetId), b = new createjs.Bitmap(e);
                b.x = 0;
                b.y = a.y; // 点击捕捉到的手机位置
                d.addChild(b);
                t.full();
                c(b, 3, 2, x / 8, function () {
                    setTimeout(function () {
                        t.clean();
                        G.get(b).to({y: u, x: 0}, O).call(function () {
                            d.person = null;
                            d.removeChild(a);
                            d.removeChild(b);
                            a.x = 0;
                            a.y = u;
                            d.shown = !1;
                            d.hit = !1
                        })
                    }, x / 8)
                })
            });
            return !0
        }

        function ja() {
            for (var d = [], c = v.length - 1; -1 < c; --c) {
                var a = v[c];
                a.shown || a.hit || d.push(a)
            }
            return d
        }

        function ka(d,
                    c) {
            for (var a = d.slice(), e = Math.min(a.length, c), b = [], f = 0; f < e; ++f) {
                var g = a.length * Math.random() | 0;
                b.push(a[g]);
                a.splice(g, 1)
            }
            return b
        }

        function la() {
            var d = "person-" + U[U.length * Math.random() | 0], c = h.getResult(d), c = new createjs.Bitmap(c);
            c.wetId = d + "-wet";
            c.x = 0;
            c.y = u;
            return c
        }

        function w(d) {
            var c = ja();
            0 < c.length && c.length > v.length / 2 && (d = ka(c, d), n += d.length, d.forEach(function (a, d) {
                a.shown = !0;
                var c = a.person = la();
                a.addChild(c);
                G.get(c).wait(100 * d).to({y: 0}, Z).wait(x).to({y: u}, O).call(function () {
                    a.person = null;
                    a.removeChild(c);
                    a.shown = !1
                })
            }))
        }

        function B() {
            var d = i$ + V - (Date.now() / 1E3 | 0);
            0 >= d ? (A.text = '0"', setTimeout(g, 2E3)) : (10 >= d ? (D = 0.6 * N, w(2)) : (D = (0.5 + 0.5 * d / V) * N, w(1)), A.text = d + '"', setTimeout(B, D))
        }

        var E = document.getElementById("canvas"), l = new createjs.Stage(E), G = createjs.Tween, H = createjs.Ticker;
        H.setFPS(30);
        H.addEventListener("tick", l);
        var v = [], u = 174, J = [516, 671, 835], H = [].concat.apply([], [118, 330, 538].map(function (d) {
            return J.map(function (c) {
                return [d, c]
            })
        })), K = 300, N = 600, M = 300, Y = 300, Z, D, x, O, r, V = 30, i$, W = new createjs.Bitmap(h.getResult("bg"));
        W.x = 0;
        W.y = 0;//画布背景图 位置
        l.addChild(W);
        var A = new createjs.Text(V + '"', "72px Arial", "#fff");
        A.x = 130;
        A.y = 28; //秒表位置
        l.addChild(A);
        var p = new createjs.Text(0, "148px Arial", "#fff"), q = new createjs.Text(0, "148px Arial", "#253574");
        q.x = p.x = 400;
        q.y = p.y = 28; //计数器位置;
        p.outline = 6;
        l.addChild(p);
        l.addChild(q);
        var s = document.getElementById("board"), aa = document.getElementById("board-text"), P, Q, R, S, y;
        b();
        window.addEventListener("resize", b, !1);
        var F = document.getElementById("share-tip");
        /*(new Image).src = "img/share_tip.png";*/
        var ha = 114, ea =
            152, ga = h.getResult("bucket"), ia = h.getResult("water"), X = ["touchstart", "mousedown"], I;
        m(E, X.join(" "), function c(a) {
            if (void 0 === I) {
                I = a.type;
                for (var e = X.length - 1; -1 < e; --e) {
                    var b = X[e];
                    b !== I && C(E, b, c)
                }
            } else if (I !== a.type)
                return;
            a.stopPropagation();
            a.preventDefault();// 阻止默认行为 ；解决ios 点击闪屏现象；
            b = a.touches ? a.touches[0].pageX : a.pageX;
            a = a.touches ? a.touches[0].pageY : a.pageY;
            b = (b - S) / y | 0;
            a = (a - R) / y | 0;
            for (e = v.length - 1; -1 < e; --e) {
                var f = v[e];
                if (!f.hit && f.shown) {
                    var g = f.x, l = f.y;
                    if (b >= g && (b <= g + 200 && a >= l && a <= l + 180) && z(f)) // 手指点中 位置;
                        break
                }
            }
        });
        H.forEach(function (c) {
            var a = new createjs.Container,
                e = c[0];
            c = c[1];
            a._x = e;
            a._y = c;
            a.x = e - 71;
            a.y = c - u;
            var b = new createjs.Shape;
            b.setBounds(0, 0, 200, 200);
            b.graphics.drawCircle(100, 100, 100);
            b.x = e - 100;
            b.y = c - 200; //手机出来的高度 phone show height
            a.mask = b;
            l.addChild(a);
            v.push(a)
        });
        e()
    }

    function da(e) {
        e = e || 0;
        var b = " ";
        B("http://game.3gjj.cn/games/ice_bucket/img/bucket.png", b, "buzhi dao sha A" + e + "  bu zhi dao  sha b" + k + "bu zhi dao sha  c",
            "bu zhi dao sha D", " ")
    }

    var B;
    !function () {
        var e = "", b = "", f = "", g = "", k = "";
        B = function (z, h, m, n, w) {
            "" != z && null != z ? e = z : "";
            "" != h && null != h ? b = h : "";
            "" != m && null != m ? f = m : "";
            "" != n && null != n ? g = n : "";
            "" != w && null != w ? k = w : ""
        };
        document.addEventListener("WeixinJSBridgeReady", function () {
            WeixinJSBridge.on("menu:share:appmessage", function (h) {
                WeixinJSBridge.invoke("sendAppMessage",
                    {img_url: e, link: b, desc: g, title: f}, function (b) {
                        document.location.href = k
                    })
            });
            WeixinJSBridge.on("menu:share:timeline", function (h) {
                WeixinJSBridge.invoke("shareTimeline", {
                    img_url: e,
                    img_width: "300",
                    img_height: "300",
                    link: b,
                    desc: g,
                    title: f
                }, function (b) {
                    document.location.href = k
                })
            });
            WeixinJSBridge.on("menu:share:weibo", function (e) {
                WeixinJSBridge.invoke("shareWeibo", {content: g, url: b}, function (b) {
                    document.location.href = k
                })
            })
        }, !1)
    }();
        // 加载画面 准备游戏
    var Y = document.getElementById("loading"), J = document.getElementById("loading-progress");
    J.innerHTML = "10";
    var U = ["lj", "fs", "ldh"], K = [{src: "bg.png", id: "bg"}, {
        src: "bucket.png",
        id: "bucket"
    }, {src: "water-full.png", id: "water"}];
    U.forEach(function (e) {
        K.push({src: "person-" + e + ".png", id: "person-" + e}, {
            src: "person-" + e + "-wet.png",
            id: "person-" + e + "-wet"
        })
    });
    var h = new createjs.LoadQueue(!1, "<%=basePath%>static/images/game/gamectp/"); //图片路径配置
    h.loadManifest(K);
    h.on("progress", function (e) {
        J.innerHTML = (100 * e.progress).toFixed(2)
    });
    m(document, "touchmove", function (e) {
        e.preventDefault();
        e.stopPropagation()
    });
    //评级
    var k = 0.1, n = 0, T = [[0.1, "good1"], [0.2, "good2"], [0.3, "good3"], [0.3, "good4"], [0.1, "good5"]], ca = T[0][1];
    h.on("complete", function () {
        var e = document.getElementById("start"), b = document.getElementById("start-btn"), f = !1;
        m(b, "click", function L(h) {
            h.preventDefault();
            h.preventDefault();
            C(b, "click", L);
            e.style.display = "none";
            f || (f = !0, M()/*, _hmt.push(["_trackEvent", "button", "gameStart"])*/)
            recordClickEvent('${suid}','toolbar/preCompetition.jsp/start-btn');
        });
        Y.style.display = "none"
    });
    da();
})();

!function(){
    $(".iclose,#oopsConfirm").on("click",function () {
        $("#aqmask").hide();
        $(this).parents(".tips_box").hide();
    });
    $("#trywins_pic").on("click",function(){
        console.log("jinlaile");
        
        luckDraw();
        
        recordClickEvent('${suid}','toolbar/preCompetition.jsp/trywins_pic');	
        
    });
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
	        $("#aqmask").show();
	        $(this).parents(".tips_box").hide();
	        $(".nowins_tips").show();    		
	        $("#tips_boxp4").show();
	        worngAnsCount = 0;    		
    	}
    	
    	recordClickEvent('${suid}','toolbar/preCompetition.jsp/no');
    	
    })


}()

function scroll(obj) {
    /*往左*/
    var tmp = (obj.scrollLeft)++;
//当滚动条到达右边顶端时
    if (obj.scrollLeft == tmp) {
        obj.scrollLeft = 0;
    }
}
var _timer = setInterval("scroll(document.getElementById('marquee'))", 20);
</script>
<!--<script type="text/javascript" src="js/gamePhone.js" charset="UTF-8"></script>-->
</html>