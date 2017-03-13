package edu.pse.beast.options.ParametereditorOptions;

import java.util.List;

import edu.pse.beast.options.OptionElement;

public class DeleteCFilesElement extends OptionElement {

	public DeleteCFilesElement(String id, List<String> choosableOptions) {
		super(id, choosableOptions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleSelection(String selection) {
	    this.chosenOption = selection;
	}

}
