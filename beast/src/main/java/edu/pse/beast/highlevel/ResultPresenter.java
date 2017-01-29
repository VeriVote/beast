/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.highlevel;

/**
 * The ResultPresenter presents results to the user.
 * @author Jonas
 */
public interface ResultPresenter {
    /**
     * Presents a result to the user.
     * @param res result which should be presented
     */
    void presentResult(ResultInterface res);
}
