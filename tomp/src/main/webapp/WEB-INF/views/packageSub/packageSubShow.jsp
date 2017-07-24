<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Title</title>
	<link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
</head>


<script type="text/javascript">
	$(function($) {
		 $('#packageSubForm').validationEngine('attach',{
				promptPosition: 'bottomRight'
		});			
	});

	//请求查询操作
	function list(){
		
		var flag = $('#packageSubForm').validationEngine('validate');
		if(!flag){
			return;
		}
	
		$.ajax({
			type: "POST",
			cache:false,
			url: "${ctx}/packageSub/list",
			data: $("#packageSubForm").serialize(),
			dataType: "json",
			success: function(data) {
			
				var result = data.list;
				var page = data.page;
				var html = '';
				
				//移除分页
				$("#myNav").empty();
				//移除旧数据表格数据
				$(".table_content").remove();
				
				//查询结果总记录数 大于 0
				if(page.totalRowsCount > 0){
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td>'+item.ordernumber+'</td>';
						html += '<td>'+item.operatororderno+'</td>';
						html += '<td title='+item.fgname+'>'+formatStr(item.fgname,0,20)+'</td>';			
						html += '<td>'+item.amount+'</td>';		
						html += '<td>'+item.currency+'</td>';		
						html += '<td>'+item.status+'</td>';	
						html += '<td>'+formatDate(item.subdate)+'</td>';	
						html += '<td>'+item.mobileno+'</td>';			
						html += '</tr>';	
					});
					
					//有数据时才展示分页
					pagination(page);
					
				}else{
					//查询结果总记录数 等于 0
					html += '<tr class="table_content">';
					html += '<td colspan="8">Query list is empty!</td>';
					html += '</tr>';	
				}
				
				$("#listTable").append(html);
				
			},
			error:function(a,b,c){
				alert('System error!');
			}
		});			
	} 
	
	
	//查询
	function query(){
		//每次查询操作恢复默认分页第一页
		$("#pageIndex").val(1);
		
		list(); 
	}

	//改变显示行数
    function changePageRows(obj) {
        $("#pageRowNum").val(obj.value);
        $("#pageIndex").val(1);
       	list(); 
    }

	//改变页数
    function changePageNum(obj) {
       $("#pageIndex").val($(obj).attr("value"));    
       list();    
	}
	
    //包含在json中的数据分页
    function pagination(page){
    	var pageRows = page.pageRowsList;
		var totalrows = page.totalRowsCount;
		var totalPags = page.totalPageNum;
		var begin = page.begin;
		var end = page.end; 
		var pageSelect = '';
		$("#pageRowNum").val(page.rowsPeerPage);
		//$("#pageIndex").val(page.currentPageNum);
       	var htmlPag = '';

       	htmlPag += '<div class="page_nav"><div class="fl"><select id="peerPageNum" onchange="changePageRows(this)"><option value="10">10</option><option value="20">20</option><option value="50">50</option></select>pcs for each page'
       					+'&nbsp;&nbsp;&nbsp;total&nbsp;<span>'+totalrows+'</span>&nbsp;pcs&nbsp;&nbsp;&nbsp;total&nbsp;<span>'+totalPags+'</span>&nbsp;pages</div><nav><ul class="pagination"> ';
       	
       	if(page.currentPageNum<=page.displayIndexCount){
       		htmlPag += '<li class=disabled><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>';
       	}
       	if(page.currentPageNum>page.displayIndexCount){
       		htmlPag += '<li><a href="#" aria-label="Previous" onclick="changePageNum(this)" value="'+(begin-1)+'"><span aria-hidden="true">«</span></a></li>';		       	
       	}
       	for(var i=begin;i<=end;i++){
       		if(i==page.currentPageNum){
       			htmlPag += '<li class="active"><a href="#">'+i+'</a></li>';
       		}
       		if(i!=page.currentPageNum &&  i<=page.totalPageNum){
       			htmlPag += '<li><a href="#" onclick="changePageNum(this)" value="'+i+'">'+i+'</a></li>';
       		}
       	}	
       	if(page.totalPageNum<=end){
       		htmlPag += '<li class=disabled><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>';
       	}
       	if(page.totalPageNum-end > 0){
       		htmlPag += '<li><a href="#" aria-label="Next" onclick="changePageNum(this)" value="'+(end+1)+'"><span aria-hidden="true">»</span></a></li>';
       	}
       	htmlPag += ' </ul></nav></div>';
       	
       	$("#myNav").empty().append(htmlPag);
       	
       	$("#peerPageNum").val(page.rowsPeerPage);
    }

	//格式化日期
    function formatDate(data) {
    	var date = new Date(data);  
    	
        var y = date.getFullYear();  
        
        var m = date.getMonth() + 1;  
        m = m < 10 ? '0' + m : m;  
        
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        
        var h = date.getHours();
        h = h < 10 ? '0' + h : h;
        
        var mi = date.getMinutes();
        mi = mi < 10 ? '0' + mi : mi;  
        
        var s = date.getSeconds();
        s = s < 10 ? '0' + s : s;
        
        return y + '-' + m + '-' + d + ' ' + h + ':' + mi + ':' + s;  
    }

	//表格数据截取字符串格式输出
	function formatStr(str,start,end){
		if(str.length > end){
			return str.substring(start,end)+"...";
		}
		return str;
	}
</script>

<body style="background-color: inherit;">

	<input type="text" id="htmlIdentifier" value="packageSubNumInterval" style="display: none;" />
	
	
	<form id="packageSubForm">
	
		<input type="hidden" id="pageIndex" name="pageIndex"  value="1"/>
		<input type="hidden" id="pageRowNum" name="pageRowNum"  value="10"/>
		
		<div class="table_search">
			<ul>
				<li>phone number<input class="phone" type="text" id="mobileNo" name="mobileNo" data-validation-engine=="validate[custom[posInteger],maxSize[12]]"></li>
				<li>package name<input class="" type="text" id="fGName" name="fGName"></li>
				<li>subscription status
					<select name="status" id="status">
	                    <option value=""></option>
	                    <option value="0">charging</option>
	                    <option value="1">charging succeeded</option>
	                    <option value="2">charging failed</option>
	                </select>
				</li>
				<li>external order no<input class="phone" type="text" id="operatorOrderNo" name="operatorOrderNo" data-validation-engine=="validate[custom[posInteger]]"></li>
				<li>from <input class="num Wdate" type="text" id="startDate" name="startDate" 
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})"></li>
				<li>to <input class="num Wdate" type="text" id="endDate" name="endDate" 
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d',readOnly:true,lang:'en'})"></li>
				<li><button class="btn_ov mid_btn search" type="button" id="queryBtn" onclick="query()">query</button></li>
			</ul>
		</div>
		
	</form>
	
	<table class="table bor" id="listTable">
		<tr class="table_title">
			<td>internal system order no</td>
			<td>external order no</td>
			<td>package name</td>
			<td>amount</td>
			<td>currency</td>
			<td>subscription status</td>
			<td>subscription date</td>
			<td>phone number</td>
		</tr>
		<tr class="table_content">
			<td colspan="8">Please do the query operation!</td>
		</tr>
	</table>

	<div id="myNav"></div>
</body>

</html>
