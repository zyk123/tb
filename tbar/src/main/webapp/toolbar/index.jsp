<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp" %>
<%
String tOperatorId = request.getParameter("tOperatorId");
String userId = request.getParameter("userId");
String timeZone = request.getParameter("timeZone");
String language = request.getParameter("language");
String countryNo = request.getParameter("countryNo");
String isFirstUseToolbar = request.getParameter("isFirstUseToolbar");
String suid = request.getParameter("suid");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath%>static/css/tbBall_S2.css">
</head>
<body style="top: 0px; margin-top: 0px;" id="body">
<div class="toolbar_menu " id="toolbar_menu">
	<div class="menu_container" >
		<ul>
			<li class="menu_item bg1" data-menu-index="1" style="display: block;">
				<div id="mainBall" ><img id="main_ball" style="opacity: 0;" class="imgc ball_a_pic" src=""></div>
			</li>
<!-- 			<li class="menu_item bg2" data-menu-index="2" style="display: none;"> -->
<!-- 				<div class="waveball_wrap"  ><img id="flow_ball" class="imgc" src=""></div> -->
<!-- 				<div id="flow_tips" class="tradet ball_a_pic"> -->
<!-- 					<strong id="toolbar_per">20%</strong> -->
<!-- 					<%--<em id="toolbar_left">30M</em>--%> -->
<!-- 				</div> -->
<!-- 			</li> -->
<!-- 			<li class="menu_item bg3" data-menu-index="3" style="display: none;"> -->
<!-- 				<div class="waveball_wrap" ><img id="activity_ball" class="imgc ball_a_pic" src=""></div> -->
<!-- 			</li> -->
		</ul>
	</div>
<!-- 	<div id="left_refer" class="left_refer"  style="display: none;"> -->
<!-- 		<div class="top_title"> -->
<!-- 				<p>Your internet data is running out.</p> -->
<!-- 		</div> -->
<!-- 		<div class="bottom_cont"> -->
<!-- 				<p id="rec_package" class="ball_a_pic" data-cnToolbarIf="<%= basePath%>page/turnPackageDetailOrder? -->
<!-- 			packageid=12&fgtips=Add-On Internet 500MB&fgno=45562&fgname=Add-On Internet 500MB& -->
<!-- 			ptname=Add-OnInternet&fgdesc=Follow Internet plan validity period&amount=10.0000&currency=RM&expenses=''&suid=<%=suid%>"><%--<span class="import">RM50</span>--%><strong>RM10/500MB<i class="gap"></i></strong><span style="float: right; margin-right: 10%;">Add-On Now</span> <i class="icon"></i></p> -->
<!-- 		</div> -->
	</div>
</div>
<div class="toolbar_active_ad" id="activeAd" style="display: none;">
	<i class="iclose" onclick="javascript:toolbar.closetbad(this);"></i>
	<img id="activity_pop" class="ball_a_pic" src="" alt="">
</div>
<div id="cnToolbarWrap" style="display:none;visibility:initial; position:fixed; top:0; left:0; width:100%; height:100%; z-index:2100000000;border:none;padding:0;margin:0; background:#fff;">
 <i id="cnToolbarIfWrapClose" style="display:block; position:absolute; top:0; right:0; width:15%; height:60px;z-index: 100;"></i>
	<div id="cnToolbarIfWrap" style="height: 86%;-webkit-overflow-scrolling:touch;overflow:auto;"></div>
	<div class="foller_nav original" style="height: 14%; width: 100%; overflow: hidden; z-index: 2140000011;" >
		<footer class="floor_nav floor " <c:if test="${param.serviceType eq 'Postpaid'}">style="background-color:#00aeef;background:#00aeef";</c:if>>
			<div class="floor-container">
				<ul class="nav-ul">
					<li class="active" id="homelc">
		                <span class="bar-img">
		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnUsage?suid=${param.suid}" style="display:inline!important;border:none!important;"><img src="<%=basePath%>static/images/icon/nav/iconHome.png" alt="">
		                    
		                   </a>
		                </span>
					</li>					
					<c:if test="${param.serviceType eq 'Prepaid'}">
					<li>
			                <span class="bar-img">
			                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnStore?suid=<%=request.getParameter("suid")%>" style="display:inline!important;border:none!important;"><img src="<%=basePath%>static/images/icon/nav/iconSubscribe.png" alt="">
			                     
			                   </a>
			                </span>
					</li>
					</c:if>
					<li>
                         <span class="bar-img">
                            <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnReload?suid=${param.suid}" style="display:inline!important;border:none!important;"><img src="<%=basePath%>static/images/icon/nav/iconReload.png" alt="">
                             
                            </a>
                         </span>
                    </li>
<!-- 					<li> -->
<!-- 		                <span class="bar-img"> -->
<!-- 		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPromotion?suid=${param.suid}"> <img src="<%=basePath%>static/images/icon/nav/x02.png" alt=""> -->
		                  
<!-- 		                   </a> -->
<!-- 		                </span> -->
<!-- 					</li>					                     									 -->
					<c:if test="${param.serviceType eq 'Prepaid'}">
					<li>
		                <span class="bar-img">
		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPreAccount?suid=${param.suid}" style="display:inline!important;border:none!important;"><img src="<%=basePath%>static/images/icon/nav/iconAccount.png" alt="">
		                  
		                   </a>
		                </span>
					</li>
					</c:if>
					<c:if test="${param.serviceType eq 'Postpaid'}">
					<li>
		                <span class="bar-img">
		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPostAccount?suid=${param.suid}" style="display:inline!important;border:none!important;"><img src="<%=basePath%>static/images/icon/nav/iconAccount.png" alt="">
		                  
		                   </a>
		                </span>
					</li>
					</c:if>
				</ul>
			</div>
		</footer>			
	</div>
	<!-- <div class="floatover " style="width:100%;height:46%; position:absolute; bottom:14%; background-color:rgba(0,0,0,0.5);"></div> -->
<!-- 	<div class="foller_nav home" style="height: 8.5%; width: 100%; overflow: hidden; z-index: 2140000011;" > -->
<!-- 		<footer class="floor_nav floor" > -->
<!-- 			<div class="floor-container"> -->
<!-- 				<ul class="nav-ul"> -->
<!-- 					<c:if test="${param.serviceType eq 'Prepaid'}"> -->
<!-- 					<li> -->
<!-- 		                <span class="bar-img"> -->
<!-- 		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnStore?suid=<%=request.getParameter("suid")%>"></a> -->
<!-- 		                </span> -->
<!-- 					</li> -->
<!-- 					</c:if> -->
<!-- 					<li> -->
<!--                          <span class="bar-img"> -->
<!--                             <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnReload?suid=${param.suid}"></a> -->
<!--                          </span> -->
<!--                      </li> -->
<!-- 					<li> -->
<!-- 		                <span class="bar-img"> -->
<!-- 		                   <a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPromotion?suid=${param.suid}"></a> -->
<!-- 		                </span> -->
<!-- 					</li>					 -->
<!-- 					<li> -->
<!-- 		                <span class="bar-img"> -->
<!-- 		                <c:if test="${param.serviceType eq 'Prepaid'}"> -->
<!-- 		                	<a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPreAccount?suid=${param.suid}"></a> -->
<!-- 		                </c:if> -->
<!-- 		                <c:if test="${param.serviceType eq 'Postpaid'}"> -->
<!-- 		                	<a class="ball_a_pic" data-cnToolbarIf="<%=basePath%>page/turnPostAccount?suid=${param.suid}"></a> -->
<!-- 		                </c:if> -->
		                   
<!-- 		                </span> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 		                <span class="bar-img iconClose"> -->
<!-- 		                </span> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</footer>			 -->
<!-- 	</div> -->
</div>
</body>
<script>
	var basePath = '<%=basePath%>';
	var suid = '${param.suid}';
	var serviceType = '${param.serviceType}';
	var flowThreshold = '${param.flowThreshold}';
	var isAlertOpen = '${param.isAlertOpen}';
</script>
<script type="text/javascript" src="<%=basePath%>static/js/toolbar_page_S2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/pageEvent.js"></script>
<script type="text/javascript">
	var browseUrl = "index.jsp";
	
	function toCall(){
		if(typeof recordVist != 'undefined'){
			recordVist('<%=suid%>');
		}else{
			setTimeout(toCall,1);
		}
	}
	toCall();
	
	$(function(){

	});
</script>
</html>
