package com.redhat.datapwere.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.resource.cci.ResultSet;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.DTUserMapper;
import com.redhat.datapwere.model.PerfReportsMapper;
import com.redhat.datapwere.model.PerfStatsMapper;

@Repository("dtUserLoadDAO")
public class DTUserLoadDAOImpl implements DTUserLoadDAO {

	private JdbcTemplate jdbcTemplate;

	private final String SQL_FIND_DTUSER = "select * from HR.DTUSER where id = ?";
	private final String SQL_DELETE_DTUSER = "delete from HR.DTUSER where id = ?";
	private final String SQL_UPDATE_DTUSER = "update HR.DTUSER set firstname = ?, lastname = ?, age  = ? where id = ?";
	private final String SQL_UPDATE_DTUSER_2 = "update HR.DTUSER set modifiedBy = ?, modified = ? where id = ?";
	
	private final String SELECT_COUNT_ALL = "SELECT count(*) FROM HR.DTUSER";
	private final String SELECT_COUNT_BY_ORIGIN = "SELECT count(*) FROM HR.DTUSER WHERE origin = ?";
	
	
	private final String SQL_GET_ALL = "select * from HR.DTUSER";
	private final String SQL_INSERT_DTUSER = "insert into HR.DTUSER(guid, firstname, lastname, fullname, "
			+ "age, birthday, email, gender, filename, phone, street, state, zip, "
			+ "createdBy, created, modifiedBy, modified"
			+ ") values("
			+ "?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?"
			+ ")";

	private final String SQL_GET_ALL_IDS = "SELECT distinct(ID) from HR.DTUSER ORDER BY ID ASC";
	
	
	private final String SQL_GET_ALL_IDS_BY_FILENAME = "SELECT distinct(ID) from HR.DTUSER WHERE FILENAME = ?";
	
	
	@Autowired
	public DTUserLoadDAOImpl(DataSource dataSource) throws SQLException {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public DTUser getDTUserById(Long id) throws SQLException {
		long startTime = System.currentTimeMillis();
		
		DTUser dtUser =  jdbcTemplate.queryForObject(SQL_FIND_DTUSER, new Object[] { id }, new DTUserMapper());
		
		long endTime = System.currentTimeMillis();
		
		dtUser.getPerfStats().setStartTime(startTime);
		dtUser.getPerfStats().setEndTime(endTime);
		dtUser.getPerfStats().setDuration((endTime - startTime));
		//dtUser.setStatus(status);
		
		return dtUser;
	}

	@Override
	public List<DTUser> getAllDTUsers() {
		return jdbcTemplate.query(SQL_GET_ALL, new DTUserMapper());
	}
	
	@Override
	public List<Long> getAllDTUsersIds() throws SQLException {
		//return jdbcTemplate.query(SQL_GET_ALL_IDS, new DTUserMapper());
		
		List<Long> ids = jdbcTemplate.queryForList(SQL_GET_ALL_IDS, Long.class);
		if(ids != null) {
			System.out.println("[OUT] getAllDTUsersIds ids : " + ids.size());
		} else {
			System.out.println("[OUT] getAllDTUsersIds FOUND 0 IDs : " + ids);
		}
		return ids;
	}
	

	@Override
	public boolean deleteDTUser(DTUser dtUser) throws SQLException {
		return jdbcTemplate.update(SQL_DELETE_DTUSER, dtUser.getId()) > 0;
	}

	@Override
	public DTUser updateDTUser(DTUser dtUser) throws SQLException {
		long startTime = System.currentTimeMillis();
		boolean status = jdbcTemplate.update(SQL_UPDATE_DTUSER_2, dtUser.getModifiedBy(), dtUser.getModified(), dtUser.getId()) > 0;
		
		long endTime = System.currentTimeMillis();
		
		dtUser.getPerfStats().setStartTime(startTime);
		dtUser.getPerfStats().setEndTime(endTime);
		dtUser.getPerfStats().setDuration((endTime - startTime));
		dtUser.setStatus(status);
		
		return dtUser;
	}

	
	@Override
	public DTUser createDTUser(DTUser dtUser) throws SQLException {
		long startTime = System.currentTimeMillis();
		boolean status = jdbcTemplate.update(SQL_INSERT_DTUSER, 
				dtUser.getGuid(),
				dtUser.getFirstname(), 
				dtUser.getLastname(),
				dtUser.getFullname(),
				dtUser.getAge(),
				dtUser.getBirthday(),
				dtUser.getEmail(),
				dtUser.getGender(),
				dtUser.getFilename(),
				dtUser.getPhone(),
				dtUser.getStreet(),
				dtUser.getState(),
				dtUser.getZip(),
				dtUser.getCreatedBy(),
				dtUser.getCreated(),
				dtUser.getModifiedBy(),
				dtUser.getModified()) > 0;
				
		long endTime = System.currentTimeMillis();
		
		dtUser.getPerfStats().setStartTime(startTime);
		dtUser.getPerfStats().setEndTime(endTime);
		dtUser.getPerfStats().setDuration((endTime - startTime));
		dtUser.setStatus(status);
		
		return dtUser;
	}
	
	
	@Override
	public DTUser createDTUserGetID(DTUser dtUser) throws SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		//System.out.println("[IN] createDTUserGetID dtUser : " + dtUser);
		//System.out.println("[IN] createDTUserGetID SQL_INSERT_DTUSER : " + SQL_INSERT_DTUSER);
		
		long startTime = System.currentTimeMillis();
		
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DTUSER, new String[] { "ID" });
			ps.setString(1, dtUser.getGuid());
			ps.setString(2, dtUser.getFirstname());
			ps.setString(3, dtUser.getLastname());
			
			ps.setString(4, dtUser.getFullname());
			ps.setLong(5, dtUser.getAge());
			ps.setString(6, dtUser.getBirthday());
			
			ps.setString(7, dtUser.getEmail());
			ps.setString(8, dtUser.getGender());
			ps.setString(9, dtUser.getFilename());
			ps.setString(10, dtUser.getPhone());
			
			ps.setString(11, dtUser.getStreet());
			ps.setString(12, dtUser.getState());
			ps.setString(13, dtUser.getZip());
			
			ps.setString(14, dtUser.getCreatedBy());
			ps.setTimestamp(15, dtUser.getCreated());
			ps.setString(16, dtUser.getModifiedBy());
			ps.setTimestamp(17, dtUser.getModified());
			
			return ps;
		}, keyHolder);
		
		long endTime = System.currentTimeMillis();

		Number key = keyHolder.getKey();
		System.out.println("[OUT] createDTUserGetID dtUser generated id: " + key.longValue());
		//System.out.println("-- loading customer by id --");
		//System.out.println(loadCustomerById(key.longValue()));
		
		jdbcTemplate.getDataSource().getConnection().close();
		
		dtUser.setGeneratedKey(key);

		dtUser.getPerfStats().setStartTime(startTime);
		dtUser.getPerfStats().setEndTime(endTime);
		dtUser.getPerfStats().setDuration((endTime - startTime));
		//dtUser.setStatus(status);
		
		return dtUser;
	}

	
	@Override
	public long selectCountAllRecords() throws SQLException {

		long count = jdbcTemplate.queryForObject(SELECT_COUNT_ALL, Long.class);

		return count;
	}

	@Override
	public List<Long> getAllDTUsersIdsByFileName(String filename) {
		List<Long> ids = jdbcTemplate.queryForList(SQL_GET_ALL_IDS_BY_FILENAME, new Object[] { filename }, Long.class);
		return ids;

	}
	

	
}
