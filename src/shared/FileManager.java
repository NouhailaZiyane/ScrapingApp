package shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.User;
import repositories.UserRepository;

// Since username is unique we are going to create text files named by username.
// The presence of a file in the system means that the user is logged in.
public class FileManager {

	public static String globalPath = "localStorage";

	private static boolean createFile(String fileName) {
		try {
			File createdFile = new File(globalPath + "/" + fileName);
			createdFile.getParentFile().mkdirs();
			return createdFile.createNewFile();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	private static boolean writeToFile(ArrayList<String> items, String fileName) {
		try {
			System.out.println("HERE");
			FileWriter writer = new FileWriter(fileName);
			for (int i = 0; i < items.size(); i++) {
				writer.write(items.get(i) + String.format("%n"));
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	public static boolean createFileByUsername(String username) throws Exception{

		User user = UserRepository.getUserByUsername(username);
		ArrayList<String> items = Helpers.userToArrayList(user);
		if (items.get(0).length() == 0) {
			System.out.println("User not found.");
			return false;
		}

		String fileName = username + ".txt";
		File file = new File(globalPath, fileName);
		if (file.exists()) {
			// if the file already exists we should delete it
			if (!file.delete()) {
				return false;
			}
			System.out.println("DELETED --> " + fileName);

		}
		System.out.println("ITEMS :" + items.toString());

		// Whether the file exists or not we create a file at the end.
		if (createFile(fileName)) {
			System.out.println("CREATED --> " + fileName);
			return true;
		}

		return false;

	}

	public static ArrayList<String> getUserFromLocalStorage(String username) {
		ArrayList<String> response = new ArrayList<String>();
		String fileName = globalPath + "/" + username + ".txt";
		try {
			File file = new File(fileName);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				response.add(reader.nextLine());
			}

			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return response;
	}

	public static boolean fileExists(String username) {
		String fileName = username + ".txt";

		File file = new File(globalPath, fileName);
		return file.exists();
	}

	public static ArrayList<String> listFilesInLocalStorage() {
		ArrayList<String> result = new ArrayList<String>();
		
		  File dir = new File(globalPath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		     result.add(child.getName().replace(".txt", ""));
		    }
		  } 
		return result;
	}
	
	public static void clearLocalStorage() {
		File dir = new File(globalPath);
		for(File file: dir.listFiles()) 
		    if (!file.isDirectory()) 
		        file.delete();
	}
}
