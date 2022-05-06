package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.Skill;
import shared.MysqlCon;

public class SkillRepository {

	public static ArrayList<Skill> getAllSkills() {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select id,name from skills");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Skill skill = new Skill(rs.getInt("id"), rs.getString("name"));
				skills.add(skill);
			}
			conn.close();

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return skills;
	}

	public static ArrayList<String> getSkillsNotInUser(String username) {
		ArrayList<String> skills = new ArrayList<String>();

		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select name from skills where name not in (select name from skills left join user_skills on user_skills.skill_id=skills.id left join users on user_skills.user_id=users.id where users.username=?)");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				skills.add(rs.getString("name"));
			}
			conn.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return skills;
	}

	public static ArrayList<Skill> getSkillsByUsername(String username) throws Exception {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn.prepareStatement(
				"select skills.id, name from skills left join user_skills on user_skills.skill_id=skills.id left join users on user_skills.user_id=users.id where users.username=?");
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Skill tempSkill = new Skill(rs.getInt("id"), rs.getString("name"));
			skills.add(tempSkill);
		}
		conn.close();

		return skills;
	}

	public static ArrayList<Skill> test() {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select id, name from skills");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Skill tempSkill = new Skill(rs.getInt("id"), rs.getString("name"));
				skills.add(tempSkill);
			}
			conn.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return skills;
	}

	public static Skill getSkillByName(String name) {
		Skill skill = new Skill();
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select id from skills where name=?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				skill = new Skill(rs.getInt("id"), name);
			}
			conn.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return skill;

	}

	public static ArrayList<Skill> searchSkills(String query) {
		ArrayList<Skill> skills = new ArrayList<Skill>();

		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn
					.prepareStatement("select id,name from skills where name like '%" + query + "%'");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Skill tempSkill = new Skill(rs.getInt("id"), rs.getString("name"));
				skills.add(tempSkill);
			}
			conn.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return skills;
	}

	public static String displaySkills(ArrayList<Skill> skills) {
		String result = "";

		for (int i = 0; i < skills.size(); i++) {
			result += skills.get(i).getName() + ", ";
		}

		return result;
	}
}
