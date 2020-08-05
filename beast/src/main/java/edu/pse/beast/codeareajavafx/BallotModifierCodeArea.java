package edu.pse.beast.codeareajavafx;

public class BallotModifierCodeArea extends BoundedVarCodeArea{
    private NewCodeArea mainCodeArea;
    public BallotModifierCodeArea(NewCodeArea parent) {
        super();
        mainCodeArea = parent;
        parent.setBallotModifierCodeArea(this);
        replaceText(parent.getElectionDescription().getBallotModifier());
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    parent.getShallowElectionDescription().setBallotModifier(this.getText());
                });
    }
}
