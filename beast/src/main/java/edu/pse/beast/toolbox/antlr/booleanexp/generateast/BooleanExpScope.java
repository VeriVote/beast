package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import java.util.ArrayList;

import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Holger Klein
 */
public class BooleanExpScope {
    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<InternalTypeContainer> types = new ArrayList<>();

    public void addTypeForId(String id, InternalTypeContainer type) {
        names.add(id);
        types.add(type);
    }

    public InternalTypeContainer getTypeForId(String id) {
        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).equals(id)) {
                return types.get(i);
            }
        }
        return null;
    }

    public void remove(String id) {
        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).equals(id)) {
                names.remove(i);
                types.remove(i);
            }
        }
    }
}