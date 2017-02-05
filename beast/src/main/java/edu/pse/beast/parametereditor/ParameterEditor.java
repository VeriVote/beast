
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
import edu.pse.beast.toolbox.ToolbarHandler;
import edu.pse.beast.toolbox.MenuBarHandler;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;

/**
 * The ParameterEditor is the central class that coordinates everything associated
 * with the ParameterEditorWindow and the communication with high level.
 * @author Jonas
 */
public class ParameterEditor implements ParameterSource, MainNotifier, ProjectSource {

    private final ParameterEditorWindow window;
    private final CElectionDescriptionEditor cElectionDescriptionEditor;
    private final PropertyList propertyList;
    private CheckListener checkListener;
    private final ArrayList<ActionListener> closeListener = new ArrayList<>();
    private final MinMaxSpinValueHandler voterHandler;
    private final MinMaxSpinValueHandler candHandler;
    private final MinMaxSpinValueHandler seatHandler;
    private final TimeoutValueHandler timeoutHandler;
    private final SingleValueSpinnerHandler processHandler;
    private final ArgumentHandler argumentHandler;
    private ToolbarHandler toolbarHandler;
    private MenuBarHandler menuBarHandler;
    private boolean reacts;
    /**
     * Constructor which also links the handlers to the GUI elements
     * @param cElectionDescriptionEditor CElectionDescriptionEditor
     * @param propertyList PropertyList
     * @param window GUI window
     */
    public ParameterEditor(
            CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, ParameterEditorWindow window) {
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.propertyList = propertyList;
        this.window = window;
        voterHandler = new MinMaxSpinValueHandler(window.getVoterMin(), window.getVoterMax());
        window.getVoterMin().addChangeListener(voterHandler);
        window.getVoterMax().addChangeListener(voterHandler);
        candHandler = new MinMaxSpinValueHandler(window.getCandMin(), window.getCandMax());
        window.getCandMin().addChangeListener(candHandler);
        window.getCandMax().addChangeListener(candHandler);
        seatHandler = new MinMaxSpinValueHandler(window.getSeatMin(), window.getSeatMax());
        window.getSeatMin().addChangeListener(seatHandler);
        window.getSeatMax().addChangeListener(seatHandler);
        timeoutHandler = new TimeoutValueHandler(window.getTimeoutNum(), window.getTimeoutUnit());
        window.getTimeoutNum().addChangeListener(timeoutHandler);
        window.getTimeoutUnit().addActionListener(timeoutHandler);
        processHandler = new SingleValueSpinnerHandler(window.getAmountProcessesSpinner());
        window.getAmountProcessesSpinner().addChangeListener(processHandler);
        argumentHandler = new ArgumentHandler(window.getAdvancedWindow().getInputField(),
                window.getAdvancedWindow().getOkButton());
        setReacts(true);
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
    /**
     * Starts check of the election by notifying the CheckListener
     */
    public void startCheck() {
        checkListener.startCheck();
    }
    /**
     * Stops check of the election by notifying the CheckListener
     */
    public void abortCheck() {
        checkListener.stopCheck();
    }
    

    @Override
    public void addCheckListener(CheckListener checkListenerObject) {
        this.checkListener = checkListenerObject;
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
        setReacts(false);
    }

    @Override
    public void resumeReacting() {
        setReacts(true);
    }

    @Override
    public Project loadProject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveProject(Project toBeSaved) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Setter for the ToolbarHandler
     * @param toolbarHandler new ToolbarHandler
     */
    void setToolbarHandler(ToolbarHandler toolbarHandler) {
        this.toolbarHandler = toolbarHandler;
    }
    /**
     * Setter for the MenuBarHandler
     * @param menuBarHandler new MenuBarHandler
     */
    void setMenuBarHandler(MenuBarHandler menuBarHandler) {
        this.menuBarHandler = menuBarHandler;
    }
    /**
     * Toggles whether the chosen minimum and maximum react to user input
     * (to not interrupt checks)
     * @param reacts whether they react
     */
    public void setReacts(boolean reacts) {
        this.reacts = reacts;
        window.setReacts(reacts);
        voterHandler.setReacts(reacts);
        candHandler.setReacts(reacts);
        seatHandler.setReacts(reacts);
        timeoutHandler.setReacts(reacts);
        processHandler.setReacts(reacts);
    }
    /**
     * Getter for whether the ParameterEditor reacts to user input (except to
     * stop checks)
     * @return whether it reacts
     */
    public boolean getReacts() {
        return reacts;
    }
}
