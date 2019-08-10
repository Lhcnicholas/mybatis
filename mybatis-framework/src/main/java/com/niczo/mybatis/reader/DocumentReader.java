package com.niczo.mybatis.reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class DocumentReader {

	private static final Logger logger = LoggerFactory.getLogger(DocumentReader.class);

	public Document createDocument(InputStream inputStream) {
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			logger.error("读取文件失败", e);
		}
		return document;
	}
}
