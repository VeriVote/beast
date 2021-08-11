package edu.pse.beast.api.codegen.ast.expression.type.election;

import java.util.List;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class ElectExp.
 *
 * @author Holger Klein
 */
public final class ElectExp extends AccessedVoteElectValueNode {

    public ElectExp(final List<SymbolicVariable> accessingVariables,
                    final int electionNumber) {
        super(accessingVariables, electionNumber);
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
