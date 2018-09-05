package edu.pse.beast.highlevel.javafx;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ResultPresenter;
import edu.pse.beast.types.cbmctypes.inputplugins.Preference;
import edu.pse.beast.types.cbmctypes.outputplugins.CandidateList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ResultTreeItem extends CustomTreeItem {

	private final Result result;
	private final ChildTreeItem owner;

	private Label name = new Label("Result");
	private Button button = new Button("delete");
	private ImageView statusIcon = new ImageView();

	public ResultTreeItem(Result result, ChildTreeItem owner) {
		this.result = result;
		this.owner = owner;

		this.result.setOwner(this);

		init();
	}

	private void init() {
		this.setAlignment(Pos.CENTER_LEFT);

		button.setOnAction((event) -> {
			owner.deleteResult(this);
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				wasClicked();
			}
		});

		this.getChildren().add(name);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.getChildren().add(button);
	}

	public void wasClicked() {
		if (result != null && result.isFinished()) {
			if (!result.isValid()) {
			} else {
				if (result.isSuccess()) {
					GUIController.getController().getResultField().setText("ASSERTION HOLDS");
					GUIController.getController().getMainTabPane().getSelectionModel()
							.select(GUIController.getController().getResultTab());
				} else {
					ResultPresenter.presentFailureExample(result);

					GUIController.getController().getResultField().setText(String.join("\n", presentResult()));
					GUIController.getController().getMainTabPane().getSelectionModel()
							.select(GUIController.getController().getResultTab());
				}
			}
		}
	}

	private List<String> presentResult() {
		CodeArrayListBeautifier resultString = new CodeArrayListBeautifier();

		resultString.add("Result for electionDescription: " + result.getElectionDescription().getName());
		resultString.add("===========================================================================");
		resultString.add("Final bounds:");
		resultString.add("voters: " + result.getNumVoters());
		resultString.add("candidates: " + result.getNumCandidates());
		resultString.add("seats: " + result.getNumSeats());
		resultString.add("===========================================================================");
		resultString.add("");
		resultString.add("");
		resultString.add("VOTING DATA");

		int amountVotes = 3; // TODO hotfix, get the real amount of voting and elect arrays later

		for (int i = 0; i < amountVotes; i++) {

			resultString.add("");
			resultString.add("VOTE " + i);

			List<List<String>> votes = result.getElectionDescription().getContainer().getInputType()
					.getNewVotes(result.getResult(), i);

			for (Iterator<List<String>> iterator = votes.iterator(); iterator.hasNext();) {
				List<String> list = (List<String>) iterator.next();
				String toAdd = "";

				for (Iterator<String> iterator2 = list.iterator(); iterator2.hasNext();) {
					String vote = (String) iterator2.next();
					toAdd = toAdd + " " + vote;
				}

				resultString.add(toAdd);
			}

		}

		resultString.add("===========================================================================");
		resultString.add("");
		resultString.add("");
		resultString.add("ELECTION DATA");

		
		amountVotes = 0;
		
		
		for (int i = 0; i < amountVotes; i++) {

			resultString.add("");
			resultString.add("ELECTION " + i);

			List<String> elects = result.getElectionDescription().getContainer().getOutputType()
					.getNewResult(result.getResult(), i);

			String toAdd = "";
			for (Iterator<String> iterator = elects.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				toAdd = toAdd + " " + string;
			}

			resultString.add(toAdd);

		}

		return resultString.getCodeArrayList();

	}

	public void setPresentable() {
		if (result != null && result.isFinished()) {
			if (!result.isValid()) {
				this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			} else {
				if (result.isSuccess()) {
					this.setBackground(
							new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				} else {
					this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
				}
			}
		}

	}

	public Result getResult() {
		return result;
	}

	public ChildTreeItem getOwner() {
		return owner;
	}

}
