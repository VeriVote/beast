package edu.pse.beast.stringresource;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holger on 12.03.17.
 */
public class EnumToIdMapping {
    private static Map<String, String> enumStringsToIds;

    private EnumToIdMapping() {}

    public static String getIdStringForEnumString(String enumString) {
        if(enumStringsToIds == null) createEnumStringsToIds();
        return enumStringsToIds.get(enumString);
    }

    public static String getIDInFile(ElectionTypeContainer.ElectionTypeIds id) {
        return getIdStringForEnumString(id.toString());
    }

    public static String getEnumStringFromIdInFile(String idInFile) {
        for (Map.Entry<String, String> e:
             enumStringsToIds.entrySet()) {
            if(e.getValue().equals(idInFile))
                return e.getKey();
        }
        return null;
    }

    private static void createEnumStringsToIds() {
        enumStringsToIds = new HashMap<>();
        createElectionTypeStringMapping();
    }

    private static void createElectionTypeStringMapping() {
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.SINGLE_CHOICE.toString(),
                "one_candidate_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.PREFERENCE.toString(),
                "list_of_candidates_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.APPROVAL.toString(),
                "list_of_yes_no_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.WEIGHTED_APPROVAL.toString(),
                "list_of_integer_vals_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.CAND_OR_UNDEF.toString(),
                "one_candidate_or_zero");
        enumStringsToIds.put(ElectionTypeContainer.ElectionTypeIds.CAND_PER_SEAT.toString(),
                "candidate_per_seat");
    }

}
