package edu.pse.beast.options.ParametereditorOptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * OptionElement subclass for the language option.
 */
public class LanguageOptionElement extends OptionElement {

    /**   
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
