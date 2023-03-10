package edu.kit.kastel.formal.beast.api.test;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;

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
