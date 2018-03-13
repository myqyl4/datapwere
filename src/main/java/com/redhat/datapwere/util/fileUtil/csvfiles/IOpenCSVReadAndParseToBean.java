package com.redhat.datapwere.util.fileUtil.csvfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.FileMetaData;

public interface IOpenCSVReadAndParseToBean {

	List<CSVUser> readFile(String filename) throws IOException, URISyntaxException;

	List<DTUser> readFileToDTUser(String filename) throws IOException, URISyntaxException;

	List<DTUser> readFileByPathToDTUser(String filepath, String filename) throws IOException, URISyntaxException;

	List<DTUser> readFileByPathToDTUser(FileMetaData fileMetaData) throws IOException, URISyntaxException;

}
