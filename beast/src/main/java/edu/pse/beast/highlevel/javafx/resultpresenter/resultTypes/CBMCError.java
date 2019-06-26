package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import edu.pse.beast.codeareajavafx.RichTextFXCodeArea;
import edu.pse.beast.propertychecker.Result;
import javafx.scene.Node;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class CBMCError extends ResultPresentationType {
	
	@Override
	public Node presentResult(Result result) {
		
		
		//TODO 
		
		
		return null;
//		RichTextFXCodeArea
//		
//		
//		TextFlow resultTextField = new TextFlow();
//		resultTextField.setTextAlignment(TextAlignment.LEFT);	
//		resultTextField.autosize();
//		
//			
//		resultTextField.getChildren().addAll(result.getErrorAsTextField());
//
//		return resultTextField;
	}

	@Override
	public String getName() {
		return "CBMC Error";
	}

	@Override
	public String getToolTipDescription() {
		return "Shows the error output cbmc gave";
	}

	@Override
	public boolean supportsZoom() {
		return false;
	}
}
