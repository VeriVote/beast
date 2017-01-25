
package edu.pse.beast.highlevel;
import java.util.ArrayList;
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
    ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions(); //TODO: Return type array or not?
    /**
     * Returns true if all the PostAndPrePropertiesDescriptions are correct.
     * @return correctness
     */
    boolean isCorrect();
}
