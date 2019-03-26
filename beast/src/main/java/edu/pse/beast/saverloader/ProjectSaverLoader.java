package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.toolbox.Project;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 *
 * @author Lukas Stapelbroek
 */
public class ProjectSaverLoader implements SaverLoader<Project> {
    private static Gson saverLoader;

    static { // here you have the chance to register typeAdapters
        GsonBuilder builder = new GsonBuilder();
        saverLoader = builder.create();
    }

    @Override
    public Project createFromSaveString(String toLoad) throws Exception { // TODO
        System.out.println("TODO: save string project");
        return saverLoader.fromJson(toLoad, Project.class);
    }

    @Override
    public String createSaveString(Project toSave) { // TODO
        System.out.println("TODO: save string project");
        return saverLoader.toJson(toSave);
    }
}