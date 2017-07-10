package edu.pse.beast.electionSimulator;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.erichseifert.gral.graphics.Container;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;

public class RowOfValues extends JPanel implements DocumentListener {

	private int elementHeight;
	private int elementWidth;

	private int widthMultiplier;

	private int offset = 0;
	
	private ElectionInputWindow parent;

	private ArrayList<Integer> values;
	private ArrayList<JTextField> fields;

	private ElectionTypeContainer container;

	private int amountOfCandidates = 0;

	private boolean locked = false;

	public RowOfValues(ElectionInputWindow parent, ElectionTypeContainer container, int amountOfCandidates,
			int elementWidth, int elementHeight, int widthMultiplier) {
		this.parent = parent;
		this.container = container;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.widthMultiplier = widthMultiplier;
		values = new ArrayList<>(amountOfCandidates);
		fields = new ArrayList<>(amountOfCandidates);

		this.setVisible(true);

		for (int i = 0; i < amountOfCandidates; i++) {
			addColumn();
		}
		update();
		this.repaint();
	}

	public void addColumn() {
		amountOfCandidates++;

		values.add(0);
		fields.add(new JTextField("0"));

		fields.get(fields.size() - 1).getDocument().addDocumentListener(this);

		update();
	}

	public void removeColumn() {
		amountOfCandidates--;
		this.remove(fields.get(fields.size() - 1));
		fields.remove(fields.size() - 1);
		System.out.println("remove");
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

	private void checkValue(int newValue, int position) {
		int vettedValue = 0;
		switch (container.getInputID()) {
		case APPROVAL:
			if (newValue != 0 && newValue != 1) {
				vettedValue = 0;
			}
			break;

		case PREFERENCE:
			if (newValue < 0 || newValue > amountOfCandidates) {
				vettedValue = 0;
			} else {
				vettedValue = newValue;
			}
			break;

		case SINGLE_CHOICE:

			if (newValue == 1) {
				for (int i = 0; i < values.size(); i++) {
					values.set(i, 0);
				}
				values.set(position, 1);
			} else {
				vettedValue = 0;
			}

			break;

		case WEIGHTED_APPROVAL:
			if (newValue < 0 || newValue > amountOfCandidates) {
				vettedValue = 0;
			} else {
				vettedValue = newValue;
			}
			break;

		default:
			System.out.println("unknown election type");
		}

		// update all values
		for (int i = 0; i < values.size(); i++) {
			try {
					fields.get(i).setText("" + values.get(i));
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		values.set(position, vettedValue);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		allEventUpdates(arg0);
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		allEventUpdates(arg0);
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		allEventUpdates(arg0);
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
		update();
	}

	private void allEventUpdates(DocumentEvent event) {
		if (locked) {
			return;
		}
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).getDocument() == event.getDocument()) {
				final int finalI = i;
				int value;
				try {
					value = Integer.parseInt(fields.get(i).getText());
				} catch (Exception e) {
					value = 0;
				}
				
				final int finalV = value;
				
				Runnable callValueChecker = new Runnable() {
					@Override
					public void run() {
						locked = true;
						checkValue(finalV, finalI);
						locked = false;
					}
				};

				SwingUtilities.invokeLater(callValueChecker);
			}
		}
	}
}
