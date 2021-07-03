package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

public class SymbVarByPosExp extends SymbolicCBMCVarExp {

	private CBMCVarType varType;
	private int accessingNumber;

	public SymbVarByPosExp(CBMCVarType varType, int accessingNumber) {
		this.varType = varType;
		this.accessingNumber = accessingNumber;
	}

	public CBMCVarType getVarType() {
		return varType;
	}
	
	public int getAccessingNumber() {
		return accessingNumber;
	}
	
	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitSymbVarByPosExp(this);
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return null;
	}

}
