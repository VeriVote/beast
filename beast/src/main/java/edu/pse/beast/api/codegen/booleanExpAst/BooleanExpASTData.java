package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.BooleanExpListNode;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * An AST which represents a boolean expression.
 * @author Holger Klein
 *
 */
public class BooleanExpASTData {
    private BooleanExpListNode topAstNode;
    private int highestElect;
    private int highestVote;
    private Map<SymbolicCBMCVar, Set<Integer>> accessingVarsToVotes = new HashMap<>();
    private Map<SymbolicCBMCVar, Set<Integer>> accessingVarsToElects = new HashMap<>();

    public Map<SymbolicCBMCVar, Set<Integer>> getAccessingVarsToElects() {
        return accessingVarsToElects;
    }

    public Map<SymbolicCBMCVar, Set<Integer>> getAccessingVarsToVotes() {
        return accessingVarsToVotes;
    }

    public void setHighestElect(final int highestElectNumber) {
        this.highestElect = highestElectNumber;
    }

    public int getHighestElect() {
        return highestElect;
    }

    public void setHighestVote(final int highestVoteNumber) {
        this.highestVote = highestVoteNumber;
    }

    public int getHighestVote() {
        return highestVote;
    }

    public void setTopAstNode(final BooleanExpListNode topBoolExpAstNode) {
        this.topAstNode = topBoolExpAstNode;
    }

    public BooleanExpListNode getTopAstNode() {
        return topAstNode;
    }

    public int getHighestVoteOrElect() {
        return Math.max(highestElect, highestVote);
    }

    private void addAccessingVar(final SymbolicCBMCVar var,
                                 final int number,
                                 final Map<SymbolicCBMCVar, Set<Integer>> map) {
        if (!map.containsKey(var)) {
            map.put(var, new HashSet<>());
        }
        map.get(var).add(number);
    }

    public void voteAccessedBy(final List<SymbolicCBMCVar> accessingVars,
                               final int number) {
        for (final SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToVotes);
        }
    }

    public void electAccessedBy(final List<SymbolicCBMCVar> accessingVars,
                                final int number) {
        for (final SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToElects);
        }
    }
}
