package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CBMCResultValueStruct implements CBMCResultValue {
    private static final String MEMBER_TAG = "member";
    private static final String MEMBER_NAME = "name";

    private Map<String, CBMCResultValueWrapper> values =
            new HashMap<String, CBMCResultValueWrapper>();

    @Override
    public void setValue(final Element element) {
        values.clear();
        // we have an object with tag "array", the children are of the form:
        // <element index="n"\>
        NodeList subVariables = element.getElementsByTagName(MEMBER_TAG);

        if (subVariables.getLength() == 0) {
            System.err.println("no elements found inside a struct");
            return;
        }

        for (int i = 0; i < subVariables.getLength(); i++) {
            Element currentMember = null;
            if ((subVariables.item(i).getNodeType() != Node.ELEMENT_NODE)) {
                System.err.println("error converting node to element");
                continue;
            } else {
                currentMember = (Element) subVariables.item(i);
                String memberName =
                        currentMember.getAttributes().getNamedItem(MEMBER_NAME).getNodeValue();
                values.put(memberName, new CBMCResultValueWrapper(currentMember.getFirstChild()));
            }
        }
    }

    public void setValue(final CBMCResultValueWrapper wrapper, final String name) {
        values.put(name, wrapper);
    }

    public CBMCResultValueWrapper getResultVariable(final String variableName) {
        return values.get(variableName);
    }

    @Override
    public ResultType getResultType() {
        return ResultType.STRUCT;
    }
}
