package edu.kit.kastel.formal.beast.api.codegen.typegenerator.vote;

import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteExpHelper {
    private static final String DOT = ".";
    private static final String ACC = "--ACC--";
    private static final String VOTE_VAR = "VOTE_VAR";
    private static final String LIST_MEMBER = "LIST_MEMBER";

    public static String getVarFromVoteAccess(final String voteVarName,
                                              final List<SymbolicVariable> list,
                                              final CodeGenOptions options,
                                              final ElectionTypeCStruct voteStruct) {
        String code = VOTE_VAR + DOT + LIST_MEMBER + ACC;
        String accBrackets = "";
        for (SymbolicVariable var : list) {
            accBrackets += "[" + var.getName() + "]";
        }
        code = code.replaceAll(ACC, accBrackets);
        code = code.replaceAll(VOTE_VAR, voteVarName);
        code = code.replaceAll(VOTE_VAR, voteVarName);
        code = code.replaceAll(LIST_MEMBER, voteStruct.getListName());
        return code;
    }
}
