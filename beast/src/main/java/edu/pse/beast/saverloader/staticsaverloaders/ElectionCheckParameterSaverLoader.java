package edu.pse.beast.saverloader.staticsaverloaders;

import java.util.ArrayList;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 * Implements static methods for creating saveStrings from
 * ElectionCheckParameter objects and vice versa.
 *
 * @author Nikolai Schnell
 */
public final class ElectionCheckParameterSaverLoader {
    private static final char LEFT         = '<';
    private static final char RIGHT        = '>';
    private static final char BREAK        = '\n';
    private static final char SLASH        = '/';

    private static final String EMPTY      = "";
    private static final String MIN        = "Min";
    private static final String MAX        = "Max";
    private static final String VOTERS     = "amountVoters";
    private static final String CANDIDATES = "amountCandidates";
    private static final String SEATS      = "amountSeats";
    private static final String TIMEOUT    = "timeout";
    private static final String PROCS      = "processes";
    private static final String ARG        = "argument";

    private static String[] splitString = {EMPTY};

    private ElectionCheckParameterSaverLoader() { }

    private static void setString(final String s) {
        splitString = new String[] {s};
    }

    private static void resetString() {
        setString(EMPTY);
    }

    private static String boundString(final String type, final String minOrMax) {
        return EMPTY + BREAK + LEFT + SLASH + type + minOrMax + RIGHT + BREAK;
    }

    private static String boundString(String type) {
        return boundString(type, EMPTY);
    }

    private static String valString(final String type, final String minOrMax) {
        return LEFT + type + minOrMax + RIGHT + BREAK;
    }

    private static String valString(String type) {
        return valString(type, EMPTY);
    }

    private static String removeFrom(String[] src, String remove) {
        return src[0].replace(EMPTY + remove, EMPTY);
    }

    private static String getString(String type, String minOrMax) {
        final int idx = splitString.length <= 1 ? 0 : 1;
        splitString = splitString[idx].split(boundString(type, minOrMax));
        return removeFrom(splitString, valString(type, minOrMax));
    }

    private static String getString(String type) {
        return getString(type, EMPTY);
    }

    private static int getInt(String type, String minOrMax) {
        return Integer.parseInt(getString(type, minOrMax));
    }

    private static int getInt(String type) {
        return getInt(type, EMPTY);
    }

    private static ArrayList<Integer> toIntList(int start, int end) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    private static ArrayList<Integer> getList(final String type) {
        return toIntList(getInt(type, MIN), getInt(type, MAX));
    }

    /**
     * Creates a String from a given ElectionCheckParameter, that can then be saved
     * to a file and later given to createFromSaveString() to retrieve the saved
     * object.
     *
     * @param electionCheckParameter the ElectionCheckParameter
     * @return the saveString
     */
    public static String createSaveString(ElectionCheckParameter electionCheckParameter) {
        final String amountVotersMin
              = valString(VOTERS, MIN)
                + electionCheckParameter.getAmountVoters().get(0)
                + boundString(VOTERS, MIN);
        final String amountVotersMax
              = valString(VOTERS, MAX)
                + electionCheckParameter.getAmountVoters()
                    .get(electionCheckParameter.getAmountVoters().size() - 1)
                + boundString(VOTERS, MAX);
        final String amountCandidatesMin
              = valString(CANDIDATES, MIN)
                + electionCheckParameter.getAmountCandidates().get(0)
                + boundString(CANDIDATES, MIN);
        final String amountCandidatesMax
              = valString(CANDIDATES, MAX)
                + electionCheckParameter.getAmountCandidates()
                    .get(electionCheckParameter.getAmountCandidates().size() - 1)
                + boundString(CANDIDATES, MAX);
        final String amountSeatsMin
              = valString(SEATS, MIN)
                + electionCheckParameter.getAmountSeats().get(0)
                + boundString(SEATS, MIN);
        final String amountSeatsMax
              = valString(SEATS, MAX)
                + electionCheckParameter.getAmountSeats()
                    .get(electionCheckParameter.getAmountSeats().size() - 1)
                + boundString(SEATS, MAX);
        final String timeout
              = valString(TIMEOUT)
                + TimeOutSaverLoader.createSaveString(electionCheckParameter.getTimeout())
                + boundString(TIMEOUT);
        final String processes
              = valString(PROCS) + electionCheckParameter.getProcesses() + boundString(PROCS);
        final String argument
              = valString(ARG) + electionCheckParameter.getArgument() + boundString(ARG);
        return (amountVotersMin + amountVotersMax
                + amountCandidatesMin + amountCandidatesMax
                + amountSeatsMin + amountSeatsMax
                + timeout + processes + argument);
    }

    /**
     * Creates an Object from a given, by createSaveString() generated, saveString
     *
     * @param s the SaveString
     * @return the Object
     * @throws ArrayIndexOutOfBoundsException if the saveString does not contain a
     *                                        valid format
     */
    public static Object createFromSaveString(String s) throws ArrayIndexOutOfBoundsException {
        setString(s);
        final ArrayList<Integer> amountVoters = getList(VOTERS);
        final ArrayList<Integer> amountCandidates = getList(CANDIDATES);
        final ArrayList<Integer> amountSeats = getList(SEATS);
        final TimeOut timeout = TimeOutSaverLoader.createFromSaveString(getString(TIMEOUT));
        final int processes = getInt(PROCS);
        final String argument = getString(ARG);
        resetString();
        return new ElectionCheckParameter(amountVoters, amountCandidates, amountSeats,
                                          timeout, processes, argument);
    }
}
