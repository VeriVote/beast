package edu.pse.beast.toolbox;

import java.util.Optional;
import java.util.function.BiConsumer;

import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import javafx.scene.Node;

public class TextFieldCreator {
    private static final TextOps<String, TextStyle> styledTextOps =
            SegmentOps.styledTextOps();
    private static final LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();
    private static Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
                                   BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg
                .getSegment().unify(
                        text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(),
                                                                    applyStyle),
                        LinkedImage::createNode);
    }

    public static GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>
                    getGenericStyledAreaInstance(TextStyle textStyle, ParStyle paragraphStyle) {
        GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area =
                new GenericStyledArea<>(paragraphStyle, // default paragraph style
                (paragraph, style) -> paragraph.setStyle(style.toCss()), // paragraph style setter
                textStyle, // default segment style
                styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()), // segment
                                                                                 // operations
                seg -> createNode(seg, // Node creator and segment
                        (text, style) -> text.setStyle(style.toCss())));
        return area;
    }
}
