package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.LinkedImage;
import edu.pse.beast.toolbox.LinkedImageOps;
import edu.pse.beast.toolbox.ParStyle;
import edu.pse.beast.toolbox.RealLinkedImage;
import edu.pse.beast.toolbox.TextFieldCreator;
import edu.pse.beast.toolbox.TextStyle;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CBMCOutput extends ResultPresentationType {

	GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area;

	int standartSize = 10;

	@Override
	public Node presentResult(Result result) {

		if (area == null) {
			area = TextFieldCreator.getGenericStyledAreaInstance(TextStyle.DEFAULT.fontSize(standartSize),
					ParStyle.EMPTY);
			area.setEditable(false);
		}

		List<String> resultText = result.getResultText();

		for (Iterator<String> iterator = resultText.iterator(); iterator.hasNext();) {
			String text = (String) iterator.next();
			area.appendText(text);
		}

		VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(
				area); // Wrap it in a scroll area

		return vsPane;
	}

	@Override
	public String getName() {
		return "CBMC Output";
	}

	@Override
	public String getToolTipDescription() {
		return "Shows the complete output cbmc gave";
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
}
