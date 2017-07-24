<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="currentPageRow" value="${page.showCount}">
<div id="Footer">
	 <c:if test="${page.totalCount gt 0 }">
		<div class="page_nav">
		     <div class="fl">
		        <select onchange="changePageRows(this.value)">
		            <option value="${page.countPerPage[0]}" <c:if test="${page.showCount eq page.countPerPage[0]}">selected="selected"</c:if>>${page.countPerPage[0]}</option>
		            <option value="${page.countPerPage[1]}" <c:if test="${page.showCount eq page.countPerPage[1]}">selected="selected"</c:if>>${page.countPerPage[1]}</option>
		            <option value="${page.countPerPage[2]}" <c:if test="${page.showCount eq page.countPerPage[2]}">selected="selected"</c:if>>${page.countPerPage[2]}</option>
		        </select>
		        pcs for each page&nbsp;&nbsp;&nbsp;total&nbsp;<span>${page.totalCount}</span>&nbsp;pcs&nbsp;&nbsp;&nbsp;total&nbsp;<span>${page.totalPage}</span>&nbsp;pages
		    </div>
		    <nav>			    	
	    		 <ul class="pagination">	    		 	
        			<c:set var="modulo" value="${page.currentPage mod page.showPage}"/>
        			<c:choose>
        				<c:when test="${(page.currentPage - modulo + page.showPage) gt page.totalPage}">
        					<c:set var="end" value="${page.totalPage }"/>
        				</c:when>
        				<c:otherwise>
        					<c:set var="end" value="${page.currentPage - modulo + page.showPage}"/>
        				</c:otherwise>
        			</c:choose>
        			<c:choose>
        				<c:when test="${modulo eq 0}">
        					<c:set var="prev" value="${page.currentPage -page.showPage * 2 + 1}"/>
        					<c:set var="next" value="${page.currentPage + 1}"/>
        				</c:when>
        				<c:otherwise>
        					<c:set var="prev" value="${page.currentPage - page.showPage - modulo + 1}"/>
        					<c:set var="next" value="${page.currentPage + page.showPage - modulo + 1}"/>
        				</c:otherwise>
        			</c:choose>
	    		 	<c:choose>
	    		 		<c:when test="${page.currentPage le page.showPage }">
	    		 			<li class="disabled">
	    		 				<a href="#" aria-label="Previous">
				                    <span aria-hidden="true">&laquo;</span>
				                </a>
	    		 			</li>
	    		 		</c:when>
	    		 		<c:otherwise>
	    		 			<li>
				                <a href="#" aria-label="Previous" onclick="nextPage(${prev})">
				                    <span aria-hidden="true">&laquo;</span>
				                </a>
				            </li>
	    		 		</c:otherwise>
    		 		</c:choose>
		        	<c:choose>				        	
		        		<c:when test="${page.totalPage gt 5}">
		        			<c:choose>
		        				<c:when test="${modulo eq 0}">
		        					<c:forEach begin="${page.currentPage - 4 }" end="${page.currentPage }" var="i">
					        			<li <c:if test="${page.currentPage eq i}">class="active"</c:if>><a href="#" onclick="nextPage(${i});">${i}</a></li>
						        	</c:forEach>
		        				</c:when>
		        				<c:otherwise>
		        					<c:forEach begin="${page.currentPage - modulo + 1}" end="${end}" var="i">
					        			<li <c:if test="${page.currentPage eq i}">class="active"</c:if>><a href="#" onclick="nextPage(${i});">${i}</a></li>
						        	</c:forEach>
		        				</c:otherwise>
		        			</c:choose>				        			
		        		</c:when>
		        		<c:otherwise>
	        				<c:forEach begin="1" end="${page.totalPage}" var="i">
			        			<li <c:if test="${page.currentPage eq i}">class="active"</c:if>><a href="#" onclick="nextPage(${i});">${i}</a></li>
				        	</c:forEach>
		        		</c:otherwise>
	        		</c:choose>	
		            	<c:choose>
		            		<c:when test="${(page.totalPage mod page.showPage) eq 0}">		            			
					            <c:choose>
				    		 		<c:when test="${(page.totalPage - page.currentPage) lt page.showPage }">
				    		 			<li class="disabled">
				    		 				<a href="#" aria-label="Previous">
							                    <span aria-hidden="true">&raquo;</span>
							                </a>
				    		 			</li>
				    		 		</c:when>
				    		 		<c:otherwise>
				    		 			<li>
							                <a href="#" aria-label="Next" onclick="nextPage(${next})">
							                    <span aria-hidden="true">&raquo;</span>
							                </a>
							            </li>
				    		 		</c:otherwise>
			    		 		</c:choose>			    
		            		</c:when>
		            		<c:otherwise>
		            			<c:choose>
				    		 		<c:when test="${(page.totalPage - page.currentPage) lt page.totalPage mod page.showPage}">
				    		 			<li class="disabled">
				    		 				<a href="#" aria-label="Previous">
							                    <span aria-hidden="true">&raquo;</span>
							                </a>
				    		 			</li>
				    		 		</c:when>
				    		 		<c:otherwise>
				    		 			<li>
							                <a href="#" aria-label="Next" onclick="nextPage(${next})">
							                    <span aria-hidden="true">&raquo;</span>
							                </a>
							            </li>
				    		 		</c:otherwise>
			    		 		</c:choose>		
		            		</c:otherwise>
		            	</c:choose>       
		        </ul>	       
		    </nav>
		</div>
   	</c:if>	
 </div>