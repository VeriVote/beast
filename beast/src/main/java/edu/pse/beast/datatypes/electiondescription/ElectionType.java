package edu.pse.beast.datatypes.electiondescription;

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
	WEIGHTEDAPPROVAL;
    
    private boolean candPerSeat = false;
    
    /**
     * sets the result type to the specified value
     * @param candPerSeat true, if it is candidates per seat, else false
     */
    public void setResultTypeSeats(boolean candPerSeat) {
        this.candPerSeat = candPerSeat;
    }
	
    /**
     * tells, if the voting methode was candidates per seats or not
     * @return true, if it is candidates per seat, else false
     */
    public boolean getResultTypeSeats() {
        return candPerSeat;
    }
}
