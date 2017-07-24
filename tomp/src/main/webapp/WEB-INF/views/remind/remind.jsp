<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
    <style type="text/css">
    	.dis{
    		background-color: #ebebe4;
    	}
    </style>
    <script type="text/javascript">
		$(function($) {
		 $('#editBatchForm').validationEngine('attach');			
		 $('#editForm').validationEngine('attach');			
		 $('#addForm').validationEngine('attach');			
			list();
		});
		
		function RemindList(){
            $("#pageIndex").val(1);
           	list();			
		}		 
		
		function list(){
// 			var flag = $('#conditionForm').validationEngine('validate');
// 			if(!flag){
// 				return;
// 			}
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/remind/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var html = '';
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td><input type="checkbox" onclick="changeCheckAll(this)"><input type="hidden" value="'+item.id+'">';			
						html += '<span>'+(item.brand!=undefined?item.brand:'')+'</span></td>';			
						html += '<td>'+(item.preposindicator!=undefined?item.preposindicator:'')+'</td>';			
						html += '<td>'+(item.type!=undefined?getPop(item.type):'')+'</td>';			
						html += '<td>'+(item.remindval!=undefined?item.remindval:'')+'</td>';			
						html += '<td>'+(item.isopen!=undefined?getVaild(item.isopen):'')+'</td>';			
						html += '<td><span class="d_mod sbtn btn_ov Modify" onclick="viewModify(\''+item.id+'\',\''+item.brand+'\',\''+item.preposindicator+'\',\''+item.type+'\',\''+item.remindval+'\',\''+item.isopen+'\')">Modify</span></td>';			
						html += '</tr>';			
					});
					$(".table_content").remove();
					if(html == ''){
						html += '<tr class="table_content"><td colspan="6">Query list is empty!</td></tr>';
						$(".page_nav").hide();
					}else{
						$(".page_nav").show();
					}					
					$("#listTable").append(html);
					
					pagination(page);
				}
			});			
		}
		
		function ViewAdd(){
			$("#addForm")[0].reset();
			
			
			$("#editBatch").hide();
			$("#edit").hide();
			$("#add").show();
			$("#mask").show();				
		}
		
		function addRemind(){
			var flag = $('#addForm').validationEngine('validate');
			if(!flag){
				return;
			}
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/remind/add",
					data: $("#addForm").serialize(),
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								list();
								$("#add").hide();
								$("#mask").hide();									
							}else{
								alert(msg)
							}
						}
					},failure:function(){
						alert('Add failure!');
					}
				});				
		}
		
		function delRemind(){
			var batchItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var batchIds = [];
			$.each(batchItems,function(i,item){
				batchIds.push($(item).val());
			});
			if(batchIds.length==0){
				alert("Please select at least one record!!");
				return;
			}
			if(window.confirm("Are you sure you want to delete the selected data?")){
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/remind/delete",
					data: {
						"batchIds":batchIds
						},
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								list();
							}else{
								alert(msg)
							}
						}
					},failure:function(){
					
						alert('Delete failure!');
					
					}
				});				
			}					
		}
		
		function viewModify(id,brand,preposindicator,type,remindval,isopen){
			$("#editForm")[0].reset();
			
			$("#editId").val(id);
			$("#editBrand").val(brand);
			$("#editPreposindicator").val(preposindicator);
			$("#editType").val(type);
			$("#editRemindVal").val(remindval);
			$("#editIsopen").val(isopen);
			
			$("#editBatch").hide();
			$("#add").hide();
			$("#edit").show();
			$("#mask").show();					
		}
		
		function modify(){
			var flag = $('#editForm').validationEngine('validate');
			if(!flag){
				return;
			}
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/remind/modify",
					data: $("#editForm").serialize(),
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								list();
								$("#edit").hide();
								$("#mask").hide();									
							}else{
								alert(msg)
							}
						}
					},failure:function(){
						alert('Update failure!');
					}
				});						
		}
		
		function batchOnOff(type){
			if(type==undefined || type=="" ){
				return;
			}
			var batchItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var batchIds = [];
			$.each(batchItems,function(i,item){
				batchIds.push($(item).val());
			});
			if(batchIds.length==0){
				alert("Please select at least one record!!");
				return;
			}
// 			if(window.confirm("Are you sure you want to delete the selected data?")){
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/remind/batchOnOff",
					data: {
						"batchIds":batchIds,
						"type" : type
						},
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								list();
							}else{
								alert(msg)
							}
						}
					},failure:function(){
					
						alert('Update failure!');
					
					}
				});				
// 			}						
		}
		
		function batchUpdateTimeInterval(){
			var batchItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var batchIds = [];
			$.each(batchItems,function(i,item){
				batchIds.push($(item).val());
			});
			if(batchIds.length==0){
				alert("Please select at least one record!!");
				return;
			}
			$("#editBatchForm")[0].reset();
			$("#edit").hide();
			$("#add").hide();
			$("#editBatch").show();
			$("#mask").show();			
			
		}
		
		function batchEditTimeSub(){
			var flag = $('#editBatchForm').validationEngine('validate');
			if(!flag){
				return;
			}		
			var batchItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var batchIds = [];
			$.each(batchItems,function(i,item){
				batchIds.push($(item).val());
			});
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/remind/batchSetTimeInterval",
					data: {
						"batchIds":batchIds,
						"remindVal" : $("#remindVal").val()
						},
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								list();
								$("#editBatch").hide();
								$("#mask").hide();									
							}else{
								alert(msg)
							}
						}
					},failure:function(){
						alert('Update failure!');
					}
				});							
		}		
		
		function changeCheckAll(obj){
			if($("#checkall").is(":checked") && !$(obj).is(":checked")){
				$("#checkall").prop("checked",false);
			}
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
		
			//获取付费类型		
		    function getType(status){
		    	var stautsAll = [['1','prepaid'],['2','postpaid']];
		    	for(var i=0;i<stautsAll.length;i++){
		    		if(status &&　status==stautsAll[i][0]){
		    			return stautsAll[i][1];
		    		}
		    	}
		    } 
		    
			//获取弹窗类型		
		    function getPop(status){
		    	var stautsAll = [['2','Flow'],['3','Activity']];
		    	for(var i=0;i<stautsAll.length;i++){
		    		if(status &&　status==stautsAll[i][0]){
		    			return stautsAll[i][1];
		    		}
		    	}
		    } 
		    		     		
			//获取弹窗类型		
		    function getVaild(status){
		    	var stautsAll = [['1','on'],['0','off']];
		    	for(var i=0;i<stautsAll.length;i++){
		    		if(status &&　status==stautsAll[i][0]){
		    			return stautsAll[i][1];
		    		}
		    	}
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
    </script>
</head>
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search">
        <ul>
        <form id="conditionForm">
            <li>Brand Name:
                <input type="text" name="bean.brand">
            </li>
            <li>Paid Type:
                <select name="bean.preposindicator" style="width: 120px;">
                        <option value="">please select</option>
                        <option value="Prepaid">prepaid</option>
                        <option value="Postpaid">postpaid</option>
                 </select>
            </li>
            <li>Pop Type:
                <select name="bean.type" id="" style="width: 120px;">
                	<option value="">please select</option>
                    <option value="2">Flow</option>
                    <option value="3">Activity</option>
                </select>
            </li>
	            <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	            <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>            
            </form>
            <li><button class="btn_ov mid_btn search" onclick="RemindList()">Research</button></li>
            <li><button class="btn_ov mid_btn search" onclick="ViewAdd()">Add</button></li>
            <li><button class="btn_ov mid_btn search" onclick="delRemind()">Delete</button></li>
            <li><button class="btn_ov mid_btn search" onclick="batchOnOff('1')">Batch On </button></li>
            <li><button class="btn_ov mid_btn search" onclick="batchOnOff('0')">Batch Off </button></li>
            <li><button class="btn_ov mid_btn search" onclick="batchUpdateTimeInterval()">Time Interval</button></li>
            <li class=""></li>
        
        </ul>
    </div>
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td><input class="checkall" id="checkall" type="checkbox"  style="left:30px;position: absolute;">Brand Name</td>
         <td >Paid Type</td>
         <td>Pop Type</td>
         <td >Time InterVal(Hours)</td>
         <td>Open Or Not</td>
         <td >Operate</td>
     </tr>
<!--      <tr class="table_content"> -->
<!--          <td class=""><input type="checkbox"> <span>18626488888</span> </td> -->
<!--          <td >1,500,000 </td> -->
<!--          <td>1,00,000 </td> -->
<!--          <td >500,000 </td> -->
<!--          <td>100,000 </td> -->
<!--          <td ><button class="btn_ov mid_btn search">edit</button></td> -->
<!--      </tr> -->
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

<div class="mask_layout" id="mask" style="display: none;">
    <div class="edit_mod" id="add" style="display: none;">
        <div class="title">Add <i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
            <form id="addForm">
                <li>Brand</li>
                <li><input type="text" id="addBrand" name="brand" data-validation-engine="validate[maxSize[20]]"></li>
                <li>Paid Type</li>
                <li>
                    <select name="preposindicator" id="addPreposindicator">
                        <option value="Prepaid">prepaid</option>
                        <option value="Postpaid">postpaid</option>
                    </select>
                </li>
                <li>Pop Type</li>
                <li> <select name="type" id="addType">
                    <option value="2">Flow</option>
                    <option value="3">Activity</option>
                </select></li>
                <li>Time Interval(Hours) <input type="text" style="width:20%" name="remindval" id="addRemindVal" data-validation-engine="validate[required,custom[hourPosInteger]]"> (Hours)</li>
                <li>Open Or Not</li>
                <li> <select name="isopen" id="addIsopen">
                    <option value="1">on</option>
                    <option value="0">off</option>
                </select></li>
           	</form>
            </ul>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="addRemind()">save</button>
        </div>
    </div>
    <div class="edit_mod" id="edit" style="display: none;">
        <div class="title">Modify <i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
            <form id="editForm">
            	<input type="hidden" name="id" id="editId"/>
                <li>Brand</li>
                <li><input type="text" id="editBrand" name="brand" disabled="disabled"></li>
                <li>Paid Type</li>
                <li>
                    <select name="preposindicator" id="editPreposindicator" disabled="disabled" class="dis">
                        <option value="Prepaid">prepaid</option>
                        <option value="Postpaid">postpaid</option>
                    </select>
                </li>
                <li>Pop Type</li>
                <li> <select name="type" id="editType" disabled="disabled" class="dis">
                    <option value="2">Flow</option>
                    <option value="3">Activity</option>
                </select></li>
                <li>Time Interval(Hours) <input type="text" style="width:20%" name="remindval" id="editRemindVal" data-validation-engine="validate[required,custom[hourPosInteger]]"> (Hours)</li>
                <li>Open Or Not</li>
                <li> <select name="isopen" id="editIsopen">
                    <option value="1">on</option>
                    <option value="0">off</option>
                </select></li>
           	</form>
            </ul>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="modify()">modify</button>
        </div>
    </div>
    <div class="edit_mod" id="editBatch" style="display: none;">
        <div class="title">Modify <i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
				<form id="editBatchForm">
                <li>Time Interval <input type="text" style="width:20%" data-validation-engine="validate[required,custom[hourPosInteger]]" id="remindVal"> (Hours)</li>
				</form>
            </ul>
        </div>
        <div class="floor">
            <button type="button" class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="batchEditTimeSub()">save</button>
        </div>
    </div>
</div>

</body>
</html>
