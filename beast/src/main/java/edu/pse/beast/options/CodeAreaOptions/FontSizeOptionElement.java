package edu.pse.beast.options.CodeAreaOptions;

import edu.pse.beast.options.OptionElement;
import java.util.List;

public class FontSizeOptionElement extends OptionElement {
    
    private int size;
    
    /**
     * 
     * @param id the id
     * @param choosableOptions the choosable options
     * @param chosenSize the chosen size
     * @param chosenType
     */
    public FontSizeOptionElement(List<String> choosableOptions, String chosenSize) {
        super("fontsize", choosableOptions);
        handleSelection(chosenSize);
    }

    public int getsize() {
        return size;
    }
     

    @Override
    public void handleSelection(String selection) {
        this.chosenOption = selection;
        this.size = Integer.valueOf(selection);
    }
}
