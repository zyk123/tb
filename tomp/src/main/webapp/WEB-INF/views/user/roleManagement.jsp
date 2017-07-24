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
	    <script src="${ctx}/js/role.management.js"></script>
	</head>
<body style="background-color: inherit;">
    <div class="table_search">
    	<form id="search_form">
	        <ul>
	            <li>Role No<input name="roleno" class="num" type="text" data-validation-engine="validate[funcCall[validateRoleNo]]"></li>
	            <li>Role Name <input name="rolename" class="num" type="text" data-validation-engine="validate[maxSize[50]]"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
	        </ul>
		</form>
    </div>    	
    <div id="add_role" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add Role  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> Role Name</label><input name="rolename" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<div id="modify_role" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify Role  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> Role Name</label><input id="rolename" name="rolename" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		            </ul>		            
	            	<input id="curr_id" name="roleid" type="hidden">
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
	        <td style="width: 18%;"><input type="checkbox" id="checkall">Role No</td>
	        <td>Role Name</td>
	        <td>Create Date</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="role">
			    	<tr class="table_content">
				        <td><input type="checkbox"> <span>${role.roleno}</span> </td>
				        <td>${role.rolename}</td>
				        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${role.createdate}" /></td>
				        <td>
				        	<input type="hidden" value="${role.roleid}">
				           <span class="sbtn btn_ov Modify">Modify</span>
				           <input type="hidden" value="${role.rolename}">
				            <span class="sbtn btn_ov Relation">Relation</span>
				        </td>
				    </tr>
			    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="4">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>	    
	</table>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>
