package edu.pse.beast.gui.options.ceditor;

import java.io.IOException;

import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsCategoryType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CEditorOptionsGUI extends OptionsCategoryGUI {
	@FXML
	private AnchorPane topLevelAnchorpane;
	@FXML
	private TextField fontSizeTextField;
	@FXML
	private Slider fontSizeSlider;
	
	private String fxml = "/edu/pse/beast/cEditorOptions.fxml";
	private FXMLLoader fxmlLoader = new FXMLLoader(
			getClass().getResource(fxml));
	
	//============options start
	private int fontSize;
	
	
	private CElectionEditor cElectionEditor;
	
	public CEditorOptionsGUI() throws IOException {
		super(OptionsCategoryType.C_DESCR_EDITOR);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}
	
	@FXML
	public void initialize() {
		System.out.println("ceditor options init");
	}

	@Override
	public void displayOptions(AnchorPane currentOptionDisplayAnchorpane) {
		displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);		
	}
	
}
