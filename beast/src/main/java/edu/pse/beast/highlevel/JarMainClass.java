package edu.pse.beast.highlevel;

/**
 * Other main class, used to launch javafx from a jar reference. See the
 * following link: https://stackoverflow.com/questions/52653836
 * /maven-shade-javafx-runtime-components-are-missing/52654791#52654791
 *
 * @author Lukas Stapelbroek
 *
 */
public class JarMainClass {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        MainApplicationClass.main(args);
    }
}
