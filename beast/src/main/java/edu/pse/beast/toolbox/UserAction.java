package edu.pse.beast.toolbox;

/**
 * This interface is implemented by all classes which represent actions the user
 * can perform which cannot be undone.
 *
 * @author Holger Klein
 */
public abstract class UserAction {

    /** The id. */
    private final String id;

    /**
     * Instantiates a new user action.
     *
     * @param idString the identification
     */
    public UserAction(final String idString) {
        this.id = idString;
    }

    /**
     * Gets the id.
     *
     * @return the identification
     */
    public String getId() {
        return id;
    }

    /**
     * Performs the action of the user.
     */
    public abstract void perform();
}
