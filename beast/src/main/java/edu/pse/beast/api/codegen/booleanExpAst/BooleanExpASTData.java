package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.BooleanExpListNode;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * An AST which represents a boolean expression.
 *
 * @author Holger Klein
 *
 */
public class BooleanExpASTData {
    private BooleanExpListNode topAstNode;
    private int highestElect;
    private int highestVote;
    private Map<SymbolicCBMCVar, Set<Integer>> accessingVarsToVotes =
            new LinkedHashMap<SymbolicCBMCVar, Set<Integer>>();
    private Map<SymbolicCBMCVar, Set<Integer>> accessingVarsToElects =
            new LinkedHashMap<SymbolicCBMCVar, Set<Integer>>();

    public final Map<SymbolicCBMCVar, Set<Integer>> getAccessingVarsToElects() {
        return accessingVarsToElects;
    }

    public final Map<SymbolicCBMCVar, Set<Integer>> getAccessingVarsToVotes() {
        return accessingVarsToVotes;
    }

    public final void setHighestElect(final int highestElectNumber) {
        this.highestElect = highestElectNumber;
    }

    public final int getHighestElect() {
        return highestElect;
    }

    public final void setHighestVote(final int highestVoteNumber) {
        this.highestVote = highestVoteNumber;
    }

    public final int getHighestVote() {
        return highestVote;
    }

    public final void setTopAstNode(final BooleanExpListNode topBoolExpAstNode) {
        this.topAstNode = topBoolExpAstNode;
    }

    public final BooleanExpListNode getTopAstNode() {
        return topAstNode;
    }

    public final int getHighestVoteOrElect() {
        return Math.max(highestElect, highestVote);
    }

    private void addAccessingVar(final SymbolicCBMCVar var,
                                 final int number,
                                 final Map<SymbolicCBMCVar, Set<Integer>> map) {
        if (!map.containsKey(var)) {
            map.put(var, new LinkedHashSet<Integer>());
        }
        map.get(var).add(number);
    }

    public final void voteAccessedBy(final List<SymbolicCBMCVar> accessingVars,
                                     final int number) {
        for (final SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToVotes);
        }
    }

    public final void electAccessedBy(final List<SymbolicCBMCVar> accessingVars,
                                      final int number) {
        for (final SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToElects);
        }
    }
}
