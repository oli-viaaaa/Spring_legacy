<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 헤더 부분 인클루드 -->
<%@ include file="../include/header.jsp" %>

		<!-- 제목 -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">게시물 목록</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <!-- 바디(내용) -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        DataTable 3'rd party web grid component
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
            			<!-- start search -->	
		 				<div class='row' style="text-align: center; margin-bottom:10px;">
							<div class="col-lg-12">
								<form id='searchForm' action="<c:url value="/board/boardList.do" />" method='get'>
									<div class="form-group">
						                <label class="control-label col-md-3"></label>
						                <div class="col-md-3">
						                    <input type="text" class="form-control"  name="searchText" id="searchText" value="${pageMaker.cri.searchText }"/>
						                </div>
						                <div class="col-md-6" style="text-align: left;">
						                    <input type="button" id="btnSearch" class='btn btn-info' value="검색" />
						                    <input type="button" class='btn btn-warning' onclick="location.href='<c:url value="/board/boardList.do" />'" value="전체보기" />
						                    <input type="button" class='btn btn-success' value="글쓰기" onclick="location.href='<c:url value="/board/boardWrite.do" />'" />
						                </div>
						            </div>
								</form>   
		 					</div>
						</div> 
            			<!-- end search -->	
                    	<!-- start table contents -->
                    	<div>
	                        <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover" >
	                            <thead>
	                                <tr>
	                                    <th>순서</th>
	                                    <th>번호</th>
	                                    <th>제목</th>
	                                    <th>작성자</th>
	                                    <th>작성일</th>
	                                </tr>
	                            </thead>
	                            <tbody>
									<c:forEach items="${boardList}" var="board" varStatus="i">
										<tr>
											<td align="center">
												<c:out value="${i.count}" />
											</td>
											<td>
												<c:out value="${board.no}" />
											</td>
											<td>
												<a class='move' href='<c:out value="${board.no}"/>'>
													<c:out value="${board.title}" /> 
														<b>[ <c:out value="${board.hit}" /> ]</b>
												</a>
											</td>	
											<td><c:out value="${board.id}" /></td>
											<td>
												<fmt:formatDate pattern="yyyy-mm-dd" value="${board.regDate}" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
	                        </table>
						</div>
						<!-- end table contents -->
						<!-- start Pagination -->						           				
            			<div style="text-align: center;">
							<ul class="pagination">
								<c:if test="${pageMaker.prev}">
									<li class="paginate_button previous"><a
										href="${pageMaker.startPage -1}">Previous</a></li>
								</c:if>
								<c:forEach var="num" begin="${pageMaker.startPage}"
									end="${pageMaker.endPage}">
									<li class="paginate_button  ${pageMaker.cri.pageNum == num ? "active":""} ">
										<a href="${num}">${num}</a>
									</li>
								</c:forEach>
								<c:if test="${pageMaker.next}">
									<li class="paginate_button next"><a
										href="${pageMaker.endPage +1 }">Next</a></li>
								</c:if>
							</ul>
						</div>	
						<!-- end Pagination -->
						<!-- 페이지 번호 클릭시 전달될 폼 -->
						<div>
							<form id='actionForm' action="/board/boardList.do" method='get'>
								<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
								<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
								<input type='hidden' name='searchText'	value='<c:out value="${ pageMaker.cri.searchText}"/>'>
							</form>		
						</div>
                    </div>
                    <!-- panel-body -->
                </div>
                <!-- panel -->
            </div>
            <!-- col-lg-12 -->
        </div>

	<script type="text/javascript">
		$(document).ready(function() {
			
			Fn_Init_Datatable();
			
			var actionForm = $("#actionForm");
			
			// [1] 게시물 제목 클릭 이벤트 처리기(리스너, 핸들러)
			// - 게시물 제목에 href=${board.no}를 걸어놓음. 그리고 그 값을 no라는 name 태그로 서버에 전송
			$(".move").on("click", function(e) {
				e.preventDefault();	// 안막으면 원래 걸려있는 링크로 submit()이 되버림.(a href가 제 기능하지 않도록 방지)
				//console.log('게시물 클릭');
				actionForm.append("<input type='hidden' name='no' value='"
					+ $(this).attr("href")
					+ "'>");
				actionForm.attr("action", "${pageContext.request.contextPath}/board/boardView.do");
				actionForm.submit();
			}); // end of move
			
			// [2] 페이지 번호 클릭 이벤트 처리기(리스너, 핸들러)
			// - 페이지 버튼 클릭시 폼태그에 해당 페이지 번호를 세팅해서 서버로 전달(submit)
			$(".paginate_button a").on("click",	function(e) {
				//alert('페이지 번호 클릭');
				e.preventDefault();	// 안막으면 원래 걸려있는 링크로 submit()이 되버림.(a href가 제 기능하지 않도록 방지)
				actionForm.find("input[name='pageNum']").val($(this).attr("href"));
				actionForm.submit();
			});

	         // [3] 검색 버튼 클릭 이벤트 핸들러(리스너)
	        $('#btnSearch').on('click', function(){

	        	// 검색 키워드 널 체크
	 			let searchText = $('#searchText').val();	// let은 var과 동일한 기능, 변수명 중복 체크해주는 장점.
	 			if(searchText == "" || searchText == null || searchText == undefined || searchText.length < 1 ){
	 				alert('검색어를 입력하세요.');
	 				$("#searchText").focus();
	 				return false;
	 			}
				$('#searchForm').submit();					
			});  
			
		}); // end of ready()
		
		// 그리드 솔루션인 데이터테이블(DataTable) 초기화
		function Fn_Init_Datatable(){
			//datatables setting
			table =  $('#dataTable').removeAttr('width').DataTable({
					select: true,
					mark: true, // Highlight search terms
					aLengthMenu: [
						// Show entries incrementally
						[10, 15, 30, 50, -1],
						[10, 15, 30, 50, "All"]
					],
					dom: 'Bfrtip',
			        buttons: [
			        	  { extend: 'pdf', text: '<i class="fa fa-pencil" aria-hidden="true"> PDF</i>' },
		                  { extend: 'csv', text: '<i class="fas fa-file-csv fa-1x">CSV</i>' },
		                  { extend: 'excel', text: '<i class="fas fa-file-excel" aria-hidden="true">EXCEL</i>' }
			        ],
		        	"scrollCollapse": true,
			    	"processing": true,
			    	"lengthChange": false,
			    	"searching": false,
			    	"order": [[ 1, "desc" ]],	//default order column index and sort direction
			    	"columnDefs": [
			    		{  
		                    "targets": [0],  
		                    "visible": true,  
		                    "searchable": true,
		                    "orderable": true
		                }, 
			    		{  
		                    "targets": [1],  
		                    "visible": true,  
		                    "searchable": true,
		                    "orderable": true
		                },  
		                {  
		                    "targets": [2],  
		                    "visible": true,  
		                    "searchable": true,
		                    "orderable": true
		                },  
		                {  
		                    "targets": [3],  
		                    "visible": true,  
		                    "searchable": true,
		                    "orderable": true
		                },  
		                {  
		                    "targets": [4],  
		                    "visible": true,  
		                    "searchable": true,
		                    "orderable": true
		                }
					], 
			        fixedColumns: true
			 });	//end of datatable
		}		
		
	</script>

 
<!-- 푸터 부분 인클루드 -->
<%@ include file="../include/footer.jsp" %>