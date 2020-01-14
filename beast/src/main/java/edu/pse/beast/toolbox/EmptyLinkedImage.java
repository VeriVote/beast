package edu.pse.beast.toolbox;

//Taken from the Demos from https://github.com/FXMisc/RichTextFX

import javafx.scene.Node;

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
