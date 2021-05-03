package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFile;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.helperfunctions.VotingFunctionHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CBMCVars;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.electiondescription.to_c.CBMCVarNames;
import edu.pse.beast.api.electiondescription.to_c.FunctionToC;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCCodeGeneratorNEW {

	private final static String STDLIB = "stdlib.h";
	private final static String STDINT = "stdint.h";
	private final static String ASSERT = "assert.h";

	private final static String ASSUME = "assume(x)";
	private final static String CPROVER_ASSUME = "__CPROVER_assume(x)";

	private final static String UNSIGNED_INT = "unsigned int";
	private final static String INT = "int";
	private final static String CBMC_UINT_FUNC_NAME = "nondet_uint";
	private final static String CBMC_INT_FUNC_NAME = "nondet_int";

	public static CBMCGeneratedCodeInfo generateCode(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		CFile created = new CFile();
		created.include(STDLIB);
		created.include(STDINT);
		created.include(ASSERT);

		created.define(ASSUME, CPROVER_ASSUME);

		created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
		created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());

		TypeManager typeManager = new TypeManager();
		created.addTypeDef(typeManager.getTypeDefs());

		CElectionVotingType votesNakedArr = CElectionVotingType
				.of(descr.getVotingFunction().getInputType());
		CElectionVotingType resultNakedArr = CElectionVotingType
				.of(descr.getVotingFunction().getOutputType());

		options.setCbmcAssumeName("assume");
		options.setCbmcAssertName("assert");
		options.setCbmcNondetUintName(CBMC_UINT_FUNC_NAME);

		ElectionTypeCStruct voteArrStruct = votingTypeToCStruct(votesNakedArr,
				"VoteStruct", "votes", "amtVotes");
		ElectionTypeCStruct voteResultStruct = votingTypeToCStruct(
				resultNakedArr, "VoteResultStruct", "result", "amtResult");

		created.addStructDef(voteArrStruct.getStruct());
		created.addStructDef(voteResultStruct.getStruct());

		created.addFunction(votingSigFuncToPlainCFunc(descr.getVotingFunction(),
				descr.getInputType(), descr.getOutputType(), voteArrStruct,
				voteResultStruct, options, loopBoundHandler));

		BooleanExpASTData preAstData = BooleanCodeToAST.generateAST(
				propDescr.getPreConditionsDescription().getCode(),
				propDescr.getCbmcVariables());
		BooleanExpASTData postAstData = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		CBMCGeneratedCodeInfo cbmcGeneratedCode = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCode.setAmtMemberVarName(voteArrStruct.getAmtName());
		cbmcGeneratedCode.setListMemberVarName(voteArrStruct.getListName());

		CFunction mainFunction = CBMCMainGenerator.main(preAstData, postAstData,
				propDescr.getCbmcVariables(), voteArrStruct, voteResultStruct,
				descr.getInputType(), descr.getOutputType(), options,
				loopBoundHandler, descr.getVotingFunction().getName(),
				cbmcGeneratedCode);

		created.addFunction(mainFunction);

		cbmcGeneratedCode.setCode(created.generateCode());

		return cbmcGeneratedCode;
	}

	private static CFunction votingSigFuncToPlainCFunc(VotingSigFunction func,
			VotingInputTypes votingInputType,
			VotingOutputTypes votingOutputType, ElectionTypeCStruct inputStruct,
			ElectionTypeCStruct outputStruct, CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {

		String votingStructVarName = "voteStruct";

		List<String> votingFuncArguments = List.of(
				inputStruct.getStruct().getName() + " " + votingStructVarName);

		CFunction created = new CFunction(func.getName(), votingFuncArguments,
				outputStruct.getStruct().getName());

		List<String> code = new ArrayList<>();

		String voteArrayVarName = "votes";
		String resultArrayVarName = "result";

		code.add(VotingFunctionHelper.generateVoteArrayInitAndCopy(
				voteArrayVarName, votingStructVarName, votingInputType,
				inputStruct, options, loopBoundHandler));

		code.add(VotingFunctionHelper.generateVoteResultInit(resultArrayVarName,
				votingOutputType, options));

		code.add("//user generated code");

		code.addAll(func.getCodeAsList());

		code.add("//end user generated code");

		String resultStructVarName = "resultStruct";
		code.add(VotingFunctionHelper.generateVoteResultCopy(resultArrayVarName,
				resultStructVarName, votingOutputType, outputStruct, options,
				loopBoundHandler));

		created.setCode(code);
		return created;
	}

	private static ElectionTypeCStruct votingTypeToCStruct(
			CElectionVotingType resultNakedArr, String structName,
			String listName, String amtName) {
		CTypeNameBrackets listMember = FunctionToC.votingTypeToC(resultNakedArr,
				listName);
		String counterType = TypeManager
				.SimpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT);
		CTypeNameBrackets counterMember = new CTypeNameBrackets(counterType,
				amtName, "");
		List<CTypeNameBrackets> members = List.of(listMember, counterMember);
		CStruct cstruct = new CStruct(structName, members);
		return new ElectionTypeCStruct(resultNakedArr, cstruct, listName,
				amtName);
	}

}
