package edu.pse.beast.toolbox;

import javafx.scene.Node;

/**
 * The Class EmptyLinkedImage.
 *
 * @author Taken from the Demos from https://github.com/FXMisc/RichTextFX
 */
public final class EmptyLinkedImage implements LinkedImage {
    @Override
    public boolean isReal() {
        return false;
    }

    @Override
    public String getImagePath() {
        return "";
    }

    @Override
    public Node createNode() {
        throw new AssertionError("Unreachable code");
    }
}
