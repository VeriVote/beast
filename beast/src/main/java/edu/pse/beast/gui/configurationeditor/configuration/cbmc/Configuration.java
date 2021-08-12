package edu.pse.beast.gui.configurationeditor.configuration.cbmc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.runner.propertycheck.run.PropertyCheckRun;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class Configuration {
    private int minCands;
    private int minVoters;
    private int minSeats;

    private int maxCands;
    private int maxVoters;
    private int maxSeats;

    private String name;

    private CElectionDescription description;
    private PropertyDescription propertyDescription;

    private boolean startRunsOnCreation;

    private List<PropertyCheckRun> runs = new ArrayList<PropertyCheckRun>();

    private String uuid;

    public Configuration() {
        uuid = UUID.randomUUID().toString();
    }

    public final void addRuns(final List<PropertyCheckRun> runList) {
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

    public final PropertyDescription getPropDescr() {
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

    public final void setPropDescr(final PropertyDescription propDescr) {
        this.propertyDescription = propDescr;
    }

    public final boolean getStartRunsOnCreation() {
        return startRunsOnCreation;
    }

    public final void setStartRunsOnCreation(final boolean startOnCreation) {
        this.startRunsOnCreation = startOnCreation;
    }

    public final void addRun(final PropertyCheckRun run) {
        runs.add(run);
    }

    public final List<PropertyCheckRun> getRuns() {
        return runs;
    }

    public final void handleDescrCodeChange() {
        for (final PropertyCheckRun r : runs) {
            r.handleDescrCodeChange();
        }
    }

    public final void handlePropDescrChanged() {
        for (final PropertyCheckRun r : runs) {
            r.handlePropDescrChanged();
        }
    }

    public final String getUuid() {
        return uuid;
    }

    public final void setUuid(final String uuidString) {
        this.uuid = uuidString;
    }

    public final void deleteRun(final PropertyCheckRun run) {
        runs.remove(run);
    }
}
