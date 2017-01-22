package edu.pse.beast.options;

import java.util.List;

public class LanguageOptionElement extends OptionElement {
    private final String chosenLangID;

    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenLangID the chosen language ID
     */
    public LanguageOptionElement(String id, List<String> choosableOptions, String chosenLangID) {
        super(id, choosableOptions);
        this.chosenLangID = chosenLangID;
    }

    @Override
    public void handleSelection(String selection) {
        // TODO Auto-generated method stub
        
    }

}
