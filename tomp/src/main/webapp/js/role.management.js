var val="";
$(function () {
    	var flag = true;
    	$('#search_form').validationEngine('attach', {promptPosition : "bottomRight"});
    	$('#add_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
    	$('#modify_form').validationEngine('attach', {promptPosition : "topRight",maxErrorsPerField:1});
    	//搜索
    	$('#search_btn').on('click',function(){
    		if($('#search_form').validationEngine('validate')){
    			nextPage(1);
    		};
    	});
    	//显示添加框
    	$('#add').on('click',function(){
    		flag = true;
    		$('#add_form').get(0).reset();
    		$('#add_form').validationEngine('hide');
    		$('#add_role').slideDown();
    	});
    	//显示修改框
        $('.table_content .Modify').on("click",function () {
        	val = $(this).next().val();
        	flag = true;
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/qxRole/selectRole?roleid=' + $(this).prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#rolename').val(map.role.rolename);
        			$('#curr_id').val(map.role.roleid);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_role').slideDown();
        });
        
    	//添加角色
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/qxRole/insertRole',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxRole/selectRoleList";
    					}else
    					{
    						alert('Add role failure');
    					}
    				},
    				error:function(){
    					alert('Add role failure');
    				}
    			});
    		}
    	});
    	
    	//修改角色
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/qxRole/updateRole',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxRole/selectRoleList";
    					}else
    					{
    						alert('Modify role failure');
    					}
    				},
    				error:function(){
    					alert('Modify role failure');
    				}
    			});
    		}
    	});

        //删除角色
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
    				url:ctx + '/qxRole/deleteRole',
    				type:'post',
    				data:{ids:array},
    				traditional:true,
    				dataType:'json',
    				success:function(data){						
    					if(0 == data.retCode){
    						nextPage(cPage);
    					}else{
    						alert('Delete role failure!');
    					}
    				},
    				error:function(){
    					alert('Delete role failure!');
    				}
    			});	
    		}				
        });
      //关联权限
        var roleid="";
        $('.table_content .Relation').click(function(){
        	flag=true;
        	$.jstree.destroy('#pop');
        	roleid = $(this).parent().children().eq(0).val();
        	$.ajax({
        		'url':ctx + '/qxRole/selectMenuPrivilegeByRole?roleid=' + roleid,
        		'type':"post",
        		'dataType':'json',
        		success:function(map){
        			var array = [];
        			$.each(map.menu,function(i,menu){
            			var child = [];
						$.each(menu.privileges,function(i,privilege){
							if(privilege.menuid != undefined){
        						child.push({
        							text:privilege.privilegename,
        							state:{checked:true},
            						li_attr:{
        								privilegeid:privilege.privilegeid
        							}
        						});
							}else{
        						child.push({
        							text:privilege.privilegename,
        							li_attr:{
        								privilegeid:privilege.privilegeid
        							}
        						});
							}
						});
    					if(menu.privileges.length == 0){
        					array.push({
        						id:menu.menuid,
        						text:menu.menuname,
        						children:true,
        						state:{
        							disabled:true
        						}
        					});
    					}else{
    						array.push({
        						id:menu.menuid,
        						text:menu.menuname,
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
        				$('#pop').css("text-align","center").html("<h3>You haven't create menu!</h3>")
        			}
        		}
        	});
        	$('#jstree').slideDown();
        });

    	$('#jstree_btn').click(function(){
    		var d_array = [];
    		var nodes = $.jstree.reference('#pop').get_bottom_checked(true);
    		$.each(nodes,function(i,node){
    			d_array.push(node.parent + "-" + node.li_attr.privilegeid);
    		});
    		if(flag && (nodes.length>0)){
    			flag = false;
        		$.ajax({
        			url:ctx + "/qxRole/updateRoleMenuPrivilege",
        			type:"post",
        			data:{
        				roleid:roleid,
        				mps:d_array
        			},
        			traditional:true,
        			success:function(ret){
        				if(ret.retCode == 0){
            				$('#jstree').slideUp(0);
        					alert("SAVE SUCCESS");
        				}else{
        					alert("SAVE ERROR");
        				}        				
        			},
        			error:function(){
        				alert("SAVE ERROR");
        			}
        		});
    		}
    	});
    });
    //显示下一页
	function nextPage(currentPage){
		window.location.href=ctx + "/qxRole/selectRoleList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	//验证函数
	function validateRoleNo(field){
		if("" != field.val() && !/^\d{12}$/.test(field.val())){
			return "Role number must be 12 positive integers";
		}
	}
	
	function checkUnique(field){
		if(field.val()!=val){
			var i = 0;
			$.ajax({
	   			url:ctx+'/qxRole/check',
	   			type:'post',
	   			async:false,
	   			data:{
	   				rolename:field.val()
	   			},
	   			success:function(map){
	   				if(map.retCode==0){
	   					i = 1;
	   				}
	   			}
	   		});
			if(i==1){
				return "The name has existed in Role";
			}
		}
	}