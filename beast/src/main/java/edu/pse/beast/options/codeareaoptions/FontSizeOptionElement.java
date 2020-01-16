package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the fontsize option.
 */
public class FontSizeOptionElement extends OptionElement {

    /** The size. */
    private int size;

    /**
     * Constructor.
     *
     * @param availableOptions the choosable options
     * @param chosenSize       the chosen size
     */
    public FontSizeOptionElement(final List<String> availableOptions,
                                 final String chosenSize) {
        super("fontsize", availableOptions);
        handleSelection(chosenSize);
    }

    /**
     * Getter for the size.
     *
     * @return the current font size
     */
    public int getSize() {
        return size;
    }

    @Override
    public void handleSelection(final String selection) {
        this.setChosenOption(selection);
        this.size = Integer.valueOf(selection);
    }
}
