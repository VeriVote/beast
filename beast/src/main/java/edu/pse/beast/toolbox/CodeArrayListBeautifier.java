package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Niels Hanselmann
 */
public class CodeArrayListBeautifier {
    /**
     * Tab sign "\t" or replacement, we currently rather use spaces than tabs.
     */
    static final String TAB = "    ";

    private int numberOfTabs;
    private final ArrayList<String> code;

    /**
     * Creates a new CodeArrayListBeautifier with an empty ArrayList
     */
    public CodeArrayListBeautifier() {
        super();
        code = new ArrayList<>();
        numberOfTabs = 0;
    }

    /**
     * Adds a String to the ArrayList with the indenting
     *
     * @param addedString
     *            the String which is added to the ArrayList
     */
    public void add(String addedString) {
        if (addedString != null) {
            if (addedString.contains("}")) {
                Math.max(0, numberOfTabs--);
            }
            String tabbed = "";
            for (int i = 0; i < numberOfTabs; i++) {
                tabbed += TAB;
            }
            code.add(tabbed + addedString);
            if (addedString.contains("{")) {
                numberOfTabs++;
            }
        }
    }

    /**
     * adds another tab to the indenting. If you add another String after this
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
     *
     * @return the ArrayList with the indenting
     */
    public ArrayList<String> getCodeArrayList() {
        return code;
    }

    /**
     * adds an ArrayList with the indenting
     *
     * @param list
     *            the added ArrayList
     */
    public void addList(List<String> list) {
        if (list != null) {
            list.forEach((singleItem) -> {
                this.add(singleItem);
            });
        }
    }

    public void addAll(List<String> votingResultCode) {
        addList(votingResultCode);
    }

    public boolean contains(String name) {
        for (Iterator<String> iterator = code.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();
            if (line.contains(name)) {
                return true;
            }
        }
        return false;
    }

    public String getNotUsedVarName(String preferredName) {
        int length = Math.min(preferredName.length(), 2);
        String name = preferredName;
        while (this.contains(name)) {
            name = RandomStringUtils.random(length++, true, false);
        }
        return name;
    }
}
