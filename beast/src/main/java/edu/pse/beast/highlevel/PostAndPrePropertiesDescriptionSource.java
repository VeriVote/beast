
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
    ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions();
    /**
     * Returns true if all the PostAndPrePropertiesDescriptions are correct.
     * @return correctness
     */
    boolean isCorrect();
    /**
     * Stops reacting to user input to not interfere with running check.
     */
    void stopReacting();
    /**
     * Resumes reacting to user input after the check is over.
     */
    void resumeReacting();
}
