package edu.pse.beast.api.codegen.cbmc.info;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class GeneratedCodeInfo {
    private String code;
    private CodeGenLoopBoundHandler loopBoundHandler;
    private Map<String, Integer> voteVariableNameToVoteNumber =
            new LinkedHashMap<String, Integer>();
    private Map<String, Integer> electVariableNameToElectNumber =
            new LinkedHashMap<String, Integer>();
    private Map<String, String> varNamesToInfo = new LinkedHashMap<String, String>();

    private Set<String> generatedVotingVarNames = new LinkedHashSet<String>();
    private Set<String> generatedElectVarNames = new LinkedHashSet<String>();

    private String votesAmtMemberVarName;
    private String votesListMemberVarName;

    private String resultAmtMemberVarName;
    private String resultListMemberVarName;

    public final void addedGeneratedVotingVar(final String name) {
        generatedVotingVarNames.add(name);
    }

    public final void addedGeneratedElectVar(final String name) {
        generatedElectVarNames.add(name);
    }

    public final Map<String, String> getVarNamesToInfo() {
        return varNamesToInfo;
    }

    public final void setVarNamesToInfo(final Map<String, String> varNamesToInfoMap) {
        this.varNamesToInfo = varNamesToInfoMap;
    }

    public final String getCode() {
        return code;
    }

    public final void setCode(final String codeString) {
        this.code = codeString;
    }

    public final void addVotingVariableName(final int voteNumber, final String varName) {
        voteVariableNameToVoteNumber.put(varName, voteNumber);
        varNamesToInfo.put(varName,
                InformationStringBuilder.genForVote(voteNumber, varName));
    }

    public final void addElectVariableName(final int electNumber, final String varName) {
        electVariableNameToElectNumber.put(varName, electNumber);
        varNamesToInfo.put(varName,
                InformationStringBuilder.genForResult(electNumber, varName));
    }

    public final void setVoteVariableNameToVoteNumber(final Map<String, Integer>
                                                        voteVarNameToVoteNumMap) {
        this.voteVariableNameToVoteNumber = voteVarNameToVoteNumMap;
    }

    public final Map<String, Integer> getVoteVariableNameToVoteNumber() {
        return voteVariableNameToVoteNumber;
    }

    public final Map<String, Integer> getElectVariableNameToElectNumber() {
        return electVariableNameToElectNumber;
    }

    public final Set<String> getGeneratedVotingVarNames() {
        return generatedVotingVarNames;
    }

    public final void setGeneratedVotingVarNames(final Set<String> genVotingVarNameSet) {
        this.generatedVotingVarNames = genVotingVarNameSet;
    }

    public final Set<String> getGeneratedElectVarNames() {
        return generatedElectVarNames;
    }

    public final void setGeneratedElectVarNames(final Set<String> genElectVarNameSet) {
        this.generatedElectVarNames = genElectVarNameSet;
    }

    public final void setElectVariableNameToElectNumber(final Map<String, Integer>
                                                            electVarNameToElectNumMap) {
        this.electVariableNameToElectNumber = electVarNameToElectNumMap;
    }

    public final String getVotesAmtMemberVarName() {
        return votesAmtMemberVarName;
    }

    public final String getVotesListMemberVarName() {
        return votesListMemberVarName;
    }

    public final CodeGenLoopBoundHandler getLoopBoundHandler() {
        return loopBoundHandler;
    }

    public final void setVotesAmtMemberVarName(final String amtMemberVarName) {
        this.votesAmtMemberVarName = amtMemberVarName;
    }

    public final void setVotesListMemberVarName(final String listMemberVarName) {
        this.votesListMemberVarName = listMemberVarName;
    }

    public final void setLoopboundHandler(final CodeGenLoopBoundHandler generatedLoopBoundHandler) {
        this.loopBoundHandler = generatedLoopBoundHandler;
    }

    public final void addInfo(final String varName, final String info) {
        varNamesToInfo.put(varName, info);
    }

    public final String getInfo(final String varName) {
        return varNamesToInfo.get(varName);
    }

    public final boolean hasInfo(final String varName) {
        return varNamesToInfo.containsKey(varName);
    }

    public final void setResultAmtMemberVarName(final String amtName) {
        resultAmtMemberVarName = amtName;
    }

    public final String getResultAmtMemberVarName() {
        return resultAmtMemberVarName;
    }

    public final void setResultListMemberVarName(final String listName) {
        resultListMemberVarName = listName;
    }

    public final String getResultListMemberVarName() {
        return resultListMemberVarName;
    }
}
