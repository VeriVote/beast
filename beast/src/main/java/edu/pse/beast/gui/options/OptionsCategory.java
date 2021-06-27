package edu.pse.beast.gui.options;

import javafx.scene.layout.AnchorPane;

public abstract class OptionsCategory {
	private OptionsCategoryType category;
	
	public OptionsCategory(OptionsCategoryType category) {
		this.category = category;
	}
	
	public OptionsCategoryType getCategory() {
		return category;
	}
	
	protected void displayOptionsAnchorpane(AnchorPane optionsPane, AnchorPane topLevelPane) {
		optionsPane.getChildren().add(topLevelPane);
		AnchorPane.setTopAnchor(topLevelPane, 0.0d);
		AnchorPane.setLeftAnchor(topLevelPane, 0.0d);
		AnchorPane.setRightAnchor(topLevelPane, 0.0d);
		AnchorPane.setBottomAnchor(topLevelPane, 0.0d);
	}

	public abstract void displayOptions(
			AnchorPane currentOptionDisplayAnchorpane);
}
