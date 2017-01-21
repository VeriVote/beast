/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;

/**
 *
 * @author Niels
 */
public abstract class Result implements ResultInterface{
    protected boolean readyToPresent;
    protected Result() {
        readyToPresent = false;
    }
    @Override
    public boolean readyToPresent() {
        return readyToPresent;
    } 

    @Override
    public void presentTo(ResultPresenterElement presenter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
