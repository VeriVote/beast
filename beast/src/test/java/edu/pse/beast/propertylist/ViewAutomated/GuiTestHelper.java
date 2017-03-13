package edu.pse.beast.propertylist.ViewAutomated;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;

/**
 * Thanks to Holger for creating the helper class. Copied for decoupling.
 * @author Justin
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

}
