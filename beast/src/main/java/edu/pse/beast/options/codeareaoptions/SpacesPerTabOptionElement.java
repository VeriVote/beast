package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the spacespertabs option.
 */
public class SpacesPerTabOptionElement extends OptionElement {
    private int numberTabs;

    /**
     * Constructor
     *
     * @param choosableOptions the choosable options
     * @param numberTabs       the number of spaces per tab
     */
    public SpacesPerTabOptionElement(List<String> choosableOptions, String numberTabs) {
        super("spaces_per_tab", choosableOptions);
        handleSelection(numberTabs);
    }

    /**
     * Getter
     *
     * @return the chosen number of tabs
     */
    public int getNumberTabs() {
        return numberTabs;
    }

    @Override
    public void handleSelection(String selection) {
        chosenOption = selection;
        numberTabs = Integer.valueOf(selection);
    }
}
