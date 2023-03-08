package edu.kit.kastel.formal.beast.api;

import java.io.File;
import java.util.List;

import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.output.JSONMessage;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface PropertyCheckCallback {

    default void onCheckStarted() {
    }

    default void onError() {
    }

    default void onCheckFinished() {
    }

    default void onCheckStopped() {
    }

    default void onPropertyCheckAddedToQueue(CElectionDescription description,
                                             PropertyDescription propertyDescr,
                                             BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckStart(CElectionDescription description,
                                      PropertyDescription propertyDescr,
                                      BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckRawOutput(String sessionUUID,
                                          CElectionDescription description,
                                          PropertyDescription propertyDescr,
                                          BoundValues bounds, String uuid, String output) {
    }

    default void onCheckFileCreated(String sessionUUID,
                                    CElectionDescription description,
                                    PropertyDescription propertyDescr,
                                    File cbmcFile) {
    }

    default void onCompleteCommand(CElectionDescription description,
                                   PropertyDescription propertyDescr,
                                   BoundValues bounds, String uuid,
                                   String completeCommand) {
    }

    default void onException(Exception ex) throws Exception {
    }

    default void onNewCBMCMessage(JSONMessage msg) {
    }

    default void onPropertyCheckFinished(CElectionDescription description,
                                         PropertyDescription propertyDescr,
                                         BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckRawOutputComplete(CElectionDescription description,
                                                  PropertyDescription propertyDescr,
                                                  BoundValues bounds,
                                                  String uuid, List<String> cbmcOutput) {
    }

    default void onPropertyCheckStopped(CElectionDescription descr,
                                        PropertyDescription propertyDescr,
                                        BoundValues bounds, String uuid) {
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    class BoundValues {
        public final int candidates;
        public final int seats;
        public final int voters;

        public BoundValues(final int candidateAmount,
                           final int seatAmount,
                           final int voteAmount) {
            this.candidates = candidateAmount;
            this.seats = seatAmount;
            this.voters = voteAmount;
        }
    }
}
