package edu.pse.beast.api.codegen.cbmc.generated_code_info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;

public class CBMCGeneratedCodeInfo {
	private String code;
	private CodeGenLoopBoundHandler loopBoundHandler;
	private Map<String, Integer> voteVariableNameToVoteNumber = new HashMap<>();
	private Map<String, Integer> electVariableNameToElectNumber = new HashMap<>();
	private Map<String, String> varNamesToInfo = new HashMap<>();

	private Set<String> generatedVotingVarNames = new HashSet();
	private Set<String> generatedElectVarNames = new HashSet();

	private String votesAmtMemberVarName;
	private String votesListMemberVarName;

	private String resultAmtMemberVarName; 
	private String resultListMemberVarName;
	
	public void addedGeneratedVotingVar(String name) {
		generatedVotingVarNames.add(name);
	}

	public void addedGeneratedElectVar(String name) {
		generatedElectVarNames.add(name);
	}

	public Map<String, String> getVarNamesToInfo() {
		return varNamesToInfo;
	}

	public void setVarNamesToInfo(Map<String, String> varNamesToInfo) {
		this.varNamesToInfo = varNamesToInfo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void addVotingVariableName(int voteNumber, String varName) {
		voteVariableNameToVoteNumber.put(varName, voteNumber);
		varNamesToInfo.put(varName,
				InformationStringBuilder.genForVote(voteNumber, varName));
	}

	public void addElectVariableName(int electNumber, String varName) {
		electVariableNameToElectNumber.put(varName, electNumber);
		varNamesToInfo.put(varName,
				InformationStringBuilder.genForResult(electNumber, varName));
	}

	public void setVoteVariableNameToVoteNumber(
			Map<String, Integer> voteVariableNameToVoteNumber) {
		this.voteVariableNameToVoteNumber = voteVariableNameToVoteNumber;
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

	public void setGeneratedVotingVarNames(
			Set<String> generatedVotingVarNames) {
		this.generatedVotingVarNames = generatedVotingVarNames;
	}

	public Set<String> getGeneratedElectVarNames() {
		return generatedElectVarNames;
	}

	public void setGeneratedElectVarNames(Set<String> generatedElectVarNames) {
		this.generatedElectVarNames = generatedElectVarNames;
	}

	public void setElectVariableNameToElectNumber(
			Map<String, Integer> electVariableNameToElectNumber) {
		this.electVariableNameToElectNumber = electVariableNameToElectNumber;
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

	public void setVotesAmtMemberVarName(String amtMemberVarName) {
		this.votesAmtMemberVarName = amtMemberVarName;
	}

	public void setVotesListMemberVarName(String listMemberVarName) {
		this.votesListMemberVarName = listMemberVarName;
	}

	public void setLoopboundHandler(CodeGenLoopBoundHandler loopBoundHandler) {
		this.loopBoundHandler = loopBoundHandler;
	}

	public void addInfo(String varName, String info) {
		varNamesToInfo.put(varName, info);
	}

	public String getInfo(String varName) {
		return varNamesToInfo.get(varName);
	}

	public boolean hasInfo(String varName) {
		return varNamesToInfo.containsKey(varName);
	}

	public void setResultAmtMemberVarName(String amtName) {
		resultAmtMemberVarName = amtName;
	}

	public String getResultAmtMemberVarName() {
		return resultAmtMemberVarName;
	}

	public void setResultListMemberVarName(String listName) {
		resultListMemberVarName = listName;
	}

	public String getResultListMemberVarName() {
		return resultListMemberVarName;
	}
}
