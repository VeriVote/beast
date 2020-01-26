package edu.pse.beast.codearea.errorhandling;

import java.util.ArrayList;

/**
 * This class contains a list of all error finders sorted by dependency. This
 * means that once the first error finder finds and error, letting the latter
 * ones search is unnecessary.
 *
 * @author Holger Klein
 */
public class ErrorFinderList {

    /** The error finder list. */
    private ArrayList<ErrorFinder> errorFinderList =
            new ArrayList<ErrorFinder>();

    /**
     * Starts asking all error finders to look for errors in the code. Once the
     * first error finder finds errors, it stops the search and returns these
     * errors.
     *
     * @return the list of errors found be the errorfinders
     */
    public ArrayList<CodeError> getErrors() {
        final ArrayList<CodeError> created = new ArrayList<CodeError>();
        for (int i = 0;
                i < errorFinderList.size() && created.size() == 0;
                ++i) {
            created.addAll(errorFinderList.get(i).getErrors());
        }
        return created;
    }

    /**
     * Adds the.
     *
     * @param errorFinder
     *            the error finder
     */
    public void add(final ErrorFinder errorFinder) {
        errorFinderList.add(errorFinder);
    }
}
