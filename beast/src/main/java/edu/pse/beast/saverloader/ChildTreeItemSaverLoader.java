package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.highlevel.javafx.ChildTreeItemValues;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.saverloader.adapter.CommonHelpMethodsAdapter;
import edu.pse.beast.saverloader.adapter.InputTypeAdapter;
import edu.pse.beast.saverloader.adapter.OutputTypeAdapter;
import edu.pse.beast.saverloader.adapter.ResultAdapter;
import edu.pse.beast.saverloader.adapter.SuperclassExclusionStrategy;
import edu.pse.beast.types.CommonHelpMethods;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 * 
 * @author lukas
 */
public class ChildTreeItemSaverLoader implements SaverLoader<ChildTreeItemValues> {

    private static Gson saverLoader;

    static { // here you have the chance to register typeAdapters
        GsonBuilder builder = new GsonBuilder();
        builder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
        builder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
        builder.registerTypeAdapter(Result.class, new ResultAdapter());
        builder.registerTypeAdapter(InputType.class, new InputTypeAdapter());
        builder.registerTypeAdapter(OutputType.class, new OutputTypeAdapter());
        builder.registerTypeAdapter(CommonHelpMethods.class, new CommonHelpMethodsAdapter());
        saverLoader = builder.create();
    }

    @Override
    public ChildTreeItemValues createFromSaveString(String toLoad) throws Exception {
        return saverLoader.fromJson(toLoad, ChildTreeItemValues.class);
    }

    @Override
    public String createSaveString(ChildTreeItemValues childTreeItemValues) {
        return saverLoader.toJson(childTreeItemValues, ChildTreeItemValues.class);
    }
}
