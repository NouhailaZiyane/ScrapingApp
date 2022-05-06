package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Skill;
import models.User;
import shared.Helpers;
import shared.MyException;
import shared.MysqlCon;

public class UserRepository {

	public static User getUserByUsername(String username) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn.prepareStatement(
				"select username, first_name, last_name, sexe, email, age from users where username=?");
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			User user = new User(rs.getString("username"), rs.getString("first_name"), rs.getString("last_name"), null,
					rs.getString("sexe"), rs.getString("email"), Integer.valueOf(rs.getString("age"))); // Null is for
																										// the password
			conn.close();
			return user;
		} else {
			conn.close();
			// it means that the user is not found.
			return new User();
		}

	}

	private static boolean checkIfUserExists(String userName) throws Exception {
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select * from users where username=?");
			st.setString(1, userName);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				conn.close();
				return true;
			} else {
				conn.close();
				// it means that the user is not found.
				return false;
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean checkLogin(String userName, String password) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);
		PreparedStatement st = (PreparedStatement) conn
				.prepareStatement("select * from users where username=? and password=?");
		st.setString(1, userName);
		st.setString(2, password);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		} else {
			conn.close();
			// it means that the user is not found.
			return false;
		}

	}

	public static ArrayList<Skill> getUserSkills(String username) throws Exception {
		return SkillRepository.getSkillsByUsername(username);
	}

	private static boolean createUser(String username, String firstName, String lastName, String password, String sexe,
			int age, String degree, String email, String school) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);
		PreparedStatement st = (PreparedStatement) conn.prepareStatement(
				"insert into users(username, first_name, last_name, password, sexe, age , email) values(?, ?, ?, ?, ?, ?, ?)");
		st.setString(1, username);
		st.setString(2, firstName);
		st.setString(3, lastName);
		st.setString(4, password);
		st.setString(5, sexe);
		st.setInt(6, age);
		st.setString(7, email);

		int success = st.executeUpdate();

		if (success != 1) {
			return false;
		}

		PreparedStatement st1 = conn.prepareStatement("select id from users where username=?");
		st1.setString(1, username);

		ResultSet rs1 = st1.executeQuery();

		if (rs1.next()) {
			int userID = rs1.getInt("id");
			// Since we added verification it's either the degree is empty or the school.
			if (degree.length() != 0) {
				PreparedStatement st2 = conn.prepareStatement("insert into graduates(user_id, degree) values(?, ?)");
				st2.setInt(1, userID);
				st2.setString(2, degree);
				int success2 = st2.executeUpdate();
				conn.close();
				return success2 == 1;
			} else {
				PreparedStatement st2 = conn.prepareStatement("insert into students(user_id, school) values(?, ?)");
				st2.setInt(1, userID);
				st2.setString(2, school);
				int success2 = st2.executeUpdate();
				conn.close();
				return success2 == 1;
			}

		}
		conn.close();
		return false;

	}

	private static int getUserId(String username) {
		int id = -1;
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select id from users where username=?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return id;
	}

	private static boolean updateUserSkills(String username, ArrayList<Skill> skills) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		int userID = getUserId(username);
		PreparedStatement st = (PreparedStatement) conn.prepareStatement("delete from user_skills where user_id=?");

		st.setInt(1, userID);

		st.executeUpdate();
		if(skills.size() == 0) {
			return true;
		}
		String query = "insert into user_skills(user_id, skill_id) values";
		for (int i = 0; i < skills.size(); i++) {
			Skill skill = skills.get(i);
			query += "(" + userID + "," + skill.getSkillID() + ") " + (i == (skills.size() - 1) ? "" : ", ");
		}

		System.out.println(query);
		PreparedStatement st1 = (PreparedStatement) conn.prepareStatement(query);

		st1.executeUpdate();
		return true;

	}

//	String firstName, String lastName, String newPassword, String oldPassword, int age, String email, ArrayList<String> skills
	public static boolean updateUser(String username, String firstName, String lastName, String oldPassword,
			String newPassword, String confirm, String age, String email, ArrayList<Skill> updatedSkills, String status,
			String degreeSchool) throws Exception {

		int userID = getUserId(username);

		if (userID == -1) {
			throw new MyException("User.NotFound");
		}

		if (validUpdates(username, firstName, lastName, oldPassword, newPassword, confirm, age, email, status,
				degreeSchool)) {
			// If all data are validated.
			if (newPassword.length() > 0) {
				// it means that the user has filled the new password field and wants to updated
				// password.
				oldPassword = newPassword;
			}

			// this cannot fail since it's already checked.
			int parsedAge = Integer.parseInt(age);

			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"update users set first_name=?, last_name=?, password=?,  age=? , email=? where username=?");
			// for basic information
			st.setString(1, firstName);
			st.setString(2, lastName);
			st.setString(3, oldPassword);
			st.setInt(4, parsedAge);
			st.setString(5, email);
			st.setString(6, username);

			int success = st.executeUpdate();
			conn.close();
			if (success == 1) {
				if (updateUserSkills(username, updatedSkills)) {
					if (status.equals("Graduate")) {
						updateDegree(userID, degreeSchool);
					}
					if (status.equals("Student")) {
						updateSchool(userID, degreeSchool);
					}

					// Update degree or school

					return true;
				}

			}
		}
		throw new MyException("General");
	}

	private static void updateDegree(int user_id, String degree) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn
				.prepareStatement("update graduates set degree=? where user_id=?");
		st.setString(1, degree);
		st.setInt(2, user_id);
		st.executeUpdate();
	}

	private static void updateSchool(int user_id, String school) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn
				.prepareStatement("update students set school=? where user_id=?");
		st.setInt(2, user_id);
		st.setString(1, school);
		st.executeUpdate();
	}

	private static boolean validUpdates(String username, String firstName, String lastName, String oldPassword,
			String newPassword, String confirm, String age, String email, String status, String degreeSchool)
			throws Exception {

		if (status.equals("Student") && degreeSchool.length() == 0) {
			throw new MyException("Student.Empty");
		}

		if (status.equals("Graduate") && degreeSchool.length() == 0) {
			throw new MyException("Degree.Empty");
		}

		if (!checkIfUserExists(username)) {
			throw new MyException("User.NotFound");
		}

		if (oldPassword.length() == 0) {
			throw new MyException("Passwrod.Empty");
		}
		// it means that the user wants to change password.
		if (!checkLogin(username, oldPassword)) {
			System.out.println(oldPassword + "!!!!" + "   AGe " + age);
			throw new MyException("password.incorrect");
		}

		if (newPassword.length() != 0 && !newPassword.equals(confirm)) {
			throw new MyException("password.missMatch");
		}

		if (firstName.length() == 0 || lastName.length() == 0 || email.length() == 0 || age.length() == 0

		) {
			throw new MyException("general.InvalidParam");
		}

		try {
			@SuppressWarnings("unused")
			int parseAge = Integer.valueOf(age);
		} catch (Exception err) {
			throw new MyException("age.invalid");
		}
		Pattern patt = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(email);
		if (!matcher.find()) {
			throw new MyException("email.Invalid");
		}

		return true;
	}

	public static boolean register(String username, String firstName, String lastName, String password, String confirm,
			String sexe, String age, String degree, String email, boolean isStudent, boolean isGraduate, String school)
			throws Exception {

		if (!Helpers.isInteger(age)) {
			throw new MyException("age.invalid");
		}

		int parsedAge = Integer.valueOf(age);
		if (username.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || password.length() == 0
				|| confirm.length() == 0 || sexe.length() == 0) {
			throw new MyException("general.InvalidParam");
		}

		if (isStudent && school.length() == 0) {
			throw new MyException("Student.Empty");
		}

		if (isGraduate && degree.length() == 0) {
			throw new MyException("Degree.Empty");
		}

		Pattern patt = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(email);

		if (!matcher.find()) {
			throw new MyException("email.Invalid");
		}

		if (!password.equals(confirm)) {
			throw new MyException("password.missMatch");
		}

		boolean userExists = checkIfUserExists(username);
		if (userExists) {
			throw new MyException("Username.taken");
			// It means that there's already a user by this username
		}

		if (createUser(username, firstName, lastName, password, sexe, parsedAge, degree, email, school)) {
			return true;
		}

		return false;

	}

	public static boolean isStudent(String username) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select school from students left join users on users.id=students.user_id where username=?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				conn.close();
				return true;
			} else {
				conn.close();
				// it means that the user is not found.
				return false;
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			return false;
		}
	}

	public static boolean isGraduate(String username) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select degree from graduates left join users on users.id=graduates.user_id where username=?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				conn.close();
				return true;
			} else {
				conn.close();
				// it means that the user is not found.
				return false;
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			return false;
		}
	}

	public static String getDegree(String username) {
		System.out.println("HER");
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select degree from graduates left join users on users.id=graduates.user_id where username=?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				String result = rs.getString("degree");
				conn.close();
				return result;
			} else {
				conn.close();
				// it means that the user is not found.
				return "";
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			return "";
		}
	}

	public static String getSchool(String username) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select school from students left join users on users.id=students.user_id where username=?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				String result = rs.getString("school");
				conn.close();
				return result;
			} else {
				conn.close();
				// it means that the user is not found.
				return "";
			}

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			return "";
		}
	}
}
