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

    public final void addRuns(final List<CBMCTestRunWithSymbolicVars> runList) {
        this.runs.addAll(runList);
    }

    public final int getMinCands() {
        return minCands;
    }

    public final int getMinVoters() {
        return minVoters;
    }

    public final int getMinSeats() {
        return minSeats;
    }

    public final int getMaxCands() {
        return maxCands;
    }

    public final int getMaxVoters() {
        return maxVoters;
    }

    public final int getMaxSeats() {
        return maxSeats;
    }

    public final String getName() {
        return name;
    }

    public final CElectionDescription getDescr() {
        return description;
    }

    public final PreAndPostConditionsDescription getPropDescr() {
        return propertyDescription;
    }

    public final void setMinCands(final int minCandidateAmount) {
        this.minCands = minCandidateAmount;
        if (maxCands < minCandidateAmount) {
            maxCands = minCandidateAmount;
        }
    }

    public final void setMaxCands(final int maxCandidateAmount) {
        this.maxCands = maxCandidateAmount;
        if (maxCandidateAmount < minCands) {
            minCands = maxCandidateAmount;
        }
    }

    public final void setMinVoters(final int minVoterAmount) {
        this.minVoters = minVoterAmount;
        if (maxVoters < minVoterAmount) {
            maxVoters = minVoterAmount;
        }
    }

    public final void setMaxVoters(final int maxVoterAmount) {
        this.maxVoters = maxVoterAmount;
        if (maxVoterAmount < minVoters) {
            minVoters = maxVoterAmount;
        }
    }

    public final void setMinSeats(final int minSeatAmount) {
        this.minSeats = minSeatAmount;
        if (maxSeats < minSeatAmount) {
            maxSeats = minSeatAmount;
        }
    }

    public final void setMaxSeats(final int maxSeatAmount) {
        this.maxSeats = maxSeatAmount;
        if (maxSeatAmount < minSeats) {
            minSeats = maxSeatAmount;
        }
    }

    public final void setName(final String nameString) {
        this.name = nameString;
    }

    public final void setDescr(final CElectionDescription descr) {
        this.description = descr;
    }

    public final void setPropDescr(final PreAndPostConditionsDescription propDescr) {
        this.propertyDescription = propDescr;
    }

    public final boolean getStartRunsOnCreation() {
        return startRunsOnCreation;
    }

    public final void setStartRunsOnCreation(final boolean startOnCreation) {
        this.startRunsOnCreation = startOnCreation;
    }

    public final void addRun(final CBMCTestRunWithSymbolicVars run) {
        runs.add(run);
    }

    public final List<CBMCTestRunWithSymbolicVars> getRuns() {
        return runs;
    }

    public final void handleDescrCodeChange() {
        for (final CBMCTestRunWithSymbolicVars r : runs) {
            r.handleDescrCodeChange();
        }
    }

    public final void handlePropDescrChanged() {
        for (final CBMCTestRunWithSymbolicVars r : runs) {
            r.handlePropDescrChanged();
        }
    }

    public final String getUuid() {
        return uuid;
    }

    public final void setUuid(final String uuidString) {
        this.uuid = uuidString;
    }

    public final void deleteRun(final CBMCTestRunWithSymbolicVars run) {
        runs.remove(run);
    }
}
