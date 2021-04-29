package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessStarterType;

public class CBMCProcessStarterSaverLoaderHelper {
	private static final String PROCESS_STARTER_TYPE_KEY = "process_starter_type";

	// windows
	private static final String PATH_TO_VS_CMD_DEV_KEY = "path_to_vs_cmd_dev";

	public static JSONObject cbmcProcessStarterToJSON(CBMCProcessHandler ps) {
		JSONObject json = new JSONObject();

		CBMCProcessStarterType type = ps.getType();

		json.put(PROCESS_STARTER_TYPE_KEY, type.toString());

		switch (type) {
			case LINUX :
				break;
			case WINDOWS :
				CBMCProcessHandlerWindows casted = (CBMCProcessHandlerWindows) ps;
				json.put(PATH_TO_VS_CMD_DEV_KEY, casted.getVsCmdPath());
				break;
			default :
				break;
		}

		return json;
	}

	public static CBMCProcessHandler cbmcProcessStarterFromJSON(
			JSONObject json) {
		CBMCProcessStarterType type = CBMCProcessStarterType
				.valueOf(json.getString(PROCESS_STARTER_TYPE_KEY));

		switch (type) {
			case LINUX :
				break;
			case WINDOWS :
				CBMCProcessHandlerWindows ps = new CBMCProcessHandlerWindows();
				String pathToVsCmd = json.getString(PATH_TO_VS_CMD_DEV_KEY);
				ps.setVsCmdPath(pathToVsCmd);
				return ps;
			default :
				break;
		}

		return null;
	}

}
