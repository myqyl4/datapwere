package com.redhat.datapwere.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opencsv.bean.CsvBindByName;



@Entity
@Table(name = "DTUSER", schema = "HR")
public class DTUser {

	private long id;
	
	@CsvBindByName
	private String guid = null;
	
	@CsvBindByName
	private String firstname = null;
	
	@CsvBindByName
	private String lastname = null;
	
	@CsvBindByName
	private String fullname = null;
	
	@CsvBindByName
	private long age = 0;
	
	@CsvBindByName
	private String birthday = null;
	
	@CsvBindByName
	private String email = null;
	
	@CsvBindByName
	private String gender = null;
	
	@CsvBindByName
	private String filename = null;
	
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
	
	@Transient
	private transient PerfStats perfStats = new PerfStats();
	
	@Transient
	private transient Number generatedKey = 0l;
	
	@Transient
	private transient boolean status = false;
	
	

	public DTUser() {}

	
	@Id
	@GeneratedValue
	@Column(name= "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}



	@Column(name= "guid", unique = true, nullable = false)
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name= "firstname", unique = true, nullable = false)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	
	@Column(name= "lastname", unique = true, nullable = false)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name= "fullname")
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name= "age")
	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	@Column(name= "birthday")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name= "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name= "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name= "filename")
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	@Column(name= "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name= "street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name= "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name= "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name= "createdBy")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name= "created")
	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name= "modifiedBy")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name= "modified")
	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	
	@Transient
	public PerfStats getPerfStats() {
		return perfStats;
	}


	public void setPerfStats(PerfStats perfStats) {
		this.perfStats = perfStats;
	}

	@Transient
	public Number getGeneratedKey() {
		return generatedKey;
	}


	public void setGeneratedKey(Number generatedKey) {
		this.generatedKey = generatedKey;
	}

	@Transient
	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
