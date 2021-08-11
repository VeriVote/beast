package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.api.runner.propertycheck.output.JSONMessage;

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
                                             PreAndPostConditions propertyDescr,
                                             BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckStart(CElectionDescription description,
                                      PreAndPostConditions propertyDescr,
                                      BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckRawOutput(String sessionUUID,
                                          CElectionDescription description,
                                          PreAndPostConditions propertyDescr,
                                          BoundValues bounds, String uuid, String output) {
    }

    default void onCheckFileCreated(String sessionUUID,
                                    CElectionDescription description,
                                    PreAndPostConditions propertyDescr,
                                    File cbmcFile) {
    }

    default void onCompleteCommand(CElectionDescription description,
                                   PreAndPostConditions propertyDescr,
                                   BoundValues bounds, String uuid,
                                   String completeCommand) {
    }

    default void onException(Exception ex) throws Exception {
    }

    default void onNewCBMCMessage(JSONMessage msg) {
    }

    default void onPropertyCheckFinished(CElectionDescription description,
                                         PreAndPostConditions propertyDescr,
                                         BoundValues bounds, String uuid) {
    }

    default void onPropertyCheckRawOutputComplete(CElectionDescription description,
                                                  PreAndPostConditions propertyDescr,
                                                  BoundValues bounds,
                                                  String uuid, List<String> cbmcOutput) {
    }

    default void onPropertyCheckStopped(CElectionDescription descr,
                                        PreAndPostConditions propertyDescr,
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
