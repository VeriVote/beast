package edu.pse.beast.api.testrunner.cbmc;

import java.io.BufferedReader;
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
import edu.pse.beast.api.testrunner.BEASTResult;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

//TODO xml trace zugriff und darstellung regeln als objekt struktur
//Was war die eingabe
public class CBMCBEASTResult extends BEASTResult {
	private Process process;
	BufferedReader reader;
	

	
	public CBMCBEASTResult(ElectionDescription electionDescription,
			PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s, BEASTCallback cb,
			Process process, BufferedReader reader) {
		super(electionDescription, preAndPostConditionsDescription, v, c, s, cb);
		this.process = process;
		this.reader = reader;
		super.startThread();
	}

	@Override
	protected void test() {
		String line;
		List<String> cbmcOutput = new ArrayList<>();
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				cbmcOutput.add(line);
			}
			//TODO errorhandling
		} catch (IOException e) {
			e.printStackTrace();
		}
		processCBMCOutput(cbmcOutput);
		System.out.println("EXIT VAL :: " + process.exitValue()); 
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
			System.out.println("RESULT RESULT");
			System.out.println(resElemTextContext);
			
			super.verificationSuccess = resElemTextContext.equals("SUCCESS");
			
		//TODO errorhandling
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
