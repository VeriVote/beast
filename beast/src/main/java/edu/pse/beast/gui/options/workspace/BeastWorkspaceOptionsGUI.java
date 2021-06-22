package edu.pse.beast.gui.options.workspace;

import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BeastWorkspaceOptionsGUI {

	@FXML
	private AnchorPane topLevelAnchorPane;
	
	private BeastWorkspace beastWorkspace;
	
	public BeastWorkspaceOptionsGUI(BeastWorkspace beastWorkspace) {
		this.beastWorkspace = beastWorkspace;
	}

}
