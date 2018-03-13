package com.redhat.datapwere.web.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redhat.datapwere.model.PerfStats;

@Component
public class RprtExecutor extends InstExecutor {


	protected ExecutorService executor = null;
	
	protected Queue<Future> taskStatus = new ConcurrentLinkedQueue<Future>();
	
	private String filename = null;
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;
	
	@Value("${datasource.operations.types}")
	protected String operationTypes;
	
	@Value("${datasource.strategy.types}")
	protected String strategyTypes;
	
	
	protected List<String> operationTypesList = new ArrayList<String>();
	protected List<String> strategyTypesList = new ArrayList<String>();
	

	
	public RprtExecutor() {}
	
	public RprtExecutor(int coreThreadPool, int maxThreadPool) {
		super(coreThreadPool, maxThreadPool);
	}

	public static void main(String[] args) {
		try {
			System.out.println("[RprtExecutor] start");

		}catch(Exception ie) {
			
		}
		System.out.println("[RprtExecutor] Finished all threads");
	}
	
	
	@Override
	public String process() {
		try {
			System.out.println("[RprtExecutor] start");
	        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
	        ThreadFactory threadFactory = Executors.defaultThreadFactory();
	        
	        //creating the ThreadPoolExecutor
	        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
	        
	        //start the monitoring thread
	        PoolMonitor monitor = new PoolMonitor(executorPool, 3);
	        Thread monitorThread = new Thread(monitor);
	        monitorThread.start();
	        
	        //submit work to the thread pool
	        for(int i=0; i<1; i++){
	        	RprtHelperCallable worker = new RprtHelperCallable(i, "RprtHelper-Thread-" + i);
	        	worker.setDtUserLoadDAO(dtUserLoadDAO);
	        	worker.setPerfStatsDAO(perfStatsDAO);
	        	worker.setPerfReportsDAO(perfReportsDAO);
	        	worker.setDatasourceStrategy(datasourceStrategy);
	        	worker.setOperationTypes(operationTypes);
	        	worker.setStrategyTypes(strategyTypes);
	            //executorPool.execute(worker);
		        Future<String> result = executorPool.submit(worker);
		        taskStatus.add(result);
	        }
	        
	        while(taskStatus != null) {
	        	for(int i = 0 ; i < taskStatus.size(); i++) {
		        	Future<String> result = taskStatus.peek();
		        	String status = (String) result.get();
		        	if(status != null && status.toLowerCase().startsWith("completed")) {
		        		System.out.println(result);
		        		taskStatus.remove(result);
		        		System.out.println("[STATUS] taskStatus removed completed task : " + result);
		        		if(taskStatus.size() <= 0) {
		        			taskStatus = null;
		        			break;
		        		}
		        	}
	        	}
	        	Thread.sleep(30000);
	        }
	        
	        if((taskStatus == null || taskStatus.size() <= 0) && (dtUserConcurrentHashMap == null && dtUserConcurrentHashMap.size() <= 0)){
	        	System.out.println("[STATUS] All Tasks completed. Shutting down All Processing.");
	        }
	        
	        long timeout = 60;
	        try {
	        	Thread.sleep(3000);
	        	//executorPool.shutdown();
	        	executorPool.shutdownNow();
	        	executorPool.awaitTermination(timeout, TimeUnit.SECONDS);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	       
	        try {
	        	Thread.sleep(5000);
	        	monitor.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

		}catch(Exception ie) {
			ie.printStackTrace();
		}
		System.out.println("[RprtExecutor] Finished all threads");
		return null;
	}
	
	
	
	
	public void prepareRprtDataByFileName() throws SQLException {
		System.out.println("prepareRprtDataByFileName ");
		List<String> filenames = perfStatsDAO.findLoadedFilesList();
		for(String filename : filenames) {
			List<PerfStats> perfStatsByFilename = perfStatsDAO.findPerfStatsByFilename(filename);
			if(perfStatsByFilename != null) {
				perfStatsByFilenameConcurrentHashMap.put(filename, perfStatsByFilename);
				System.out.println("prepareRprtDataByFileName filename : " + filename + " : " + perfStatsByFilename.size() + " perfstats");
			} else {
				System.out.println("prepareRprtDataByFileName filename : " + filename + " : 0 perfstats");
			}
		}
		
	}
	
	
	public void prepareRprtDataByOperationType() throws SQLException {
		System.out.println("prepareRprtDataByOperationType ");
		for(String operationType : operationTypesList) {
			System.out.println("prepareRprtDataByOperationType operationType : " + operationType);
			List<PerfStats> perfStatsByOperationType = perfStatsDAO.findPerfStatsByOperationType(operationType);
			if(perfStatsByOperationType != null) {
				perfStatsByOperationTypeConcurrentHashMap.put(operationType, perfStatsByOperationType);
				System.out.println("prepareRprtDataByOperationType operationType : " + operationType + " : " + perfStatsByOperationType.size() + " perfstats");
			} else {
				System.out.println("prepareRprtDataByOperationType operationType : " + operationType + " : 0 perfstats");
			}
		}
		
	}
	
	
	
	public void prepareRprtDataByStrategyType() throws SQLException {
		System.out.println("prepareRprtDataByStrategyType ");
		for(String strategyType : strategyTypesList) {
			System.out.println("prepareRprtDataByOperationType strategyType : " + strategyType);
			List<PerfStats> perfStatsByStrategyType = perfStatsDAO.findPerfStatsByStrategyType(strategyType);
			if(perfStatsByStrategyType != null) {
				perfStatsByStrategyTypeConcurrentHashMap.put(strategyType, perfStatsByStrategyType);
				System.out.println("prepareRprtDataByStrategyType strategyType : " + strategyType + " : " + perfStatsByStrategyType.size() + " perfstats");
			} else {
				System.out.println("prepareRprtDataByStrategyType strategyType : " + strategyType + " : 0 perfstats");
			}
		}
		
	}
	
	
	public void prepareRprtDataByOperationTypeAndStrategyType() throws SQLException {
		System.out.println("prepareRprtDataByOperationTypeAndStrategyType ");
		for(String operationType : operationTypesList) {
			for(String strategyType : strategyTypesList) {
				System.out.println("prepareRprtDataByOperationType operationType : " + operationType + " strategyType : " + strategyType);
				List<PerfStats> perfStatsByOperationTypeAndStrategyType = perfStatsDAO.findPerfStatsByOperationTypeAndStrategyType(operationType, strategyType);
				if(perfStatsByOperationTypeAndStrategyType != null) {
					perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap.put(operationType+strategyType, perfStatsByOperationTypeAndStrategyType);
					System.out.println("prepareRprtDataByOperationTypeAndStrategyType operationType+strategyType : " + operationType+strategyType + " : " + perfStatsByOperationTypeAndStrategyType.size() + " perfstats");
				} else {
					System.out.println("prepareRprtDataByOperationTypeAndStrategyType operationType+strategyType : " + operationType+strategyType + " : 0 perfstats");
				}
			}
		}
		
	}
	
	
	public void initLists() {
		initOperationTypesList();
		initStrategyTypesList();
	}


	public void initOperationTypesList() {
		if(this.operationTypes != null) {
			String[] types = operationTypes.split(",");
			for(int i = 0; i < types.length; i++) {
				this.operationTypesList.add(types[i]);
			}
		}
	}
	public void initStrategyTypesList() {
		if(this.operationTypes != null) {
			String[] types = strategyTypes.split(",");
			for(int i = 0; i < types.length; i++) {
				this.strategyTypesList.add(types[i]);
			}
		}
	}
	
	
	public void initializeReports() {
		try {
			perfReportsDAO.deleteAllPerfReports();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public String getDatasourceStrategy() {
		return datasourceStrategy;
	}

	public void setDatasourceStrategy(String datasourceStrategy) {
		this.datasourceStrategy = datasourceStrategy;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public List<String> getOperationTypesList() {
		return operationTypesList;
	}

	public void setOperationTypesList(List<String> operationTypesList) {
		this.operationTypesList = operationTypesList;
	}

	public List<String> getStrategyTypesList() {
		return strategyTypesList;
	}

	public void setStrategyTypesList(List<String> strategyTypesList) {
		this.strategyTypesList = strategyTypesList;
	}


	
}
