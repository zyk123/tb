<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <link rel="stylesheet" href="<%=path %>/css/base2.css">
    <script src="<%=path %>/js/base.js"></script>
    <style type="text/css">
    	#editPackages{
    		height:240px;
    	}
    </style>
    <script type="text/javascript">
	    $(function($) {
	    	$('#conditionForm').validationEngine('attach',{
				promptPosition: 'bottomRight'
			});
			
	    	$('#addEditForm').validationEngine('attach');
			
			$("#typeSelect").on('change',function(){
				var value = $(this).val();
				judgeType(value);
			})			
			
			list();	    
	    });
	    

		function changeCheckAll(obj){
			if($("#checkall").is(":checked") && !$(obj).is(":checked")){
				$("#checkall").prop("checked",false);
			}
		}
		
		function packageDetailList(){
            $("#pageIndex").val(1);
           	list();			
		}
		
		function list(){
			var flag = $('#conditionForm').validationEngine('validate');
			if(!flag){
				return;
			}
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/packageDetail/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var parentResult = data.parentList;
					var html = '';
					$("#parentdetail option").not(":first").remove();
				    $('#parentdetail').select2({
							     data: parentResult,
							     placeholder:'please select',//默认文字提示
							     allowClear: true//允许清空
					        })					
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td><input type="checkbox" onclick="changeCheckAll(this)"><input type="hidden" value="'+item.bean.packagedetailid+'">';			
						html += '<span>'+getRealVal(item.packagename)+'</span></td>';			
						html += '<td>'+getRealVal(item.bean.itemname)+'</td>';			
						html += '<td>'+getRealVal(item.bean.itemvalue)+'</td>';			
						html += '<td>'+getRealVal(item.bean.detailname)+'</td>';			
						html += '<td><button class="btn_ov ovpd normal" onclick="packageDetailModify(\''+item.bean.packagedetailid+'\',\''+item.bean.parentid+'\',\''+getRealVal(item.bean.itemname)+'\',\''+getRealVal(item.bean.itemvalue)+'\',\''+getRealVal(item.bean.detailname)+'\')">edit</button></td>';			
						html += '</tr>';			
					});
					$(".table_content").remove();
					if(html == ''){
						html += '<tr class="table_content"><td colspan="7">Query list is empty!</td></tr>';
						$(".page_nav").hide();
					}else{
						$(".page_nav").show();
					}					
					$("#listTable").append(html);
					
					pagination(page);
				}
			});			
		}
		
		
		function saveDetail(){
			var flag = $('#addEditForm').validationEngine('validate');
			if(!flag){
				return;
			}
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}		
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/packageDetail/save",
					data: $("#addEditForm").serialize(),
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							$("#editPackages").hide();
							$("#mask").hide();							
							if(success){
								list();
							}else{
								alert(data.msg)
							}
						}
					},failure:function(){
						alert('Save failure!');
					}
				});			
		}
		
		function packageDetailView(){
			$("#area").show();
			judgeType('1');	
			$("#addEditForm")[0].reset();
			$("#parentdetail").select2().val(null).trigger("change");
			$("#packagedetailid").val("");

			$("#editPackages").show();
			$("#mask").show();
		}
		
		
		function  packageDetailModify(packagedetailid,parentid,itemname,itemvalue,detailname){
			var value = "2";
			if(parentid=='-1'){
				value = '1';
			}
			judgeType(value);		
		
			$("#addEditForm")[0].reset();
			$("#packagedetailid").val(packagedetailid);
			$("#parentdetail").select2().val(parentid).trigger("change");
			$("#itemname").val(itemname);
			$("#itemvalue").val(itemvalue);
			$("#detailname").val(detailname);
			
			$("#operateType").empty().text('Edit');
			$("#area").hide();			
			$("#editPackages").show();
			$("#mask").show();						
		}
		
		function deletePackageDetailList(){
			var delItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var delIds = [];
			$.each(delItems,function(i,item){
				delIds.push($(item).val());
			});
			if(delIds.length==0){
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
					url: "${ctx}/packageDetail/delete",
					data: {
						"delIds":delIds
						},
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							if(success){
								list();
							}else{
								alert(msg)
							}
						}
					},failure:function(){
					
						alert('DeLete failure!');
					
					}
				});				
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
        
        function getRealVal(value){
        	if(value==undefined || value=="null" || value==null){
        		value='';
        	}
        	return value;
        } 
        
        
	    
	    function judgeType(value){
				$("#addEditForm input").removeAttr("data-validation-engine");
				if(value=='1'){
					$("#detailname").attr("data-validation-engine","validate[required]");
					$("#area1").show();
					$("#area2").hide();
					$("#area3").hide();
					$("#editPackages").css("height","240px");
				}
				if(value=='2'){
					$("#itemname").attr("data-validation-engine","validate[required]");
					$("#itemvalue").attr("data-validation-engine","validate[required]");
					$("#area1").hide();
					$("#area2").show();
					$("#area3").show();
					$("#editPackages").css("height","400px");
				}	    
	    }        				
    </script>
</head>
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search ">
        <ul class="clearfixa">
			<form id="conditionForm">
            <li>package name:
                <input type="text" name="packagename">
	            <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	            <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>                
            </li>
            </form>
            <li><button class="btn_defs1" onclick="packageDetailList()">Search</button></li>
            <li><button class="btn_ov ovpd del" onclick="deletePackageDetailList()">Batch Delete</button></li>
            <li class="fr"><button class="btn_ov ovpd normal" onclick="packageDetailView()">Add</button></li>
            <li class=""></li>
        </ul>

    </div>
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td><input type="checkbox" id="checkall">package name</td>
         <td >item name</td>
         <td>item value</td>
         <td>detail name</td>
         <td >operation</td>
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

<div class="mask_layout" id="mask" style="display: none;">

    <div class="edit_mod pm_addpackage" id="editPackages" style="display: none;">
        <div class="title"><span id="operateType">Add</span><i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
            	<form id="addEditForm">
            	<div id="area">
                <li>type</li>
                <li>
                    <select name="" id="typeSelect" >
                        <option value="1">primary value</option>
                        <option value="2">secondary value</option>
                    </select>
                </li>
                </div>
                <div id="area1">
                <li>detail name</li>
                <li><input type="text" name="detailname" id="detailname" data-validation-engine="validate[required]"></li>
                </div>
                <div id="area2" style="display: none;">
                <li>item name</li>
                <li><input type="text" name="itemname" id="itemname" data-validation-engine="validate[required]"></li>
                <li>item value</li>
                <li><input type="text" name="itemvalue" id="itemvalue" data-validation-engine="validate[required]"></li>
                </div>
                <div id="area3" style="display: none;">
                <li>parent node</li>
                <li>
                    <select name="parentid" id="parentdetail" style="width: 100%" data-validation-engine="validate[required]">
                    	<option value="">please select</option>
                    </select>
                </li>
                <input type="hidden" name="packagedetailid" id="packagedetailid"/>
                </div>
                </form>
            </ul>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="saveDetail()">save</button>
        </div>
    </div>
</div>

</body>
</html>
