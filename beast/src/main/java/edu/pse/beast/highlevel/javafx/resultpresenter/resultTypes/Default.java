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
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

/**
 * We just print out the input votes, and the result
 *
 * @author Lukas Stapelbroek
 *
 */
public class Default extends ResultPresentationType {

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

		InputType inType = result.getElectionDescription().getContainer().getInputType();
		OutputType outType = result.getElectionDescription().getContainer().getOutputType();

		String votesNameMatcher = inType.getContainer().getNameContainer().getVotingArray() + "\\d";

		List<String> toAdd = inType.drawResult(result, votesNameMatcher);

		for (int i = 0; i < toAdd.size(); i++) {
			area.appendText(toAdd.get(i));
		}
		
		String resultNameMatcher = outType.getContainer().getNameContainer().getVotingArray() + "\\d";

		toAdd = outType.drawResult(result, resultNameMatcher);

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
	public void zoomTo(double zoomValue) {
		if (area != null) {
			area.setStyle(0, area.getLength(), TextStyle.DEFAULT.fontSize((int) (standartSize + zoomValue)));
		}
	}

	@Override
	public boolean supports(AnalysisType analysisType) {
		switch (analysisType) {
		case Check:
			return true;
		default:
			return false;
		}
	}
}
