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

/**
 * The Class TextAndImages.
 *
 * @author Lukas Stapelbroek
 */
public class TextAndImages extends ResultPresentationType {

    /** The Constant DEFAULT_FONT_SIZE. */
    private static final int DEFAULT_FONT_SIZE = 12;

    /** The Constant THOUSAND. */
    private static final int THOUSAND = 1000;

    /** The Constant CANVAS_SIZE. */
    private static final int CANVAS_SIZE = 100;

    /** The Constant OVAL_X_COORD. */
    private static final int OVAL_X_COORD = 10;

    /** The Constant OVAL_Y_COORD. */
    private static final int OVAL_Y_COORD = 60;

    /** The Constant OVAL_SIZE. */
    private static final int OVAL_SIZE = 30;

    /** The styled text ops. */
    private final TextOps<String, TextStyle> styledTextOps =
            SegmentOps.styledTextOps();

    /** The linked image ops. */
    private final LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();

    /** The area. */
    private final GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area =
            new GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>(
                    ParStyle.EMPTY, // default paragraph style
                    // paragraph style setter
                (paragraph, style) -> paragraph.setStyle(style.toCss()),
                    TextStyle.DEFAULT.updateFontSize(DEFAULT_FONT_SIZE)
                    // default segment style
                    .updateFontFamily("Serif").updateTextColor(Color.BLACK),
                    // segment operations
                    styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()),
                seg -> createNode(seg,
                            // Node creator and segment style setter
                    (text, style) -> text.setStyle(style.toCss())
                            )
                    );

    /**
     * Creates the node.
     *
     * @param seg
     *            the seg
     * @param applyStyle
     *            the apply style
     * @return the node
     */
    private Node createNode(final StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
                            final BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg
                .getSegment().unify(
                    text -> StyledTextArea.createStyledTextNode(text,
                                seg.getStyle(), applyStyle),
                        LinkedImage::createNode);
    }

    @Override
    public Node presentResult(final Result result) {
        // TODO still WIP, no images get created
        GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> resultArea =
                new GenericStyledArea<>(
                        // default paragraph style paragraph style setter
                        ParStyle.EMPTY,
                    (paragraph, style) -> paragraph.setStyle(style.toCss()),
                        TextStyle.DEFAULT.updateFontSize(DEFAULT_FONT_SIZE)
                        // default segment style
                        .updateFontFamily("Serif").updateTextColor(Color.BLACK),
                        // segment operations
                        styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()),
                    seg -> createNode(seg, // Node creator and segment
                        (text, style) -> text.setStyle(style.toCss())));
        resultArea.setEditable(false);
        Canvas can = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        GraphicsContext g = can.getGraphicsContext2D();

        ReadOnlyStyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> ros =
                ReadOnlyStyledDocument.fromSegment(
                        Either.right(new RealLinkedImage(
                                new Canvas(CANVAS_SIZE, CANVAS_SIZE))),
                        ParStyle.EMPTY, TextStyle.DEFAULT,
                        resultArea.getSegOps());
        g.setFill(Color.RED);
        g.fillOval(OVAL_X_COORD, OVAL_Y_COORD, OVAL_SIZE, OVAL_SIZE);
        String textTest = "testasdjfklasdf\n";
        for (int i = 0; i < THOUSAND; i++) {
            resultArea.replaceText(resultArea.getLength(),
                                   resultArea.getLength(), textTest);
        }
        resultArea.replace(resultArea.getLength(), resultArea.getLength(), ros);
        VirtualizedScrollPane<GenericStyledArea<ParStyle,
                                                Either<String, LinkedImage>,
                                                TextStyle>> vsPane =
            new VirtualizedScrollPane<>(resultArea); // Wrap it in a scroll area
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
    public void zoomTo(final double zoomValue) {
        // if (zoomValue < 0) { //TODO add zoom later
        // currentScale = 1 + (0.09 * zoomValue);
        // } else {
        // currentScale = 1 + (0.9 * zoomValue);
        // }
        //
        // if (imageDesiredWidth * currentScale > IMAGE_MAX_WIDTH) {
        // currentScale = Math.max(1, IMAGE_MAX_WIDTH / imageDesiredWidth);
        // }
        //
        // if (imageDesiredHeight * currentScale > IMAGE_MAX_HEIGHT) {
        // currentScale = Math.max(1, IMAGE_MAX_HEIGHT / imageDesiredHeight);
        // }
    }

    @Override
    public boolean supports(final AnalysisType analysisType) {
        switch (analysisType) {
        case Check: // TODO
            return false;
        default:
            return false;
        }
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
