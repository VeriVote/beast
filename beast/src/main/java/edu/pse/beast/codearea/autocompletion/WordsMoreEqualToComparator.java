//package edu.pse.beast.codearea.autocompletion;
//
//import java.util.Comparator;
//
///**
// * This comparator is used by the autocompletioncontroller to sort all
// * autocompletionoptions by how similar they are to the beginning of the
// * word the user already typed
// * @author Holger-Desktop
// */
//public class WordsMoreEqualToComparator implements Comparator<AutocompletionOption>{
//    private final String compareWord;
//    public WordsMoreEqualToComparator(String compareWord) {
//        this.compareWord = compareWord;
//    }
//
//    @Override
//    public int compare(AutocompletionOption lhs, AutocompletionOption rhs) {
//        String lhsS = lhs.getSimilarString();
//        String rhsS = rhs.getSimilarString();
//        int lhsScore = similarityCode(lhsS);
//        int rhsScore = similarityCode(rhsS);
//        if(lhsScore == rhsScore) return Integer.compare(
//                Math.abs(lhsS.length() - compareWord.length()),
//                Math.abs(rhsS.length() - compareWord.length()));
//        return Integer.compare(rhsScore, lhsScore);
//    }   
//    
//    private int similarityCode(String s) {
//        int score = 0;
//        for(int i = 0; i < s.length() && i <  compareWord.length(); ++i) {
//            char comp = compareWord.charAt(i);
//            char sc = s.charAt(i);
//            if(compareWord.charAt(i) == s.charAt(i)) ++score;
//        }
//        return score;
//    }
//    
//}
