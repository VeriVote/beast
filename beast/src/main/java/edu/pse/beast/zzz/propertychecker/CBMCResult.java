package edu.pse.beast.zzz.propertychecker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.types.cbmctypes.CBMCxmlParser;
import edu.pse.beast.zzz.toolbox.Tuple;
import edu.pse.beast.zzz.toolbox.XMLtoolbox;
import edu.pse.beast.zzz.toolbox.valueContainer.ResultValueWrapper;

/**
 * The Enum CDATATYPE.
 *
 * @author Lukas Stapelbroek
 */
enum CDATATYPE {
    /** The single. */
    SINGLE,

    /** The array. */
    ARRAY,

    /** The struct. */
    STRUCT,

    /** The pointer. */
    POINTER;
}

/**
 * The Class CBMCResult.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCResult extends Result {
    /** The Constant WIN_OFFSET. */
    private static final int WIN_OFFSET = 8;

    /** This xml tag contains the result of the cbmc run. */
    private static final transient String RESULT_TAG = "cprover-status";

    /** This will be printed in the above mentioned tag, if it was successful. */
    private static final transient String SUCCESS_IDENTIFIER = "SUCCESS";

    /** This will be printed in the above mentioned tag, if it failed. */
    private static final transient String FAILURE_IDENTIFIER = "FAILURE";

    // The charset in which we want to present the data to the xml parser
    // private transient Charset charSet = new UTF_16();

    /** The char set. */
    private transient Charset charSet = Charset.forName("UTF-8");
    // TODO Check if this charset is fitting for

    /** The value cache: Both windows and linux. */
    private transient Map<String, List<ResultValueWrapper>> valueCache =
            new HashMap<String, List<ResultValueWrapper>>();

    /** The root element: The element containing all previous. */
    private transient Document rootElement;

    /**
     * Instantiates a new CBMC result.
     */
    public CBMCResult() {
        // Empty constructor
    }

    @Override
    void reset() {
        this.valueCache.clear();
        this.rootElement = null;
        this.isInitialized();
    }

    @Override
    public void setResult(final List<String> result) {
        reset();
        super.setResult(result);
        if (result != null) {
            try {
                // Get the xml list from this list of strings.
                parseResult();
            } catch (SAXException e) {
                e.printStackTrace();
                return;
            }
            // TODO Add the NameContainer here.
            final String[] arr = {"votes\\d", "elect\\d"};
            readVariableValue(Arrays.asList(arr));
            // Already read "votes" and "elect", so the access can be faster.
        }
    }
    
    /**
     * goes through @param lines and returns the first position of an xml tag.
     * We use this in parseResult 
     * @param lines list of strings
     * @return first position of an xml tag
     */
    private int findFirstXMLLine(List<String> lines) {
    	int lineNumber = 0;
    	for (String string : lines) {
			if(string.contains("<?xml")) {
				return lineNumber;
			}
			lineNumber++;
		}
    	return -1;
    }

    /**
     * Parses the result.
     *
     * @throws SAXException
     *           an SAX exception in case the xml stream cannot be parsed properly
     */
    private void parseResult() throws SAXException {
        int offset = 0; // TODO Beautify
        final OperatingSystems os = CBMCProcessFactory.determineOS();
        switch (os) {
        case Windows:
            offset = WIN_OFFSET;
            break;
        case Linux:
            offset = 0;
            break;
        default:
            break;
        }
        List<String> lines = super.getResult();
        offset = findFirstXMLLine(lines);

        final InputStream xmlStream =
                IOUtils.toInputStream(
                        String.join("", lines.subList(offset, lines.size())),
                        charSet
                        );
        final DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.rootElement = builder.parse(xmlStream);
        } catch (ParserConfigurationException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XMLtoolbox.clean(rootElement);
    }

    @Override
    public List<ResultValueWrapper> readVariableValue(final String variableMatcher) {
        final List<String> arg = new LinkedList<String>();
        arg.add(variableMatcher);
        if (valueCache.containsKey(variableMatcher)) {
            return valueCache.get(variableMatcher);
        } else {
            // Afterwards, variables matching are in the cache.
            readVariableValue(arg);
        }
        return valueCache.get(variableMatcher);
    }

    /**
     * Read variable value.
     *
     * @param variablesToFind
     *            the variables to find
     * @return the map
     */
    private Map<String, List<ResultValueWrapper>>
                readVariableValue(final List<String> variablesToFind) {
        final Map<String, List<ResultValueWrapper>> toReturn =
                new HashMap<String, List<ResultValueWrapper>>();
        if (isInitialized()) {
            for (Iterator<String> iterator = variablesToFind.iterator();
                    iterator.hasNext();) {
                final String currentMatcher = iterator.next();
                if (valueCache.containsKey(currentMatcher)) {
                    // The variable was requested before.
                    toReturn.put(currentMatcher,
                            valueCache.get(currentMatcher));
                    // We do not need to request for this value to be extracted again.
                    iterator.remove();
                }
            }
            final List<Tuple<String, List<ResultValueWrapper>>> newValues =
                    CBMCxmlParser.extractVariables(rootElement, variablesToFind);
            for (int i = 0; i < newValues.size(); i++) {
                valueCache.put(newValues.get(i).first(),
                               newValues.get(i).second());
                toReturn.put(newValues.get(i).first(),
                             newValues.get(i).second());
            }
            return toReturn;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkAssertionSuccess() {
        return checkAssertion(SUCCESS_IDENTIFIER);
    }

    @Override
    public boolean checkAssertionFailure() {
        return checkAssertion(FAILURE_IDENTIFIER);
    }

    /**
     * Checks if is initialized.
     *
     * @return true, if is initialized
     */
    private boolean isInitialized() {
        if (super.getResult() != null && super.getResult().size() > 0) {
            // the result object is set
            if (rootElement == null) {
                try {
                    parseResult();
                } catch (SAXException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        } else { // it is not set, so we return false
            return false;
        }
    }

    /**
     * Check assertion.
     *
     * @param identifier
     *            the identifier
     * @return true, if successful
     * @throws IndexOutOfBoundsException
     *            if multiple result tags are detected.
     */
    private boolean checkAssertion(final String identifier) {
        if (isInitialized()) {
            final NodeList resultElements =
                    rootElement.getElementsByTagName(RESULT_TAG);
            if (resultElements.getLength() == 0) {
                return false; // no result tag found, so it cannot hold
            } else if (resultElements.getLength() > 1) {
                throw new IndexOutOfBoundsException(
                        "Multiple Result Tags detected, this cannot happen"
                        );
            } else {
                return resultElements.item(0).getTextContent().equals(identifier);
            }
        }
        return false;
    }
}
