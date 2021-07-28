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
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.descr.c_electiondescription.to_c.FunctionToC;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * Generates the entire C code needed to run a cbmc check
 * on. This means, it generates the structs passed to the
 * voting function, Changes the voting function to take those
 * structs, and the main function which calls the voting function
 * and implements the cbmc checks.
 *
 * @author Holger Klein
 *
 */
public class CBMCCodeGenerator {
    private static final String BLANK = " ";
    private static final String SEMICOLON = ";";
    private static final String ASSUME = "assume";
    private static final String ASSERT = "assert";

    private static final String STDLIB_H = "stdlib.h";
    private static final String STDINT_H = "stdint.h";
    private static final String ASSERT_H = "assert.h";

    private static final String INVALID_VOTE = "INVALID_VOTE";
    private static final String INVALID_RESULT = "INVALID_RESULT";

    private static final String ASSUME_X = ASSUME + "(x)";
    private static final String CPROVER_ASSUME_X = "__CPROVER_" + ASSUME_X;

    private static final String UNSIGNED_INT = "unsigned int";
    private static final String INT = "int";
    private static final String CBMC_UINT_FUNC_NAME = "nondet_uint";
    private static final String CBMC_INT_FUNC_NAME = "nondet_int";

    private static final String COLOUR = "0xFFFFFFFE";

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

    private static CFile prepareCodeFile(final CElectionDescription descr,
                                         final CodeGenOptions options) {
        final CFile created = new CFile();
        created.include(STDLIB_H);
        created.include(STDINT_H);
        created.include(ASSERT_H);

        created.define(ASSUME_X, CPROVER_ASSUME_X);
        created.define(INVALID_VOTE, COLOUR);
        created.define(INVALID_RESULT, COLOUR);

        created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
        created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());

        addSimpleFunctionDecls(descr, created, options);
        addSimpleFunctionDefs(descr, created, options);

        options.setCbmcAssumeName(ASSUME);
        options.setCbmcAssertName(ASSERT);
        options.setCbmcNondetUintName(CBMC_UINT_FUNC_NAME);
        return created;
    }

    public static CBMCGeneratedCodeInfo
                generateCodeForCBMCPropertyTest(final CElectionDescription descr,
                                                final PreAndPostConditionsDescription propDescr,
                                                final CodeGenOptions options,
                                                final InitVoteHelper initVoteHelper,
                                                final Class<?> c) {
        final CFile created = prepareCodeFile(descr, options);

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
        final CodeGenLoopBoundHandler loopBoundHandler = descr.generateLoopBoundHandler();
        final CFunction.Input input =
                new CFunction.Input(descr.getInputType(), voteInputStruct, votingStructVarName);
        final CFunction.Output output =
                new CFunction.Output(descr.getOutputType(), voteResultStruct, resultStructVarName);

        created.addFunction(
                votingSigFuncToPlainCFunc(descr.getVotingFunction(), input,
                                          output, options, loopBoundHandler, c));
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

        final CFunction.PropertyExpressions expressions =
                new CFunction.PropertyExpressions(preAstData, postAstData,
                                                  propDescr.getCbmcVariables());
        final CFunction.VotingFunction votingFunction =
                new CFunction.VotingFunction(voteInputStruct, voteResultStruct,
                                             descr.getInputType(), descr.getOutputType(),
                                             descr.getVotingFunction().getName());

        final CFunction mainFunction =
                CBMCMainGenerator.main(expressions, votingFunction,
                                       options, loopBoundHandler,
                                       cbmcGeneratedCode, initVoteHelper);
        created.addFunction(mainFunction);
        cbmcGeneratedCode.setCode(created.generateCode());
        loopBoundHandler.finishAddedLoopbounds();
        cbmcGeneratedCode.setLoopboundHandler(loopBoundHandler);
        return cbmcGeneratedCode;
    }

    private static CFunction
                votingSigFuncToPlainCFunc(final VotingSigFunction func,
                                          final CFunction.Input input,
                                          final CFunction.Output output,
                                          final CodeGenOptions options,
                                          final CodeGenLoopBoundHandler loopBoundHandler,
                                          final Class<?> c) {
        final String structArg =
                input.struct.getStruct().getName()
                + BLANK + input.structVarName;
        final String currentAmtVoterArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + BLANK + options.getCurrentAmountVotersVarName();
        final String currentAmtCandArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + BLANK + options.getCurrentAmountCandsVarName();
        final String currentAmtSeatArg =
                TypeManager.simpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT)
                + BLANK + options.getCurrentAmountSeatsVarName();

        final List<String> votingFuncArguments =
                List.of(structArg, currentAmtVoterArg, currentAmtCandArg, currentAmtSeatArg);
        final CFunction created =
                new CFunction(func.getName(), votingFuncArguments,
                              output.struct.getStruct().getName());

        final List<String> code = new ArrayList<>();
        final VotingFunctionHelper.CNames votingNames =
                new VotingFunctionHelper.CNames(func.getName(), func.getVotesArrayName(),
                                                input.structVarName);
        code.add(VotingFunctionHelper.generateVoteArrayCopy(votingNames, input.type, input.struct,
                                                            options, loopBoundHandler, c));
        code.add(FunctionToC
                .votingTypeToC(CElectionVotingType.of(func.getOutputType()),
                                                      func.getResultArrayName(),
                                                      options.getCurrentAmountVotersVarName(),
                                                      options.getCurrentAmountCandsVarName(),
                                                      options.getCurrentAmountSeatsVarName())
                                .generateCode() + SEMICOLON);

        code.add("// user generated code");
        code.addAll(func.getCodeAsList());
        code.add("// end user generated code");

        final VotingFunctionHelper.CNames resultNames =
                new VotingFunctionHelper.CNames(func.getName(), func.getResultArrayName(),
                                                output.structVarName);
        code.add(VotingFunctionHelper.generateVoteResultCopy(resultNames,
                                                             output.type, output.struct,
                                                             options, loopBoundHandler, c));
        code.add("return" + BLANK + output.structVarName + SEMICOLON);
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
