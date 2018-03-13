package com.redhat.datapwere.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	private Log logger = LogFactory.getLog(ExceptionController.class);

	@ExceptionHandler(value = Exception.class)
	public String handleException(HttpServletRequest request, Exception ex) {
		System.err.println("Request " + request.getRequestURL()
				+ " Threw an Exception");
		ex.printStackTrace();
		return "error";
	}

}
