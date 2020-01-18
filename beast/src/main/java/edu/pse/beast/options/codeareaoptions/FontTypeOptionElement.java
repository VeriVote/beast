package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the fonttype option.
 *
 * @author Lukas Stapelbroek
 */
public final class FontTypeOptionElement extends OptionElement {
    /**
     * Constructor.
     *
     * @param availableOptions
     *            the choosable options
     * @param chosenType
     *            the chosen type
     */
    public FontTypeOptionElement(final List<String> availableOptions,
                                 final String chosenType) {
        super("fonttype", availableOptions);
        handleSelection(chosenType);
    }

    @Override
    public void handleSelection(final String selection) {
        setChosenOption(selection);
    }
}
