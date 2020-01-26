package edu.pse.beast.toolbox;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class XMLtoolbox.
 *
 * @author Lukas Stapelbroek
 */
public final class XMLtoolbox {

    /**
     * Instantiates a new XM ltoolbox.
     */
    private XMLtoolbox() { }

    /**
     * Removes all empty text child nodes from a node taken from the following
     * link. https://stackoverflow.com/questions/978810/
     * how-to-strip-whitespace-only-text-nodes-from-a-dom-before-serialization/16285664#16285664
     *
     * @param node
     *            the node which child items should be cleaned
     */
    public static void clean(final Node node) {
        final NodeList childNodes = node.getChildNodes();

        for (int n = childNodes.getLength() - 1; n >= 0; n--) {
            final Node child = childNodes.item(n);
            final short nodeType = child.getNodeType();

            if (nodeType == Node.ELEMENT_NODE) {
                clean(child);
            } else if (nodeType == Node.TEXT_NODE) {
                final String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0) {
                    node.removeChild(child);
                } else {
                    child.setNodeValue(trimmedNodeVal);
                }
            } else if (nodeType == Node.COMMENT_NODE) {
                node.removeChild(child);
            }
        }
    }
}
