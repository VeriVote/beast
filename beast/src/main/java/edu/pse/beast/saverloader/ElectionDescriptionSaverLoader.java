package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.types.CommonHelpMethods;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.saverLoader.CommonHelpMethodsAdapter;
import edu.pse.beast.types.saverLoader.InputTypeAdapter;
import edu.pse.beast.types.saverLoader.OutputTypeAdapter;

/**
 * 
 * @author lukas
 *
 */
public class ElectionDescriptionSaverLoader implements SaverLoader<ElectionDescription> {
	private static Gson saverLoader;
	
	static { //here you have the chance to register typeAdapters
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(InputType.class, new InputTypeAdapter());
		builder.registerTypeAdapter(OutputType.class, new OutputTypeAdapter());
		builder.registerTypeAdapter(CommonHelpMethods.class, new CommonHelpMethodsAdapter());
		saverLoader = builder.create();
	}
	
	@Override
	public ElectionDescription createFromSaveString(String toLoad) {
		return saverLoader.fromJson(toLoad, ElectionDescription.class);
	}
	
	@Override
	public String createSaveString(ElectionDescription toSave) {
		return saverLoader.toJson(toSave);
	}
	
}
