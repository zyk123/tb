
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.flash.toolbar.omp.common.util.Pagination"%>
<%--<div style="border-style:solid; border-bottom-color:cornflowerblue; border-bottom-width: 2px; borheight: 100px">--%>
<div>
    <script type="text/javascript">
        function changePageRows(obj) {
            document.getElementById("rowsPeerPage").value = obj.value;
//              会导致index改变
            document.getElementById("currentPageNum").value = 1;
            document.getElementById("searchForm").submit();
        }

        function changePageNum(obj) {
            var value = $(obj).attr("value");
            document.getElementById("currentPageNum").value = value;
            $("#searchForm").submit();
        }
        function clearPage() {
            document.getElementById("currentPageNum").value = 1;
        }
    </script>
    <%--<div style="margin:0 auto;width:50px;text-align:left">--%>
    <div style="float:left;width:auto;height: 30px;margin-top: 20px">
        <span><s:text name="PER_PAGE"/>
        <select name="rowsPeerPage" onchange="changePageRows(this)" style="width: 80px">
            <c:forEach var="pageRows" items="${page.pageRowsList}">
                <option value="${pageRows}" ${pageRows==page.rowsPeerPage?'selected="selected"':''}> ${pageRows}
                </option>
            </c:forEach>
        </select>
          <s:text name="NUMBER_DATE"/>  
            </span>
        <span>
        	<s:text name="TOTAL_NUM">
        		<s:param>${page.totalRowsCount }</s:param>
        	</s:text>
        </span>
    </div>

    <%--<c:set var="begin" value="${custList.rowCount/noOfRows}"/>--%>
    <%--<fmt:formatNumber var="range" value="${(page.currentPageNum/5)}" maxFractionDigits="0"/>--%>

    <%
        Pagination pagination = (Pagination) request.getAttribute("page");
        int indexCount = pagination.getDisplayIndexCount();
        int range = (pagination.getCurrentPageNum() - 1) / 5;
        int begin = range * indexCount + 1;
        int end = range * indexCount + indexCount;
        request.setAttribute("range", range);
        request.setAttribute("begin", begin);
        request.setAttribute("end", end);
    %>
    <%--显示current相互关联的5个。 1-5 6-10--%>
    <%--<div style="margin:0 auto;text-align:right">--%>
    <div style="float:right;height: 30px">
        <div class="pagination">
            <ul>
                <c:if test="${page.currentPageNum<=page.displayIndexCount}">
                    <li class=disabled><a href="#">&laquo;</a></li>
                </c:if>
                <c:if test="${page.currentPageNum>page.displayIndexCount}">
                    <li><a href="#" onclick="changePageNum(this)"
                           value="${begin-1}">&laquo;</a></li>
                </c:if>

                <c:forEach var="t" begin="${begin}" end="${end}">
                    <c:if test="${t==page.currentPageNum}">
                        <li class="active"><a href="#">${t}</a></li>
                    </c:if>
                    <c:if test="${t!=page.currentPageNum &&  t<=page.totalPageNum}">
                        <li><a href="#" onclick="changePageNum(this)" value="${t}">${t}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${page.totalPageNum<=end}">
                    <li class=disabled><a href="#">&raquo;</a></li>
                </c:if>
                <c:if test="${page.totalPageNum-end > 0}">
                    <li><a href="#" onclick="changePageNum(this)"
                           value="${end+1}">&raquo;</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
