package com.redhat.datapwere.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DTUserMapper implements RowMapper<DTUser> {

	public DTUser mapRow(ResultSet resultSet, int i) throws SQLException {

		DTUser dtUser = new DTUser();
		dtUser.setId(resultSet.getLong("id"));
		dtUser.setGuid(resultSet.getString("guid"));
		dtUser.setFirstname(resultSet.getString("firstname"));
		dtUser.setLastname(resultSet.getString("lastname"));
		dtUser.setAge(resultSet.getLong("age"));
		dtUser.setFullname(resultSet.getString("fullname"));
		dtUser.setGender(resultSet.getString("gender"));
		dtUser.setFilename(resultSet.getString("filename"));
		dtUser.setBirthday(resultSet.getString("birthday"));
		dtUser.setPhone(resultSet.getString("phone"));
		dtUser.setStreet(resultSet.getString("street"));
		dtUser.setState(resultSet.getString("state"));
		dtUser.setZip(resultSet.getString("zip"));
		dtUser.setCreated(resultSet.getTimestamp("created"));
		dtUser.setCreatedBy(resultSet.getString("createdBy"));
		dtUser.setModified(resultSet.getTimestamp("modified"));
		dtUser.setModifiedBy(resultSet.getString("modifiedBy"));
		return dtUser;
	}
}
