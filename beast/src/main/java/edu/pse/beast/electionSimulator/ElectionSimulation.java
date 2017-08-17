package edu.pse.beast.electionSimulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;

import javax.swing.JTextField;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.electionSimulator.View.ElectionSimulationWindow;
import edu.pse.beast.stringresource.StringLoaderInterface;

public class ElectionSimulation
		implements Runnable, ActionListener, ComponentListener, AdjustmentListener, MouseWheelListener {

	private ElectionTypeContainer container;

	private boolean react = false;
	
	private boolean running = false;
	
	private enum Modes {
		compileAndRun,
		searchMinDiffAndShow;
	}

	private ElectionSimulationWindow view;
	private ElectionSimulationModel model;

	public ElectionSimulation(ElectionTypeContainer container) {
		this(container, new StringLoaderInterface("de"));
	}

	public ElectionSimulation(ElectionTypeContainer container, StringLoaderInterface sli) {
		this.container = container;
		this.model = new ElectionSimulationModel(container);
		this.view = new ElectionSimulationWindow(sli, container, this, model);
		this.start();
		react = true;
	}

	public void changeContainer(ElectionTypeContainer container) {
		model.changeContainer(container);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		if (react) {
			int amount = event.getWheelRotation();
			if (amount > 0) {
				model.setVerticalOffset(Math.min(model.getVerticalOffset() + amount * 5,
						(model.getAmountVoters() - 1) * model.getElementHeight() * 2));
				view.update();
			} else {
				model.setVerticalOffset(Math.max(model.getVerticalOffset() + amount * 5, 0));
				view.update();
			}
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (react) {
			if (e.getSource() == view.horizontalScroll) {
				model.setHorizontalOffset(view.horizontalScroll.getValue());
			}

			if (e.getSource() == view.verticalScroll) {
				model.setVerticalOffset(view.verticalScroll.getValue());
			}
			view.update();
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		if (react) {
			model.setFieldsPerWidth((int) ((view.getWidth() - 2 * (model.getBorderMarginSmall()))
					/ (1.25 * model.getWidthMultiplier())));
			model.setFieldsPerHeight((int) ((view.getHeight() - 2 * (model.getBorderMarginSmall()))
					/ (1.25 * model.getHeightMultiplier())));

			updateRows();
			view.update();
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (react) { // if the main plane should react to inputs, these conditions get checked
			if (e.getSource() == view.addCandidate) {
				model.setAmountCandidates(model.getAmountCandidates() + 1);
				for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
					RowOfValues row = (RowOfValues) iterator.next();
					row.addColumn();
					row.repaint();
				}
				model.getCandidates().add(new JTextField("C" + model.getAmountCandidates()));
			} else if (e.getSource() == view.removeCandidate) {
				if (model.getAmountCandidates() > 1) {
					model.setAmountCandidates(model.getAmountCandidates() - 1);
					for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
						RowOfValues row = (RowOfValues) iterator.next();
						row.removeColumn();
					}
					view.remove(model.getCandidates().get(model.getCandidates().size() - 1));
					model.getCandidates().remove(model.getCandidates().size() - 1);
					model.setHorizontalOffset(Math.min(model.getHorizontalOffset(),
							(model.getAmountCandidates() - 1) * model.getElementWidth() * 2));
				}
			} else if (e.getSource() == view.addVoter) {
				model.setAmountVoters(model.getAmountVoters() + 1);
				model.getRows().add(new RowOfValues(model, container, model.getAmountCandidates(),
						model.getElementWidth(), model.getElementHeight(), model.getWidthMultiplier()));
				for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
					RowOfValues row = (RowOfValues) iterator.next();
					row.repaint();
				}
				model.getVoters().add(new JTextField("V" + model.getAmountVoters()));

			} else if (e.getSource() == view.removeVoter) {
				if (model.getAmountVoters() > 1) {
					model.setAmountVoters(model.getAmountVoters() - 1);
					view.remove(model.getRows().get(model.getRows().size() - 1));
					model.getRows().remove(model.getRows().size() - 1);
					view.remove(model.getVoters().get(model.getVoters().size() - 1));
					model.getVoters().remove(model.getVoters().size() - 1);
					model.setVerticalOffset(Math.min(model.getVerticalOffset(),
							(model.getAmountVoters() - 1) * model.getElementHeight() * 2));
				}
			}
			view.update();
		}
	}

	public synchronized void updateRows() {
		if (react) {
			for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
				RowOfValues row = (RowOfValues) iterator.next();
				row.update();
			}
		}
	}

	/**
	 * Displays the view.
	 */
	public void start() {
		java.awt.EventQueue.invokeLater(this);
	}

	//TODO
	/**
	 * resets all fields and returns the view back to its original state
	 */
	public void reset() {
		// TODO Auto-generated method stub
		
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

	public void startStop() {
		if(running) {
			//TODO stop the running
			running = false;
			react = true;
		} else {
			react = false;
			
			int[][] valuesToPass = new int[model.getAmountVoters()][model.getAmountCandidates()];
			
			//lies die Daten in ein 2d array
			for (int i = 0; i < model.getAmountVoters(); i++) {
				for (int j = 0; j < model.getAmountCandidates(); j ++) {					
					valuesToPass[i][j] = model.getRows().get(i).getValues().get(j);
				}
			}
			running = true;
		}
	}
}
