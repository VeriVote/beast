package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.datatypes.FailureExample;

public class ResultPresenterWindow extends JFrame {
	
	private JButton hide;
	private JButton export;
	
	private JTextPane result;
	
	private FailureExample example;
	
	public ResultPresenterWindow() {
		this.setVisible(false);
		init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		setBounds(0, 0, 300, 300);
		
		Dimension iconSize = new Dimension(80,40);
		
		hide = new JButton();
		hide.setPreferredSize(iconSize);
		hide.setIcon(new ImageIcon(getClass().getResource("/images/other/eye.png")));
		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		getContentPane().add(hide, BorderLayout.PAGE_START);
		
		result = new JTextPane();
		result.setEditable(false);
		result.setText("Not a result yet.");
		getContentPane().add(result, BorderLayout.CENTER);
		
		
		export = new JButton();
		export.setPreferredSize(iconSize);
		export.setText("Export");
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		getContentPane().add(export, BorderLayout.PAGE_END);
		
		
	}
	
	private void appendPane(String text) {
		StyledDocument doc = result.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), text, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setStyledDocument(doc);
	}

	public FailureExample getExample() {
		return example;
	}

	public void setExample(FailureExample example) {
		this.example = example;
	}
	
	public void present(String message) {
		
	}
	
	public void presentFailure(List<String> error) {
		result.setText("");
		for (String line : error) appendPane(line + "\n");
	}

	public void presentFailureExample(FailureExample example) {
		
	}
	
	
	
}
