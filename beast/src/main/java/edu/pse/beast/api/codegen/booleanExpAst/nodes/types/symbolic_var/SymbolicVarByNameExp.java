package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class SymbolicVarExp.
 *
 * @author Lukas Stapelbroek
 */
public final class SymbolicVarByNameExp extends SymbolicCBMCVarExp {
    private SymbolicCBMCVar cbmcVar;

    public SymbolicVarByNameExp(final SymbolicCBMCVar symbolicVariable) {
        this.cbmcVar = symbolicVariable;
    }

    public SymbolicCBMCVar getCbmcVar() {
        return cbmcVar;
    }

    @Override
    public String getTreeString(final int depth) {
        return "SymbVar: {id " + cbmcVar.getName() + "}";
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitSymbolicVarExp(this);
    }
}
