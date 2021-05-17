package edu.pse.beast.antlrtree;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;

public class CAntlrTreeMainClass {
	private static String code = "    unsigned int i = 0;\n"
			+ "    unsigned int j = 0;\n"
			+ "    for (i = 0; i < C; i++) {\n" 
			+ "        result[i] = 0;\n"
			+ "    }\n" 
			+ "    for (i = 0; i < V; i++) {\n"
			+ "        for (j = 0; j < C; j++) {\n"
			+ "            if (votes[i][j] < C) {\n"
			+ "                result[votes[i][j]] += (C - j) - 1;\n"
			+ "            }\n" 
			+ "        }\n" 
			+ "    }";
	
	private static String loop = 
				"for (int i = 0; i < C; i++) {\n" 
			+ 	"        result[i] = 0;\n"
			+ 	"}\n";
	
	public static void main(String[] args) {
		CLexer l = new CLexer(CharStreams.fromString(loop));
		final CommonTokenStream ts = new CommonTokenStream(l);
		CParser p = new CParser(ts);
		ParseTree tree = p.blockItemList();
		
		 //show AST in GUI
        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(
                p.getRuleNames()),tree);
        viewer.setScale(1.5); // Scale a little
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);		
	}
}
