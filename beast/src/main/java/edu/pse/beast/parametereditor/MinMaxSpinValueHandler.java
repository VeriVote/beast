package edu.pse.beast.parametereditor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * The MinMaxSpinValueHandler handles all user inputs on the ParameterEditor
 * which set a minimum and a maximum for a value using two JSpinners.
 * 
 * @author Jonas
 */
public class MinMaxSpinValueHandler implements ChangeListener {

	private final JSpinner minSpinner;
	private final JSpinner maxSpinner;
	private Integer minBefore;
	private Integer maxBefore;
	private boolean reacts;
	private boolean hasChanged;

	/**
	 * Constructor
	 * 
	 * @param minSpinner
	 *            JSpinner for the minimum
	 * @param maxSpinner
	 *            JSpinner for the maximum
	 */
	public MinMaxSpinValueHandler(JSpinner minSpinner, JSpinner maxSpinner) {
		this.minSpinner = minSpinner;
		this.maxSpinner = maxSpinner;
		setMinAndMax(1, 1);
		setHasChanged(false);
	}

	/**
	 * Getter for the natural numbers between and including the minimum and the
	 * maximum set by the user.
	 * 
	 * @return ArrayList of Integers representing these values
	 */
	public ArrayList<Integer> getValues() {
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = minBefore; i <= maxBefore; i++) {
			result.add(i);
		}
		return result;
	}

	/**
	 * Setter for the minimum and maximum. Only allows values 1 to 10000 and
	 * ensures that min gets the lower value.
	 * 
	 * @param min
	 *            new minimum
	 * @param max
	 *            new maximum
	 */
	public synchronized void setMinAndMax(Integer min, Integer max) {
		reacts = true;
		if (min <= 10000 && min >= 1 && max <= 10000 && max >= 1) {
			minSpinner.setValue(java.lang.Math.min(min, max));
			maxSpinner.setValue(java.lang.Math.max(min, max));
		} else {
			minSpinner.setValue(minBefore);
			maxSpinner.setValue(maxBefore);
		}
		Integer minAfter = Integer.parseInt(minSpinner.getValue().toString());
		Integer maxAfter = Integer.parseInt(maxSpinner.getValue().toString());
		if (minBefore == null || !(minBefore.equals(minAfter) && maxBefore.equals(maxAfter))) {
			minBefore = minAfter;
			maxBefore = maxAfter;
			setHasChanged(true);
		}
		reacts = false;
	}

	@Override
	public synchronized void stateChanged(ChangeEvent e) {
		String minString = (minSpinner.getValue().toString());
		String maxString = (maxSpinner.getValue().toString());
		if ((minString + maxString).chars().allMatch(Character::isDigit) && reacts) {
			Integer min = Integer.parseInt(minString);
			Integer max = Integer.parseInt(maxString);
			if (min <= 10000 && min >= 1 && max <= 10000 && max >= 1) {
				if (e.getSource().equals(minSpinner)) {
					if (min > maxBefore) {
						max = min;
					}
				} else if (e.getSource().equals(maxSpinner)) {
					if (max < minBefore) {
						min = max;
					}
				}
				minSpinner.setValue(min);
				maxSpinner.setValue(max);
				setHasChanged(true);
			} else {
				minSpinner.setValue(minBefore);
				maxSpinner.setValue(maxBefore);
			}
		} else {
			minSpinner.setValue(minBefore);
			maxSpinner.setValue(maxBefore);
		}
		minBefore = Integer.parseInt(minSpinner.getValue().toString());
		maxBefore = Integer.parseInt(maxSpinner.getValue().toString());
	}

	/**
	 * Toggles whether the chosen minimum and maximum react to user input (to
	 * not interrupt checks)
	 * 
	 * @param reacts
	 *            whether they react
	 */
	protected synchronized void setReacts(boolean reacts) {
		this.reacts = reacts;
	}

	/**
	 * Returns whether the values of the JSpinners were changed since last time
	 * saving.
	 * 
	 * @return hasChanged
	 */
	protected synchronized boolean hasChanged() {
		return hasChanged;
	}

	/**
	 * Sets whether the values of the JSpinners were changed since last time
	 * saving.
	 * 
	 * @param hasChanged
	 *            whether they changed
	 */
	protected synchronized void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
}
