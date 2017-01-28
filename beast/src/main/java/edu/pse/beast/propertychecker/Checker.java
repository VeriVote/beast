package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Checker implements Runnable {

    private final String arguments;
    private final File toCheck;
    private final CheckerFactory parent;
    
    private final List<String> results = new ArrayList<String>();
    private final List<String> errors = new ArrayList<String>();
    
  //  private Process process;
    private boolean finished = false;
    private boolean success = false;
    private boolean interrupted = false;
    
    public Checker(String arguments, File toCheck, CheckerFactory parent) {
        this.arguments = arguments;
        this.toCheck = toCheck;
        this.parent = parent;
        
        new Thread(this).start();
        
    }
    
    @Override
    public void run() {
        Process process = createProcess(toCheck, arguments);
        
        polling: while (!interrupted) {
            if (!process.isAlive() && !interrupted) {
                finished = true;
                //TODO stopped here for today
                break polling;
            }
            
        }
        
    }

    public void interruptChecking() {
        interrupted = false;
    }
    
    public List<String> getResultList() {
        return results;
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
        
    }
    
    protected abstract String sanitizeArguments(String toSanitize);
    
    protected abstract Process createProcess(File toCheck, String arguments);

    protected abstract void stopProcess(); 
}
