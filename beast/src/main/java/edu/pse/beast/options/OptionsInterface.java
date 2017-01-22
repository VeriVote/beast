package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.parametereditor.ParameterEditor;
import toBeImplemented.CElectionEditor;
import toBeImplemented.CheckerList;
import toBeImplemented.LanguageOptions;

public class OptionsInterface {

    public BooleanExpEditorOptions getBooleanExpEditorOptions(BooleanExpEditor editor,
            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
        return new BooleanExpEditorOptions("id", editor, booleanExpCodeAreaOptions);
    }

    public CElectionEditorOptions getCElectionEditorOptions(CElectionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        return new CElectionEditorOptions("id", editor, cElectionCodeAreaOptions);
    }

    public ParametereditorOptions getParameterEditorOptions(CheckerOptionElement checker, CheckerList availableChecker, ParameterEditor editor, LanguageOptions langOpts) {
        return new ParametereditorOptions("id", checker, availableChecker, langOpts);
    }

}
