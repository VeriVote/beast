//package edu.pse.beast.celectiondescriptioneditor.celectioncodearea;
//
//import java.awt.Color;
//import java.util.ArrayList;
//
//import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CAntlrHandler;
//import edu.pse.beast.codearea.syntaxhighlighting.RegexAndColor;
//import edu.pse.beast.codearea.syntaxhighlighting.SyntaxHL;
//
///**
// * Sets the regular expressions to be highlighted by a syntaxhighlighter for the c
// * programming language.
// * @author Holger Klein
// */
//public class CSyntaxHl {
//
//    private final CAntlrHandler antlrHandler;
//    private final SyntaxHL syntaxHL;
//
//    /**
//     *  constructor
//     * @param antlrHandler the antlr handler
//     * @param syntaxHL the syntax highlighter
//     */
//    public CSyntaxHl(CAntlrHandler antlrHandler, SyntaxHL syntaxHL) {
//        this.antlrHandler = antlrHandler;
//        this.syntaxHL = syntaxHL;
//
//        ArrayList<RegexAndColor> regexAndColorList = new ArrayList<>();
//        for (String s : antlrHandler.getTypeLiterals()) {
//            String regexWithWhiteSpace = "(\\(|\\s|\\A)" + s + "(\\s|\\Z|\\))";
//            regexAndColorList.add(new RegexAndColor(regexWithWhiteSpace,
//                                                    new Color(75,0,130).brighter())); // BRIGHT INDIGO
//        }
//        for (String s : antlrHandler.getControllLiterals()) {
//            String regexWithWhiteSpace = "(\\(|\\s|\\A)" + s + "(\\s|\\Z|\\))";
//            regexAndColorList.add(new RegexAndColor(regexWithWhiteSpace, Color.BLUE));
//        }
//        regexAndColorList.add(new RegexAndColor(antlrHandler.getStringRegex(), Color.RED));
//        regexAndColorList.add(new RegexAndColor(antlrHandler.getCommentRegex(), Color.GRAY));
//
//        syntaxHL.updateFilter(regexAndColorList);
//    }
//}
