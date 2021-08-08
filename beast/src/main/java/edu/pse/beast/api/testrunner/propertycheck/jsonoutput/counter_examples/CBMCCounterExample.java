package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCCounterExample {
    private Map<String, CBMCStructAssignment> varNamesToAssignments =
            new LinkedHashMap<String, CBMCStructAssignment>();

    private CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo;
    private int sortNumber;

    public CBMCCounterExample(final CBMCGeneratedCodeInfo generatedCodeInfo) {
        this.cbmcGeneratedCodeInfo = generatedCodeInfo;
    }

    public final CBMCGeneratedCodeInfo getCbmcGeneratedCodeInfo() {
        return cbmcGeneratedCodeInfo;
    }

    public final void add(final String structName,
                          final CBMCAssignmentType assType,
                          final String memberName,
                          final String assignment,
                          final String info) {
        if (!varNamesToAssignments.containsKey(structName)) {
            varNamesToAssignments.put(structName,
                                      new CBMCStructAssignment(assType, structName, info));
        }
        varNamesToAssignments.get(structName)
            .add(memberName, assignment, sortNumber++);

    }

    public final List<CBMCStructAssignment> getAssignments(final Set<CBMCAssignmentType> types) {
        final List<CBMCStructAssignment> list = new ArrayList<CBMCStructAssignment>();
        for (final CBMCStructAssignment ass : varNamesToAssignments.values()) {
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
    public final String toString() {
        String s = "";
        for (final String varname : varNamesToAssignments.keySet()) {
            s += varNamesToAssignments.get(varname).toString();
        }
        return s;
    }
}
