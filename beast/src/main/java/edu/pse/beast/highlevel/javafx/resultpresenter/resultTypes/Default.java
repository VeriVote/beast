package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.List;
import java.util.Map;

import org.fxmisc.richtext.GenericStyledArea;
import org.reactfx.util.Either;

import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.LinkedImage;
import edu.pse.beast.toolbox.ParStyle;
import edu.pse.beast.toolbox.TextFieldCreator;
import edu.pse.beast.toolbox.TextStyle;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

/**
 * We just print out the input votes, and the result.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class Default extends ResultPresentationType {
    /** The area. */
    private GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area;

    @Override
    public Node presentResult(final Result result) {
        if (area == null) {
            area = TextFieldCreator.getGenericStyledAreaInstance(
                    TextStyle.fontSize(STANDARD_SIZE), ParStyle.EMPTY);
            area.setEditable(false);
        }
        area.clear();
        InputType inType =
                result.getElectionDescription().getContainer().getInputType();
        OutputType outType =
                result.getElectionDescription().getContainer().getOutputType();
        String voters = UnifiedNameContainer.getVoter() + "\\d";
        Map<Integer, Long> sizeOfVoters =
                getAllSizes(result.readVariableValue(voters));
        String votesNameMatcher = UnifiedNameContainer.getVotingArray() + "\\d";
        List<String> toAdd = inType.drawResult(result, votesNameMatcher,
                                               sizeOfVoters);

        for (int i = 0; i < toAdd.size(); i++) {
            area.appendText(toAdd.get(i));
        }
        String candidates = UnifiedNameContainer.getCandidate() + "\\d";
        Map<Integer, Long> sizeOfCandidates =
                getAllSizes(result.readVariableValue(candidates));
        String resultNameMatcher = UnifiedNameContainer.getElect() + "\\d";
        toAdd = outType.drawResult(result, resultNameMatcher, sizeOfCandidates);

        for (int i = 0; i < toAdd.size(); i++) {
            area.appendText(toAdd.get(i));
        }
        return area;
    }

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public String getToolTipDescription() {
        return "The Defaul way of presenting a result";
    }

    @Override
    public boolean supportsZoom() {
        return true;
    }

    @Override
    public void zoomTo(final double zoomValue) {
        if (area != null) {
            area.setStyle(0, area.getLength(),
                          TextStyle.fontSize((int) (STANDARD_SIZE + zoomValue)));
        }
    }

    @Override
    public boolean supports(final AnalysisType analysisType) {
        switch (analysisType) {
        case Check:
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}
