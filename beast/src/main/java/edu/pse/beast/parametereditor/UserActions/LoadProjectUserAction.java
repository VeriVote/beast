package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.saverloader.SaverLoaderInterface;

/**
 *
 * @author Jonas
 */
public class LoadProjectUserAction extends UserAction {
    private final PropertyList propertyList;
    private final CElectionDescriptionEditor cElectionEditor;
    private final ParameterEditor paramEditor;
    private final SaverLoaderInterface saverLoaderIf;
    
    public LoadProjectUserAction(PropertyList propertyList, 
            CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor, 
            SaverLoaderInterface saverLoaderIf) {
        super("load");
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
        this.saverLoaderIf = saverLoaderIf;
    }

    @Override
    public void perform() {
        //TODO: implement
    }
}