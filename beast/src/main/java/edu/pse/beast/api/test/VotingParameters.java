package edu.pse.beast.api.test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;

/**
 * Generates the C code to initialize a voting
 * struct of given name and type.
 * @author Holger Klein
 *
 */
public interface VotingParameters {

    int getV();

    int getC();

    int getS();

    String genVoteStructInitCode(ElectionTypeCStruct voteInputStruct,
                                 CodeGenOptions options,
                                 GeneratedCodeInfo cbmcGeneratedCodeInfo,
                                 String generatedVarName);

    int getLastElectionNumber();
}
