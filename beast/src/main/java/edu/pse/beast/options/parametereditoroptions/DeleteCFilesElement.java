package edu.pse.beast.options.parametereditoroptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

/**
 * The Class DeleteCFilesElement.
 */
public class DeleteCFilesElement extends OptionElement {

    /**
     * Instantiates a new delete C files element.
     *
     * @param choosableOptions the choosable options
     * @param chosenDeleteCID the chosen delete CID
     */
    public DeleteCFilesElement(final List<String> choosableOptions,
                               final String chosenDeleteCID) {
        super("delete_file", choosableOptions);
        if (chosenDeleteCID == null) {
            handleSelection(choosableOptions.get(0));
        } else {
            handleSelection(chosenDeleteCID);
        }
    }

    @Override
    public void handleSelection(final String selection) {
        this.setChosenOption(selection);
    }
}
