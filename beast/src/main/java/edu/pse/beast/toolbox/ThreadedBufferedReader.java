package edu.pse.beast.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * this class is a BufferedReader that runs in a seperate Thread.
 * All read lines are stored in a passed List
 * It either stops if the stream ends, or it gets stopped from the outside.
 * When it finished normally it also counts down a latch to notify other waiting Threads
 * @author Lukas
 *
 */
public class ThreadedBufferedReader implements Runnable {

	private BufferedReader reader;
	private List<String> readLines;
	private volatile boolean isInterrupted = false;
	private CountDownLatch latch;

	/**
	 * Class for reading a stream from the program
	 * 
	 * @param reader
	 *            The reader to be read from.
	 * @param readLines
	 *            The list where the read lines should be added
	 */
	public ThreadedBufferedReader(BufferedReader reader, List<String> readLines, CountDownLatch latch) {
		this.reader = reader;
		this.readLines = readLines;
		this.latch = latch;

		new Thread(this, "ReaderThread").start();
	}

	/**
	 * starts the Thread. The reader reads each line, adds it to 
	 * the list. In the end it notifies a latch, that it is finished
	 */
	@Override
	public void run() {
		String line = null;

		try {
			line = reader.readLine();
			while (line != null && !isInterrupted) {
				readLines.add(line);
				line = reader.readLine();
				
				System.out.println(line);
				
			}
		} catch (IOException e) {
			ErrorLogger.log("Reader was closed unexpectedly");
		}
		latch.countDown();
	}

	/**
	 * interrupts the reader if it is needed
	 */
	public void stopReading() {
		isInterrupted = true;
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
