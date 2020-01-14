package edu.pse.beast.codearea.useractions;

import edu.pse.beast.codearea.actionlist.Actionlist;
import edu.pse.beast.toolbox.UserAction;

/**
 * this useraction asks the given actionlist to redo the last action
 *
 * @author Holger Klein
 */
public class RedoLastUserAction extends UserAction {
    private Actionlist list;

    public RedoLastUserAction(Actionlist list) {
        super("redo");
        this.list = list;
    }

    @Override
    public void perform() {
        list.redoLast();
    }
}
