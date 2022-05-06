package shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import models.User;

public class SkillsToDB {

	public static void skillToDB(ArrayList<String> li) throws Exception {
		int size = li.size();
		for(int i = 0; i < li.size(); i++) {
			
		}
	}
	
	// société name;
	
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn.prepareStatement(
				"select id from skills where name=?");
		st.setString(1, "kjlkj");
		ResultSet rs = st.executeQuery();
		
		if (rs.next()) {
			System.out.println("HEY");
			int id = rs.getInt("id");
			PreparedStatement st2 = (PreparedStatement) conn.prepareStatement(
					"insert into user_skills(user_id, skill_id) values(1, ?)");
			st2.setInt(1, id);
			int rs2 = st2.executeUpdate();
			conn.close();
		} else {
			PreparedStatement st2 = (PreparedStatement) conn.prepareStatement(
					"insert into skills(name) values(?)");
			st2.setString(1, "kjlkj");
			int rs2 = st2.executeUpdate();
			conn.close();
			// it means that the user is not found.
		}
//		File dir = new File("skills");
//		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
//				MysqlCon.DBPassword);
//		File[] directoryListing = dir.listFiles();
//		if (directoryListing != null) {
//			for (File child : directoryListing) {
//				System.out.println("CHILD : " + child.getName() + "====================\n\n\n");
//				File f = new File("skills/" + child.getName());
//				Scanner reader = new Scanner(f);
//				while (reader.hasNextLine()) {
//					String data = reader.nextLine();
//					PreparedStatement st = (PreparedStatement) conn.prepareStatement(
//							"insert into skills(name) values(?)");
//					st.setString(1, data);
//					int rs = st.executeUpdate();
//				}
//				reader.close();
//			}
//		} else {
//			System.out.println("NULL");
//		}
	}
}
