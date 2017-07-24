<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
 	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Title</title>
	    <%@ include file="../../include.jsp" %>
	    <link rel="stylesheet" href="${ctx}/css/base.css">
	    <link rel="stylesheet" href="${ctx}/js/dist/themes/default/style.min.css" />
	    <script src="${ctx}/js/base.js"></script>
	    <script src="${ctx}/js/dist/jstree.min.js"></script>
	    <script src="${ctx}/js/user.management.js"></script>
	</head>
<body style="background-color: inherit;">
    <div class="table_search">
    	<form id="search_form">
	        <ul>
	            <li>User No<input name="userno" class="num" type="text" data-validation-engine="validate[funcCall[validateUserNo]]"></li>
	            <li>User Name <input name="username" class="num" type="text" data-validation-engine="validate[maxSize[50]]"></li>
	            <li>Mobile Phone <input name="mobilephone" class="phone" type="text" data-validation-engine="validate[custom[posInteger]]"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
	        </ul>
		</form>
    </div>    	
    <div id="add_user" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add User  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> User Name</label><input name="username" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		                <li><label for=""> Password</label><input id="password" name="password" type="password" data-validation-engine="validate[required]"></li>
		                <li><label for=""> Confirm Password</label><input type="password" data-validation-engine="validate[required,equals[password]]" data-errormessage-pattern-mismatch="The two passwords don't match"></li>
		                <li><label for=""> Mobile Phone</label><input name="mobilephone" type="text" data-validation-engine="validate[required,funcCall[validateMobileNo]]"></li>
		                <li><label for=""> Email</label><input name="email" type="text" data-validation-engine="validate[required,custom[email]]"></li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<div id="modify_user" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify User  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> User Name</label><input id="username" name="username" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		                <li><label for=""> Mobile Phone</label><input id="mobilephone" name="mobilephone" type="text" data-validation-engine="validate[required,funcCall[validateMobileNo]]"></li>
		                <li><label for=""> Email</label><input id="email" name="email" type="text" data-validation-engine="validate[required,custom[email]]"></li>
		            </ul>		            
	            	<input id="curr_id" name="userid" type="hidden">
	            </form>
	            <button id ="modify_btn" type="button" class="btn_ov apply">Modify</button>
	        </div>	
	    </div>
	</div>
	<div id="jstree" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Relation Role<i class="icon iclose" >X</i></div>
	        <div class="cont">	        	
    			<div id="pop"></div>
	            <button id ="jstree_btn" type="button" class="btn_ov apply">Save</button>
	        </div>	
	    </div>
	</div>
	<table class="table bor">
	    <tr class="table_title">
	        <td style="width: 18%;"><input type="checkbox" id="checkall">User No</td>
	        <td>User Name</td>
	        <td>Mobile Phone</td>
	        <td>Email</td>
	        <td>Create Date</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="user">
			    	<tr class="table_content">
				        <td><input type="checkbox"> <span style="margin-left:15px;">${user.userno}</span> </td>
				        <td>${user.username}</td>
				        <td>${user.mobilephone}</td>
				        <td>${user.email}</td>
				        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${user.createdate}" /></td>
				        <td>
				        	<input type="hidden" value="${user.userid}">
				           <span class="sbtn btn_ov Modify">Modify</span>
				           <input type="hidden" value="${user.username}">
				            <span class="sbtn btn_ov Relation">Relation</span>
				        </td>
				    </tr>
			    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="6">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>	    
	</table>
	<jsp:include page="../common/footer.jsp"/>
</body>
<script>		
	function validateMobileNo(field){
		if("" != field.val() && !/^\d{${userModelSession.mobileLength}}$/.test(field.val())){
			return "MobileNo must be " + mobileLength + "positive integers";
		}
	}
</script>
</html>
