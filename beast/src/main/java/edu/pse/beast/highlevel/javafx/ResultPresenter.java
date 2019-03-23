package edu.pse.beast.highlevel.javafx;

import java.util.List;

import edu.pse.beast.propertychecker.Result;

public class ResultPresenter implements Runnable {

    private Result result;

    private boolean running;

    private Thread currentThread = null;

    private static final long SLEEPTIME = 100;

    public void setResult(Result result) {

        if (currentThread != null) {
            this.running = false;
            currentThread.interrupt();
        }
        this.result = result;

        this.running = false;

        this.running = true;

        new Thread(this).start();

    }

    private void tmp() {

        if (result != null && result.isFinished()) {
            if (!result.isValid()) {
            } else {
                if (result.isSuccess()) {
                    GUIController.getController().getResultField().setText("ASSERTION HOLDS");
                    GUIController.getController().getMainTabPane().getSelectionModel()
                            .select(GUIController.getController().getResultTab());
                } else {
                    // ResultPresenter.presentFailureExample(result);

                    // GUIController.getController().getResultField().setText(String.join("\n",
                    // presentResult()));
                    GUIController.getController().getMainTabPane().getSelectionModel()
                            .select(GUIController.getController().getResultTab());
                }
            }
        }
    }

    @Override
    public void run() {

        if (result == null) {
            return;
        } else {

            while (running) {

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
                        break;
                    }
                case previous:
                    if (result.getStatusStrings() != null) {
                        output = output + " LAST RESULTS :\n_________________\n";

                        List<String> lastResults = result.getStatusStrings();

                        for (int i = 0; i < lastResults.size(); i++) {
                            output = output + i + ": " + lastResults.get(i) + "\n_________________\n";
                        }
                    }
                    break;

                case result:

                    if (result.isFinished()) {
                        System.out.println("fix printing results");
                    } else {
                        output = "result not finished yet!";
                    }

                    break;

                default:
                    break;
                }

                GUIController.getController().getResultField().setText(output);

                if (result.isFinished()) {
                    running = false;
                }

                if (running) {
                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

}
