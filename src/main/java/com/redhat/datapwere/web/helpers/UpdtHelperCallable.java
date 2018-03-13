package com.redhat.datapwere.web.helpers;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.PerfStats;

public class UpdtHelperCallable extends InstHelperRunnable {
	
	protected static String UPDATE_OPERATION_TYPE = "UPDATE-OP";
	
	protected String filename = null;
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;

	public UpdtHelperCallable() {

	}

	public UpdtHelperCallable(int threadNumber, String threadName) {
		super(threadNumber, threadName);
	}
	
	
	@Override
	protected String process() throws InterruptedException, SQLException {
		long dtUserId = getAndRemoveItemFromDTUserIdFromList();
		System.out.println("UpdtHelperCallable " + dtUserId);
		while(dtUserId > 0) {
			//Thread.sleep(1000);
			DTUser dtUser = dtUserLoadDAO.getDTUserById(dtUserId);
			if(dtUser != null) {
				//System.out.println("\n " + this.getThreadName() + " Updating DTUser: " + + dtUser.getId());
				dtUser.setModifiedBy(this.getThreadName());
				dtUser.setModified(new Timestamp(System.currentTimeMillis()));
				
				
				dtUser = dtUserLoadDAO.updateDTUser(dtUser);
				System.out.println("\n " + this.getThreadName() + " Updated DTUser: " + dtUser.getId());
				recordsProcessed++;
				
				
				PerfStats perfStats = dtUser.getPerfStats();
		    	perfStats = preparePerfStats(perfStats, dtUser);
		    	long perfStatsId = perfStatsDAO.createPerfStatsGetID(perfStats);
		    	System.out.println("\nStats  : " + this.getThreadName() + " : " + perfStats.getDuration());
			}
			dtUserId = getAndRemoveItemFromDTUserIdFromList();
		}
		
		return "Completed:Success- Records : " + recordsProcessed + " - " + this.getThreadName();
	}
	
	
	private PerfStats preparePerfStats(PerfStats perfStats, DTUser dtUser) {
		perfStats.setOperationType(UPDATE_OPERATION_TYPE);
		perfStats.setStrategy(datasourceStrategy);
		perfStats.setFilename(dtUser.getFilename());
		perfStats.setItemCount(1);
		perfStats.setRunDate(new Timestamp(System.currentTimeMillis()));
		
		perfStats.setCreatedBy(this.getThreadName());
		perfStats.setCreated(new Timestamp(System.currentTimeMillis()));
		
		perfStats.setModifiedBy(this.getThreadName());
		perfStats.setModified(new Timestamp(System.currentTimeMillis()));
		return perfStats;
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
