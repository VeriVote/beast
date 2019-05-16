package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
import java.util.List;

import org.w3c.dom.Element;

public interface CBMCResultValue {
    public void setValue(Element element);

    public CBMCResultValue getResultValue(List<Integer> indices);
}
