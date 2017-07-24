<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>500</title>
    <link rel="stylesheet" href="<%=basePath %>static/css/base.css">
    <script src="<%=basePath %>static/js/jquery.1.8.3.min.js"></script>
	<style type="text/css">
	body {
		background-color: #F2EFF8;
	}
	</style>
  </head>
  
  <body>
	<header class="usage tb_header fixed-top">
		<span class="s_gap"></span>	    
	    <h2>500</h2>
	    <i class="close_i"> </i>
	</header>
	<img alt="" src="../static/images/imgs/500c.jpg" style="width:100%; height:100%">
  </body>
</html>
