package edu.pse.beast.options;

import edu.pse.beast.codearea.CodeArea;

public class CodeAreaOptions {
    private final CodeArea codeArea;
    private final FontTypeOptionElement fontType;
    private final FontSizeOptionElement fontSize;
    private final SpacesPerTabOptionElement spacesPerTab;

    /**
     * 
     * @param codeArea the code area
     * @param fontType the font type
     * @param fontSize the font size
     * @param spacesPerTab the spaces per tab
     */
    public CodeAreaOptions(CodeArea codeArea, FontTypeOptionElement fontType, FontSizeOptionElement fontSize,
            SpacesPerTabOptionElement spacesPerTab) {
        this.codeArea = codeArea;
        this.fontType = fontType;
        this.fontSize = fontSize;
        this.spacesPerTab = spacesPerTab;
    }

    /**
     * 
     * @return the code area
     */
    public CodeArea getCodeArea() {
        return codeArea;
    }

    /**
     * 
     * @return the font type
     */
    public FontTypeOptionElement getFontType() {
        return fontType;
    }

    /**
     * 
     * @return the font size
     */
    public FontSizeOptionElement getFontSize() {
        return fontSize;
    }

    /**
     * 
     * @return the spaces per tabs
     */
    public SpacesPerTabOptionElement getSpacesPerTab() {
        return spacesPerTab;
    }

}
