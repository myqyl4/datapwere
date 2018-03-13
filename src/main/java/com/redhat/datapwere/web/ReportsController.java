package com.redhat.datapwere.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.redhat.datapwere.data.DTUserLoadDAO;
import com.redhat.datapwere.data.PerfReportsDAO;
import com.redhat.datapwere.data.PerfStatsDAO;
import com.redhat.datapwere.model.PerfReports;
import com.redhat.datapwere.web.helpers.RprtExecutor;

@Controller
public class ReportsController {

	private static Logger log = LoggerFactory.getLogger(ReportsController.class);


	@Value("${number.of.inst.threads}")
	private Integer maxThreads;
	
	@Value("${number.of.inst.pool}")
	private Integer maxThreadPool;
	
	@Value("${number.of.core.pool}")
	private Integer coreThreadPool;
	
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;
	
	@Value("${datasource.operations.types}")
	protected String operationTypes;
	
	@Value("${datasource.strategy.types}")
	protected String strategyTypes;
	
	@Autowired
	protected RprtExecutor rprtExecutor;
	
	@Autowired
	protected DTUserLoadDAO dtUserLoadDAO;
	
	@Autowired
	protected PerfStatsDAO perfStatsDAO;

	@Autowired
	protected PerfReportsDAO perfReportsDAO;

	
	

	@RequestMapping(value = "/runReports", method = RequestMethod.GET)
	public ModelAndView runReports(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[OUT] ReportsController: /runReports maxThreads :" + maxThreads + " maxThreadPool : " + maxThreadPool);
		ModelAndView mav = new ModelAndView("runReports");
		
		PerfReports perfReports = new PerfReports();
		
		mav.addObject("perfReports", perfReports);
		System.out.println("[OUT] ReportsController:");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/executeReports", method = RequestMethod.GET)
	public ModelAndView executeReports(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[OUT] ReportsController: /executeReports maxThreads :" + maxThreads + " maxThreadPool : " + maxThreadPool);
		ModelAndView mav = new ModelAndView("executeReports");
		
		PerfReports perfReports = new PerfReports();

		try {
			generateReports();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mav.addObject("perfReports", perfReports);
		System.out.println("[OUT] ReportsController:");
		return mav;
	}
	
	
	
	
	
	@RequestMapping(value = "/viewReports", method = RequestMethod.GET)
	public ModelAndView viewReports(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[OUT] ReportsController: /viewReports maxThreads :" + maxThreads + " maxThreadPool : " + maxThreadPool);
		ModelAndView mav = new ModelAndView("viewReports");
		
		List<PerfReports> perfReportsList = new ArrayList<PerfReports>();
		
		try {
			perfReportsList = perfReportsDAO.getAllPerfReports();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("perfReportsList", perfReportsList);
		System.out.println("[OUT] ReportsController:");
		return mav;
	}

	
	
	
	protected void generateReports() throws SQLException {
		System.out.println("[IN] runUpdates :" );
		rprtExecutor.setCoreThreadPool(coreThreadPool);
		rprtExecutor.setMaxThreadPool(maxThreadPool);
		rprtExecutor.setMaxThreads(maxThreads);
		rprtExecutor.setDatasourceStrategy(datasourceStrategy);
		rprtExecutor.setOperationTypes(operationTypes);
		rprtExecutor.setStrategyTypes(strategyTypes);
		rprtExecutor.initLists();
			
		rprtExecutor.setDtUserLoadDAO(dtUserLoadDAO);
		rprtExecutor.setPerfStatsDAO(perfStatsDAO);
		rprtExecutor.setPerfReportsDAO(perfReportsDAO);
		//rprtExecutor.setFilename(filename);
		rprtExecutor.prepareRprtDataByFileName();
		rprtExecutor.prepareRprtDataByOperationType();
		rprtExecutor.prepareRprtDataByStrategyType();
		rprtExecutor.prepareRprtDataByOperationTypeAndStrategyType();
		
		rprtExecutor.initializeReports();
			
		rprtExecutor.process();
	
		System.out.println("[OUT] loadDataRecords :" );
	}
	

	public Integer getMaxThreads() {
		return maxThreads;
	}


	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}


	public Integer getMaxThreadPool() {
		return maxThreadPool;
	}


	public void setMaxThreadPool(Integer maxThreadPool) {
		this.maxThreadPool = maxThreadPool;
	}


	public Integer getCoreThreadPool() {
		return coreThreadPool;
	}


	public void setCoreThreadPool(Integer coreThreadPool) {
		this.coreThreadPool = coreThreadPool;
	}



	public String getDatasourceStrategy() {
		return datasourceStrategy;
	}



	public void setDatasourceStrategy(String datasourceStrategy) {
		this.datasourceStrategy = datasourceStrategy;
	}



	public RprtExecutor getRprtExecutor() {
		return rprtExecutor;
	}



	public void setRprtExecutor(RprtExecutor rprtExecutor) {
		this.rprtExecutor = rprtExecutor;
	}



	public DTUserLoadDAO getDtUserLoadDAO() {
		return dtUserLoadDAO;
	}



	public void setDtUserLoadDAO(DTUserLoadDAO dtUserLoadDAO) {
		this.dtUserLoadDAO = dtUserLoadDAO;
	}



	public PerfStatsDAO getPerfStatsDAO() {
		return perfStatsDAO;
	}



	public void setPerfStatsDAO(PerfStatsDAO perfStatsDAO) {
		this.perfStatsDAO = perfStatsDAO;
	}



	public PerfReportsDAO getPerfReportsDAO() {
		return perfReportsDAO;
	}



	public void setPerfReportsDAO(PerfReportsDAO perfReportsDAO) {
		this.perfReportsDAO = perfReportsDAO;
	}



	public String getOperationTypes() {
		return operationTypes;
	}



	public void setOperationTypes(String operationTypes) {
		this.operationTypes = operationTypes;
	}



	public String getStrategyTypes() {
		return strategyTypes;
	}



	public void setStrategyTypes(String strategyTypes) {
		this.strategyTypes = strategyTypes;
	}
	
	


	

}