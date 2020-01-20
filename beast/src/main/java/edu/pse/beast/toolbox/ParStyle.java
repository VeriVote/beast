package edu.pse.beast.toolbox;

import static javafx.scene.text.TextAlignment.CENTER;
import static javafx.scene.text.TextAlignment.JUSTIFY;
import static javafx.scene.text.TextAlignment.LEFT;
import static javafx.scene.text.TextAlignment.RIGHT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import org.fxmisc.richtext.model.Codec;

//TODO maybe make the same changes that were done to TextStyle (default values)
/**
 * Holds information about the style of a paragraph.
 *
 * @author Taken from a demo from https://github.com/FXMisc/RichTextFX.
 */
public final class ParStyle {
    /** The Constant EMPTY. */
    public static final ParStyle EMPTY = new ParStyle();

    /** The Constant CODEC. */
    public static final Codec<ParStyle> CODEC =
            new Codec<ParStyle>() {
        private final Codec<Optional<TextAlignment>> optAlignmentCodec =
                Codec.optionalCodec(Codec.enumCodec(TextAlignment.class));
        private final Codec<Optional<Color>> optColorCodec =
                Codec.optionalCodec(Codec.COLOR_CODEC);

        @Override
        public String getName() {
            return "par-style";
        }

        @Override
        public void encode(final DataOutputStream os, final ParStyle t)
                throws IOException {
            optAlignmentCodec.encode(os, t.alignment);
            optColorCodec.encode(os, t.backgroundColor);
        }

        @Override
        public ParStyle decode(final DataInputStream is) throws IOException {
            return new ParStyle(optAlignmentCodec.decode(is),
                    optColorCodec.decode(is));
        }

    };

    /** The semicolon symbol. */
    private static final String SEMICOLON = ";";

    /** The alignment. */
    private final Optional<TextAlignment> alignment;

    /** The background color. */
    private final Optional<Color> backgroundColor;

    /**
     * The constructor.
     */
    public ParStyle() {
        this(Optional.empty(), Optional.empty());
    }

    /**
     * Instantiates a new par style.
     *
     * @param align
     *            the align
     * @param backColor
     *            the back color
     */
    public ParStyle(final Optional<TextAlignment> align,
                    final Optional<Color> backColor) {
        this.alignment = align;
        this.backgroundColor = backColor;
    }

    /**
     * Align left.
     *
     * @return the par style
     */
    public static ParStyle alignLeft() {
        return EMPTY.updateAlignment(LEFT);
    }

    /**
     * Align center.
     *
     * @return the par style
     */
    public static ParStyle alignCenter() {
        return EMPTY.updateAlignment(CENTER);
    }

    /**
     * Align right.
     *
     * @return the par style
     */
    public static ParStyle alignRight() {
        return EMPTY.updateAlignment(RIGHT);
    }

    /**
     * Align justify.
     *
     * @return the par style
     */
    public static ParStyle alignJustify() {
        return EMPTY.updateAlignment(JUSTIFY);
    }

    /**
     * Background color.
     *
     * @param color
     *            the color
     * @return the par style
     */
    public static ParStyle backgroundColor(final Color color) {
        return EMPTY.updateBackgroundColor(color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alignment, backgroundColor);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof ParStyle) {
            ParStyle that = (ParStyle) other;
            return Objects.equals(this.alignment, that.alignment) && Objects
                    .equals(this.backgroundColor, that.backgroundColor);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toCss();
    }

    /**
     * To css.
     *
     * @return the string
     */
    public String toCss() {
        StringBuilder sb = new StringBuilder();

        alignment.ifPresent(al -> {
            String cssAlignment;
            switch (al) {
            case LEFT:
                cssAlignment = "left";
                break;
            case CENTER:
                cssAlignment = "center";
                break;
            case RIGHT:
                cssAlignment = "right";
                break;
            case JUSTIFY:
                cssAlignment = "justify";
                break;
            default:
                throw new AssertionError("unreachable code");
            }
            sb.append("-fx-text-alignment: " + cssAlignment + SEMICOLON);
        });

        backgroundColor.ifPresent(color -> {
            sb.append("-fx-background-color: "
                    + TextStyle.cssColor(color) + SEMICOLON);
        });

        return sb.toString();
    }

    /**
     * Update with.
     *
     * @param mixin
     *            the mixin
     * @return the par style
     */
    public ParStyle updateWith(final ParStyle mixin) {
        return new ParStyle(
                mixin.alignment.isPresent()
                    ? mixin.alignment : alignment,
                mixin.backgroundColor.isPresent()
                    ? mixin.backgroundColor : backgroundColor
                );
    }

    /**
     * Update alignment.
     *
     * @param align
     *            the align
     * @return the par style
     */
    public ParStyle updateAlignment(final TextAlignment align) {
        return new ParStyle(Optional.of(align), backgroundColor);
    }

    /**
     * Update background color.
     *
     * @param backColor
     *            the back color
     * @return the par style
     */
    public ParStyle updateBackgroundColor(final Color backColor) {
        return new ParStyle(alignment, Optional.of(backColor));
    }
}
