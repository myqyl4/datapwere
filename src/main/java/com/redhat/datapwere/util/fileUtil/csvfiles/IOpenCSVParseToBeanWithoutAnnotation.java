package com.redhat.datapwere.util.fileUtil.csvfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface IOpenCSVParseToBeanWithoutAnnotation {

	List<MyUser> readFile(String csvFile) throws IOException, URISyntaxException;

}
