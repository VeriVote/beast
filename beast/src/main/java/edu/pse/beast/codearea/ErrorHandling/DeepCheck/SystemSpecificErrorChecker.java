package edu.pse.beast.codearea.ErrorHandling.DeepCheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

public abstract class SystemSpecificErrorChecker {
    
    private final String pathToTempFolder = "/core/c_tempfiles/";
    
    public List<CodeError> checkCodeForErrors(List<String> toCheck) {
        
        List<String> result = new ArrayList<String>();
        
        List<String> errors = new ArrayList<String>();
        
        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
        
        absolutePath = absolutePath.replaceAll("%20", " ");
        
        String pathToNewFile = absolutePath + FileLoader.getNewUniqueName(absolutePath);
        
        File cFile = new File(pathToNewFile + ".c");

        File objFile = new File(pathToNewFile + ".obj");
        
        FileSaver.writeStringLinesToFile(toCheck, cFile);
        
        
        Process process = checkCodeFileForErrors(cFile);
        
        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);
            ThreadedBufferedReader outReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getInputStream())), result, latch);
            ThreadedBufferedReader errReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getErrorStream())), errors, latch);

            
            //wait for the process;
            try {
                process.waitFor();
                latch.await();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            //parse the errors out of the returned lists
            List<CodeError> toReturn = parseError(result, errors);
            
            //deletes the temporary file, so it doesn't clog up the filesystem
            cFile.delete();            
            objFile.delete();
            
            return toReturn;
        } else {
            ErrorLogger.log("Process couldn't be started");
            return null;
        }
    }
    
    
    protected abstract Process checkCodeFileForErrors(File toChecky);
    
    protected abstract List<CodeError> parseError(List<String> result, List<String> errors);
}
