<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		list();
    	
    		$("#period").on("change",function(){
				$("#startDateInp").empty();
				$("#endDateInp").empty();
    			var ss = $(this).val();
    			if(ss == "0"){
    				$("#startDateInp").append('From: <input class="num Wdate" type="text" id="startTime" name="startTime" onfocus="WdatePicker({maxDate:\'#F{$dp.$D(\\\'endTime\\\')||\\\'%y-%M-%d\\\'}\',readOnly:true,lang:\'en\'})">');
    				$("#endDateInp").append('to:<input class="num Wdate" type="text" id="endTime" name="endTime" onfocus="WdatePicker({minDate:\'#F{$dp.$D(\\\'startTime\\\')}\',maxDate:\'%y-%M-%d\',readOnly:true,lang:\'en\'})">');
    			}
    			if(ss == "1"){
    				$("#startDateInp").append('From: <input class="num Wdate" type="text" id="startTime" name="startTime" onfocus="WdatePicker({disabledDays:[0,2,3,4,5,6],maxDate:\'#F{$dp.$D(\\\'endTime\\\')||\\\'%y-%M-%d\\\'}\',readOnly:true,lang:\'en\'})">');
    				$("#endDateInp").append('to:<input class="num Wdate" type="text" id="endTime" name="endTime" onfocus="WdatePicker({disabledDays:[0,2,3,4,5,6],minDate:\'#F{$dp.$D(\\\'startTime\\\')}\',maxDate:\'%y-%M-%d\',readOnly:true,lang:\'en\'})">');    			
    			}
    			if(ss == "2"){
    				$("#startDateInp").append('From: <input class="num Wdate" type="text" id="startTime" name="startTime" onfocus="WdatePicker({opposite:true,disabledDates:[\'....-..-01\'],maxDate:\'#F{$dp.$D(\\\'endTime\\\')||\\\'%y-%M-%d\\\'}\',readOnly:true,lang:\'en\'})">');
    				$("#endDateInp").append('to:<input class="num Wdate" type="text" id="endTime" name="endTime" onfocus="WdatePicker({opposite:true,disabledDates:[\'....-..-01\'],minDate:\'#F{$dp.$D(\\\'startTime\\\')}\',maxDate:\'%y-%M-%d\',readOnly:true,lang:\'en\'})">');    			
    			}
    		})    	
    	});
    	
    	
    	function  pageAccessList(){
    		$("#exportStartTime").val($("#startTime").val());
    		$("#exportEndTime").val($("#endTime").val());
    	
            $("#pageIndex").val(1);
           	list();	    	
    	}
    	
    	
		function list(){
			var flag = $('#conditionForm').validationEngine('validate');
			if(!flag){
				return;
			}
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/pageAccess/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {
					$("#tableHeader").empty();
					$("#firstTableTitle").empty();
					$("#table_content .table_title:gt(1)").remove();
					var html = '';				
				if(data!=null && data.list!=null){				
					var result = data.list;
					var page = data.page;
					var type = data.typeList;
					var typeHtml = '<td rowspan="2">Time</td>';
					var headerHtml = '';
					if(result!=null && result.length>0){
						var pagenames = result[0].accessurl.split(",");
						$.each(pagenames,function(i,item){
							typeHtml += '<td colspan="2">'+item+'</td>';
							headerHtml += '<td>Times</td><td >Users</td>'; 
						})
						$("#tableHeader").append(typeHtml);
						$("#firstTableTitle").append(headerHtml);
					}
					$.each(result,function(i,item){
						html += '<tr class="table_title">';
						html += '<td>'+formatDate(item.datadate)+'</td>';
						var totnums = item.totnum.split(",");
						var consnum = item.consnum.split(",");
						if(totnums!=null && consnum!=null && totnums.length == consnum.length){
							for(var i=0;i<totnums.length;i++){
								if(totnums[i]=='empty'){
									html += '<td></td>';
								}else{
									html += '<td>'+totnums[i]+'</td>';	
								}
								if(consnum[i]=='empty'){
									html += '<td></td>';
								}else{
									html += '<td>'+consnum[i]+'</td>';	
								}
							}
						}
						html += '</tr>';			
					});
					 
					if(html == ''){
						html += '<tr class="table_title"><td colspan="6">Query list is empty!</td></tr>';
						$(".page_nav").hide();
					}else{
						$(".page_nav").show();
					}					
					$("#table_content").append(html);
					
					pagination(page);
					}else{
						html += '<tr class="table_title"><td colspan="6">Query list is empty!</td></tr>';
						$("#table_content").append(html);
						$(".page_nav").hide();						
					}
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
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search">
        <ul>
        	<form id="conditionForm">
            <li>Period:
                <select name="bean.datatype" id="period">
                    <option value="0">day</option>
                    <option value="1">week</option>
                    <option value="2">month</option>
                </select>
            </li>
            <li id="startDateInp">From: <input class="num Wdate" type="text" id="startTime" name="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})"></li>
           <li id="endDateInp">to:<input class="num Wdate" type="text" id="endTime" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',readOnly:true,lang:'en'})"></li> 
	        <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	        <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>	              
            </form>
            <li><button class="btn_ov mid_btn search" onclick="pageAccessList()">Research</button></li>
            <form action="${ctx}/pageAccess/export" method="post">
            <input type="hidden" value="" name="startTime" id="exportStartTime"/>
            <input type="hidden" value="" name="endTime" id="exportEndTime"/>
            <li class=""><button class="btn_ov mid_btn import" type="submit">Output</button></li>
            </form>
        </ul>
    </div>
 <table class="table bor" id="table_content">
     <tr class="table_title" id="tableHeader">
     </tr>
     <tr class="table_title" id="firstTableTitle">
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


<script>


</script>
</body>
</html>
