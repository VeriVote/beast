package edu.pse.beast.gui.options;

import java.io.IOException;

import edu.pse.beast.gui.options.workspace.BeastWorkspaceOptionsGUI;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class OptionsGuiController {	
	
	private final String workspaceOptionsFXML = "/edu/pse/beast/options/testConfigDetailGUI.fxml";
	private FXMLLoader workspaceOptionsFXMLLoader = new FXMLLoader(
			getClass().getResource(workspaceOptionsFXML));
	private BeastWorkspaceOptionsGUI beastWorkspaceOptionsGUI;
	
	private ListView<OptionCategory> optionCategoriesListView;
	private AnchorPane optionTopLevelAnchorPane;	

	private BeastWorkspace beastWorkspace;
	
	public OptionsGuiController(
			ListView<OptionCategory> optionCategoriesListView,
			AnchorPane optionTopLevelAnchorPane,
			BeastWorkspace beastWorkspace) throws IOException {
		this.optionCategoriesListView = optionCategoriesListView;
		this.optionTopLevelAnchorPane = optionTopLevelAnchorPane;
		this.beastWorkspace = beastWorkspace;
		
		beastWorkspaceOptionsGUI = new BeastWorkspaceOptionsGUI(beastWorkspace);
		workspaceOptionsFXMLLoader.setController(beastWorkspaceOptionsGUI);
		//workspaceOptionsFXMLLoader.load();
		
		setup();
	}
	
	private void setup() {
		
	}

}
