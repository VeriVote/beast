package edu.pse.beast.zzz.toolbox;

import java.util.Optional;
import java.util.function.BiConsumer;

import javafx.scene.Node;

import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

/**
 * The Class TextFieldCreator.
 *
 * @author Lukas Stapelbroek
 */
public final class TextFieldCreator {

    /** The Constant STYLED_TEXT_OPS. */
    private static final TextOps<String, TextStyle> STYLED_TEXT_OPS =
            SegmentOps.styledTextOps();

    /** The Constant LINKED_IMAGE_OPS. */
    private static final LinkedImageOps<TextStyle> LINKED_IMAGE_OPS =
            new LinkedImageOps<TextStyle>();

    /**
     * The constructor.
     */
    private TextFieldCreator() { }

    /**
     * Creates the node.
     *
     * @param seg
     *            the seg
     * @param applyStyle
     *            the apply style
     * @return the node
     */
    private static Node createNode(final StyledSegment<Either<String, LinkedImage>,
                                                       TextStyle> seg,
                                   final BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg
                .getSegment().unify(
                    text -> StyledTextArea.createStyledTextNode(text,
                                seg.getStyle(), applyStyle),
                        LinkedImage::createNode);
    }

    /**
     * Gets the generic styled area instance.
     *
     * @param textStyle
     *            the text style
     * @param paragraphStyle
     *            the paragraph style
     * @return the generic styled area instance
     */
    public static GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>
                    getGenericStyledAreaInstance(final TextStyle textStyle,
                                                 final ParStyle paragraphStyle) {
        final GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area =
                new GenericStyledArea<ParStyle,
                                      Either<String, LinkedImage>,
                                      TextStyle>(
                        // default paragraph style
                        paragraphStyle,
                    // paragraph style setter
                    (paragraph, style) -> paragraph.setStyle(style.toCss()),
                        // default segment style
                        textStyle,
                        STYLED_TEXT_OPS._or(LINKED_IMAGE_OPS,
                            (s1, s2) -> Optional.empty()), // segment
                    // operations
                    seg -> createNode(seg, // Node creator and segment
                        (text, style) -> text.setStyle(style.toCss()))
                );
        return area;
    }
}
