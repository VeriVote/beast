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
import edu.pse.beast.toolbox.TextStyle;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CBMCOutput extends ResultPresentationType {

	private final TextOps<String, TextStyle> styledTextOps = SegmentOps.styledTextOps();

	private final LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();

	private Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
			BiConsumer<? super TextExt, TextStyle> applyStyle) {
		return seg.getSegment().unify(text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
				LinkedImage::createNode);
	}

	private final GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area = new GenericStyledArea<>(
			ParStyle.EMPTY, // default paragraph style
			(paragraph, style) -> paragraph.setStyle(style.toCss()), // paragraph style setter

			TextStyle.DEFAULT.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK), // default
																											// segment																				// style
			styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()), // segment operations
			seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))); // Node creator and segment style
																					// setter

	@Override
	public Node presentResult(Result result) {

		//generate the text area in which the results can be displayed
		GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area = new GenericStyledArea<>(
				ParStyle.EMPTY, // default paragraph style
				(paragraph, style) -> paragraph.setStyle(style.toCss()), // paragraph style setter

				TextStyle.DEFAULT.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK), // default
																												// segment
																												// style
				styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()), // segment operations
				seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))); // Node creator and segment

		area.setEditable(false);
		
		List<String> resultText = result.getResultText();
		
		for (Iterator<String> iterator = resultText.iterator(); iterator.hasNext();) {
			String text = (String) iterator.next();
			area.appendText(text);
		}

		VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane
			= new VirtualizedScrollPane<>(area); // Wrap it in a scroll area

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
		return false;
	}
}