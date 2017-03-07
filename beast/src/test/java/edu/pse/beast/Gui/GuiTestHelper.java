package edu.pse.beast.Gui;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;

import java.awt.*;

/**
 * Created by holger on 07.03.17.
 */
public class GuiTestHelper {
    private CentralObjectProvider centralObjectProvider;


    public void startNewBEASTInstance() throws InterruptedException {
        BEASTCommunicator communicator = new BEASTCommunicator();
        centralObjectProvider = new PSECentralObjectProvider(communicator);
        communicator.setCentralObjectProvider(centralObjectProvider);
        while(!centralObjectProvider.getParameterSrc().getView().isVisible())
            Thread.sleep(100);
    }

    public ParameterEditor getParamEditorOfCurrentInstace() {
        return (ParameterEditor) centralObjectProvider.getParameterSrc();
    }

    public CElectionDescriptionEditor getCEditorOfCurrentInstace() {
        return (CElectionDescriptionEditor) centralObjectProvider.getElectionDescriptionSource();
    }

    public BooleanExpEditor getBooleanExpEditorOfCurrentInstance() {
        return ((PropertyList) (centralObjectProvider.getPostAndPrePropertiesSource())).getEditor();
    }

    public PropertyList getPropListOfCurrentInstance() {
        return (PropertyList) (centralObjectProvider.getPostAndPrePropertiesSource());
    }

    public void performKeystrokes(int[] keys, long waittimeBetweenStrokes) throws AWTException, InterruptedException {
        Robot r = new Robot();
        for (int i = 0; i < keys.length; i++) {
            r.keyPress(keys[i]);
            r.keyRelease(keys[i]);
            Thread.sleep(waittimeBetweenStrokes);
        }
    }
}
