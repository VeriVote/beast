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
        
//        File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");
        
        File file = new File("./core/c_tempfiles/9neh46uhermi1ghlucrl.c");
        
      //  FileSaver.writeStringLinesToFile(toCheck, file);
        
        System.out.println(file.getAbsolutePath());
        
        
        Process process = checkCodeFileForErrors(file);
        
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
            
            //deletes the temporary file
     //       file.delete();
            
            return parseError(result, errors);
        } else {
            ErrorLogger.log("Process couldn't be started");
            return null;
        }
    }
    
    protected CodeError generateCodeError(int line, int posInLine, String varName, String message) {
        CodeError toReturn = new CodeError(line, posInLine, "compilererror", -1, -1, -1);
        toReturn.setExtraInfo("varname", varName);
        toReturn.setExtraInfo("msg", message);
        
        return toReturn;
    }
    
    protected abstract Process checkCodeFileForErrors(File toCheck);
    
    protected abstract List<CodeError> parseError(List<String> result, List<String> errors);
}
