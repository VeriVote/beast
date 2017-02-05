
package edu.pse.beast.highlevel;

/**
 * The ResultPresenter presents results to the user.
 * @author Jonas & Justin
 */
public interface ResultPresenter {
    /**
     * Presents a result to the user.
     * @param res result which should be presented
     */
    void presentResult(ResultInterface res);
    
    /**
     * Resets all results in the result presenter.
     */
    void resetResults();
}
