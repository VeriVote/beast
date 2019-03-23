package edu.pse.beast.highlevel.javafx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.options.OptionsNew;
import edu.pse.beast.saverloader.SaverLoader;

public class OptionsSaverLoader extends edu.pse.beast.codeareajavafx.SaverLoader implements SaverLoader<OptionsNew> {

	public OptionsSaverLoader(String fileEnding, String fileExtensionDescription) {
		super(fileEnding, fileExtensionDescription);
		// TODO Auto-generated constructor stub
	}

	private static Gson saverLoader;

	static { // here you have the chance to register typeAdapters
		GsonBuilder builder = new GsonBuilder();
		saverLoader = builder.create();
	}

	@Override
	public OptionsNew createFromSaveString(String toLoad) throws Exception {
		return saverLoader.fromJson(toLoad, OptionsNew.class);
	}

	@Override
	public String createSaveString(OptionsNew toSave) {
		return saverLoader.toJson(toSave, OptionsNew.class);
	}
	
}
