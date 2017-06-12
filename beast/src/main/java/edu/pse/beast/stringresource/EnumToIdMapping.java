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

    /**
     * used to get the string for the input type
     * @param id the input type id
     * @return the id String that was mapped to this ID
     */
    public static String getIDInFile(ElectionTypeContainer.ElectionInputTypeIds id) {
        return getIdStringForEnumString(id.toString());
    }
    
    /**
     * used to get the string for the output type
     * @param id the input type id
     * @return the id String that was mapped to this ID
     */
    public static String getIDInFile(ElectionTypeContainer.ElectionOutputTypeIds id) {
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
        enumStringsToIds.put(ElectionTypeContainer.ElectionInputTypeIds.SINGLE_CHOICE.toString(),
                "one_candidate_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE.toString(),
                "list_of_candidates_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionInputTypeIds.APPROVAL.toString(),
                "list_of_yes_no_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionInputTypeIds.WEIGHTED_APPROVAL.toString(),
                "list_of_integer_vals_per_voter");
        enumStringsToIds.put(ElectionTypeContainer.ElectionOutputTypeIds.CAND_OR_UNDEF.toString(),
                "one_candidate_or_zero");
        enumStringsToIds.put(ElectionTypeContainer.ElectionOutputTypeIds.CAND_PER_SEAT.toString(),
                "candidate_per_seat");
    }
    
    public enum ElectionInputTypeIds {
        SINGLE_CHOICE,
        PREFERENCE,
        APPROVAL,
        WEIGHTED_APPROVAL,
    }
    
    public enum ElectionOutputTypeIds {
        CAND_OR_UNDEF,
        CAND_PER_SEAT
    }

}
