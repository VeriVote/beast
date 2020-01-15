package edu.pse.beast.codearea.errorhandling;

import java.util.HashMap;

/**
 *
 * @author Holger Klein
 */
public class CodeError {
    private int line;
    private int posInLine;
    private String id;
    private int errorNumber;
    private int startPos;
    private int endPos;
    private HashMap<String, String> extraInfo = new HashMap<>();

    public CodeError(final int lineNum,
                     final int positionInLine,
                     final String idString,
                     final int errorNum,
                     final int startPosition,
                     final int endPosition) {
        this.line = lineNum;
        this.posInLine = positionInLine;
        this.id = idString;
        this.errorNumber = errorNum;
        this.startPos = startPosition;
        this.endPos = endPosition;
    }

    public String getExtraInfo(final String idString) {
        return extraInfo.get(idString);
    }

    public void setExtraInfo(final String idString,
                             final String extra) {
        extraInfo.put(idString, extra);
    }

    public String getId() {
        return id;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public int getLine() {
        return line;
    }

    public int getPosInLine() {
        return posInLine;
    }

    public String getMsg() {
        return extraInfo.get("msg");
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(final int startPosition) {
        this.startPos = startPosition;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(final int endPosition) {
        this.endPos = endPosition;
    }
}
