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
@Table(name = "PERF_STATS", schema = "HR")
public class PerfStats {

	private long id;
	
	private long startTime = 0l;
	
	private long endTime = 0l;
	
	private long duration = 0l;
	
	private String operationType = null;
	
	private String strategy = null;
	
	private String filename = null;
	
	private int itemCount = 0;
	
	private Timestamp runDate = null;
	
	private String createdBy = null;
	
	private Timestamp created = null;
	
	private String modifiedBy = null;
	
	private Timestamp modified = null;

	
	@Id
	@GeneratedValue
	@Column(name= "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name= "startTime")
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Column(name= "endTime")
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Column(name= "duration")
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
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

	@Column(name= "itemCount")
	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	@Column(name= "runDate")
	public Timestamp getRunDate() {
		return runDate;
	}

	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
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
