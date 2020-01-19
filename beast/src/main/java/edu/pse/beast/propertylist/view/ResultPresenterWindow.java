package edu.pse.beast.propertylist.view; // TODO remove

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyledDocument;
//
//import edu.pse.beast.datatypes.FailureExample;
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//import edu.pse.beast.propertychecker.Result;
//import edu.pse.beast.stringresource.PropertyListStringResProvider;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.ErrorLogger;
//import edu.pse.beast.toolbox.SuperFolderFinder;
//
//public class ResultPresenterWindow extends JFrame {
//    private static final long serialVersionUID = 1L;
//    private static final String PATH_TO_EYE = "/core/images/other/eye.png";
//
//    StringResourceLoader srl;
//
//    private JButton showResult;
//    private JButton export;
//    private JTextPane result;
//    private FailureExample example;
//
//    private final ImageIcon eyeIcon
//          = new ImageIcon(SuperFolderFinder.getSuperFolder() + PATH_TO_EYE);
//
//    /**
//     *
//     */
//    public ResultPresenterWindow() {
//        this(new StringLoaderInterface("en"));
//    }
//
//    /**
//     * @param sli string loader interface
//     */
//    public ResultPresenterWindow(StringLoaderInterface sli) {
//        PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
//        this.srl = provider.getOtherStringRes();
//        this.setVisible(false);
//        init();
//    }
//
//    private void init() {
//        this.setLayout(new BorderLayout());
//
//        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
//        this.setResizable(true);
//        setBounds(0, 0, 400, 500);
//        Dimension iconSize = new Dimension(120, 40);
//
//        setShowResult(new JButton());
//        getShowResult().setPreferredSize(iconSize);
//        getShowResult().setIcon(eyeIcon);
//        getShowResult().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                setVisible(false);
//            }
//        });
//        getContentPane().add(getShowResult(), BorderLayout.PAGE_START);
//
//        result = new JTextPane();
//        result.setEditable(false);
//        result.setText(srl.getStringFromID("noResultYet"));
//        getContentPane().add(result, BorderLayout.CENTER);
//
//        JScrollPane jsp = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        this.add(jsp);
//
//        setExport(new JButton());
//        getExport().setPreferredSize(iconSize);
//        getExport().setText(srl.getStringFromID("export"));
//        getExport().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fc = new JFileChooser();
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
//                fc.setFileFilter(filter);
//                fc.showSaveDialog(getParent());
//                if (fc.getSelectedFile() == null) {
//                    return;
//                }
//                try {
//                    File file = fc.getSelectedFile();
//                    String filename = file.toString();
//                    if (!filename.endsWith(".txt")) {
//                        filename += ".txt";
//                    }
//                    FileWriter fw = new FileWriter(filename);
//                    fw.write(result.getText());
//                    fw.flush();
//                    fw.close();
//                } catch (IOException ioe) {
//                    ErrorLogger.log(ioe.getMessage());
//                }
//            }
//        });
//        getContentPane().add(getExport(), BorderLayout.PAGE_END);
//
//        pack();
//    }
//
//    // private methods
//    private String[] getVotePoints(String[] votes,
//                                   ElectionTypeContainer type,
//                                   FailureExample ex) {
//        int amountCandidates = ex.getNumOfCandidates();
//        int amountVoters = ex.getNumOfVoters();
//        return type.getInputType().getVotePoints(votes, amountCandidates, amountVoters);
//    }
//
//    private String[] getVotePoints(String[][] votes,
//                                   ElectionTypeContainer type,
//                                   FailureExample ex) {
//        int amountCandidates = ex.getNumOfCandidates();
//        int amountVoters = ex.getNumOfVoters();
//        Long[] result = new Long[amountCandidates];
//        Arrays.fill(result, 0l);
//        return type.getInputType().getVotePoints(votes, amountCandidates, amountVoters);
//    }
//
//    private void appendPane(String text) {
//        appendPaneStyled(text, null);
//    }
//
//    private void appendPaneColored(String text, Color color) {
//        SimpleAttributeSet attr = new SimpleAttributeSet();
//        StyleConstants.setForeground(attr, color);
//        StyleConstants.setBold(attr, true);
//        appendPaneStyled(text, attr);
//    }
//
//    private void appendPaneStyled(String text, AttributeSet attr) {
//        StyledDocument doc = result.getStyledDocument();
//        try {
//            doc.insertString(doc.getLength(), text, attr);
//        } catch (BadLocationException e) {
//            ErrorLogger.log(e.getMessage());
//        }
//        result.setStyledDocument(doc);
//    }
//
//    private void appendLine(String text) {
//        appendPane(text + "\n");
//    }
//
//    private void eraseLastCharacters(int amount) {
//        StyledDocument doc = result.getStyledDocument();
//        try {
//            doc.remove(doc.getLength() - amount, amount);
//        } catch (BadLocationException e) {
//            ErrorLogger.log(e.getMessage());
//        }
//    }
//
//    private void erasePane() {
//        result.setText("");
//    }
//
//    private void packFrame() {
//        getRootPane().setBorder(
//                BorderFactory.createMatteBorder(
//                        2, 2, 2, 2, getShowResult().getBackground()
//                )
//        );
//        pack();
//    }
//
//    public void makeInvisible() {
//        this.setVisible(false);
//    }
//
//    public void presentFailure(List<String> error) {
//        if (error == null) {
//            return;
//        }
//        erasePane();
//        appendLine(srl.getStringFromID("failureMessage"));
//        appendLine("");
//        for (String line : error) {
//            appendLine(line);
//        }
//        setSize(600, 600);
//        // packFrame();
//    }
//
//    public void presentFailureExample(Result parentResult) {
//        // writes the failure example to the styled document
//        erasePane();
//        if (!parentResult.hasFinalMargin()) { //
//            FailureExample ex = parentResult.getFailureExample();
//            if (ex == null) {
//                return;
//            }
//            appendLine(srl.getStringFromID("failureExampleMessage"));
//            appendLine(srl.getStringFromID("electionType") + ": "
//                       + srl.getStringFromID(ex.getTypeString()) + "\n");
//            appendLine("");
//
//            for (int i = 0; i < ex.getNumOfElections(); i++) {
//                appendLine("====== " + srl.getStringFromID("election").toUpperCase()
//                           + " " + (i + 1) + " ======");
//                // The votes part of the document
//                appendPane(srl.getStringFromID("votes") + ": ");
//                if (ex.isChooseOneCandidate()) {
//                    writeVotesForOneCandidate(ex, i);
//                } else {
//                    writeVotesForMultipleCandidates(ex, i);
//                }
//                appendLine("");
//
//                // The elected part of the document
//                appendPane(srl.getStringFromID("elected") + ": ");
//                if (ex.getElectionDescription().getContainer()
//                        .getOutputType().isOutputOneCandidate()) {
//                    writeElectedOneCandidate(ex, i);
//                } else {
//                    writeElectedMultipleCandidates(ex, i);
//                }
//
//                // The vote points part of the document
//                appendLine("\n");
//                appendLine("====== "
//                           + srl.getStringFromID("electionpoints").toUpperCase()
//                           + " ======");
//                String[] result;
//                if (ex.isChooseOneCandidate()) {
//                    result = getVotePoints(ex.getVotes().get(i).getArray(),
//                                           ex.getElectionDescription().getContainer(),
//                                           ex);
//                } else {
//                    result = getVotePoints(ex.getVoteList().get(i).getArray(),
//                            ex.getElectionDescription().getContainer(), ex);
//                }
//                for (int j = 0; j < result.length; j++) {
//                    appendLine(srl.getStringFromID("Candidate") + " "
//                               + ex.getSymbolicCandidateForIndex(j) + ": "
//                               + result[j]);
//                }
//                appendLine("\n");
//                if (parentResult.hasSubResult()) {
//                    appendMarginResult(parentResult.getSubResult());
//                }
//            }
//        } else {
//            appendMarginResult(parentResult);
//        }
//
//        setSize(600, 600);
//        // packFrame();
//    }
//
//    private void appendMarginResult(Result marginResult) {
//        // FailureExample ex = marginResult.getFailureExample();
//        ElectionTypeContainer container
//              = marginResult.getFailureExample().getElectionDescription().getContainer();
//
//        appendLine("================================");
//        appendLine("===========Margin Result===========");
//        appendLine("================================");
//        appendLine("");
//        appendLine("====original Votes====");
//        appendLine("");
//        String toAppend
//              = container.getInputType()
//                .getVoteDescriptionString(marginResult.getOrigVoting());
//        appendPane(toAppend);
//
//        appendLine("");
//        appendLine("====original Result====");
//        appendLine("");
//        toAppend
//              = container.getOutputType()
//                .getResultDescriptionString(marginResult.getOrigWinner());
//        appendPane(toAppend);
//
//        appendLine("");
//        appendLine("====Margin computation====");
//        appendLine("");
//        appendLine("has final margin: " + marginResult.hasFinalMargin());
//        if (marginResult.hasFinalMargin()) {
//            appendLine("");
//            appendLine("final margin:" + marginResult.getFinalMargin());
//            appendLine("");
//            appendLine("====new Votes====");
//            appendLine("");
//            toAppend
//                  = container.getInputType()
//                    .getVoteDescriptionString(marginResult.getNewVotes());
//            appendPane(toAppend);
//
//            appendLine("");
//            appendLine("====new Result====");
//            appendLine("");
//            toAppend
//                  = container.getOutputType()
//                    .getResultDescriptionString(marginResult.getNewWinner());
//            appendPane(toAppend);
//        } else {
//            appendPane("There is no final margin!");
//        }
//    }
//
//    private void writeElectedMultipleCandidates(FailureExample ex, int i) {
//        String[] preceding;
//        String[] elected = ex.getSeats().get(i).getArray();
//        preceding = i == 0 ? elected : ex.getSeats().get(i - 1).getArray();
//        for (int j = 0; j < elected.length; j++) {
//            Color color = preceding[j].equals(elected[j]) ? Color.BLACK : Color.RED;
//            if (Long.parseLong(elected[j]) >= ex.getNumOfCandidates()) { // no candidate wins
//                appendPaneColored(srl.getStringFromID("draw") + "("
//                    + ex.getSymbolicCandidateForIndex(Long.parseLong(elected[j])) + ")", color);
//            } else {
//                appendPaneColored("" + elected[j], color);
//            }
//            appendPane(", ");
//        }
//        eraseLastCharacters(2);
//    }
//
//    private void writeElectedOneCandidate(FailureExample ex, int i) {
//        String preceding;
//        String elected = ex.getElect().get(i).getValue();
//        // only show differences to preceding election when it is not the first election
//        preceding = i == 0 ? elected : ex.getElect().get(i - 1).getValue();
//        Color color = preceding == elected ? Color.BLACK : Color.RED;
//        long electedL = Long.parseLong(elected);
//        if (electedL >= ex.getNumOfCandidates()) {
//            // no candidate wins
//            appendPaneColored(
//                    srl.getStringFromID("draw")
//                    + "(" + ex.getSymbolicCandidateForIndex(electedL) + ")"
//                    + ", ",
//                    color);
//        } else {
//            appendPaneColored(ex.getSymbolicCandidateForIndex(electedL) + ", ", color);
//        }
//        eraseLastCharacters(2);
//    }
//
//    private void writeVotesForMultipleCandidates(FailureExample ex, int i) {
//        String[][] precedingList;
//        String[][] voteList = ex.getVoteList().get(i).getArray();
//        precedingList = i == 0 ? voteList : ex.getVoteList().get(i - 1).getArray();
//        for (int j = 0; j < voteList.length; j++) {
//            if (Arrays.equals(precedingList[j], voteList[j])) {
//                appendPane(Arrays.toString(voteList[j]) + ", ");
//            } else {
//                appendPaneColored(Arrays.toString(voteList[j]), Color.RED);
//                appendPane(", ");
//            }
//        }
//        eraseLastCharacters(2);
//    }
//
//    private void writeVotesForOneCandidate(FailureExample ex, int i) {
//        String[] precedingList;
//        String[] voteList = ex.getVotes().get(i).getArray();
//        precedingList = i == 0 ? voteList : ex.getVotes().get(i - 1).getArray();
//        if (ex.isOneSeatOnly()) {
//            for (int j = 0; j < voteList.length; j++) {
//                Color color = precedingList[j].equals(voteList[j]) ? Color.BLACK : Color.RED;
//                appendPaneColored(
//                        ex.getSymbolicCandidateForIndex(Long.parseLong(voteList[j])),
//                                                        color);
//                appendPane(", ");
//            }
//        } else {
//            for (int j = 0; j < voteList.length; j++) {
//                Color color
//                      = precedingList[j].equals(voteList[j]) ? Color.BLACK : Color.RED;
//                appendPaneColored(ex.getSymbolicSeatForIndex(Long.parseLong(voteList[j])),
//                                  color);
//                appendPane(", ");
//            }
//        }
//        eraseLastCharacters(2);
//    }
//
//    public void presentSuccess() {
//        erasePane();
//        appendPane(srl.getStringFromID("successMessage"));
//        packFrame();
//    }
//
//    public void presentTimeOut() {
//        erasePane();
//        appendPane(srl.getStringFromID("timeoutMessage"));
//        packFrame();
//    }
//
//    public void presentCancel() {
//        erasePane();
//        appendPane(srl.getStringFromID("cancelMessage"));
//        packFrame();
//    }
//
//    public void resetResult() {
//        erasePane();
//        result.setText(srl.getStringFromID("noResultYet"));
//        packFrame();
//    }
//
//    // getter and setter
//    public JButton getShowResult() {
//        return showResult;
//    }
//
//    public FailureExample getExample() {
//        return example;
//    }
//
//    public void setExample(FailureExample example) {
//        this.example = example;
//    }
//
//    private void setShowResult(JButton showResult) {
//        this.showResult = showResult;
//    }
//
//    private JButton getExport() {
//        return export;
//    }
//
//    private void setExport(JButton export) {
//        this.export = export;
//    }
//}
