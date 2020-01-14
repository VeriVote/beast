package edu.pse.beast.toolbox;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

/**
 * A custom object which contains a file path to an image file. When rendered in
 * the rich text editor, the image is loaded from the specified file.
 */
public class RealLinkedImage implements LinkedImage {
    private Canvas canvas;

    /**
     * Creates a new linked image object.
     *
     * @param imagePath
     *            The path to the image file.
     */
    public RealLinkedImage(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public boolean isReal() {
        return true;
    }

    @Override
    public String getImagePath() {
        return "No image path used";
    }

    @Override
    public String toString() {
        return String.format("RealLinkedImage[path=%s]", "No Image path used");
    }

    @Override
    public Node createNode() {
        return canvas;
    }
}
