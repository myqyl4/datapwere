package com.redhat.datapwere.util.fileUtil.csvfiles;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;



@Service("openCSVParseToBeanWithoutAnnotation")
public class OpenCSVParseToBeanWithoutAnnotation implements IOpenCSVParseToBeanWithoutAnnotation {
    
	
	public static final String SAMPLE_CSV_FILE_PATH = "testdatacsv.csv";
    
    private static Logger log = LoggerFactory.getLogger(OpenCSVParseToBeanWithoutAnnotation.class);

    public static void main(String[] args) throws IOException {

        try {
        		OpenCSVParseToBeanWithoutAnnotation openCSVParseToBeanWithoutAnnotation = new OpenCSVParseToBeanWithoutAnnotation();
        		openCSVParseToBeanWithoutAnnotation.readFile(SAMPLE_CSV_FILE_PATH);
        } catch(Exception e){
        	System.err.println("Error : " + e.getMessage());
        }
    }
    
    
    
    @Override
    public List<MyUser> readFile(String csvFile) throws IOException, URISyntaxException {
    	List<MyUser> myUsers = null;
        try (
            //Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(csvFile).toURI()));
        ) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(MyUser.class);
            String[] memberFieldsToBindTo = {"name", "email", "phoneNo", "country"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            myUsers = csvToBean.parse();
            /*
            for (MyUser myUser : myUsers) {
                System.out.println("Name : " + myUser.getFirstname() + " " + myUser.getLastname());
                System.out.println("Email : " + myUser.getEmail());
                System.out.println("PhoneNo : " + myUser.getPhone());
                System.out.println("Age : " + myUser.getAge());
                System.out.println("---------------------------");
            }
            */
        }
        
        return myUsers;
    }
    
    
}


