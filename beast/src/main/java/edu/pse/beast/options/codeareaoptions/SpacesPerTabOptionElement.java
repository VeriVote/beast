package edu.pse.beast.options.codeareaoptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the spacespertabs option.
 */
public class SpacesPerTabOptionElement extends OptionElement {

    /** The number tabs. */
    private int numberTabs;

    /**
     * Constructor.
     *
     * @param availableOptions    the available options
     * @param numberTabsStr       the number of spaces per tab
     */
    public SpacesPerTabOptionElement(final List<String> availableOptions,
                                     final String numberTabsStr) {
        super("spaces_per_tab", availableOptions);
        handleSelection(numberTabsStr);
    }

    /**
     * Getter for the number of spaces per tab.
     *
     * @return the chosen number of tabs
     */
    public int getNumberTabs() {
        return numberTabs;
    }

    @Override
    public void handleSelection(final String selection) {
        setChosenOption(selection);
        numberTabs = Integer.valueOf(selection);
    }
}
