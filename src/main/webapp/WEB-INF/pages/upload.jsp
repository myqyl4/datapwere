<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>
<div class="container" >
    <div class="well lead">Upload File</div>
	<form method="POST" action="uploadFile" enctype="multipart/form-data">
	<table>
		<tr>
			<td>
		File to upload: <input type="file" name="file">
 			</td>
 			</tr>
 			<tr>
 			<td>
		Name: <input type="text" name="name">
 			</td>
 			</tr>
 			<tr>
 			<td>
		<input type="submit" value="Upload">
		</td>
 			</tr>
 			</table>
	</form>	
</div>
<%@ include file="common/footer.jsp"%>