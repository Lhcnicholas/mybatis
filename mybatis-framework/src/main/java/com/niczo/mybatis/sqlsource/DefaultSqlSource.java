package com.niczo.mybatis.sqlsource;

public class DefaultSqlSource implements SqlSource {

	private String sqlText;

	public DefaultSqlSource(String sqlText) {
		this.sqlText = sqlText;
	}

	@Override
	public BoundSql getBoundSql() {
		return null;
	}
}
