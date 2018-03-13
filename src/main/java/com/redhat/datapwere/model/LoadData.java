package com.redhat.datapwere.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LoadData {

	
	private List<FileMetaData> filesList = new ArrayList<FileMetaData>(); 
	
	private FileMetaData selectedFileMetaData = null;
	
	private long totalRecords = 0l;
	
	public LoadData() {}
	

	
	public List<FileMetaData> getFilesList() {
		return filesList;
	}



	public void setFilesList(List<FileMetaData> filesList) {
		this.filesList = filesList;
	}



	public FileMetaData getSelectedFileMetaData() {
		return selectedFileMetaData;
	}



	public void setSelectedFileMetaData(FileMetaData selectedFileMetaData) {
		this.selectedFileMetaData = selectedFileMetaData;
	}



	public long getTotalRecords() {
		return totalRecords;
	}



	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
