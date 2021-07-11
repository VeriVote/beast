package edu.pse.beast.api.cbmc_run_with_specific_values;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

/**
 * Generates the C code to initialize a voting 
 * struct of given name and type 
 * @author holge
 *
 */
public interface VotingParameters {

    int getV();

    int getC();

    int getS();

    public String generateVoteStructInitCode(
            ElectionTypeCStruct voteInputStruct, CodeGenOptions options,
            CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo,
            String generatedVarName);

    int getHighestVote();
}
