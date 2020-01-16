package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import java.util.ArrayList;

import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class BooleanExpScope.
 *
 * @author Holger Klein
 */
public class BooleanExpScope {

    /** The names. */
    private final ArrayList<String> names = new ArrayList<>();

    /** The types. */
    private final ArrayList<InternalTypeContainer> types = new ArrayList<>();

    /**
     * The constructor.
     *
     * @param id the id
     * @param type the type
     */
    public void addTypeForId(final String id,
                             final InternalTypeContainer type) {
        names.add(id);
        types.add(type);
    }

    /**
     * Gets the type for id.
     *
     * @param id the id
     * @return the type for id
     */
    public InternalTypeContainer getTypeForId(final String id) {
        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).equals(id)) {
                return types.get(i);
            }
        }
        return null;
    }

    /**
     * Removes the.
     *
     * @param id the id
     */
    public void remove(final String id) {
        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).equals(id)) {
                names.remove(i);
                types.remove(i);
            }
        }
    }
}
