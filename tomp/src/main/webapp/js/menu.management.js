var val=""
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
    		if($('#add_form li').size()>3){
//    			$('#menulevel').parent().prev().remove();
//        		$('#menulevel').parent().next().remove();
    			$('#fid').parent().remove();
    			$("#menuindex").parent().remove();
    		}    		
    		$('#add_menu').slideDown();
    	});
    	//显示修改框
        $('.table_content .Modify').on("click",function () {
        	val = $(this).next().next().val();
        	flag = true;
        	if($(this).next().val() == 1){
        		$('#m_url').remove();
        		$('#p_menu').remove();
        	}else {
                if($('#m_url').size()==0){
                    $('#m_name').before('<li id="m_url"><label for=""> Menu Url</label><input id="menuindex" name="menuindex" type="text" data-validation-engine="validate[required,maxSize[50]"></li>');
                }
                $('#p_menu').remove();
				var li_start = '<li id="p_menu"><label for=""> Menu Parent</label><select id="fid" name="fid" data-validation-engine="validate[required]">';
				var li_end='</select></li>';
				var pid = $(this).prev().val();
				$.ajax({
					url : ctx + "/qxMenu/selectParentMenu",
					type:'get',
					success:function(map){
						$.each(map.menu,function(i,obj){
							if(obj.menuid == pid){
								li_start += "<option value='" + obj.menuid + "' selected='selected'>" + obj.menuname + "</option>";
							}else{
								li_start += "<option value='" + obj.menuid + "'>" + obj.menuname + "</option>";
							}
						});
						$('#m_url').before(li_start + li_end);
					}
				});
			}
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/qxMenu/selectMenu?menuid=' + $(this).prev().prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#menulevel1 option').each(function(){
        				if($(this).val() == map.menu.menulevel){
        					$(this).prop("selected",true);
        					return false;
        				}
        			});
        			$('#menulevel1').prop("disabled",true);
        			$('#menuname').val(map.menu.menuname);
        			$('#menuindex').val(map.menu.menuindex);
        			$('#orderno').val(map.menu.orderno);
        			$('#curr_id').val(map.menu.menuid);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_menu').slideDown();
        });
        
    	//添加菜单
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/qxMenu/insertMenu',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxMenu/selectMenuList";
    					}else
    					{
    						alert('Add menu failure');
    					}
    				},
    				error:function(){
    					alert('Add menu failure');
    				}
    			});
    		}
    	});
    	
    	//修改菜单
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/qxMenu/updateMenu',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/qxMenu/selectMenuList";
    					}else
    					{
    						alert('Modify menu failure');
    					}
    				},
    				error:function(){
    					alert('Modify menu failure');
    				}
    			});
    		}
    	});

        //删除菜单
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
    				url:ctx + '/qxMenu/deleteMenu',
    				type:'post',
    				data:{array:array},
    				traditional:true,
    				dataType:'json',
    				success:function(data){						
    					if(0 == data.retCode){
    						nextPage(cPage);
    					}else{
    						alert('Delete menu failure!');
    					}
    				},
    				error:function(){
    					alert('Delete menu failure!');
    				}
    			});	
    		}				
        });
    	$('#menulevel').on('change',function(){
    		var t = $(this);
    		if(t.val() == "2"){
    			var li_start = '<li><label for=""> Menu Parent</label><select id="fid" name="fid" data-validation-engine="validate[required]">';
    			var li_end='</select></li>';
    			$.ajax({
    				url : ctx + "/qxMenu/selectParentMenu",
    				type:'get',
    				success:function(map){
                        t.parent().after('<li><label for=""> Menu url</label><input id="menuindex" name="menuindex" type="text" data-validation-engine="validate[required,maxSize[50]"></li>');
    					$.each(map.menu,function(i,obj){
    						li_start += "<option value='" + obj.menuid + "'>" + obj.menuname + "</option>";
    					});
						t.parent().after(li_start + li_end);
    				}
    			});
    		}else{
    			t.parent().next().remove();
    			t.parent().next().remove();
    		}
    	});
    	//关联权限
        var menuid="";
        $('.table_content .Relation').click(function(){
        	flag=true;
        	$.jstree.destroy('#pop');
        	menuid = $(this).parent().children().eq(0).val();
        	$.ajax({
        		'url':ctx + '/qxMenu/selectPrivilegeByMenu?menuid=' + menuid,
        		'dataType':'json',
        		success:function(map){
        			var array = [];
        			$.each(map.qxPrivilege,function(i,privilege){
        				if(privilege.menuid != undefined){
        					array.push({
        						id:privilege.privilegeid,
        						text:privilege.privilegename,
        						state:{checked:true}
        					});
        				}else{
        					array.push({
        						id:privilege.privilegeid,
        						text:privilege.privilegename
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
        				$('#pop').css("text-align","center").html("<h3>You haven't create privilege!</h3>")
        			}
        		}
        	});
        	$('#jstree').slideDown();
        });

    	$('#jstree_btn').click(function(){
    		if(flag && ($.jstree.reference('#pop').get_checked().length > 0)){
    			flag = false;
        		$.ajax({
        			url:ctx + "/qxMenu/updateMenuPrivilege",
        			type:"post",
        			data:{menuid:menuid,privileges:$.jstree.reference('#pop').get_checked()},
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
		window.location.href=ctx + "/qxMenu/selectMenuList?" + $('#search_form').serialize()
		+ "&currentPage=" + currentPage + "&showCount=" + $('#currentPageRow').val();
	}
	//改变显示行数
	function changePageRows(value){
		$('#currentPageRow').val(value);
		nextPage(1);
	}
	
	//验证函数
	function validateMenuNo(field){
		if("" != field.val() && !/^\d{12}$/.test(field.val())){
			return "Menu number must be 12 positive integers";
		}
	}
	
	function checkUnique(field){
		if(field.val()!=val){
			var i = 0;
			$.ajax({
	   			url:ctx+'/qxMenu/check',
	   			type:'post',
	   			async:false,
	   			data:{
	   				menuname:field.val()
	   			},
	   			success:function(map){
	   				if(map.retCode==0){
	   					i = 1;
	   				}
	   			}
	   		});
			if(i==1){
				return "The name has existed in Menu";
			}
		}
	}