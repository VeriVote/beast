//package edu.pse.beast.saverloader;
//
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.propertylist.Model.PLModel;
//import edu.pse.beast.propertylist.Model.PropertyItem;
//
//
///**
// * Implements SaverLoader methods for creating saveStrings from PropertyList objects and vice versa.
// * @author NikolaiLMS
// */
//public class PropertyListSaverLoader implements SaverLoader {
//    private PreAndPostConditionsDescriptionSaverLoader preAndPostConditionsDescriptionSaverLoader;
//
//    /**
//     * Constructor
//     * Initializes PreAndPostConditionsDescriptionSaverLoader object.
//     */
//    public PropertyListSaverLoader() {
//        this.preAndPostConditionsDescriptionSaverLoader = new PreAndPostConditionsDescriptionSaverLoader();
//    }
//
//    @Override
//    public String createSaveString(Object propertyList) {
//        String name = "<propertyListName>\n"
//                + ((PLModel) propertyList).getName()
//                + "\n</propertyListName>\n";
//        String propItems = "";
//        for (PropertyItem propertyItem : ((PLModel) propertyList).getPropertyList()) {
//            propItems += "<propertyItem>\n" + createPropertyItemString(propertyItem) + "\n</propertyItem>\n";
//        }
//        return name + propItems;
//    }
//
//    @Override
//    public Object createFromSaveString(String s) throws ArrayIndexOutOfBoundsException {
//        String [] split = s.split("\n</propertyListName>\n");
//        String name = split[0].replace("<propertyListName>\n", "");
//        PLModel plModel = new PLModel();
//        plModel.initialize();
//        plModel.setNewName(name);
//        if (split.length > 1) {
//            split = split[1].split("\n</propertyItem>\n");
//            int numberOfItems = split.length;
//            for (int i = 0; i < numberOfItems; i++) {
//                plModel.addDescription(createPropertyItem(split[i].replace("<propertyItem>\n", "")));
//            }
//        }
//        return plModel;
//    }
//
//
//    private String createPropertyItemString(PropertyItem propertyItem) {
//        String preAndPostConditions = "<preAndPostConditions>\n"
//                + preAndPostConditionsDescriptionSaverLoader.createSaveString(propertyItem.getDescription())
//                + "\n</preAndPostConditions>\n";
//        String testStatus = "<testStatus>\n" + propertyItem.getTestStatus() + "\n</testStatus>\n";
//        String marginStatus = "<marginStatus>\n" + propertyItem.getMarginStatus() + "\n</marginStatus>\n";
//        return preAndPostConditions + testStatus + marginStatus;
//    }
//
//    private PropertyItem createPropertyItem(String saveString) throws ArrayIndexOutOfBoundsException {
//        String [] split = saveString.split("\n</preAndPostConditions>\n");
//        PreAndPostConditionsDescription preAndPostConditionsDescription
//                = ((PreAndPostConditionsDescription) preAndPostConditionsDescriptionSaverLoader.
//                        createFromSaveString(split[0].replace("<preAndPostConditions>\n", "")));
//        split = split[1].split("\n</testStatus>\n");
//        
//        boolean willBeTested = (split[0].replace("<testStatus>\n", "")).equals("true");
//        
//        split = split[1].split("\n</marginStatus>\n");
//        
//        boolean willBeMargined = (split[0].replace("<marginStatus>\n", "")).equals("true");
//        
//        return new PropertyItem(preAndPostConditionsDescription, willBeTested, willBeMargined);
//    }
//}
