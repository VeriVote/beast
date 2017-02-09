package edu.pse.beast.parametereditor;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.parametereditor.View.ParameterEditorWindow;
import edu.pse.beast.parametereditor.View.ParameterEditorWindowStarter;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.saverloader.SaverLoader;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.*;
import edu.pse.beast.parametereditor.UserActions.*;
import edu.pse.beast.saverloader.SaverLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ParameterEditorBuilder builds a ParameterEditor and provides the references
 * to other parts of BEAST for it.
 * @author Holger-Desktop
 */
public class ParameterEditorBuilder {

    private ParameterEditor editor;
    private ParameterEditorWindow window;
    private final String[] menuHeadingIds = {"fileMenu", "projectMenu", "editorMenu", "showHideWindowsMenu"};
    /**
     * Creates the ParameterEditor
     * @param refs contains all important references for the builder
     * @param cElectionDescriptionEditor editor for the election
     * @param booleanExpEditor editor for properties
     * @param propertyList list of properties
     * @return ParameterEditor
     */
    public ParameterEditor createParameterEditor(
            ObjectRefsForBuilder refs,
            CElectionDescriptionEditor cElectionDescriptionEditor,
            BooleanExpEditor booleanExpEditor,
            PropertyList propertyList) {
        ParameterEditorWindowStarter windowStarter = new ParameterEditorWindowStarter();
        SaverLoaderInterface saverLoaderInterface = new SaverLoaderInterface();
        window = windowStarter.getParameterEditorWindow();
        window.updateStringRes(refs.getStringIF());
        FileChooser fileChooser = new FileChooser(
                refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
                new ProjectSaverLoader(),
                null);
        editor = new ParameterEditor(cElectionDescriptionEditor, propertyList, window, fileChooser);
        ParameterEditorMenuBarHandler menuBarHandler = new ParameterEditorMenuBarHandler(menuHeadingIds,
                createActionIdAndListenerListForMenuHandler(cElectionDescriptionEditor, booleanExpEditor, propertyList,
                        refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
                        saverLoaderInterface.getProjectSaverLoader()),
                refs.getStringIF().getParameterEditorStringResProvider().getMenuStringRes(), window);
        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
        ParameterEditorToolbarHandler toolbarHandler = new ParameterEditorToolbarHandler(imageRes,
                refs.getStringIF().getParameterEditorStringResProvider().getToolbarTipStringRes(),
                createActionIdAndListenerListForToolbarHandler(cElectionDescriptionEditor, propertyList,
                        refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
                        saverLoaderInterface.getProjectSaverLoader()), window.getToolbar(), window);

        
        editor.setToolbarHandler(toolbarHandler);
        editor.setMenuBarHandler(menuBarHandler);
        windowStarter.start();
        editor.setReacts(true);
        return editor;
    }
    /**
     * Creates an Arraylist of Arraylists of ActionIdAndListeners which the MenuBarHandler uses to create the
     * MenuBar with the appropriate Strings.
     * @param cElectionDescriptionEditor
     * @param booleanExpEditor
     * @param propertyList
     * @param stringResourceLoader
     * @return the ArrayList
     */
    private ArrayList<ArrayList<ActionIdAndListener>>
            createActionIdAndListenerListForMenuHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
                                                        BooleanExpEditor booleanExpEditor,
                    PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, stringResourceLoader, saverLoader);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoader);
        UserAction save_as = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, stringResourceLoader,
                saverLoader);
        UserAction start = createStartCheckUserAction();
        UserAction stop = createAbortCheckUserAction();
        UserAction options = createOptionsUserAction(cElectionDescriptionEditor, propertyList);
        UserAction showPropertyList = createShowPropertyListUserAction(propertyList.getView());
        UserAction showBooleanExpEditor = createShowBooleanExpEditorUserAction(booleanExpEditor.getView());
        UserAction showCElectionEditor = createShowCElectionEditorUserAction(cElectionDescriptionEditor.getView());

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
    /**
     * Creates ActionIdAndListeners which the ToolbarHandler uses to create the
     * Toolbar with the appropriate Strings.
     * @param cElectionDescriptionEditor the CelectionDescriptionEditor
     * @param propertyList the PropertyList
     * @param stringResourceLoader the StringRessourceLoader
     * @return the Array of ActionIdAndListener objects
     */
    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(
            CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader) {
        ActionIdAndListener[] created = new ActionIdAndListener[6];

        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, stringResourceLoader, saverLoader);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoader);
        UserAction save_as = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, stringResourceLoader,
                saverLoader);
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
            PropertyList propertyList, SaverLoader saverLoader) {
        return new SaveProjectUserAction(propertyList, cElectionDescriptionEditor, editor);
    }

    private UserAction createSaveProjectAsUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
                                                     PropertyList propertyList, StringResourceLoader stringResourceLoader,
                                                     SaverLoader saverLoader) {
        return new SaveProjectAsUserAction(propertyList, cElectionDescriptionEditor, editor, stringResourceLoader);
    }

    private UserAction createLoadProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader) {
        return new LoadProjectUserAction(propertyList, cElectionDescriptionEditor, editor, stringResourceLoader);
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
