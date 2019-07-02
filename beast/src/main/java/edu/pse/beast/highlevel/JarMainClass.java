package edu.pse.beast.highlevel;

/**
 * other main class, used to launch javafx from a jar reference:
 * https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing/52654791#52654791
 * 
 * @author lukas
 *
 */
public class JarMainClass {
	public static void main(String[] args) {
		MainApplicationClass.main(args);
	}
}
