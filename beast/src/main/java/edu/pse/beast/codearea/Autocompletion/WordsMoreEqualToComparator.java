/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import java.util.Comparator;

/**
 *
 * @author Holger-Desktop
 */
public class WordsMoreEqualToComparator implements Comparator<AutocompletionOption>{
    private String compareWord;
    public WordsMoreEqualToComparator(String compareWord) {
        this.compareWord = compareWord;
    }

    @Override
    public int compare(AutocompletionOption lhs, AutocompletionOption rhs) {
        String lhsS = lhs.getSimilarString();
        String rhsS = rhs.getSimilarString();
        int lhsScore = similarityCode(lhsS);
        int rhsScore = similarityCode(rhsS);
        return Integer.compare(rhsScore, lhsScore);
    }   
    
    private int similarityCode(String s) {
        int score = 0;
        for(int i = 0; i < s.length() && i <  compareWord.length(); ++i) {
            char comp = compareWord.charAt(i);
            char sc = s.charAt(i);
            if(compareWord.charAt(i) == s.charAt(i)) ++score;
        }
        return score;
    }
    
}
