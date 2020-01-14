package edu.pse.beast.stringresource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;

import edu.pse.beast.toolbox.ErrorLogger;

/**
 *
 * @author Niels Hanselmann
 */
public class StringResourceLoader {
    private final HashMap<String, String> idsToString;

    /**
     *
     * @param stringRes a Linked List with the correct format. Id : string
     * @throws ArrayIndexOutOfBoundsException if the list is not correctly formatted
     */
    public StringResourceLoader(LinkedList<String> stringRes)
            throws ArrayIndexOutOfBoundsException {
        idsToString = new HashMap<>();

        for (Iterator<String> iterator = stringRes.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            if (line.length() != 0) {
                String[] split = line.split(":", 2);
                String id = split[0].trim();
                String displayedText = split[1].trim();

                idsToString.put(id.toLowerCase(), displayedText);
            }
        }
    }

    /**
     * @param id Id of the String you want to load
     * @return the String with the id
     */
    public String getStringFromID(String id) {
        final String get;
        if (id != null) {
            get = idsToString.get(id.toLowerCase());
        } else {
            get = null;
        }
        if (get == null) {
            ErrorLogger.log("this Id was not found in a String file: "
                            + (id != null ? id.toLowerCase() : null));
        }
        return get;
    }

    /**
     * if multiple keys have the same Value only the first key is returned
     *
     * @param s the String for which you want to know the Id
     * @return if the String is not found null is returned, otherwise the id is
     *         returned
     */
    public String getIdForString(String s) {
        if (idsToString.containsValue(s)) {
            for (Entry<String, String> entry : idsToString.entrySet()) {
                if (Objects.equals(s.toLowerCase(), entry.getValue().toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     *
     * @param id the checked id
     * @return true if it contains the id
     */
    public boolean containsId(String id) {
        return id != null && idsToString.containsKey(id.toLowerCase());
    }
}
