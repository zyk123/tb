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
    		$('#add_privilege').slideDown();
    	});
    	//显示修改框
        $('.table_content .Modify').on("click",function () {
        	val = $(this).next().val();
        	flag = true;
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/qxPrivilege/selectPrivilege?privilegeid=' + $(this).prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#privilegename').val(map.privilege.privilegename);
        			$('#curr_id').val(map.privilege.privilegeid);
        			$('#mp_mark option').each(function(){
        				if($(this).val()==map.privilege.privilegecode){
        					$(this).prop("selected",true);
        				}
        			});
        			$('#mp_mark').prop("disabled",true);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_privilege').slideDown();
        });
        
    	//添加权限
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/qxPrivilege/insertPrivilege',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxPrivilege/selectPrivilegeList";
    					}else
    					{
    						alert('Add privilege failure');
    					}
    				},
    				error:function(){
    					alert('Add privilege failure');
    				}
    			});
    		}
    	});
    	
    	//修改权限
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/qxPrivilege/updatePrivilege',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxPrivilege/selectPrivilegeList";
    					}else
    					{
    						alert('Modify privilege failure');
    					}
    				},
    				error:function(){
    					alert('Modify privilege failure');
    				}
    			});
    		}
    	});

        //删除权限
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
    				url:ctx + '/qxPrivilege/deletePrivilege',
    				type:'post',
    				data:{ids:array},
    				traditional:true,
    				dataType:'json',
    				success:function(data){						
    					if(0 == data.retCode){
    						nextPage(cPage);
    					}else{
    						alert('Delete privilege failure!');
    					}
    				},
    				error:function(){
    					alert('Delete privilege failure!');
    				}
    			});	
    		}				
        });
    });
    //显示下一页
	function nextPage(currentPage){
		window.location.href=ctx + "/qxPrivilege/selectPrivilegeList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	//验证函数
	function validatePrivilegeNo(field){
		if("" != field.val() && !/^\d{12}$/.test(field.val())){
			return "Privilege number must be 12 positive integers";
		}
	}
	
	function validatePrivilegeCode(field){
		var value = ["VIEW","SEARCH","ADD","DETAIL","MODIFY","DELETE","EXPORT","IMPORT"];
		if("" != field.val() && value.indexOf(field.val().toUpperCase()) == -1){
			return "Privilege code must be in [\"VIEW\",\"SEARCH\",\"ADD\",\"DETAIL\",\"MODIFY\",\"DELETE\",\"EXPORT\",\"IMPORT\"]";
		}
	}
	
	function checkUnique(field){
		if(field.val()!=val){
			var i = 0;
			$.ajax({
	   			url:ctx+'/qxPrivilege/check',
	   			type:'post',
	   			async:false,
	   			data:{
	   				privilegename:field.val()
	   			},
	   			success:function(map){
	   				if(map.retCode==0){
	   					i = 1;
	   				}
	   			}
	   		});
			if(i==1){
				return "The name has existed in Privilege";
			}
		}
	}