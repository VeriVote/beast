package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextField;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import javafx.scene.control.TextField;

public class NEWRowOfValues extends TextField {

	private int elementHeight;
	private int elementWidth;

	private int widthMultiplier;

	private int offset = 0;
	
	private ArrayList<String> values;
	private ArrayList<TextField> fields;

	private ElectionTypeContainer container;

	private int amountOfCandidates = 0;

	public NEWRowOfValues(ElectionSimulationModel parent, ElectionTypeContainer container, int amountOfCandidates) {
		this.container = container;
		values = new ArrayList<>(amountOfCandidates);
		fields = new ArrayList<>(amountOfCandidates);
		this.setVisible(true);

		for (int i = 0; i < amountOfCandidates; i++) {
			addColumn();
		}
	}

	public void addColumn() {
		amountOfCandidates++;

		values.add("0");
		
		JTextField toAdd = new JTextField("0");
		toAdd.getDocument().addDocumentListener(this);
		toAdd.setSize(elementWidth, elementHeight);
		
		fields.add(toAdd);
		
		update();
	}

	public void removeColumn() {
		amountOfCandidates--;
		this.remove(fields.get(fields.size() - 1));
		fields.remove(fields.size() - 1);
		update();
	}

	public void update() {
		for (Iterator<JTextField> iterator = fields.iterator(); iterator.hasNext();) {
			JTextField field = (JTextField) iterator.next();
			this.remove(field);
		}

		this.setSize(widthMultiplier * (amountOfCandidates), elementHeight * 2);

		for (int i = 0; i < amountOfCandidates; i++) {
			fields.get(i).setBounds(widthMultiplier * i - offset, 0, elementWidth, elementHeight);
			this.add(fields.get(i));
		}

		this.setVisible(true);
		this.repaint();
	}

	private void checkAndInsertValue(String newValue, int position) {
		
		String vettedValue = container.getInputType().vetValue(newValue, container, this);

		values.set(position, vettedValue);
		
		// update all values that will get shown
		for (int i = 0; i < values.size(); i++) {
			try {
					fields.get(i).setText("" + values.get(i));
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	
	//getter and setter
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
}
