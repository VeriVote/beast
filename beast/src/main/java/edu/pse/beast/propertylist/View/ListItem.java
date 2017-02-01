package edu.pse.beast.propertylist.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyItem;
import edu.pse.beast.propertylist.Model.PLModelInterface;

/**
*
* @author Justin
*/
public class ListItem extends JPanel {
	
	PLModelInterface model;
	PLControllerInterface controller;
	
	private PropertyItem prop;
	
	protected ResultButton showResult = new ResultButton();
	protected JTextField name = new JTextField();
	protected JCheckBox testStatus = new JCheckBox();
	protected JButton changeButton = new JButton();
	protected JButton deleteButton = new JButton();
	
	public ListItem(PLControllerInterface controller, PLModelInterface model) {
		this.model = model;
		this.controller = controller;
		prop = new PropertyItem();
		init();
	}
	
	public ListItem(PLControllerInterface controller, PLModelInterface model, PropertyItem prop) {
		this.model = model;
		this.controller = controller;
		this.prop = prop;
		init();
	}
	
	private void init() {
		Dimension iconSize = new Dimension(40,40);
		showResult.setPreferredSize(iconSize);
		showResult.setIcon(new ImageIcon(getClass().getResource("/images/other/eye.png")));
		this.add(showResult);
		
		name.setText(prop.getDescription().getName());
		name.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { try {
				change(e);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} }
			public void removeUpdate(DocumentEvent e) { try {
				change(e);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} }
			public void insertUpdate(DocumentEvent e) { try {
				change(e);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} }
			private void change(DocumentEvent c) throws BadLocationException { 
				controller.changeName(prop, c.getDocument().getText(0, c.getDocument().getLength())); 
			}
		});
		this.add(name);
		
		
		testStatus.setText("Check");
		testStatus.setSelected(prop.willBeTested());
		testStatus.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					controller.setTestStatus(prop, true);
				}
				else {
					controller.setTestStatus(prop, false);
				}
				
			}
		});
		this.add(testStatus);
		
		
		changeButton.setPreferredSize(iconSize);
		changeButton.setIcon(new ImageIcon(getClass().getResource("/images/other/wrench.png")));
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.editProperty(prop);
			}
		});
		this.add(changeButton);
		
		deleteButton.setPreferredSize(iconSize);
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/images/other/x-mark.png")));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteProperty(prop);
			}
		});
		this.add(deleteButton);
	}
	
	public void presentResultOf() {
		//TODO eigentliches Argument: Result res
	}
	
	public void presentTimeout() {
		//TODO
	}
	
	public void presentSuccess() {
		//TODO
	}
	
	public void presentFailure() {
		//TODO
	}
	
	public void presentFailureExample(FailureExample ex) {
		//TODO
	}

	/* @Override
	public boolean equals (Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		if (this.prop.equals((PropertyItem)o)) return true;
		else return false;
	}*/
}
