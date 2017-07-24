<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
	<%@ include file="../../include.jsp"  %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$('#search_form').validationEngine('attach',{
				promptPosition: 'bottomRight'
			});	
    		//提交表单查询
    		$('#search_btn').click(function(){
    			var flag = $('#search_form').validationEngine('validate');
				if(!flag){
					return;
				}
    			nextPage(1);
    		});  
    		//上传文件
    		$('#fileImport').change(function(){
    			var fileName = $(this).val();
    			var suffix = fileName.substr(fileName.lastIndexOf('.') + 1).toLowerCase();
    			if('csv' != suffix){
    				alert("File is not a CSV format!");
    				$(this).val('');
    				return;
    			}
    			$('#search_form').attr({
    				action:'${ctx}/hyWhiteSection/upload',
    				method:'post',
    				enctype:'multipart/form-data',
    				target:'upload_frame'
    			}).submit();
    		}); 
    		
		//删除白名单号段
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
					url:'${ctx}/hyWhiteSection/delHyWhiteSection',
					type:'post',
					data:{ids:array},
					traditional:true,
					dataType:'json',
					success:function(data){						
						if(0 == data.retCode){
							nextPage('${page.currentPage}');
						}else{
							alert('DeLete failure!');
						}
					},
					error:function(a,b,c){
						alert('DeLete failure!');
					}
				});	
			}					
		});	
    	});    	 
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
		//显示下一页
		function nextPage(currentPage){
			window.location.href="${ctx}/hyWhiteSection/select?" + $('#search_form').serialize()
			+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
		}
		//改变显示行数
		function changePageRows(value){
			$('#currentPageRow').val(value);
			nextPage(1);
		}
    </script>
</head>
<body style="background-color: inherit;">
	<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
	 <c:set var="t"  value=""/>
	 <c:forEach begin="1" end="${empty userModelSession.mobileLength ? 5 : userModelSession.mobileLength - 5}">
	 	<c:set var="t" value="${t}${'x'}" />
	 </c:forEach>
    <form id="search_form">
	    <div class="table_search">
	        <ul>
	            <li>phone number <input id="mobileNo" name="mobileNo" value="${param.mobileNo}" class="validate[custom[posInteger],maxSize[12]] phone" type="text"></li>
	            <li>from <input readonly="readonly" value="${param.startTime}" class="num Wdate" name="startTime" id="startNum" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endNum\')||\'%y-%M-%d\'}',lang:'en'})"></li>
	            <li>to <input readonly="readonly" value="${param.endTime}" class="num Wdate" name="endTime" id="endNum" type="text"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startNum\')}',maxDate:'%y-%M-%d',lang:'en'})"></li>
	            <li>batch No <input id="batchNo" name="batchNo" value="${param.batchNo}" class="validate[custom[lengthNumber]] num" type="text"></li>
	            <li><button id="search_btn" type="button" class="btn_ov mid_btn search">query</button></li>
	        	<li><button id="del" type="button" class="btn_ov mid2_btn delete">delete</button></li>
            	<li><span class="btn_ov mid2_btn import"  style="position: relative; text-align: center;">import <input type="file" id="fileImport" name="file" style="opacity:0; width: 100%; height: 100%; cursor: pointer; top:0; left: 0; position: absolute;" value="选择文件" /></button></span>
	        </ul>
	        <div class="import_tip"><p><i>*</i>The style of segment you import is:01936${t} - 01937${t}</p></div>
	    </div>    	
    </form>
	<table class="table bor">
	    <tr class="table_title">
	        <td><input style="position:absolute; left:30px;" id="checkall" type="checkbox">start number</td>
	        <td>end number</td>
	        <td>creation time</td>
	        <td>batch No</td>
	    </tr>
	    <c:choose>
	    	<c:when test="${not empty list}">
	    		<c:forEach items="${list}" var="section">
	    			<tr class="table_content">
			         <td class="phone_start"><input type="checkbox"><span style="display:none">${section.wsectionid}</span> <span>${section.mobilenostart }</span> </td>
			         <td class="phone_end">${section.mobilenoend }</td>
			         <td><fmt:formatDate value="${section.adddate }" pattern="yyyy-MM-dd"/></td>
			         <td>${section.batchNo}</td>
			     </tr>
	    		</c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan="4">Query list is empty！</td>
	    	</c:otherwise>
	    </c:choose>
	</table>
	<jsp:include page="../common/footer.jsp" />
	<iframe name="upload_frame" style="display:none;"></iframe>
</body>
</html>