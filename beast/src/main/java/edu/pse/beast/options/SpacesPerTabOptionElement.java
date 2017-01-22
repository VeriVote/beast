package edu.pse.beast.options;

import java.util.List;

public class SpacesPerTabOptionElement extends OptionElement {

    private final int numberTabs;

    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param numberTabs the number of spaces per tab
     * @param chosenType
     */
    public SpacesPerTabOptionElement(String id, List<String> choosableOptions, int numberTabs) {
        super(id, choosableOptions);
        this.numberTabs = numberTabs;
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
        // TODO Auto-generated method stub
    }
}
