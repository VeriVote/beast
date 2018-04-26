package edu.pse.beast.electionSimulator.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ElectionSimulationModel extends Observable implements NameInterface {

	private String name;

	private List<NEWRowOfValues> rows = new ArrayList<NEWRowOfValues>();

	private List<TextField> candidates = new ArrayList<TextField>();

	private List<TextField> voters = new ArrayList<TextField>();

	private final GridPane inputGridPane;

	private final GridPane voterGridPane;

	private final GridPane candidateGridPane;

	private ElectionTypeContainer container;

	private int amountCandidates = 0;
	private int amountVoters = 0;
	private int amountSeats = 0;

	private int currentRows = 0;
	private int maxRows = 0;

	private int currentCandidates = 0;

	public ElectionSimulationModel(ElectionTypeContainer container, GridPane inputGridPane, GridPane voterGridPane,
			GridPane candidateGridPane) {
		this.container = container;

		this.inputGridPane = inputGridPane;
		this.voterGridPane = voterGridPane;
		this.candidateGridPane = candidateGridPane;
	}

	private void addRow() {
		if (currentRows == maxRows) {
			NEWRowOfValues toAdd = new NEWRowOfValues(this, container, this.getAmountCandidates(), currentRows);
			rows.add(toAdd);

			TextField newVoter = new TextField("voter" + currentRows);

			voters.add(newVoter);

			voterGridPane.add(newVoter, 0, currentRows);

			currentRows++;
			maxRows++;
		} else { // we already have a row with for this index, so we just make it visible again
			rows.get(currentRows).setCandidates(this.getAmountCandidates());

			voterGridPane.add(voters.get(currentRows), 0, currentRows);

			currentRows++;
		}
	}

	private void removeRow() {

		if (currentRows > 0) {
			rows.remove(rows.size() - 1);

			voterGridPane.getChildren().remove(voters.get(rows.size() - 1));

			currentRows--;
		}
	}

	@Override
	public void setNewName(String newName) {
		this.name = newName;
	}

	@Override
	public String getName() {
		return name;
	}

	public void changeContainer(ElectionTypeContainer container) {
		this.container = container;
		for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			NEWRowOfValues currentRow = iterator.next();
			currentRow.setContainer(container);
		}
	}

	public List<TextField> getCandidates() {
		return candidates;
	}

	public void setCandidates(ArrayList<TextField> candidates) {
		this.candidates = candidates;
	}

	public ElectionTypeContainer getContainer() {
		return container;
	}

	public void setContainer(ElectionTypeContainer container) {
		this.container = container;
	}

	public int getAmountCandidates() {
		return amountCandidates;
	}

	public int getAmountSeats() {
		return amountSeats;
	}

	public int getAmountVoters() {
		return amountVoters;
	}

	public void setAmountCandidates(int amountCandidates) {
		this.amountCandidates = container.getInputType().vetAmountCandidates(amountCandidates);
		updateCandidates();
	}

	public void setAmountVoters(int amountVoters) {
		this.amountVoters = container.getInputType().vetAmountVoters(amountVoters);
		updateVoters();
	}

	public void setAmountSeats(int amountSeats) {
		this.amountSeats = container.getInputType().vetAmountSeats(amountSeats);
		updateVetting();
	}

	public List<NEWRowOfValues> getRows() {
		return rows;
	}

	public List<TextField> getVoters() {
		return voters;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void reset() {
		rows = new ArrayList<NEWRowOfValues>();
	}

	public GridPane getInputGridPane() {
		return inputGridPane;
	}

	private void updateVoters() {
		if (currentRows < amountVoters) {
			while (currentRows < amountVoters) {
				addRow();
			}
		} else {
			while (currentRows > amountVoters) {
				removeRow();
			}
		}

		updateVetting();
	}

	private void updateCandidates() {

		System.out.println("update");
		
		for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			NEWRowOfValues row = (NEWRowOfValues) iterator.next();
			row.setCandidates(amountCandidates);
		}
		if (currentCandidates < amountCandidates) {
			while (currentCandidates < amountCandidates) {
				TextField candToAdd = new TextField("cand" + currentCandidates);

				candidates.add(candToAdd);
				candidateGridPane.add(candToAdd, currentCandidates, 0);

				currentCandidates++;
			}
		} else {
			while (currentCandidates > amountCandidates) {
				
				candidateGridPane.getChildren().remove(candidates.get(currentCandidates - 1));

				currentCandidates++;
			}
		}

		updateVetting();
	}

	private void updateVetting() {
		for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			NEWRowOfValues row = (NEWRowOfValues) iterator.next();
			row.updateVetting();
		}
	}
}
