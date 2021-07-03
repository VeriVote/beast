package edu.pse.beast.zzz.toolbox;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

/**
 * A custom object which contains a file path to an image file. When rendered in
 * the rich text editor, the image is loaded from the specified file.
 *
 * @author Lukas Stapelbroek
 */
public final class RealLinkedImage implements LinkedImage {
    /** The canvas. */
    private Canvas canvas;

    /**
     * Creates a new linked image object.
     *
     * @param canv
     *            The path to the image file.
     */
    public RealLinkedImage(final Canvas canv) {
        this.canvas = canv;
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
