<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>
<div class="container">
	<div class="well lead">View Reports</div>

<div class="container">

		<table class="table table-striped">
			<caption>Performance Report by Operation & Strategy</caption>
			<thead>
				<tr>
					<th></th>
					<th>Operation</th>
					<th>Strategy</th>
					<th>Num of Records</th>
					<th>Average Duration</th>
					<th>Longest Duration</th>
					<th>Shortest Duration</th>
					<th>Total Duration</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${perfReportsList}" var="perfReports">
					<tr>
						<td></td>
						<td>${perfReports.operationType}</td>
						<td>${perfReports.strategy}</td>
						<td>${perfReports.totalRecords}</td>
						<td>${perfReports.averageDuration}</td>
						<td>${perfReports.longestDuration}</td>
						<td>${perfReports.shortestDuration}</td>
						<td>${perfReports.totalDuration}</td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		

</div>



<%@ include file="common/footer.jsp"%>