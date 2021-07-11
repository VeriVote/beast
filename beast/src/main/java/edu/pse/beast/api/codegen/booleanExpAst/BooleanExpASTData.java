package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.BooleanExpListNode;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * AST which represents boolean Expression
 * @author holge
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

    public void setHighestElect(int highestElect) {
        this.highestElect = highestElect;
    }

    public int getHighestElect() {
        return highestElect;
    }

    public void setHighestVote(int highestVote) {
        this.highestVote = highestVote;
    }

    public int getHighestVote() {
        return highestVote;
    }

    public void setTopAstNode(BooleanExpListNode topAstNode) {
        this.topAstNode = topAstNode;
    }

    public BooleanExpListNode getTopAstNode() {
        return topAstNode;
    }

    public int getHighestVoteOrElect() {
        return Math.max(highestElect, highestVote);
    }

    private void addAccessingVar(SymbolicCBMCVar var, int number,
            Map<SymbolicCBMCVar, Set<Integer>> map) {
        if (!map.containsKey(var)) {
            map.put(var, new HashSet<>());
        }
        map.get(var).add(number);
    }

    public void voteAccessedBy(List<SymbolicCBMCVar> accessingVars,
            int number) {
        for (SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToVotes);
        }
    }

    public void electAccessedBy(List<SymbolicCBMCVar> accessingVars,
            int number) {
        for (SymbolicCBMCVar var : accessingVars) {
            addAccessingVar(var, number, accessingVarsToElects);
        }
    }

}
