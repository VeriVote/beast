package edu.pse.beast.datatypes;

/**
 * The enumeration holds information about the type of an election. That is the way a candidate is chosen.
 * @author Justin
 *
 */
public enum ElectionType {
	/**
	 * Every voter chooses exactly one candidate.
	 */
	SINGLECHOICE,
	
	/**
	 * Every voter lists the candidates in order of preference. The most wanted candidate at the beginning, the least wanted
	 * at the end.
	 */
	PREFERENCE,
	
	/**
	 * Every voter approves (or not) of each candidate.
	 */
	APPROVAL,
	
	/**
	 * Every voter gives each candidate a rating from a minimum value to a maximum value.
	 */
	WEIGHTEDAPPROVAL
	
}
