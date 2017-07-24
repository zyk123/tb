<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
    <script type="text/javascript">
		$(function($) {
			list();
		});
		
		function clickEventList(){
            $("#pageIndex").val(1);
           	list();			
		}
			
		
		function list(){
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/reload/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var html = '';
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td>'+formatDate(item.datadate)+'</td>';			
						html += '<td>'+item.totnum+'</td>';			
						html += '<td>'+item.consnum+'</td>';			
						html += '<td>'+item.paynum+'</td>';			
						html += '</tr>';			
					});
					$(".table_content").remove();
					if(html == ''){
						html += '<tr class="table_content"><td colspan="4">Query list is empty!</td></tr>';
						$(".page_nav").hide();
					}else{
						$(".page_nav").show();
					}					
					$("#listTable").append(html);
					
					pagination(page);
				}
			});			
		} 
		
		
		
		//改变显示行数
        function changePageRows(obj) {
            $("#pageRowNum").val(obj.value);
//              会导致index改变
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
						var totalPageNum = page.totalPageNum;
						var begin = page.begin;
						var end = page.end;
						var pageSelect = '';
						$.each(pageRows,function(i,item){
							var isSelectClass = '';
							if(item==page.rowsPeerPage){
								isSelectClass = 'selected=selected';
							}
							pageSelect += '<option value="'+item+'" '+isSelectClass+'>'+item+'</option>';
						})
						$("select[name='rowsPeerPage']").empty().append(pageSelect);
						$("#totalRowNum").empty().append(totalrows);
						$("#totalPageNum").empty().append(totalPageNum);
						$("#pageRowNum").val(page.rowsPeerPage);
						$("#pageIndex").val(page.currentPageNum);
				       	var htmlPag = '';
				       	if(page.currentPageNum<=page.displayIndexCount){
				       	
				       		htmlPag += '<li class=disabled><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>';
				       	}
				       	if(page.currentPageNum>page.displayIndexCount){
				       		
      						htmlPag += '<li><a href="#" aria-label="Previous" onclick="changePageNum(this)" value="'+(begin-1)+'"><span aria-hidden="true">«</span></a></li>'		;		       	
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
				       	$(".pagination").empty().append(htmlPag); 
        } 
        
        
		    var formatDate = function (data) {
		    	var date = new Date(data);  
		        var y = date.getFullYear();  
		        var m = date.getMonth() + 1;  
		        m = m < 10 ? '0' + m : m;  
		        var d = date.getDate();  
		        d = d < 10 ? ('0' + d) : d;  
		        return y + '-' + m + '-' + d;  
		    };          
        
    </script>
</head>
<body style="background-color: inherit;" class="table_body">
<input type="text" id="htmlIdentifier" value="add" style="display: none;" />
    <div class="table_search">
        <ul>
        	<form id="conditionForm">
            <li id="startDateInp">From: <input class="num Wdate" type="text" id="startTime" name="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})"></li>
           <li id="endDateInp">to:<input class="num Wdate" type="text" id="endTime" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',readOnly:true,lang:'en'})"></li> 
	        <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	        <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>	              
            </form>
            <li><button class="btn_ov mid_btn search" onclick="clickEventList()">Research</button></li>
        </ul>
    </div>
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td>datadate</td>
         <td>totnum</td>
         <td>consnum</td>
         <td>paynum(MB)</td>
     </tr>
 </table>
<div class="page_nav">
    <div class="fl">
        <select name="rowsPeerPage" onchange="changePageRows(this)">

        </select>
        pcs for each page&nbsp;&nbsp;&nbsp;total <span id="totalRowNum"></span> pcs&nbsp;&nbsp;&nbsp;total <span id="totalPageNum"></span> pages
    </div>
<!--     <div class="checkall_wrap"> -->
<!--         <input class="checkall" id="checkall" type="checkbox"> <span>全选</span> -->
<!--     </div> -->
    <nav>
        <ul class="pagination">
        </ul>
    </nav>
</div>

</body>
</html>
