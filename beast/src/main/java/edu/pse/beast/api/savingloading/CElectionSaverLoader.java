package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.savingloading.loopbound.ExtractedCLoopSaverLoaderHelper;

public class CElectionSaverLoader {
    private static final int CURRENT_VERSION = 1;

    private static final String VERSION_KEY = "version";
    private static final String NAME_KEY = "name";
    private static final String INPUT_TYPE_KEY = "inputType";
    private static final String OUTPUT_TYPE_KEY = "outputType";
    private static final String VOTING_FUNC_KEY = "votingFunction";

    private static final String VOTING_FUNCTION_ARRAY_KEY = "voting_function_array";
    private static final String VOTING_FUNC_NAME_KEY = "voting_func_name";
    private static final String VOTING_FUNC_CODE_KEY = "code";

    private static final String SIMPLE_FUNCTION_ARRAY_KEY = "simple_function_array";
    private static final String SIMPLE_FUNC_NAME_KEY = "simple_func_name";
    private static final String SIMPLE_FUNC_CODE_KEY = "code";
    private static final String SIMPLE_FUNC_ARG_TYPES_KEY = "arg_types";
    private static final String SIMPLE_FUNC_ARG_NAMES_KEY = "arg_names";
    private static final String SIMPLE_FUNC_RETURN_TYPE = "return_types";

    private static final String EXTRACTED_LOOPS_KEY = "extracted_loops";

    private static final String DESCR_UUID_KEY = "descr_uuid";

    private static boolean isVersionCompatible(int version) {
        return true;
    }

    private static SimpleTypeFunction fromSimpleFunction(JSONObject json) {
        String name = json.getString(SIMPLE_FUNC_NAME_KEY);
        String code = json.getString(SIMPLE_FUNC_CODE_KEY);
        JSONArray argTypesJsonArr = json
                .getJSONArray(SIMPLE_FUNC_ARG_TYPES_KEY);
        List<CElectionSimpleTypes> argTypes = new ArrayList<>();
        for (int i = 0; i < argTypesJsonArr.length(); ++i) {
            argTypes.add(
                    CElectionSimpleTypes.valueOf(argTypesJsonArr.getString(i)));
        }
        JSONArray argNamesJsonArr = json
                .getJSONArray(SIMPLE_FUNC_ARG_NAMES_KEY);
        List<String> argNames = new ArrayList<>();
        for (int i = 0; i < argNamesJsonArr.length(); ++i) {
            argNames.add(argNamesJsonArr.getString(i));
        }
        CElectionSimpleTypes returnType = CElectionSimpleTypes
                .valueOf(json.getString(SIMPLE_FUNC_RETURN_TYPE));
        SimpleTypeFunction simpleFunction = new SimpleTypeFunction(name,
                argTypes, argNames, returnType);
        simpleFunction.setCode(code);
        return simpleFunction;
    }

    private static List<SimpleTypeFunction> toSimpleFunctions(JSONArray arr) {
        List<SimpleTypeFunction> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); ++i) {
            list.add(fromSimpleFunction(arr.getJSONObject(i)));
        }
        return list;
    }

    private static JSONObject fromVotingFunction(VotingSigFunction func) {
        JSONObject json = new JSONObject();
        json.put(VOTING_FUNC_NAME_KEY, func.getName());
        json.put(VOTING_FUNC_CODE_KEY, func.getCode());
        json.put(EXTRACTED_LOOPS_KEY, ExtractedCLoopSaverLoaderHelper
                .fromExtractedLoops(func.getExtractedLoops()));
        return json;
    }

    private static VotingSigFunction toVotingFunction(JSONObject json,
            VotingInputTypes inputType, VotingOutputTypes outputType) {
        String name = json.getString(VOTING_FUNC_NAME_KEY);
        String code = json.getString(VOTING_FUNC_CODE_KEY);
        VotingSigFunction func = new VotingSigFunction(name, inputType,
                outputType);
        func.setCode(code);
        List<ExtractedCLoop> loops = ExtractedCLoopSaverLoaderHelper
                .toExtractedLoops(json.getJSONArray(EXTRACTED_LOOPS_KEY));
        func.setExtractedLoops(loops);
        return func;
    }

    private static JSONArray fromVotingFunctions(CElectionDescription descr) {
        JSONArray array = new JSONArray();

        for (CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                array.put(fromVotingFunction((VotingSigFunction) f));
            }
        }

        return array;
    }

    private static JSONObject fromSimpleFunction(SimpleTypeFunction f) {
        JSONObject json = new JSONObject();

        json.put(SIMPLE_FUNC_NAME_KEY, f.getName());
        json.put(SIMPLE_FUNC_CODE_KEY, f.getCode());
        json.put(EXTRACTED_LOOPS_KEY, ExtractedCLoopSaverLoaderHelper
                .fromExtractedLoops(f.getExtractedLoops()));

        json.put(SIMPLE_FUNC_ARG_TYPES_KEY, new JSONArray(f.getArgTypes()));
        json.put(SIMPLE_FUNC_ARG_NAMES_KEY, new JSONArray(f.getArgNames()));
        json.put(SIMPLE_FUNC_RETURN_TYPE, f.getOutputType());

        return json;
    }

    private static JSONArray fromSimpleFunctions(CElectionDescription descr) {
        JSONArray array = new JSONArray();

        for (CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f.getClass().equals(SimpleTypeFunction.class)) {
                array.put(fromSimpleFunction((SimpleTypeFunction) f));
            }
        }

        return array;
    }

    private static List<VotingSigFunction> toVotingFunctions(JSONArray array,
            VotingInputTypes inputType, VotingOutputTypes outputType) {
        List<VotingSigFunction> list = new ArrayList();

        for (int i = 0; i < array.length(); ++i) {
            list.add(toVotingFunction(array.getJSONObject(i), inputType,
                    outputType));
        }

        return list;
    }

    public static void storeCElection(CElectionDescription descr, File f)
            throws IOException {
        JSONObject json = new JSONObject();
        json.put(VERSION_KEY, CURRENT_VERSION);
        json.put(INPUT_TYPE_KEY, descr.getInputType().toString());
        json.put(OUTPUT_TYPE_KEY, descr.getOutputType().toString());

        json.put(VOTING_FUNC_KEY, descr.getVotingFunction().getName());

        json.put(VOTING_FUNCTION_ARRAY_KEY, fromVotingFunctions(descr));
        json.put(SIMPLE_FUNCTION_ARRAY_KEY, fromSimpleFunctions(descr));

        json.put(NAME_KEY, descr.getName());

        json.put(DESCR_UUID_KEY, descr.getUuid());

        String s = json.toString();
        SavingLoadingInterface.writeStringToFile(f, s);
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

        JSONArray votingFuncArray = json
                .getJSONArray(VOTING_FUNCTION_ARRAY_KEY);
        List<VotingSigFunction> votingFuncs = toVotingFunctions(votingFuncArray,
                inputType, outputType);

        List<SimpleTypeFunction> simpleFuncs = new ArrayList<>();
        if (json.has(SIMPLE_FUNCTION_ARRAY_KEY)) {
            JSONArray simpleFunctionArray = json
                    .getJSONArray(SIMPLE_FUNCTION_ARRAY_KEY);
            simpleFuncs = toSimpleFunctions(simpleFunctionArray);
        }

        String votingFunctionName = json.getString(VOTING_FUNC_KEY);
        VotingSigFunction votingFunction = null;

        List<CElectionDescriptionFunction> allFunctions = new ArrayList();

        for (VotingSigFunction func : votingFuncs) {
            allFunctions.add(func);
            if (func.getName().equals(votingFunctionName)) {
                votingFunction = func;
            }
        }
        for (SimpleTypeFunction func : simpleFuncs) {
            allFunctions.add(func);
        }
        descr.setVotingFunction(votingFunction);
        descr.setFunctions(allFunctions);

        return descr;
    }

}
