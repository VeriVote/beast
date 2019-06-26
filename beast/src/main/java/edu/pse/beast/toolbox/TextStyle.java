package edu.pse.beast.toolbox;

//taken with changes from https://github.com/FXMisc/RichTextFX/blob/5d64bd7ef211292ec096b5b152aa79ee934e4678/richtextfx-demos/src/main/java/org/fxmisc/richtext/demo/hyperlink/TextStyle.java

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.fxmisc.richtext.model.Codec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Holds information about the style of a text fragment.
 */
public class TextStyle {

	public static final TextStyle DEFAULT = new TextStyle();

	public static final Codec<TextStyle> CODEC = new Codec<TextStyle>() {

		private final Codec<Optional<String>> OPT_STRING_CODEC = Codec.optionalCodec(Codec.STRING_CODEC);
		private final Codec<Optional<Color>> OPT_COLOR_CODEC = Codec.optionalCodec(Codec.COLOR_CODEC);

		@Override
		public String getName() {
			return "text-style";
		}

		@Override
		public void encode(DataOutputStream os, TextStyle s) throws IOException {
			os.writeByte(encodeBoldItalicUnderlineStrikethrough(s));
			os.writeInt(encodeOptionalUint(Optional.of((int) s.font.getSize())));
			OPT_STRING_CODEC.encode(os, Optional.of(s.font.getFamily()));
			OPT_COLOR_CODEC.encode(os, Optional.of(s.textColor));
			OPT_COLOR_CODEC.encode(os, Optional.of(s.backgroundColor));
		}

		@Override
		public TextStyle decode(DataInputStream is) throws IOException {
			byte bius = is.readByte();
			Optional<Integer> fontSize = decodeOptionalUint(is.readInt());
			Optional<String> fontFamily = OPT_STRING_CODEC.decode(is);
			Optional<Color> textColor = OPT_COLOR_CODEC.decode(is);
			Optional<Color> bgrColor = OPT_COLOR_CODEC.decode(is);

			Font decodedFont = new Font(fontFamily.orElse(getDefaultFont().getFamily()),
					fontSize.orElse((int) getDefaultFont().getSize()));

			return new TextStyle(bold(bius), italic(bius), underline(bius), strikethrough(bius),
					Optional.of(decodedFont), Optional.of(textColor.orElse(getDefaultTextColor())),
					Optional.of(bgrColor.orElse(getDefaultBackgroundColor())));
		}

		private int encodeBoldItalicUnderlineStrikethrough(TextStyle s) {
			return encodeOptionalBoolean(s.bold) << 6 | encodeOptionalBoolean(s.italic) << 4
					| encodeOptionalBoolean(s.underline) << 2 | encodeOptionalBoolean(s.strikethrough);
		}

		private Optional<Boolean> bold(byte bius) throws IOException {
			return decodeOptionalBoolean((bius >> 6) & 3);
		}

		private Optional<Boolean> italic(byte bius) throws IOException {
			return decodeOptionalBoolean((bius >> 4) & 3);
		}

		private Optional<Boolean> underline(byte bius) throws IOException {
			return decodeOptionalBoolean((bius >> 2) & 3);
		}

		private Optional<Boolean> strikethrough(byte bius) throws IOException {
			return decodeOptionalBoolean((bius >> 0) & 3);
		}

		private int encodeOptionalBoolean(Optional<Boolean> ob) {
			return ob.map(b -> 2 + (b ? 1 : 0)).orElse(0);
		}

		private Optional<Boolean> decodeOptionalBoolean(int i) throws IOException {
			switch (i) {
			case 0:
				return Optional.empty();
			case 2:
				return Optional.of(false);
			case 3:
				return Optional.of(true);
			}
			throw new MalformedInputException(0);
		}

		private int encodeOptionalUint(Optional<Integer> oi) {
			return oi.orElse(-1);
		}

		private Optional<Integer> decodeOptionalUint(int i) {
			return (i < 0) ? Optional.empty() : Optional.of(i);
		}
	};

	private static Font getDefaultFont() {
		return javafx.scene.text.Font.getDefault();
	}

	private static Color getDefaultTextColor() {
		return Color.BLACK;
	}

	private static Color getDefaultBackgroundColor() {
		return Color.WHITE;
	}

	public static TextStyle bold(boolean bold) {
		return DEFAULT.updateBold(bold);
	}

	public static TextStyle italic(boolean italic) {
		return DEFAULT.updateItalic(italic);
	}

	public static TextStyle underline(boolean underline) {
		return DEFAULT.updateUnderline(underline);
	}

	public static TextStyle strikethrough(boolean strikethrough) {
		return DEFAULT.updateStrikethrough(strikethrough);
	}

	public static TextStyle fontSize(int fontSize) {
		return DEFAULT.updateFontSize(fontSize);
	}

	public static TextStyle fontFamily(String family) {
		return DEFAULT.updateFontFamily(family);
	}

	public static TextStyle textColor(Color color) {
		return DEFAULT.updateTextColor(color);
	}

	public static TextStyle backgroundColor(Color color) {
		return DEFAULT.updateBackgroundColor(color);
	}

	static String cssColor(Color color) {
		int red = (int) (color.getRed() * 255);
		int green = (int) (color.getGreen() * 255);
		int blue = (int) (color.getBlue() * 255);
		return "rgb(" + red + ", " + green + ", " + blue + ")";
	}

	final Optional<Boolean> bold;
	final Optional<Boolean> italic;
	final Optional<Boolean> underline;
	final Optional<Boolean> strikethrough;
	final Font font;
	final Color textColor;
	final Color backgroundColor;

	public TextStyle() {
		this(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty());
	}

	public TextStyle(Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underline,
			Optional<Boolean> strikethrough, Optional<Font> fontOption, Optional<Color> textColorOption,
			Optional<Color> backgroundColorOption) {

		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
		this.strikethrough = strikethrough;
		this.font = fontOption.orElse(getDefaultFont());
		this.textColor = textColorOption.orElse(getDefaultTextColor());
		this.backgroundColor = backgroundColorOption.orElse(getDefaultBackgroundColor());
	}

	@Override
	public int hashCode() {
		return Objects.hash(bold, italic, underline, strikethrough, font, textColor, backgroundColor);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof TextStyle) {
			TextStyle that = (TextStyle) other;
			return Objects.equals(this.bold, that.bold) && Objects.equals(this.italic, that.italic)
					&& Objects.equals(this.underline, that.underline)
					&& Objects.equals(this.strikethrough, that.strikethrough) && Objects.equals(this.font, that.font)
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
		strikethrough.ifPresent(s -> styles.add(s.toString()));

		Optional.of(font.getSize()).ifPresent(s -> styles.add(s.toString()));
		Optional.of(font.getFamily()).ifPresent(s -> styles.add(s.toString()));

		Optional.of(textColor).ifPresent(c -> styles.add(c.toString()));
		Optional.of(backgroundColor).ifPresent(b -> styles.add(b.toString()));

		return String.join(",", styles);
	}

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

		if (strikethrough.isPresent()) {
			if (strikethrough.get()) {
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

	public TextStyle updateWith(TextStyle mixin) {
		return new TextStyle(mixin.bold.isPresent() ? mixin.bold : bold,
				mixin.italic.isPresent() ? mixin.italic : italic,
				mixin.underline.isPresent() ? mixin.underline : underline,
				mixin.strikethrough.isPresent() ? mixin.strikethrough : strikethrough,
				Optional.of(mixin.font),
				Optional.of(mixin.textColor),
				Optional.of(mixin.backgroundColor));
	}

	public TextStyle updateBold(boolean bold) {
		return new TextStyle(Optional.of(bold), italic, underline, strikethrough, Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateItalic(boolean italic) {
		return new TextStyle(bold, Optional.of(italic), underline, strikethrough, Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateUnderline(boolean underline) {
		return new TextStyle(bold, italic, Optional.of(underline), strikethrough, Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateStrikethrough(boolean strikethrough) {
		return new TextStyle(bold, italic, underline, Optional.of(strikethrough), Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateFontSize(int fontSize) {
		return new TextStyle(bold, italic, underline, strikethrough, Optional.of(new Font(font.getName(), fontSize)), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateFontFamily(String fontFamily) {
		return new TextStyle(bold, italic, underline, strikethrough, Optional.of(new Font(fontFamily, font.getSize())), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateTextColor(Color textColor) {
		return new TextStyle(bold, italic, underline, strikethrough, Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}

	public TextStyle updateBackgroundColor(Color backgroundColor) {
		return new TextStyle(bold, italic, underline, strikethrough, Optional.of(font), Optional.of(textColor),
				Optional.of(backgroundColor));
	}
}
