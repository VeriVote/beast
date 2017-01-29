package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.ChangeHandler;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;
    private SaverLoaderInterface saverLoaderInterface;
    private ChangeHandler changeHandler;
    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor, SaverLoaderInterface saverLoaderInterface,
                               ChangeHandler changeHandler) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
        this.saverLoaderInterface = saverLoaderInterface;
        this.changeHandler = changeHandler;
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
