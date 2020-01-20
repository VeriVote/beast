package edu.pse.beast.types.cbmctypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.XMLtoolbox;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

/**
 * The Class CBMCxmlParser.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCxmlParser {

    /** The Constant BASE_NAME. */
    private static final String BASE_NAME = "base_name";

    /** The Constant DISPLAY_NAME. */
    private static final String DISPLAY_NAME = "display_name";

    /** The Constant MAIN_METHOD_ID. */
    private static final String MAIN_METHOD_ID = "main::";

    /** The Constant VALUE_EXPRESSION. */
    private static final String VALUE_EXPRESSION = "value_expression";

    /** The Constant ASSIGNMENT. */
    private static final String ASSIGNMENT = "assignment";

    /**
     * The constructor.
     */
    private CBMCxmlParser() { }

    /**
     * Extract variables.
     *
     * @param document
     *            the document
     * @param variablesToFind
     *            the variables to find
     * @return the list
     */
    public static List<Tuple<String, List<ResultValueWrapper>>>
            extractVariables(final Document document,
                             final List<String> variablesToFind) {
        List<Tuple<String, List<ResultValueWrapper>>> toReturn =
                new ArrayList<Tuple<String, List<ResultValueWrapper>>>();

        for (int i = 0; i < variablesToFind.size(); i++) {
            toReturn.add(
                    new Tuple<String, List<ResultValueWrapper>>(
                            variablesToFind.get(i),
                            new ArrayList<ResultValueWrapper>()
                    )
            );
        }

        // TODO possible optimization: traverse the document backwards, and stop
        // when
        // the "last" occurrence of a variable is found. If you are given all
        // the
        // trailing numbers behind the variables, you could even stop
        // prematurely.

        XMLtoolbox.clean(document);

        NodeList assignments =
                document.getDocumentElement().getElementsByTagName(ASSIGNMENT);

        for (int i = 0; i < assignments.getLength(); i++) {
            Node node = assignments.item(i);
            // we extract the name of the variable which a value is described
            // upon
            String identifier =
                    node.getAttributes().getNamedItem(DISPLAY_NAME).getNodeValue();
            // TODO maybe we can filter for all assignments with function:
            // "main"
            if (identifier.startsWith(MAIN_METHOD_ID)) {
                // we only care about variables from the main method
                String name =
                        node.getAttributes().getNamedItem(BASE_NAME).getNodeValue();

                for (int index = 0; index < variablesToFind.size(); index++) {
                    String variableNameMatcher = variablesToFind.get(index);
                    if (name.matches(variableNameMatcher)) {
                        Element element = null;
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            element = (Element) node;
                        } else {
                            System.err.println("problem parsing the"
                                                + " node to an element");
                            continue;
                        }
                        NodeList valueNodeList =
                                element.getElementsByTagName(VALUE_EXPRESSION);
                        if (valueNodeList.getLength() != 1) {
                            System.err.println("Empty value expression found");
                            continue;
                        }
                        int mainIndex = getMainIndex(name);
                        expandSparseList(toReturn.get(index).second(),
                                         mainIndex);
                        ResultValueWrapper toAdd =
                                new CBMCResultValueWrapper(
                                        mainIndex, name,
                                        valueNodeList.item(0).getFirstChild()
                                );
                        toReturn.get(index).second().set(mainIndex, toAdd);
                    }
                }
            }
        }
        // remove null elements from all lists
        for (Iterator<Tuple<String, List<ResultValueWrapper>>>
                    iterator = toReturn.iterator();
                iterator.hasNext();) {
            final Tuple<String, List<ResultValueWrapper>> toClean = iterator.next();
            cleanSparseList(toClean.second());
        }
        return toReturn;
    }

    /**
     * Gets the main index.
     *
     * @param variableWithIndex
     *            the variable with index
     * @return the main index
     */
    private static int getMainIndex(final String variableWithIndex) {
        try {
            String number = variableWithIndex.replaceAll("[^\\d.]", "");
            return Integer.parseInt(number);
        } catch (PatternSyntaxException | NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Expand sparse list.
     *
     * @param list
     *            the list
     * @param position
     *            the position
     */
    private static void expandSparseList(final List<?> list,
                                         final int position) {
        for (int i = list.size(); i < (position + 1); i++) {
            list.add(null);
        }
    }

    /**
     * Removes all null objects from a list. The previous order of the elements
     * will not be changed.
     *
     * @param toClean
     *            the to clean
     */
    private static void cleanSparseList(final List<?> toClean) {
        for (Iterator<?> iterator = toClean.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            if (object == null) {
                iterator.remove();
            }
        }
    }
}
