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

    public final String getCbmcAmountMaxVotersVarName() {
        return cbmcAmountMaxVotersVarName;
    }

    public final String getCbmcAssertName() {
        return assertName;
    }

    public final void setCbmcAssertName(final String cbmcAssertName) {
        this.assertName = cbmcAssertName;
    }

    public final String getCbmcAmountMaxSeatsVarName() {
        return cbmcAmountMaxSeatsVarName;
    }

    public final void setCbmcAmountMaxSeatsVarName(final String cbmcAmountSeatsVarName) {
        this.cbmcAmountMaxSeatsVarName = cbmcAmountSeatsVarName;
    }

    public final String getCbmcAmountMaxCandsVarName() {
        return cbmcAmountMaxCandidatesVarName;
    }

    public final void setCbmcAmountMaxVotersVarName(final String cbmcAmountVotesVarName) {
        this.cbmcAmountMaxVotersVarName = cbmcAmountVotesVarName;
    }

    public final void setCbmcAmountMaxCandidatesVarName(final String cbmcAmountCandidatesVarName) {
        this.cbmcAmountMaxCandidatesVarName = cbmcAmountCandidatesVarName;
    }

    public final String getVotesLowerBoundVarName() {
        return lowerVoteBoundVarName;
    }

    public final String getVotesUpperBoundVarName() {
        return upperVoteBoundVarName;
    }

    public final String getCbmcAssumeName() {
        return assumeName;
    }

    public final String getCbmcNondetUintName() {
        return nondeterministicUintName;
    }

    public final void setVotesLowerBoundVarName(final String votesLowerBoundVarName) {
        this.lowerVoteBoundVarName = votesLowerBoundVarName;
    }

    public final void setVotesUpperBoundVarName(final String votesUpperBoundVarName) {
        this.upperVoteBoundVarName = votesUpperBoundVarName;
    }

    public final void setCbmcAssumeName(final String cbmcAssumeName) {
        this.assumeName = cbmcAssumeName;
    }

    public final void setCbmcNondetUintName(final String cbmcNondetUintName) {
        this.nondeterministicUintName = cbmcNondetUintName;
    }

    public final String getCurrentAmountVotersVarName() {
        return currAmountVotersVarName;
    }

    public final String getCurrentAmountCandsVarName() {
        return currAmountCandsVarName;
    }

    public final String getCurrentAmountSeatsVarName() {
        return currAmountSeatsVarName;
    }

    public final void setCurrentAmountVotersVarName(final String currentAmountVotersVarName) {
        this.currAmountVotersVarName = currentAmountVotersVarName;
    }

    public final void setCurrentAmountCandsVarName(final String currentAmountCandsVarName) {
        this.currAmountCandsVarName = currentAmountCandsVarName;
    }

    public final void setCurrentAmountSeatsVarName(final String currentAmountSeatsVarName) {
        this.currAmountSeatsVarName = currentAmountSeatsVarName;
    }
}
