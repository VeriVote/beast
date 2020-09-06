package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.Tuple;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class RLAController {

    @FXML
    private Label marginLabel;

    @FXML
    private ListView<Integer> selectedVotes;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<String, Tuple<String, String>> originalVoteCol;
    @FXML
    private TableColumn<String, Tuple<String, String>> correctVoteCol;

    private Result result;
    public RLAController(Result result) {
        this.result = result;

    }
    @FXML
    public void initialize() {
        ObservableList<Integer> list = FXCollections.observableList(new ArrayList<Integer>());
        list.add(5);
        list.add(4);
        selectedVotes.setItems(list);
        PropertyValueFactory factory1 = new PropertyValueFactory("first");
        originalVoteCol.setCellFactory(factory1);
        correctVoteCol.setCellFactory(new PropertyValueFactory("second"));
        tableView.getItems().add(new Tuple<String, String>("first", "second"));
    }

    private int getMargin() {
        return result.getFinalMargin();
    }
}
