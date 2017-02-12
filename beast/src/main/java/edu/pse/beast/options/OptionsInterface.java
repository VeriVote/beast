package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.io.IOException;

public class OptionsInterface {    
    private OptionPresenter presenter;
    private LanguageOptions languageOptions;
    private BooleanExpEditorOptions booleanExpEditorOptions;
    private CElectionEditorOptions cElectionEditorOptions;
    private ParametereditorOptions parametereditorOptions;
    
    public BooleanExpEditorOptions getBooleanExpEditorOptions(BooleanExpEditor editor,
            ObjectRefsForBuilder refs) {
        if(booleanExpEditorOptions == null) {
            try {
            booleanExpEditorOptions = OptionsSaverLoaderInterface.loadBooleanExpEditorOpts(editor);
            } catch (IOException ex) {            
                booleanExpEditorOptions = new BooleanExpEditorOptions(editor);
            }            
        }        
        return booleanExpEditorOptions;
    }

    public CElectionEditorOptions getCElectionEditorOptions(CElectionDescriptionEditor editor) {
        if(cElectionEditorOptions == null) {
            try {
                cElectionEditorOptions = OptionsSaverLoaderInterface.loadCEditorOpts(editor);
            } catch (Exception ex) {
                cElectionEditorOptions = new CElectionEditorOptions(editor);
            }            
        }        
        return cElectionEditorOptions;
    }

    public ParametereditorOptions getParameterEditorOptions(LanguageOptions langOpts) {
        if(parametereditorOptions == null) {
            parametereditorOptions = new ParametereditorOptions(langOpts);            
        }
        return parametereditorOptions;
    }

    public LanguageOptions getLanguageOptions(StringLoaderInterface stringIf) {
        if(languageOptions == null) {
            try {
                languageOptions = OptionsSaverLoaderInterface.loadLangOpts(stringIf);
            } catch (IOException ex) {
                languageOptions = new LanguageOptions(stringIf);
            }            
        }
        return languageOptions;
    }

    public OptionPresenter getOptionPresenter(ObjectRefsForBuilder refs) {
        if(presenter == null) {
            presenter = new OptionPresenter(refs.getStringIF().getOptionStringResProvider().getOptionStringRes());
            refs.getLanguageOpts().addStringDisplayer(presenter);
        }
        return presenter;
    }
}
