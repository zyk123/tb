<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
 	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Title</title>
	    <%@ include file="../../include.jsp" %>
	    <link rel="stylesheet" href="${ctx}/css/base.css">
	    <script src="${ctx}/js/base.js"></script>
	</head>
<body style="background-color: inherit;">
    <div class="table_search">
    	<form id="search_form">
	        <ul>	            
	            <li>Device Name <input name="name" class="num" type="text" value=${param.name}></li>
	            <li>
		            Device Type 
		            <select name="type" style="width:140px;">
		            	<option value="">please choose</option>
		            	<option value="1" <c:if test="${param.type eq 1}">selected="selected"</c:if>>browser</option>
		            	<option value="2" <c:if test="${param.type eq 2}">selected="selected"</c:if>>mobile phone</option>
		            </select>
	            </li>	            
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">Research</button></li>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
	        </ul>
		</form>
    </div>    	
    <div id="add_device" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add Device  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> Device Name</label><input id="name" name="name" type="text" data-validation-engine="validate[required,maxSize[25],funcCall[checkUnique]]"></li>
		            	<li>
		            		<label for=""> Device Type</label>
		            		<select id="type">
				            	<option value="1">browser</option>
				            	<option value="2">mobile phone</option>
				            </select>
		            	</li>
		            </ul>
	            </form>
	            <button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	        </div>	
	    </div>
	</div>
	<form id="t_form">
	<table class="table bor">
	    <tr class="table_title">
	        <td><input type="checkbox" id="checkall">Device Name</td>
	        <td>Device Type</td>
	        <td>Create Date</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	  
	    		  		
				    <c:forEach items="${list}" var="bDevice">
				    	<tr class="table_content">
					        <td><input type="checkbox"> <span style="margin-left:15px;">${bDevice.name}</span> </td>
					        <td>
					        	<c:choose>
					        		<c:when test="${bDevice.type eq 1 }">browser</c:when>
					        		<c:when test="${bDevice.type eq 2 }">mobile phone</c:when>
					        	</c:choose>
					        </td>
					        <td><fmt:formatDate value='${bDevice.createdate}' pattern="yyyy-MM-dd"/></td>
					        <td>
					        	<input type="hidden" value="${bDevice.id}">
					           <span class="d_mod sbtn btn_ov Modify">Modify</span>
					        </td>
					    </tr>
				    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="4">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>
	</table>
	</form>
	<jsp:include page="../common/footer.jsp"/>
</body>
<script>
	$(function(){
    	$('#add_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
		$('#add').on('click',function(){
			$('#add_device').slideDown();
			$('#add_form').get(0).reset();
		});
		var flag = true;
		$('#add_btn').on('click',function(){
			if($('#add_form').validationEngine('validate') && flag){
				flag = false;
				$.ajax({
				url:'${ctx}/whiteListDevice/addDevice',
				type:'post',
				data:{
					name:$.trim($('#name').val()),
					type:$.trim($('#type').val())
				},
				success:function(map){
					if(map.retCode==1){
						window.location.reload();
					}else{
						alert("Add Unsuccessfully!");
					}
				},
				error:function(){
					alert("server error!");
				},
				complete:function(){
					flag = true;
				}
			});
			}			
		});
	});
	$('#del_btn').on('click',function(){
		if($('.table_content input:checked').length == 0){
        		alert('Please select at least one record!');
    			return;
        	}
       	if(confirm("Are you sure you want to delete the selected data?")){
   			var array = [];
   			$('.table_content input:checked').each(function(){
   				array.push($(this).parents('.table_content').find('input[type="hidden"]').val());
   			});
   			$.ajax({
   				url:ctx + '/whiteListDevice/delDevice',
   				type:'post',
   				data:{array:array},
   				traditional:true,
   				dataType:'json',
   				success:function(data){						
   					if(1 == data.retCode){
   						nextPage(cPage);
   					}else{
   						alert('Delete user failure!');
   					}
   				},
   				error:function(){
   					alert('server error!');
   				}
   			});	
   		}				
	});
	var value;
	$('.d_mod').on('click',function(){
		if('Modify' == $(this).text()){
			var span = $(this).parents('.table_content').children().first().find('span');
			value = span.text();
			span.replaceWith('<input type="text" data-validation-engine="validate[required,maxSize[25],funcCall[checkUnique]]" style="margin-left:15px;" value=' + value + '>');
			$(this).text('Save');
		}else{
			$('#t_form').validationEngine('attach', {promptPosition : "topRight"});			
			var obj = $(this);							
			var input = obj.parents('.table_content').children().first().find('input[type="text"]');
			var val = input.val();
			if(val == value||$('#t_form').validationEngine('validate')){	
				$.ajax({
				url:'${ctx}/whiteListDevice/updateDevice',
				type:'post',
				data:{
					id:obj.prev().val(),
					name:$.trim(val)
				},
				success:function(map){
					if(map.retCode==1){	
						input.replaceWith('<span style="margin-left:15px;" >'+val+'</span>');
						obj.text('Modify');
					}else{
						alert("Modify Unsuccessfully!");
					}
				},
				error:function(){
					alert("server error!");
				}
			});
			}			
		}		
	});
	$('#search_btn').on('click',function(){
   		nextPage(1);
   	});
   	function checkUnique(field){
   		var i,j=0;
   		$.ajax({
   			url:'${ctx}/whiteListDevice/check',
   			type:'post',
   			async:false,
   			data:{
   				name:field.val()
   			},
   			success:function(map){
   				if(map.retCode==0){
   					i = 1;
   				}
   			}
   		});
   		$.ajax({
   			url:'${ctx}/blackListDevice/check',
   			type:'post',
   			async:false,
   			data:{
   				name:field.val()
   			},
   			success:function(map){
   				if(map.retCode==0){
   					j = 1;
   				}
   			}
   		});
   		if(i == 1){
   			return "The name has existed in the whitelist device";
   		}
   		if(j == 1){
   			return "The name has existed in the blacklist device";
   		}
   	}
	 //显示下一页
	function nextPage(currentPage){
		window.location.href="${ctx}/whiteListDevice/showDeviceList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
</script>
</html>
