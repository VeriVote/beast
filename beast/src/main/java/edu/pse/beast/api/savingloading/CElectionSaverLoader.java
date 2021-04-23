package edu.pse.beast.api.savingloading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;

public class CElectionSaverLoader {
	private static final int CURRENT_VERSION = 1;

	private static final String VERSION_KEY = "version";
	private static final String NAME_KEY = "name";
	private static final String INPUT_TYPE_KEY = "inputType";
	private static final String OUTPUT_TYPE_KEY = "outputType";
	private static final String VOTING_FUNC_KEY = "votingFunction";
	private static final String VOTING_FUNC_NAME_KEY = "voting_func_name";
	private static final String VOTING_FUNC_CODE_KEY = "code";
	private static final String LOOP_BOUNDS_KEY = "loop_bounds";
	private static final String LOOP_BOUND_FUNCTION_KEY = "loop_bound_function";
	private static final String LOOP_BOUND_INDEX_KEY = "loop_bounds_index";
	private static final String LOOP_BOUND_BOUND_KEY = "loop_bounds_bound";
	private static final String DESCR_UUID_KEY = "descr_uuid";

	private static boolean isVersionCompatible(int version) {
		return true;
	}
	private static JSONObject fromVotingFunction(VotingSigFunction func) {
		JSONObject json = new JSONObject();
		json.put(VOTING_FUNC_NAME_KEY, func.getName());
		json.put(VOTING_FUNC_CODE_KEY, func.getCodeAsString());
		return json;
	}

	private static JSONArray fromLoopBounds(List<LoopBound> loopbounds) {
		JSONArray jsonArray = new JSONArray();
		for (LoopBound b : loopbounds) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(LOOP_BOUND_FUNCTION_KEY, b.getFunctionName());
			jsonObj.put(LOOP_BOUND_INDEX_KEY, b.getIndex());
			jsonObj.put(LOOP_BOUND_BOUND_KEY, b.getBound());
			jsonArray.put(jsonObj);
		}
		return jsonArray;
	}

	private static VotingSigFunction toVotingFunction(JSONObject json,
			VotingInputTypes inputType, VotingOutputTypes outputType) {
		String name = json.getString(VOTING_FUNC_NAME_KEY);
		String code = json.getString(VOTING_FUNC_CODE_KEY);
		VotingSigFunction func = new VotingSigFunction(name, inputType,
				outputType);
		func.setCode(code);
		return func;
	}

	public static void storeCElection(CElectionDescription descr, File f)
			throws IOException {
		JSONObject json = new JSONObject();
		json.put(VERSION_KEY, CURRENT_VERSION);
		json.put(INPUT_TYPE_KEY, descr.getInputType().toString());
		json.put(OUTPUT_TYPE_KEY, descr.getOutputType().toString());
		json.put(VOTING_FUNC_KEY,
				fromVotingFunction(descr.getVotingFunction()));
		json.put(NAME_KEY, descr.getName());
		json.put(LOOP_BOUNDS_KEY, fromLoopBounds(descr.getLoopBounds()));
		json.put(DESCR_UUID_KEY, descr.getUuid());

		String s = json.toString();
		SavingLoadingInterface.writeStringToFile(f, s);
	}

	private static List<LoopBound> loopBoundsFromJsonArray(JSONArray arr) {
		List<LoopBound> loopBounds = new ArrayList<>();
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject json = (JSONObject) arr.get(i);

			String funcName = json.getString(LOOP_BOUND_FUNCTION_KEY);
			int index = json.getInt(LOOP_BOUND_INDEX_KEY);
			String bound = json.getString(LOOP_BOUND_BOUND_KEY);

			loopBounds.add(new LoopBound(funcName, index, bound));
		}

		return loopBounds;
	}

	public static CElectionDescription loadCElection(File f)
			throws NotImplementedException, IOException {
		String s = SavingLoadingInterface.readStringFromFile(f);
		JSONObject json = new JSONObject(s);
		int version = json.getInt(VERSION_KEY);

		if (!isVersionCompatible(version)) {
			throw new NotImplementedException("Version is not compatible");
		}

		VotingInputTypes inputType = VotingInputTypes
				.valueOf(json.getString(INPUT_TYPE_KEY));
		VotingOutputTypes outputType = VotingOutputTypes
				.valueOf(json.getString(OUTPUT_TYPE_KEY));

		String name = json.getString(NAME_KEY);
		String uuid = json.getString(DESCR_UUID_KEY);

		CElectionDescription descr = new CElectionDescription(uuid, name,
				inputType, outputType);
		VotingSigFunction votingFunction = toVotingFunction(
				json.getJSONObject(VOTING_FUNC_KEY), inputType, outputType);
		descr.setVotingFunction(votingFunction);

		descr.setLoopBounds(
				loopBoundsFromJsonArray(json.getJSONArray(LOOP_BOUNDS_KEY)));

		return descr;
	}

}
