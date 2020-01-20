package edu.pse.beast.toolbox.valueContainer.cbmcValueContainers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * The Class CBMCResultValueSingle.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCResultValueSingle implements CBMCResultValue {
    /** The Constant INT_LENGTH. */
    private static final int INT_LENGTH = 32;

    /** The Constant LONG_LENGTH. */
    private static final int LONG_LENGTH = 64;

    /** The Constant TYPE_NAME. */
    private static final String TYPE_NAME = "c_type";

    /** The Constant WIDTH_NAME. */
    private static final String WIDTH_NAME = "width";

    /** The parsed. */
    private boolean parsed;

    /** The type. */
    private String type = "";

    /** The value. */
    private String value = "";

    /** The width. */
    private int width;

    /** The number value. */
    private Number numberValue;

    /**
     * Sets the value.
     *
     * @param typeString
     *            the datatye (e.g "int")
     * @param val
     *            the value (e.g "5")
     * @param bitWidth
     *            the width in bit this datatype has (e.g 32 for int)
     */
    public void setValue(final String typeString, final String val,
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
                Integer.parseInt(
                        attributes.getNamedItem(WIDTH_NAME).getNodeValue()
                );
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the value type.
     *
     * @return the value type
     */
    public String getValueType() {
        return type;
    }

    /**
     * Gets the value width.
     *
     * @return the value width
     */
    public int getValueWidth() {
        return width;
    }

    /**
     * Gets the number value.
     *
     * @return the number value
     */
    public Number getNumberValue() {
        if (!parsed) {
            this.numberValue = parseNumber(type, value, width);
            parsed = true;
        }
        return numberValue;
    }

    /**
     * Check index.
     *
     * @param indices
     *            the indices
     * @throws IndexOutOfBoundsException
     *            if the index list is not empty.
     */
    private void checkIndex(final List<Integer> indices) {
        if (indices.size() != 0) {
            throw new IndexOutOfBoundsException("ResultValueSingle objects"
                                                + " only contain one element,"
                                                + " therefore the index list"
                                                + " must be empty.");
        }
    }

    /**
     * Parses the number.
     *
     * @param dataType
     *            the data type
     * @param dataValue
     *            the data value
     * @param dataWidth
     *            the data width
     * @return the number
     */
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

    /**
     * Parses the natural number.
     *
     * @param dataValue
     *            the data value
     * @param dataWidth
     *            the data width
     * @return the number
     */
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

    /**
     * Parses the decimal number.
     *
     * @param dataValue
     *            the data value
     * @param dataWidth
     *            the data width
     * @return the number
     */
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

    /**
     * Gets the value as number.
     *
     * @return the value as number
     */
    public Number getValueAsNumber() {
        return getNumberValue();
    }

    @Override
    public ResultType getResultType() {
        return ResultType.SINGLE;
    }
}
