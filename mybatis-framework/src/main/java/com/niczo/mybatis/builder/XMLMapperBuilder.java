package com.niczo.mybatis.builder;

import com.niczo.mybatis.Configuration;
import com.niczo.mybatis.MappedStatement;
import com.niczo.mybatis.sqlsource.DefaultSqlSource;
import com.niczo.mybatis.sqlsource.SqlSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class XMLMapperBuilder {

	private Configuration configuration;

	private String nameSpace;

	public XMLMapperBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	public void parse(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			root.elements("select");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void parseStatement(Element selectElement) {
		MappedStatement mappedStatement = null;
		String id = selectElement.attributeValue("id");
		String parameterType = selectElement.attributeValue("parameterType");
		Class<?> parameterClass = resolveClass(parameterType);
		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = resolveClass(resultType);
		String statementType = selectElement.attributeValue("statementType");

		String statementId = nameSpace + "." + id;
		String sqlText = selectElement.getTextTrim();
		SqlSource sqlSource = new DefaultSqlSource(sqlText);
		mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType, sqlSource);
		configuration.addMappedStatement(statementId, mappedStatement);
	}

	private Class<?> resolveClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
