//package edu.pse.beast.stringresource;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//
///**
// * @author Holger Klein
// */
//public class EnumToIdMapping {
//    private static Map<String, String> enumStringsToIds;
//
//    private EnumToIdMapping() {}
//
//    public static String getIdStringForEnumString(String enumString) {
//        if (enumStringsToIds == null) createEnumStringsToIds();
//        return enumStringsToIds.get(enumString);
//    }
//
//    /**
//     * used to get the string for the input type
//     * @param id the input type id
//     * @return the id String that was mapped to this ID
//     */
//    public static String getIDInFile(ElectionTypeContainer.ElectionInputTypeIds id) {
//        return getIdStringForEnumString(id.toString());
//    }
//
//    /**
//     * used to get the string for the output type
//     * @param id the input type id
//     * @return the id String that was mapped to this ID
//     */
//    public static String getIDInFile(ElectionTypeContainer.ElectionOutputTypeIds id) {
//        return getIdStringForEnumString(id.toString());
//    }
//
//    public static String getEnumStringFromIdInFile(String idInFile) {
//        for (Map.Entry<String, String> e:
//                 enumStringsToIds.entrySet()) {
//            if (e.getValue().equals(idInFile))
//                return e.getKey();
//        }
//        return null;
//    }
//
//    private static void createEnumStringsToIds() {
//        enumStringsToIds = new HashMap<>();
//        createElectionTypeStringMapping();
//    }
//
//    private static void createElectionTypeStringMapping() {
//        ElectionInputTypeIds[] inTypes = ElectionTypeContainer.ElectionInputTypeIds.values();
//        for (int i = 0; i < inTypes.length; i++) {
//            enumStringsToIds.put(inTypes[i].toString(), inTypes[i].toString());
//        }
//
//        ElectionOutputTypeIds[] outTypes = ElectionTypeContainer.ElectionOutputTypeIds.values();
//        for (int i = 0; i < outTypes.length; i++) {
//            enumStringsToIds.put(outTypes[i].toString(), outTypes[i].toString());
//        }
//    }
//}