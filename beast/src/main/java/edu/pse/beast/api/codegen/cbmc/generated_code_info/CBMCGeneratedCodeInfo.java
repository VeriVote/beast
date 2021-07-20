package edu.pse.beast.api.codegen.cbmc.generated_code_info;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;

public class CBMCGeneratedCodeInfo {
    private String code;
    private CodeGenLoopBoundHandler loopBoundHandler;
    private Map<String, Integer> voteVariableNameToVoteNumber = new HashMap<>();
    private Map<String, Integer> electVariableNameToElectNumber = new HashMap<>();
    private Map<String, String> varNamesToInfo = new HashMap<>();

    private Set<String> generatedVotingVarNames = new HashSet<String>();
    private Set<String> generatedElectVarNames = new HashSet<String>();

    private String votesAmtMemberVarName;
    private String votesListMemberVarName;

    private String resultAmtMemberVarName;
    private String resultListMemberVarName;

    public void addedGeneratedVotingVar(final String name) {
        generatedVotingVarNames.add(name);
    }

    public void addedGeneratedElectVar(final String name) {
        generatedElectVarNames.add(name);
    }

    public Map<String, String> getVarNamesToInfo() {
        return varNamesToInfo;
    }

    public void setVarNamesToInfo(final Map<String, String> varNamesToInfoMap) {
        this.varNamesToInfo = varNamesToInfoMap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String codeString) {
        this.code = codeString;
    }

    public void addVotingVariableName(final int voteNumber, final String varName) {
        voteVariableNameToVoteNumber.put(varName, voteNumber);
        varNamesToInfo.put(varName,
                InformationStringBuilder.genForVote(voteNumber, varName));
    }

    public void addElectVariableName(final int electNumber, final String varName) {
        electVariableNameToElectNumber.put(varName, electNumber);
        varNamesToInfo.put(varName,
                InformationStringBuilder.genForResult(electNumber, varName));
    }

    public void setVoteVariableNameToVoteNumber(final Map<String, Integer>
                                                    voteVarNameToVoteNumMap) {
        this.voteVariableNameToVoteNumber = voteVarNameToVoteNumMap;
    }

    public Map<String, Integer> getVoteVariableNameToVoteNumber() {
        return voteVariableNameToVoteNumber;
    }

    public Map<String, Integer> getElectVariableNameToElectNumber() {
        return electVariableNameToElectNumber;
    }

    public Set<String> getGeneratedVotingVarNames() {
        return generatedVotingVarNames;
    }

    public void setGeneratedVotingVarNames(final Set<String> genVotingVarNameSet) {
        this.generatedVotingVarNames = genVotingVarNameSet;
    }

    public Set<String> getGeneratedElectVarNames() {
        return generatedElectVarNames;
    }

    public void setGeneratedElectVarNames(final Set<String> genElectVarNameSet) {
        this.generatedElectVarNames = genElectVarNameSet;
    }

    public void setElectVariableNameToElectNumber(final Map<String, Integer>
                                                    electVarNameToElectNumMap) {
        this.electVariableNameToElectNumber = electVarNameToElectNumMap;
    }

    public String getVotesAmtMemberVarName() {
        return votesAmtMemberVarName;
    }

    public String getVotesListMemberVarName() {
        return votesListMemberVarName;
    }

    public CodeGenLoopBoundHandler getLoopBoundHandler() {
        return loopBoundHandler;
    }

    public void setVotesAmtMemberVarName(final String amtMemberVarName) {
        this.votesAmtMemberVarName = amtMemberVarName;
    }

    public void setVotesListMemberVarName(final String listMemberVarName) {
        this.votesListMemberVarName = listMemberVarName;
    }

    public void setLoopboundHandler(final CodeGenLoopBoundHandler generatedLoopBoundHandler) {
        this.loopBoundHandler = generatedLoopBoundHandler;
    }

    public void addInfo(final String varName, final String info) {
        varNamesToInfo.put(varName, info);
    }

    public String getInfo(final String varName) {
        return varNamesToInfo.get(varName);
    }

    public boolean hasInfo(final String varName) {
        return varNamesToInfo.containsKey(varName);
    }

    public void setResultAmtMemberVarName(final String amtName) {
        resultAmtMemberVarName = amtName;
    }

    public String getResultAmtMemberVarName() {
        return resultAmtMemberVarName;
    }

    public void setResultListMemberVarName(final String listName) {
        resultListMemberVarName = listName;
    }

    public String getResultListMemberVarName() {
        return resultListMemberVarName;
    }
}
