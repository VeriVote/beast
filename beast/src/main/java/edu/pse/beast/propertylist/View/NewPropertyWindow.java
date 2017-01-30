package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
		this.setVisible(false);
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		setBounds(600, 100, 500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel();
		getContentPane().add(panel1, BorderLayout.NORTH);
		
		addNewDescription = new JButton();
		addNewDescription.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addNewDescription.setText("Neu");
		addNewDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		panel1.add(addNewDescription);
		
		panel2 = new JPanel();
		getContentPane().add(panel2, BorderLayout.CENTER);
		
		standardDescriptions = new JList();
		addStandardDescription = new JButton();
		addStandardDescription.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addStandardDescription.setText("Standard waehlen");
		
		
		panel2.add(standardDescriptions);
		panel2.add(addStandardDescription);
	}
	
	protected void toggleVisibility() {
		this.setVisible(!this.isVisible());
	}
}
