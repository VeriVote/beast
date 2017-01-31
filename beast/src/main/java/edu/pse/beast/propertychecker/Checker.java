package edu.pse.beast.propertychecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.xalan.xsltc.dom.CachedNodeListIterator;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

public abstract class Checker implements Runnable {

	private final int voters;
	private final int candidates;
	private final int seats;
	private final String advanced;
	private final File toCheck;
	private final CheckerFactory parent;
	private final long pollInterval = 1000;

	private final List<String> result = new ArrayList<String>();
	private final List<String> errors = new ArrayList<String>();

	private boolean finished = false;
	private boolean success = false;
	private boolean interrupted = false;

	protected Process process;

	public Checker(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
	    this.voters = voters;
	    this.candidates = candidates;
	    this.seats = seats;
	    this.advanced = advanced;
	    
		this.toCheck = toCheck;
		this.parent = parent;

		new Thread(this).start();
	}

	@Override
	public void run() {

		process = createProcess(toCheck, voters, candidates, seats, advanced);

		if (process != null) {
			CountDownLatch latch = new CountDownLatch(2);
			ThreadedBufferedReader outReader = new ThreadedBufferedReader(
					new BufferedReader(new InputStreamReader(process.getInputStream())), result, latch);
			ThreadedBufferedReader errReader = new ThreadedBufferedReader(
					new BufferedReader(new InputStreamReader(process.getErrorStream())), errors, latch);

			polling: while (!interrupted || !finished) {
				if (!process.isAlive() && !interrupted) {
					if (process.exitValue() == 0) {
						success = true;
					} else {
						ErrorLogger.log("Process finished with exitcode: " + process.exitValue());
					}
					break polling;
				} else if (interrupted) {
					
					stopProcess();
					
					outReader.stopReading();
					errReader.stopReading();
					break polling;
				}
				try {
					Thread.sleep(pollInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finished = true;
		} else {
			ErrorLogger.log("Process couldn't be started");
		}

		parent.notifyThatFinished(result);
	}

	public List<String> getResultList() {
		return result;
	}

	public List<String> getErrorList() {
		return errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFinished() {
		return finished;
	}

	public void stopChecking() {
		interrupted = true;
	}

	protected abstract String sanitizeArguments(String toSanitize);

	protected abstract Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced);

	protected abstract void stopProcess();
}
