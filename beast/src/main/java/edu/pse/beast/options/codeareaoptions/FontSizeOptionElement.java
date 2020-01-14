package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the fontsize option.
 */
public class FontSizeOptionElement extends OptionElement {
    private int size;

    /**
     * Constructor
     *
     * @param choosableOptions the choosable options
     * @param chosenSize       the chosen size
     */
    public FontSizeOptionElement(List<String> choosableOptions, String chosenSize) {
        super("fontsize", choosableOptions);
        handleSelection(chosenSize);
    }

    /**
     * Getter
     *
     * @return the current font size
     */
    public int getsize() {
        return size;
    }

    @Override
    public void handleSelection(String selection) {
        this.chosenOption = selection;
        this.size = Integer.valueOf(selection);
    }
}
