package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.Company;
import shared.MysqlCon;

public class CompanyRepository {

	public static Company getCompanyById(int id) throws Exception {
		Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
				MysqlCon.DBPassword);

		PreparedStatement st = (PreparedStatement) conn.prepareStatement("select * from companies where id=?");

		st.setInt(1, id);

		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			String name = rs.getString("name");
			String secteur_activite = rs.getString("secteur_activite");
			String description = rs.getString("description");
			conn.close();
			return new Company(name, secteur_activite, description);
		} else {
			return new Company();
		}

	}

}
