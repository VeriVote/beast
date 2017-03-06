/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.util.ArrayList;

/**
 *
 * @author Niels
 */
public class CodeArrayListBeautifier {

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
     * @param addedString the String which is added to the ArrayList
     */
    public void add(String addedString) {
        if (addedString != null) {
            String tabbed = "";
            for (int i = 0; i < numberOfTabs; i++) {
                tabbed += "\t";
            }
            code.add(tabbed + addedString);
        }
    }

    /**
     * adds another tab to the indenting. If you add another String after this
     * it will be indeted one more time
     */
    public void addTab() {
        numberOfTabs++;
    }

    /**
     * Decreases the number of tabs by one. If the Tabcount is allready 0 it
     * will no longer decrease it.
     */
    public void deleteTab() {
        if (numberOfTabs > 0) {
            numberOfTabs--;
        } else {
            ErrorLogger.log("number of tabs is allready 0");
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
     * @param arrayList the added ArrayList
     */
    public void addArrayList(ArrayList<String> arrayList) {
        if (arrayList != null) {
            arrayList.forEach((singleItem) -> {
                this.add(singleItem);
            });
        }
    }
}
