package com.redhat.datapwere.util.fileUtil.csvfiles;


import java.sql.Timestamp;
import java.util.Date;

import com.opencsv.bean.CsvBindByName;


public class CSVUser {

    
    @CsvBindByName
	private String guid = null;
    
    @CsvBindByName
	private String firstname = null;
    
    @CsvBindByName
	private String lastname = null;
    
    @CsvBindByName
	private String fullname = null;
    
    @CsvBindByName
	private short age = 0;
    
    @CsvBindByName
	private String birthday = null;
    
    @CsvBindByName
	private String email = null;
    
    @CsvBindByName
	private String gender = null;
    
    @CsvBindByName
	private String phone = null;
    
    @CsvBindByName
	private String street = null;
    
    @CsvBindByName
	private String state = null;
    
    @CsvBindByName
	private String zip = null;
    
    @CsvBindByName
	private String createdBy = null;
    
    @CsvBindByName
	private Timestamp created = null;
    
    @CsvBindByName
	private String modifiedBy = null;
    
    @CsvBindByName
	private Timestamp modified = null;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
    
    
    


}
