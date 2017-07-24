<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>

<body>

<%
String storeId  = request.getParameter("storeId");
String orderId  = request.getParameter("orderId");
String transDate  = request.getParameter("transDate");
String totalAmount  = request.getParameter("totalAmount");
String msisdn  = request.getParameter("msisdn");

String balance  = request.getParameter("balance");
String suspendDate  = request.getParameter("suspendDate");
String returnCode  = request.getParameter("returnCode");
String reasonCode  = request.getParameter("reasonCode");
String reasonDesc  = request.getParameter("reasonDesc");
String referId  = request.getParameter("referId");
String signature  = request.getParameter("signature");

String paymentMethod  = request.getParameter("paymentMethod");
String cardPaddedNum  = request.getParameter("cardPaddedNum");
String authCode  = request.getParameter("authCode");
String token  = request.getParameter("token");
String transactionId  = request.getParameter("transactionId");
String accountNum  = request.getParameter("accountNum");
%>

<Br/>
<%out.print("storeId=" + storeId + "/");%><Br/>
<%out.print("orderId=" + orderId + "/");%><Br/>
<%out.print("transDate=" + transDate + "/");%><Br/>
<%out.print("totalAmount=" + totalAmount + "/");%><Br/>
<%out.print("msisdn=" + msisdn + "/");%><Br/>

<%out.print("balance=" + balance + "/");%><Br/>
<%out.print("suspendDate=" + suspendDate + "/");%><Br/>
<%out.print("returnCode=" + returnCode + "/");%><Br/>
<%out.print("reasonCode=" + reasonCode + "/");%><Br/>
<%out.print("reasonDesc=" + reasonDesc + "/");%><Br/>
<%out.print("referId=" + referId + "/");%><Br/>
<%out.print("signature=" + signature + "/");%><Br/>

<%out.print("paymentMethod=" + paymentMethod + "/");%><Br/>
<%out.print("cardPaddedNum=" + cardPaddedNum + "/");%><Br/>
<%out.print("authCode=" + authCode + "/");%><Br/>
<%out.print("token=" + token + "/");%><Br/>
<%out.print("transactionId=" + transactionId + "/");%><Br/>
<%out.print("accountNum=" + accountNum + "/");%><Br/>

<Br/>
</body>
</html>