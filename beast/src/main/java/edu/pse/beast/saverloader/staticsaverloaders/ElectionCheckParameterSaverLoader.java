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

    private static final String MIN        = "Min";
    private static final String MAX        = "Max";
    private static final String VOTERS     = "amountVoters";
    private static final String CANDIDATES = "amountCandidates";
    private static final String SEATS      = "amountSeats";
    private static final String TIMEOUT    = "timeout";
    private static final String PROCS      = "processes";
    private static final String ARG        = "argument";

    private ElectionCheckParameterSaverLoader() { }

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
              = LEFT + VOTERS + MIN + RIGHT + BREAK
                + electionCheckParameter.getAmountVoters().get(0)
                + BREAK + LEFT + SLASH + VOTERS + MIN + RIGHT + BREAK;
        final String amountVotersMax
              = LEFT + VOTERS + MAX + RIGHT + BREAK
                + electionCheckParameter.getAmountVoters()
                    .get(electionCheckParameter.getAmountVoters().size() - 1)
                + BREAK + LEFT + SLASH + VOTERS + MAX + RIGHT + BREAK;
        final String amountCandidatesMin
              = LEFT + CANDIDATES + MIN + RIGHT + BREAK
                + electionCheckParameter.getAmountCandidates().get(0)
                + BREAK + LEFT + SLASH + CANDIDATES + MIN + RIGHT + BREAK;
        final String amountCandidatesMax
              = LEFT + CANDIDATES + MAX + RIGHT + BREAK
                + electionCheckParameter.getAmountCandidates()
                    .get(electionCheckParameter.getAmountCandidates().size() - 1)
                    + BREAK + LEFT + SLASH + CANDIDATES + MAX + RIGHT + BREAK;
        final String amountSeatsMin
              = LEFT + SEATS + MIN + RIGHT + BREAK
                + electionCheckParameter.getAmountSeats().get(0)
                + BREAK + LEFT + SLASH + SEATS + MIN + RIGHT + BREAK;
        final String amountSeatsMax
              = LEFT + SEATS + MAX + RIGHT + BREAK
                + electionCheckParameter.getAmountSeats()
                    .get(electionCheckParameter.getAmountSeats().size() - 1)
                    + BREAK + LEFT + SLASH + SEATS + MAX + RIGHT + BREAK;
        final String timeout
              = LEFT + TIMEOUT + RIGHT + BREAK
                + TimeOutSaverLoader.createSaveString(electionCheckParameter.getTimeout())
                + BREAK + LEFT + SLASH + TIMEOUT + RIGHT + BREAK;
        final String processes
              = LEFT + PROCS + RIGHT + BREAK
                + electionCheckParameter.getProcesses()
                + BREAK + LEFT + SLASH + PROCS + RIGHT + BREAK;
        final String argument
              = LEFT + ARG + RIGHT + BREAK
                + electionCheckParameter.getArgument()
                + BREAK + LEFT + SLASH + ARG + RIGHT + BREAK;
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
        String split[] = s.split(BREAK + LEFT + SLASH + VOTERS + MIN + RIGHT + BREAK);
        int amountVotersMin
            = Integer.parseInt(split[0].replace(LEFT + VOTERS + MIN + RIGHT + BREAK, ""));
        split = split[1].split(BREAK + LEFT + SLASH + VOTERS + MAX + RIGHT + BREAK);
        int amountVotersMax
            = Integer.parseInt(split[0].replace(LEFT + VOTERS + MAX + RIGHT + BREAK, ""));
        ArrayList<Integer> amountVoters = new ArrayList<>();
        for (int i = amountVotersMin; i <= amountVotersMax; i++) {
            amountVoters.add(i);
        }
        split = split[1].split(BREAK + LEFT + SLASH + CANDIDATES + MIN + RIGHT + BREAK);
        int amountCandidatesMin
            = Integer.parseInt(split[0].replace(LEFT + CANDIDATES + MIN + RIGHT + BREAK, ""));
        split = split[1].split(BREAK + LEFT + SLASH + CANDIDATES + MAX + RIGHT + BREAK);
        int amountCandidatesMax
            = Integer.parseInt(split[0].replace(LEFT + CANDIDATES + MAX + RIGHT + BREAK, ""));
        ArrayList<Integer> amountCandidates = new ArrayList<>();
        for (int i = amountCandidatesMin; i <= amountCandidatesMax; i++) {
            amountCandidates.add(i);
        }
        split = split[1].split(BREAK + LEFT + SLASH + SEATS + MIN + RIGHT + BREAK);
        int amountSeatsMin
            = Integer.parseInt(split[0].replace(LEFT + SEATS + MIN + RIGHT + BREAK, ""));
        split = split[1].split(BREAK + LEFT + SLASH + SEATS + MAX + RIGHT + BREAK);
        int amountSeatsMax
            = Integer.parseInt(split[0].replace(LEFT + SEATS + MAX + RIGHT + BREAK, ""));
        ArrayList<Integer> amountSeats = new ArrayList<>();
        for (int i = amountSeatsMin; i <= amountSeatsMax; i++) {
            amountSeats.add(i);
        }
        split = split[1].split(BREAK + LEFT + SLASH + TIMEOUT + RIGHT + BREAK);
        String st = split[0].replace(LEFT + TIMEOUT + RIGHT + BREAK, "");
        TimeOut timeout
            = TimeOutSaverLoader.createFromSaveString(st);
        split = split[1].split(BREAK + LEFT + SLASH + PROCS + RIGHT + BREAK);
        int processes
            = Integer.parseInt(split[0].replace(LEFT + PROCS + RIGHT + BREAK, ""));
        split = split[1].split(BREAK + LEFT + SLASH + ARG + RIGHT + BREAK);
        String argument = split[0].replace(LEFT + ARG + RIGHT + BREAK, "");
        return new ElectionCheckParameter(amountVoters, amountCandidates, amountSeats,
                                          1, 1, 1, timeout, processes, argument);
    }
}