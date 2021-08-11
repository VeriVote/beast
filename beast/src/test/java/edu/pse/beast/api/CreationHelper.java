package edu.pse.beast.api;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.property.PreAndPostConditions;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CreationHelper {
    public static List<PreAndPostConditions> createSimpleCondList(final String name,
                                                                  final String preCode,
                                                                  final String postCode) {
        final PreAndPostConditions conds =
                new PreAndPostConditions(name);
        conds.getPreConditionsDescription().setCode(preCode);
        conds.getPostConditionsDescription().setCode(postCode);
        final List<PreAndPostConditions> condList =
                new ArrayList<PreAndPostConditions>();
        condList.add(conds);
        return condList;
    }
}
