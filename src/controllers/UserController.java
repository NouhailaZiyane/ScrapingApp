package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.Skill;
import models.User;
import repositories.SkillRepository;
import repositories.UserRepository;
import shared.MysqlCon;

public class UserController {
	public static boolean login(String username, String password) throws Exception {
		return UserRepository.checkLogin(username, password);
	}

	public static boolean updateUser(String username, String firstName, String lastName, String oldPassword,
			String newPassword, String confirm, String age, String email, ArrayList<Skill> updatedSkills,
			String status, String degreeSchool) throws Exception {
		return UserRepository.updateUser(username, firstName, lastName, oldPassword, newPassword, confirm, age, email,
				updatedSkills, status, degreeSchool);

	}

	public static boolean register(String username, String firstName, String lastName, String password, String confirm,
			String sexe, String age, String degree, String email, boolean isStudent, boolean isGraduate, String school)
			throws Exception {
		return UserRepository.register(username, firstName, lastName, password, confirm, sexe, age, degree, email,
				isStudent, isGraduate, school);
	}

	public static User getUserByUsername(String username) throws Exception {
		return UserRepository.getUserByUsername(username);
	}

	public static ArrayList<Skill> getUserSkills(String username) throws Exception {
		return UserRepository.getUserSkills(username);
	}

}
