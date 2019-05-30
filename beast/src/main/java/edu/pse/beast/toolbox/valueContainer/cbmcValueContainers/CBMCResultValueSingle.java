package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class CBMCResultValueSingle implements CBMCResultValue {

    private final String TYPE_NAME = "c_type";
    private final String WIDTH_NAME = "width";

    private boolean set = false;
    private boolean parsed = false;

    private String type = "";
    private String value = "";

    private int width = 0;
    private Number numberValue = null;

    @Override
    public void setValue(Element element) {
        NamedNodeMap attributes = element.getAttributes();

        this.type = attributes.getNamedItem(TYPE_NAME).getNodeValue();

        this.value = element.getFirstChild().getNodeValue(); // the value is saved in the first child

        this.width = Integer.parseInt(attributes.getNamedItem(WIDTH_NAME).getNodeValue()); // the width of the data type

        Number toNumber = Long.parseLong(this.value);
    }

    public String getValue() {
        return value;
    }
    
    public String getValueType(List<Integer> indices) {
        return type;
    }

    public int getValueWidth(List<Integer> indices) {
        return width;
    }

    public Number getNumberValue(List<Integer> indices) {
        if (!parsed) {
            this.numberValue = parseNumber(type, value, width);
            parsed = true;
        }
        return numberValue;
    }

    private void checkIndex(List<Integer> indices) {
        if (indices.size() != 0) {
            throw new IndexOutOfBoundsException("ResultValueSingle objects only contain one element, "
                    + "therefore the index list has to be empty");
        }
    }

    private static Number parseNumber(String dataType, String dataValue, int dataWidth) {
        System.out.println("extract to toolbox!");
        
        if (dataType.contains("char") || dataType.contains("byte") || dataType.contains("short")
                || dataType.contains("int")
                || (dataType.contains("long") && (!dataType.contains("double") && !dataType.contains("float")))) {
            return parseNaturalNumber(dataValue, dataWidth);
        } else if (dataType.contains("float") || dataType.contains("double")) {
            return parseDecimalNumber(dataValue, dataWidth);
        } else {
            System.out.println("unknown type: " + dataType);
            return null;
        }
    }

    private static Number parseNaturalNumber(String dataValue, int dataWidth) {
        // we are non inclusive in the width checks, as unsigned values could be cut off
        // otherwise
        if (dataWidth < 32) {
            return Integer.parseInt(dataValue);
        } else if (dataWidth < 64) {
            return Long.parseLong(dataValue);
        } else {
            return new BigInteger(dataValue);
        }
    }

    private static Number parseDecimalNumber(String dataValue, int dataWidth) {
        if (dataWidth <= 32) {
            return Float.parseFloat(dataValue);
        } else if (dataWidth <= 64) {
            return Double.parseDouble(dataValue);
        } else {
            return new BigDecimal(dataValue);
        }
    }
}
