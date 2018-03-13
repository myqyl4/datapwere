package com.redhat.datapwere.model;

import java.sql.Timestamp;

public class DTUserUtil {

	public static String DEFAULT_USER = "pwere";
	
	public static void cleanupDTUser(DTUser dtUser) {
		
		dtUser.setFullname(dtUser.getFirstname() + " " + dtUser.getLastname());
		
		dtUser.setCreatedBy(DEFAULT_USER);
		dtUser.setCreated(new Timestamp(System.currentTimeMillis()));
		
		dtUser.setModifiedBy(DEFAULT_USER);
		dtUser.setModified(new Timestamp(System.currentTimeMillis()));
		
		String email = dtUser.getFirstname() + "." + dtUser.getLastname();
		email = email + dtUser.getEmail().substring(dtUser.getEmail().indexOf("@"));
		dtUser.setEmail(email);
	}

}
