package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
import org.w3c.dom.Element;

public class CBMCResultValuePointer implements CBMCResultValue {

    @Override
    public void setValue(Element element) {
        System.err.println("FIX ResultValuePointer"); //TODO ask Michael how pointers can be extracted
    }

	@Override
	public ResultType getResultType() {
		return ResultType.POINTER;
	}
}
