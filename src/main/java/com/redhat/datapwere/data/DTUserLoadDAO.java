package com.redhat.datapwere.data;

import java.sql.SQLException;
import java.util.List;

import com.redhat.datapwere.model.DTUser;

public interface DTUserLoadDAO {
	DTUser getDTUserById(Long id) throws SQLException;

	List<DTUser> getAllDTUsers() throws SQLException;

	boolean deleteDTUser(DTUser dtUser) throws SQLException;

	DTUser updateDTUser(DTUser dtUser) throws SQLException;

	DTUser createDTUser(DTUser dtUser) throws SQLException;

	DTUser createDTUserGetID(DTUser dtUser) throws SQLException;

	List<Long> getAllDTUsersIds() throws SQLException;

	long selectCountAllRecords() throws SQLException;

	List<Long> getAllDTUsersIdsByFileName(String filename);

}
