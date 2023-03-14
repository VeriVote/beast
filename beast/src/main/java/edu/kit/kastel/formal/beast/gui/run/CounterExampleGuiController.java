package edu.kit.kastel.formal.beast.gui.run;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.trace.AssignmentType;
import edu.kit.kastel.formal.beast.api.trace.CounterExample;
import edu.kit.kastel.formal.beast.api.trace.StructAssignment;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CounterExampleGuiController {
    private static final String BLANK = " ";
    private static final String LINE_BREAK = "\n";
    private static final String COMMA = ", ";
    private static final int LINE_WIDTH = 100;

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
    private ListView<StructAssignment> overviewListView;
    @FXML
    private AnchorPane codeinfoAnchorpane;
    @FXML
    private AnchorPane displayAnchorpane;

    private CodeArea codeinfoCodearea = new CodeArea();
    private CodeArea displayCodearea = new CodeArea();

    private CounterExample currentExample;
    private GeneratedCodeInfo codeInfo;

    private static List<Integer> numbersFromBrackets(final String brackets) {
        final List<Integer> bracketNumbers = new ArrayList<Integer>();

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

    private static String buildAssignmentString(final StructAssignment assignment,
                                                final String amtVarName,
                                                final String listVarName) {
        final Map<List<Integer>, String> positionsToStrings =
                new LinkedHashMap<List<Integer>, String>();
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

        StringBuilder assignmentString = new StringBuilder();
        if (amtBrackets == 0) {
            final String value = assignment.getAssignmentFor(listVarName);
            assignmentString = new StringBuilder(listVarName + BLANK + value + LINE_BREAK);
        } else if (amtBrackets == 1) {
            for (int i = 0; i < LINE_WIDTH; ++i) {
                for (final Map.Entry<List<Integer>, String> positions
                        : positionsToStrings.entrySet()) {
                    if (positions.getKey().get(0) == i) {
                        assignmentString.append(positions.getValue() + COMMA);
                        continue;
                    }
                }
            }
            assignmentString.append(LINE_BREAK);
        } else if (amtBrackets == 2) {
            for (int i = 0; i < LINE_WIDTH; ++i) {
                boolean foundNew = false;
                for (int j = 0; j < LINE_WIDTH; ++j) {
                    for (final Map.Entry<List<Integer>, String> posToString
                            : positionsToStrings.entrySet()) {
                        if (posToString.getKey().get(0) == i && posToString.getKey().get(1) == j) {
                            assignmentString.append(posToString.getValue() + COMMA);
                            foundNew = true;
                            continue;
                        }
                    }
                }
                if (foundNew) {
                    assignmentString.append(LINE_BREAK);
                }
            }
        }
        return assignmentString.toString();
    }

    private void displayAssignment(final StructAssignment assignment) {
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

    public final AnchorPane display(final CounterExample example) {
        currentExample = example;
        codeInfo = example.getGeneratedCodeInfo();
        display();
        return topLevelAnchorPane;
    }

    private void display() {
        final Set<AssignmentType> types = new LinkedHashSet<AssignmentType>();
        if (votesCheckbox.isSelected()) {
            types.add(AssignmentType.VOTE);
        }
        if (generatedVotesCheckbox.isSelected()) {
            types.add(AssignmentType.GENERATED_VOTE);
        }
        if (resultsCheckbox.isSelected()) {
            types.add(AssignmentType.ELECT);
        }
        if (generatedResultsCheckbox.isSelected()) {
            types.add(AssignmentType.GENERATED_ELECT);
        }
        final List<StructAssignment> asses = currentExample.getAssignments(types);

        overviewListView.getItems().clear();
        overviewListView.getItems().addAll(asses);

        displayCodearea.clear();
        for (final StructAssignment ass : asses) {
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
                new VirtualizedScrollPane<CodeArea>(codeinfoCodearea);
        codeinfoAnchorpane.getChildren().add(vsp1);
        AnchorPane.setTopAnchor(vsp1, 0d);
        AnchorPane.setBottomAnchor(vsp1, 0d);
        AnchorPane.setLeftAnchor(vsp1, 0d);
        AnchorPane.setRightAnchor(vsp1, 0d);

        final VirtualizedScrollPane<CodeArea> vsp2 =
                new VirtualizedScrollPane<CodeArea>(displayCodearea);
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
