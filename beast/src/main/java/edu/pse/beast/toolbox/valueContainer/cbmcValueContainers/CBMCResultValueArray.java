package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CBMCResultValueArray implements CBMCResultValue {
    
    private final String ELEMENT_TAG = "element";
    private final String INDEX_NAME = "index";
    
    private List<CBMCResultValueWrapper> values = new ArrayList<CBMCResultValueWrapper>();
    List<Integer> indices = new ArrayList<Integer>();

    @Override
    public void setValue(Element element) {
        values.clear();
        
        // we have an object with tag "array", the children are of the form: 
        //<element index="n"\>
        
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
            
            int index = Integer.parseInt(childElement.getAttributes().getNamedItem(INDEX_NAME).getNodeValue());
            
            if (index != currentPos) {
                //throw new RuntimeException("mismatch between indices when creating an array");
            } else {
                values.add(new CBMCResultValueWrapper(childElement.getFirstChild()));
            }
            
            currentPos++;
            
            nextNode = childElement.getNextSibling();
        }        
    }

    @Override
    public CBMCResultValue getResultValue(List<Integer> indices) {
        int index = indices.get(0);
        // the sublist contains all indices not used so far
        return values.get(index).getResultValueAtPos(indices.subList(1, indices.size()));
    }
}
