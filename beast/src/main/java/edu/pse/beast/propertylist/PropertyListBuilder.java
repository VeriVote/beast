//package edu.pse.beast.propertylist;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.propertylist.model.PLModel;
//import edu.pse.beast.propertylist.useractions.LoadPropertyList;
//import edu.pse.beast.propertylist.useractions.NewPropertyList;
//import edu.pse.beast.propertylist.useractions.OpenSimulationWindow;
//import edu.pse.beast.propertylist.useractions.SaveAsPropertyList;
//import edu.pse.beast.propertylist.useractions.SavePropertyList;
//import edu.pse.beast.propertylist.useractions.UndoChangesPropertyList;
//import edu.pse.beast.propertylist.view.PropertyListWindow;
//import edu.pse.beast.saverloader.FileChooser;
//import edu.pse.beast.saverloader.PropertyListSaverLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.ImageResourceProvider;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Builds the property list components and returns the controller of property
// * list.
// * @author Justin
// */
//public class PropertyListBuilder {
//
//    PropertyList controller;
//    private PropertyListWindow window;
//    private String[] menuHeadingIds = {"fileMenu", "editMenu"};
//
//    /**
//     * Builds all relevant components.
//     * @param refs A reference to the objects that are important for the builder such as string loaders and the like
//     * @param booleanExpEditor A reference to the editor for boolean expressions
//     * @return Returns the controller class of PropertyList
//     */
//    public PropertyList createPropertyList(ObjectRefsForBuilder refs, BooleanExpEditor booleanExpEditor) {
//        PLModel model = new PLModel();
//        FileChooser fileChooser = new FileChooser(refs.getStringIF().getPropertyListStringResProvider().getOtherStringRes(),
//                new PropertyListSaverLoader(), null);
//        controller = new PropertyList(model, booleanExpEditor, fileChooser);
//        booleanExpEditor.setPropertyListController(controller);
//
//        window = controller.getView();
//        //window.updateStringRes(refs.getStringIF());
//
//		PropertyListMenuBarHandler menuBarHandler = new PropertyListMenuBarHandler(menuHeadingIds,
//                createActionIdAndListenerListForMenuHandler(),
//                refs.getStringIF().getPropertyListStringResProvider().getMenuStringRes(), window);
//
//        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
//
//		PropertyListToolbarHandler toolbarHandler = new PropertyListToolbarHandler(imageRes,
//                refs.getStringIF().getPropertyListStringResProvider().getToolbarTipStringRes(),
//                createActionIdAndListenerListForToolbarHandler(), window.getToolbar(), window);
//
//        
//
//        refs.getLanguageOpts().addStringDisplayer(window);
//        refs.getLanguageOpts().addStringDisplayer(menuBarHandler);
//        refs.getLanguageOpts().addStringDisplayer(toolbarHandler);
//        //refs.getLanguageOpts().addStringDisplayer(controller);
//        
//        controller.start();
//        return controller;
//    }
//
//    
//    private ArrayList<ArrayList<ActionIdAndListener>>
//            createActionIdAndListenerListForMenuHandler() {
//        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
//        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
//
//        UserAction newly = createNewPropertyList();
//        UserAction undo = createUndoChangesPropertyList();
//        UserAction load = createLoadPropertyList();
//        UserAction save = createSavePropertyList();
//        UserAction saveAs = createSaveAsPropertyList();
//        UserAction openSim = createOpenSimulation();
//
//        fileList.add(createFromUserAction(newly));
//        fileList.add(createFromUserAction(load));
//        fileList.add(createFromUserAction(save));
//        fileList.add(createFromUserAction(saveAs));
//        fileList.add(createFromUserAction(openSim));
//
//        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
//
//        editList.add(createFromUserAction(undo));
//
//        created.add(fileList);
//        created.add(editList);
//        return created;
//    }
//
//    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler() {
//        ActionIdAndListener[] created = new ActionIdAndListener[6];
//
//        UserAction newly = createNewPropertyList();
//        UserAction load = createLoadPropertyList();
//        UserAction save = createSavePropertyList();
//        UserAction saveAs = createSaveAsPropertyList();
//        UserAction undo = createUndoChangesPropertyList();
//        UserAction openSim = createOpenSimulation();
//
//        created[0] = createFromUserAction(newly);
//        created[1] = createFromUserAction(load);
//        created[2] = createFromUserAction(save);
//        created[3] = createFromUserAction(saveAs);
//        created[4] = createFromUserAction(undo);
//        created[5] = createFromUserAction(openSim);
//
//        return created;
//    }
//
//    private LoadPropertyList createLoadPropertyList() {
//        return new LoadPropertyList((PropertyList) controller);
//    }
//
//    private NewPropertyList createNewPropertyList() {
//        return new NewPropertyList((PropertyList) controller);
//    }
//
//    private SaveAsPropertyList createSaveAsPropertyList() {
//        return new SaveAsPropertyList((PropertyList) controller);
//    }
//
//    private SavePropertyList createSavePropertyList() {
//        return new SavePropertyList((PropertyList) controller);
//    }
//
//    private UndoChangesPropertyList createUndoChangesPropertyList() {
//        return new UndoChangesPropertyList((PropertyList) controller);
//    }
//    
//    private OpenSimulationWindow createOpenSimulation() {
//        return new OpenSimulationWindow((PropertyList) controller);
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
//
//}
