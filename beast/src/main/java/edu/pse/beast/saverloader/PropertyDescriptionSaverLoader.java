package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 *
 * @author Lukas Stapelbroek
 */
public final class PropertyDescriptionSaverLoader
        implements SaverLoader<PreAndPostConditionsDescription> {

    /** The saver loader. */
    private static Gson saverLoader;

    static { // here you have the chance to register typeAdapters
        GsonBuilder builder = new GsonBuilder();
        // builder.registerTypeAdapter(VariableListListener.class, new
        // VariableListListenerAdapter());
        saverLoader = builder.create();
    }

    @Override
    public PreAndPostConditionsDescription createFromSaveString(final String toLoad)
            throws JsonSyntaxException {
        return saverLoader.fromJson(toLoad,
                                    PreAndPostConditionsDescription.class);
    }

    @Override
    public String createSaveString(final PreAndPostConditionsDescription toSave) {
        return saverLoader.toJson(toSave);
    }
}
