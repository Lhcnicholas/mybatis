package com.niczo.mybatis.builder;

import com.niczo.mybatis.Configuration;
import com.niczo.mybatis.reader.DocumentReader;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {
	private static final Logger logger = LoggerFactory.getLogger(XMLConfigBuilder.class);

	private Configuration configuration;

	public XMLConfigBuilder() {
		this.configuration = new Configuration();
	}

	public Configuration parse(InputStream inputStream) {
		//根据传入的流获取Document配置文件
		DocumentReader documentReader = new DocumentReader();
		Document document = documentReader.createDocument(inputStream);

		parseConfiguration(document.getRootElement());
		return configuration;
	}

	private void parseConfiguration(Element rootElement) {
		parseEnvironmentsElement(rootElement.element("environments"));
		parseMappersElement(rootElement.element("mappers"));
	}

	private void parseMappersElement(Element mappers) {
		List mapperElements = mappers.elements();
		for (Object o : mapperElements) {
			if (o instanceof Element) {
				parseMapperElement((Element) o);
			}
		}
	}

	private void parseMapperElement(Element element) {
		String resource = element.attributeValue("resource");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);

		XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
		xmlMapperBuilder.parse(inputStream);
	}

	@SuppressWarnings("unchecked")
	private void parseEnvironmentsElement(Element environments) {
		String defaultEnvId = environments.attributeValue("default");
		if (defaultEnvId == null || defaultEnvId.equals("")) {
			return;
		}

		List elements = environments.elements();
		elements.stream().filter(o -> o instanceof Element)
				.forEach(envElement -> {
					String envId = ((Element) envElement).attributeValue("id");
					if (defaultEnvId.equals(envId)) {
						createDataSource((Element) envElement);
					}
				});
	}

	@SuppressWarnings("unchecked")
	private void createDataSource(Element envElement) {
		Element dataSourceElement = envElement.element("dataSource");
		String dataSourceType = dataSourceElement.attributeValue("type");
		List<Element> propertyElements = (List<Element>)dataSourceElement.elements("property");

		Properties properties = new Properties();
		propertyElements.forEach(element -> {
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			properties.setProperty(name, value);
		});

		if (dataSourceType.equals("DBCP")) {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(properties.getProperty("driver"));
			dataSource.setUrl(properties.getProperty("url"));
			dataSource.setUsername(properties.getProperty("username"));
			dataSource.setPassword(properties.getProperty("password"));

			configuration.setDataSource(dataSource);
		}
	}
}
