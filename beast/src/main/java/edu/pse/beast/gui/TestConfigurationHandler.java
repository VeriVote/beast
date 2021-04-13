package edu.pse.beast.gui;

import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import com.sun.jna.platform.win32.COM.COMEarlyBindingObject;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.TestConfiguration.TestTypes;
import edu.pse.beast.gui.elements.CEditorElement;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValuePointer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class TestConfigurationHandler implements WorkspaceUpdateListener {

	private final String cbmcTestConfigDetailFXML = "/edu/pse/beast/cbmcTestConfigDetailGUI.fxml";
	private CBMCTestConfigController cbmcTestConfigController = new CBMCTestConfigController();

	private TreeView<String> testConfigTreeView;
	private AnchorPane testConfigDetailsAnchorPane;

	private TreeItem<String> root = new TreeItem();

	private BeastWorkspace beastWorkspace;
	private FXMLLoader cbmcTestConfigDetailLoader = new FXMLLoader(
			getClass().getResource(cbmcTestConfigDetailFXML));

	private Button startTestConfigButton;
	private Button stopTestConfigButton;

	private CodeArea outputArea = new CodeArea();

	private CBMCPropertyTestRunHandler cbmcTestRunHandler;

	public TestConfigurationHandler(
			Button startTestConfigButton,
			Button stopTestConfigButton, 
			TreeView testConfigTreeView, 
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace,
			CBMCPropertyTestRunHandler cbmcTestRunHandler) throws IOException {

		cbmcTestConfigDetailLoader.setController(cbmcTestConfigController);
		Node n = (Node) cbmcTestConfigDetailLoader.load();

		VirtualizedScrollPane<CodeArea> vsp = new VirtualizedScrollPane<>(
				outputArea);
		AnchorPane op = cbmcTestConfigController.getOutputAnchorPane();
		op.getChildren().add(vsp);
		AnchorPane.setTopAnchor(vsp, 0d);
		AnchorPane.setLeftAnchor(vsp, 0d);
		AnchorPane.setRightAnchor(vsp, 0d);
		AnchorPane.setBottomAnchor(vsp, 0d);
		outputArea.setEditable(false);

		this.startTestConfigButton = startTestConfigButton;
		startTestConfigButton.setOnAction((e) -> {
			startTest();
		});
		

		this.stopTestConfigButton = stopTestConfigButton;		
		stopTestConfigButton.setOnAction(e -> stopTest());
		

		this.testConfigTreeView = testConfigTreeView;
		this.testConfigDetailsAnchorPane = testConfigDetailsAnchorPane;
		this.beastWorkspace = beastWorkspace;

		initTestConfigTreeView();
		initCBMCTestConfigView();

		this.cbmcTestRunHandler = cbmcTestRunHandler;
		cbmcTestRunHandler.setDisplayArea(outputArea);

		handleWorkspaceUpdate();
		beastWorkspace.registerUpdateListener(this);
	}
	
	private void stopTest() {
		
	}

	private void startTest() {
		TreeItem<String> selectedItem = testConfigTreeView.getSelectionModel()
				.getSelectedItem();

		CBMCPropertyTestConfiguration selectedConfig = getSelectedCBMCTestConfig(
				selectedItem);

		cbmcTestRunHandler.startTest(selectedConfig, beastWorkspace.getCodeGenOptions());		
	}

	private TestConfiguration getParentTestConfig(
			TreeItem<String> selectedItem) {
		if (selectedItem == null)
			return null;
		if (selectedItem.getParent() == root)
			return null;
		String parentName = selectedItem.getParent().getValue();
		TestConfiguration testConfig = beastWorkspace
				.getTestConfigByName(parentName);
		return testConfig;
	}

	private CBMCPropertyTestConfiguration getSelectedCBMCTestConfig(
			TreeItem<String> selectedItem) {
		TestConfiguration testConfig = getParentTestConfig(selectedItem);
		if (testConfig == null)
			return null;
		TestConfiguration.TestTypes selectedTestType = testConfig
				.getTestTypeFromName(selectedItem.getValue());
		if (selectedTestType == TestTypes.CBMC_PROPERTY_TEST) {
			CBMCPropertyTestConfiguration cbmcTestConfig = testConfig
					.getCbmcPropertyTestConfigByName(selectedItem.getValue());
			return cbmcTestConfig;
		}
		return null;
	}

	private void cbmcTestConfigDescrChanged(String newDescrName) {
		if (newDescrName == null)
			return;
		TreeItem<String> selectedItem = testConfigTreeView.getSelectionModel()
				.getSelectedItem();

		TestConfiguration oldParentConfig = getParentTestConfig(selectedItem);
		CBMCPropertyTestConfiguration selectedConfig = getSelectedCBMCTestConfig(
				selectedItem);
		if (selectedConfig == null || oldParentConfig == null)
			return;

		beastWorkspace.changeDescrForCBMCTestConfig(selectedConfig,
				newDescrName);
	}

	private void cbmcTestConfigMinVoterChanged(int newVal) {
		TreeItem<String> selectedItem = testConfigTreeView.getSelectionModel()
				.getSelectedItem();

		CBMCPropertyTestConfiguration selectedConfig = getSelectedCBMCTestConfig(
				selectedItem);
		if (selectedConfig == null)
			return;
		selectedConfig.setMaxVoters(newVal);
	}

	private void initCBMCTestConfigView() {
		SpinnerValueFactory<Integer> fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				1, 500);
		cbmcTestConfigController.getMinVoter().setValueFactory(fac);
		cbmcTestConfigController.getMinVoter().setEditable(true);
		cbmcTestConfigController.getMinVoter().valueProperty()
				.addListener((o, oldVal, newVal) -> {
					cbmcTestConfigMinVoterChanged(newVal);
				});

		cbmcTestConfigController.getDescrChoiceBox().getSelectionModel()
				.selectedItemProperty().addListener((o, oldVal, newVal) -> {
					cbmcTestConfigDescrChanged(newVal);
				});
	}

	private void displayCBMCPropertyTestConfigDetails(
			CBMCPropertyTestConfiguration config) {
		testConfigDetailsAnchorPane.getChildren().clear();
		testConfigDetailsAnchorPane.getChildren()
				.add(cbmcTestConfigController.getTopLevelAnchorPane());
		AnchorPane.setTopAnchor(
				cbmcTestConfigController.getTopLevelAnchorPane(), 0d);
		AnchorPane.setLeftAnchor(
				cbmcTestConfigController.getTopLevelAnchorPane(), 0d);
		AnchorPane.setRightAnchor(
				cbmcTestConfigController.getTopLevelAnchorPane(), 0d);
		AnchorPane.setBottomAnchor(
				cbmcTestConfigController.getTopLevelAnchorPane(), 0d);

		cbmcTestConfigController.getNameTextField().setText(config.getName());
		cbmcTestConfigController.getDescrChoiceBox().getSelectionModel()
				.select(config.getDescr().getName());
		cbmcTestConfigController.getPropDescrChoiceBox().getSelectionModel()
				.select(config.getPropDescr().getName());

		cbmcTestConfigController.getMinVoter().getValueFactory()
				.setValue(config.getMinVoters());
		cbmcTestConfigController.getMaxVoter()
				.setText(String.valueOf(config.getMaxVoters()));
		cbmcTestConfigController.getMinCandidates()
				.setText(String.valueOf(config.getMinCands()));
		cbmcTestConfigController.getMaxCandidates()
				.setText(String.valueOf(config.getMaxCands()));
		cbmcTestConfigController.getMinSeats()
				.setText(String.valueOf(config.getMinSeats()));
		cbmcTestConfigController.getMaxSeats()
				.setText(String.valueOf(config.getMaxSeats()));

		cbmcTestRunHandler.display(config);
	}

	private void displayTestConfig(TestConfiguration config) {
		testConfigDetailsAnchorPane.getChildren().clear();
	}

	private void testConfigSelectionChanged(TreeItem<String> newlySelected) {
		cbmcTestRunHandler.stopDisplay();
		if (newlySelected == null)
			return;
		if (newlySelected.getParent() != root) {
			TestConfiguration testConfig = getParentTestConfig(newlySelected);
			TestConfiguration.TestTypes selectedTestType = testConfig
					.getTestTypeFromName(newlySelected.getValue());
			if (selectedTestType == TestTypes.CBMC_PROPERTY_TEST) {
				CBMCPropertyTestConfiguration cbmcTestConfig = testConfig
						.getCbmcPropertyTestConfigByName(
								newlySelected.getValue());
				displayCBMCPropertyTestConfigDetails(cbmcTestConfig);
			}
		} else {
			TestConfiguration testConfig = beastWorkspace
					.getTestConfigByName(newlySelected.getValue());
			displayTestConfig(testConfig);
		}
	}

	private void initTestConfigTreeView() {
		testConfigTreeView.setRoot(root);
		testConfigTreeView.setShowRoot(false);

		testConfigTreeView.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> {
					testConfigSelectionChanged(newVal);
				});
	}

	public void handleWorkspaceUpdate() {
		cbmcTestConfigController.getDescrChoiceBox().getItems().clear();
		for (CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
			cbmcTestConfigController.getDescrChoiceBox().getItems()
					.add(descr.getName());
		}

		cbmcTestConfigController.getPropDescrChoiceBox().getItems().clear();
		for (PreAndPostConditionsDescription propDescr : beastWorkspace
				.getLoadedPropDescrs()) {
			cbmcTestConfigController.getPropDescrChoiceBox().getItems()
					.add(propDescr.getName());
		}

		String selected = "";
		if (!root.getChildren().isEmpty()) {
			selected = testConfigTreeView.getSelectionModel().getSelectedItem()
					.getValue();
		}
		TreeItem<String> toSelect = null;
		root.getChildren().clear();
		for (TestConfiguration tc : beastWorkspace.getTestConfigs()) {
			TreeItem<String> tcItem = new TreeItem<>(tc.getName());
			if (tc.getName().equals(selected)) {
				toSelect = tcItem;
			}
			for (CBMCPropertyTestConfiguration cbmcTc : tc
					.getCbmcPropertyTestConfigurations()) {
				TreeItem<String> cbmcTcItem = new TreeItem<>(cbmcTc.getName());
				tcItem.getChildren().add(cbmcTcItem);
				if (cbmcTc.getName().equals(selected)) {
					toSelect = cbmcTcItem;
				}
			}
			root.getChildren().add(tcItem);
		}
		if (toSelect != null) {
			testConfigTreeView.getSelectionModel().select(toSelect);
		} else if (!root.getChildren().isEmpty()) {
			testConfigTreeView.getSelectionModel().selectLast();
		}

	}

}
