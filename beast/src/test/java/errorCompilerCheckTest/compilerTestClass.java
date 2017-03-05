package errorCompilerCheckTest;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.DeepErrorChecker;
import edu.pse.beast.codearea.ErrorHandling.CodeError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
