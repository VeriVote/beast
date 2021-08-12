package edu.pse.beast.api;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CreationHelper {
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
