package cmps251.components;

import java.util.function.Function;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * This is a component used to add a button to table rows
 * 
 * <p> <i>Created on 20/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.15
 * @since		1.15
 */
public class TableCellButton<T> extends TableCell<T, Button> {



/* --------------------------- Private Attributes --------------------------- */
//region

	private final Button cellButton;

//endregion



/* ------------------------------ Constructors ------------------------------ */
//region

	/**
	 * This constructor takes in a label and callback function.
	 * 
	 * <p> <b>NOTE</b>: to create a new instance, use {@code createCellButton} instead!
	 *
	 * @param label	   				- the label to be displayed on the button
	 * @param callback 		   	   	- the function to be called when the button is clicked
	 */
	private TableCellButton(String label, Function<T, T> callback) {
		this.cellButton = new Button(label);
		this.cellButton.setOnAction((ActionEvent e) -> {
			callback.apply((T) getTableView().getItems().get(getIndex()));
		});
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/**
	 * This function creates a new table cell button in the form of a callback
	 * This is meant to be the default constructor to be used
	 * 
	 * <p> This function should be called inside {@code setCellFactory} of a table column component
	 *
	 * @param label	   				- the label to be displayed on the button
	 * @param callback 		   	   	- the function to be called when the button is clicked
	 */
	public static <T> Callback<TableColumn<T, Button>, TableCell<T, Button>> createCellButton(String label, Function<T, T> callback) {
		return param -> new TableCellButton<>(label, callback);
	}

	/**
	 * This function is called whenever the item in this table row is updated 
	 * 
	 * @param item	   				- the instance of the button
	 * @param empty 		   	   	- is there data in this row?
	 */
	@Override
	protected void updateItem(Button item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) setGraphic(null);
		else setGraphic(cellButton);
	}

//endregion



}
