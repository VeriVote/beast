package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pse.beast.toolbox.XMLtoolbox;
import edu.pse.beast.toolbox.valueContainer.ResultValue;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

enum C_DATATYPE {
    SINGLE, ARRAY, STRUCT, POINTER;
}

public class CBMCResultValueWrapper extends ResultValueWrapper {

    private C_DATATYPE dataType;
    
    private boolean initialized = false;

    private CBMCResultValue valueContainer;

    /**
     * creates a new wrapper
     *
     * @param mainIndex    the index of this variable (for example votes1 has the
     *                     main index of 1)
     * @param name         the name of this variable (for example votes1 has the
     *                     name votes)
     * @param singleObject if true, it signals that only a single value will be
     *                     stored
     */
    public CBMCResultValueWrapper(int mainIndex, String name, Node node) {
    	super(mainIndex, name);
    	
        updateValue(node);
    }
    
    public CBMCResultValueWrapper() {
    	//TODO add functionality to add sub values by adding them through objects, and not through xml nodes
    }
    
    public CBMCResultValueWrapper(Node node) {
    	super();
    	
    	updateValue(node);
    }

    public void updateValue(Node node) {
        XMLtoolbox.clean(node);
        
        Element element = null;

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) node;
        } else {
            System.err.println("problem parsing the node to an element");
            return;
        }
        
        C_DATATYPE newType = getDataType(element); // get the data type of the first element
        
        if (initialized) {
            if (newType != dataType) {
                throw new RuntimeException("Mismatching datatypes found while parsing");
            }
        } else {
            initialize(newType);
        }
        
        setNewValue(element);
    }

    private void initialize(C_DATATYPE newType) {
        this.dataType = newType;
        initialized = true;

        switch (newType) {
        case ARRAY:
            valueContainer = new CBMCResultValueArray();
            break;

        case STRUCT:
            valueContainer = new CBMCResultValueStruct();
            break;

        case POINTER:
            valueContainer = new CBMCResultValuePointer();
            break;

        case SINGLE:
            valueContainer = new CBMCResultValueSingle();
            break;

        default:
            
            System.err.println("unrecognized data type");
            
            break;
        }
    }

    /**
     * set the new value of the underlying property
     * @param element
     */
    private void setNewValue(Element element) {
        valueContainer.setValue(element);
    }

    private static C_DATATYPE getDataType(Node node) {

        String nodeTagName = node.getNodeName();
        
        C_DATATYPE type = null;
        
        switch (nodeTagName.toLowerCase()) {
        case "array":
            type = C_DATATYPE.ARRAY;
            break;

        case "struct":
            type = C_DATATYPE.STRUCT;
            break;

        case "pointer":
            type = C_DATATYPE.POINTER;
            break;

        default:

            NodeList children = node.getChildNodes();

            if (children.getLength() != 1) {
                System.err.println("parsing the XML encounterd a 'single' element which does not contain one child node");
                throwError();
            } else {
                type = C_DATATYPE.SINGLE;
            }

            break;
        }

        return type;
    }
//    
//    public CBMCResultValue getResultValueAtPos(List<Integer> indices) {
//        return valueContainer.getResultValue(indices);
//    }

    private static void throwError() {
        throw new IndexOutOfBoundsException(
                "The dimension of the object does not match the dimension of this container");
    }

    @Override
    public ResultValue getResultValue() {
        return valueContainer;
    }
}
