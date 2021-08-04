package edu.pse.beast.gui.options;

import javafx.scene.layout.AnchorPane;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class OptionsCategoryGUI {
    private OptionsGUIController optionsGUIController;
    private OptionsCategoryType category;

    public OptionsCategoryGUI(final OptionsCategoryType categoryType) {
        this.category = categoryType;
    }

    public final OptionsGUIController getOptionsGUIController() {
        return optionsGUIController;
    }

    public final void setOptionsGUIController(final OptionsGUIController optGUIController) {
        this.optionsGUIController = optGUIController;
    }

    public final OptionsCategoryType getCategory() {
        return category;
    }

    protected final void displayOptionsAnchorpane(final AnchorPane optionsPane,
                                            final AnchorPane topLevelPane) {
        optionsPane.getChildren().add(topLevelPane);
        AnchorPane.setTopAnchor(topLevelPane, 0.0d);
        AnchorPane.setLeftAnchor(topLevelPane, 0.0d);
        AnchorPane.setRightAnchor(topLevelPane, 0.0d);
        AnchorPane.setBottomAnchor(topLevelPane, 0.0d);
    }

    public abstract void displayOptions(AnchorPane currentOptionDisplayAnchorpane);

    @Override
    public final String toString() {
        return category.toString();
    }
}
