package com.redhat.datapwere.util.fileUtil.csvfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.redhat.datapwere.data.AppConfig;
import com.redhat.datapwere.data.DTUserLoadDAO;
import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.DTUserUtil;
import com.redhat.datapwere.model.FileMetaData;



@Service("openCSVReadAndParseToBean")
public class OpenCSVReadAndParseToBean implements IOpenCSVReadAndParseToBean{
    
	
	public static final String SAMPLE_CSV_FILE_PATH = "testdatacsv.csv";

	
	private static Logger log = LoggerFactory.getLogger(OpenCSVReadAndParseToBean.class);
	
	
    public static void main(String[] args) throws IOException {
    	List<DTUser> dtUsers = null;
        try {
        	OpenCSVReadAndParseToBean openCSVReadAndParseToBean = new OpenCSVReadAndParseToBean();
        	//openCSVReadAndParseToBean.readFile(SAMPLE_CSV_FILE_PATH);
        	dtUsers = openCSVReadAndParseToBean.readFileToDTUser(SAMPLE_CSV_FILE_PATH);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        if(dtUsers != null) {
        	
        	try {
	        	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	
	        	DTUserLoadDAO dtUserLoadDAO = context.getBean(DTUserLoadDAO.class);
	
	        	for (DTUser dtUser : dtUsers) {
	        		DTUserUtil.cleanupDTUser(dtUser);
	        		//System.out.println("\nCreating DTUser: " + dtUser);
	        		dtUser= dtUserLoadDAO.createDTUserGetID(dtUser);
	        		Number id = dtUser.getGeneratedKey();
	        		//System.out.println("\nCreated DTUser: " + id);
	        	}
		
	        	List<DTUser> dtUsersFound = dtUserLoadDAO.getAllDTUsers();
		    	if(dtUsersFound != null) {
		    		log.info("\nFound : " + dtUsersFound.size() + " in the DB");
		    		System.out.println("\nFound : " + dtUsersFound.size() + " in the DB");
		    	}
	
	
	    		context.close();
    		
        	}catch(SQLException sqle){
        		sqle.printStackTrace();
            }
        }
        
    }
    
    
    
    @Override
    public List<CSVUser> readFile(String filename) throws IOException, URISyntaxException {
    	System.out.println("[IN] readFile");
    	List<CSVUser> csvUsers = null;
        try (
            //Reader reader = Files.newBufferedReader(Paths.get(filename));
        		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
        ) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVUser.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            csvUsers = csvToBean.parse();

            for (CSVUser csvUser : csvUsers) {
                System.out.println("Name : " + csvUser.getFirstname() + " " + csvUser.getLastname());
                System.out.println("Email : " + csvUser.getEmail());
                System.out.println("PhoneNo : " + csvUser.getPhone());
                System.out.println("Age : " + csvUser.getAge());
                System.out.println("==========================");
            }
        }
        System.out.println("[OUT] readFile");
        return csvUsers;
    }
    
    
    
    
    
    
    @Override
    public List<DTUser> readFileToDTUser(String filename) throws IOException, URISyntaxException {
    	List<DTUser> dtUsers = null;
    	log.info("[IN] readFileToDTUser filename :" + filename);
    	System.out.println("[IN] readFileToDTUser filename :" + filename);
        try (
            //Reader reader = Files.newBufferedReader(Paths.get(filename));
        		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
        ) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(DTUser.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            dtUsers = csvToBean.parse();
            /*
            for (DTUser dtUser : dtUsers) {
                System.out.println("Name : " + dtUser.getFirstname() + " " + dtUser.getLastname());
                System.out.println("Email : " + dtUser.getEmail());
                System.out.println("PhoneNo : " + dtUser.getPhone());
                System.out.println("Age : " + dtUser.getAge());
                System.out.println("==========================");
            }
            */
            if(dtUsers != null) {
            	log.info("[OUT] readFileToDTUser dtUsers :" + dtUsers.size());
            	System.out.println("[OUT] readFileToDTUser dtUsers :" + dtUsers.size());
            }
        }
        
        return dtUsers;
    }
    
    
    
    @Override
    public List<DTUser> readFileByPathToDTUser(FileMetaData fileMetaData) throws IOException, URISyntaxException {
    	return readFileByPathToDTUser(fileMetaData.getFilepath(), fileMetaData.getFilename());
    }
    
    
    @Override
    public List<DTUser> readFileByPathToDTUser(String filepath, String filename) throws IOException, URISyntaxException {
    	List<DTUser> dtUsers = null;
    	//String fname = null;
    	log.info("[IN] readFileToDTUser filename :" + filename);
    	System.out.println("[IN] readFileToDTUser filename :" + filename);

        	Reader reader = null;
            //Reader reader = Files.newBufferedReader(Paths.get(filename));
        	if(filepath.indexOf(filename)>= 0) {
        		reader = new BufferedReader(new FileReader(filepath));
        	} else {
        		reader = new BufferedReader(new FileReader(filepath+filename));
        	}

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(DTUser.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            dtUsers = csvToBean.parse();
            /*
            for (DTUser dtUser : dtUsers) {
                System.out.println("Name : " + dtUser.getFirstname() + " " + dtUser.getLastname());
                System.out.println("Email : " + dtUser.getEmail());
                System.out.println("PhoneNo : " + dtUser.getPhone());
                System.out.println("Age : " + dtUser.getAge());
                System.out.println("==========================");
            }
            */
            if(dtUsers != null) {
            	log.info("[OUT] readFileToDTUser dtUsers :" + dtUsers.size());
            	System.out.println("[OUT] readFileToDTUser dtUsers :" + dtUsers.size());
            }

        
        return dtUsers;
    }
    
    
    
}


