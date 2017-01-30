/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.CheckListener;
import edu.pse.beast.highlevel.MainNotifier;
import edu.pse.beast.highlevel.ProjectSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.awt.event.ActionListener;

/**
 *
 * @author Jonas
 */
public class ParameterEditor implements ParameterSource, MainNotifier, ProjectSource {
    private final ParameterEditorWindowStarter windowStarter;
    private final CElectionDescriptionEditor cElectionDescriptionEditor; 
    private final PropertyList propertyList;
    private final ArrayList<CheckListener> checkListener = new ArrayList<>();
    private final ArrayList<ActionListener> closeListener = new ArrayList<>();
    private MinMaxSpinValueHandler voterHandler;
    private MinMaxSpinValueHandler candHandler;
    private MinMaxSpinValueHandler seatHandler;
    private TimeoutValueHandler timeoutHandler;
    private SingleValueSpinnerHandler processHandler;
    private ArgumentHandler argumentHandler;
    
    public ParameterEditor(
            CElectionDescriptionEditor cElectionDescriptionEditor, 
            PropertyList propertyList) {
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.propertyList = propertyList;
        windowStarter = new ParameterEditorWindowStarter();
        windowStarter.start();
        ParameterEditorWindow win = windowStarter.getParameterEditorWindow();
        voterHandler = new MinMaxSpinValueHandler(win.getVoterMin(), win.getVoterMax());
        win.getVoterMin().addChangeListener(voterHandler);
        win.getVoterMax().addChangeListener(voterHandler);
        candHandler = new MinMaxSpinValueHandler(win.getCandMin(), win.getCandMax());
        win.getCandMin().addChangeListener(candHandler);
        win.getCandMax().addChangeListener(candHandler);
        seatHandler = new MinMaxSpinValueHandler(win.getSeatMin(), win.getSeatMax());
        win.getSeatMin().addChangeListener(seatHandler);
        win.getSeatMax().addChangeListener(seatHandler);
        timeoutHandler = new TimeoutValueHandler(win.getTimeoutNum(), win.getTimeoutUnit());
        win.getTimeoutNum().addChangeListener(timeoutHandler);
        //win.getTimeoutUnit().addActionListener(timeoutHandler); //TODO:Find solution to ActionListener/ChangeListener requirements
        processHandler = new SingleValueSpinnerHandler(win.getAmountProcessesSpinner());
        win.getAmountProcessesSpinner().addChangeListener(processHandler);
        argumentHandler = new ArgumentHandler(win.getAdvancedWindow().getInputField(), win.getAdvancedWindow().getOkButton());
        //win.getAdvancedWindow().getInputField().addActionListener(argumentHandler); //TODO:Find solution to ActionListener requirements
    }
    
    
    @Override
    public ElectionCheckParameter getParameter() {
        List<Integer> voter = voterHandler.getValues();
        List<Integer> cand = candHandler.getValues();
        List<Integer> seat = seatHandler.getValues();
        TimeOut timeout = timeoutHandler.getTimeout();
        Integer processes = processHandler.getValue();
        String argument = argumentHandler.getArgument();
        ElectionCheckParameter param = new ElectionCheckParameter(voter, cand, seat, timeout, processes, argument);
        return param;
    }
    public void startCheck() {
        Iterator<CheckListener> iterator = checkListener.iterator();
        while(iterator.hasNext()) {
            iterator.next().startCheck();
        }
    }
    public void abortCheck() {
        Iterator<CheckListener> iterator = checkListener.iterator();
        while(iterator.hasNext()) {
            iterator.next().stopCheck();
        }
    }

    @Override
    public void addCheckListener(CheckListener l) {
        checkListener.add(l);
    }
    @Override
    public void addCloseAllListener(ActionListener l) {
        closeListener.add(l);
    }

    @Override
    public boolean isCorrect() {
        return true;
    }

    @Override
    public void stopReacting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resumeReacting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Project loadProject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveProject(Project toBeSaved) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
