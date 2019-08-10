package com.niczo.mybatis;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();


	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MappedStatement getMappedStatement(String statementId) {
		return mappedStatementMap.get(statementId);
	}

	public void addMappedStatement(String statmentId, MappedStatement mappedStatement) {
		mappedStatementMap.put(statmentId, mappedStatement);
	}
}
