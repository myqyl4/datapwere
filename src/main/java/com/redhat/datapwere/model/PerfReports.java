package com.redhat.datapwere.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Entity
@Table(name = "PERF_REPORTS", schema = "HR")
public class PerfReports {

    
	private long id;
    
	private long totalRecords = 0l;
	
	private long averageDuration = 0l;
	
	private long longestDuration = 0l;
	
	private long shortestDuration = 0l;
	
	private long totalDuration = 0l;
	
	private String operationType = null;
	
	private String strategy = null;
	
	private String filename = null;
	
	private Timestamp runDate = null;
	
	private int itemCount = 0;
	
	private String createdBy = null;
	
	private Timestamp created = null;
	
	private String modifiedBy = null;
	
	private Timestamp modified = null;
	

	public PerfReports() {}
	

	@Id
	@GeneratedValue
	@Column(name= "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name= "totalRecords")
	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	@Column(name= "averageDuration")
	public long getAverageDuration() {
		return averageDuration;
	}

	public void setAverageDuration(long averageDuration) {
		this.averageDuration = averageDuration;
	}

	@Column(name= "longestDuration")
	public long getLongestDuration() {
		return longestDuration;
	}

	public void setLongestDuration(long longestDuration) {
		this.longestDuration = longestDuration;
	}

	@Column(name= "shortestDuration")
	public long getShortestDuration() {
		return shortestDuration;
	}

	public void setShortestDuration(long shortestDuration) {
		this.shortestDuration = shortestDuration;
	}
	
	@Column(name= "totalDuration")
	public long getTotalDuration() {
		return totalDuration;
	}


	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}


	@Column(name= "operationType")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@Column(name= "strategy")
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	@Column(name= "filename")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name= "runDate")
	public Timestamp getRunDate() {
		return runDate;
	}

	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
	}
	
	@Column(name= "itemCount")
	public int getItemCount() {
		return itemCount;
	}


	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	
}
