package edu.pse.beast.toolbox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The Class UnifiedNameContainer.
 */
public final class UnifiedNameContainer { // TODO make not static and then only
                                          // accessible through electionContainer
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
        map.put("candidate", "C");
        map.put("voter", "V");
        map.put("seats", "S");
        map.put("elect", "elect");

        map.put("votingMethod", "voting");

        map.put("result_arr_name", "arr");

        map.put("votingArray", "votes");

        map.put("new_result", "new_result");
        map.put("new_votes", "new_votes");
        map.put("orig_result", "ORIG_RESULT");
        map.put("orig_votes", "ORIG_VOTES");
    }

    /**
     * Adds the listener.
     *
     * @param toAdd the to add
     */
    public static void addListener(final NameChangeListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Notify name change listeners.
     */
    private static void notifyNameChangeListeners() {
        for (Iterator<NameChangeListener> iterator = listeners.iterator();
                iterator.hasNext();) {
            NameChangeListener nameChangeListener =
                    iterator.next();
            nameChangeListener.notifyNameChange();
        }
    }

    /**
     * Gets the by key.
     *
     * @param key the key
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
        return map.get("candidate");
    }

    /**
     * Gets the voter.
     *
     * @return the voter
     */
    public static String getVoter() {
        return map.get("voter");
    }

    /**
     * Gets the seats.
     *
     * @return the seats
     */
    public static String getSeats() {
        return map.get("seats");
    }

    /**
     * Gets the elect.
     *
     * @return the elect
     */
    public static String getElect() {
        return map.get("elect");
    }

    /**
     * Gets the voting method.
     *
     * @return the voting method
     */
    public static String getVotingMethod() {
        return map.get("votingMethod");
    }

    /**
     * Gets the struct value name.
     *
     * @return the struct value name
     */
    public static String getStructValueName() {
        return map.get("result_arr_name");
    }

    /**
     * Gets the new result name.
     *
     * @return the new result name
     */
    public static String getNewResultName() {
        return map.get("new_result");
    }

    /**
     * Gets the new votes name.
     *
     * @return the new votes name
     */
    public static String getNewVotesName() {
        return map.get("new_votes");
    }

    /**
     * Gets the orig result name.
     *
     * @return the orig result name
     */
    public static String getOrigResultName() {
        return map.get("orig_result");
    }

    /**
     * Gets the orig votes name.
     *
     * @return the orig votes name
     */
    public static String getOrigVotesName() {
        return map.get("orig_votes");
    }

    /**
     * Gets the voting array.
     *
     * @return the voting array
     */
    public static String getVotingArray() {
        return map.get("votingArray");
    }

    /**
     * Gets the candidate key.
     *
     * @return the candidate key
     */
    public static String getCandidateKey() {
        return "candidate";
    }

    /**
     * Gets the voter key.
     *
     * @return the voter key
     */
    public static String getVoterKey() {
        return "voter";
    }

    /**
     * Gets the seats key.
     *
     * @return the seats key
     */
    public static String getSeatsKey() {
        return "seats";
    }

    /**
     * Gets the elect key.
     *
     * @return the elect key
     */
    public static String getElectKey() {
        return "elect";
    }

    /**
     * Gets the voting method key.
     *
     * @return the voting method key
     */
    public static String getVotingMethodKey() {
        return "votingMethod";
    }

    /**
     * Gets the struct value name key.
     *
     * @return the struct value name key
     */
    public static String getStructValueNameKey() {
        return "result_arr_name";
    }

    /**
     * Gets the voting array key.
     *
     * @return the voting array key
     */
    public static String getVotingArrayKey() {
        return "votingArray";
    }

    /**
     * Gets the orig votes key.
     *
     * @return the orig votes key
     */
    public static String getOrigVotesKey() {
        return "orig_votes";
    }

    /**
     * Sets the candidate.
     *
     * @param candidate the new candidate
     */
    public static void setCandidate(final String candidate) {
        setInMap("candidate", candidate);
    }

    /**
     * Sets the voter.
     *
     * @param voter the new voter
     */
    public static void setVoter(final String voter) {
        setInMap("voter", voter);
    }

    // public static void setVotes(final String votes) {
    // setInMap("votes", votes);
    // } TODO check if used

    /**
     * Sets the seats.
     *
     * @param seats the new seats
     */
    public static void setSeats(final String seats) {
        setInMap("seats", seats);
    }

    /**
     * Sets the voting method.
     *
     * @param votingMethod the new voting method
     */
    public static void setVotingMethod(final String votingMethod) {
        setInMap("votingMethod", votingMethod);
    }

    /**
     * Sets the result arr name.
     *
     * @param resultArrName the new result arr name
     */
    public static void setResultArrName(final String resultArrName) {
        setInMap("result_arr_name", resultArrName);
    }

    /**
     * Sets the voting array.
     *
     * @param votingArray the new voting array
     */
    public static void setVotingArray(final String votingArray) {
        setInMap("votingArray", votingArray);
    }

    /**
     * Sets the in map.
     *
     * @param key the key
     * @param toSet the to set
     */
    private static void setInMap(final String key, final String toSet) {
        map.put(key, toSet);
        notifyNameChangeListeners();
    }
}
