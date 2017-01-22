/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.AbstractBeastFactory;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.highlevel.CheckStartStopNotifier;
//import edu.pse.beast.highlevel.CheckListener;
//import edu.pse.beast.datatypes.ElectionCheckParameter;

/**
 *
 * @author Jonas
 */
public class ParameterEditor extends BEASTCommunicator{
    private ParameterEditorWindowStarter windowStarter;
    private AbstractBeastFactory factory;
    private CElectionDescriptionEditor editor;
    private PropertyList propertyList;
    //private PropertyChecker checker;
    //private MinMaxSpinValueHandler voterHandler;
    //private MinMaxSpinValueHandler candHandler;
    //private MinMaxSpinValueHandler seatHandler;
    //private TimeoutValueHandler timeoutHandler;
    //private SingleValueSpinnerHandler processHandler;
    //private UserArgumentHandler argumentHandler;
    //private CheckListener checkListener;
    public ParameterEditor(AbstractBeastFactory fac) {
        super(fac);
    }
    /*
    * implements CheckStartStopNotifier
    @Override
    public void addCheckListener(CheckListener l) {
        this.checkListener = l;
    }
    */
    /*
    public ElectionCheckParameter getParameter() {
        Integer[] voter = voterHandler.getValues();
        //...
    }
    */
    /*
    public startCheck() {
        
    }
    */
    /*
    public abortCheck() {
        
    }
    */
    @Override
    protected void beforeChecking() {
        
    }
    @Override
    protected void afterChecking() {
        
    }
}
