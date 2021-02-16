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
import edu.pse.beast.api.electiondescription.CBMCVars;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
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

	public static String generateCode(CElectionDescription descr, PreAndPostConditionsDescription propDescr,
			CodeGenOptions options) {
		CFile created = new CFile();
		created.include(STDLIB);
		created.include(STDINT);
		created.include(ASSERT);

		created.define(ASSUME, CPROVER_ASSUME);

		created.define("V", "1");
		created.define("C", "1");

		created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
		created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());

		TypeManager typeManager = new TypeManager();
		created.addTypeDef(typeManager.getTypeDefs());

		CElectionVotingType votesNakedArr = CElectionVotingType.of(descr.getVotingFunction().getInputType());
		CElectionVotingType resultNakedArr = CElectionVotingType.of(descr.getVotingFunction().getOutputType());

		ElectionTypeCStruct voteArrStruct = votingTypeToCStruct(votesNakedArr, "VoteStruct", "votes", "amtVotes");
		ElectionTypeCStruct voteResultStruct = votingTypeToCStruct(resultNakedArr, "VoteResultStruct", "result",
				"amtResult");

		created.addStructDef(voteArrStruct.getStruct());
		created.addStructDef(voteResultStruct.getStruct());

		created.addFunction(votingSigFuncToPlainCFunc(descr.getVotingFunction(), voteArrStruct, voteResultStruct, options));

		// we do this here to know which helper functions we need to generate
		BooleanExpASTData preAstData = BooleanCodeToAST.generateAST(propDescr.getPreConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());
		BooleanExpASTData postAstData = BooleanCodeToAST.generateAST(propDescr.getPostConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());

		InputAndOutputElectionStructs inAndOutStructs = new InputAndOutputElectionStructs(voteArrStruct,
				voteResultStruct);

		options.setCbmcAssumeName("assume");
		options.setCbmcNondetUintName(CBMC_UINT_FUNC_NAME);

		created.addFunction(CBMCMainGenerator.main(preAstData, postAstData, voteArrStruct, voteResultStruct, options));

		return created.generateCode();
	}

	private static CFunction votingSigFuncToPlainCFunc(VotingSigFunction func, ElectionTypeCStruct input,
			ElectionTypeCStruct output, CodeGenOptions options) {
		
		String votingStructVarName = "voteStruct";

		CFunction created = new CFunction(func.getName(), List.of(input.getStruct().getName() + " " + votingStructVarName),
				output.getStruct().getName());
		
		List<String> code = new ArrayList<>();
		
		String voteArrayName = "votes";
		code.add("Vote votes[V][C];");
		code.add("VoteResult result;");
		
		code.add(VotingFunctionHelper.generateVoteCopy(voteArrayName, votingStructVarName, input, options));

		code.add("//user generated code");

		code.addAll(func.getCode());

		code.add("//end user generated code");

		String resultName = "result";
		String outputVarName = "resultStruct";
		code.add(output.getStruct().getName() + " " + outputVarName + ";");
		code.add(VotingFunctionHelper.generateResultCopy(resultName, outputVarName, output, options));

		code.add("return RES;".replaceAll("RES", outputVarName));
		created.setCode(code);
		return created;
	}

	private static String unusedVarName() {
		return "var" + UUID.randomUUID().toString().replaceAll("-", "");
	}

	private static String generateDeepCopy(int listDimensions, List<String> listSizes, String arrFrom, String arrTo) {
		if (listDimensions == 0) {
			return arrTo + " = " + arrFrom + ";";
		} else {
			List<String> created = new ArrayList<>();
			int lvl = 0;
			String varAccBrackets = "";
			String closingBrackets = "";
			for (String dimSize : listSizes) {
				String varName = "loopCounter" + lvl;
				String loopTemplate = "for(int VAR_NAME=0; VAR_NAME < DIM_SIZE; ++VAR_NAME) {";
				created.add(loopTemplate.replaceAll("VAR_NAME", varName).replaceAll("DIM_SIZE", dimSize));
				varAccBrackets += "[" + varName + "]";
				closingBrackets += "}";
				lvl++;
			}
			created.add(arrTo + varAccBrackets + " = " + arrFrom + varAccBrackets + ";");
			created.add(closingBrackets);
			return String.join("\n", created);
		}
	}

	private static List<String> cbmcVarArrToVarNames(List<CBMCVars> vars) {
		return vars.stream().map(var -> CBMCVarNames.name(var)).collect(Collectors.toList());
	}

	private static String generateCopyToResultStruct(VotingSigFunction func, CStruct votingResult,
			CElectionVotingType resultNakedArr) {
		List<String> code = new ArrayList<>();
		String resultStructVarName = unusedVarName();

		code.add(votingResult.getName() + " " + resultStructVarName + ";");

		List<String> listSizes = cbmcVarArrToVarNames(resultNakedArr.getListSizes());
		code.add(generateDeepCopy(resultNakedArr.getListDimensions(), listSizes, func.getResultVarName(),
				resultStructVarName + "." + votingResult.getMembers().get(0).getName()));

		code.add("return " + resultStructVarName + ";");

		return String.join("\n", code);
	}

	private static ElectionTypeCStruct votingTypeToCStruct(CElectionVotingType resultNakedArr, String structName,
			String listName, String amtName) {
		CTypeNameBrackets listMember = FunctionToC.votingTypeToC(resultNakedArr, listName);
		String counterType = TypeManager.SimpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT);
		CTypeNameBrackets counterMember = new CTypeNameBrackets(counterType, amtName, "");
		List<CTypeNameBrackets> members = List.of(listMember, counterMember);
		CStruct cstruct = new CStruct(structName, members);
		return new ElectionTypeCStruct(resultNakedArr, cstruct, listName, amtName);
	}

}
