package edu.pse.beast.toolbox;

//Taken from the Demos from https://github.com/FXMisc/RichTextFX

import org.fxmisc.richtext.model.NodeSegmentOpsBase;

public class LinkedImageOps<S> extends NodeSegmentOpsBase<LinkedImage, S> {

    public LinkedImageOps() {
        super(new EmptyLinkedImage());
    }

    @Override
    public int length(LinkedImage linkedImage) {
        return linkedImage.isReal() ? 1 : 0;
    }

}
