package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface CBMCTestCallback {

    default void onTestStarted() {
    }

    default void onError() {
    }

    default void onTestFinished() {
    }

    default void onTestStopped() {
    }

    default void onPropertyTestAddedToQueue(CElectionDescription description,
                                            PreAndPostConditionsDescription propertyDescr,
                                            BoundValues bounds, String uuid) {
    }

    default void onPropertyTestStart(CElectionDescription description,
                                     PreAndPostConditionsDescription propertyDescr,
                                     BoundValues bounds, String uuid) {
    }

    default void onPropertyTestRawOutput(String sessionUUID,
                                         CElectionDescription description,
                                         PreAndPostConditionsDescription propertyDescr,
                                         BoundValues bounds, String uuid, String output) {
    }

    default void onTestFileCreated(String sessionUUID,
                                   CElectionDescription description,
                                   PreAndPostConditionsDescription propertyDescr,
                                   File cbmcFile) {
    }

    default void onCompleteCommand(CElectionDescription description,
                                   PreAndPostConditionsDescription propertyDescr,
                                   BoundValues bounds, String uuid,
                                   String completeCommand) {
    }

    default void onException(Exception ex) throws Exception {
    }

    default void onNewCBMCMessage(CBMCJsonMessage msg) {
    }

    default void onPropertyTestFinished(CElectionDescription description,
                                        PreAndPostConditionsDescription propertyDescr,
                                        BoundValues bounds, String uuid) {
    }

    default void onPropertyTestRawOutputComplete(CElectionDescription description,
                                                 PreAndPostConditionsDescription propertyDescr,
                                                 BoundValues bounds,
                                                 String uuid, List<String> cbmcOutput) {
    }

    default void onPropertyTestStopped(CElectionDescription descr,
                                       PreAndPostConditionsDescription propertyDescr,
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
