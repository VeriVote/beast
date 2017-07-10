package edu.pse.beast.electionSimulator;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.SuperFolderFinder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class ElectionInputWindow extends JFrame implements ActionListener, ComponentListener, AdjustmentListener {

	private int amountCandidates = 1;
	private int amountVoters = 1;

	private int elementHeight = 30;
	private int elementWidth = 50;

	private int scrollBarWidth = 20;

	private int borderMarginSmall = Math.max(elementHeight, elementWidth);

	private int widthMultiplier = Math.max(elementHeight, elementWidth) * 2;

	private int heightMultiplier = elementHeight * 2;

	private int horizontalOffset = 0;

	private int verticalOffset = 0;
	
	private int fieldsPerWidth = 0;
	
	private int fieldsPerHeight = 0;

	private JButton addCandidate;
	private JButton addVoter;

	private JButton removeCandidate;
	private JButton removeVoter;

	private JScrollBar horizontalScroll;
	private JScrollBar verticalScroll;

	StringResourceLoader srl;

	private int startHeight = 500;
	private int startWidth = 700;

	private ArrayList<RowOfValues> rows = new ArrayList<RowOfValues>();

	private ArrayList<JTextField> candidates = new ArrayList<JTextField>();

	private ArrayList<JTextField> voters = new ArrayList<JTextField>();

	private ElectionTypeContainer container;

	/**
	 *
	 */
	public ElectionInputWindow(ElectionTypeContainer container) {
		this(new StringLoaderInterface("de"), container);
	}

	/**
	 * @param sli
	 */
	public ElectionInputWindow(StringLoaderInterface sli, ElectionTypeContainer container) {
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		this.srl = provider.getOtherStringRes();
		this.container = container;
		this.setVisible(false);
		init();
	}

	private void init() {

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		this.getContentPane().addComponentListener(this);

		// add initial row
		rows.add(new RowOfValues(this, container, amountCandidates, elementWidth, elementHeight, widthMultiplier));

		this.setLayout(null);
		this.setBounds(0, 0, startWidth, startHeight);

		fieldsPerWidth = (this.getWidth() - 2 * (borderMarginSmall)) / (2 * widthMultiplier);
		
		fieldsPerHeight = (this.getHeight() - 2 * (borderMarginSmall * 2)) / (2 * heightMultiplier);
		
		addCandidate = new JButton("+C");

		addVoter = new JButton("+V");

		removeCandidate = new JButton("-C");

		removeVoter = new JButton("-V");

		horizontalScroll = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, amountCandidates);

		verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, amountVoters);

		candidates.add(new JTextField("C1"));
		voters.add(new JTextField("V1"));

		this.setResizable(true);

		setVisible(true);

		update();

		addCandidate.addActionListener(this);
		removeCandidate.addActionListener(this);

		addVoter.addActionListener(this);
		removeVoter.addActionListener(this);

		horizontalScroll.addAdjustmentListener(this);

		verticalScroll.addAdjustmentListener(this);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		updateRows();

	}

	private void update() {
	    
		for (int i = 0; i < amountVoters; i++) {
			this.remove(rows.get(i));
			this.remove(horizontalScroll);
			this.remove(verticalScroll);
		}
		
		for (Iterator<JTextField> iterator = candidates.iterator(); iterator.hasNext();) {
			JTextField cField = (JTextField) iterator.next();
			this.remove(cField);
		}
		
		for (Iterator<JTextField> iterator = voters.iterator(); iterator.hasNext();) {
			JTextField vField = (JTextField) iterator.next();
			this.remove(vField);
		}
		
		if (amountCandidates > fieldsPerWidth) {
			horizontalScroll.setBounds((int) (borderMarginSmall + (elementWidth * 3 - scrollBarWidth / 2)),
					borderMarginSmall * 2,
					Math.min(amountCandidates * widthMultiplier , (this.getWidth() - 2 * (borderMarginSmall + (elementWidth * 3 - scrollBarWidth / 2)))),
					scrollBarWidth);
			horizontalScroll.setValues(horizontalOffset, 1, 0, (amountCandidates - 1) * elementWidth * 2);
			this.add(horizontalScroll);
		} else {
		    horizontalOffset = 0;
		}

		if (amountVoters > fieldsPerHeight) {
			verticalScroll.setBounds((int) (borderMarginSmall + (elementWidth * 2 - scrollBarWidth / 2)),
					borderMarginSmall * 2 + elementHeight,
					scrollBarWidth,
					Math.min(amountVoters * heightMultiplier, (this.getHeight() - 2 * (borderMarginSmall * 2 + elementHeight))));
			verticalScroll.setValues(verticalOffset, 1, 0, (amountVoters -1) * elementHeight * 2);
			this.add(verticalScroll);
		} else {
		    verticalOffset = 0;
		}

		addCandidate.setBounds(((amountCandidates + 2) * widthMultiplier - horizontalOffset), borderMarginSmall, elementWidth,
				elementHeight);
		this.add(addCandidate);

		addVoter.setBounds(borderMarginSmall,
				(int) ((amountVoters + 1) * heightMultiplier + borderMarginSmall + elementHeight) - verticalOffset, elementWidth,
				elementHeight);
		this.add(addVoter);

		removeCandidate.setBounds(borderMarginSmall + elementWidth, borderMarginSmall, elementWidth, elementHeight);
		this.add(removeCandidate);

		removeVoter.setBounds(borderMarginSmall, borderMarginSmall + elementHeight, elementWidth, elementHeight);
		this.add(removeVoter);

		for (int i = 0; i < amountVoters; i++) {
			rows.get(i).setBounds((int) (borderMarginSmall + elementWidth * 3),
					borderMarginSmall + ((i + 1) * heightMultiplier + elementHeight - verticalOffset),
					widthMultiplier * amountCandidates, elementHeight);
			if(rows.get(i).getY() >= borderMarginSmall + heightMultiplier + elementHeight) {
				rows.get(i).setOffset(horizontalOffset);
				this.add(rows.get(i));				
			}

		}

		for (int i = 0; i < voters.size(); i++) {
			voters.get(i).setBounds(borderMarginSmall,
					(int) ((i + 1) * heightMultiplier + borderMarginSmall + elementHeight - verticalOffset), elementWidth,
					elementHeight);
			if(voters.get(i).getY() >= (heightMultiplier + borderMarginSmall + elementHeight)) {
				this.add(voters.get(i));				
			}
		}

		for (int i = 0; i < candidates.size(); i++) {
			candidates.get(i).setBounds(((i + 2) * widthMultiplier) - horizontalOffset, borderMarginSmall, elementWidth, elementHeight);
			if(candidates.get(i).getX() >= (2 * widthMultiplier)) {
				this.add(candidates.get(i));				
			}
		}

		this.repaint();
	}

	public void componentResized(ComponentEvent e) {
	    fieldsPerWidth = (int) ((this.getWidth() - 1.5 * (borderMarginSmall)) / (1.5 * widthMultiplier));
	    fieldsPerHeight = (int) ((this.getHeight() - 1.5 * (borderMarginSmall)) / (1.5 * heightMultiplier));
	    
		updateRows();
		update();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addCandidate) {
			amountCandidates++;
			for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
				RowOfValues row = (RowOfValues) iterator.next();
				row.addColumn();
				row.repaint();
			}
			candidates.add(new JTextField("C" + amountCandidates));
		} else if (e.getSource() == removeCandidate) {
			if (amountCandidates > 1) {
				amountCandidates--;
				for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
					RowOfValues row = (RowOfValues) iterator.next();
					row.removeColumn();
				}
				this.remove(candidates.get(candidates.size() - 1));
				candidates.remove(candidates.size() - 1);
				horizontalOffset = Math.min(horizontalOffset, (amountCandidates -1) * elementWidth * 2);
			}
		} else if (e.getSource() == addVoter) {
			amountVoters++;
			rows.add(new RowOfValues(this, container, amountCandidates, elementWidth, elementHeight, widthMultiplier));
			for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
				RowOfValues row = (RowOfValues) iterator.next();
				row.repaint();
			}
			voters.add(new JTextField("V" + amountVoters));

		} else if (e.getSource() == removeVoter) {
			if (amountVoters > 1) {
				amountVoters--;
				this.remove(rows.get(rows.size() - 1));
				rows.remove(rows.size() - 1);
				this.remove(voters.get(voters.size() - 1));
				voters.remove(voters.size() - 1);
				verticalOffset = Math.min(verticalOffset, (amountVoters - 1) * elementHeight * 2);
			}
		}
		update();
	}

	public synchronized void updateRows() {
		for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
			RowOfValues row = (RowOfValues) iterator.next();
			row.update();
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource() == horizontalScroll) {
			horizontalOffset = horizontalScroll.getValue();
		}

		if (e.getSource() == verticalScroll) {
			verticalOffset = verticalScroll.getValue();
		}
		update();
	}
}
