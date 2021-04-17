package edu.pse.beast.gui.testruneditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyledTextArea;

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.BEASTPropertyCheckSession;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.toolbox.Tuple;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CBMCPropertyTestRunHandler implements BEASTCallback {

	private BEAST beast;

	private Map<String, BEASTPropertyCheckSession> sessionUUIDToTestSession = new HashMap<>();
	private ConcurrentHashMap<String, List<String>> sessionUUIDToLogs = new ConcurrentHashMap<>();
	private boolean display = false;
	private List<Tuple<CBMCPropertyTestConfiguration, String>> activeTests = new ArrayList<>();

	private BEASTPropertyCheckSession currentActiveSession;
	private CodeArea displayArea;

	private Button startTestConfigButton;
	private Button stopTestConfigButton;

	public CBMCPropertyTestRunHandler(Button startTestConfigButton,
			Button stopTestConfigButton, BEAST beast) {
		this.beast = beast;
		this.startTestConfigButton = startTestConfigButton;
		this.stopTestConfigButton = stopTestConfigButton;
	}

	public void setDisplayArea(CodeArea displayArea) {
		this.displayArea = displayArea;
	}

	public void display(CBMCPropertyTestConfiguration selectedConfig) {
		for (Tuple<CBMCPropertyTestConfiguration, String> t : activeTests) {
			if (t.first() == selectedConfig) {
				startTestConfigButton.setDisable(true);
				stopTestConfigButton.setDisable(false);
				BEASTPropertyCheckSession session = sessionUUIDToTestSession
						.get(t.second());
				currentActiveSession = session;
				startDisplay(session.getUuid());
				return;
			}
		}
		startTestConfigButton.setDisable(false);
		stopTestConfigButton.setDisable(true);
	}

	public void stopDisplay() {
		display = false;
		displayArea.clear();
		startTestConfigButton.setDisable(true);
		stopTestConfigButton.setDisable(true);
	}

	public void stopTest(CBMCPropertyTestConfiguration selectedConfig) {
		for (Tuple<CBMCPropertyTestConfiguration, String> t : activeTests) {
			if (t.first() == selectedConfig) {
				BEASTPropertyCheckSession session = sessionUUIDToTestSession
						.get(t.second());
				session.interruptAllTests();

				startTestConfigButton.setDisable(false);
				stopTestConfigButton.setDisable(true);
				return;
			}
		}
	}

	public void startTest(CBMCPropertyTestConfiguration selectedConfig,
			CodeGenOptions codeGenOptions) {
		List<Integer> amountOfVoters = new ArrayList<>();
		for (int i = selectedConfig.getMinVoters(); i <= selectedConfig
				.getMaxVoters(); ++i)
			amountOfVoters.add(i);

		List<Integer> amountOfCandidates = new ArrayList<>();
		for (int i = selectedConfig.getMinCands(); i <= selectedConfig
				.getMaxCands(); ++i)
			amountOfCandidates.add(i);

		List<Integer> amountOfSeats = new ArrayList<>();
		for (int i = selectedConfig.getMinSeats(); i <= selectedConfig
				.getMaxSeats(); ++i)
			amountOfSeats.add(i);

		TimeOut timeOut = new TimeOut(TimeUnit.SECONDS, 1);

		ElectionCheckParameter param = new ElectionCheckParameter(
				amountOfVoters, amountOfCandidates, amountOfSeats, timeOut, 1,
				"");

		BEASTPropertyCheckSession session = beast.createCheckSession(this,
				selectedConfig.getDescr(),
				List.of(selectedConfig.getPropDescr()), param, codeGenOptions);

		currentActiveSession = session;

		sessionUUIDToTestSession.put(session.getUuid(), session);
		sessionUUIDToLogs.put(session.getUuid(), new ArrayList<>());
		activeTests.add(new Tuple<CBMCPropertyTestConfiguration, String>(
				selectedConfig, session.getUuid()));
		startTestConfigButton.setDisable(true);
		stopTestConfigButton.setDisable(false);
		startDisplay(session.getUuid());
		
		session.start();
	}

	private void startDisplay(String uuid) {
		display = true;
		List<String> prevLogs = sessionUUIDToLogs.get(uuid);
		for (String log : prevLogs) {
			displayArea.insertText(displayArea.getCaretPosition(), log + "\n");
		}
	}

	private void addLog(String uuid, String log) {
		sessionUUIDToLogs.get(uuid).add(log);
		Platform.runLater(() -> {
			displayArea.insertText(displayArea.getCaretPosition(), log + "\n");
		});
	}

	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
		addLog(sessionUUID, output);
	}

	public class FileLink {
		private File file;

		public FileLink(File file) {

		}

		public void applyToText(Text text) {

		}
	}

	public class FileStyle {

	}

	@Override
	public void onTestFileCreated(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, File cbmcFile) {

		addLog(sessionUUID, "Codefile creation has successfully completed: "
				+ cbmcFile.getAbsolutePath());
	}

}
