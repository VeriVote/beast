package edu.pse.beast.gui;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class BeastGUIController {
	@FXML
	private AnchorPane codePane;

	private void addChildToAnchorPane(AnchorPane pane, Node child, double top,
			double bottom, double left, double right) {
		codePane.getChildren().add(child);
		AnchorPane.setTopAnchor(child, top);
		AnchorPane.setLeftAnchor(child, left);
		AnchorPane.setRightAnchor(child, right);
		AnchorPane.setBottomAnchor(child, bottom);
	}
	
	private CElectionDescription getTestDescr() {
		String name = "test";
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.WEIGHTED_APPROVAL,
				VotingOutputTypes.PARLIAMENT_STACK,
				"test");
		return descr;
	}

	@FXML
	public void initialize() {
		CElectionEditor cElectionEditor = new CElectionEditor();
		VirtualizedScrollPane<CElectionEditor> vsp = new VirtualizedScrollPane(
				cElectionEditor);
		addChildToAnchorPane(codePane, vsp, 0, 0, 0, 0);
		
	}
}
