package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the fonttype option.
 */
public class FontTypeOptionElement extends OptionElement {
    /**
     * Constructor
     * @param choosableOptions the choosable options
     * @param chosenType the chosen type
     */
    public FontTypeOptionElement(List<String> choosableOptions, String chosenType) {
        super("fonttype", choosableOptions);
        handleSelection(chosenType);
    }

    @Override
    public void handleSelection(String selection) {
        chosenOption = selection;
    }
}