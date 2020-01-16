package edu.pse.beast.codearea.codeinput;

import java.util.ArrayList;

/**
 * This class creates all available chars with a corresponding open or closing
 * char.
 *
 * @author Holger Klein
 */
public class OpenCloseCharList {

    /** The open close chars. */
    private ArrayList<OpenCloseChar> openCloseChars = new ArrayList<OpenCloseChar>();

    /** The open chars. */
    private Character[] openChars = {'{', '[', '(', '"'};

    /** The close chars. */
    private Character[] closeChars = {'}', ']', ')', '"'};

    /**
     * Instantiates a new open close char list.
     */
    public OpenCloseCharList() {
        initializeOpenCloseChars();
    }

    /**
     * Checks if is open char.
     *
     * @param c the c
     * @return true, if is open char
     */
    public boolean isOpenChar(final char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getOpen() == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the open close char.
     *
     * @param c the c
     * @return the open close char
     */
    public OpenCloseChar getOpenCloseChar(final char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getOpen() == c
                    || openCloseChars.get(i).getClose() == c) {
                return openCloseChars.get(i);
            }
        }
        return null;
    }

    /**
     * Initialize open close chars.
     */
    private void initializeOpenCloseChars() {
        for (int i = 0; i < openChars.length; ++i) {
            openCloseChars.add(new OpenCloseChar(openChars[i], closeChars[i]));
        }
    }

    /**
     * Checks if is close char.
     *
     * @param c the c
     * @return true, if is close char
     */
    public boolean isCloseChar(final char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getClose() == c) {
                return true;
            }
        }
        return false;
    }
}
