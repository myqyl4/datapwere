package com.redhat.datapwere.data;

import java.sql.SQLException;
import java.util.List;

import com.redhat.datapwere.model.PerfStats;

public interface PerfStatsDAO {
	PerfStats getPerfStatsById(Long id) throws SQLException;

	List<PerfStats> getAllPerfStats() throws SQLException;

	boolean deletePerfStats(PerfStats perfStats) throws SQLException;

	boolean updatePerfStats(PerfStats perfStats) throws SQLException;

	boolean createPerfStats(PerfStats perfStats) throws SQLException;

	long createPerfStatsGetID(PerfStats perfStats) throws SQLException;

	List<Long> getAllPerfStatsIds() throws SQLException;

	List<String> findLoadedFilesList() throws SQLException;

	List<PerfStats> findPerfStatsByFilename(String filename) throws SQLException;

	List<PerfStats> findPerfStatsByOperationType(String operationType) throws SQLException;

	List<PerfStats> findPerfStatsByStrategyType(String strategy) throws SQLException;

	List<PerfStats> findPerfStatsByOperationTypeAndStrategyType(String operationType, String strategy) throws SQLException;

}
