package edu.pse.beast.electionSimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.layout.GridPane;

public class NewElectionSimulation implements ElectionDescriptionChangeListener {

	private ElectionTypeContainer container;

	private ElectionSimulationModel model;
	
	public NewElectionSimulation(
		ElectionTypeContainer container, GridPane inputGridPane, GridPane voterGridPane, GridPane candidateGridPane) {
			this.container = container;
			model = new ElectionSimulationModel(container, inputGridPane, voterGridPane, candidateGridPane);
	}

	public void updateContainer(ElectionTypeContainer container) {
		this.container = container;
		model.changeContainer(container);
	}

	public synchronized void updateRows() {
		for (Iterator<NEWRowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
			NEWRowOfValues row = (NEWRowOfValues) iterator.next();
			row.update();
		}
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

	public String[][] getVotingData() {
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

	public int getNumVoters() {
		return model.getAmountVoters();
	}

	public int getNumCandidates() {
		return model.getAmountCandidates();
	}

	public int getNumSeats() {
		return model.getAmountSeats();
	}

	public List<List<String>> getVotingDataListofList() {
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

	public int getNumVotingPoints() {
		return container.getInputType().getNumVotingPoints(getVotingData());
	}

	public String getPartyName(int index) {
		return model.getCandidates().get(index).getText();
	}

	public String getVoterName(int index) {
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

	public String setAndVetVoterNumber(String toVet) {
		int vetted = container.getInputType().vetAmountVoters(Integer.parseInt(toVet));
		model.setAmountVoters(vetted);
		
		return "" + vetted;
	}
	
	public String setAndVetCandidateNumber(String toVet) {
		int vetted = container.getInputType().vetAmountCandidates(Integer.parseInt(toVet));
		model.setAmountCandidates(vetted);
		
		return "" + vetted;
	}
	
	public String setAndVetSeatNumber(String toVet) {
		int vetted = container.getInputType().vetAmountSeats(Integer.parseInt(toVet));
		model.setAmountSeats(vetted);
		
		return "" + vetted;
	}

	@Override
	public void inputChanged(InputType input) {
		System.out.println("todo? ");
	}

	@Override
	public void outputChanged(OutputType output) {
		inputChanged(null);
	}
}
