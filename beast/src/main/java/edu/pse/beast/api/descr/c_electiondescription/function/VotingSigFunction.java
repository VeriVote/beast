package edu.pse.beast.api.descr.c_electiondescription.function;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.to_c.FunctionToC;

public class VotingSigFunction extends CElectionDescriptionFunction {
    private static final String BLANK = " ";
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
        return "[" + s + "]";
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
        final String type;
        switch (outputType) {
        case CANDIDATE_LIST:
            type = UINT + BLANK + arr(CAND_SYMB);
            break;
        case PARLIAMENT:
            type = UINT + BLANK + arr(SEAT_SYMB);
            break;
        case PARLIAMENT_STACK:
            type = UINT + BLANK + arr(SEAT_SYMB);
            break;
        case SINGLE_CANDIDATE:
            type = UINT + BLANK;
            break;
        default:
            type = "";
        }
        return type;
    }

    private String getArgType() {
        final String argType;
        switch (inputType) {
        case APPROVAL:
            argType = UINT + arr(VOTE_SYMB) + arr(CAND_SYMB) + BLANK + VOTES_ARRAY_NAME;
            break;
        case WEIGHTED_APPROVAL:
            argType = UINT + arr(VOTE_SYMB) + arr(CAND_SYMB) + BLANK + VOTES_ARRAY_NAME;
            break;
        case PREFERENCE:
            argType = UINT + arr(VOTE_SYMB) + arr(CAND_SYMB) + BLANK + VOTES_ARRAY_NAME;
            break;
        case SINGLE_CHOICE:
            argType = UINT + arr(VOTE_SYMB) + BLANK + VOTES_ARRAY_NAME;
            break;
        case SINGLE_CHOICE_STACK:
            argType = UINT + arr(CAND_SYMB) + BLANK + VOTES_ARRAY_NAME;
            break;
        default:
            argType = "";
        }
        return argType;
    }

    // TODO(Holger) This can be moves somewhere else, just dont know where yet.
    // Probably together with the rest of code generation.
    @Override
    public final String getDeclCString(final CodeGenOptions codeGenOptions) {
        final String template =
                RETURN_TYPE + BLANK + NAME + "(" + ARG + ") {\n" + "    " + RESULT_ARR + ";";

        String returnType = getReturnType();
        returnType = returnType.replaceAll(arr(VOTE_SYMB),
                codeGenOptions.getCurrentAmountVotersVarName());
        returnType = returnType.replaceAll(arr(CAND_SYMB),
                codeGenOptions.getCurrentAmountCandsVarName());
        returnType = returnType.replaceAll(arr(SEAT_SYMB),
                codeGenOptions.getCurrentAmountSeatsVarName());

        String arg = getArgType();
        arg = arg.replaceAll(arr(VOTE_SYMB),
                codeGenOptions.getCurrentAmountVotersVarName());
        arg = arg.replaceAll(arr(CAND_SYMB),
                codeGenOptions.getCurrentAmountCandsVarName());
        arg = arg.replaceAll(arr(SEAT_SYMB),
                codeGenOptions.getCurrentAmountSeatsVarName());

        final CTypeNameBrackets resultType =
                FunctionToC.votingTypeToC(CElectionVotingType.of(outputType), RESULT_ARRAY_NAME,
                                          codeGenOptions.getCurrentAmountVotersVarName(),
                                          codeGenOptions.getCurrentAmountCandsVarName(),
                                          codeGenOptions.getCurrentAmountSeatsVarName());

        return template.replaceAll(RETURN_TYPE, returnType)
                       .replaceAll(ARG, arg).replaceAll(NAME, getName())
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
        return "    return " + RETURN_NAME + ";\n}".replaceAll(RETURN_NAME, RESULT_ARRAY_NAME);
    }

}
