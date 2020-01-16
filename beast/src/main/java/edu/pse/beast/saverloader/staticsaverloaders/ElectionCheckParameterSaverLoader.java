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
    /** The Constant LEFT. */
    private static final char LEFT         = '<';

    /** The Constant RIGHT. */
    private static final char RIGHT        = '>';

    /** The Constant BREAK. */
    private static final char BREAK        = '\n';

    /** The Constant SLASH. */
    private static final char SLASH        = '/';

    /** The Constant EMPTY. */
    private static final String EMPTY      = "";

    /** The Constant MIN. */
    private static final String MIN        = "Min";

    /** The Constant MAX. */
    private static final String MAX        = "Max";

    /** The Constant VOTERS. */
    private static final String VOTERS     = "amountVoters";

    /** The Constant CANDIDATES. */
    private static final String CANDIDATES = "amountCandidates";

    /** The Constant SEATS. */
    private static final String SEATS      = "amountSeats";

    /** The Constant TIMEOUT. */
    private static final String TIMEOUT    = "timeout";

    /** The Constant PROCS. */
    private static final String PROCS      = "processes";

    /** The Constant ARG. */
    private static final String ARG        = "argument";

    /** The split string. */
    private static String[] splitString = {EMPTY};

    /**
     * Instantiates a new election check parameter saver loader.
     */
    private ElectionCheckParameterSaverLoader() { }

    /**
     * Sets the string.
     *
     * @param s the new string
     */
    private static void setString(final String s) {
        splitString = new String[] {s};
    }

    /**
     * Reset string.
     */
    private static void resetString() {
        setString(EMPTY);
    }

    /**
     * Bound string.
     *
     * @param type the type
     * @param minOrMax the min or max
     * @return the string
     */
    private static String boundString(final String type, final String minOrMax) {
        return EMPTY + BREAK + LEFT + SLASH + type + minOrMax + RIGHT + BREAK;
    }

    /**
     * Bound string.
     *
     * @param type the type
     * @return the string
     */
    private static String boundString(final String type) {
        return boundString(type, EMPTY);
    }

    /**
     * Val string.
     *
     * @param type the type
     * @param minOrMax the min or max
     * @return the string
     */
    private static String valString(final String type, final String minOrMax) {
        return LEFT + type + minOrMax + RIGHT + BREAK;
    }

    /**
     * Val string.
     *
     * @param type the type
     * @return the string
     */
    private static String valString(final String type) {
        return valString(type, EMPTY);
    }

    /**
     * Removes the from.
     *
     * @param src the src
     * @param remove the remove
     * @return the string
     */
    private static String removeFrom(final String[] src, final String remove) {
        return src[0].replace(EMPTY + remove, EMPTY);
    }

    /**
     * Gets the string.
     *
     * @param type the type
     * @param minOrMax the min or max
     * @return the string
     */
    private static String getString(final String type, final String minOrMax) {
        final int idx = splitString.length <= 1 ? 0 : 1;
        splitString = splitString[idx].split(boundString(type, minOrMax));
        return removeFrom(splitString, valString(type, minOrMax));
    }

    /**
     * Gets the string.
     *
     * @param type the type
     * @return the string
     */
    private static String getString(final String type) {
        return getString(type, EMPTY);
    }

    /**
     * Gets the int.
     *
     * @param type the type
     * @param minOrMax the min or max
     * @return the int
     */
    private static int getInt(final String type, final String minOrMax) {
        return Integer.parseInt(getString(type, minOrMax));
    }

    /**
     * Gets the int.
     *
     * @param type the type
     * @return the int
     */
    private static int getInt(final String type) {
        return getInt(type, EMPTY);
    }

    /**
     * To int list.
     *
     * @param start the start
     * @param end the end
     * @return the array list
     */
    private static ArrayList<Integer> toIntList(final int start, final int end) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * Gets the list.
     *
     * @param type the type
     * @return the list
     */
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
    public static String createSaveString(final ElectionCheckParameter electionCheckParameter) {
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
     * Creates an Object from a given, by createSaveString() generated, saveString.
     *
     * @param s the SaveString
     * @return the Object
     * @throws ArrayIndexOutOfBoundsException if the saveString does not contain a
     *                                        valid format
     */
    public static Object createFromSaveString(final String s)
                            throws ArrayIndexOutOfBoundsException {
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
