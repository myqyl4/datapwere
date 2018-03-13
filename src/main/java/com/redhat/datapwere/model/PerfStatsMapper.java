package com.redhat.datapwere.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PerfStatsMapper implements RowMapper<PerfStats> {

	public PerfStats mapRow(ResultSet resultSet, int i) throws SQLException {

		PerfStats perfStats = new PerfStats();
		perfStats.setId(resultSet.getLong("id"));
		perfStats.setStartTime(resultSet.getLong("startTime"));
		perfStats.setEndTime(resultSet.getLong("endTime"));
		perfStats.setDuration(resultSet.getLong("duration"));
		perfStats.setOperationType(resultSet.getString("operationType"));
		perfStats.setStrategy(resultSet.getString("strategy"));
		perfStats.setFilename(resultSet.getString("filename"));
		perfStats.setItemCount(resultSet.getInt("itemCount"));
		perfStats.setRunDate(resultSet.getTimestamp("runDate"));
		perfStats.setCreated(resultSet.getTimestamp("created"));
		perfStats.setCreatedBy(resultSet.getString("createdBy"));
		perfStats.setModified(resultSet.getTimestamp("modified"));
		perfStats.setModifiedBy(resultSet.getString("modifiedBy"));
		return perfStats;
	}
}
