package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class NewPropertyWindow extends JFrame {

	private JPanel panel1;
	private JPanel panel2;
	private JButton addNewDescription;
	private JList standardDescriptions;
	private JButton addStandardDescription;
	
	public NewPropertyWindow() {
		init();
		this.setVisible(true);
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel();
		getContentPane().add(panel1, BorderLayout.NORTH);
		
		addNewDescription = new JButton();
		addNewDescription.setText("Neu");
		
		panel1.add(addNewDescription);
		//this.dispose();
		
		
		panel2 = new JPanel();
		getContentPane().add(panel2, BorderLayout.CENTER);
		
		standardDescriptions = new JList();
		addStandardDescription = new JButton();
		addStandardDescription.setText("Standard waehlen");
		
		panel2.add(standardDescriptions);
		panel2.add(addStandardDescription);
	}
}
