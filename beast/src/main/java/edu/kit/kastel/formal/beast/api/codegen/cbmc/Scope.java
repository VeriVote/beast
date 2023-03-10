package edu.kit.kastel.formal.beast.api.codegen.cbmc;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable.VariableType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class Scope {
    private Map<String, SymbolicVariable.VariableType> containedVars =
            new LinkedHashMap<String, SymbolicVariable.VariableType>();

    public final void add(final SymbolicVariable var) {
        containedVars.put(var.getName(), var.getVarType());
    }

    public final VariableType getType(final String name) {
        if (containedVars.containsKey(name)) {
            return containedVars.get(name);
        }
        return null;
    }

}
