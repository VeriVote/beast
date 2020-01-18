package edu.pse.beast.toolbox;

import org.fxmisc.richtext.model.NodeSegmentOpsBase;

/**
 * The Class LinkedImageOps.
 *
 * @author Taken from the Demos from https://github.com/FXMisc/RichTextFX.
 *
 * @param <S>
 *            the generic type
 */
public final class LinkedImageOps<S> extends NodeSegmentOpsBase<LinkedImage, S> {
    /**
     * Instantiates a new linked image ops.
     */
    public LinkedImageOps() {
        super(new EmptyLinkedImage());
    }

    @Override
    public int length(final LinkedImage linkedImage) {
        return linkedImage.isReal() ? 1 : 0;
    }

}
