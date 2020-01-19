package edu.pse.beast.options.ceditoroptions;

//import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.CElectionCodeArea;
//import edu.pse.beast.options.Options;
//import edu.pse.beast.options.codeareaoptions.CodeAreaOptions;
//
///**
// * Options subclass for the CElectionCodeAreaOptions.
// */
//public class CElectionCodeAreaOptions extends Options {
//    private CElectionCodeArea cElecCodeArea;
//    private final CodeAreaOptions codeAreaOptions;
//
//    /**
//     * Constructor
//     * @param cElecCodeArea the cCodeArea
//     * @param codeAreaOptions the options
//     */
//    public CElectionCodeAreaOptions(CElectionCodeArea cElecCodeArea,
//            CodeAreaOptions codeAreaOptions) {
//        super("ccodearea_opts");
//        this.cElecCodeArea = cElecCodeArea;
//        this.codeAreaOptions = codeAreaOptions;
//        subOptions.add(codeAreaOptions);
//    }
//
//    CElectionCodeAreaOptions(CElectionCodeArea codeArea) {
//        super("ccodearea_opts");
//        this.cElecCodeArea = codeArea;
//        this.codeAreaOptions = new CodeAreaOptions(codeArea);
//        subOptions.add(codeAreaOptions);
//    }
//
//    /**
//     * Getter
//     * @return the options
//     */
//    public CodeAreaOptions getCodeAreaOptions() {
//        return codeAreaOptions;
//    }
//
//    @Override
//    protected void reapplySpecialized() {
//    }
//
//    /**
//     * Setter for the new CodeArea
//     * @param codeArea new CElectionCodeArea object
//     */
//    public void setCodeArea(CElectionCodeArea codeArea) {
//        this.cElecCodeArea = codeArea;
//        this.codeAreaOptions.setCodeArea(codeArea);
//    }
//}
