package edu.pse.beast.toolbox;

import javafx.scene.Node;

/**
 * The Class EmptyLinkedImage.
 *
 * Taken from the Demos from https://github.com/FXMisc/RichTextFX
 *
 * @author Lukas Stapelbroek
 */
public class EmptyLinkedImage implements LinkedImage {

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
