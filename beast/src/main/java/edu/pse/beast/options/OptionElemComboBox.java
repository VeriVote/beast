
package edu.pse.beast.options;

import javax.swing.JComboBox;

/**
 * A JComboBox to which OptionElement subclasses can be added.
 * @author Holger-Desktop
 */
public class OptionElemComboBox extends JComboBox<String> {
    private static final long serialVersionUID = 1L;
    private final OptionElement elem;
    /**
     * Constructor
     * @param elem OptionElement displayed by this OptionElemComboBox
     */
    public OptionElemComboBox(OptionElement elem) {
        super();
        this.elem = elem;
    }
    /**
     * Getter for the displayed OptionElement
     * @return displayed OptionElement
     */
    public OptionElement getElem() {
        return elem;
    }
    
}
