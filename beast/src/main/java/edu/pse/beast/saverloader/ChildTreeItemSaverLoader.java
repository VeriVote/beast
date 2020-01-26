package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import edu.pse.beast.highlevel.javafx.ChildTreeItemValues;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.saverloader.adapter.InOutTypeAdapter;
import edu.pse.beast.saverloader.adapter.InputTypeAdapter;
import edu.pse.beast.saverloader.adapter.OutputTypeAdapter;
import edu.pse.beast.saverloader.adapter.ResultAdapter;
import edu.pse.beast.saverloader.adapter.SuperclassExclusionStrategy;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 *
 * @author Lukas Stapelbroek
 */
public final class ChildTreeItemSaverLoader
        implements SaverLoader<ChildTreeItemValues> {

    /** The saver loader. */
    private static Gson saverLoader;

    static { // here you have the chance to register typeAdapters
        final GsonBuilder builder = new GsonBuilder();
        builder.addDeserializationExclusionStrategy(
                new SuperclassExclusionStrategy());
        builder.addSerializationExclusionStrategy(
                new SuperclassExclusionStrategy());
        builder.registerTypeAdapter(Result.class, new ResultAdapter());
        builder.registerTypeAdapter(InOutType.class, new InOutTypeAdapter());
        builder.registerTypeAdapter(InputType.class, new InputTypeAdapter());
        builder.registerTypeAdapter(OutputType.class, new OutputTypeAdapter());
        saverLoader = builder.create();
    }

    @Override
    public ChildTreeItemValues createFromSaveString(final String toLoad)
            throws JsonSyntaxException {
        return saverLoader.fromJson(toLoad, ChildTreeItemValues.class);
    }

    @Override
    public String createSaveString(final ChildTreeItemValues childTreeItemValues) {
        return saverLoader.toJson(childTreeItemValues,
                                  ChildTreeItemValues.class);
    }
}
