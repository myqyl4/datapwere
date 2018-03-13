package com.redhat.datapwere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redhat.datapwere.data.IDTUserDao;
import com.redhat.datapwere.model.DTUser;

@Service("testViewServiceImpl")
public class TestViewServiceImpl implements ITestViewService {
	
	@Autowired
	private IDTUserDao dtUserDaoImpl;



	public IDTUserDao getDtUserDaoImpl() {
		return dtUserDaoImpl;
	}



	public void setDtUserDaoImpl(IDTUserDao dtUserDaoImpl) {
		this.dtUserDaoImpl = dtUserDaoImpl;
	}


	@Override
	public void update(DTUser dtuser) {
		// TODO Auto-generated method stub
		
	}

	
	
}
