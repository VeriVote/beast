package edu.pse.beast.api.codegen;

public class CodeGenOptions {
	private String votesLowerBoundVarName;
	private String votesUpperBoundVarName;
	private String cbmcAssumeName;
	private String cbmcAssertName;
	private String cbmcNondetUintName;
	private String cbmcAmountVotersVarName;
	private String cbmcAmountCandidatesVarName;
	private String voteFuncName;

	public String getCbmcAmountVotersVarName() {
		return cbmcAmountVotersVarName;
	}

	public String getCbmcAssertName() {
		return cbmcAssertName;
	}

	public void setCbmcAssertName(String cbmcAssertName) {
		this.cbmcAssertName = cbmcAssertName;
	}

	public String getCbmcAmountCandidatesVarName() {
		return cbmcAmountCandidatesVarName;
	}

	public void setCbmcAmountVotesVarName(String cbmcAmountVotesVarName) {
		this.cbmcAmountVotersVarName = cbmcAmountVotesVarName;
	}

	public void setCbmcAmountCandidatesVarName(String cbmcAmountCandidatesVarName) {
		this.cbmcAmountCandidatesVarName = cbmcAmountCandidatesVarName;
	}

	public String getVotesLowerBoundVarName() {
		return votesLowerBoundVarName;
	}

	public String getVotesUpperBoundVarName() {
		return votesUpperBoundVarName;
	}

	public String getCbmcAssumeName() {
		return cbmcAssumeName;
	}

	public String getCbmcNondetUintName() {
		return cbmcNondetUintName;
	}

	public void setVotesLowerBoundVarName(String votesLowerBoundVarName) {
		this.votesLowerBoundVarName = votesLowerBoundVarName;
	}

	public void setVotesUpperBoundVarName(String votesUpperBoundVarName) {
		this.votesUpperBoundVarName = votesUpperBoundVarName;
	}

	public void setCbmcAssumeName(String cbmcAssumeName) {
		this.cbmcAssumeName = cbmcAssumeName;
	}

	public void setCbmcNondetUintName(String cbmcNondetUintName) {
		this.cbmcNondetUintName = cbmcNondetUintName;
	}

	public String getVoteFuncName() {
		return voteFuncName;
	}

	public void setVoteFuncName(String voteFuncName) {
		this.voteFuncName = voteFuncName;
	}
	
	

}