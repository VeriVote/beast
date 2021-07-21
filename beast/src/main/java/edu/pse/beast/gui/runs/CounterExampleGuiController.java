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
    private static final String BLANK = " ";
    private static final String LINE_BREAK = "\n";
    private static final String COMMA = ",";

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

    private static List<Integer> numbersFromBrackets(final String brackets) {
        final List<Integer> bracketNumbers = new ArrayList<>();

        String number = "";
        for (int i = 0; i < brackets.length(); ++i) {
            final char c = brackets.charAt(i);
            if (Character.isDigit(c)) {
                number += c;
            } else if (!number.isBlank()) {
                bracketNumbers.add(Integer.valueOf(number));
                number = "";
            }
        }
        return bracketNumbers;
    }

    private static String buildAssignmentString(final CBMCStructAssignment assignment,
                                                final String amtVarName,
                                                final String listVarName) {
        final Map<List<Integer>, String> positionsToStrings = new HashMap<>();
        int amtBrackets = 0;

        for (final String key : assignment.getMemberToAssignment().keySet()) {
            if (key.equals(amtVarName) || key.equals(listVarName)) { // no brackets
                continue;
            }
            final String brackets = key.substring(listVarName.length());
            final List<Integer> bracketNumbers = numbersFromBrackets(brackets);
            amtBrackets = bracketNumbers.size();
            positionsToStrings.put(bracketNumbers,
                                   assignment.getMemberToAssignment().get(key));
        }

        String assignmentString = "";
        if (amtBrackets == 0) {
            final String value = assignment.getAssignmentFor(listVarName);
            assignmentString = listVarName + BLANK + value + LINE_BREAK;
        } else if (amtBrackets == 1) {
            for (int i = 0; i < 100; ++i) {
                for (final List<Integer> positions : positionsToStrings.keySet()) {
                    if (positions.get(0) == i) {
                        assignmentString +=
                                positionsToStrings.get(positions) + COMMA + BLANK;
                        continue;
                    }
                }
            }
            assignmentString += LINE_BREAK;
        } else if (amtBrackets == 2) {
            for (int i = 0; i < 100; ++i) {
                boolean foundNew = false;
                for (int j = 0; j < 100; ++j) {
                    for (final List<Integer> positions : positionsToStrings.keySet()) {
                        if (positions.get(0) == i && positions.get(1) == j) {
                            assignmentString +=
                                    positionsToStrings.get(positions) + COMMA + BLANK;
                            foundNew = true;
                            continue;
                        }
                    }
                }
                if (foundNew) {
                    assignmentString += LINE_BREAK;
                }
            }
        }
        return assignmentString;
    }

    private void displayAssignment(final CBMCStructAssignment assignment) {
        final String structName = assignment.getAssignmentType().toString();

        String amtVarName = "";
        String listVarName = "";
        switch (assignment.getAssignmentType()) {
        case ELECT:
        case GENERATED_ELECT:
            amtVarName = codeInfo.getResultAmtMemberVarName();
            listVarName = codeInfo.getResultListMemberVarName();
            break;
        case VOTE:
        case GENERATED_VOTE:
            amtVarName = codeInfo.getVotesAmtMemberVarName();
            listVarName = codeInfo.getVotesListMemberVarName();
            break;
        default:
            break;
        }

        final String assignmString = buildAssignmentString(assignment, amtVarName, listVarName);
        final String textString =
                structName + BLANK + assignment.getVarName() + "{" + LINE_BREAK
                + amtVarName + BLANK + assignment.getAssignmentFor(amtVarName) + LINE_BREAK
                + assignmString + "}" + LINE_BREAK
                + "-----------------------" + LINE_BREAK;
        displayCodearea.appendText(textString);
    }

    public final AnchorPane display(final CBMCCounterExample example) {
        currentExample = example;
        codeInfo = example.getCbmcGeneratedCodeInfo();
        display();
        return topLevelAnchorPane;
    }

    private void display() {
        final Set<CBMCAssignmentType> types = new HashSet<>();
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
        final List<CBMCStructAssignment> asses = currentExample.getAssignments(types);

        overviewListView.getItems().clear();
        overviewListView.getItems().addAll(asses);

        displayCodearea.clear();
        for (final CBMCStructAssignment ass : asses) {
            displayAssignment(ass);
        }
    }

    private void displayOnChange(final CheckBox cb) {
        cb.setOnAction(e -> {
            display();
        });
    }

    @FXML
    public final void initialize() {
        displayOnChange(votesCheckbox);
        displayOnChange(generatedVotesCheckbox);
        displayOnChange(resultsCheckbox);
        displayOnChange(generatedResultsCheckbox);
        final VirtualizedScrollPane<CodeArea> vsp1 =
                new VirtualizedScrollPane<>(codeinfoCodearea);
        codeinfoAnchorpane.getChildren().add(vsp1);
        AnchorPane.setTopAnchor(vsp1, 0d);
        AnchorPane.setBottomAnchor(vsp1, 0d);
        AnchorPane.setLeftAnchor(vsp1, 0d);
        AnchorPane.setRightAnchor(vsp1, 0d);

        final VirtualizedScrollPane<CodeArea> vsp2 =
                new VirtualizedScrollPane<>(displayCodearea);
        displayAnchorpane.getChildren().add(vsp2);
        AnchorPane.setTopAnchor(vsp2, 0d);
        AnchorPane.setBottomAnchor(vsp2, 0d);
        AnchorPane.setLeftAnchor(vsp2, 0d);
        AnchorPane.setRightAnchor(vsp2, 0d);

        overviewListView.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    codeinfoCodearea.clear();
                    if (n == null) {
                        return;
                    }
                    if (n.getVarInfo() != null) {
                        codeinfoCodearea.appendText(n.getVarInfo());
                    }
                });
    }
}
