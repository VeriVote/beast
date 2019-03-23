package edu.pse.beast.highlevel;

import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

/**
 * The PreAndPostConditionsDescriptionSource provides access to the description
 * of pre- and postconditions for the election check.
 *
 * @author Jonas
 */
public interface PreAndPostConditionsDescriptionSource {

    /**
     * Provides access to the description of pre- and postconditions for the
     * election check.
     *
     * @return PreAndPostConditionsDescription
     */
    List<PreAndPostConditionsDescription> getPreAndPostConditionsDescriptionsCheck();

    /**
     * Provides access to the description of pre- and postconditions for the
     * election for which the margin computation is wished.
     *
     * @return PreAndPostConditionsDescription
     */
    List<PreAndPostConditionsDescription> getPreAndPostConditionsDescriptionsMargin();

    /**
     * Returns true if all the PreAndPostConditionsDescriptions are correct.
     *
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

    List<PropertyAndMarginBool> getPreAndPostPropertiesDescriptionsCheckAndMargin();

//  /**
//   * references result objects together, if they belong to the same property
//   * @param results
//   * @return
//   */
//  public List<Integer> referenceResult(List<Result> results);
//    
}