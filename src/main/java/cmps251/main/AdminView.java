package cmps251.main;

/**
 * This class contains all the visualization related operations and functions in the Sehha hospital reception system
 * 
 * <p> This is where you can print out the different information contained within the system.
 * The code contained within this class is solely for printing information.
 * Processing data should be done in the {@code AdminRepository} class.
 * 
 * <p> <i>Created on 17/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.14
 * @since		1.7
 */
public final class AdminView {



/* ------------------------------ Query Methods ----------------------------- */
//region

	/** 
	 * Starts a new form to add a new patient to the system
	 */
	public static void addPatient() {

	}

//endregion



/* ---------------------------- Graphical Methods --------------------------- */
//region

	// public static void showSlots

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

public static void moveCursor(int x, int y) {
	char escapeCode = 0x1B;
	System.out.printf("%c[%d;%df", escapeCode, x, y);
}

//endregion


	
}
