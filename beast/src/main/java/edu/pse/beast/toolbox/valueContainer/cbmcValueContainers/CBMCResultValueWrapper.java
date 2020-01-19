package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pse.beast.toolbox.XMLtoolbox;
import edu.pse.beast.toolbox.valueContainer.ResultValue;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

/**
 * The Enum CDATATYPE.
 *
 * @author Lukas Stapelbroek
 */
enum CDATATYPE {
    /** The single. */
    SINGLE,

    /** The array. */
    ARRAY,

    /** The struct. */
    STRUCT,

    /** The pointer. */
    POINTER;
}

/**
 * The Class CBMCResultValueWrapper.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCResultValueWrapper extends ResultValueWrapper {
    /** The data type. */
    private CDATATYPE dataType;

    /** The initialized. */
    private boolean initialized = false;

    /** The value container. */
    private CBMCResultValue valueContainer;

    /**
     * Creates a new wrapper.
     *
     * @param mainIndex
     *            the index of this variable (for example votes1 has the main
     *            index of 1)
     * @param name
     *            the name of this variable (for example votes1 has the name
     *            votes)
     * @param node
     *            the node
     */
    public CBMCResultValueWrapper(final int mainIndex, final String name,
                                  final Node node) {
        super(mainIndex, name);
        updateValue(node);
    }

    /**
     * The constructor.
     */
    public CBMCResultValueWrapper() {
    }

    /**
     * The constructor.
     *
     * @param resultValueContainer
     *            the result value container
     */
    public CBMCResultValueWrapper(final CBMCResultValue resultValueContainer) {
        this.valueContainer = resultValueContainer;
    }

    /**
     * The constructor.
     *
     * @param node
     *            the node
     */
    public CBMCResultValueWrapper(final Node node) {
        super();
        updateValue(node);
    }

    /**
     * Update value.
     *
     * @param node
     *            the node
     */
    public void updateValue(final Node node) {
        XMLtoolbox.clean(node);
        Element element = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) node;
        } else {
            System.err.println("problem parsing the node to an element");
            return;
        }
        // get the data type of the first element
        CDATATYPE newType = getDataType(element);
        if (initialized) {
            if (newType != dataType) {
                throw new RuntimeException("Mismatching datatypes found"
                                            + " while parsing");
            }
        } else {
            initialize(newType);
        }
        setNewValue(element);
    }

    /**
     * Sets the value.
     *
     * @param newValueContainer
     *            the new value
     */
    public void setValue(final CBMCResultValue newValueContainer) {
        this.valueContainer = newValueContainer;
    }

    /**
     * Initialize.
     *
     * @param newType
     *            the new type
     */
    private void initialize(final CDATATYPE newType) {
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
            System.err.println("Unrecognized data type.");
            break;
        }
    }

    /**
     * Set the new value of the underlying property.
     *
     * @param element
     *            the new new value
     */
    private void setNewValue(final Element element) {
        valueContainer.setValue(element);
    }

    /**
     * Gets the data type.
     *
     * @param node
     *            the node
     * @return the data type
     */
    private static CDATATYPE getDataType(final Node node) {
        String nodeTagName = node.getNodeName();
        CDATATYPE type = null;
        switch (nodeTagName.toLowerCase()) {
        case "array":
            type = CDATATYPE.ARRAY;
            break;
        case "struct":
            type = CDATATYPE.STRUCT;
            break;
        case "pointer":
            type = CDATATYPE.POINTER;
            break;
        default:
            NodeList children = node.getChildNodes();
            if (children.getLength() != 1) {
                System.err.println("Parsing the XML encounterd a"
                                    + " 'single' element, which"
                                    + " does not contain one child node");
                throwError();
            } else {
                type = CDATATYPE.SINGLE;
            }
            break;
        }
        return type;
    }

    //
    // public CBMCResultValue getResultValueAtPos(List<Integer> indices) {
    // return valueContainer.getResultValue(indices);
    // }

    /**
     * Throw error.
     *
     * @throws IndexOutOfBoundsException
     *            always
     */
    private static void throwError() {
        throw new IndexOutOfBoundsException("The dimension of the object does"
                                            + " not match the dimension of"
                                            + " this container.");
    }

    @Override
    public ResultValue getResultValue() {
        return valueContainer;
    }
}
