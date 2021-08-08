package edu.pse.beast.api.descr.c_electiondescription.function;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.to_c.FunctionToC;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VotingSigFunction extends CElectionDescriptionFunction {
    private static final String RESOURCES =
            "/edu/pse/beast/api/descr/c_electiondescription/function/";
    private static final String FILE_PREFIX = "voting_";
    private static final String FILE_ENDING = ".template";

    private static final String DECLARE_KEY = "DECLARATION";
    private static final String RETURN_KEY = "RETURN";

    private static final String BLANK = " ";
    private static final String SQUARE_LEFT = "[";
    private static final String SQUARE_RIGHT = "]";

    private static final String NAME = "NAME";
    private static final String ARG = "ARG";

    private static final String RETURN_NAME = "RETURN_NAME";
    private static final String RETURN_TYPE = "RETURN_TYPE";
    private static final String RESULT_ARR = "RESULT_ARR";

    private static final String UINT = "unsigned int";
    private static final String RESULT_ARRAY_NAME = "result";
    private static final String VOTES_ARRAY_NAME = "votes";
    private static final String CAND_SYMB = "C";
    private static final String SEAT_SYMB = "S";
    private static final String VOTE_SYMB = "V";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private VotingInputTypes inputType;
    private VotingOutputTypes outputType;

    public VotingSigFunction(final String nameString,
                             final VotingInputTypes inType,
                             final VotingOutputTypes outType) {
        super(nameString);
        this.inputType = inType;
        this.outputType = outType;
    }

    private static String arr(final String s) {
        return SQUARE_LEFT + s + SQUARE_RIGHT;
    }

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + FILE_PREFIX + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public final VotingInputTypes getInputType() {
        return inputType;
    }

    public final VotingOutputTypes getOutputType() {
        return outputType;
    }

    public final void setInputType(final VotingInputTypes inType) {
        this.inputType = inType;
    }

    public final void setOutputType(final VotingOutputTypes outType) {
        this.outputType = outType;
    }

    private String getReturnType() {
        assert outputType != null;
        String type = UINT;
        if (VotingOutputTypes.CANDIDATE_LIST.equals(outputType)) {
            type += arr(CAND_SYMB);
        } else if (VotingOutputTypes.PARLIAMENT.equals(outputType)
                || VotingOutputTypes.PARLIAMENT_STACK.equals(outputType)) {
            type += arr(SEAT_SYMB);
        }
        return type;
    }

    private String getArgType() {
        String argType = UINT;
        switch (inputType) {
        case APPROVAL:
            argType += arr(VOTE_SYMB) + arr(CAND_SYMB);
            break;
        case WEIGHTED_APPROVAL:
            argType += arr(VOTE_SYMB) + arr(CAND_SYMB);
            break;
        case PREFERENCE:
            argType += arr(VOTE_SYMB) + arr(CAND_SYMB);
            break;
        case SINGLE_CHOICE:
            argType += arr(VOTE_SYMB);
            break;
        case SINGLE_CHOICE_STACK:
            argType += arr(CAND_SYMB);
            break;
        default:
            break;
        }
        return argType + BLANK + VOTES_ARRAY_NAME;
    }

    // TODO(Holger) This can be moved somewhere else, just do not know where yet.
    // Probably together with the rest of code generation.
    @Override
    public final String getDeclCString(final CodeGenOptions codeGenOptions) {
        final Class<?> c = this.getClass();
        final String votersVarName = codeGenOptions.getCurrentAmountVotersVarName();
        final String candsVarName = codeGenOptions.getCurrentAmountCandsVarName();
        final String seatsVarName = codeGenOptions.getCurrentAmountSeatsVarName();

        final String returnType =
                getReturnType()
                .replaceAll(arr(VOTE_SYMB), votersVarName)
                .replaceAll(arr(CAND_SYMB), candsVarName)
                .replaceAll(arr(SEAT_SYMB), seatsVarName);
        final String arg =
                getArgType()
                .replaceAll(arr(VOTE_SYMB), votersVarName)
                .replaceAll(arr(CAND_SYMB), candsVarName)
                .replaceAll(arr(SEAT_SYMB), seatsVarName);
        final CTypeNameBrackets resultType =
                FunctionToC.votingTypeToC(CElectionVotingType.of(outputType), RESULT_ARRAY_NAME,
                                          votersVarName, candsVarName, seatsVarName);
        return getTemplate(DECLARE_KEY, c)
                .replaceAll(RETURN_TYPE, returnType)
                .replaceAll(ARG, arg)
                .replaceAll(NAME, getName())
                .replaceAll(RESULT_ARR, resultType.generateCode());
    }

    public final String getResultArrayName() {
        return RESULT_ARRAY_NAME;
    }

    public final String getVotesArrayName() {
        return VOTES_ARRAY_NAME;
    }

    @Override
    public final String getReturnText(final CodeGenOptions codeGenOptions) {
        final Class<?> c = this.getClass();
        return getTemplate(RETURN_KEY, c)
                .replaceAll(RETURN_NAME, RESULT_ARRAY_NAME);
    }
}
