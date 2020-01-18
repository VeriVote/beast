package edu.pse.beast.toolbox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.fxmisc.richtext.model.Codec;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Holds information about the style of a text fragment.
 *
 * <p>Taken with changes from https://github.com/FXMisc/RichTextFX/blob/
 * 5d64bd7ef211292ec096b5b152aa79ee934e4678/richtextfx-demos/src/main/java/org/
 * fxmisc/richtext/demo/hyperlink/TextStyle.java
 *
 * @author Lukas Stapelbroek
 */
public final class TextStyle {
    /** The Constant DEFAULT. */
    public static final TextStyle DEFAULT = new TextStyle();

    /** The Constant TWO. */
    private static final int TWO = 2;

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The Constant FOUR. */
    private static final int FOUR = 4;

    /** The Constant SIX. */
    private static final int SIX = 6;

    /** The Constant COLOR_MASK. */
    private static final int COLOR_MASK = 255;

    /** The Constant DEFAULT_FONT_SIZE. */
    private static final int DEFAULT_FONT_SIZE = 12;

    /** The Constant CODEC. */
    public static final Codec<TextStyle> CODEC =
            new Codec<TextStyle>() {

        private final Codec<Optional<String>> optStringCodec =
                Codec.optionalCodec(Codec.STRING_CODEC);
        private final Codec<Optional<Color>> optColorCodec =
                Codec.optionalCodec(Codec.COLOR_CODEC);

        @Override
        public String getName() {
            return "text-style";
        }

        @Override
        public void encode(final DataOutputStream os, final TextStyle s)
                throws IOException {
            os.writeByte(encodeBoldItalicUnderlineStrikeThrough(s));
            os.writeInt(
                    encodeOptionalUint(Optional.of((int) s.font.getSize()))
            );
            optStringCodec.encode(os, Optional.of(s.font.getFamily()));
            optColorCodec.encode(os, Optional.of(s.textColor));
            optColorCodec.encode(os, Optional.of(s.backgroundColor));
        }

        @Override
        public TextStyle decode(final DataInputStream is) throws IOException {
            byte bius = is.readByte();
            Optional<Integer> fontSize = decodeOptionalUint(is.readInt());
            Optional<String> fontFamily = optStringCodec.decode(is);
            Optional<Color> textColor = optColorCodec.decode(is);
            Optional<Color> bgrColor = optColorCodec.decode(is);

            Font decodedFont =
                    new Font(fontFamily.orElse(getDefaultFont().getFamily()),
                             fontSize.orElse((int) getDefaultFont().getSize()));

            return new TextStyle(bold(bius), italic(bius), underline(bius),
                                 strikeThrough(bius), Optional.of(decodedFont),
                                 Optional.of(
                                         textColor.orElse(getDefaultTextColor())
                                 ),
                                 Optional.of(
                                         bgrColor.orElse(getDefaultBackgroundColor())
                                 )
                    );
        }

        private int encodeBoldItalicUnderlineStrikeThrough(final TextStyle s) {
            return encodeOptionalBoolean(s.bold) << SIX
                    | encodeOptionalBoolean(s.italic) << FOUR
                    | encodeOptionalBoolean(s.underline) << TWO
                    | encodeOptionalBoolean(s.strikeThrough);
        }

        private Optional<Boolean> bold(final byte bius) throws IOException {
            return decodeOptionalBoolean((bius >> SIX) & THREE);
        }

        private Optional<Boolean> italic(final byte bius) throws IOException {
            return decodeOptionalBoolean((bius >> FOUR) & THREE);
        }

        private Optional<Boolean> underline(final byte bius)
                throws IOException {
            return decodeOptionalBoolean((bius >> TWO) & THREE);
        }

        private Optional<Boolean> strikeThrough(final byte bius)
                throws IOException {
            return decodeOptionalBoolean((bius >> 0) & THREE);
        }

        private int encodeOptionalBoolean(final Optional<Boolean> ob) {
            return ob.map(b -> TWO + (b ? 1 : 0)).orElse(0);
        }

        private Optional<Boolean> decodeOptionalBoolean(final int i)
                throws IOException {
            switch (i) {
            case 0:
                return Optional.empty();
            case TWO:
                return Optional.of(false);
            case THREE:
                return Optional.of(true);
            default:
                throw new MalformedInputException(0);
            }
        }

        private int encodeOptionalUint(final Optional<Integer> oi) {
            return oi.orElse(-1);
        }

        private Optional<Integer> decodeOptionalUint(final int i) {
            return (i < 0) ? Optional.empty() : Optional.of(i);
        }
    };

    /** The bold. */
    private final Optional<Boolean> bold;

    /** The italic. */
    private final Optional<Boolean> italic;

    /** The underline. */
    private final Optional<Boolean> underline;

    /** The strike through. */
    private final Optional<Boolean> strikeThrough;

    /** The font. */
    private final Font font;

    /** The text color. */
    private final Color textColor;

    /** The background color. */
    private final Color backgroundColor;

    /**
     * The constructor.
     */
    public TextStyle() {
        this(Optional.empty(), Optional.empty(), Optional.empty(),
             Optional.empty(), Optional.empty(), Optional.empty(),
             Optional.empty());
    }

    /**
     * Instantiates a new text style.
     *
     * @param bld
     *            the bld
     * @param ital
     *            the ital
     * @param underln
     *            the underln
     * @param strikeThroughAttr
     *            the strike through attr
     * @param fontOption
     *            the font option
     * @param textColorOption
     *            the text color option
     * @param backgroundColorOption
     *            the background color option
     */
    public TextStyle(final Optional<Boolean> bld,
                     final Optional<Boolean> ital,
                     final Optional<Boolean> underln,
                     final Optional<Boolean> strikeThroughAttr,
                     final Optional<Font> fontOption,
                     final Optional<Color> textColorOption,
                     final Optional<Color> backgroundColorOption) {
        this.bold = bld;
        this.italic = ital;
        this.underline = underln;
        this.strikeThrough = strikeThroughAttr;
        this.font = fontOption.orElse(getDefaultFont());
        this.textColor = textColorOption.orElse(getDefaultTextColor());
        this.backgroundColor =
                backgroundColorOption.orElse(getDefaultBackgroundColor());
    }

    /**
     * Gets the default font.
     *
     * @return the default font
     */
    private static Font getDefaultFont() {
        return new Font("Monospaced", DEFAULT_FONT_SIZE);
    }

    /**
     * Gets the default text color.
     *
     * @return the default text color
     */
    private static Color getDefaultTextColor() {
        return Color.BLACK;
    }

    /**
     * Gets the default background color.
     *
     * @return the default background color
     */
    private static Color getDefaultBackgroundColor() {
        return Color.WHITE;
    }

    /**
     * Bold.
     *
     * @param bold
     *            the bold
     * @return the text style
     */
    public static TextStyle bold(final boolean bold) {
        return DEFAULT.updateBold(bold);
    }

    /**
     * Italic.
     *
     * @param italic
     *            the italic
     * @return the text style
     */
    public static TextStyle italic(final boolean italic) {
        return DEFAULT.updateItalic(italic);
    }

    /**
     * Underline.
     *
     * @param underline
     *            the underline
     * @return the text style
     */
    public static TextStyle underline(final boolean underline) {
        return DEFAULT.updateUnderline(underline);
    }

    /**
     * Strike through.
     *
     * @param strikethrough
     *            the strikethrough
     * @return the text style
     */
    public static TextStyle strikeThrough(final boolean strikethrough) {
        return DEFAULT.updateStrikeThrough(strikethrough);
    }

    /**
     * Font size.
     *
     * @param fontSize
     *            the font size
     * @return the text style
     */
    public static TextStyle fontSize(final int fontSize) {
        return DEFAULT.updateFontSize(fontSize);
    }

    /**
     * Font family.
     *
     * @param family
     *            the family
     * @return the text style
     */
    public static TextStyle fontFamily(final String family) {
        return DEFAULT.updateFontFamily(family);
    }

    /**
     * Text color.
     *
     * @param color
     *            the color
     * @return the text style
     */
    public static TextStyle textColor(final Color color) {
        return DEFAULT.updateTextColor(color);
    }

    /**
     * Background color.
     *
     * @param color
     *            the color
     * @return the text style
     */
    public static TextStyle backgroundColor(final Color color) {
        return DEFAULT.updateBackgroundColor(color);
    }

    /**
     * Css color.
     *
     * @param color
     *            the color
     * @return the string
     */
    static String cssColor(final Color color) {
        int red = (int) (color.getRed() * COLOR_MASK);
        int green = (int) (color.getGreen() * COLOR_MASK);
        int blue = (int) (color.getBlue() * COLOR_MASK);
        return "rgb(" + red + ", " + green + ", " + blue + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(bold, italic, underline, strikeThrough, font,
                            textColor, backgroundColor);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TextStyle) {
            TextStyle that = (TextStyle) other;
            return Objects.equals(this.bold, that.bold)
                    && Objects.equals(this.italic, that.italic)
                    && Objects.equals(this.underline, that.underline)
                    && Objects.equals(this.strikeThrough, that.strikeThrough)
                    && Objects.equals(this.font, that.font)
                    && Objects.equals(this.textColor, that.textColor)
                    && Objects.equals(this.backgroundColor, that.backgroundColor);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        List<String> styles = new ArrayList<>();
        bold.ifPresent(b -> styles.add(b.toString()));
        italic.ifPresent(i -> styles.add(i.toString()));
        underline.ifPresent(u -> styles.add(u.toString()));
        strikeThrough.ifPresent(s -> styles.add(s.toString()));
        Optional.of(font.getSize()).ifPresent(s -> styles.add(s.toString()));
        Optional.of(font.getFamily()).ifPresent(s -> styles.add(s.toString()));
        Optional.of(textColor).ifPresent(c -> styles.add(c.toString()));
        Optional.of(backgroundColor).ifPresent(b -> styles.add(b.toString()));
        return String.join(",", styles);
    }

    /**
     * To css.
     *
     * @return the string
     */
    public String toCss() {
        StringBuilder sb = new StringBuilder();
        if (bold.isPresent()) {
            if (bold.get()) {
                sb.append("-fx-font-weight: bold;");
            } else {
                sb.append("-fx-font-weight: normal;");
            }
        }
        if (italic.isPresent()) {
            if (italic.get()) {
                sb.append("-fx-font-style: italic;");
            } else {
                sb.append("-fx-font-style: normal;");
            }
        }
        if (underline.isPresent()) {
            if (underline.get()) {
                sb.append("-fx-underline: true;");
            } else {
                sb.append("-fx-underline: false;");
            }
        }
        if (strikeThrough.isPresent()) {
            if (strikeThrough.get()) {
                sb.append("-fx-strikethrough: true;");
            } else {
                sb.append("-fx-strikethrough: false;");
            }
        }

        sb.append("-fx-font-size: " + font.getSize() + "pt;");
        sb.append("-fx-font-family: " + font.getFamily() + ";");
        sb.append("-fx-fill: " + cssColor(textColor) + ";");
        sb.append("-rtfx-background-color: " + cssColor(backgroundColor) + ";");
        return sb.toString();
    }

    /**
     * Update with.
     *
     * @param mixin
     *            the mixin
     * @return the text style
     */
    public TextStyle updateWith(final TextStyle mixin) {
        return new TextStyle(
                mixin.bold.isPresent()
                    ? mixin.bold : bold,
                mixin.italic.isPresent()
                    ? mixin.italic : italic,
                mixin.underline.isPresent()
                    ? mixin.underline : underline,
                mixin.strikeThrough.isPresent()
                    ? mixin.strikeThrough : strikeThrough,
                Optional.of(mixin.font),
                Optional.of(mixin.textColor),
                Optional.of(mixin.backgroundColor)
        );
    }

    /**
     * Update bold.
     *
     * @param bld
     *            the bld
     * @return the text style
     */
    public TextStyle updateBold(final boolean bld) {
        return new TextStyle(Optional.of(bld), italic, underline, strikeThrough,
                             Optional.of(font), Optional.of(textColor),
                             Optional.of(backgroundColor));
    }

    /**
     * Update italic.
     *
     * @param ital
     *            the ital
     * @return the text style
     */
    public TextStyle updateItalic(final boolean ital) {
        return new TextStyle(bold, Optional.of(ital), underline, strikeThrough,
                             Optional.of(font), Optional.of(textColor),
                             Optional.of(backgroundColor));
    }

    /**
     * Update underline.
     *
     * @param underln
     *            the underln
     * @return the text style
     */
    public TextStyle updateUnderline(final boolean underln) {
        return new TextStyle(bold, italic, Optional.of(underln), strikeThrough,
                             Optional.of(font), Optional.of(textColor),
                             Optional.of(backgroundColor));
    }

    /**
     * Update strike through.
     *
     * @param strikeThroughAttribute
     *            the strike through attribute
     * @return the text style
     */
    public TextStyle updateStrikeThrough(final boolean strikeThroughAttribute) {
        return new TextStyle(bold, italic, underline,
                             Optional.of(strikeThroughAttribute), Optional.of(font),
                             Optional.of(textColor), Optional.of(backgroundColor));
    }

    /**
     * Update font size.
     *
     * @param fontSize
     *            the font size
     * @return the text style
     */
    public TextStyle updateFontSize(final int fontSize) {
        return new TextStyle(bold, italic, underline, strikeThrough,
                             Optional.of(new Font(font.getName(), fontSize)),
                             Optional.of(textColor), Optional.of(backgroundColor));
    }

    /**
     * Update font family.
     *
     * @param fontFamily
     *            the font family
     * @return the text style
     */
    public TextStyle updateFontFamily(final String fontFamily) {
        return new TextStyle(bold, italic, underline, strikeThrough,
                             Optional.of(new Font(fontFamily, font.getSize())),
                             Optional.of(textColor), Optional.of(backgroundColor));
    }

    /**
     * Update text color.
     *
     * @param textClr
     *            the text clr
     * @return the text style
     */
    public TextStyle updateTextColor(final Color textClr) {
        return new TextStyle(bold, italic, underline, strikeThrough,
                             Optional.of(font), Optional.of(textClr),
                             Optional.of(backgroundColor));
    }

    /**
     * Update background color.
     *
     * @param backColor
     *            the back color
     * @return the text style
     */
    public TextStyle updateBackgroundColor(final Color backColor) {
        return new TextStyle(bold, italic, underline, strikeThrough,
                             Optional.of(font), Optional.of(textColor),
                             Optional.of(backColor));
    }
}
