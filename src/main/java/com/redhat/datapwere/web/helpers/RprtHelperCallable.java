package com.redhat.datapwere.web.helpers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.redhat.datapwere.model.PerfReports;
import com.redhat.datapwere.model.PerfStats;
import com.redhat.datapwere.model.PerfStatsSortByDuration;

public class RprtHelperCallable extends InstHelperRunnable {
	
	protected static String OPERATION_TYPE = "INSERT-OP";
	
	protected String filename = null;
	
	@Value("${datasource.strategy}")
	private String datasourceStrategy;
	
	@Value("${datasource.operations.types}")
	protected String operationTypes;
	
	@Value("${datasource.strategy.types}")
	protected String strategyTypes;
	
	
	protected List<String> operationTypesList = new ArrayList<String>();
	protected List<String> strategyTypesList = new ArrayList<String>();
	

	public RprtHelperCallable() {

	}

	public RprtHelperCallable(int threadNumber, String threadName) {
		super(threadNumber, threadName);
	}
	
	
	@Override
	protected String process() throws InterruptedException, SQLException {
		//Thread.sleep(1000);
		
		initLists();
		
		List<PerfStats> perfStatsByFilename = getFromPerfStatsFilenameListAndRemoveItemList();
		if(perfStatsByFilename != null && perfStatsByFilename.size() > 0) {
			PerfReports  perfReportsFilename = generateAverageDurationReportByFileName(perfStatsByFilename);
			perfReportsFilename = preparePerfReports(perfReportsFilename);
			this.perfReportsDAO.createPerfReportsGetID(perfReportsFilename);
			System.out.println("\n " + this.getThreadName() + " compelted filename reports : " + perfReportsFilename);
		}
		
		
		if(this.operationTypes != null) {
			for(String operationType : operationTypesList) {
				if(StringUtils.isNotEmpty(operationType)) {
					System.out.println("Start operationType : " + operationType);
					List<PerfStats> perfStatsByOperationType = getFromPerfStatsOperationsListAndRemoveItemList(operationType);
					if(perfStatsByOperationType != null && perfStatsByOperationType.size() > 0) {
						PerfReports  perfReportsOperationType = generateAverageDurationReportByOperationType(perfStatsByOperationType);
						perfReportsOperationType = preparePerfReports(perfReportsOperationType);
						this.perfReportsDAO.createPerfReportsGetID(perfReportsOperationType);
						System.out.println("\n " + this.getThreadName() + " compelted perfReportsOperationType : " + perfReportsOperationType);
					}
				}
			}
		}
		
		
		
		if(this.strategyTypes != null) {
			for(String strategyType : strategyTypesList) {
				if(StringUtils.isNotEmpty(strategyType)) {
					System.out.println("Start strategyType : " + strategyType);
					List<PerfStats> perfStatsByStrategyType = getFromPerfStatsStrategyListAndRemoveItemList(strategyType);
					if(perfStatsByStrategyType != null && perfStatsByStrategyType.size() > 0) {
						PerfReports  perfReportsStrategyType = generateAverageDurationReportByStrategyType(perfStatsByStrategyType);
						perfReportsStrategyType = preparePerfReports(perfReportsStrategyType);
						this.perfReportsDAO.createPerfReportsGetID(perfReportsStrategyType);
						System.out.println("\n " + this.getThreadName() + " compelted perfReportsStrategyType : " + perfReportsStrategyType);
					}
				}
			}
		}
		
		
		if(this.operationTypes != null) {
			for(String operationType : operationTypesList) {
				if(StringUtils.isNotEmpty(operationType)) {
					if(this.strategyTypes != null) {
						for(String strategyType : strategyTypesList) {
							if(StringUtils.isNotEmpty(strategyType)) {
								System.out.println("Start operationType : " + operationType + " strategyType : " + strategyType);
								List<PerfStats> perfStatsByOperationTypeAndStrategyType = getFromPerfStatsOperationsTypeAndStrategyTypeListAndRemoveItemList(operationType, strategyType);
								if(perfStatsByOperationTypeAndStrategyType != null && perfStatsByOperationTypeAndStrategyType.size() > 0) {
									PerfReports  perfReportsOperationTypeStrategyType = generateAverageDurationReportByOperationTypeAndStrategyType(perfStatsByOperationTypeAndStrategyType);
									perfReportsOperationTypeStrategyType = preparePerfReports(perfReportsOperationTypeStrategyType);
									this.perfReportsDAO.createPerfReportsGetID(perfReportsOperationTypeStrategyType);
									System.out.println("\n " + this.getThreadName() + " compelted perfReportsOperationTypeStrategyType : " + perfReportsOperationTypeStrategyType);
								}
							}
						}
					}
				}
			}
		}
		
		return "Completed:Success - " + this.getThreadName();
		
		
		
		
	}
	
	
	public PerfReports generateAverageDurationReportByFileName(List<PerfStats> perfStatsByFilename) {
		List<Long> ignored = new ArrayList<Long>();
		PerfReports perfReports = new PerfReports();
		
		if(perfStatsByFilename != null && perfStatsByFilename.size() >0) {
			String filename  = perfStatsByFilename.get(0).getFilename();

			System.out.println("\n " + this.getThreadName() + " running reports for filename : " + filename);
			
			perfReports.setFilename(filename);
		
			Collections.sort(perfStatsByFilename, new PerfStatsSortByDuration());
			long shortestDuration = perfStatsByFilename.get(0).getDuration();
			long longestDuration = perfStatsByFilename.get(perfStatsByFilename.size()-1).getDuration();
			
			perfReports.setShortestDuration(shortestDuration);
			perfReports.setLongestDuration(longestDuration);
			
			long totalDuration = 0l;
			long records = 0l;
			long numInList = perfStatsByFilename.size();
			long start = (10);
			long end = (numInList - 10);
			
			for(PerfStats perfStats : perfStatsByFilename) {
				//if((records >= start) || (records <= end)) {
					totalDuration = (totalDuration + perfStats.getDuration());
					//System.out.println("[OUT] generateAverageDurationReportByFileName totalDuration : " + totalDuration);
				//} else {
					//ignored.add(perfStats.getDuration());
				//}
				records++;
			}
			
			long divisor = (records - 20);
			
			long averageDuration = (totalDuration / divisor);
			perfReports.setTotalDuration(totalDuration);
			perfReports.setAverageDuration(averageDuration);
	
			perfReports.setTotalRecords(records);
		}
		
		//System.out.println("[OUT] generateAverageDurationReportByFileName ignored : " + ignored);
		
		return perfReports;
	}
	
	
	

	
	public PerfReports generateAverageDurationReportByOperationType(List<PerfStats> perfStatsByOperationType) {
		List<Long> ignored = new ArrayList<Long>();
		PerfReports perfReports = new PerfReports();
		
		if(perfStatsByOperationType != null && perfStatsByOperationType.size() >0) {
			String operationType  = perfStatsByOperationType.get(0).getOperationType();
			System.out.println("\n " + this.getThreadName() + " running reports for operation type : " + operationType);
			
			perfReports.setOperationType(operationType);
		
			Collections.sort(perfStatsByOperationType, new PerfStatsSortByDuration());
			long shortestDuration = perfStatsByOperationType.get(0).getDuration();
			long longestDuration = perfStatsByOperationType.get(perfStatsByOperationType.size()-1).getDuration();
			
			perfReports.setShortestDuration(shortestDuration);
			perfReports.setLongestDuration(longestDuration);
			
			long totalDuration = 0l;
			long records = 0l;
			long numInList = perfStatsByOperationType.size();
			long start = (10);
			long end = (numInList - 10);
			
			for(PerfStats perfStats : perfStatsByOperationType) {
				//if((records >= start) || (records <= end)) {
					totalDuration = (totalDuration + perfStats.getDuration());
					//System.out.println("[OUT] generateAverageDurationReportByOperationType totalDuration : " + totalDuration);
				//} else {
					//ignored.add(perfStats.getDuration());
				//}
				records++;
			}
			
			long divisor = (records - 20);
			
			long averageDuration = (totalDuration / divisor);
			perfReports.setTotalDuration(totalDuration);
			perfReports.setAverageDuration(averageDuration);
	
			perfReports.setTotalRecords(records);
		}
		
		//System.out.println("[OUT] generateAverageDurationReportByOperationType ignored : " + ignored);
		
		return perfReports;
	}
	
	
	
	
	public PerfReports generateAverageDurationReportByStrategyType(List<PerfStats> perfStatsByStrategyType) {
		List<Long> ignored = new ArrayList<Long>();
		PerfReports perfReports = new PerfReports();
		
		if(perfStatsByStrategyType != null && perfStatsByStrategyType.size() >0) {
			String strategyType  = perfStatsByStrategyType.get(0).getStrategy();
			System.out.println("\n " + this.getThreadName() + " running reports for strategy : " + strategyType);
			
			perfReports.setStrategy(strategyType);
		
			Collections.sort(perfStatsByStrategyType, new PerfStatsSortByDuration());
			long shortestDuration = perfStatsByStrategyType.get(0).getDuration();
			long longestDuration = perfStatsByStrategyType.get(perfStatsByStrategyType.size()-1).getDuration();
			
			perfReports.setShortestDuration(shortestDuration);
			perfReports.setLongestDuration(longestDuration);
			
			long totalDuration = 0l;
			long records = 0l;
			long numInList = perfStatsByStrategyType.size();
			long start = (10);
			long end = (numInList - 10);
			
			for(PerfStats perfStats : perfStatsByStrategyType) {
				//if((records >= start) || (records <= end)) {
					totalDuration = (totalDuration + perfStats.getDuration());
					//System.out.println("[OUT] generateAverageDurationReportByStrategyType totalDuration : " + totalDuration);
				//} else {
					//ignored.add(perfStats.getDuration());
				//}
				records++;
			}
			
			long divisor = (records - 20);
			
			long averageDuration = (totalDuration / divisor);
			perfReports.setTotalDuration(totalDuration);
			perfReports.setAverageDuration(averageDuration);
	
			perfReports.setTotalRecords(records);
		}
		
		//System.out.println("[OUT] generateAverageDurationReportByStrategyType ignored : " + ignored);
		
		return perfReports;
	}
	
	
	
	public PerfReports generateAverageDurationReportByOperationTypeAndStrategyType(List<PerfStats> perfReportsOperationTypeStrategyType) {
		List<Long> ignored = new ArrayList<Long>();
		PerfReports perfReports = new PerfReports();
		
		if(perfReportsOperationTypeStrategyType != null && perfReportsOperationTypeStrategyType.size() >0) {
			String operationType  = perfReportsOperationTypeStrategyType.get(0).getOperationType();
			String strategy  = perfReportsOperationTypeStrategyType.get(0).getStrategy();

			System.out.println("\n " + this.getThreadName() + " running reports for operation type : " + operationType + " strategy : " + strategy);
			
			perfReports.setOperationType(operationType);
			perfReports.setStrategy(strategy);
		
			Collections.sort(perfReportsOperationTypeStrategyType, new PerfStatsSortByDuration());
			long shortestDuration = perfReportsOperationTypeStrategyType.get(0).getDuration();
			long longestDuration = perfReportsOperationTypeStrategyType.get(perfReportsOperationTypeStrategyType.size()-1).getDuration();
			
			perfReports.setShortestDuration(shortestDuration);
			perfReports.setLongestDuration(longestDuration);
			
			long totalDuration = 0l;
			long records = 0l;
			long numInList = perfReportsOperationTypeStrategyType.size();
			long start = (10);
			long end = (numInList - 10);
			
			for(PerfStats perfStats : perfReportsOperationTypeStrategyType) {
				//if((records >= start) || (records <= end)) {
					totalDuration = (totalDuration + perfStats.getDuration());
					//System.out.println("[OUT] generateAverageDurationReportByOperationTypeAndStrategyType totalDuration : " + totalDuration);
				//} else {
					//ignored.add(perfStats.getDuration());
				//}
				records++;
			}
			
			long divisor = (records - 20);
			
			long averageDuration = (totalDuration / divisor);
			perfReports.setTotalDuration(totalDuration);
			perfReports.setAverageDuration(averageDuration);
	
			perfReports.setTotalRecords(records);
		}
		
		//System.out.println("[OUT] generateAverageDurationReportByOperationTypeAndStrategyType ignored : " + ignored);
		
		return perfReports;
	}
	
	
	
	private PerfReports preparePerfReports(PerfReports perReports) {
		perReports.setRunDate(new Timestamp(System.currentTimeMillis()));
		
		perReports.setCreatedBy(this.getThreadName());
		perReports.setCreated(new Timestamp(System.currentTimeMillis()));
		
		perReports.setModifiedBy(this.getThreadName());
		perReports.setModified(new Timestamp(System.currentTimeMillis()));
		return perReports;
	}
	

	
	
	public void initLists() {e
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
		if(this.strategyTypes != null) {
			String[] types = strategyTypes.split(",");
			for(int i = 0; i < types.length; i++) {
				this.strategyTypesList.add(types[i]);
			}
		}
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
