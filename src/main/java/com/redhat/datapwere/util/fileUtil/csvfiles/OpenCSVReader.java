package com.redhat.datapwere.util.fileUtil.csvfiles;


import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;




public class OpenCSVReader {
    private static final String SAMPLE_CSV_FILE_PATH = "testdatacsv.csv";

    public static void main(String[] args) throws IOException {
    	OpenCSVReader openCSVReader = new OpenCSVReader();
    	try {
    		openCSVReader.loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public void loadData() throws IOException {
    	List<String[]> records =  null;
    	try {
			//readRecordsOneByOne(SAMPLE_CSV_FILE_PATH);
    		records = readAllRecordsAtOnce(SAMPLE_CSV_FILE_PATH);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	try {
    		if(records != null) {
    			
    		}
    		
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    

    private void readRecordsOneByOne(String filename) throws IOException, URISyntaxException {
        try (
            //Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
            CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("Guid : " + nextRecord[0]);
                System.out.println("FirstName : " + nextRecord[1]);
                System.out.println("LastName : " + nextRecord[2]);
                System.out.println("FullName : " + nextRecord[3]);
                System.out.println("==========================");
            }
        }
    }

    private List<String[]> readAllRecordsAtOnce(String filename) throws IOException, URISyntaxException {
    	List<String[]> records = null;
        try (
            //Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
            CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading All Records at once into a List<String[]>
            records = csvReader.readAll();
            
            /*
            for (String[] record : records) {
                System.out.println("Guid : " + record[0]);
                System.out.println("FirstName : " + record[1]);
                System.out.println("LastName : " + record[2]);
                System.out.println("FullName : " + record[3]);
                System.out.println("---------------------------");
            }
            */
        }
        
        return records;
    }
}
