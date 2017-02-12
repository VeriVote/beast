package edu.pse.beast.options.CodeAreaOptions;

import edu.pse.beast.options.OptionElement;
import java.util.List;

public class SpacesPerTabOptionElement extends OptionElement {

    private int numberTabs;

    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param numberTabs the number of spaces per tab
     * @param chosenType
     */
    public SpacesPerTabOptionElement(List<String> choosableOptions, String numberTabs) {
        super("spaces_per_tab", choosableOptions);
        handleSelection(numberTabs);
    }

    /**
     * 
     * @return the chosen type
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
