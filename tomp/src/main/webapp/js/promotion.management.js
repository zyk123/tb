var acceptFlag = '0';
var pprizetotalnum = '0';
$(function () {
    	var flag = true;
    	$('#search_form').validationEngine('attach', {promptPosition : "bottomRight"});
    	$('#add_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
    	$('#modify_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
    	// 搜索
    	$('#search_btn').on('click',function(){
    		if($('#search_form').validationEngine('validate')){
    			nextPage(1);
    		};
    	});
    	// 显示添加框
    	$('#add').on('click',function(){
    		flag = true;
    		$('#add_form').get(0).reset();
    		$('#add_form').validationEngine('hide');
    		$('#add_promotion').slideDown();
    	});
    	// 显示修改框
        $('.table_content .Modify').on("click",function () {
        	flag = true;
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/promotionlist/listById?promotionid=' + $(this).prev().prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#promotionnameM').val(map.promotion.promotionname);
        			$('#promotiondescM').val(map.promotion.promotiondesc);
        			$('#prizedescM').val(map.promotion.prizedesc);
        			$('#startTimeM').val(Format(new Date(map.promotion.startdate),"yyyy-MM-dd"));
        			$('#endTimeM').val(Format(new Date(map.promotion.enddate),"yyyy-MM-dd"));
        			$('#onedaytimesM').val(map.promotion.onedaytimes);
        			$('#curr_id').val(map.promotion.promotionid);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_promotion').slideDown();
        });
        
    	// 添加角色
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/promotionlist/insertPromotion',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/promotionlist/listPromotionInfo";
    					}else
    					{
    						alert('Add promotion failure');
    					}
    				},
    				error:function(){
    					alert('Add promotion failure');
    				}
    			});
    		}
    	});
    	
    	$('.table_content .Shift').on("click",function () {
    		    var status = $(this).prev().prev().prev().val();
    		    var modstatus;
    		    if(status=='0'){
    		    	modstatus = '1';
    		    }else{
    		    	modstatus = '0';
    		    }
    			$.ajax({
    				url:ctx + '/promotionlist/shiftPromotion',
    				method:'post',
    				data:{
    					'promotionid':$(this).prev().prev().prev().prev().val(),
    					'status':modstatus
    					},
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/promotionlist/listPromotionInfo";
    					}else
    					{
    						alert('Shift promotion failure');
    					}
    				},
    				error:function(){
    					alert('Shift promotion failure');
    				}
    			});
    	});
    	
    	// 修改角色
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/promotionlist/updatePromotion',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/promotionlist/listPromotionInfo";
    					}else
    					{
    						alert('Modify promotion failure');
    					}
    				},
    				error:function(){
    					alert('Modify promotion failure');
    				}
    			});
    		}
    	});

        // 删除角色
        $('.table_content .Delete').on('click',function(){
        	if(confirm("Are you sure you want to delete the selected data?")){
    			$.ajax({
    				url:ctx + '/promotionlist/deletePromotion',
    				type:'post',
    				data:{id:$(this).prev().prev().prev().val()},
    				traditional:true,
    				dataType:'json',
    				success:function(data){	
    					if(true == data.success){
    						nextPage(1);
    					}else{
    						alert(data.msg);
    					}
    				},
    				error:function(){
    					alert('Delete promotion failure!');
    				}
    			});	
    		}				
        });
        
        $('.table_content .Relation').click(function(){
        	acceptFlag = '0';
        	$('#jstable').slideDown();
        	$('#promotionid').val($(this).prev().prev().prev().prev().prev().val());
        	coustructDataGrid();
        	$('#dg').datagrid("unselectAll");
        });
        
        $('#save_btn').click(function(){
        	if(acceptFlag == '0'){
        		alert('please accept first!');
        		return;
        	}
        	var promotionid = $('#promotionid').val();
        	var relations = [];
//        	var _list={};
        	var rows = $('#dg').datagrid('getRows');
        	var returnFlag = 0;
        	var mySet = new Set();
        	relations.push({'prizeid':promotionid,  
                'prizename':promotionid,  
                'ptotalnum':promotionid,  
                'prestnum':promotionid,
                'prizelevel':promotionid,
                'prizetotalnum':promotionid,
                'probability':promotionid,
                'promotionid':promotionid,
                'scorestart':promotionid,
                'scoreend':promotionid,
                'orderno':promotionid,
                'iconurl':promotionid
                });
        	for (var i = 0; i < rows.length; i++) {
        	    var row = rows[i];            
//        	    _list["list[" + i + "].bean.prizeid"] = row["bean.prizeid"]; //这里list要和后台的参数名List<Category> list一样
//        	    _list["list[" + i + "].prizename"] = row["prizename"]; 
//        	    _list["list[" + i + "].ptotalnum"] = row["ptotalnum"]; 
//        	    _list["list[" + i + "].prestnum"] = row["prestnum"]; 
//        	    _list["list[" + i + "].bean.prizelevel"] = row["bean.prizelevel"]; 
//        	    _list["list[" + i + "].bean.prizetotalnum"] = row["bean.prizetotalnum"]; 
//        	    _list["list[" + i + "].bean.probability"] = row["bean.probability"];
        	    if(parseInt(row["prizetotalnum"])<=parseInt(pprizetotalnum)){
        	    	
        	    }else{ 
        	    	if(parseInt(row["prizetotalnum"])>parseInt(row["prestnum"])){
        	    	returnFlag = 1;
        	    	break;
        	        }
        	    }
        	    if(!mySet.has(row["prizeid"])){
        	    	mySet.add(row["prizeid"]);
        	    }else{
        	    	returnFlag = 2;
        	    	break;
        	    }
        	    if(null==row["prizeid"]||''==row["prizeid"]){
        	    	returnFlag = 3;
        	    	break;
        	    }
        	    if(null==row["prizename"]||''==row["prizename"]){
        	    	returnFlag = 4;
        	    	break;
        	    }
        	    if(null==row["prizelevel"]||''==row["prizelevel"]){
        	    	returnFlag = 5;
        	    	break;
        	    }
        	    if(null==row["prizetotalnum"]||''==row["prizetotalnum"]){
        	    	returnFlag = 6;
        	    	break;
        	    }
        	    if(null==row["probability"]||''==row["probability"]){
        	    	returnFlag = 7;
        	    	break;
        	    }
        	    if(null==row["iconurl"]||''==row["iconurl"]){
        	    	returnFlag = 8;
        	    	break;
        	    }
        	    relations.push({'prizeid':row["prizeid"],  
                    'prizename':row["prizename"],  
                    'ptotalnum':row["ptotalnum"],  
                    'prestnum':row["prestnum"],
                    'prizelevel':row["prizelevel"],
                    'prizetotalnum':row["prizetotalnum"],
                    'probability':row["probability"],
                    'promotionid':promotionid,
                    'scorestart':row["scorestart"],
                    'scoreend':row["scoreend"],
                    'orderno':row["orderno"],
                    'iconurl':row["iconurl"]
                    });  
        	}
        	
        	if(returnFlag == 1){
        		alert('prizetotalnum num can not larger then prestnum!');
        		return false;
        	}
        	if(returnFlag == 2){
        		alert('prizeid can not has the same prizeid!');
        		return false;
        	}
        	if(returnFlag == 3){
        		alert('prizeid can not be empty!');
        		return false;
        	}
        	if(returnFlag == 4){
        		alert('prizename can not be empty!');
        		return false;
        	}
        	if(returnFlag == 5){
        		alert('prizelevel can not be empty!');
        		return false;
        	}
        	if(returnFlag == 6){
        		alert('prizetotalnum can not be empty!');
        		return false;
        	}
        	if(returnFlag == 7){
        		alert('probability can not be empty!');
        		return false;
        	}
        	if(returnFlag == 8){
        		alert('iconurl can not be empty!');
        		return false;
        	}
        	$.ajax({
        	    url:ctx + '/promotionlist/saveRelation',
				type:'post',
				data:JSON.stringify(relations),
				traditional:true,
				dataType:'json',
				contentType:"application/json",
        	    success: function (data) {
        	        alert('success');
        	        $('#jstable').slideUp();
        	    },
				error:function(){
					alert('failure!');
				}
        	});
        });

    });
    // 显示下一页
	function nextPage(currentPage){
		window.location.href=ctx + "/promotionlist/listPromotionInfo?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	// 改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	// 验证函数
	function validateRoleNo(field){
		if("" != field.val() && !/^\d{12}$/.test(field.val())){
			return "Role number must be 12 positive integers";
		}
	}
	
	function Format(now,mask)
    {
        var d = now;
        var zeroize = function (value, length)
        {
            if (!length) length = 2;
            value = String(value);
            for (var i = 0, zeros = ''; i < (length - value.length); i++)
            {
                zeros += '0';
            }
            return zeros + value;
        };
     
        return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
        {
            switch ($0)
            {
                case 'd': return d.getDate();
                case 'dd': return zeroize(d.getDate());
                case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
                case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
                case 'M': return d.getMonth() + 1;
                case 'MM': return zeroize(d.getMonth() + 1);
                case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
                case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
                case 'yy': return String(d.getFullYear()).substr(2);
                case 'yyyy': return d.getFullYear();
                case 'h': return d.getHours() % 12 || 12;
                case 'hh': return zeroize(d.getHours() % 12 || 12);
                case 'H': return d.getHours();
                case 'HH': return zeroize(d.getHours());
                case 'm': return d.getMinutes();
                case 'mm': return zeroize(d.getMinutes());
                case 's': return d.getSeconds();
                case 'ss': return zeroize(d.getSeconds());
                case 'l': return zeroize(d.getMilliseconds(), 3);
                case 'L': var m = d.getMilliseconds();
                    if (m > 99) m = Math.round(m / 10);
                    return zeroize(m);
                case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
                case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
                case 'Z': return d.toUTCString().match(/[A-Z]+$/);
                // Return quoted strings with the surrounding quotes removed
                default: return $0.substr(1, $0.length - 2);
            }
        });
    }
	
	// easyui
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#dg').datagrid('validateRow', editIndex)){
			$('#dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickCell(index, field){
		acceptFlag = '0';
		if (editIndex != index){
			if (endEditing()){
				$('#dg').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#dg').datagrid('getEditor', {'index':index,'field':field});
				try{
					if('prizetotalnum'==field){
						var v = ed.target.numberbox('getValue');
						pprizetotalnum=v;
					}
				}catch(e){
					
				}
				if (ed){
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function(){
					$('#dg').datagrid('selectRow', editIndex);
				},0);
			}
		}
	}
	function onEndEdit(index, row){
		var ed = $(this).datagrid('getEditor', {
			index: index,
			field: 'prizeid'
		});
//		row.prizename = $(ed.target).combobox('getText');
	}
	//var packageTypeList;
	function append(){
		if (endEditing()){
			$('#dg').datagrid('appendRow',{prizeid:'',prizename:'',ptotalnum:0,prestnum:0,prizelevel:0,prizetotalnum:0,probability:0,scorestart:0,scoreend:0,orderno:0});
			editIndex = $('#dg').datagrid('getRows').length-1;
			$('#dg').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
//			var promotionid=$("#promotionid").val();
//			$.ajax({    
//		        url:ctx+'/promotionlist/getPrizeInfo?promotionid='+promotionid,    
//		        dataType : 'json',    
//		        type : 'POST',    
//		        async:false,  
//		        success: function (data){    
//		        packageTypeList = JSON.stringify(data);  
//		        }    
//		  });
		}
	}
	function removeit(){
		if (editIndex == undefined){return}
		$('#dg').datagrid('cancelEdit', editIndex)
				.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	function accept(){
		if (endEditing()){
			$('#dg').datagrid('acceptChanges');
			acceptFlag = '1';
			alert('accept');
		}
	}
	function reject(){
		$('#dg').datagrid('rejectChanges');
		editIndex = undefined;
	}
	function getChanges(){
		var rows = $('#dg').datagrid('getChanges');
		alert(rows.length+' rows are changed!');
	}
	function onComboxOnChange(newValue,oldValue){
		try{
			//var row = $('#dg').datagrid('getSelected');
			//var rowIndex = $('#dg').datagrid('getRowIndex',row);
			$.ajax({
        		url:ctx + '/prizelist/listById?prizeid=' + newValue,
        		method:'get',
        		async:false,
        		success:function(map){
//        			var ptotalnum = $('#dg').datagrid('getEditor', {'index':editIndex,'field':'ptotalnum'}).target;
//        			ptotalnum.numberbox('setValue', map.prize.prizetotalnum);
//        			var prestnum = $('#dg').datagrid('getEditor', {'index':editIndex,'field':'prestnum'}).target;
//        			prestnum.numberbox('setValue', map.prize.prizerestnum);
        			try{
        				
        				var rows = $('#dg').datagrid('getRows');
        				var row = rows[editIndex];
//        			
//        			var ptotalnum = $('#dg').datagrid('getEditor', {'index':editIndex,'field':'ptotalnum'}).target;
//        			ptotalnum.numberbox('setValue', map.prize.prizetotalnum);
//        			var prestnum = $('#dg').datagrid('getEditor', {'index':editIndex,'field':'prestnum'}).target;
//        			prestnum.numberbox('setValue', map.prize.prizerestnum);
        			
//        				$('#dg').datagrid('updateRow', {
//        					                index: $('#dg').datagrid('getRowIndex',row),
//        					                row: {
//        					                    ptotalnum: map.prize.prizetotalnum,
//        					                }
//        					            });
//        				$('#dg').datagrid('updateRow', {
//        					                index: $('#dg').datagrid('getRowIndex',row),
//        					                row: {
//        						                    prestnum: map.prize.prizerestnum,
//        						                }
//        				            });
        				var i=$('#dg').datagrid('getRowIndex',row);
        				//var td=$('.datagrid-body td[field="ptotalnum"]')[i];
        				var prizetotalnum = map.prize.prizetotalnum; 
        				$('.datagrid-view2 .datagrid-body tr').eq(i).find("td").each(function () {
        					if('ptotalnum'==$(this).attr("field")){
        						var div = $(this).find('div')[0];
        						$(div).text(prizetotalnum);
        					}
        				});
        				var prizerestnum = map.prize.prizerestnum;
        				$('.datagrid-view2 .datagrid-body tr').eq(i).find("td").each(function () {
        					if('prestnum'==$(this).attr("field")){
        						var div = $(this).find('div')[0];
        						$(div).text(prizerestnum);
        					}
        				});
        				var prizename = map.prize.prizename; 
        				$('.datagrid-view2 .datagrid-body tr').eq(i).find("td").each(function () {
        					if('prizename'==$(this).attr("field")){
        						var hiddeninput = $(this).find('div table tbody tr td span input')[1];
        						$(hiddeninput).val(prizename);
        					}
        				});
        				
        				row["ptotalnum"]=map.prize.prizetotalnum;
        				row["prestnum"]=map.prize.prizerestnum;
        				row["prizename"]=map.prize.prizename;
        				row["prizeid"]=map.prize.prizeid;
//        			$('#dg').datagrid('endEdit', rowIndex).datagrid('refreshRow', rowIndex).datagrid('selectRow', rowIndex);
//        				$('#dg').datagrid('validateRow', editIndex)
//        				$('#dg').datagrid('endEdit', editIndex);
//        				editIndex = undefined;
        			}catch(e){
        				
        			}
        		}
        	});
			
		}catch(e){
			
		}
	}
	function coustructDataGrid(){
		var promotionid=$("#promotionid").val();
	$('#dg').datagrid(  
            {  
                url : ctx+"/promotionlist/getDataGrid?promotionid="  
                        + $("#promotionid").val(),  
                idField : 'id',  
                pageSize : '10',  
                pageNumber : '1',  
                pageList : [ 5, 10 ],
                onClickCell: onClickCell,
                onEndEdit: onEndEdit,
                columns : [ [  
                        {  
                            field : 'prizename',  
                            title : 'prizename',  
                            width : 320,  
                            align : 'center',
                            formatter:function(value,row,index){
                            return row['prizename'];  //row.prizenamec;
                            },
                            editor : {  
                                type : 'combobox',  
                                options : {  
                                url:ctx+'/promotionlist/getPrizeInfo?promotionid='+promotionid,
                                valueField: 'prizeid',    
                                textField: 'prizename',
                                onChange:function(newValue,oldValue)
                                {
                                	onComboxOnChange(newValue,oldValue);
                                },
                                panelHeight: 'auto',  
                                required: true ,  
                                editable:true  
                                }  
                            }
                        },  
                        {  
                            field : 'ptotalnum',  
                            title : 'ptotalnum',  
                            width : 220,  
                            align : 'center'
//                            editor : {  
//                                type : 'numberbox',  
//                                required: true,
//                                disabled:true
//                            }  
                        },  
                        {  
                            field : 'prestnum',  
                            title : 'prestnum',  
                            width : 220,  
                            align : 'center'
//                            editor : {  
//                            	 type : 'numberbox',  
//                                 required: true,
//                                 disabled:true
//                            }  
                        },  
                        {  
                            field : 'prizelevel',  
                            title : 'prizelevel',  
                            width : 320,  
                            align : 'center',  
                            editor : {  
                           	 type : 'numberbox',  
                                required: true 
                            } 
                        },
                        {  
                            field : 'prizetotalnum',  
                            title : 'prizetotalnum',  
                            width : 320,  
                            align : 'center',  
                            editor : {  
                           	 type : 'numberbox',  
                                required: true 
                            } 
                        }, 
                        {  
                            field : 'probability',  
                            title : 'probability',  
                            width : 320,  
                            align : 'center',  
                            editor : {  
                           	 type : 'numberbox',  
                                required: true 
                            } 
                        },
                        {  
                            field : 'scorestart',  
                            title : 'scorestart',  
                            width : 320,  
                            align : 'center',  
                            editor : {  
                           	 type : 'numberbox',  
                                required: true 
                            } 
                        },
                        {  
                            field : 'scoreend',  
                            title : 'scoreend',  
                            width : 320,  
                            align : 'center',  
                            editor : {  
                           	 type : 'numberbox',  
                                required: true 
                            } 
                        },
                        {  
                        	field : 'orderno',  
                        	title : 'orderno',  
                        	width : 320,  
                        	align : 'center',  
                        	editor : {  
                        		type : 'numberbox',  
                        		required: true 
                        	} 
                        },
                        {  
                        	field : 'iconurl',  
                        	title : 'iconurl',  
                        	width : 320,  
                        	align : 'center',  
                        	editor : {  
                        		type : 'textbox',  
                        		required: true 
                        	} 
                        }
                        ] ],  
                toolbar : [  
                        {  
                            id : "businessAdd",  
                            text : 'Append',  
                            iconCls : 'icon-add',  
                            handler : function() {  
                            	append(); 
                            }  
                        },  
                        {  
                            id : "bDestory",  
                            text : 'Remove',  
                            iconCls : 'icon-undo',  
                            handler : function() {  
//                            	$('#dg').datagrid(  
//                                'rejectChanges'); 
                            	removeit();
                            }  
                        }, 
                        {  
                        	id : "bAccept",  
                        	text : 'accept',  
                        	iconCls : 'icon-ok',  
                        	handler : function() {  
                        		accept();
                        	}  
                        } 
                        ]  
            }); 
	} 