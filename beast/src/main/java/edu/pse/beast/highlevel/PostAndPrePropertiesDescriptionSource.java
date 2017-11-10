package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

import java.util.List;

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
    List<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptionsCheck();
    
    /**
     * Provides access to the description of post and preproperties for the
     * election for which the margin computation is wished.
     * @return PostAndPrePropertiesDescription
     */
    List<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptionsMargin();
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

    /**
     * gives all properties for which a check (AND OR) a margin computation are requested
     * @return
     */
	List<PropertyAndMarginBool> getPostAndPrePropertiesDescriptionsCheckAndMargin();
}
