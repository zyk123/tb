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
	    <script src="${ctx}/js/menu.management.js"></script>
	</head>
<body style="background-color: inherit;">
    <div class="table_search">
    	<form id="search_form">
	        <ul>
	            <li>Menu No<input name="menuno" class="num" type="text" data-validation-engine="validate[funcCall[validateMenuNo]]"></li>
	            <li>Menu Name <input name="menuname" class="num" type="text" data-validation-engine="validate[maxSize[50]]"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
	        </ul>
		</form>
    </div>    	
    <div id="add_menu" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add Menu  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">	
		            	<li>
			            	<label for=""> Menu Level</label>
			            	<select id="menulevel" name="menulevel">
			            		<option value="1" selected="true">一级菜单</option>
			            		<option value="2">二级菜单</option>
			            	</select>
		            	</li>	                
		                <li><label for=""> Menu Name</label><input name="menuname" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>		                
		                <li><label for=""> Order No</label><input name="orderno" type="text" data-validation-engine="validate[required]"></li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<div id="modify_menu" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify Menu  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">
		            	<li>
			            	<label for=""> Menu Level</label>
			            	<select id="menulevel1" name="menulevel">
			            		<option value="1">一级菜单</option>
			            		<option value="2">二级菜单</option>
			            	</select>
		            	</li>
		                <li id="m_name"><label for=""> Menu Name</label><input id="menuname" name="menuname" type="text" data-validation-engine="validate[required,maxSize[50],funcCall[checkUnique]]"></li>
						<li><label for=""> Order No</label><input id="orderno" name="orderno" type="text" data-validation-engine="validate[required]"></li>
		            </ul>		            
	            	<input id="curr_id" name="menuid" type="hidden">
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
	        <td style="width: 18%;"><input type="checkbox" id="checkall">Menu No</td>
	        <td>Menu Name</td>
	        <td>Menu Url</td>
	        <td>Create Date</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="menu">
			    	<tr class="table_content">
				        <td><input type="checkbox"> <span>${menu.menuno}</span> </td>
				        <td>${menu.menuname}</td>
				        <td>${menu.menuindex}</td>
				        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${menu.createdate}" /></td>
				        <td>
				        	<input type="hidden" value="${menu.menuid}">
							<input type="hidden" value="${menu.fid}">
				           <span class="sbtn btn_ov Modify">Modify</span>
				           <input type="hidden" value="${menu.menulevel}">
				           <input type="hidden" value="${menu.menuname}">
				           <c:if test="${menu.menulevel eq 2}">
				           	<span class="sbtn btn_ov Relation">Relation</span>
				           </c:if>
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
