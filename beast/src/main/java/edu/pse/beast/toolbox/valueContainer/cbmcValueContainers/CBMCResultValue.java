package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import org.w3c.dom.Element;

import edu.pse.beast.toolbox.valueContainer.ResultValue;

public interface CBMCResultValue extends ResultValue {
    void setValue(Element element);
}
