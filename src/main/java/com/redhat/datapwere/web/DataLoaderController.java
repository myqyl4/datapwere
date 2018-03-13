package com.redhat.datapwere.web;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.redhat.datapwere.data.DTUserLoadDAO;
import com.redhat.datapwere.data.PerfStatsDAO;
import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.FileMetaData;
import com.redhat.datapwere.model.LoadData;
import com.redhat.datapwere.util.fileUtil.csvfiles.OpenCSVReadAndParseToBean;
import com.redhat.datapwere.web.helpers.InstExecutor;
import com.redhat.datapwere.web.helpers.UpdtExecutor;



@Controller
public class DataLoaderController {

	@Autowired
	protected OpenCSVReadAndParseToBean openCSVReadAndParseToBean;
	
	@Value("${file.upload.tmp.folder}")
	private String fileUploadTmpFolder;
	
	@Value("${file.to.upload.name}")
	private String fileToUploadName;
	
	@Value("${number.of.inst.threads}")
	private Integer maxThreads;
	
	@Value("${number.of.inst.pool}")
	private Integer maxThreadPool;
	
	@Value("${number.of.core.pool}")
	private Integer coreThreadPool;
	
	@Value("${file.to.upload.ext}")
	private String fileToUploadExtension;
	
	
	@Value("${file.upload.complete.ext}")
	private String fileUploadCompleteExtension;
	
	@Value("${file.upload.err.ext}")
	private String fileUploadErrExtension;
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;
	
	
	@Autowired
	protected InstExecutor instExecutor;
	
	@Autowired
	protected UpdtExecutor updtExecutor;
	
	@Autowired
	protected DTUserLoadDAO dtUserLoadDAO;
	
	@Autowired
	protected PerfStatsDAO perfStatsDAO;

	
	
	private static Logger log = LoggerFactory.getLogger(DataLoaderController.class);
	
	private LoadData loadData = new LoadData();
	
	
	
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ModelAndView load(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("load");
		
		System.out.println("DataLoaderController fileToUploadExtension : " + fileToUploadExtension);
		
		loadData.getFilesList().clear();
		
		List<File> files = findFiles(fileUploadTmpFolder);

		List<FileMetaData> fileMetaDatas = generateFileMetadata(files);

		loadData.getFilesList().addAll(fileMetaDatas);

		
		mav.addObject("loadData", loadData);
		System.out.println("[OUT] DataLoaderController:.getModel();");
		return mav;
	}
	
	
	
	public List<File> findFiles(String dir) {
		final File folder = new File(dir);
		List<File> files = listFilesForFolder(folder, fileToUploadName, fileToUploadExtension, null);

		if(files != null && files.size() > 0) {
			System.out.println("DataLoaderController files : " + files.size());
		}
		
		return files;
	}
	
	
	
	public List<FileMetaData> generateFileMetadata(List<File> files) {
		List<FileMetaData> fileMetaDatas = new ArrayList<FileMetaData>();

		if(files != null && files.size() > 0) {
			int fileIndex = 0;
			System.out.println("DataLoaderController files : " + files.size());
			for(File file : files) {
				FileMetaData fileMetaData = new FileMetaData();
				fileMetaData.setFileIndex(fileIndex++);
				fileMetaData.setFilename(file.getName());
				fileMetaData.setFilesize(file.length());
				fileMetaData.setFilepath(file.getAbsolutePath());
				fileMetaData.setLastModified(new Timestamp(file.lastModified()));
				
				loadData.getFilesList().add(fileMetaData);
				System.out.println("DataLoaderController fileMetaData : " + fileMetaData);
			}
		}
		
		return fileMetaDatas;
	}
	
	
	//@RequestMapping(value = "/selectFileAndLoadData", method = RequestMethod.GET)
	//public ModelAndView selectFileAndLoadData(ModelMap model, @RequestParam int fileIndex) {
	
		
	@RequestMapping(value = "/selectFileAndLoadData", method = RequestMethod.GET)
	public ModelAndView selectFileAndLoadData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileIndex") String fileIndex) {
	
		List<DTUser> dtUsers = null;
		int selectedFileIndex = -1;
		try {
			System.out.println("[IN] selectFileAndLoadData : fileIndex :" + fileIndex);
			if(StringUtils.isNotEmpty(fileIndex)) {
				selectedFileIndex = Integer.parseInt(fileIndex);
			}
			if(selectedFileIndex >= 0) {
				//LoadData loadData = (LoadData) model.get("loadData");
				FileMetaData selectedFileMetaData = loadData.getFilesList().get(selectedFileIndex);
				selectedFileMetaData.setSelected(true);
				loadData.setSelectedFileMetaData(selectedFileMetaData);
	
				
				dtUsers =  openCSVReadAndParseToBean.readFileByPathToDTUser(selectedFileMetaData);
				
				
				
				if(dtUsers != null) {
					ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap = populateDataMap(dtUsers);
					loadDataRecords(dtUserConcurrentHashMap, selectedFileMetaData.getFilename());
					loadData.getSelectedFileMetaData().setNumberOfRecords(dtUsers.size());
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		ModelAndView mav = new ModelAndView("load", "loadData", loadData);
	    System.out.println("[OUT] DataLoaderController: fileUploadTmpFolder :" + fileUploadTmpFolder);
		
		
	    return mav;
	}
	
	
	@RequestMapping(value = "/loadData", method = RequestMethod.GET)
	public ModelAndView loadData(HttpServletRequest request, HttpServletResponse response) {
	
		List<DTUser> dtUsers = null;
		
		try {
			FileMetaData selectedFileMetaData = loadData.getSelectedFileMetaData();
			dtUsers =  openCSVReadAndParseToBean.readFileByPathToDTUser(selectedFileMetaData);
			
			if(dtUsers != null) {
				ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap = populateDataMap(dtUsers);
				loadDataRecords(dtUserConcurrentHashMap, selectedFileMetaData.getFilename());
				loadData.getSelectedFileMetaData().setNumberOfRecords(dtUsers.size());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ModelAndView mav = new ModelAndView("load", "loadData", loadData);
	    System.out.println("[OUT] DataLoaderController: fileUploadTmpFolder :" + fileUploadTmpFolder);
		
		
	    return mav;
		
	}
	
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("update");
		
		long totalRecords = 0l;
		
		try {
			loadData.getFilesList().clear();
			List<File> files = findFiles(fileUploadTmpFolder);

			List<FileMetaData> fileMetaDatas = generateFileMetadata(files);

			loadData.getFilesList().addAll(fileMetaDatas);
			
			totalRecords = dtUserLoadDAO.selectCountAllRecords();
			loadData.setTotalRecords(totalRecords);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("loadData", loadData);
		System.out.println("[OUT] DataLoaderController:");
		return mav;
		
	}
	
	
	
	@RequestMapping(value = "/updateData", method = RequestMethod.GET)
	public ModelAndView updateData(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[IN] DataLoaderController: updateData :");
		try {
			runUpdates(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("update", "loadData", loadData);
	    System.out.println("[OUT] DataLoaderController: updateData :");
		
		
	    return mav;
		
	}
	
	
	
	@RequestMapping(value = "/selectAndUpdateData", method = RequestMethod.GET)
	public ModelAndView selectAndUpdateData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileIndex") String fileIndex) {
				
		int selectedFileIndex = -1;
		try {
			System.out.println("[IN] selectFileAndLoadData : fileIndex :" + fileIndex);
			
			if(StringUtils.isNotEmpty(fileIndex)) {
				selectedFileIndex = Integer.parseInt(fileIndex);
			}
	
			if(selectedFileIndex >= 0) {
				//LoadData loadData = (LoadData) model.get("loadData");
				FileMetaData selectedFileMetaData = loadData.getFilesList().get(selectedFileIndex);
				selectedFileMetaData.setSelected(true);
				loadData.setSelectedFileMetaData(selectedFileMetaData);
				runUpdates(selectedFileMetaData);
			} else {
				runUpdates(null);
			}
		} catch (NumberFormatException nfe) {
			// TODO Auto-generated catch block
			nfe.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		ModelAndView mav = new ModelAndView("update", "loadData", loadData);
		System.out.println("[OUT] DataLoaderController: updateData :");
		
		
	    return mav;
	}
	
	

	
	
	protected void loadDataRecords(ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap, String filename) {
		System.out.println("[IN] loadDataRecords :" );
		if(dtUserConcurrentHashMap != null) {
			System.out.println("[IN] loadDataRecords dtUserConcurrentHashMap :" + dtUserConcurrentHashMap.size());
			instExecutor.setCoreThreadPool(coreThreadPool);
			instExecutor.setMaxThreadPool(maxThreadPool);
			instExecutor.setMaxThreads(maxThreads);
			instExecutor.setDatasourceStrategy(datasourceStrategy);
			
			instExecutor.setDtUserLoadDAO(dtUserLoadDAO);
			instExecutor.setPerfStatsDAO(perfStatsDAO);
			instExecutor.setFilename(filename);
			instExecutor.prepareInstData(dtUserConcurrentHashMap);
			
			instExecutor.process();
		}
		
		System.out.println("[OUT] loadDataRecords :" );
	}
	
	
	protected void runUpdates(FileMetaData selectedFileMetaData) throws SQLException {
		System.out.println("[IN] runUpdates :" );
		updtExecutor.setCoreThreadPool(coreThreadPool);
		updtExecutor.setMaxThreadPool(maxThreadPool);
		updtExecutor.setMaxThreads(maxThreads);
		updtExecutor.setDatasourceStrategy(datasourceStrategy);

			
		updtExecutor.setDtUserLoadDAO(dtUserLoadDAO);
		updtExecutor.setPerfStatsDAO(perfStatsDAO);
		//updtExecutor.setFilename(filename);
		updtExecutor.prepareUpdtData(selectedFileMetaData.getFilename());
			
		updtExecutor.process();
	
		System.out.println("[OUT] loadDataRecords :" );
	}
	
	
	
	protected ConcurrentMap<Integer, DTUser> populateDataMap(List<DTUser> dtUsers) {
		ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap = new ConcurrentHashMap<Integer, DTUser>();
		for(int i= 0;  i < dtUsers.size(); i++) {
			dtUserConcurrentHashMap.putIfAbsent(i, dtUsers.get(i));
		}
		return dtUserConcurrentHashMap;
	}


	public OpenCSVReadAndParseToBean getOpenCSVReadAndParseToBean() {
		return openCSVReadAndParseToBean;
	}


	public void setOpenCSVReadAndParseToBean(OpenCSVReadAndParseToBean openCSVReadAndParseToBean) {
		this.openCSVReadAndParseToBean = openCSVReadAndParseToBean;
	}


	public String getFileUploadTmpFolder() {
		return fileUploadTmpFolder;
	}


	public void setFileUploadTmpFolder(String fileUploadTmpFolder) {
		this.fileUploadTmpFolder = fileUploadTmpFolder;
	}
	
	
	
	private List<File> listFilesForFolder(final File folder, String filename, String fileExtension, 
			List<File> files ) {
		if(files == null) {
			files = new ArrayList<File>();
		}
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, filename, fileExtension, files);
	        } else {
	            System.out.println(fileEntry.getName());
	        	if(fileEntry.getName().toLowerCase().contains(filename)) {
	        		files.add(fileEntry);
	        	}
	        }
	    }
	    
	    return files;
	}


	
}
