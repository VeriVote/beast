package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.UserActions.*;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.UserActions.*;
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

        //creation of SymbolicVarList object
        SymbolicVarList symbolicVarList = new SymbolicVarList(window.getSymVarList(), window.getAddSymVarButton(),
                window.getRemoveSymVarButton(), objectRefsForBuilder.getStringIF());

        //creation of ErrorWindow object
        ErrorWindow errorWindow = new ErrorWindow(window.getErrorTextPane(), objectRefsForBuilder.getStringIF());

        //creation of BooleanExpCodeAreas objects using the JTextPanes from the BooleanExpEditorWindow instance "window"
        BooleanExpCodeAreaBuilder codeAreaBuilder = new BooleanExpCodeAreaBuilder();
        BooleanExpCodeArea prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPrePropTextPane(), window.getPrePropScrollPane());
        BooleanExpCodeArea postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPostPropTextPane(), window.getPostPropScrollPane());

        //creation of BooleanExpEditorMenubarHandler
        BooleanExpEditorMenubarHandler menuBarHandler = new BooleanExpEditorMenubarHandler(menuHeadingIds, window,
                createActionIdAndListenerListForMenuHandler(), objectRefsForBuilder.getStringIF());

        //creation of BooleanExpEditorToolbarHandler
        BooleanExpEditorToolbarHandler toolbarHandler = new BooleanExpEditorToolbarHandler(window,
                ImageResourceProvider.getToolbarImages(),
                objectRefsForBuilder.getStringIF().getBooleanExpEditorStringResProvider().getToolbarTipStringRes(),
                createActionIdAndListenerListForToolbarHandler());

        return new BooleanExpEditor(prePropCodeArea, postPropCodeArea, window, symbolicVarList, errorWindow,
                menuBarHandler, toolbarHandler);
    }

    /**
     * Method creating the ArrayList of ArrayLists of ActionIdAndListener objects, containing necessary elements
     * for building and handling the JMenuBar of a BooleanExpEditorWindow object.
     * @return said list, a ArrayList<ArrayList<ActionIdAndListener>> object
     */
    private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerListForMenuHandler() {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        UserAction newProps = createNewPropsUserAction();
        UserAction load = createLoadPropsUserAction();
        UserAction save = createSavePropsUserAction();
        UserAction saveAs = createSaveAsPropsUserAction();
        fileList.add(createFromUserAction(newProps));
        fileList.add(createFromUserAction(load));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));

        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        UserAction undo = createUndoUserAction();
        UserAction redo = createRedoUserAction();
        UserAction copy = createCopyUserAction();
        UserAction cut = createCutUserAction();
        UserAction paste = createPasteUserAction();
        editList.add(createFromUserAction(undo));
        editList.add(createFromUserAction(redo));
        editList.add(createFromUserAction(copy));
        editList.add(createFromUserAction(cut));
        editList.add(createFromUserAction(paste));

        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        UserAction presentOptions = createPresentOptionsUserAction();
        editorList.add(createFromUserAction(presentOptions));

        ArrayList<ActionIdAndListener> makroList = new ArrayList<>();
        UserAction forAllVotersMakro = createMakroUserAction("forAllVoters");
        UserAction forAllCandidatesMakro = createMakroUserAction("forAllCandidates");
        UserAction forAllSeatsMakro = createMakroUserAction("forAllSeats");
        UserAction existsOneVoterMakro = createMakroUserAction("existsOneVoter");
        UserAction existsOneCandidateMakro = createMakroUserAction("existsOneCandidate");
        UserAction existsOneSeatMakro = createMakroUserAction("existsOneSeat");
        UserAction sumVotesForCandidateMakro =  createMakroUserAction("sumVotesForCandidate");
        makroList.add(createFromUserAction(forAllVotersMakro));
        makroList.add(createFromUserAction(forAllCandidatesMakro));
        makroList.add(createFromUserAction(forAllSeatsMakro));
        makroList.add(createFromUserAction(existsOneVoterMakro));
        makroList.add(createFromUserAction(existsOneCandidateMakro));
        makroList.add(createFromUserAction(existsOneSeatMakro));
        makroList.add(createFromUserAction(sumVotesForCandidateMakro));

        ArrayList<ActionIdAndListener> constantsList = new ArrayList<>();
        UserAction votersConstant = createConstantUserAction("Voters");
        UserAction candidatesConstant = createConstantUserAction("Candidates");
        UserAction seatsConstant = createConstantUserAction("Seats");
        constantsList.add(createFromUserAction(votersConstant));
        constantsList.add(createFromUserAction(candidatesConstant));
        constantsList.add(createFromUserAction(seatsConstant));

        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
        UserAction staticCodeAnalysis = createStaticCheckUserAction();
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
    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler() {
        ActionIdAndListener[] created = new ActionIdAndListener[9];

        UserAction newProps = createNewPropsUserAction();
        UserAction undo = createUndoUserAction();
        UserAction redo = createRedoUserAction();
        UserAction save = createSavePropsUserAction();
        UserAction saveAs = createSaveAsPropsUserAction();
        UserAction load = createLoadPropsUserAction();
        UserAction copy = createCopyUserAction();
        UserAction cut = createCutUserAction();
        UserAction paste = createPasteUserAction();
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

    //TODO
    private SaveBeforeChangeHandler createSaveBeforeChangeHandler() {
        return new SaveBeforeChangeHandler();
    }

    //file
    private NewPropsUserAction createNewPropsUserAction() {
        return new NewPropsUserAction();
    }
    private SavePropsUserAction createSavePropsUserAction() {
        return new SavePropsUserAction();
    }
    private SaveAsPropsUserAction createSaveAsPropsUserAction() {
        return new SaveAsPropsUserAction();
    }
    private LoadPropsUserAction createLoadPropsUserAction() {
        return new LoadPropsUserAction();
    }

    //edit
    private UndoBoolUserAction createUndoUserAction() {
        return new UndoBoolUserAction();
    }
    private RedoBoolUserAction createRedoUserAction() {
        return new RedoBoolUserAction();
    }
    private CopyUserAction createCopyUserAction() {
        return new CopyUserAction();
    }
    private CutUserAction createCutUserAction() {
        return new CutUserAction();
    }
    private PasteUserAction createPasteUserAction() {
        return new PasteUserAction();
    }


    //editor
    private PresentOptionsBoolUserAction createPresentOptionsUserAction() {
        return new PresentOptionsBoolUserAction();
    }

    //constants
    private AddConstUserAction createConstantUserAction(String constant) {
        return new AddConstUserAction(new BooleanExpEditorConst(constant));
    }

    //makro
    private AddMakroUserAction createMakroUserAction(String makro) {
        return new AddMakroUserAction(new BooleanExpEditorMakro(makro));
    }

    //code
    private CheckErrorsUserAction createStaticCheckUserAction() {
        return new CheckErrorsUserAction();
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
