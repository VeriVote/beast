package edu.pse.beast.codearea.useractions;

import edu.pse.beast.codearea.actionlist.ActionList;
import edu.pse.beast.toolbox.UserAction;

/**
 * This useraction asks the given actionlist to redo the last action.
 *
 * @author Holger Klein
 */
public class RedoLastUserAction extends UserAction {

    /** The list. */
    private ActionList list;

    /**
     * Instantiates a new redo last user action.
     *
     * @param actList
     *            the act list
     */
    public RedoLastUserAction(final ActionList actList) {
        super("redo");
        this.list = actList;
    }

    @Override
    public void perform() {
        list.redoLast();
    }
}
