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
    	var flag = true;
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
		
		function packageTypeList(){
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
				url: "${ctx}/packageType/list",
				data: $("#conditionForm").serialize(),
				dataType: "json",
				success: function(data) {				
					var result = data.list;
					var page = data.page;
					var html = '';
					$.each(result,function(i,item){
						html += '<tr class="table_content">';
						html += '<td><input type="checkbox" onclick="changeCheckAll(this)"><input type="hidden" value="'+item.packagetypeid+'">';			
						html += '<span>'+getRealVal(item.packagetypeid)+'</span></td>';			
						html += '<td>'+getRealVal(item.ptname)+'</td>';			
						html += '<td>'+getRealVal(item.brand)+'</td>';			
						html += '<td>'+getRealVal(item.orderno)+'</td>';			
						html += '<td>';			
						html += '<button class="btn_ov ovpd normal" onclick="packageTypeEditView(\''+getRealVal(item.packagetypeid)+'\',\''+getRealVal(item.ptname)+'\',\''+getRealVal(item.brand)+'\',\''+getRealVal(item.orderno)+'\')">edit</button>';
						html += '<button class="btn_ov ovpd goods" onclick="up(this,\''+item.packagetypeid+'\',\''+item.orderno+'\',\'-1\')">Up</button>';
						html += '<button class="btn_ov ovpd goods" onclick="down(this,\''+item.packagetypeid+'\',\''+item.orderno+'\',\'+1\')">Down</button>';
						html += '<button class="btn_ov ovpd normal" onclick="relation(\''+item.packagetypeid+'\')">Relation</button>';
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
		
		function up(va,packagetypeid,orderno,type){
			if(orderno!=null && orderno!=undefined && orderno!=1){
				var preId = $(va).parents(".table_content").prev().find(":hidden").val();
				var preType = "+1";
				updateOrderNo(packagetypeid,type,preId,preType);
			}else{
				alert('This PackageType can not up');
			}
		}
		
		function down(va,packagetypeid,orderno,type){
			if(orderno!=null && orderno!=undefined && orderno!=parseInt($("#totalRowNum").text())){
				var afterId = $(va).parents(".table_content").next().find(":hidden").val();
				var afterType = "-1";
				updateOrderNo(packagetypeid,type,afterId,afterType);
			}else{
				alert('This PackageType can not down');
			}
		}
		
		function updateOrderNo(packagetypeid,type,preorafterid,preoraftertype){
				if($("#checkall").is(":checked")){	
					$("#checkall").prop("checked",false);
				}
				if(flag){
					flag = false;
					$.ajax({
						type: "POST",
						cache:false,
						url: "${ctx}/packageType/updateOrderNo",
						data: {
							"packagetypeid":packagetypeid,
							"type" : type,
							"preorafterid" : preorafterid,
							"preoraftertype" : preoraftertype
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
						
						},
						complete : function(){
							flag = true;
						}
					});				
				}			
			
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
					url: "${ctx}/packageType/delete",
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
		
		function relation(packagetypeid){
			$("#relationpackagetypeid").val(packagetypeid);
        	$.jstree.destroy('#pop');
        	$.ajax({
        		'url':"${ctx}/packageType/getRelationPackageList",
        		'dataType':'json',
        		data:{"packagetypeid":packagetypeid},
        		success:function(map){
        			var array = [];
        			$.each(map.list,function(i,relationPackage){
	        				if(relationPackage.packagetypeid != undefined && relationPackage.packagetypeid!=""){
	        					array.push({
	        						id:relationPackage.packageid,
	        						text:relationPackage.fgname+"_"+relationPackage.fgno,
	        						state:{checked:true}
	        					});
	        				}else{
	        					array.push({
	        						id:relationPackage.packageid,
	        						text:relationPackage.fgname+"_"+relationPackage.fgno
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
				var packagetypeid = $("#relationpackagetypeid").val();
	        		$.ajax({
	        			url: "${ctx}/packageType/saveRelation",
	        			type:"post",
	        			data:{
	        				"packagetypeid":packagetypeid,
	        				"packageids":$.jstree.reference('#pop').get_checked()
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
		
		
		function packageTypeAddView(){
			$("#addEditForm")[0].reset();
			$("#packagetypeid").val("");
			$("#jstree").hide();
			
			$(".edit_mod ").css({"height":"245px","top":"50%"});
			
			$("#addpackages").show();
			$("#mask").show();
		}
		
		
		function packageTypeEditView(packagetypeid,ptname,brand,orderno){
			$("#addEditForm")[0].reset();
			$("#packagetypeid").val(packagetypeid);
			$("#ptname").val(ptname);
			$("#brand").val(brand);
			
			$(".edit_mod ").css({"height":"245px","top":"50%"});
			
			$("#operateType").empty().html("Edit");
			$("#jstree").hide();
			$("#addpackages").show();
			$("#mask").show();
		}		
		
		function savePackageType(){
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
					url: "${ctx}/packageType/save",
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
    </script>
</head>
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search ">
        <ul class="clearfixa">
        	<form id="conditionForm">
            <li>packagetype name:
                <input type="text" name="bean.ptname">
            </li>
            <li>brand name:
                <input type="text" name="bean.brand">
            </li>
	            <input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
	            <input type="hidden" name="pageRowNum" id="pageRowNum" value="10"/>              
			</form>
            <li><button class="btn_defs1" onclick="packageTypeList()">Research</button></li>
            <li><button class="btn_ov ovpd del" onclick="batchDel()">Batch Delete</button></li>
            <li class="fr"><button class="btn_ov ovpd normal" onclick="packageTypeAddView()">Add</button></li>
            <li class=""></li>
        </ul>

    </div>
 <table class="table bor" id="listTable">
     <tr class="table_title">
         <td><input type="checkbox" id="checkall">packagetype no</td>
         <td >packagetype name</td>
         <td>brand name</td>
         <td >order no</td>
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
        <div class="title"><span id="operateType">Add </span><i class="icon iclose"></i></div>
        <div class="cont">
            <ul>
            	<form id="addEditForm">
	            <li>packagetype name:</li>
	            <li>
	                <input type="text" id="ptname" name="ptname">
	            </li>
	            <li>brand name:</li>
	            <li>
	                <input type="text" id="brand" name="brand">
	            </li>
                <input type="hidden" name="packagetypeid" id="packagetypeid"/>                
                </form>
            </ul>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" onclick="savePackageType();">save</button>
        </div>
    </div>

	<div id="jstree" class="mask_layout addUser" >
	    <div class="role_selection_wrap">
	        <div class="top">Relation Package<i class="icon iclose" >X</i></div>
	        <div class="cont">	        	
    			<div id="pop"></div>
    			<input type="hidden" id="relationpackagetypeid" value=""/>
	            <button id ="jstree_btn" type="button" class="btn_ov apply" onclick="saveRelation()">Save</button>
	        </div>	
	    </div>
	</div>
</div>

</body>
</html>
