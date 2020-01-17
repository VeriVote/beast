package edu.pse.beast.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * this class is a BufferedReader that runs in a separate thread. All read lines
 * are stored in a passed list. It either stops if the stream ends, or it gets
 * stopped from the outside. When it finishes normally, it also counts down a
 * latch to notify other waiting threads.
 *
 * @author Lukas Stapelbroek
 *
 */
public class ThreadedBufferedReader implements Runnable {

    /** The Constant CHECKING_INTERVAL. */
    private static final int CHECKING_INTERVAL = 5000;

    /** The Constant WARNING_INTERVAL. */
    private static final int WARNING_INTERVAL = 1000;

    /** The Constant UNWIND_PREFIX. */
    private static final String UNWIND_PREFIX = "  <text>Unwinding loop";

    /** The reader. */
    private final BufferedReader reader;

    /** The read lines. */
    private final List<String> readLines;

    /** The is interrupted. */
    private volatile boolean isInterrupted = false;

    /** The latch. */
    private final CountDownLatch latch;

    /** The check for unwind. */
    private final boolean checkForUnwind;

    /**
     * Class for reading a stream from the program.
     *
     * @param bufferedReader
     *            the reader to be read from.
     * @param rdLines
     *            the list where the read lines should be added
     * @param cntDwnLatch
     *            the latch to synchronize on
     * @param checkUnwind
     *            whether we care for the amount of loop unwindings
     */
    public ThreadedBufferedReader(final BufferedReader bufferedReader,
                                  final List<String> rdLines,
                                  final CountDownLatch cntDwnLatch,
                                  final boolean checkUnwind) {
        this.reader = bufferedReader;
        this.readLines = rdLines;
        this.latch = cntDwnLatch;
        this.checkForUnwind = checkUnwind;
        new Thread(this, "ReaderThread").start();
    }

    // Starts the thread. The reader reads each line, adds it to the list. At
    // the
    // end it notifies a latch, that it is finished.
    @Override
    public void run() {
        int curr = 0;
        String line = null;

        try {
            line = reader.readLine();
            while (line != null && !isInterrupted) {
                readLines.add(line);
                if (checkForUnwind && (curr > CHECKING_INTERVAL)) {
                    if (line.startsWith(UNWIND_PREFIX)) {
                        // we are still unwinding, so we check the line now
                        // to see how much we are unwinding
                        try {
                            int iteration =
                                    Integer.parseInt(
                                            line.split("iteration")[1].split("file")[0]
                                                    .replace(" ", "")
                                    );
                            if (iteration > WARNING_INTERVAL) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        ErrorForUserDisplayer
                                            .displayError("A loop in your C"
                                                        + " program is still"
                                                        + " (more than a"
                                                        + " thousand times)"
                                                        + " getting unrolled."
                                                        + " Maybe you want"
                                                        + " to stop the"
                                                        + " checking manually"
                                                        + " and add enter an"
                                                        + " upper bound for"
                                                        + " how many unrolls"
                                                        + " are done."
                                                        + "(Parameters->"
                                                        + "Solveroptions->"
                                                        + "max loop unwinds");
                                    }
                                }.start();
                            }
                            // reset curr;
                            curr = 0;
                        } catch (NumberFormatException e) {
                            // do nothing
                        }
                    }
                }
                line = reader.readLine();
                curr++;
            }
        } catch (IOException e) {
            ErrorLogger.log("Reader was closed unexpectedly");
        }
        latch.countDown();
    }

    /**
     * Interrupts the reader if it is needed.
     */
    public void stopReading() {
        isInterrupted = true;
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finish.
     */
    public void finish() {
        // nothing, just to get the warning away
    }
}
