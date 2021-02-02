package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.TypeManager;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;

public class CopyVotesHelperFunc extends HelperFunction {
	
	private static final String template2D = 
			"	for (int COUNTER = 0; COUNTER < AMT; ++COUNTER) {\n"
			+ "		DEST.LIST[POS1][COUNTER] = SRC.LIST[POS2][COUNTER];\n"
			+ "	}";
	
	private ElectionTypeCStruct voteStruct;

	public CopyVotesHelperFunc(ElectionTypeCStruct voteStruct) {
		super(HelperFunctionTypes.COPY_VOTES);
		this.voteStruct = voteStruct;
	}

	@Override
	public String uniqueName() {
		return this.type.str();
	}
	
	private String code(String destVarName, String destPosVarName, String srcVarName, String srcPosVarName) {
		if(voteStruct.getVotingType().getListDimensions() == 2) {
			String code = template2D;
			code = code.replaceAll("COUNTER", "i");
			code = code.replaceAll("AMT", voteStruct.getVotingType().getListSizes().get(1).toString());
			code = code.replaceAll("DEST", destVarName);
			code = code.replaceAll("POS1", destPosVarName);
			code = code.replaceAll("SRC", srcVarName);
			code = code.replaceAll("LIST", voteStruct.getListName());
			code = code.replaceAll("POS2", srcPosVarName);			
			return code;
		}
		return null;
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		//function declaration: void copyVotes(VoteStruct dest, int destPos, VoteStruct src, int srcPos) {		
		String destVarName = "dest";
		String destPosVarName = "destPos";
		String srcVarName = "src";
		String srcPosVarName = "srcPos";
		String intType = TypeManager.SimpleTypeToCType(CElectionSimpleTypes.INT);
		List<CTypeNameBrackets> args = List.of(
					new CTypeNameBrackets(voteStruct.getStruct().getName(), destVarName, ""),
					new CTypeNameBrackets(intType, destPosVarName, ""),
					new CTypeNameBrackets(voteStruct.getStruct().getName(), srcVarName, ""),
					new CTypeNameBrackets(intType, srcPosVarName, "")
				);
		CFunction created = new CFunction(uniqueName(), TypeManager.SimpleTypeToCType(CElectionSimpleTypes.VOID), args);
		created.setCode(List.of(code(destVarName, destPosVarName, srcVarName, srcPosVarName)));
		return created;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of();
	}

}
