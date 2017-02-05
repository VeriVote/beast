package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.Project;

/**
 * The ProjectSource provides access to saved projects and saves the project the
 * user is currently working on.
 * @author Jonas
 */
public interface ProjectSource {
    /**
     * Loads Project from file
     * @return Project Project that was just loaded
     */
    Project loadProject();
    /**
     * Saves Project to file
     * @param toBeSaved Project that is going to be saved
     */
    void saveProject(Project toBeSaved);
}
