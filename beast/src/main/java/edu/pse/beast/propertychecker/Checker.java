package edu.pse.beast.propertychecker;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class Checker implements Runnable {

	/**
	 * the process that this chcker runs
	 */
	protected Process process;

	private final int voters;
	private final int candidates;
	private final int seats;
	private final String advanced;
	private final File toCheck;
	private final CheckerFactory parent;
	private final long POLLINGINTERVAL = 1000;

	private final List<String> result = new ArrayList<String>();
	private final List<String> errors = new ArrayList<String>();

	private boolean finished = false;
	private boolean success = false;
	private volatile boolean interrupted = false;

	/**
	 * This class describes the highest abstract layer of a checker. The task of
	 * a checker is to check a vote for the given properties and then return the
	 * result.
	 * 
	 * @param voters
	 *            the amount of voters to check with
	 * @param candidates
	 *            the amount of candidates to check with
	 * @param seats
	 *            the amoutn of seats to check with
	 * @param advanced
	 *            all advanced options that might be applied
	 * @param toCheck
	 *            the path to the file to check
	 * @param parent
	 *            the parent checkerfactory that has to be notified about
	 *            finished checking
	 */
	public Checker(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
		this.voters = voters;
		this.candidates = candidates;
		this.seats = seats;
		this.advanced = advanced;

		this.toCheck = toCheck;
		this.parent = parent;

		new Thread(this, "Checker").start();
	}

	@Override
	public void run() {

		process = createProcess(toCheck, voters, candidates, seats, advanced);
		if (process != null) {
			CountDownLatch latch = new CountDownLatch(2);
			ThreadedBufferedReader outReader = new ThreadedBufferedReader(
					new BufferedReader(new InputStreamReader(process.getInputStream())), result, latch, true);
			ThreadedBufferedReader errReader = new ThreadedBufferedReader(
					new BufferedReader(new InputStreamReader(process.getErrorStream())), errors, latch, false);

			polling: while (!interrupted || !finished) {
				if (!process.isAlive() && !interrupted) {
					if (process.exitValue() == 0) {
						success = true;
					} else {
						if (process.exitValue() != 10) {
							ErrorLogger.log("Process finished with exitcode: " + process.exitValue());
						}
					}
					break polling;
				} else if (interrupted) {

					stopProcess();
					outReader.stopReading();
					errReader.stopReading();
					break polling;
				}
				try {
					Thread.sleep(POLLINGINTERVAL);
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
			
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                System.out.println("r: " + string);
            }
			
			
			for (Iterator iterator = errors.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                System.out.println("e: " + string);
            }
			
			finished = true;
		} else {
			ErrorLogger.log("Process couldn't be started");
		}

		parent.notifyThatFinished(result, errors);
	}

	/**
	 * 
	 * @return the list that contains the output of the checker
	 */
	public List<String> getResultList() {
		return result;
	}

	/**
	 * 
	 * @return the list that contains the errors that were returned by the
	 *         checker
	 */
	public List<String> getErrorList() {
		return errors;
	}

	/**
	 * 
	 * @return true, if the property is satisfied, false, if it is violated
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 
	 * @return true, when the checking has finished, false else
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * tells the Checker to stop checking and also kill the sub processes
	 */
	public void stopChecking() {
		interrupted = true;
	}

	/**
	 * used to filter out arguments that can be used on some plattforms, but
	 * would crash the checker on others
	 * 
	 * @param toSanitize
	 *            the string to be sanitized
	 * @return that string, with all occurencen of all forbidden words removed
	 */
	protected abstract String sanitizeArguments(String toSanitize);

	/**
	 * creates the process, which is run in a seperate thread
	 * 
	 * @param voters
	 *            the amount of voters to check with
	 * @param candidates
	 *            the amount of candidates to check with
	 * @param seats
	 *            the amoutn of seats to check with
	 * @param advanced
	 *            all advanced options that might be applied
	 * @param toCheck
	 *            the path to the file to check
	 * @return a process that describes the system process that is currently
	 *         checking the property
	 */
	protected abstract Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced);

	/**
	 * system specific implementation to stop the process that got started
	 */
	protected abstract void stopProcess();
}
