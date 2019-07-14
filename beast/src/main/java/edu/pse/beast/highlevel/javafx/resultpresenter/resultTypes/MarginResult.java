package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.awt.Container;
import java.util.List;

import org.fxmisc.richtext.GenericStyledArea;
import org.reactfx.util.Either;

import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.LinkedImage;
import edu.pse.beast.toolbox.ParStyle;
import edu.pse.beast.toolbox.TextFieldCreator;
import edu.pse.beast.toolbox.TextStyle;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

public class MarginResult extends ResultPresentationType {

	GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area;

	int standartSize = 10;

	@Override
	public Node presentResult(Result result) {
		
		area.clear();

		if (area == null) {
			area = TextFieldCreator.getGenericStyledAreaInstance(TextStyle.DEFAULT.fontSize(standartSize),
					ParStyle.EMPTY);
			area.setEditable(false);
		}
		
		if (!result.hasFinalMargin()) {
			area.appendText("There is no final margin.");
		} else {

			area.appendText("Final Margin: " + result.getFinalMargin());

			area.appendText("\n=================================================");
			
			InputType inType = result.getElectionDescription().getContainer().getInputType();
			OutputType outType = result.getElectionDescription().getContainer().getOutputType();

			CBMCResultValueStruct structVotes = new CBMCResultValueStruct();

			structVotes.setValue((CBMCResultValueWrapper) result.getOrigVoting().values,
					inType.getContainer().getNameContainer().getStructValueName());

			ResultValueWrapper structVotesWrapped = new CBMCResultValueWrapper(structVotes);

			List<String> toAdd = inType.drawResult(structVotesWrapped, "\norig votes: ");

			for (int i = 0; i < toAdd.size(); i++) {
				area.appendText(toAdd.get(i));
			}

			toAdd = outType.drawResult(result.getOrigWinner().values, "\norig result: ");

			for (int i = 0; i < toAdd.size(); i++) {
				area.appendText(toAdd.get(i));
			}
			
			area.appendText("\n=================================================\n");
			
			toAdd = inType.drawResult(result.getNewVotes().values, "\nnew votes: ");
			
			for (int i = 0; i < toAdd.size(); i++) {
				area.appendText(toAdd.get(i));
			}
			
			toAdd = outType.drawResult(result.getNewWinner().values, "\nnew result: ");
			
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
	public void zoomTo(double zoomValue) {
		if (area != null) {
			area.setStyle(0, area.getLength(), TextStyle.DEFAULT.fontSize((int) (standartSize + zoomValue)));
		}
	}

	@Override
	public boolean supports(AnalysisType analysisType) {
		switch (analysisType) {
		case Margin:
			return true;
		default:
			return false;
		}
	}

}
