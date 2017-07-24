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
	        <ul>
	            <li><button id="add" type="button" class="btn_ov mid2_btn import">Add</button></li>
	            <li><button id="del_btn" type="button" class="btn_ov mid2_btn delete">Delete</button></li>
				<li><button onclick="sync()" type="button" class="btn_ov mid2_btn import">Sync</button></li>
	        </ul>
    </div>    	
    <div id="add_icon" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Add Icon  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="add_form">
		            <ul class="clearfixa add_ul">		                
		                <li>
		                	<label for=""> Icon</label><input type="text" data-validation-engine="validate[required]" readonly="readonly"/>
	            		   <span class="btn_ov mid2_btn import"  style="display:inline-block; position: absolute; text-align:center;">upload <input type="file" class="iconUpload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span>
				       </li>		                		                
		            	<li><label for="">Link Url</label><input name="linkurl" type="text" data-validation-engine="validate[required,maxSize[50]]"></li>
						<li><label for="">Type</label>
							<select name="type">
								<option value="1">预付费</option>
								<option value="2">后付费</option>
							</select>
						</li>
						<li><label for="">Sign</label>
		            	<select class="sign" name="sign" onchange="showType(this)">
		            		<option value="1">主球</option>
		            		<option value="2">流量球</option>
		            		<option value="3">活动球</option>
		            		<option value="4">安全球</option>
		            	</select>
		            	</li>
		            </ul>
	            	<button id ="add_btn" type="button" class="btn_ov apply">Add</button>
	            </form>
	        </div>	
	    </div>
	</div>
	<div id="modify_icon" class="mask_layout addUser" style="display: none;">
	    <div class="role_selection_wrap">
	        <div class="top">Modify Icon  <i class="icon iclose" >X</i></div>
	        <div class="cont">
	        	<form id="modify_form">
		            <ul class="clearfixa add_ul">		                
		                <li>
		                	<label id="i_label" for=""> Icon</label><span style="display:inline-block;width:200px"><img id="i_img" style="width:50px"></span>
	            		   <span class="btn_ov mid2_btn import"  style="display:inline-block; position: absolute; text-align:center;">upload <input type="file" class="icon_upload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span>
				       </li>             
		            	<li><label for="">Link Url</label><input id="linkurl" name="linkurl" type="text" data-validation-engine="validate[required,maxSize[50]]"></li>
						<li><label for="">Type</label>
							<select id="u_type" name="type" disabled="disabled">
								<option value="1">预付费</option>
								<option value="2">后付费</option>
							</select>
						</li>
		            	<li><label for="">Sign</label>
						<select id="sign" name="sign" disabled="disabled">
		            		<option value="1">主球</option>
		            		<option value="2">流量球</option>
		            		<option value="3">活动球</option>
		            		<option value="4">安全球</option>
		            	</select>
		            	</li>
		            </ul>   
		            <input id="icon_id" name="id" type="hidden">         	
	            	<button id ="modify_btn" type="button" class="btn_ov apply">Modify</button>
	            </form>
	        </div>	
	    </div>
	</div>
	<table class="table bor">
	    <tr class="table_title">
	        <td><input type="checkbox" id="checkall"><span style="margin-left:20px;">Icon</span></td>
	        <td>Link Url</td>
	        <td>Sign</td>
	        <td>Type</td>
	        <td>Percent</td>
	        <td>Mark</td>
	        <td>Popup</td>
	        <td>Edit</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">	    		
			    <c:forEach items="${list}" var="icon">
			    	<tr class="table_content">
				        <td><input type="checkbox"> <span style="margin-left:15px;"><img src="${ctx}/icon/readIcon?id=${icon.id}" style="width:50px"></span> </td>
				        <td>${icon.linkurl}</td>
				        <td>
				        	<c:choose>
				        		<c:when test="${icon.sign eq 1}">主球</c:when>
				        		<c:when test="${icon.sign eq 2}">流量球</c:when>
				        		<c:when test="${icon.sign eq 3}">活动球</c:when>
				        		<c:when test="${icon.sign eq 4}">安全球</c:when>
				        	</c:choose>
				        </td>
				        <td>
				        	<c:choose>
				        		<c:when test="${icon.type eq 1}">预付费</c:when>
				        		<c:when test="${icon.type eq 2}">后付费</c:when>
				        	</c:choose>
				        </td>
						<td>
							<c:if test="${not empty icon.percent}">${icon.percent}%</c:if>
						</td>
				        <td>
				        	<c:choose>
				        		<c:when test="${icon.mark eq 1}">大转盘</c:when>
				        		<c:when test="${icon.mark eq 2}">抓手机</c:when>
				        	</c:choose>
				        </td>
						<td><c:if test="${icon.sign eq 3}"><img src="${ctx}/icon/readPopup?id=${icon.id}" style="width:50px"></c:if> </td>
				        <td>
				        	<input type="hidden" value="${icon.id}">
				           <span class="sbtn btn_ov Modify" onclick="toShow('${icon.id}','${icon.linkurl}','${icon.sign}','${icon.type}','${icon.percent}','${icon.mark}')">Modify</span>
				        </td>
				    </tr>
			    </c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="8">Query list is empty!</td>
	    	</c:otherwise>
	    </c:choose>	    
	</table>
	<iframe name="upload" style="display:none"></iframe>
	<jsp:include page="../common/footer.jsp" />
</body>
<script>
    var flag=true,flag1 = true;
	$(function(){
    	$('#add_form,#modify_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
		$('#add').on('click',function(){
		    $('.sign').parent().nextAll().remove();
			$('#add_icon').slideDown();
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
		$('#add_btn').on('click',function(){
			if($('#add_form').validationEngine('validate') && flag){
				flag = false;
				$('#add_form').attr({
					action:"${ctx}/icon/iconUpload",
					method:"post",
					enctype:"multipart/form-data",
					target:"upload"
				}).submit();
			}			
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
                    url:ctx + '/icon/delIcon',
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
        $('#modify_btn').on('click',function(){
            if($('#add_form').validationEngine('validate') && flag1){
                flag1 = false;
                $('#modify_form').attr({
                    action:"${ctx}/icon/updateIcon",
                    method:"post",
                    enctype:"multipart/form-data",
                    target:"upload"
                }).submit();
            }
        });
        $('#search_btn').on('click',function(){
            nextPage(1);
        });
	});

   	function toShow(id,url,sign,type,percent,mark){
   	    if($('#i_img').length==0){
   	        $('#i_label').next().replaceWith('<span style="display:inline-block;width:200px"><img id="i_img" style="width:50px"></span>');
		}
   		$('#modify_icon').slideDown();
   		$('#i_img').attr('src','${ctx}/icon/readIcon?id=' + id);
   		$('#icon_id').val(id);
   		$('#linkurl').val(url);
   		$('#sign option').each(function(){
   			if($(this).val()==sign){
   				$(this).prop("selected",true);
   				if(sign == 1){
   					$(this).parents('li').nextAll().remove();
   					$('#u_type option').each(function(){
   						if($(this).val()==type){
   							$(this).prop("selected",true);
   						}
   					});
   				}else if(sign == 2){
   					$(this).parents('li').nextAll().remove();
   					if($('#u_percent').size()==0){
   						$(this).parents('li').after('<li><label for="">Percent</label>\
   						<select id=u_percent name="percent" disabled="disabled">\
   						<option value="0">0%</option>\
			   			<option value="10">10%</option>\
			   			<option value="20">20%</option>\
			   			<option value="30">30%</option>\
			   			<option value="40">40%</option>\
			   			<option value="50">50%</option>\
			   			<option value="60">60%</option>\
			   			<option value="70">70%</option>\
			   			<option value="80">80%</option>\
			   			<option value="90">90%</option>\
			   			<option value="100">100%</option>\
			   			</select>\
			   			</li>');
   					}
   					$('#u_percent option').each(function(){
   						if($(this).val()==percent){
   							$(this).prop("selected",true);
   						}
   					});
   				}else if(sign == 3){
   					$(this).parents('li').nextAll().remove();
   					if($('#u_mark').size()==0){
   						$(this).parents('li').after('<li><label for="">Mark</label>\
   						<select id=u_mark name="mark" disabled="disabled">\
   						<option value="1">大转盘</option>\
			   			<option value="2">抓手机</option>\
			   			</select>\
			   			</li>').after('<li><label for=""> Popup</label><span style="display:inline-block;width:200px"><img id="p_img" src="${ctx}/icon/readPopup?id=' + id + '" style="width:50px"></span><span class="btn_ov mid2_btn import"  style="display:inline-block; position: absolute; text-align:center;">upload <input type="file" class="icon_upload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span></li>');
   					}
   					$('#u_mark option').each(function(){
   						if($(this).val()==mark){
   							$(this).prop("selected",true);
   						}
   					});
   				}
   				else{
   					$(this).parents('li').nextAll().remove();
   				}
   				return false;
   			}
   		});
   	}
   	function callback(map){
   		if(map.retCode){  
   			alert("Upload Successfully!"); 		
   			nextPage(1);
   		}else{
   			alert("Upload Unsuccessfully!");
   			flag = true;
   			flag1 = true;
   		}
   	}
   	function showType(obj){
   		if(obj.value == 1){
   			$(obj).parent().nextAll().remove();
   		}else if(obj.value==2){
   			$(obj).parent().nextAll().remove();
   			$(obj).parent().after('<li><label for="">Percent</label>\
   			<select name="percent">\
   			<option value="0">0%</option>\
   			<option value="10">10%</option>\
   			<option value="20">20%</option>\
   			<option value="30">30%</option>\
   			<option value="40">40%</option>\
   			<option value="50">50%</option>\
			<option value="60">60%</option>\
			<option value="70">70%</option>\
			<option value="80">80%</option>\
			<option value="90">90%</option>\
			<option value="100">100%</option>\
   			</select>\
   			</li>');
   		}else if(obj.value==3){
   			$(obj).parent().nextAll().remove();
            $(obj).parent().after('<li><label for=""> Popup</label><input type="text" data-validation-engine="validate[required]" readonly="readonly"/><span class="btn_ov mid2_btn import"  style="display:inline-block; position: absolute; text-align:center;">upload <input type="file" class="iconUpload" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span></li>');
   			$(obj).parent().after('<li><label for="">Mark</label><select name="mark"><option value="1">大转盘</option><option value="2">抓手机</option></select></li>');
   		}
   		else{
   			$(obj).parent().nextAll().remove();
   		}
   	}   	
	 //显示下一页
	function nextPage(currentPage){
		window.location.href="${ctx}/icon/showIconList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}

	function sync() {
		$.ajax({
		    url:ctx+'/icon/syncIcon',
			type:'post',
			success:function(ret){
		        if(ret == "SUCCESS"){
		            alert("Sync Successful");
				}else{
		            alert("Sync Failed");
				}
			},
			error:function(){
			    alert("server error");
			}
		});
    }
</script>
</html>
