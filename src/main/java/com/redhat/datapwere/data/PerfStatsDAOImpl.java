package com.redhat.datapwere.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.redhat.datapwere.model.PerfStats;
import com.redhat.datapwere.model.PerfStatsMapper;

@Repository("perfStatsDAO")
public class PerfStatsDAOImpl implements PerfStatsDAO {

	private JdbcTemplate jdbcTemplate;

	private final String SQL_FIND_PERF_STATS = "select * from HR.PERF_STATS where id = ?";
	private final String SQL_DELETE_PERF_STATS = "delete from HR.PERF_STATS where id = ?";
	private final String SQL_UPDATE_PERF_STATS_2 = "update HR.PERF_STATS set modifiedBy = ?, modified = ? where id = ?";
	
	private final String SQL_GET_ALL = "select * from HR.PERF_STATS";
	private final String SQL_INSERT_PERF_STATS = "insert into HR.PERF_STATS(startTime, endTime, duration, operationType, "
			+ "strategy, filename, itemCount, runDate, "
			+ "createdBy, created, modifiedBy, modified"
			+ ") values("
			+ "?, ?, ?, ?, "
			+ "?, ?, ?, ?, "
			+ "?, ?, ?, ?"
			+ ")";

	private final String SQL_GET_ALL_IDS = "SELECT distinct(ID) from HR.PERF_STATS ORDER BY ID ASC";
	
	private final String SQL_DISTINCT_FILENAME = "SELECT distinct(FILENAME) from HR.PERF_STATS ORDER BY FILENAME ASC";
	
	
	private final String SQL_FIND_PERF_STATS_BY_FILENAME = "SELECT * FROM HR.PERF_STATS where filename = ? ORDER BY FILENAME ASC, DURATION ASC";
	
	private final String SQL_FIND_PERF_STATS_BY_OPERATION_TYPE = "SELECT * FROM HR.PERF_STATS where operationType = ? ORDER BY OPERATIONTYPE ASC, DURATION ASC";
	
	private final String SQL_FIND_PERF_STATS_BY_STRATEGY_TYPE = "SELECT * FROM HR.PERF_STATS where strategy = ? ORDER BY STRATEGY ASC, DURATION ASC";
	
	private final String SQL_FIND_PERF_STATS_BY_OPERATION_TYPE_AND_STRATEGY_TYPE = "SELECT * FROM HR.PERF_STATS where operationType = ? AND strategy = ? ORDER BY OPERATIONTYPE ASC, STRATEGY ASC, DURATION ASC";
	
	
	@Autowired
	public PerfStatsDAOImpl(DataSource dataSource) throws SQLException {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public PerfStats getPerfStatsById(Long id) throws SQLException {
		return jdbcTemplate.queryForObject(SQL_FIND_PERF_STATS, new Object[] { id }, new PerfStatsMapper());
	}

	@Override
	public List<PerfStats> getAllPerfStats() {
		return jdbcTemplate.query(SQL_GET_ALL, new PerfStatsMapper());
	}
	
	@Override
	public List<Long> getAllPerfStatsIds() throws SQLException {
		//return jdbcTemplate.query(SQL_GET_ALL_IDS, new PerfStatsMapper());
		
		List<Long> ids = jdbcTemplate.queryForList(SQL_GET_ALL_IDS, Long.class);
		if(ids != null) {
			System.out.println("[OUT] getAllPerfStatssIds ids : " + ids.size());
		} else {
			System.out.println("[OUT] getAllPerfStatssIds FOUND 0 IDs : " + ids);
		}
		return ids;
	}
	

	@Override
	public boolean deletePerfStats(PerfStats perfStats) throws SQLException {
		return jdbcTemplate.update(SQL_DELETE_PERF_STATS, perfStats.getId()) > 0;
	}

	@Override
	public boolean updatePerfStats(PerfStats perfStats) throws SQLException {
		return jdbcTemplate.update(SQL_UPDATE_PERF_STATS_2, perfStats.getModifiedBy(), perfStats.getModified(), perfStats.getId()) > 0;
	}

	
	@Override
	public boolean createPerfStats(PerfStats perfStats) throws SQLException {
		
		return jdbcTemplate.update(SQL_INSERT_PERF_STATS, 
				perfStats.getStartTime(), 
				perfStats.getEndTime(),
				perfStats.getDuration(),
				perfStats.getOperationType(),
				perfStats.getStrategy(),
				perfStats.getFilename(),
				perfStats.getItemCount(),
				perfStats.getRunDate(),
				perfStats.getCreatedBy(),
				perfStats.getCreated(),
				perfStats.getModifiedBy(),
				perfStats.getModified()) > 0;
	}
	
	
	@Override
	public long createPerfStatsGetID(PerfStats perfStats) throws SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		//System.out.println("[IN] createPerfStatsGetID perfStats : " + perfStats);
		//System.out.println("[IN] createPerfStatsGetID SQL_INSERT_PERF_STATS : " + SQL_INSERT_PERF_STATS);
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PERF_STATS, new String[] { "ID" });
			ps.setLong(1, perfStats.getStartTime());
			ps.setLong(2, perfStats.getEndTime());
			ps.setLong(3, perfStats.getDuration());
			
			ps.setString(4, perfStats.getOperationType());
			ps.setString(5, perfStats.getStrategy());
			ps.setString(6, perfStats.getFilename());
			ps.setInt(7, perfStats.getItemCount());
			ps.setTimestamp(8, perfStats.getRunDate());

			ps.setString(9, perfStats.getCreatedBy());
			ps.setTimestamp(10, perfStats.getCreated());
			ps.setString(11, perfStats.getModifiedBy());
			ps.setTimestamp(12, perfStats.getModified());
			
			return ps;
		}, keyHolder);

		Number key = keyHolder.getKey();
		System.out.println("[OUT] createPerfStatsGetID perfStats generated id: " + key.longValue());
		//System.out.println("-- loading customer by id --");
		//System.out.println(loadCustomerById(key.longValue()));
		
		jdbcTemplate.getDataSource().getConnection().close();
		
		return key.longValue();
	}

	
	@Override
	public List<String> findLoadedFilesList() throws SQLException {
		List<String> filenames = new ArrayList<String>();

		filenames = jdbcTemplate.queryForList(SQL_DISTINCT_FILENAME, String.class);
		
		return filenames;
	}
	
	
	
	@Override
	public List<PerfStats> findPerfStatsByFilename(String filename) throws SQLException{
		List<PerfStats> perfStats = new ArrayList<PerfStats>();
		
		perfStats = jdbcTemplate.query(SQL_FIND_PERF_STATS_BY_FILENAME, new Object[] { filename }, new PerfStatsMapper());
		
		return perfStats;
	}
	
	
	
	
	@Override
	public List<PerfStats> findPerfStatsByOperationType(String operationType) throws SQLException{
		List<PerfStats> perfStats = new ArrayList<PerfStats>();
		
		perfStats = jdbcTemplate.query(SQL_FIND_PERF_STATS_BY_OPERATION_TYPE, new Object[] { operationType }, new PerfStatsMapper());
		
		return perfStats;
	}
	
	
	@Override
	public List<PerfStats> findPerfStatsByStrategyType(String strategy) throws SQLException{
		List<PerfStats> perfStats = new ArrayList<PerfStats>();
		
		perfStats = jdbcTemplate.query(SQL_FIND_PERF_STATS_BY_STRATEGY_TYPE, new Object[] { strategy }, new PerfStatsMapper());
		
		return perfStats;
	}
	
	
	
	@Override
	public List<PerfStats> findPerfStatsByOperationTypeAndStrategyType(String operationType, String strategy) throws SQLException{
		List<PerfStats> perfStats = new ArrayList<PerfStats>();
		
		perfStats = jdbcTemplate.query(SQL_FIND_PERF_STATS_BY_OPERATION_TYPE_AND_STRATEGY_TYPE, new Object[] { operationType, strategy}, new PerfStatsMapper());
		
		return perfStats;
	}
	
	
}
