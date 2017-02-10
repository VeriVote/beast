
package edu.pse.beast.parametereditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.parametereditor.View.ParameterEditorWindow;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.CheckListener;
import edu.pse.beast.highlevel.MainNotifier;
import edu.pse.beast.highlevel.ProjectSource;
import edu.pse.beast.saverloader.FileChooser;
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
    private final FileChooser fileChooser;
    private boolean hasChanged;
    private Project currentlyLoadedProject;
    private String version = "1.23"; //TODO: Get Version from somewhere

    /**
     * Constructor which also links the handlers to the View elements
     * @param cElectionDescriptionEditor CElectionDescriptionEditor
     * @param propertyList PropertyList where the PostAndPrePropertyDescriptions come from
     * @param window View window
     * @param fileChooser FileChooser for saving/loading
     */
    public ParameterEditor(
            CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList,
            ParameterEditorWindow window,
            FileChooser fileChooser) {
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.propertyList = propertyList;
        this.window = window;
        this.fileChooser = fileChooser;
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
        window.setVersion(version);
        setReacts(true);
        setHasChanged(false);
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
     * Setter for all parameters (needed for loading projects)
     * @param param 
     */
    public void setParameter(ElectionCheckParameter param) {
        voterHandler.setMinAndMax(param.getAmountVoters().get(0),
                param.getAmountVoters().get(param.getAmountVoters().size() - 1));
        candHandler.setMinAndMax(param.getAmountCandidates().get(0),
                param.getAmountCandidates().get(param.getAmountCandidates().size() - 1));
        seatHandler.setMinAndMax(param.getAmountSeats().get(0),
                param.getAmountSeats().get(param.getAmountSeats().size() - 1));
        timeoutHandler.setValue(param.getTimeout());
        processHandler.setValue(param.getProcesses());
        argumentHandler.setArgument(param.getArgument());
        hasChanged = false;
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
     * Returns whether the user changed any data in the ParameterEditor since the
     * last time saving.
     * @return if it hasChanged
     */
    public boolean hasChanged() {
        hasChanged = (hasChanged || voterHandler.hasChanged() || seatHandler.hasChanged()
                || candHandler.hasChanged() || argumentHandler.hasChanged()
                || timeoutHandler.hasChanged() || processHandler.hasChanged());
        return hasChanged;
    }
    /**
     * Setter for hasChanged
     * @param hasChanged whether something has changed since the last time saving.
     */
    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
        if (!hasChanged) {
            voterHandler.setHasChanged(hasChanged);
            seatHandler.setHasChanged(hasChanged);
            candHandler.setHasChanged(hasChanged);
            argumentHandler.setHasChanged(hasChanged);
            timeoutHandler.setHasChanged(hasChanged);
            processHandler.setHasChanged(hasChanged);
        }
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
     * Toggles whether the ParameterEditor reacts to user input other than stopping
     * an analysis (to not interrupt an analysis)
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
    /**
     * Getter for the FileChooser
     * @return FileChooser used by ParameterEditor
     */
    public FileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Getter for the ParameterEditorWindow
     * @return ParameterEditorWindow
     */
    public ParameterEditorWindow getView() {
        return window;
    }
    /**
     * Getter for the Project which is currently worked on by the user.
     * @return Project
     */
    public Project getCurrentlyLoadedProject() {
        if (currentlyLoadedProject == null) {
            return new Project(getParameter(), propertyList.getModel(),
                    cElectionDescriptionEditor.getElectionDescription(), "New Project");
        } else {
            return new Project(getParameter(), propertyList.getModel(),
                    cElectionDescriptionEditor.getElectionDescription(), currentlyLoadedProject.getName());
        }
    }
}
