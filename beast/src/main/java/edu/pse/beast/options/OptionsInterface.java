package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.parametereditor.ParameterEditor;
import toBeImplemented.CheckerList;

public class OptionsInterface {

    public BooleanExpEditorOptions getBooleanExpEditorOptions(BooleanExpEditor editor,
            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
        return new BooleanExpEditorOptions("id", editor, booleanExpCodeAreaOptions);
    }

    public CElectionEditorOptions getCElectionEditorOptions(CElectionDescriptionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        return new CElectionEditorOptions("id", editor, cElectionCodeAreaOptions);
    }

    public ParametereditorOptions getParameterEditorOptions(CheckerOptionElement checker, CheckerList availableChecker, ParameterEditor editor, LanguageOptions langOpts) {
        return new ParametereditorOptions("id", checker, availableChecker, langOpts);
    }

    public LanguageOptions getLanguageOptions() {
        //TODO implement
        return new LanguageOptions(null, null, null, null);
    }
}
