package edu.pse.beast.codeareajavafx;

public class BallotModifierCodeArea extends BoundedVarCodeArea{
    private NewCodeArea mainCodeArea;
    public BallotModifierCodeArea(NewCodeArea parent) {
        super();
        mainCodeArea = parent;
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    parent.getElectionDescription().setBallotModifier(this.getText());
                });
    }
}
