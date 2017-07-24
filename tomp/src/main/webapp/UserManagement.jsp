<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ include file="/WEB-INF/include.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/base.css">
    <script src="<%=path %>/js/base.js"></script>
</head>
<body style="background-color: inherit;">
<input type="text" id="htmlIdentifier" value="whiteListNumInterval" style="display: none;" />
    <div class="table_search">
        <ul>

            <li>User No<input class="num" type="text"></li>
            <li>User Name <input class="num" type="text"></li>
            <li>Mobile Phone <input class="phone" type="text"></li>
            <li><button class="btn_ov mid_btn search">query</button></li>
            <li><button class="btn_ov mid2_btn delete">delete</button></li>
            <li><button class="btn_ov mid2_btn import" id="addUser">add</button></li>
            <li><button class="btn_ov mid2_btn import" id="PwdRule">ruleconfig</button></li>
        </ul>
    </div>
 <table class="table bor">
     <tr class="table_title">
         <td><input type="checkbox" id="checkall">User No</td>
         <td>User Name</td>
         <td>Mobile Phone</td>
         <td>Email</td>
         <td>Create Date</td>
         <td>Edit</td>
     </tr>
     <tr class="table_content">
         <td class=""><input type="checkbox"> <span>18626488888</span> </td>
         <td class="">James</td>
         <td>0193682080</td>
         <td>baidu@163.com</td>
         <td>2016-08-28</td>
         <td>
            <span class="sbtn btn_ov Modify" id="modifyUser">Modify</span>
             <span class="sbtn btn_ov Relation">Relation</span>
         </td>
     </tr>
     <tr class="table_content">
         <td class=""><input type="checkbox"> <span>15826483268</span> </td>
         <td class="">lyn</td>
         <td>0145317374</td>
         <td>kingaof@163.com</td>
         <td>2016-09-28</td>
         <td>
            <span class="sbtn btn_ov Modify" id="modifyUser">Modify</span>
             <span class="sbtn btn_ov Relation">Relation</span>
         </td>
     </tr>
     <tr class="table_content">
         <td class=""><input type="checkbox"> <span>18306558846</span> </td>
         <td class="">henry</td>
         <td>0135832020</td>
         <td>henry@163.com</td>
         <td>2016-04-16</td>
         <td>
            <span class="sbtn btn_ov Modify" id="modifyUser">Modify</span>
             <span class="sbtn btn_ov Relation">Relation</span>
         </td>
     </tr>


 </table>
<div class="page_nav">
    <div class="fl">
        每页
        <select name="" id="">
            <option value="">10</option>
            <option value="">20</option>
            <option value="">50</option>
        </select>
        条 共 <span>10000</span> 数据
    </div>
    <nav>
        <ul class="pagination">
            <li>
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
                <a href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>




    <script>
        $(function () {
            $(".edit").on("click",function () {
                $(this).closest(".table_content").css("color","#db0");
            });

        });
    </script>

</body>
</html>
