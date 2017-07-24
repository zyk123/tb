<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
    <script src="${ctx}/js/prize.management.js"></script>
</head>
<body style="background-color: inherit;" class="table_body">
<div class="table_search">
    	<form id="search_form">
	        <ul>
	            <li>Prize Name <input name="bean.prizename" class="num" type="text"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	        </ul>
		</form>
</div> 
 <div id="add_prize" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add prize  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> Prize name</label><input name="bean.prizename" type="text" data-validation-engine="validate[required,maxSize[50]]"></li>
		                <li><label for=""> prize describe</label><input name="bean.prizedesc" type="text" data-validation-engine="validate[required]"></li>
		                <li><label for=""> Prize price</label><input name="bean.prizeprice" type="number" data-validation-engine="validate[required],minSize[0],maxSize[9]"></li>
		                <li><label for=""> Prize totalNum</label><input name="bean.prizetotalnum" type="number" style="width:200px;" data-validation-engine="validate[required,max[99999]]"></li>
		                <li><label for=""> Prize type</label><input name="bean.prizetype" type="number" data-validation-engine="validate[required,min[0],max[2]]"></li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<div id="modify_prize" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify prize  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> Prize name</label><input name="bean.prizename" id="prizenameM" type="text" data-validation-engine="validate[required,maxSize[50]]"></li>
		                <li><label for=""> prize describe</label><input name="bean.prizedesc" id="prizedescM" type="text" data-validation-engine="validate[required]"></li>
		                <li><label for=""> Prize price</label><input name="bean.prizeprice" id="prizepriceM" type="number" data-validation-engine="validate[required],minSize[0],maxSize[9]"></li>
		                <li><label for=""> Prize totalNum</label><input name="bean.prizetotalnum" id="prizetotalnumM" type="number" style="width:200px;" data-validation-engine="validate[required,max[99999]]"></li>
		                <li><label for=""> Prize type</label><input name="bean.prizetype" id="prizetypeM" type="number" data-validation-engine="validate[required,min[0],max[2]]"></li>
		            </ul>		            
	            	<input id="curr_id" name="bean.prizeid" type="hidden">
	            </form>
	            <button id ="modify_btn" type="button" class="btn_ov apply">Modify</button>
	        </div>	
	    </div>
	</div>
<table class="table bor">
	    <tr class="table_title">
	        <td style="width:15%;"><input type="checkbox" id="checkall"><span style="margin-left:15px;">Prize name</span></td>
	        <td style="width:30%;">Prize Describe</td>
	        <td>Prize Price</td>
	        <td>Prize Total Num</td>
	        <td>Prize Residu Num</td>
	        <td>Modify Date</td>
	        <td>Operate</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="prize">
			    	<tr class="table_content">
				        <td style="width:15%;"><input type="checkbox"> <span style="margin-left:15px;">${prize.bean.prizename}</span> </td>
				        <td style="width:30%;">${prize.bean.prizedesc}</td>
				        <td>${prize.bean.prizeprice}</td>
				        <td>${prize.bean.prizetotalnum}</td>
<!-- 				        <td>${prize.bean.prizerestnum}</td> -->
				        <td><c:choose><c:when test="${empty prize.bean.prizerestnum}">0</c:when><c:otherwise>${prize.bean.prizerestnum}</c:otherwise></c:choose></td>
				        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${prize.bean.modifydate}" /></td>
				        <td>
				        	<input type="hidden" value="${prize.bean.prizeid}">
				           <span class="sbtn btn_ov Modify">Modify</span>
				           <c:choose>
				           <c:when test="${prize.bean.showdelete eq 1}">
				            <span class="sbtn btn_ov Delete">delete</span>
				            </c:when>
	    	                <c:otherwise>
	    	                </c:otherwise>
	    	                </c:choose>
				        </td>
				    </tr>
			    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="7">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>	    
	</table>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>
