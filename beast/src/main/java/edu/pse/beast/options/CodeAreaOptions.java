package edu.pse.beast.options;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.stringresource.StringResourceLoader;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

public class CodeAreaOptions extends Options {
    private CodeArea codeArea;
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
    public CodeAreaOptions(CodeArea codeArea, 
            StringResourceLoader loader) {
        super("codearea_opts");
        String fonts[] = 
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        ArrayList<String> possibleFonts = new ArrayList<>();
        for (int i = 0; i < fonts.length; i++) {
            String font = fonts[i];
            possibleFonts.add(font);
        }
        fontType = new FontTypeOptionElement(possibleFonts, loader.getStringFromID("fonttype"));
        
        ArrayList<String> possibleSizes = new ArrayList<>();
        for (int i = 5; i < 30; i++) {
            possibleSizes.add(String.valueOf(i));
        }
        
        fontSize = new FontSizeOptionElement(possibleSizes, loader.getStringFromID("fontsize"));
        
        ArrayList<String> possibleTabSizes = new ArrayList<>();
        for (int i = 2; i < 30; i++) {
            possibleSizes.add(String.valueOf(i));
        }
        
        spacesPerTab = new SpacesPerTabOptionElement(possibleSizes, loader.getStringFromID("spaces_per_tab"));
        
        optElements.add(fontType);
        optElements.add(fontSize);
        optElements.add(spacesPerTab);
    }  

    CodeAreaOptions(CodeArea codeArea) {
        super("codearea_opts");
        String fonts[] = 
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        ArrayList<String> possibleFonts = new ArrayList<>();
        for (int i = 0; i < fonts.length; i++) {
            String font = fonts[i];
            possibleFonts.add(font);
        }
        fontType = new FontTypeOptionElement(possibleFonts, "Arial");
        
        ArrayList<String> possibleSizes = new ArrayList<>();
        for (int i = 5; i < 30; i++) {
            possibleSizes.add(String.valueOf(i));
        }
        
        fontSize = new FontSizeOptionElement(possibleSizes, "12");
        
        ArrayList<String> possibleTabSizes = new ArrayList<>();
        for (int i = 2; i < 30; i++) {
            possibleSizes.add(String.valueOf(i));
        }
        
        spacesPerTab = new SpacesPerTabOptionElement(possibleSizes, "8");        
        
        optElements.add(fontType);
        optElements.add(fontSize);
        optElements.add(spacesPerTab);
    }

    @Override
    protected void reapplySpecialized() {
        Font f = new Font(fontType.getChosenOption(), Font.PLAIN, fontSize.getsize());
        codeArea.getPane().setFont(f);
        codeArea.getInsertToCode().getTabInserter().setAmountSpacesPerTab(spacesPerTab.getNumberTabs());
    }

    void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
   

}
