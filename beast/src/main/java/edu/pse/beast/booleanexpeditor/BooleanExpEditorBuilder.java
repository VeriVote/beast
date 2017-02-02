package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.UserActions.*;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.CodeAreaFocusListener;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Builder Class to create BooleanExpEditor Object.
 * @author Nikolai
 */
public class BooleanExpEditorBuilder{

    private String[] menuHeadingIds = {
            "fileMenu",
            "editMenu",
            "editorMenu",
            "makroMenu",
            "constantsMenu",
            "codeMenu"
    };

    /**
     * Constructor
     * @param objectRefsForBuilder Object with references to needed interfaces to build GUI
     * @return BooleanExpEditor Object
     */
    public BooleanExpEditor createBooleanExpEditorObject(ObjectRefsForBuilder objectRefsForBuilder) {
        //creation of BooleanExpEditorWindow object
        BooleanExpEditorWindow window = new BooleanExpEditorWindow();
        window.updateStringRes(objectRefsForBuilder.getStringIF());

        //creation of empty SymbolicVarListController object
        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
        SymbolicVarListController symbolicVarListController = new SymbolicVarListController(window.getSymVarList(), window.getAddSymVarButton(),
                window.getRemoveSymVarButton(), objectRefsForBuilder.getStringIF(), symbolicVariableList);

        //creation of empty PostAndPrePropertiesDescription object
        PostAndPrePropertiesDescription emptyPostAndPrePropertiesDescription =
                NewPropsUserAction.createEmptyPostAndPropObject();

        //creation of ErrorWindow object
        ErrorWindow errorWindow = new ErrorWindow(window.getErrorTextPane(), objectRefsForBuilder.getStringIF());

        //creation of BooleanExpCodeAreas objects using the JTextPanes from the BooleanExpEditorWindow instance "window"
        //TODO create an ArrayList of RegexAndColor objects and apply it to the codeareas by calling
        //codeAreaObject.setSyntaxHLRegexAndColorList()

        BooleanExpCodeAreaBuilder codeAreaBuilder = new BooleanExpCodeAreaBuilder();
        BooleanExpCodeArea prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPrePropTextPane(), window.getPrePropScrollPane(), errorWindow);
        BooleanExpCodeArea postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPostPropTextPane(), window.getPostPropScrollPane(), errorWindow);

        // create SaveBeforeChangeHandler
        SaveBeforeChangeHandler saveBeforeChangeHandler = new SaveBeforeChangeHandler(prePropCodeArea.getPane(),
                postPropCodeArea.getPane(), symbolicVariableList);

        //create CodeAreaFocusListener
        CodeAreaFocusListener codeAreaFocusListener = new CodeAreaFocusListener(prePropCodeArea, postPropCodeArea);

        BooleanExpEditor editor = new BooleanExpEditor(prePropCodeArea, postPropCodeArea, window, symbolicVarListController,
                errorWindow, saveBeforeChangeHandler, codeAreaFocusListener, emptyPostAndPrePropertiesDescription,
                codeAreaBuilder, objectRefsForBuilder);

        //creation of BooleanExpEditorMenubarHandler
        BooleanExpEditorMenubarHandler menuBarHandler = new BooleanExpEditorMenubarHandler(menuHeadingIds, window,
                createActionIdAndListenerListForMenuHandler(editor, saveBeforeChangeHandler,
                        objectRefsForBuilder.getSaverLoaderIF()), objectRefsForBuilder.getStringIF());

        //creation of BooleanExpEditorToolbarHandler
        BooleanExpEditorToolbarHandler toolBarHandler = new BooleanExpEditorToolbarHandler(window,
                ImageResourceProvider.getToolbarImages(),
                objectRefsForBuilder.getStringIF().getBooleanExpEditorStringResProvider().getToolbarTipStringRes(),
                createActionIdAndListenerListForToolbarHandler(editor, saveBeforeChangeHandler,
                        objectRefsForBuilder.getSaverLoaderIF()));

        editor.setToolBarHandler(toolBarHandler);
        editor.setMenuBarHandler(menuBarHandler);

        return editor;
    }

    /**
     * Method creating the ArrayList of ArrayLists of ActionIdAndListener objects, containing necessary elements
     * for building and handling the JMenuBar of a BooleanExpEditorWindow object.
     * @return said list, a ArrayList<ArrayList<ActionIdAndListener>> object
     */
    private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerListForMenuHandler(BooleanExpEditor editor,
                                                SaveBeforeChangeHandler saveBeforeChangeHandler,
                                                SaverLoaderInterface saverLoaderInterface) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        UserAction newProps = createNewPropsUserAction(editor, saveBeforeChangeHandler);
        UserAction load = createLoadPropsUserAction(editor, saveBeforeChangeHandler, saverLoaderInterface);
        UserAction save = createSavePropsUserAction();
        UserAction saveAs = createSaveAsPropsUserAction();
        fileList.add(createFromUserAction(newProps));
        fileList.add(createFromUserAction(load));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));

        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        UserAction undo = createUndoUserAction(editor);
        UserAction redo = createRedoUserAction(editor);
        UserAction copy = createCopyUserAction(editor);
        UserAction cut = createCutUserAction(editor);
        UserAction paste = createPasteUserAction(editor);
        editList.add(createFromUserAction(undo));
        editList.add(createFromUserAction(redo));
        editList.add(createFromUserAction(copy));
        editList.add(createFromUserAction(cut));
        editList.add(createFromUserAction(paste));

        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        UserAction presentOptions = createPresentOptionsUserAction();
        editorList.add(createFromUserAction(presentOptions));

        ArrayList<ActionIdAndListener> makroList = new ArrayList<>();
        UserAction forAllVotersMakro = createMakroUserAction("forAllVoters", "FOR_ALL_VOTERS()", editor);
        UserAction forAllCandidatesMakro = createMakroUserAction("forAllCandidates", "FOR_ALL_CANDIDATES()",
                editor);
        UserAction forAllSeatsMakro = createMakroUserAction("forAllSeats", "FOR_ALL_SEATS()", editor);
        UserAction existsOneVoterMakro = createMakroUserAction("existsOneVoter", "EXISTS_ONE_VOTER()",
                editor);
        UserAction existsOneCandidateMakro = createMakroUserAction("existsOneCandidate",
                "EXISTS_ONE_CANDIDATE()", editor);
        UserAction existsOneSeatMakro = createMakroUserAction("existsOneSeat", "EXISTS_ONE_SEAT()", editor);
        UserAction sumVotesForCandidateMakro =  createMakroUserAction("sumVotesForCandidate",
                "SUM_VOTES_FOR_CANDIDATE()", editor);
        makroList.add(createFromUserAction(forAllVotersMakro));
        makroList.add(createFromUserAction(forAllCandidatesMakro));
        makroList.add(createFromUserAction(forAllSeatsMakro));
        makroList.add(createFromUserAction(existsOneVoterMakro));
        makroList.add(createFromUserAction(existsOneCandidateMakro));
        makroList.add(createFromUserAction(existsOneSeatMakro));
        makroList.add(createFromUserAction(sumVotesForCandidateMakro));

        ArrayList<ActionIdAndListener> constantsList = new ArrayList<>();
        UserAction votersConstant = createConstantUserAction("Voters", "V", editor);
        UserAction candidatesConstant = createConstantUserAction("Candidates", "C", editor);
        UserAction seatsConstant = createConstantUserAction("Seats", "S", editor);
        constantsList.add(createFromUserAction(votersConstant));
        constantsList.add(createFromUserAction(candidatesConstant));
        constantsList.add(createFromUserAction(seatsConstant));

        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
        UserAction staticCodeAnalysis = createStaticCheckUserAction(editor);
        codeList.add(createFromUserAction(staticCodeAnalysis));

        created.add(fileList);
        created.add(editList);
        created.add(editorList);
        created.add(makroList);
        created.add(constantsList);
        created.add(codeList);

        return created;
    }

    /**
     * Method creating the an Array of ActionIdAndListener objects necessary for building and handling the JToolBar of
     * a BooleanExpEditorWindow object.
     * @return said list, a ActionIdAndListener[] object
     */
    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(BooleanExpEditor editor,
                                                                     SaveBeforeChangeHandler saveBeforeChangeHandler,
                                                                         SaverLoaderInterface saverLoaderInterface) {
        ActionIdAndListener[] created = new ActionIdAndListener[9];

        UserAction newProps = createNewPropsUserAction(editor, saveBeforeChangeHandler);
        UserAction undo = createUndoUserAction(editor);
        UserAction redo = createRedoUserAction(editor);
        UserAction save = createSavePropsUserAction();
        UserAction saveAs = createSaveAsPropsUserAction();
        UserAction load = createLoadPropsUserAction(editor, saveBeforeChangeHandler, saverLoaderInterface);
        UserAction copy = createCopyUserAction(editor);
        UserAction cut = createCutUserAction(editor);
        UserAction paste = createPasteUserAction(editor);
        created[0] = createFromUserAction(newProps);
        created[1] = createFromUserAction(undo);
        created[2] = createFromUserAction(redo);
        created[3] = createFromUserAction(save);
        created[4] = createFromUserAction(saveAs);
        created[5] = createFromUserAction(load);
        created[6] = createFromUserAction(copy);
        created[7] = createFromUserAction(cut);
        created[8] = createFromUserAction(paste);

        return created;
    }

    //file
    private NewPropsUserAction createNewPropsUserAction(BooleanExpEditor editor,
                                                        SaveBeforeChangeHandler saveBeforeChangeHandler) {
        return new NewPropsUserAction(editor, saveBeforeChangeHandler);
    }
    private SavePropsUserAction createSavePropsUserAction() {
        return new SavePropsUserAction();
    }
    private SaveAsPropsUserAction createSaveAsPropsUserAction() {
        return new SaveAsPropsUserAction();
    }
    private LoadPropsUserAction createLoadPropsUserAction(BooleanExpEditor editor,
                                                          SaveBeforeChangeHandler saveBeforeChangeHandler,
                                                          SaverLoaderInterface saverLoaderInterface) {
        return new LoadPropsUserAction(editor, saverLoaderInterface, saveBeforeChangeHandler);
    }

    //edit
    private UndoBoolUserAction createUndoUserAction(BooleanExpEditor editor) {
        return new UndoBoolUserAction(editor);
    }
    private RedoBoolUserAction createRedoUserAction(BooleanExpEditor editor) {
        return new RedoBoolUserAction(editor);
    }
    private CopyUserAction createCopyUserAction(BooleanExpEditor editor) {
        return new CopyUserAction(editor);
    }
    private CutUserAction createCutUserAction(BooleanExpEditor editor) {
        return new CutUserAction(editor);
    }
    private PasteUserAction createPasteUserAction(BooleanExpEditor editor) {
        return new PasteUserAction(editor);
    }

    //editor
    private PresentOptionsBoolUserAction createPresentOptionsUserAction() {
        return new PresentOptionsBoolUserAction();
    }

    //constants
    private AddConstUserAction createConstantUserAction(String id, String constant, BooleanExpEditor editor) {
        return new AddConstUserAction(id, new BooleanExpEditorConst(constant), editor);
    }

    //makro
    private AddMakroUserAction createMakroUserAction(String id, String makro, BooleanExpEditor editor) {
        return new AddMakroUserAction(id, new BooleanExpEditorMakro(makro), editor);
    }

    //code
    private CheckErrorsUserAction createStaticCheckUserAction(BooleanExpEditor editor) {
        return new CheckErrorsUserAction(editor);
    }

    private ActionIdAndListener createFromUserAction(UserAction userAc) {
        return new ActionIdAndListener(userAc.getId(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                userAc.perform();
            }
        });
    }
}
