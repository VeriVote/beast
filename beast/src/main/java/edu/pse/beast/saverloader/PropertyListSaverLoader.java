package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;


/**
 * @author NikolaiLMS
 */
public class PropertyListSaverLoader implements SaverLoader{
    private PostAndPrePropertiesDescriptionSaverLoader postAndPrePropertiesDescriptionSaverLoader;

    /**
     * Constructor
     */
    public PropertyListSaverLoader() {
        this.postAndPrePropertiesDescriptionSaverLoader = new PostAndPrePropertiesDescriptionSaverLoader();
    }

    public String createPropertyItemString(PropertyItem propertyItem) throws Exception {
        String postAndPreProps = "<postAndPreProps>\n" +
                postAndPrePropertiesDescriptionSaverLoader.createSaveString(propertyItem.getDescription())
                + "\n</postAndPreProps>\n";
        String testStatus = "<testStatus>\n" + propertyItem.willBeTested() + "\n</testStatus>\n";
        return postAndPreProps + testStatus;
    }

    private PropertyItem createPropertyItem(String saveString) throws Exception{
        String [] split = saveString.split("\n</postAndPreProps>\n");
        PostAndPrePropertiesDescription postAndPrePropertiesDescription =
                ((PostAndPrePropertiesDescription) postAndPrePropertiesDescriptionSaverLoader.
                        createFromSaveString(split[0].replace("<postAndPreProps>\n", "")));
        split = split[1].split("\n</testStatus>\n");
        if ((split[0].replace("<testStatus>\n", "")).equals("true")) {
            return new PropertyItem(postAndPrePropertiesDescription, true);
        } else {
            return new PropertyItem(postAndPrePropertiesDescription, false);
        }
    }

    public String createSaveString(Object propertyList) throws Exception{
        String created = "";
        for(PropertyItem propertyItem : ((PLModel )propertyList).getPropertyList()) {
            created += "<propertyItem>\n" + createPropertyItemString(propertyItem) + "\n</propertyItem>\n";
        }
        return created;
    }

    public Object createFromSaveString(String s) throws Exception {
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
