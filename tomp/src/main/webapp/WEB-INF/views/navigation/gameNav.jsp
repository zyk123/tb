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
    	var flag=true,flag1 = true;
		$(function($) {
		$('#add_form,#modify_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});	
			
		$("#deviceChoice li a").on("click",function(){
			$("#deviceChoice li a").removeClass("hover");
			$(this).addClass("hover");
			var type = $(this).attr("value");
			GameNavList(type);
		});
		
		$("#deviceChoice li a:first").click();
		
		$('#add_btn').on('click',function(){
			if($('#add_form').validationEngine('validate') && flag){
				flag = false;
				$('#add_form').attr({
					action:"${ctx}/navContent/upload",
					method:"post",
					enctype:"multipart/form-data",
					target:"upload"
				}).submit();				
			}			
		});
		
        $('#modify_btn').on('click',function(){
            if($('#modify_form').validationEngine('validate') && flag1){
                flag1 = false;
                $('#modify_form').attr({
                    action:"${ctx}/navContent/upload",
                    method:"post",
                    enctype:"multipart/form-data",
                    target:"upload"
                }).submit();
            }
        });				
		});
		
	   	function callback(map){
	   		$(".iclose").click();
	   		if(map.retCode){  
	   			alert("Upload Successfully!");
	   			flag = true;
	   			flag1 = true;	   			 
				var type = $("#deviceChoice li a.hover").attr("value");
				list(type);	   			
	   		}else{
	   			alert("Upload Unsuccessfully!");
	   			flag = true;
	   			flag1 = true;
	   		}
	   	}		
		
		function GameNavList(type){
            $("#pageIndex").val(1);
           	list(type);			
		}			 
		
		function list(type){
			$.ajax({
				type: "POST",
				cache:false,
				url: "${ctx}/navContent/list",
				data:{"bean.type":type},
				dataType: "json",
				success: function(data) {
					try{
						var result = data.list;
						var page = data.page;
						var html = '';
						$.each(result,function(i,item){
							html += '<tr class="table_content">';
							html += '<td><input type="hidden" value="'+item.id+'">';			
							html += '<span>'+(item.orderno!=undefined?item.orderno:'')+'</span></td>';			
							html += '<td>'+(item.name!=undefined?item.name:'')+'</td>';			
							html += '<td  class="icon"><img src="${ctx}/navContent/readIcon?id='+item.id+'&t='+Math.random()+'" alt=""></td>';			
							html += '<td>'+(item.url!=undefined?item.url:'')+'</td>';			
							html += '<td class="startgrade"><img src="${ctx}/image/starGrade/grade'+(item.recommendlevel!=undefined?item.recommendlevel:'0')+'.png" alt=""></td>';			
							html += '<td>'+(item.downloadnum!=undefined?item.downloadnum:'')+'</td>';			
							html += '<td>';
							html += '<button class="btn_ov auto edit" onclick="viewModify(\''+item.id+'\',\''+item.name+'\',\''+item.url+'\',\''+item.recommendlevel+'\',\''+item.downloadnum+'\',\''+item.orderno+'\')">edit</button>';
							html += '<button class="btn_ov auto delete" onclick="delGameNav(\''+item.id+'\',\''+item.orderno+'\')">delete</button>';
							html += '</td>';			
							html += '</tr>';			
						});
						$(".table_content").remove();
						if(html == ''){
							html += '<tr class="table_content" id="empty"><td colspan="6">Query list is empty!</td></tr>';
							$(".page_nav").hide();
						}else{
							$(".page_nav").show();
						}					
						$("#listTable").append(html);
						
						pagination(page);					
					}catch(e){
						var html = '<tr class="table_content" id="empty"><td colspan="7">Query list is empty!</td></tr>';
						$(".table_content").remove();
						$("#listTable").append(html);
					}				
				}
			});			
		}
		
		
		function viewModify(id,name,url,recommendlevel,downloadnum,orderno){
	   	    if($('#i_img').length==0){
	   	        $('#i_label').prev().replaceWith('<span style="display:inline-block;width:100px"><img id="i_img" style="width:100px"></span>');
			}		
			$("#modify_form :hidden").val("");
        	$("#modify_form .recommendLevel .star").removeClass("active");
        	$("#modify_form")[0].reset();
        	$("#modify_form [name='type']").val($("#deviceChoice .hover").attr("value"));
        	$("#modify_form [name='name']").val(name);
        	$("#modify_form [name='id']").val(id);
        	$("#modify_form [name='url']").val(url);
        	$("#modify_form [name='downloadnum']").val(downloadnum);
        	$("#modify_form [name='orderno']").val(orderno);
        	$("#modify_form .recommendLevel .star"+recommendlevel).addClass("active");
        	$("#modify_form [name='recommendlevel']").val($("#modify_form .recommendLevel .star"+recommendlevel).attr("value"));
        	$('#i_img').attr('src','${ctx}/navContent/readIcon?id=' + id+'&t='+Math.random());
        	$("#add").hide();
        	$("#edit").show();        	
            $(".mask_layout").show();        				
		}


		function delGameNav(id,orderNo){
			if(window.confirm("Are you sure you want to delete the selected data?")){
				$.ajax({
					type: "POST",
					cache:false,
					url: "${ctx}/navContent/delete",
					data: {
						"id":id,
						"orderCount":$("#totalRowNum").text(),
						"orderNo":orderNo
						},
					dataType: "json",
					success: function(data) {
						if(data!=null){
							var success = data.success;
							var msg = data.msg;
							var data = data.data;
							if(success){
								var type = $("#deviceChoice li a.hover").attr("value");
								list(type);
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
    </script>
</head>
<body class="nav_cont_set" style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search nav">
        <ul id="deviceChoice">
            <li><a href="#" class="hover" value="1">IOS</a></li>
            <li><a href="#" value="2">Android</a></li>
        </ul>

    </div>
 <table class="table bor" id="listTable">
     <tr><td colspan="7" style="text-align: left;"><button class="btn_defs1" id="addbanner">add</button></td></tr>
     <tr class="table_title">
         <td >sequence</td>
         <td >name</td>
         <td class="icon">icon</td>
         <td >link</td>
         <td >recommend level</td>
         <td >download count</td>
         <td >operation</td>

     </tr>
 </table>
<div class="page_nav">
    <div class="fl">
        <select name="rowsPeerPage" onchange="changePageRows(this)">

        </select>
        pcs for each page&nbsp;&nbsp;&nbsp;total <span id="totalRowNum">0</span> pcs&nbsp;&nbsp;&nbsp;total <span id="totalPageNum"></span> pages
    </div>
<!--     <div class="checkall_wrap"> -->
<!--         <input class="checkall" id="checkall" type="checkbox"> <span>全选</span> -->
<!--     </div> -->
    <nav>
        <ul class="pagination">
        </ul>
    </nav>
</div>

<div class="mask_layout" style="display: none;">
    <div class="edit_mod" id="add" style="display: none;">
        <div class="title">Add <i class="icon iclose"></i></div>
        <div class="cont">
        	<form id="add_form">
            <ul>
                <li>name</li>
                <li><input type="text" name="name" data-validation-engine="validate[required,maxSize[20]]"></li>
                <li>icon</li>
                <li>
                <input type="text" data-validation-engine="validate[required]" readonly="readonly">
                <span class="btn_ov mid_btn search"  style="display:inline-block; position: absolute; text-align:center;width: 90px;margin-left: 12px;">upload <input type="file" class="iconUpload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span>
                </li>
<!--                 <li style="text-align: right">40*40</li> -->
                <li>url</li>
                <li><input type="text" name="url" data-validation-engine="validate[required,custom[url],maxSize[32]]"></li>
                <li>recommend level</li>
                <li>
                    <div class="recommendLevel">
                        <span class="star star1" value="1"></span>
                        <span class="star star2" value="2"></span>
                        <span class="star star3" value="3"></span>
                        <span class="star star4" value="4"></span>
                        <span class="star star5" value="5"></span>
                    </div>
                </li>
                <li>download count</li>
                <li><input type="text"  name="downloadnum" data-validation-engine="validate[required,custom[posInteger],maxSize[4]]"></li>
                <input type="hidden" value="" name="orderno" id="orderNo"/>
                <input type="hidden" value="" name="recommendlevel" id="recommendlevel"/>
                <input type="hidden" value="" name="type" id="type"/>
            </ul>
            </form>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" id="add_btn">save</button>
        </div>
    </div>
    
    <div class="edit_mod" id="edit" style="display: none;">
        <div class="title">Edit <i class="icon iclose"></i></div>
        <div class="cont">
        	<form id="modify_form">
            <ul>
                <li>name</li>
                <li><input type="text" name="name" data-validation-engine="validate[required,maxSize[20]]"></li>
                <li>icon</li>
                <li>
                <span style="display:inline-block;width:100px"><img id="i_img" style="width:100px"></span>
                <span id="i_label" class="btn_ov mid_btn search"  style="display:inline-block; position: absolute; text-align:center;width: 90px;margin-left: 12px;">upload <input type="file" class="icon_upload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span>
                </li>
<!--                 <li style="text-align: right">40*40</li> -->
                <li>url</li>
                <li><input type="text" name="url" data-validation-engine="validate[required,custom[url],maxSize[32]]"></li>
                <li>recommend level</li>
                <li>
                    <div class="recommendLevel">
                        <span class="star star1" value="1"></span>
                        <span class="star star2" value="2"></span>
                        <span class="star star3" value="3"></span>
                        <span class="star star4" value="4"></span>
                        <span class="star star5" value="5"></span>
                    </div>
                </li>
                <li>download count</li>
                <li><input type="text" name="downloadnum" data-validation-engine="validate[required,custom[posInteger]]"></li>
                <input type="hidden" value="" name="orderno" />
                <input type="hidden" value="" name="recommendlevel"/>
                <input type="hidden" value="" name="type"/>
                <input type="hidden" value="" name="id"/>
            </ul>
            </form>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel">cancel</button>
            <button class="btn_ov mid_btn search" id="modify_btn">save</button>
        </div>
    </div>    
</div>
<iframe name="upload" style="display:none"></iframe>
<script>
    $(function(){
        $("#addbanner").on("click",function () {
        	$("#add_form :hidden").val("");
        	$("#add_form .recommendLevel .star").removeClass("active");
        	
        	$("#add_form")[0].reset();
        	$("#add_form [name='orderno']").val(parseInt($("#totalRowNum").text())+1);
        	$("#add_form [name='type']").val($("#deviceChoice .hover").attr("value"));
        	$("#add").show();
        	$("#edit").hide();
            $(".mask_layout").show();
        });
        $(".radios").on("click",function () {
                $(".radios").removeClass("active");
                $(this).addClass("active");
        });
        $(".recommendLevel .star").on("click",function () {
            $(".recommendLevel .star").removeClass("active");
            $(this).addClass("active");
            $("#modify_form [name='recommendlevel']").val($(this).attr("value"));
            $("#add_form [name='recommendlevel']").val($(this).attr("value"));
        });

        $(".recommendLevel .star").on("mouseover",function () {
           $(this).addClass("hover");
        });
        $(".recommendLevel .star").on("mouseout",function () {
            $(this).removeClass("hover");
        });


		$(document).on('change','.iconUpload',function(){
   			var fileName = $(this).val();
   			var suffix = fileName.substr(fileName.lastIndexOf('.') + 1).toLowerCase();
   			if(!/^(png)$/.test(suffix)){
   				alert("Picture must be png");
   				$(this).val('');
   				return;
   			} 
   			$(this).parent().prev().val(fileName);
		});
		
        $(document).on('change','.icon_upload',function(){
			$(this).parent().prev().replaceWith('<input type="text" data-validation-engine="validate[required]" readonly="readonly"/>');
			var fileName = $(this).val();
   			var suffix = fileName.substr(fileName.lastIndexOf('.') + 1).toLowerCase();
   			//gif|jpg|jpeg|bmp|
   			if(!/^(png)$/.test(suffix)){
   				alert("Picture must be png");
   				$(this).val('');
   				return;
   			}   			
   			$(this).parent().prev().val(fileName);
		});		

    });

</script>
</body>
</html>
