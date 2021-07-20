package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class ElectExp.
 *
 * @author Holger Klein
 */
public final class ElectExp extends AccessedVoteElectValueNode {

    public ElectExp(final List<SymbolicCBMCVar> accessingCBMCVars,
                    final int electionNumber) {
        super(accessingCBMCVars, electionNumber);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectExpNode(this);
    }
}
