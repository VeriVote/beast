package edu.pse.beast.options.parametereditoroptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

public class DeleteCFilesElement extends OptionElement {

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
