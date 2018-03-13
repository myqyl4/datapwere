package com.redhat.datapwere.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.redhat.datapwere.model.PerfReports;
import com.redhat.datapwere.model.PerfReportsMapper;

@Repository("perfReportsDAO")
public class PerfReportsDAOImpl implements PerfReportsDAO {

	private JdbcTemplate jdbcTemplate;

	private final String SQL_FIND_PERF_REPORTS = "select * from HR.PERF_REPORTS where id = ?";
	private final String SQL_DELETE_PERF_REPORTS = "DELETE from HR.PERF_REPORTS where id = ?";
	private final String SQL_DELETE_ALL_PERF_REPORTS = "DELETE from HR.PERF_REPORTS";
	
	
	private final String SQL_UPDATE_PERF_REPORTS_2 = "update HR.PERF_REPORTS set modifiedBy = ?, modified = ? where id = ?";
	
	private final String SQL_GET_ALL = "SELECT * from HR.PERF_REPORTS";
	private final String SQL_GET_ALL_ORDER_BY = "SELECT * from HR.PERF_REPORTS ORDER BY OPERATIONTYPE ASC, STRATEGY ASC";
	
	
	private final String SQL_INSERT_PERF_REPORTS = "insert into HR.PERF_REPORTS("
			+ "totalRecords, averageDuration, longestDuration, shortestDuration, totalDuration, operationType, "
			+ "strategy, filename, runDate, itemCount, "
			+ "createdBy, created, modifiedBy, modified"
			+ ") values("
			+ "?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, "
			+ "?, ?, ?, ?"
			+ ")";

	private final String SQL_GET_ALL_IDS = "SELECT distinct(ID) from HR.PERF_REPORTS ORDER BY ID ASC";
	

	
	@Autowired
	public PerfReportsDAOImpl(DataSource dataSource) throws SQLException {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public PerfReports getPerfReportsById(Long id) throws SQLException {
		return jdbcTemplate.queryForObject(SQL_FIND_PERF_REPORTS, new Object[] { id }, new PerfReportsMapper());
	}

	@Override
	public List<PerfReports> getAllPerfReports() {
		return jdbcTemplate.query(SQL_GET_ALL_ORDER_BY, new PerfReportsMapper());
	}
	
	@Override
	public List<Long> getAllPerfReportsIds() throws SQLException {
		//return jdbcTemplate.query(SQL_GET_ALL_IDS, new PerfReportsMapper());
		
		List<Long> ids = jdbcTemplate.queryForList(SQL_GET_ALL_IDS, Long.class);
		if(ids != null) {
			System.out.println("[OUT] getAllPerfReportssIds ids : " + ids.size());
		} else {
			System.out.println("[OUT] getAllPerfReportssIds FOUND 0 IDs : " + ids);
		}
		return ids;
	}
	

	@Override
	public boolean deletePerfReports(PerfReports perfReports) throws SQLException {
		return jdbcTemplate.update(SQL_DELETE_PERF_REPORTS, perfReports.getId()) > 0;
	}

	
	@Override
	public boolean deleteAllPerfReports() throws SQLException {
		return jdbcTemplate.update(SQL_DELETE_ALL_PERF_REPORTS) > 0;
	}
	
	@Override
	public boolean updatePerfReports(PerfReports perfReports) throws SQLException {
		return jdbcTemplate.update(SQL_UPDATE_PERF_REPORTS_2, perfReports.getModifiedBy(), perfReports.getModified(), perfReports.getId()) > 0;
	}

	
	@Override
	public boolean createPerfReports(PerfReports perfReports) throws SQLException {
		
		return jdbcTemplate.update(SQL_INSERT_PERF_REPORTS, 
				perfReports.getTotalRecords(), 
				perfReports.getAverageDuration(),
				perfReports.getLongestDuration(),
				perfReports.getShortestDuration(),
				perfReports.getTotalDuration(),
				perfReports.getOperationType(),
				perfReports.getStrategy(),
				perfReports.getFilename(),
				perfReports.getRunDate(),
				perfReports.getItemCount(),
				perfReports.getCreatedBy(),
				perfReports.getCreated(),
				perfReports.getModifiedBy(),
				perfReports.getModified()) > 0;
	}
	
	
	@Override
	public long createPerfReportsGetID(PerfReports perfReports) throws SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		//System.out.println("[IN] createPerfReportsGetID perfReports : " + perfReports);
		//System.out.println("[IN] createPerfReportsGetID SQL_INSERT_PERF_REPORTS : " + SQL_INSERT_PERF_REPORTS);
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PERF_REPORTS, new String[] { "ID" });
			ps.setLong(1, perfReports.getTotalRecords());
			ps.setLong(2, perfReports.getAverageDuration());
			ps.setLong(3, perfReports.getLongestDuration());
			ps.setLong(4, perfReports.getShortestDuration());
			ps.setLong(5, perfReports.getTotalDuration());
			
			ps.setString(6, perfReports.getOperationType());
			ps.setString(7, perfReports.getStrategy());
			ps.setString(8, perfReports.getFilename());
			ps.setTimestamp(9, perfReports.getRunDate());
			ps.setInt(10, perfReports.getItemCount());

			ps.setString(11, perfReports.getCreatedBy());
			ps.setTimestamp(12, perfReports.getCreated());
			ps.setString(13, perfReports.getModifiedBy());
			ps.setTimestamp(14, perfReports.getModified());
			
			return ps;
		}, keyHolder);

		Number key = keyHolder.getKey();
		System.out.println("[OUT] createPerfReportsGetID perfReports generated id: " + key.longValue());
		//System.out.println("-- loading customer by id --");
		//System.out.println(loadCustomerById(key.longValue()));
		
		jdbcTemplate.getDataSource().getConnection().close();
		
		return key.longValue();
	}

	

}
