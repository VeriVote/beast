package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

/**
 * The Class CBMCResultPresentationHelper.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCResultPresentationHelper {
    /** The line break constant. */
    private static final String LINE_BREAK = "\n";

    /**
     * The constructor.
     */
    private CBMCResultPresentationHelper() { }

    /**
     * Gets the white spaces.
     *
     * @param amount
     *            the amount
     * @return the white spaces
     */
    private static String getWhiteSpaces(final int amount) {
        final char[] spaces = new char[amount];
        Arrays.fill(spaces, ' ');
        return new String(spaces);
    }

    /**
     * Prints the single element.
     *
     * @param single
     *            the single
     * @param offset
     *            the offset
     * @return the string
     */
    public static String printSingleElement(final CBMCResultValueSingle single,
                                            final int offset) {
        return getWhiteSpaces(offset) + single.getValue() + LINE_BREAK;
    }

    /**
     * Prints the one dim result.
     *
     * @param array
     *            the array
     * @param size
     *            the size
     * @param offset
     *            the offset
     * @return the string
     */
    public static String printOneDimResult(final CBMCResultValueArray array,
                                           final long size, final int offset) {
        String toReturn = getWhiteSpaces(offset);
        final List<CBMCResultValueWrapper> arrayValues = array.getValues();

        final long s;
        if (size < 0) {
            s = arrayValues.size();
        } else {
            s = size;
        }

        for (int i = 0; i < s; i++) {
            final CBMCResultValueSingle singleValue =
                    (CBMCResultValueSingle) arrayValues.get(i).getResultValue();
            toReturn += singleValue.getValue() + " ";
        }
        return toReturn + LINE_BREAK;
    }

    /**
     * Prints the two dim result.
     *
     * @param array
     *            the array
     * @param size
     *            the size
     * @param offset
     *            the offset
     * @return the list
     */
    public static List<String> printTwoDimResult(final CBMCResultValueArray array,
                                                 final long size,
                                                 final int offset) {
        final List<String> toReturn = new ArrayList<String>();
        final List<CBMCResultValueWrapper> arrayValues = array.getValues();

        final long s;
        if (size < 0) {
            s = arrayValues.size();
        } else {
            s = size;
        }

        for (int i = 0; i < s; i++) {
            final CBMCResultValueArray currentArray =
                    (CBMCResultValueArray) arrayValues.get(i).getResultValue();
            toReturn.add(printOneDimResult(currentArray, -1, offset));
        }
        return toReturn;
    }
}
