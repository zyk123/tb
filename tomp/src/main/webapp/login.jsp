<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="login_html">
<head>
	<%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <link type="text/css" rel="stylesheet" href="<%=path %>/third/showBo/css/showBo.css" />
    <script src="<%=path %>/js/base.js"></script>	
    <script type="text/javascript" src="<%=path %>/third/showBo/js/showBo.js"></script>
</head>
<body class="login_body">
	<script type="text/javascript">
		if (window != top)
		top.location.href = location.href; 		
		document.onkeydown = function(event) {
			e = event ? event :(window.event ? window.event : null); 
		    if (e.keyCode == 13) {
		    	login();
		    }
		}	
		$(function($) {
		 $('#loginForm').validationEngine('attach',{
				promptPosition: 'topLeft'
		});		
		});
		
		function login(){
			var flag = $('#loginForm').validationEngine('validate');
			if(!flag){
				return;
			}
			$('#loginForm').submit();
		}
		
		
			function callback(result){
				$("#loginForm")[0].reset();
				$("#username").focus();
				var success = result.success;
				var msg = result.msg;
				if(success){
					window.location.href = "/tomp/index.jsp";
				}else{
					Showbo.Msg.alert(msg);
				}
			} 			
	</script>
    <div class="loginbox">
        <div class="top">
            <div class="logo"><img src="<%=path %>/image/icon/logo.png" alt=""></div>
<!--             <div class="name">业务运行管理平台</div> -->
        </div>
        <div class="middle">
        
            <ul>
            <form id="loginForm" action="${ctx }/user/login" method="post" target="login_iframe">
                <li class="input_wrap">
                    <input class="user validate[required]" type="text" name="username" id="username" placeholder="enter account">
                </li>
                <li class="input_wrap">
                    <input class="pwd validate[required]" type="password" name="password" id="password" placeholder="enter password">
                </li>
            </form>    
                <li class="input_wrap " style=" margin-top: 17px;">
                    <button type="button" class="btn_ov login" onclick="login()">login</button>
                </li>
            </ul>
            <p style="padding-left: 6%; color:#8c8c8c; margin-top: 10px;"><i style="color: red">*</i>&nbsp;tips: The system you are currently logged in is the Business Operation Management System.</p>
		
		<iframe style="display:none;" id="login_iframe" name="login_iframe"></iframe>		
            <i class="triangle"></i>
            <p class="corp_name">Malaysia company</p>
        </div>
    </div>
</body>
</html>
