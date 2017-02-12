package edu.pse.beast.options;

import java.util.List;

public class FontTypeOptionElement extends OptionElement {
    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenType the chosen type
     * @param chosenType
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
