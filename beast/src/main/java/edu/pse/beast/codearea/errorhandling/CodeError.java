package edu.pse.beast.codearea.errorhandling;

import java.util.HashMap;

/**
 * The Class CodeError.
 *
 * @author Holger Klein
 */
public class CodeError {

    /** The line. */
    private int line;

    /** The pos in line. */
    private int posInLine;

    /** The id. */
    private String id;

    /** The error number. */
    private int errorNumber;

    /** The start pos. */
    private int startPos;

    /** The end pos. */
    private int endPos;

    /** The extra info. */
    private HashMap<String, String> extraInfo = new HashMap<>();

    /**
     * Instantiates a new code error.
     *
     * @param lineNum the line num
     * @param positionInLine the position in line
     * @param idString the id string
     * @param errorNum the error num
     * @param startPosition the start position
     * @param endPosition the end position
     */
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

    /**
     * Gets the extra info.
     *
     * @param idString the id string
     * @return the extra info
     */
    public String getExtraInfo(final String idString) {
        return extraInfo.get(idString);
    }

    /**
     * Sets the extra info.
     *
     * @param idString the id string
     * @param extra the extra
     */
    public void setExtraInfo(final String idString,
                             final String extra) {
        extraInfo.put(idString, extra);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the error number.
     *
     * @return the error number
     */
    public int getErrorNumber() {
        return errorNumber;
    }

    /**
     * Gets the line.
     *
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets the pos in line.
     *
     * @return the pos in line
     */
    public int getPosInLine() {
        return posInLine;
    }

    /**
     * Gets the msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return extraInfo.get("msg");
    }

    /**
     * Gets the start pos.
     *
     * @return the start pos
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Sets the start pos.
     *
     * @param startPosition the new start pos
     */
    public void setStartPos(final int startPosition) {
        this.startPos = startPosition;
    }

    /**
     * Gets the end pos.
     *
     * @return the end pos
     */
    public int getEndPos() {
        return endPos;
    }

    /**
     * Sets the end pos.
     *
     * @param endPosition the new end pos
     */
    public void setEndPos(final int endPosition) {
        this.endPos = endPosition;
    }
}
