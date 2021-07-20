package edu.pse.beast.api.codegen.cbmc;

public class CodeGenOptions {
    public static final String MAIN_FUNC_NAME = "main";
    private String lowerVoteBoundVarName;
    private String upperVoteBoundVarName;

    private String assumeName;
    private String assertName;
    private String nondeterministicUintName;

    private String cbmcAmountMaxVotersVarName = "MAX_AMT_VOTERS";
    private String cbmcAmountMaxCandidatesVarName = "MAX_AMT_CANDIDATES";
    private String cbmcAmountMaxSeatsVarName = "MAX_AMT_SEATS";

    private String currAmountVotersVarName = "V";
    private String currAmountCandsVarName = "C";
    private String currAmountSeatsVarName = "S";

    public String getCbmcAmountMaxVotersVarName() {
        return cbmcAmountMaxVotersVarName;
    }

    public String getCbmcAssertName() {
        return assertName;
    }

    public void setCbmcAssertName(final String cbmcAssertName) {
        this.assertName = cbmcAssertName;
    }

    public String getCbmcAmountMaxSeatsVarName() {
        return cbmcAmountMaxSeatsVarName;
    }

    public void setCbmcAmountMaxSeatsVarName(final String cbmcAmountSeatsVarName) {
        this.cbmcAmountMaxSeatsVarName = cbmcAmountSeatsVarName;
    }

    public String getCbmcAmountMaxCandsVarName() {
        return cbmcAmountMaxCandidatesVarName;
    }

    public void setCbmcAmountMaxVotersVarName(final String cbmcAmountVotesVarName) {
        this.cbmcAmountMaxVotersVarName = cbmcAmountVotesVarName;
    }

    public void setCbmcAmountMaxCandidatesVarName(final String cbmcAmountCandidatesVarName) {
        this.cbmcAmountMaxCandidatesVarName = cbmcAmountCandidatesVarName;
    }

    public String getVotesLowerBoundVarName() {
        return lowerVoteBoundVarName;
    }

    public String getVotesUpperBoundVarName() {
        return upperVoteBoundVarName;
    }

    public String getCbmcAssumeName() {
        return assumeName;
    }

    public String getCbmcNondetUintName() {
        return nondeterministicUintName;
    }

    public void setVotesLowerBoundVarName(final String votesLowerBoundVarName) {
        this.lowerVoteBoundVarName = votesLowerBoundVarName;
    }

    public void setVotesUpperBoundVarName(final String votesUpperBoundVarName) {
        this.upperVoteBoundVarName = votesUpperBoundVarName;
    }

    public void setCbmcAssumeName(final String cbmcAssumeName) {
        this.assumeName = cbmcAssumeName;
    }

    public void setCbmcNondetUintName(final String cbmcNondetUintName) {
        this.nondeterministicUintName = cbmcNondetUintName;
    }

    public String getCurrentAmountVotersVarName() {
        return currAmountVotersVarName;
    }

    public String getCurrentAmountCandsVarName() {
        return currAmountCandsVarName;
    }

    public String getCurrentAmountSeatsVarName() {
        return currAmountSeatsVarName;
    }

    public void setCurrentAmountVotersVarName(final String currentAmountVotersVarName) {
        this.currAmountVotersVarName = currentAmountVotersVarName;
    }

    public void setCurrentAmountCandsVarName(final String currentAmountCandsVarName) {
        this.currAmountCandsVarName = currentAmountCandsVarName;
    }

    public void setCurrentAmountSeatsVarName(final String currentAmountSeatsVarName) {
        this.currAmountSeatsVarName = currentAmountSeatsVarName;
    }
}
