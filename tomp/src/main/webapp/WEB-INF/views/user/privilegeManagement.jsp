<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
 	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Title</title>
	    <%@ include file="../../include.jsp" %>
	    <link rel="stylesheet" href="${ctx}/css/base.css">
	    <script src="${ctx}/js/base.js"></script>
	    <script src="${ctx}/js/privilege.management.js"></script>
	</head>
<body style="background-color: inherit;">
    <div class="table_search">
    	<form id="search_form">
	        <ul>
	            <li>Privilege No<input name="privilegeno" class="num" type="text" data-validation-engine="validate[funcCall[validatePrivilegeNo]]"></li>
	            <li>Privilege Code<input name="privilegecode" class="num" type="text" data-validation-engine="validate[funcCall[validatePrivilegeCode]]"></li>
	            <li>Privilege Name <input name="privilegename" class="num" type="text" data-validation-engine="validate[maxSize[50]]"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
	        </ul>
		</form>
    </div>    	
    <div id="add_privilege" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add Privilege  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">	
		            	<li>
		            		<label>Privilege Mark</label>
		            		<select name="privilegecode" id="ap_mark">
								<option value="VIEW">VIEW</option>
								<option value="SEARCH">SEARCH</option>
								<option value="ADD">ADD</option>
								<option value="DETAIL">DETAIL</option>
								<option value="MODIFY">MODIFY</option>
								<option value="DELETE">DELETE</option>
								<option value="EXPORT">EXPORT</option>
								<option value="IMPORT">IMPORT</option>
							</select>
		            	</li>	                
		                <li><label for=""> Privilege Name</label><input name="privilegename" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<div id="modify_privilege" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify Privilege  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">	
		            	<li>
		            		<label>Privilege Mark</label>
		            		<select name="privilegecode" id="mp_mark">
								<option value="VIEW">VIEW</option>
								<option value="SEARCH">SEARCH</option>
								<option value="ADD">ADD</option>
								<option value="DETAIL">DETAIL</option>
								<option value="MODIFY">MODIFY</option>
								<option value="DELETE">DELETE</option>
								<option value="EXPORT">EXPORT</option>
								<option value="IMPORT">IMPORT</option>
							</select>
		            	</li>	  	                
		                <li><label for=""> Privilege Name</label><input id="privilegename" name="privilegename" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
		            </ul>		            
	            	<input id="curr_id" name="privilegeid" type="hidden">
	            </form>
	            <button id ="modify_btn" type="button" class="btn_ov apply">Modify</button>
	        </div>	
	    </div>
	</div>
	<table class="table bor">
	    <tr class="table_title">
	        <td style="width: 18%;"><input type="checkbox" id="checkall">Privilege No</td>
	        <td>Privilege Code</td>
	        <td>Privilege Name</td>
	        <td>Create Date</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="privilege">
			    	<tr class="table_content">
				        <td><input type="checkbox"> <span>${privilege.privilegeno}</span> </td>
				        <td><span>${privilege.privilegecode}</span></td>
				        <td>${privilege.privilegename}</td>
				        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${privilege.createdate}" /></td>
				        <td>
				        	<input type="hidden" value="${privilege.privilegeid}">
				           <span class="sbtn btn_ov Modify">Modify</span>
				           <input type="hidden" value="${privilege.privilegename}">
				        </td>
				    </tr>
			    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="5">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>	    
	</table>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>
