package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * The Class CodeArrayListBeautifier.
 *
 * @author Niels Hanselmann
 */
public class CodeArrayListBeautifier {
    /**
     * Tab sign "\t" or replacement, we currently rather use spaces than tabs.
     */
    static final String TAB = "    ";

    /** The number of tabs. */
    private int numberOfTabs;

    /** The code. */
    private final ArrayList<String> code;

    /**
     * Creates a new CodeArrayListBeautifier with an empty ArrayList.
     */
    public CodeArrayListBeautifier() {
        super();
        code = new ArrayList<String>();
        numberOfTabs = 0;
    }

    /**
     * Adds a String to the ArrayList with indenting.
     *
     * @param addedString
     *            the String which is added to the ArrayList
     */
    public void add(final String addedString) {
        if (addedString != null) {
            if (addedString.contains(CCodeHelper.CLOSING_BRACES)) {
                Math.max(0, numberOfTabs--);
            }
            String tabbed = "";
            for (int i = 0; i < numberOfTabs; i++) {
                tabbed += TAB;
            }
            code.add(tabbed + addedString);
            if (addedString.contains(CCodeHelper.OPENING_BRACES)) {
                numberOfTabs++;
            }
        }
    }

    /**
     * Adds an empty line to the ArrayList with indenting.
     */
    public void add() {
        add("");
    }

    /**
     * Adds the given number of spaces to the ArrayList with indenting.
     *
     * @param numberOfSpaces
     *            the number of spaces
     */
    public void addSpaces(final int numberOfSpaces) {
        if (0 <= numberOfSpaces) {
            String spaces = "";
            for (int remSpaces = numberOfSpaces; 0 < remSpaces; remSpaces--) {
                spaces += " ";
            }
            add(spaces);
        }
    }

    /**
     * Adds another tab to the indenting. If you add another String after this
     * it will be indented one more time
     */
    public void addTab() {
        numberOfTabs++;
    }

    /**
     * Decreases the number of tabs by one. If the tab count is already 0 it
     * will no longer decrease it.
     */
    public void deleteTab() {
        if (numberOfTabs > 0) {
            numberOfTabs--;
        } else {
            ErrorLogger.log("number of tabs is already 0");
        }
    }

    /**
     * Gets the code array list.
     *
     * @return the ArrayList with the indenting
     */
    public ArrayList<String> getCodeArrayList() {
        return code;
    }

    /**
     * Adds an ArrayList with the indenting.
     *
     * @param list
     *            the added ArrayList
     */
    public void addList(final List<String> list) {
        if (list != null) {
            list.forEach(singleItem -> {
                this.add(singleItem);
            });
        }
    }

    /**
     * Adds the all.
     *
     * @param votingResultCode
     *            the voting result code
     */
    public void addAll(final List<String> votingResultCode) {
        addList(votingResultCode);
    }

    /**
     * Contains.
     *
     * @param name
     *            the name
     * @return true, if successful
     */
    public boolean contains(final String name) {
        for (Iterator<String> iterator = code.iterator(); iterator.hasNext();) {
            final String line = iterator.next();
            if (line.contains(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the not used var name.
     *
     * @param preferredName
     *            the preferred name
     * @return the not used var name
     */
    public String getNotUsedVarName(final String preferredName) {
        int length = Math.min(preferredName.length(), 2);
        String name = preferredName;
        while (this.contains(name)) {
            name = RandomStringUtils.random(length++, true, false);
        }
        return name;
    }
}
