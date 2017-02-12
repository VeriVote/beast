package edu.pse.beast.options.ParametereditorOptions;

import edu.pse.beast.options.OptionElement;
import java.util.List;

public class CheckerOptionElement extends OptionElement {

    
    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param checkerIDs the list of all available checker ids
     */
    public CheckerOptionElement(List<String> choosableOptions, String chosen) {
        super("checker", choosableOptions);
        if(chosen == null) handleSelection(choosableOptions.get(0));
        else handleSelection(chosen);
    }


    @Override
    public void handleSelection(String selection) {
        this.chosenOption = selection;
    }

}
