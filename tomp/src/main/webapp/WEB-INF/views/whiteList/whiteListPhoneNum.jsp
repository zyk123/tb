<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Title</title>
		<%@ include file="../../include.jsp" %>	    
	    <link rel="stylesheet" href="<%=path %>/css/base.css">
	    <script src="<%=path %>/js/base.js"></script>	  
		<script type="text/javascript">
			//显示下一页
			function nextPage(currentPage){
				window.location.href="${ctx}/hyWhiteList/select?" + $('#search_form').serialize()
				+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
			}
			//改变显示行数
			function changePageRows(value){
				$('#currentPageRow').val(value);
				nextPage(1);
			}
			//上传回调
			function callback(map){
				$('#fileImport').val('');
				var retCode = map.retCode;
				if(0 == retCode){
					alert("Import success!");
					nextPage(1);
				}else if( 1 == retCode){
					alert("Import success!,\nBut some data was filtered because the format is wrong,please view the log");
					nextPage(1);
				}else if(-1 == retCode){
					alert("File can not be empty!");
				}else if(-2 == retCode){
					alert("File parse failed!");
				}else if(-3 == retCode){
					alert("Import failure!");
				}
			}
			$(document).ready(function(){
				$('#search_form').validationEngine('attach',{
					promptPosition: 'bottomRight'
				});		
				//文件上传
				$('#fileImport').change(function(){
					var fileName = $(this).val();
					var index = fileName.lastIndexOf('.');
					var suffix = fileName.substr(index + 1).toLowerCase();
					if('csv' != suffix){
						alert('File is not a CSV format!');
						$(this).val('');
						return;
					} 
					$('#search_form').attr({
						action:'<%=path %>/hyWhiteList/import',
						method:'post',
						enctype:'multipart/form-data',
						target:'upload_frame'
					}).submit();
				});
				//删除白名单
				$('#del').click(function(){
					if(0 == $('.table_content input:checked').size()){
						alert('Please select at least one record!');
						return;
					}
					if(confirm("Are you sure you want to delete the selected data?")){
						var array = [];
						$('.table_content input:checked').each(function(){
							array.push($(this).next().text());
						});
						$.ajax({
							url:'${ctx}/hyWhiteList/delHyWhiteList',
							type:'post',
							data:{ids:array},
							traditional:true,
							dataType:'json',
							success:function(data){						
								if(0 == data.retCode){
									nextPage('${page.currentPage}');
								}else{
									alert('Delete failure!');
								}
							},
							error:function(a,b,c){
								alert('Delete failure!');
							}
						});	
					}					
				});
				//搜索
				$('#search').click(function(){
					var flag = $('#search_form').validationEngine('validate');
					if(!flag){
						return;
					}
					nextPage(1);
				});
			});
		</script>
	</head>
	<body style="background-color: inherit;">
		<input type="text" id="htmlIdentifier" value="add" style="display: none;" />
		 <c:set var="t"  value=""/>
		 <c:forEach begin="1" end="${empty userModelSession.mobileLength ? 5 : userModelSession.mobileLength - 5}">
		 	<c:set var="t" value="${t}${'x'}" />
		 </c:forEach>
			<form id="search_form">
				<div class="table_search">
			       <ul>
			           <li>phone number <input id ="mobileno" name="mobileno" class="validate[custom[posInteger],maxSize[12]] phone" type="text" value="${param.mobileno}"></li>
			           <li>from <input readonly="readonly" id="startTime" class="num Wdate" name="startTime" type="text" value="${param.startTime}" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',lang:'en'})"></li>
			           <li>to <input readonly="readonly" id="endTime" class="num Wdate" name="endTime" type="text" value="${param.endTime}"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',lang:'en'})"></li>
			           <li>batch No <input id="batchno" name="batchno" class="validate[custom[lengthNumber]] num" type="text" value="${param.batchno}"></li>
			           <li><button type="button" id="search" class="btn_ov mid_btn search">query</button></li>
			           <li><button type="button" id="del" class="btn_ov mid2_btn delete">delete</button></li>
            		   <li>
	            		   <span class="btn_ov mid2_btn import"  style="position: relative; text-align:center;">import <input type="file" id="fileImport" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></span>
				       </li>
			        </ul>
			        <div class="import_tip"><p><i>*</i>The style of phone num you import is:01936${t}</p></div>
			   </div>
			</form>		   
	   	<table class="table bor">
		     <tr class="table_title">
		         <td><input id="checkall" type="checkbox" style="position:absolute; left:30px;">phone number</td>
		         <td>creation time</td>
		         <td>batch No</td>
		     </tr>
		     <c:choose>
   				<c:when test="${page.totalCount gt 0 }">
				    <c:forEach items="${list}" var="hwl">
				    	<tr class="table_content">
					         <td><input type="checkbox"><span style="display:none">${hwl.wlistid}</span> <span>${hwl.mobileno}</span> </td>
					         <td><fmt:formatDate value='${hwl.adddate}' pattern="yyyy-MM-dd"/></td>
					         <td>${hwl.batchno}</td>
				     	</tr>
				     </c:forEach>		
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="3">Query list is empty!</td>
					</tr>
				</c:otherwise>
			</c:choose>		
		</table>
		<jsp:include page="../common/footer.jsp"/>
   		<iframe style="display:none;" name="upload_frame"></iframe>
	</body>
</html>