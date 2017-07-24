<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Title</title>
<%@ include file="/WEB-INF/include.jsp"%>
<link rel="stylesheet" href="<%=path%>/css/base.css">
<script src="<%=path%>/js/base.js"></script>
<script src="${ctx}/js/promotion.management.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/demo.css">
<style type="text/css">
.table.bor td{
    line-height: 2.2;
    
}
</style>
<script type="text/javascript"
	src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: inherit;" class="table_body">
	<div class="table_search">
		<form id="search_form">
			<ul>
				<li>Promotion Name <input name="bean.promotionname" class="num"
					type="text">
				</li>
				<li><button id="search_btn" type="button"
						class="btn_ov mid_btn search">Research</button>
				</li>
				<li><button id="add" type="button"
						class="btn_ov mid2_btn import">Add</button>
				</li>
			</ul>
		</form>
	</div>
	<div id="add_promotion" class="mask_layout addUser"
		style="display: none;">
		<div class="role_selection_wrap">
			<div class="top">
				Add Promotion <i class="icon iclose">X</i>
			</div>
			<div class="cont">
				<form id="add_form">
					<ul class="clearfixa add_ul">
						<li><label for=""> Promotion name</label><input
							name="bean.promotionname" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Promotion describe</label><input
							name="bean.promotiondesc" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Prize describe</label><input
							name="bean.prizedesc" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Promotion begintime</label><input
							class="num Wdate" name="bean.startdate" id="startTime"
							type="text"
							onFocus="WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}',readOnly:true,lang:'en'})">
						</li>
						<li><label for=""> Promotion endtime</label><input
							class="num Wdate" name="bean.enddate" id="endTime" type="text"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
						</li>
						<li><label for=""> Daily limit</label><input
							name="bean.onedaytimes" type="number"
							data-validation-engine="validate[required,min[1]]">
						</li>
					</ul>
				</form>
				<button id="add_btn" type="button" class="btn_ov apply">Add</button>
			</div>
		</div>
	</div>
	<div id="modify_promotion" class="mask_layout addUser"
		style="display: none;">
		<div class="role_selection_wrap">
			<div class="top">
				Modify Promotion <i class="icon iclose">X</i>
			</div>
			<div class="cont">
				<form id="modify_form">
					<ul class="clearfixa add_ul">
						<li><label for=""> Promotion name</label><input
							name="bean.promotionname" id="promotionnameM" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Promotion describe</label><input
							name="bean.promotiondesc" id="promotiondescM" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Prize describe</label><input
							name="bean.prizedesc" id="prizedescM" type="text"
							data-validation-engine="validate[required]">
						</li>
						<li><label for=""> Promotion begintime</label><input
							class="num Wdate" name="bean.startdate" id="startTimeM"
							type="text"
							onFocus="WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endTimeM\')}',readOnly:true,lang:'en'})">
						</li>
						<li><label for=""> Promotion endtime</label><input
							class="num Wdate" name="bean.enddate" id="endTimeM" type="text"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTimeM\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
						</li>
						<li><label for=""> Daily limit</label><input
							name="bean.onedaytimes" id="onedaytimesM" type="number"
							data-validation-engine="validate[required,min[1]]">
						</li>
					</ul>
					<input id="curr_id" name="bean.promotionid" type="hidden">
				</form>
				<button id="modify_btn" type="button" class="btn_ov apply">Modify</button>
			</div>
		</div>
	</div>
	<div id="jstable" class="mask_layout addUser" style="display: none;">
		<div class="role_selection_wrap">
			<div class="top">
				Modify Promotion <i class="icon iclose">X</i>
			</div>
			<div class="cont">
<!-- 				<table id="dg" class="easyui-datagrid" -->
<!-- 					title="Row Editing in DataGrid" style="width:502px;height:auto" -->
<!-- 					data-options="iconCls: 'icon-edit',singleSelect: true,toolbar: '#tb', -->
<!-- 					url: '${ctx}/promotionlist/getDataGrid', -->
<!-- 					method: 'get', -->
<!-- 					onClickCell: onClickCell, -->
<!-- 					onEndEdit: onEndEdit -->
<!-- 				"> -->
<!-- 					<thead> -->
<!-- 						<tr> -->
<!-- 							<th -->
<!-- 								data-options="field:'bean.prizeid',width:100, -->
<!-- 								formatter:function(value,row){ -->
<!-- 							return row.prizename; -->
<!-- 						}, -->
<!-- 						editor:{ -->
<!-- 							type:'combobox', -->
<!-- 							options:{ -->
<!-- 								valueField:'bean.prizeid', -->
<!-- 								textField:'prizename', -->
<!-- 								method:'get', -->
<!-- 								url:'${ctx}/promotionlist/getPrizeInfo', -->
<!-- 								required:true, -->
<!-- 								onSelect: function(rec){ -->
<!-- 								var url = 'get_data2.php?id='+rec.id; -->
								
<!-- 								var row = $('#dg').datagrid('getSelected'); -->
<!-- 								var rowIndex = $('#dg').datagrid('getRowIndex',row); -->
								
<!--                                 var ptotalnum = $('#dg').datagrid('getEditor', {'index':rowIndex,'field':'ptotalnum'}).target; -->
<!--                                 ptotalnum.numberbox('setValue', '23'); -->
                                
<!--                                 var prestnum = $('#dg').datagrid('getEditor', {'index':rowIndex,'field':'prestnum'}).target; -->
<!--                                 prestnum.numberbox('setValue', '24'); -->
                                
<!--                                 var prizelevel = $('#dg').datagrid('getEditor', {'index':rowIndex,'field':'bean.prizelevel'}).target; -->
<!--                                 prizelevel.numberbox('setValue', '25'); -->
								
<!--                                 var prizetotalnum = $('#dg').datagrid('getEditor', {'index':rowIndex,'field':'bean.prizetotalnum'}).target; -->
<!--                                 prizetotalnum.numberbox('setValue', '26'); -->
                                
<!--                                 var probability = $('#dg').datagrid('getEditor', {'index':rowIndex,'field':'bean.probability'}).target; -->
<!--                                 probability.numberbox('setValue', '27'); -->
<!-- 								} -->
<!-- 							} -->
<!-- 						}">prizename</th> -->
<!-- 							<th -->
<!-- 								data-options="field:'ptotalnum',width:80,align:'right',editor:'numberbox'">prizetotalnum</th> -->
<!-- 							<th -->
<!-- 								data-options="field:'prestnum',width:80,align:'right',editor:'numberbox'">prizerestnum</th> -->
<!-- 							<th -->
<!-- 								data-options="field:'bean.prizelevel',width:80,align:'right',editor:'numberbox'">prizelevel</th> -->
<!-- 							<th -->
<!-- 								data-options="field:'bean.prizetotalnum',width:80,align:'right',editor:'numberbox'">assign</th> -->
<!-- 							<th -->
<!-- 								data-options="field:'bean.probability',width:80,align:'right',editor:'numberbox'">probability</th> -->
<!-- 						</tr> -->
<!-- 					</thead> -->
<!-- 				</table> -->

<!-- 				<div id="tb" style="height:auto"> -->
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" -->
<!-- 						data-options="iconCls:'icon-add',plain:true" onclick="append()">Append</a> -->
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" -->
<!-- 						data-options="iconCls:'icon-remove',plain:true" -->
<!-- 						onclick="removeit()">Remove</a> <a href="javascript:void(0)" -->
<!-- 						class="easyui-linkbutton" -->
<!-- 						data-options="iconCls:'icon-save',plain:true" onclick="accept()">Accept</a> -->
<!-- 				</div> -->
                 <div id="dg"></div>
				<input id="promotionid" name="promotionid" type="hidden" />
				<button id="save_btn" type="button" class="btn_ov apply">Save</button>
			</div>
		</div>
	</div>
	<table class="table bor">
		<tr class="table_title">
			<td><input type="checkbox" id="checkall"><span
				style="margin-left:38px;">Promotion name</span>
			</td>
			<td>Promotion describe</td>
			<td>Prize describe</td>
			<td>Promotion status</td>
<!-- 			<td>Begin time</td> -->
<!-- 			<td>End time</td> -->
			<td>Daily limit</td>
			<td>Operate</td>
		</tr>
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="promotion">
					<tr class="table_content">
						<td><input type="checkbox"> <span
							style="margin-left:38px;">${promotion.bean.promotionname}</span>
						</td>
						<td>${promotion.bean.promotiondesc}</td>
						<td>${promotion.bean.prizedesc}</td>
						<td>${promotion.bean.status}</td>
<!-- 						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${promotion.bean.startdate}" /></td> -->
<!-- 						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${promotion.bean.enddate}" /></td> -->
						<td>${promotion.bean.onedaytimes}</td>
						<td><input type="hidden" id="promotionidS"
							value="${promotion.bean.promotionid}" />
							<input type="hidden" id="statusS"
							value="${promotion.bean.status}" /> <span
							class="sbtn btn_ov Modify">Modify</span> <span
							class="sbtn btn_ov Delete">Delete</span> <span
							class="sbtn btn_ov Shift" id="shiftmy">
							<c:choose>
				        		<c:when test="${promotion.bean.status eq 0}">Shift</c:when>
				        		<c:when test="${promotion.bean.status eq 1}">UnShift</c:when>
				        	</c:choose>
				        	</span> <span
							class="sbtn btn_ov Relation">Relation</span></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<td colspan="7">Query list is empty!</td>
			</c:otherwise>
		</c:choose>
	</table>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
