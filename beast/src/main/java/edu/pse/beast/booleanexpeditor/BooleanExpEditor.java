//package edu.pse.beast.booleanexpeditor;
//
//import java.util.ArrayList;
//
//import edu.pse.beast.booleanexpeditor.view.BooleanExpEditorWindow;
//import edu.pse.beast.booleanexpeditor.view.BooleanExpEditorWindowStarter;
//import edu.pse.beast.booleanexpeditor.view.ErrorWindow;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpCodeArea;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpCodeAreaBuilder;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.CodeAreaFocusListener;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorDisplayer;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.codearea.errorhandling.CodeError;
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.highlevel.DisplaysStringsToUser;
//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.saverloader.FileChooser;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * The main class of this package that serves as an interface to the outside.
// * Main "Controller" as part of the MVC-Pattern.
// * @author Nikolai Schnell
// */
//public class BooleanExpEditor implements DisplaysStringsToUser {
//    private final BooleanExpEditorWindow window;
//    private final SymbolicVarListController symbolicVarListController;
//    private final BooleanExpEditorWindowStarter windowStarter;
//    private final ErrorWindow errorWindow;
//    private PreAndPostConditionsDescription currentlyLoadedPreAndPostCondition;
//    private BooleanExpEditorMenubarHandler menuBarHandler;
//    private BooleanExpEditorToolbarHandler toolBarHandler;
//    private BooleanExpCodeArea preConditionCodeArea;
//    private BooleanExpCodeArea postConditionCodeArea;
//    private final ChangeHandler changeHandler;
//    private final CodeAreaFocusListener codeAreaFocusListener;
//    private final BooleanExpCodeAreaBuilder codeAreaBuilder;
//    private final CElectionDescriptionEditor cEditor;
//    private boolean loadedFromPropertyList;
//    private final FileChooser fileChooser;
//    private final ArrayList<UserAction> userActions = new ArrayList<>();
//    private final ArrayList<Character> userActionChars = new ArrayList<>();
//    private ObjectRefsForBuilder objectRefsForBuilder;
//    private PropertyList propertyListController;
//
//    /**
//     * Constructor
//     * @param preConditionCodeArea            the initial BooleanExpCodeArea
//     *                                        object for the preConditions
//     * @param postConditionCodeArea           the initial BooleanExpCodeArea
//     *                                        object for the postConditions
//     * @param window                          the view this class forms the
//     *                                        controller to
//     * @param symbolicVarListController       the controller for the SymbolicVarList
//     *                                        displayed in window
//     * @param errorWindow                     the controller for the TextPane displaying
//     *                                        errors in pre- and postconditions
//     * @param changeHandler                   ChangeHandler
//     * @param codeAreaFocusListener           CodeAreaFocusListener
//     * @param preAndPostConditionsDescription the initial PreAndPostConditionsDescription
//     *                                        object
//     * @param codeAreaBuilder                 the BuilderClass for building
//     *                                        BooleanExpCodeAreas
//     * @param objectRefsForBuilder            the ObjectRefsForBuilder object,
//     *                                        used for creating new BooleanExpCodeAreas
//     * @param cEditor                         the CElectionDescriptionEditor object,
//     *                                        used for error finding based on
//     *                                        the currently loaded
//     *                                        ElectionDescription and its inputs/outputs
//     * @param fileChooser the FileChooser object used for saving and loading files
//     */
//    BooleanExpEditor(BooleanExpCodeArea preConditionCodeArea,
//                     BooleanExpCodeArea postConditionCodeArea,
//                     BooleanExpEditorWindow window,
//                     SymbolicVarListController symbolicVarListController,
//                     ErrorWindow errorWindow,
//                     ChangeHandler changeHandler,
//                     CodeAreaFocusListener codeAreaFocusListener,
//                     PreAndPostConditionsDescription preAndPostConditionsDescription,
//                     BooleanExpCodeAreaBuilder codeAreaBuilder,
//                     ObjectRefsForBuilder objectRefsForBuilder,
//                     CElectionDescriptionEditor cEditor,
//                     FileChooser fileChooser) {
//        this.window = window;
//        this.objectRefsForBuilder = objectRefsForBuilder;
//        this.errorWindow = errorWindow;
//        this.currentlyLoadedPreAndPostCondition = preAndPostConditionsDescription;
//        this.symbolicVarListController = symbolicVarListController;
//        this.preConditionCodeArea = preConditionCodeArea;
//        this.postConditionCodeArea = postConditionCodeArea;
//        this.changeHandler = changeHandler;
//        this.codeAreaBuilder = codeAreaBuilder;
//        this.cEditor = cEditor;
//        this.fileChooser = fileChooser;
//        preConditionCodeArea.getPane().addFocusListener(codeAreaFocusListener);
//        postConditionCodeArea.getPane().addFocusListener(codeAreaFocusListener);
//        this.codeAreaFocusListener = codeAreaFocusListener;
//        letUserEditPreAndPostConditions(preAndPostConditionsDescription, false);
//        windowStarter = new BooleanExpEditorWindowStarter(window);
//    }
//
//    /**
//     * Setter to set the ToolBarHandler object of this class, cannot be passed in
//     * constructor because of toolbarHandler
//     * depends on a BooleanExpEditor.
//     * @param toolBarHandler the BooleanExpEditorToolbarHandler object to be set
//     */
//    public void setToolBarHandler(BooleanExpEditorToolbarHandler toolBarHandler) {
//        this.toolBarHandler = toolBarHandler;
//    }
//
//    /**
//     * Setter to set the MenuBarHandler object of this class, cannot be passed in
//     * constructor because of menuBarHandler
//     * depends on a BooleanExpEditor.
//     * @param menuBarHandler the BooleanExpEditorMenubarHandler object to be set
//     */
//    public void setMenuBarHandler(BooleanExpEditorMenubarHandler menuBarHandler) {
//        this.menuBarHandler = menuBarHandler;
//    }
//
//    /**
//     * Executes the showWindow function in windowStarter.
//     */
//    public void showWindow() {
//        windowStarter.showWindow();
//    }
//
//    /**
//     * Fetches errors from the pre- and postCondition TextPanes in form of CodeError ArrayLists.
//     * @return true if no errors were found.
//     */
//    public boolean findErrorsAndDisplayThem() {
//        ArrayList<CodeError> preConditionErrors =
//                preConditionCodeArea.getErrorCtrl().getErrorFinderList().getErrors();
//        ArrayList<CodeError> postConditionErrors =
//                postConditionCodeArea.getErrorCtrl().getErrorFinderList().getErrors();
//        errorWindow.displayErrors(preConditionErrors, postConditionErrors,
//                ((BooleanExpErrorDisplayer) preConditionCodeArea.getErrorCtrl().getDisplayer()));
//        updatePreAndPostConditionObject();
//        return (preConditionErrors.size() == 0 && postConditionErrors.size() == 0);
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
//        window.updateStringRes(stringLoaderInterface);
//        symbolicVarListController.updateStringRes(stringLoaderInterface);
//        menuBarHandler.updateStringRes(stringLoaderInterface);
//        toolBarHandler.updateStringRes(stringLoaderInterface);
//        fileChooser.updateStringRessourceLoader(
//                stringLoaderInterface.getBooleanExpEditorStringResProvider()
//                .getBooleanExpEditorWindowStringRes()
//        );
//    }
//
//    /**
//     * Adds a shortcut to the given UserAction
//     * @param c the shortcut character, used with control to trigger the UserAction
//     * @param userAction the UserAction
//     */
//    void addUserAction(char c, UserAction userAction) {
//        userActions.add(userAction);
//        userActionChars.add(c);
//        preConditionCodeArea.getUserInputHandler().getShortcutHandler().addAction(c, userAction);
//    }
//
//    /**
//     * Loads and displays the given PreAndPostConditionsDescription Object.
//     * @param preAndPostConditionsDescription the PreAndPostConditionsDescription object
//     * @param loadedFromPropertyList          boolean that is used to know whether
//     *                                        the object is loaded from the property list,
//     *                                        or from inside the editor, used for
//     *                                        SaveBeforeChange handling
//     * @return a boolean stating the success of the loading
//     */
//    public boolean letUserEditPreAndPostConditions(
//                        PreAndPostConditionsDescription preAndPostConditionsDescription,
//                        boolean loadedFromPropertyList) {
//        if (!this.loadedFromPropertyList && changeHandler.hasChanged()) {
//            if (fileChooser.openSaveChangesDialog(getCurrentlyLoadedPreAndPostCondition())) {
//                loadNewProperties(preAndPostConditionsDescription);
//                this.loadedFromPropertyList = loadedFromPropertyList;
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            loadNewProperties(preAndPostConditionsDescription);
//            this.loadedFromPropertyList = loadedFromPropertyList;
//            return true;
//        }
//    }
//
//    /**
//     * Loads the given PreAndPostConditions object into the BooleanExpEditor
//     * @param preAndPostConditionsDescription the PreAndPostConditions object
//     *                                        that should be loaded in the editor
//     */
//    public void loadNewProperties(PreAndPostConditionsDescription
//                                      preAndPostConditionsDescription) {
//        updatePreAndPostConditionObject();
//        currentlyLoadedPreAndPostCondition = preAndPostConditionsDescription;
//        symbolicVarListController.setSymbVarList(
//            preAndPostConditionsDescription.getSymbolicVariableList());
//        window.setNewTextpanes();
//        changeHandler.addNewTextPanes(window.getPreConditionTextPane(),
//                                      window.getPostConditionTextPane());
////
////        cEditor.removeListener(preConditionCodeArea.getVariableErrorFinder());
////        cEditor.removeListener(postConditionCodeArea.getVariableErrorFinder());
////
////        symbolicVarListController.getSymbolicVariableList().removeListener(
////            preConditionCodeArea.getVariableErrorFinder().getLis());
////        symbolicVarListController.getSymbolicVariableList().removeListener(
////            postConditionCodeArea.getVariableErrorFinder().getLis());
//
//        preConditionCodeArea.getErrorCtrl().stopThread();
//        postConditionCodeArea.getErrorCtrl().stopThread();
//
//        preConditionCodeArea.getAutoComplCtrl().stopThread();
//        postConditionCodeArea.getAutoComplCtrl().stopThread();
//        preConditionCodeArea =
//            codeAreaBuilder.createBooleanExpCodeAreaObject(
//                objectRefsForBuilder, window.getPreConditionTextPane(),
//                window.getPreConditionScrollPane(),
//                symbolicVarListController.getSymbolicVariableList(),
//                cEditor
//            );
//        postConditionCodeArea =
//            codeAreaBuilder.createBooleanExpCodeAreaObject(
//                objectRefsForBuilder,
//                window.getPostConditionTextPane(),
//                window.getPostConditionScrollPane(),
//                symbolicVarListController.getSymbolicVariableList(),
//                cEditor
//            );
//        for (int i = 0; i < userActions.size(); i++) {
//            UserAction get = userActions.get(i);
//            char c = userActionChars.get(i);
//            preConditionCodeArea.linkActionToShortcut(c, get);
//            postConditionCodeArea.linkActionToShortcut(c, get);
//        }
//
////        cEditor.addListener(preConditionCodeArea.getVariableErrorFinder());
////        cEditor.addListener(postConditionCodeArea.getVariableErrorFinder());
////
////        postConditionCodeArea.getVariableErrorFinder().inputChanged(
////            cEditor.getElectionDescription().getContainer().getInputType());
////        postConditionCodeArea.getVariableErrorFinder().outputChanged(
////            cEditor.getElectionDescription().getContainer().getOutputType());
////
////        preConditionCodeArea.getVariableErrorFinder().inputChanged(
////            cEditor.getElectionDescription().getContainer().getInputType());
////        preConditionCodeArea.getVariableErrorFinder().outputChanged(
////            cEditor.getElectionDescription().getContainer().getOutputType());
//
//        preConditionCodeArea.getPane().addFocusListener(codeAreaFocusListener);
//        postConditionCodeArea.getPane().addFocusListener(codeAreaFocusListener);
//        codeAreaFocusListener.addNewCodeAreas(preConditionCodeArea, postConditionCodeArea);
//
//        preConditionCodeArea.getPane().setText(
//            preAndPostConditionsDescription.getPreConditionsDescription().getCode());
//        postConditionCodeArea.getPane().setText(
//            preAndPostConditionsDescription.getPostConditionsDescription().getCode());
//
//        this.findErrorsAndDisplayThem();
//        this.window.setWindowTitle(preAndPostConditionsDescription.getName());
//        changeHandler.updatePreValues();
//        objectRefsForBuilder.getOptionIF()
//            .getBooleanExpEditorOptions(this, objectRefsForBuilder).reapply();
//    }
//
//    /**
//     * Method for checking whether the currently loaded
//     * PreAndPostConditionsDescription Object contains errors.
//     * @return true if there are no errors, false otherwise
//     */
//    public boolean isCorrect() {
//        return findErrorsAndDisplayThem();
//    }
//
//    /**
//     * Updates the currentlyLoadedPreAndPostCondition object (model)
//     * according to the current state of the BooleanExpEditorWindow (View).
//     */
//    public void updatePreAndPostConditionObject() {
//        currentlyLoadedPreAndPostCondition.setPreConditionsDescription(
//                new FormalPropertiesDescription(preConditionCodeArea.getPane().getText()));
//        currentlyLoadedPreAndPostCondition.setPostConditionsDescription(
//                new FormalPropertiesDescription(postConditionCodeArea.getPane().getText()));
//        currentlyLoadedPreAndPostCondition.getSymVarList().setSymbolicVariableList(
//                symbolicVarListController.getSymbolicVariableList().getSymbolicVariables());
//    }
//
//    /**
//     * Getter, used by Copy, Paste, Cut, Redo and Undo UserActions,
//     * to get the last focused CodeArea.
//     * @return the CodeAreaFocusListener instance of this class
//     */
//    public CodeAreaFocusListener getCodeAreaFocusListener() {
//        return codeAreaFocusListener;
//    }
//
//    /**
//     * Getter
//     * @return the BooleanExpEditorWindow instance (the View) of this class
//     */
//    public BooleanExpEditorWindow getView() {
//        return window;
//    }
//
//    /**
//     * Getter, used by SaveProps/SaveAsProps/LoadProps and NewProps
//     * UserActions, to check whether the used wants to save if changes have
//     * been made since last save/load.
//     * @return the ChangeHandler
//     */
//    public ChangeHandler getChangeHandler() {
//        return changeHandler;
//    }
//
//    /**
//     * Getter, used by SaveProps/SaveAsProps/LoadProps and NewProps
//     * UserActions to load and save files containing
//     * PreAndPostConditionsDescription objects.
//     * @return the FileChooser
//     */
//    public FileChooser getFileChooser() {
//        return fileChooser;
//    }
//
//    /**
//     * Updates the the PreAndPostConditionsDescription object (model) of this class and
//     * @return s it.
//     */
//    public PreAndPostConditionsDescription getCurrentlyLoadedPreAndPostCondition() {
//        updatePreAndPostConditionObject();
//        return currentlyLoadedPreAndPostCondition;
//    }
//
//    /**
//     * Returns loadedFromPropertyList,
//     * @return true if the
//     */
//    public boolean getLoadedFromPropertyList() {
//        return loadedFromPropertyList;
//    }
//
//    /**
//     * Getter for the PropertyListController, used when new Property
//     * is created to add it to the PropertyList.
//     * @return propertyListController
//     */
//    public PropertyList getPropertyListController() {
//        return propertyListController;
//    }
//
//    /**
//     * Setter for the PropertyListController, see above for explanation.
//     * @param propertyListController the PropertyListController
//     */
//    public void setPropertyListController(PropertyList propertyListController) {
//            this.propertyListController = propertyListController;
//    }
//
//    /**
//     * Getter for the current preConditionCodeArea
//     * @return the current preConditionCodeArea
//     */
//    public BooleanExpCodeArea getPreConditionCodeArea() {
//        return preConditionCodeArea;
//    }
//
//    /**
//     * Getter for the current postConditionCodeArea
//     * @return the current postConditionCodeArea
//     */
//    public BooleanExpCodeArea getPostConditionCodeArea() {
//        return postConditionCodeArea;
//    }
//}