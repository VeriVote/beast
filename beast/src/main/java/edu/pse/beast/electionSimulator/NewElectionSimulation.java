package edu.pse.beast.electionSimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class NewElectionSimulation implements ElectionDescriptionChangeListener {

	private static ElectionTypeContainer container;

	private static ElectionSimulationModel model;

	public NewElectionSimulation() {

	}

	public synchronized void updateRows() {
		for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
			RowOfValues row = (RowOfValues) iterator.next();
			row.update();
		}
	}

	@Override
	public void inputChanged(InputType input) {
		electionTypeChanged();
	}

	@Override
	public void outputChanged(OutputType output) {
		electionTypeChanged();
	}

	/**
	 * gets called when one of the changed listener gets called here, we update the
	 * models which are used
	 */
	private void electionTypeChanged() {
		// ElectionSimulation.container =
		// centralObjectProvider.getElectionDescriptionSource().getElectionDescription()
		// .getContainer();
		// model.changeContainer(container);
	}

	// TODO
	/**
	 * resets all fields and returns the view back to its original state
	 */
	public void reset() {
		model.reset();
	}

	public void save() {
		// TODO Auto-generated method stub

	}

	public void saveAs() {
		// TODO Auto-generated method stub

	}

	public void loadData() {
		// TODO Auto-generated method stub

	}

	public void undo() {
		// TODO Auto-generated method stub

	}

	public void redo() {
		// TODO Auto-generated method stub

	}

	public static String[][] getVotingData() {
		String[][] votingData = { { "0" }, { "0" } };

		votingData = new String[model.getAmountVoters()][model.getAmountCandidates()];

		// read the data in a 2d array
		for (int i = 0; i < model.getAmountVoters(); i++) {
			for (int j = 0; j < model.getAmountCandidates(); j++) {
				votingData[i][j] = model.getRows().get(i).getValues().get(j);
			}
		}

		return votingData;
	}

	public static int getNumVoters() {
		return model.getAmountVoters();
	}

	public static int getNumCandidates() {
		return model.getAmountCandidates();
	}

	public static int getNumSeats() {
		return model.getAmountSeats();
	}

	public static List<List<String>> getVotingDataListofList() {
		List<List<String>> toReturn = new ArrayList<List<String>>();

		String[][] data = getVotingData();

		for (int i = 0; i < data.length; i++) {
			List<String> tmp = new ArrayList<String>();
			for (int j = 0; j < data[0].length; j++) {
				tmp.add(data[i][j]);
			}
			toReturn.add(tmp);
		}

		return toReturn;
	}

	public static int getNumVotingPoints() {
		return container.getInputType().getNumVotingPoints(getVotingData());
	}

	public static String getPartyName(int index) {
		return model.getCandidates().get(index).getText();
	}

	public static String getVoterName(int index) {
		return model.getVoters().get(index).getText();
	}

	public void numVotersChanged(int numVoters) {
		model.setAmountVoters(numVoters);
	}
	
	public void numCandidatesChanged(int numCandidates) {
		model.setAmountCandidates(numCandidates);
	}
	
	public void numSeatsChanged(int numSeats) {
		model.setAmountSeats(numSeats);
	}
}
