package edu.pse.beast.options.parametereditoroptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the checker option.
 */
public class CheckerOptionElement extends OptionElement {
    /**
     * Constructor
     *
     * @param choosableOptions the choosable options
     * @param chosen           the String for the chosen checker
     */
    public CheckerOptionElement(List<String> choosableOptions, String chosen) {
        super("checker", choosableOptions);
        if (chosen == null) {
            handleSelection(choosableOptions.get(0));
        } else {
            handleSelection(chosen);
        }
    }

    @Override
    public void handleSelection(String selection) {
        this.chosenOption = selection;
    }
}