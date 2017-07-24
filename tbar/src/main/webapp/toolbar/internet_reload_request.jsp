<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.flash.common.util.SHA1HashSignature"%>


<html>
<head>
<meta charset="gb2312">
<title></title>
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<link href="hstyles/style.css" rel="stylesheet" type="text/css">
</head>

<body>

<%

String orderId = "20170206154612124";

String amount = "10.00";
String amount1 = "1000";
String storeId = "xpax";
String password = "xpax";

String responseUrl = "http://58.27.120.214:8080/tbar/toolbar/internet_reload_response.jsp";
String reconFilename = "20170206";
String iframes = "Y";
String transDate = "20170206154612+0800";

SHA1HashSignature sha1 = new SHA1HashSignature();
String signature = sha1.createSignature(storeId, password, orderId, amount1);
System.out.print("signature = " + signature + "\n");

String msisdn = "0136999394";

%>


	<div id="view" align="center">
		<table>
					<form id="merchant" name="merchant" method="post" action="https://onlinepayment.celcom.com.my/Payment-Testing/MIPayment">
						<input type="hidden" name="orderId" id="orderId" value=<%=orderId%> /><br>
						<input type="hidden" name="totalAmount" id="totalAmount" value=<%=amount%> /><br> 
						<input type="hidden" name="storeId" id="storeId" value=<%=storeId%> /><br> 
						<input type="hidden" name="responseUrl" id="responseUrl" value=<%=responseUrl%> /><br>
						<input type="hidden" name="reconFilename" id="reconFilename" value=<%=reconFilename%> /><br>
						<input type="hidden" name="iframes" id="iframes" value=<%=iframes%> /><br>
						<input type="hidden" name="transDate" id="transDate" value=<%=transDate%> /><br>
						<input type="hidden" name="signature" id="signature" value=<%=signature%> /><br>
						<input type="hidden" name="msisdn" id="msisdn" value=<%=msisdn%> /><br>
							
						<input type="submit" name="submit" value="submit"/>
					</form>					
					
		</table>
	</div>

</body>

</html>