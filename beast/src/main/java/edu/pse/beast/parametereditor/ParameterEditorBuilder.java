package edu.pse.beast.parametereditor;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.UserActions.*;
import edu.pse.beast.saverloader.SaverLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Holger-Desktop
 */
public class ParameterEditorBuilder {

    private ParameterEditor editor;
    private ParameterEditorWindow window;
    private String[] menuHeadingIds = {"fileMenu", "projectMenu", "editorMenu", "showHideWindowsMenu"};

    public ParameterEditor createParameterEditor(
            ObjectRefsForBuilder refs,
            CElectionDescriptionEditor cElectionDescriptionEditor,
            BooleanExpEditor booleanExpEditor,
            PropertyList propertyList) {
        ParameterEditorWindowStarter windowStarter = new ParameterEditorWindowStarter();
        window = windowStarter.getParameterEditorWindow();
        window.updateStringRes(refs.getStringIF());
        editor = new ParameterEditor(cElectionDescriptionEditor, propertyList, window);
        ParameterEditorMenuBarHandler menuBarHandler = new ParameterEditorMenuBarHandler(menuHeadingIds,
                createActionIdAndListenerListForMenuHandler(cElectionDescriptionEditor, booleanExpEditor, propertyList,
                        refs.getSaverLoaderIF()),
                refs.getStringIF().getParameterEditorStringResProvider().getMenuStringRes(), window);
        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
        ParameterEditorToolbarHandler toolbarHandler = new ParameterEditorToolbarHandler(imageRes,
                refs.getStringIF().getParameterEditorStringResProvider().getToolbarTipStringRes(),
                createActionIdAndListenerListForToolbarHandler(cElectionDescriptionEditor, propertyList,
                        refs.getSaverLoaderIF()), window.getToolbar(), window);

        
        editor.setToolbarHandler(toolbarHandler);
        editor.setMenuBarHandler(menuBarHandler);
        windowStarter.start();
        return editor;
    }

    private ArrayList<ArrayList<ActionIdAndListener>>
            createActionIdAndListenerListForMenuHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
                                                        BooleanExpEditor booleanExpEditor,
                    PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction save_as = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction start = createStartCheckUserAction();
        UserAction stop = createAbortCheckUserAction();
        UserAction options = createOptionsUserAction(cElectionDescriptionEditor, propertyList);
        UserAction showPropertyList = createShowPropertyListUserAction(propertyList.getView());
        UserAction showBooleanExpEditor = createShowBooleanExpEditorUserAction(booleanExpEditor.getWindow());
        UserAction showCElectionEditor = createShowCElectionEditorUserAction(cElectionDescriptionEditor.getGui());

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        fileList.add(createFromUserAction(newly));
        fileList.add(createFromUserAction(load));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(save_as));

        ArrayList<ActionIdAndListener> projectList = new ArrayList<>();
        projectList.add(createFromUserAction(start));
        projectList.add(createFromUserAction(stop));

        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        editorList.add(createFromUserAction(options));

        ArrayList<ActionIdAndListener> showHideWindowsList = new ArrayList<>();
        showHideWindowsList.add(createFromUserAction(showCElectionEditor));
        showHideWindowsList.add(createFromUserAction(showPropertyList));
        showHideWindowsList.add(createFromUserAction(showBooleanExpEditor));

        created.add(fileList);
        created.add(projectList);
        created.add(editorList);
        created.add(showHideWindowsList);
        return created;
    }

    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        ActionIdAndListener[] created = new ActionIdAndListener[6];

        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction save_as = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction start = createStartCheckUserAction();
        UserAction stop = createAbortCheckUserAction();

        created[0] = createFromUserAction(newly);
        created[1] = createFromUserAction(load);
        created[3] = createFromUserAction(save);
        created[2] = createFromUserAction(save_as);
        created[4] = createFromUserAction(start);
        created[5] = createFromUserAction(stop);

        return created;
    }

    private UserAction createNewProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList) {
        return new NewProjectUserAction(propertyList, cElectionDescriptionEditor, editor);
    }

    private UserAction createSaveProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new SaveProjectUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createSaveProjectAsUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new SaveProjectAsUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createLoadProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new LoadProjectUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createStartCheckUserAction() {
        return new StartCheckUserAction(editor);
    }

    private UserAction createAbortCheckUserAction() {
        return new AbortCheckUserAction(editor);
    }
    
    private UserAction createOptionsUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList) {
        return new OptionsUserAction(propertyList, cElectionDescriptionEditor, editor);
    }

    private UserAction createShowPropertyListUserAction(JFrame propertyListWindow) {
        return new ShowHidePropertyList(propertyListWindow);
    }

    private UserAction createShowBooleanExpEditorUserAction(JFrame booleanExpEditorWindow) {
        return new ShowHideBooleanExpEditor(booleanExpEditorWindow);
    }

    private UserAction createShowCElectionEditorUserAction(JFrame cCodeEditorGUI) {
        return new ShowHideCElectionEditor(cCodeEditorGUI);
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
