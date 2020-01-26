package edu.pse.beast.codearea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

/**
 * This class will display line numbers for a related text component. The text
 * component must use the same line height for each line. TextLineNumber
 * supports wrapped lines and will highlight the line number of the current line
 * in the text component.
 *
 * <p>This class was designed to be used as a component added to the row header of
 * a JScrollPane.
 *
 * @author https://tips4java.wordpress.com/2009/05/23/text-component-line-number/
 */
public final class TextLineNumber extends JPanel
        implements CaretListener, DocumentListener, PropertyChangeListener {

    /** The Constant LEFT. */
    public static final float LEFT = 0.0F;

    /** The Constant CENTER. */
    public static final float CENTER = 0.5F;

    /** The Constant RIGHT. */
    public static final float RIGHT = 1.0F;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant RIGHT_BORDER_INSET. */
    private static final int RIGHT_BORDER_INSET = 2;

    /** The Constant MIN_DISPLAY_WIDTH. */
    private static final int MIN_DISPLAY_WIDTH = 3;

    /** The Constant DEFAULT_BORDER_GAP. */
    private static final int DEFAULT_BORDER_GAP = 5;

    /** The Constant OFFSET. */
    private static final int OFFSET = 1000000;

    /** The Constant HEIGHT. */
    private static final int HEIGHT = Integer.MAX_VALUE - OFFSET;

    /** The Constant OUTER. */
    private static final Border OUTER =
            new MatteBorder(0, 0, 0, RIGHT_BORDER_INSET, Color.GRAY);

    // Text component this TextTextLineNumber component is in sync with

    /** The component. */
    private JTextComponent component;

    // Properties that can be changed

    /** The update font. */
    private boolean updateFont;

    /** The border gap. */
    private int borderGap;

    /** The current line foreground. */
    private Color currentLineForeground;

    /** The current line style. */
    private int currentLineStyle;

    /** The digit alignment. */
    private float digitAlignment;

    /** The minimum display digits. */
    private int minimumDisplayDigits;

    // Keep history information to reduce the number of times the component
    // needs to be repainted

    /** The last digits. */
    private int lastDigits;

    /** The last height. */
    private int lastHeight;

    /** The last line. */
    private int lastLine;

    /** The fonts. */
    private Map<String, FontMetrics> fonts;

    /**
     * Create a line number component for a text component. This minimum display
     * width will be based on 3 digits.
     *
     * @param textComponent
     *            the related text component
     */
    public TextLineNumber(final JTextComponent textComponent) {
        this(textComponent, MIN_DISPLAY_WIDTH);
    }

    /**
     * Create a line number component for a text component.
     *
     * @param textComponent
     *            the related text component
     * @param minDisplayDigits
     *            the number of digits used to calculate the minimum width of
     *            the component
     */
    public TextLineNumber(final JTextComponent textComponent,
                          final int minDisplayDigits) {
        this.component = textComponent;

        setFont(textComponent.getFont());

        setBorderGap(DEFAULT_BORDER_GAP);
        // setCurrentLineForeground( Color.RED );
        setCurrentLineStyle(Font.BOLD);
        setDigitAlignment(RIGHT);
        setMinimumDisplayDigits(minDisplayDigits);

        textComponent.getDocument().addDocumentListener(this);
        textComponent.addCaretListener(this);
        textComponent.addPropertyChangeListener("font", this);
    }

    /**
     * Gets the update font property.
     *
     * @return the update font property
     */
    public boolean getUpdateFont() {
        return updateFont;
    }

    /**
     * Set the update font property. Indicates whether this Font should be
     * updated automatically when the Font of the related text component is
     * changed.
     *
     * @param updateFontVal
     *            when true update the Font and repaint the line numbers,
     *            otherwise just repaint the line numbers.
     */
    public void setUpdateFont(final boolean updateFontVal) {
        this.updateFont = updateFontVal;
    }

    /**
     * Gets the border gap.
     *
     * @return the border gap in pixels
     */
    public int getBorderGap() {
        return borderGap;
    }

    /**
     * The border gap is used in calculating the left and right insets of the
     * border. Default value is 5.
     *
     * @param borderGapVal
     *            the gap in pixels
     */
    public void setBorderGap(final int borderGapVal) {
        this.borderGap = borderGapVal;
        final Border inner = new EmptyBorder(0, borderGapVal, 0, borderGapVal);
        setBorder(new CompoundBorder(OUTER, inner));
        lastDigits = 0;
        setPreferredWidth();
    }

    /**
     * Gets the current line rendering color.
     *
     * @return the Color used to render the current line number
     */
    public Color getCurrentLineForeground() {
        return currentLineForeground == null
                ? getForeground() : currentLineForeground;
    }

    /**
     * The Color used to render the current line digits. Default is Coolor.RED.
     *
     * @param currLineForeground
     *            the Color used to render the current line
     */
    public void setCurrentLineForeground(final Color currLineForeground) {
        this.currentLineForeground = currLineForeground;
    }

    /**
     * The style used to render the current line digits. Default style is
     * Font.BOLD.
     *
     * @param style
     *            the style used to render the current line
     */
    public void setCurrentLineStyle(final int style) {
        this.currentLineStyle = style;
    }

    /**
     * Gets the default plain line rendering style.
     *
     * @return the Style used to render the default line number
     */
    public Font getDefaultLineStyle() {
        final Font f = getFont();
        return new Font(f.getFontName(), Font.PLAIN, f.getSize());
    }

    /**
     * Gets the current line rendering style.
     *
     * @return the Style used to render the current line number
     */
    public Font getCurrentLineStyle() {
        final Font f = getFont();
        return new Font(f.getFontName(), currentLineStyle, f.getSize());
    }

    /**
     * Gets the digit alignment.
     *
     * @return the alignment of the painted digits
     */
    public float getDigitAlignment() {
        return digitAlignment;
    }

    /**
     * Specify the horizontal alignment of the digits within the component.
     * Common values would be:
     * <ul>
     * <li>TextLineNumber.LEFT
     * <li>TextLineNumber.CENTER
     * <li>TextLineNumber.RIGHT (default)
     * </ul>
     *
     * @param digitAlignm
     *            the Color used to render the current line
     */
    public void setDigitAlignment(final float digitAlignm) {
        this.digitAlignment = digitAlignm > 1.0f
                ? 1.0f : digitAlignm < 0.0f
                        ? -1.0f : digitAlignm;
    }

    /**
     * Gets the minimum display digits.
     *
     * @return the minimum display digits
     */
    public int getMinimumDisplayDigits() {
        return minimumDisplayDigits;
    }

    /**
     * Specify the mimimum number of digits used to calculate the preferred
     * width of the component. Default is 3.
     *
     * @param minDisplayDigits
     *            the number digits used in the preferred width calculation
     */
    public void setMinimumDisplayDigits(final int minDisplayDigits) {
        this.minimumDisplayDigits = minDisplayDigits;
        setPreferredWidth();
    }

    /**
     * Calculate the width needed to display the maximum line number.
     */
    private void setPreferredWidth() {
        final Element root = component.getDocument().getDefaultRootElement();
        final int lines = root.getElementCount();
        final int digits = Math.max(String.valueOf(lines).length(),
                                    minimumDisplayDigits);
        // Update sizes when number of digits in the line number changes.
        if (lastDigits != digits) {
            lastDigits = digits;
            final FontMetrics fontMetrics = getFontMetrics(getFont());
            final int width = fontMetrics.charWidth('0') * digits;
            final Insets insets = getInsets();
            final int preferredWidth = insets.left + insets.right + width;

            final Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize(d);
            setSize(d);
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // Determine the width of the space available to draw the line number
        final FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        final Insets insets = getInsets();
        final int availableWidth = getSize().width - insets.left - insets.right;

        // Determine the rows to draw within the clipped bounds.
        final Rectangle2D clip = g.getClipBounds();
        int rowStartOffset =
                component.viewToModel2D(new Point2D.Double(0, clip.getY()));
        final int endOffset =
                component.viewToModel2D(
                        new Point2D.Double(
                                0,
                                clip.getY()
                                + clip.getHeight()
                        )
                );
        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset)) {
                    g.setColor(getCurrentLineForeground());
                    g.setFont(getCurrentLineStyle());
                } else {
                    g.setColor(getForeground());
                    g.setFont(getDefaultLineStyle());
                }
                // Get the line number as a string and then determine the
                // "X" and "Y" offsets for drawing the string.
                final String lineNumber = getTextLineNumber(rowStartOffset);
                final int stringWidth = fontMetrics.stringWidth(lineNumber);
                final int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                final int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                // Move to the next row

                rowStartOffset =
                        Utilities.getRowEnd(component, rowStartOffset) + 1;
            } catch (BadLocationException e) {
                break;
            }
        }
    }

    /**
     * Checks if is current line. We need to know if the caret is currently
     * positioned on the line we are about to paint so the line number can be
     * highlighted.
     *
     * @param rowStartOffset
     *            the row start offset
     * @return true, if is current line
     */
    private boolean isCurrentLine(final int rowStartOffset) {
        final int caretPosition = component.getCaretPosition();
        final Element root = component.getDocument().getDefaultRootElement();
        return root.getElementIndex(rowStartOffset)
                == root.getElementIndex(caretPosition);
    }

    /**
     * Get the line number to be drawn. The empty string will be returned when a
     * line of text has wrapped.
     *
     * @param rowStartOffset
     *            the row start offset
     * @return the text line number
     */
    protected String getTextLineNumber(final int rowStartOffset) {
        final Element root = component.getDocument().getDefaultRootElement();
        final int index = root.getElementIndex(rowStartOffset);
        final Element line = root.getElement(index);
        if (line.getStartOffset() == rowStartOffset) {
            return String.valueOf(index + 1);
        } else {
            return "";
        }
    }

    /**
     * Determine the X offset to properly align the line number when drawn.
     *
     * @param availableWidth
     *            the available width
     * @param stringWidth
     *            the string width
     * @return the offset X
     */
    private int getOffsetX(final int availableWidth, final int stringWidth) {
        return (int) ((availableWidth - stringWidth) * digitAlignment);
    }

    /**
     * Determine the Y offset for the current row.
     *
     * @param rowStartOffset
     *            the row start offset
     * @param fontMetrics
     *            the font metrics
     * @return the offset Y
     * @throws BadLocationException
     *             the bad location exception
     */
    private int getOffsetY(final int rowStartOffset,
                           final FontMetrics fontMetrics) throws BadLocationException {
        // Get the bounding rectangle of the row.
        final Rectangle2D r = component.modelToView2D(rowStartOffset);
        final double lineHeight = fontMetrics.getHeight();
        final Double y = r.getY() + r.getHeight();
        int descent = 0;

        // The text needs to be positioned above the bottom of the bounding
        // rectangle based on the descent of the font(s) contained on the row.
        if (Double.compare(r.getHeight(), lineHeight) == 0) {
            // default font is being used
            descent = fontMetrics.getDescent();
        } else { // We need to check all the attributes for font changes.
            if (fonts == null) {
                fonts = new HashMap<String, FontMetrics>();
            }
            final Element root = component.getDocument().getDefaultRootElement();
            final int index = root.getElementIndex(rowStartOffset);
            final Element line = root.getElement(index);
            for (int i = 0; i < line.getElementCount(); i++) {
                final Element child = line.getElement(i);
                final AttributeSet as = child.getAttributes();
                final String fontFamily =
                        (String) as.getAttribute(StyleConstants.FontFamily);
                final Integer fontSize =
                        (Integer) as.getAttribute(StyleConstants.FontSize);
                final String key = fontFamily + fontSize;
                FontMetrics fm = fonts.get(key);
                if (fm == null) {
                    final Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }
                descent = Math.max(descent, fm.getDescent());
            }
        }
        return y.intValue() - descent;
    }

    //
    // Implement CaretListener interface
    //

    @Override
    public void caretUpdate(final CaretEvent e) {
        // Get the line the caret is positioned on
        final int caretPosition = component.getCaretPosition();
        final Element root = component.getDocument().getDefaultRootElement();
        final int currentLine = root.getElementIndex(caretPosition);

        // Need to repaint so the correct line number can be highlighted
        if (lastLine != currentLine) {
            repaint();
            lastLine = currentLine;
        }
    }

    //
    // Implement DocumentListener interface
    //

    @Override
    public void changedUpdate(final DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        documentChanged();
    }

    /**
     * A document change may affect the number of displayed lines of text.
     * Therefore the lines numbers will also change.
     */
    private void documentChanged() {
        // View of the component has not been updated at the time
        // the DocumentEvent is fired
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final int endPos = component.getDocument().getLength();
                    final Rectangle2D rect = component.modelToView2D(endPos);
                    final Double y = rect != null ? rect.getCenterY() : null;
                    if (rect != null && y.intValue() != lastHeight) {
                        setPreferredWidth();
                        repaint();
                        lastHeight = y.intValue();
                    }
                } catch (BadLocationException ex) {
                    /* nothing to do */
                }
            }
        });
    }

    //
    // Implement PropertyChangeListener interface
    //

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            if (updateFont) {
                final Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                lastDigits = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
        }
    }
}
