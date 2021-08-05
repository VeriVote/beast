package edu.pse.beast.api.codegen.cbmc;

import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class Scope {
    private Map<String, SymbolicCBMCVar.CBMCVarType> containedVars = new HashMap<>();

    public final void add(final SymbolicCBMCVar var) {
        containedVars.put(var.getName(), var.getVarType());
    }

    public final CBMCVarType getType(final String name) {
        if (containedVars.containsKey(name)) {
            return containedVars.get(name);
        }
        return null;
    }

}
