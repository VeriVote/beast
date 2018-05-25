package edu.pse.beast.saverloader.StaticSaverLoaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.saverLoader.InputTypeAdapter;
import edu.pse.beast.types.saverLoader.OutputTypeAdapter;

public class ElectionDescriptionSaverLoader {
	private static Gson saverLoader;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(InputType.class, new InputTypeAdapter());
		builder.registerTypeAdapter(OutputType.class, new OutputTypeAdapter());
		saverLoader = builder.create();
	}
	
	public String saveElectionDescription(ElectionDescription desc) {
		return saverLoader.toJson(desc);
	}
	
	public ElectionDescription loadElectionDescription(String toLoad) {
		return saverLoader.fromJson(toLoad, ElectionDescription.class);
	}
}
