package edu.pse.beast.codearea.useractions;

import edu.pse.beast.codearea.actionlist.Actionlist;
import edu.pse.beast.toolbox.UserAction;

/**
 * this useraction asks the given actionlist to undo the last action
 * @author Holger-Desktop
 */
public class UndoLastUserAction extends UserAction {
    private Actionlist list;
    public UndoLastUserAction(Actionlist list) {
        super("undo");
        this.list = list;
    }

    @Override
    public void perform() {
        list.undoLast();
    }
}