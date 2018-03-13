<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>
<div class="container" >
    <div class="well lead">Update Data</div>
    
    
    <form method="GET" action="selectAndUpdateData">
		<table class="table table-striped">
			<caption>Select File and Load Data</caption>
			<thead>
				<tr>
					<th></th>
					<th>File Name</th>
					<th>Number of Records</th>
					<th>Total</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${loadData.filesList}" var="fileMetaData">
					<tr>
						<!-- <td><input type="hidden" name="fileIndex" id="fileIndex"  value="${fileMetaData.fileIndex}"/></td> -->
						<td><input type="radio" name="fileIndex" id="fileIndex"  value="${fileMetaData.fileIndex}" checked="checked"/></td>
						<td>${fileMetaData.filename}</td>
						<td>${fileMetaData.numberOfRecords}</td>
						<td>${loadData.totalRecords}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
			<label  class="radio-inline">
   				<input type="submit" class="btn btn-success" value="Updated The Selected Records"/>
 			</label>
	</form>
    
</div>
<%@ include file="common/footer.jsp"%>