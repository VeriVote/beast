package edu.pse.beast.options;

import java.util.List;


/**
*
* @author Lukas
*/
public abstract class OptionElement {
	private String id;
	private List<String> choosableOptions;

	public OptionElement(String id, List<String> choosableOptions) {
		this.id = id;
		this.choosableOptions = choosableOptions;
	}
	
	public String getID() {
		return id;
	}
	
	public List<String> getChoosableOptions() {
		return choosableOptions;
	}
	
	public abstract void handleSelection(String selection);
}
