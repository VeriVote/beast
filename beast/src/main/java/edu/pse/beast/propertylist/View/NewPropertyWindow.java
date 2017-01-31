package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.Model.PLModelInterface;

public class NewPropertyWindow extends JFrame {
	
	PLModelInterface model;
	PLControllerInterface controller;

	private JPanel panel1;
	private JPanel panel2;
	private JButton addNewProperty;
	private JList standardDescriptions;
	private JButton addDescription;
	
	public NewPropertyWindow(PLControllerInterface controller, PLModelInterface model) {
		this.model = model;
		this.controller = controller;
		init();
		this.setVisible(false);
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		setBounds(600, 100, 500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel();
		getContentPane().add(panel1, BorderLayout.NORTH);
		
		addNewProperty = new JButton();
		addNewProperty.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addNewProperty.setText("Neu");
		addNewProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.addNewProperty();
			}
		});
		
		panel1.add(addNewProperty);
		
		panel2 = new JPanel();
		getContentPane().add(panel2, BorderLayout.CENTER);
		
		standardDescriptions = new JList();
		addDescription = new JButton();
		addDescription.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addDescription.setText("Standard waehlen");
		addDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.addNewProperty();
				
				// TODO 
			}
		});
		
		
		panel2.add(standardDescriptions);
		panel2.add(addDescription);
	}
	
	protected void toggleVisibility() {
		this.setVisible(!this.isVisible());
	}

}
