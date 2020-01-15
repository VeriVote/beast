package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CBMCResultValueArray implements CBMCResultValue {

    private static final String ELEMENT_TAG = "element";
    private static final String INDEX_NAME = "index";

    private List<CBMCResultValueWrapper> values = new ArrayList<CBMCResultValueWrapper>();

    public void setValue(final List<CBMCResultValueWrapper> vals) {
        this.values = vals;
    }

    @Override
    public void setValue(final Element element) {
        values.clear();
        // we have an object with tag "array", the children are of the form:
        // <element index="n"\>
        NodeList children = element.getElementsByTagName(ELEMENT_TAG);

        if (children.getLength() == 0) {
            System.err.println("no element found inside an array");
            return;
        }

        Node nextNode = children.item(0);
        int currentPos = 0;

        while (nextNode != null) {
            Element childElement = null;
            if ((nextNode.getNodeType() != Node.ELEMENT_NODE)) {
                System.err.println("error converting node to element");
                continue;
            } else {
                childElement = (Element) nextNode;
            }
            int index = Integer.parseInt(childElement.getAttributes()
                            .getNamedItem(INDEX_NAME).getNodeValue());
            if (index == currentPos) {
                values.add(new CBMCResultValueWrapper(
                        childElement.getFirstChild()));
            }
            // else {
                // throw new RuntimeException("mismatch between indices when
                // creating an array");
            // }
            currentPos++;
            nextNode = childElement.getNextSibling();
        }
    }

    public List<CBMCResultValueWrapper> getValues() {
        return values;
    }

    /**
     *
     * @return the size of the datastructure which is represented here
     */
    public int getArraySize() {
        return values.size();
    }

    @Override
    public ResultType getResultType() {
        return ResultType.ARRAY;
    }
}
