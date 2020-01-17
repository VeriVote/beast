package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import org.w3c.dom.Element;

import edu.pse.beast.toolbox.valueContainer.ResultValue;

/**
 * The Interface CBMCResultValue.
 */
public interface CBMCResultValue extends ResultValue {

    /**
     * Sets the value.
     *
     * @param element
     *            the new value
     */
    void setValue(Element element);
}
