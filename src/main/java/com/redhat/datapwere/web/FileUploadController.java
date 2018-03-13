package com.redhat.datapwere.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.redhat.datapwere.model.Home;


@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	
	@Value("${file.upload.tmp.folder}")
	private String fileUploadTmpFolder;
	
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[IN] FileUploadController: /upload fileUploadTmpFolder :" + fileUploadTmpFolder);
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("home", new Home());
		System.out.println("[OUT] FileUploadController: /upload fileUploadTmpFolder :" + fileUploadTmpFolder);
		return mav;
	}
	

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		System.out.println("[IN] uploadFileHandler");
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = fileUploadTmpFolder;//System.getProperty("catalina.home");
				//File dir = new File(rootPath + File.separator + "tmpFiles");
				File dir = new File(rootPath + File.separator);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				System.out.println("[IN] uploadFileHandler name : " + name);
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location=" + serverFile.getAbsolutePath());
				
				System.out.println("[IN] uploadFileHandler : You successfully uploaded file=" + name);
				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam("name") String[] names, @RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				//File dir = new File(rootPath + File.separator + "tmpFiles");
				File dir = new File(rootPath + File.separator);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location="+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name + "";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
}
