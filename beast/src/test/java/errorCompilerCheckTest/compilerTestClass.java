package errorCompilerCheckTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.DeepErrorChecker;
import edu.pse.beast.toolbox.FileLoader;

public class compilerTestClass {
    
    public static void main(String[] args) {
        new compilerTestClass();
    }
    
    public compilerTestClass() {
        
        

        ArrayList<String> list = new ArrayList<String>();
        
   //     String location = "/Beast/core/c_tempfiles/qbg5bq3g53ndi52q7oc.c";
   //     InputStream in = getClass().getClassLoader().getResourceAsStream(location);
    //    List<String> toTest = null;
        
        
        DeepErrorChecker d = new DeepErrorChecker();
        
        List<CodeError> toTest = d.checkCodeForErrors(list);
        
        for (Iterator<CodeError> iterator = toTest.iterator(); iterator.hasNext();) {
            CodeError codeError = (CodeError) iterator.next();
            System.out.println("line: " + codeError.getLine() + " | posinline " + codeError.getPosInLine() + " | varname: " + codeError.getExtraInfo("varname") + " | message: " + codeError.getExtraInfo("msg") + " | errortype: " + codeError.getId());
        }
        
    }
}
