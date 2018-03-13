package com.redhat.datapwere.data;

import java.sql.SQLException;
import java.util.List;

import com.redhat.datapwere.model.PerfReports;

public interface PerfReportsDAO {
	PerfReports getPerfReportsById(Long id) throws SQLException;

	List<PerfReports> getAllPerfReports() throws SQLException;

	boolean deletePerfReports(PerfReports perfReports) throws SQLException;

	boolean updatePerfReports(PerfReports perfReports) throws SQLException;

	boolean createPerfReports(PerfReports perfReports) throws SQLException;

	long createPerfReportsGetID(PerfReports perfReports) throws SQLException;

	List<Long> getAllPerfReportsIds() throws SQLException;

	boolean deleteAllPerfReports() throws SQLException;



}
