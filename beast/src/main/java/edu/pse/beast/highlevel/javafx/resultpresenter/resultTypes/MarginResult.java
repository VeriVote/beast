package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.List;

import org.fxmisc.richtext.GenericStyledArea;
import org.reactfx.util.Either;

import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.LinkedImage;
import edu.pse.beast.toolbox.ParStyle;
import edu.pse.beast.toolbox.TextFieldCreator;
import edu.pse.beast.toolbox.TextStyle;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

/**
 * The Class MarginResult.
 *
 * @author Lukas Stapelbroek
 */
public class MarginResult extends ResultPresentationType {

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
        if (!result.hasFinalMargin()) {
            area.appendText("There is no final margin.");
        } else {
            area.appendText("Final Margin: " + result.getFinalMargin());
            area.appendText(
                    "\n=================================================");
            InputType inType = result.getElectionDescription().getContainer()
                    .getInputType();
            OutputType outType = result.getElectionDescription().getContainer()
                    .getOutputType();
            CBMCResultValueStruct structVotes = new CBMCResultValueStruct();
            structVotes.setValue(
                    (CBMCResultValueWrapper) result.getOrigVoting().getValues(),
                    UnifiedNameContainer.getStructValueName());
            ResultValueWrapper structVotesWrapped = new CBMCResultValueWrapper(
                    structVotes);
            List<String> toAdd = inType.drawResult(structVotesWrapped,
                    "\norig votes: ", -1L);
            for (int i = 0; i < toAdd.size(); i++) {
                area.appendText(toAdd.get(i));
            }
            toAdd = outType.drawResult(result.getOrigWinner().getValues(),
                    "\norig result: ", -1L);
            for (int i = 0; i < toAdd.size(); i++) {
                area.appendText(toAdd.get(i));
            }
            area.appendText(
                    "\n=================================================\n");
            toAdd = inType.drawResult(result.getNewVotes().getValues(),
                    "\nnew votes: ", -1L);
            for (int i = 0; i < toAdd.size(); i++) {
                area.appendText(toAdd.get(i));
            }
            toAdd = outType.drawResult(result.getNewWinner().getValues(),
                    "\nnew result: ", -1L);
            for (int i = 0; i < toAdd.size(); i++) {
                area.appendText(toAdd.get(i));
            }
        }
        return area;
    }

    @Override
    public String getName() {
        return "Margin Result";
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
        case Margin:
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
