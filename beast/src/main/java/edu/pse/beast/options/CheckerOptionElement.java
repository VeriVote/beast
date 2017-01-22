package edu.pse.beast.options;

import java.util.List;

public class CheckerOptionElement extends OptionElement {
    private final List<String> checkerIDs;
    
    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param checkerIDs the list of all available checker ids
     */
    public CheckerOptionElement(String id, List<String> choosableOptions, List<String> checkerIDs) {
        super(id, choosableOptions);
        this.checkerIDs = checkerIDs;
    }

    /**
     * 
     * @return the list of checker ids
     */
    public List<String> getCheckerIDs() {
        return checkerIDs;
    }

    @Override
    public void handleSelection(String selection) {
        // TODO Auto-generated method stub
        
    }

}
