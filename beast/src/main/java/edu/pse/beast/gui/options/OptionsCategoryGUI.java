package edu.pse.beast.gui.options;

import javafx.scene.layout.AnchorPane;

public abstract class OptionsCategoryGUI {
    private OptionsGUIController optionsGUIController;
    private OptionsCategoryType category;

    public OptionsCategoryGUI(final OptionsCategoryType categoryType) {
        this.category = categoryType;
    }

    public OptionsGUIController getOptionsGUIController() {
        return optionsGUIController;
    }

    public void setOptionsGUIController(final OptionsGUIController optGUIController) {
        this.optionsGUIController = optGUIController;
    }

    public OptionsCategoryType getCategory() {
        return category;
    }

    protected void displayOptionsAnchorpane(final AnchorPane optionsPane,
                                            final AnchorPane topLevelPane) {
        optionsPane.getChildren().add(topLevelPane);
        AnchorPane.setTopAnchor(topLevelPane, 0.0d);
        AnchorPane.setLeftAnchor(topLevelPane, 0.0d);
        AnchorPane.setRightAnchor(topLevelPane, 0.0d);
        AnchorPane.setBottomAnchor(topLevelPane, 0.0d);
    }

    public abstract void displayOptions(AnchorPane currentOptionDisplayAnchorpane);

    @Override
    public String toString() {
        return category.toString();
    }
}
