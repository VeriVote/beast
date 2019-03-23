//package edu.pse.beast.propertylist;
//
//import java.awt.AWTException;
//import java.awt.Robot;
//import java.awt.event.KeyEvent;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
//import edu.pse.beast.highlevel.BEASTCommunicator;
//import edu.pse.beast.highlevel.CentralObjectProvider;
//import edu.pse.beast.highlevel.PSECentralObjectProvider;
//import edu.pse.beast.options.OptionsInterface;
//import edu.pse.beast.options.parametereditoroptions.LanguageOptions;
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.propertylist.model.PLModel;
//import edu.pse.beast.saverloader.SaverLoaderInterface;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//
///**
// * Builds modules needed for testing.
// * @author Justin Hecht
// */
//public class TestHelper {
//    private CentralObjectProvider centralObjectProvider;
//    private ObjectRefsForBuilder refs;
//
//    public TestHelper() {
//        OptionsInterface optionsInterface = new OptionsInterface();
//        StringLoaderInterface stringIf = new StringLoaderInterface("en");
//        SaverLoaderInterface saverLoaderIF = new SaverLoaderInterface();
//        LanguageOptions langOpts = optionsInterface.getLanguageOptions(stringIf);
//        refs = new ObjectRefsForBuilder(optionsInterface, stringIf,
//                                        langOpts, saverLoaderIF);
//    }
//
//    public void startNewBEASTInstance() throws InterruptedException {
//        BEASTCommunicator communicator = new BEASTCommunicator();
//        centralObjectProvider = new PSECentralObjectProvider(communicator);
//        communicator.setCentralObjectProvider(centralObjectProvider);
//        while(!centralObjectProvider.getParameterSrc().getView().isVisible())
//            Thread.sleep(100);
//    }
//
//    public ParameterEditor getParamEditorOfCurrentInstace() {
//        return (ParameterEditor) centralObjectProvider.getParameterSrc();
//    }
//
//    public CElectionDescriptionEditor getCEditorOfCurrentInstace() {
//        return (CElectionDescriptionEditor) centralObjectProvider.getElectionDescriptionSource();
//    }
//
//    public BooleanExpEditor getBooleanExpEditorOfCurrentInstance() {
//        return ((PropertyList) (centralObjectProvider.getPreAndPostConditionsSource())).getEditor();
//    }
//
//    public PropertyList getPropListOfCurrentInstance() {
//        return (PropertyList) (centralObjectProvider.getPreAndPostConditionsSource());
//    }
//
//    public PropertyList getPropertyList() {
//        CElectionDescriptionEditor cElectionEditor =
//            new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
//        BooleanExpEditor booleanExpEditor =
//            new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);
//        return new PropertyList(new PLModel(), booleanExpEditor, null);
//    }
//
//    public BooleanExpEditor getBooleanExpEditor() {
//        CElectionDescriptionEditor cElectionEditor =
//            new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
//        return new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);
//
//        /* Just what you need
//        cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
//        booleanExpEditor = new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);
//        propertyList = new PropertyListBuilder().createPropertyList(refs, booleanExpEditor);
//        checkerCommunicator = new PropertyChecker("cbmc"); //this must be done via the checkerfactory at some point
//        paramEd =
//            new ParameterEditorBuilder().createParameterEditor(refs, cElectionEditor,
//                                                               booleanExpEditor, propertyList, this);
//        paramEd.addCheckListener(communicator);
//        langOpts.reapply(); */
//    }
//
//    public void performKeystrokesConcurrently(int[] strokes, long timeBefore, long timeBetween) {
//        Thread t = new Thread(() -> {
//            try {
//                Thread.sleep(timeBefore);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            try {
//                performKeystrokes(strokes, timeBetween);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        t.start();
//    }
//
//    public void performShortcut(int key, long timeoutafter) throws InterruptedException {
//        Robot r = null;
//        try {
//            r = new Robot();
//        } catch (AWTException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        r.keyPress(KeyEvent.VK_CONTROL);
//        r.keyPress(key);
//        r.keyRelease(key);
//        r.keyPress(KeyEvent.VK_CONTROL);
//        Thread.sleep(timeoutafter);
//    }
//
//    public void performKeystrokes(int[] keys, long waittimeBetweenStrokes) throws InterruptedException {
//        Robot r = null;
//        try {
//            r = new Robot();
//        } catch (AWTException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        for (int i = 0; i < keys.length; i++) {
//            r.keyPress(keys[i]);
//            r.keyRelease(keys[i]);
//            Thread.sleep(waittimeBetweenStrokes);
//        }
//    }
//}