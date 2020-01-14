//package edu.pse.beast.options.codeareaoptions;
//
//import java.awt.Font;
//import java.awt.GraphicsEnvironment;
//import java.util.ArrayList;
//
//import edu.pse.beast.codearea.CodeArea;
//import edu.pse.beast.options.Options;
//import edu.pse.beast.stringresource.StringResourceLoader;
//
///**
// * Options subclass for the CodeAreaOptions.
// */
//public class CodeAreaOptions extends Options {
//    private CodeArea codeArea;
//    private final FontTypeOptionElement fontType;
//    private final FontSizeOptionElement fontSize;
//    private final SpacesPerTabOptionElement spacesPerTab;
//
//    /**
//     * Constructor
//     * @param codeArea the code area
//     * @param loader the StringResourceLoader
//     */
//    public CodeAreaOptions(CodeArea codeArea,
//                           StringResourceLoader loader) {
//        super("codearea_opts");
//        String fonts[] =
//            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        ArrayList<String> possibleFonts = new ArrayList<>();
//        for (int i = 0; i < fonts.length; i++) {
//            String font = fonts[i];
//            possibleFonts.add(font);
//        }
//        fontType = new FontTypeOptionElement(possibleFonts, loader.getStringFromID("fonttype"));
//
//        ArrayList<String> possibleSizes = new ArrayList<>();
//        for (int i = 5; i < 30; i++) {
//            possibleSizes.add(String.valueOf(i));
//        }
//
//        fontSize = new FontSizeOptionElement(possibleSizes, loader.getStringFromID("fontsize"));
//
//        ArrayList<String> possibleTabSizes = new ArrayList<>();
//        for (int i = 2; i < 30; i++) {
//            possibleTabSizes.add(String.valueOf(i));
//        }
//
//        spacesPerTab =
//            new SpacesPerTabOptionElement(possibleTabSizes,
//                                          loader.getStringFromID("spaces_per_tab"));
//
//        optElements.add(fontType);
//        optElements.add(fontSize);
//        optElements.add(spacesPerTab);
//    }
//
//    public CodeAreaOptions(CodeArea codeArea) {
//        super("codearea_opts");
//        String fonts[] =
//            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        ArrayList<String> possibleFonts = new ArrayList<>();
//        for (int i = 0; i < fonts.length; i++) {
//            String font = fonts[i];
//            possibleFonts.add(font);
//        }
//        fontType = new FontTypeOptionElement(possibleFonts, "Arial");
//
//        ArrayList<String> possibleSizes = new ArrayList<>();
//        for (int i = 5; i < 30; i++) {
//            possibleSizes.add(String.valueOf(i));
//        }
//
//        fontSize = new FontSizeOptionElement(possibleSizes, "12");
//
//        ArrayList<String> possibleTabSizes = new ArrayList<>();
//        for (int i = 2; i < 30; i++) {
//            possibleTabSizes.add(String.valueOf(i));
//        }
//
//        spacesPerTab = new SpacesPerTabOptionElement(possibleTabSizes, "8");
//
//        optElements.add(fontType);
//        optElements.add(fontSize);
//        optElements.add(spacesPerTab);
//    }
//
//    @Override
//    protected void reapplySpecialized() {
//        Font f = new Font(fontType.getChosenOption(), Font.PLAIN, fontSize.getsize());
//        codeArea.setFont(f);
//        codeArea.getInsertToCode().getTabInserter()
//            .setAmountSpacesPerTab(spacesPerTab.getNumberTabs());
//    }
//
//    /**
//     * Setter for the new CodeArea
//     * @param codeArea the new CodeArea
//     */
//    public void setCodeArea(CodeArea codeArea) {
//        this.codeArea = codeArea;
//    }
//}
