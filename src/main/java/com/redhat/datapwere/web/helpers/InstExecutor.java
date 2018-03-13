package com.redhat.datapwere.web.helpers;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redhat.datapwere.model.DTUser;

@Component
public class InstExecutor extends AbstractWorker {

	protected static int maxThreadPool = 5;
	
	protected static int coreThreadPool = 5;
	
	protected static int maxThreads = 5;
	
	protected String filename = null;
	
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;
	

	protected ExecutorService executor = null;
	
	protected Queue<Future> taskStatus = new ConcurrentLinkedQueue<Future>();
	
	public InstExecutor() {}
	
	
	public InstExecutor(int coreThreadPool, int maxThreadPool) {
		maxThreadPool = maxThreadPool;
		coreThreadPool = coreThreadPool;
	}
	
	public InstExecutor(int coreThreadPool, int maxThreadPool, ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap) {
		AbstractWorker.dtUserConcurrentHashMap = dtUserConcurrentHashMap;
		AbstractWorker.dtUserKeyList = new LinkedBlockingQueue(new ArrayList<>(dtUserConcurrentHashMap.keySet()));
		maxThreadPool = maxThreadPool;
		coreThreadPool = coreThreadPool;
	}


	public static void main(String[] args) {
		try {
			System.out.println("[InstExecutor] start");

		}catch(Exception ie) {
			
		}
		System.out.println("[InstExecutor] Finished all threads");
	}
	
	
	@Override
	public String process() {
		try {
			System.out.println("[InstExecutor] start");
	        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
	        ThreadFactory threadFactory = Executors.defaultThreadFactory();
	        
	        //creating the ThreadPoolExecutor
	        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(coreThreadPool, maxThreadPool, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
	        
	        //start the monitoring thread
	        PoolMonitor monitor = new PoolMonitor(executorPool, 3);
	        Thread monitorThread = new Thread(monitor);
	        monitorThread.start();
	        
	        //submit work to the thread pool
	        for(int i=0; i<maxThreads; i++){
	        	InstHelperRunnable worker = new InstHelperRunnable(i, "InstHelper-Thread-" + i);
	        	worker.setDtUserLoadDAO(dtUserLoadDAO);
	        	worker.setPerfStatsDAO(perfStatsDAO);
	        	worker.setDatasourceStrategy(datasourceStrategy);
	        	worker.setFilename(this.filename);
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
		System.out.println("[InstExecutor] Finished all threads");
		return null;
	}
	
	
	
	public void prepareInstData(ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMapIn) {
		setDtUserConcurrentHashMap(dtUserConcurrentHashMapIn);
		setDtUserKeyList(new LinkedBlockingQueue(new ArrayList<>(dtUserConcurrentHashMapIn.keySet())));
	}


	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public static int getMaxThreadPool() {
		return maxThreadPool;
	}

	public static void setMaxThreadPool(int maxThreadPool) {
		InstExecutor.maxThreadPool = maxThreadPool;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public static int getCoreThreadPool() {
		return coreThreadPool;
	}

	public static void setCoreThreadPool(int coreThreadPool) {
		InstExecutor.coreThreadPool = coreThreadPool;
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


	

	
}
