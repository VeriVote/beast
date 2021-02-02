package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeDef;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class TypeManager {

	private List<ElectionTypeCStruct> elecTypeCStructs = new ArrayList<>();

	public CStruct getCStructForVotingType(CElectionVotingType votingType) {
		return null;
	}

	public List<CTypeDef> getTypeDefs() {
		return List.of(
				new CTypeDef(SimpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT),
						SimpleTypeToCType(CElectionSimpleTypes.RESULT)),
				new CTypeDef(SimpleTypeToCType(CElectionSimpleTypes.UNSIGNED_INT),
						SimpleTypeToCType(CElectionSimpleTypes.VOTE)));
	}

	public void add(ElectionTypeCStruct electionTypeCStruct) {
		this.elecTypeCStructs.add(electionTypeCStruct);
	}

	public static String SimpleTypeToCType(CElectionSimpleTypes simpleType) {
		switch (simpleType) {
		case INT:
			return "int";
		case DOUBLE:
			return "double";
		case FLOAT:
			return "float";
		case UNSIGNED_INT:
			return "unsigned int";
		case VOTE:
			return "Vote";
		case RESULT:
			return "VoteResult";
		case VOID:
			return "void";
		case BOOL:
			return "bool";
		default:
			return null;
		}
	}
}
