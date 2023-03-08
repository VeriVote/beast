package edu.kit.kastel.formal.beast.api.codegen.ast;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.kastel.formal.beast.api.codegen.ast.expression.BooleanExpListNode;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * An AST which represents a boolean expression.
 *
 * @author Holger Klein
 *
 */
public class BooleanExpASTData {
    private BooleanExpListNode topAstNode;
    private int lastElectNumber;
    private int lastVoterNumber;
    private Map<SymbolicVariable, Set<Integer>> accessingVarsToVotes =
            new LinkedHashMap<SymbolicVariable, Set<Integer>>();
    private Map<SymbolicVariable, Set<Integer>> accessingVarsToElects =
            new LinkedHashMap<SymbolicVariable, Set<Integer>>();

    public final Map<SymbolicVariable, Set<Integer>> getAccessingVarsToElects() {
        return accessingVarsToElects;
    }

    public final Map<SymbolicVariable, Set<Integer>> getAccessingVarsToVotes() {
        return accessingVarsToVotes;
    }

    public final void setLastElectNumber(final int lastElectCall) {
        this.lastElectNumber = lastElectCall;
    }

    public final int getLastElectNumber() {
        return lastElectNumber;
    }

    public final void setLastVoteNumber(final int lastVoteCall) {
        this.lastVoterNumber = lastVoteCall;
    }

    public final int getLastVoterNumber() {
        return lastVoterNumber;
    }

    public final void setTopAstNode(final BooleanExpListNode topBoolExpAstNode) {
        this.topAstNode = topBoolExpAstNode;
    }

    public final BooleanExpListNode getTopAstNode() {
        return topAstNode;
    }

    public final int getLastVoteOrElect() {
        return Math.max(lastElectNumber, lastVoterNumber);
    }

    private void addAccessingVar(final SymbolicVariable var,
                                 final int number,
                                 final Map<SymbolicVariable, Set<Integer>> map) {
        if (!map.containsKey(var)) {
            map.put(var, new LinkedHashSet<Integer>());
        }
        map.get(var).add(number);
    }

    public final void voteAccessedBy(final List<SymbolicVariable> accessingVars,
                                     final int number) {
        for (final SymbolicVariable var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToVotes);
        }
    }

    public final void electAccessedBy(final List<SymbolicVariable> accessingVars,
                                      final int number) {
        for (final SymbolicVariable var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToElects);
        }
    }
}
