<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>

<div class="container">
	<form method="GET" action="selectFileAndLoadData">
		<table class="table table-striped">
			<caption>Select File and Load Data</caption>
			<thead>
				<tr>
					<th></th>
					<th>File Name</th>
					<th>FilePath</th>
					<th>File Size</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${loadData.filesList}" var="fileMetaData">
					<tr>
						<td><input type="radio" name="fileIndex" id="fileIndex"  value="${fileMetaData.fileIndex}" checked="checked"/></td>
						<td>${fileMetaData.filename}</td>
						<td>${fileMetaData.filepath}</td>
						<td>${fileMetaData.filesize}</td>
						<!-- <td><a href="selectFileAndLoadData?fileIndex=${fileMetaData.fileIndex}" class="btn btn-success">Load This File</a></td> -->
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
			<label  class="radio-inline">
   				<input type="submit" class="btn btn-success" value="Load This Selected File"/>
 			</label>
	</form>

</div>


<%@ include file="common/footer.jsp"%>