package edu.pse.beast.electionsimulator;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionsimulator.model.ElectionSimulationModel;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.InputType;
import javafx.scene.layout.GridPane;

public class ElectionSimulation implements MenuBarInterface {
	private static final String CSV_SEPARATOR = ",";

	private ElectionTypeContainer container;
	private ElectionSimulationModel model;

	private GridPane inputGridPane;
	private GridPane voterGridPane;
	private GridPane candidateGridPane;

	private SaverLoader saverLoader;

	public ElectionSimulation(ElectionTypeContainer container, GridPane inputGridPane, GridPane voterGridPane,
			GridPane candidateGridPane) {
		this.container = container;
		this.saverLoader = new SaverLoader(".elecIn", "Election Input Data", this);
		this.inputGridPane = inputGridPane;
		this.voterGridPane = voterGridPane;
		this.candidateGridPane = candidateGridPane;
		inputGridPane.setHgap(10);
		inputGridPane.setVgap(10);
		voterGridPane.setHgap(10);
		voterGridPane.setVgap(10);
		candidateGridPane.setHgap(10);
		candidateGridPane.setVgap(10);
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
		inputGridPane.getChildren().clear();
		voterGridPane.getChildren().clear();
		candidateGridPane.getChildren().clear();
		model = new ElectionSimulationModel(container, inputGridPane, voterGridPane, candidateGridPane);
		model.setAmountCandidates(1);
		model.setAmountVoters(1);
		GUIController.getController().getInputVoters().setText("1");
		GUIController.getController().getInputCandidates().setText("1");
		GUIController.getController().getInputSeats().setText("1");
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

	public ElectionSimulationData getVotingData() {

		// TODO when multiple Model checkers would be installed, some distinction would
		// have to be made here
		List<NEWRowOfValues> rows = model.getRows();

		InputType inType = container.getInputType();

		CBMCResultValueArray topArrayValue = new CBMCResultValueArray();

		List<CBMCResultValueWrapper> valueWrappers = new ArrayList<CBMCResultValueWrapper>();

		for (int i = 0; i < this.getNumCandidates(); i++) {
			CBMCResultValue result = inType.convertRowToResultValue(rows.get(i));

			valueWrappers.add(new CBMCResultValueWrapper(result));
		}

		topArrayValue.setValue(valueWrappers);

		ElectionSimulationData toReturn = new ElectionSimulationData(getNumVoters(), getNumCandidates(), getNumSeats(),
				new CBMCResultValueWrapper(topArrayValue));

		return toReturn;
	}

	public int getNumVotingPoints() {
		return container.getInputType().getNumVotingPoints(null);
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

	public void saveAs(File file) {
		saverLoader.save(file, generateSaveString());
	}

	@Override
	public void open() {
		String input = saverLoader.load();
		openInput(input, true);
	}

	private void openInput(String input, boolean bringToFront) {
		reset();
		if (!input.equals("")) {
			String[] lines = input.split("\n");
			for (int y = 0; y < lines.length; y++) {
				String[] values = lines[y].replaceAll("\\r", "").split(CSV_SEPARATOR);
				if (y == 0) {
					GUIController.getController().getInputVoters().setText("" + values[0]);
					GUIController.getController().getInputCandidates().setText("" + values[1]);
					GUIController.getController().getInputSeats().setText("" + values[2]);
				} else {
					for (int x = 0; x < values.length; x++) {
						model.setValue(x, (y - 1), values[x]);
					}
				}
			}
		}
		if (bringToFront) {
			bringToFront();
		}
	}

	public void open(File file) {
		if (file.exists()) {
			String input = saverLoader.load(file);
			openInput(input, false);
		}
	}

	public void bringToFront() {
		GUIController.getController().getMainTabPane().getSelectionModel()
				.select(GUIController.getController().getInputTab());
	}

	@Override
	public void save() {
		saverLoader.save("", generateSaveString());
	}

	private String generateSaveString() {
		String saveString = "";

		saveString = model.getAmountVoters() + CSV_SEPARATOR + model.getAmountCandidates() + CSV_SEPARATOR
				+ model.getAmountSeats() + "\n";

		List<NEWRowOfValues> rows = model.getRows();

		for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			NEWRowOfValues row = (NEWRowOfValues) iterator.next();
			for (Iterator<String> valueIterator = row.getValues().iterator(); valueIterator.hasNext();) {
				String value = (String) valueIterator.next();
				saveString = saveString + value;
				if (valueIterator.hasNext()) { // another value will follow
					saveString = saveString + CSV_SEPARATOR;
				}
			}
			if (iterator.hasNext()) {
				saveString = saveString + "\n";
			}
		}
		return saveString;
	}

	@Override
	public void saveAs() {
		saverLoader.saveAs("", generateSaveString());
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
	}

	@Override
	public void cut() {
		// TODO Auto-generated method stub
	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void paste() {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	@Override
	public void autoComplete() {
		// do nothing
	}
}