package edu.pse.beast.highlevel.javafx;

import java.util.List;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class ResultPresenter implements Runnable {

    private static final long SLEEP_TIME = 100;

    private Result result;

    private boolean running;

    private Thread currentThread = null;

    private InputType inType = null;
    private OutputType outType = null;

    public void setResult(Result result) {
        if (currentThread != null) {
            this.running = false;
            currentThread.interrupt();
        }
        this.inType = result.getElectionDescription().getContainer().getInputType();
        this.outType = result.getElectionDescription().getContainer().getOutputType();
        this.result = result;
        this.running = true;
        new Thread(this).start();
    }

//    private void tmp() {
//        if (result != null && result.isFinished()) {
//            if (!result.isValid()) {
//            } else {
//                if (result.isSuccess()) {
//                    GUIController.getController().getResultField().setText("ASSERTION HOLDS");
//                    GUIController.getController().getMainTabPane().getSelectionModel()
//                            .select(GUIController.getController().getResultTab());
//                } else {
//                    // ResultPresenter.presentFailureExample(result);
//
//                    // GUIController.getController().getResultField().setText(String.join("\n",
//                    // presentResult()));
//                    GUIController.getController().getMainTabPane().getSelectionModel()
//                            .select(GUIController.getController().getResultTab());
//                }
//            }
//        }
//    }

    public void present() {
        if (result == null) {
            return;
        } else {
        }
    }

    @Override
    public void run() {

        if (result == null) {
            return;
        } else {

            while (running) {
                result.getElectionDescription().getContainer().getInputType();
                result.getElectionDescription().getContainer().getOutputType();
                // do all the printing
                String output = "";
                switch (GUIController.getController().getPresentationType()) {
                case output:
                    if (result.getLastTmpResult() != null) {
                        output = String.join("\n", result.getLastTmpResult());
                    }
                    break;
                case error:
                    if (result.getLastTmpError() != null) {
                        output = String.join("\n", result.getLastTmpError());
                    }
                    break;
                case previous:
                    if (result.getStatusStrings() != null) {
                        output += " LAST RESULTS :\n_________________\n";
                        List<String> lastResults = result.getStatusStrings();
                        for (int i = 0; i < lastResults.size(); i++) {
                            output += i + ": "
                                      + lastResults.get(i)
                                      + "\n_________________\n";
                        }
                    }
                    break;
                case result:
                    if (result.isFinished()) {
                        System.out.println("fix printing results");
                    } else {
                        System.out.println("result not finished yet!");
                        output = "result not finished yet!";
                    }
                    break;
                default:
                    break;
                }
                //GUIController.getController().getResultField().setText(output);
                if (result.isFinished()) {
                    running = false;
                }
                if (running) {
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }
}