package errorcompilerchecktest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.DeepErrorChecker;
import edu.pse.beast.codearea.errorhandling.CodeError;

/**
 * Tests for CompilerClass.
 *
 * @author Lukas Stapelbroek
 */
public final class CompilerTestClass {
    private CompilerTestClass() {
        final ArrayList<String> list = new ArrayList<String>();

        // String location = "/Beast/core/c_tempfiles/qbg5bq3g53ndi52q7oc.c";
        // InputStream in = getClass().getClassLoader().getResourceAsStream(location);
        // List<String> toTest = null;
        final List<CodeError> toTest = DeepErrorChecker.checkCodeForErrors(list, 0);

        for (Iterator<CodeError> iterator = toTest.iterator(); iterator.hasNext();) {
            final CodeError codeError = (CodeError) iterator.next();
            System.out.println(
                    "line: " + codeError.getLine()
                    + " | posinline " + codeError.getPosInLine()
                    + " | varname: " + codeError.getExtraInfo("varname")
                    + " | message: " + codeError.getExtraInfo("msg")
                    + " | errortype: " + codeError.getId());
        }
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        new CompilerTestClass();
    }
}
