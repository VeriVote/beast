package errorcompilerchecktest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.DeepErrorChecker;
import edu.pse.beast.codearea.errorhandling.CodeError;

public final class CompilerTestClass {
    private CompilerTestClass() {
        ArrayList<String> list = new ArrayList<String>();

        // String location = "/Beast/core/c_tempfiles/qbg5bq3g53ndi52q7oc.c";
        // InputStream in = getClass().getClassLoader().getResourceAsStream(location);
        // List<String> toTest = null;

        List<CodeError> toTest = DeepErrorChecker.checkCodeForErrors(list, 0);

        for (Iterator<CodeError> iterator = toTest.iterator(); iterator.hasNext();) {
            CodeError codeError = (CodeError) iterator.next();
            System.out.println(
                    "line: " + codeError.getLine()
                    + " | posinline " + codeError.getPosInLine()
                    + " | varname: " + codeError.getExtraInfo("varname")
                    + " | message: " + codeError.getExtraInfo("msg")
                    + " | errortype: " + codeError.getId());
        }
    }

    public static void main(String[] args) {
        new CompilerTestClass();
    }
}