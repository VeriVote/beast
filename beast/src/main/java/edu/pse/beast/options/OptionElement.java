package edu.pse.beast.options;

import java.util.List;

/**
 * A parent class representing an Option.
 *
 * @author Lukas Stapelbroek
 */
public abstract class OptionElement {

    /**
     * The currently chosen option.
     */
    private String chosenOption;

    /** The id. */
    private final String id;

    /** The available options. */
    private final List<String> availableOptions;

    /**
     * Constructor.
     *
     * @param idString            The ID of this OptionElement
     * @param availableOpts A List of Strings with the availableOptions
     */
    public OptionElement(final String idString,
                         final List<String> availableOpts) {
        this.id = idString;
        this.availableOptions = availableOpts;
    }

    /**
     * Getter for the ID.
     *
     * @return the ID of this OptionElement
     */
    public String getID() {
        return id;
    }

    /**
     * Getter for ChoosableOptions.
     *
     * @return the List of the ChoosableOptions
     */
    public List<String> getChoosableOptions() {
        return availableOptions;
    }

    /**
     * Handles the selection.
     *
     * @param selection identifies the selection
     */
    public abstract void handleSelection(String selection);

    /**
     * Getter for the currently chosen option.
     *
     * @return the chosen option
     */
    public String getChosenOption() {
        return chosenOption;
    }

    /**
     * Sets the chosen option.
     *
     * @param chosenOpt the new chosen option
     */
    public void setChosenOption(final String chosenOpt) {
        this.chosenOption = chosenOpt;
    }
}
