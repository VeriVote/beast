package edu.pse.beast.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadedBufferedReader extends Thread{

	private BufferedReader reader;
	private List<String> readLines;
	private boolean isInterrupted = false;	
	private CountDownLatch latch;
	
	/**
	 * Class for reading a stream from the program
	 * @param reader The reader to be read from.
	 * @param readLines The list where the read lines should be added
	 */
	public ThreadedBufferedReader(BufferedReader reader, List<String> readLines, CountDownLatch latch) {
		this.reader = reader;
		this.readLines = readLines;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		String line = null;
		try {
			line = reader.readLine();
			while (line != null && !isInterrupted) {
				readLines.add(line);
				line = reader.readLine();
				System.out.println("ASDFASDF" + line);
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
