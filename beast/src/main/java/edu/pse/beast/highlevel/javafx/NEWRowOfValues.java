package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NEWRowOfValues {

	private double elementHeight;
	private double elementWidth;

	private int rowIndex;

	private ArrayList<String> values;
	private ArrayList<TextField> fields;

	private ElectionTypeContainer container;

	private int amountOfCandidates = 0;
	private ElectionSimulationModel parent;
	private boolean disabled;

	public NEWRowOfValues(ElectionSimulationModel parent, ElectionTypeContainer container, int amountOfCandidates,
			int rowIndex, double elementWidth, double elementHeight) {
		this.parent = parent;
		this.container = container;
		this.rowIndex = rowIndex;

		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		values = new ArrayList<>(amountOfCandidates);
		fields = new ArrayList<>(amountOfCandidates);

		this.setCandidates(amountOfCandidates);
	}

	public synchronized void addColumn() {
		if (values.size() == amountOfCandidates) {
			values.add("0");
		}

		TextField field = new TextField(values.get(amountOfCandidates));

		field.setMinSize(elementWidth, elementHeight);
		field.setMaxSize(elementWidth, elementHeight);
		field.setPrefSize(elementWidth, elementHeight);

		fields.add(field);

		field.textProperty().addListener(new ChangeListener<String>() {
			
			int position = fields.indexOf(field);;
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(() -> {
					checkAndInsertValue(newValue, position);
				});
			}
		});

		amountOfCandidates++;
		update();
	}

	public synchronized void removeColumn() {
		if (fields.size() > 0) {
			amountOfCandidates--;
		}
		update();
	}

	public void update() {
		if (!disabled) {
			for (Iterator<TextField> iterator = fields.iterator(); iterator.hasNext();) {
				TextField textField = (TextField) iterator.next();
				parent.getInputGridPane().getChildren().remove(textField);
			}

			for (int i = 0; i < amountOfCandidates; i++) {
				TextField field = fields.get(i);
				field.setText(values.get(i));
				parent.getInputGridPane().add(field, i, rowIndex);
			}
		}
	}

	private void checkAndInsertValue(String newValue, int position) {
		String vettedValue = container.getInputType().vetValue(newValue, container, this);
		
		values.set(position, vettedValue);

		if (position < fields.size()) {
			fields.get(position).setText(vettedValue);
		}
		update();
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
//
//	private synchronized void addValueEnforcer(TextField field, int index) {
//		System.out.println(field.getText());
//		String vettedValue = this.container.getInputType().vetValue(field.getText(), container, this);
//		field.setText(vettedValue);
//		values.set(index, vettedValue);
//	}

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
	 * vetts all numbers in the fields again, in case they are dependant of a
	 * variable that was changed
	 */
	public void updateVetting() {
		for (int i = 0; i < values.size(); i++) {
			checkAndInsertValue(values.get(i), i);
		}
	}

	public void enable() {
		this.disabled = false;
		update();
	}

	public void disable() {
		this.disabled = true;
		for (Iterator<TextField> iterator = fields.iterator(); iterator.hasNext();) {
			TextField textField = (TextField) iterator.next();
			parent.getInputGridPane().getChildren().remove(textField);
		}
	}

	public void setValue(int x, String value) {
		checkAndInsertValue(value, x);
	}
}
