package edu.pse.beast.api.testrunner.propertycheck;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCResult {
	private ElectionDescription descr;
	private PreAndPostConditionsDescription cond;
	private int s, c, v;
	String uuid;
	List<String> rawOutput;
	private Document cbmcDocument;
	private boolean success;

	public CBMCResult(ElectionDescription descr, PreAndPostConditionsDescription cond, int s, int c, int v, String uuid,
			List<String> rawOutput) {
		this.descr = descr;
		this.cond = cond;
		this.s = s;
		this.c = c;
		this.v = v;
		this.rawOutput = rawOutput;
		processCBMCOutput(rawOutput);
	}

	void processCBMCOutput(List<String> cbmcOutput) {
		// find first line of cbmc xml output
		int i = 0;
		for (; i < cbmcOutput.size() && !cbmcOutput.get(i).contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); ++i)
			;

		String xmlString = String.join("\n", cbmcOutput.subList(i, cbmcOutput.size()));
		InputStream xmlStream = IOUtils.toInputStream(xmlString, Charset.forName("UTF-8"));

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			this.cbmcDocument = builder.parse(xmlStream);
			NodeList resultElements = cbmcDocument.getElementsByTagName("cprover-status");
			String resElemTextContext = resultElements.item(0).getTextContent();
			success = resElemTextContext.equals("SUCCESS");
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isSuccess() {
		return success;
	}

	public boolean isResForParams(ElectionDescription description, PreAndPostConditionsDescription propertyDescr, int s,
			int c, int v) {
		return (this.descr == description && this.cond == propertyDescr && this.s == s && this.c == c && this.v == v);
	}

}
