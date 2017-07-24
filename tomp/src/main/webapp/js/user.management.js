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
    		$('#add_user').slideDown();
    	});
    	//显示修改框
        $('.table_content .Modify').on("click",function () {
        	val = $(this).next().val();
        	flag = true;
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/user/selectUser?userid=' + $(this).prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#username').val(map.user.username);
        			$('#mobilephone').val(map.user.mobilephone);
        			$('#email').val(map.user.email);
        			$('#curr_id').val(map.user.userid);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_user').slideDown();
        });
        
    	//添加用户
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/user/insertUser',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/user/selectUserList";
    					}else
    					{
    						alert('Add user failure');
    					}
    				},
    				error:function(){
    					alert('Add user failure');
    				}
    			});
    		}
    	});
    	
    	//修改用户
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/user/updateUser',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/user/selectUserList";
    					}else
    					{
    						alert('Modify user failure');
    					}
    				},
    				error:function(){
    					alert('Modify user failure');
    				}
    			});
    		}
    	});

        //删除用户
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
    				url:ctx + '/user/deleteUser',
    				type:'post',
    				data:{ids:array},
    				traditional:true,
    				dataType:'json',
    				success:function(data){						
    					if(0 == data.retCode){
    						nextPage(cPage);
    					}else{
    						alert('Delete user failure!');
    					}
    				},
    				error:function(){
    					alert('Delete user failure!');
    				}
    			});	
    		}				
        });
        
        //关联角色
        var userid="";
        $('.table_content .Relation').click(function(){
        	flag=true;
        	$.jstree.destroy('#pop');
        	userid = $(this).parent().children().eq(0).val();
        	$.ajax({
        		'url':ctx + '/user/selectRoleByUser?userid=' + userid,
        		'dataType':'json',
        		success:function(map){
        			var array = [];
        			$.each(map.roles,function(i,role){
        				if(role.userid != undefined){
        					array.push({
        						id:role.roleid,
        						text:role.rolename,
        						state:{checked:true}
        					});
        				}else{
        					array.push({
            					id:role.roleid,
            					text:role.rolename
            				});
        				}        				
        			});
        			if(array.length > 0){
        				$('#pop').jstree({
                    		plugins : ["checkbox","wholerow"],
                    		checkbox:{
                    			tie_selection:false
                    		},
                    		core:{
                    			data:array
                    		}
                    	});
        			}else{
        				$('#pop').css("text-align","center").html("<h3>You haven't create role!</h3>")
        			}                	
        		}
        	});
        	$('#jstree').slideDown();
        });

    	$('#jstree_btn').click(function(){
    		if(flag && ($.jstree.reference('#pop').get_checked().length > 0)){
    			flag = false;
        		$.ajax({
        			url:ctx + "/user/insertUserRole",
        			type:"post",
        			data:{userid:userid,roleids:$.jstree.reference('#pop').get_checked()},
        			traditional:true,
        			success:function(ret){
        				$('#jstree').slideUp(0);
        				alert("SAVE " + ret);
        			},
        			error:function(){
        				alert("SAVE FAILURE");
        			}
        		});
    		}
    	});
    });
    //显示下一页
	function nextPage(currentPage){
		window.location.href=ctx + "/user/selectUserList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	//验证函数
	function validateUserNo(field){
		if("" != field.val() && !/^\d{12}$/.test(field.val())){
			return "User number must be 12 positive integers";
		}
	}
	
	function checkUnique(field){
		if(field.val() != val){
			var i = 0
			$.ajax({
	   			url:ctx+'/user/check',
	   			type:'post',
	   			async:false,
	   			data:{
	   				username:field.val()
	   			},
	   			success:function(map){
	   				if(map.retCode==0){
	   					i = 1;   					
	   				}
	   			}
	   		});
			if(i==1){
				return "The name has existed in User";
			}
		}		
	}