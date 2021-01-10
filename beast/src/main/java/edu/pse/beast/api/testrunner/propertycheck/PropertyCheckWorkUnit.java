package edu.pse.beast.api.testrunner.propertycheck;

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
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;

public class PropertyCheckWorkUnit implements WorkUnit {
	ElectionDescription description;
	PreAndPostConditionsDescription propertyDescr;
	int v, c, s;
	String uuid;
	CBMCProcessStarter processStarter;
	BEASTCallback cb;
	private boolean finished = false;
	File cbmcFile;

	public PropertyCheckWorkUnit(ElectionDescription description, PreAndPostConditionsDescription propertyDescr, int v,
			int c, int s, String uuid, BEASTCallback cb, CBMCProcessStarter processStarter, File cbmcFile) {
		this.description = description;
		this.propertyDescr = propertyDescr;
		this.v = v;
		this.c = c;
		this.s = s;
		this.uuid = uuid;
		this.cb = cb;
		this.processStarter = processStarter;
		this.cbmcFile = cbmcFile;
	}

	@Override
	public void doWork() {
		cb.onPropertyTestStart(description, propertyDescr, s, c, v, uuid);

		ProcessBuilder pb = processStarter.startTestForParam(description, propertyDescr, v, c, s, uuid, cb, cbmcFile);
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
			cb.onPropertyTestRawOutputComplete(description, propertyDescr, s, c, v, uuid, cbmcOutput);
			cb.onPropertyTestFinished(description, propertyDescr, s, c, v, uuid);
			finished = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public String getUUID() {
		return uuid;
	}


	@Override
	public void interrupt() {

	}

}
