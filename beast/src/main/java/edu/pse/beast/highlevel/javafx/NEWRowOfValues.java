package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NEWRowOfValues {

	private int elementHeight;
	private int elementWidth;

	private int rowIndex;

	private ArrayList<String> values;
	private ArrayList<TextField> fields;

	private ElectionTypeContainer container;

	private int amountOfCandidates = 0;
	private ElectionSimulationModel parent;

	public NEWRowOfValues(ElectionSimulationModel parent, ElectionTypeContainer container, int amountOfCandidates,
			int rowIndex) {
		this.parent = parent;
		this.container = container;
		this.amountOfCandidates = amountOfCandidates;
		this.rowIndex = rowIndex;
		values = new ArrayList<>(amountOfCandidates);
		fields = new ArrayList<>(amountOfCandidates);

		for (int i = 0; i < amountOfCandidates; i++) {
			addColumn();
		}
	}

	public synchronized void addColumn() {
		amountOfCandidates++;

		if (values.size() == fields.size()) {
			values.add("0");
		}

		TextField toAdd = new TextField(values.get(fields.size()));

		toAdd.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				addValueEnforcer(toAdd, fields.size());

			}
		});

		toAdd.setMinSize(elementWidth, elementHeight);
		toAdd.setPrefSize(elementWidth, elementHeight);
		toAdd.setMaxSize(elementWidth, elementHeight);

		fields.add(toAdd);

		update();
	}

	public void removeColumn() {
		amountOfCandidates--;
		fields.remove(fields.size() - 1);
		update();
	}

	public void update() {
		for (Iterator<TextField> iterator = fields.iterator(); iterator.hasNext();) {
			TextField textField = (TextField) iterator.next();
			parent.getInputGridPane().getChildren().remove(textField);
		}

		for (int i = 0; i < amountOfCandidates; i++) {
			TextField field = fields.get(i);
			parent.getInputGridPane().add(field, i, rowIndex);
		}
	}

	private void checkAndInsertValue(String newValue, int position) {

		String vettedValue = container.getInputType().vetValue(newValue, container, this);

		values.set(position, vettedValue);
		
		if(position < fields.size()) {
			fields.get(position).setText(vettedValue);
		}
	}

	// getter and setter
	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public void setContainer(ElectionTypeContainer container) {
		this.container = container;

		for (int i = 0; i < values.size(); i++) {
			checkAndInsertValue("0", i);
		}

	}

	public int getAmountCandidates() {
		return amountOfCandidates;
	}

	public int getAmountVoters() {
		return getAmountVoters();
	}

	private void addValueEnforcer(TextField field, int index) {
		String vettedValue = this.container.getInputType().vetValue(field.getText(), container, this);
		field.setText(vettedValue);
		values.set(index, vettedValue);
	}

	public void setCandidates(int amountCandidates) {

		if (this.amountOfCandidates < amountCandidates) {
			while (this.amountOfCandidates < amountCandidates) {
				addColumn();
			}
		} else if (this.amountOfCandidates > amountCandidates) {
			while (this.amountOfCandidates > amountCandidates) {
				removeColumn();
			}
		}
		this.amountOfCandidates = amountCandidates;
		updateVetting();
	}

	/**
	 * vetts all numbers in the fields again, in case they are
	 * dependant of a variable that was changed
	 */
	public void updateVetting() {
		for (int i = 0; i < values.size(); i++) {

			checkAndInsertValue(values.get(i), i);
			
		}
	}
}
