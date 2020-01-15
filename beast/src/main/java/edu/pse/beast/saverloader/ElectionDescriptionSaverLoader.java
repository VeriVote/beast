package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.saverloader.adapter.InOutTypeAdapter;
import edu.pse.beast.saverloader.adapter.InputTypeAdapter;
import edu.pse.beast.saverloader.adapter.OutputTypeAdapter;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Lukas Stapelbroek
 */
public class ElectionDescriptionSaverLoader implements SaverLoader<ElectionDescription> {
    private static Gson saverLoader;

    static { // here you have the chance to register typeAdapters
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(InOutType.class, new InOutTypeAdapter());
        builder.registerTypeAdapter(InputType.class, new InputTypeAdapter());
        builder.registerTypeAdapter(OutputType.class, new OutputTypeAdapter());
        saverLoader = builder.create();
    }

    @Override
    public ElectionDescription createFromSaveString(final String toLoad)
            throws JsonSyntaxException {
        return saverLoader.fromJson(toLoad, ElectionDescription.class);
    }

    @Override
    public String createSaveString(final ElectionDescription toSave) {
        return saverLoader.toJson(toSave);
    }
}
