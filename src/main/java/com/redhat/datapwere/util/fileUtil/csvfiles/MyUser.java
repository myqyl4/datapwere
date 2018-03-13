package com.redhat.datapwere.util.fileUtil.csvfiles;

import java.sql.Timestamp;
import java.util.Date;


public class MyUser {
	private String guid = null;
	private String firstname = null;
	private String lastname = null;
	private String fullname = null;
	private short age = 0;
	private String birthday = null;
	private String email = null;
	private String gender = null;
	private String phone = null;
	private String street = null;
	private String state = null;
	private String zip = null;
	private String createdBy = null;
	private Timestamp created = null;
	private String modifiedBy = null;
	private Timestamp modified = null;


    public MyUser() {

    }

    public MyUser(String guid, String firstname, String lastname, String fullname, short age, String birthday, 
    		String email, String gender, String phone, String street, String state, String zip,
    		String createdBy, Timestamp created, String modifiedBy, Timestamp modified) {
        this.guid = guid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.age = age;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
        
        this.phone = phone;
        this.street = street;
        this.state = state;
        this.zip = zip;
        this.createdBy = createdBy;
        this.created = created;
        this.modifiedBy = modifiedBy;
        this.modified = modified;
    }

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
