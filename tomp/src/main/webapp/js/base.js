/**
 * Created by jarvis.chen on 2016/8/18.
 */
$(function () {

    /*全选 s*/
    $("#checkall").bind("click",checkAllmes);
    function checkAllmes(){
        if($("#checkall").prop("checked")==false){
            $(".table_content :checkbox").prop("checked",false);
        }else{
            $(".table_content :checkbox").prop("checked",true);
        }
    }

    $(".table_content").on("change",function (){
            var checkcount =$(".table_content :checkbox").length;
            var flagTnum =0;
            $(".table_content :checkbox").each(function(){
                if($(this).prop("checked")==true){
                    flagTnum++;
                }
            });

            if(flagTnum==checkcount){
                $("#checkall").prop("checked",true);
            }else{
                $("#checkall").prop("checked",false);
            }
        }
    );
    /*全选 e*/



    $(".sidebar-items").on("click",function(e){
        $(this).hasClass("active")?$(this).removeClass("active"):$(this).addClass("active");
    });
    tableAutoTop();
    $(".iclose,.cancel").on("click",function () {
    		if($(this).parents(".mask_layout").find("form").length == 1){
    			$(this).parents(".mask_layout").find("form")[0].reset();
    			$(this).parents(".mask_layout").find("form").validationEngine('hide');
    		}
            $(this).parents(".mask_layout").slideUp();
    });
    $(".Relation").on("click",function () {
        $(window.parent.document).contents().find(".relation").show();
    });
    $("#addUser").on("click",function () {
    	$(window.parent.document).contents().find("#conditionForm")[0].reset();
    	$(window.parent.document).contents().find("#userName").removeAttr("disabled");
        $(window.parent.document).contents().find(".addUser").show();
    });

    $("#PwdRule").on("click",function () {
 
        $(window.parent.document).contents().find(".pwdRule").show();
    });
    
    
    $("#modifyUser").on("click",function () {
    	$(window.parent.document).contents().find(".addUser .top").empty().append('Modify user  <i class="icon iclose" >X</i>');
    	$(window.parent.document).contents().find("#userName").val("admin").attr("disabled","disabled");
    	$(window.parent.document).contents().find("#password").val("qweQWE@13@#v");
    	$(window.parent.document).contents().find("#passwordSure").val("qweQWE@13@#v");
    	$(window.parent.document).contents().find("#mobileNo").val("1566156516");
    	$(window.parent.document).contents().find("#email").val("");
    	$(window.parent.document).contents().find("#expiryDate").val("2016-09-29");
    	$(window.parent.document).contents().find(".addUser").show();
    });
});
    


function tableAutoTop() {
    var   searchH =$(".table_search").height();
    $(".table").css("top",searchH+10+"px");
}
window.onresize=function(){
    tableAutoTop();
}


