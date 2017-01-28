package edu.pse.beast.booleanexpeditor.UserActions;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.SaveBeforeChangeHandler;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;
    private SaverLoaderInterface saverLoaderInterface;
    private SaveBeforeChangeHandler saveBeforeChangeHandler;
    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor, SaverLoaderInterface saverLoaderInterface,
                               SaveBeforeChangeHandler saveBeforeChangeHandler) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
        this.saverLoaderInterface = saverLoaderInterface;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
