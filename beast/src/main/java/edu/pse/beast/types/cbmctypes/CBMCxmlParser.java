package edu.pse.beast.types.cbmctypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.XMLtoolbox;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

public class CBMCxmlParser {

	private static final String baseName = "base_name";
	private static final String displayName = "display_name";
	private static final String mainMethodID = "main::";
	private static final String valueExpression = "value_expression";
	private static final String assignment = "assignment";

	public static List<Tuple<String, List<ResultValueWrapper>>> extractVariables(Document document, List<String> variablesToFind) {
		List<Tuple<String, List<ResultValueWrapper>>> toReturn = new ArrayList<Tuple<String, List<ResultValueWrapper>>>();

		for (int i = 0; i < variablesToFind.size(); i++) {
			toReturn.add(new Tuple<String, List<ResultValueWrapper>>(variablesToFind.get(i), new ArrayList<ResultValueWrapper>()));
		}

		// TODO possible optimization: traverse the document backwards, and stop when
		// the "last" occurance of a variable is found. If you are given all the
		// trailing numbers behind the variables, you could even stop prematurely.

		XMLtoolbox.clean(document);

		NodeList assignments = document.getDocumentElement().getElementsByTagName(assignment);

		for (int i = 0; i < assignments.getLength(); i++) {

			Node node = assignments.item(i);

			// we extract the name of the variable which a value is described upon
			String identifier = node.getAttributes().getNamedItem(displayName).getNodeValue();
				//TODO maybe we can filter for all assignments with function: "main"
			if (identifier.startsWith(mainMethodID)) { // we only care for variables from the main method
				String name = node.getAttributes().getNamedItem(baseName).getNodeValue();

				for (int index = 0; index < variablesToFind.size(); index++) {
					String variableNameMatcher = variablesToFind.get(index);

					if (name.matches(variableNameMatcher)) {

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

						int mainIndex = getMainIndex(name);
						
						expandSparseList(toReturn.get(index).y, mainIndex);
						
						ResultValueWrapper toAdd = new CBMCResultValueWrapper(mainIndex, name,
								valueNodeList.item(0).getFirstChild());
						
						toReturn.get(index).y.set(mainIndex, toAdd);
					}
				}
			}
		}
		
		//remove null elements from all lists
		for (Iterator<Tuple<String, List<ResultValueWrapper>>> iterator = toReturn.iterator(); iterator.hasNext();) {
			Tuple<String, List<ResultValueWrapper>> toClean = iterator.next();
			
			cleanSparseList(toClean.y);
			
		}
		
		return toReturn;
	}

	private static int getMainIndex(String variableWithIndex) {
		String number = variableWithIndex.replaceAll("[^\\d.]", "");

		return Integer.parseInt(number);
	}

	private static void expandSparseList(List<?> list, int position) {
		for (int i = list.size(); i < (position + 1); i++) {
			list.add(null);
		}
	}
	
	/**
	 * removes all null objects from a list. The previous order of the elements will not be changed
	 * @param toClean
	 */
	private static void cleanSparseList(List<?> toClean) {
		for (Iterator iterator = toClean.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object == null) {
				iterator.remove();
			}
		}
	}
}