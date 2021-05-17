package edu.pse.beast.api.specificcbmcrun;

import edu.pse.beast.api.electiondescription.CElectionDescription;

public class SpecificCBMCRunParametersInfo {
	private CElectionDescription descr;
	private VotingParameters votingParameters;

	public CElectionDescription getDescr() {
		return descr;
	}

	public void setDescr(CElectionDescription descr) {
		this.descr = descr;
	}

	public VotingParameters getVotingParameters() {
		return votingParameters;
	}

	public void setVotingParameters(VotingParameters votingParameters) {
		this.votingParameters = votingParameters;
	}

	public int getV() {
		return votingParameters.getV();
	}

	public int getS() {
		return votingParameters.getS();
	}

	public int getC() {
		return votingParameters.getC();
	}

}
