package edu.pse.beast.highlevel;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.options.ParametereditorOptions.DeleteCFilesOptions;
import edu.pse.beast.options.ParametereditorOptions.LanguageOptions;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.parametereditor.ParameterEditorBuilder;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.PropertyListBuilder;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 * The PSECentralObjectProvider creates and provides access to all instances of
 * classes that implement high level interfaces used to run BEAST.
 * @author Jonas
 */
public class PSECentralObjectProvider implements CentralObjectProvider {
    
    private final ParameterEditor paramEd;
    private final PropertyList propertyList;
    private final BooleanExpEditor booleanExpEditor;
    private final CElectionDescriptionEditor cElectionEditor;
    private ResultCheckerCommunicator checkerCommunicator;
    
    /**
     * Constructor that creates instances of the classes that implement high level
     * interfaces via their builders.
     * @param communicator the BEASTCommunicator which needs access to these
     * instances
     */
    public PSECentralObjectProvider(BEASTCommunicator communicator) {
        //All data needed to create the instances are created.
        OptionsInterface optionsInterface = new OptionsInterface();
        StringLoaderInterface stringIf = new StringLoaderInterface("de"); 
        SaverLoaderInterface saverLoaderIF = new SaverLoaderInterface();
        LanguageOptions langOpts = optionsInterface.getLanguageOptions(stringIf);
        DeleteCFilesOptions delCFileOpts = optionsInterface.getDeleteFileOptions();
        ObjectRefsForBuilder refs = new ObjectRefsForBuilder(
                optionsInterface, stringIf, 
                langOpts, delCFileOpts, saverLoaderIF);
        //The instances themselves are created.
        cElectionEditor = new CElectionDescriptionEditorBuilder().createCElectionDescriptionEditor(refs);
        booleanExpEditor = new BooleanExpEditorBuilder().createBooleanExpEditorObject(refs, cElectionEditor);        
        propertyList = new PropertyListBuilder().createPropertyList(refs, booleanExpEditor);
        checkerCommunicator = new PropertyChecker("cbmc"); //this must be done via the checkerfactory at some point
        paramEd = new ParameterEditorBuilder().createParameterEditor(refs, cElectionEditor,
                booleanExpEditor, propertyList, this);
        paramEd.addCheckListener(communicator);
        langOpts.reapply();
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

    @Override
    public CheckStatusDisplay getCheckStatusDisplay() {
        return paramEd.getView();
    }

    /**
     * Setter for the CheckerCommunicator, called when other checker than cbmc is switched to.
     * @param checkerCommunicator the new ResultCheckerCommunicator object
     */
    public void setCheckerCommunicator(ResultCheckerCommunicator checkerCommunicator) {
        this.checkerCommunicator = checkerCommunicator;
    }

}
