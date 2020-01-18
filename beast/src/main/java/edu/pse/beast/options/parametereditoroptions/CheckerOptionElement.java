package edu.pse.beast.options.parametereditoroptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the checker option.
 *
 * @author Lukas Stapelbroek
 */
public final class CheckerOptionElement extends OptionElement {
    /**
     * Constructor.
     *
     * @param choosableOptions
     *            the available options
     * @param chosen
     *            the String for the chosen checker
     */
    public CheckerOptionElement(final List<String> choosableOptions,
                                final String chosen) {
        super("checker", choosableOptions);
        if (chosen == null) {
            handleSelection(choosableOptions.get(0));
        } else {
            handleSelection(chosen);
        }
    }

    @Override
    public void handleSelection(final String selection) {
        this.setChosenOption(selection);
    }
}
