package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

public class CBMCCounterExample {
	private Map<String, CBMCStructAssignment> varNamesToAssignments = new HashMap<>();

	private CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo;
	private int sortNumber = 0;

	public CBMCCounterExample(CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo) {
		this.cbmcGeneratedCodeInfo = cbmcGeneratedCodeInfo;
	}

	public CBMCGeneratedCodeInfo getCbmcGeneratedCodeInfo() {
		return cbmcGeneratedCodeInfo;
	}

	public void add(String structName, CBMCAssignmentType assType,
			String memberName, String assignment, String info) {
		if (!varNamesToAssignments.containsKey(structName)) {
			varNamesToAssignments.put(structName,
					new CBMCStructAssignment(assType, structName, info));
		}
		varNamesToAssignments.get(structName).add(memberName, assignment,
				sortNumber++);

	}

	public List<CBMCStructAssignment> getAssignments(
			Set<CBMCAssignmentType> types) {
		List<CBMCStructAssignment> list = new ArrayList<>();
		for (CBMCStructAssignment ass : varNamesToAssignments.values()) {
			if (types.contains(ass.getAssignmentType())) {
				list.add(ass);
			}
		}
		list.sort((lhs, rhs) -> {
			return Integer.compare(lhs.getSortNumber(), rhs.getSortNumber());
		});
		return list;
	}

	@Override
	public String toString() {
		String s = "";
		for (String varname : varNamesToAssignments.keySet()) {
			s += varNamesToAssignments.get(varname).toString();
		}
		return s;
	}
}
