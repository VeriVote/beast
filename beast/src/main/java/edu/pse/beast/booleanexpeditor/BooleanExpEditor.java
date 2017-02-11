/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.View.BooleanExpEditorWindow;
import edu.pse.beast.booleanexpeditor.View.BooleanExpEditorWindowStarter;
import edu.pse.beast.booleanexpeditor.View.ErrorWindow;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.CodeAreaFocusListener;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;

import java.util.ArrayList;

/**
 * The main class of this package that serves as an interface to the outside.
 * Main "Controller" in an MVC-Pattern.
 * @author NikolaiLMS
 */
public class BooleanExpEditor implements DisplaysStringsToUser{
    private final BooleanExpEditorWindow window;
    private final SymbolicVarListController symbolicVarListController;
    private final BooleanExpEditorWindowStarter windowStarter;
    private final ErrorWindow errorWindow;
    private PostAndPrePropertiesDescription currentlyLoadedPostAndPreProp;
    private BooleanExpEditorMenubarHandler menuBarHandler;
    private BooleanExpEditorToolbarHandler toolBarHandler;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;
    private final ChangeHandler changeHandler;
    private final CodeAreaFocusListener codeAreaFocusListener;
    private final BooleanExpCodeAreaBuilder codeAreaBuilder;
    private final CElectionDescriptionEditor cEditor;
    private boolean loadedFromPropertyList;
    private final FileChooser fileChooser;
    private final ArrayList<UserAction> userActions = new ArrayList<>();
    private final ArrayList<Character> userActionChars = new ArrayList<>();
    private ObjectRefsForBuilder objectRefsForBuilder;


    /**
     * Setter to set the ToolBarHandler object of this class, can't be passed in constructor because the toolbarHandler
     * depends on a BooleanExpEditor.
     * @param toolBarHandler the BooleanExpEditorToolbarHandler object to be set
     */
    public void setToolBarHandler(BooleanExpEditorToolbarHandler toolBarHandler) {
        this.toolBarHandler = toolBarHandler;
    }

    /**
     * Setter to set the MenuBarHandler object of this class, can't be passed in constructor because the menuBarHandler
     * depends on a BooleanExpEditor.
     * @param menuBarHandler the BooleanExpEditorMenubarHandler object to be set
     */
    public void setMenuBarHandler(BooleanExpEditorMenubarHandler menuBarHandler) {
        this.menuBarHandler = menuBarHandler;
    }

    /**
     * Constructor
     * @param prePropCodeArea the initial BooleanExpCodeArea object for the preConditions
     * @param postPropCodeArea the initial BooleanExpCodeArea object for the postConditions
     * @param window the View this class forms the controller to
     * @param symbolicVarListController the controller for the SymbolicVarList displayed in window
     * @param errorWindow the controller for the TextPane displaying Errors in pre- and post-properties
     * @param changeHandler ChangeHandler
     * @param codeAreaFocusListener CodeAreaFocusListener
     * @param postAndPrePropertiesDescription the initial PostAndPrePropertiesDescription object
     * @param codeAreaBuilder the BuilderClass for building BooleanExpCodeAreas
     * @param objectRefsForBuilder the ObjectRefsForBuilder object, used for creating new BooleanExpCodeAreas
     * @param cEditor the CElectionDescriptionEditor object, used for error finding based on the currently loaded
     *                ElectionDescription and its inputs/outputs
     * @param fileChooser the FileChooser object used for saving and loading files
     */
    BooleanExpEditor(BooleanExpCodeArea prePropCodeArea,
                     BooleanExpCodeArea postPropCodeArea,
                     BooleanExpEditorWindow window,
                     SymbolicVarListController symbolicVarListController,
                     ErrorWindow errorWindow,
                     ChangeHandler changeHandler,
                     CodeAreaFocusListener codeAreaFocusListener,
                     PostAndPrePropertiesDescription postAndPrePropertiesDescription,
                     BooleanExpCodeAreaBuilder codeAreaBuilder,
                     ObjectRefsForBuilder objectRefsForBuilder,
                     CElectionDescriptionEditor cEditor,
                     FileChooser fileChooser) {
        this.window = window;
        this.objectRefsForBuilder = objectRefsForBuilder;
        this.errorWindow = errorWindow;
        this.currentlyLoadedPostAndPreProp = postAndPrePropertiesDescription;
        this.symbolicVarListController = symbolicVarListController;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
        this.changeHandler = changeHandler;
        this.codeAreaBuilder = codeAreaBuilder;
        this.cEditor = cEditor;
        this.fileChooser = fileChooser;
        prePropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        postPropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        this.codeAreaFocusListener = codeAreaFocusListener;
        letUserEditPostAndPreProperties(postAndPrePropertiesDescription, false);
        windowStarter = new BooleanExpEditorWindowStarter(window);
    }

    /**
     * Executes the showWindow function in windowStarter.
     */
    public void showWindow() {
        windowStarter.showWindow();
    }

    /**
     * Fetches errors from the pre- and postProp TextPanes in form of CodeError ArrayLists.
     * @return true if no errors were found.
     */
    public boolean findErrorsAndDisplayThem() {
        ArrayList<CodeError> prePropErrors = prePropCodeArea.getErrorCtrl().getErrorFinderList().getErrors();
        ArrayList<CodeError> postPropErrors = postPropCodeArea.getErrorCtrl().getErrorFinderList().getErrors();
        errorWindow.displayErrors(prePropErrors, postPropErrors,
                ((BooleanExpErrorDisplayer) prePropCodeArea.getErrorCtrl().getDisplayer()));
        updatePostAndPrePropObject();
        return (prePropErrors.size() == 0 && postPropErrors.size() == 0);
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        window.updateStringRes(stringLoaderInterface);
        symbolicVarListController.updateStringRes(stringLoaderInterface);
        menuBarHandler.updateStringRes(stringLoaderInterface);
        toolBarHandler.updateStringRes(stringLoaderInterface);
        fileChooser.updateStringRessourceLoader(stringLoaderInterface.getBooleanExpEditorStringResProvider()
                .getBooleanExpEditorWindowStringRes());
    }

    /**
     * Adds
     * @param c the shortcut character
     * @param userAction
     */
    void addUserAction(char c, UserAction userAction) {
        userActions.add(userAction);
        userActionChars.add(c);
    }

    /**
     * Loads and displays the given PostAndPrePropertiesDescription Object.
     * @param postAndPrePropertiesDescription The PostAndPrePropertiesDescription Object
     * @param loadedFromPropertyList boolean that is used to know whether the object is loaded from the propertylist,
     *                               or from inside the editor, used for SaveBeforeChange handling
     * @return a boolean stating the success of the loading
     */
    public boolean letUserEditPostAndPreProperties(PostAndPrePropertiesDescription postAndPrePropertiesDescription,
                                                   boolean loadedFromPropertyList) {
        if (!this.loadedFromPropertyList && changeHandler.hasChanged()) {
            if (fileChooser.openSaveChangesDialog(getCurrentlyLoadedPostAndPreProp())) {
                loadNewProperties(postAndPrePropertiesDescription);
                this.loadedFromPropertyList = loadedFromPropertyList;
                return true;
            } else {
                return false;
            }
        } else {
            loadNewProperties(postAndPrePropertiesDescription);
            this.loadedFromPropertyList = loadedFromPropertyList;
            return true;
        }
    }

    /**
     * Loads the given PostAndPreProperties object into the BooleanExpEditor
     * @param postAndPrePropertiesDescription the PostAndPreProperties object that should be loaded in the editor
     */
    public void loadNewProperties(PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        updatePostAndPrePropObject();
        System.out.println("Loading symbolic variable list");
        currentlyLoadedPostAndPreProp = postAndPrePropertiesDescription;
        symbolicVarListController.setSymbVarList(postAndPrePropertiesDescription.getSymbolicVariableList());
        window.setNewTextpanes();
        changeHandler.addNewTextPanes(window.getPrePropTextPane(), window.getPostPropTextPane());

        cEditor.removeListener(prePropCodeArea.getVariableErrorFinder());
        cEditor.removeListener(postPropCodeArea.getVariableErrorFinder());

        symbolicVarListController.getSymbolicVariableList().removeListener(prePropCodeArea.getVariableErrorFinder().getLis());
        symbolicVarListController.getSymbolicVariableList().removeListener(postPropCodeArea.getVariableErrorFinder().getLis());

        prePropCodeArea.getErrorCtrl().stopThread();
        postPropCodeArea.getErrorCtrl().stopThread();

        prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder, window.getPrePropTextPane(),
                window.getPrePropScrollPane(), symbolicVarListController.getSymbolicVariableList(), cEditor);
        postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder, window.getPostPropTextPane(),
                window.getPostPropScrollPane(), symbolicVarListController.getSymbolicVariableList(), cEditor);

        for (int i = 0; i < userActions.size(); i++) {
            UserAction get = userActions.get(i);
            char c = userActionChars.get(i);
            prePropCodeArea.linkActionToShortcut(c, get);
            postPropCodeArea.linkActionToShortcut(c, get);
        }

        cEditor.addListener(prePropCodeArea.getVariableErrorFinder());
        cEditor.addListener(postPropCodeArea.getVariableErrorFinder());

        postPropCodeArea.getVariableErrorFinder().inputChanged(cEditor.getElectionDescription().getInputType());
        postPropCodeArea.getVariableErrorFinder().outputChanged(cEditor.getElectionDescription().getOutputType());

        prePropCodeArea.getVariableErrorFinder().inputChanged(cEditor.getElectionDescription().getInputType());
        prePropCodeArea.getVariableErrorFinder().outputChanged(cEditor.getElectionDescription().getOutputType());

        prePropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        postPropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        codeAreaFocusListener.addNewCodeAreas(prePropCodeArea, postPropCodeArea);

        prePropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPrePropertiesDescription().getCode());
        postPropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPostPropertiesDescription().getCode());

        this.findErrorsAndDisplayThem();
        this.window.setWindowTitle(postAndPrePropertiesDescription.getName());
        changeHandler.updatePreValues();
        fileChooser.setHasBeenSaved(false);
    }

    public boolean isCorrect() {
        return findErrorsAndDisplayThem();
    }

    /**
     * Updates the currentlyLoadedPostAndPreProp object (model) according to the current state of the
     * BooleanExpEditorWindow (View).
     */
    public void updatePostAndPrePropObject() {
        currentlyLoadedPostAndPreProp.setPrePropertiesDescription(
                new FormalPropertiesDescription(prePropCodeArea.getPane().getText()));
        currentlyLoadedPostAndPreProp.setPostPropertiesDescription(
                new FormalPropertiesDescription(postPropCodeArea.getPane().getText()));
        currentlyLoadedPostAndPreProp.getSymVarList().setSymbolicVariableList(symbolicVarListController.getSymbolicVariableList().getSymbolicVariables());
    }

    /**
     * Getter, used by Copy, Paste, Cut, Redo and Undo UserActions, to get the last focused CodeArea.
     * @return the CodeAreaFocusListener instance of this class
     */
    public CodeAreaFocusListener getCodeAreaFocusListener() {
        return codeAreaFocusListener;
    }

    /**
     * Getter
     * @return the BooleanExpEditorWindow instance (the View) of this class
     */
    public BooleanExpEditorWindow getView() {
        return window;
    }

    /**
     * Getter, used by SaveProps/SaveAsProps/LoadProps and NewProps UserActions, to check whether the used wants to
     * save if changes have been made since last save/load.
     * @return the ChangeHandler
     */
    public ChangeHandler getChangeHandler() {
        return changeHandler;
    }

    /**
     * Getter, used by SaveProps/SaveAsProps/LoadProps and NewProps UserActions to load and save files containing
     * PostAndPrePropertiesDescription objects.
     * @return the FileChooser
     */
    public FileChooser getFileChooser(){
        return fileChooser;
    }

    /**
     * Updates the the PostAndPrePropertiesDescription object (model) of this class and
     * @return s it.
     */
    public PostAndPrePropertiesDescription getCurrentlyLoadedPostAndPreProp() {
        updatePostAndPrePropObject();
        return currentlyLoadedPostAndPreProp;
    }

    public boolean getLoadedFromPropertyList() {
        return loadedFromPropertyList;
    }
}