package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CDefine;
import edu.pse.beast.api.codegen.c_code.CFile;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.VotingFunctionHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.electiondescription.to_c.FunctionToC;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
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

	
	public static CBMCGeneratedCodeInfo generateCodeForCBMCPropertyTest(
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, CodeGenOptions options) {

		CFile created = new CFile();
		created.include(STDLIB);
		created.include(STDINT);
		created.include(ASSERT);				

		created.define(ASSUME, CPROVER_ASSUME);

		created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
		created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());

		options.setCbmcAssumeName("assume");
		options.setCbmcAssertName("assert");
		options.setCbmcNondetUintName(CBMC_UINT_FUNC_NAME);

		CElectionVotingType votesNakedArr = CElectionVotingType
				.of(descr.getVotingFunction().getInputType());
		CElectionVotingType resultNakedArr = CElectionVotingType
				.of(descr.getVotingFunction().getOutputType());

		ElectionTypeCStruct voteInputStruct = votingTypeToCStruct(votesNakedArr,
				"VoteStruct", "votes", "amtVotes");
		ElectionTypeCStruct voteResultStruct = votingTypeToCStruct(
				resultNakedArr, "VoteResultStruct", "result", "amtResult");

		created.addStructDef(voteInputStruct.getStruct());
		created.addStructDef(voteResultStruct.getStruct());
		
		String votingStructVarName = "voteStruct";
		String resultStructVarName = "resultStruct";

		CodeGenLoopBoundHandler loopBoundHandler = descr
				.generateLoopBoundHandler();

		created.addFunction(votingSigFuncToPlainCFunc(descr.getVotingFunction(),
				descr.getInputType(), descr.getOutputType(), voteInputStruct,
				voteResultStruct, options, loopBoundHandler,
				votingStructVarName, resultStructVarName));

		BooleanExpASTData preAstData = BooleanCodeToAST.generateAST(
				propDescr.getPreConditionsDescription().getCode(),
				propDescr.getCbmcVariables());
		BooleanExpASTData postAstData = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		CBMCGeneratedCodeInfo cbmcGeneratedCode = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCode.setAmtMemberVarName(voteInputStruct.getAmtName());
		cbmcGeneratedCode.setListMemberVarName(voteInputStruct.getListName());

		CFunction mainFunction = CBMCMainGenerator.main(
				preAstData, postAstData,
				propDescr.getCbmcVariables(), voteInputStruct, voteResultStruct,
				descr.getInputType(), descr.getOutputType(), options,
				loopBoundHandler, descr.getVotingFunction().getName(),
				cbmcGeneratedCode);

		created.addFunction(mainFunction);

		cbmcGeneratedCode.setCode(created.generateCode());

		loopBoundHandler.finishAddedLoopbounds();
		cbmcGeneratedCode.setLoopboundHandler(loopBoundHandler);

		return cbmcGeneratedCode;
	}
	
	private static CFunction votingSigFuncToPlainCFunc(VotingSigFunction func,
			VotingInputTypes votingInputType,
			VotingOutputTypes votingOutputType, ElectionTypeCStruct inputStruct,
			ElectionTypeCStruct outputStruct, CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler,
			String votingStructVarName, String resultStructVarName) {

		List<String> votingFuncArguments = List.of(
				inputStruct.getStruct().getName() + " " + votingStructVarName);

		CFunction created = new CFunction(func.getName(), votingFuncArguments,
				outputStruct.getStruct().getName());

		List<String> code = new ArrayList<>();

		code.add(VotingFunctionHelper.generateVoteArrayCopy(func.getName(),
				func.getVotesArrayName(), votingStructVarName, votingInputType,
				inputStruct, options, loopBoundHandler));

		code.add(FunctionToC
				.votingTypeToC(CElectionVotingType.of(func.getOutputType()),
						func.getResultArrayName())
				.generateCode() + ";");

		code.add("//user generated code");

		code.addAll(func.getCodeAsList());

		code.add("//end user generated code");

		code.add(VotingFunctionHelper.generateVoteResultCopy(func.getName(),
				func.getResultArrayName(), resultStructVarName,
				votingOutputType, outputStruct, options, loopBoundHandler));

		code.add("return " + resultStructVarName + ";");

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
