package edu.pse.beast.api.codegen.cbmc.info;

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

	private String amtMemberVarName;
	private String listMemberVarName;

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
	}

	public void addElectVariableName(int electNumber, String varName) {
		electVariableNameToElectNumber.put(varName, electNumber);
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

	public String getAmtMemberVarName() {
		return amtMemberVarName;
	}

	public String getListMemberVarName() {
		return listMemberVarName;
	}

	public CodeGenLoopBoundHandler getLoopBoundHandler() {
		return loopBoundHandler;
	}

	public void setAmtMemberVarName(String amtMemberVarName) {
		this.amtMemberVarName = amtMemberVarName;
	}

	public void setListMemberVarName(String listMemberVarName) {
		this.listMemberVarName = listMemberVarName;
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
}
