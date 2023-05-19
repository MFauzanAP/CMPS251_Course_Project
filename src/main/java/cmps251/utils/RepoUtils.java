package cmps251.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class offers some utility methods related to the repository and files
 * 
 * <p> <i>Created on 17/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.10
 * @since		1.8
 */
public class RepoUtils {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static final String DATA_DIRECTORY = "data/";

//endregion



/* ---------------------------------- Enums --------------------------------- */
//region

	public enum DataType {
		PATIENT("patients.dat"), SERVICE("services.dat"), SLOT("slots.dat");
		private String fileName;
		private DataType(String fileName) { this.fileName = DATA_DIRECTORY + fileName; }
		public String getFileName() { return fileName; }
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Loads data of the given type from the respective file
	 * 
	 * @param type						- the type of data being loaded
	 * 
	 * @throws IOException				if there was a problem loading the data
	 * @throws ClassNotFoundException	if the file was corrupted
	 */
	public static Object loadDataFromFile(DataType type) throws IOException, ClassNotFoundException {

		//	Get the final file path
		String path = type.getFileName();

		//	Try and load data from the specified file based on the type
		try {

			//	Create a new object output stream
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(path));

			//	Load data from the specified file
			Object obj = input.readObject();
			input.close();

			//	Return the results
			return obj;

		}
		catch (IOException e) {

			//	Print error message
			System.err.printf("We were unable to load data from the file %s! %n", path);
			System.err.printf("The following error occurred: %s! %n", e.getMessage());
			throw new IOException(String.format("We were unable to load data from the file %s!", path));
			
		}
		catch (ClassNotFoundException e) {

			//	Print error message
			System.err.printf("The file at %s is corrupted! %n", path);
			System.err.printf("The following error occurred: %s! %n", e.getMessage());
			throw new ClassNotFoundException(String.format("The file at %s is corrupted!", path));
			
		}

	}

	/** 
	 * Saves the given values to the appropriate file
	 * 
	 * @param type						- the type of data being saved
	 * @param values					- the values to be saved
	 */
	public static void saveDataToFile(DataType type, Object values) {

		//	Get the final file path
		String path = type.getFileName();

		//	Try and save the values to the specified file based on the type
		try {

			//	Create a new object output stream
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path));

			//	Export the given values to the file
			output.writeObject(values);
			output.close();

		}
		catch (IOException e) {

			//	Print error message
			System.err.printf("We were unable to save data of the type %s! %n", type.toString());
			System.err.printf("The following error occurred: %s! %n", e.getMessage());
			
		}

	}

//endregion


	
}
