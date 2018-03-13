package com.redhat.datapwere.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.redhat.datapwere.model.Home;

@Controller
public class HomeController {

	private static Logger log = LoggerFactory.getLogger(HomeController.class);

	@Value("${number.of.inst.threads}")
	private Integer numThreads;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[IN] HomeController: /home numThreads :" + numThreads);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("home", new Home());
		System.out.println("[OUT] HomeController: /home numThreads :" + numThreads);
		return mav;
	}

	public Integer getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(Integer numThreads) {
		this.numThreads = numThreads;
	}

	

}