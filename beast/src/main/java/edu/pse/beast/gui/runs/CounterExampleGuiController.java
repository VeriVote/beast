package edu.pse.beast.gui.runs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCAssignmentType;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCCounterExample;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCStructAssignment;

public class CounterExampleGuiController {
    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private CheckBox votesCheckbox;
    @FXML
    private CheckBox generatedVotesCheckbox;
    @FXML
    private CheckBox resultsCheckbox;
    @FXML
    private CheckBox generatedResultsCheckbox;
    @FXML
    private ListView<CBMCStructAssignment> overviewListView;
    @FXML
    private AnchorPane codeinfoAnchorpane;
    @FXML
    private AnchorPane displayAnchorpane;

    private CodeArea codeinfoCodearea = new CodeArea();
    private CodeArea displayCodearea = new CodeArea();

    private CBMCCounterExample currentExample;
    private CBMCGeneratedCodeInfo codeInfo;

    public AnchorPane display(CBMCCounterExample example) {
        currentExample = example;
        codeInfo = example.getCbmcGeneratedCodeInfo();
        display();
        return topLevelAnchorPane;
    }

    private void display() {
        Set<CBMCAssignmentType> types = new HashSet<>();
        if (votesCheckbox.isSelected()) {
            types.add(CBMCAssignmentType.VOTE);
        }
        if (generatedVotesCheckbox.isSelected()) {
            types.add(CBMCAssignmentType.GENERATED_VOTE);
        }
        if (resultsCheckbox.isSelected()) {
            types.add(CBMCAssignmentType.ELECT);
        }
        if (generatedResultsCheckbox.isSelected()) {
            types.add(CBMCAssignmentType.GENERATED_ELECT);
        }
        List<CBMCStructAssignment> asses = currentExample.getAssignments(types);

        overviewListView.getItems().clear();
        overviewListView.getItems().addAll(asses);

        displayCodearea.clear();
        for (CBMCStructAssignment ass : asses) {
            displayAss(ass);
        }
    }

    private void displayAss(CBMCStructAssignment ass) {
        String structName = ass.getAssignmentType().toString();

        String amtVarName = "";
        String listVarName = "";
        switch (ass.getAssignmentType()) {
        case ELECT:
        case GENERATED_ELECT:
            amtVarName = codeInfo.getResultAmtMemberVarName();
            listVarName = codeInfo.getResultListMemberVarName();
            break;
        case VOTE:
        case GENERATED_VOTE:
            amtVarName = codeInfo.getVotesAmtMemberVarName();
            listVarName = codeInfo.getVotesListMemberVarName();
        default:
            break;
        }

        Map<List<Integer>, String> positionsToStrings = new HashMap<>();
        int amtBrackets = 0;

        for (String key : ass.getMemberToAssignment().keySet()) {
            if (key.equals(amtVarName))
                continue;
            if (key.equals(listVarName)) {// no brackets
                continue;
            }
            String brackets = key.substring(listVarName.length());
            List<Integer> bracketNumbers = numbersFromBrackets(brackets);

            amtBrackets = bracketNumbers.size();

            positionsToStrings.put(bracketNumbers,
                    ass.getMemberToAssignment().get(key));
        }

        String assignmentString = "";
        if (amtBrackets == 0) {
            String value = ass.getAssignmentFor(listVarName);
            assignmentString = listVarName + " " + value + "\n";
        } else if (amtBrackets == 1) {
            for (int i = 0; i < 100; ++i) {
                boolean foundNew = false;

                for (List<Integer> positions : positionsToStrings.keySet()) {
                    if (positions.get(0) == i) {
                        assignmentString += positionsToStrings.get(positions)
                                + ", ";
                        foundNew = true;
                        continue;
                    }
                }
            }
            assignmentString += "\n";
        } else if (amtBrackets == 2) {
            for (int i = 0; i < 100; ++i) {
                boolean foundNew = false;
                for (int j = 0; j < 100; ++j) {
                    for (List<Integer> positions : positionsToStrings
                            .keySet()) {
                        if (positions.get(0) == i && positions.get(1) == j) {
                            assignmentString += positionsToStrings
                                    .get(positions) + ", ";
                            foundNew = true;
                            continue;
                        }
                    }
                }
                if (foundNew)
                    assignmentString += "\n";
            }
        }

        String s = structName + " " + ass.getVarName() + "{\n";
        s += amtVarName + " " + ass.getAssignmentFor(amtVarName) + "\n";
        s += assignmentString;
        s += "}\n-----------------------\n";

        displayCodearea.appendText(s);
    }

    private List<Integer> numbersFromBrackets(String brackets) {
        List<Integer> bracketNumbers = new ArrayList<>();

        String number = "";
        for (int i = 0; i < brackets.length(); ++i) {
            char c = brackets.charAt(i);
            if (Character.isDigit(c)) {
                number += c;
            } else if (!number.isBlank()) {
                bracketNumbers.add(Integer.valueOf(number));
                number = "";
            }
        }

        return bracketNumbers;
    }

    private void displayOnChange(CheckBox cb) {
        cb.setOnAction(e -> {
            display();
        });
    }

    @FXML
    public void initialize() {
        displayOnChange(votesCheckbox);
        displayOnChange(generatedVotesCheckbox);
        displayOnChange(resultsCheckbox);
        displayOnChange(generatedResultsCheckbox);
        VirtualizedScrollPane<CodeArea> vsp1 = new VirtualizedScrollPane<>(
                codeinfoCodearea);
        codeinfoAnchorpane.getChildren().add(vsp1);
        AnchorPane.setTopAnchor(vsp1, 0d);
        AnchorPane.setBottomAnchor(vsp1, 0d);
        AnchorPane.setLeftAnchor(vsp1, 0d);
        AnchorPane.setRightAnchor(vsp1, 0d);

        VirtualizedScrollPane<CodeArea> vsp2 = new VirtualizedScrollPane<>(
                displayCodearea);
        displayAnchorpane.getChildren().add(vsp2);
        AnchorPane.setTopAnchor(vsp2, 0d);
        AnchorPane.setBottomAnchor(vsp2, 0d);
        AnchorPane.setLeftAnchor(vsp2, 0d);
        AnchorPane.setRightAnchor(vsp2, 0d);

        overviewListView.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    codeinfoCodearea.clear();
                    if (n == null)
                        return;
                    if (n.getVarInfo() != null)
                        codeinfoCodearea.appendText(n.getVarInfo());
                });
    }

}
