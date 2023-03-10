package edu.kit.kastel.formal.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.VotingFunctionHelper;
import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanCodeToAST;
import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanExpASTData;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CFile;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CFunction;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CStruct;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CTypeNameBrackets;
import edu.kit.kastel.formal.beast.api.codegen.init.InitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.method.CElectionSimpleType;
import edu.kit.kastel.formal.beast.api.method.CElectionVotingType;
import edu.kit.kastel.formal.beast.api.method.function.CElectionDescriptionFunction;
import edu.kit.kastel.formal.beast.api.method.function.FunctionToC;
import edu.kit.kastel.formal.beast.api.method.function.SimpleTypeFunction;
import edu.kit.kastel.formal.beast.api.method.function.VotingSigFunction;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;

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
    private static final String NONE = "";
    private static final String BLANK = " ";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";
    private static final String PAREN_OPEN = "(";
    private static final String PAREN_CLOSE = ")";

    private static final String TWO = "2";
    private static final String CPROVER_PREFIX = "__CPROVER_";
    private static final String ASSUME = "assume";
    private static final String ASSERT = "assert";
    private static final String RETURN = "return";

    private static final String STDLIB_H = "stdlib.h";
    private static final String STDINT_H = "stdint.h";
    private static final String ASSERT_H = "assert.h";

    private static final String VOTES = "votes";
    private static final String RESULT = "result";
    private static final String VOTE_STRUCT = "VoteStruct";
    private static final String VOTE_RESULT_STRUCT = "VoteResultStruct";
    private static final String VOTE_STRUCT_NAME = "voteStruct";
    private static final String RESULT_STRUCT_NAME = "resultStruct";
    private static final String AMOUNT_RESULT = "amtResult";
    private static final String AMOUNT_VOTES = "amtVotes";
    private static final String BEGIN_USER_CODE = "\n// user generated code";
    private static final String END_USER_CODE = "// end user generated code\n";

    private static final String INVALID_VOTE = "INVALID_VOTE";
    private static final String INVALID_RESULT = "INVALID_RESULT";

    private static final String X = "x";
    private static final String Y = "y";
    private static final String ARRAY = "[]";

    private static final String ASSUME_X = ASSUME + paren(X);
    private static final String ASSERT_X_Y = ASSERT + paren(X + COMMA + BLANK + Y);
    private static final String ASSERT2_X_Y = ASSERT + TWO + paren(X + COMMA + BLANK + Y);
    private static final String CPROVER_ASSUME = CPROVER_PREFIX + ASSUME;
    private static final String CPROVER_ASSERT = CPROVER_PREFIX + ASSERT;
    private static final String CPROVER_ASSUME_X = CPROVER_PREFIX + ASSUME_X;
    private static final String CPROVER_ASSERT_X_Y = CPROVER_PREFIX + ASSERT_X_Y;

    private static final String VOID = "void";
    private static final String CHAR = "char";
    private static final String INT = "int";
    private static final String UNSIGNED_INT = "unsigned" + BLANK + INT;
    private static final String CBMC_UINT_FUNC_NAME = "nondet_uint";
    private static final String CBMC_INT_FUNC_NAME = "nondet_int";

    private static final String COLOUR = "0xFFFFFFFE";

    private static String paren(final String arg) {
        return PAREN_OPEN + arg + PAREN_CLOSE;
    }

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

        created.addFunctionDecl(VOID, CPROVER_ASSUME, new CFunction.Parameter(INT));
        created.addFunctionDecl(VOID, CPROVER_ASSERT,
                                List.of(new CFunction.Parameter(INT),
                                        new CFunction.Parameter(CHAR, Y + ARRAY)));

        created.define(ASSERT2_X_Y, CPROVER_ASSERT_X_Y);
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

    public static GeneratedCodeInfo generateCodeForPropertyCheck(final CElectionDescription descr,
                                                                 final PropertyDescription pDescr,
                                                                 final CodeGenOptions options,
                                                                 final InitVoteHelper initHelper) {
        final CFile created = prepareCodeFile(descr, options);
        final CElectionVotingType votesNakedArr =
                CElectionVotingType.of(descr.getVotingFunction().getInputType());
        final CElectionVotingType resultNakedArr =
                CElectionVotingType.of(descr.getVotingFunction().getOutputType());

        final ElectionTypeCStruct voteInputStruct =
                votingTypeToCStruct(votesNakedArr, VOTE_STRUCT,
                                    VOTES, AMOUNT_VOTES, options);
        final ElectionTypeCStruct voteResultStruct =
                votingTypeToCStruct(resultNakedArr, VOTE_RESULT_STRUCT,
                                    RESULT, AMOUNT_RESULT,
                                    options);
        created.addStructDef(voteInputStruct.getStruct());
        created.addStructDef(voteResultStruct.getStruct());
        final String votingStructVarName = VOTE_STRUCT_NAME;
        final String resultStructVarName = RESULT_STRUCT_NAME;
        final CodeGenLoopBoundHandler loopBoundHandler = descr.generateLoopBoundHandler();
        final CFunction.Input input =
                new CFunction.Input(descr.getInputType(), voteInputStruct, votingStructVarName);
        final CFunction.Output output =
                new CFunction.Output(descr.getOutputType(), voteResultStruct, resultStructVarName);

        created.addFunction(
                votingSigFuncToPlainCFunc(descr.getVotingFunction(), input,
                                          output, options, loopBoundHandler));
        final BooleanExpASTData preAstData =
                BooleanCodeToAST.generateAST(pDescr.getPreConditionsDescription().getCode(),
                                             pDescr.getVariables(), options);
        final BooleanExpASTData postAstData =
                BooleanCodeToAST.generateAST(pDescr.getPostConditionsDescription().getCode(),
                                             pDescr.getVariables(), options);

        final GeneratedCodeInfo codeInfo = new GeneratedCodeInfo();
        codeInfo.setVotesAmtMemberVarName(voteInputStruct.getAmountName());
        codeInfo.setVotesListMemberVarName(voteInputStruct.getListName());
        codeInfo.setResultAmtMemberVarName(voteResultStruct.getAmountName());
        codeInfo.setResultListMemberVarName(voteResultStruct.getListName());

        final CFunction.PropertyExpressions expressions =
                new CFunction.PropertyExpressions(preAstData, postAstData,
                                                  pDescr.getVariables());
        final CFunction.VotingFunction votingFunction =
                new CFunction.VotingFunction(voteInputStruct, voteResultStruct,
                                             descr.getInputType(), descr.getOutputType(),
                                             descr.getVotingFunction().getName());
        final CFunction mainFunction =
                CBMCMainGenerator.main(expressions, votingFunction,
                                       options, loopBoundHandler,
                                       codeInfo, initHelper);
        created.addFunction(mainFunction);
        codeInfo.setCode(created.generateCode());
        loopBoundHandler.finishAddedLoopbounds();
        codeInfo.setLoopboundHandler(loopBoundHandler);
        return codeInfo;
    }

    private static CFunction
                votingSigFuncToPlainCFunc(final VotingSigFunction func,
                                          final CFunction.Input input,
                                          final CFunction.Output output,
                                          final CodeGenOptions options,
                                          final CodeGenLoopBoundHandler loopBoundHandler) {
        final String uint =
                TypeManager.simpleTypeToCType(CElectionSimpleType.UNSIGNED_INT) + BLANK;
        final String structArg =
                input.struct.getStruct().getName() + BLANK + input.structVarName;
        final String currentAmtVoterArg = uint + options.getCurrentAmountVotersVarName();
        final String currentAmtCandArg = uint + options.getCurrentAmountCandsVarName();
        final String currentAmtSeatArg = uint + options.getCurrentAmountSeatsVarName();

        final List<String> votingFuncArguments =
                List.of(structArg, currentAmtVoterArg, currentAmtCandArg, currentAmtSeatArg);
        final CFunction created =
                new CFunction(func.getName(), votingFuncArguments,
                              output.struct.getStruct().getName());

        final List<String> code = new ArrayList<String>();
        final VotingFunctionHelper.CNames votingNames =
                new VotingFunctionHelper.CNames(func.getName(), func.getVotesArrayName(),
                                                input.structVarName);
        code.add(VotingFunctionHelper.generateVoteArrayCopy(votingNames, input.type, input.struct,
                                                            options, loopBoundHandler));
        code.add(FunctionToC
                .votingTypeToC(CElectionVotingType.of(func.getOutputType()),
                                                      func.getResultArrayName(),
                                                      options.getCurrentAmountVotersVarName(),
                                                      options.getCurrentAmountCandsVarName(),
                                                      options.getCurrentAmountSeatsVarName())
                                .generateCode() + SEMICOLON);

        code.add(BEGIN_USER_CODE);
        code.addAll(CFunction.sanitize(func.getCodeAsList()));
        code.add(END_USER_CODE);

        final VotingFunctionHelper.CNames resultNames =
                new VotingFunctionHelper.CNames(func.getName(), func.getResultArrayName(),
                                                output.structVarName);
        code.add(VotingFunctionHelper.generateVoteResultCopy(resultNames,
                                                             output.type, output.struct,
                                                             options, loopBoundHandler));
        code.add(RETURN + BLANK + output.structVarName + SEMICOLON);
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
                TypeManager.simpleTypeToCType(CElectionSimpleType.UNSIGNED_INT);
        final CTypeNameBrackets counterMember = new CTypeNameBrackets(counterType, amtName, NONE);
        final List<CTypeNameBrackets> members = List.of(listMember, counterMember);
        final CStruct cstruct = new CStruct(structName, members);
        return new ElectionTypeCStruct(resultNakedArr, cstruct, listName, amtName);
    }
}
