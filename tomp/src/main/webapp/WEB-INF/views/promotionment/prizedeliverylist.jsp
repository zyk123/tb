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
		 $('#conditionForm').validationEngine('attach',{
				promptPosition: 'bottomRight'
		});		
		$('#modify_form').validationEngine('attach');		
			list();
		});
		
		
		function prizeDeliveryList(){
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
				url: "${ctx}/prizedeliverylist/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var html = '';
					$.each(result,function(i,item){
						var deliveryTime = item.bean.shipdate;
						if(deliveryTime!='' && deliveryTime!=undefined){
							deliveryTime = formatDate(item.bean.shipdate);
						}else{
							deliveryTime = '';
						}
						var receiverName = item.receiverName;
						if(receiverName==null || receiverName==undefined){
							receiverName = '';
						}
						var receiverAddress = item.receiverAddress;
						if(receiverAddress==null || receiverAddress==undefined){
							receiverAddress = '';
						}
						var shipnumber = item.bean.shipnumber;
						if(shipnumber==null || shipnumber==undefined){
							shipnumber = '';
						}
						var prizeType = item.prizeType;
						if(prizeType==null || prizeType==undefined){
							prizeType = '';
						}
						html += '<tr class="table_content">';
						html += '<td><input type="hidden" value="'+item.bean.shipid+'">';			
						html += '<span>'+item.bean.mobileno+'</span></td>';			
						html += '<td>'+item.activityName+'</td>';			
						html += '<td>'+item.prizeName+'</td>';			
						html += '<td>'+getStatus(item.bean.shipstatus)+'</td>';			
						html += '<td>'+deliveryTime+'</td>';
						html += '<td>'+receiverName+'</td>';			
						html += '<td>'+item.prizeLevel+'</td>';			
						html += '<td><span class="d_mod sbtn btn_ov Modify" onclick="viewDeliveryInfo(\''+item.bean.shipid+'\',\''+item.bean.mobileno+'\',\''+item.activityName+'\',\''+item.prizeName+'\',\''+receiverName+'\',\''+receiverAddress+'\',\''+shipnumber+'\',\''+prizeType+'\')">Delivery</span></td>';			
						html += '</tr>';			
					});
					$(".table_content").remove();
					if(html == ''){
						html += '<tr class="table_content"><td colspan="3">Query list is empty!</td></tr>';
						$(".page_nav").hide();
					}else{
						$(".page_nav").show();
					}					
					$("#listTable").append(html);
					
					pagination(page);
				}
			});			
		} 
		
		function viewDeliveryInfo(shipid,mobileNo,activityName,prizeName,receiverName,receiverAdress,shipnumber,prizeType){
			$("#modify_form li:gt(2)").show();		
			$("#shipid").val(shipid);
			$("#ModiMobileNo").val(mobileNo);
			$("#ModiActivityName").val(activityName);
			$("#ModiPrizeName").val(prizeName);
			if(prizeType=='1'){
				$("#ModiReceiverName").val(receiverName);
				$("#ModiReceiverAdress").val(receiverAdress);
				$("#ModiShipNumber").val(shipnumber);
			}else{
				$("#modify_form li:gt(2)").hide();
			}
			$('#modify_deliveryInfo').slideDown();
		}
		
		
		function modifyDeliveryInfo(){
			var flag = $('#modify_form').validationEngine('validate');
			if(!flag){
				return;
			}
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/prizedeliverylist/update",
				data: $("#modify_form").serialize(),
				dataType: "json",
				success: function(data) {				
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								$('#modify_deliveryInfo').slideUp();
								list();
							}else{
								alert(data.msg)
							}
						}
				},failure:function(){
						alert('Update failure!');
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
		    
		    function getStatus(status){
		    	var stautsAll = [['0','already wined'],['1','Already receiver info completed'],['2','already shipped']];
		    	for(var i=0;i<stautsAll.length;i++){
		    		if(status &&　status==stautsAll[i][0]){
		    			return stautsAll[i][1];
		    		}
		    	}
		    }          
        
    </script>
</head>
<body style="background-color: inherit;" class="table_body">
<input type="text" id="htmlIdentifier" value="add" style="display: none;" />
		<c:set var="t"  value=""/>
		 <c:forEach begin="1" end="${empty userModelSession.mobileLength ? 5 : userModelSession.mobileLength - 5}">
		 	<c:set var="t" value="${t}${'x'}" />
		 </c:forEach>
    <div class="table_search">
	        <ul>
	        <form id="conditionForm">
	            <li>phone number<input class="validate[custom[posInteger],maxSize[11]] phone" id="mobileNo" name="bean.mobileno" type="text"  ></li>
	            <li>shipDate <input class="num Wdate" name="startTime" id="startTime" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
	            <li>to <input class="num Wdate" name="endTime" id="endTime" type="text"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',readOnly:true,lang:'en'})"></li>
	            <li>activity Name <input class=" num" name="activityName" id="activityName" type="text"></li>
	            <li>delivery Status 
		            	<select id="shipstatus" name="bean.shipstatus" >
		            		<option value="">请选择</option>
		            		<option value="0">已中奖</option>
		            		<option value="1">已填写收件人信息</option>
		            		<option value="2">已发货</option>
		            	</select>	            	
	            </li>
	            <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	            <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>	  	            
	        </form>
	            <li><button class="btn_ov mid_btn search" onclick="prizeDeliveryList()">query</button></li>
	        </ul>	        
    </div>
    
	<div id="modify_deliveryInfo" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify DeiveryInfo  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">		                
		                <li><label for=""> phone number</label><input id="ModiMobileNo"  type="text"  disabled="disabled"/></li>
		                <li><label for=""> activity Name</label><input  id="ModiActivityName" type="text"  disabled="disabled"/></li>		                
		            	<li><label for="">prize name</label><input id="ModiPrizeName" type="text" disabled="disabled"></li>
		            	<li><label for="">receiver name</label><input id="ModiReceiverName"  type="text" disabled="disabled"></li>
		            	<li><label for="">receiver address</label><input id="ModiReceiverAdress" type="text" disabled="disabled"></li>
		            	<li><label for="">ship number</label><input id="ModiShipNumber" name="shipnumber" type="text" data-validation-engine="validate[required,maxSize[100]]"></li>
		            	<input type="hidden" name="shipid" id="shipid" value="" data-validation-engine="validate[required]"/>
		            </ul>   
		            <input id="icon_id" name="id" type="hidden">         	
	            	<button id ="modify_btn" type="button" class="btn_ov apply" onclick="modifyDeliveryInfo()">Modify</button>
	            </form>
	        </div>	
	    </div>
	</div>    
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td>phone number</td>
         <td>activity name</td>
         <td>prize name</td>
         <td>delivery status</td>
         <td>delivery time</td>
         <td>receiver name</td>
         <td>prize level</td>
         <td>operation</td>
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
