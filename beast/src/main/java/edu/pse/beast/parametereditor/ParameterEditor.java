/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.highlevel.CheckStartStopNotifier;
//import edu.pse.beast.highlevel.CheckListener;
import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.highlevel.CheckListener;
import edu.pse.beast.highlevel.CheckStartStopNotifier;
import java.util.ArrayList;
import edu.pse.beast.highlevel.AbstractCentralObjectProvider;

/**
 *
 * @author Jonas
 */
public class ParameterEditor implements ParameterSource, CheckStartStopNotifier {
    private ParameterEditorWindowStarter windowStarter;
    private CElectionDescriptionEditor cElectionDescriptionEditor; 
    private PropertyList propertyList;
    private ArrayList<CheckListener> listener = new ArrayList<>();
    //private MinMaxSpinValueHandler voterHandler;
    //private MinMaxSpinValueHandler candHandler;
    //private MinMaxSpinValueHandler seatHandler;
    //private TimeoutValueHandler timeoutHandler;
    //private SingleValueSpinnerHandler processHandler;
    //private UserArgumentHandler argumentHandler;
    //private CheckListener checkListener;
    public ParameterEditor(
            CElectionDescriptionEditor cElectionDescriptionEditor, 
            PropertyList propertyList) {
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.propertyList = propertyList;
        windowStarter = new ParameterEditorWindowStarter();
        windowStarter.start();
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

    @Override
    public void addCheckListener(CheckListener l) {
        listener.add(l);
    }
}
