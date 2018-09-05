/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 * 
 * @author lukas
 */
public class PropertyDescriptionSaverLoader implements SaverLoader<PreAndPostConditionsDescription> {

	private static Gson saverLoader;

	static { //here you have the chance to register typeAdapters
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(VariableListListener.class, new VariableListListenerAdapter());
		saverLoader = builder.create();
	}

	@Override
	public PreAndPostConditionsDescription createFromSaveString(String toLoad) throws Exception {
		return saverLoader.fromJson(toLoad, PreAndPostConditionsDescription.class);
	}

	@Override
	public String createSaveString(PreAndPostConditionsDescription toSave) {
		return saverLoader.toJson(toSave);
	}
}
