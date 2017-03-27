package edu.pse.beast.propertylist.View;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionType;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.SuperFolderFinder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class ResultPresenterWindow extends JFrame {

    private JButton showResult;
    private JButton export;
    private JTextPane result;
    StringResourceLoader srl;
    private FailureExample example;

    private final String pathToEye = "/core/images/other/eye.png";
    private final ImageIcon eyeIcon = new ImageIcon(SuperFolderFinder.getSuperFolder() + pathToEye);

    /**
     *
     */
    public ResultPresenterWindow() {
        this(new StringLoaderInterface("de"));
    }

    /**
     * @param sli
     */
    public ResultPresenterWindow(StringLoaderInterface sli) {
        PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
        this.srl = provider.getOtherStringRes();
        this.setVisible(false);
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());

        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        this.setResizable(true);
        setBounds(0, 0, 400, 500);
        Dimension iconSize = new Dimension(120, 40);

        setShowResult(new JButton());
        getShowResult().setPreferredSize(iconSize);
        getShowResult().setIcon(eyeIcon);
        getShowResult().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        getContentPane().add(getShowResult(), BorderLayout.PAGE_START);

        result = new JTextPane();
        result.setEditable(false);
        result.setText(srl.getStringFromID("noResultYet"));
        getContentPane().add(result, BorderLayout.CENTER);

        JScrollPane jsp = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jsp);

        setExport(new JButton());
        getExport().setPreferredSize(iconSize);
        getExport().setText(srl.getStringFromID("export"));
        getExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
                fc.setFileFilter(filter);
                fc.showSaveDialog(getParent());
                if (fc.getSelectedFile() == null) {
                    return;
                }
                try {
                    File file = fc.getSelectedFile();
                    String filename = file.toString();
                    if (!filename.endsWith(".txt")) {
                        filename += ".txt";
                    }
                    FileWriter fw = new FileWriter(filename);
                    fw.write(result.getText());
                    fw.flush();
                    fw.close();
                } catch (IOException ioe) {
                    ErrorLogger.log(ioe.getMessage());
                }
            }
        });
        getContentPane().add(getExport(), BorderLayout.PAGE_END);

        pack();
    }

    // private methods
    private Long[] getVotePoints(Long[] votes, FailureExample ex) {
        Long[] result = new Long[ex.getNumOfCandidates()];
        Arrays.fill(result, 0l);

        for (int i = 0; i < ex.getNumOfVoters(); i++) {
            int vote = votes[i].intValue();
            result[vote]++;
            
        }
        return result;
    }

    private Long[] getVotePoints(Long[][] votes, ElectionType type, FailureExample ex) {
        int candidates = ex.getNumOfCandidates();
        Long[] result = new Long[candidates];
        Arrays.fill(result, 0l);

        for (int i = 0; i < ex.getNumOfVoters(); i++) {
            Long[] vote = votes[i];
            switch (type) {
                case PREFERENCE:
                    for (int j = 0; j < candidates; j++) {
                    	int chosenCandidate = (int) (long) vote[j];
                    	result[chosenCandidate] += candidates - j;
                    }
                    break;
                case WEIGHTEDAPPROVAL:
                    for (int j = 0; j < candidates; j++) {
                        result[j] += vote[j];
                    }
                    break;
                case APPROVAL:
                    for (int j = 0; j < candidates; j++) {
                        if (vote[j] == 1l) {
                            result[j]++;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    private void appendPane(String text) {
        appendPaneStyled(text, null);
    }

    private void appendPaneColored(String text, Color color) {
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, color);
        StyleConstants.setBold(attr, true);
        appendPaneStyled(text, attr);
    }

    private void appendPaneStyled(String text, AttributeSet attr) {
        StyledDocument doc = result.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, attr);
        } catch (BadLocationException e) {
            ErrorLogger.log(e.getMessage());
        }
        result.setStyledDocument(doc);
    }

    private void appendLine(String text) {
        appendPane(text + "\n");
    }

    private void eraseLastCharacters(int amount) {
        StyledDocument doc = result.getStyledDocument();
        try {
            doc.remove(doc.getLength() - amount, amount);
        } catch (BadLocationException e) {
        	ErrorLogger.log(e.getMessage());
        }
    }

    private void erasePane() {
        result.setText("");
    }

    private void packFrame() {
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, getShowResult().getBackground()));
        pack();
    }

    public void makeInvisible() {
        this.setVisible(false);
    }

    public void presentFailure(List<String> error) {
        if (error == null) {
            return;
        }
        erasePane();
        appendLine(srl.getStringFromID("failureMessage"));
        appendLine("");
        for (String line : error) {
            appendLine(line);
        }
        packFrame();
    }

    public void presentFailureExample(FailureExample ex) { // writes the failure example to the styled document
        if (ex == null) {
            return;
        }
        erasePane();
        appendLine(srl.getStringFromID("failureExampleMessage"));
        appendLine(srl.getStringFromID("electionType") + ": " + srl.getStringFromID(ex.getTypeString()) + "\n");
        appendLine("");

        for (int i = 0; i < ex.getNumOfElections(); i++) {
            appendLine(srl.getStringFromID("election") + " " + (i + 1));

            // The votes part of the document
            appendPane(srl.getStringFromID("votes") + ": ");
            if (ex.isChooseOneCandidate()) {
            	writeVotesForOneCandidate(ex, i);
            } else {
            	writeVotesForMultipleCandidates(ex, i);
            }
            appendLine("");

            
            // The elected part of the document
            appendPane(srl.getStringFromID("elected") + ": ");
            if (ex.getElectionType().getResultTypeSeats()) {
                writeElectedMultipleCandidates(ex, i);
            } else {
                writeElectedOneCandidate(ex, i);
            }

            // The vote points part of the document
            appendLine("\n");
            appendLine(srl.getStringFromID("electionpoints"));
            Long[] result;
            if (ex.isChooseOneCandidate()) {
                result = getVotePoints(ex.getVotes().get(i).getArray(), ex);
            } else {
                result = getVotePoints(ex.getVoteList().get(i).getArray(), ex.getElectionType(), ex);
            }
            for (int j = 0; j < result.length; j++) {
                appendLine(srl.getStringFromID("Candidate") + " " + ex.getSymbolicCandidateForIndex(j) + ": " + (int) (long) result[j]);
            }
            appendLine("\n");
        }
        packFrame();
    }

    private void writeElectedMultipleCandidates(FailureExample ex, int i) {
    	Long[] preceding;
        Long[] elected = ex.getSeats().get(i).getArray();

        preceding = i == 0 ? elected : ex.getSeats().get(i - 1).getArray();
        
        for (int j = 0; j < elected.length; j++) {
        	Color color = preceding[j].equals(elected[j]) ? Color.BLACK : Color.RED;
        	
        	if (elected[j] >= ex.getNumOfCandidates()) { // no candidate wins
        		appendPaneColored(srl.getStringFromID("draw"), color);
        	}
        	else {
        		appendPaneColored("" + elected[j], color);
        	}
        	appendPane(", ");
        }
        eraseLastCharacters(2);
		
	}

	private void writeElectedOneCandidate(FailureExample ex, int i) {
    	Long preceding;
        Long elected = ex.getElect().get(i).getValue();

        // only show differences to preceding election when it is not the first election
        preceding = i == 0 ? elected : ex.getElect().get(i - 1).getValue();
        
        Color color = preceding == elected ? Color.BLACK : Color.RED;

        if (elected >= ex.getNumOfCandidates()) { // no candidate wins
        	appendPaneColored(srl.getStringFromID("draw") + ", ", color);
        }
        else {
        	appendPaneColored(ex.getSymbolicCandidateForIndex(elected) + ", ", color);
        }
        
        eraseLastCharacters(2);
	}

	private void writeVotesForMultipleCandidates(FailureExample ex, int i) {
    	Long[][] precedingList;
        Long[][] voteList = ex.getVoteList().get(i).getArray();

        precedingList = i == 0 ? voteList : ex.getVoteList().get(i - 1).getArray();

        for (int j = 0; j < voteList.length; j++) {

            if (Arrays.equals(precedingList[j], voteList[j])) {
                appendPane(Arrays.toString(voteList[j]) + ", ");
            } else {
                appendPaneColored(Arrays.toString(voteList[j]), Color.RED);
                appendPane(", ");
            }
            
        }
        eraseLastCharacters(2);
		
	}

	private void writeVotesForOneCandidate(FailureExample ex, int i) {
    	Long[] precedingList;
        Long[] voteList = ex.getVotes().get(i).getArray();
        
        precedingList = i == 0 ? voteList : ex.getVotes().get(i - 1).getArray();
        
        for (int j = 0; j < voteList.length; j++) {
        	Color color = precedingList[j].equals(voteList[j]) ? Color.BLACK : Color.RED;
        	appendPaneColored(ex.getSymbolicCandidateForIndex(voteList[j]), color);
        	appendPane(", ");
        }
        eraseLastCharacters(2);
	}

	public void presentSuccess() {
        erasePane();
        appendPane(srl.getStringFromID("successMessage"));
        packFrame();
    }

    public void presentTimeOut() {
        erasePane();
        appendPane(srl.getStringFromID("timeoutMessage"));
        packFrame();
    }

    public void presentCancel() {
        erasePane();
        appendPane(srl.getStringFromID("cancelMessage"));
        packFrame();
    }

    public void resetResult() {
        erasePane();
        result.setText(srl.getStringFromID("noResultYet"));
        packFrame();
    }

    // getter and setter
    public JButton getShowResult() {
        return showResult;
    }

    public FailureExample getExample() {
        return example;
    }

    public void setExample(FailureExample example) {
        this.example = example;
    }

	private void setShowResult(JButton showResult) {
		this.showResult = showResult;
	}

	private JButton getExport() {
		return export;
	}

	private void setExport(JButton export) {
		this.export = export;
	}

}
