package edu.pse.beast.codearea.codeinput;

import java.util.ArrayList;

/**
 * This class creates all available chars with a corresponding open or closing
 * char
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharList {
    private ArrayList<OpenCloseChar> openCloseChars = new ArrayList<OpenCloseChar>();
    private Character[] openChars = {'{', '[', '(', '"'};
    private Character[] closeChars = {'}', ']', ')', '"'};

    public OpenCloseCharList() {
        initializeOpenCloseChars();
    }

    public boolean isOpenChar(char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getOpen() == c)
                return true;
        }
        return false;
    }

    public OpenCloseChar getOpenCloseChar(char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getOpen() == c || openCloseChars.get(i).getClose() == c)
                return openCloseChars.get(i);
        }
        return null;
    }

    private void initializeOpenCloseChars() {
        for (int i = 0; i < openChars.length; ++i) {
            openCloseChars.add(new OpenCloseChar(openChars[i], closeChars[i]));
        }
    }

    public boolean isCloseChar(char c) {
        for (int i = 0; i < openCloseChars.size(); ++i) {
            if (openCloseChars.get(i).getClose() == c)
                return true;
        }
        return false;
    }
}