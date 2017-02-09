package edu.pse.beast.toolbox;

import javax.swing.JOptionPane;

public final class ErrorForUserDisplayer {
	
	private static int currentlyDisplayedErrors = 0;
	
	private ErrorForUserDisplayer() {
		
	}
	
	public static void displayError(String message) {
		increment();
		
		ErrorLogger.log("HIER SPÄTER EIN POPUP AUFBAUEN!: " + message);
		
		decrement();
		
		while (true) {
			if (currentlyDisplayedErrors == 0) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private static synchronized void increment() {
		currentlyDisplayedErrors++;
	}
	
	private static synchronized void decrement() {
		currentlyDisplayedErrors--;
	}
}
