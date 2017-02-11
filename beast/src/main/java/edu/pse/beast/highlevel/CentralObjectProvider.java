
package edu.pse.beast.highlevel;

/**
 * The CentralObjectProvider provides access to the interfaces for the packages used 
 * to run BEAST for the BEASTCommunicator.
 * @author Jonas
 */
public interface CentralObjectProvider {
    /**
     * Provides access to the ElectionDescriptionSource
     * @return ElectionDescriptionSource to which access is needed
     */
    ElectionDescriptionSource getElectionDescriptionSource();
    /**
     * Provides access to the PostAndPrePropertiesDescriptionSource
     * @return PostAndPrePropertiesDescriptionSource to which access is needed
     */
    PostAndPrePropertiesDescriptionSource getPostAndPrePropertiesSource();
    /**
     * Provides access to the ResultCheckerCommunicator
     * @return ResultCheckerCommunicator to which access is needed
     */
    ResultCheckerCommunicator getResultCheckerCommunicator();
    /**
     * Provides access to the ParameterSource
     * @return ParameterSource to which access is needed
     */
    ParameterSource getParameterSrc();
    /**
     * Provides access to the ResultPresenter
     * @return ResultPresenter to which access is needed
     */
    ResultPresenter getResultPresenter();
    /**
     * Provides access to the MainNotifier
     * @return MainNotifier to which access is needed
     */
    MainNotifier getMainNotifier();
    /**
     * Provides access to the CheckStatusDisplay
     * @return CheckStatusDisplay to which access is needed
     */
    CheckStatusDisplay getCheckStatusDisplay();
}
