package edu.pse.beast.options;

import java.util.List;

/**
 * A parent class representing an Option.
 * @author Lukas
 */
public abstract class OptionElement {
    
    /**
     * the currently chosen option
     */
    protected String chosenOption;
    private final String id;
    private final List<String> choosableOptions;
    /**
     * Constructor
     * @param id The ID of this OptionElement
     * @param choosableOptions A List of Strings with the choosableOptions
     */
    public OptionElement(String id, List<String> choosableOptions) {
        this.id = id;
        this.choosableOptions = choosableOptions;
    }

    /**
     * Getter for the ID
     * @return the ID of this OptionElement
     */
    public String getID() {
        return id;
    }

    /**
     * Getter for ChoosableOptions
     * @return the List of the ChoosableOptions
     */
    public List<String> getChoosableOptions() {
        return choosableOptions;
    }

    /**
     * Handles the selection
     * @param selection identifies the selection
     */
    public abstract void handleSelection(String selection);

    /**
     * Getter for the currently chosen option
     * @return the chosen option
     */
    public String getChosenOption() {
        return chosenOption;
    }
}
