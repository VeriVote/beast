package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import org.w3c.dom.Element;

public class CBMCResultValuePointer implements CBMCResultValue {

    @Override
    public void setValue(final Element element) {
        // TODO ask Michael how pointers can be extracted
        System.err.println("FIX ResultValuePointer");
    }

    @Override
    public ResultType getResultType() {
        return ResultType.POINTER;
    }
}
