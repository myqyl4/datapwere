package com.redhat.datapwere.web.helpers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.DTUserUtil;
import com.redhat.datapwere.model.PerfStats;

@Component
public class InstHelperRunnable extends AbstractWorker implements Callable {
	
	private String threadName = null;

	private int threadNumber = 0;
	
	protected long recordsProcessed = 0;
	
	protected String filename = null;
	
	protected static String INSTERT_OPERATION_TYPE = "INSERT-OP";
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;

	public InstHelperRunnable() {
		
	}

	public InstHelperRunnable(int threadNumber, String threadName) {
		this.threadName = threadName;
		this.threadNumber = threadNumber;
	}

	@Override
	public String call() {
		String status = "Undefined";
		System.out.println("LoadHelperRunnable heavy processing - START " + Thread.currentThread().getName() + " : " + this.getThreadName());
		try {
			//Thread.sleep(1000);
			status = process();
		} catch (InterruptedException e) {
			e.printStackTrace();
			status = "Completed:Error";
		} catch (SQLException e) {
			e.printStackTrace();
			status = "Completed:Error";
		} finally {
			
		}
		System.out.println("LoadHelperRunnable heavy processing - END " + Thread.currentThread().getName() + " : " + this.getThreadName() + " status : " + status);
		
		return status;
	}

	
	@Override
	protected String process() throws InterruptedException, SQLException {
		DTUser dtUser = getAndRemoveItemFromDTUserMap();
		while(dtUser != null) {
			//Thread.sleep(1000);
			
			DTUserUtil.cleanupDTUser(dtUser);
			dtUser.setModifiedBy(this.getThreadName());
			dtUser.setFilename(this.filename);
			//System.out.println("\n " + this.getThreadName() + " Creating DTUser: " + dtUser.getFullname());
			dtUser = dtUserLoadDAO.createDTUserGetID(dtUser);
			Number id = dtUser.getGeneratedKey();
			System.out.println("\n " + this.getThreadName() + " Created DTUser: " + id);
			recordsProcessed++;
	    	
	    	PerfStats perfStats = dtUser.getPerfStats();
	    	perfStats = preparePerfStats(perfStats);
	    	Number perfStatsId = perfStatsDAO.createPerfStatsGetID(perfStats);
	    	System.out.println("\nStats  : " + this.getThreadName() + " : " + perfStats.getDuration());
	    	
	    	dtUser = getAndRemoveItemFromDTUserMap();
		}
		
		return "Completed:Success- Records : " + recordsProcessed + " - " + this.getThreadName();
	}
	
	
	private PerfStats preparePerfStats(PerfStats perfStats) {
		perfStats.setOperationType(INSTERT_OPERATION_TYPE);
		perfStats.setStrategy(datasourceStrategy);
		perfStats.setFilename(this.filename);
		perfStats.setItemCount(1);
		perfStats.setRunDate(new Timestamp(System.currentTimeMillis()));
		
		perfStats.setCreatedBy(this.getThreadName());
		perfStats.setCreated(new Timestamp(System.currentTimeMillis()));
		
		perfStats.setModifiedBy(this.getThreadName());
		perfStats.setModified(new Timestamp(System.currentTimeMillis()));
		return perfStats;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public long getRecordsProcessed() {
		return recordsProcessed;
	}

	public void setRecordsProcessed(long recordsProcessed) {
		this.recordsProcessed = recordsProcessed;
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
