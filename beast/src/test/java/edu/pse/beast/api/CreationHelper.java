package edu.pse.beast.api;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;

public class CreationHelper {

    public static List<PreAndPostConditionsDescription> createSimpleCondList(
            String name, String preCode, String postCode) {
        PreAndPostConditionsDescription conds = new PreAndPostConditionsDescription(
                name);

        conds.getPreConditionsDescription().setCode(preCode);
        conds.getPostConditionsDescription().setCode(postCode);

        List<PreAndPostConditionsDescription> condList = new ArrayList<>();
        condList.add(conds);

        return condList;
    }

}
