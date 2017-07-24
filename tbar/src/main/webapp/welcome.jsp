<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Welcome</title>	
    <%@ include file="toolbar/header.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
  	<link rel="stylesheet" href="<%=basePath%>static/css/tbBall.css">
  	<script>
  		
  	</script>
  	<style type="text/css">
  		#popRemind{font-family:"Exo 2",sans-serif,monospace; z-index: 9900;}
  	</style>
  </head>
  
  <body>
    <div class="popbox" id="popRemind">
		<div class="popbox_bg setting_close">
			<div class="popbox_top close_top" <c:if test="${param.serviceType eq 'Postpaid'}">style="background-color:#00aeef";</c:if>><img style="display: inline-block;margin: 0 auto;width: 30%;vertical-align: middle;" alt="" <c:if test="${param.serviceType eq 'Prepaid'}">src="<%=basePath %>static/images/imgs/welcome.jpg"</c:if> <c:if test="${param.serviceType eq 'Postpaid'}">src="<%=basePath %>static/images/imgs/welcome.png"</c:if>> <i onclick="showIntro('<%=basePath %>','${param.suid}','${param.serviceType }')" class="iclose"><img src="<%=basePath %>static/images/icon/xx.png"></i></div>
			<!-- <div class="s_text_con">
			<p>Check your Xpax credit balance,</p>
			<p>Internet usage or subscribe to any </p>
			<p>Internet Plans and many more here!</p>
			</div> -->		
			<img style="width: 80%;margin: 40px auto;display: block;" alt="" <c:if test="${param.serviceType eq 'Prepaid'}">src="<%=basePath %>static/images/imgs/tips.jpg"</c:if> <c:if test="${param.serviceType eq 'Postpaid'}">src="<%=basePath %>static/images/imgs/tips.png"</c:if>>
			<div class="popbox_btn btn_inline_block">
				<button type="button" onclick="showIntro('<%=basePath %>','${param.suid}','${param.serviceType}')" class="btn_ov btn_mid btn_ok" id="closePop">OK</button>
			</div>
		</div>
	</div>
	<div id="d_h" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; z-index:2100000000;border:none;padding:0;margin:0; background:#fff;-webkit-overflow-scrolling:touch;overflow-y:auto;"></div>
  </body>
</html>