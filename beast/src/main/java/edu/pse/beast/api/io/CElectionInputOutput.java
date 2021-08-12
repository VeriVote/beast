package edu.pse.beast.api.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.io.loopbound.ExtractedCLoopInputOutputHelper;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.CElectionSimpleType;
import edu.pse.beast.api.method.VotingInputType;
import edu.pse.beast.api.method.VotingOutputType;
import edu.pse.beast.api.method.function.CElectionDescriptionFunction;
import edu.pse.beast.api.method.function.SimpleTypeFunction;
import edu.pse.beast.api.method.function.VotingSigFunction;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CElectionInputOutput {
    private static final int CURRENT_VERSION = 1;
    private static final String NOT_COMPATIBLE = "Version is not compatible";

    private static final String VERSION_KEY = "version";
    private static final String NAME_KEY = "name";
    private static final String INPUT_TYPE_KEY = "inputType";
    private static final String OUTPUT_TYPE_KEY = "outputType";
    private static final String VOTING_FUNC_KEY = "votingFunction";

    private static final String VOTING_FUNCTION_ARRAY_KEY = "voting_function_array";
    private static final String VOTING_FUNC_NAME_KEY = "voting_func_name";
    private static final String FUNC_CODE_KEY = "code";

    private static final String SIMPLE_FUNCTION_ARRAY_KEY = "simple_function_array";
    private static final String SIMPLE_FUNC_NAME_KEY = "simple_func_name";
    private static final String SIMPLE_FUNC_ARG_TYPES_KEY = "arg_types";
    private static final String SIMPLE_FUNC_ARG_NAMES_KEY = "arg_names";
    private static final String SIMPLE_FUNC_RETURN_TYPE = "return_types";

    private static final String EXTRACTED_LOOPS_KEY = "extracted_loops";

    private static final String DESCR_UUID_KEY = "descr_uuid";

    private static boolean isVersionCompatible(final int version) {
        return true;
    }

    private static SimpleTypeFunction fromSimpleFunction(final JSONObject json) {
        final String name = json.getString(SIMPLE_FUNC_NAME_KEY);
        final String code = json.getString(FUNC_CODE_KEY);
        final JSONArray argTypesJsonArr = json.getJSONArray(SIMPLE_FUNC_ARG_TYPES_KEY);
        final List<CElectionSimpleType> argTypes = new ArrayList<CElectionSimpleType>();
        for (int i = 0; i < argTypesJsonArr.length(); ++i) {
            argTypes.add(CElectionSimpleType.valueOf(argTypesJsonArr.getString(i)));
        }
        final JSONArray argNamesJsonArr = json.getJSONArray(SIMPLE_FUNC_ARG_NAMES_KEY);
        final List<String> argNames = new ArrayList<String>();
        for (int i = 0; i < argNamesJsonArr.length(); ++i) {
            argNames.add(argNamesJsonArr.getString(i));
        }
        final CElectionSimpleType returnType =
                CElectionSimpleType.valueOf(json.getString(SIMPLE_FUNC_RETURN_TYPE));
        final SimpleTypeFunction simpleFunction =
                new SimpleTypeFunction(name, argTypes, argNames, returnType);
        simpleFunction.setCode(code);
        return simpleFunction;
    }

    private static JSONObject fromSimpleFunction(final SimpleTypeFunction f) {
        final JSONObject json = new JSONObject();
        json.put(SIMPLE_FUNC_NAME_KEY, f.getName());
        json.put(FUNC_CODE_KEY, f.getCode());
        json.put(EXTRACTED_LOOPS_KEY,
                ExtractedCLoopInputOutputHelper
                .fromExtractedLoops(f.getExtractedLoops()));
        json.put(SIMPLE_FUNC_ARG_TYPES_KEY, new JSONArray(f.getArgTypes()));
        json.put(SIMPLE_FUNC_ARG_NAMES_KEY, new JSONArray(f.getArgNames()));
        json.put(SIMPLE_FUNC_RETURN_TYPE, f.getOutputType());
        return json;
    }

    private static JSONArray fromSimpleFunctions(final CElectionDescription descr) {
        final JSONArray array = new JSONArray();
        for (final CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f.getClass().equals(SimpleTypeFunction.class)) {
                array.put(fromSimpleFunction((SimpleTypeFunction) f));
            }
        }
        return array;
    }

    private static List<SimpleTypeFunction> toSimpleFunctions(final JSONArray arr) {
        final List<SimpleTypeFunction> list = new ArrayList<SimpleTypeFunction>();
        for (int i = 0; i < arr.length(); ++i) {
            list.add(fromSimpleFunction(arr.getJSONObject(i)));
        }
        return list;
    }

    private static JSONObject fromVotingFunction(final VotingSigFunction func) {
        final JSONObject json = new JSONObject();
        json.put(VOTING_FUNC_NAME_KEY, func.getName());
        json.put(FUNC_CODE_KEY, func.getCode());
        json.put(EXTRACTED_LOOPS_KEY,
                 ExtractedCLoopInputOutputHelper
                 .fromExtractedLoops(func.getExtractedLoops()));
        return json;
    }

    private static VotingSigFunction toVotingFunction(final JSONObject json,
                                                      final VotingInputType inputType,
                                                      final VotingOutputType outputType) {
        final String name = json.getString(VOTING_FUNC_NAME_KEY);
        final String code = json.getString(FUNC_CODE_KEY);
        final VotingSigFunction func =
                new VotingSigFunction(name, inputType, outputType);
        func.setCode(code);
        final List<ExtractedCLoop> loops =
                ExtractedCLoopInputOutputHelper
                .toExtractedLoops(json.getJSONArray(EXTRACTED_LOOPS_KEY));
        func.setExtractedLoops(loops);
        return func;
    }

    private static JSONArray fromVotingFunctions(final CElectionDescription descr) {
        final JSONArray array = new JSONArray();
        for (final CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                array.put(fromVotingFunction((VotingSigFunction) f));
            }
        }
        return array;
    }

    private static List<VotingSigFunction> toVotingFunctions(final JSONArray array,
                                                             final VotingInputType inputType,
                                                             final VotingOutputType outputType) {
        final List<VotingSigFunction> list = new ArrayList<VotingSigFunction>();
        for (int i = 0; i < array.length(); ++i) {
            list.add(toVotingFunction(array.getJSONObject(i), inputType,
                                      outputType));
        }
        return list;
    }

    public static void storeCElection(final CElectionDescription descr, final File f)
            throws IOException {
        final JSONObject json = new JSONObject();
        json.put(VERSION_KEY, CURRENT_VERSION);
        json.put(INPUT_TYPE_KEY, descr.getInputType().name());
        json.put(OUTPUT_TYPE_KEY, descr.getOutputType().name());

        json.put(VOTING_FUNC_KEY, descr.getVotingFunction().getName());

        json.put(VOTING_FUNCTION_ARRAY_KEY, fromVotingFunctions(descr));
        json.put(SIMPLE_FUNCTION_ARRAY_KEY, fromSimpleFunctions(descr));

        json.put(NAME_KEY, descr.getName());

        json.put(DESCR_UUID_KEY, descr.getUuid());

        final String s = json.toString(2);
        InputOutputInterface.writeStringToFile(f, s);
    }

    public static CElectionDescription loadCElection(final File f)
            throws NotImplementedException, IOException {
        final String s = InputOutputInterface.readStringFromFile(f);
        final JSONObject json = new JSONObject(s);
        final int version = json.getInt(VERSION_KEY);

        if (!isVersionCompatible(version)) {
            throw new NotImplementedException(NOT_COMPATIBLE);
        }

        final VotingInputType inputType =
                VotingInputType.valueOf(json.getString(INPUT_TYPE_KEY));
        final VotingOutputType outputType =
                VotingOutputType.valueOf(json.getString(OUTPUT_TYPE_KEY));

        final String name = json.getString(NAME_KEY);
        final String uuid = json.getString(DESCR_UUID_KEY);

        final CElectionDescription descr =
                new CElectionDescription(uuid, name, inputType, outputType);

        final JSONArray votingFuncArray = json.getJSONArray(VOTING_FUNCTION_ARRAY_KEY);
        final List<VotingSigFunction> votingFuncs =
                toVotingFunctions(votingFuncArray, inputType, outputType);

        List<SimpleTypeFunction> simpleFuncs = new ArrayList<SimpleTypeFunction>();
        if (json.has(SIMPLE_FUNCTION_ARRAY_KEY)) {
            final JSONArray simpleFunctionArray = json.getJSONArray(SIMPLE_FUNCTION_ARRAY_KEY);
            simpleFuncs = toSimpleFunctions(simpleFunctionArray);
        }

        final String votingFunctionName = json.getString(VOTING_FUNC_KEY);
        VotingSigFunction votingFunction = null;

        final List<CElectionDescriptionFunction> allFunctions =
                new ArrayList<CElectionDescriptionFunction>();

        for (final VotingSigFunction func : votingFuncs) {
            allFunctions.add(func);
            if (func.getName().equals(votingFunctionName)) {
                votingFunction = func;
            }
        }
        for (final SimpleTypeFunction func : simpleFuncs) {
            allFunctions.add(func);
        }
        descr.setVotingFunction(votingFunction);
        descr.setFunctions(allFunctions);
        return descr;
    }
}
