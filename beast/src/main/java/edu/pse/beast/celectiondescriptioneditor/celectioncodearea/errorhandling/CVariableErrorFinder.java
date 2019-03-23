package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * This class uses an external compiler (either gcc on linux or cl on windows)
 * to find errors in the c code
 * 
 * @author Holger-Desktop
 */
public class CVariableErrorFinder {
    
    public static List<CodeError> findErrors(List<String> code) {
        ArrayList<String> seperated = new ArrayList<>();
        seperated.add("#ifndef " + UnifiedNameContainer.getVoter());
        seperated.add("#define " + UnifiedNameContainer.getVoter() + " 1");
        seperated.add("#endif");
        
        seperated.add("#ifndef " + UnifiedNameContainer.getCandidate());
        seperated.add("#define " + UnifiedNameContainer.getCandidate() + " 1");
        seperated.add("#endif");
        
        seperated.add("#ifndef " + UnifiedNameContainer.getSeats());
        seperated.add("#define " + UnifiedNameContainer.getSeats() + " 1");
        seperated.add("#endif");

        //because we want to reserver the function name "verify" we define it here
        seperated.add("void verify() {}");
        
        
        
        //WORKAROUND: Will change, if I think of a more elegant solution (if there is one) (look at issue 49 on github)
        //Maybe it is possible to include all CBMC functions, but I will have to see. At least I can extract it to a file 
        //which would make updating easier.
        
        seperated.add("void __CPROVER_assert(int x, int y) {}");
        seperated.add("void __CPROVER_assume(int x) {}");
        
        seperated.add("struct result { unsigned int arr[" + UnifiedNameContainer.getSeats() + "]; };");
        seperated.add("struct stack_result { unsigned int arr[" + UnifiedNameContainer.getCandidate() + "]; };");
        
        seperated.add(UnifiedNameContainer.getStruct_candidateList() + " { unsigned int "
				+ UnifiedNameContainer.getResult_arr_name() + "[" + UnifiedNameContainer.getCandidate() + "]; };"); // add a

        
        seperated.add("void assume(int x) {}");
        seperated.add("void assert(int x) {}");
        seperated.add("void assert2(int x, int y) {}");
        
        seperated.add("int nondet_int() {return 0;}");
        seperated.add("unsigned int nondet_uint() {return 0;}");
        seperated.add("unsigned char nondet_uchar() {return 0;}");
        seperated.add("char nondet_char() {return 0;}");
        
        //WORKAROUND ende
        
        seperated.add("int main() {");
        seperated.add("}");
        
        int lineOffset = seperated.size() + 1;
        
        seperated.addAll(code);
        ArrayList<CodeError> found = new ArrayList<>(DeepErrorChecker.checkCodeForErrors(seperated, lineOffset));
        return found;
    }
}
