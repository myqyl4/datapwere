package com.redhat.datapwere.web.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.redhat.datapwere.data.DTUserLoadDAO;
import com.redhat.datapwere.data.PerfReportsDAO;
import com.redhat.datapwere.data.PerfStatsDAO;
import com.redhat.datapwere.model.DTUser;
import com.redhat.datapwere.model.PerfStats;

public abstract class AbstractWorker {

	protected static ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap = new ConcurrentHashMap<Integer, DTUser>();

	protected static BlockingQueue<Integer> dtUserKeyList = new LinkedBlockingQueue<Integer>();
	
	
	protected static BlockingQueue<Long> dtUserIdsList = new LinkedBlockingQueue<Long>();
	
	
	protected static ConcurrentMap<String, List<PerfStats>> perfStatsByFilenameConcurrentHashMap = new ConcurrentHashMap<String, List<PerfStats>>();
	
	protected static ConcurrentMap<String, List<PerfStats>> perfStatsByOperationTypeConcurrentHashMap = new ConcurrentHashMap<String, List<PerfStats>>();
	
	protected static ConcurrentMap<String, List<PerfStats>> perfStatsByStrategyTypeConcurrentHashMap = new ConcurrentHashMap<String, List<PerfStats>>();
	
	protected static ConcurrentMap<String, List<PerfStats>> perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap = new ConcurrentHashMap<String, List<PerfStats>>();

	
	@Autowired
	protected DTUserLoadDAO dtUserLoadDAO;
	
	@Autowired
	protected PerfStatsDAO perfStatsDAO;
	
	@Autowired
	protected PerfReportsDAO perfReportsDAO;


	
	public static ConcurrentMap<Integer, DTUser> getDtUserConcurrentHashMap() {
		return dtUserConcurrentHashMap;
	}

	public static void setDtUserConcurrentHashMap(ConcurrentMap<Integer, DTUser> dtUserConcurrentHashMap) {
		AbstractWorker.dtUserConcurrentHashMap = dtUserConcurrentHashMap;
	}
	
	

	public static BlockingQueue<Integer> getDtUserKeyList() {
		return dtUserKeyList;
	}

	public static void setDtUserKeyList(BlockingQueue<Integer> dtUserKeyList) {
		AbstractWorker.dtUserKeyList = dtUserKeyList;
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

	public static BlockingQueue<Long> getDtUserIdsList() {
		return dtUserIdsList;
	}
	

	public PerfReportsDAO getPerfReportsDAO() {
		return perfReportsDAO;
	}

	public void setPerfReportsDAO(PerfReportsDAO perfReportsDAO) {
		this.perfReportsDAO = perfReportsDAO;
	}

	public static void setDtUserIdsList(BlockingQueue<Long> dtUserIdsList) {
		AbstractWorker.dtUserIdsList = dtUserIdsList;
	}
	
	

	public static ConcurrentMap<String, List<PerfStats>> getPerfStatsByFilenameConcurrentHashMap() {
		return perfStatsByFilenameConcurrentHashMap;
	}

	public static void setPerfStatsByFilenameConcurrentHashMap(
			ConcurrentMap<String, List<PerfStats>> perfStatsByFilenameConcurrentHashMap) {
		AbstractWorker.perfStatsByFilenameConcurrentHashMap = perfStatsByFilenameConcurrentHashMap;
	}

	public synchronized static DTUser getAndRemoveItemFromDTUserMap() {
		DTUser dtUser = null;
		if(dtUserConcurrentHashMap != null && !dtUserConcurrentHashMap.isEmpty()) {
			Integer key = dtUserKeyList.remove();
			dtUser = dtUserConcurrentHashMap.get(key);
			if(dtUser != null) {
				dtUserConcurrentHashMap.remove(key);
			}
		}
		
		return dtUser;
	}
	
	
	
	public synchronized static long getAndRemoveItemFromDTUserIdFromList() {
		long id = -1l;
		if(dtUserIdsList != null && !dtUserIdsList.isEmpty()) {
			id = dtUserIdsList.remove();
		}
		
		return id;
	}
	
	
	public synchronized static List<PerfStats> getFromPerfStatsFilenameListAndRemoveItemList() {
		List<PerfStats> perfStatsByFilename = new ArrayList<PerfStats>();
		if(perfStatsByFilenameConcurrentHashMap != null && !perfStatsByFilenameConcurrentHashMap.isEmpty()) {
			List<String> keys = new ArrayList<String>(perfStatsByFilenameConcurrentHashMap.keySet());
			for(String key : keys) {
				if(key != null) {
					perfStatsByFilename = perfStatsByFilenameConcurrentHashMap.get(key);
					perfStatsByFilenameConcurrentHashMap.remove(key);
					break;
				}
			}
		}
		
		return perfStatsByFilename;
	}
	
	
	
	public synchronized static List<PerfStats> getFromPerfStatsOperationsListAndRemoveItemList(String operationType) {
		List<PerfStats> perfStatsByOperationType = new ArrayList<PerfStats>();
		if(perfStatsByOperationTypeConcurrentHashMap != null && !perfStatsByOperationTypeConcurrentHashMap.isEmpty()) {
			List<String> keys = new ArrayList<String>(perfStatsByOperationTypeConcurrentHashMap.keySet());
			for(String key : keys) {
				if(key != null && key.equalsIgnoreCase(operationType)) {
					perfStatsByOperationType = perfStatsByOperationTypeConcurrentHashMap.get(key);
					perfStatsByOperationTypeConcurrentHashMap.remove(key);
					break;
				}
			}
		}
		return perfStatsByOperationType;
	}
	
	
	public synchronized static List<PerfStats> getFromPerfStatsStrategyListAndRemoveItemList(String strategyType) {
		List<PerfStats> perfStatsByStrategyType = new ArrayList<PerfStats>();
		if(perfStatsByStrategyTypeConcurrentHashMap != null && !perfStatsByStrategyTypeConcurrentHashMap.isEmpty()) {
			List<String> keys = new ArrayList<String>(perfStatsByStrategyTypeConcurrentHashMap.keySet());
			for(String key : keys) {
				if(key != null && key.equalsIgnoreCase(strategyType)) {
					perfStatsByStrategyType = perfStatsByStrategyTypeConcurrentHashMap.get(key);
					perfStatsByStrategyTypeConcurrentHashMap.remove(key);
					break;
				}
			}
		}
		
		return perfStatsByStrategyType;
	}
	
	
	
	public synchronized static List<PerfStats> getFromPerfStatsOperationsTypeAndStrategyTypeListAndRemoveItemList(String operationType, String strategyType) {
		List<PerfStats> perfStatsByOperationTypeAndStrategyType = new ArrayList<PerfStats>();
		if(perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap != null && !perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap.isEmpty()) {
			List<String> keys = new ArrayList<String>(perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap.keySet());
			for(String key : keys) {
			System.out.println("getFromPerfStatsOperationsTypeAndStrategyTypeListAndRemoveItemList key: " + key + " operationType+strategyType : " + operationType+strategyType );
				if(key != null && key.equalsIgnoreCase(operationType+strategyType)) {
					perfStatsByOperationTypeAndStrategyType = perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap.get(key);
					perfStatsByOperationTypeAndStrategyTypeConcurrentHashMap.remove(key);
					break;
				}
			}
		}
		
		return perfStatsByOperationTypeAndStrategyType;
	}
	
	
	
	protected abstract String process() throws InterruptedException, SQLException;
	
}
