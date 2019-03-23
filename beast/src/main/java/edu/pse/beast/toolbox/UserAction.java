package edu.pse.beast.toolbox;

/**
 * This interface is implemented by all classes which represent actions the user
 * can perform which cannot be undone
 *
 * @author Holger-Desktop
 */
public abstract class UserAction {
    private final String id;

    /**
     * 
     * @param id the identification
     */
    public UserAction(String id) {
        this.id = id;
    }

    /**
     * 
     * @return the identification
     */
    public String getId() {
        return id;
    }

    /**
     * performs the action of the user
     */
    public abstract void perform();
}