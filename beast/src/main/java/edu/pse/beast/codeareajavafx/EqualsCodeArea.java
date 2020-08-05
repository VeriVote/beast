package edu.pse.beast.codeareajavafx;

import edu.pse.beast.highlevel.javafx.MenuBarInterface;

public class EqualsCodeArea extends BoundedVarCodeArea {
    private NewCodeArea mainCodeArea;
    public EqualsCodeArea(NewCodeArea parent) {
        super();
        mainCodeArea = parent;
        parent.setEqualsCodeArea(this);

        replaceText(parent.getElectionDescription().getEqualsFunction());
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    parent.getShallowElectionDescription().setEqualsFunction(this.getText());
                });
    }
}
