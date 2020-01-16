package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class CBMCResultValueArray.
 */
public class CBMCResultValueArray implements CBMCResultValue {

    /** The Constant ELEMENT_TAG. */
    private static final String ELEMENT_TAG = "element";

    /** The Constant INDEX_NAME. */
    private static final String INDEX_NAME = "index";

    /** The values. */
    private List<CBMCResultValueWrapper> values = new ArrayList<CBMCResultValueWrapper>();

    /**
     * Sets the value.
     *
     * @param vals the new value
     */
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

    /**
     * Gets the values.
     *
     * @return the values
     */
    public List<CBMCResultValueWrapper> getValues() {
        return values;
    }

    /**
     * Gets the array size.
     *
     * @return the size of the data structure which is represented here
     */
    public int getArraySize() {
        return values.size();
    }

    @Override
    public ResultType getResultType() {
        return ResultType.ARRAY;
    }
}
