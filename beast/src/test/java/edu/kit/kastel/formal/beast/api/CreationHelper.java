package edu.kit.kastel.formal.beast.api;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CreationHelper {
    /**
     * Create a simple condition list.
     *
     * @param name the name for the property description
     * @param preCode the precondition descriptions
     * @param postCode the postcondition descriptions
     * @return the list of property descriptions
     */
    public static List<PropertyDescription> createSimpleCondList(final String name,
                                                                  final String preCode,
                                                                  final String postCode) {
        final PropertyDescription conds =
                new PropertyDescription(name);
        conds.getPreConditionsDescription().setCode(preCode);
        conds.getPostConditionsDescription().setCode(postCode);
        final List<PropertyDescription> condList =
                new ArrayList<PropertyDescription>();
        condList.add(conds);
        return condList;
    }
}
