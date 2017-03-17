package edu.pse.beast.propertylist;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.options.ParametereditorOptions.LanguageOptions;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.parametereditor.ParameterEditorBuilder;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 * Builds modules needed for testing.
 * @author Justin
 */
public class TestHelper {
	private CentralObjectProvider centralObjectProvider;
	private ObjectRefsForBuilder refs;
	
	public TestHelper() {
		OptionsInterface optionsInterface = new OptionsInterface();
        StringLoaderInterface stringIf = new StringLoaderInterface("de"); 
        SaverLoaderInterface saverLoaderIF = new SaverLoaderInterface();
        LanguageOptions langOpts = optionsInterface.getLanguageOptions(stringIf);
        refs = new ObjectRefsForBuilder(
                optionsInterface, stringIf, 
                langOpts, saverLoaderIF);
	}


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
    
    public PropertyList getPropertyList() {
    	CElectionDescriptionEditor cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
    	BooleanExpEditor booleanExpEditor = new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);
    	return new PropertyList(new PLModel(), booleanExpEditor, null);
    }

    public BooleanExpEditor getBooleanExpEditor() {

        CElectionDescriptionEditor cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
        return new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);
        
        /* Just what you need
        cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
        booleanExpEditor = new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);        
        propertyList = new PropertyListBuilder().createPropertyList(refs, booleanExpEditor);
        checkerCommunicator = new PropertyChecker("cbmc"); //this must be done via the checkerfactory at some point
        paramEd = new ParameterEditorBuilder().createParameterEditor(refs, cElectionEditor,
                booleanExpEditor, propertyList, this);
        paramEd.addCheckListener(communicator);
        langOpts.reapply();*/
    }
}
