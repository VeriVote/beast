package edu.pse.beast.toolbox;

import org.fxmisc.richtext.model.NodeSegmentOpsBase;

/**
 * The Class LinkedImageOps.
 *
 * Taken from the Demos from https://github.com/FXMisc/RichTextFX.
 *
 * @author Lukas Stapelbroek
 *
 * @param <S>
 *            the generic type
 */
public class LinkedImageOps<S> extends NodeSegmentOpsBase<LinkedImage, S> {

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
