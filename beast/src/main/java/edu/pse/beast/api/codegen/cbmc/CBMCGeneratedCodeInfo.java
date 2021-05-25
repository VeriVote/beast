package edu.pse.beast.api.codegen.cbmc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;

public class CBMCGeneratedCodeInfo {
	private String code;
	private CodeGenLoopBoundHandler loopBoundHandler;
	private Map<String, Integer> voteVariableNameToVoteNumber = new HashMap<>();
	private Map<String, Integer> electVariableNameToElectNumber = new HashMap<>();

	private String amtMemberVarName;
	private String listMemberVarName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void addVotingVariableName(int voteNumber, String varName) {
		voteVariableNameToVoteNumber.put(varName, voteNumber);
	}

	public void addElectVariable(int electNumber, String varName) {
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
}
