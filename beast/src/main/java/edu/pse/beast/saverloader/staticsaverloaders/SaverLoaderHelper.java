package edu.pse.beast.saverloader.staticsaverloaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Holger Klein
 */
public class SaverLoaderHelper {
    private String currentSaveString;

    public String getStringForAttr(final String attrName, final String content) {
        return attrName.length() + " " + attrName + content.length() + " " + content;
    }

    public String getStringForAttr(final String attrName, final int content) {
        String contentString = String.valueOf(content);
        return attrName.length() + " " + attrName + contentString.length() + " " + contentString;
    }

    public String getStringForAttr(final String attrName, final List<String> content) {
        StringBuilder code = new StringBuilder();
        content.forEach(s -> code.append(s + "\n"));
        return getStringForAttr(attrName, code.toString());
    }

    public Map<String, String> parseSaveString(final String saveFile) {
        Map<String, String> attrNameToContent = new HashMap<>();
        currentSaveString = saveFile;
        try {
            while (currentSaveString.length() > 0) {
                int len = getNumberAndRemoveNumberPartFromString();
                String attr = getAttrFromStringAndRemoveIt(len);
                len = getNumberAndRemoveNumberPartFromString();
                String cont = getAttrFromStringAndRemoveIt(len);
                attrNameToContent.put(attr, cont);
            }
        } catch (StringIndexOutOfBoundsException ex) {
            // if the string gets loaded from the file it ends with a \n which
            // causes the load algorithm to throw a StringIndexOutOfBoundsException
            // this exception can safely be ignored
        }
        return attrNameToContent;
    }

    private String getAttrFromStringAndRemoveIt(final int len) {
        String attr = currentSaveString.substring(0, len);
        currentSaveString = currentSaveString.substring(len);
        return attr;
    }

    private int getNumberAndRemoveNumberPartFromString() {
        int i = 0;
        while (currentSaveString.charAt(i) != ' ') {
            i++;
        }
        String numberS = currentSaveString.substring(0, i);
        currentSaveString = currentSaveString.substring(i + 1);
        return Integer.valueOf(numberS);
    }
}
