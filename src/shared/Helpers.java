package shared;

import java.util.ArrayList;

import models.User;

public class Helpers {

	public static String showMessage(String err) {
		switch (err) {
		case "user.notFound": {
			return "The user is not found.";
		}

		case "User.NotFound": {
			return "The user is not found";
		}

		case "Username.taken": {
			return "The username is already taken.";
		}
		case "credential.incorrect": {
			return "The login or password are incorrect.";
		}
		case "password.incorrect": {
			return "incorrect password";
		}
		case "email.invalid": {
			return "Invalid email format.";
		}
		case "age.invalid": {
			return "Age must be an integer";
		}
		case "email.Invalid": {
			return "Email should be of form: exemple@exemple.com or example.example..@example.com";
		}

		case "password.invalid": {
			return "Password must contain at least six letters.";
		}

		case "password.missMatch": {
			return "The password doesnt match the confirmed one.";
		}
		case "general.InvalidParam": {
			return "All the parameters are required!";
		}
		case "passwords.empty": {
			return "Check password fields";
		}
		
		case "Student.Empty":{
			return "Please enter the name of your school";
		}
		case "Degree.Empty":{
			return "Please enter your degree";
		}
		
		case "Passwrod.Empty" :{
			return "Enter your password to submit changes.";
		}
		case "General" :{
			return "Something went wrong, please try again.";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + err);
		}

	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			int isInt = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}

	}

	public static String[] arrayListToStringArray(ArrayList<String> arrayList) {

		String[] stringArray = new String[arrayList.size()];

		for (int i = 0; i < arrayList.size(); i++) {
			stringArray[i] = arrayList.get(i);
		}

		return stringArray;
	}

	public static ArrayList<String> userToArrayList(User user) {
		ArrayList<String> result = new ArrayList<String>();

		result.add(user.getUsername());
		result.add(user.getFirstName());
		result.add(user.getLastName());
//		result.add(user.getDegree());
		result.add(user.getEmail());
		result.add(user.getSex());
		result.add(String.valueOf(user.getAge()));

		return result;
	}

}
