package edu.pse.beast.types.cbmctypes.outputtypes;

import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class Parliament extends CBMCOutputType {

	@Override
	public String getOutputString() {
		return "struct result";
	}

	@Override
	public String getOutputIDinFile() {
		return "CAND_PER_SEAT";
	}

	@Override
	public boolean isOutputOneCandidate() {
		return false;
	}

	@Override
	public Long[] extractResult(List<String> toExtract) {
		return super.helper.readOneDimVar("new_result", toExtract).get(0).getArray();
	}

	@Override
	public List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract) {
		return super.helper.readOneDimVar("elect", toExtract);
	}

	@Override
	public List<CBMCResultWrapperLong> readElect(List<String> toExtract) {
		return null;
	}
}
