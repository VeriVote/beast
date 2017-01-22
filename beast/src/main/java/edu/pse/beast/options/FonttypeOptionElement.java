package edu.pse.beast.options;

import java.util.List;

public class FontTypeOptionElement extends OptionElement {

    private final String chosenType;

    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenType the chosen type
     * @param chosenType
     */
    public FontTypeOptionElement(String id, List<String> choosableOptions, String chosenType) {
        super(id, choosableOptions);
        this.chosenType = chosenType;
    }

    /**
     * 
     * @return the chosen type
     */
    public String getChosenType() {
        return chosenType;
    }

    @Override
    public void handleSelection(String selection) {
        // TODO Auto-generated method stub
    }
}
