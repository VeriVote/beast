package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class SymbolicVarExp.
 *
 * @author Lukas Stapelbroek
 */
public final class SymbolicVarExp extends TypeExpression {
	private SymbolicCBMCVar cbmcVar;

	public SymbolicVarExp(SymbolicCBMCVar cbmcVar) {
		this.cbmcVar = cbmcVar;
	}

	public SymbolicCBMCVar getCbmcVar() {
		return cbmcVar;
	}

	@Override
	public String getTreeString(final int depth) {
		return "SymbVar: {id " + cbmcVar.getName() + "}";
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitSymbolicVarExp(this);
	}
}
