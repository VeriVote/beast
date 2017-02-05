package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

public class ResultPresenterWindow extends JFrame {
	
	private JButton showResult;
	private JButton export;
	
	private JTextPane result;
	StringResourceLoader srl;
	
	private FailureExample example;
	
	public ResultPresenterWindow() {
		this(new StringLoaderInterface("de"));
	}
	public ResultPresenterWindow(StringLoaderInterface sli) {
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		srl = provider.getOtherStringRes();
		this.setUndecorated(true);
		this.setVisible(false);
		init();
	}
	
	public JButton getShowResult() { return showResult; }
	public FailureExample getExample() { return example; }
	public void setExample(FailureExample example) { this.example = example; }
	
	private void init() {
		this.setLayout(new BorderLayout());
		
		setBounds(0, 0, 400, 400);
		
		Dimension iconSize = new Dimension(80,40);
		
		showResult = new JButton();
		showResult.setPreferredSize(iconSize);
		showResult.setIcon(new ImageIcon(getClass().getResource("/images/other/eye.png")));
		showResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		getContentPane().add(showResult, BorderLayout.PAGE_START);
		
		result = new JTextPane();
		result.setEditable(false);
		//result.setText("Not a result yet.");
		result.setText(srl.getStringFromID("noResultYet"));
		getContentPane().add(result, BorderLayout.CENTER);
		
		JScrollPane jsp = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		
		export = new JButton();
		export.setPreferredSize(iconSize);
		//export.setText("Export");
		export.setText(srl.getStringFromID("export"));
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
		try { doc.insertString(doc.getLength(), text, null); } 
		catch (BadLocationException e) { e.printStackTrace(); }
		result.setStyledDocument(doc);
	}
	
	private void appendLine(String text) {
		appendPane(text + "\n");
	}

	private void erasePane() {
		result.setText("");
	}

	
	/*public void present(String message) {
		// what does this do
	}*/
	
	public void presentFailure(List<String> error) {
		erasePane();
		appendLine(srl.getStringFromID("failureMessage"));
		appendLine("");
		for (String line : error) appendLine(line);
	}

	public void presentFailureExample(FailureExample ex) {
		erasePane();
		appendLine(srl.getStringFromID("failureExampleMessage"));
		appendLine(srl.getStringFromID("electionType") + ": "+ ex.getTypeString() + "\n");
		for (int i = 0; i < ex.getNumOfElections(); i++) {
			appendLine(srl.getStringFromID("election") + " " + i);
			appendPane(srl.getStringFromID("votes") + ": ");
			if (ex.isChooseOneCandidate()) appendPane(Arrays.toString(ex.getVotes().get(i).getArray()));
			else {
				Long[][] arr = ex.getVoteList().get(i).getArray();
				for (int j = 0; j < arr.length; j++) {
					appendPane(Arrays.toString(arr[j]));
					if (j < arr.length - 1) appendPane(", ");
				}
			}
			appendLine("");
			
			appendPane(srl.getStringFromID("elected") + ": ");
			if (ex.isOneSeatOnly()) appendPane(Long.toString(ex.getElect().get(i).getValue()));
			else appendPane(Arrays.toString(ex.getSeats().get(i).getArray()));
			appendLine("\n");
		}
	}
	
	public void presentSuccess() {
		erasePane();
		//appendPane("The election fulfills this property.");
		appendPane(srl.getStringFromID("successMessage"));
	}
	public void presentTimeOut() {
		erasePane();
		//appendPane("The analysis was timed out.");
		appendPane(srl.getStringFromID("timeoutMessage"));
	}
	
	
}
