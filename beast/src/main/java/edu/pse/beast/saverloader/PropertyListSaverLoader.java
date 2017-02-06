package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;


/**
 * @author NikolaiLMS
 */
public class PropertyListSaverLoader {

    public static String createPropertyItemString(PropertyItem propertyItem) {
        String postAndPreProps = "<postAndPreProps>\n" + PostAndPrePropertiesDescriptionSaverLoader.createSaveString(propertyItem.getDescription())
                + "\n</postAndPreProps>\n";
        String testStatus = "<testStatus>\n" + propertyItem.willBeTested() + "\n</testStatus>\n";
        return postAndPreProps + testStatus;
    }

    private static PropertyItem createPropertyItem(String saveString) {
        String [] split = saveString.split("\n</postAndPreProps>\n");
        PostAndPrePropertiesDescription postAndPrePropertiesDescription =
                PostAndPrePropertiesDescriptionSaverLoader.createFromSaveString(split[0].replace("<postAndPreProps>\n", ""));
        split = split[1].split("\n</testStatus>\n");
        if ((split[0].replace("<testStatus>\n", "")).equals("true")) {
            return new PropertyItem(postAndPrePropertiesDescription, true);
        } else {
            return new PropertyItem(postAndPrePropertiesDescription, false);
        }
    }

    public static String createSaveString(PLModel propertyList) {
        String created = "";
        for(PropertyItem propertyItem : propertyList.getList()) {
            created += "<propertyItem>\n" + createPropertyItemString(propertyItem) + "\n</propertyItem>\n";
        }
        return created;
    }

    public static PLModel createFromSaveString(String s) throws Exception {
        PLModel plModel = new PLModel();
        plModel.initialize();
        String[] split = s.split("\n</propertyItem>\n");
        int numberOfItems = split.length;
        for (int i = 0; i < numberOfItems; i++) {
            plModel.addDescription(createPropertyItem(split[i].replace("<propertyItem>\n", "")));
        }
        plModel.setChangedSinceSave(false);
        return plModel;
    }
}
