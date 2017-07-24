<%@ include file="../../../toolbar/header.jsp" %>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>Usage</title>
    <link rel="stylesheet" href="<%=basePath %>static/css/base.css">
    <script src="<%=basePath %>static/js/jquery.1.8.3.min.js"></script>    
    <style type="text/css">
    	
    	html ,body{height:100%; font-family:"Exo 2",sans-serif,monospace;}
        .wave_wrap{
            width: 2.92rem;
            height: 2.92rem;
            -webkit-border-radius: 50%;
            -moz-border-radius: 50%;
            border-radius: 50%;
            -webkit-transform:rotate(0deg);
            margin:0.65rem auto;
            position: relative;
            background-image: url("../static/images/tbBall/ballBg.png");
            -webkit-background-size:cover;
            background-size:cover;
            overflow: hidden;
           

        }
        .wave_wrap img{
            width: 100%;
            position: absolute;
            bottom:0;
            left: 0;
            transition: all 3s ease;
        }
        .tb_ball_zout{
            width: 2.92rem;
            height: 2.92rem;
            -webkit-background-size:cover;
            background-size:cover;
            background-image: url("../static/images/tbBall/ballZout.png");
            position: absolute;
            top:0.01rem;
            left: 0.01rem;
            z-index: 1000;
        }
        .query_msg{
        	color: #fc7f01;
        	font-size: 0.24rem;
        	font-weight: normal;
        }
        .query_msg2{
        	color: #fc7f01;
        	font-size: 0.24rem;
        	font-weight: normal;
        }
        .top_infos{
         margin-top: 0.5rem;
         }
         .expire_info{
         	font-size: 0.20rem;
         	font-weight: normal;
         }
         .laston_info{
         	text-align: center;
         	font-size: 0.20rem;
         	font-weight: normal;
         }
    </style>

</head>
<body class="pt-08r" >

<header class="usage tb_header fixed-top">
    <span class="s_gap"></span>
    <h2>Usage</h2>
     <i class="close_i"> </i>
</header>
<div class="top_infos" style="">
    <div class="tip_infos_inner" id="prepaid">
        <p class="usage_title">Credit Balance</p>
        <label class="query_msg2" style="display:block">Querying...</label>
        <label class="usage_total_balance" id="balance"></label>
        <p class="expire_info" id="expiry"></p>
    </div>

    <div class="wave_wrap">
        <div class="tb_ball_zout"></div>
        <img class="ball_wave" src="" alt="">
        <div class="wave_balance_cont">
            <p class="wave_balance_name">Internet Available(GB)</p>
            <p style="display:block;" class="query_msg">Querying...</p>
            <p class="wave_balance_num" id="remainedData"></p>
        </div>
    </div>
    <p class="balance_credit">Internet Quota: <span class="query_msg">Querying...</span><span class="balance_currency" id="totalData"></span><br><span class="expire_info" id="expiry2"></span> </p>
    <div class="tip_infos_inner">
    	<p class="laston_info" id="lastOn"></p>
    </div>
</div>

<!-- <div class="package_detail usage">
    <p class="title">My plans</p>
    <p class="errmsg"></p>
</div> -->

<!-- <footer style="background-color:#5c398d; width:100%; height:0.4rem;position:fixed;bottom:0;"></footer> -->

<script type="text/javascript">
	var baseUrl = "<%=basePath%>";
	var browseUrl = "toolbar/usage.jsp";
	
	function ball_wave(per) {
        $(".ball_wave").attr("src",baseUrl + 'static/images/tbBall/wave/' + per + '.png');
    }
	
	window.onload = function() {
		//查询剩余流量
		$.ajax({
			type: "get",
			async: false,
			url: "<%=basePath%>portal/queryUserFlow?suid=${suid}",
			dataType: "jsonp",
			jsonp: "jsonpCallback",
			success : function(data) {
				//console.log(data.result);
				var result = eval( "(" + data.result + ")" );
				if(result.body.retCode == "0"){
					$(".query_msg").hide();
					$("#totalData").text(Number(result.body.totalData).toFixed(2)+"GB");//总流量
					$("#remainedData").text( Number(result.body.remainedData).toFixed(2) );//剩余流�
					var temp = result.body.remainedData/result.body.totalData*10;
					if(!$.isNumeric(temp)){
						temp = 0;
					}
					if(temp<1){
						$(".ball_wave").hide();
					}
					else{
						ball_wave((temp>10?10:Math.round(temp))*10);
						$(".ball_wave").show();
						//到期时间
						if(result.body.validity2 != null && result.body.validity2 != undefined && result.body.validity2 != ""){
							$("#expiry2").text("Expiry date: " + result.body.validity2);
						}
						//上一次获取时间
						if(result.body.lastOn2 != null && result.body.lastOn2 != undefined && result.body.lastOn2 != ""){
							$("#lastOn").text("Last updated at " + result.body.lastOn2);
						}
						else{
							if(result.body.lastOn != null && result.body.lastOn != undefined && result.body.lastOn != ""){
								$("#lastOn").text("Last updated at " + result.body.lastOn);
							}
						}
					}
				}
				else{
					$(".query_msg").text("Query failed...");
					$(".ball_wave").hide();
				}
				//预付费余额
				if(result.body.serviceType != null && result.body.serviceType != undefined && result.body.serviceType == "prepaid"){
					$("#prepaid").show();
					if(result.body.balance != null && result.body.balance != undefined){
						$(".query_msg2").hide();
						$("#balance").text(result.body.unit + result.body.balance);
						if(result.body.validity != null && result.body.validity != undefined && result.body.validity != ""){
							$("#expiry").text("Expiry date: " + result.body.validity);
						}
						//上一次获取时间
						if(result.body.lastOn != null && result.body.lastOn != undefined && result.body.lastOn != ""){
							$("#lastOn").text("Last updated at " + result.body.lastOn);
						}
						else{
							if(result.body.lastOn2 != null && result.body.lastOn2 != undefined && result.body.lastOn2 != ""){
								$("#lastOn").text("Last updated at " + result.body.lastOn2);
							}
						}
					}
					else{
						$(".query_msg2").text("Query failed...");
					}
				}
				else{
					$("#prepaid").hide();
				}
			},
			error:function(){
				//alert('fail');
			}
		});
		
		//查询已订购套餐
		<%-- 
		$.ajax({
			type: "get",
			async: false,
			url: "<%=basePath%>portal/queryMyPackage?suid=${suid}",
			dataType: "jsonp",
			jsonp: "jsonpCallback",
			success : function(data) {
				//console.log(data.result);
				$(".errmsg").html("");
				var result = eval( "(" + data.result + ")" );
				try{
					if(result.resultCode == "0" && result.body.retCode == "0"){
						var info = result.body.packageInfo;
						for(var j=0;j<info.length;j++){
							var html = "<div class=\"package_cont usage_plan\">";
							html += "<div class=\"package_title\"><p>" + info[j].packageName + "</p></div>";
							html += "<div class=\"package_name\"><p >Internet Usage</p></div>";
							html += "<p class=\"plan_text1\">" + info[j].volumeUsed + "GB</p>";
							html += "<div class=\"plan_balance_tip\"><p >Internet balance<span class=\"plan_balance_num\">" + info[j].purchasedVolume + "GB</span></p></div>";
							html += "<div class=\"balance_progress_wrap\"><div class=\"balance_progress\"><i class=\"progress_current\" style=\"width: " + (info[j].volumeUsed/info[j].purchasedVolume)*100 + "%\"></i></div></div>";
							html += "<p class=\"plan_date\">Expiry date:" + info[j].endTime + "</p></div>";
							
							$(".package_detail.usage").after(html);
						}
					}
					else{
						//modify by zhulin 2019-09-30 修改英文样式，后期要重制提醒
						$(".errmsg").html("<p style='color:#fff;text-align:center'>Query no data.</p>");
					}
				}
				catch(e){
					$(".errmsg").html("<p style='color:#fff;text-align:center'>The network is busy, please try again later.</p>");
				}
			},
			error:function(){
				//alert('fail');
			}
		});
		 --%>
		//记录页面访问事件
		recordVist('${suid}');
	};
</script>
<script src="<%=basePath%>js/pageEvent.js"></script>
</body>

</html>