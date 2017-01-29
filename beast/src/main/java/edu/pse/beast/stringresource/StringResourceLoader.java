/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Niels
 */
public class StringResourceLoader {

    private final HashMap<String, String> idsToString;

    /**
     *
     * @param stringRes a Linked List with the correct format. Id : string
     * @throws ArrayIndexOutOfBoundsException if the list is not correctly
     * formatted
     */
    public StringResourceLoader(LinkedList<String> stringRes) throws ArrayIndexOutOfBoundsException {
        idsToString = new HashMap<>();
        String line;
        String[] split;
        String id;
        String displayedText;
        while (!stringRes.isEmpty()) {
            line = stringRes.pop();
            if(line.length() == 0) continue;
            split = line.split(":");
            id = split[0].trim();
            displayedText = split[1].trim();
            idsToString.put(id, displayedText);
        }
    }

    /**
     *
     * @param id Id of the String you want to load
     * @return the String with the id
     */
    public String getStringFromID(String id) {
        return idsToString.get(id);
    }
}
