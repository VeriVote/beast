package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;

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
                                            int s, int c, int v, String uuid) {
    }

    default void onPropertyTestStart(CElectionDescription description,
                                     PreAndPostConditionsDescription propertyDescr,
                                     int s, int c, int v, String uuid) {
    }

    default void onPropertyTestRawOutput(String sessionUUID,
                                         CElectionDescription description,
                                         PreAndPostConditionsDescription propertyDescr,
                                         int s, int c, int v, String uuid, String output) {
    }

    default void onTestFileCreated(String sessionUUID,
                                   CElectionDescription description,
                                   PreAndPostConditionsDescription propertyDescr,
                                   File cbmcFile) {
    }

    default void onCompleteCommand(CElectionDescription description,
                                   PreAndPostConditionsDescription propertyDescr,
                                   int v, int c, int s, String uuid,
                                   String completeCommand) {
    }

    default void onException(Exception ex) throws Exception {
    }

    default void onNewCBMCMessage(CBMCJsonMessage msg) {
    }

    default void onPropertyTestFinished(CElectionDescription description,
                                        PreAndPostConditionsDescription propertyDescr,
                                        int s, int c, int v, String uuid) {
    }

    default void onPropertyTestRawOutputComplete(CElectionDescription description,
                                                 PreAndPostConditionsDescription propertyDescr,
                                                 int s, int c, int v,
                                                 String uuid, List<String> cbmcOutput) {
    }

    default void onPropertyTestStopped(CElectionDescription descr,
                                       PreAndPostConditionsDescription propertyDescr,
                                       int s, int c, int v, String uuid) {
    }
}
