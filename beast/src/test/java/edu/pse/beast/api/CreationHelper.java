package edu.pse.beast.api;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CreationHelper {
    public static List<PreAndPostConditionsDescription>
            createSimpleCondList(final String name,
                                 final String preCode,
                                 final String postCode) {
        final PreAndPostConditionsDescription conds =
                new PreAndPostConditionsDescription(name);
        conds.getPreConditionsDescription().setCode(preCode);
        conds.getPostConditionsDescription().setCode(postCode);
        final List<PreAndPostConditionsDescription> condList = new ArrayList<>();
        condList.add(conds);
        return condList;
    }
}
