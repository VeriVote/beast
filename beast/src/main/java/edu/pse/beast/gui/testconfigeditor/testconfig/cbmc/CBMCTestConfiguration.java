package edu.pse.beast.gui.testconfigeditor.testconfig.cbmc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;

public class CBMCTestConfiguration {
    private int minCands;
    private int minVoters;
    private int minSeats;

    private int maxCands;
    private int maxVoters;
    private int maxSeats;

    private String name;

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;

    private boolean startRunsOnCreation;

    private List<CBMCTestRunWithSymbolicVars> runs = new ArrayList<>();

    private String uuid;

    public CBMCTestConfiguration() {
        uuid = UUID.randomUUID().toString();
    }

    public void addRuns(final List<CBMCTestRunWithSymbolicVars> runList) {
        this.runs.addAll(runList);
    }

    public int getMinCands() {
        return minCands;
    }

    public int getMinVoters() {
        return minVoters;
    }

    public int getMinSeats() {
        return minSeats;
    }

    public int getMaxCands() {
        return maxCands;
    }

    public int getMaxVoters() {
        return maxVoters;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public String getName() {
        return name;
    }

    public CElectionDescription getDescr() {
        return description;
    }

    public PreAndPostConditionsDescription getPropDescr() {
        return propertyDescription;
    }

    public void setMinCands(final int minCandidateAmount) {
        this.minCands = minCandidateAmount;
        if (maxCands < minCandidateAmount) {
            maxCands = minCandidateAmount;
        }
    }

    public void setMaxCands(final int maxCandidateAmount) {
        this.maxCands = maxCandidateAmount;
        if (maxCandidateAmount < minCands) {
            minCands = maxCandidateAmount;
        }
    }

    public void setMinVoters(final int minVoterAmount) {
        this.minVoters = minVoterAmount;
        if (maxVoters < minVoterAmount) {
            maxVoters = minVoterAmount;
        }
    }

    public void setMaxVoters(final int maxVoterAmount) {
        this.maxVoters = maxVoterAmount;
        if (maxVoterAmount < minVoters) {
            minVoters = maxVoterAmount;
        }
    }

    public void setMinSeats(final int minSeatAmount) {
        this.minSeats = minSeatAmount;
        if (maxSeats < minSeatAmount) {
            maxSeats = minSeatAmount;
        }
    }

    public void setMaxSeats(final int maxSeatAmount) {
        this.maxSeats = maxSeatAmount;
        if (maxSeatAmount < minSeats) {
            minSeats = maxSeatAmount;
        }
    }

    public void setName(final String nameString) {
        this.name = nameString;
    }

    public void setDescr(final CElectionDescription descr) {
        this.description = descr;
    }

    public void setPropDescr(final PreAndPostConditionsDescription propDescr) {
        this.propertyDescription = propDescr;
    }

    public boolean getStartRunsOnCreation() {
        return startRunsOnCreation;
    }

    public void setStartRunsOnCreation(final boolean startOnCreation) {
        this.startRunsOnCreation = startOnCreation;
    }

    public void addRun(final CBMCTestRunWithSymbolicVars run) {
        runs.add(run);
    }

    public List<CBMCTestRunWithSymbolicVars> getRuns() {
        return runs;
    }

    public void handleDescrCodeChange() {
        for (final CBMCTestRunWithSymbolicVars r : runs) {
            r.handleDescrCodeChange();
        }
    }

    public void handlePropDescrChanged() {
        for (final CBMCTestRunWithSymbolicVars r : runs) {
            r.handlePropDescrChanged();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuidString) {
        this.uuid = uuidString;
    }

    public void deleteRun(final CBMCTestRunWithSymbolicVars run) {
        runs.remove(run);
    }
}
