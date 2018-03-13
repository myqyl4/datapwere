package com.redhat.datapwere.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PerfReportsMapper implements RowMapper<PerfReports> {

	public PerfReports mapRow(ResultSet resultSet, int i) throws SQLException {

		PerfReports perfReports = new PerfReports();
		perfReports.setId(resultSet.getLong("id"));
		perfReports.setTotalRecords(resultSet.getLong("totalRecords"));
		perfReports.setAverageDuration(resultSet.getLong("averageDuration"));
		perfReports.setLongestDuration(resultSet.getLong("longestDuration"));
		perfReports.setShortestDuration(resultSet.getLong("shortestDuration"));
		perfReports.setTotalDuration(resultSet.getLong("totalDuration"));
		perfReports.setOperationType(resultSet.getString("operationType"));
		perfReports.setStrategy(resultSet.getString("strategy"));
		perfReports.setFilename(resultSet.getString("filename"));
		perfReports.setRunDate(resultSet.getTimestamp("runDate"));
		perfReports.setItemCount(resultSet.getInt("itemCount"));
		perfReports.setCreated(resultSet.getTimestamp("created"));
		perfReports.setCreatedBy(resultSet.getString("createdBy"));
		perfReports.setModified(resultSet.getTimestamp("modified"));
		perfReports.setModifiedBy(resultSet.getString("modifiedBy"));
		return perfReports;
	}
}
