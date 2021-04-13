package edu.pse.beast.gui;

import java.io.IOException;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.TestConfiguration.TestTypes;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValuePointer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class TestConfigurationHandler {

	private final String cbmcTestConfigDetailFXML = "/edu/pse/beast/cbmcTestConfigDetailGUI.fxml";
	private CBMCTestConfigController cbmcTestConfigController = new CBMCTestConfigController();

	private TreeView<String> testConfigTreeView;
	private AnchorPane testConfigDetailsAnchorPane;

	private TreeItem<String> root = new TreeItem();

	private BeastWorkspace beastWorkspace;
	private FXMLLoader cbmcTestConfigDetailLoader = new FXMLLoader(
			getClass().getResource(cbmcTestConfigDetailFXML));

	public TestConfigurationHandler(TreeView testConfigTreeView,
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace) throws IOException {

		cbmcTestConfigDetailLoader.setController(cbmcTestConfigController);
		Node n = (Node) cbmcTestConfigDetailLoader.load();

		this.testConfigTreeView = testConfigTreeView;
		this.testConfigDetailsAnchorPane = testConfigDetailsAnchorPane;
		this.beastWorkspace = beastWorkspace;

		initTestConfigTreeView();
		initCBMCTestConfigView();

		handleWorkspaceUpdate();
	}

	private TestConfiguration getParentTestConfig(
			TreeItem<String> selectedItem) {
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
		TreeItem<String> selectedItem = testConfigTreeView.getSelectionModel()
				.getSelectedItem();

		TestConfiguration oldParentConfig = getParentTestConfig(selectedItem);
		CBMCPropertyTestConfiguration selectedConfig = getSelectedCBMCTestConfig(
				selectedItem);
		if (selectedConfig == null || oldParentConfig == null)
			return;
		if (selectedConfig.getDescr().getName().equals(newDescrName))
			return;

		TestConfiguration newParentTestConfig = beastWorkspace
				.getTestConfigByDescrName(newDescrName);
		if (newParentTestConfig == null) {
			CElectionDescription descr = beastWorkspace
					.getDescrByName(newDescrName);
			newParentTestConfig = beastWorkspace
					.createAndReturnTestConfigForDescr(descr);
		}

		newParentTestConfig.getCbmcPropertyTestConfigurations()
				.add(selectedConfig);
		oldParentConfig.getCbmcPropertyTestConfigurations()
				.remove(selectedConfig);

		handleWorkspaceUpdate();
	}

	private void cbmcTestConfigMinVoterChanged(int newVal) {
		TreeItem<String> selectedItem = testConfigTreeView.getSelectionModel()
				.getSelectedItem();

		CBMCPropertyTestConfiguration selectedConfig = getSelectedCBMCTestConfig(
				selectedItem);
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

	}

	private void testConfigSelectionChanged(TreeItem<String> newlySelected) {
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

	private void handleWorkspaceUpdate() {
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

		for (TestConfiguration tc : beastWorkspace.getTestConfigs()) {
			TreeItem<String> tcItem = new TreeItem<>(tc.getName());
			for (CBMCPropertyTestConfiguration cbmcTc : tc
					.getCbmcPropertyTestConfigurations()) {
				TreeItem<String> cbmcTcItem = new TreeItem<>(cbmcTc.getName());
				tcItem.getChildren().add(cbmcTcItem);
			}
			root.getChildren().add(tcItem);
		}
	}

}
