package edu.pse.beast.api.testrunner.threadpool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;

public class PropertyCheckWorkUnit implements WorkUnit {
	ElectionDescription description;
	PreAndPostConditionsDescription propertyDescr;
	int v, c, s;
	String uuid;
	CBMCProcessWindows windows = new CBMCProcessWindows();
	BEASTCallback cb;
	private boolean finished = false;

	public PropertyCheckWorkUnit(ElectionDescription description, PreAndPostConditionsDescription propertyDescr, int v,
			int c, int s, String uuid, BEASTCallback cb) {
		this.description = description;
		this.propertyDescr = propertyDescr;
		this.v = v;
		this.c = c;
		this.s = s;
		this.uuid = uuid;
		this.cb = cb;
	}

	@Override
	public void doWork() {
		cb.onPropertyTestStart(description, propertyDescr, s, c, v, uuid);
		
		ProcessBuilder pb = windows.startTestForParam(description, propertyDescr, v, c, s, cb);
		Process process;
		try {
			process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			List<String> cbmcOutput = new ArrayList<>();
			try {
				while ((line = reader.readLine()) != null) {
					cb.onPropertyTestRawOutput(description, propertyDescr, s, c, v, uuid, line);
					cbmcOutput.add(line);
				}
				// TODO errorhandling
			} catch (IOException e) {
				e.printStackTrace();
			}
			processCBMCOutput(cbmcOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		finished = true;
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public String getUUID() {
		return uuid;
	}
	
	private void processCBMCOutput(List<String> cbmcOutput) {
		// find first line of cbmc xml output
		int i = 0;
		for (; i < cbmcOutput.size() && !cbmcOutput.get(i).contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); ++i)
			;

		String xmlString = String.join("\n", cbmcOutput.subList(i, cbmcOutput.size()));
		InputStream xmlStream = IOUtils.toInputStream(xmlString, Charset.forName("UTF-8"));

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document cbmcDocument = builder.parse(xmlStream);
			NodeList resultElements = cbmcDocument.getElementsByTagName("cprover-status");
			String resElemTextContext = resultElements.item(0).getTextContent();					
			cb.onPropertyTestResult(description, propertyDescr, v, c, s, new PropertyTestResult(cbmcDocument));
			// TODO errorhandling
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
