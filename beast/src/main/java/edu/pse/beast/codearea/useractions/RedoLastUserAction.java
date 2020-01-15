package edu.pse.beast.codearea.useractions;

import edu.pse.beast.codearea.actionlist.ActionList;
import edu.pse.beast.toolbox.UserAction;

/**
 * This useraction asks the given actionlist to redo the last action.
 *
 * @author Holger Klein
 */
public class RedoLastUserAction extends UserAction {
    private ActionList list;

    public RedoLastUserAction(final ActionList actList) {
        super("redo");
        this.list = actList;
    }

    @Override
    public void perform() {
        list.redoLast();
    }
}
