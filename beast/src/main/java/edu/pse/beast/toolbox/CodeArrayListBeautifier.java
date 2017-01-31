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

    public CodeArrayListBeautifier() {
        super();
        code = new ArrayList<>();
        numberOfTabs = 0;
    }

    public void add(String e) {
        String tabbed = "";
        for (int i = 0; i < numberOfTabs; i++) {
            tabbed += "\t";
        }
        code.add(tabbed + e);
    }

    public void addTab() {
        numberOfTabs++;
    }

    public void deleteTab() {
        if (numberOfTabs > 0) {
            numberOfTabs--;
        } else {
            ErrorLogger.log("number of tabs is allready 0");
        }
    }

    public ArrayList<String> getCodeArrayList() {
        return code;
    }
    public void addArrayList(ArrayList<String> arrayList){
        for (String singleItem : arrayList){
            this.add(singleItem);
        }
    }
}
