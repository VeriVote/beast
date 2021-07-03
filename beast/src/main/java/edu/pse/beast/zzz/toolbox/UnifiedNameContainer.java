package edu.pse.beast.zzz.toolbox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The Class UnifiedNameContainer.
 *
 * <p>TODO make not static and then only
 * accessible through electionContainer
 *
 * @author Lukas Stapelbroek
 */
public final class UnifiedNameContainer {
    /** The string C. */
    private static final String C = "C";
    /** The string V. */
    private static final String V = "V";
    /** The string S. */
    private static final String S = "S";
    /** The candidate string. */
    private static final String CANDIDATE = "candidate";
    /** The voter string. */
    private static final String VOTER = "voter";
    /** The seats string. */
    private static final String SEATS = "seats";
    /** The elect string. */
    private static final String ELECT = "elect";
    /** The string voting. */
    private static final String VOTING = "voting";
    /** The string arr. */
    private static final String ARR = "arr";
    /** The string votes. */
    private static final String VOTES = "votes";
    /** The voting method string. */
    private static final String VOTING_METHOD = "votingMethod";
    /** The result array name string. */
    private static final String RESULT_ARR_NAME = "result_arr_name";
    /** The voting array string. */
    private static final String VOTING_ARRAY = "votingArray";
    /** The new result string. */
    private static final String NEW_RESULT = "new_result";
    /** The new votes string. */
    private static final String NEW_VOTES = "new_votes";
    /** The orig result key. */
    private static final String ORIG_RESULT_KEY = "orig_result";
    /** The orig result string. */
    private static final String ORIG_RESULT = "ORIG_RESULT";
    /** The orig votes key. */
    private static final String ORIG_VOTES_KEY = "orig_votes";
    /** The orig votes string. */
    private static final String ORIG_VOTES = "ORIG_VOTES";

    /** The listeners. */
    private static List<NameChangeListener> listeners = new LinkedList<NameChangeListener>();

    /** The map. */
    private static Map<String, String> map = new HashMap<String, String>();

    /**
     * The constructor.
     */
    public UnifiedNameContainer() {
        init();
    }

    static {
        init();
    }

    /**
     * Inits the.
     */
    private static void init() {
        map.put(CANDIDATE, C);
        map.put(VOTER, V);
        map.put(SEATS, S);
        map.put(ELECT, ELECT);

        map.put(VOTING_METHOD, VOTING);

        map.put(RESULT_ARR_NAME, ARR);

        map.put(VOTING_ARRAY, VOTES);

        map.put(NEW_RESULT, NEW_RESULT);
        map.put(NEW_VOTES, NEW_VOTES);
        map.put(ORIG_RESULT_KEY, ORIG_RESULT);
        map.put(ORIG_VOTES_KEY, ORIG_VOTES);
    }

    /**
     * Adds the listener.
     *
     * @param toAdd
     *            the to add
     */
    public static void addListener(final NameChangeListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Notify name change listeners.
     */
    private static void notifyNameChangeListeners() {
        for (Iterator<NameChangeListener> iterator =
                    listeners.iterator();
                iterator.hasNext();) {
            final NameChangeListener nameChangeListener = iterator.next();
            nameChangeListener.notifyNameChange();
        }
    }

    /**
     * Gets the by key.
     *
     * @param key
     *            the key
     * @return the by key
     */
    public static String getByKey(final String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return key;
        }
    }

    /**
     * Gets the candidate.
     *
     * @return the candidate
     */
    public static String getCandidate() {
        return map.get(CANDIDATE);
    }

    /**
     * Gets the voter.
     *
     * @return the voter
     */
    public static String getVoter() {
        return map.get(VOTER);
    }

    /**
     * Gets the seats.
     *
     * @return the seats
     */
    public static String getSeats() {
        return map.get(SEATS);
    }

    /**
     * Gets the elect.
     *
     * @return the elect
     */
    public static String getElect() {
        return map.get(ELECT);
    }

    /**
     * Gets the voting method.
     *
     * @return the voting method
     */
    public static String getVotingMethod() {
        return map.get(VOTING_METHOD);
    }

    /**
     * Gets the struct value name.
     *
     * @return the struct value name
     */
    public static String getStructValueName() {
        return map.get(RESULT_ARR_NAME);
    }

    /**
     * Gets the new result name.
     *
     * @return the new result name
     */
    public static String getNewResultName() {
        return map.get(NEW_RESULT);
    }

    /**
     * Gets the new votes name.
     *
     * @return the new votes name
     */
    public static String getNewVotesName() {
        return map.get(NEW_VOTES);
    }

    /**
     * Gets the orig result name.
     *
     * @return the orig result name
     */
    public static String getOrigResultName() {
        return map.get(ORIG_RESULT_KEY);
    }

    /**
     * Gets the orig votes name.
     *
     * @return the orig votes name
     */
    public static String getOrigVotesName() {
        return map.get(ORIG_VOTES_KEY);
    }

    /**
     * Gets the voting array.
     *
     * @return the voting array
     */
    public static String getVotingArray() {
        return map.get(VOTING_ARRAY);
    }

    /**
     * Gets the candidate key.
     *
     * @return the candidate key
     */
    public static String getCandidateKey() {
        return CANDIDATE;
    }

    /**
     * Gets the voter key.
     *
     * @return the voter key
     */
    public static String getVoterKey() {
        return VOTER;
    }

    /**
     * Gets the seats key.
     *
     * @return the seats key
     */
    public static String getSeatsKey() {
        return SEATS;
    }

    /**
     * Gets the elect key.
     *
     * @return the elect key
     */
    public static String getElectKey() {
        return ELECT;
    }

    /**
     * Gets the voting method key.
     *
     * @return the voting method key
     */
    public static String getVotingMethodKey() {
        return VOTING_METHOD;
    }

    /**
     * Gets the struct value name key.
     *
     * @return the struct value name key
     */
    public static String getStructValueNameKey() {
        return RESULT_ARR_NAME;
    }

    /**
     * Gets the voting array key.
     *
     * @return the voting array key
     */
    public static String getVotingArrayKey() {
        return VOTING_ARRAY;
    }

    /**
     * Gets the orig votes key.
     *
     * @return the orig votes key
     */
    public static String getOrigVotesKey() {
        return ORIG_VOTES_KEY;
    }

    /**
     * Sets the candidate.
     *
     * @param candidate
     *            the new candidate
     */
    public static void setCandidate(final String candidate) {
        setInMap(CANDIDATE, candidate);
    }

    /**
     * Sets the voter.
     *
     * @param voter
     *            the new voter
     */
    public static void setVoter(final String voter) {
        setInMap(VOTER, voter);
    }

    // public static void setVotes(final String votes) {
    // setInMap("votes", votes);
    // } TODO check if used

    /**
     * Sets the seats.
     *
     * @param seats
     *            the new seats
     */
    public static void setSeats(final String seats) {
        setInMap(SEATS, seats);
    }

    /**
     * Sets the voting method.
     *
     * @param votingMethod
     *            the new voting method
     */
    public static void setVotingMethod(final String votingMethod) {
        setInMap(VOTING_METHOD, votingMethod);
    }

    /**
     * Sets the result arr name.
     *
     * @param resultArrName
     *            the new result arr name
     */
    public static void setResultArrName(final String resultArrName) {
        setInMap(RESULT_ARR_NAME, resultArrName);
    }

    /**
     * Sets the voting array.
     *
     * @param votingArray
     *            the new voting array
     */
    public static void setVotingArray(final String votingArray) {
        setInMap(VOTING_ARRAY, votingArray);
    }

    /**
     * Sets the in map.
     *
     * @param key
     *            the key
     * @param toSet
     *            the to set
     */
    private static void setInMap(final String key, final String toSet) {
        map.put(key, toSet);
        notifyNameChangeListeners();
    }
}
