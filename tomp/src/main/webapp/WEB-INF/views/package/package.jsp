<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <link rel="stylesheet" href="<%=path %>/css/base2.css">
    <link rel="stylesheet" href="${ctx}/js/dist/themes/default/style.min.css" />
    <script src="<%=path %>/js/base.js"></script>
    <script src="${ctx}/js/dist/jstree.min.js"></script>
    <script type="text/javascript">
	    $(function($) {
			$('#conditionForm').validationEngine('attach',{
					promptPosition: 'bottomRight'
			});	    	
	    	
	    	$('#addEditForm').validationEngine('attach');
	    
			list();	 
	    }); 
	    
		function changeCheckAll(obj){
			if($("#checkall").is(":checked") && !$(obj).is(":checked")){
				$("#checkall").prop("checked",false);
			}
		}
		
		function packageList(){
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
				url: "${ctx}/package/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var html = '';
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td><input type="checkbox" onclick="changeCheckAll(this)"><input type="hidden" value="'+item.packageid+'">';			
						html += '<span>'+item.fgno+'</span></td>';			
						html += '<td>'+getRealVal(item.fgname)+'</td>';			
						html += '<td>'+getRealVal(item.fgdesc)+'</td>';			
						html += '<td>'+item.amount+''+item.currency+'</td>';
						html += '<td>'+getRealVal(item.langno)+'</td>';			
						html += '<td>'+getStatusStr(item.status)+'</td>';			
						html += '<td>';			
						html += '<button class="btn_ov ovpd normal" onclick="packageEditView(\''+getRealVal(item.packageid)+'\',\''+getRealVal(item.fgno)+'\',\''+getRealVal(item.fgname)+'\',\''+getRealVal(item.fgdesc)+'\',\''+item.amount+'\',\''+getRealVal(item.currency)+'\',\''+getRealVal(item.langno)+'\',\''+getRealVal(item.status)+'\')">edit</button>';
						html += '<button class="btn_ov ovpd goods" onclick="on(\''+item.packageid+'\',\'0\')">Put On</button>';
						html += '<button class="btn_ov ovpd goods" onclick="off(\''+item.packageid+'\',\'1\')">Pull Off</button>';
						html += '<button class="btn_ov ovpd normal" onclick="relation(\''+item.packageid+'\')">Relation</button>';
						html += '</td>';			
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
		
		
		function batchDel(){
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
					url: "${ctx}/package/delete",
					data: {
						"delIds":delIds,
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
		
		function relation(packageid){
			$("#relationPackageId").val(packageid);
        	$.jstree.destroy('#pop');
        	$.ajax({
        		'url':"${ctx}/package/getPackageDetailList",
        		'dataType':'json',
        		success:function(map){
        			var array = [];
        			$.each(map.list,function(i,parentDetail){
	            			var child = [];
							$.each(parentDetail.childDetail,function(i,childDetail){
									if(childDetail.packageid == packageid ){
		        						child.push({
		        							text:childDetail.itemname+"_"+childDetail.itemvalue,
		        							state:{checked:true},
		            						li_attr:{
		        								packagedetailid:childDetail.packagedetailid
		        							}
		        						});
									}else{
		        						child.push({
		        							text:childDetail.itemname+"_"+childDetail.itemvalue,
		        							li_attr:{
		        								packagedetailid:childDetail.packagedetailid
		        							}
		        						});
									}								
							});
							if( parentDetail.packageid == packageid ){
	        					array.push({
		        						id:parentDetail.packagedetailid,
		        						text:parentDetail.packagename,
		        						children:child,
		        						state:{checked:true}										
	        					});								
							}else{
	        					array.push({
		        						id:parentDetail.packagedetailid,
		        						text:parentDetail.packagename,
		        						children:child
	        					});							
							}
					
        			});
        			if(array.length > 0){
	                	$('#pop').jstree({
	                		plugins : ["wholerow","checkbox"],
	                		checkbox:{
	                			tie_selection:false
	                		},
	                		core:{
	                			data:array
	                		}
	                	});
        			}else{
        				$('#pop').css("text-align","center").html("<h3>You haven't create tree!</h3>")
        			}               	
        		}
        	});
        	$("#mask").show();
        	$("#addpackages").hide();
        	$('#jstree').slideDown();
		}
		
		function saveRelation(){
				var packageid = $("#relationPackageId").val();
	    		var d_array = [];
	    		var nodes = $.jstree.reference('#pop').get_bottom_checked(true);
	    		console.log(nodes);
	    		$.each(nodes,function(i,node){
	    			if(node.parents.length>1){
	    				d_array.push(node.parent + "-" + node.li_attr.packagedetailid);
	    			}else{
	    				d_array.push(node.id+"-"+node.id);
	    			}
	    			
	    		});
	    		if(nodes.length>0){
	        		$.ajax({
	        			url: "${ctx}/package/saveRelation",
	        			type:"post",
	        			data:{
	        				"packageid":packageid,
	        				"relationids":d_array
	        			},
	        			dataType:'json',
	        			success:function(data){
	        				if(data.success){
	        					$("#mask").hide();
	            				$('#jstree').slideUp(0);
	        				}else{
	        					alert(data.msg);
	        				}        				
	        			},
	        			error:function(){
	        				alert("Save failed!");
	        			}
	        		});
	    		}		
		}
		
		function on(packageid,status){
			var delIds = [];
			delIds.push(packageid);
			updateStatus(delIds,status);
		}
		
		function off(packageid,status){
			var delIds = [];
			delIds.push(packageid);
			updateStatus(delIds,status);
		}
		
		
		function batchOnOrOff(status){
			if(status==null || status==undefined || status==''){
				return;
			}
			var delItems = $("#listTable").find(":checkbox:checked").next(":hidden");
			var delIds = [];
			$.each(delItems,function(i,item){
				delIds.push($(item).val());
			});
			if(delIds.length==0){
				alert("Please select at least one record!!");
				return;
			}
			updateStatus(delIds,status);	
		}
		
		
		function updateStatus(delIds,status){
			var tips = "Are you sure you want to add these selected datas in bulk?";
			if(status == '1'){
				tips = 'Are you sure you want to bulk off these packages?';
			}
			if(window.confirm(tips)){
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}			
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/package/updateStatus",
					data: {
						"delIds":delIds,
						"status" : status
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
						alert('Update failure!');
					
					}
				});				
			}		
		}
		
		
		function packageAddView(){
			$("#addEditForm")[0].reset();
			$("#packageid").val("");
			$("#orderno").val(parseInt($("#totalRowNum").text())+1);
			$("#jstree").hide();
			$("#addpackages").show();
			$("#mask").show();
		}
		
		function packageEditView(packageid,fgno,fgname,fgdesc,amount,currency,langno,status){
			$("#addEditForm")[0].reset();
			$("#packageid").val(packageid);
			$("#orderno").val("");
			$("#fgno").val(fgno);
			$("#fgname").val(fgname);
			$("#fgdesc").val(fgdesc);
			$("#amount").val(amount);
			$("#currency").val(currency);
			$("#langno").val(langno);
			$("#status").val(status);
			
			$("#operateType").empty().html("Edit");
// 			$("#fgno").attr("readOnly","readOnly");
			$("#jstree").hide();
			$("#addpackages").show();
			$("#mask").show();
		}		
		
		
		function savePackage(){
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
					url: "${ctx}/package/save",
					data: $("#addEditForm").serialize(),
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							$("#addpackages").hide();
							$("#mask").hide();							
							if(success){
								list();
							}else{
								alert(msg)
							}
						}
					},failure:function(){
						alert('Save failure!');
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
        
        function getRealVal(value){
        	if(value==undefined || value=="null" || value==null){
        		value='';
        	}
        	return value;
        }
        
        function getStatusStr(value){
        	var str = getRealVal(value);
			if(str == ''){
				return str;
			}
			if(str == '0'){
				return 'on';
			}
			if(str == '1'){
				return 'off';
			}
        } 							       	
    </script>
</head>
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search ">
        <ul class="clearfixa">
        	<form id="conditionForm">
            <li>package no:
                <input type="text" name="bean.fgno" data-validation-engine="validate[custom[number]]">
            </li>
            <li>package name:
                <input type="text" name="bean.fgname">
            </li>
            <li>status:
                <select name="bean.status" id="">
                        <option value="">please select</option>
                        <option value="0">on</option>
                        <option value="1">off</option>
                 </select>
            </li>
	            <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	            <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>             
            </form>
            <li><button class="btn_defs1" onclick="packageList()">Search</button></li>
            <li><button class="btn_ov ovpd del" onclick="batchDel()">Batch Delete</button></li>
            <li><button class="btn_ov ovpd batch" onclick="batchOnOrOff('0')">Batch Put On</button></li>
            <li><button class="btn_ov ovpd batch" onclick="batchOnOrOff('1')">Batch Pull Off</button></li>
            <li class="fr"><button class="btn_ov ovpd normal" onclick="packageAddView()">+ Add Packages</button></li>
            <li class=""></li>
        </ul>

    </div>
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td><input type="checkbox" id="checkall">package no</td>
         <td >package name</td>
         <td>package desc</td>
         <td >price</td>
         <td>language</td>
         <td>status</td>
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
    <div class="edit_mod pm_addpackage" id="addpackages" style="display: none;">
        <div class="title"><span id="operateType">Add</span> <i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
            	<form id="addEditForm">
                <li>package no</li>
                <li><input type="text" name="fgno" id="fgno" data-validation-engine="validate[custom[number]]"></li>
                <li>package name</li>
                <li><input type="text" name="fgname" id="fgname" ></li>
                <li>package describe</li>
                <li><input type="text" name="fgdesc" id="fgdesc" ></li>
                <li>amount</li>
                <li><input type="text" name="amount" id="amount" data-validation-engine="validate[required]"></li>
                <li>currency</li>
                <li><input type="text" name="currency" id="currency"></li>
                <li>language</li>
                <li><input type="text" name="langno" id="langno"></li>
                <li>status</li>
                <li>
                    <select name="status" id="status">
                        <option value="0">on</option>
                        <option value="1">off</option>
                    </select>
                </li>
                <input type="hidden" name="orderno" id="orderno"/>
                <input type="hidden" name="packageid" id="packageid"/>
                </form>
            </ul>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="savePackage();">save</button>
        </div>
    </div>

	<div id="jstree" class="mask_layout addUser" >
	    <div class="role_selection_wrap">
	        <div class="top">Relation PackageDetail<i class="icon iclose" >X</i></div>
	        <div class="cont">	        	
    			<div id="pop"></div>
    			<input type="hidden" id="relationPackageId" value=""/>
	            <button id ="jstree_btn" type="button" class="btn_ov apply" onclick="saveRelation()">Save</button>
	        </div>	
	    </div>
	</div>

<!--     <div class="edit_mod" id="jstree" style="display: none;"> -->
<!--         <div class="title">分配 <i class="icon iclose"></i></div> -->
<!--         <div class="cont"> -->
<!--     			<div id="pop"></div> -->
<!-- 	            <button id ="jstree_btn" type="button" class="btn_ov apply">Save</button> -->
<!--         </div> -->
<!--         <div class="floor"> -->
<!--             <button class="btn_ov mid_btn search cancel">cancel</button> -->
<!--             <button class="btn_ov mid_btn search">save</button> -->
<!--         </div> -->
<!--     </div> -->
</div>

</body>
</html>
