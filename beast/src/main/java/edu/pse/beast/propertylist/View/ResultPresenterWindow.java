package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionType;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

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
        srl = provider.getOtherStringRes();
        this.setVisible(false);
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        //this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //this.setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        this.setResizable(true);
        setBounds(0, 0, 400, 500);
        Dimension iconSize = new Dimension(120, 40);

        showResult = new JButton();
        showResult.setPreferredSize(iconSize);
        showResult.setIcon(eyeIcon);
        showResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        getContentPane().add(showResult, BorderLayout.PAGE_START);

        result = new JTextPane();
        result.setEditable(false);
        result.setText(srl.getStringFromID("noResultYet"));
        getContentPane().add(result, BorderLayout.CENTER);

        JScrollPane jsp = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jsp);

        export = new JButton();
        export.setPreferredSize(iconSize);
        export.setText(srl.getStringFromID("export"));
        export.addActionListener(new ActionListener() {
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
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        getContentPane().add(export, BorderLayout.PAGE_END);

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                setVisible(false);
            }
        });
        pack();
    }

    // private methods
    private Long[] getVotePoints(Long[] votes, FailureExample ex) {
        Long[] result = new Long[ex.getNumOfCandidates()];
        Arrays.fill(result, 0l);

        for (int i = 0; i < ex.getNumOfVoters(); i++) {
            int vote = votes[i].intValue();
            // assumes that vote value is not 0
            if (vote < 0) {
                result[vote - 1]++;
            }
            else{
                result[vote]++;
            }
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
                        // assumes that chosenCandidate is not 0. if chosenCandidate is 0, the election is rigged
                        int chosenCandidate = (int) (long) vote[j];
                        if (chosenCandidate != 0) {
                            result[chosenCandidate - 1] += candidates - j;
                        }
                        //result[candidate] += candidates - j;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private void erasePane() {
        result.setText("");
    }

    private void packFrame() {
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, showResult.getBackground()));
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

    public void presentFailureExample(FailureExample ex) {
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

                List<Long> voteList = ex.getVotes().get(i).getList();
                // first votelist is shown without difference to earlier votings
                if (i == 0) {
                    for (Long vote : voteList) {
                        appendPane(vote + ", ");
                    }
                } else {
                    // further lists are shown, if they did something different
                    List<Long> precedingList = ex.getVotes().get(i - 1).getList();
                    for (int j = 0; j < voteList.size(); j++) {
                        Long preceding = precedingList.get(j);
                        Long vote = voteList.get(j);
                        if (preceding.equals(vote)) {
                            appendPane(vote.toString() + ", ");
                        } else {
                            appendPaneColored(vote.toString(), Color.RED);
                            appendPane(", ");
                        }
                    }
                }
                eraseLastCharacters(2);
            } else {
                List<ArrayList<Long>> precedingList;
                List<ArrayList<Long>> voteList = ex.getVoteList().get(i).getList();

                if (i == 0) {
                    precedingList = voteList;
                } else {
                    precedingList = ex.getVoteList().get(i - 1).getList();
                }

                for (int j = 0; j < voteList.size(); j++) {
                    ArrayList<Long> preceding = precedingList.get(j);
                    ArrayList<Long> vote = voteList.get(j);
                    if (preceding.equals(vote)) {
                        appendPane(vote.toString() + ", ");
                    } else {
                        appendPaneColored(vote.toString(), Color.RED);
                        appendPane(", ");
                    }
                }

            }
            appendLine("");

            // The elected part of the document
            appendPane(srl.getStringFromID("elected") + ": ");
            if (ex.isOneSeatOnly()) {
                Long preceding;
                Long elected = ex.getElect().get(i).getValue();

                // only show differences to preceding election when it is not
                // the first election
                if (i == 0) {
                    preceding = elected;
                } else {
                    preceding = ex.getElect().get(i - 1).getValue();
                }

                if (preceding == elected) {
                    appendPane(elected.toString() + ", ");
                } else {
                    appendPaneColored(elected.toString(), Color.RED);
                    appendPane(", ");
                }
                eraseLastCharacters(2);
            } else {
                Long[] preceding;
                Long[] elected = ex.getSeats().get(i).getArray();

                if (i == 0) {
                    preceding = elected;
                } else {
                    preceding = ex.getSeats().get(i - 1).getArray();
                }

                if (preceding.equals(elected)) {
                    appendPane(Arrays.toString(elected));
                } else {
                    appendPaneColored(Arrays.toString(elected), Color.RED);
                }
            }

            // The votes points part of the document
            appendLine("\n");
            appendLine(srl.getStringFromID("electionpoints"));
            Long[] result;
            if (ex.isChooseOneCandidate()) {
                result = getVotePoints(ex.getVotes().get(i).getArray(), ex);
            } else {
                result = getVotePoints(ex.getVoteList().get(i).getArray(), ex.getElectionType(), ex);
            }
            for (int j = 0; j < result.length; j++) {
                appendLine(srl.getStringFromID("Candidate") + " " + (j + 1) + ": " + (int) (long) result[j]);
            }
            appendLine("\n");
        }
        packFrame();
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

}
