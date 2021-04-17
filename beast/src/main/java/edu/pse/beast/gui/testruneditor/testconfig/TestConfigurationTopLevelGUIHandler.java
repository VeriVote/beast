package edu.pse.beast.gui.testruneditor.testconfig;

import java.io.IOException;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.testruneditor.CBMCPropertyTestRunHandler;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCTestConfigurationParameterController;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEvent;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEventType;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class TestConfigurationTopLevelGUIHandler
		implements
			WorkspaceUpdateListener {

	private final String cbmcTestConfigDetailFXML = "/edu/pse/beast/cbmcTestConfigDetailGUI.fxml";
	private CBMCTestConfigurationParameterController cbmcTestConfigController = new CBMCTestConfigurationParameterController();

	private ChoiceBox<String> sortCriteriumChoiceBox;
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

	private final String descrSortCrit = "Election Description";
	private final String propDescrSortCrit = "Property Description";

	public TestConfigurationTopLevelGUIHandler(Button startTestConfigButton,
			Button stopTestConfigButton,
			ChoiceBox<String> sortCriteriumChoiceBox,
			TreeView<String> testConfigTreeView,
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace,
			CBMCPropertyTestRunHandler cbmcTestRunHandler) throws IOException {

		this.sortCriteriumChoiceBox = sortCriteriumChoiceBox;
		this.testConfigTreeView = testConfigTreeView;

		setupSortCriteriumChoiceBox();
		
		testConfigTreeView.getSelectionModel().selectFirst();
	}

	private void sortCriteriumChoiceBoxSelectionChanged(String oldVal,
			String newVal) {
		
	}

	private void setupSortCriteriumChoiceBox() {
		sortCriteriumChoiceBox.getItems().add(descrSortCrit);
		sortCriteriumChoiceBox.getItems().add(propDescrSortCrit);

		sortCriteriumChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((e, oldVal, newVal) -> {
					sortCriteriumChoiceBoxSelectionChanged(oldVal, newVal);
				});
	}

	@Override
	public void handleWorkspaceUpdate(WorkspaceUpdateEvent evt) {

	}

}
