package edu.pse.beast.electionSimulator.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.swing.JTextField;

import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;

public class ElectionSimulationModel extends Observable implements NameInterface {

	private String name;
	
	private List<RowOfValues> rows = new ArrayList<RowOfValues>();

	private ArrayList<JTextField> candidates = new ArrayList<JTextField>();

	private ArrayList<JTextField> voters = new ArrayList<JTextField>();
	
	private ElectionTypeContainer container;	

	private int amountCandidates = 1;
	private int amountVoters = 1;

	private int elementHeight = 30;
	private int elementWidth = 50;

	private final int scrollBarWidth = 20;

	private final int borderMarginSmall = Math.max(elementHeight, elementWidth);

	private final int widthMultiplier = Math.max(elementHeight, elementWidth) * 2;

	private final int heightMultiplier = elementHeight * 2;

	private int horizontalOffset = 0;

	private int verticalOffset = 0;
	
	private int fieldsPerWidth = 0;
	
	private int fieldsPerHeight = 0;

	private final int startHeight = 500;
	private final int startWidth = 700;
	
	private int height = startHeight;
	
	private int width = startWidth;
	
	public ElectionSimulationModel(ElectionTypeContainer container) {
		this.container = container;
		init();
	}

	/**
	 * Initializes the model.
	 */
	private void init() {
		// add initial row
		rows.add(new RowOfValues(this, container, this.getAmountCandidates(), this.getElementWidth(), this.getElementHeight(), this.getWidthMultiplier()));
		
		fieldsPerWidth = (width - 2 * (borderMarginSmall)) / (2 *  this.getWidthMultiplier());
		
		fieldsPerHeight = (height - 2 * (borderMarginSmall * 2)) / (2 * heightMultiplier);

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
		for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			RowOfValues currentRow = iterator.next();
			currentRow.setContainer(container);
		}
	}
	public ArrayList<JTextField> getCandidates() {
		return candidates;
	}
	
	public void setCandidates(ArrayList<JTextField> candidates) {
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
	
	public void setAmountCandidates(int amountCandidates) {
		this.amountCandidates = amountCandidates;
	}
	
	public int getAmountVoters() {
		return amountVoters;
	}
	
	public void setAmountVoters(int amountVoters) {
		this.amountVoters = amountVoters;
	}
	
	public int getElementHeight() {
		return elementHeight;
	}
	
	public void setElementHeight(int elementHeight) {
		this.elementHeight = elementHeight;
	}
	
	public int getElementWidth() {
		return elementWidth;
	}
	
	public void setElementWidth(int elementWidth) {
		this.elementWidth = elementWidth;
	}
	
	public int getHorizontalOffset() {
		return horizontalOffset;
	}
	
	public void setHorizontalOffset(int horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}
	
	public int getVerticalOffset() {
		return verticalOffset;
	}
	
	public void setVerticalOffset(int verticalOffset) {
		this.verticalOffset = verticalOffset;
	}
	
	public int getFieldsPerWidth() {
		return fieldsPerWidth;
	}
	
	public void setFieldsPerWidth(int fieldsPerWidth) {
		this.fieldsPerWidth = fieldsPerWidth;
	}
	
	public int getFieldsPerHeight() {
		return fieldsPerHeight;
	}
	
	public void setFieldsPerHeight(int fieldsPerHeight) {
		this.fieldsPerHeight = fieldsPerHeight;
	}
	
	public List<RowOfValues> getRows() {
		return rows;
	}
	
	public ArrayList<JTextField> getVoters() {
		return voters;
	}
	
	public int getScrollBarWidth() {
		return scrollBarWidth;
	}
	
	public int getBorderMarginSmall() {
		return borderMarginSmall;
	}
	
	public int getWidthMultiplier() {
		return widthMultiplier;
	}
	
	public int getHeightMultiplier() {
		return heightMultiplier;
	}
	
	public int getStartHeight() {
		return startHeight;
	}
	
	public int getStartWidth() {
		return startWidth;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getButtonWidth() {
		return borderMarginSmall;
	}

	public int getButtonHeight() {
		return borderMarginSmall;
	}
}
