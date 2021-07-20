package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFile;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.VotingFunctionHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.descr.c_electiondescription.to_c.FunctionToC;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * Generates the entire C code needed to run a cbmc check
 * on. This means, it generates the structs passed to the
 * voting function, Changes the voting funciton to take those
 * structs, and the main function which calls the voting function
 * and implements the cbmc checks.
 * @author Holger Klein
 *
 */
public class CBMCCodeGenerator {

    private static final String STDLIB = "stdlib.h";
    private static final String STDINT = "stdint.h";
    private static final String ASSERT = "assert.h";

    private static final String INVALID_VOTE = "INVALID_VOTE";
    private static final String INVALID_RESULT = "INVALID_RESULT";

    private static final String ASSUME = "assume(x)";
    private static final String CPROVER_ASSUME = "__CPROVER_assume(x)";

    private static final String UNSIGNED_INT = "unsigned int";
    private static final String INT = "int";
    private static final String CBMC_UINT_FUNC_NAME = "nondet_uint";
    private static final String CBMC_INT_FUNC_NAME = "nondet_int";

    private static void addSimpleFunctionDecls(final CElectionDescription descr,
                                               final CFile cfile,
                                               final CodeGenOptions options) {
        for (final CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f != descr.getVotingFunction()) {
                cfile.declare(f.getDeclCString(options));
            }
        }
    }

    private static void addSimpleFunctionDefs(final CElectionDescription descr,
                                              final CFile cfile,
                                              final CodeGenOptions options) {
        for (final CElectionDescriptionFunction f : descr.getFunctions()) {
            if (f != descr.getVotingFunction()) {
                cfile.addFunction(((SimpleTypeFunction) f).toCFunc());
            }
        }
    }

    public static CBMCGeneratedCodeInfo
                generateCodeForCBMCPropertyTest(final CElectionDescription descr,
                                                final PreAndPostConditionsDescription propDescr,
                                                final CodeGenOptions options,
                                                final InitVoteHelper initVoteHelper) {
        final CFile created = new CFile();
        created.include(STDLIB);
        created.include(STDINT);
        created.include(ASSERT);

        created.define(ASSUME, CPROVER_ASSUME);
        created.define(INVALID_VOTE, "0xFFFFFFFE");
        created.define(INVALID_RESULT, "0xFFFFFFFE");

        created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
        created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());

        addSimpleFunctionDecls(descr, created, options);
        addSimpleFunctionDefs(descr, created, options);

        options.setCbmcAssumeName("assume");
        options.setCbmcAssertName("assert");
        options.setCbmcNondetUintName(CBMC_UINT_FUNC_NAME);

        final CElectionVotingType votesNakedArr =
                CElectionVotingType.of(descr.getVotingFunction().getInputType());
        final CElectionVotingType resultNakedArr =
                CElectionVotingType.of(descr.getVotingFunction().getOutputType());

        final ElectionTypeCStruct voteInputStruct =
                votingTypeToCStruct(votesNakedArr, "VoteStruct",
                                    "votes", "amtVotes", options);
        final ElectionTypeCStruct voteResultStruct =
                votingTypeToCStruct(resultNakedArr, "VoteResultStruct",
                                    "result", "amtResult",
                                    options);
        created.addStructDef(voteInputStruct.getStruct());
        created.addStructDef(voteResultStruct.getStruct());
        final String votingStructVarName = "voteStruct";
        final String resultStructVarName = "resultStruct";
        final CodeGenLoopBoundHandler loopBoundHandler =
                descr.generateLoopBoundHandler();

        created.addFunction(
                votingSigFuncToPlainCFunc(descr.getVotingFunction(),
                                          descr.getInputType(),
                                          descr.getOutputType(),
                                          voteInputStruct, voteResultStruct,
                                          options, loopBoundHandler,
                                          votingStructVarName, resultStructVarName));
        final BooleanExpASTData preAstData =
                BooleanCodeToAST.generateAST(propDescr.getPreConditionsDescription().getCode(),
                                             propDescr.getCbmcVariables());
        final BooleanExpASTData postAstData =
                BooleanCodeToAST.generateAST(propDescr.getPostConditionsDescription().getCode(),
                                             propDescr.getCbmcVariables());

        final CBMCGeneratedCodeInfo cbmcGeneratedCode = new CBMCGeneratedCodeInfo();
        cbmcGeneratedCode.setVotesAmtMemberVarName(voteInputStruct.getAmtName());
        cbmcGeneratedCode.setVotesListMemberVarName(voteInputStruct.getListName());
        cbmcGeneratedCode.setResultAmtMemberVarName(voteResultStruct.getAmtName());
        cbmcGeneratedCode.setResultListMemberVarName(voteResultStruct.getListName());

        final CFunction mainFunction =
                CBMCMainGenerator.main(preAstData, postAstData,
                                       propDescr.getCbmcVariables(),
                                       voteInputStruct, voteResultStruct,
                                       descr.getInputType(), descr.getOutputType(),
                                       options, loopBoundHandler,
                                       descr.getVotingFunction().getName(),
                                       cbmcGeneratedCode, initVoteHelper);
        created.addFunction(mainFunction);
        cbmcGeneratedCode.setCode(created.generateCode());
        loopBoundHandler.finishAddedLoopbounds();
        cbmcGeneratedCode.setLoopboundHandler(loopBoundHandler);
        return cbmcGeneratedCode;
    }

    private static CFunction
                votingSigFuncToPlainCFunc(final VotingSigFunction func,
                                          final VotingInputTypes votingInputType,
                                          final VotingOutputTypes votingOutputType,
                                          final ElectionTypeCStruct inputStruct,
                                          final ElectionTypeCStruct outputStruct,
                                          final CodeGenOptions options,
                                          final CodeGenLoopBoundHandler loopBoundHandler,
                                          final String votingStructVarName,
                                          final String resultStructVarName) {
        final String structArg = inputStruct.getStruct().getName() + " "
                + votingStructVarName;
        final String currentAmtVoterArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + " " + options.getCurrentAmountVotersVarName();
        final String currentAmtCandArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + " " + options.getCurrentAmountCandsVarName();
        final String currentAmtSeatArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + " " + options.getCurrentAmountSeatsVarName();

        final List<String> votingFuncArguments =
                List.of(structArg, currentAmtVoterArg, currentAmtCandArg, currentAmtSeatArg);
        final CFunction created =
                new CFunction(func.getName(), votingFuncArguments,
                              outputStruct.getStruct().getName());

        final List<String> code = new ArrayList<>();
        code.add(VotingFunctionHelper.generateVoteArrayCopy(func.getName(),
                                                            func.getVotesArrayName(),
                                                            votingStructVarName,
                                                            votingInputType,
                                                            inputStruct, options,
                                                            loopBoundHandler));
        code.add(FunctionToC
                .votingTypeToC(CElectionVotingType.of(func.getOutputType()),
                                                      func.getResultArrayName(),
                                                      options.getCurrentAmountVotersVarName(),
                                                      options.getCurrentAmountCandsVarName(),
                                                      options.getCurrentAmountSeatsVarName())
                                .generateCode() + ";");

        code.add("//user generated code");
        code.addAll(func.getCodeAsList());
        code.add("//end user generated code");

        code.add(VotingFunctionHelper.generateVoteResultCopy(func.getName(),
                                                             func.getResultArrayName(),
                                                             resultStructVarName,
                                                             votingOutputType,
                                                             outputStruct,
                                                             options,
                                                             loopBoundHandler));
        code.add("return " + resultStructVarName + ";");
        created.setCode(code);
        return created;
    }

    private static ElectionTypeCStruct
                votingTypeToCStruct(final CElectionVotingType resultNakedArr,
                                    final String structName,
                                    final String listName,
                                    final String amtName,
                                    final CodeGenOptions codeGenOptions) {
        final CTypeNameBrackets listMember =
                FunctionToC.votingTypeToC(resultNakedArr, listName,
                                          codeGenOptions.getCbmcAmountMaxVotersVarName(),
                                          codeGenOptions.getCbmcAmountMaxCandsVarName(),
                                          codeGenOptions.getCbmcAmountMaxSeatsVarName());
        final String counterType =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT);
        final CTypeNameBrackets counterMember = new CTypeNameBrackets(counterType, amtName, "");
        final List<CTypeNameBrackets> members = List.of(listMember, counterMember);
        final CStruct cstruct = new CStruct(structName, members);
        return new ElectionTypeCStruct(resultNakedArr, cstruct, listName, amtName);
    }
}
