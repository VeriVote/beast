package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.UserActions.*;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.UserActions.*;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Builder Class to create BooleanExpEditor Object. Called by PropertyList.
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
        BooleanExpCodeAreaBuilder codeAreaBuilder = new BooleanExpCodeAreaBuilder();
        BooleanExpEditorWindow window = new BooleanExpEditorWindow();
        window.updateStringRes(objectRefsForBuilder.getStringIF());
        SymbolicVarList symbolicVarList = new SymbolicVarList(window.getSymVarList(), window.getAddSymVarButton(),
                window.getRemoveSymVarButton(), objectRefsForBuilder.getStringIF());
        ErrorWindow errorWindow = new ErrorWindow(window.getErrorTextPane(), objectRefsForBuilder.getStringIF());
        BooleanExpCodeArea prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPrePropTextPane(), window.getPrePropScrollPane());
        BooleanExpCodeArea postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPostPropTextPane(), window.getPostPropScrollPane());
        BooleanExpEditorMenubarHandler menuBarHandler = new BooleanExpEditorMenubarHandler(menuHeadingIds, window,
                createActionIdAndListenerList(), objectRefsForBuilder.getStringIF());
        return new BooleanExpEditor(prePropCodeArea, postPropCodeArea, window, symbolicVarList, errorWindow,
                menuBarHandler);
    }

    private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerList() {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        UserAction newAcc = createNewPropsUserAction();
        UserAction load = createLoadPropsUserAction();
        UserAction save = createSavePropsUserAction();
        UserAction saveAs = createSaveAsPropsUserAction();
        fileList.add(createFromUserAction(newAcc));
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

    //file
    private SaveBeforeChangeHandler createSaveBeforeChangeHandler() {
        return new SaveBeforeChangeHandler();
    }
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
