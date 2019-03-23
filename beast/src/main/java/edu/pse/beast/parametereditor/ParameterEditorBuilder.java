//package edu.pse.beast.parametereditor;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.JFrame;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.highlevel.PSECentralObjectProvider;
//import edu.pse.beast.parametereditor.useractions.AbortCheckUserAction;
//import edu.pse.beast.parametereditor.useractions.LoadProjectUserAction;
//import edu.pse.beast.parametereditor.useractions.OptionsUserAction;
//import edu.pse.beast.parametereditor.useractions.SaveProjectAsUserAction;
//import edu.pse.beast.parametereditor.useractions.SaveProjectUserAction;
//import edu.pse.beast.parametereditor.useractions.ShowHideBooleanExpEditor;
//import edu.pse.beast.parametereditor.useractions.ShowHideCElectionEditor;
//import edu.pse.beast.parametereditor.useractions.ShowHidePropertyList;
//import edu.pse.beast.parametereditor.useractions.StartCheckUserAction;
//import edu.pse.beast.parametereditor.view.ParameterEditorWindow;
//import edu.pse.beast.parametereditor.view.ParameterEditorWindowStarter;
//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.saverloader.FileChooser;
//import edu.pse.beast.saverloader.ProjectSaverLoader;
//import edu.pse.beast.saverloader.SaverLoader;
//import edu.pse.beast.saverloader.SaverLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.ImageResourceProvider;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * The ParameterEditorBuilder builds a ParameterEditor and provides the references
// * to other parts of BEAST for it.
// * @author Holger Klein
// */
//public class ParameterEditorBuilder {
//    private ParameterEditor editor;
//    private ParameterEditorWindow window;
//    private final String[] menuHeadingIds = {"fileMenu", "projectMenu", "editorMenu", "showHideWindowsMenu"};
//    /**
//     * Creates the ParameterEditor
//     * @param refs contains all important references for the builder
//     * @param cElectionDescriptionEditor editor for the election
//     * @param booleanExpEditor editor for properties
//     * @param propertyList list of properties
//     * @param centralObjectProvider CentralObjectProvider
//     * @return ParameterEditor
//     */
//    public ParameterEditor createParameterEditor(
//            ObjectRefsForBuilder refs,
//            CElectionDescriptionEditor cElectionDescriptionEditor,
//            BooleanExpEditor booleanExpEditor,
//            PropertyList propertyList, PSECentralObjectProvider centralObjectProvider) {
//        ParameterEditorWindowStarter windowStarter = new ParameterEditorWindowStarter();
//        SaverLoaderInterface saverLoaderInterface = new SaverLoaderInterface();
//        window = windowStarter.getParameterEditorWindow();
//        window.updateStringRes(refs.getStringIF());
//        FileChooser fileChooser = new FileChooser(
//                refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
//                new ProjectSaverLoader(),
//                null);
//        editor = new ParameterEditor(cElectionDescriptionEditor, propertyList, window, fileChooser);
//        ParameterEditorMenuBarHandler menuBarHandler = new ParameterEditorMenuBarHandler(menuHeadingIds,
//                createActionIdAndListenerListForMenuHandler(
//                    cElectionDescriptionEditor, booleanExpEditor, propertyList,
//                    refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
//                    saverLoaderInterface.getProjectSaverLoader(), refs, centralObjectProvider),
//                refs.getStringIF().getParameterEditorStringResProvider().getMenuStringRes(),
//                window);
//
//        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
//
//        ParameterEditorToolbarHandler toolbarHandler = new ParameterEditorToolbarHandler(imageRes,
//                refs.getStringIF().getParameterEditorStringResProvider().getToolbarTipStringRes(),
//                createActionIdAndListenerListForToolbarHandler(cElectionDescriptionEditor, propertyList,
//                        refs.getStringIF().getParameterEditorStringResProvider().getOtherStringRes(),
//                        saverLoaderInterface.getProjectSaverLoader()), window.getToolbar(), window);
//
//        refs.getLanguageOpts().addStringDisplayer(menuBarHandler);
//        refs.getLanguageOpts().addStringDisplayer(toolbarHandler);
//        refs.getLanguageOpts().addStringDisplayer(window);        
//        refs.getLanguageOpts().addStringDisplayer(editor);
//        windowStarter.start();
//        editor.setReacts(true);
//        return editor;
//    }
//    /**
//     * Creates an Arraylist of Arraylists of ActionIdAndListeners which the MenuBarHandler uses to create the
//     * MenuBar with the appropriate Strings.
//     * @param cElectionDescriptionEditor
//     * @param booleanExpEditor
//     * @param propertyList
//     * @param stringResourceLoader
//     * @return the ArrayList
//     */
//    private ArrayList<ArrayList<ActionIdAndListener>>
//            createActionIdAndListenerListForMenuHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
//                                                        BooleanExpEditor booleanExpEditor,
//                    PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader,
//                    ObjectRefsForBuilder refs, PSECentralObjectProvider centralObjectProvider) {
//        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
//
//        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoader);
//        UserAction save_as =
//            createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList,
//                                          stringResourceLoader, saverLoader);
//        UserAction load =
//            createLoadProjectUserAction(cElectionDescriptionEditor, propertyList,
//                                        stringResourceLoader, saverLoader);
//        UserAction start = createStartCheckUserAction();
//        UserAction stop = createAbortCheckUserAction();
//        UserAction options = createOptionsUserAction(refs, centralObjectProvider);
//        UserAction showPropertyList = createShowPropertyListUserAction(propertyList.getView());
//        UserAction showBooleanExpEditor = createShowBooleanExpEditorUserAction(booleanExpEditor.getView());
//        UserAction showCElectionEditor = createShowCElectionEditorUserAction(cElectionDescriptionEditor.getView());
//
//        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
//        fileList.add(createFromUserAction(load));
//        fileList.add(createFromUserAction(save));
//        fileList.add(createFromUserAction(save_as));
//
//        ArrayList<ActionIdAndListener> projectList = new ArrayList<>();
//        projectList.add(createFromUserAction(start));
//        projectList.add(createFromUserAction(stop));
//
//        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
//        editorList.add(createFromUserAction(options));
//
//        ArrayList<ActionIdAndListener> showHideWindowsList = new ArrayList<>();
//        showHideWindowsList.add(createFromUserAction(showCElectionEditor));
//        showHideWindowsList.add(createFromUserAction(showPropertyList));
//        showHideWindowsList.add(createFromUserAction(showBooleanExpEditor));
//
//        created.add(fileList);
//        created.add(projectList);
//        created.add(editorList);
//        created.add(showHideWindowsList);
//        return created;
//    }
//    /**
//     * Creates ActionIdAndListeners which the ToolbarHandler uses to create the
//     * Toolbar with the appropriate Strings.
//     * @param cElectionDescriptionEditor the CelectionDescriptionEditor
//     * @param propertyList the PropertyList
//     * @param stringResourceLoader the StringRessourceLoader
//     * @return the Array of ActionIdAndListener objects
//     */
//    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(
//            CElectionDescriptionEditor cElectionDescriptionEditor,
//            PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader) {
//        ActionIdAndListener[] created = new ActionIdAndListener[5];
//
//        UserAction load =
//            createLoadProjectUserAction(cElectionDescriptionEditor, propertyList,
//                                        stringResourceLoader, saverLoader);
//        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoader);
//        UserAction save_as =
//            createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList,
//                                          stringResourceLoader, saverLoader);
//        UserAction start = createStartCheckUserAction();
//        UserAction stop = createAbortCheckUserAction();
//
//        created[0] = createFromUserAction(load);
//        created[1] = createFromUserAction(save);
//        created[2] = createFromUserAction(save_as);
//        created[3] = createFromUserAction(start);
//        created[4] = createFromUserAction(stop);
//
//        return created;
//    }
//
//    private UserAction createSaveProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
//            PropertyList propertyList, SaverLoader saverLoader) {
//        return new SaveProjectUserAction(propertyList, cElectionDescriptionEditor, editor);
//    }
//
//    private UserAction createSaveProjectAsUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
//                                                     PropertyList propertyList,
//                                                     StringResourceLoader stringResourceLoader,
//                                                     SaverLoader saverLoader) {
//        return new SaveProjectAsUserAction(propertyList, cElectionDescriptionEditor, editor, stringResourceLoader);
//    }
//
//    private UserAction createLoadProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
//            PropertyList propertyList, StringResourceLoader stringResourceLoader, SaverLoader saverLoader) {
//        return new LoadProjectUserAction(propertyList, cElectionDescriptionEditor, editor, stringResourceLoader);
//    }
//
//    private UserAction createStartCheckUserAction() {
//        return new StartCheckUserAction(editor);
//    }
//
//    private UserAction createAbortCheckUserAction() {
//        return new AbortCheckUserAction(editor);
//    }
//
//    private UserAction createOptionsUserAction(ObjectRefsForBuilder refs,
//                                               PSECentralObjectProvider centralObjectProvider) {
//        return new OptionsUserAction(
//                refs.getOptionIF().getParameterEditorOptions(refs.getLanguageOpts(), editor,
//                                                             centralObjectProvider), editor,
//                                                             refs.getOptionIF().getOptionPresenter(refs));
//    }
//
//    private UserAction createShowPropertyListUserAction(JFrame propertyListWindow) {
//        return new ShowHidePropertyList(editor, propertyListWindow);
//    }
//
//    private UserAction createShowBooleanExpEditorUserAction(JFrame booleanExpEditorWindow) {
//        return new ShowHideBooleanExpEditor(editor, booleanExpEditorWindow);
//    }
//
//    private UserAction createShowCElectionEditorUserAction(JFrame cCodeEditorGUI) {
//        return new ShowHideCElectionEditor(editor, cCodeEditorGUI);
//    }
//
//    private ActionIdAndListener createFromUserAction(UserAction userAc) {
//        return new ActionIdAndListener(userAc.getId(), new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                userAc.perform();
//            }
//        });
//    }
//}