package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;


/**
 * Implements SaverLoader methods for creating saveStrings from PropertyList objects and vice versa.
 * @author NikolaiLMS
 */
public class PropertyListSaverLoader implements SaverLoader {
    private PostAndPrePropertiesDescriptionSaverLoader postAndPrePropertiesDescriptionSaverLoader;

    /**
     * Constructor
     * Initializes PostAndPrePropertiesDescriptionSaverLoader object.
     */
    public PropertyListSaverLoader() {
        this.postAndPrePropertiesDescriptionSaverLoader = new PostAndPrePropertiesDescriptionSaverLoader();
    }

    @Override
    public String createSaveString(Object propertyList) {
        String name = "<propertyListName>\n"
                + ((PLModel) propertyList).getName()
                + "\n</propertyListName>\n";
        String propItems = "";
        for (PropertyItem propertyItem : ((PLModel) propertyList).getPropertyList()) {
            propItems += "<propertyItem>\n" + createPropertyItemString(propertyItem) + "\n</propertyItem>\n";
        }
        return name + propItems;
    }

    @Override
    public Object createFromSaveString(String s) throws ArrayIndexOutOfBoundsException {
        String [] split = s.split("\n</propertyListName>\n");
        String name = split[0].replace("<propertyListName>\n", "");
        PLModel plModel = new PLModel();
        plModel.initialize();
        plModel.setNewName(name);
        if (split.length > 1) {
            split = split[1].split("\n</propertyItem>\n");
            int numberOfItems = split.length;
            for (int i = 0; i < numberOfItems; i++) {
                plModel.addDescription(createPropertyItem(split[i].replace("<propertyItem>\n", "")));
            }
        }
        return plModel;
    }


    private String createPropertyItemString(PropertyItem propertyItem) {
        String postAndPreProps = "<postAndPreProps>\n"
                + postAndPrePropertiesDescriptionSaverLoader.createSaveString(propertyItem.getDescription())
                + "\n</postAndPreProps>\n";
        String testStatus = "<testStatus>\n" + propertyItem.getTestStatus() + "\n</testStatus>\n";
        String marginStatus = "<marginStatus>\n" + propertyItem.getMarginStatus() + "\n</marginStatus>\n";
        return postAndPreProps + testStatus + marginStatus;
    }

    private PropertyItem createPropertyItem(String saveString) throws ArrayIndexOutOfBoundsException {
        String [] split = saveString.split("\n</postAndPreProps>\n");
        PostAndPrePropertiesDescription postAndPrePropertiesDescription
                = ((PostAndPrePropertiesDescription) postAndPrePropertiesDescriptionSaverLoader.
                        createFromSaveString(split[0].replace("<postAndPreProps>\n", "")));
        split = split[1].split("\n</testStatus>\n");
        
        boolean willBeTested = (split[0].replace("<testStatus>\n", "")).equals("true");
        
        split = split[1].split("\n</marginStatus>\n");
        
        boolean willBeMargined = (split[0].replace("<marginStatus>\n", "")).equals("true");
        
        return new PropertyItem(postAndPrePropertiesDescription, willBeTested, willBeMargined);
    }
}
