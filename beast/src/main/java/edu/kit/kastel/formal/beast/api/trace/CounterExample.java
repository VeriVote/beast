package edu.kit.kastel.formal.beast.api.trace;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CounterExample {
    private static final String EMPTY = "";

    private Map<String, StructAssignment> varNamesToAssignments =
            new LinkedHashMap<String, StructAssignment>();

    private GeneratedCodeInfo generatedCodeInfo;
    private int sortNumber;

    public CounterExample(final GeneratedCodeInfo codeInfo) {
        this.generatedCodeInfo = codeInfo;
    }

    public final GeneratedCodeInfo getGeneratedCodeInfo() {
        return generatedCodeInfo;
    }

    public final void add(final String structName,
                          final AssignmentType assType,
                          final String memberName,
                          final String assignment,
                          final String info) {
        if (!varNamesToAssignments.containsKey(structName)) {
            varNamesToAssignments.put(structName,
                                      new StructAssignment(assType, structName, info));
        }
        varNamesToAssignments.get(structName)
            .add(memberName, assignment, sortNumber++);

    }

    public final List<StructAssignment> getAssignments(final Set<AssignmentType> types) {
        final List<StructAssignment> list = new ArrayList<StructAssignment>();
        for (final StructAssignment ass : varNamesToAssignments.values()) {
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
        final StringBuilder s = new StringBuilder(EMPTY);
        for (final StructAssignment varAssignment : varNamesToAssignments.values()) {
            s.append(varAssignment.toString());
        }
        return s.toString();
    }
}
