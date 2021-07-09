package edu.pse.beast.api.codegen.cbmc;

public class CodeGenOptions {
    public static final String MAIN_FUNC_NAME = "main";
    private String votesLowerBoundVarName;
    private String votesUpperBoundVarName;

    private String cbmcAssumeName;
    private String cbmcAssertName;
    private String cbmcNondetUintName;

    private String cbmcAmountMaxVotersVarName = "MAX_AMT_VOTERS";
    private String cbmcAmountMaxCandidatesVarName = "MAX_AMT_CANDIDATES";
    private String cbmcAmountMaxSeatsVarName = "MAX_AMT_SEATS";

    private String currentAmountVotersVarName = "V";
    private String currentAmountCandsVarName = "C";
    private String currentAmountSeatsVarName = "S";

    public String getCbmcAmountMaxVotersVarName() {
        return cbmcAmountMaxVotersVarName;
    }

    public String getCbmcAssertName() {
        return cbmcAssertName;
    }

    public void setCbmcAssertName(String cbmcAssertName) {
        this.cbmcAssertName = cbmcAssertName;
    }

    public String getCbmcAmountMaxSeatsVarName() {
        return cbmcAmountMaxSeatsVarName;
    }

    public void setCbmcAmountMaxSeatsVarName(String cbmcAmountSeatsVarName) {
        this.cbmcAmountMaxSeatsVarName = cbmcAmountSeatsVarName;
    }

    public String getCbmcAmountMaxCandsVarName() {
        return cbmcAmountMaxCandidatesVarName;
    }

    public void setCbmcAmountMaxVotersVarName(String cbmcAmountVotesVarName) {
        this.cbmcAmountMaxVotersVarName = cbmcAmountVotesVarName;
    }

    public void setCbmcAmountMaxCandidatesVarName(
            String cbmcAmountCandidatesVarName) {
        this.cbmcAmountMaxCandidatesVarName = cbmcAmountCandidatesVarName;
    }

    public String getVotesLowerBoundVarName() {
        return votesLowerBoundVarName;
    }

    public String getVotesUpperBoundVarName() {
        return votesUpperBoundVarName;
    }

    public String getCbmcAssumeName() {
        return cbmcAssumeName;
    }

    public String getCbmcNondetUintName() {
        return cbmcNondetUintName;
    }

    public void setVotesLowerBoundVarName(String votesLowerBoundVarName) {
        this.votesLowerBoundVarName = votesLowerBoundVarName;
    }

    public void setVotesUpperBoundVarName(String votesUpperBoundVarName) {
        this.votesUpperBoundVarName = votesUpperBoundVarName;
    }

    public void setCbmcAssumeName(String cbmcAssumeName) {
        this.cbmcAssumeName = cbmcAssumeName;
    }

    public void setCbmcNondetUintName(String cbmcNondetUintName) {
        this.cbmcNondetUintName = cbmcNondetUintName;
    }

    public String getCurrentAmountVotersVarName() {
        return currentAmountVotersVarName;
    }

    public String getCurrentAmountCandsVarName() {
        return currentAmountCandsVarName;
    }

    public String getCurrentAmountSeatsVarName() {
        return currentAmountSeatsVarName;
    }

    public void setCurrentAmountVotersVarName(
            String currentAmountVotersVarName) {
        this.currentAmountVotersVarName = currentAmountVotersVarName;
    }

    public void setCurrentAmountCandsVarName(String currentAmountCandsVarName) {
        this.currentAmountCandsVarName = currentAmountCandsVarName;
    }

    public void setCurrentAmountSeatsVarName(String currentAmountSeatsVarName) {
        this.currentAmountSeatsVarName = currentAmountSeatsVarName;
    }

}
