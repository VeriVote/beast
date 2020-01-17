package edu.pse.beast.highlevel;

/**
 * The ResultPresenter presents results to the user.
 *
 * @author Jonas Wohnig, Justin Hecht
 */
public interface ResultPresenter {
    /**
     * Presents a result to the user.
     *
     * @param res
     *            result which should be presented
     * @param index
     *            index of the result which should be presented
     */
    void presentResult(ResultInterface res, Integer index);

    /**
     * Resets all results in the result presenter.
     */
    void resetResults();
}
