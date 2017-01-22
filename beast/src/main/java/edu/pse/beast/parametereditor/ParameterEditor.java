/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.AbstractBeastFactory;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.highlevel.CheckStartStopNotifier;
//import edu.pse.beast.highlevel.CheckListener;
import edu.pse.beast.datatypes.ElectionCheckParameter;

/**
 *
 * @author Jonas
 */
public class ParameterEditor implements ParameterSource{
    private ParameterEditorWindowStarter windowStarter;
    //private MinMaxSpinValueHandler voterHandler;
    //private MinMaxSpinValueHandler candHandler;
    //private MinMaxSpinValueHandler seatHandler;
    //private TimeoutValueHandler timeoutHandler;
    //private SingleValueSpinnerHandler processHandler;
    //private UserArgumentHandler argumentHandler;
    //private CheckListener checkListener;
    public ParameterEditor() {
    }
    
    /*
    * implements CheckStartStopNotifier
    @Override
    public void addCheckListener(CheckListener l) {
        this.checkListener = l;
    }
    */
    @Override
    public ElectionCheckParameter getParameter() {
        //Integer[] voter = voterHandler.getValues();
        //...
        return null;
    }
    /*
    public startCheck() {
        
    }
    */
    /*
    public abortCheck() {
        
    }
    */
}
