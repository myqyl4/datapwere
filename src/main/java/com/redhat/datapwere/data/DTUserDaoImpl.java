package com.redhat.datapwere.data;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("dtUserDaoImpl")
public class DTUserDaoImpl implements IDTUserDao {
	private static Logger log = LoggerFactory.getLogger(DTUserDaoImpl.class);

    protected DataSource dataSource;
    protected JdbcTemplate jdbc;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public DTUserDaoImpl(DataSource ds) {
        log.info("CreateTable constructor called");
        setDataSource(ds);
        //setUpTable();
    }
    
    public void setUpTable() {
        log.info("About to set up table...");
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("create table accounts (name varchar(50), amount int)");
        jdbc.update("insert into accounts (name,amount) values (?,?)",
                new Object[] {"Major Clanger", 2000}
        );
        jdbc.update("insert into accounts (name,amount) values (?,?)",
                new Object[] {"Tiny Clanger", 100}
        );
        log.info("Table created");
    }
}
