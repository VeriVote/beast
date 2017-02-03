package edu.pse.beast.highlevel;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.parametereditor.ParameterEditorBuilder;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.PropertyListBuilder;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 *
 * @author Jonas
 */
public class PSECentralObjectProvider implements CentralObjectProvider{
    
    private final ParameterEditor paramEd;
    private final PropertyList propertyList;
    private final BooleanExpEditor booleanExpEditor;
    private final CElectionDescriptionEditor cElectionEditor;
    private final ResultCheckerCommunicator checkerCommunicator;
    
    public PSECentralObjectProvider() {
        OptionsInterface optionsInterface = new OptionsInterface();
        StringLoaderInterface stringIf = new StringLoaderInterface("de"); //must be provided  by language options at some point
        SaverLoaderInterface saverLoaderIF = new SaverLoaderInterface();
        ObjectRefsForBuilder refs = new ObjectRefsForBuilder(
                optionsInterface, stringIf, 
                optionsInterface.getLanguageOptions(), saverLoaderIF);
        cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
        booleanExpEditor = new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);        
        propertyList = new PropertyListBuilder().createPropertyList(refs, booleanExpEditor);
        checkerCommunicator = new PropertyChecker("cbmc"); //this must be done via the checkerfactory at some point
        paramEd = new ParameterEditorBuilder().createParameterEditor(refs, cElectionEditor, propertyList);        
    }

    @Override
    public ElectionDescriptionSource getElectionDescriptionSource() {
        return cElectionEditor;
    }

    @Override
    public PostAndPrePropertiesDescriptionSource getPostAndPrePropertiesSource() {
        return propertyList;
    }

    @Override
    public ResultCheckerCommunicator getResultCheckerCommunicator() {
        return checkerCommunicator;
    }

    @Override
    public ParameterSource getParameterSrc() {
        return paramEd;
    }

    @Override
    public ResultPresenter getResultPresenter() {
        return propertyList;
    }

    @Override
    public MainNotifier getMainNotifier() {
        return paramEd;
    }
    
}
