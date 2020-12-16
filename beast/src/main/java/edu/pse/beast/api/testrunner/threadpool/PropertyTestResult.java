package edu.pse.beast.api.testrunner.threadpool;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class PropertyTestResult {
	Document cbmcDocument;
	
	public PropertyTestResult(Document cbmcDocument) {
		this.cbmcDocument = cbmcDocument;
	}

	public boolean isSuccess() {
		NodeList resultElements = cbmcDocument.getElementsByTagName("cprover-status");
		String resElemTextContext = resultElements.item(0).getTextContent();
		return resElemTextContext.equals("SUCCESS");
	}
	
}
