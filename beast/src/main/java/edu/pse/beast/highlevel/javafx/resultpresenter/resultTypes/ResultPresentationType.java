package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.highlevel.javafx.resultpresenter.ResultPresenterNEW;
import edu.pse.beast.propertychecker.Result;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

public abstract class ResultPresentationType {
	public abstract Node presentResult(Result result);

	public abstract String getName();

	public abstract String getToolTipDescription();

	/**
	 * 
	 * @return all implementations of this class
	 */
	public static List<ResultPresentationType> getImplementations() {
		ServiceLoader<ResultPresentationType> loader = ServiceLoader.load(ResultPresentationType.class);

		List<ResultPresentationType> implementations = new ArrayList<ResultPresentationType>();
		for (Iterator<ResultPresentationType> iterator = loader.iterator(); iterator.hasNext();) {
			ResultPresentationType implementation = iterator.next();
			implementations.add(implementation);
		}
		return implementations;
	}

	public MenuItem getMenuItem() {
		CustomMenuItem item = new CustomMenuItem(new Label(getName()));

		Tooltip tip = new Tooltip(getToolTipDescription());

		Tooltip.install(item.getContent(), tip);
		
		item.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ResultPresenterNEW.getInstance().setPresentationType(ResultPresentationType.this);
			}
		});
		
		return item;
	}
}
