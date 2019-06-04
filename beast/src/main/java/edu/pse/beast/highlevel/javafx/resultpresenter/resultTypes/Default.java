package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import edu.pse.beast.highlevel.javafx.resultpresenter.ResultImageRenderer;
import edu.pse.beast.propertychecker.Result;
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
	
	@Override
	public Node presentResult(Result result) {
		InputType inType = result.getElectionDescription().getContainer().getInputType();
		OutputType outType = result.getElectionDescription().getContainer().getOutputType();

		int maxY = inType.drawResult(result, 0);

		outType.drawResult(result, maxY);

		ResultImageRenderer.drawElements();

		return (ResultImageRenderer.getImageView());
	}

	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public String getToolTipDescription() {
		return "The Defaul way of presenting a result";
	}
}
