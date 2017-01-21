
package edu.pse.beast.highlevel;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

/**
 * The PostAndPrePropertiesDescriptionSource provides access to the
 * description of post and preproperties for the election check.
 * @author Jonas
 */
public interface PostAndPrePropertiesDescriptionSource {
    /**
     * Provides access to the description of post and preproperties for the
     * election check.
     * @return PostAndPrePropertiesDescription
     */
    PostAndPrePropertiesDescription getPostAndPrePropertiesDescriptions(); //TODO: Return type array or not?
}
