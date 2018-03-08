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
		map.put("candidate" , "C");
		map.put("voter" , "V");
		map.put("seats" , "S");

		map.put("votingMethod" , "voting");
		map.put("struct_result" , "result");
		map.put("stack_result" , "stack_result");
		map.put("result_arr_name" , "arr");
		
		map.put("votingArray" , "votes");
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

	public static String getStruct_result() {
		return map.get("struct_result");
	}

	public static String getStruct_stack_result() {
		return map.get("stack_result");
	}

	public static String getResult_arr_name() {
		return map.get("result_arr_name");
	}

	public static String getVotingArray() {
		return map.get("votingArray");
	}
	
	public static String getCandidateKey() {
		return  "candidate"; 
	}

	public static String getVoterKey() {
		return  "voter"; 
	}

	public static String getSeatsKey() {
		return  "seats"; 
	}

	public static String getVotingMethodKey() {
		return "votingMethod"; 
	}

	public static String getStruct_resultKey() {
		return "struct_result"; 
	}

	public static String getStruct_stack_resultKey() {
		return "stack_result"; 
	}

	public static String getResult_arr_nameKey() {
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

	public static void setStruct_result(String struct_result) {
		setInMap("struct_result", struct_result);
	}

	public static void setStack_result(String stack_result) {
		setInMap("stack_result", stack_result);
	}

	public static void setResult_arr_name(String result_arr_name) {
		setInMap("result_arr_name", result_arr_name);
	}

	public static void setVotingArray(String votingArray) {
		setInMap("votingArray", votingArray);
	}

	private static void setInMap(String key, String toSet) {
		map.put(key, toSet);
		notifyNameChangeListeners();
	}
	
}
