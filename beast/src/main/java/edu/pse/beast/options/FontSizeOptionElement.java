package edu.pse.beast.options;

import java.util.List;

public class FontSizeOptionElement extends OptionElement {
    private final int chosenSize;

    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenSize the chosen size
     * @param chosenType
     */
    public FontSizeOptionElement(String id, List<String> choosableOptions, int chosenSize) {
        super(id, choosableOptions);
        this.chosenSize = chosenSize;
    }

    /**
     * 
     * @return the chosen type
     */
    public int getChosenSize() {
        return chosenSize;
    }

    @Override
    public void handleSelection(String selection) {
        // TODO Auto-generated method stub
    }
}
