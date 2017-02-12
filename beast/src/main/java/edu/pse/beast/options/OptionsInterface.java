package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import toBeImplemented.CheckerList;

public class OptionsInterface {    
    private OptionPresenter presenter;
    
    public BooleanExpEditorOptions getBooleanExpEditorOptions(BooleanExpEditor editor,
            ObjectRefsForBuilder refs) {
        try {
            BooleanExpEditorOptions opts = OptionsSaverLoaderInterface.loadBooleanExpEditorOpts(editor);
            return opts;
        } catch (IOException ex) {
        }
        return new BooleanExpEditorOptions(editor);
    }

    public CElectionEditorOptions getCElectionEditorOptions(CElectionDescriptionEditor editor) {
        try {
            CElectionEditorOptions opts = OptionsSaverLoaderInterface.loadCEditorOpts(editor);
            return opts;
        } catch (Exception ex) {
        }
        CElectionEditorOptions opts = new CElectionEditorOptions(editor);
        return opts;
    }

    public ParametereditorOptions getParameterEditorOptions(LanguageOptions langOpts) {
        return new ParametereditorOptions(langOpts);
    }

    public LanguageOptions getLanguageOptions(StringLoaderInterface stringIf) {
        try {
            return OptionsSaverLoaderInterface.loadLangOpts(stringIf);
        } catch (IOException ex) {
        }
        return new LanguageOptions(stringIf);
    }

    public OptionPresenter getOptionPresenter(ObjectRefsForBuilder refs) {
        if(presenter == null) {
            presenter = new OptionPresenter(refs.getStringIF().getOptionStringResProvider().getOptionStringRes());
            refs.getLanguageOpts().addStringDisplayer(presenter);
        }
        return presenter;
    }
}
