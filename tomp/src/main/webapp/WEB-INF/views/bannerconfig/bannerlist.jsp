<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Title</title>
<%@ include file="../../include.jsp"%>
<link rel="stylesheet" href="${ctx}/css/base.css">
<script src="${ctx}/js/base.js"></script>
</head>
<body style="background-color: inherit;">
<form id="search_form">
<input type="hidden" name="pagetype" id="nav_id" value="${pagetype}"/>
<input type="hidden" name="orderno" value="1"/>
<input type="hidden" name="type" value="1"/>
<input type="hidden" name="turnurl" value="1"/>
</form>
	<div class="table_search nav">
		<ul>
			<li><a href="#" class="hover" id="nav1" onclick="changeNav(1);return false;">home</a>
			</li>
			<li><a href="#" id="nav2" onclick="changeNav(2);return false;">游戏下载与导航</a>
			</li>
			<li><a href="#"  id="nav3" onclick="changeNav(3);return false;">网站导航</a>
			</li>
			<li><a href="#" id="nav4" onclick="changeNav(4);return false;">其他（默认）</a>
			</li>
		</ul>

	</div>
	<table class="table bor">
		<tr>
			<td colspan="4" style="text-align: left;"><button
					class="btn_defs1" id="addbanner">add banner</button>
			</td>
		</tr>
		<tr class="table_title">
			<td>orderno</td>
			<td>turnurl</td>
			<td>objectdetail</td>
			<td>operator</td>
		</tr>
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="icon">
					<tr class="table_content">
						<td>${icon.orderno}</td>
						<td>${icon.turnurl}</td>
						<td><img
							src="${ctx}/bannerconfig/readBannerconfig?id=${icon.id}"
							style="width:50px">
						</td>
						<td><input type="hidden" value="${icon.id}"> 
						<span class="sbtn btn_ov Modify"
							onclick="toShow('${icon.id}','${icon.orderno}','${icon.turnurl}','${icon.type}')">Modify</span>
						<span class="sbtn btn_ov Delete">Delete</span>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<td colspan="8">Query list is empty!</td>
			</c:otherwise>
		</c:choose>
	</table>
<!-- 	<div id="add_icon" class="mask_layout addUser" style="display: none;"> -->
<!-- 		<div class="role_selection_wrap"> -->
<!-- 			<div class="top"> -->
<!-- 				Add Icon <i class="icon iclose">X</i> -->
<!-- 			</div> -->
<!-- 			<div class="cont"> -->
<!-- 				<form id="add_form"> -->
<!-- 				<input type="hidden" name="pagetype" value="${pagetype}"/> -->
<!-- 					<ul> -->
<!-- 						<li>Banner</li> -->
<!-- 						<li>显示顺序</li> -->
<!-- 						<li><input type="number" name="orderno"/> -->
<!-- 						</li> -->
<!-- 						<li>banner关联对象 -->
<!-- 						<input type="hidden" name="type"/> -->
<!-- 							<div class="radio_s1"> -->
<!-- 								<button class="radios  btn_defs1 active">inner</button> -->
<!-- 								<button class="radios  right btn_defs1">out</button> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 						<li>超链接地址</li> -->
<!-- 						<li><input type="text" name="turnurl"/> -->
<!-- 						</li> -->
<!-- 						<li>上传banner图片</li> -->
<!-- 						<li><input type="file" name="file"/> -->
<!-- 						</li> -->
<!-- 						<li style="text-align: right">540*157 1:0.29</li> -->
<!-- 					</ul> -->
<!-- 					<button id="add_btn" type="button" class="btn_ov apply">Add</button> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div id="modify_icon" class="mask_layout addUser" -->
<!-- 		style="display: none;"> -->
<!-- 		<div class="role_selection_wrap"> -->
<!-- 			<div class="top"> -->
<!-- 				Modify Icon <i class="icon iclose">X</i> -->
<!-- 			</div> -->
<!-- 			<div class="cont"> -->
<!-- 				<form id="modify_form"> -->
<!-- 				<input type="hidden" name="pagetype" id="pagetypem" value="${pagetype}"/> -->
<!-- 				<input type="hidden" name="id" id="idm"/> -->
<!-- 					<ul> -->
<!-- 						<li>Banner</li> -->
<!-- 						<li>显示顺序</li> -->
<!-- 						<li><input type="number" name="orderno" id="ordernom"/> -->
<!-- 						</li> -->
<!-- 						<li>banner关联对象 -->
<!-- 						<input type="hidden" name="type" id="typem"/> -->
<!-- 							<div class="radio_s1"> -->
<!-- 								<button class="radios  btn_defs1 active">inner</button> -->
<!-- 								<button class="radios  right btn_defs1">out</button> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 						<li>超链接地址</li> -->
<!-- 						<li><input type="text" name="turnurl" id="turnurlm"/> -->
<!-- 						</li> -->
<!-- 						<li>上传banner图片</li> -->
<!-- 						<li><input type="file" name="file"/> -->
<!-- 						</li> -->
<!-- 						<li style="text-align: right">540*157 1:0.29</li> -->
<!-- 					</ul> -->
<!-- 					<button id="modify_btn" type="button" class="btn_ov apply">Modify</button> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<div class="mask_layout" style="display: none;" id="add_icon">
    <div class="edit_mod" id="edit" style="display: block;">
        <div class="title">Add<i class="icon iclose"></i></div>
        <div class="cont">
            <form id="add_form">
				<input type="hidden" name="pagetype" value="${pagetype}"/>
					<ul>
						<li>Banner配置</li>
						<li>显示顺序</li>
						<li><input type="number" name="orderno"/>
						</li>
						<li>banner关联对象
						<input type="hidden" name="type" id="typeadd" value="1"/>
							<div class="radio_s1">
								<button type="button" id="btn1" class="radios  btn_defs1 active" onclick="changeInnerOrOut(1);">inner</button>
								<button type="button" id="btn2" class="radios  right btn_defs1" onclick="changeInnerOrOut(2);">out</button>
							</div>
						</li>
						<li>超链接地址</li>
						<li><input type="text" name="turnurl"/>
						</li>
						<li>上传banner图片</li>
						<li><input type="file" name="file" id="iconUpload"/>
						</li>
						<li style="text-align: right">540*157 1:0.29</li>
					</ul>
				</form>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel1">cancel</button>
            <button class="btn_ov mid_btn search" id="add_btn">save</button>
        </div>
    </div>
</div>
	<div id="modify_icon" class="mask_layout" style="display: none;">
    <div class="edit_mod" id="edit" style="display: block;">
        <div class="title">Modify<i class="icon iclose"></i></div>
        <div class="cont">
            <form id="modify_form">
				<input type="hidden" name="pagetype" id="pagetypem" value="${pagetype}"/>
				<input type="hidden" name="id" id="idm"/>
					<ul>
						<li>Banner配置</li>
						<li>显示顺序</li>
						<li><input type="number" name="orderno" id="ordernom"/>
						</li>
						<li>banner关联对象
						<input type="hidden" name="type" id="typem" value="1"/>
							<div class="radio_s1">
								<button type="button" id="btn3" class="radios  btn_defs1 active" onclick="changeInnerOrOut(3);">inner</button>
								<button type="button" id="btn4" class="radios  right btn_defs1" onclick="changeInnerOrOut(4);">out</button>
							</div>
						</li>
						<li>超链接地址</li>
						<li><input type="text" name="turnurl" id="turnurlm"/>
						</li>
						<li>上传banner图片</li>
						<li><input type="file" name="file" id="icon_upload"/>
						</li>
						<li style="text-align: right">540*157 1:0.29</li>
					</ul>
				</form>
        </div>
        <div class="floor">
            <button class="btn_ov mid_btn search cancel2">cancel</button>
            <button class="btn_ov mid_btn search" id="modify_btn">save</button>
        </div>
    </div>
</div>
	<iframe name="upload" style="display:none"></iframe>
	<jsp:include page="../common/footer.jsp" />
</body>
<script>
	var flag = true, flag1 = true;
	$(function() {
	    if($("#nav_id").val()=='1'){
	      $("#nav1").addClass("hover");
	      $("#nav2").removeClass("hover");
	      $("#nav3").removeClass("hover");
	      $("#nav4").removeClass("hover");
	    }
	    else if($("#nav_id").val()=='2'){
	      $("#nav1").removeClass("hover");
	      $("#nav2").addClass("hover");
	      $("#nav3").removeClass("hover");
	      $("#nav4").removeClass("hover");
	    }
	    else if($("#nav_id").val()=='3'){
	      $("#nav1").removeClass("hover");
	      $("#nav2").removeClass("hover");
	      $("#nav3").addClass("hover");
	      $("#nav4").removeClass("hover");
	    }
	    else if($("#nav_id").val()=='4'){
	      $("#nav1").removeClass("hover");
	      $("#nav2").removeClass("hover");
	      $("#nav3").removeClass("hover");
	      $("#nav4").addClass("hover");
	    }
		$('#add_form,#modify_form').validationEngine('attach', {
			promptPosition : "topRight",
			maxErrorsPerField : 1
		});
		$('#addbanner').on('click', function() {
			$('#add_icon').slideDown();
		});
		$(document).on('change','#iconUpload',
						function() {
							var fileName = $(this).val();
							var suffix = fileName.substr(
									fileName.lastIndexOf('.') + 1)
									.toLowerCase();
							if (!/^(gif|jpg|jpeg|bmp|png)$/.test(suffix)) {
								alert("Picture must be a type of gif,jpg,jpeg,bmp,png");
								$(this).val('');
								return;
							}
						});
		$(document).on('change','#icon_upload',
						function() {
							var fileName = $(this).val();
							var suffix = fileName.substr(fileName.lastIndexOf('.') + 1).toLowerCase();
							if (!/^(gif|jpg|jpeg|bmp|png)$/.test(suffix)) {
								alert("Picture must be a type of gif,jpg,jpeg,bmp,png");
								$(this).val('');
								return;
							}
						});
		$('#add_btn').on('click', function() {
			if ($('#add_form').validationEngine('validate') && flag) {
				flag = false;
				$('#add_form').attr({
					action : "${ctx}/bannerconfig/bannerconfigUpload",
					method : "post",
					enctype : "multipart/form-data",
					target : "upload"
				}).submit();
			}
		});
		$('.table_content .Delete').on('click',
						function() {
							if (confirm("Are you sure you want to delete the selected data?")) {
								var array = [];
							    array.push($(this).prev().prev().val());
								$.ajax({
									url : ctx + '/bannerconfig/delBannerconfig',
									type : 'post',
									data : {
										array : array
									},
									traditional : true,
									dataType : 'json',
									success : function(data) {
										if (1 == data.retCode) {
											nextPage(1);
										} else {
											alert('Delete bannerconfig failure!');
										}
									},
									error : function() {
										alert('server error!');
									}
								});
							}
						});
		$('#modify_btn').on('click', function() {
			if ($('#add_form').validationEngine('validate') && flag1) {
				flag1 = false;
				$('#modify_form').attr({
					action : "${ctx}/bannerconfig/updateBannerconfig",
					method : "post",
					enctype : "multipart/form-data",
					target : "upload"
				}).submit();
			}
		});
	});

	function toShow(id, orderno, turnurl, type) {
	    $('#idm').val(id);
	    $('#ordernom').val(orderno);
	    $('#turnurlm').val(turnurl);
	    $('#typem').val(type);
		$('#modify_icon').slideDown();
	}
	function callback(map) {
		if (map.retCode) {
			alert("Upload Successfully!");
			window.location.href = "${ctx}/bannerconfig/showBannerconfigList?"
				+ $('#add_form').serialize() + "&currentPage=1" + "&showCount=" + $('#currentPageRow').val()+"&number="+Math.random();
		} else {
			alert("Upload Unsuccessfully!");
			flag = true;
			flag1 = true;
		}
	}
	//显示下一页
	function nextPage(currentPage) {
		window.location.href = "${ctx}/bannerconfig/showBannerconfigList?"
				+ $('#search_form').serialize() + "&currentPage=" +currentPage+ "&showCount=" + $('#currentPageRow').val()+"&number="+Math.random();
	}
	//改变显示行数
	function changePageRows(value) {
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	function changeNav(navid){
	   $("#nav_id").val(navid);
	   $(this).addClass("hover").siblings().removeClass("hover");
	   nextPage(1);
	}
	
	function changeInnerOrOut(flag){
// 	      $(this).addClass("active").siblings().removeClass("active");
// 	      $(this).siblings().css('background-color', 'red');
	      if(1==flag){
	      $("#btn1").addClass("active");
	      $("#btn2").removeClass("active");
	      $("#typeadd").val(1);
	      }
	      else if(2==flag){
	      $("#btn2").addClass("active");
	      $("#btn1").removeClass("active");
	       $("#typeadd").val(2);
	      }
	      else if(3==flag){
	      $("#btn3").addClass("active");
	      $("#btn4").removeClass("active");
	       $("#typem").val(1);
	      }
	      else if(4==flag){
	      $("#btn4").addClass("active");
	      $("#btn3").removeClass("active");
	       $("#typem").val(2);
	      }
	   
	}
	
	$('.floor .cancel1').on('click',
						function() {
						$('#add_icon').slideUp();
    });
	$('.floor .cancel2').on('click',
						function() {
						$('#modify_icon').slideUp();
    });
</script>
</html>
