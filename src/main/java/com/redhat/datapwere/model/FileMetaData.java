package com.redhat.datapwere.model;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FileMetaData {

	private int fileIndex = -1;
	private String filename = null;
	private String filepath = null;
	private long filesize = 0l;
	private long numberOfRecords = 0l;
	private Timestamp lastModified = null;
	private String createdBy = null;
	private boolean selected = false;

	public FileMetaData() {
	}

	
	public int getFileIndex() {
		return fileIndex;
	}


	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public long getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(long numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
