<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test1result.jsp' starting page</title>
    
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
    <%=request.getParameter("storeId")%></br>
    <%=request.getParameter("orderId")%></br>
    <%=request.getParameter("transDate")%></br>
    <%=request.getParameter("totalAmount")%></br>
    <%=request.getParameter("msisdn")%></br>
    <%=request.getParameter("balance")%></br>
    <%=request.getParameter("suspendDate")%></br>
    <%=request.getParameter("returnCode")%></br>
    <%=request.getParameter("reasonCode")%></br>
    <%=request.getParameter("reasonDesc")%></br>
    <%=request.getParameter("referId")%></br>
    <%=request.getParameter("signature")%></br>
    <%=request.getParameter("paymentMethod")%></br>
    <%=request.getParameter("cardPaddedNum")%></br>
    <%=request.getParameter("authCode")%></br>
    <%=request.getParameter("token")%></br>
    <%=request.getParameter("payer")%></br>
    <%=request.getParameter("transactionId")%></br>
    <%=request.getParameter("accountNum")%></br>
  </body>
</html>
