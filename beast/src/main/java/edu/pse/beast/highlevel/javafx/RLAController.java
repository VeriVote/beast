package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.rla.RiskLimitingAuditSTV;
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

    RiskLimitingAuditSTV audit;
    private Result result;
    public RLAController(Result result) {
        this.result = result;

    }
    @FXML
    public void initialize() {

        audit = new RiskLimitingAuditSTV(result.getFinalMargin(), 0.05, 0.01, 1, result.getNumVoters());

        ObservableList<Integer> list = FXCollections.observableList(audit.getSelectedVotes());
        list.add(5);
        list.add(4);
        selectedVotes.setItems(list);
    }

    private int getMargin() {
        return result.getFinalMargin();
    }
}
