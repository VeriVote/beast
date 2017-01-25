
package edu.pse.beast.highlevel;

/**
 * The CentralObjectProvider provides access to the interfaces for the packages used 
 * to run BEAST for the BEASTCommunicator.
 * @author Jonas
 */
public interface CentralObjectProvider {
    /**
     * Provides access to the ElectionDescriptionSource
     * @return ElectionDescriptionSource
     */
    ElectionDescriptionSource getElectionDescriptionSource();
    /**
     * Provides access to the PostAndPrePropertiesDescriptionSource
     * @return PostAndPrePropertiesDescriptionSource
     */
    PostAndPrePropertiesDescriptionSource getPostAndPrePropertiesSource();
    /**
     * Provides access to the ResultCheckerCommunicator
     * @return ResultCheckerCommunicator
     */
    ResultCheckerCommunicator getResultCheckerCommunicator();
    /**
     * Provides access to the ParameterSource
     * @return ParameterSource
     */
    ParameterSource getParameterSrc();
    /**
     * Provides access to the ResultPresenter
     * @return ResultPresenter
     */
    ResultPresenter getResultPresenter();
    /**
     * Provides access to the MainNotifier
     * @return MainNotifier
     */
    MainNotifier getMainNotifier();
}
