//package edu.pse.beast.booleanexpeditor;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import edu.pse.beast.booleanexpeditor.useractions.AddConstUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.AddMacroUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.BooleanExpEditorConst;
//import edu.pse.beast.booleanexpeditor.useractions.BooleanExpEditorMacro;
//import edu.pse.beast.booleanexpeditor.useractions.CopyUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.CutUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.LoadPropsUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.NewPropsUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.PasteUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.PresentOptionsBoolUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.RedoBoolUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.SaveAsPropsUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.SavePropsUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.ShowHelpUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.StaticErrorFindingUserAction;
//import edu.pse.beast.booleanexpeditor.useractions.UndoBoolUserAction;
//import edu.pse.beast.booleanexpeditor.view.BooleanExpEditorWindow;
//import edu.pse.beast.booleanexpeditor.view.ErrorWindow;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpCodeArea;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpCodeAreaBuilder;
//import edu.pse.beast.booleanexpeditor.booleanexpcodearea.CodeAreaFocusListener;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
//import edu.pse.beast.saverloader.FileChooser;
//import edu.pse.beast.saverloader.PreAndPostConditionsDescriptionSaverLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.ImageResourceProvider;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//import edu.pse.beast.toolbox.UnifiedNameContainer;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Builder Class to create BooleanExpEditor Object.
// * @author Nikolai Schnell
// */
//public class BooleanExpEditorBuilder {
//    /**
//     * An array of Strings encompassing the HeaderIds for the Menu.
//     */
//    private String[] menuHeaderIds = {
//            "fileMenu",
//            "editMenu",
//            "editorMenu",
//            "codeMenu",
//            "constantsMenu",
//            "macroMenu"
//    };
//
//    /**
//     * Constructor
//     * @param objectRefsForBuilder Object with references to needed
//                                   interfaces to build BooleanExpEditor
//     * @param ceditor the CElectionDescriptionEditor object
//     * @return the built BooleanExpEditor Object
//     */
//    public BooleanExpEditor
//                createBooleanExpEditorObject(ObjectRefsForBuilder objectRefsForBuilder,
//                                             CElectionDescriptionEditor ceditor) {
//        //creation of BooleanExpEditorWindow object
//        BooleanExpEditorWindow window = new BooleanExpEditorWindow();
//        window.updateStringRes(objectRefsForBuilder.getStringIF());
//
//        //creation of empty SymbolicVarListController object
//        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
//        SymbolicVarListController symbolicVarListController =
//            new SymbolicVarListController(
//                window.getSymVarList(),
//                window.getAddSymVarButton(),
//                window.getRemoveSymVarButton(),
//                objectRefsForBuilder.getStringIF(),
//                symbolicVariableList,
//                window
//            );
//
//        //creation of empty PreAndPostConditionsDescription object
//        PreAndPostConditionsDescription emptyPreAndPostConditionsDescription
//                = NewPropsUserAction.createEmptyPreAndPostConditionObject();
//
//        //creation of ErrorWindow object
//        ErrorWindow errorWindow =
//            new ErrorWindow(window.getErrorTextPane(),
//                            objectRefsForBuilder.getStringIF());
//
//        //creation of BooleanExpCodeAreas objects using the JTextPanes from the
//        // BooleanExpEditorWindow instance "window"
//        BooleanExpCodeAreaBuilder codeAreaBuilder = new BooleanExpCodeAreaBuilder();
//        BooleanExpCodeArea preConditionCodeArea = codeAreaBuilder
//                .createBooleanExpCodeAreaObject(objectRefsForBuilder,
//                                                window.getPreConditionTextPane(),
//                                                window.getPreConditionScrollPane(),
//                                                symbolicVariableList, ceditor);
//        BooleanExpCodeArea postConditionCodeArea = codeAreaBuilder
//                .createBooleanExpCodeAreaObject(objectRefsForBuilder,
//                                                window.getPostConditionTextPane(),
//                                                window.getPostConditionScrollPane(),
//                                                symbolicVariableList, ceditor);
//
//        // create ChangeHandler
//        ChangeHandler changeHandler = new ChangeHandler(preConditionCodeArea.getPane(),
//                postConditionCodeArea.getPane(), symbolicVariableList);
//
//        //create CodeAreaFocusListener
//        CodeAreaFocusListener codeAreaFocusListener =
//            new CodeAreaFocusListener(preConditionCodeArea, postConditionCodeArea);
//
//        //create FileChooser
//        FileChooser fileChooser =
//            new FileChooser(
//                objectRefsForBuilder.getStringIF().getBooleanExpEditorStringResProvider()
//                    .getBooleanExpEditorWindowStringRes(),
//                new PreAndPostConditionsDescriptionSaverLoader(),
//                null);
//
//        BooleanExpEditor editor =
//            new BooleanExpEditor(preConditionCodeArea, postConditionCodeArea,
//                                 window, symbolicVarListController, errorWindow,
//                                 changeHandler, codeAreaFocusListener,
//                                 emptyPreAndPostConditionsDescription,
//                                 codeAreaBuilder, objectRefsForBuilder, ceditor,
//                                 fileChooser);
//
//        //creation of BooleanExpEditorMenubarHandler
//        BooleanExpEditorMenubarHandler menuBarHandler =
//            new BooleanExpEditorMenubarHandler(
//                menuHeaderIds,
//                window,
//                createActionIdAndListenerListForMenuHandler(editor, objectRefsForBuilder),
//                objectRefsForBuilder.getStringIF()
//            );
//
//        //creation of BooleanExpEditorToolbarHandler
//        BooleanExpEditorToolbarHandler toolBarHandler =
//            new BooleanExpEditorToolbarHandler(
//                window,
//                ImageResourceProvider.getToolbarImages(),
//                objectRefsForBuilder.getStringIF()
//                    .getBooleanExpEditorStringResProvider().getToolbarTipStringRes(),
//                createActionIdAndListenerListForToolbarHandler(
//                    editor,
//                    objectRefsForBuilder
//                )
//            );
//
//        editor.setToolBarHandler(toolBarHandler);
//        editor.setMenuBarHandler(menuBarHandler);
//        objectRefsForBuilder.getLanguageOpts().addStringDisplayer(editor);
//        return editor;
//    }
//
//    /**
//     * Method creating the ArrayList of ArrayLists of ActionIdAndListener
//     * objects, containing necessary elements for building and handling
//     * the JMenuBar of a BooleanExpEditorWindow object.
//     * @return said list, an ArrayList<ArrayList<ActionIdAndListener>> object
//     */
//    private ArrayList<ArrayList<ActionIdAndListener>>
//    createActionIdAndListenerListForMenuHandler(BooleanExpEditor editor,
//                                                ObjectRefsForBuilder refs) {
//        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
//
//        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
//        UserAction newProps = createNewPropsUserAction(editor);
//        UserAction load = createLoadPropsUserAction(editor);
//        UserAction saveAs = createSaveAsPropsUserAction(editor);
//        UserAction save = createSavePropsUserAction(editor);
//
//        fileList.add(createFromUserAction(newProps));
//        fileList.add(createFromUserAction(load));
//        fileList.add(createFromUserAction(save));
//        fileList.add(createFromUserAction(saveAs));
//
//        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
//        UserAction undo = createUndoUserAction(editor);
//        UserAction redo = createRedoUserAction(editor);
//        UserAction cut = createCutUserAction(editor);
//        UserAction copy = createCopyUserAction(editor);
//        UserAction paste = createPasteUserAction(editor);
//
//        editList.add(createFromUserAction(undo));
//        editList.add(createFromUserAction(redo));
//        editList.add(createFromUserAction(cut));
//        editList.add(createFromUserAction(copy));
//        editList.add(createFromUserAction(paste));
//
//        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
//        UserAction presentOptions = createPresentOptionsUserAction(editor, refs);
//        editorList.add(createFromUserAction(presentOptions));
//
//        ArrayList<ActionIdAndListener> macroList = new ArrayList<>();
//
//        UserAction electMacro =
//                createMacroUserAction("elect", "ELECT", editor);
//        UserAction votesMacro =
//                createMacroUserAction("votes", "VOTES()", editor);
//        UserAction voterAtPosMacro =
//                createMacroUserAction("voterAtPos", "VOTER_AT_POS()", editor);
//        UserAction candAtPosMacro =
//                createMacroUserAction("candAtPos", "CAND_AT_POS()", editor);
//        UserAction seatAtPosMacro =
//                createMacroUserAction("seatAtPos", "SEAT_AT_POS()", editor);
//        UserAction forAllVotersMacro =
//                createMacroUserAction("forAllVoters", "FOR_ALL_VOTERS()", editor);
//        UserAction forAllCandidatesMacro =
//                createMacroUserAction("forAllCandidates", "FOR_ALL_CANDIDATES()",
//                editor);
//        UserAction forAllSeatsMacro =
//                createMacroUserAction("forAllSeats", "FOR_ALL_SEATS()", editor);
//        UserAction existsOneVoterMacro =
//                createMacroUserAction("existsOneVoter", "EXISTS_ONE_VOTER()",
//                editor);
//        UserAction existsOneCandidateMacro =
//                createMacroUserAction("existsOneCandidate", "EXISTS_ONE_CANDIDATE()", editor);
//        UserAction existsOneSeatMacro =
//                createMacroUserAction("existsOneSeat", "EXISTS_ONE_SEAT()", editor);
//        UserAction sumVotesForCandidateMacro =
//                createMacroUserAction("voteSumForCandidate", "VOTE_SUM_FOR_CANDIDATE()", editor);
//        UserAction sumVotesForUniqueCandidateMacro =
//                createMacroUserAction("voteSumForUniqueCandidate",
//                                      "VOTE_SUM_FOR_UNIQUE_CANDIDATE()", editor);
//
//        macroList.add(createFromUserAction(electMacro));
//        macroList.add(createFromUserAction(votesMacro));
//        macroList.add(createFromUserAction(voterAtPosMacro));
//        macroList.add(createFromUserAction(candAtPosMacro));
//        macroList.add(createFromUserAction(seatAtPosMacro));
//        macroList.add(createFromUserAction(forAllVotersMacro));
//        macroList.add(createFromUserAction(forAllCandidatesMacro));
//        macroList.add(createFromUserAction(forAllSeatsMacro));
//        macroList.add(createFromUserAction(existsOneVoterMacro));
//        macroList.add(createFromUserAction(existsOneCandidateMacro));
//        macroList.add(createFromUserAction(existsOneSeatMacro));
//        macroList.add(createFromUserAction(sumVotesForCandidateMacro));
//        macroList.add(createFromUserAction(sumVotesForUniqueCandidateMacro));
//
//        ArrayList<ActionIdAndListener> constantsList = new ArrayList<>();
//        UserAction votersConstant =
//            createConstantUserAction("Voters",
//                                     UnifiedNameContainer.getVoterKey(),
//                                     editor);
//        UserAction candidatesConstant =
//            createConstantUserAction("Candidates",
//                                     UnifiedNameContainer.getCandidateKey(),
//                                     editor);
//        UserAction seatsConstant =
//            createConstantUserAction("Seats",
//                                     UnifiedNameContainer.getSeatsKey(),
//                                     editor);
//        constantsList.add(createFromUserAction(votersConstant));
//        constantsList.add(createFromUserAction(candidatesConstant));
//        constantsList.add(createFromUserAction(seatsConstant));
//
//        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
//        UserAction staticCodeAnalysis = createStaticCheckUserAction(editor);
//        codeList.add(createFromUserAction(staticCodeAnalysis));
//
//        created.add(fileList);
//        created.add(editList);
//        created.add(editorList);
//        created.add(codeList);
//        created.add(constantsList);
//        created.add(macroList);
//        return created;
//    }
//
//    /**
//     * Method creating the an Array of ActionIdAndListener objects necessary
//     * for building and handling the JToolBar of a BooleanExpEditorWindow
//     * object.
//     * @return said list, a ActionIdAndListener[] object
//     */
//    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(
//            BooleanExpEditor editor, ObjectRefsForBuilder refs) {
//        ActionIdAndListener[] created = new ActionIdAndListener[10];
//
//        UserAction newProps = createNewPropsUserAction(editor);
//        UserAction undo = createUndoUserAction(editor);
//        UserAction redo = createRedoUserAction(editor);
//        UserAction saveAs = createSaveAsPropsUserAction(editor);
//        UserAction save = createSavePropsUserAction(editor);
//        UserAction load = createLoadPropsUserAction(editor);
//        UserAction copy = createCopyUserAction(editor);
//        UserAction cut = createCutUserAction(editor);
//        UserAction paste = createPasteUserAction(editor);
//        ShowHelpUserAction showHelp = new ShowHelpUserAction();
//        refs.getLanguageOpts().addStringDisplayer(showHelp);
//
//        created[0] = createFromUserAction(newProps);
//        created[1] = createFromUserAction(undo);
//        created[2] = createFromUserAction(redo);
//        created[3] = createFromUserAction(load);
//        created[4] = createFromUserAction(save);
//        created[5] = createFromUserAction(saveAs);
//        created[6] = createFromUserAction(copy);
//        created[7] = createFromUserAction(cut);
//        created[8] = createFromUserAction(paste);
//        created[9] = createFromUserAction(showHelp);
//
//        editor.addUserAction('n', newProps);
//        editor.addUserAction('o', load);
//        editor.addUserAction('s', save);
//        editor.addUserAction('x', cut);
//        editor.addUserAction('c', copy);
//        editor.addUserAction('v', paste);
//        return created;
//    }
//
//    //methods for creating UserActions in "file"-Menu
//    private NewPropsUserAction createNewPropsUserAction(BooleanExpEditor booleanExpEditor) {
//        return new NewPropsUserAction(booleanExpEditor);
//    }
//    private SavePropsUserAction createSavePropsUserAction(BooleanExpEditor booleanExpEditor) {
//        return new SavePropsUserAction(booleanExpEditor);
//    }
//    private SaveAsPropsUserAction createSaveAsPropsUserAction(BooleanExpEditor booleanExpEditor) {
//        return new SaveAsPropsUserAction(booleanExpEditor);
//    }
//    private LoadPropsUserAction createLoadPropsUserAction(BooleanExpEditor booleanExpEditor) {
//        return new LoadPropsUserAction(booleanExpEditor);
//    }
//
//    //methods for creating UserActions in "edit"-Menu
//    private UndoBoolUserAction createUndoUserAction(BooleanExpEditor editor) {
//        return new UndoBoolUserAction(editor);
//    }
//    private RedoBoolUserAction createRedoUserAction(BooleanExpEditor editor) {
//        return new RedoBoolUserAction(editor);
//    }
//    private CopyUserAction createCopyUserAction(BooleanExpEditor editor) {
//        return new CopyUserAction(editor);
//    }
//    private CutUserAction createCutUserAction(BooleanExpEditor editor) {
//        return new CutUserAction(editor);
//    }
//    private PasteUserAction createPasteUserAction(BooleanExpEditor editor) {
//        return new PasteUserAction(editor);
//    }
//
//    //methods for creating UserAction in "editor"-Menu
//    private PresentOptionsBoolUserAction
//                createPresentOptionsUserAction(BooleanExpEditor editor,
//                                               ObjectRefsForBuilder refs) {
//        return new PresentOptionsBoolUserAction(
//                refs.getOptionIF().getBooleanExpEditorOptions(editor, refs),
//                refs.getOptionIF().getOptionPresenter(refs));
//    }
//
//    //methods for creating UserActions in "constants"-Menu
//    private AddConstUserAction createConstantUserAction(String id,
//                                                        String key,
//                                                        BooleanExpEditor editor) {
//        return new AddConstUserAction(id, new BooleanExpEditorConst(key), editor);
//    }
//
//    //methods for creating UserActions in "macro"-Menu
//    private AddMacroUserAction createMacroUserAction(String id,
//                                                     String macro,
//                                                     BooleanExpEditor editor) {
//        return new AddMacroUserAction(id, new BooleanExpEditorMacro(macro), editor);
//    }
//
//    //methods for creating UserAction in "code"-Menu
//    private StaticErrorFindingUserAction createStaticCheckUserAction(BooleanExpEditor editor) {
//        return new StaticErrorFindingUserAction(editor);
//    }
//
//    /**
//     * Method that creates a ActionIdAndListener object given just
//     * a UserAction by creating an ActionListener that
//     * calls the UserActions perform method when triggered.
//     * @param userAction the UserAction object
//     * @return the created ActionIdAndListener object
//     */
//    private ActionIdAndListener createFromUserAction(UserAction userAction) {
//        return new ActionIdAndListener(userAction.getId(), new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                userAction.perform();
//            }
//        });
//    }
//}