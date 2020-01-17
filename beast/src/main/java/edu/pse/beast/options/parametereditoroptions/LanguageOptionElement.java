package edu.pse.beast.options.parametereditoroptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the language option.
 *
 * @author Lukas Stapelbroek
 */
public class LanguageOptionElement extends OptionElement {

    /**
     * Instantiates a new language option element.
     *
     * @param availableOptions
     *            the choosable options
     * @param chosenLangID
     *            the chosen language ID
     */
    public LanguageOptionElement(final List<String> availableOptions,
                                 final String chosenLangID) {
        super("lang", availableOptions);
        this.setChosenOption(chosenLangID);
    }

    @Override
    public void handleSelection(final String selection) {
        this.setChosenOption(selection);
    }
}
