package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

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

import edu.pse.beast.highlevel.javafx.AnalysisType;
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

public class TextAndImages extends ResultPresentationType {

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
																											// segment
																											// style
			styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()), // segment operations
			seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))); // Node creator and segment style
																					// setter

	@Override
	public Node presentResult(Result result) {

		//TODO still WIP, no images get created
		
		GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area = new GenericStyledArea<>(
				ParStyle.EMPTY, // default paragraph style
				(paragraph, style) -> paragraph.setStyle(style.toCss()), // paragraph style setter

				TextStyle.DEFAULT.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK), // default
																												// segment
																												// style
				styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()), // segment operations
				seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))); // Node creator and segment
		
		area.setEditable(false);
		
		Canvas can = new Canvas(100, 100);
		GraphicsContext g = can.getGraphicsContext2D();
		
        ReadOnlyStyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> ros =
                ReadOnlyStyledDocument.fromSegment(Either.right(new RealLinkedImage(new Canvas(100, 100))),
                                                   ParStyle.EMPTY, TextStyle.DEFAULT, area.getSegOps());
		

		g.setFill(Color.RED);
        g.fillOval(10, 60, 30, 30);
        
		String textTest = "testasdjfklasdf\n";
		
		for (int i = 0; i < 1000; i++) {
			area.replaceText(area.getLength(), area.getLength(), textTest);
			
		}
		
		area.replace(area.getLength(), area.getLength(), ros);
		
		VirtualizedScrollPane<GenericStyledArea> vsPane = new VirtualizedScrollPane<>(area); //Wrap it in a scroll area
		
		return vsPane;
	}

	@Override
	public String getName() {
		return "TextAndImagesWIP";
	}

	@Override
	public String getToolTipDescription() {
		return "WIP merge of images and text";
	}

	@Override
	public boolean supportsZoom() {
		return true;
	}

	@Override
	public void zoomTo(double zoomValue) {
//		if (zoomValue < 0) { //TODO add zoom later
//			currentScale = 1 + (0.09 * zoomValue);
//		} else {
//			currentScale = 1 + (0.9 * zoomValue);
//		}
//
//		if (imageDesiredWidth * currentScale > IMAGE_MAX_WIDTH) {
//			currentScale = Math.max(1, IMAGE_MAX_WIDTH / imageDesiredWidth);
//		}
//
//		if (imageDesiredHeight * currentScale > IMAGE_MAX_HEIGHT) {
//			currentScale = Math.max(1, IMAGE_MAX_HEIGHT / imageDesiredHeight);
//		}
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
