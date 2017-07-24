<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>TOMP</title>
<link rel="stylesheet"
	href="<%=path%>/third/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/base.css">
<link rel="stylesheet"
	href="<%=path%>/third/jQuery-Validation-Engine/css/validationEngine.jquery.css">
<link rel="stylesheet"
	href="<%=path%>/third/passStronger/style/main.css">
<script type="text/javascript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script src="<%=path%>/js/jquery.from.js"></script>
<script
	src="<%=path%>/third/jQuery-Validation-Engine/js/languages/jquery.validationEngine-en.js"></script>
<script
	src="<%=path%>/third/jQuery-Validation-Engine/js/jquery.validationEngine.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=path%>/third/passStronger/jquery.passwordStrength.js"></script>
<script src="<%=path%>/js/base.js"></script>
</head>
<body>
	<nav class="navbar">
		<div class="nav-item fl">
			<ul class="">
				<li class="brands navbar-nav">CELCOM</li>
				<li class="brands navbar-nav">TOMP</li>
			</ul>
		</div>
		<div class="nav-item fr">
			<button class="sty1" type="button" onclick="logout()">log
				out</button>
		</div>
		<div class="nav-item fr">user information</div>

	</nav>

<div class="viewFramework-body">
    <div class="viewFramework-sidebar">
	    <c:if test="${!empty userMenuPrivileges }">
		    	<c:forEach var="item" items="${userMenuPrivileges}" varStatus="status">
			    	<div class="sidebar-items <c:if test='${status.first}'>active</c:if>">
			            <div class="sidebar-item-title" data-list-on="<c:choose><c:when test='${status.first}'>true</c:when><c:otherwise>false</c:otherwise></c:choose>" data-list-num="${ status.index+1}">${item.menuname }&nbsp;<i class="icon"></i></div>
			            <c:if test="${!empty item.childmenuprivilege }">
			            <ul class="siderbar-item-cont" id="siderbar-ic${ status.index+1}">
			            	<c:forEach var="childItem" items="${item.childmenuprivilege}" varStatus="childStatus">
			            	<c:if test="${!empty childItem.privilegecodes && fn:contains(childItem.privilegecodes,'VIEW') }">
			                <li class="sidebar-item <c:if test='${status.first&&childStatus.first}'> active</c:if>" >
			                    <a class="reportForms"  href="javascript:void(0);" data-url="<c:choose><c:when test='${fn:startsWith(childItem.menuindex,"/")}'><%=path %>${childItem.menuindex}?menuPrivileges=${childItem.privilegecodes }</c:when><c:otherwise><%=path %>/${childItem.menuindex}?menuPrivileges=${childItem.privilegecodes }</c:otherwise></c:choose>" title="${ childItem.menuname}">${ childItem.menuname}</a>
			                </li>
			                </c:if>
			                </c:forEach>
			            </ul>
			            </c:if>
			        </div>
		        </c:forEach>
	    </c:if>		
    </div>
    <div class="viewFramework-content">
        <iframe id="vc-frame" name="vc-frame" src="" frameborder="0" style="height: 100%; width:100%;"></iframe>
    </div>

	</div>

	<div class="mask_layout" style="display: none;">
		<div class="add_num_wrap">
			<div class="add_num_top">
				新增 <i class="closeLayout">X</i>
			</div>
			<div class="add_num_content">
				<input class="input_ov" type="text" />
				<div class="btn_wrap">
					<button class="btn_ov mid_btn verify fl">确认</button>
					<button class="btn_ov mid_btn cancel fr closeLayout">取消</button>
				</div>
			</div>
		</div>
	</div>

<!-- 	<div class="mask_layout relation" style="display: none;"> -->
<!-- 		<div class="role_selection_wrap"> -->
<!-- 			<div class="top"> -->
<!-- 				Role selection <i class="icon iclose">X</i> -->
<!-- 			</div> -->
<!-- 			<div class="cont"> -->
<!-- 				<ul class="clearfixa relation_ul"> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 					<li><input type="checkbox">System management</li> -->
<!-- 				</ul> -->
<!-- 				<button class="btn_ov apply">Apply</button> -->
<!-- 			</div> -->

<!-- 		</div> -->
<!-- 	</div> -->

	<div class="mask_layout addUser" style="display: none;">
		<div class="role_selection_wrap">
			<div class="top">
				Add user <i class="icon iclose">X</i>
			</div>
			<div class="cont">
				<form id="conditionForm">
					<ul class="clearfixa add_ul">
						<!--                 <li><label for=""> User number</label><input type="text"></li> -->
						<li><label for="">UserName</label><input id="userName"
							type="text"
							data-validation-engine="validate[required,maxSize[10]]">
						</li>
						<li><label for="">New Password</label><input type="password"
							id="password"
							data-validation-engine="validate[required,custom[hello]]">
							<div id="passwordStrengthDiv" class="is0"></div></li>
						<li><label for="">Confirm Password</label><input
							type="password" id="passwordSure"
							data-validation-engine="validate[required,custom[hello]]">
						</li>
						<li><label for=""> Mobile Phone</label><input type="text"
							id="mobileNo"
							data-validation-engine="validate[required,custom[posInteger],maxSize[${userModelSession.mobileLength}]]">
						</li>
						<li><label for=""> Email</label><input type="text" id="email"
							data-validation-engine="validate[custom[email]]"></li>
						<li><label for=""> Expiry Date</label><input type="text"
							id="expiryDate" class="Wdate"
							onFocus="WdatePicker({maxDate:'\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
						</li>
						<!--                 <li><label for=""> Create Date</label><select name="" id=""></select></li> -->
					</ul>
				</form>
				<button class="btn_ov apply">Save</button>
			</div>

		</div>
	</div>

	<div class="mask_layout pwdRule" style="display: none;">
		<div class="role_selection_wrap">
			<div class="top">
				Password rule config <i class="icon iclose">X</i>
			</div>
			<div class="cont">
				<form id="conditionForm">
					<ul class="clearfixa add_ul">
						<!--                 <li><label for=""> User number</label><input type="text"></li> -->
						<li><label for="">Minimum length</label><input id="Minimum"
							type="text"
							data-validation-engine="validate[required,maxSize[10]]">
						</li>
						<li><label for=""> Maximum Length</label><input type="text"
							id="Maximum"
							data-validation-engine="validate[required,custom[hello]]">

						</li>
						<li><label for="">Whether contain Digits</label> <select>
								<option>no</option>
								<option>yes</option>
						</select></li>

						<li><label for="">Whether contain Letters</label> <select>
								<option>no</option>
								<option>yes</option>
						</select></li>

						<li><label for="">Whether case sensitive</label> <select>
								<option>no</option>
								<option>yes</option>
						</select></li>

						<li><label for="">Whether contain Characters</label> <select>
								<option>no</option>
								<option>yes</option>
						</select></li>

						<li><label for="">Password expiry date</label><input
							type="text" id="expiryDate" class="Wdate"
							onFocus="WdatePicker({maxDate:'\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
						</li>
						<!--                 <li><label for=""> Create Date</label><select name="" id=""></select></li> -->
					</ul>
				</form>
				<button class="btn_ov apply">Save</button>
			</div>

		</div>
	</div>
	<script>
/*    window.onload=function(){
       var pagechoiceBtn2= $(window.frames["vc-frame"].document).find("#htmlIdentifier").val();
        choiceBtn();
        function choiceBtn(){
            if(pagechoiceBtn2=="add"){
                $("button.import").hide();
                $("#addNum").show();
            }else{
                $("button.import").show();
                $("#addNum").hide();
            }
        }
    };*/


    $(function(){
   		 $("#vc-frame").attr("src",$(".reportForms:eq(0)").attr("data-url"));  
    	
    	 $("#password").on("keydown",function(){
			$("#passwordSure").val("");    	 
    	 })
    		
    	 $('#password').passwordStrength();
		 $('#conditionForm').validationEngine('attach');	    	
//     	window.location.href="${ctx}/blackList/list";
        /*siderbar 伸展效果高度 s*/
        var siderbarcHeight ={};
        $(".siderbar-item-cont").each(function(i){
            var thisChildren =$(this).children();
            siderbarcHeight[i] =thisChildren.length*thisChildren.height();
            $(this).css("height",siderbarcHeight[i]);
        });

        $(".sidebar-item-title").each(function(i){ //默认关闭 list 导航
            if($(this).attr("data-list-on")=="false"){
                $(this).next().css("height","0px");
            }
        });

        $(".sidebar-item-title").on("click", function () {
            var listnum = parseInt($(this).attr("data-list-num"));
            if($(this).attr("data-list-on")=="true"){
                $(this).attr("data-list-on","false");
                $(this).next().css("height","0px");
            }else{
                $(this).attr("data-list-on","true");
                switch(listnum){
                    case 1:  $(this).next().css("height",siderbarcHeight[listnum-1]+"px"); break;
                    case 2:  $(this).next().css("height",siderbarcHeight[listnum-1]+"px"); break;
                    case 3:  $(this).next().css("height",siderbarcHeight[listnum-1]+"px"); break;
                    default: $(this).next().css("height","auto"); break;
                }
            }
        });
        /*siderbar 伸展效果高度  E*/

        $(".sidebar-item").on("click",function(){
            var pagechoiceBtn= $(this).attr("data-btn-choice");
            if(pagechoiceBtn=="add"){
                $("button.import").hide();
              $("#addNum").show();
            }else{
                $("button.import").show();
                $("#addNum").hide();
            }
        });

        $(".reportForms").on({
            click: function(){
                $(".sidebar-item").removeClass("active");
                $(this).parent().addClass("active");

                var targetUrl =$(this).attr("data-url");
                $("#vc-frame").attr("src",targetUrl);
            },
            mouseover:function(){
                $(this).attr("title",$(this).text());
            }
        });
        

        $("#fileImport").on("change",function(){
            console.log("选择完成");
            var flag = confirm("上传");
                if(flag){
                    console.log("上传");
                }
        });

    });
    
//     function mytest(field, rules, i, options){
//     	console.log(field)
//     	console.log(rules)
//     	console.log(i)
//     	console.log(options)
//     }
    
    function hideAllTip(){
    	$('#conditionForm').validationEngine('validate');
    }

	function logout(){
		window.location.href = "<%=path%>/user/logout";
		}
	</script>
</body>
</html>
