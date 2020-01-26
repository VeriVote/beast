package edu.pse.beast.stringresource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import edu.pse.beast.toolbox.ErrorLogger;

/**
 * The Class StringResourceLoader.
 *
 * @author Niels Hanselmann
 */
public class StringResourceLoader {
    /** The ids to string. */
    private final Map<String, String> idsToString;

    /**
     * Instantiates a new string resource loader.
     *
     * @param stringRes
     *            a Linked List with the correct format. Id : string
     * @throws ArrayIndexOutOfBoundsException
     *             if the list is not correctly formatted
     */
    public StringResourceLoader(final LinkedList<String> stringRes)
            throws ArrayIndexOutOfBoundsException {
        idsToString = new HashMap<String, String>();

        for (final Iterator<String> iterator = stringRes.iterator();
                iterator.hasNext();) {
            final String line = iterator.next();

            if (line.length() != 0) {
                final String[] split = line.split(":", 2);
                final String id = split[0].trim();
                final String displayedText = split[1].trim();
                idsToString.put(id.toLowerCase(), displayedText);
            }
        }
    }

    /**
     * Gets the string from ID.
     *
     * @param id
     *            Id of the String you want to load
     * @return the String with the id
     */
    public String getStringFromID(final String id) {
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
     * If multiple keys have the same value, only the first key is returned.
     *
     * @param s
     *            the String for which you want to know the Id
     * @return if the String is not found null is returned, otherwise the id is
     *         returned
     */
    public String getIdForString(final String s) {
        if (idsToString.containsValue(s)) {
            for (Entry<String, String> entry : idsToString.entrySet()) {
                if (Objects.equals(s.toLowerCase(),
                                   entry.getValue().toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Contains id.
     *
     * @param id
     *            the checked id
     * @return true if it contains the id
     */
    public boolean containsId(final String id) {
        return id != null && idsToString.containsKey(id.toLowerCase());
    }
}
