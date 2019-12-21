package edu.pse.beast.toolbox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class UnifiedNameContainer { // TODO make not static and then only
                                          // accessible through electionContainer
    private static List<NameChangeListener> listeners = new LinkedList<NameChangeListener>();
    private static Map<String, String> map = new HashMap<String, String>();

    public UnifiedNameContainer() {
        init();
    }

    static {
        init();
    }

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

    public static void addListener(NameChangeListener toAdd) {
        listeners.add(toAdd);
    }

    private static void notifyNameChangeListeners() {
        for (Iterator<NameChangeListener> iterator = listeners.iterator(); iterator.hasNext();) {
            NameChangeListener nameChangeListener = (NameChangeListener) iterator.next();
            nameChangeListener.notifyNameChange();
        }
    }

    public static String getByKey(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return key;
        }
    }

    public static String getCandidate() {
        return map.get("candidate");
    }

    public static String getVoter() {
        return map.get("voter");
    }

    public static String getSeats() {
        return map.get("seats");
    }

    public static String getElect() {
        return map.get("elect");
    }

    public static String getVotingMethod() {
        return map.get("votingMethod");
    }

    public static String getStructValueName() {
        return map.get("result_arr_name");
    }

    public static String getNewResultName() {
        return map.get("new_result");
    }

    public static String getNewVotesName() {
        return map.get("new_votes");
    }

    public static String getOrigResultName() {
        return map.get("orig_result");
    }

    public static String getOrigVotesName() {
        return map.get("orig_votes");
    }

    public static String getVotingArray() {
        return map.get("votingArray");
    }

    public static String getCandidateKey() {
        return "candidate";
    }

    public static String getVoterKey() {
        return "voter";
    }

    public static String getSeatsKey() {
        return "seats";
    }

    public static String getElectKey() {
        return "elect";
    }

    public static String getVotingMethodKey() {
        return "votingMethod";
    }

    public static String getStructValueNameKey() {
        return "result_arr_name";
    }

    public static String getVotingArrayKey() {
        return "votingArray";
    }

    public static String getOrigVotesKey() {
        return "orig_votes";
    }

    public static void setCandidate(String candidate) {
        setInMap("candidate", candidate);
    }

    public static void setVoter(String voter) {
        setInMap("voter", voter);
    }

    // public static void setVotes(String votes) {
    // setInMap("votes", votes);
    // } TODO check if used

    public static void setSeats(String seats) {
        setInMap("seats", seats);
    }

    public static void setVotingMethod(String votingMethod) {
        setInMap("votingMethod", votingMethod);
    }

    public static void setResultArrName(String resultArrName) {
        setInMap("result_arr_name", resultArrName);
    }

    public static void setVotingArray(String votingArray) {
        setInMap("votingArray", votingArray);
    }

    private static void setInMap(String key, String toSet) {
        map.put(key, toSet);
        notifyNameChangeListeners();
    }
}