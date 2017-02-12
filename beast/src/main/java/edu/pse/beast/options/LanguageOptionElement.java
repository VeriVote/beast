package edu.pse.beast.options;

import java.util.List;

public class LanguageOptionElement extends OptionElement {

    /**   
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenLangID the chosen language ID
     */
    public LanguageOptionElement(List<String> choosableOptions, String chosenLangID) {
        super("lang", choosableOptions);        
        this.chosenOption = chosenLangID;
    }

    @Override
    public void handleSelection(String selection) {
        this.chosenOption = selection;
    }

}
