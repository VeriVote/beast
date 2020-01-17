package edu.pse.beast.options;

import javax.swing.JComboBox;

/**
 * A JComboBox to which OptionElement subclasses can be added.
 *
 * @author Holger Klein
 */
public class OptionElemComboBox extends JComboBox<String> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The elem. */
    private final OptionElement elem;

    /**
     * Constructor.
     *
     * @param optElem
     *            OptionElement displayed by this OptionElemComboBox
     */
    public OptionElemComboBox(final OptionElement optElem) {
        super();
        this.elem = optElem;
    }

    /**
     * Getter for the displayed OptionElement.
     *
     * @return displayed OptionElement
     */
    public OptionElement getElem() {
        return elem;
    }
}
