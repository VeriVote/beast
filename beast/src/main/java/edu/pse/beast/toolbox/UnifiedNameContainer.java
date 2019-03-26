package edu.pse.beast.toolbox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UnifiedNameContainer {
    private static List<NameChangeListener> listeners = new LinkedList<NameChangeListener>();
    private static Map<String, String> map = new HashMap<String, String>();

    static {
        init();
    }

    private static void init() {
        map.put("candidate", "C");
        map.put("voter", "V");
        map.put("seats", "S");

        map.put("votingMethod", "voting");
        map.put("struct_result", "result");
        map.put("stack_result", "stack_result");
        map.put("struct_candidateList", "candidateList_result");
        map.put("result_arr_name", "arr");

        map.put("votingArray", "votes");

        map.put("new_result", "new_result");
        map.put(("new_votes"), ("new_votes"));
        map.put("orig_result", "ORIG_RESULT");
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

    public static String getVotingMethod() {
        return map.get("votingMethod");
    }

    public static String getStructResult() {
        return "struct " + map.get("struct_result");
    }

    public static String getStructCandidateList() {
        return "struct " + map.get("struct_candidateList");
    }

    public static String getStructStackResult() {
        return "struct " + map.get("stack_result");
    }

    public static String getResultArrName() {
        return map.get("result_arr_name");
    }

    public static String getNewResultName() {
        return map.get("new_result");
    }

    public static String getNewVotesName() {
        return map.get("new_votes");
    }

    public static String getOrigResultName() {
        return map.get("ORIG_RESULT");
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

    public static String getVotingMethodKey() {
        return "votingMethod";
    }

    public static String getStructResultKey() {
        return "struct_result";
    }

    public static String getStructStackResultKey() {
        return "stack_result";
    }

    public static String getResultArrNameKey() {
        return "result_arr_name";
    }

    public static String getVotingArrayKey() {
        return "votingArray";
    }

    public static void setCandidate(String candidate) {
        setInMap("candidate", candidate);
    }

    public static void setVoter(String voter) {
        setInMap("voter", voter);
    }

    public static void setVotes(String votes) {
        setInMap("votes", votes);
    }

    public static void setSeats(String seats) {
        setInMap("seats", seats);
    }

    public static void setVotingMethod(String votingMethod) {
        setInMap("votingMethod", votingMethod);
    }

    public static void setStructResult(String structResult) {
        setInMap("struct_result", structResult);
    }

    public static void setStackResult(String stackResult) {
        setInMap("stack_result", stackResult);
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