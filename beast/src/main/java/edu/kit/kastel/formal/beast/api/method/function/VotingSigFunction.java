package edu.kit.kastel.formal.beast.api.method.function;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CTypeNameBrackets;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionVotingType;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VotingSigFunction extends CElectionDescriptionFunction {
    private static final String FILE_PREFIX = "voting_";

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

    private VotingInputType inputType;
    private VotingOutputType outputType;

    public VotingSigFunction(final String nameString,
                             final VotingInputType inType,
                             final VotingOutputType outType) {
        super(nameString);
        this.inputType = inType;
        this.outputType = outType;
    }

    private static String arr(final String s) {
        return SQUARE_LEFT + s + SQUARE_RIGHT;
    }

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }

    public final VotingInputType getInputType() {
        return inputType;
    }

    public final VotingOutputType getOutputType() {
        return outputType;
    }

    public final void setInputType(final VotingInputType inType) {
        this.inputType = inType;
    }

    public final void setOutputType(final VotingOutputType outType) {
        this.outputType = outType;
    }

    private String getReturnType() {
        assert outputType != null;
        String type = UINT;
        if (VotingOutputType.CANDIDATE_LIST.equals(outputType)) {
            type += arr(CAND_SYMB);
        } else if (VotingOutputType.PARLIAMENT.equals(outputType)
                || VotingOutputType.PARLIAMENT_STACK.equals(outputType)) {
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
        return getTemplate(DECLARE_KEY)
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
        return getTemplate(RETURN_KEY)
                .replaceAll(RETURN_NAME, RESULT_ARRAY_NAME);
    }
}
