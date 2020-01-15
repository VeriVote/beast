package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class CBMCResultValueSingle implements CBMCResultValue {
    private static final int INT_LENGTH = 32;
    private static final int LONG_LENGTH = 64;
    private static final String TYPE_NAME = "c_type";
    private static final String WIDTH_NAME = "width";

    private boolean parsed = false;
    private String type = "";
    private String value = "";
    private int width = 0;
    private Number numberValue = null;

    /**
     *
     * @param typeString
     *            the datatye (e.g "int")
     * @param val
     *            the value (e.g "5")
     * @param bitWidth
     *            the width in bit this datatype has (e.g 32 for int)
     */
    public void setValue(final String typeString,
                         final String val,
                         final int bitWidth) {
        this.type = typeString;
        this.value = val;
        this.width = bitWidth;
    }

    @Override
    public void setValue(final Element element) {
        NamedNodeMap attributes = element.getAttributes();
        this.type = attributes.getNamedItem(TYPE_NAME).getNodeValue();
        this.value = // the value is saved in the first child
                element.getFirstChild().getNodeValue();
        this.width = // the width of the data type
                Integer.parseInt(attributes.getNamedItem(WIDTH_NAME).getNodeValue());
    }

    public String getValue() {
        return value;
    }

    public String getValueType() {
        return type;
    }

    public int getValueWidth() {
        return width;
    }

    public Number getNumberValue() {
        if (!parsed) {
            this.numberValue = parseNumber(type, value, width);
            parsed = true;
        }
        return numberValue;
    }

    private void checkIndex(final List<Integer> indices) {
        if (indices.size() != 0) {
            throw new IndexOutOfBoundsException(
                    "ResultValueSingle objects only contain one element, "
                            + "therefore the index list has to be empty");
        }
    }

    private static Number parseNumber(final String dataType,
                                      final String dataValue,
                                      final int dataWidth) {
        if (dataType.contains("char") || dataType.contains("byte")
                || dataType.contains("short") || dataType.contains("int")
                || (dataType.contains("long") && (!dataType.contains("double")
                        && !dataType.contains("float")))) {
            return parseNaturalNumber(dataValue, dataWidth);
        } else if (dataType.contains("float") || dataType.contains("double")) {
            return parseDecimalNumber(dataValue, dataWidth);
        } else {
            System.out.println("unknown type: " + dataType);
            return null;
        }
    }

    private static Number parseNaturalNumber(final String dataValue,
                                             final int dataWidth) {
        // we are non inclusive in the width checks, as unsigned values could be
        // cut off
        // otherwise
        if (dataWidth < INT_LENGTH) {
            return Integer.parseInt(dataValue);
        } else if (dataWidth < LONG_LENGTH) {
            return Long.parseLong(dataValue);
        } else {
            return new BigInteger(dataValue);
        }
    }

    private static Number parseDecimalNumber(final String dataValue,
                                             final int dataWidth) {
        if (dataWidth <= INT_LENGTH) {
            return Float.parseFloat(dataValue);
        } else if (dataWidth <= LONG_LENGTH) {
            return Double.parseDouble(dataValue);
        } else {
            return new BigDecimal(dataValue);
        }
    }

    public Number getValueAsNumber() {
        return getNumberValue();
    }

    @Override
    public ResultType getResultType() {
        return ResultType.SINGLE;
    }
}
