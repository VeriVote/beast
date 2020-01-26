package edu.pse.beast.toolbox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.Node;

import org.fxmisc.richtext.model.Codec;

/**
 * The Interface LinkedImage.
 *
 * @author Taken from the Demos from https://github.com/FXMisc/RichTextFX
 */
public interface LinkedImage {
    /** The Constant REPLACE_TO. */
    String REPLACE_TO = "/";
    /** The Constant REPLACE_TARGET. */
    String REPLACE_TARGET = "\\";

    /**
     * Codec.
     *
     * @param <S>
     *            the generic type
     * @return the codec
     */
    static <S> Codec<LinkedImage> codec() {
        System.out.println("Code called! Investigate in LinkedImage,"
                            + " as we do not use paths.");

        return new Codec<LinkedImage>() {
            @Override
            public String getName() {
                return "LinkedImage";
            }

            @Override
            public void encode(final DataOutputStream os,
                               final LinkedImage linkedImage) throws IOException {
                if (linkedImage.isReal()) {
                    os.writeBoolean(true);
                    final String externalPath =
                            linkedImage.getImagePath().replace(REPLACE_TARGET,
                                                               REPLACE_TO);
                    Codec.STRING_CODEC.encode(os, externalPath);
                } else {
                    os.writeBoolean(false);
                }
            }

            @Override
            public LinkedImage decode(final DataInputStream is)
                    throws IOException {
                if (is.readBoolean()) {
                    String imagePath = Codec.STRING_CODEC.decode(is);
                    imagePath = imagePath.replace(REPLACE_TARGET, REPLACE_TO);
                    // Maybe rewrite encoder to encode the image, and not the
                    // path.
                    throw new AssertionError("Unreachable code");
                    // return new RealLinkedImage(imagePath);
                } else {
                    return new EmptyLinkedImage();
                }
            }
        };
    }

    /**
     * Checks if is real.
     *
     * @return true, if is real
     */
    boolean isReal();

    /**
     * Gets the image path.
     *
     * @return The path of the image to render.
     */
    String getImagePath();

    /**
     * Creates the node.
     *
     * @return the node
     */
    Node createNode();
}
