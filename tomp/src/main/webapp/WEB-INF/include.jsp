<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link rel="stylesheet" href="<%=path %>/third/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path %>/third/jQuery-Validation-Engine/css/validationEngine.jquery.css">
<link href="<%=path%>/js/select2/css/select2.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script src="<%=path%>/third/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="<%=path %>/third/jQuery-Validation-Engine/js/languages/jquery.validationEngine-en.js"></script>
<script src="<%=path %>/third/jQuery-Validation-Engine/js/jquery.validationEngine.min.js"></script>
<!-- <script src="<%=path%>/js/jquery.fileupload.js"></script> -->
<script src="<%=path%>/js/jquery.from.js"></script>
<script src="<%=path%>/js/select2/js/select2.full.min.js"></script>
<script src="<%=path%>/js/select2/js/i18n/zh-CN.js"></script>
<script type="text/javascript" src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var ctx = "${ctx}";
	var mobileLength = "${userModelSession.mobileLength}";
	var cPage = ${page.currentPage};
</script>
<style>
label.error{
    color:  red;
}
</style>
