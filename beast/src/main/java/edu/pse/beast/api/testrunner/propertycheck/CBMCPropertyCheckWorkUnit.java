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

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;

public class CBMCPropertyCheckWorkUnit implements WorkUnit {
	CElectionDescription descr;
	PreAndPostConditionsDescription propertyDescr;
	int v, c, s;
	String uuid;
	CBMCProcessStarter processStarter;
	CBMCTestCallback cb;
	private boolean finished = false;
	File cbmcFile;
	private LoopBoundHandler loopBoundHandler;
	private CodeGenOptions codeGenOptions;
	private String sessionUUID;
	private Process process;
	private WorkUnitState state;

	public CBMCPropertyCheckWorkUnit(String sessionUUID, CElectionDescription descr,
			PreAndPostConditionsDescription propertyDescr, int v, int c, int s,
			String uuid, CBMCProcessStarter processStarter, File cbmcFile,
			LoopBoundHandler loopBoundHandler, CodeGenOptions codeGenOptions) {
		this.sessionUUID = sessionUUID;
		this.descr = descr;
		this.propertyDescr = propertyDescr;
		this.v = v;
		this.c = c;
		this.s = s;
		this.uuid = uuid;
		this.processStarter = processStarter;
		this.cbmcFile = cbmcFile;
		this.loopBoundHandler = loopBoundHandler;
		this.codeGenOptions = codeGenOptions;
	}
	
	public void setCallback(CBMCTestCallback cb) {
		this.cb = cb;
	}
	
	public boolean hasCallback() {
		return this.cb != null;
	}

	@Override
	public void doWork() {
		state = WorkUnitState.WORKED_ON;
		cb.onPropertyTestStart(descr, propertyDescr, s, c, v, uuid);

		ProcessBuilder pb = processStarter.startTestForParam(sessionUUID, descr,
				propertyDescr, v, c, s, uuid, cb, cbmcFile, loopBoundHandler,
				codeGenOptions);
		try {
			process = pb.start();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line;
			List<String> cbmcOutput = new ArrayList<>();
			try {
				while ((line = reader.readLine()) != null) {
					cb.onPropertyTestRawOutput(sessionUUID, descr,
							propertyDescr, s, c, v, uuid, line);
					cbmcOutput.add(line);
				}
				// TODO errorhandling
			} catch (IOException e) {
				e.printStackTrace();
			}
			cb.onPropertyTestRawOutputComplete(descr, propertyDescr, s, c, v,
					uuid, cbmcOutput);
			cb.onPropertyTestFinished(descr, propertyDescr, s, c, v, uuid);
			state = WorkUnitState.FINISHED;
			process.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public void interrupt() {
		process.destroyForcibly();
		state = WorkUnitState.STOPPED;
	}

	@Override
	public void addedToQueue() {
		state = WorkUnitState.ON_QUEUE;
	}

	@Override
	public WorkUnitState getState() {
		return state;
	}


}
