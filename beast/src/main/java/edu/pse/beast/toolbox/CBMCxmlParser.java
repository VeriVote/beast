package edu.pse.beast.toolbox;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

public class CBMCxmlParser {

    private final String baseName = "base_name";
    private final String displayName = "display_name";
    private final String mainMethodID = "main::";
    private final String valueExpression = "value_expression";
    private final String assignment = "assignment";
    
    public List<CBMCResultValueWrapper> parse(List<String> variablesToFind) {

        Document document = null;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File("C:\\Users\\lukas\\Desktop\\CBMC test\\test.xml"));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        // clean the nodes:
        XMLtoolbox.clean(document);

        // all assignment nodes:

        NodeList assignments = document.getDocumentElement().getElementsByTagName(assignment);

        for (int i = 0; i < assignments.getLength(); i++) {

            Node node = assignments.item(i);

            // we extract the name of the variable which a value is described upon
            String identifier = node.getAttributes().getNamedItem(displayName).getNodeValue();

            if (identifier.startsWith(mainMethodID)) { // we only care for variables from the main method
                String name = node.getAttributes().getNamedItem(baseName).getNodeValue();

                for (Iterator<String> iterator = variablesToFind.iterator(); iterator.hasNext();) {
                    String variableName = (String) iterator.next();

                    if (name.matches(variableName)) {

                        Element element = null;

                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            element = (Element) node;
                        } else {
                            System.err.println("problem parsing the node to an element");
                            continue;
                        }

                        NodeList valueNodeList = element.getElementsByTagName(valueExpression);

                        if (valueNodeList.getLength() != 1) {
                            System.err.println("Empty value expression found");
                            continue;
                        }

                        CBMCResultValueWrapper wrapper = new CBMCResultValueWrapper(i, variableName,
                                valueNodeList.item(0).getFirstChild());
                    }
                }
            }
        }
        return null;
    }
}
