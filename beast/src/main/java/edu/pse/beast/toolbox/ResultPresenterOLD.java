package edu.pse.beast.toolbox;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

public final class ResultPresenterOLD {
    private static StringResourceLoader srl;
    // private static JTextPane result;
    private static String text;

    private ResultPresenterOLD() { }

    static {
        StringLoaderInterface sli = new StringLoaderInterface("en");
        PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
        srl = provider.getOtherStringRes();
    }

    // private methods
    private static String[] getVotePoints(String[] votes,
                                          ElectionTypeContainer type,
                                          FailureExample ex) {
        int amountCandidates = ex.getNumOfCandidates();
        int amountVoters = ex.getNumOfVoters();
        return type.getInputType().getVotePoints(votes, amountCandidates, amountVoters);
    }

    private static String[] getVotePoints(String[][] votes,
                                          ElectionTypeContainer type,
                                          FailureExample ex) {
        int amountCandidates = ex.getNumOfCandidates();
        int amountVoters = ex.getNumOfVoters();
        Long[] result = new Long[amountCandidates];
        Arrays.fill(result, 0l);
        return type.getInputType().getVotePoints(votes, amountCandidates, amountVoters);
    }

    private static void erasePane() {
        text = "";
    }

    public static void appendPane(String s) {
        text = text + s;
    }

    public static void appendLine(String s) {
        appendPane("\n" + s);
    }

    public static void presentFailureExample(Result parentResult) { // writes the failure
        // example to the styled document
        erasePane();
        if (!parentResult.hasFinalMargin()) { //
            FailureExample ex = parentResult.getFailureExample();
            if (ex == null) {
                return;
            }
            appendLine(((StringResourceLoader) srl).getStringFromID("failureExampleMessage"));
            appendLine(((StringResourceLoader) srl).getStringFromID("electionType") + ": "
                    + srl.getStringFromID(ex.getTypeString()) + "\n");
            appendLine("");
            for (int i = 0; i < ex.getNumOfElections(); i++) {
                appendLine("====== " + srl.getStringFromID("election").toUpperCase()
                           + " " + (i + 1) + " ======");
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
                if (ex.getElectionDescription().getContainer()
                        .getOutputType().isOutputOneCandidate()) {
                    writeElectedOneCandidate(ex, i);
                } else {
                    writeElectedMultipleCandidates(ex, i);
                }

                // The vote points part of the document
                appendLine("\n");
                appendLine("====== "
                           + srl.getStringFromID("electionpoints").toUpperCase()
                           + " ======");
                String[] result;
                if (ex.isChooseOneCandidate()) {
                    result = getVotePoints(ex.getVotes().get(i).getArray(),
                                           ex.getElectionDescription().getContainer(),
                                           ex);
                } else {
                    result = getVotePoints(ex.getVoteList().get(i).getArray(),
                            ex.getElectionDescription().getContainer(), ex);
                }
                for (int j = 0; j < result.length; j++) {
                    appendLine(srl.getStringFromID("Candidate") + " "
                               + ex.getSymbolicCandidateForIndex(j) + ": "
                               + result[j]);
                }
                appendLine("\n");
                if (parentResult.hasSubResult()) {
                    appendMarginResult(parentResult.getSubResult());
                }
            }
        } else {
            appendMarginResult(parentResult);
        }
        // packFrame();
    }

    private static void appendMarginResult(Result marginResult) {
        // FailureExample ex = marginResult.getFailureExample();
        ElectionTypeContainer container
              = marginResult.getFailureExample().getElectionDescription().getContainer();

        appendLine("================================");
        appendLine("===========Margin Result===========");
        appendLine("================================");
        appendLine("");
        final List<List<String>> origVotes = marginResult.getOrigVoting();

        appendLine("====original Votes====");
        appendLine("");
        String toAppend = container.getInputType().getVoteDescriptionString(origVotes);
        appendPane(toAppend);

        appendLine("");
        appendLine("====original Result====");
        appendLine("");
        toAppend = container.getOutputType()
                    .getResultDescriptionString(marginResult.getOrigWinner());
        appendPane(toAppend);

        appendLine("");
        appendLine("====Margin computation====");
        appendLine("");
        appendLine("has final margin: " + marginResult.hasFinalMargin());
        if (marginResult.hasFinalMargin()) {
            appendLine("");
            appendLine("final margin:" + marginResult.getFinalMargin());
            appendLine("");
            appendLine("====new Votes====");
            appendLine("");
            toAppend = container.getInputType()
                            .getVoteDescriptionString(marginResult.getNewVotes());
            appendPane(toAppend);

            appendLine("");
            appendLine("====new Result====");
            appendLine("");
            toAppend = container.getOutputType()
                        .getResultDescriptionString(marginResult.getNewWinner());
            appendPane(toAppend);
        } else {
            appendPane("There is no final margin!");
        }
    }

    private static void writeElectedOneCandidate(FailureExample ex, int i) {
        String preceding;
        String elected = ex.getElect().get(i).getValue();

        // only show differences to preceding election when it is not the first
        // election
        preceding = i == 0 ? elected : ex.getElect().get(i - 1).getValue();
        long electedL = Long.parseLong(elected);
        if (electedL >= ex.getNumOfCandidates()) { // no candidate wins
            appendPane(srl.getStringFromID("draw")
                       + "(" + ex.getSymbolicCandidateForIndex(electedL) + ")"
                       + ", ");
        } else {
            appendPane(ex.getSymbolicCandidateForIndex(electedL) + ", ");
        }
    }

    private static void writeElectedMultipleCandidates(FailureExample ex, int i) {
        String[] preceding;
        String[] elected = ex.getSeats().get(i).getArray();
        preceding = i == 0 ? elected : ex.getSeats().get(i - 1).getArray();
        for (int j = 0; j < elected.length; j++) {
            if (Long.parseLong(elected[j]) >= ex.getNumOfCandidates()) {
                // no candidate wins
                appendPane(srl.getStringFromID("draw") + "("
                        + ex.getSymbolicCandidateForIndex(Long.parseLong(elected[j])) + ")");
            } else {
                appendPane("" + elected[j]);
            }
            appendPane(", ");
        }
    }

    private static void writeVotesForMultipleCandidates(FailureExample ex, int i) {
        String[][] precedingList;
        String[][] voteList = ex.getVoteList().get(i).getArray();
        precedingList = i == 0 ? voteList : ex.getVoteList().get(i - 1).getArray();
        for (int j = 0; j < voteList.length; j++) {
            if (Arrays.equals(precedingList[j], voteList[j])) {
                appendPane(Arrays.toString(voteList[j]) + ", ");
            } else {
                appendPane(Arrays.toString(voteList[j]));
                appendPane(", ");
            }
        }
    }

    private static void writeVotesForOneCandidate(FailureExample ex, int i) {
        String[] precedingList;
        String[] voteList = ex.getVotes().get(i).getArray();
        precedingList = i == 0 ? voteList : ex.getVotes().get(i - 1).getArray();
        if (ex.isOneSeatOnly()) {
            for (int j = 0; j < voteList.length; j++) {
                appendPane(ex.getSymbolicCandidateForIndex(Long.parseLong(voteList[j])));
                appendPane(", ");
            }
        } else {
            for (int j = 0; j < voteList.length; j++) {
                appendPane(ex.getSymbolicSeatForIndex(Long.parseLong(voteList[j])));
                appendPane(", ");
            }
        }
    }

    public static String getText() {
        return text;
    }

//  public static void presentSuccess() {
//    erasePane();
//    appendPane(srl.getStringFromID("successMessage"));
//    packFrame();
//  }

//  public static void presentTimeOut() {
//    erasePane();
//    appendPane(srl.getStringFromID("timeoutMessage"));
//    packFrame();
//  }

//  public static void presentCancel() {
//    erasePane();
//    appendPane(srl.getStringFromID("cancelMessage"));
//    packFrame();
//  }
//
//  public static void resetResult() {
//    erasePane();
//    result.setText(srl.getStringFromID("noResultYet"));
//    packFrame();
//  }

//  // getter and setter
//  public JButton getShowResult() {
//    return showResult;
//  }
//
//  public FailureExample getExample() {
//    return example;
//  }
//
//  public static void setExample(FailureExample example) {
//    this.example = example;
//  }
//
//  private static void setShowResult(JButton showResult) {
//    this.showResult = showResult;
//  }
//
//  private JButton getExport() {
//    return export;
//  }
//
//  private static void setExport(JButton export) {
//    this.export = export;
//  }
}