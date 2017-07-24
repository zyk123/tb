<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'reloadtest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form name="Merchant" method="post"
action="https://onlinepayment.celcom.com.my/Payment-Testing/PrepaidPayment">
<input type="text" name="orderId" value="20170105113237"/><br>
<input type="text" name="totalAmount" value="10.00"/><br>
<input type="text" name="storeId" value="xpax"/><br>
<input type="text" name="responseUrl"
value="http://192.168.1.30:8080/tbar/result.jsp"><br>
<input type="text" name="reconFilename" value="20170105">
<input type="text" name="transDate" value="20170105113237+0800">
<input type="text" name="signature" value="l5c/kRWaw0KchgDD+V1Ppa/ZEB8="/><br>
<input type="text" name="msisdn" value="0133933429" > <br>
<input type="submit" name="submit" value="submit"/>
</form>
  </body>
</html>
