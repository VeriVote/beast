package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.Project;

/**
 *
 * @author Jonas
 */
public interface ProjectSource {
    /**
     * Loads Project from file
     * @return Project
     */
    Project loadProject();
    
    void saveProject(Project toBeSaved);
}
