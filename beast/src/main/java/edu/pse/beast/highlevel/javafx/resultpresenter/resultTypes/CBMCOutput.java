package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import edu.pse.beast.propertychecker.Result;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;

public class CBMCOutput extends ResultPresentationType {
	
	@Override
	public Node presentResult(Result result) {
		TextFlow resultTextField = new TextFlow();
		resultTextField.getChildren().addAll(result.getResultText());

		return resultTextField;
	}

	@Override
	public String getName() {
		return "CBMC Output";
	}

	@Override
	public String getToolTipDescription() {
		return "Shows the complete output cbmc gave";
	}

	@Override
	public boolean supportsZoom() {
		return false;
	}
}
