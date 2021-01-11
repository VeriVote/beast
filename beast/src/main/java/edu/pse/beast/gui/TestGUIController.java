package edu.pse.beast.gui;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.codeareajavafx.AutoCompletionCodeArea;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.highlevel.javafx.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class TestGUIController {
	
	@FXML
	private AnchorPane codePane;
	
	@FXML
	private Accordion functionacc;
	
	@FXML
	public void changeVote() {
	}
	
	@FXML
	public void addFunction() {
		AnchorPane anchor = new AnchorPane();
		CodeArea codeArea = new CodeArea(); 		
		final VirtualizedScrollPane<CodeArea> vsp =
                new VirtualizedScrollPane<>(codeArea);
		 codePane.getChildren().add(vsp);
	        AnchorPane.setTopAnchor(vsp, (double) 0);
	        AnchorPane.setBottomAnchor(vsp, (double) 0);
	        AnchorPane.setLeftAnchor(vsp, (double) 0);
	        AnchorPane.setRightAnchor(vsp, (double) 0);
		
		functionacc.getPanes().add(new TitledPane("func", anchor));
	}
	
	@FXML
	public void removeFunction() {
		
	}
	
	@FXML
	public void initialize() {		
		CodeArea codeArea = new CodeArea(); 
				
		final VirtualizedScrollPane<CodeArea> vsp =
                new VirtualizedScrollPane<>(codeArea);
        codePane.getChildren().add(vsp);
        AnchorPane.setTopAnchor(vsp, (double) 0);
        AnchorPane.setBottomAnchor(vsp, (double) 0);
        AnchorPane.setLeftAnchor(vsp, (double) 0);
        AnchorPane.setRightAnchor(vsp, (double) 0);
	}
	
}
