package edu.pse.beast.highlevel.javafx;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import edu.pse.beast.propertychecker.Result;

/**
 * The Class ResultTreeItem.
 *
 * @author Lukas Stapelbroek
 */
public class ResultTreeItem extends CustomTreeItem {

    /** The result. */
    private final Result result;

    /** The owner. */
    private final ChildTreeItem owner;

    /** The name. */
    private Label name = new Label("Result");

    /** The button. */
    private Button button = new Button("delete");

    /**
     * Instantiates a new result tree item.
     *
     * @param resultVal
     *            the result val
     * @param ownerItem
     *            the owner item
     */
    public ResultTreeItem(final Result resultVal,
                          final ChildTreeItem ownerItem) {
        this.result = resultVal;
        this.owner = ownerItem;
        this.result.setOwner(this);
        init();
    }

    /**
     * Inits the.
     */
    private void init() {
        this.setAlignment(Pos.CENTER_LEFT);
        button.setOnAction(event -> {
            owner.deleteResult(this);
        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                wasClicked();
            }
        });
        this.getChildren().add(name);
        this.getChildren().add(new Separator(Orientation.VERTICAL));
        this.getChildren().add(button);
    }

    /**
     * Was clicked.
     */
    public void wasClicked() {
        if (result != null) {
            owner.showResult(result);
        }
    }
    //
    // private List<String> presentResult() {
    // CodeArrayListBeautifier resultString = new CodeArrayListBeautifier();
    // resultString.add("Result for electionDescription: "
    // + result.getElectionDescription().getName());
    // resultString.add(SEPARATOR_LINE);
    // resultString.add("Final bounds:");
    // resultString.add("voters: " + result.getNumVoters());
    // resultString.add("candidates: " + result.getNumCandidates());
    // resultString.add("seats: " + result.getNumSeats());
    // resultString.add(SEPARATOR_LINE);
    // resultString.add("");
    // resultString.add("");
    // resultString.add("VOTING DATA");
    //
    // System.out.println("fix result presentation: in resultTreeItem");
    //
    //// int amountVotes = 3; // TODO hotfix, get the real amount of voting and
    // elect arrays later
    //// for (int i = 0; i < amountVotes; i++) {
    //// resultString.add("");
    //// resultString.add("VOTE " + i);
    ////
    //// List<List<String>> votes
    //// = result.getElectionDescription().getContainer()
    //// .getInputType().getNewVotes(result.getResult(), i);
    ////
    //// for (Iterator<List<String>> iterator = votes.iterator();
    // iterator.hasNext();) {
    //// List<String> list = (List<String>) iterator.next();
    //// String toAdd = "";
    //// for (Iterator<String> iterator2 = list.iterator();
    // iterator2.hasNext();) {
    //// String vote = (String) iterator2.next();
    //// toAdd = toAdd + " " + vote;
    //// }
    //// resultString.add(toAdd);
    //// }
    //// }
    //// resultString.add(SEPARATOR_LINE);
    //// resultString.add("");
    //// resultString.add("");
    //// resultString.add("ELECTION DATA");
    //// amountVotes = 0;
    //// for (int i = 0; i < amountVotes; i++) {
    //// resultString.add("");
    //// resultString.add("ELECTION " + i);
    //// List<String> elects =
    // result.getElectionDescription().getContainer().getOutputType()
    //// .getNewResult(result.getResult(), i);
    //// String toAdd = "";
    //// for (Iterator<String> iterator = elects.iterator();
    // iterator.hasNext();) {
    //// String string = (String) iterator.next();
    //// toAdd = toAdd + " " + string;
    //// }
    //// resultString.add(toAdd);
    //// }
    // return resultString.getCodeArrayList();
    // }

    /**
     * Sets the presentable.
     */
    public void setPresentable() {
        if (result != null && result.isFinished()) {
            if (result.isMarginComp() || result.isTest()) {
                this.setBackground(new Background(new BackgroundFill(Color.AQUA,
                                   CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (!result.isValid()) {
                this.setBackground(new Background(new BackgroundFill(
                                   Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                if (result.isSuccess()) {
                    this.setBackground(new Background(new BackgroundFill(
                                       Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    this.setBackground(new Background(new BackgroundFill(
                                       Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public ChildTreeItem getOwner() {
        return owner;
    }
}
