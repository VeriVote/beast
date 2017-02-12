package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.io.IOException;
import toBeImplemented.CheckerList;

public class OptionsInterface {    
    private OptionPresenter presenter;
    
    public BooleanExpEditorOptions getBooleanExpEditorOptions(BooleanExpEditor editor,
            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
        return new BooleanExpEditorOptions("id", editor, booleanExpCodeAreaOptions);
    }

    public CElectionEditorOptions getCElectionEditorOptions(CElectionDescriptionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        return new CElectionEditorOptions("id", editor, cElectionCodeAreaOptions);
    }

    public ParametereditorOptions getParameterEditorOptions(LanguageOptions langOpts) {
        return new ParametereditorOptions(langOpts);
    }

    public LanguageOptions getLanguageOptions(StringLoaderInterface stringIf) throws IOException {
        return OptionsSaverLoaderInterface.loadLangOpts(stringIf);
    }

    public OptionPresenter getOptionPresenter(ObjectRefsForBuilder refs) {
        if(presenter == null) {
            presenter = new OptionPresenter(refs.getStringIF().getOptionStringResProvider().getOptionStringRes());
        }
        return presenter;
    }
}
