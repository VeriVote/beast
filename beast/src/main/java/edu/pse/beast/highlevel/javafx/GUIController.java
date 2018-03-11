package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GUIController {

	String pathToImages = "file:///" + SuperFolderFinder.getSuperFolder() + "/core/images/";
	
    @FXML // fx:id="maxVoter"
    private TextField maxVoter;

    @FXML // fx:id="minVoter"
    private TextField minVoter;

    @FXML // fx:id="maxCandidates"
    private TextField maxCandidates;

    @FXML // fx:id="minCandidates"
    private TextField minCandidates;

    @FXML // fx:id="maxSeats"
    private TextField maxSeats;

    @FXML // fx:id="minSeats"
    private TextField minSeats;

    @FXML // fx:id="timeOut"
    private TextField timeOut;

    @FXML // fx:id="TimeUnitChoice"
    private ChoiceBox<?> TimeUnitChoice;

    @FXML // fx:id="processes"
    private TextField processes;

    @FXML // fx:id="solverChoice"
    private ChoiceBox<?> solverChoice;

    @FXML // fx:id="advancedParameters1"
    private TextField advancedParameters1;

    @FXML // fx:id="maxUnrolls"
    private TextField maxUnrolls;

    @FXML // fx:id="helpButton"
    private MenuItem helpButton;

    @FXML // fx:id="startStopButton"
    private Button startStopButton;

    @FXML // fx:id="openButton"
    private Button openButton;

    @FXML // fx:id="saveButton"
    private Button saveButton;

    @FXML // fx:id="saveAsButton"
    private Button saveAsButton;

    @FXML // fx:id="undoButton"
    private Button undoButton;

    @FXML // fx:id="redoButton"
    private Button redoButton;

    @FXML // fx:id="cutButton"
    private Button cutButton;

    @FXML // fx:id="copyButton"
    private Button copyButton;

    @FXML // fx:id="pasteButton"
    private Button pasteButton;

    @FXML // fx:id="codePane"
    private Tab codePane;

    @FXML // fx:id="propertyPane"
    private Tab propertyPane;

    @FXML // fx:id="resultPane"
    private Tab resultPane;

    @FXML // fx:id="inputPane"
    private Tab inputPane;

    @FXML // fx:id="errorPane"
    private Tab errorPane;

    @FXML // fx:id="consolePane"
    private Tab consolePane;

    
	@FXML
	public void initialize() {
		//saveButton.setGraphic(new ImageView(pathToImages + "toolbar/save.png"));
	}
    
    //Top Panels
    @FXML
    void errorPaneClicked(Event event) {
    	
    }
    
    @FXML
    void inputPaneClicked(Event event) {
    	
    }
    
    @FXML
    void propertyPaneClicked(Event event) {
    	
    }
    
    @FXML
    void resultPaneClicked(Event event) {
    	
    }

    //------------
    //Bottom Panels
    @FXML
    void codePaneClicked(Event event) {

    }

    @FXML
    void consolePaneClicked(Event event) {

    }
    
    //--------
    //Icon Bar
    @FXML
    void startStopPressed(ActionEvent event) {
    	
    }
    
    @FXML
    void advancedParameters(Event event) {

    }
    
    @FXML
    void copyButton(ActionEvent event) {

    }    

    @FXML
    void undoButton(ActionEvent event) {

    }

    @FXML
    void cutButton(ActionEvent event) {

    }
    
    @FXML
    void openButton(ActionEvent event) {

    }

    @FXML
    void pasteButton(ActionEvent event) {
    	
    }
    
    @FXML
    void redoButton(ActionEvent event) {
    	
    }
    
    @FXML
    void saveAsButton(ActionEvent event) {
    	
    }
        
    @FXML
    void saveButton(ActionEvent event) {
    	
    }
    
    @FXML
    void deleteButton(ActionEvent event) {
    	
    }
    
    //text manipulation menu buttons
    @FXML
    void copy(ActionEvent event) {
    	copyButton(event);
    }

    @FXML
    void delete(ActionEvent event) {
    	deleteButton(event);
    }

    @FXML
    void cut(ActionEvent event) {
    	cutButton(event);
    }
    
    @FXML
    void paste(ActionEvent event) {
    	pasteButton(event);
    }

    @FXML
    void redo(ActionEvent event) {
    	redoButton(event);
    }
    
    @FXML
    void undo(ActionEvent event) {
    	undoButton(event);
    }
    
    //other menu buttons
    @FXML
    void helpClicked(ActionEvent event) {
    	
    }
    
    @FXML
    void maxCandidates(ActionEvent event) {

    }

    @FXML
    void maxSeats(ActionEvent event) {

    }

    @FXML
    void maxUnrolls(ActionEvent event) {

    }

    @FXML
    void maxVoter(ActionEvent event) {

    }

    @FXML
    void minCandidates(ActionEvent event) {

    }

    @FXML
    void minSeats(ActionEvent event) {

    }

    @FXML
    void minVoter(ActionEvent event) {

    }

    @FXML
    void newElectionDescription(ActionEvent event) {

    }

    @FXML
    void newProject(ActionEvent event) {

    }

    @FXML
    void newPropertyList(ActionEvent event) {

    }

    @FXML
    void newVotingInpup(ActionEvent event) {

    }
    
    @FXML
    void openElectionDescription(ActionEvent event) {

    }

    @FXML
    void openProject(ActionEvent event) {

    }

    @FXML
    void openPropertyList(ActionEvent event) {

    }

    @FXML
    void openVotingInput(ActionEvent event) {

    }

    @FXML
    void processes(ActionEvent event) {

    }

    @FXML
    void quitProgram(ActionEvent event) {

    }


    @FXML
    void saveAsElectionDescription(ActionEvent event) {

    }

    @FXML
    void saveAsProject(ActionEvent event) {

    }

    @FXML
    void saveAsPropertyList(ActionEvent event) {

    }

    @FXML
    void saveAsVotingInput(ActionEvent event) {

    }

    @FXML
    void saveElectionDescription(ActionEvent event) {

    }

    @FXML
    void saveProject(ActionEvent event) {

    }

    @FXML
    void savePropertyList(ActionEvent event) {

    }

    @FXML
    void saveVotingInput(ActionEvent event) {

    }

    @FXML
    void timeOut(ActionEvent event) {

    }

}
