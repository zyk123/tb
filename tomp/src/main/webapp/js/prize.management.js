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
    		$('#add_prize').slideDown();
    	});
    	//显示修改框
        $('.table_content .Modify').on("click",function () {
        	flag = true;
        	$('#modify_form').validationEngine('attach', {promptPosition : "topRight"});
        	$.ajax({
        		url:ctx + '/prizelist/listById?prizeid=' + $(this).prev().val(),
        		method:'get',
        		async:false,
        		success:function(map){
        			$('#prizenameM').val(map.prize.prizename);
        			$('#prizedescM').val(map.prize.prizedesc);
        			$('#prizepriceM').val(map.prize.prizeprice);
        			$('#prizetotalnumM').val(map.prize.prizetotalnum);
        			$('#prizetypeM').val(map.prize.prizetype);
        			$('#curr_id').val(map.prize.prizeid);
        		}
        	});
            $('#modify_form').validationEngine('hide');
    		$('#modify_prize').slideDown();
        });
        
    	//添加角色
    	$('#add_btn').on('click',function(){
    		if($('#add_form').validationEngine('validate') && flag){   
    			flag = false;     		
    			$.ajax({
    				url:ctx + '/prizelist/insertPrize',
    				method:'post',
    				data:$('#add_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/prizelist/listPrizeInfo";
    					}else
    					{
    						alert('Add prize failure');
    					}
    				},
    				error:function(){
    					alert('Add prize failure');
    				}
    			});
    		}
    	});
    	
    	//修改角色
    	$('#modify_btn').on('click',function(){
    		if($('#modify_form').validationEngine('validate') && flag){  
    			flag = false;      		
    			$.ajax({
    				url:ctx + '/prizelist/updatePrize',
    				method:'post',
    				data:$('#modify_form').serialize(),
    				success:function(ret){
    					if(ret.retCode == '0'){
    						window.location.href=ctx + "/prizelist/listPrizeInfo";
    					}
    					else if(ret.retCode == '2')
    					{
    						alert('prize totalnum can not be little then has asign num with promotion');
    					}
    					else
    					{
    						alert('Modify prize failure');
    					}
    				},
    				error:function(){
    					alert('Modify prize failure');
    				}
    			});
    		}
    	});

        //删除角色
        $('.table_content .Delete').on('click',function(){
        	if(confirm("Are you sure you want to delete the selected data?")){
    			$.ajax({
    				url:ctx + '/prizelist/deletePrize',
    				type:'post',
    				data:{id:$(this).prev().prev().val()},
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
    					alert('Delete prize failure!');
    				}
    			});	
    		}				
        });

    });
    //显示下一页
	function nextPage(currentPage){
		window.location.href=ctx + "/prizelist/listPrizeInfo?" + $('#search_form').serialize()
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