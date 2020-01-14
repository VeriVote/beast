package edu.pse.beast.toolbox;

//Taken from the Demos from https://github.com/FXMisc/RichTextFX

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.fxmisc.richtext.model.Codec;

import javafx.scene.Node;

public interface LinkedImage {
    static <S> Codec<LinkedImage> codec() {
        System.out.println("Code called! Investigate in LinkedImage, as we don't use paths");

        return new Codec<LinkedImage>() {
            @Override
            public String getName() {
                return "LinkedImage";
            }
            @Override
            public void encode(DataOutputStream os, LinkedImage linkedImage) throws IOException {
                if (linkedImage.isReal()) {
                    os.writeBoolean(true);
                    String externalPath = linkedImage.getImagePath().replace("\\", "/");
                    Codec.STRING_CODEC.encode(os, externalPath);
                } else {
                    os.writeBoolean(false);
                }
            }
            @Override
            public LinkedImage decode(DataInputStream is) throws IOException {
                if (is.readBoolean()) {
                    String imagePath = Codec.STRING_CODEC.decode(is);
                    imagePath = imagePath.replace("\\", "/");
                    // maybe rewrite encoder to encode the image, and not the path
                    throw new AssertionError("Unreachable code");
                    // return new RealLinkedImage(imagePath);
                } else {
                    return new EmptyLinkedImage();
                }
            }
        };
    }

    boolean isReal();

    /**
     * @return The path of the image to render.
     */
    String getImagePath();

    Node createNode();
}
